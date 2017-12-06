package com.tayyarah.esmart.bus.util;

import org.apache.log4j.Logger;

import com.tayyarah.apiproviderconfig.entity.ApiProviderEtravelSmartConfig;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.util.ApiProviderServiceImpl;

public class EsmartBusConfig {
	static final Logger logger = Logger.getLogger(EsmartBusConfig.class);
	public static ApiProviderEtravelSmartConfig apiProviderEtravelSmartConfig = null;
	public static EsmartBusConfig esmartBusConfig =new EsmartBusConfig();
	public static final String DEFAULT_CURRENCY = "INR";
	private boolean isEnabled;
	private boolean isTest;
	private String url;
	private String username;
	private String password;
	
	public static EsmartBusConfig GetEsmartBusConfig(String app_key) {      
		try{
			AppKeyVo appKeyVo=new AppKeyVo();
			String appkey[]= app_key.split("-");
			if(appkey!=null && appkey.length>0){
			appKeyVo.setConfigId(Integer.parseInt(appkey[0]));
			appKeyVo.setCompanyId(Integer.parseInt(appkey[1]));
			//appKeyVo.setAppKey(app_key);
			}
			apiProviderEtravelSmartConfig = ApiProviderServiceImpl.getApiProviderEtravelSmartConfig(appKeyVo);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		if(apiProviderEtravelSmartConfig != null){		
			esmartBusConfig.setUrl(apiProviderEtravelSmartConfig.getUrl());
			esmartBusConfig.setPassword(apiProviderEtravelSmartConfig.getBusPassword());
			esmartBusConfig.setUsername(apiProviderEtravelSmartConfig.getBusUserName());			
			esmartBusConfig.setTest(apiProviderEtravelSmartConfig.getEnvironment().equalsIgnoreCase("test")?true:false);
			esmartBusConfig.setEnabled(apiProviderEtravelSmartConfig.getActive());
		}
		else{
			esmartBusConfig.setEnabled(false);
		}	
		return esmartBusConfig;
	}

	public static ApiProviderEtravelSmartConfig getApiProviderEtravelSmartConfig() {
		return apiProviderEtravelSmartConfig;
	}

	public static EsmartBusConfig getEsmartBusConfig() {
		return esmartBusConfig;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public boolean isTest() {
		return isTest;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public static void setApiProviderEtravelSmartConfig(ApiProviderEtravelSmartConfig apiProviderEtravelSmartConfig) {
		EsmartBusConfig.apiProviderEtravelSmartConfig = apiProviderEtravelSmartConfig;
	}

	public static void setEsmartBusConfig(EsmartBusConfig esmartBusConfig) {
		EsmartBusConfig.esmartBusConfig = esmartBusConfig;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public void setTest(boolean isTest) {
		this.isTest = isTest;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public static final String METHOD_STATIONS = "getStations";
	public static final String METHOD_AVAILABLEBUSES = "getAvailableBuses";
	public static final String METHOD_BUSLAYOUT = "getBusLayout";
	public static final String METHOD_BLOCKTICKET = "blockTicket";
	public static final String METHOD_RTCUPDATEDFARE = "getRtcUpdatedFare";
	public static final String METHOD_SEATBOOKING = "seatBooking";
	public static final String METHOD_GETICKETBYNUMBER = "getTicketByETSTNumber";
	public static final String METHOD_CANCELTICKETCONFIRMATION = "cancelTicketConfirmation";
	public static final String METHOD_CANCELTICKET = "cancelTicket";
	public static final String METHOD_MYBALANCE = "getMyPlanAndBalance";
	
}
