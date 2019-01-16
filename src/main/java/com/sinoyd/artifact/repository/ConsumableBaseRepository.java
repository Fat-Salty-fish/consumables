package com.sinoyd.artifact.repository;

import com.sinoyd.artifact.entity.ConsumableBase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-15 14:06
 */
public interface ConsumableBaseRepository extends JpaRepository<ConsumableBase,Integer> {
    Integer deleteAllByIdIn(Collection<Integer> ids);
    Integer countById(Integer id);
}
