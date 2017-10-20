package com.lizhy.service.impl;

import com.lizhy.auto.dao.ScheduleExecutorDAO;
import com.lizhy.auto.model.ScheduleExecutorDO;
import com.lizhy.common.CallResult;
import com.lizhy.common.service.AbstractTemplateAction;
import com.lizhy.enu.ExecutorStatusEnum;
import com.lizhy.model.ExecutorModel;
import com.lizhy.service.AbstractBaseService;
import com.lizhy.service.ExecutorService;
import com.lizhy.validator.ValidateResult;
import com.lizhy.validator.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhiyang on 2017-02-06 14:42.
 */
@Service
public class ExecutorServiceImpl extends AbstractBaseService implements ExecutorService {
    private Logger logger = LoggerFactory.getLogger(ExecutorServiceImpl.class);
    private BeanCopier do2modelCopier = BeanCopier.create(ScheduleExecutorDO.class, ExecutorModel.class, false);
    private BeanCopier model2doCopier = BeanCopier.create(ExecutorModel.class, ScheduleExecutorDO.class, false);
    @Autowired
    private ScheduleExecutorDAO scheduleExecutorDAO;

    @Override
    public CallResult<List<ExecutorModel>> getExecutorList(final Long jobId) {
        try {
            return serviceTemplate.exeOnSlave(new AbstractTemplateAction<List<ExecutorModel>>() {
                @Override
                public CallResult<List<ExecutorModel>> checkParam() {
                    if(jobId == null) {
                        logger.warn("jobId is null");
                        return CallResult.failure("参数错误");
                    }
                    return super.checkParam();
                }

                @Override
                public CallResult<List<ExecutorModel>> doAction() {
                    List<ScheduleExecutorDO> doList = scheduleExecutorDAO.selectAllByJobId(jobId);
                    List<ExecutorModel> modelList = new ArrayList<>();
                    if(!CollectionUtils.isEmpty(doList)) {
                        ExecutorModel model = null;
                        for(ScheduleExecutorDO executorDO : doList) {
                            model = new ExecutorModel();
                            do2modelCopier.copy(executorDO, model, null);
                            modelList.add(model);
                        }
                    }
                    return CallResult.success(modelList);
                }
            });
        } catch (Exception e) {
            logger.error("getExecutorList exception", e);
        }
        return CallResult.failure("获取执行机器列表失败");
    }

    @Override
    public CallResult<Boolean> saveExecutor(final ExecutorModel model) {
        try {
            return serviceTemplate.exeOnMaster(new AbstractTemplateAction<Boolean>() {
                @Override
                public CallResult<Boolean> checkParam() {
                    if(model == null) {
                        logger.warn("model is null");
                        return CallResult.failure("参数错误");
                    }
                    ValidateResult result = ValidateUtil.validator(model);
                    if(result == null || !result.isSuccess()) {
                        logger.warn("model validator fail, result={}", result);
                        return CallResult.failure("参数错误");
                    }
                    return super.checkParam();
                }

                @Override
                public CallResult<Boolean> doAction() {
                    ScheduleExecutorDO executorDO = new ScheduleExecutorDO();
                    model2doCopier.copy(model, executorDO, null);
                    int n = 0;
                    if(model.getExeId() != null) {
                        executorDO.setUpdateTime(System.currentTimeMillis());
                        n = scheduleExecutorDAO.updateByPrimaryKeySelective(executorDO);
                    } else {
                        long now = System.currentTimeMillis();
                        if(now >= model.getEffectiveTime()) {
                            executorDO.setStatus(ExecutorStatusEnum.VALID.getCode());
                        } else {
                            executorDO.setStatus(ExecutorStatusEnum.FUTURE.getCode());
                        }
                        executorDO.setCreateTime(now);
                        n = scheduleExecutorDAO.insertSelective(executorDO);
                    }
                    if(n == 1) {
                        return CallResult.success(true);
                    }
                    logger.warn("save executor fail,n={}", n);
                    return CallResult.failure("保存失败");
                }
            });
        } catch (Exception e) {
            logger.error("saveExecutor exception", e);
        }
        return CallResult.failure("保存失败");
    }

    @Override
    public List<ExecutorModel> getEffectiveExecutor(Long jobId) {
        return null;
    }
}
