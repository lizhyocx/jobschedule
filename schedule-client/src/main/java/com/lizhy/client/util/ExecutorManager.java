package com.lizhy.client.util;

import com.lizhy.client.JobRunnable;
import com.lizhy.client.config.ScheduleConfig;
import com.lizhy.common.http.HttpClientManager;
import com.lizhy.common.http.HttpResult;
import com.lizhy.common.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExecutorManager {
	private static Map<String, Thread> threadMap = new HashMap<String, Thread>();
	private static Map<String, JobRunnable> taskMap = new HashMap<String, JobRunnable>();
	private static final  Logger logger = LoggerFactory.getLogger(ExecutorManager.class);
	
	public static Result queryJobRuningStatus(String jobInterface, long logId){
		try {
			if(taskMap.containsKey(jobInterface)){
				if(null != threadMap.get(jobInterface) && threadMap.get(jobInterface).isAlive()){
					return new Result(1,taskMap.get(jobInterface).getRunResult());
				}
			}
			//任务不存在
			return new Result(2,"Job not exists,logId:"+logId);
		} catch (Exception e) {
			String msg = e.toString();
			if(msg.getBytes().length>100){
				msg = msg.substring(0,100);
			}
			return new Result(2,"system exception,logId:"+logId+",cause="+msg);
		}
	}
	/**
	 * 运行任务
	 * 
	 * @param taskInterface
	 *            任务接口名
	 * @return 0 失败－任务正在运行中 1 成功 －2 失败－任务不存在 －2 失败－程序异常
	 */
	public static Result runJob(String jobInterface, long logId) {
		try {
			Thread thread = threadMap.get(jobInterface);
			if (thread != null && thread.isAlive()) {
				//任务正在执行
				return new Result(10,"Job is running,logId:"+logId);
			}

			JobRunnable task = null;
			
			Object obj = SpringContextUtil.getBean(jobInterface);
			if (obj instanceof JobRunnable) {
				task = (JobRunnable) obj;
				task.setLogId(logId);
				taskMap.put(jobInterface, task);

				thread = new Thread(task, jobInterface);
				threadMap.put(jobInterface, thread);
				thread.start();
				return new Result(1,"Job started,logId:"+logId);
			} else {
				return new Result(2,jobInterface+" is not implements JobRunnable,logId="+logId);
			}

		} catch (Exception e) {
			logger.error("runJob "+jobInterface+" exception",e);
			String msg = e.toString();
			if(msg.getBytes().length>100){
				msg = msg.substring(0,100);
			}
			return new Result(2,"system exception,logId:"+logId+",cause="+msg);
		}
	}

	/**
	 * 停止任务
	 * 
	 * @param taskInterface
	 *            任务接口名
	 * @return 1成功 其它失败
	 */
	@SuppressWarnings({ "deprecation" })
	public static Result stopJob(String jobInterface,long logId) {
		JobRunnable task = taskMap.get(jobInterface);
		if (task == null || task.getLogId() != logId) {
			//没有此任务在执行
			return new Result(2,"this job not running,logId:"+logId);
		}
		task.stop();
		Thread thread = threadMap.get(jobInterface);
		if (thread != null && thread.isAlive()) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
			}
		}

		if (thread != null && thread.isAlive()) {
			thread.interrupt();
			thread.stop();
			thread = null;
			task = null;
		}
		taskMap.remove(jobInterface);
		threadMap.remove(jobInterface);
		return new Result(1,"stopped,logId:"+logId);
	}
	/**
	 * 中断任务
	 * @param jobInterface
	 * 			任务接口名
	 * @param logId
	 * 			任务logId
	 * @return
	 */
	public static Result interruptJob(String jobInterface,long logId){
		JobRunnable task = taskMap.get(jobInterface);
		if (task == null || task.getLogId() != logId) {
			//没有此任务在执行
			return new Result(2,"this job not running,logId:"+logId);
		}
		task.stop();
		Thread thread = threadMap.get(jobInterface);

		if (thread != null && thread.isAlive()) {
			thread.interrupt();
			thread = null;
			task = null;
		}
		taskMap.remove(jobInterface);
		threadMap.remove(jobInterface);
		return new Result(1,"interrupted,logId:"+logId);
	}

	public static boolean stopAllTask() {
		return true;
	}

	public static void callBack(long logId, String retMsg, int code) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("logId", logId + "");
		//数据库的最大长度为125
		if(retMsg.length()>63){
			retMsg = retMsg.substring(0, 64);
		}
		param.put("retMsg", retMsg);
		param.put("code", code + "");
		param.put("finished", System.currentTimeMillis() + "");
		ScheduleConfig config = (ScheduleConfig) SpringContextUtil.getBean("scheduleConfig");
		List<String> urls = config.getScheduleCenterUrls();
		if(!CollectionUtils.isEmpty(urls)) {
			for(String url : urls) {
				logger.info("ExecutorManager callback url :"+url+",param="+param);
				HttpResult res = HttpClientManager.executePost(url, param);
				if(res != null && res.isSuccess()) {
					logger.info("callBackRespone:"+res.getResponseTxt());
					break;
				} else {
					if(res == null) {
						logger.warn("callback "+ url + " fail, res is null");
					} else {
						logger.warn("callBack "+url+" fail, status code="+res.getStatusCode()+", responseTxt="+res.getResponseTxt());
					}
				}
			}
		}
	}

}
