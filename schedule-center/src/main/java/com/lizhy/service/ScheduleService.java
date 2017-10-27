package com.lizhy.service;

import com.lizhy.model.JobData;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;

/**
 * Created by lizhiyang on 2016/11/17.
 */
public interface ScheduleService {
    /**
     * 校验是否是第一次运行
     * @return
     */
    boolean checkFirstRun();

    /**
     * 启动调度
     */
    void start() throws SchedulerException;

    /**
     * 添加一个定时任务，如果存在则更新
     * @param jobData
     * @param jobDataMap
     * @param clazz
     */
    boolean scheduleJob(JobData jobData, JobDataMap jobDataMap, Class<? extends Job> clazz);

    /**
     * 暂停定时任务
     * @param jobName
     * @return
     */
    boolean pauseJob(String jobName);

    /**
     * 恢复定时任务
     * @param jobName
     * @return
     */
    boolean resumeJob(String jobName);

}
