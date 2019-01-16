package com.sinoyd.artifact.service;

import com.sinoyd.artifact.entity.ConsumableDetail;
import com.sinoyd.artifact.entity.Storage;
import com.sinoyd.artifact.repository.ConsumableBaseRepository;
import com.sinoyd.artifact.repository.ConsumableDetailRepository;
import com.sinoyd.artifact.repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.MissingResourceException;

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

    @Transactional
    public void save(ConsumableDetail detailInfo){
        Integer consumablesId = detailInfo.getConsumablesId();
        if(consumablesId == null||consumableBaseRepository.countById(detailInfo.getConsumablesId())==0){
            throw new IllegalArgumentException("输入错误 必须输入消耗品id");
        }
        consumableDetailRepository.save(detailInfo);
        Storage storage = storageRepository.findByConsumablesId(consumablesId);
        if(storage == null) {
            throw new NullPointerException("无法找到对应的库存");
        }
        storage.setStore(storage.getStore()+detailInfo.getInsertNum());
        storageRepository.save(storage);
    }


    public Collection<ConsumableDetail> find(Integer id){
        return consumableDetailRepository.findByConsumablesId(id);
    }


}
