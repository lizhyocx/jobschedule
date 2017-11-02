package com.lizhy.job;

import com.lizhy.client.AbstractJobRunnable;
import com.lizhy.client.util.ExecutorManager;
import org.springframework.stereotype.Service;

/**
 * NOTE: 客户端测试
 *
 * @author lizhiyang
 * @Date 2017-10-30 14:18
 */
@Service("clientTestJob")
public class ClientTestJob extends AbstractJobRunnable {
    @Override
    public void run() {
        System.out.println("开始执行："+logId);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("执行结束："+logId);
        ExecutorManager.callBack(logId, "execute success", 1);
    }
}
