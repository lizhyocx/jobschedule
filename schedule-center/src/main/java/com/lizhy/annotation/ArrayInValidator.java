/**
 * Yunzongnet.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.lizhy.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 
 * @author yanxiaobo
 * @version $Id: ArrayInValidator.java, v 0.1 2016年5月10日 下午3:39:17 yanxiaobo Exp $
 */
public class ArrayInValidator implements ConstraintValidator<In, String[]>{

    private String[] checkedValues;
    
    @Override
    public void initialize(In in) {
        checkedValues = in.values();
    }

    @Override
    public boolean isValid(String[] values, ConstraintValidatorContext context) {
        if (null == values || values.length == 0 || checkedValues.length == 0) {
            return false;
        }
        
        for (String value : values) {
            if (!contain(checkedValues, value)) {
                return false;
            }
        }
        return true;
    }
    
    //判断数组arrays中是否包含str
    private boolean contain(String[] arrays, String str) {
        for (String checked : arrays) {
            if (str.equals(checked)) {
                return true;
            }
        }
        return false;
    }
}
