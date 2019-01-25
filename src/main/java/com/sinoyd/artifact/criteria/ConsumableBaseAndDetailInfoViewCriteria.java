package com.sinoyd.artifact.criteria;

import com.sinoyd.frame.base.util.BaseCriteria;
import com.sinoyd.frame.base.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description 用来展示消耗品基础以及详细信息的分页展示以及条件搜索
 * 即消耗品管理界面右边的消耗品详情信息
 * 条件搜索为 消耗品id 以及 消耗品的库存数量情况
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
        if (StringUtils.isNotNullAndEmpty(this.consumablesId)) {
            condition.append("and consumablesId = :consumablesId ");
            values.put("consumablesId", this.consumablesId);
        }
        if (StringUtils.isNotNullAndEmpty(this.situation)) {
            if ("现有库存信息".equals(situation)) {
                condition.append("and currentNum > :currentNum ");
                values.put("currentNum", 0);
            }
        }
        return condition.toString();
    }
}
