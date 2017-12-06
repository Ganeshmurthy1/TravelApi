package com.tayyarah.hotel.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import com.tayyarah.hotel.exception.HotelErrorCodeCustomerEnum;
import com.tayyarah.hotel.exception.HotelException;
import com.tayyarah.hotel.model.HotelOverview;

public class HotelApiValidator {
	public void validateString(String param, String paramName) {
		if (param == null || param.isEmpty()) {
			throw new HotelException(HotelErrorCodeCustomerEnum.Exception, 
					paramName + " is required");
		}
	}

	public void validateLong(Long param, String paramName) {
		if (param == null || param.equals(0L)) {
			throw new HotelException(HotelErrorCodeCustomerEnum.Exception, 
					paramName + " is required");
		}
	}

	public void validateDate(String param, String paramName) {
		if (param == null || param.isEmpty()) {
			throw new HotelException(HotelErrorCodeCustomerEnum.Exception, 
					paramName + " is required");
		}else{
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			format.setLenient(false);
			try {
				Date date = format.parse(param);				
			} catch (ParseException e) {
				throw new HotelException(HotelErrorCodeCustomerEnum.Exception, 
						paramName + " format should be in YYYYMMDD");				
			}
		}
	}

	public void validateInteger(String param, String paramName) {
		if (param == null || param.isEmpty()) {
			throw new HotelException(HotelErrorCodeCustomerEnum.Exception, 
					paramName + " is required");
		}else{
			try{
				Integer.parseInt(param);
			}catch(NumberFormatException e){
				throw new HotelException(HotelErrorCodeCustomerEnum.Exception, 
						paramName + " is invalid, it should be numeric");	
			}catch (Exception e) {
				throw new HotelException(HotelErrorCodeCustomerEnum.Exception, 
						paramName + " is invalid, it should be numeric");	

			}

		}
	}

	public void validateCustomer(HotelOverview hotelOverview) {
		if (hotelOverview == null) {
			throw new HotelException(HotelErrorCodeCustomerEnum.Exception, 
					"Hotel is empty");
		}

		if (hotelOverview.getCity() == null || hotelOverview.getCity_Zone() == null|| hotelOverview.getCity().isEmpty()|| hotelOverview.getCity_Zone().isEmpty()){
			throw new HotelException(HotelErrorCodeCustomerEnum.Exception, 
					"City ,City_Zone, Hotel_Name ,Areais required");
		}	
	}
	public void paymentValidator(String refno,String payment_status,String transaction_id,String Authcode){
		validateString(Authcode, "AuthCode");
		validateString(refno, "refno");
		validateString(payment_status, "payment_status");
		validateString(transaction_id, "transaction_id");
	}

	public void searchValidator(String triptype,String origin,String destination,String depDate,String adult,String kid,String infant,String trips,String cabinClass,String currency,String app_key){
		validateString(triptype, "triptype");
		validateString(origin, "origin");
		validateString(destination, "destination");
		validateDate(depDate, "depDate");
		validateInteger(adult, "adult");
		validateInteger(kid, "kid");
		validateInteger(infant, "infant");
		validateInteger(trips, "trips");
		validateString(cabinClass, "cabinClass");
		validateString(currency, "currency");
		validateString(app_key, "app_key");
		int tripNo=Integer.parseInt(trips);


		if ((!triptype.equals("O")&&!triptype.equals("M")&&!triptype.equals("R"))) {
			throw new HotelException(HotelErrorCodeCustomerEnum.Exception, 
					"Invalid triptype");
		}
		if ((Integer.parseInt(adult)+Integer.parseInt(kid)+Integer.parseInt(infant)>9)) {
			throw new HotelException(HotelErrorCodeCustomerEnum.Exception, 
					"Maximum passengers is 9");
		}
		if ((Integer.parseInt(trips)>5)) {
			throw new HotelException(HotelErrorCodeCustomerEnum.Exception, 
					"Maximum trips is 5");
		}
		if ((triptype.equals("O")||triptype.equals("R"))&&destination.length()!=3) {
			throw new HotelException(HotelErrorCodeCustomerEnum.Exception, 
					"Invalid origin");
		}
		if ((triptype.equals("O")||triptype.equals("R"))&&destination.length()!=3) {
			throw new HotelException(HotelErrorCodeCustomerEnum.Exception, 
					"Invalid destination");
		}
		if ((triptype.equals("M"))&& (tripNo>2)&&(origin.length()!=((tripNo*3)+(tripNo-1)))&&(destination.length()!=((tripNo*3)+(tripNo-1)))) {
			throw new HotelException(HotelErrorCodeCustomerEnum.Exception, 
					"Invalid source or destination or trips");
		}

	}

	public void fareRuleValidator(String farerulevalue,String providercode,String farerulekey){
		validateString(farerulevalue, "farerulevalue");
		validateString(providercode, "providercode");
		validateString(farerulekey, "farerulekey");		
	}

	public void airPriceValidator(String searchkey, String flightindex)
	{
		validateString(flightindex, "flightindex");
		validateString(searchkey, "searchkey");
	}

	public void bookingValidator(String transactionkey)
	{
		validateString(transactionkey, "transactionkey");
	}

	public String getDateformat(String dateInString){

		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
		try {
			Date date = formatter1.parse(dateInString);
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
			dateInString=formatter2.format(date);
		} catch (Exception e) {
			throw new HotelException(HotelErrorCodeCustomerEnum.Exception, 
					"invalid date format");
		}
		return dateInString;
	}
}
