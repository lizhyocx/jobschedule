package com.lizhy.service;

import com.lizhy.model.JobModel;
import org.quartz.Job;
import org.quartz.JobDataMap;

/**
 * Created by lizhiyang on 2016/11/17.
 */
public interface ScheduleService {
    /**
     * 校验是否是第一次运行
     * @return
     */
    boolean checkFirstRun();

    void start();

    void scheduleJob(JobModel jobModel, JobDataMap jobDataMap, Class<? extends Job> clazz);

    boolean pauseJob(String jobName);

    boolean resumeJob(String jobName);

}
