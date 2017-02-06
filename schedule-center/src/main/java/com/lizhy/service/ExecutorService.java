package com.lizhy.service;

import com.lizhy.model.ExecutorModel;

import java.util.List;

/**
 * 执行机器服务类
 * Created by lizhiyang on 2017-02-06 14:39.
 */
public interface ExecutorService {
    /**
     * 根据任务Id获取有效执行机器
     * @param jobId
     * @return
     */
    List<ExecutorModel> getEffectiveExecutor(long jobId);
}
