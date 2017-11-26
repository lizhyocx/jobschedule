package com.lizhy.service;

import com.lizhy.common.CallResult;
import com.lizhy.model.JobLogModel;
import com.lizhy.model.PageData;

/**
 * NOTE: 任务执行记录
 *
 * @author lizhiyang
 * @Date 2017-11-26 15:37
 */
public interface JobLogService {
    /**
     * @Description: 回掉更新执行日志
     * @author lizhiyang
     * @Date 2017-11-26 16:43:14
     */
    CallResult<Boolean> feedbackJobLog(Long logId, Integer retCode, String finished, String retMsg);
    /**
     * @Description: 获取任务执行明细
     * @author lizhiyang
     * @Date 2017-11-26 15:38:08
     */
    CallResult<PageData<JobLogModel>> getLogList(Long jobId, Integer exeResult, Integer pageNo, Integer pageSize);
}
