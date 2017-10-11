package com.lizhy.client.config;

import org.springframework.beans.factory.config.AbstractFactoryBean;

import java.util.List;

/**
 * Note: 调度配置类
 * Created by lizhiyang on 2017-10-11 10:48.
 */
public class ScheduleConfig extends AbstractFactoryBean{
    private List<String> scheduleCenterUrls;

    @Override
    public Class<?> getObjectType() {
        return ScheduleConfig.class;
    }

    @Override
    protected Object createInstance() throws Exception {
        return new ScheduleConfig();
    }

    public List<String> getScheduleCenterUrls() {
        return scheduleCenterUrls;
    }

    public void setScheduleCenterUrls(List<String> scheduleCenterUrls) {
        this.scheduleCenterUrls = scheduleCenterUrls;
    }
}
