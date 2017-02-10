package com.lizhy.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lizhiyang on 2017-02-08 15:22.
 */
@Controller
public class NotifyController {

    @RequestMapping("/job/notify")
    public void notify(HttpServletRequest request, HttpServletResponse response) {

    }
}
