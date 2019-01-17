package com.sinoyd.artifact.controller;

import com.sinoyd.artifact.criteria.ApplyBaseInfoCriteria;
import com.sinoyd.artifact.entity.ApplyBase;
import com.sinoyd.artifact.result.ResultBean;
import com.sinoyd.artifact.service.ApplyBaseService;
import com.sinoyd.frame.base.controller.BaseController;
import com.sinoyd.frame.base.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-17 10:25
 */
@RestController
@RequestMapping("/api/bas/apply/baseInfo")
public class ApplyBaseController extends BaseController {
    @Autowired
    private ApplyBaseService applyBaseService;

    /**
     * 新建一条基础申请信息
     * @param baseInfo
     * @return
     */
    @PostMapping("")
    public Object create(@RequestBody ApplyBase baseInfo){
        applyBaseService.save(baseInfo);
        return ResultBean.success();
    }

    /**
     * 分页搜索基础申请信息
     * @param applyPerson  申请人 可选填
     * @param state        申请状态 可选填
     * @param startDate    查询开始时间 可选填
     * @param endDate      查询结束时间 可选填
     * @return
     * @throws ParseException
     */
    @GetMapping("")
    public Object findByPage(@RequestParam(name = "applyPerson",required = false)String applyPerson,
                             @RequestParam(name = "state",required = false)String state,
                             @RequestParam(name = "startDate",required = false) String startDate,
                             @RequestParam(name = "endDate",required = false)String endDate) throws ParseException {
        ApplyBaseInfoCriteria criteria = new ApplyBaseInfoCriteria(applyPerson,state,startDate,endDate);
        PageBean pageBean = super.getPageBean();
        applyBaseService.findByPage(pageBean,criteria);
        return setJsonPaginationMap(pageBean);
    }

    /**
     * 根据id搜索一条基础申请信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Object findById(@PathVariable("id")Integer id){
        return ResultBean.success(applyBaseService.findById(id));
    }
}
