package com.lizhy.client;

public interface JobRunnable extends Runnable {
	public String getRunResult();

	public void stop();

	public void setLogId(long logId);
	public long getLogId();
}
