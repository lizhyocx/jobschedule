package com.lizhy.enu;

/**
 * NOTE: 定时任务状态枚举
 *
 * @author lizhiyang
 * @Date 2017-10-19 11:08
 */
public enum JobStatusEnum {
    INVALID(0, "无效"),
    VALID(1, "启用")
    ;
    private int code;
    private String msg;

    JobStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }
}
