package com.sinoyd.artifact.repository;

import com.sinoyd.artifact.entity.Storage;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-15 14:08
 */
public interface StorageRepository extends CrudRepository<Storage,Integer> {
    Storage findByConsumablesId(Integer consumablesId);

    List<Storage> findByConsumablesIdIn(Collection<Integer> ids);

    Integer deleteAllByConsumablesIdIn(Collection<Integer> ids);
}
