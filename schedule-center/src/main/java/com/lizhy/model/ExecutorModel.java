package com.lizhy.model;

import com.lizhy.BaseObject;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 执行集齐
 * Created by lizhiyang on 2017-02-06 14:32.
 */
public class ExecutorModel extends BaseObject {

    private static final long serialVersionUID = -5142705388166989841L;

    private Long exeId;

    @NotNull(message = "jobId is null")
    private Long jobId;

    @NotNull(message = "exeName is null")
    @NotEmpty(message = "exeName is empty")
    private String exeName;

    @NotNull(message = "exeUrl is null")
    @NotEmpty(message = "exeUrl is empty")
    @Pattern(regexp = "^(http://|https://).*", message = "exeUrl is illegal")
    private String exeUrl;

    @NotNull(message = "exeInterface is null")
    @NotEmpty(message = "exeInterface is empty")
    private String exeInterface;

    private Integer status;

    @NotNull(message = "effectiveTime is illegal")
    private Long effectiveTime;

    private Long createTime;

    private Long updateTime;

    public Long getExeId() {
        return exeId;
    }

    public void setExeId(Long exeId) {
        this.exeId = exeId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getExeName() {
        return exeName;
    }

    public void setExeName(String exeName) {
        this.exeName = exeName;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Long effectiveTime) {
        this.effectiveTime = effectiveTime;
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
}
