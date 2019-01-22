package com.sinoyd.artifact.view;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-21 17:19
 */
@Entity
@Table(name = "v_consumable_base_and_apply_detail_info")
@Getter
@Setter
public class ConsumableBaseAndApplyDetailInfoView {
    @Id
    private Integer id;
    private String name;
    private String code;
    private String unit;
    private String level;
    private String size;
    private String type;
    private Integer applyId;
    private Integer applyNum;
    private Integer consumablesId;
}
