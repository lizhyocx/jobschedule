package com.lizhy.service.impl;

import com.lizhy.auto.dao.ScheduleJobDAO;
import com.lizhy.auto.dao.ScheduleTimerDAO;
import com.lizhy.auto.model.ScheduleJobDO;
import com.lizhy.auto.model.ScheduleTimerDO;
import com.lizhy.common.CallResult;
import com.lizhy.common.service.AbstractTemplateAction;
import com.lizhy.enu.JobCategoryEnum;
import com.lizhy.enu.TimerStatusEnum;
import com.lizhy.job.ExecuteJob;
import com.lizhy.model.JobData;
import com.lizhy.model.TimerModel;
import com.lizhy.service.AbstractBaseService;
import com.lizhy.service.ScheduleService;
import com.lizhy.service.TimerService;
import com.lizhy.validator.ValidateResult;
import com.lizhy.validator.ValidateUtil;
import org.quartz.CronExpression;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * NOTE:
 *
 * @author lizhiyang
 * @Date 2017-10-26 14:11
 */
@Service
public class TimerServiceImpl extends AbstractBaseService implements TimerService {
    private Logger logger = LoggerFactory.getLogger(TimerServiceImpl.class);
    @Autowired
    private ScheduleTimerDAO scheduleTimerDAO;
    @Autowired
    private ScheduleJobDAO scheduleJobDAO;

    @Override
    public CallResult<Boolean> saveTimer(final TimerModel model) {
        try {
            return serviceTemplate.exeInTranscation(new AbstractTemplateAction<Boolean>() {
                private ScheduleJobDO jobDO = null;
                @Override
                public CallResult<Boolean> checkParam() {
                    if(model == null) {
                        logger.warn("model is null");
                        return CallResult.failure("参数错误");
                    }
                    ValidateResult result = ValidateUtil.validator(model);
                    if(result == null || !result.isSuccess()) {
                        logger.warn("model validate fail, result={}", result);
                        return CallResult.failure("参数错误");
                    }
                    if(!CronExpression.isValidExpression(model.getCron())) {
                        logger.warn("cron is illegal:{}", model.getCron());
                        return CallResult.failure("参数错误");
                    }
                    return super.checkParam();
                }

                @Override
                public CallResult<Boolean> checkBiz() {
                    jobDO = scheduleJobDAO.selectByPrimaryKey(model.getJobId());
                    if(jobDO == null) {
                        logger.warn("cannot find job by id {}", model.getJobId());
                        return CallResult.failure("参数错误");
                    }
                    return super.checkBiz();
                }

                @Override
                public CallResult<Boolean> doAction() {
                    long now = System.currentTimeMillis();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Long effectiveTime = 0L;
                    try {
                        effectiveTime = format.parse(model.getEffectiveTime()).getTime();
                    } catch (ParseException e) {
                        logger.warn("effectiveTime is illegal {}", model.getEffectiveTime());
                        return CallResult.failure("参数错误");
                    }
                    ScheduleTimerDO timerDO = new ScheduleTimerDO();
                    timerDO.setJobId(model.getJobId());
                    timerDO.setCron(model.getCron());
                    timerDO.setEffectiveTime(effectiveTime);
                    timerDO.setCreateTime(now);
                    timerDO.setUpdateTime(now);
                    if(effectiveTime > now) {
                        timerDO.setStatus(TimerStatusEnum.FUTURE.getCode());
                    } else {
                        timerDO.setStatus(TimerStatusEnum.VALID.getCode());
                    }
                    int n = scheduleTimerDAO.upsertSelective(timerDO);
                    if(n > 0) {
                        //timer保存成功，如果是现在生效，加入调度中
                        if(TimerStatusEnum.VALID.getCode() == timerDO.getStatus()) {
                            if(loadJob(jobDO, timerDO)) {
                                return CallResult.success(true);
                            }
                            logger.warn("loadJob fail");
                            return CallResult.failure("加入调度失败");
                        }
                    }
                    logger.warn("upsert timer fail, n = {}", n);
                    return CallResult.failure("保存时间规则失败");
                }
            });
        } catch (Exception e) {
            logger.error("saveTimer exception", e);
        }
        return CallResult.failure("保存时间规则失败");
    }

    @Override
    public CallResult<TimerModel> getTimer(final Long jobId) {
        try {
            return serviceTemplate.exeOnSlave(new AbstractTemplateAction<TimerModel>() {
                @Override
                public CallResult<TimerModel> checkParam() {
                    if(jobId == null) {
                        logger.warn("jobId is null");
                        return CallResult.failure("参数错误");
                    }
                    return super.checkParam();
                }

                @Override
                public CallResult<TimerModel> doAction() {
                    TimerModel model = new TimerModel();
                    model.setJobId(jobId);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    List<ScheduleTimerDO> list =  scheduleTimerDAO.selectByJobId(jobId);
                    if(!CollectionUtils.isEmpty(list)) {
                        for(ScheduleTimerDO timerDO : list) {
                            if(TimerStatusEnum.VALID.getCode() == timerDO.getStatus()) {
                                model.setTimerId(timerDO.getTimerId());
                                model.setCron(timerDO.getCron());
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(timerDO.getEffectiveTime());
                                model.setEffectiveTime(format.format(calendar.getTime()));
                            } else if(TimerStatusEnum.FUTURE.getCode() == timerDO.getStatus()) {
                                model.setFutureTimerId(timerDO.getTimerId());
                                model.setFutureCron(timerDO.getCron());
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(timerDO.getEffectiveTime());
                                model.setFutureEffectiveTime(format.format(calendar.getTime()));
                            }
                        }
                    }
                    return CallResult.success(model);
                }
            });
        } catch (Exception e) {
            logger.error("getTime exception", e);
        }
        return CallResult.failure("获取时间规则失败");
    }

    /*=========================================================================================*/
}
