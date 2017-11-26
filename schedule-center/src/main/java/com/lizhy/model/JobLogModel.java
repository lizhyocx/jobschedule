package com.lizhy.model;

import com.lizhy.BaseObject;

/**
 * 任务监控
 * Created by lizhiyang on 2017-02-06 14:35.
 */
public class JobLogModel extends BaseObject {
    private static final long serialVersionUID = 1554015179231338655L;

    private Long logId;
    private Long jobId;
    private Long executorId;
    private String exeUrl;
    private String exeInterface;
    private Long notifyStart;
    private Long notifyEnd;
    private Long finished;
    private String retMsg;
    private Integer notifyStatus;
    private Integer returnStatus;
    private Integer alertTimes;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getExecutorId() {
        return executorId;
    }

    public void setExecutorId(Long executorId) {
        this.executorId = executorId;
    }

    public String getExeUrl() {
        return exeUrl;
    }

    public void setExeUrl(String exeUrl) {
        this.exeUrl = exeUrl;
    }

    public String getExeInterface() {
        return exeInterface;
    }

    public void setExeInterface(String exeInterface) {
        this.exeInterface = exeInterface;
    }

    public Long getNotifyStart() {
        return notifyStart;
    }

    public void setNotifyStart(Long notifyStart) {
        this.notifyStart = notifyStart;
    }

    public Long getNotifyEnd() {
        return notifyEnd;
    }

    public void setNotifyEnd(Long notifyEnd) {
        this.notifyEnd = notifyEnd;
    }

    public Long getFinished() {
        return finished;
    }

    public void setFinished(Long finished) {
        this.finished = finished;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public Integer getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(Integer notifyStatus) {
        this.notifyStatus = notifyStatus;
    }

    public Integer getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(Integer returnStatus) {
        this.returnStatus = returnStatus;
    }

    public Integer getAlertTimes() {
        return alertTimes;
    }

    public void setAlertTimes(Integer alertTimes) {
        this.alertTimes = alertTimes;
    }
}
