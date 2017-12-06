/**
 * 
 */
package com.tayyarah.apiconfig.model;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tayyarah.apiproviderconfig.entity.ApiProviderTboConfig;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.util.ApiProviderServiceImpl;

/**
 * @author Manish Samrat
 * @createdAt 20-01-2017
 *
 */
/*@Repository*/
public class TboHotelConfig {
	@Autowired()
	static  ApiProviderServiceImpl apiProviderServiceImpl;
	public static ApiProviderTboConfig apiProviderTboConfig=null;
	public static TboHotelConfig config  ;
	private boolean isEnabled;
	private boolean isTesting;
	private String clientId;
	private String userName;
	private String password; 
	private String propertyId; 
	private int id; 
	private String apiProviderName; 
	private String apiCurrency;
	private String rateType;
	private BigDecimal ratePercentage;

	private String authUrlHotel ;
	private String hotelUrlSearchHotel; //URL_SEARCH_HOTELS ;
	private String hotelUrlSearchHotelInfo;//URL_SEARCH_HOTELINFO ;
	private String hotelUrlSearchRooms; //URL_SEARCH_ROOMS ;
	private String hotelUrlBlockRooms; //URL_BLOCK_ROOMS ;
	private String hotelUrlBooking; //URL_BOOKING ;
	private String hotelUrlCancel; //URL_CANCEL ;
	private String hotelUrlCancelStatus; //URL_CANCEL_STATUS ;
	private String hotelUrlBookingSummary; //URL_BOOKING_SUMMARY ;



	public static TboHotelConfig GetTboHotelConfig(AppKeyVo appKeyVo) {
		config = new TboHotelConfig();
		try{
			apiProviderTboConfig = ApiProviderServiceImpl.businnessLogicforTboHotelLiveOrTest(appKeyVo);
			if(apiProviderTboConfig!=null){
				config.setAuthUrlHotel(apiProviderTboConfig.getAuthUrlHotel());
				config.setHotelUrlBlockRooms(apiProviderTboConfig.getHotelUrlBlockRooms());
				config.setHotelUrlBooking(apiProviderTboConfig.getHotelUrlBooking());
				config.setHotelUrlBookingSummary(apiProviderTboConfig.getHotelUrlBookingSummary());
				config.setHotelUrlCancel(apiProviderTboConfig.getHotelUrlCancel());
				config.setHotelUrlCancelStatus(apiProviderTboConfig.getHotelUrlCancelStatus());
				config.setHotelUrlSearchHotel(apiProviderTboConfig.getHotelUrlSearchHotel());
				config.setHotelUrlSearchHotelInfo(apiProviderTboConfig.getHotelUrlSearchHotelInfo());
				config.setHotelUrlSearchRooms(apiProviderTboConfig.getHotelUrlSearchRooms());

				config.setPassword(apiProviderTboConfig.getPasswordHotel());
				config.setUserName(apiProviderTboConfig.getUserNameHotel());
				config.setPropertyId(apiProviderTboConfig.getPropertyId());
				config.setId(apiProviderTboConfig.getProviderId());
				config.setApiProviderName(apiProviderTboConfig.getProviderName());
				config.setApiCurrency(apiProviderTboConfig.getApiCurrency());
				config.setTesting(apiProviderTboConfig.getEnvironment()!=null?apiProviderTboConfig.getEnvironment().equalsIgnoreCase("test"):false);
				config.setEnabled(apiProviderTboConfig.isActive());
				config.setClientId(apiProviderTboConfig.getClientId());
				config.setRateType(apiProviderTboConfig.getHotelVendorRateType());
				config.setRatePercentage(apiProviderTboConfig.getHotelVendorPercentage());

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
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
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

	public String getAuthUrlHotel() {
		return authUrlHotel;
	}

	public void setAuthUrlHotel(String authUrlHotel) {
		this.authUrlHotel = authUrlHotel;
	}

	public String getHotelUrlSearchHotel() {
		return hotelUrlSearchHotel;
	}

	public void setHotelUrlSearchHotel(String hotelUrlSearchHotel) {
		this.hotelUrlSearchHotel = hotelUrlSearchHotel;
	}

	public String getHotelUrlSearchHotelInfo() {
		return hotelUrlSearchHotelInfo;
	}

	public void setHotelUrlSearchHotelInfo(String hotelUrlSearchHotelInfo) {
		this.hotelUrlSearchHotelInfo = hotelUrlSearchHotelInfo;
	}

	public String getHotelUrlSearchRooms() {
		return hotelUrlSearchRooms;
	}

	public void setHotelUrlSearchRooms(String hotelUrlSearchRooms) {
		this.hotelUrlSearchRooms = hotelUrlSearchRooms;
	}

	public String getHotelUrlBlockRooms() {
		return hotelUrlBlockRooms;
	}

	public void setHotelUrlBlockRooms(String hotelUrlBlockRooms) {
		this.hotelUrlBlockRooms = hotelUrlBlockRooms;
	}

	public String getHotelUrlBooking() {
		return hotelUrlBooking;
	}

	public void setHotelUrlBooking(String hotelUrlBooking) {
		this.hotelUrlBooking = hotelUrlBooking;
	}

	public String getHotelUrlCancel() {
		return hotelUrlCancel;
	}

	public void setHotelUrlCancel(String hotelUrlCancel) {
		this.hotelUrlCancel = hotelUrlCancel;
	}

	public String getHotelUrlCancelStatus() {
		return hotelUrlCancelStatus;
	}

	public void setHotelUrlCancelStatus(String hotelUrlCancelStatus) {
		this.hotelUrlCancelStatus = hotelUrlCancelStatus;
	}

	public String getHotelUrlBookingSummary() {
		return hotelUrlBookingSummary;
	}

	public void setHotelUrlBookingSummary(String hotelUrlBookingSummary) {
		this.hotelUrlBookingSummary = hotelUrlBookingSummary;
	} 
}
