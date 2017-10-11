package com.lizhy.client;

/**
 * 兼容对象，包路径不可移动
 *
 */
public abstract class AbstractJobRunnable implements JobRunnable {
	protected String runResult = "";
	protected boolean isStop = false;

	protected long logId;

	public void stop() {
		isStop = true;
	}

	public String getRunResult() {
		return runResult;
	}

	public void setLogId(long logId) {
		this.logId = logId;
	}
	
	public long getLogId(){
		return logId;
	}

}
