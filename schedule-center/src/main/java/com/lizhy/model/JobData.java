package com.lizhy.model;

import com.lizhy.BaseObject;

import java.util.Date;

/**
 * Created by lizhiyang on 2017-01-17 10:09.
 */
public class JobData extends BaseObject {

    private static final long serialVersionUID = -4982510071328309657L;
    //simple/cron
    private String category;
    //任务名称
    private String name;
    //开始时间
    private Date startTime = new Date();
    //结束时间
    private Date endTime;
    //重复次数
    private int repeatCount;
    //间隔时间
    private long repeatInterval;
    //描述
    private String desc;
    //quartz表达式
    private String cronExpression;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    public long getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(long repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
}
