package com.tayyarah.flight.util.api.tbo;

public class TboConstants {

	
	// Testing URL Below	
	
	/*public final static String URL_AUTHENTICATE = "SharedServices/SharedData.svc/rest/Authenticate";
	public final static String SEARCH_URL = "BookingEngineService_Air/AirService.svc/rest/Search/";
	public final static String FARERULE_URL = "BookingEngineService_Air/AirService.svc/rest/FareRule/";
	public final static String AIRPRICE_URL = "BookingEngineService_Air/AirService.svc/rest/FareQuote/";
	public final static String SSR_URL = "BookingEngineService_Air/AirService.svc/rest/SSR/";
	public final static String BOOK_URL = "BookingEngineService_Air/AirService.svc/rest/Book/";
	public final static String TICKET_URL = "BookingEngineService_Air/AirService.svc/rest/Ticket/";
	public final static String GETBOOKDETAIL_URL = "BookingEngineService_Air/AirService.svc/rest/GetBookingDetails/";
	public final static String RELEASEPNR_URL = "BookingEngineService_Air/AirService.svc/rest/ReleasePNRRequest/";
	public final static String CANCELLATION_URL = "BookingEngineService_Air/AirService.svc/rest/SendChangeRequest/";
	public final static String CANCELLATIONSTATUS_URL = "BookingEngineService_Air/AirService.svc/rest/GetChangeRequestStatus/";
	public final static String PRICERBD_URL = "BookingEngineService_Air/AirService.svc/rest/PriceRBD/";
	
	// LIVE URL Below	
	public final static String URL_AUTHENTICATE = "SharedAPI/SharedData.svc/rest/authenticate";
	public final static String SEARCH_URL = "AirAPI_V10/AirService.svc/rest/Search/";
	public final static String FARERULE_URL = "AirAPI_V10/AirService.svc/rest/FareRule/";
	public final static String AIRPRICE_URL = "AirAPI_V10/AirService.svc/rest/FareQuote/";
	public final static String SSR_URL = "AirAPI_V10/AirService.svc/rest/SSR/";
	public final static String BOOK_URL = "AirAPI_V10/AirService.svc/rest/Book/";
	public final static String TICKET_URL = "AirAPI_V10/AirService.svc/rest/Ticket/";
	public final static String GETBOOKDETAIL_URL = "AirAPI_V10/AirService.svc/rest/GetBookingDetails/";
	public final static String RELEASEPNR_URL = "AirAPI_V10/AirService.svc/rest/ReleasePNRRequest/";
	public final static String CANCELLATION_URL = "AirAPI_V10/AirService.svc/rest/SendChangeRequest/";
	public final static String CANCELLATIONSTATUS_URL = "AirAPI_V10/AirService.svc/rest/GetChangeRequestStatus/";
	public final static String PRICERBD_URL = "AirAPI_V10/AirService.svc/rest/PriceRBD/";
	*/
	
	/*public String getURL_AUTHENTICATE() {
		
		return URL_AUTHENTICATE;
	}
	public void setURL_AUTHENTICATE(String uRL_AUTHENTICATE) {
		URL_AUTHENTICATE = uRL_AUTHENTICATE;
	}
	public String getSEARCH_URL() {
		return SEARCH_URL;
	}
	public void setSEARCH_URL(String sEARCH_URL) {
		SEARCH_URL = sEARCH_URL;
	}
	public String getFARERULE_URL() {
		return FARERULE_URL;
	}
	public void setFARERULE_URL(String fARERULE_URL) {
		FARERULE_URL = fARERULE_URL;
	}
	public String getAIRPRICE_URL() {
		return AIRPRICE_URL;
	}
	public void setAIRPRICE_URL(String aIRPRICE_URL) {
		AIRPRICE_URL = aIRPRICE_URL;
	}
	public String getSSR_URL() {
		return SSR_URL;
	}
	public void setSSR_URL(String sSR_URL) {
		SSR_URL = sSR_URL;
	}
	public String getBOOK_URL() {
		return BOOK_URL;
	}
	public void setBOOK_URL(String bOOK_URL) {
		BOOK_URL = bOOK_URL;
	}
	public String getTICKET_URL() {
		return TICKET_URL;
	}
	public void setTICKET_URL(String tICKET_URL) {
		TICKET_URL = tICKET_URL;
	}
	public String getGETBOOKDETAIL_URL() {
		return GETBOOKDETAIL_URL;
	}
	public void setGETBOOKDETAIL_URL(String gETBOOKDETAIL_URL) {
		GETBOOKDETAIL_URL = gETBOOKDETAIL_URL;
	}
	public String getRELEASEPNR_URL() {
		return RELEASEPNR_URL;
	}
	public void setRELEASEPNR_URL(String rELEASEPNR_URL) {
		RELEASEPNR_URL = rELEASEPNR_URL;
	}
	public String getCANCELLATION_URL() {
		return CANCELLATION_URL;
	}
	public void setCANCELLATION_URL(String cANCELLATION_URL) {
		CANCELLATION_URL = cANCELLATION_URL;
	}
	public String getCANCELLATIONSTATUS_URL() {
		return CANCELLATIONSTATUS_URL;
	}
	public void setCANCELLATIONSTATUS_URL(String cANCELLATIONSTATUS_URL) {
		CANCELLATIONSTATUS_URL = cANCELLATIONSTATUS_URL;
	}
	public String getPRICERBD_URL() {
		return PRICERBD_URL;
	}
	public void setPRICERBD_URL(String pRICERBD_URL) {
		PRICERBD_URL = pRICERBD_URL;
	}
	private String URL_AUTHENTICATE ="SharedServices/SharedData.svc/rest/Authenticate";
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
	
	
	*/
	
}
