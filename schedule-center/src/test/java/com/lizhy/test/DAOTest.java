package com.lizhy.test;

import com.lizhy.auto.dao.ScheduleMonitorDAO;
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

    @Test
    public void test() {
        Map<String, Object> param = new HashMap<>();
        param.put("offset", 5);
        param.put("pageSize", 5);
        List<ScheduleMonitorDO> list = scheduleMonitorDAO.selectScheduleList(param);
        System.out.println(list);
    }
}
