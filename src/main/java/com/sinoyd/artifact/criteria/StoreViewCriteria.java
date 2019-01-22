package com.sinoyd.artifact.criteria;

import com.sinoyd.frame.base.controller.BaseController;
import com.sinoyd.frame.base.util.BaseCriteria;
import com.sinoyd.frame.base.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description 用来展示消耗品基础信息以及当前库存信息的分页展示与模糊检索
 *              即在消耗品管理界面的左边的基础信息
 *              模糊检索条件为 消耗品名称
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
