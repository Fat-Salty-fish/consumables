package com.sinoyd.artifact.service;

import com.sinoyd.artifact.entity.ConsumableBase;
import com.sinoyd.artifact.repository.ConsumableBaseRepository;
import com.sinoyd.artifact.view.StoreView;
import com.sinoyd.frame.base.repository.CommonRepository;
import com.sinoyd.frame.base.util.BaseCriteria;
import com.sinoyd.frame.base.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;


/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-15 14:12
 */
@Service
public class ConsumableBaseService {
    @Autowired
    private ConsumableBaseRepository consumableBaseRepository;

    @Autowired
    private CommonRepository commonRepository;

    /**
     * @param baseInfo 要新增的基础信息
     */
    public void save(ConsumableBase baseInfo) {
        if(baseInfo.getWarningNum() >= 0){
            consumableBaseRepository.save(baseInfo);
        }
        else {
            throw new IllegalArgumentException("输入错误 输入小于0的警告数量");
        }
    }

    /**
     * 根据id获取一条消耗品基础信息
     * @param id
     * @return
     */
    public ConsumableBase findById(Integer id){
        if (id == null) {
            throw new IllegalArgumentException("输入错误 输入的消耗品id为空");
        }
        return consumableBaseRepository.findOne(id);
    }

    /**
     * 分页搜索与模糊检索 搜索相关基础信息以及库存信息 同时库存量低于报警值时进行报警
     *
     * @param pageBean
     * @param storeViewCriteria
     */
    public void findByPage(PageBean pageBean, BaseCriteria storeViewCriteria) {
        pageBean.setEntityName("StoreView a");
        pageBean.setSelect("Select a");
        commonRepository.findByPage(pageBean,storeViewCriteria);
        List<StoreView> viewList = pageBean.getData();
        viewList.stream().forEach(item->item.setIsLessThan());
    }

    /**
     * 根据id删除消耗品基础信息
     *
     * @param ids 要删除的基础信息id
     * @return
     */
    @Transactional
    public Integer delete(Collection<Integer> ids) {
        if (ids == null || ids.size() == 0) {
            throw new NullPointerException("输入错误 传入数组为空");
        }
        return consumableBaseRepository.deleteAllByIdIn(ids);
    }

    /**
     * 更新相关的消耗品id信息
     *
     * @param baseInfo
     */
    @Transactional
    public void update(ConsumableBase baseInfo) {
        if (baseInfo.getId() == null) {
            throw new IllegalArgumentException("输入错误 更新时未输入id值");
        }
        consumableBaseRepository.save(baseInfo);
    }
}
