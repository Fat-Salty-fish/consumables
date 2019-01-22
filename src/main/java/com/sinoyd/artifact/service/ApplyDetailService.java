package com.sinoyd.artifact.service;

import com.alibaba.fastjson.JSON;
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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
     * @param detailsInfo
     */
    @Transactional
    public void create(Map<String,Object> detailsInfo){
        Integer applyId = (Integer) detailsInfo.get("applyId");
        if(applyId == null){
            throw new IllegalArgumentException("输入错误 申请基础信息id为空!");
        }
        ApplyDetail applyDetail = JSON.parseObject(JSON.toJSONString(detailsInfo.get("detailData")),ApplyDetail.class);
        if(applyDetail == null){
            throw new NullPointerException("输入错误 新增的数据为空!");
        }
        applyDetail.setApplyId(applyId);
        applyDetail.setRemain(consumableDetailRepository.findByConsumablesId(applyDetail.getConsumablesId()).stream().collect(Collectors.summingInt(ConsumableDetail::getCurrentNum))-applyDetail.getApplyNum());
        applyDetailRepository.save(applyDetail);
    }

    /**
     * 提交申请详细信息 根据保存的详细信息内容 减去库存中有的消耗品 保存到数据库中 并更新申请状态为'申请提交'
     * @param submitInfo
     */
    @Transactional
    public void submit(Map<String,Object> submitInfo){
        Integer applyId = (Integer) submitInfo.get("applyId");
        if(applyId == null){
            throw new NullPointerException("输入错误 申请基础信息id为空!");
        }
        String checkPerson = (String) submitInfo.get("checkPerson");
        if(!StringUtils.isNotNullAndEmpty(checkPerson)){
            throw new NullPointerException("输入错误 审核人为空!");
        }
        ApplyBase applyBase = applyBaseRepository.findOne(applyId);
        List<ApplyDetail> applyDetails = applyDetailRepository.findByApplyId(applyId);
        List<ConsumableDetail> consumableDetails = consumableDetailRepository.findByConsumablesIdInOrderByDateInStorage(applyDetails.stream().map(ApplyDetail::getConsumablesId).collect(toList()));
        List<UsingRecord> usingRecords = new ArrayList<>();
        for(ApplyDetail applyDetail:applyDetails){
            List<ConsumableDetail> consumableDetailList = consumableDetails.stream().filter(d->d.getConsumablesId().equals(applyDetail.getConsumablesId())).collect(toList());
            Integer numToSubtract = applyDetail.getApplyNum();
            for(ConsumableDetail consumableDetail:consumableDetailList){
                if(numToSubtract > 0 && consumableDetail.getCurrentNum() != 0) {
                    UsingRecord usingRecord = new UsingRecord();
                    usingRecord.setConsumablesId(consumableDetail.getConsumablesId());
                    usingRecord.setConsumablesDetailsId(consumableDetail.getId());
                    usingRecord.setApplyId(applyDetail.getApplyId());
                    Integer currentNum = consumableDetail.getCurrentNum();
                    if (currentNum >= numToSubtract) {
                        consumableDetail.setCurrentNum(currentNum - numToSubtract);
                        numToSubtract -= numToSubtract;
                        usingRecord.setUsingNum(numToSubtract);
                    } else {
                        consumableDetail.setCurrentNum(currentNum - currentNum);
                        numToSubtract -= currentNum;
                        usingRecord.setUsingNum(currentNum);
                    }
                    usingRecords.add(usingRecord);
                }
                else {
                    break;
                }
            }
        }
        applyBase.setCheckPerson(checkPerson);
        applyBase.setState("申请提交");
        applyBaseRepository.save(applyBase);
        consumableDetailRepository.save(consumableDetails);
        usingRecordRepository.save(usingRecords);
    }

    /**
     * 申请详细信息分页搜索
     * @param pageBean
     * @param consumableBaseAndApplyDetailInfoView
     */
    public void findByPage(PageBean pageBean, BaseCriteria consumableBaseAndApplyDetailInfoView){
        pageBean.setEntityName("ConsumableBaseAndApplyDetailInfoView a ");
        pageBean.setSelect("Select a");
        commonRepository.findByPage(pageBean,consumableBaseAndApplyDetailInfoView);
    }

    @Transactional
    public void review(Map<String,Object> reviewInfo){
        Integer applyId = (Integer) reviewInfo.get("applyId");
        String permitPerson = (String) reviewInfo.get("permitPerson");
        String comment = (String) reviewInfo.get("comment");
        if(applyId == null){
            throw new NullPointerException("输入错误 申请基础信息id为空!");
        }
        if(!StringUtils.isNotNullAndEmpty(permitPerson)){
            throw new NullPointerException("输入错误 发料人为空!");
        }
        ApplyBase applyBase = applyBaseRepository.findOne(applyId);
        applyBase.setPermitPerson(permitPerson);
        applyBase.setState("审核通过");
        applyBase.setComment(comment);
        applyBaseRepository.save(applyBase);
    }

    @Transactional
    public void permit(Map<String,Object> permitInfo){
        Integer applyId = (Integer) permitInfo.get("applyId");
        if(applyId == null){
            throw new NullPointerException("输入错误 申请基础信息id为空!");
        }
        ApplyBase applyBase = applyBaseRepository.findOne(applyId);
        applyBase.setState("已发料");
        applyBaseRepository.save(applyBase);
    }

    @Transactional
    public void reject(Map<String,Object> rejectInfo){
        Integer applyId = (Integer) rejectInfo.get("applyId");
        String comment = (String) rejectInfo.get("comment");
        if(applyId == null){
            throw new NullPointerException("输入错误 申请基础信息id为空!");
        }
        if(!StringUtils.isNotNullAndEmpty(comment)){
            throw new NullPointerException("输入错误 评审意见为空!");
        }
        ApplyBase applyBase = applyBaseRepository.findOne(applyId);
        List<UsingRecord> usingRecords = usingRecordRepository.findByApplyId(applyId);
        List<ConsumableDetail> consumableDetails = consumableDetailRepository.findByConsumablesIdInOrderByDateInStorage(usingRecords.stream().map(UsingRecord::getConsumablesDetailsId).collect(toList()));
        for(UsingRecord record:usingRecords){
            ConsumableDetail consumableDetail = consumableDetails.stream().filter(detail -> detail.getId().equals(record.getConsumablesDetailsId())).distinct().collect(toList()).get(0);
            consumableDetail.setCurrentNum(consumableDetail.getCurrentNum()+record.getUsingNum());
        }
        applyBase.setComment(comment);
        applyBase.setState("不发料");
        applyBaseRepository.save(applyBase);
        usingRecordRepository.deleteAllByApplyId(applyId);
    }

    @Transactional
    public void update(ApplyDetail detailInfo){
        if(detailInfo.getId() == null){
            throw new NullPointerException("输入错误 要修改的详细申请信息id为空!");
        }
        applyDetailRepository.save(detailInfo);
    }

    @Transactional
    public Integer delete(Collection<Integer> ids){
        if(ids==null || ids.size()==0){
            throw new NullPointerException("输入错误 要删除的详细申请信息id为空!");
        }
        return applyDetailRepository.deleteByIdIn(ids);
    }
}
