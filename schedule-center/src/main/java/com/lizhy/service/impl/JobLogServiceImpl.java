package com.lizhy.service.impl;

import com.lizhy.auto.dao.ScheduleJobDAO;
import com.lizhy.auto.dao.ScheduleJobLogDAO;
import com.lizhy.auto.model.ScheduleJobLogDO;
import com.lizhy.common.CallResult;
import com.lizhy.common.service.AbstractTemplateAction;
import com.lizhy.model.JobInfoModel;
import com.lizhy.model.JobLogModel;
import com.lizhy.model.PageData;
import com.lizhy.service.AbstractBaseService;
import com.lizhy.service.JobLogService;
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
 * NOTE:
 *
 * @author lizhiyang
 * @Date 2017-11-26 15:38
 */
@Service("jobLogService")
public class JobLogServiceImpl extends AbstractBaseService implements JobLogService {
    private static Logger logger = LoggerFactory.getLogger(JobLogServiceImpl.class);
    private BeanCopier do2ModelCopier = BeanCopier.create(ScheduleJobLogDO.class, JobLogModel.class, false);

    @Autowired
    private ScheduleJobLogDAO scheduleJobLogDAO;

    @Override
    public CallResult<PageData<JobLogModel>> getLogList(final Long jobId, final Integer exeResult, final Integer pageNo, final Integer pageSize) {
        try {
            CallResult<PageData<JobLogModel>> result = serviceTemplate.exeOnSlave(new AbstractTemplateAction<PageData<JobLogModel>>() {
                @Override
                public CallResult<PageData<JobLogModel>> checkParam() {
                    if(jobId == null) {
                        logger.warn("jobId is null");
                        return CallResult.failure("jobId is null");
                    }
                    if(pageNo == null || pageSize == null || pageNo <= 0 || pageSize <= 0) {
                        logger.warn("pageNo={}, pageSize={} is illegal", pageNo, pageSize);
                        return CallResult.failure("pageNo or pageSize is illegal");
                    }
                    return super.checkParam();
                }

                @Override
                public CallResult<PageData<JobLogModel>> doAction() {
                    PageData<JobLogModel> pageData = new PageData<>();
                    Map<String, Object> param = new HashMap<>();
                    param.put("jobId", jobId);
                    param.put("exeResult", exeResult);
                    int count = scheduleJobLogDAO.selectJobLogListCount(param);
                    pageData.setTotalNumber(count);
                    if(count > 0) {
                        int offset = (pageNo - 1) * pageSize;
                        param.put("offset", offset);
                        param.put("pageSize", pageSize);
                        List<ScheduleJobLogDO> doList = scheduleJobLogDAO.selectJobLogList(param);
                        if(!CollectionUtils.isEmpty(doList)) {
                            List<JobLogModel> modelList = new ArrayList<>(doList.size());
                            JobLogModel model = null;
                            for(ScheduleJobLogDO jobLogDO : doList) {
                                model = new JobLogModel();
                                do2ModelCopier.copy(jobLogDO, model, null);
                                modelList.add(model);
                            }
                            pageData.setDataList(modelList);
                        }

                    }
                    return CallResult.success(pageData);
                }
            });
            if(result != null && result.isSuccess()) {
                return result;
            }
            logger.warn("getLogList fail, result={}", result);
        } catch (Exception e) {
            logger.error("getLogList exception", e);
        }
        return CallResult.failure("获取任务监控明细失败");
    }
}
