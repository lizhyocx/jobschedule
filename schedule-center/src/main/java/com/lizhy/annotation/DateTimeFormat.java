/**
 * Yunzongnet.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.lizhy.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 字符串时间日期格式校验
 * 
 * @author yanxiaobo
 * @version $Id: DateTime.java, v 0.1 2016年3月9日 下午2:58:36 yanxiaobo Exp $
 */
@Documented
@Target({
    ElementType.FIELD, 
    ElementType.METHOD, 
    ElementType.ANNOTATION_TYPE, 
    ElementType.CONSTRUCTOR, 
    ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateTimeFormatValidator.class)
public @interface DateTimeFormat {

    String format() default "yyyy-MM-dd HH:mm:ss";
    
    String message() default "日期格式错误";  
    
    Class<?>[] groups() default {};  
     
    Class<? extends Payload>[] payload() default {};  
}
