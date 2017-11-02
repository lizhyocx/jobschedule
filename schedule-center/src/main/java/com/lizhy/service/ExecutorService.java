package com.lizhy.service;

import com.lizhy.common.CallResult;
import com.lizhy.model.ExecutorModel;

import java.util.List;

/**
 * 执行机器服务类
 * Created by lizhiyang on 2017-02-06 14:39.
 */
public interface ExecutorService {

    /**
     * @Description: 获取任务下的所有执行机器列表
     * @author lizhiyang
     * @Date 2017-10-20 14:12:13
     */
    CallResult<List<ExecutorModel>> getExecutorList(Long jobId);

    /**
     * @Description: 保存执行机器
     * @author lizhiyang
     * @Date 2017-10-20 14:22:51
     */
    CallResult<Boolean> saveExecutor(ExecutorModel model);
}
