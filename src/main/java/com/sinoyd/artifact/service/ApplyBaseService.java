package com.sinoyd.artifact.service;

import com.sinoyd.artifact.repository.ApplyBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-01-15 14:11
 */
@Service
public class ApplyBaseService {
    @Autowired
    private ApplyBaseRepository applyBaseRepository;
}
