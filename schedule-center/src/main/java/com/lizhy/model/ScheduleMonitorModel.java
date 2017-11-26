package com.lizhy.model;

import com.lizhy.BaseObject;

/**
 * NOTE:
 *
 * @author lizhiyang
 * @Date 2017-11-26 12:01
 */
public class ScheduleMonitorModel extends BaseObject {
    private static final long serialVersionUID = 7628780051464656607L;

    private Long jobId;
    private String jobName;
    private String exeInterface;
    private String exeUrl;
    private Long notifyStart;
    private Long notifyEnd;
    private Integer notifyStatus;
    private Integer returnStatus;
    private String retMsg;
    private Long prevFireTime;
    private Long nextFireTime;
    private Long lastLogId;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getExeInterface() {
        return exeInterface;
    }

    public void setExeInterface(String exeInterface) {
        this.exeInterface = exeInterface;
    }

    public String getExeUrl() {
        return exeUrl;
    }

    public void setExeUrl(String exeUrl) {
        this.exeUrl = exeUrl;
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

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public Long getPrevFireTime() {
        return prevFireTime;
    }

    public void setPrevFireTime(Long prevFireTime) {
        this.prevFireTime = prevFireTime;
    }

    public Long getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(Long nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    public Long getLastLogId() {
        return lastLogId;
    }

    public void setLastLogId(Long lastLogId) {
        this.lastLogId = lastLogId;
    }
}
