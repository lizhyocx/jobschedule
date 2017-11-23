package com.lizhy.service;

import com.lizhy.auto.model.ScheduleJobDO;
import com.lizhy.auto.model.ScheduleTimerDO;
import com.lizhy.common.service.ServiceTemplate;
import com.lizhy.enu.JobCategoryEnum;
import com.lizhy.job.ExecuteJob;
import com.lizhy.model.JobData;
import org.quartz.JobDataMap;
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
    @Autowired
    protected ScheduleService scheduleService;

    /**
     * @Description: 加载任务
     * @author lizhiyang
     * @Date 2017-10-27 15:13:08
     */
    protected boolean loadJob(ScheduleJobDO jobDO, ScheduleTimerDO timerDO) {
        JobData jobData = new JobData();
        jobData.setCategory(JobCategoryEnum.CRON.getType());
        jobData.setName(jobDO.getJobName());
        jobData.setDesc(jobDO.getJobDesc());
        jobData.setCronExpression(timerDO.getCron());
        JobDataMap dataMap = new JobDataMap();
        dataMap.put("executeRule", jobDO.getExecuteRule());
        dataMap.put("executeSelect", jobDO.getExecuteSelect());
        dataMap.put("jobId", jobDO.getJobId());
        dataMap.put("jobName", jobDO.getJobName());
        return scheduleService.scheduleJob(jobData, dataMap, ExecuteJob.class);
    }
}
