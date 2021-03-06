package com.sinoyd.artifact.criteria;

import com.sinoyd.frame.base.util.BaseCriteria;
import com.sinoyd.frame.base.util.StringUtils;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 用来展示在消耗品管理界面右方的消耗品领用信息的数据分页搜索类
 * @auther 李忠杰
 * @create 2019-01-21 16:11
 */
@Setter
@Getter
public class UsingRecordViewCriteria extends BaseCriteria {
    private Integer consumablesId;

    @Override
    public String getCondition() {
        values.clear();
        StringBuilder condition = new StringBuilder();
        if (StringUtils.isNotNullAndEmpty(this.consumablesId)) {
            condition.append(" and consumablesId = :consumablesId");
            values.put("consumablesId", this.consumablesId);
        }
        List<String> states = new ArrayList<>();
        states.add("已发料");
        states.add("已领料");
        condition.append(" and state in :state");
        values.put("state", states);
        return condition.toString();
    }
}
