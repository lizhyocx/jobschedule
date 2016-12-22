package com.lizhy.service;

/**
 * Created by lizhiyang on 2016/11/17.
 */
public interface ScheduleService {
    /**
     * 校验是否是第一次运行
     * @return
     */
    boolean checkFirstRun();

    void start();
}
