package com.sinoyd.artifact.repository;

import com.sinoyd.artifact.entity.ApplyDetail;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-15 14:06
 */
public interface ApplyDetailRepository extends CrudRepository<ApplyDetail,Integer> {
    Integer deleteAllByApplyIdIn(Collection<Integer> ids);
}
