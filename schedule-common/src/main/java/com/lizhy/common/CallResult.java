package com.lizhy.common;
/**
 * 结果对象
 * （既作为“服务模板返回的结果对象”，也可以作为“接口返回给调用方的最终结果对象”）
 * @author WangChao
 * @date 2014-9-25
 */

public class CallResult<T> extends BaseObject {
    private static final long serialVersionUID = -6429389007061684196L;

    public static final int CODE_FAILURE = -1;
    public static final int CODE_SUCCESS = 1;

    private final boolean success;//操作是否成功
    private final int code;	//错误码
    private final String msg;	//消息
    private final T resultObject;	//业务对象

    private String stringValue;	//toString的懒加载缓存（无需强制voletile）

    public CallResult(boolean isSuccess, int code, String msg, T resultObject){
        this.success = isSuccess;
        this.code = code;
        this.msg = msg;
        this.resultObject = resultObject;
    }

	/* ----------------------------- 常用的工具方法 ------------------------------- */

    /**
     * 成功
     * @author WangChao
     * @return
     */
    public static <T> CallResult<T> success(){
        return new CallResult<T>(true,CODE_SUCCESS,"default success",null);
    }

    /**
     * 成功，指定业务业务对象
     * @author WangChao
     * @param resultObject
     * @return
     */
    public static <T> CallResult<T> success(T resultObject){
        return new CallResult<T>(true,CODE_SUCCESS,"default success",resultObject);
    }

    /**
     * 成功，指定结果码、结果消息，业务对象
     * @author WangChao
     * @param code
     * @param msg
     * @param resultObject
     * @return
     */
    public static <T> CallResult<T> success(int code, String msg, T resultObject){
        return new CallResult<T>(true,code,msg,resultObject);
    }

    /**
     * 失败
     * @return
     */
    public static <T> CallResult<T> failure(){
        return new CallResult<T>(false,CODE_FAILURE,"default failure",null);
    }

    /**
     * 失败，指定消息
     * @return
     */
    public static <T> CallResult<T> failure(String msg){
        return new CallResult<T>(false,CODE_FAILURE,msg,null);
    }

    /**
     * 失败，指定结果码和消息
     * @author WangChao
     * @param code
     * @param msg
     * @return
     */
    public static <T> CallResult<T> failure(int code, String msg){
        return new CallResult<T>(false,code,msg,null);
    }

    @Override
    public String toString(){
        String result = stringValue;
        if(result != null){
            return result;
        }
        result = new StringBuilder()
                .append('{')
                .append("\"success\":").append(success).append(',')
                .append("\"code\":").append(code).append(',')
                .append("\"msg\":\"").append(msg).append("\",")
                .append("\"resultObject\":").append(resultObject)
                .append('}')
                .toString();
        stringValue = result;
        return result;
    }

	/* -----------------------------getters&setters------------------------------- */

    /**
     * 判断是否拿到了结果数据
     * @author WangChao
     * @return
     */
    public boolean hasData(){
        return resultObject!=null;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getResultObject() {
        return resultObject;
    }
}
