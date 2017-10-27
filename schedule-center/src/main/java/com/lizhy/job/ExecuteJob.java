package com.lizhy.job;

import com.lizhy.common.http.HttpClientManager;
import com.lizhy.common.http.HttpResult;
import com.lizhy.common.util.SpringContextUtil;
import com.lizhy.common.enu.ExecuteSelectEnum;
import com.lizhy.model.ExecutorModel;
import com.lizhy.service.ExecutorService;
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
public class ExecuteJob implements Job {
    private Logger logger = LoggerFactory.getLogger(ExecuteJob.class);
    private ExecutorService executorService = (ExecutorService) SpringContextUtil.getBean("executorService");
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        JobDataMap map = jobDetail.getJobDataMap();

        logger.info("ExecuteJob execute....JobDetail={}, JobDataMap={}", jobDetail, map);

        long jobId = (Long)map.get("jobId");
        String jobName = map.getString("jobName");
        int executeSelect = (Integer)map.get("executeSelect");
        int executeRules = (Integer)map.get("executeRules");

        List<ExecutorModel> executorModelList = executorService.getEffectiveExecutor(jobId);
        if(CollectionUtils.isEmpty(executorModelList)) {
            //没有执行机器
            logger.warn("JObId[{}]-JobName[{}] has no executor", jobId, jobName);
            return;
        }
        if(executeSelect == ExecuteSelectEnum.ANY.getCode()) {
            //任选其一
            ExecutorModel executorModel = executorModelList.get(new Random().nextInt(executorModelList.size()));
            doExecute(jobId, executorModel.getExeInterface(), executorModel.getExeUrl());
        } else if(executeSelect == ExecuteSelectEnum.ALL.getCode()) {
            //全部执行
            for(ExecutorModel executorModel : executorModelList) {
                doExecute(jobId, executorModel.getExeInterface(), executorModel.getExeUrl());
            }
        }
    }

    private boolean doExecute(long jobId, String exeInterface, String executeUrl) {
        Map<String, String> params = new HashMap<>();
        params.put("jobId", jobId+"");
        params.put("exeInterface", exeInterface);
        params.put("type", "exe");
        HttpResult result = HttpClientManager.executePost(executeUrl, params);
        if(result != null && result.isSuccess()) {
            return true;
        }
        return false;
    }
}
