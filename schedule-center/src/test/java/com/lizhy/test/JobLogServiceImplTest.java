package com.lizhy.test;

import com.lizhy.service.JobLogService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * NOTE:
 *
 * @author lizhiyang
 * @Date 2017-11-26 15:55
 */
public class JobLogServiceImplTest extends BaseSpringTest {
    @Autowired
    private JobLogService jobLogService;

    @Test
    public void test() {
        System.out.println(jobLogService.getLogList(16L, 1, 1, 5));
    }
}
