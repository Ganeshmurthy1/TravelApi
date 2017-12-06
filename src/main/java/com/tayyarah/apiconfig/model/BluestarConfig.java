/**
@Author ilyas
5-10-2015 
BluestarConfig.java
 */
/**
 * 
 */
package com.tayyarah.apiconfig.model;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.apiproviderconfig.entity.ApiProviderBluestarConfig;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.util.ApiProviderServiceImpl;


public class BluestarConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired()
	static  ApiProviderServiceImpl apiProviderServiceImpl;	
	public static ApiProviderBluestarConfig apiProviderBluestarConfig=null;
	public static BluestarConfig bluestarConfig;
	public static final String DEFAULT_CURRENCY = "INR";
	private String url;
	private String interface_Code;
	private String interface_Auth_Key;
	private String Agent_Code;
	private String transaction_Pwd;
	private boolean isEnabled;
	private boolean isTest;

	public static BluestarConfig GetBluestarConfig(AppKeyVo appKeyVo) {	
		bluestarConfig = new BluestarConfig();
		try{
			apiProviderBluestarConfig = ApiProviderServiceImpl.businessLogicforBluestarLiveOrTest(appKeyVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(apiProviderBluestarConfig!=null){
			bluestarConfig.setUrl(apiProviderBluestarConfig.getHostUrl());
			bluestarConfig.setTransaction_Pwd(apiProviderBluestarConfig.getPassword());
			bluestarConfig.setInterface_Code(apiProviderBluestarConfig.getInterfaceCode());
			bluestarConfig.setInterface_Auth_Key(apiProviderBluestarConfig.getInterfaceAuthKey());
			bluestarConfig.setAgent_Code(apiProviderBluestarConfig.getAgentCode());
			bluestarConfig.setIsTest(apiProviderBluestarConfig.getEnvironment().equalsIgnoreCase("test")?true:false);
			bluestarConfig.setIsEnabled(apiProviderBluestarConfig.isActive());
		}
		else{
			bluestarConfig.setIsEnabled(false);
		}
		return bluestarConfig;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(boolean isEnabled) {// changed from setEnabled to
		// setIsEnabled
		this.isEnabled = isEnabled;
	}
	public boolean isTest() {
		return isTest;
	}

	public void setIsTest(boolean isTest) {
		this.isTest = isTest;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getInterface_Code() {
		return interface_Code;
	}

	public void setInterface_Code(String interface_Code) {
		this.interface_Code = interface_Code;
	}

	public String getInterface_Auth_Key() {
		return interface_Auth_Key;
	}

	public void setInterface_Auth_Key(String interface_Auth_Key) {
		this.interface_Auth_Key = interface_Auth_Key;
	}

	public String getAgent_Code() {
		return Agent_Code;
	}

	public void setAgent_Code(String agent_Code) {
		Agent_Code = agent_Code;
	}

	public String getTransaction_Pwd() {
		return transaction_Pwd;
	}

	public void setTransaction_Pwd(String transaction_Pwd) {
		this.transaction_Pwd = transaction_Pwd;
	}

}
