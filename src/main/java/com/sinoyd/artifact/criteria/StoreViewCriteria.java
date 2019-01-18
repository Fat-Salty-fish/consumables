package com.sinoyd.artifact.criteria;

import com.sinoyd.frame.base.controller.BaseController;
import com.sinoyd.frame.base.util.BaseCriteria;
import com.sinoyd.frame.base.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-18 16:36
 */
@Getter
@Setter
public class StoreViewCriteria extends BaseCriteria {
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
