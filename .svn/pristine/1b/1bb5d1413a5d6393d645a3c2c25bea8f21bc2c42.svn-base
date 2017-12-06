/**
@Author ilyas
16-12-2015 
TayyarahConfig.java
 */
/**
 * 
 */
package com.tayyarah.apiconfig.model;

import java.io.Serializable;


import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LintasConfig implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static LintasConfig lintasConfig ;
	public static final String DEFAULT_CURRENCY = "MYR";
	private boolean isEnabled;
	private boolean isTest;
	private String paymode;
	private String app_key ;
	private String url ; 
	private String userid ;  
	private String username;

	private LintasConfig(){

	}
	public static LintasConfig GetLintasConfig() {
		String confFile = "APIConfiguration.xml";
		ConfigurableApplicationContext context
		= new ClassPathXmlApplicationContext(confFile);
		lintasConfig = (LintasConfig) context.getBean("lintasConfig");		
		context.close();
		return lintasConfig;
	}

	public String getApp_key() {
		return app_key;
	}	
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public boolean isTest() {
		return isTest;
	}
	public void setIsTest(boolean isTest) {
		this.isTest = isTest;
	}
	public void setApp_key(String app_key) {
		this.app_key = app_key;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPaymode() {
		return paymode;
	}
	public void setPaymode(String paymode) {
		this.paymode = paymode;
	}
}