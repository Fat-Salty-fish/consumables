package com.sinoyd.artifact.controller;

import com.sinoyd.artifact.criteria.ConsumableBaseInfoAndStorageViewCriteria;
import com.sinoyd.artifact.entity.ConsumableBase;
import com.sinoyd.artifact.result.ResultBean;
import com.sinoyd.artifact.service.ConsumableBaseService;
import com.sinoyd.frame.base.controller.BaseController;
import com.sinoyd.frame.base.repository.CommonRepository;
import com.sinoyd.frame.base.util.PageBean;
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
@RequestMapping("/api/bas/consumable/baseInfo")
public class ConsumableBaseController extends BaseController{
    @Autowired
    private ConsumableBaseService consumableBaseService;

    @PostMapping("")
    public Object save(@RequestBody ConsumableBase baseInfo){
        consumableBaseService.save(baseInfo);
        return ResultBean.success();
    }

    @GetMapping("")
    public Object getByPage(ConsumableBaseInfoAndStorageViewCriteria criteria){
        PageBean pageBean = super.getPageBean();
        consumableBaseService.getByPage(pageBean,criteria);
        return super.setJsonPaginationMap(pageBean);
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
        return ResultBean.error(-99,e.getMessage());
    }

    @ExceptionHandler
    public Object illegalArgumentExceptionHandler(IllegalArgumentException e){
        return ResultBean.error(1,e.getMessage());
    }

    @ExceptionHandler
    public Object sqlException(SQLException e){
        return ResultBean.error(1,e.getMessage());
    }
}
