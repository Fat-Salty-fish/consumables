package com.sinoyd.artifact.entity;

import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-15 13:34
 */
@Entity
@Table(name = "t_consumables_detail_info")
@Setter
@Getter
public class ConsumableDetail {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private Integer consumablesId;
    @Column(nullable = false)
    private Integer insertNum;
    @Column(nullable = false)
    private Integer currentNum;
    @Column(nullable = false)
    private String batchNumber;
    @Column(nullable = false)
    private Date dateInStorage;
    private String manufacturer;
    private Date validity;
    private String dealer;
    @Column(nullable = false)
    private String acceptPerson;
    @Column(nullable = false)
    private Date acceptTime;
    @Column(nullable = false)
    private String acceptConclusion;
    private String appearence;
    private String verifyProject;
    private String reasonToBuy;
    private String location;
    private String remark;
}
