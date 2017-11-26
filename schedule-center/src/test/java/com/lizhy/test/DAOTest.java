package com.lizhy.test;

import com.lizhy.auto.dao.ScheduleJobLogDAO;
import com.lizhy.auto.dao.ScheduleMonitorDAO;
import com.lizhy.auto.model.ScheduleJobLogDO;
import com.lizhy.auto.model.ScheduleMonitorDO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * NOTE:
 *
 * @author lizhiyang
 * @Date 2017-11-26 11:48
 */
public class DAOTest extends BaseSpringTest {
    @Autowired
    private ScheduleMonitorDAO scheduleMonitorDAO;
    @Autowired
    private ScheduleJobLogDAO scheduleJobLogDAO;

    @Test
    public void test() {
        Map<String, Object> param = new HashMap<>();
        param.put("offset", 5);
        param.put("pageSize", 5);
        List<ScheduleMonitorDO> list = scheduleMonitorDAO.selectScheduleList(param);
        System.out.println(list);
    }

    @Test
    public void test1() {
        ScheduleJobLogDO record = new ScheduleJobLogDO();
        record.setJobId(1L);
        int n = scheduleJobLogDAO.insertSelective(record);
        System.out.println(record.getLogId());
    }
}
