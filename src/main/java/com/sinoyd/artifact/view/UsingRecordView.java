package com.sinoyd.artifact.view;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;
/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-21 15:41
 */

@Entity
@Table(name = "v_using_record")
@Setter
@Getter
public class UsingRecordView {
    @Id
    private Integer id;
    private String applyPerson;
    private Date applyDate;
    private Integer consumablesId;
    private Integer applyNum;
    private String remark;
    private Integer remain;
}
