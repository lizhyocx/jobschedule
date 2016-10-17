/*
 * Copyright (c) 2014 All Rights Reserved.
 */
package com.lizhy.auto.model;
import com.lizhy.common.BaseDO;
// auto generated imports

/**
 * A data object class directly models database table <tt>user</tt>.
 *
 * This file is generated by <tt>ibatis-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>ibatis</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/user.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>ibatis-dalgen</tt> 
 * to generate this file.
 *
 * @author dalgen
 */
public class UserDO extends BaseDO{
    private static final long serialVersionUID = 741231858441822688L;

    //========== properties ==========

	/**
	 * This property corresponds to db column <tt>id</tt>.
	 */

	private Integer id;


	/**
	 * This property corresponds to db column <tt>name</tt>.
	 */

	private String name;


	/**
	 * This property corresponds to db column <tt>age</tt>.
	 */

	private Integer age;


	/**
	 * This property corresponds to db column <tt>password</tt>.
	 */

	private String password;


    //========== getters and setters ==========

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
	public Integer getId() {
		return id;
	}
	
	/**
	 * Setter method for property <tt>id</tt>.
	 * 
	 * @param id value to be assigned to property id
     */
	public void setId(Integer id) {
		this.id = id;
	}

    /**
     * Getter method for property <tt>name</tt>.
     *
     * @return property value of name
     */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter method for property <tt>name</tt>.
	 * 
	 * @param name value to be assigned to property name
     */
	public void setName(String name) {
		this.name = name;
	}

    /**
     * Getter method for property <tt>age</tt>.
     *
     * @return property value of age
     */
	public Integer getAge() {
		return age;
	}
	
	/**
	 * Setter method for property <tt>age</tt>.
	 * 
	 * @param age value to be assigned to property age
     */
	public void setAge(Integer age) {
		this.age = age;
	}

    /**
     * Getter method for property <tt>password</tt>.
     *
     * @return property value of password
     */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Setter method for property <tt>password</tt>.
	 * 
	 * @param password value to be assigned to property password
     */
	public void setPassword(String password) {
		this.password = password;
	}
}
