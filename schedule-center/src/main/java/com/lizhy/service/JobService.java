package com.lizhy.service;

import com.lizhy.common.CallResult;
import com.lizhy.model.JobInfoModel;
import com.lizhy.model.PageData;

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
     * @Description: 查询定时任务列表
     * @author lizhiyang
     * @Date 2017-10-19 11:17:16
     */
    CallResult<PageData<JobInfoModel>> getJobList(JobInfoModel queryModel, Integer pageNo, Integer pageSize);

    /**
     * @Description: 获取任务详情
     * @author lizhiyang
     * @Date 2017-10-19 15:03:38
     */
    CallResult<JobInfoModel> getJobById(Long jobId);
    /**
     * 获取有效的定时任务
     * @return
     */
    List<JobInfoModel> getEffectiveJob();
}
