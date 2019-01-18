package com.sinoyd.artifact.controller;

import com.sinoyd.artifact.entity.ApplyDetail;
import com.sinoyd.artifact.result.ResultBean;
import com.sinoyd.artifact.service.ApplyDetailService;
import com.sinoyd.frame.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;
import java.util.Collection;

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
     * @param detailCollection 多条申请信息
     * @return
     */
    @PostMapping("")
    public Object create(@RequestBody Collection<ApplyDetail> detailCollection){
        applyDetailService.create(detailCollection);
        return ResultBean.success();
    }
}
