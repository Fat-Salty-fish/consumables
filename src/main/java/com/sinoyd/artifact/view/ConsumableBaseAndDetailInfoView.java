package com.sinoyd.artifact.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-18 10:10
 */
@Entity
@Table(name = "v_consumable_base_and_detail_info")
@Setter
@Getter
public class ConsumableBaseAndDetailInfoView {
    @Id
    private Integer id;
    private Integer consumablesId;
    private String unit;
    private Integer currentNum;
    private String manufacturer;
    private String batchNumber;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date validity;
}
