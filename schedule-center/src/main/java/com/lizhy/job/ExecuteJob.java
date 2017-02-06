package com.lizhy.job;

import com.lizhy.common.util.SpringContextUtil;
import com.lizhy.enu.ExecuteSelectEnum;
import com.lizhy.model.ExecutorModel;
import com.lizhy.service.ExecutorService;
import org.quartz.*;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 任务执行
 * Created by lizhiyang on 2017-01-18 13:45.
 */
public class ExecuteJob implements Job {
    private ExecutorService executorService = (ExecutorService) SpringContextUtil.getBean("executorService");
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        JobDataMap map = jobDetail.getJobDataMap();

        long jobId = (Long)map.get("jobId");
        String jobName = map.getString("jobName");
        String exeInterface = map.getString("executeInterface");
        int executeSelect = (Integer)map.get("executeSelect");
        int executeRules = (Integer)map.get("executeRules");

        List<ExecutorModel> executorModelList = executorService.getEffectiveExecutor(jobId);
        if(CollectionUtils.isEmpty(executorModelList)) {
            //没有执行机器

            return;
        }
        if(executeSelect == ExecuteSelectEnum.ANY.getCode()) {
            //任选其一
        } else if(executeSelect == ExecuteSelectEnum.ALL.getCode()) {
            //全部执行
        }
    }
}
