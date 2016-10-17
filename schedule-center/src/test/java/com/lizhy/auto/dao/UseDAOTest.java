package com.lizhy.auto.dao;

import com.lizhy.auto.model.UserDO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lizhiyang on 2016/10/17.
 */
public class UseDAOTest extends BaseSpringTest {
    @Autowired
    private UserDAO userDAO;
    @Test
    public void test() {
        UserDO userDO = userDAO.selectById(1);
        System.out.println(userDO);
    }
}
