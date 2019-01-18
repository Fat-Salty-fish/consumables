package com.sinoyd.artifact.repository;

import com.sinoyd.artifact.entity.ApplyBase;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-15 14:05
 */
public interface ApplyBaseRepository extends CrudRepository<ApplyBase,Integer> {
    Integer deleteAllByIdIn(Collection<Integer> ids);
}
