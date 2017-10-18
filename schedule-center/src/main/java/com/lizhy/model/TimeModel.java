package com.lizhy.model;

import com.lizhy.BaseObject;

/**
 * 执行时间
 * Created by lizhiyang on 2017-02-06 14:34.
 */
public class TimeModel extends BaseObject {
    private static final long serialVersionUID = 5141293497693946595L;
    private long jobId;
    private String cronExpression;
    private long effectTime;

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public long getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(long effectTime) {
        this.effectTime = effectTime;
    }
}
