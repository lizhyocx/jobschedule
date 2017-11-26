package com.lizhy.controller;

import com.lizhy.common.CallResult;
import com.lizhy.model.JobLogModel;
import com.lizhy.model.PageData;
import com.lizhy.model.ScheduleMonitorModel;
import com.lizhy.service.JobLogService;
import com.lizhy.service.ScheduleMonitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * NOTE:
 *
 * @author lizhiyang
 * @Date 2017-11-26 13:50
 */
@Controller
@RequestMapping("/schedule/")
public class ScheduleMonitorController {
    private static final int PAGE_SIZE = 10;
    @Autowired
    private ScheduleMonitorService scheduleMonitorService;
    @Autowired
    private JobLogService jobLogService;

    @RequestMapping("/list.do")
    @ResponseBody
    public CallResult<PageData<ScheduleMonitorModel>> list(Long jobId, String jobName, Integer pageNo, Integer pageSize) {
        if(pageNo == null) {
            pageNo = 1;
        }
        if(pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        return scheduleMonitorService.getScheduleList(jobId, jobName, pageNo, pageSize);
    }
    @RequestMapping("/monitor/list.do")
    @ResponseBody
    public CallResult<PageData<JobLogModel>> monitorList(Long jobId, Integer exeResult, Integer pageNo, Integer pageSize) {
        if(pageNo == null) {
            pageNo = 1;
        }
        if(pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        return jobLogService.getLogList(jobId, exeResult, pageNo, pageSize);
    }
}
