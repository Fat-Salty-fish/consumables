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
    private Integer consumablesId; //绑定的消耗品id 外键
    @Column(nullable = false)
    private Integer insertNum;     //新增个数
    @Column(nullable = false)
    private Integer currentNum;    //现有个数
    @Column(nullable = false)
    private String batchNumber;    //生产编号
    @Column(nullable = false)
    private Date dateInStorage;    //入库时间
    private String manufacturer;   //制造商
    private Date validity;         //有效期截止日
    private String dealer;         //经销商
    @Column(nullable = false)
    private String acceptPerson;//enum('薛宇昊','刘伯健','李森','浦小军','孙小峰','程玉明')
    @Column(nullable = false)
    private Date acceptTime;       //接收日期
    @Column(nullable = false)
    private String acceptConclusion;    //enum('合格','不合格','过期')
    private String appearence;     //外观
    private String verifyProject;   //评审项目
    private String reasonToBuy;     //购买原因
    private String location;        //储存地点
    private String remark;          //备注
}
