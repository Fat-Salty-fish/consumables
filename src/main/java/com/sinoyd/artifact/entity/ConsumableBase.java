package com.sinoyd.artifact.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-15 11:41
 */

@Table(name = "t_consumables_base_info")
@Entity
@Getter
@Setter
public class ConsumableBase {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private String name;
    private String code;
    private String size;
    private String unit;
    private String level;       //enum('色谱标准','指定级','GC色谱纯','环保试剂','HPLC')
    @Column(nullable = false)
    private String type;        //enum('高纯物质','生物染色剂','色谱纯试剂','仪器设备','光谱纯试剂')
    @Column(nullable = false)
    private Integer warningNum;
    @Column(nullable = false)
    private String warningPerson;//enum('薛宇昊','刘伯健','李森','浦小军','孙小峰','程玉明')
    private String aliasName;
    private Integer easyToProduceDrug;
    private String savingConditions;
    private String safetyInstruction;
    private String remark;

    @Transient
    private Integer store;
    @Transient
    private Boolean isLessThan;
}
