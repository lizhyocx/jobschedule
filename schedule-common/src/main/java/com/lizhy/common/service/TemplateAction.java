package com.lizhy.common.service;

import com.lizhy.common.CallResult;

/**
 * Created by lizhiyang on 2016/10/17.
 */
public interface TemplateAction<T> {
    /**
     * 检查参数合法性
     * @return
     */
    CallResult<T> checkParam();

    /**
     * 检查业务，执行查询操作
     * @return
     */
    CallResult<T> checkBiz();

    /**
     * 业务执行
     * @return
     */
    CallResult<T> doAction();

    /**
     * 业务执行成功后的清理工作
     * @param callResult
     */
    void finishUp(CallResult<T> callResult);
}
