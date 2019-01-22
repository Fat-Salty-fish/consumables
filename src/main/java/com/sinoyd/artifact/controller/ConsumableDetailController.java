package com.sinoyd.artifact.controller;

import com.sinoyd.artifact.criteria.ConsumableBaseAndDetailInfoViewCriteria;
import com.sinoyd.artifact.criteria.UsingRecordViewCriteria;
import com.sinoyd.artifact.entity.ApplyDetail;
import com.sinoyd.artifact.entity.ConsumableDetail;
import com.sinoyd.artifact.result.ResultBean;
import com.sinoyd.artifact.service.ConsumableDetailService;
import com.sinoyd.frame.base.controller.BaseController;
import com.sinoyd.frame.base.util.PageBean;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
    public Object create(@RequestBody ConsumableDetail detailInfo){
        System.out.println(detailInfo.getValidity());
        consumableDetailService.save(detailInfo);
        return ResultBean.success();
    }

    /**
     * 展示消耗品基础以及详细信息
     * @param criteria 分页搜索的信息 须要有关联的消耗品id
     * @return 返回详细信息的对象数组
     */
    @GetMapping("")
    public Object find(ConsumableBaseAndDetailInfoViewCriteria criteria){
        PageBean pageBean = super.getPageBean();
        consumableDetailService.findByPage(pageBean,criteria);
        return super.setJsonPaginationMap(pageBean);
    }

    /**
     * 展示消耗品的申领使用信息
     * @param criteria 分页搜索的信息 须要有关联的消耗品id
     * @return
     */
    @GetMapping("/usingRecord")
    public Object findUsingRecord(UsingRecordViewCriteria criteria){
        PageBean pageBean = super.getPageBean();
        consumableDetailService.findUsingRecord(pageBean,criteria);
        return super.setJsonPaginationMap(pageBean);
    }

//    @GetMapping("/addingRecord")
//    public Object findAddingRecord()

}
