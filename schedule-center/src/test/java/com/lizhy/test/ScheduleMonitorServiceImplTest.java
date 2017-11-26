package com.lizhy.test;

import com.lizhy.common.CallResult;
import com.lizhy.service.ScheduleMonitorService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * NOTE:
 *
 * @author lizhiyang
 * @Date 2017-11-26 12:18
 */
public class ScheduleMonitorServiceImplTest extends BaseSpringTest {
    @Autowired
    private ScheduleMonitorService scheduleMonitorService;

    @Test
    public void testScheduleList() {
        CallResult result= scheduleMonitorService.getScheduleList(null, null, 2, 5);
        System.out.println(result);
    }
}
