package com.lizhy.enu;

/**
 * NOTE: 时间规则状态
 *
 * @author lizhiyang
 * @Date 2017-10-20 14:26
 */
public enum TimerStatusEnum {
    INVALID(0, "无效"),
    VALID(1, "有效"),
    FUTURE(2, "未来生效"),
    ;

    int code;
    String msg;
    TimerStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }
}
