package com.lizhy.common.enu;
/**
 * 执行状态状态码
 *
 */
public enum ReturnStatusEnum {

	/** 0 未执行 **/
	STATUS_NOTEXE(0,"未执行"),
	/** 1 执行成功 **/
	STATUS_SUCCESS(1,"执行成功"),
	/** 2 执行失败 **/
	STATUS_FAILEXE(2,"执行失败");

	private ReturnStatusEnum(int code, String describe){
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
