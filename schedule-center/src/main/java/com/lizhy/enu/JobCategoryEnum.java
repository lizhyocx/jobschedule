package com.lizhy.enu;

/**
 * NOTE:
 *
 * @author lizhiyang
 * @Date 2017-10-27 10:45
 */
public enum JobCategoryEnum {
    SIMPLE("simple", "普通定时任务"),
    CRON("cron", "cron定时任务")
    ;

    String type;
    String msg;

    JobCategoryEnum(String type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public String getType() {
        return type;
    }
}
