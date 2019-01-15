package com.sinoyd.artifact.repository;

import com.sinoyd.artifact.entity.ConsumableDetail;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-15 14:08
 */
public interface ConsumableDetailRepository extends CrudRepository<ConsumableDetail,Integer> {
    Integer deleteAllByIdIn(Collection<Integer> ids);

    Collection<ConsumableDetail> findByConsumablesId(Integer id);
}
