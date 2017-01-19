package com.lizhy.service.impl;

import com.lizhy.model.JobModel;
import com.lizhy.service.ScheduleService;
import org.apache.commons.lang.StringUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Set;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * 定时任务调度管理类
 * Created by lizhiyang on 2016/11/17.
 */
public class ScheduleServiceImpl implements ScheduleService {
    private static Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);
    private Scheduler scheduler;

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public boolean checkFirstRun() {
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            if(jobKeys == null || jobKeys.isEmpty()) {
                return true;
            }
        } catch (SchedulerException e) {
            logger.error("scheduler getJObKeys exception", e);
        }
        return false;
    }

    @Override
    public void start() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            logger.error("scheduler start exception", e);
        }
    }

    @Override
    public void scheduleJob(JobModel jobModel, JobDataMap jobDataMap, Class<? extends Job> clazz) {
        try {
            if(jobModel == null) {
                logger.warn("scheduleJob jobModel is null");
            }
            if("simple".equals(jobModel.getCategory())) {
                //普通定时任务
                scheduleSimple(jobModel, jobDataMap, clazz);
            } else if("cron".equals(jobModel.getCategory())) {
                //cron定时任务
                scheduleCron(jobModel, jobDataMap, clazz);
            } else {
                logger.warn("error job category:"+jobModel.getCategory());
            }
        } catch (Exception e) {
            logger.error("scheduleJob Exception ",e);
        }
    }

    private void scheduleSimple(JobModel jobModel, JobDataMap jobDataMap, Class<? extends Job> clazz) {
        JobBuilder jobB = newJob(clazz).withIdentity(jobModel.getName(), Scheduler.DEFAULT_GROUP);

        if(jobDataMap!=null){
            jobB.usingJobData(jobDataMap);
        }
        JobDetail jobDetail = jobB.build();
        TriggerBuilder<Trigger> triggerB = newTrigger()
                .withIdentity(getTriggerName(jobModel.getName()), Scheduler.DEFAULT_GROUP);
        // 设置开始时间
        Date startTime = jobModel.getStartTime();
        if (null != startTime) {
            triggerB.startAt(startTime);
        }
        // 设置结束时间
        Date endTime = jobModel.getEndTime();
        if (null != endTime) {
            triggerB.endAt(endTime);
        }
        // 设置执行次数
        int repeatCount = jobModel.getRepeatCount();
        // 设置执行时间间隔
        long repeatInterval = jobModel.getRepeatInterval();
        if (repeatCount > 0 || repeatInterval>0) {
            SimpleScheduleBuilder ssb = simpleSchedule();
            if(repeatCount>0){
                ssb.withRepeatCount(repeatCount);
            }
            if(repeatInterval>0){
                ssb.withIntervalInMilliseconds(repeatInterval);
            }
            triggerB.withSchedule(ssb);
        }

        try {
            scheduler.scheduleJob(jobDetail, triggerB.build());
            if(logger.isInfoEnabled()){
                logger.info("加入调度：Name->"+jobDetail.getKey()+ ", Description->"+jobModel.getDesc());
            }
        } catch (SchedulerException e) {
            logger.error("加入任务调度出现异常!", e);
        }
    }

    private void scheduleCron(JobModel jobModel, JobDataMap jobDataMap, Class<? extends Job> clazz) {
        String triggerName = getTriggerName(jobModel.getName());
        try {
            JobBuilder jobB = newJob(clazz)
                    .withIdentity(jobModel.getName(), Scheduler.DEFAULT_GROUP) ;
            if(jobDataMap!=null){
                jobB.usingJobData(jobDataMap);
            }
            JobDetail jobDetail = jobB.build();
            TriggerBuilder<Trigger> triggerB = newTrigger().withIdentity(triggerName, Scheduler.DEFAULT_GROUP);
            triggerB.withSchedule(CronScheduleBuilder.cronSchedule(jobModel.getCronExpression()));
            Trigger trigger = triggerB.build();
            scheduler.scheduleJob(jobDetail,trigger);
            if(logger.isInfoEnabled()){
                logger.info("加入调度任务：Name->"+jobModel.getName()+ ", Description->"+jobModel.getDesc());
            }
        } catch (Exception e) {
            logger.error("加入执行cron触发器出现异常!{}", jobModel.getName(),e);
        }
    }

    private String getTriggerName(String name) {
        return StringUtils.trim(name + "_trigger");
    }

    @Override
    public boolean pauseJob(String jobName) {
        try {
            scheduler.pauseJob(new JobKey(jobName, Scheduler.DEFAULT_GROUP));
        } catch (Exception e) {
            logger.error("pauseJob exception ", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean resumeJob(String jobName) {
        try {
            scheduler.resumeJob(new JobKey(jobName, Scheduler.DEFAULT_GROUP));
        } catch (Exception e) {
            logger.error("resumeJob exception ", e);
            return false;
        }
        return true;
    }
}
