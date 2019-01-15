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
    private Integer applyId;
    @Column(nullable = false)
    private Integer consumablesDetailsId;
    @Column(nullable = false)
    private Integer applyNum;
    private String purpose;
    private String remark;
}
