package com.lizhy.service;

import com.lizhy.common.CallResult;
import com.lizhy.model.JobInfoModel;

import java.util.List;

/**
 * @Description: 定时任务服务类
 * @author lizhiyang
 * @Date 2017-10-18 14:21:57
 */
public interface JobService {

    /**
     * @Description: 保存定时任务
     * @author lizhiyang
     * @Date 2017-10-18 14:39:29
     */
    CallResult<Boolean> saveJob(JobInfoModel model);
    /**
     * 获取有效的定时任务
     * @return
     */
    List<JobInfoModel> getEffectiveJob();
}
