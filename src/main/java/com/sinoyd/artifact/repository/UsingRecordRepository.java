package com.sinoyd.artifact.repository;

import com.sinoyd.artifact.entity.UsingRecord;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-15 14:09
 */
public interface UsingRecordRepository extends CrudRepository<UsingRecord,Integer> {
    List<UsingRecord> findByApplyId(Integer applyId);
    Integer deleteAllByApplyId(Integer applyId);
}
