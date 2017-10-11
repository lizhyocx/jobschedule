package com.lizhy.client.controller;

import com.lizhy.client.util.ExecutorManager;
import com.lizhy.client.util.Result;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by lizhiyang on 2017-02-08 15:22.
 */
@Controller
public class NotifyController {
    private static Logger logger = LoggerFactory.getLogger(NotifyController.class);

    @RequestMapping("/job/notify")
    public void notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        String type = request.getParameter("type");
        String interf = request.getParameter("interface");
        String logIdStr = request.getParameter("logId");
        if(logger.isInfoEnabled()){
            logger.info("business="+getClass().getName()+
                    ",category=notify,"+
                    "type="+type+",inerface="+interf+",logIdStr="+logIdStr);
        }
        long logId = 0;
        if (StringUtils.isNotBlank(logIdStr)) {
            logId = Long.parseLong(logIdStr);
        }
        Result res = new Result(0,"Unkonwn");

        if ("exe".equalsIgnoreCase(type)) {
            res = ExecutorManager.runJob(interf, logId);
        }
        if("query".equalsIgnoreCase(type)){
            res = ExecutorManager.queryJobRuningStatus(interf, logId);
        }
        if ("stop".equalsIgnoreCase(type)) {
            res = ExecutorManager.stopJob(interf, logId);
        }
        if("interrupt".equalsIgnoreCase(type)){
            res = ExecutorManager.interruptJob(interf, logId);
        }
        response.setCharacterEncoding("UTF-8");
        printResult(res, response);
    }

    private void printResult(Result res, HttpServletResponse response)
            throws Exception {
        response.setHeader("Cache-Control", "no-cache");
        //2014年4月22日 增加响应编码
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String retStr = "{\"msg\":\"" + res.getMsg() + "\",\"code\":" + res.getCode() + "}";
        try {
            out.print(retStr);
            out.flush();
        } catch (Exception e) {
            //print stack
            logger.error("error while print ",e);
        }
        finally{
            out.close();
        }
        if(logger.isDebugEnabled()){
            logger.debug("business="+getClass().getName()+",category=notify,msg=notify return to schedule center!!");
        }

    }
}
