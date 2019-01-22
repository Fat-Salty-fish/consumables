package com.sinoyd.artifact.service;

import com.sinoyd.artifact.criteria.ConsumableBaseAndDetailInfoViewCriteria;
import com.sinoyd.artifact.criteria.UsingRecordViewCriteria;
import com.sinoyd.artifact.entity.ConsumableBase;
import com.sinoyd.artifact.entity.ConsumableDetail;
import com.sinoyd.artifact.repository.ConsumableBaseRepository;
import com.sinoyd.artifact.repository.ConsumableDetailRepository;
import com.sinoyd.frame.base.repository.CommonRepository;
import com.sinoyd.frame.base.util.BaseCriteria;
import com.sinoyd.frame.base.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;


/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-15 14:13
 */
@Service
public class ConsumableDetailService {
    @Autowired
    private ConsumableDetailRepository consumableDetailRepository;

    @Autowired
    private ConsumableBaseRepository consumableBaseRepository;

    @Autowired
    private CommonRepository commonRepository;

    /**
     * 保存一条消耗品详细信息
     *
     * @param detailInfo
     */
    @Transactional
    public void save(ConsumableDetail detailInfo){
        Integer consumablesId = detailInfo.getConsumablesId();
        if(consumablesId == null){
            throw new IllegalArgumentException("输入错误 必须输入消耗品id");
        }
        ConsumableBase baseInfo = consumableBaseRepository.findOne(detailInfo.getConsumablesId());
        if(baseInfo==null){
            throw new IllegalArgumentException("输入错误 必须输入消耗品id");
        }
        detailInfo.setCurrentNum(detailInfo.getInsertNum());
        consumableDetailRepository.save(detailInfo);
    }

    /**
     * 消耗品详细信息分页搜索和模糊搜索
     * @param pageBean
     * @param consumableBaseAndDetailInfoViewCriteria
     */
    public void findByPage(PageBean pageBean, BaseCriteria consumableBaseAndDetailInfoViewCriteria){
        pageBean.setEntityName("ConsumableBaseAndDetailInfoView a");
        pageBean.setSelect("Select a");
        commonRepository.findByPage(pageBean,consumableBaseAndDetailInfoViewCriteria);
    }

    public void findUsingRecord(PageBean pageBean, BaseCriteria usingRecordViewCriteria){
        pageBean.setEntityName("UsingRecordView a ");
        pageBean.setSelect("Select a ");
        commonRepository.findByPage(pageBean,usingRecordViewCriteria);
    }
}
