package com.sinoyd.artifact.service;

import com.sinoyd.artifact.entity.ConsumableDetail;
import com.sinoyd.artifact.repository.ConsumableBaseRepository;
import com.sinoyd.artifact.repository.ConsumableDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

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

    public void save(ConsumableDetail detailInfo){

        if(detailInfo.getConsumablesId()==null||consumableBaseRepository.countById(detailInfo.getConsumablesId())==0){
            throw new IllegalArgumentException("输入错误 必须输入消耗品id");
        }
        consumableDetailRepository.save(detailInfo);
    }

//    public ConsumableDetail find(Integer id){
//        return consumableDetailRepository.findOne(id);
//    }

    public Collection<ConsumableDetail> find(Integer id){
        return consumableDetailRepository.findByConsumablesId(id);
    }


}
