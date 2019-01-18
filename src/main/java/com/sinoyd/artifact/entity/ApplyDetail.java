package com.sinoyd.artifact.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-15 13:54
 */
@Entity
@Table(name = "t_apply_detail_info")
@Getter
@Setter
public class ApplyDetail {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private Integer applyId;                //绑定的领用单基础表id
    @Column(nullable = false)
    private Integer consumablesId;          //绑定的消耗品id
    @Column(nullable = false)
    private Integer applyNum;               //领用的数量
    private String purpose;                 //领用目的
    private String remark;                  //备注
}
