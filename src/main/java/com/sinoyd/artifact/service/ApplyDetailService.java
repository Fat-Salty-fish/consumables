package com.sinoyd.artifact.service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinoyd.artifact.entity.ApplyBase;
import com.sinoyd.artifact.entity.ApplyDetail;
import com.sinoyd.artifact.entity.ConsumableDetail;
import com.sinoyd.artifact.entity.UsingRecord;
import com.sinoyd.artifact.repository.ApplyBaseRepository;
import com.sinoyd.artifact.repository.ApplyDetailRepository;
import com.sinoyd.artifact.repository.ConsumableDetailRepository;
import com.sinoyd.artifact.repository.UsingRecordRepository;
import com.sinoyd.frame.base.repository.CommonRepository;
import com.sinoyd.frame.base.util.BaseCriteria;
import com.sinoyd.frame.base.util.PageBean;
import com.sinoyd.frame.base.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-15 14:11
 */
@Service
public class ApplyDetailService {
    @Autowired
    private ApplyDetailRepository applyDetailRepository;

    @Autowired
    private ConsumableDetailRepository consumableDetailRepository;

    @Autowired
    private ApplyBaseRepository applyBaseRepository;

    @Autowired
    private CommonRepository commonRepository;

    @Autowired
    private UsingRecordRepository usingRecordRepository;

    /**
     * 保存申请详细信息 在已经有申请基础信息的前提下 将详细信息保存
     * 在保存申请的时候进行预申请计算 如果预申请的数量大于已经被预申请的数量 则预申请失败 申请驳回
     * 反之申请成功
     *
     * @param detailsInfo
     */
    @Transactional
    public void create(Map<String, Object> detailsInfo) {


        Integer applyId = (Integer) detailsInfo.get("applyId");
        if (applyId == null) {
            throw new NullPointerException("输入错误 申请基础信息id为空!");
        }
        ApplyDetail applyDetail = JSON.parseObject(JSON.toJSONString(detailsInfo.get("detailData")), ApplyDetail.class);
        if (applyDetail == null) {
            throw new NullPointerException("输入错误 新增的数据为空!");
        }


        List<ConsumableDetail> consumableDetails = consumableDetailRepository.findByConsumablesIdOrderByDateInStorage(applyDetail.getConsumablesId());
        if (applyDetail.getApplyNum() > consumableDetails.stream().collect(summingInt(ConsumableDetail::getVirtualNum))) {
            throw new IllegalArgumentException("预申请失败 申请数量大于库存量");
        }

        //进行预申请计算 对数据库中的虚拟库存进行删减
        List<UsingRecord> usingRecords = new ArrayList<>();
        Integer numToSubtract = applyDetail.getApplyNum();
        for (ConsumableDetail detail : consumableDetails) {
            Integer virtualNum = detail.getVirtualNum();
            if (numToSubtract > 0 && virtualNum > 0) {
                UsingRecord usingRecord = new UsingRecord();
                usingRecord.setApplyId(applyId);
                usingRecord.setConsumablesDetailsId(detail.getId());
                usingRecord.setConsumablesId(detail.getConsumablesId());

                detail.setVirtualNum(virtualNum - Math.min(numToSubtract, virtualNum));
                usingRecord.setUsingNum(Math.min(numToSubtract, virtualNum));
                numToSubtract = numToSubtract - Math.min(numToSubtract, virtualNum);

                usingRecords.add(usingRecord);
            }
        }
        usingRecordRepository.save(usingRecords);

        applyDetail.setApplyId(applyId);

        applyDetailRepository.save(applyDetail);
    }

    /**
     * 提交申请详细信息 更新申请状态为'申请提交'
     *
     * @param submitInfo
     */
    @Transactional
    public void submit(Map<String, Object> submitInfo) {
        Integer applyId = (Integer) submitInfo.get("applyId");
        if (applyId == null) {
            throw new NullPointerException("输入错误 申请基础信息id为空!");
        }
        String checkPerson = (String) submitInfo.get("checkPerson");
        if (!StringUtils.isNotNullAndEmpty(checkPerson)) {
            throw new NullPointerException("输入错误 审核人为空!");
        }
        //在提交审核时 只需要修改状态即可
        ApplyBase applyBase = applyBaseRepository.findOne(applyId);
        applyBase.setCheckPerson(checkPerson);
        applyBase.setState("申请提交");
        applyBaseRepository.save(applyBase);
    }

    /**
     * 申请详细信息分页搜索
     *
     * @param pageBean
     * @param consumableBaseAndApplyDetailInfoView
     */
    public void findByPage(PageBean pageBean, BaseCriteria consumableBaseAndApplyDetailInfoView) {
        pageBean.setEntityName("ConsumableBaseAndApplyDetailInfoView a ");
        pageBean.setSelect("Select a");
        commonRepository.findByPage(pageBean, consumableBaseAndApplyDetailInfoView);
    }

    /**
     * 审核通过 修改申请状态即可
     *
     * @param reviewInfo
     */
    @Transactional
    public void review(Map<String, Object> reviewInfo) {
        Integer applyId = (Integer) reviewInfo.get("applyId");
        String permitPerson = (String) reviewInfo.get("permitPerson");
        String comment = (String) reviewInfo.get("comment");
        if (applyId == null) {
            throw new NullPointerException("输入错误 申请基础信息id为空!");
        }
        if (!StringUtils.isNotNullAndEmpty(permitPerson)) {
            throw new NullPointerException("输入错误 发料人为空!");
        }
        ApplyBase applyBase = applyBaseRepository.findOne(applyId);
        applyBase.setPermitPerson(permitPerson);
        applyBase.setState("审核通过");
        applyBase.setComment(comment);
        applyBaseRepository.save(applyBase);
    }

    /**
     * 发料接口 在发料之后 应当在消耗品的真实库存中减去申请的消耗品 表明库存的数量真实地减少
     *
     * @param applyId
     */
    @Transactional
    public void permit(Integer applyId) {
        if (applyId == null) {
            throw new NullPointerException("输入错误 申请基础信息id为空!");
        }
        //设置真实库存数量
        ApplyBase applyBase = applyBaseRepository.findOne(applyId);
        List<UsingRecord> usingRecords = usingRecordRepository.findByApplyId(applyId);
        List<ConsumableDetail> consumableDetails = consumableDetailRepository.findByConsumablesIdInOrderByDateInStorage(usingRecords.stream().map(UsingRecord::getConsumablesId).collect(toList()));
        List<ApplyDetail> applyDetails = applyDetailRepository.findByApplyId(applyId);
        for (UsingRecord record : usingRecords) {
            ConsumableDetail consumableDetail = consumableDetails.stream().filter(consumable -> consumable.getId().equals(record.getConsumablesDetailsId())).distinct().collect(toList()).get(0);
            consumableDetail.setCurrentNum(consumableDetail.getCurrentNum() - record.getUsingNum());
        }
        //对ApplyDetail 设置申领后结余的库存数量
        for (ApplyDetail detail : applyDetails) {
            detail.setRemain(consumableDetails.stream().filter(consumableDetail -> consumableDetail.getConsumablesId().equals(detail.getConsumablesId())).distinct().collect(Collectors.summingInt(ConsumableDetail::getCurrentNum)));
        }
        applyBase.setState("已发料");
        applyDetailRepository.save(applyDetails);
        consumableDetailRepository.save(consumableDetails);
        applyBaseRepository.save(applyBase);
    }

    @Transactional
    public void personCheck(Integer applyId) {
        if (applyId == null) {
            throw new NullPointerException("输入错误 申请基础信息id为空!");
        }
        ApplyBase applyBase = applyBaseRepository.findOne(applyId);
        applyBase.setState("已领料");
        applyBaseRepository.save(applyBase);
    }

    /**
     * 申请驳回 在申请驳回时 需要将预申请的数量根据记录再加回去 使得之后的申领人员再申领时可以申领成功
     *
     * @param rejectInfo
     */
    @Transactional
    public void reject(Map<String, Object> rejectInfo) {
        Integer applyId = (Integer) rejectInfo.get("applyId");
        String comment = (String) rejectInfo.get("comment");
        if (applyId == null) {
            throw new NullPointerException("输入错误 申请基础信息id为空!");
        }
        if (!StringUtils.isNotNullAndEmpty(comment)) {
            throw new NullPointerException("输入错误 评审意见为空!");
        }
        ApplyBase applyBase = applyBaseRepository.findOne(applyId);
        List<UsingRecord> usingRecords = usingRecordRepository.findByApplyId(applyId);
        List<ConsumableDetail> consumableDetails = consumableDetailRepository.findByConsumablesIdInOrderByDateInStorage(usingRecords.stream().map(UsingRecord::getConsumablesId).collect(toList()));
        for (UsingRecord record : usingRecords) {
            ConsumableDetail consumableDetail = consumableDetails.stream().filter(detail -> detail.getId().equals(record.getConsumablesDetailsId())).distinct().collect(toList()).get(0);
            consumableDetail.setVirtualNum(consumableDetail.getVirtualNum() + record.getUsingNum());
        }
        applyBase.setComment(comment);
        applyBase.setState("不发料");
        applyBaseRepository.save(applyBase);
        usingRecordRepository.deleteAllByApplyId(applyId);
        consumableDetailRepository.save(consumableDetails);
    }

    @Transactional
    public void update(ApplyDetail detailInfo) {
        if (detailInfo.getId() == null) {
            throw new NullPointerException("输入错误 要修改的详细申请信息id为空!");
        }

        List<UsingRecord> usingRecords = usingRecordRepository.findByApplyId(detailInfo.getApplyId()).stream().filter(usingRecord -> usingRecord.getConsumablesId().equals(detailInfo.getConsumablesId())).collect(toList());
        List<ConsumableDetail> consumableDetails = consumableDetailRepository.findByConsumablesIdOrderByDateInStorage(detailInfo.getConsumablesId());

        for (UsingRecord record : usingRecords) {
            ConsumableDetail consumableDetail = consumableDetails.stream().filter(detail -> detail.getId().equals(record.getConsumablesDetailsId())).distinct().collect(toList()).get(0);
            consumableDetail.setVirtualNum(consumableDetail.getVirtualNum() + record.getUsingNum());
        }

        if (detailInfo.getApplyNum() > consumableDetails.stream().collect(Collectors.summingInt(ConsumableDetail::getVirtualNum))) {
            throw new IllegalArgumentException("预申请失败 申请数量大于库存量");
        }
        usingRecordRepository.delete(usingRecords);

        usingRecords = new ArrayList<>();
        Integer numToSubtract = detailInfo.getApplyNum();
        for (ConsumableDetail detail : consumableDetails) {
            Integer virtualNum = detail.getVirtualNum();
            if (numToSubtract > 0 && virtualNum > 0) {
                UsingRecord usingRecord = new UsingRecord();
                usingRecord.setApplyId(detailInfo.getApplyId());
                usingRecord.setConsumablesDetailsId(detail.getId());
                usingRecord.setConsumablesId(detail.getConsumablesId());

                detail.setVirtualNum(virtualNum - Math.min(numToSubtract, virtualNum));
                usingRecord.setUsingNum(Math.min(numToSubtract, virtualNum));
                numToSubtract = numToSubtract - Math.min(numToSubtract, virtualNum);

                usingRecords.add(usingRecord);
            }
        }
        usingRecordRepository.save(usingRecords);
        consumableDetailRepository.save(consumableDetails);
        applyDetailRepository.save(detailInfo);
    }

    @Transactional
    public Integer delete(Map<String, Object> deleteInfo) {
        Integer applyId = (Integer) deleteInfo.get("applyId");


        List<Integer> applyDetailIds = (List<Integer>) deleteInfo.get("applyDetailIds");
        if (applyDetailIds == null || applyDetailIds.size() == 0) {
            throw new NullPointerException("输入错误 要删除的详细申请信息id为空!");
        }

        List<ApplyDetail> applyDetails = applyDetailRepository.findByIdIn(applyDetailIds);
        List<Integer> consumablesIds = applyDetails.stream().map(ApplyDetail::getConsumablesId).collect(toList());

        List<UsingRecord> usingRecords = usingRecordRepository.findByApplyId(applyId).stream()
                .filter(usingRecord -> consumablesIds.contains(usingRecord.getConsumablesId()))
                .collect(toList());
        List<ConsumableDetail> consumableDetails = consumableDetailRepository.findByConsumablesIdInOrderByDateInStorage(consumablesIds);

        //将预申请的库存删除
        for (UsingRecord record : usingRecords) {
            ConsumableDetail consumableDetail =
                    consumableDetails.stream()
                    .filter(detail -> detail.getId().equals(record.getConsumablesDetailsId()))
                    .distinct().findFirst().orElse(null);

            consumableDetail.setVirtualNum(consumableDetail.getVirtualNum() + record.getUsingNum());
        }

        usingRecordRepository.delete(usingRecords);
        consumableDetailRepository.save(consumableDetails);
        return applyDetailRepository.deleteByIdIn(applyDetailIds);
    }

    @Transactional
    public void deleteAll(Integer applyId) {
        if (applyId == null) {
            throw new NullPointerException("输入错误 要删除的详细申请信息id为空!");
        }
        List<ApplyDetail> applyDetail = applyDetailRepository.findByApplyId(applyId);

        List<UsingRecord> usingRecords = usingRecordRepository.findByApplyId(applyId);
        List<ConsumableDetail> consumableDetails = consumableDetailRepository.findByConsumablesIdInOrderByDateInStorage(usingRecords.stream().map(UsingRecord::getConsumablesId).collect(toList()));
        for (UsingRecord record : usingRecords) {
            ConsumableDetail consumableDetail = consumableDetails.stream().filter(detail -> detail.getId().equals(record.getConsumablesDetailsId())).distinct().collect(toList()).get(0);
            consumableDetail.setVirtualNum(consumableDetail.getVirtualNum() + record.getUsingNum());
        }
        usingRecordRepository.deleteAllByApplyId(applyId);
        consumableDetailRepository.save(consumableDetails);
        applyDetailRepository.delete(applyDetail);
    }
}
