package com.lizhy.controller;

import com.lizhy.common.CallResult;
import com.lizhy.model.TimerModel;
import com.lizhy.service.TimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * NOTE:
 *
 * @author lizhiyang
 * @Date 2017-10-26 15:56
 */
@Controller
@RequestMapping("/timer")
public class TimerController {
    @Autowired
    private TimerService timerService;

    @RequestMapping("/get.do")
    @ResponseBody
    public CallResult<TimerModel> get(Long jobId) {
        return timerService.getTimer(jobId);
    }

    @RequestMapping("/save.do")
    @ResponseBody
    public CallResult<Boolean> save(TimerModel model) {
        return timerService.saveTimer(model);
    }
}
