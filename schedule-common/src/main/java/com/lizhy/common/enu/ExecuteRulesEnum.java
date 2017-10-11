package com.lizhy.common.enu;

/**
 * 执行规则枚举
 * Created by lizhiyang on 2017-02-06 15:37.
 */
public enum ExecuteRulesEnum {
    NOTIFY_ONCE(1, "只通知一次"),
    NOTIFY_SUCCESSL(2, "保证通知成功"),
    ;

    int code;
    String msg;

    ExecuteRulesEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }
}
