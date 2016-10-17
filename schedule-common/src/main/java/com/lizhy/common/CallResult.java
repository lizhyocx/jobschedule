package com.lizhy.common;

/**
 * Created by lizhiyang on 2016/10/17.
 */
public class CallResult<T> extends BaseDO {
    private static final long serialVersionUID = 3561427752567079986L;
    private static final int CODE_SUCCESS = 1;
    private static final int CODE_FAILURE = -1;
    private boolean success;
    private int code;
    private String msg;
    private T resultObject;

    public CallResult(boolean success, int code, String msg, T resultObject) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.resultObject = resultObject;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public static <T> CallResult<T> success() {
        return new CallResult<>(true, CODE_SUCCESS, "default success", null);
    }

    public static <T> CallResult<T> success(T resultObject) {
        return new CallResult<>(true, CODE_SUCCESS, "default success", resultObject);
    }

    public static <T> CallResult<T> failure() {
        return new CallResult<>(false, CODE_FAILURE, "default failure", null);
    }

    public static <T> CallResult<T> failure(String msg) {
        return new CallResult<>(false, CODE_FAILURE, msg, null);
    }

    public static <T> CallResult<T> failure(int code, String msg) {
        return new CallResult<>(false, code, msg, null);
    }
}
