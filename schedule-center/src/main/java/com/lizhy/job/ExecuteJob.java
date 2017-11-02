package com.lizhy.job;

import com.alibaba.fastjson.JSON;
import com.lizhy.auto.dao.ScheduleExecutorDAO;
import com.lizhy.auto.dao.ScheduleJobLogDAO;
import com.lizhy.auto.model.ScheduleExecutorDO;
import com.lizhy.auto.model.ScheduleJobLogDO;
import com.lizhy.client.util.Result;
import com.lizhy.common.enu.ExecuteSelectEnum;
import com.lizhy.common.enu.NotifyStatusEnum;
import com.lizhy.common.enu.ReturnStatusEnum;
import com.lizhy.common.http.HttpClientManager;
import com.lizhy.common.http.HttpResult;
import com.lizhy.common.util.SpringContextUtil;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 任务执行
 * Created by lizhiyang on 2017-01-18 13:45.
 */
@DisallowConcurrentExecution
public class ExecuteJob implements Job {
    private Logger logger = LoggerFactory.getLogger(ExecuteJob.class);
    private ScheduleExecutorDAO scheduleExecutorDAO = (ScheduleExecutorDAO) SpringContextUtil.getBean("scheduleExecutorDAO");
    private ScheduleJobLogDAO scheduleJobLogDAO = (ScheduleJobLogDAO) SpringContextUtil.getBean("scheduleJobLogDAO");

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        JobDataMap map = jobDetail.getJobDataMap();

        logger.info("ExecuteJob execute....JobDetail={}, JobDataMap={}", jobDetail, map);

        long jobId = (Long)map.get("jobId");
        String jobName = map.getString("jobName");
        Integer executeSelect = (Integer)map.get("executeSelect");
        Integer executeRules = (Integer)map.get("executeRule");

        List<ScheduleExecutorDO> executorList = scheduleExecutorDAO.selectEffective(jobId);
        if(CollectionUtils.isEmpty(executorList)) {
            //没有执行机器
            logger.warn("JObId[{}]-JobName[{}] has no executor", jobId, jobName);
            return;
        }
        if(executeSelect == ExecuteSelectEnum.ANY.getCode()) {
            //任选其一
            ScheduleExecutorDO executorDO = executorList.get(new Random().nextInt(executorList.size()));
            doExecute(jobId, executorDO.getExeId(), executorDO.getExeInterface(), executorDO.getExeUrl());
        } else if(executeSelect == ExecuteSelectEnum.ALL.getCode()) {
            //全部执行
            for(ScheduleExecutorDO executorDO : executorList) {
                doExecute(jobId, executorDO.getExeId(), executorDO.getExeInterface(), executorDO.getExeUrl());
            }
        }
    }

    private boolean doExecute(long jobId, long executorId, String exeInterface, String executeUrl) {
        ScheduleJobLogDO jobLogDO = new ScheduleJobLogDO();
        jobLogDO.setJobId(jobId);
        jobLogDO.setExecutorId(executorId);
        jobLogDO.setExeUrl(executeUrl);
        jobLogDO.setExeInterface(exeInterface);
        jobLogDO.setNotifyStart(System.currentTimeMillis());
        jobLogDO.setNotifyStatus(NotifyStatusEnum.STATUS_NOSTART.getCode());
        jobLogDO.setReturnStatus(ReturnStatusEnum.STATUS_NOTEXE.getCode());
        long logId = scheduleJobLogDAO.insertSelective(jobLogDO);
        if(logId <= 0) {
            logger.warn("insert jobLog fail, res={}", logId);
            return false;
        }
        Map<String, String> params = new HashMap<>();
        params.put("jobId", jobId+"");
        params.put("exeInterface", exeInterface);
        params.put("type", "exe");
        params.put("logId", logId+"");
        HttpResult result = HttpClientManager.executePost(executeUrl, params);
        logger.info("executeJob execute return:{}", result);
        if(result != null && result.isSuccess()) {
            jobLogDO = new ScheduleJobLogDO();
            jobLogDO.setLogId(logId);
            jobLogDO.setNotifyEnd(System.currentTimeMillis());
            String responseTxt = result.getResponseTxt();
            try {
                Result res = JSON.parseObject(responseTxt, Result.class);
                if(res.getCode() == 1) {
                    jobLogDO.setNotifyStatus(NotifyStatusEnum.STATUS_NOTIFY.getCode());
                } else {
                    jobLogDO.setNotifyStatus(NotifyStatusEnum.STATUS_NOTIFY.getCode());
                    jobLogDO.setReturnStatus(ReturnStatusEnum.STATUS_FAILEXE.getCode());
                    jobLogDO.setRetMsg(res.getMsg());
                }
            } catch (Exception e) {
                logger.error("resonseTxt is illegal,{}", responseTxt);
                jobLogDO.setNotifyStatus(NotifyStatusEnum.STATUS_FAIL.getCode());
                jobLogDO.setRetMsg("illegal response:"+responseTxt);
            }
            int n = scheduleJobLogDAO.updateByPrimaryKeySelective(jobLogDO);
            if(n == 0) {
                logger.warn("update jobLog fail, res={}", n);
                return false;
            }
            return true;
        }
        return false;
    }
}
