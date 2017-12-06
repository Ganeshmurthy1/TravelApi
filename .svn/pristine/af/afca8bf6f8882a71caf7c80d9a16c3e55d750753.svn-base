
package com.tayyarah.apiconfig.model;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class TravelportConfig{

	public static final String DEFAULT_CURRENY = "MYR";
	public static TravelportConfig travelportConfig ;
	private String url ;
	private String environment;
	private String targetBranch; 
	private String username; 
	private String password; 
	private String pcc;
	private boolean isTest;
	private boolean isEnabled;
	
	
	public static TravelportConfig GetTravelportConfig() {
		String confFile = "APIConfiguration.xml";
		ConfigurableApplicationContext context
		= new ClassPathXmlApplicationContext(confFile);
		travelportConfig = (TravelportConfig) context.getBean("travelportConfig");		
		context.close();
		return travelportConfig;
	}	
	public String getPcc() {
		return pcc;
	}	
	public boolean isEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(boolean isEnabled) {// changed from setEnabled to setIsEnabled
		this.isEnabled = isEnabled;
	}
	public boolean isTest() {
		return isTest;
	}
	public void setIsTest(boolean isTest) {
		this.isTest = isTest;
	}
	public void setPcc(String pcc) {
		this.pcc = pcc;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTargetBranch() {
		return targetBranch;
	}
	public void setTargetBranch(String targetBranch) {
		this.targetBranch = targetBranch;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
}
