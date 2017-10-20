package com.lizhy.test;

import com.lizhy.common.CallResult;
import com.lizhy.model.ExecutorModel;
import com.lizhy.service.ExecutorService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * NOTE:
 *
 * @author lizhiyang
 * @Date 2017-10-20 14:20
 */
public class ExecutorServiceImplTest extends BaseSpringTest {
    @Autowired
    private ExecutorService executorService;

    @Test
    public void testList() {
        CallResult result = executorService.getExecutorList(1L);
        System.out.println(result);
    }

    @Test
    public void testSave() {
        ExecutorModel model = new ExecutorModel();
        model.setJobId(1L);
        model.setExeName("机器1");
        model.setExeUrl("httdp://sdf");
        model.setExeInterface("test");
        model.setEffectiveTime(1234567L);
        CallResult result = executorService.saveExecutor(model);
        System.out.println(result);
    }
}
