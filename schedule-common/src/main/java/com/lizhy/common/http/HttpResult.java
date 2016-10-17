package com.lizhy.common.http;

import com.lizhy.common.BaseDO;

/**
 * Created by lizhiyang on 2016/10/17.
 */
public class HttpResult extends BaseDO {
    private boolean isSuccess;
    private int statusCode;
    private String responseTxt;

    public HttpResult(boolean isSuccess, int statusCode, String responseTxt) {
        this.isSuccess = isSuccess;
        this.statusCode = statusCode;
        this.responseTxt = responseTxt;
    }

    public boolean isSuccess() {

        return isSuccess;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseTxt() {
        return responseTxt;
    }
}
