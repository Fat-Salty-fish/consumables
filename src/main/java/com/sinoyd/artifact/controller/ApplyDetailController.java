package com.sinoyd.artifact.controller;

import com.sinoyd.artifact.criteria.ConsumableBaseAndApplyDetailInfoViewCriteria;
import com.sinoyd.artifact.entity.ApplyDetail;
import com.sinoyd.artifact.result.ResultBean;
import com.sinoyd.artifact.service.ApplyDetailService;
import com.sinoyd.frame.base.controller.BaseController;
import com.sinoyd.frame.base.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-18 11:01
 */
@RestController
@RequestMapping("/api/bas/apply/detail")
public class ApplyDetailController extends BaseController {
    @Autowired
    private ApplyDetailService applyDetailService;

    /**
     * 新增详细申请信息 即基础申请信息下的多个详细信息
     * @param
     * @return
     */
    @PostMapping("")
    public Object create(@RequestBody Map<String,Object> detailsInfo){
        applyDetailService.create(detailsInfo);
        return ResultBean.success();
    }

    @GetMapping("")
    public Object findByPage(ConsumableBaseAndApplyDetailInfoViewCriteria criteria){
        PageBean pageBean = super.getPageBean();
        applyDetailService.findByPage(pageBean,criteria);
        return setJsonPaginationMap(pageBean);
    }

    @PutMapping("/submit")
    public Object submit(@RequestBody Map<String,Object> submitInfo){
        applyDetailService.submit(submitInfo);
        return ResultBean.success();
    }

    @PutMapping("/review")
    public Object review(@RequestBody Map<String,Object> reviewInfo){
        applyDetailService.review(reviewInfo);
        return ResultBean.success();
    }

    @PutMapping("/permit")
    public Object permit(@RequestBody Map<String,Object> permitInfo){
        applyDetailService.permit(permitInfo);
        return ResultBean.success();
    }

    @PutMapping("/reject")
    public Object reject(@RequestBody Map<String,Object> rejectInfo){
        applyDetailService.reject(rejectInfo);
        return ResultBean.success();
    }

    @PutMapping("")
    public Object update(@RequestBody ApplyDetail detailInfo){
        applyDetailService.update(detailInfo);
        return ResultBean.success();
    }

    @DeleteMapping("")
    public Object delete
}
