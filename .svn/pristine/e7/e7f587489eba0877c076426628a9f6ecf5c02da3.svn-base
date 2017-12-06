package com.tayyarah.hotel.util;

import java.io.Serializable;
import java.math.BigDecimal;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tayyarah.apiconfig.model.DesiyaHotelConfig;
import com.tayyarah.apiconfig.model.TayyarahRepoConfig;
import com.tayyarah.apiconfig.model.TboHotelConfig;
import com.tayyarah.common.model.AppKeyVo;



public class HotelApiCredentials {

	//	public static DesiyaHotelConfig desiyaHotelConfig;
	public static TboHotelConfig tboHotelConfig;
	public static DesiyaHotelConfig desiyaHotelConfig;
	public static TayyarahRepoConfig tayyarahRepoConfig;
	public HotelApiCredentials() {
		super();
	}
	private String authUrl;
	private String endPointUrl;
	private String userName;
	private String password;
	private String propertyId;
	private String appId;
	private String appKey;
	private String appPassword;
	private String apiProviderName;
	private String apiId;
	private String apiKey;
	private String apiPassword;
	private String apiCurrency;
	private String systemId;
	private String messageId;	
	private int id;
	private boolean isTesting;
	private boolean isEnabled;
	private String tokenId;	
	private String clientId;
	private String rateType;
	private BigDecimal ratePercentage;
	
	private static HotelApiCredentials apiTravelguru ;
	private static HotelApiCredentials apiReznext;
	private static HotelApiCredentials apiRezlive; 
	private static HotelApiCredentials apiTayyarah; 
	private static HotelApiCredentials apiLintas; 
	private static HotelApiCredentials apiLintasReposit; 
	private static HotelApiCredentials apiTBO; 

	private static HotelApiCredentials apiTayyarahReposit;

	public static HotelApiCredentials getApiTravelguru(AppKeyVo appKeyVo) {

		desiyaHotelConfig=DesiyaHotelConfig.GetDesiyaHotelConfig(appKeyVo);
		if(desiyaHotelConfig!=null){
			apiTravelguru = new HotelApiCredentials();
			apiTravelguru.setPassword(desiyaHotelConfig.getPassword());
			apiTravelguru.setUserName(desiyaHotelConfig.getUserName());
			apiTravelguru.setEndPointUrl(desiyaHotelConfig.getEndPointUrl());
			apiTravelguru.setPropertyId(desiyaHotelConfig.getPropertyId());
			apiTravelguru.setId(desiyaHotelConfig.getId());
			apiTravelguru.setApiProviderName(desiyaHotelConfig.getApiProviderName());
			apiTravelguru.setApiCurrency(desiyaHotelConfig.getApiCurrency());
			apiTravelguru.setTesting(desiyaHotelConfig.isTesting());
			apiTravelguru.setIsEnabled(desiyaHotelConfig.isEnabled());
			apiTravelguru.setRateType(desiyaHotelConfig.getRateType());
			apiTravelguru.setRatePercentage(desiyaHotelConfig.getRatePercentage());
		}

		return apiTravelguru;		
	}

	public static HotelApiCredentials getApiReznext() {
		String confFile = "APIConfiguration.xml";
		ConfigurableApplicationContext context
		= new ClassPathXmlApplicationContext(confFile);
		apiReznext = (HotelApiCredentials) context.getBean("configReznext");	
		context.close();
		return apiReznext;
	}

	public static HotelApiCredentials getApiRezlive() {
		String confFile = "APIConfiguration.xml";
		ConfigurableApplicationContext context
		= new ClassPathXmlApplicationContext(confFile);
		apiRezlive = (HotelApiCredentials) context.getBean("configRezive");	
		context.close();
		return apiRezlive;
	}

	public static HotelApiCredentials getApiTayyarah() {
		String confFile = "APIConfiguration.xml";
		ConfigurableApplicationContext context
		= new ClassPathXmlApplicationContext(confFile);
		apiTayyarah = (HotelApiCredentials) context.getBean("configTayyarah");	
		context.close();
		return apiTayyarah;
	}

	public static HotelApiCredentials getApiLintas() {
		String confFile = "APIConfiguration.xml";
		ConfigurableApplicationContext context
		= new ClassPathXmlApplicationContext(confFile);
		apiLintas = (HotelApiCredentials) context.getBean("configLintas");	
		context.close();
		return apiLintas;
	}
	public static HotelApiCredentials getApiLintasReposit() {
		String confFile = "APIConfiguration.xml";
		ConfigurableApplicationContext context
		= new ClassPathXmlApplicationContext(confFile);
		apiLintasReposit = (HotelApiCredentials) context.getBean("configLintasReposit");	
		context.close();
		return apiLintasReposit;
	}

	public static HotelApiCredentials getApiTBO(AppKeyVo appKeyVo) {

		tboHotelConfig = new TboHotelConfig();	
		tboHotelConfig = TboHotelConfig.GetTboHotelConfig(appKeyVo);		
		if(tboHotelConfig!=null){
			apiTBO=new HotelApiCredentials();
			apiTBO.setPassword(tboHotelConfig.getPassword());
			apiTBO.setUserName(tboHotelConfig.getUserName());
			apiTBO.setPropertyId(tboHotelConfig.getPropertyId());
			apiTBO.setId(tboHotelConfig.getId());
			apiTBO.setApiProviderName(tboHotelConfig.getApiProviderName());
			apiTBO.setApiCurrency(tboHotelConfig.getApiCurrency());
			apiTBO.setTesting(tboHotelConfig.isTesting());
			apiTBO.setEnabled(tboHotelConfig.isEnabled());
			apiTBO.setClientId(tboHotelConfig.getClientId());
		}			

		return apiTBO;
	}


	public static HotelApiCredentials getApiTayyarahReposit() {
		String confFile = "APIConfiguration.xml";
		ConfigurableApplicationContext context
		= new ClassPathXmlApplicationContext(confFile);
		apiTayyarahReposit = (HotelApiCredentials) context.getBean("configTayyarahReposit");	
		context.close();		
		return apiTayyarahReposit;
	}



	@Override
	public String toString() {
		return "HotelApiCredentials [endPointUrl=" + endPointUrl + ", userName=" + userName + ", password=" + password
				+ ", propertyId=" + propertyId + ", appId=" + appId + ", appKey=" + appKey + ", appPassword="
				+ appPassword + ", apiProviderName=" + apiProviderName + ", apiId=" + apiId + ", apiKey=" + apiKey
				+ ", apiPassword=" + apiPassword + ", systemId=" + systemId + ", messageId=" + messageId + ", id=" + id +", authUrl" +authUrl 
				+ "]";
	}
	public HotelApiCredentials(String endPointUrl, String userName, String password, String propertyId, int id, String apiProviderName) {
		super();
		this.endPointUrl = endPointUrl;
		this.userName = userName;
		this.password = password;
		this.propertyId = propertyId;
		this.id = id;
		this.apiProviderName = apiProviderName;
	}
	public HotelApiCredentials(String endPointUrl, String userName, String password, String propertyId, int id, String apiProviderName, String systemId,
			String messageId) {
		super();
		this.endPointUrl = endPointUrl;
		this.userName = userName;
		this.password = password;
		this.propertyId = propertyId;
		this.systemId = systemId;
		this.messageId = messageId;
		this.id = id;
		this.apiProviderName = apiProviderName;
	}
	public HotelApiCredentials(String endPointUrl, String userName, String password, String propertyId, int id, String apiProviderName, String appkey) {
		super();
		this.endPointUrl = endPointUrl;
		this.userName = userName;
		this.password = password;
		this.propertyId = propertyId;
		this.id = id;
		this.apiProviderName = apiProviderName;
		this.apiKey = appkey;
	}
	public HotelApiCredentials(String endPointUrl, String userName, String password, int id, String apiProviderName, String appkey) {
		super();
		this.endPointUrl = endPointUrl;
		this.userName = userName;
		this.password = password;		
		this.id = id;
		this.apiProviderName = apiProviderName;
		this.apiKey = appkey;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the endPointUrl
	 */
	public String getEndPointUrl() {
		return endPointUrl;
	}
	/**
	 * @param endPointUrl the endPointUrl to set
	 */
	public void setEndPointUrl(String endPointUrl) {
		this.endPointUrl = endPointUrl;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return the propertyId
	 */
	public String getPropertyId() {
		return propertyId;
	}
	/**
	 * @param propertyId the propertyId to set
	 */
	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}
	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}
	/**
	 * @param appId the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}
	/**
	 * @return the appKey
	 */
	public String getAppKey() {
		return appKey;
	}
	/**
	 * @param appKey the appKey to set
	 */
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	/**
	 * @return the appPassword
	 */
	public String getAppPassword() {
		return appPassword;
	}
	/**
	 * @param appPassword the appPassword to set
	 */
	public void setAppPassword(String appPassword) {
		this.appPassword = appPassword;
	}
	/**
	 * @return the apiId
	 */
	public String getApiId() {
		return apiId;
	}
	/**
	 * @param apiId the apiId to set
	 */
	public void setApiId(String apiId) {
		this.apiId = apiId;
	}
	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}
	/**
	 * @param apiKey the apiKey to set
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	/**
	 * @return the apiPassword
	 */
	public String getApiPassword() {
		return apiPassword;
	}
	/**
	 * @param apiPassword the apiPassword to set
	 */
	public void setApiPassword(String apiPassword) {
		this.apiPassword = apiPassword;
	}

	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}


	public String getApiProviderName() {
		return apiProviderName;
	}
	public void setApiProviderName(String apiProviderName) {
		this.apiProviderName = apiProviderName;
	}





	public static TboHotelConfig getTboHotelConfig() {
		return tboHotelConfig;
	}

	public static void setTboHotelConfig(TboHotelConfig tboHotelConfig) {
		HotelApiCredentials.tboHotelConfig = tboHotelConfig;
	}

	public String getAuthUrl() {
		return authUrl;
	}

	public void setAuthUrl(String authUrl) {
		this.authUrl = authUrl;
	}

	public void setTesting(boolean isTesting) {
		this.isTesting = isTesting;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public boolean isTesting() {
		return isTesting;
	}
	public void setIsTesting(boolean isTesting) {
		this.isTesting = isTesting;
	}	
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getApiCurrency() {
		return apiCurrency;
	}
	public void setApiCurrency(String apiCurrency) {
		this.apiCurrency = apiCurrency;
	}



	//api code for a country... api_providername_countrycode..
	//api code international... api_providername_all..


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
	/**
	 * Unique constant id for api providers.
	 * these constants later can be retrived from DB
	 */
	public static final int API_DESIA_IND = 1;
	public static final int API_REZNEXT_IND = 2;
	public static final int API_TRAVELPORT_ALL = 3;
	public static final int API_REZLIVE_INTERNATIONAL = 4;
	public static final int API_TAYYARAH_INTERNATIONAL = 5;

	public static final int API_LINTAS_REPOSITORY = 6;
	public static final int API_LINTAS_INTERNATIONAL = 7;

	public static final int API_TBO_INTERNATIONAL = 8;

	public static final int API_TAYYARAH_REPOSIT_INTERNATIONAL = 9;




	public static final String LINTAS_REQUESTERID = "Lintas123";



	/**
	 * this generates API credential obj provided api providers unique id
	 * these credentials later can be retrived from DB
	 */

	/*public static HotelApiCredentials getApiCredentials(int id)
	{
		HotelApiCredentials api = null;
		switch (id) {
		case API_DESIA_IND:
			api = new HotelApiCredentials("http://stage-api.travelguru.com/services-2.0/tg-services/TGServiceEndPoint?wsdl", "testnet", "t90za6", "1300000141", id, "Travelguru Domestic");
			break;
		case API_TRAVELPORT_ALL:
			api = new HotelApiCredentials("http://stage-api.travelguru.com/services-2.0/tg-services/TGServiceEndPoint?wsdl", "testnet", "t90za6", "1300000141", id, "Travelguru International");			
			break;
		case API_REZNEXT_IND:
			api = new HotelApiCredentials("http://test.reznext.com/ReznextHotelInfo/RezNextHotelInfoService.svc/", "Reznext123", "Reznext@123", "", id, "Reztnext International", "Reznext", "713");
			break;
		//Testing credentials
		case API_REZLIVE_INTERNATIONAL:
			api = new HotelApiCredentials("http://test.xmlhub.com/testpanel.php/action/", "tayyarah", "tayyarah@123", "X29DC10", id, "RezLive International", "RezLive", "");
			break;		
		case API_TAYYARAH_INTERNATIONAL:
			api = new HotelApiCredentials("http://128.199.96.87:8080/TayyarahAPI/hotel/", "Lintas", "lintas", "2", id, "Tayyarah International", "v+iPH5eGGaXaLSEUG1UCjg==");
			break;
		case API_LINTAS_REPOSITORY:
			api = new HotelApiCredentials("http://128.199.96.87:8080/TayyarahAPI/hotel/", "Lintas", "lintas", "2", id, "Lintas Repositary", "v+iPH5eGGaXaLSEUG1UCjg==");
			break;
		case API_LINTAS_INTERNATIONAL:
			api = new HotelApiCredentials("http://128.199.96.87:8080/TayyarahAPI/hotel/", "Lintas", "lintas", "2", id, "Lintas API International", "v+iPH5eGGaXaLSEUG1UCjg==");
			break;
		default:
			break;
		}		
		return api;

	}*/

	public static HotelApiCredentials getApiCredentials(int id,AppKeyVo appKeyVo)
	{
		HotelApiCredentials api = null;
		switch (id) {
		case API_DESIA_IND:
			api = getApiTravelguru(appKeyVo);
			break;
		case API_TRAVELPORT_ALL:
			api = getApiTravelguru(appKeyVo);
			break;
		case API_REZNEXT_IND:
			api = getApiReznext();
			break;
			//Testing credentials
		case API_REZLIVE_INTERNATIONAL:
			api = getApiRezlive();
			break;		
		case API_TAYYARAH_INTERNATIONAL:
			api = getApiTayyarah();
			break;
		case API_LINTAS_REPOSITORY:
			api = getApiLintasReposit();
			break;
		case API_LINTAS_INTERNATIONAL:
			api = getApiLintas();
			break;
		case API_TBO_INTERNATIONAL:
			api = getApiTBO(appKeyVo);
			break;
		case API_TAYYARAH_REPOSIT_INTERNATIONAL:
			api = getApiTayyarahReposit();
			break;
		default:
			break;
		}		
		return api;

	}




	public static class ApiRateInfo implements Serializable{		
		public Integer getApiId() {
			return apiId;
		}
		public void setApiId(Integer apiId) {
			this.apiId = apiId;
		}
		public String getApiCurrency() {
			return apiCurrency;
		}
		public void setApiCurrency(String apiCurrency) {
			this.apiCurrency = apiCurrency;
		}
		public String getBaseCurrency() {
			return baseCurrency;
		}
		public void setBaseCurrency(String baseCurrency) {
			this.baseCurrency = baseCurrency;
		}
		public String getBookingCurrency() {
			return bookingCurrency;
		}
		public void setBookingCurrency(String bookingCurrency) {
			this.bookingCurrency = bookingCurrency;
		}
		public BigDecimal getExRateApiToBase() {
			return exRateApiToBase;
		}
		public void setExRateApiToBase(BigDecimal exRateApiToBase) {
			this.exRateApiToBase = exRateApiToBase;
		}
		public BigDecimal getExRateBaseToBooking() {
			return exRateBaseToBooking;
		}
		public void setExRateBaseToBooking(BigDecimal exRateBaseToBooking) {
			this.exRateBaseToBooking = exRateBaseToBooking;
		}
		public ApiRateInfo(Integer apiId, String apiCurrency, String baseCurrency, String bookingCurrency,
				BigDecimal exRateApiToBase, BigDecimal exRateBaseToBooking) {
			super();
			this.apiId = apiId;
			this.apiCurrency = apiCurrency;
			this.baseCurrency = baseCurrency;
			this.bookingCurrency = bookingCurrency;
			this.exRateApiToBase = exRateApiToBase;
			this.exRateBaseToBooking = exRateBaseToBooking;
		}
		public ApiRateInfo() {
			super();
			this.apiId = -1;			
			this.apiCurrency = null;
			this.baseCurrency = null;
			this.bookingCurrency = null;		
			this.exRateApiToBase  = null;
			this.exRateBaseToBooking = null;			
		}
		private Integer apiId;			
		private String apiCurrency = null;
		private String baseCurrency = null;
		private String bookingCurrency = null;		
		private BigDecimal exRateApiToBase  = null;
		private BigDecimal exRateBaseToBooking = null;			
	}

}
