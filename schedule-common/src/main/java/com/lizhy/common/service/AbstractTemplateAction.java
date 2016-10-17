package com.lizhy.common.service;

import com.lizhy.common.CallResult;

/**
 * Created by lizhiyang on 2016/10/17.
 */
public abstract class AbstractTemplateAction<T> implements TemplateAction<T> {
    public CallResult<T> checkParams() {
        return CallResult.success();
    }

    public CallResult<T> chechBiz() {
        return CallResult.success();
    }

    public void finishUp(CallResult<T> callResult) {

    }
}
