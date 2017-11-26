package com.lizhy.service.impl;

import com.lizhy.auto.dao.ScheduleJobDAO;
import com.lizhy.auto.dao.ScheduleMonitorDAO;
import com.lizhy.auto.model.ScheduleMonitorDO;
import com.lizhy.common.CallResult;
import com.lizhy.common.service.AbstractTemplateAction;
import com.lizhy.enu.JobStatusEnum;
import com.lizhy.model.PageData;
import com.lizhy.model.ScheduleMonitorModel;
import com.lizhy.service.AbstractBaseService;
import com.lizhy.service.ScheduleMonitorService;
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
 * @Date 2017-11-26 12:05
 */
@Service("scheduleMonitorService")
public class ScheduleMonitorServiceImpl extends AbstractBaseService implements ScheduleMonitorService {
    private static Logger logger = LoggerFactory.getLogger(ScheduleMonitorServiceImpl.class);
    private BeanCopier do2ModelCopier = BeanCopier.create(ScheduleMonitorDO.class, ScheduleMonitorModel.class, false);
    @Autowired
    private ScheduleMonitorDAO scheduleMonitorDAO;
    @Autowired
    private ScheduleJobDAO scheduleJobDAO;
    @Override
    public CallResult<PageData<ScheduleMonitorModel>> getScheduleList(final Integer pageNo, final Integer pageSize) {
        try {
            CallResult<PageData<ScheduleMonitorModel>> result = serviceTemplate.exeOnSlave(new AbstractTemplateAction<PageData<ScheduleMonitorModel>>() {
                @Override
                public CallResult<PageData<ScheduleMonitorModel>> checkParam() {
                    if(pageNo == null || pageSize == null || pageNo <= 0 || pageSize <= 0) {
                        logger.warn("pageNo={}, pageSize={} is illegal", pageNo, pageSize);
                    }
                    return super.checkParam();
                }

                @Override
                public CallResult<PageData<ScheduleMonitorModel>> doAction() {
                    PageData<ScheduleMonitorModel> pageData = new PageData();
                    Map<String, Object> param = new HashMap<>();
                    param.put("status", JobStatusEnum.VALID.getCode());
                    int count = scheduleJobDAO.selectCountByParam(param);
                    pageData.setTotalNumber(count);
                    if(count > 0) {
                        param.clear();
                        int offset = (pageNo-1) * pageSize;
                        param.put("offset", offset);
                        param.put("pageSize", pageSize);
                        List<ScheduleMonitorDO> doList = scheduleMonitorDAO.selectScheduleList(param);
                        if(!CollectionUtils.isEmpty(doList)) {
                            List<ScheduleMonitorModel> modelList = new ArrayList<>(doList.size());
                            ScheduleMonitorModel model = null;
                            for(ScheduleMonitorDO monitorDO : doList) {
                                model = new ScheduleMonitorModel();
                                do2ModelCopier.copy(monitorDO, model, null);
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
            logger.warn("getScheduleList fail, result={}", result);
            return CallResult.failure("获取任务调度列表失败");
        } catch (Exception e) {
            logger.error("getScheduleList exception", e);
        }
        return CallResult.failure("获取调度列表失败");
    }
}
