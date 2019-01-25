package com.sinoyd.artifact.repository;

import com.sinoyd.artifact.entity.ApplyDetail;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-15 14:06
 */
public interface ApplyDetailRepository extends CrudRepository<ApplyDetail, Integer> {
    List<ApplyDetail> findByApplyId(Integer id);

    List<ApplyDetail> findByIdIn(Collection<Integer> ids);

    Integer deleteByIdIn(Collection<Integer> ids);
}
