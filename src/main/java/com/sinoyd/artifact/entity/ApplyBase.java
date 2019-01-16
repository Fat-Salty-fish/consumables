package com.sinoyd.artifact.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-15 13:48
 */
@Entity
@Table(name = "t_apply_base_info")
@Getter
@Setter
public class ApplyBase {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private String applyPerson;
    private String permitPerson;
    @Column(nullable = false)
    private Date applyDate;
    @Column(nullable = false)
    private String state;   //enum('审核新建','正在审核','审核通过','审核未通过')
    private String comment;
}
