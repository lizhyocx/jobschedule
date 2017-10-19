package com.lizhy.controller;

import com.lizhy.common.CallResult;
import com.lizhy.model.JobInfoModel;
import com.lizhy.model.PageData;
import com.lizhy.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * NOTE: 定时任务控制器
 *
 * @author lizhiyang
 * @Date 2017-10-18 15:46
 */
@Controller
@RequestMapping("/job/")
public class JobController {
    @Autowired
    private JobService jobService;

    @RequestMapping(value="/save.do", method = RequestMethod.POST)
    @ResponseBody
    public CallResult<Boolean> save(JobInfoModel model) {
        return jobService.saveJob(model);
    }

    @RequestMapping(value = "/list.do")
    @ResponseBody
    public CallResult<PageData<JobInfoModel>> list(JobInfoModel model, Integer pageNo, Integer pageSize) {
        return jobService.getJobList(model, pageNo, pageSize);
    }

    @RequestMapping(value = "get.do")
    @ResponseBody
    public CallResult<JobInfoModel> get(Long jobId) {
        return jobService.getJobById(jobId);
    }

    /*@ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public String messageNotReadable(HttpMessageNotReadableException exception, HttpServletResponse response){
        exception.printStackTrace();
        return exception.getMessage();
    }*/
}
