package com.lizhy.service;

import com.lizhy.common.CallResult;
import com.lizhy.model.TimerModel;

/**
 * NOTE:
 *
 * @author lizhiyang
 * @Date 2017-10-26 14:04
 */
public interface TimerService {
    /**
     * @Description: 保存时间规则
     * @author lizhiyang
     * @Date 2017-10-26 14:05:31
     */
    CallResult<Boolean> saveTimer(TimerModel model);
    
    /**
     * @Description: 获取时间规则
     * @author lizhiyang
     * @Date 2017-10-26 14:11:05
     */
    CallResult<TimerModel> getTimer(Long jobId);
}
