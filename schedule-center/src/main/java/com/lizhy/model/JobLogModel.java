package com.lizhy.model;

import com.lizhy.auto.model.BaseDO;

/**
 * 任务监控
 * Created by lizhiyang on 2017-02-06 14:35.
 */
public class JobLogModel extends BaseDO{
    private static final long serialVersionUID = 1554015179231338655L;
    private long jobId;
    private long executorId;
    private String executeUrl;
    private String executeInterface;
    private long notifyStartTime;
    private long notifyReturnTime;
    private long executeReturnTime;
    private int executeResult;
    private int notifyStatus;
    private int executeStatus;
    private int alertTime;

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public long getExecutorId() {
        return executorId;
    }

    public void setExecutorId(long executorId) {
        this.executorId = executorId;
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

    public long getNotifyStartTime() {
        return notifyStartTime;
    }

    public void setNotifyStartTime(long notifyStartTime) {
        this.notifyStartTime = notifyStartTime;
    }

    public long getNotifyReturnTime() {
        return notifyReturnTime;
    }

    public void setNotifyReturnTime(long notifyReturnTime) {
        this.notifyReturnTime = notifyReturnTime;
    }

    public long getExecuteReturnTime() {
        return executeReturnTime;
    }

    public void setExecuteReturnTime(long executeReturnTime) {
        this.executeReturnTime = executeReturnTime;
    }

    public int getExecuteResult() {
        return executeResult;
    }

    public void setExecuteResult(int executeResult) {
        this.executeResult = executeResult;
    }

    public int getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(int notifyStatus) {
        this.notifyStatus = notifyStatus;
    }

    public int getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(int executeStatus) {
        this.executeStatus = executeStatus;
    }

    public int getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(int alertTime) {
        this.alertTime = alertTime;
    }
}
