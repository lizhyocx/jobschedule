package com.lizhy.common.datasource;

/**
 * Created by lizhiyang on 2016/10/17.
 */
public class DataSourceContextHolder {
    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDataSourceType(String type) {
        contextHolder.set(type);
    }

    public static String getDataSourceType() {
        return contextHolder.get();
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }

}
