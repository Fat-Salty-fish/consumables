package com.sinoyd.artifact.service;

import com.sinoyd.artifact.repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-15 14:13
 */
@Service
public class StorageService {
    @Autowired
    private StorageRepository storageRepository;
}
