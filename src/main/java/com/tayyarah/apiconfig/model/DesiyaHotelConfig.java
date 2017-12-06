/**
 * 
 */
package com.tayyarah.apiconfig.model;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tayyarah.apiproviderconfig.entity.ApiProviderDesiyaConfig;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.util.ApiProviderServiceImpl;

/**
 * @author Manish Samrat
 * @createdAt 20-01-2017
 *
 */

@Repository
public class DesiyaHotelConfig {

	@Autowired()
	static  ApiProviderServiceImpl apiProviderServiceImpl;
	public static ApiProviderDesiyaConfig apiProviderDesiyaConfig=null;
	public static DesiyaHotelConfig config  ;
	private boolean isEnabled;
	private boolean isTesting;
	private String endPointUrl;
	private String userName;
	private String password; 
	private String propertyId; 
	private int id; 
	private String apiProviderName; 
	private String apiCurrency;
	private String rateType;
	private BigDecimal ratePercentage;	

	public static DesiyaHotelConfig GetDesiyaHotelConfig(AppKeyVo appKeyVo) {
		config = new DesiyaHotelConfig();
		try{
			apiProviderDesiyaConfig = ApiProviderServiceImpl.businnessLogicforDesiyaLiveOrTest(appKeyVo);
			if(apiProviderDesiyaConfig!=null){
				config.setPassword(apiProviderDesiyaConfig.getPassword());
				config.setUserName(apiProviderDesiyaConfig.getUserName());
				config.setEndPointUrl(apiProviderDesiyaConfig.getEndPointUrl());
				config.setPropertyId(apiProviderDesiyaConfig.getPropertyId());
				config.setId(apiProviderDesiyaConfig.getProviderId());
				config.setApiProviderName(apiProviderDesiyaConfig.getProviderName());
				config.setApiCurrency(apiProviderDesiyaConfig.getApiCurrency());
				config.setTesting(apiProviderDesiyaConfig.getEnvironment()!=null?apiProviderDesiyaConfig.getEnvironment().equalsIgnoreCase("test"):false);
				config.setEnabled(apiProviderDesiyaConfig.isActive());
				config.setRateType(apiProviderDesiyaConfig.getVendorRateType());
				config.setRatePercentage(apiProviderDesiyaConfig.getVendorPercentage());
			}
			else{
				config.setEnabled(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return config;
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
	public static ApiProviderServiceImpl getApiProviderServiceImpl() {
		return apiProviderServiceImpl;
	}
	public static void setApiProviderServiceImpl(ApiProviderServiceImpl apiProviderServiceImpl) {
		DesiyaHotelConfig.apiProviderServiceImpl = apiProviderServiceImpl;
	}
	public static ApiProviderDesiyaConfig getApiProviderDesiyaConfig() {
		return apiProviderDesiyaConfig;
	}
	public static void setApiProviderDesiyaConfig(ApiProviderDesiyaConfig apiProviderDesiyaConfig) {
		DesiyaHotelConfig.apiProviderDesiyaConfig = apiProviderDesiyaConfig;
	}
	public static DesiyaHotelConfig getConfig() {
		return config;
	}
	public static void setConfig(DesiyaHotelConfig config) {
		DesiyaHotelConfig.config = config;
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
	public String getRateType() {
		return rateType;
	}
	public BigDecimal getRatePercentage() {
		return ratePercentage;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public void setRatePercentage(BigDecimal ratePercentage) {
		this.ratePercentage = ratePercentage;
	} 
}
