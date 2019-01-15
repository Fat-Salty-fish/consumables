package com.sinoyd.artifact.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-15 13:58
 */
@Entity
@Table(name = "t_using_record")
@Setter
@Getter
public class UsingRecord {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private Integer consumablesId;
    @Column(nullable = false)
    private Integer detailsId;
    @Column(nullable = false)
    private Date dateToUsing;
    @Column(nullable = false)
    private Integer usingNum;
}
