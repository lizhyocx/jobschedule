package com.lizhy.job;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 任务执行
 * Created by lizhiyang on 2017-01-18 13:45.
 */
public class TestJob implements Job {
    private Logger logger = LoggerFactory.getLogger(TestJob.class);
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        JobDataMap map = jobDetail.getJobDataMap();

//        logger.info("TestJob execute....JobDetail={}, JobDataMap={}", jobDetail, map);
        System.out.println("TestJob execute....JobDetail="+jobDetail+", JobDataMap="+map);

    }
}
