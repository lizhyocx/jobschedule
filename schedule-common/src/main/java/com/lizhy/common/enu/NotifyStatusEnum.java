package com.lizhy.common.enu;
/**
 * 通知状态状态码
 *
 */
public enum NotifyStatusEnum {

	/** 0 未通知 **/
	STATUS_NOSTART(0,"未通知"),
	/** 1 通知成功 **/
	STATUS_NOTIFY(1,"通知成功"),
	/** 2 通知失败 **/
	STATUS_FAIL(2,"通知失败"),
	/** 3 已结束 **/
	STATUS_CLOSE(3,"已结束"),
	/** 10 通知成功，上次执行未完成 **/
	STATUS_EXEING(10,"通知成功，上次执行未完成");
	private NotifyStatusEnum(int code, String describe){
		this.code = code;
		this.describe = describe;
	}
	
	private int code;
	private String describe;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	
}
