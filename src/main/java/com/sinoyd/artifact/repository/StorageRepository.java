package com.sinoyd.artifact.repository;

import com.sinoyd.artifact.entity.Storage;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-15 14:08
 */
public interface StorageRepository extends CrudRepository<Storage,Integer> {
    Storage findByConsumablesId(Integer consumablesId);

    Integer deleteAllByConsumablesIdIn(Collection<Integer> ids);
}
