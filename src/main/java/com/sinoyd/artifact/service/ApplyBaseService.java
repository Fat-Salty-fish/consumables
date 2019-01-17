package com.sinoyd.artifact.service;

import com.sinoyd.artifact.entity.ApplyBase;
import com.sinoyd.artifact.repository.ApplyBaseRepository;
import com.sinoyd.frame.base.repository.CommonRepository;
import com.sinoyd.frame.base.util.BaseCriteria;
import com.sinoyd.frame.base.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

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

    /**
     * 保存申请基础信息 无关联其他表
     * @param baseInfo
     */
    public void save(ApplyBase baseInfo){
        applyBaseRepository.save(baseInfo);
    }

    /**
     * 分页查找申请基础信息
     * @param pageBean
     * @param applyBaseInfoCriteria
     */
    public void findByPage(PageBean pageBean, BaseCriteria applyBaseInfoCriteria){
        pageBean.setEntityName("ApplyBase a");
        pageBean.setSelect("Select a");
        commonRepository.findByPage(pageBean,applyBaseInfoCriteria);
    }

    /**
     * 根据id查找单条基础信息
     * @param id
     * @return
     */
    public ApplyBase findById(Integer id){
        if(id==null){
            throw new NullPointerException("输入错误 id不能为空");
        }
        return applyBaseRepository.findOne(id);
    }

    /**
     * 删除申请基础信息 需要删除关联的申请详细信息的内容
     * @param ids
     * @return
     */
    @Transactional
    public Integer delete(Collection<Integer> ids){
        return null;
    }

    /**
     * 更新申请基础信息
     * @param baseInfo
     */
    @Transactional
    public void update(ApplyBase baseInfo){

    }


}
