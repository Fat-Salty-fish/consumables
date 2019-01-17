package com.sinoyd.artifact.criteria;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sinoyd.frame.base.util.BaseCriteria;
import com.sinoyd.frame.base.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-17 11:03
 */
@Getter
@Setter
public class ApplyBaseInfoCriteria extends BaseCriteria {
    private String applyPerson;
    private String state;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    public ApplyBaseInfoCriteria(){ }

    public ApplyBaseInfoCriteria(String applyPerson,String state,String startDate,String endDate) throws ParseException {
        if(applyPerson!=null){
            this.applyPerson = applyPerson;
        }
        if(state!=null){
            this.state = state;
        }
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd");
        if(startDate!=null){
            this.startDate = simpleDateFormat.parse(startDate);
        }
        if(endDate!=null){
            this.endDate = simpleDateFormat.parse(endDate);
        }
    }

    @Override
    public String getCondition() {
        values.clear();
        StringBuilder condition = new StringBuilder();
        if(StringUtils.isNotNullAndEmpty(this.applyPerson)){
            condition.append("and applyPerson = :applyPerson ");
            values.put("applyPerson",this.applyPerson);
        }
        if(StringUtils.isNotNullAndEmpty(this.state)){
            condition.append("and state = :state ");
            values.put("state",this.state);
        }
        if(StringUtils.isNotNullAndEmpty(this.startDate)){
            condition.append("and applyDate > :startDate ");
            values.put("startDate",this.startDate);
        }
        if(StringUtils.isNotNullAndEmpty(this.endDate)){
            condition.append("and applyDate < :endDate ");
            values.put("endDate",this.endDate);
        }
        return condition.toString();
    }
}
