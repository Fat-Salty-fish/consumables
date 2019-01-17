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
    private Integer consumablesId;      //绑定的消耗品id
    @Column(nullable = false)
    private Integer store;              //总库存
    @Column(nullable = false)
    private Integer isLessThan;         //现有库存数是否低于警报值

    public Storage(){}

    public Storage(Integer consumablesId,Integer store,Integer isLessThan){
        this.consumablesId = consumablesId;
        this.store = store;
        this.isLessThan = isLessThan;
    }

    public static Integer isLessThan(Integer storeNum,Integer warningNum){
        if(storeNum==null||warningNum==null){
            return 1;
        }
        if(storeNum>warningNum){
            return 0;
        }
        else {
            return 1;
        }
    }
}
