/**
 * Yunzongnet.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.lizhy.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author yanxiaobo
 * @version $Id: DateTimeValidator.java, v 0.1 2016年3月9日 下午3:12:09 yanxiaobo Exp $
 */
public class DateTimeFormatValidator implements ConstraintValidator<DateTimeFormat, String> {

    private String format;
    
    @Override
    public void initialize(DateTimeFormat dt) {
        format = dt.format(); 
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (null == value) {
            return true;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(value);
        } catch (ParseException e) {
            //
        }
        if (null == date) {
            return false;
        }
        return true;
    }

}
