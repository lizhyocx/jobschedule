package com.lizhy;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lizhiyang on 2016/10/10.
 */
@DisallowConcurrentExecution
public class MyJob1 implements Job , Serializable{

    private static final long serialVersionUID = -4816708186668603950L;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("MyJob***1 execute==================="+format.format(new Date()));
    }
}
