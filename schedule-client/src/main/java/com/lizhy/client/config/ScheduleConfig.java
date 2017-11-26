package com.lizhy.client.config;

import java.util.List;

/**
 * Note: 调度配置类
 * Created by lizhiyang on 2017-10-11 10:48.
 */
public class ScheduleConfig {
    private List<String> scheduleCenterUrls;

    public List<String> getScheduleCenterUrls() {
        return scheduleCenterUrls;
    }

    public void setScheduleCenterUrls(List<String> scheduleCenterUrls) {
        this.scheduleCenterUrls = scheduleCenterUrls;
    }
}
