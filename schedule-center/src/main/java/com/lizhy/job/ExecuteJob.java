package com.lizhy.job;

import org.quartz.*;

/**
 * 任务执行
 * Created by lizhiyang on 2017-01-18 13:45.
 */
public class ExecuteJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        JobDataMap map = jobDetail.getJobDataMap();

        long jobId = (Long)map.get("jobId");
        String jobName = map.getString("jobName");
        String exeInterface = map.getString("exeInterface");
        int jobMode = (Integer)map.get("jobMode");
        int jobOrder = (Integer)map.get("jobOrder");
    }
}
