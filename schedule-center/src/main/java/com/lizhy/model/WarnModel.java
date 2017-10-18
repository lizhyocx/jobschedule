package com.lizhy.model;

import com.lizhy.BaseObject;

/**
 * 告警人
 * Created by lizhiyang on 2017-02-06 14:35.
 */
public class WarnModel extends BaseObject {
    private static final long serialVersionUID = 3926999519994941143L;
    private long jobId;
    private String warnName;
    private String warnEmail;
    private String warnPhone;

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public String getWarnName() {
        return warnName;
    }

    public void setWarnName(String warnName) {
        this.warnName = warnName;
    }

    public String getWarnEmail() {
        return warnEmail;
    }

    public void setWarnEmail(String warnEmail) {
        this.warnEmail = warnEmail;
    }

    public String getWarnPhone() {
        return warnPhone;
    }

    public void setWarnPhone(String warnPhone) {
        this.warnPhone = warnPhone;
    }
}
