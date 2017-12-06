package com.tayyarah.apiconfig.model;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.apiproviderconfig.entity.ApiProviderTboConfig;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.util.ApiProviderServiceImpl;
/*
 * @Created by : Manish Samrat
 * @Date : 16-01-2017
 * 
 * 
 * */

public class TboFlightConfig {
	@Autowired()
	static  ApiProviderServiceImpl apiProviderServiceImpl;
	static final Logger logger = Logger.getLogger(TboFlightConfig.class);
	/*	private String URL_AUTHENTICATE = "SharedServices/SharedData.svc/rest/Authenticate";
	private String SEARCH_URL = "BookingEngineService_Air/AirService.svc/rest/Search/";
	private String FARERULE_URL = "BookingEngineService_Air/AirService.svc/rest/FareRule/";
	private String AIRPRICE_URL = "BookingEngineService_Air/AirService.svc/rest/FareQuote/";
	private String SSR_URL = "BookingEngineService_Air/AirService.svc/rest/SSR/";
	private String BOOK_URL = "BookingEngineService_Air/AirService.svc/rest/Book/";
	private String TICKET_URL = "BookingEngineService_Air/AirService.svc/rest/Ticket/";
	private String GETBOOKDETAIL_URL = "BookingEngineService_Air/AirService.svc/rest/GetBookingDetails/";
	private String RELEASEPNR_URL = "BookingEngineService_Air/AirService.svc/rest/ReleasePNRRequest/";
	private String CANCELLATION_URL = "BookingEngineService_Air/AirService.svc/rest/SendChangeRequest/";
	private String CANCELLATIONSTATUS_URL = "BookingEngineService_Air/AirService.svc/rest/GetChangeRequestStatus/";
	private String PRICERBD_URL = "BookingEngineService_Air/AirService.svc/rest/PriceRBD/";
	private String  AGENCY_BALANCE_URL = "SharedServices/SharedData.svc/rest/GetAgencyBalance";
	private String CALENDAR_SEARCH_URL = "BookingEngineService_Air/SharedData.svc/rest/GetCalendarFare/"; */

	public static ApiProviderTboConfig apiProviderTboConfig=null;
	public static final String DEFAULT_CURRENCY = "INR";
	public static TboFlightConfig tboFlightConfig ;
	private boolean isEnabled;
	private boolean isTest;
	private String username;
	private String password;
	private String enduserip; 
	private String clientid; 

	private String generalUrlAgencyBalance ;
	
	private String authUrlFlight ;
	private String flightUrlSearch;
	private String flightUrlFarerule ;
	private String flightUrlAirprice ;
	private String flightUrlSsr ;
	private String flightUrlBook ;
	private String flightUrlTicket ;
	private String flightUrlGetBookingDetail ;
	private String flightUrlReleasePnr ;
	private String flightUrlCancellation ;
	private String flightUrlCancellationStatus ;
	private String flightUrlPriceRbd ;
	private String flightUrlCalendarSearch ;


	// Default constructor
	private  TboFlightConfig(){

	} 

	public static TboFlightConfig GetTboConfig(AppKeyVo appKeyVo) {      
		try{
			tboFlightConfig = new TboFlightConfig();
			apiProviderTboConfig = ApiProviderServiceImpl.businnessLogicforTboLiveOrTest(appKeyVo);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		if(apiProviderTboConfig!=null){
			tboFlightConfig.setAuthUrlFlight(apiProviderTboConfig.getAuthUrlFlight());
			tboFlightConfig.setFlightUrlAirprice(apiProviderTboConfig.getFlightUrlAirprice());
			tboFlightConfig.setFlightUrlBook(apiProviderTboConfig.getFlightUrlBook());
			tboFlightConfig.setFlightUrlCalendarSearch(apiProviderTboConfig.getFlightUrlCalendarSearch());
			tboFlightConfig.setFlightUrlCancellation(apiProviderTboConfig.getFlightUrlCancellation());
			tboFlightConfig.setFlightUrlCancellationStatus(apiProviderTboConfig.getFlightUrlCancellationStatus());
			tboFlightConfig.setFlightUrlFarerule(apiProviderTboConfig.getFlightUrlFarerule());
			tboFlightConfig.setFlightUrlGetBookingDetail(apiProviderTboConfig.getFlightUrlGetBookingDetail());
			tboFlightConfig.setFlightUrlPriceRbd( apiProviderTboConfig.getFlightUrlPriceRbd());
			tboFlightConfig.setFlightUrlReleasePnr(apiProviderTboConfig.getFlightUrlReleasePnr());
			tboFlightConfig.setFlightUrlSearch(apiProviderTboConfig.getFlightUrlSearch());
			tboFlightConfig.setFlightUrlSsr(apiProviderTboConfig.getFlightUrlSsr());
			tboFlightConfig.setFlightUrlTicket(apiProviderTboConfig.getFlightUrlTicket());

			tboFlightConfig.setPassword(apiProviderTboConfig.getPassword());
			tboFlightConfig.setUsername(apiProviderTboConfig.getUserName());
			tboFlightConfig.setEnduserip(apiProviderTboConfig.getEnduserIp());
			tboFlightConfig.setClientid(apiProviderTboConfig.getClientId());
			tboFlightConfig.setIsTest(apiProviderTboConfig.getEnvironment().equalsIgnoreCase("test")?true:false);
			tboFlightConfig.setIsEnabled(apiProviderTboConfig.isActive());
		}
		else{
			tboFlightConfig.setIsEnabled(false);
		}	
		return tboFlightConfig;
	}

	public String getClientid() {
		return clientid;
	}
	public void setClientid(String clientid) {
		this.clientid = clientid;
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
	public String getEnduserip() {
		return enduserip;
	}
	public void setEnduserip(String enduserip) {
		this.enduserip = enduserip;
	}

	public String getAuthUrlFlight() {
		return authUrlFlight;
	}

	public void setAuthUrlFlight(String authUrlFlight) {
		this.authUrlFlight = authUrlFlight;
	}

	public String getFlightUrlSearch() {
		return flightUrlSearch;
	}

	public void setFlightUrlSearch(String flightUrlSearch) {
		this.flightUrlSearch = flightUrlSearch;
	}

	public String getFlightUrlFarerule() {
		return flightUrlFarerule;
	}

	public void setFlightUrlFarerule(String flightUrlFarerule) {
		this.flightUrlFarerule = flightUrlFarerule;
	}

	public String getFlightUrlAirprice() {
		return flightUrlAirprice;
	}

	public void setFlightUrlAirprice(String flightUrlAirprice) {
		this.flightUrlAirprice = flightUrlAirprice;
	}

	public String getFlightUrlSsr() {
		return flightUrlSsr;
	}

	public void setFlightUrlSsr(String flightUrlSsr) {
		this.flightUrlSsr = flightUrlSsr;
	}

	public String getFlightUrlBook() {
		return flightUrlBook;
	}

	public void setFlightUrlBook(String flightUrlBook) {
		this.flightUrlBook = flightUrlBook;
	}

	public String getFlightUrlTicket() {
		return flightUrlTicket;
	}

	public void setFlightUrlTicket(String flightUrlTicket) {
		this.flightUrlTicket = flightUrlTicket;
	}


	public String getFlightUrlReleasePnr() {
		return flightUrlReleasePnr;
	}

	public void setFlightUrlReleasePnr(String flightUrlReleasePnr) {
		this.flightUrlReleasePnr = flightUrlReleasePnr;
	}

	public String getFlightUrlCancellationStatus() {
		return flightUrlCancellationStatus;
	}

	public void setFlightUrlCancellationStatus(String flightUrlCancellationStatus) {
		this.flightUrlCancellationStatus = flightUrlCancellationStatus;
	}

	public String getFlightUrlPriceRbd() {
		return flightUrlPriceRbd;
	}

	public void setFlightUrlPriceRbd(String flightUrlPriceRbd) {
		this.flightUrlPriceRbd = flightUrlPriceRbd;
	}

	public String getFlightUrlCalendarSearch() {
		return flightUrlCalendarSearch;
	}

	public void setFlightUrlCalendarSearch(String flightUrlCalendarSearch) {
		this.flightUrlCalendarSearch = flightUrlCalendarSearch;
	}

	public String getFlightUrlCancellation() {
		return flightUrlCancellation;
	}

	public void setFlightUrlCancellation(String flightUrlCancellation) {
		this.flightUrlCancellation = flightUrlCancellation;
	}

	public String getFlightUrlGetBookingDetail() {
		return flightUrlGetBookingDetail;
	}

	public void setFlightUrlGetBookingDetail(String flightUrlGetBookingDetail) {
		this.flightUrlGetBookingDetail = flightUrlGetBookingDetail;
	}

	public String getGeneralUrlAgencyBalance() {
		return generalUrlAgencyBalance;
	}

	public void setGeneralUrlAgencyBalance(String generalUrlAgencyBalance) {
		this.generalUrlAgencyBalance = generalUrlAgencyBalance;
	}


	/*public String getURL_AUTHENTICATE() {
		URL_AUTHENTICATE = "SharedServices/SharedData.svc/rest/Authenticate";
		if(!isTest)
			URL_AUTHENTICATE = "SharedAPI/SharedData.svc/rest/authenticate";
		return URL_AUTHENTICATE;
	}

	public String getSEARCH_URL() {
		SEARCH_URL = "BookingEngineService_Air/AirService.svc/rest/Search/";
		if(!isTest)
			SEARCH_URL = "AirAPI_V10/AirService.svc/rest/Search/";
		return SEARCH_URL;
	}

	public String getFARERULE_URL() {
		FARERULE_URL = "BookingEngineService_Air/AirService.svc/rest/FareRule/";		
		if(!isTest)
			FARERULE_URL = "AirAPI_V10/AirService.svc/rest/FareRule/";
		return FARERULE_URL;
	}

	public String getAIRPRICE_URL() {
		AIRPRICE_URL = "BookingEngineService_Air/AirService.svc/rest/FareQuote/";
		if(!isTest)
			AIRPRICE_URL = "AirAPI_V10/AirService.svc/rest/FareQuote/";
		return AIRPRICE_URL;
	}

	public String getSSR_URL() {
		SSR_URL = "BookingEngineService_Air/AirService.svc/rest/SSR/";
		if(!isTest)
			SSR_URL = "AirAPI_V10/AirService.svc/rest/SSR/";
		return SSR_URL;
	}

	public String getBOOK_URL() {
		BOOK_URL = "BookingEngineService_Air/AirService.svc/rest/Book/";
		if(!isTest)
			BOOK_URL = "AirAPI_V10/AirService.svc/rest/Book/";
		return BOOK_URL;
	}

	public String getTICKET_URL() {
		TICKET_URL = "BookingEngineService_Air/AirService.svc/rest/Ticket/";
		if(!isTest)
			TICKET_URL = "AirAPI_V10/AirService.svc/rest/Ticket/";
		return TICKET_URL;
	}

	public String getGETBOOKDETAIL_URL() {
		GETBOOKDETAIL_URL = "BookingEngineService_Air/AirService.svc/rest/GetBookingDetails/";
		if(!isTest)
			GETBOOKDETAIL_URL = "AirAPI_V10/AirService.svc/rest/GetBookingDetails/";
		return GETBOOKDETAIL_URL;
	}

	public String getRELEASEPNR_URL() {
		RELEASEPNR_URL = "BookingEngineService_Air/AirService.svc/rest/ReleasePNRRequest/";
		if(!isTest)
			RELEASEPNR_URL = "AirAPI_V10/AirService.svc/rest/ReleasePNRRequest/";
		return RELEASEPNR_URL;
	}

	public String getCANCELLATION_URL() {
		CANCELLATION_URL = "BookingEngineService_Air/AirService.svc/rest/SendChangeRequest/";
		if(!isTest)
			CANCELLATION_URL = "AirAPI_V10/AirService.svc/rest/SendChangeRequest/";
		return CANCELLATION_URL;
	}

	public String getCANCELLATIONSTATUS_URL() {
		CANCELLATIONSTATUS_URL = "BookingEngineService_Air/AirService.svc/rest/GetChangeRequestStatus/";
		if(!isTest)
			CANCELLATIONSTATUS_URL = "AirAPI_V10/AirService.svc/rest/GetChangeRequestStatus/";
		return CANCELLATIONSTATUS_URL;
	}

	public String getPRICERBD_URL() {
		PRICERBD_URL = "BookingEngineService_Air/AirService.svc/rest/PriceRBD/";
		if(!isTest)
			PRICERBD_URL = "AirAPI_V10/AirService.svc/rest/PriceRBD/";
		return PRICERBD_URL;
	}

	public String getAGENCY_BALANCE_URL() {
		AGENCY_BALANCE_URL = "SharedServices/SharedData.svc/rest/GetAgencyBalance";
		if(!isTest)
			AGENCY_BALANCE_URL = "SharedAPI/SharedData.svc/rest/GetAgencyBalance";
		return AGENCY_BALANCE_URL;
	}

	public String getCALENDAR_SEARCH_URL() {
		CALENDAR_SEARCH_URL = "BookingEngineService_Air/AirService.svc/rest/GetCalendarFare/";
		if(!isTest)
			CALENDAR_SEARCH_URL = "AirAPI_V10/AirService.svc/rest/GetCalendarFare/";
		return CALENDAR_SEARCH_URL;
	}*/
}