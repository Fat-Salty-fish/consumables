package com.sinoyd.artifact.service;

import com.sinoyd.artifact.entity.ApplyDetail;
import com.sinoyd.artifact.entity.Storage;
import com.sinoyd.artifact.repository.ApplyDetailRepository;
import com.sinoyd.artifact.repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-15 14:11
 */
@Service
public class ApplyDetailService {
    @Autowired
    private ApplyDetailRepository applyDetailRepository;

    @Autowired
    private StorageRepository storageRepository;

    public void create(Collection<ApplyDetail> detailCollection){
        if(detailCollection==null||detailCollection.size()==0){
            throw new NullPointerException("输入错误 新增的数据为空!");
        }
        List<Integer> consumablesId = detailCollection.stream().map(ApplyDetail::getConsumablesId).collect(toList());
        List<Storage> storageList =  storageRepository.findByConsumablesIdIn(consumablesId);

        applyDetailRepository.save(detailCollection);
    }
}
