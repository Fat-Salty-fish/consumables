package com.sinoyd.artifact.criteria;

import com.sinoyd.frame.base.util.BaseCriteria;
import com.sinoyd.frame.base.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-18 10:14
 */
@Getter
@Setter
public class ConsumableBaseAndDetailInfoViewCriteria extends BaseCriteria {
    private Integer consumablesId;
    private String situation;
    @Override
    public String getCondition() {
        values.clear();
        StringBuilder condition = new StringBuilder();
        if(StringUtils.isNotNullAndEmpty(this.consumablesId)){
            condition.append("and consumablesId = :consumablesId ");
            values.put("consumablesId",this.consumablesId);
        }
        if(StringUtils.isNotNullAndEmpty(this.situation)){
            if ("现有库存信息".equals(situation)){
                condition.append("and currentNum > :currentNum ");
                values.put("currentNum",0);
            }
        }
        return condition.toString();
    }
}
