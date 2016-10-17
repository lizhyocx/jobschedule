package com.lizhy.common.service;

import com.lizhy.common.CallResult;
import com.lizhy.common.datasource.DataSourceContextHolder;
import com.lizhy.common.datasource.DataSourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Created by lizhiyang on 2016/10/17.
 */
public class ServiceTemplateImpl implements ServiceTemplate {
    private static final Logger logger = LoggerFactory.getLogger(ServiceTemplateImpl.class);
    protected TransactionTemplate transactionTemplate;
    @Override
    public <T> CallResult<T> exeInTranscation(final TemplateAction<T> action) {
        DataSourceContextHolder.setDataSourceType(DataSourceType.MASTER);
        CallResult<T> callResult = action.checkParam();
        if(callResult == null) {
            logger.warn("exeInTranscation null result when checkParam");
            return CallResult.failure("null result when checkParam");
        }
        if(!callResult.isSuccess()) {
            return callResult;
        }
        callResult = action.checkBiz();
        if(callResult == null) {
            logger.warn("exeInTranscation null result when checkBiz");
            return CallResult.failure("null result when checkBiz");
        }
        if(!callResult.isSuccess()) {
            return callResult;
        }
        callResult = (CallResult<T>) transactionTemplate.execute(new TransactionCallback() {
            @Override
            public Object doInTransaction(TransactionStatus status) {
                CallResult<T> callResult = action.doAction();
                if(callResult == null) {
                    logger.warn("exeInTransaction null result when doAction");
                    return CallResult.failure("null result when doAction");
                }
                if(!callResult.isSuccess()) {
                    status.setRollbackOnly();
                }
                return callResult;
            }
        });
        if(callResult.isSuccess()) {
            action.finishUp(callResult);
        }
        return callResult;
    }

    @Override
    public <T> CallResult<T> exeOnMaster(TemplateAction<T> action) throws Exception {
        DataSourceContextHolder.setDataSourceType(DataSourceType.MASTER);
        CallResult<T> callResult = action.checkParam();
        if(callResult == null) {
            logger.warn("exeOnMaster null result when checkParam");
            return CallResult.failure("null result when checkParam");
        }
        if(!callResult.isSuccess()) {
            return callResult;
        }
        callResult = action.doAction();
        if(callResult == null) {
            logger.warn("exeOnMaster null result when doAction");
            return CallResult.failure("null result when doAction");
        }
        if(!callResult.isSuccess()) {
            return callResult;
        }
        if(callResult.isSuccess()) {
            action.finishUp(callResult);
        }
        return callResult;
    }

    @Override
    public <T> CallResult<T> exeOnSlave(TemplateAction<T> action) {
        DataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE_1);
        CallResult<T> callResult = action.checkParam();
        if(callResult == null) {
            logger.warn("exeOnSlave null result when checkParam");
            return CallResult.failure("null result when checkParam");
        }
        if(!callResult.isSuccess()) {
            return callResult;
        }
        callResult = action.doAction();
        if(callResult == null) {
            logger.warn("exeOnSlave null result when doAction");
            return CallResult.failure("null result when doAction");
        }
        if(!callResult.isSuccess()) {
            return callResult;
        }
        if(callResult.isSuccess()) {
            action.finishUp(callResult);
        }
        return callResult;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }
}
