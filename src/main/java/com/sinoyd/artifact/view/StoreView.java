package com.sinoyd.artifact.view;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-18 16:25
 */
@Entity
@Table(name = "v_storage")
@Getter
@Setter
public class StoreView {
    @Id
    private Integer id;
    private String name;
    private String code;
    private String size;
    private String unit;
    private String level;
    private String type;
    private Integer warningNum;
    private String warningPerson;
    private String alias_name;
    private Integer easyToProduceDrug;
    private String savingConditions;
    private String safetyInstruction;
    private String remark;
    private Integer store;

    @Transient
    private Integer isLessThan;
}
