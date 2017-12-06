package com.tayyarah.bus.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.tayyarah.bus.model.BusBlockTicketRequest;
import com.tayyarah.bus.model.BusCancelRequest;
import com.tayyarah.bus.model.BusConfirmRequest;
import com.tayyarah.bus.model.BusLayoutRequest;
import com.tayyarah.bus.model.BusPaxDetail;
import com.tayyarah.bus.model.BusPaymentRequest;
import com.tayyarah.bus.model.BusSearchRequest;



public class BusParamValidator {
	static final Logger logger = Logger.getLogger(BusParamValidator.class);
	
	public void validateString(String param, String paramName) {
		if (param == null || param.isEmpty()) {
			throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.PARAMVALUE_REQUIED.getErrorMessage());			
		}
	}
	public void validateLong(Long param, String paramName) {
		if (param == null || param.equals(0L)) {
			throw new BusException(ErrorCodeCustomerEnum.Exception, 
					paramName + " is required");
		}
	}
	public void validateDate(String param, String paramName) {
		if (param == null || param.isEmpty()) {
			throw new BusException(ErrorCodeCustomerEnum.Exception, 
					paramName + " is required");
		}else{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			format.setLenient(false);
			try {
				Date date = format.parse(param);			
			} catch (ParseException e) {
				throw new BusException(ErrorCodeCustomerEnum.Exception, 
						paramName + " format should be in YYYY-MM-DD");
				
			}
		}
	}
	public void validateInteger(String param, String paramName) {
		if (param == null || param.isEmpty()) {
			throw new BusException(ErrorCodeCustomerEnum.Exception, 
					paramName + " is required");
		}else{
			try{
				Integer.parseInt(param);
			}catch(NumberFormatException e){
				throw new BusException(ErrorCodeCustomerEnum.Exception, 
						paramName + " is invalid, it should be numeric");	
			}catch (Exception e) {
				throw new BusException(ErrorCodeCustomerEnum.Exception, 
						paramName + " is invalid, it should be numeric");	

			}

		}
	}
	public void validateDecimal(String param, String paramName) {
		if (param == null || param.isEmpty()) {
			throw new BusException(ErrorCodeCustomerEnum.Exception,
					paramName + " is required");
		}else{
			try{
				new BigDecimal(param);
			}catch(NumberFormatException e){
				throw new BusException(ErrorCodeCustomerEnum.Exception,
						paramName + " is invalid, it should be numeric");	
			}catch (Exception e) {
				throw new BusException(ErrorCodeCustomerEnum.Exception,
						paramName + " is invalid, it should be numeric");	

			}

		}
	}	
	public void searchValidator(BusSearchRequest busSearchRequest){
		validateString(busSearchRequest.getOrigin(), "origin");
		validateString(busSearchRequest.getDestination(), "destination");
		validateDate(busSearchRequest.getOnwardDate(), "onwardDate");		
		if(busSearchRequest.getIsDynamicMarkup()){
			validateString(busSearchRequest.getSearchkey(), "searchkey");
			validateDecimal(busSearchRequest.getMarkupAmount(),"markupAmount");
		}
	
	}
	public void layoutValidator(BusLayoutRequest busLayoutRequest){
		validateString(busLayoutRequest.getOrigin(), "origin");
		validateString(busLayoutRequest.getDestination(), "destination");	
		validateDate(busLayoutRequest.getOnwardDate(), "onwardDate");
		validateString(busLayoutRequest.getRouteScheduleId(), "routeScheduleId");
		validateString(busLayoutRequest.getSearchkey(), "searchkey");
		validateString(busLayoutRequest.getInventoryType(), "inventoryType");
	}
	public void blockRequestValidator(BusBlockTicketRequest busBlockTicketRequest){
		validateString(busBlockTicketRequest.getOrigin(), "origin");
		validateString(busBlockTicketRequest.getDestination(), "destination");	
		validateDate(busBlockTicketRequest.getOnwardDate(), "onwardDate");
		validateString(busBlockTicketRequest.getRouteScheduleId(), "routeScheduleId");
		validateString(busBlockTicketRequest.getSearchkey(), "searchkey");
		validateString(busBlockTicketRequest.getInventoryType(), "inventoryType");
		validateString(busBlockTicketRequest.getEmail(), "email");
		validateString(busBlockTicketRequest.getPhone(), "phone");		
	}
	public void blockRequestPaxDetailValidator(List<BusPaxDetail> busPaxDetails){	
		if(busPaxDetails.size() > 0){	
			for (BusPaxDetail busPaxDetail : busPaxDetails) {				
				validateString(busPaxDetail.getAge(), "age");
				validateString(busPaxDetail.getFirstName(), "firstName");	
				validateString(busPaxDetail.getLastName(), "lastName");
				validateString(busPaxDetail.getGender(), "gender");
				validateString(busPaxDetail.getSeatNbr(), "seatNbr");			
			}			
		}else{
			throw new BusException(ErrorCodeCustomerEnum.Exception,"Passenger details are required");
		}		
	}
	
	public void confirmRequestValidator(BusConfirmRequest busConfirmRequest){
		validateString(busConfirmRequest.getBlockTicketKey(), "blockTicketKey");
		validateString(busConfirmRequest.getSearchkey(), "searchkey");		
		validateString(busConfirmRequest.getTransactionkey(), "transactionkey");			
	}
	public void cancelRequestValidator(BusCancelRequest busCancelRequest){
		validateString(busCancelRequest.getOrderid(), "orderid");
		validateString(busCancelRequest.getSearchkey(), "searchkey");		
		validateString(busCancelRequest.getTransactionkey(), "transactionkey");	
		if(busCancelRequest.getSeatNbr().size() == 0){
			throw new BusException(ErrorCodeCustomerEnum.Exception,"Seat numbers are required");
		}
	}
	public void paymentRequestValidator(BusPaymentRequest busPaymentRequest){
	
		validateString(busPaymentRequest.getPaymentId(), "paymentId");		
		validateString(busPaymentRequest.getPaymentStatus(), "paymentStatus");	
		validateString(busPaymentRequest.getResponseCode(), "responseCode");
		validateString(busPaymentRequest.getResponseMessage(), "responseMessage");	
	}
}
