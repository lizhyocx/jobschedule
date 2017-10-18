package com.lizhy.service.impl;

import com.lizhy.auto.dao.ScheduleJobDAO;
import com.lizhy.auto.model.ScheduleJobDO;
import com.lizhy.common.CallResult;
import com.lizhy.common.service.AbstractTemplateAction;
import com.lizhy.model.JobInfoModel;
import com.lizhy.service.AbstractBaseService;
import com.lizhy.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 任务相关服务类
 * @author lizhiyang
 * @Date 2017-10-18 14:19:12
 */
@Service
public class JobServiceImpl extends AbstractBaseService implements JobService {
    private Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);
    private BeanCopier model2doCopier = BeanCopier.create(JobInfoModel.class, ScheduleJobDO.class, false);

    @Autowired
    private ScheduleJobDAO scheduleJobDAO;

    @Override
    public CallResult<Boolean> saveJob(final JobInfoModel model) {
        try {
            return serviceTemplate.exeOnMaster(new AbstractTemplateAction<Boolean>() {
                @Override
                public CallResult<Boolean> checkParam() {
                    if(model == null) {
                        return CallResult.failure("参数为空");
                    }
                    return super.checkParam();
                }
                @Override
                public CallResult<Boolean> doAction() {
                    ScheduleJobDO jobDO = new ScheduleJobDO();
                    model2doCopier.copy(model, jobDO, null);
                    int n = scheduleJobDAO.insertSelective(jobDO);
                    if(n == 1) {
                        return CallResult.success(true);
                    }
                    logger.warn("db save job fail, res={}", n);
                    return CallResult.failure("保存任务失败");
                }
            });
        } catch (Exception e) {
            logger.error("saveJob exception", e);
        }
        return CallResult.failure("保存任务异常");
    }

    @Override
    public List<JobInfoModel> getEffectiveJob() {
        return null;
    }
}
