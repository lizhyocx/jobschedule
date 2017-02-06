package com.lizhy.model;

import com.lizhy.auto.model.BaseDO;

/**
 * 定时任务基本信息
 * Created by lizhiyang on 2017-02-06 14:29.
 */
public class JobInfoModel extends BaseDO {

    private static final long serialVersionUID = 5572033482086578111L;
    private long jobId;
    private String jobName;
    private String jobDesc;
    private int timeout;
    private int executeSelect;
    private int executeRules;
    private long creatTime;
    private int status;
    private long disableTime;
    private long modifyTime;

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getExecuteSelect() {
        return executeSelect;
    }

    public void setExecuteSelect(int executeSelect) {
        this.executeSelect = executeSelect;
    }

    public int getExecuteRules() {
        return executeRules;
    }

    public void setExecuteRules(int executeRules) {
        this.executeRules = executeRules;
    }

    public long getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(long creatTime) {
        this.creatTime = creatTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getDisableTime() {
        return disableTime;
    }

    public void setDisableTime(long disableTime) {
        this.disableTime = disableTime;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }
}
