package com.sinoyd.artifact.controller;

import com.sinoyd.artifact.criteria.StoreViewCriteria;
import com.sinoyd.artifact.entity.ConsumableBase;
import com.sinoyd.artifact.result.ResultBean;
import com.sinoyd.artifact.service.ConsumableBaseService;
import com.sinoyd.frame.base.controller.BaseController;
import com.sinoyd.frame.base.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

/**
 * @Description 消耗品基础信息controller类
 * @auther 李忠杰
 * @create 2019-01-15 14:15
 */
@RestController
@RequestMapping("/api/bas/consumable/baseInfo")
public class ConsumableBaseController extends BaseController {
    @Autowired
    private ConsumableBaseService consumableBaseService;

    /**
     * 新增消耗品基础信息
     *
     * @param baseInfo 消耗品信息
     * @return 返回插入成功 不返回消耗品信息数据 为插入成功
     */
    @PostMapping("")
    public Object create(@RequestBody ConsumableBase baseInfo) {
        consumableBaseService.save(baseInfo);
        return ResultBean.success();
    }

    /**
     * 消耗品基础信息分页搜索 使用视图展示 包括现有库存信息等
     *
     * @param criteria 查询信息
     * @return 返回查询结果
     */
    @GetMapping("")
    public Object findByPage(StoreViewCriteria criteria) {
        PageBean pageBean = super.getPageBean();
        consumableBaseService.findByPage(pageBean, criteria);
        return super.setJsonPaginationMap(pageBean);
    }

    /**
     * 根据id搜索单条消耗品基础信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Object findById(@PathVariable("id") Integer id) {
        return ResultBean.success(consumableBaseService.findById(id));
    }

    /**
     * 删除消耗品基础信息
     *
     * @param ids 要删除的消耗品基础信息
     * @return 返回删除的条数
     */
    @DeleteMapping("")
    public Object delete(@RequestBody Collection<Integer> ids) {
        return ResultBean.success(consumableBaseService.delete(ids));
    }

    /**
     * 更新基础信息
     *
     * @param baseInfo 基础信息 id不能为空
     * @return 不返回数据即认为修改成功
     */
    @PutMapping("")
    public Object update(@RequestBody ConsumableBase baseInfo) {
        consumableBaseService.update(baseInfo);
        return ResultBean.success();
    }

}
