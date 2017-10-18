package com.lizhy.model;

import com.lizhy.BaseObject;

/**
 * 定时任务基本信息
 * Created by lizhiyang on 2017-02-06 14:29.
 */
public class JobInfoModel extends BaseObject {

    private static final long serialVersionUID = 5572033482086578111L;

    private Long jobId;

    private String jobName;

    private Integer executeSelect;

    private Integer executeRule;

    private String jobDesc;

    private Integer timeout;

    private Long createTime;

    private Long updateTime;

    private Integer status;

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

    public Integer getExecuteSelect() {
        return executeSelect;
    }

    public void setExecuteSelect(Integer executeSelect) {
        this.executeSelect = executeSelect;
    }

    public Integer getExecuteRule() {
        return executeRule;
    }

    public void setExecuteRule(Integer executeRule) {
        this.executeRule = executeRule;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
