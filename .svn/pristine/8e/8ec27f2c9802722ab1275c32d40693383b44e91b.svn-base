package com.tayyarah.apiconfig.model;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.apiproviderconfig.entity.ApiProviderTrawellTagConfig;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.util.ApiProviderServiceImpl;




public class TrawellTagConfig {
	@Autowired()
	static  ApiProviderServiceImpl apiProviderServiceImpl;
	static final Logger logger = Logger.getLogger(TrawellTagConfig.class);
	public static ApiProviderTrawellTagConfig apiProviderTrawellTagConfig = null;
	public static TrawellTagConfig trawellTagConfig;
	private boolean isEnabled;
	private boolean isTest;
	private String sign;
	private String branchSign;
	private String userName;
	private String referance;
	private String url;
	public static final String DEFAULT_CURRENCY = "INR";
	public static final String CREATE_POLICY = "CreatePolicy.aspx?WSDL";
	
	public static TrawellTagConfig GetTrawellTagConfig(AppKeyVo appKeyVo) {      
		try{
			trawellTagConfig = new TrawellTagConfig();
			apiProviderTrawellTagConfig = ApiProviderServiceImpl.getApiProviderTrawellTagConfig(appKeyVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		if(apiProviderTrawellTagConfig!=null){			
			trawellTagConfig.setUrl(apiProviderTrawellTagConfig.getUrl());
			trawellTagConfig.setBranchSign(apiProviderTrawellTagConfig.getBranchSign());
			trawellTagConfig.setUserName(apiProviderTrawellTagConfig.getInsuranceUserName());			
			trawellTagConfig.setSign(apiProviderTrawellTagConfig.getSign());
			trawellTagConfig.setReferance(apiProviderTrawellTagConfig.getReference());
			trawellTagConfig.setTest(apiProviderTrawellTagConfig.getEnvironment().equalsIgnoreCase("test")?true:false);
			trawellTagConfig.setEnabled(apiProviderTrawellTagConfig.getActive());
		}
		else{
			trawellTagConfig.setEnabled(false);
		}
		//logger.info("tboFlightConfig  : " +tboFlightConfig.toString());
		return trawellTagConfig;
	}

	public static ApiProviderTrawellTagConfig getApiProviderTrawellTagConfig() {
		return apiProviderTrawellTagConfig;
	}

	public static TrawellTagConfig getTrawellTagConfig() {
		return trawellTagConfig;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public boolean isTest() {
		return isTest;
	}

	public String getSign() {
		return sign;
	}

	public String getBranchSign() {
		return branchSign;
	}

	public String getUserName() {
		return userName;
	}

	public String getReferance() {
		return referance;
	}

	public String getUrl() {
		return url;
	}

	public static void setApiProviderTrawellTagConfig(ApiProviderTrawellTagConfig apiProviderTrawellTagConfig) {
		TrawellTagConfig.apiProviderTrawellTagConfig = apiProviderTrawellTagConfig;
	}

	public static void setTrawellTagConfig(TrawellTagConfig trawellTagConfig) {
		TrawellTagConfig.trawellTagConfig = trawellTagConfig;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public void setTest(boolean isTest) {
		this.isTest = isTest;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public void setBranchSign(String branchSign) {
		this.branchSign = branchSign;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setReferance(String referance) {
		this.referance = referance;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
