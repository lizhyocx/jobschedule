package com.lizhy.service;

import com.lizhy.common.service.ServiceTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * NOTE:
 *
 * @author lizhiyang
 * @Date 2017-10-18 14:42
 */
public abstract class AbstractBaseService {
    @Autowired
    protected ServiceTemplate serviceTemplate;
}
