package com.lizhy.validator;

import com.lizhy.BaseObject;

/**
 * 参数校验结果
 * Created by lizhiyang on 2017-06-22 16:54.
 */
public class ValidateResult extends BaseObject {
    private boolean result = false; //校验结果

    private String[] errMsg; //错误信息

    public ValidateResult(boolean result, String[] errMsg) {
        this.result = result;
        this.errMsg = errMsg;
    }


    public boolean isSuccess() {
        return result;
    }

    public String getErrMsg() {
        StringBuilder builder = new StringBuilder(32);
        if(errMsg != null) {
            for(String msg : errMsg) {
                builder.append(msg).append(";");
            }
        }
        return builder.toString();
    }

}
