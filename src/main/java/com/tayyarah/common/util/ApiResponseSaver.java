package com.tayyarah.common.util;

import java.util.Calendar;

import org.apache.log4j.Logger;

import com.tayyarah.api.bus.esmart.model.ApiConfirmTicket;
import com.tayyarah.api.flight.tbo.model.TboTicketResponse;
import com.tayyarah.api.insurance.trawelltag.model.CreatePolicyResult;
import com.tayyarah.bus.dao.BusCommonDao;
import com.tayyarah.bus.entity.BusOrderRow;
import com.tayyarah.common.entity.FlightAndHotelBookApiResponse;
import com.tayyarah.flight.dao.FlightBookingDao;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.hotel.model.APIHotelBook;
import com.tayyarah.hotel.util.HotelApiCredentials;
import com.tayyarah.insurance.dao.InsuranceCommonDao;
import com.tayyarah.insurance.entity.InsuranceOrderRow;
import com.tayyarah.insurance.util.TrawellTagException;



public class ApiResponseSaver {
	static Logger logger = Logger.getLogger(ApiResponseSaver.class);

	public static void saveFlightApiResponse(FlightBookingDao FBDAO,TboTicketResponse tboTicketResponse,FlightOrderRow flightOrderRow){
		FlightAndHotelBookApiResponse flightAndHotelBookApiResponse = new FlightAndHotelBookApiResponse();
		flightAndHotelBookApiResponse.setApiStatusCode(String.valueOf(tboTicketResponse.getResponse().getError().getErrorCode()));
		flightAndHotelBookApiResponse.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));	
		if(tboTicketResponse.getResponse().getError().getErrorCode() == 0){
			flightAndHotelBookApiResponse.setApiStatusMessage("Confirmed");
		}else{
			flightAndHotelBookApiResponse.setApiStatusMessage(tboTicketResponse.getResponse().getError().getErrorMessage());
		}		
		flightAndHotelBookApiResponse.setApiProvider("TBO");
		flightAndHotelBookApiResponse.setApiTraceId(tboTicketResponse.getResponse().getTraceId());
		flightAndHotelBookApiResponse.setOrderRowId(flightOrderRow.getId());
		flightAndHotelBookApiResponse.setProductType("Flight");
		try {						
			FBDAO.SaveApiBookingStatus(flightAndHotelBookApiResponse);
		} catch (Exception e) {
			logger.error("Exception", e);					
		}		
	}
	
	public static void saveHotelApiResponse(APIHotelBook apiHotelBook, HotelOrderRow hotelOrderRow,FlightBookingDao FBDAO){

		if(apiHotelBook.getBookRes() != null){
			FlightAndHotelBookApiResponse flightAndHotelBookApiResponse = new FlightAndHotelBookApiResponse();
			flightAndHotelBookApiResponse.setApiStatusCode(String.valueOf(apiHotelBook.getBookRes().getStatus().getCode()));
			flightAndHotelBookApiResponse.setApiStatusMessage(apiHotelBook.getBookRes().getStatus().getMessage());
			flightAndHotelBookApiResponse.setApiProvider(apiHotelBook.getRoomsummary().getBasicPropertyInfo().getApiVendorID());
			flightAndHotelBookApiResponse.setApiTraceId(apiHotelBook.getRoomsummary().getBasicPropertyInfo().getApiTraceId());
			flightAndHotelBookApiResponse.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));				
			flightAndHotelBookApiResponse.setProductType("Hotel");
			
			switch (apiHotelBook.getRoomsummary().getBasicPropertyInfo().getApiProvider()) {
			case HotelApiCredentials.API_REZLIVE_INTERNATIONAL:
				flightAndHotelBookApiResponse.setApiProvider("REZLIVE");
				break;		
			case HotelApiCredentials.API_DESIA_IND:
				flightAndHotelBookApiResponse.setApiProvider("DESIYA");
				break;
			case HotelApiCredentials.API_REZNEXT_IND:	
				flightAndHotelBookApiResponse.setApiProvider("REZLIVE");
				break;
			case HotelApiCredentials.API_TAYYARAH_INTERNATIONAL:	
				flightAndHotelBookApiResponse.setApiProvider("TAYYARAH");
				break;
			case HotelApiCredentials.API_LINTAS_REPOSITORY:	
				flightAndHotelBookApiResponse.setApiProvider("LINTAS");
				break;
			case HotelApiCredentials.API_TBO_INTERNATIONAL:
				flightAndHotelBookApiResponse.setApiProvider("TBO");
				break;
			case HotelApiCredentials.API_TAYYARAH_REPOSIT_INTERNATIONAL:
				flightAndHotelBookApiResponse.setApiProvider("TAYYARAH");
				break;
			}			
			flightAndHotelBookApiResponse.setOrderRowId(hotelOrderRow.getId());
			try {						
				FBDAO.SaveApiBookingStatus(flightAndHotelBookApiResponse);
			} catch (Exception e) {
				logger.error("Exception", e);					
			}			
		}
    }
	
	public static void saveBusApiResponse(BusCommonDao busCommonDao,ApiConfirmTicket apiConfirmTicket,BusOrderRow busOrderRow){

		FlightAndHotelBookApiResponse flightAndHotelBookApiResponse = new FlightAndHotelBookApiResponse();
		flightAndHotelBookApiResponse.setApiStatusCode(apiConfirmTicket.getApiStatus().getSuccess() == true?"0":"1");
		flightAndHotelBookApiResponse.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));	
		if(apiConfirmTicket.getApiStatus().getSuccess()){
			flightAndHotelBookApiResponse.setApiStatusMessage("Confirmed");
		}else{
			flightAndHotelBookApiResponse.setApiStatusMessage(apiConfirmTicket.getApiStatus().getMessage());
		}		
		flightAndHotelBookApiResponse.setApiProvider("Esmart Travels");
		flightAndHotelBookApiResponse.setApiTraceId(String.valueOf(apiConfirmTicket.getInventoryType()));
		flightAndHotelBookApiResponse.setOrderRowId(busOrderRow.getId());
		flightAndHotelBookApiResponse.setProductType("Bus");
		try {						
			busCommonDao.SaveApiBookingStatus(flightAndHotelBookApiResponse);
		} catch (Exception e) {
			logger.error("Exception", e);					
		}

	}
	public static void saveInsuranceApiResponse(InsuranceCommonDao insuranceCommonDao,CreatePolicyResult createPolicyResult,InsuranceOrderRow insuranceOrderRow){

		FlightAndHotelBookApiResponse flightAndHotelBookApiResponse = new FlightAndHotelBookApiResponse();
		flightAndHotelBookApiResponse.setApiStatusCode(createPolicyResult.getData().getErrorcode() != null?createPolicyResult.getData().getErrorcode():"0");
		flightAndHotelBookApiResponse.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));	
		if(createPolicyResult.getData().getStatus().equalsIgnoreCase("ok") ){
			flightAndHotelBookApiResponse.setApiStatusMessage("Confirmed");
		}else{
			flightAndHotelBookApiResponse.setApiStatusMessage(TrawellTagException.getErrorMessage(createPolicyResult.getData().getErrorcode()!=null?createPolicyResult.getData().getErrorcode():"") );
		}		
		flightAndHotelBookApiResponse.setApiProvider("TrawellTag");
		flightAndHotelBookApiResponse.setApiTraceId(createPolicyResult.getData().getReference());
		flightAndHotelBookApiResponse.setOrderRowId(insuranceOrderRow.getId());
		flightAndHotelBookApiResponse.setProductType("Insurance");
		try {						
			insuranceCommonDao.SaveApiBookingStatus(flightAndHotelBookApiResponse);
		} catch (Exception e) {
			logger.error("Exception", e);					
		}

	}
}
