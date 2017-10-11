package com.lizhy.common.enu;

/**
 * 执行机器选择枚举类
 * Created by lizhiyang on 2017-02-06 15:33.
 */
public enum ExecuteSelectEnum {
    ANY(1, "任意一台"),
    ALL(2, "所有机器"),
    ;

    int code;
    String msg;

    ExecuteSelectEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }
}
