package com.sinoyd.artifact.controller;

import com.sinoyd.artifact.entity.ConsumableBase;
import com.sinoyd.artifact.result.ResultBean;
import com.sinoyd.artifact.service.ConsumableBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.sinoyd.artifact.result.ResultBean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-15 14:15
 */
@RestController
@RequestMapping("/api/consumable/baseInfo")
public class ConsumableBaseController {
    @Autowired
    private ConsumableBaseService consumableBaseService;

    @PostMapping("")
    public Object save(@RequestBody ConsumableBase baseInfo){
        consumableBaseService.save(baseInfo);
        return ResultBean.success();
    }

    @GetMapping("/{id}")
    public Object find(@PathVariable("id") Integer id){
        return ResultBean.success(consumableBaseService.find(id));
    }

    @DeleteMapping("")
    public Object delete(@RequestBody Collection<Integer> ids){
        return ResultBean.success(consumableBaseService.delete(ids));
    }

    @PutMapping("")
    public Object update(@RequestBody ConsumableBase baseInfo){
        consumableBaseService.update(baseInfo);
        return ResultBean.success();
    }

    @ExceptionHandler
    public Object nullPointerExceptionHandler(NullPointerException e){
        return ResultBean.error(-99,"发生了空指针错误,请联系管理员进行设置");
    }

    @ExceptionHandler
    public Object illegalArgumentExceptionHandler(IllegalArgumentException e){
        return ResultBean.error(1,"输入错误 更新时未输入消耗品id");
    }

    @ExceptionHandler
    public Object sqlException(SQLException e){
        return ResultBean.error(1,"输入错误 枚举值输入错误");
    }
}
