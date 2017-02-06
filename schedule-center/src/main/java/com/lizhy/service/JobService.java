package com.lizhy.service;

import com.lizhy.model.JobInfoModel;

import java.util.List;

/**
 * 定时任务服务类
 * Created by lizhiyang on 2017-02-06 14:41.
 */
public interface JobService {
    /**
     * 获取有效的定时任务
     * @return
     */
    List<JobInfoModel> getEffectiveJob();
}
