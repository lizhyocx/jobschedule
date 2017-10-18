package com.lizhy.test;

import com.lizhy.common.CallResult;
import com.lizhy.model.JobInfoModel;
import com.lizhy.service.JobService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * NOTE:
 *
 * @author lizhiyang
 * @Date 2017-10-18 15:07
 */
public class JobServiceImplTest extends BaseSpringTest {
    @Autowired
    private JobService jobService;

    @Test
    public void testSave() {
        JobInfoModel model = new JobInfoModel();
        model.setJobName("测试");
        model.setTimeout(100);
        CallResult<Boolean> result = jobService.saveJob(model);
        System.out.println(result);
    }
}
