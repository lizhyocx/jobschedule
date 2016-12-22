package com.lizhy.service.impl;

import com.lizhy.service.ScheduleService;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

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
}
