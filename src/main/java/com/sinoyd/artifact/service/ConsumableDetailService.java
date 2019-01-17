package com.sinoyd.artifact.service;

import com.sinoyd.artifact.entity.ConsumableBase;
import com.sinoyd.artifact.entity.ConsumableDetail;
import com.sinoyd.artifact.entity.Storage;
import com.sinoyd.artifact.repository.ConsumableBaseRepository;
import com.sinoyd.artifact.repository.ConsumableDetailRepository;
import com.sinoyd.artifact.repository.StorageRepository;
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
    private StorageRepository storageRepository;

    @Autowired
    private CommonRepository commonRepository;

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
        consumableDetailRepository.save(detailInfo);
        Storage storage = storageRepository.findByConsumablesId(consumablesId);
        if(storage == null) {
            throw new NullPointerException("无法找到对应的库存");
        }
        storage.setStore(storage.getStore()+detailInfo.getInsertNum());
        storage.setIsLessThan(Storage.isLessThan(storage.getStore(),baseInfo.getWarningNum()));
        storageRepository.save(storage);
    }

    public void findByPage(PageBean pageBean, BaseCriteria consumableDetailInfoCriteria){
        pageBean.setEntityName("ConsumableDetail a");
        pageBean.setSelect("Select a");
        commonRepository.findByPage(pageBean,consumableDetailInfoCriteria);
    }
}
