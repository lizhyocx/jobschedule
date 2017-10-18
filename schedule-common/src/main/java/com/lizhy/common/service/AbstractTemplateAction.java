package com.lizhy.common.service;

import com.lizhy.common.CallResult;

/**
 * Created by lizhiyang on 2016/10/17.
 */
public abstract class AbstractTemplateAction<T> implements TemplateAction<T> {
    @Override
    public CallResult<T> checkParam() {
        return CallResult.success();
    }

    @Override
    public CallResult<T> checkBiz() {
        return CallResult.success();
    }

    @Override
    public void finishUp(CallResult<T> callResult) {

    }
}
