/**
 * Yunzongnet.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.lizhy.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 
 * @author yanxiaobo
 * @version $Id: InEnum.java, v 0.1 2016年5月10日 上午10:57:00 yanxiaobo Exp $
 */
@Documented
@Target({
    ElementType.FIELD, 
    ElementType.METHOD, 
    ElementType.ANNOTATION_TYPE, 
    ElementType.CONSTRUCTOR, 
    ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {InValidator.class, ArrayInValidator.class})
public @interface In {
    
    String message() default "值不在给定范围内";  
    
    String[] values() default {};
    
    Class<?>[] groups() default {};  
    
    Class<? extends Payload>[] payload() default {};  
}
