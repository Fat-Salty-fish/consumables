package com.sinoyd.artifact.view;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-16 15:00
 */
@Entity
@Table(name = "v_consumable_base_info_and_store")
@Setter
@Getter
public class ConsumableBaseInfoAndStorageView{
    @Id
    private Integer id;
    private String name;
    private String code;
    private String size;
    private String unit;
    private String level;       //enum('色谱标准','指定级','GC色谱纯','环保试剂','HPLC')
    private String type;        //enum('高纯物质','生物染色剂','色谱纯试剂','仪器设备','光谱纯试剂')
    private Integer warningNum;
    private String warningPerson;//enum('薛宇昊','刘伯健','李森','浦小军','孙小峰','程玉明')
    private String aliasName;
    private Integer easyToProduceDrug;
    private String savingConditions;
    private String safetyInstruction;
    private String remark;

    private Integer store;
    private Integer isLessThan;
}
