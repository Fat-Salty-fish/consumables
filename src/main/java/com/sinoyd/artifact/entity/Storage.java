package com.sinoyd.artifact.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-15 13:43
 */
@Entity
@Table(name = "t_storage")
@Getter
@Setter
public class Storage {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private Integer consumablesId;
    @Column(nullable = false)
    private Integer store;
    @Column(nullable = false)
    private Integer isLessThan;

    public Storage(){}

    public Storage(Integer consumablesId,Integer store,Integer isLessThan){
        this.consumablesId = consumablesId;
        this.store = store;
        this.isLessThan = isLessThan;
    }


}
