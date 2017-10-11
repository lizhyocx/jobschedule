package com.lizhy.client.util;

/**
 * 兼容对象，包路径不可移动
 *
 */
public class Result {
	private int code;
	private String msg;

	public Result(){
		
	}
	public Result(int code,String msg){
		this.code = code;
		this.msg = msg;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static void main(String[] args) {
		Result o = new Result();
		o.setCode(1);
		o.setMsg("成功");
		System.out.println(o.toString());
	}
}
