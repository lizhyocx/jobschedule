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
     * @Description: 获取任务执行明细
     * @author lizhiyang
     * @Date 2017-11-26 15:38:08
     */
    CallResult<PageData<JobLogModel>> getLogList(Long jobId, Integer exeResult, Integer pageNo, Integer pageSize);
}
