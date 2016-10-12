package com.lizhy;

import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by lizhiyang on 2016/10/10.
 */
public class SchedulerJob {
    private Logger logger = LoggerFactory.getLogger(SchedulerJob.class);
    private Scheduler scheduler;

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void loadJob() {
        System.out.println("SchedulerJob loadJob begin========================");
        try {
            //获取数据库中的Job
            GroupMatcher<JobKey> jobMatcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(jobMatcher);
            if(jobKeys != null && !jobKeys.isEmpty()) {
                for(JobKey jobKey : jobKeys) {
                    System.out.println("existJobKeys:"+jobKey.getGroup()+"."+jobKey.getName());
                }
            }
            JobKey jobKey1 = JobKey.jobKey("MyJob1");
            if(!scheduler.checkExists(jobKey1)) {
                System.out.println("new MyJob1===");
                JobBuilder jobB1 = newJob(MyJob1.class)
                    .withIdentity("MyJob1", Scheduler.DEFAULT_GROUP) ;
                JobDetail jobDetail1 = jobB1.build();

                TriggerBuilder<Trigger> triggerB1 = newTrigger().withIdentity("MyJob1_trigger", Scheduler.DEFAULT_GROUP);
                triggerB1.withSchedule(cronSchedule("0/2 * * * * ?"));
                Trigger trigger1 = triggerB1.build();
                scheduler.scheduleJob(jobDetail1, trigger1);
            }
            JobKey jobKey2 = JobKey.jobKey("MyJob2");
            if(!scheduler.checkExists(jobKey2)) {
                System.out.println("new MyJob2===");
                JobBuilder jobB2 = newJob(MyJob2.class)
                    .withIdentity("MyJob2", Scheduler.DEFAULT_GROUP) ;
                JobDetail jobDetail2 = jobB2.build();
                TriggerBuilder<Trigger> triggerB2 = newTrigger().withIdentity("MyJob2_trigger", Scheduler.DEFAULT_GROUP);
                triggerB2.withSchedule(cronSchedule("0/5 * * * * ?"));
                Trigger trigger2 = triggerB2.build();
                scheduler.scheduleJob(jobDetail2, trigger2);
            }
            //获取正在运行中的Job
//            List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();

//            //获取数据库中的Job
//            GroupMatcher<JobKey> jobMatcher = GroupMatcher.anyJobGroup();
//            Set<JobKey> jobKeys = scheduler.getJobKeys(jobMatcher);
//            List<JobDetail> jobDetails = new ArrayList<JobDetail>();
//
//            for (JobKey key : jobKeys) {
//                jobDetails.add(scheduler.getJobDetail(key));
//            }
//
//            //获取数据库中的Trigger
//            GroupMatcher<TriggerKey> TgrMatcher = GroupMatcher.anyTriggerGroup();
//            Set<TriggerKey> Keys = scheduler.getTriggerKeys(TgrMatcher);
//            List<Trigger> triggers = new ArrayList<Trigger>();
//
//            for (TriggerKey key : Keys) {
//                triggers.add(scheduler.getTrigger(key));
//            }
            //自动获取数据库中触发器和job的信息  然后执行
            scheduler.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("SchedulerJob loadJob end========================");
    }
}
