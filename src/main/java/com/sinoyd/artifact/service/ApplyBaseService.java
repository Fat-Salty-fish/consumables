package com.sinoyd.artifact.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinoyd.artifact.entity.ApplyBase;
import com.sinoyd.artifact.repository.ApplyBaseRepository;
import com.sinoyd.frame.base.repository.CommonRepository;
import com.sinoyd.frame.base.util.BaseCriteria;
import com.sinoyd.frame.base.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-15 14:11
 */
@Service
public class ApplyBaseService {
    @Autowired
    private ApplyBaseRepository applyBaseRepository;

    @Autowired
    private CommonRepository commonRepository;

    @Autowired
    private ApplyDetailService applyDetailService;

    /**
     * 保存申请基础信息 无关联其他表
     *
     * @param baseInfo
     */
    public void save(ApplyBase baseInfo) {
        baseInfo.setState("申请新建");
        applyBaseRepository.save(baseInfo);
    }

    /**
     * 分页查找申请基础信息
     *
     * @param pageBean
     * @param applyBaseInfoCriteria
     */
    public void findByPage(PageBean pageBean, BaseCriteria applyBaseInfoCriteria) {
        pageBean.setEntityName("ApplyBase a");
        pageBean.setSelect("Select a");
        commonRepository.findByPage(pageBean, applyBaseInfoCriteria);

//        List<ApplyBase> data = pageBean.getData();
//        List<Map> maps = new ArrayList<>();
//        for (ApplyBase datum : data) {
//            System.out.println(datum.getApplyDate());
//            ObjectMapper om = new ObjectMapper();
//            Map map = null;
//            try {
//                String s = om.writeValueAsString(datum);
//                map = om.readValue(s, Map.class);
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            maps.add(map);
//        }
//        pageBean.setData(maps);
    }

    /**
     * 根据id查找单条基础信息
     *
     * @param id
     * @return
     */
    public ApplyBase findById(Integer id) {
        if (id == null) {
            throw new NullPointerException("输入错误 id不能为空");
        }
        return applyBaseRepository.findOne(id);
    }

    /**
     * 删除申请基础信息 需要删除关联的申请详细信息的内容
     *
     * @param ids
     * @return
     */
    @Transactional
    public Integer delete(Collection<Integer> ids) {
        if (ids == null || ids.size() == 0) {
            throw new NullPointerException("输入错误 传入数组为空");
        }
        for (Integer id : ids) {
            applyDetailService.deleteAll(id);
        }
        return applyBaseRepository.deleteAllByIdIn(ids);
    }

    /**
     * 更新申请基础信息
     *
     * @param baseInfo
     */
    @Transactional
    public void update(ApplyBase baseInfo) {
        if (baseInfo.getId() == null) {
            throw new IllegalArgumentException("输入错误 更新时未输入id值");
        }
        applyBaseRepository.save(baseInfo);
    }
}
