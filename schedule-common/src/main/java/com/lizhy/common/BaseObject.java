package com.lizhy.common;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

/**
 * Created by lizhiyang on 2016/10/17.
 */
public class BaseObject implements Serializable {
    @Override
    public String toString() {
        try {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        } catch (Exception e) {
            return super.toString();
        }
    }
}
