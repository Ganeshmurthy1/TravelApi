/**
 * 
 */
package com.tayyarah.apiconfig.model;

import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.apiproviderconfig.entity.ApiProviderTayyarahConfig;
import com.tayyarah.common.util.ApiProviderServiceImpl;

/**
 * @author Manish Samrat
 * @createdAt 20-01-2017
 *
 */
public class TayyarahRepoConfig {

	@Autowired()
	static  ApiProviderServiceImpl apiProviderServiceImpl;
	public static ApiProviderTayyarahConfig apiProviderTayyarahConfig=null;
	public static TayyarahRepoConfig config  ;
	private boolean isEnabled;
	private boolean isTesting;
	private String authUrl;
	private String endPointUrl;
	private String userName;
	private String password; 
	private String propertyId; 
	private int id; 
	private String apiProviderName; 
	private String apiCurrency;
	

	public static TayyarahRepoConfig GetTayyarahHotelConfig() {
		config = new TayyarahRepoConfig();
		try{
			apiProviderTayyarahConfig = apiProviderServiceImpl.businnessLogicforTayyarahHotelLiveOrTest(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(apiProviderTayyarahConfig!=null){
			config.setAuthUrl(apiProviderTayyarahConfig.getAuthUrl());
			config.setPassword(apiProviderTayyarahConfig.getPassword());
			config.setUserName(apiProviderTayyarahConfig.getUserName());
			config.setEndPointUrl(apiProviderTayyarahConfig.getEndPointUrl());
			config.setPropertyId(apiProviderTayyarahConfig.getPropertyId());
			config.setId(apiProviderTayyarahConfig.getProviderId());
			config.setApiProviderName(apiProviderTayyarahConfig.getProviderName());
			config.setApiCurrency(apiProviderTayyarahConfig.getApiCurrency());
			config.setTesting(apiProviderTayyarahConfig.getEnvironment()!=null?apiProviderTayyarahConfig.getEnvironment().equalsIgnoreCase("test"):false);
			config.setEnabled(apiProviderTayyarahConfig.isActive());
		}
		else{
			config.setEnabled(false);
		}
		return config;
	}

	public static ApiProviderServiceImpl getApiProviderServiceImpl() {
		return apiProviderServiceImpl;
	}
	public static void setApiProviderServiceImpl(ApiProviderServiceImpl apiProviderServiceImpl) {
		TayyarahRepoConfig.apiProviderServiceImpl = apiProviderServiceImpl;
	}
	public static TayyarahRepoConfig getConfig() {
		return config;
	}
	public static void setConfig(TayyarahRepoConfig config) {
		TayyarahRepoConfig.config = config;
	}
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public boolean isTesting() {
		return isTesting;
	}
	public void setTesting(boolean isTesting) {
		this.isTesting = isTesting;
	}
	public String getAuthUrl() {
		return authUrl;
	}
	public void setAuthUrl(String authUrl) {
		this.authUrl = authUrl;
	}
	public String getEndPointUrl() {
		return endPointUrl;
	}
	public void setEndPointUrl(String endPointUrl) {
		this.endPointUrl = endPointUrl;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getApiProviderName() {
		return apiProviderName;
	}
	public void setApiProviderName(String apiProviderName) {
		this.apiProviderName = apiProviderName;
	}
	public String getApiCurrency() {
		return apiCurrency;
	}
	public void setApiCurrency(String apiCurrency) {
		this.apiCurrency = apiCurrency;
	}
}
