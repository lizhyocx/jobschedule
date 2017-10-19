package com.lizhy.service.impl;

import com.lizhy.auto.dao.ScheduleJobDAO;
import com.lizhy.auto.model.ScheduleJobDO;
import com.lizhy.common.CallResult;
import com.lizhy.common.service.AbstractTemplateAction;
import com.lizhy.enu.JobStatusEnum;
import com.lizhy.model.JobInfoModel;
import com.lizhy.model.PageData;
import com.lizhy.service.AbstractBaseService;
import com.lizhy.service.JobService;
import com.lizhy.validator.ValidateResult;
import com.lizhy.validator.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 任务相关服务类
 * @author lizhiyang
 * @Date 2017-10-18 14:19:12
 */
@Service
public class JobServiceImpl extends AbstractBaseService implements JobService {
    private Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);
    private BeanCopier model2doCopier = BeanCopier.create(JobInfoModel.class, ScheduleJobDO.class, false);
    private BeanCopier do2modelCopier = BeanCopier.create(ScheduleJobDO.class, JobInfoModel.class, false);

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
                    ValidateResult result = ValidateUtil.validator(model);
                    if(result == null || !result.isSuccess()) {
                        logger.warn("model validate fail, result={}", result);
                        return CallResult.failure("参数错误");
                    }
                    return super.checkParam();
                }
                @Override
                public CallResult<Boolean> doAction() {
                    ScheduleJobDO jobDO = new ScheduleJobDO();
                    model2doCopier.copy(model, jobDO, null);
                    jobDO.setStatus(JobStatusEnum.VALID.getCode());
                    jobDO.setCreateTime(System.currentTimeMillis());
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
    public CallResult<PageData<JobInfoModel>> getJobList(final JobInfoModel queryModel, final Integer pageNo, final Integer pageSize) {
        try {
            return serviceTemplate.exeOnSlave(new AbstractTemplateAction<PageData<JobInfoModel>>() {
                @Override
                public CallResult<PageData<JobInfoModel>> checkParam() {
                    if(pageNo == null || pageSize == null || pageNo <= 0 || pageSize <= 0) {
                        logger.warn("pageNo={} or pageSize={} is illegal", pageNo, pageSize);
                        return CallResult.failure("列表参数错误");
                    }
                    return super.checkParam();
                }

                @Override
                public CallResult<PageData<JobInfoModel>> doAction() {
                    PageData<JobInfoModel> pageData = new PageData();
                    int offset = (pageNo - 1) * pageSize;
                    Map<String, Object> params = new HashMap<>();
                    params.put("offset", offset);
                    params.put("pageSize", pageSize);
                    if(queryModel != null) {
                        params.put("jobId", queryModel.getJobId());
                        params.put("jobName", queryModel.getJobName());
                        params.put("status", queryModel.getStatus());
                    }
                    int count = scheduleJobDAO.selectCountByParam(params);
                    List<JobInfoModel> modelList = new ArrayList<>();
                    if(count > 0) {
                        List<ScheduleJobDO> doList = scheduleJobDAO.selectListByParam(params);
                        if(!CollectionUtils.isEmpty(doList)) {
                            JobInfoModel model = null;
                            for(ScheduleJobDO jobDO : doList) {
                                model = new JobInfoModel();
                                do2modelCopier.copy(jobDO, model, null);
                                modelList.add(model);
                            }
                        }
                    }
                    pageData.setTotalNumber(count);
                    pageData.setDataList(modelList);
                    return CallResult.success(pageData);
                }
            });
        } catch (Exception e) {
            logger.error("getJobList exception", e);
        }
        return CallResult.failure("获取任务列表失败");
    }

    @Override
    public List<JobInfoModel> getEffectiveJob() {
        return null;
    }
}
