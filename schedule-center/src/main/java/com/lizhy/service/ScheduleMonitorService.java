package com.lizhy.service;

import com.lizhy.common.CallResult;
import com.lizhy.model.PageData;
import com.lizhy.model.ScheduleMonitorModel;

/**
 * NOTE: 任务调度监控列表
 *
 * @author lizhiyang
 * @Date 2017-11-26 12:00
 */
public interface ScheduleMonitorService {
    /**
     * @Description: 获取调度监控列表
     * @author lizhiyang
     * @Date 2017-11-26 12:02:44
     */
    CallResult<PageData<ScheduleMonitorModel>> getScheduleList(Integer pageNo, Integer pageSize);
}
