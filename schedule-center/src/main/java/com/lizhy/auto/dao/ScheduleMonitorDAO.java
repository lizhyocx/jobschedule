package com.lizhy.auto.dao;

import com.lizhy.auto.model.ScheduleMonitorDO;

import java.util.List;
import java.util.Map;

/**
 * NOTE:
 *
 * @author lizhiyang
 * @Date 2017-11-26 11:47
 */
public interface ScheduleMonitorDAO {
    /**
     * @Description: 获取调度监控列表
     * @author lizhiyang
     * @Date 2017-11-26 12:08:00
     */
    List<ScheduleMonitorDO> selectScheduleList(Map<String, Object> param);
}
