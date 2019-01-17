package com.sinoyd.artifact.criteria;

import com.sinoyd.frame.base.util.BaseCriteria;
import com.sinoyd.frame.base.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-16 16:31
 */
@Setter
@Getter
public class ConsumableDetailInfoCriteria extends BaseCriteria {
    private Integer consumablesId;

    @Override
    public String getCondition() {
        values.clear();
        StringBuilder condition = new StringBuilder();
        if(StringUtils.isNotNullAndEmpty(consumablesId)){
            condition.append("and consumablesId = :consumablesId");
            values.put("consumablesId",this.consumablesId);
        }
        return condition.toString();
    }
}
