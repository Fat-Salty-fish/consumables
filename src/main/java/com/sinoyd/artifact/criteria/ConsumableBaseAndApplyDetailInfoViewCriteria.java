package com.sinoyd.artifact.criteria;

import com.sinoyd.frame.base.util.BaseCriteria;
import com.sinoyd.frame.base.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-21 17:24
 */
@Getter
@Setter
public class ConsumableBaseAndApplyDetailInfoViewCriteria extends BaseCriteria {
    private Integer applyId;

    @Override
    public String getCondition() {
        values.clear();
        StringBuilder condition = new StringBuilder();
        if (this.applyId != null) {
            condition.append(" and applyId = :applyId ");
            values.put("applyId", this.applyId);
        }
        return condition.toString();
    }
}
