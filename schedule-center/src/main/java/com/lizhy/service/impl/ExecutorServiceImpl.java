package com.lizhy.service.impl;

import com.lizhy.model.ExecutorModel;
import com.lizhy.service.ExecutorService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lizhiyang on 2017-02-06 14:42.
 */
@Service
public class ExecutorServiceImpl implements ExecutorService {
    @Override
    public List<ExecutorModel> getEffectiveExecutor(long jobId) {
        return null;
    }
}
