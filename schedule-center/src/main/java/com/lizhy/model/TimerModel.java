package com.lizhy.model;

import com.lizhy.BaseObject;
import com.lizhy.annotation.DateTimeFormat;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * 执行时间
 * Created by lizhiyang on 2017-02-06 14:34.
 */
public class TimerModel extends BaseObject {
    private static final long serialVersionUID = 5141293497693946595L;
    /* 任务id */
    @NotNull(message = "jobId is null")
    private Long jobId;
    /* 当前时间表达式 */
    @NotNull(message = "cron is null")
    @NotEmpty(message = "cron is empty")
    private String cron;
    /* 当前规则生效时间 */
    @NotNull(message = "effectTime is null")
    @NotEmpty(message = "effectTime is empty")
    @DateTimeFormat(message = "effectTime is illegal")
    private String effectiveTime;
    /* 未来生效表达式 */
    private String futureCron;
    /* 未来生效时间 */
    private String futureEffectiveTime;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(String effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getFutureCron() {
        return futureCron;
    }

    public void setFutureCron(String futureCron) {
        this.futureCron = futureCron;
    }

    public String getFutureEffectiveTime() {
        return futureEffectiveTime;
    }

    public void setFutureEffectiveTime(String futureEffectiveTime) {
        this.futureEffectiveTime = futureEffectiveTime;
    }
}
