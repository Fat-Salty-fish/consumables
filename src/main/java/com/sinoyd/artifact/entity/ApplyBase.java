package com.sinoyd.artifact.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String applyPerson;         //申请人

    private String permitPerson;        //发料人 enum('薛宇昊','刘伯健','李森','浦小军','孙小峰','程玉明')

    private String checkPerson;         //审核人 enum('薛宇昊','刘伯健','李森','浦小军','孙小峰','程玉明')
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date applyDate;             //申请时间
    @Column(nullable = false)
    private String state;               //enum('申请新建','不发料','申请提交','审核通过','已发料')
    private String comment;             //评审意见
}
