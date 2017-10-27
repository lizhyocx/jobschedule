package com.lizhy.test;

import com.lizhy.enu.JobCategoryEnum;
import com.lizhy.job.TestJob;
import com.lizhy.model.JobData;
import com.lizhy.service.ScheduleService;
import org.junit.Test;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.utils.Key;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * NOTE:
 *
 * @author lizhiyang
 * @Date 2017-10-27 14:13
 */
public class QuartzTest extends BaseSpringTest {
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private Scheduler scheduler;
    @Test
    public void test1() {
        for(int i=0;i<2;i++) {
            String cron = "0/3 * * * * ? *";
            String jobName = "ONE-"+i;
            doJob(jobName, cron);
        }
        doPrintJob();
    }

    @Test
    public void test2() {
        doPrintJob();
    }

    @Test
    public void test3() {
        JobKey jobKey = new JobKey("ONE-0", Key.DEFAULT_GROUP);
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void clear() {
        try {
            scheduler.clear();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private void doJob(String jobName, String cronExpression) {
        JobData jobData = new JobData();
        jobData.setName(jobName);
        jobData.setCronExpression(cronExpression);
        jobData.setCategory(JobCategoryEnum.CRON.getType());

        scheduleService.scheduleJob(jobData, null, TestJob.class);
    }

    private void doPrintJob() {
        try {
            while(true) {
                for (String groupName : scheduler.getJobGroupNames()) {

                    for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {

                        String jobName = jobKey.getName();
                        String jobGroup = jobKey.getGroup();

                        //get job's trigger
                        List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
                        Date nextFireTime = triggers.get(0).getNextFireTime();
                        System.out.println(scheduler.getSchedulerInstanceId()+ "-- [jobName] : " + jobName + " [groupName] : "
                                + jobGroup + " - " + nextFireTime);
                    }
                }

                Thread.sleep(10*1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void get() {
        while(true) {
            try {
                GroupMatcher<JobKey> matcher = GroupMatcher.anyGroup();
                Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
                System.out.println(jobKeys);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
