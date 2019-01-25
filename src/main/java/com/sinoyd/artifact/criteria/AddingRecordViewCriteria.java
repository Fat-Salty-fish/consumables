package com.sinoyd.artifact.criteria;

import com.sinoyd.frame.base.util.BaseCriteria;
import com.sinoyd.frame.base.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-22 15:23
 */
@Setter
@Getter
public class AddingRecordViewCriteria extends BaseCriteria {
    private Integer consumablesId;

    @Override
    public String getCondition() {
        values.clear();
        StringBuilder condition = new StringBuilder();
        if (consumablesId != null) {
            condition.append(" and consumablesId = :consumablesId ");
            values.put("consumablesId", this.consumablesId);
        }
        return condition.toString();
    }
}
