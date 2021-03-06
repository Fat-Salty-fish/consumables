package com.sinoyd.artifact.criteria;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sinoyd.frame.base.util.BaseCriteria;
import com.sinoyd.frame.base.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "GMT+8")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "GMT+8")
    private Date endDate;

    public ApplyBaseInfoCriteria() {
    }

    public ApplyBaseInfoCriteria(String applyPerson, String state, String startDate, String endDate) throws ParseException {
        if (applyPerson != null) {
            this.applyPerson = applyPerson;
        }
        if (state != null) {
            this.state = state;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isNotNullAndEmpty(startDate)) {
            this.startDate = simpleDateFormat.parse(startDate);
        }
        if (StringUtils.isNotNullAndEmpty(endDate)) {
            this.endDate = simpleDateFormat.parse(endDate);
        }
    }

    @Override
    public String getCondition() {
        values.clear();
        StringBuilder condition = new StringBuilder();
        if (StringUtils.isNotNullAndEmpty(this.applyPerson)) {
            condition.append("and applyPerson = :applyPerson ");
            values.put("applyPerson", this.applyPerson);
        }
        if (StringUtils.isNotNullAndEmpty(this.state)) {
            if ("全部".equals(this.state)) {
                List<String> states = new ArrayList<>();
                states.add("已发料");
                states.add("已领料");
                states.add("不发料");
                condition.append("and state in :states ");
                values.put("states", states);
            } else {
                condition.append("and state = :state ");
                values.put("state", this.state);
            }

        }
        if (StringUtils.isNotNullAndEmpty(this.startDate)) {
            System.out.println(this.startDate);
            condition.append("and applyDate >= :startDate ");
            values.put("startDate", this.startDate);
        }
        if (StringUtils.isNotNullAndEmpty(this.endDate)) {
            condition.append("and applyDate <= :endDate ");
            values.put("endDate", this.endDate);
        }
        return condition.toString();
    }
}
