package com.sinoyd.artifact.criteria;

import com.sinoyd.frame.base.util.BaseCriteria;
import com.sinoyd.frame.base.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-16 14:29
 */
@Setter
@Getter
public class ConsumableBaseInfoAndStorageViewCriteria extends BaseCriteria {
    private String name;

    @Override
    public String getCondition() {
        values.clear();
        StringBuilder condition = new StringBuilder();
        if(StringUtils.isNotNullAndEmpty(this.name)){
            condition.append("and name like :name");
            values.put("name", "%" + this.name + "%");
        }
        return condition.toString();
    }

    public String getName(){
        return this.name;
    }
}
