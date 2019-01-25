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
 * @Description 详细申请信息controller类
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
     *
     * @param
     * @return
     */
    @PostMapping("")
    public Object create(@RequestBody Map<String, Object> detailsInfo) {
        applyDetailService.create(detailsInfo);
        return ResultBean.success();
    }

    /**
     * 消耗品部分基础信息以及申请信息分页搜索 用于新建申请之后展示所选择的消耗品以及申领情况
     *
     * @param criteria
     * @return
     */
    @GetMapping("")
    public Object findByPage(ConsumableBaseAndApplyDetailInfoViewCriteria criteria) {
        PageBean pageBean = super.getPageBean();
        applyDetailService.findByPage(pageBean, criteria);
        return setJsonPaginationMap(pageBean);
    }

    /**
     * 提交申请接口 将申领状态修改为申请提交 提交申请之后申请便无法修改
     *
     * @param submitInfo
     * @return
     */
    @PutMapping("/submit")
    public Object submit(@RequestBody Map<String, Object> submitInfo) {
        applyDetailService.submit(submitInfo);
        return ResultBean.success();
    }

    /**
     * 审核通过接口 将申领状态修改为审核通过
     *
     * @param reviewInfo
     * @return
     */
    @PutMapping("/review")
    public Object review(@RequestBody Map<String, Object> reviewInfo) {
        applyDetailService.review(reviewInfo);
        return ResultBean.success();
    }

    /**
     * 领料接口 即将实际库存中的消耗品根据之前的记录尽心减去操作 同时申领状态修改为已发料
     *
     * @param applyId
     * @return
     */
    @PutMapping("/permit")
    public Object permit(@RequestParam("applyId") Integer applyId) {
        applyDetailService.permit(applyId);
        return ResultBean.success();
    }

    /**
     * 申请驳回接口 将预申请的数量重新添加到库存中 并将状态改为不发料
     *
     * @param rejectInfo
     * @return
     */
    @PutMapping("/reject")
    public Object reject(@RequestBody Map<String, Object> rejectInfo) {
        applyDetailService.reject(rejectInfo);
        return ResultBean.success();
    }

    /**
     * 个人领用接口 已发料的消耗品经过个人实际领取时的操作 将申领状态修改为已领料
     *
     * @param applyId
     * @return
     */
    @PutMapping("/personCheck")
    public Object personCheck(@RequestParam("applyId") Integer applyId) {
        applyDetailService.personCheck(applyId);
        return ResultBean.success();
    }

    /**
     * 更新详细申请信息 同时修改预申请的数量 将之前预申请的数量删除并重新预申请
     *
     * @param detailInfo
     * @return
     */
    @PutMapping("")
    public Object update(@RequestBody ApplyDetail detailInfo) {
        applyDetailService.update(detailInfo);
        return ResultBean.success();
    }

    /**
     * 删除详细申请信息 同时删除之前预修改的数量
     *
     * @param deleteInfo
     * @return
     */
    @DeleteMapping("")
    public Object delete(@RequestBody Map<String, Object> deleteInfo) {
        return ResultBean.success(applyDetailService.delete(deleteInfo));
    }
}
