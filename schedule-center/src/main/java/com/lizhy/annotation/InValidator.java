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
 * @version $Id: InEnumValidator.java, v 0.1 2016年5月10日 上午10:58:17 yanxiaobo Exp $
 */
public class InValidator implements ConstraintValidator<In, String>{

    private String[] checkedValues;
    
    @Override
    public void initialize(In in) {
        checkedValues = in.values();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (null == value || checkedValues.length == 0) {
            return false;
        }
        
        for (String checkedValue : checkedValues) {
            if (value.equals(checkedValue)) {
                return true;
            }
        }
        return false;
    }

}
