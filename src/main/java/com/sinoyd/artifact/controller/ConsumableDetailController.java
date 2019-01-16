package com.sinoyd.artifact.controller;

import com.sinoyd.artifact.entity.ConsumableDetail;
import com.sinoyd.artifact.result.ResultBean;
import com.sinoyd.artifact.service.ConsumableDetailService;
import com.sinoyd.frame.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @Description 消耗品新增详情控制类 只有新增以及根据消耗品id搜索的条件
 * @auther 李忠杰
 * @create 2019-01-15 16:29
 */
@RestController
@RequestMapping("/api/bas/consumable/detail")
public class ConsumableDetailController extends BaseController {
    @Autowired
    private ConsumableDetailService consumableDetailService;

    /**
     * 新增详细信息 即添加某消耗品的数量操作
     * @param detailInfo 详细信息对象
     * @return 返回基本操作信息
     */
    @PostMapping("")
    public Object save(@RequestBody ConsumableDetail detailInfo){
        consumableDetailService.save(detailInfo);
        return ResultBean.success();
    }

    /**
     * 根据消耗品的id来搜索详细信息
     * @param id 消耗品id 注意不是详细信息的id
     * @return 返回详细信息的对象数组
     */
    @GetMapping("/{id}")
    public Object find(@PathVariable("id") Integer id){
        return ResultBean.success(consumableDetailService.find(id));
    }

    @ExceptionHandler
    public Object illegalArgumentExceptionHandler(IllegalArgumentException e){
        return ResultBean.error(1,e.getMessage());
    }
}
