package com.lizhy.common.service;

import com.lizhy.common.CallResult;

/**
 * Created by lizhiyang on 2016/10/17.
 */
public interface ServiceTemplate {
    <T> CallResult<T> exeInTranscation(TemplateAction<T> action) throws Exception;

    <T> CallResult<T> exeOnMaster(TemplateAction<T> action) throws Exception;

    <T> CallResult<T> exeOnSlave(TemplateAction<T> action) throws Exception;
}
