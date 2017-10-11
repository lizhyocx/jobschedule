package com.lizhy.controller;

import com.lizhy.common.enu.ReturnStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Note: job回调处理类
 * Created by lizhiyang on 2017-10-11 13:55.
 */
@Controller
public class FeedBackController {
    private Logger logger = LoggerFactory.getLogger(FeedBackController.class);

    @RequestMapping("/job/feedback.do")
    public void feedback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int retCode = ReturnStatusEnum.STATUS_SUCCESS.getCode();
        String logId = request.getParameter("logId");
        String retMsg = request.getParameter("retMsg");
        String code = request.getParameter("code");
        String finished = request.getParameter("finished");
        logger.info("jobfeedback" +",logId:"+logId +",retMsg:"+retMsg +",code:"+code +",finished:"+finished);
        try {
            if (!"1".equals(code)) {
                retCode = ReturnStatusEnum.STATUS_FAILEXE.getCode();
            }
            int i = 0;//更新log表数据
            if (i > 0) {
                printResult(1, "FeadbackSuccess", response);
            } else {
                printResult(0, "FeadbackFailed", response);
            }
        } catch (Exception e) {
            printResult(0, "FeadbackException", response);
            logger.error("feedback !!", "logId:" + logId + ",retMsg:" + retMsg + ",code:" + code + ",finished:" + finished, e);        }
    }

    private void printResult(int code, String msg, HttpServletResponse response)
            throws Exception {
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String retStr = "{\"msg\":\"" + msg + "\",\"code\":" + code + "}";
        try {
            out.print(retStr);
        } catch (Exception e) {
            logger.error("printResult", "feedback print result!!", retStr);
        }
    }
}
