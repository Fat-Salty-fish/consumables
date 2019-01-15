package com.sinoyd.artifact.service;

import com.sinoyd.artifact.entity.ConsumableBase;
import com.sinoyd.artifact.repository.ConsumableBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.Collection;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-15 14:12
 */
@Service
public class ConsumableBaseService {
    @Autowired
    private ConsumableBaseRepository consumableBaseRepository;

    public void save(ConsumableBase baseInfo){

        consumableBaseRepository.save(baseInfo);
    }

    public ConsumableBase find(Integer id){
        return consumableBaseRepository.findOne(id);
    }

    @Transactional
    public Integer delete(Collection<Integer> ids){
        return consumableBaseRepository.deleteAllByIdIn(ids);
    }

    @Transactional
    public void update(ConsumableBase baseInfo){
        if(baseInfo.getId()==null){
            throw new IllegalArgumentException();
        }
        consumableBaseRepository.save(baseInfo);
    }

}
