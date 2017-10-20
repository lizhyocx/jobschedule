package com.lizhy.controller;

import com.lizhy.common.CallResult;
import com.lizhy.model.ExecutorModel;
import com.lizhy.service.ExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * NOTE:
 *
 * @author lizhiyang
 * @Date 2017-10-20 15:29
 */
@Controller
@RequestMapping("/executor")
public class ExecutorController {
    @Autowired
    private ExecutorService executorService;

    @RequestMapping("list.do")
    @ResponseBody
    public CallResult<List<ExecutorModel>> list(Long jobId) {
        return executorService.getExecutorList(jobId);
    }

    @RequestMapping(value = "save.do", method = RequestMethod.POST)
    @ResponseBody
    public CallResult<Boolean> save(ExecutorModel model) {
        return executorService.saveExecutor(model);
    }
}
