package com.tayyarah.flight.util;

//import com.freitas.model.Customer;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.server.UID;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.exception.ErrorMessages;
import com.tayyarah.flight.exception.FlightException;
import com.tayyarah.flight.model.FareFlightSegment;
import com.tayyarah.flight.model.FlightCancelRequest;
import com.tayyarah.flight.model.FlightSegments;
import com.tayyarah.flight.model.FlightSegmentsGroup;
import com.tayyarah.flight.model.Flightsearch;
import com.tayyarah.flight.model.SearchFlightResponse;
import com.tayyarah.flight.model.Segments;


public class FlightWebServiceEndPointValidator {
	static final Logger logger=Logger.getLogger(FlightWebServiceEndPointValidator.class);
	public void validateString(String param, String paramName) {
		if (param == null || param.isEmpty()) {
			throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_PARAMVALUE);

		}
	}

	public void validatepaymentString(String param, String paramName, 
			String refno) {
		if (param == null || param.isEmpty()) {
			throw new FlightException(ErrorCodeCustomerEnum.Exception, 
					paramName + " is required",ErrorMessages.PAY_S_BOOKING_FAILED,refno);
		}
	}



	public void validateLong(Long param, String paramName) {
		if (param == null || param.equals(0L)) {
			throw new FlightException(ErrorCodeCustomerEnum.Exception, 
					paramName + " is required");
		}
	}

	public void validateDate(String param, String paramName) {
		if (param == null || param.isEmpty()) {
			throw new FlightException(ErrorCodeCustomerEnum.Exception, 
					paramName + " is required");
		}else{
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			format.setLenient(false);
			try {
				Date date = format.parse(param);
				//System.out.println("validdate");
			} catch (ParseException e) {
				throw new FlightException(ErrorCodeCustomerEnum.Exception, 
						paramName + " format should be in YYYYMMDD");
				////e.printStackTrace();
			}
		}
	}

	public void validateInteger(String param, String paramName) {
		if (param == null || param.isEmpty()) {
			throw new FlightException(ErrorCodeCustomerEnum.Exception, 
					paramName + " is required");
		}else{
			try{
				Integer.parseInt(param);
			}catch(NumberFormatException e){
				throw new FlightException(ErrorCodeCustomerEnum.Exception, 
						paramName + " is invalid, it should be numeric");	
			}catch (Exception e) {
				throw new FlightException(ErrorCodeCustomerEnum.Exception, 
						paramName + " is invalid, it should be numeric");	

			}

		}
	}
	public void validateDEcimal(String param, String paramName) {
		if (param == null || param.isEmpty()) {
			throw new FlightException(ErrorCodeCustomerEnum.Exception, 
					paramName + " is required");
		}else{
			try{
				new BigDecimal(param);
			}catch(NumberFormatException e){
				throw new FlightException(ErrorCodeCustomerEnum.Exception, 
						paramName + " is invalid, it should be numeric");	
			}catch (Exception e) {
				throw new FlightException(ErrorCodeCustomerEnum.Exception, 
						paramName + " is invalid, it should be numeric");	

			}

		}
	}

	public void validateCustomer(Flightsearch flight) {
		if (flight == null) {
			throw new FlightException(ErrorCodeCustomerEnum.Exception, 
					"flight is empty");
		}
		if (flight.getCurrency() == null || flight.getCurrency().isEmpty()) {
			throw new FlightException(ErrorCodeCustomerEnum.Exception, 
					"curency  is required");
		}
	}

	public void searchValidator(String triptype,String origin,String destination,String depDate,String adult,String kid,String infant,String trips,String cabinClass,String currency,String app_key,String markupAmount){


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
		validateDEcimal(markupAmount, "markupAmount");

		int tripNo=Integer.parseInt(trips);


		if ((currency.length()!=3)) {
			throw new FlightException(ErrorCodeCustomerEnum.Exception, 
					"Invalid currency");
		}

		if ((!triptype.equals("O")&&!triptype.equals("M")&&!triptype.equals("R")&&!triptype.equals("AD")&&!triptype.equals("SR"))) {
			throw new FlightException(ErrorCodeCustomerEnum.Exception, 
					"Invalid triptype");
		}
		if ((Integer.parseInt(adult)+Integer.parseInt(kid)+Integer.parseInt(infant)>9)) {
			throw new FlightException(ErrorCodeCustomerEnum.Exception, 
					"Maximum passengers is 9");
		}
		if ((Integer.parseInt(trips)>5)) {
			throw new FlightException(ErrorCodeCustomerEnum.Exception, 
					"Maximum trips is 5");
		}
		if ((triptype.equals("O")||triptype.equals("R"))&&destination.length()!=3) {
			throw new FlightException(ErrorCodeCustomerEnum.Exception, 
					"Invalid origin");
		}
		if ((triptype.equals("O")||triptype.equals("R"))&&destination.length()!=3) {
			throw new FlightException(ErrorCodeCustomerEnum.Exception, 
					"Invalid destination");
		}
		if ((triptype.equals("M"))&& (tripNo>2)&&(origin.length()!=((tripNo*3)+(tripNo-1)))&&(destination.length()!=((tripNo*3)+(tripNo-1)))) {
			throw new FlightException(ErrorCodeCustomerEnum.Exception, 
					"Invalid source or destination or trips");
		}


	}

	public void searchqQuoteValidator(String selectedAirline,String selectedFlightNumber,String selectedFlightDepartTime,String selectedFlightArrivalTime){


		validateString(selectedAirline, "selectedAirline");
		validateString(selectedFlightNumber, "selectedFlightNumber");
		validateString(selectedFlightDepartTime, "selectedFlightDepartTime");
		validateString(selectedFlightArrivalTime, "selectedFlightArrivalTime");		

	}


	public void fareRuleValidator(String farerulevalue,String providercode,String farerulekey){
		validateString(farerulevalue, "farerulevalue");
		validateString(providercode, "providercode");
		validateString(farerulekey, "farerulekey");
	}

	public void cancelrequestValidator(FlightCancelRequest FlightCancelRequest){
		validateString(FlightCancelRequest.getOrderId(), "orderId");
		validateString(FlightCancelRequest.getUserId(), "userId");
		validateString(FlightCancelRequest.getPassword(), "password");
		validateString(FlightCancelRequest.getMethodtype(), "methodtype");
		validateString(FlightCancelRequest.getRequesttype(), "requesttype");
		validateString(FlightCancelRequest.getCancellationtype(), "cancellationtype");
	}

	public void currencyconverterValidator(String from,String to){
		validateString(from, "from");
		validateString(to, "to");
	}


	public void paymentValidator(String refno,String payment_status,String transaction_id,String Authcode){
		//paymentValidator(refno, response_message, response_code,payment_status,transaction_id);
		validatepaymentString(Authcode, "AuthCode",refno);
		validatepaymentString(refno, "refno",refno);
		validatepaymentString(payment_status, "payment_status",refno);
		validatepaymentString(transaction_id, "transaction_id",refno);
	}


	public void airPriceValidator(String searchkey, String flightindex)
	{
		validateString(flightindex, "flightindex");
		validateString(searchkey, "searchkey");
	}

	public void bookingValidator(String pricekey, String transactionkey,String username, String userid,String paymode)
	{
		validateString(pricekey, "pricekey");
		validateString(transactionkey, "transactionkey");
		validateString(username, "username");
		validateString(userid, "userid");
		validateString(paymode, "paymode");
	}

	public void bookingValidator(String transactionkey)
	{
		validateString(transactionkey, "transactionkey");
	}

	public static String getDateformat(String dateInString){

		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");

		try {

			Date date = formatter1.parse(dateInString);

			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
			dateInString=formatter2.format(date);

		} catch (Exception e) {
			throw new FlightException(ErrorCodeCustomerEnum.Exception, 
					"invalid date format");
		}

		return dateInString;
	}
	public static String getMarkupDateformat(String dateInString){

		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

		try {

			Date date = formatter1.parse(dateInString);

			SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MM-yyyy");
			dateInString=formatter2.format(date);

		} catch (Exception e) {
			throw new FlightException(ErrorCodeCustomerEnum.Exception, 
					"invalid date format");
		}

		return dateInString;
	}

	public static String getBluestarDate(String dateInString){

		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

		try {

			Date date = formatter1.parse(dateInString);

			SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
			dateInString=formatter2.format(date);

		} catch (Exception e) {
			logger.error("Exception",e);
			//e.printStackTrace();
			throw new FlightException(ErrorCodeCustomerEnum.Exception, 
					"invalid date format");
		}

		return dateInString;
	}
	public static String getDateYYYYMMDD(String dateInString){

		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

		try {

			Date date = formatter1.parse(dateInString);

			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");
			dateInString=formatter2.format(date);

		} catch (Exception e) {
			logger.error("Exception",e);
			////e.printStackTrace();
			throw new FlightException(ErrorCodeCustomerEnum.Exception, 
					"invalid date format");
		}

		return dateInString;
	}

	public static String getBluestarDateFromDate(Date date){

		String dateInString="";

		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

		try {

			//	Date date = formatter1.parse(dateInString);

			SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
			dateInString=formatter2.format(date);

		} catch (Exception e) {
			logger.error("Exception",e);
			//e.printStackTrace();
			throw new FlightException(ErrorCodeCustomerEnum.Exception, 
					"invalid date format");
		}

		return dateInString;
	}
	public static String convertBlueToTravel(String dateInString){

		SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");

		try {

			Date date = formatter1.parse(dateInString);

			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
			dateInString=formatter2.format(date);

		} catch (Exception e) {
			logger.error("Exception",e);
			//e.printStackTrace();
			throw new FlightException(ErrorCodeCustomerEnum.Exception, 
					"invalid date format");
		}

		return dateInString;
	}

	public static String usingDatefromTimeStamp(long input){
		Date date = new Date(input);
		Calendar cal = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setCalendar(cal);
		cal.setTime(date); 
		return sdf.format(date);

	}
	void filterFareFlightSegmentForOneway(SearchFlightResponse SFR,List<SearchFlightResponse> flightResponses){


		try {
			SearchFlightResponse searchFlightResponse1=flightResponses.get(0);
			SearchFlightResponse searchFlightResponse2=flightResponses.get(1);

			for(FareFlightSegment fareFlightSegment1:searchFlightResponse1.getFareFlightSegment()){
				for(FlightSegmentsGroup flightSegmentsGroup1:fareFlightSegment1.getFlightSegmentsGroups()){
					for(FlightSegments flightSegments1:flightSegmentsGroup1.getFlightSegments()){
						int i=0;	
						List<Segments> tempSegmentList1=flightSegments1.getSegments();						
						Iterator<Segments> it1 = tempSegmentList1.iterator();
						while(it1.hasNext()){
							Segments segments1=it1.next();
							boolean result=false;
							List<FareFlightSegment> tempFareFlightSegmentList2=searchFlightResponse2.getFareFlightSegment();
							Iterator<FareFlightSegment> itFareFlightSegment2 = tempFareFlightSegmentList2.iterator();

							while(itFareFlightSegment2.hasNext()){
								FareFlightSegment fareFlightSegment2=itFareFlightSegment2.next();
								boolean isFareFlightSegment2 = false;
								Iterator<FlightSegmentsGroup> itFlightSegmentsGroup2 = fareFlightSegment2.getFlightSegmentsGroups().iterator();						
								while(itFlightSegmentsGroup2.hasNext()){
									boolean isFlightSegmentsGroup2=false;
									FlightSegmentsGroup flightSegmentsGroup2=itFlightSegmentsGroup2.next();	
									Iterator<FlightSegments> itFlightSegments2 = flightSegmentsGroup2.getFlightSegments().iterator();						
									while(itFlightSegments2.hasNext()){
										boolean isFlightSegments2=false;
										FlightSegments flightSegments2=itFlightSegments2.next();							
										List<Segments> tempSegmentList2=flightSegments2.getSegments();
										Iterator<Segments> it2 = tempSegmentList2.iterator();
										while(it2.hasNext()){
											Segments segments2=it2.next();
											if((segments1.getOri().equals(segments2.getOri()))&&(segments1.getDest().equals(segments2.getDest()))&&(segments1.getArrDate().equals(segments2.getArrDate()))&&(segments1.getDepDate().equals(segments2.getDepDate()))&&(segments1.getArrTime().equals(segments2.getArrTime()))
													&&(segments1.getCarrier().getCode().equals(segments2.getCarrier().getCode()))&&(segments1.getCabin().getCode().equals(segments2.getCabin().getCode()))&&(segments1.getFlight().getNumber().equals(segments2.getFlight().getNumber()))){
												BigDecimal totalPrice1=new BigDecimal(fareFlightSegment1.getTotalPrice());
												BigDecimal totalPrice2=new BigDecimal(fareFlightSegment2.getTotalPrice());
												if(totalPrice1.compareTo(totalPrice2)>-1){//totalPrice1>==totalPrice2
													it1.remove();													
													result = true;														
												}else{
													it2.remove();
													if(flightSegments2.getSegments().size()==0){
														isFlightSegments2=true;
													}
												}
											}
											if(result)	{
												break;	
											}
										}
										if(result)	{
											break;	
										}
										if(isFlightSegments2){
											itFlightSegments2.remove();
										}
										if(flightSegmentsGroup2.getFlightSegments().size() == 0){											
											isFlightSegmentsGroup2 = true;											
										}
									}
									if(result)	{break;	}
									if(isFlightSegmentsGroup2){
										itFlightSegmentsGroup2.remove();
									}	

									if(fareFlightSegment2.getFlightSegmentsGroups().size() == 0){									
										isFareFlightSegment2 = true;										
									}
								}
								if(result)	{break;	}	
								if(isFareFlightSegment2){
									itFareFlightSegment2.remove();
								}
							}
							i++;
						}						
					}
				}
			}
			SFR.getFareFlightSegment().addAll(searchFlightResponse1.getFareFlightSegment());
			SFR.getFareFlightSegment().addAll(searchFlightResponse2.getFareFlightSegment());
		} catch (Exception e) {

			SearchFlightResponse searchFlightResponse1=flightResponses.get(0);
			SearchFlightResponse searchFlightResponse2=flightResponses.get(1);
			SFR.getFareFlightSegment().addAll(searchFlightResponse1.getFareFlightSegment());
			SFR.getFareFlightSegment().addAll(searchFlightResponse2.getFareFlightSegment());			
			logger.error("Exception", e); 
		}

		for(int i=2;i<flightResponses.size();i++){		
			SearchFlightResponse searchFlightResponseOther= flightResponses.get(i);
			SFR.getFareFlightSegment().addAll(searchFlightResponseOther.getFareFlightSegment());
		}
	}

	void filterFareFlightSegmentRoundtrip(SearchFlightResponse SFR,List<SearchFlightResponse> flightResponses, String Ori, String Dest){
		try {
			SearchFlightResponse searchFlightResponse1=flightResponses.get(0);
			SearchFlightResponse searchFlightResponse2=flightResponses.get(1);
			List<Integer> removableList2=new ArrayList<Integer>();
			for(int indexfareFlightSegment1=0;indexfareFlightSegment1<searchFlightResponse1.getFareFlightSegment().size();indexfareFlightSegment1++){
				FareFlightSegment fareFlightSegment1=searchFlightResponse1.getFareFlightSegment().get(indexfareFlightSegment1);
				for(int k=0;k<1;k++){
					FlightSegmentsGroup flightSegmentsGroup1=fareFlightSegment1.getFlightSegmentsGroups().get(k);
					for(FlightSegments flightSegments1:flightSegmentsGroup1.getFlightSegments()){
						int i=0;	
						List<Segments> tempSegmentList1=flightSegments1.getSegments();
						Iterator<Segments> it1 = tempSegmentList1.iterator();
						while(it1.hasNext()){
							Segments segments1=it1.next();
							boolean result=false;
							List<FareFlightSegment> tempFareFlightSegmentList2=searchFlightResponse2.getFareFlightSegment();
							Iterator<FareFlightSegment> itFareFlightSegment2 = tempFareFlightSegmentList2.iterator();
							int indexfareFlightSegment2=0;
							while(itFareFlightSegment2.hasNext()){
								FareFlightSegment fareFlightSegment2=itFareFlightSegment2.next();
								boolean isFareFlightSegment2=false;							
								Iterator<FlightSegmentsGroup> itFlightSegmentsGroup2 = fareFlightSegment2.getFlightSegmentsGroups().iterator();						
								while(itFlightSegmentsGroup2.hasNext()){
									boolean isFlightSegmentsGroup2=false;
									FlightSegmentsGroup flightSegmentsGroup2=itFlightSegmentsGroup2.next();	
									Iterator<FlightSegments> itFlightSegments2 = flightSegmentsGroup2.getFlightSegments().iterator();						
									while(itFlightSegments2.hasNext()){
										boolean isFlightSegments2=false;
										FlightSegments flightSegments2=itFlightSegments2.next();
										List<Segments> tempSegmentList2=flightSegments2.getSegments();
										int size=tempSegmentList2.size();										
										Iterator<Segments> it2 = tempSegmentList2.iterator();									
										while(it2.hasNext()){
											Segments segments2 = it2.next();											
											if((segments1.getOri().equals(segments2.getOri()))&&(segments1.getDest().equals(segments2.getDest()))&&(segments1.getArrDate().equals(segments2.getArrDate()))&&(segments1.getDepDate().equals(segments2.getDepDate()))&&(segments1.getArrTime().equals(segments2.getArrTime()))
													&&(segments1.getCarrier().getCode().equals(segments2.getCarrier().getCode()))&&(segments1.getCabin().getCode().equals(segments2.getCabin().getCode()))&&(segments1.getFlight().getNumber().equals(segments2.getFlight().getNumber()))){
												BigDecimal totalPrice1=new BigDecimal(fareFlightSegment1.getTotalPrice());
												BigDecimal totalPrice2=new BigDecimal(fareFlightSegment2.getTotalPrice());
												if(!segments1.getDest().equals(Dest)){
													if(it1.hasNext()){
														segments1=it1.next();
													}if(it2.hasNext()){
														segments2=it2.next();
													}
													if((segments1.getOri().equals(segments2.getOri()))&&(segments1.getDest().equals(segments2.getDest()))&&(segments1.getArrDate().equals(segments2.getArrDate()))&&(segments1.getDepDate().equals(segments2.getDepDate()))&&(segments1.getArrTime().equals(segments2.getArrTime()))
															&&(segments1.getCarrier().getCode().equals(segments2.getCarrier().getCode()))&&(segments1.getCabin().getCode().equals(segments2.getCabin().getCode()))&&(segments1.getFlight().getNumber().equals(segments2.getFlight().getNumber()))){
														if(!segments1.getDest().equals(Dest)){
															if(it1.hasNext()){
																segments1=it1.next();
															}if(it2.hasNext()){
																segments2=it2.next();
															}
															if((segments1.getOri().equals(segments2.getOri()))&&(segments1.getDest().equals(segments2.getDest()))&&(segments1.getArrDate().equals(segments2.getArrDate()))&&(segments1.getDepDate().equals(segments2.getDepDate()))&&(segments1.getArrTime().equals(segments2.getArrTime()))
																	&&(segments1.getCarrier().getCode().equals(segments2.getCarrier().getCode()))&&(segments1.getCabin().getCode().equals(segments2.getCabin().getCode()))&&(segments1.getFlight().getNumber().equals(segments2.getFlight().getNumber()))){
																logger.info("common3");
																if(!segments1.getDest().equals(Dest)){

																	if(it1.hasNext()){
																		segments1=it1.next();
																	}if(it2.hasNext()){
																		segments2=it2.next();
																	}
																	if((segments1.getOri().equals(segments2.getOri()))&&(segments1.getDest().equals(segments2.getDest()))&&(segments1.getArrDate().equals(segments2.getArrDate()))&&(segments1.getDepDate().equals(segments2.getDepDate()))&&(segments1.getArrTime().equals(segments2.getArrTime()))
																			&&(segments1.getCarrier().getCode().equals(segments2.getCarrier().getCode()))&&(segments1.getCabin().getCode().equals(segments2.getCabin().getCode()))&&(segments1.getFlight().getNumber().equals(segments2.getFlight().getNumber()))){
																		logger.info("common4");
																		if(!segments1.getDest().equals(Dest)){

																			if(it1.hasNext()){
																				segments1=it1.next();
																			}if(it2.hasNext()){
																				segments2=it2.next();
																			}
																			if((segments1.getOri().equals(segments2.getOri()))&&(segments1.getDest().equals(segments2.getDest()))&&(segments1.getArrDate().equals(segments2.getArrDate()))&&(segments1.getDepDate().equals(segments2.getDepDate()))&&(segments1.getArrTime().equals(segments2.getArrTime()))
																					&&(segments1.getCarrier().getCode().equals(segments2.getCarrier().getCode()))&&(segments1.getCabin().getCode().equals(segments2.getCabin().getCode()))&&(segments1.getFlight().getNumber().equals(segments2.getFlight().getNumber()))){
																				logger.info("common5");
																				if(!segments1.getDest().equals(Dest)){

																					if(it1.hasNext()){
																						segments1=it1.next();
																					}if(it2.hasNext()){
																						segments2=it2.next();
																					}
																					if((segments1.getOri().equals(segments2.getOri()))&&(segments1.getDest().equals(segments2.getDest()))&&(segments1.getArrDate().equals(segments2.getArrDate()))&&(segments1.getDepDate().equals(segments2.getDepDate()))&&(segments1.getArrTime().equals(segments2.getArrTime()))
																							&&(segments1.getCarrier().getCode().equals(segments2.getCarrier().getCode()))&&(segments1.getCabin().getCode().equals(segments2.getCabin().getCode()))&&(segments1.getFlight().getNumber().equals(segments2.getFlight().getNumber()))){
																						logger.info("common6");
																					}
																				}else
																					if(segments1.getDest().equals(Dest)){
																						callFilterAgain(searchFlightResponse1,searchFlightResponse2,indexfareFlightSegment1,indexfareFlightSegment2,totalPrice1,totalPrice2,it1,it2,result,removableList2,Ori);
																						if(flightSegments2.getSegments().size()==0){
																							//	//logger.info("inside the size zero");
																							System.out.println("inside the size zero");
																							isFlightSegments2=true;
																						}	
																					}																			}
																		}else
																			if(segments1.getDest().equals(Dest)){
																				callFilterAgain(searchFlightResponse1,searchFlightResponse2,indexfareFlightSegment1,indexfareFlightSegment2,totalPrice1,totalPrice2,it1,it2,result,removableList2,Ori);
																				if(flightSegments2.getSegments().size()==0){
																					isFlightSegments2=true;
																				}	
																			}
																	}
																}else
																	if(segments1.getDest().equals(Dest)){
																		callFilterAgain(searchFlightResponse1,searchFlightResponse2,indexfareFlightSegment1,indexfareFlightSegment2,totalPrice1,totalPrice2,it1,it2,result,removableList2,Ori);
																		if(flightSegments2.getSegments().size()==0){
																			isFlightSegments2 = true;
																		}	
																	}
																}
														}else
															if(segments1.getDest().equals(Dest)){
																callFilterAgain(searchFlightResponse1,searchFlightResponse2,indexfareFlightSegment1,indexfareFlightSegment2,totalPrice1,totalPrice2,it1,it2,result,removableList2,Ori);
																if(flightSegments2.getSegments().size()==0){
																isFlightSegments2=true;

																}	
															}
													}
												}else
													if(segments1.getDest().equals(Dest)){
														callFilterAgain(searchFlightResponse1,searchFlightResponse2,indexfareFlightSegment1,indexfareFlightSegment2,totalPrice1,totalPrice2,it1,it2,result,removableList2,Ori);
														if(flightSegments2.getSegments().size()==0||size!=flightSegments2.getSegments().size()){
												          	isFlightSegments2=true;
														}	
													}
											}
											if(result)	{
												break;	
												}
											break;
											}

										if(isFlightSegments2){
											itFlightSegments2.remove();
									}

										if(flightSegmentsGroup2.getFlightSegments().size()==0){
										isFlightSegmentsGroup2=true;								
										}
									}
									if(result)	{break;	}
									if(isFlightSegmentsGroup2){
										
									}	

									if(fareFlightSegment2.getFlightSegmentsGroups().size()==0){										
										fareFlightSegment2.getFlightSegmentsGroups().remove(0);
										isFareFlightSegment2=true;										
									}
									break;//breaking the while loop for taking only one group	
								}
								if(result)	{break;	}	
								if(isFareFlightSegment2){
									itFareFlightSegment2.remove();
								}
								indexfareFlightSegment2++;
							}
							i++;
							break;
							}						
					}
				}
			}
			for(int i=removableList2.size()-1;i>=0;i--){
				int getnum=removableList2.get(i);
				searchFlightResponse2.getFareFlightSegment().remove(getnum);
			}
			SFR.getFareFlightSegment().addAll(searchFlightResponse1.getFareFlightSegment());
			SFR.getFareFlightSegment().addAll(searchFlightResponse2.getFareFlightSegment());
		} catch (Exception e) {		
			SearchFlightResponse searchFlightResponse1=flightResponses.get(0);
			SearchFlightResponse searchFlightResponse2=flightResponses.get(1);
			SFR.getFareFlightSegment().addAll(searchFlightResponse1.getFareFlightSegment());
			SFR.getFareFlightSegment().addAll(searchFlightResponse2.getFareFlightSegment());			
			logger.error("Exception", e); 
		}
		for(int i=2;i<flightResponses.size();i++){		
			SearchFlightResponse searchFlightResponseOther= flightResponses.get(i);
			SFR.getFareFlightSegment().addAll(searchFlightResponseOther.getFareFlightSegment());
		}
	}
	
	void callFilterAgain(SearchFlightResponse searchFlightResponse1,SearchFlightResponse searchFlightResponse2,int indexfareFlightSegment1,int indexfareFlightSegment2,BigDecimal totalPrice1,BigDecimal totalPrice2,Iterator<Segments> it1parent,Iterator<Segments> it2parent,boolean resultparent,List<Integer> removableList2,String Dest){
		if(searchFlightResponse1!=null){
			FareFlightSegment fareFlightSegment1=searchFlightResponse1.getFareFlightSegment().get(indexfareFlightSegment1);
			if(fareFlightSegment1!=null){
				FlightSegmentsGroup flightSegmentsGroup1=fareFlightSegment1.getFlightSegmentsGroups().get(1);
				for(FlightSegments flightSegments1:flightSegmentsGroup1.getFlightSegments()){
					int i = 0;	
					List<Segments> tempSegmentList1=flightSegments1.getSegments();
					Iterator<Segments> it1 = tempSegmentList1.iterator();
					while(it1.hasNext()){
						Segments segments1 = it1.next();
						boolean result = false;
						List<FareFlightSegment> tempFareFlightSegmentList2=searchFlightResponse2.getFareFlightSegment();
						if(tempFareFlightSegmentList2.size()>indexfareFlightSegment2){
							FareFlightSegment fareFlightSegment2=tempFareFlightSegmentList2.get(indexfareFlightSegment2);
							boolean isFareFlightSegment2=false;
							List<FlightSegmentsGroup> tempFlightSegmentsGroupList2=fareFlightSegment2.getFlightSegmentsGroups();
							while(tempFlightSegmentsGroupList2.size()>1){
								boolean isFlightSegmentsGroup2=false;
								FlightSegmentsGroup flightSegmentsGroup2=tempFlightSegmentsGroupList2.get(1);	
								Iterator<FlightSegments> itFlightSegments2 = flightSegmentsGroup2.getFlightSegments().iterator();						
								while(itFlightSegments2.hasNext()){
									boolean isFlightSegments2=false;
									FlightSegments flightSegments2=itFlightSegments2.next();							
									List<Segments> tempSegmentList2=flightSegments2.getSegments();
									Iterator<Segments> it2 = tempSegmentList2.iterator();
									int size=flightSegments2.getSegments().size();							
									while(it2.hasNext()){
										Segments segments2=it2.next();
										if((segments1.getOri().equals(segments2.getOri()))&&(segments1.getDest().equals(segments2.getDest()))&&(segments1.getArrDate().equals(segments2.getArrDate()))&&(segments1.getDepDate().equals(segments2.getDepDate()))&&(segments1.getArrTime().equals(segments2.getArrTime()))
												&&(segments1.getCarrier().getCode().equals(segments2.getCarrier().getCode()))&&(segments1.getCabin().getCode().equals(segments2.getCabin().getCode()))&&(segments1.getFlight().getNumber().equals(segments2.getFlight().getNumber()))){
											if(!segments1.getDest().equals(Dest)){
												if(it1.hasNext()){
													segments1=it1.next();
												}if(it2.hasNext()){
													segments2=it2.next();
												}
												if((segments1.getOri().equals(segments2.getOri()))&&(segments1.getDest().equals(segments2.getDest()))&&(segments1.getArrDate().equals(segments2.getArrDate()))&&(segments1.getDepDate().equals(segments2.getDepDate()))&&(segments1.getArrTime().equals(segments2.getArrTime()))
														&&(segments1.getCarrier().getCode().equals(segments2.getCarrier().getCode()))&&(segments1.getCabin().getCode().equals(segments2.getCabin().getCode()))&&(segments1.getFlight().getNumber().equals(segments2.getFlight().getNumber()))){
													if(!segments1.getDest().equals(Dest)){
														if(it1.hasNext()){
															segments1=it1.next();
														}if(it2.hasNext()){
															segments2=it2.next();
														}
														if((segments1.getOri().equals(segments2.getOri()))&&(segments1.getDest().equals(segments2.getDest()))&&(segments1.getArrDate().equals(segments2.getArrDate()))&&(segments1.getDepDate().equals(segments2.getDepDate()))&&(segments1.getArrTime().equals(segments2.getArrTime()))
																&&(segments1.getCarrier().getCode().equals(segments2.getCarrier().getCode()))&&(segments1.getCabin().getCode().equals(segments2.getCabin().getCode()))&&(segments1.getFlight().getNumber().equals(segments2.getFlight().getNumber()))){
														
															if(!segments1.getDest().equals(Dest)){
																if(it1.hasNext()){
																	segments1=it1.next();
																}if(it2.hasNext()){
																	segments2=it2.next();
																}
																if((segments1.getOri().equals(segments2.getOri()))&&(segments1.getDest().equals(segments2.getDest()))&&(segments1.getArrDate().equals(segments2.getArrDate()))&&(segments1.getDepDate().equals(segments2.getDepDate()))&&(segments1.getArrTime().equals(segments2.getArrTime()))
																		&&(segments1.getCarrier().getCode().equals(segments2.getCarrier().getCode()))&&(segments1.getCabin().getCode().equals(segments2.getCabin().getCode()))&&(segments1.getFlight().getNumber().equals(segments2.getFlight().getNumber()))){
																	if(!segments1.getDest().equals(Dest)){
																	if(it1.hasNext()){
																			segments1=it1.next();
																		}if(it2.hasNext()){
																			segments2=it2.next();
																		}
																		if((segments1.getOri().equals(segments2.getOri()))&&(segments1.getDest().equals(segments2.getDest()))&&(segments1.getArrDate().equals(segments2.getArrDate()))&&(segments1.getDepDate().equals(segments2.getDepDate()))&&(segments1.getArrTime().equals(segments2.getArrTime()))
																				&&(segments1.getCarrier().getCode().equals(segments2.getCarrier().getCode()))&&(segments1.getCabin().getCode().equals(segments2.getCabin().getCode()))&&(segments1.getFlight().getNumber().equals(segments2.getFlight().getNumber()))){
																			if(!segments1.getDest().equals(Dest)){
																				if(it1.hasNext()){
																					segments1=it1.next();
																				}if(it2.hasNext()){
																					segments2=it2.next();
																				}
																				if((segments1.getOri().equals(segments2.getOri()))&&(segments1.getDest().equals(segments2.getDest()))&&(segments1.getArrDate().equals(segments2.getArrDate()))&&(segments1.getDepDate().equals(segments2.getDepDate()))&&(segments1.getArrTime().equals(segments2.getArrTime()))
																						&&(segments1.getCarrier().getCode().equals(segments2.getCarrier().getCode()))&&(segments1.getCabin().getCode().equals(segments2.getCabin().getCode()))&&(segments1.getFlight().getNumber().equals(segments2.getFlight().getNumber()))){
																				}else{
																					if(totalPrice1.compareTo(totalPrice2)>-1){//totalPrice1>==totalPrice2
																						it1.remove();
																						it1parent.remove();					
																						result=true;
																						resultparent=true;
																					}else{
																						it2.remove();
																						it2parent.remove();	
																						if(flightSegments2.getSegments().size()==0||size!=flightSegments2.getSegments().size()){
																							isFlightSegments2 = true;
																						}
																					}	
																				}
																				if(result)	{
																					break;	}
																			}	
																		}else{
																			if(totalPrice1.compareTo(totalPrice2)>-1){//totalPrice1>==totalPrice2
																				it1.remove();
																				it1parent.remove();					
																				result=true;
																				resultparent=true;
																			}else{
																				it2.remove();
																				it2parent.remove();	
																				if(flightSegments2.getSegments().size()==0||size!=flightSegments2.getSegments().size()){
																					isFlightSegments2=true;
																				}
																			}	
																		}
																		if(result)	{
																			break;	}
																	}	
																}else{
																	if(totalPrice1.compareTo(totalPrice2)>-1){//totalPrice1>==totalPrice2
																		it1.remove();
																		it1parent.remove();					
																		result=true;
																		resultparent=true;
																	}else{
																		it2.remove();
																		it2parent.remove();
																		if(flightSegments2.getSegments().size()==0||size!=flightSegments2.getSegments().size()){
																			isFlightSegments2=true;
																		}
																	}	
																}
																if(result)	{
																	break;	}
															}	
														}else{

															if(totalPrice1.compareTo(totalPrice2)>-1){//totalPrice1>==totalPrice2
																it1.remove();
																it1parent.remove();					
																result=true;
																resultparent=true;
															}else{
																it2.remove();
																it2parent.remove();	
																if(flightSegments2.getSegments().size()==0||size!=flightSegments2.getSegments().size()){
																	isFlightSegments2=true;
																}
															}	
														}
														if(result)	{
															break;	}
													}	
												}else{

													if(totalPrice1.compareTo(totalPrice2)>-1){//totalPrice1>==totalPrice2
														it1.remove();
														it1parent.remove();					
														result=true;
														resultparent=true;
													}else{
														it2.remove();
														it2parent.remove();		
														if(flightSegments2.getSegments().size()==0||size!=flightSegments2.getSegments().size()){
															isFlightSegments2=true;
														}
													}	
												}
												if(result)	{
													break;	}
											}					
											else{
												if(totalPrice1.compareTo(totalPrice2)>-1){//totalPrice1>==totalPrice2
													it1.remove();
													it1parent.remove();													
													result=true;
													resultparent=true;														
												}else{
													it2.remove();
													it2parent.remove();
													if(flightSegments2.getSegments().size()==0||size!=flightSegments2.getSegments().size()){													
														isFlightSegments2=true;
													}
												}
											}
										}
										if(result)	{
											break;	}
										logger.info("end of the loop..."+"segments1.getArrDate :"+segments1.getFareInfoRef()+"segments2.getArrDate :"+segments2.getFareInfoRef()+"segments1.getFlight().getNumber :"+segments1.getFlight().getNumber()+"segments2.getFlight().getNumber :"+segments2.getFlight().getNumber());

									}
									if(result)	{
										break;	}
									if(isFlightSegments2){
										itFlightSegments2.remove();
									}
									if(flightSegmentsGroup2.getFlightSegments().size()==0){						
										isFlightSegmentsGroup2=true;
									}
								}
								if(result)	{break;	}
								if(isFlightSegmentsGroup2){								
									tempFlightSegmentsGroupList2.remove(1);
								}
								if(fareFlightSegment2.getFlightSegmentsGroups().size()==0){
									isFareFlightSegment2=true;
								}
							}						
							if(isFareFlightSegment2){
								removableList2.add(indexfareFlightSegment2);
							}
						}
						i++;
						if(result)	{break;	}
					}					
				}
			}
		}
	}	
}