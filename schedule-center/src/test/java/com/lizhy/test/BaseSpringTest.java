package com.lizhy.test;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * Created by lizhiyang on 2016/10/17.
 */
@ContextConfiguration(locations = {"classpath:jobschedule-root-bean-test.xml"})
public class BaseSpringTest extends AbstractJUnit4SpringContextTests {
}
