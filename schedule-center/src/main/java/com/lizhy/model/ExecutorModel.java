package com.lizhy.model;

import com.lizhy.BaseObject;

/**
 * 执行集齐
 * Created by lizhiyang on 2017-02-06 14:32.
 */
public class ExecutorModel extends BaseObject {

    private static final long serialVersionUID = -5142705388166989841L;
    private long executorId;
    private long jobId;
    private String executorName;
    private String executeUrl;
    private String executeInterface;
    private int status;
    private long effectTime;

    public long getExecutorId() {
        return executorId;
    }

    public void setExecutorId(long executorId) {
        this.executorId = executorId;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public String getExecutorName() {
        return executorName;
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }

    public String getExecuteUrl() {
        return executeUrl;
    }

    public void setExecuteUrl(String executeUrl) {
        this.executeUrl = executeUrl;
    }

    public String getExecuteInterface() {
        return executeInterface;
    }

    public void setExecuteInterface(String executeInterface) {
        this.executeInterface = executeInterface;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(long effectTime) {
        this.effectTime = effectTime;
    }
}
