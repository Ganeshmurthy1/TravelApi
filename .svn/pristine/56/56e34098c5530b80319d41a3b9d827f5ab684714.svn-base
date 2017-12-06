package com.tayyarah.flight.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.flight.exception.FlightErrorMessages;
import com.tayyarah.flight.exception.FlightException;
import com.tayyarah.flight.model.FareFlightSegment;
import com.tayyarah.flight.model.FlightPriceResponse;
import com.tayyarah.flight.model.FlightSegments;
import com.tayyarah.flight.model.FlightSegmentsGroup;
import com.tayyarah.flight.model.SearchFlightResponse;
import com.tayyarah.flight.model.Segments;
import com.tayyarah.flight.quotation.dao.FlightTravelRequestDao;
import com.tayyarah.flight.quotation.entity.FlightTravelRequestConnectingFlightTripDetail;
import com.tayyarah.flight.quotation.entity.FlightTravelRequestQuotation;
import com.tayyarah.flight.quotation.entity.FlightTravelRequestTripDetail;
import com.tayyarah.flight.util.FlightWebServiceEndPointValidator;



@RestController
@RequestMapping("/flight/Quote")
public class FlightQuoteSearchPriceController {

	private static FlightWebServiceEndPointValidator flightWebServiceEndPointValidator = new FlightWebServiceEndPointValidator();
	@Autowired
	FlightSearchController flightSearchController;
	@Autowired
	FlightPriceController flightPriceController;
	@Autowired
	FlightTravelRequestDao flightTravelRequestDao;

	static final Logger logger = Logger.getLogger(FlightQuoteSearchPriceController.class);

	@RequestMapping(value = "/search", method = RequestMethod.GET, headers = { "Accept=application/json" }, produces = { "application/json" })
	public @ResponseBody
	FlightPriceResponse quoteSearch(
			@RequestParam(value = "airline", defaultValue = "All") String airline,
			@RequestParam(value = "trips", defaultValue = "1") String trips,
			@RequestParam(value = "triptype") String triptype,
			@RequestParam(value = "origin") String origin,
			@RequestParam(value = "destination") String destination,
			@RequestParam(value = "depDate") String depDate,
			@RequestParam(value = "arvlDate", defaultValue = "20150731") String arvlDate,
			@RequestParam(value = "adult") String adult,
			@RequestParam(value = "kid", defaultValue = "0") String kid,
			@RequestParam(value = "infant", defaultValue = "0") String infant,
			@RequestParam(value = "cabinClass") String cabinClass,
			@RequestParam(value = "currency") String currency,
			@RequestParam(value = "app_key") String app_key,
			@RequestParam(value = "isDynamicMarkup", defaultValue = "false") boolean isDynamicMarkup,
			@RequestParam(value = "isCache", defaultValue = "false") boolean isCache,
			@RequestParam(value = "isDomestic", defaultValue = "false") boolean isDomestic,
			@RequestParam(value = "isSpecialSearch", defaultValue = "false") boolean isSpecialSearch,
			@RequestParam(value = "markupAmount", defaultValue = "0.0") String markupAmount,
			@RequestParam(value = "searchkey") String searchkey,
			@RequestParam(value = "selectedAirline") String selectedAirline,
			@RequestParam(value = "selectedFlightNumber") String selectedFlightNumber,
			@RequestParam(value = "selectedFlightDepartTime") String selectedFlightDepartTime,
			@RequestParam(value = "selectedFlightArrivalTime") String selectedFlightArrivalTime,
			@RequestParam(value = "returnSelectedAirline") String returnSelectedAirline,
			@RequestParam(value = "returnSelectedFlightNumber") String returnSelectedFlightNumber,
			@RequestParam(value = "returnSelectedFlightDepartTime") String returnSelectedFlightDepartTime,
			@RequestParam(value = "returnSelectedFlightArrivalTime") String returnSelectedFlightArrivalTime,
			@RequestParam(value = "flightquoteid") String flightquoteid,
			HttpServletRequest request,
			HttpServletResponse response) {

		FlightPriceResponse flightPriceResponse = null;
		
		try{
			flightWebServiceEndPointValidator.searchqQuoteValidator(selectedAirline, selectedFlightNumber,selectedFlightDepartTime, selectedFlightArrivalTime);
			
			SearchFlightResponse searchFlightResponse = null;
			searchFlightResponse = flightSearchController.search(airline, trips, triptype, origin, destination, depDate, arvlDate, adult, kid, infant, cabinClass, currency, app_key, isDynamicMarkup,isCache, isDomestic, isSpecialSearch, markupAmount, searchkey,false,request, response);

			if(searchFlightResponse!=null){
				FlightTravelRequestQuotation flightTravelRequestQuotation = flightTravelRequestDao.getFlightTravelRequestQuotation(Long.valueOf(flightquoteid)); 
				List<FlightTravelRequestTripDetail> flightTravelRequestTripDetaillist = flightTravelRequestDao.getFlightTravelRequestTripDetailList(flightTravelRequestQuotation.getId());

				String searchKey = searchFlightResponse.getSearchKey();

				/*logger.info("FlightQuoteSearchPriceController triptype" + triptype);
				logger.info("FlightQuoteSearchPriceController searchKey" + searchKey);
				logger.info("FlightQuoteSearchPriceController selectedAirline" + selectedAirline);
				logger.info("FlightQuoteSearchPriceController selectedFlightNumber" + selectedFlightNumber);
				logger.info("FlightQuoteSearchPriceController selectedFlightDepartTime" + selectedFlightDepartTime);
				logger.info("FlightQuoteSearchPriceController selectedFlightArrivalTime" + selectedFlightArrivalTime);*/

				String flightIndex = "";
				if(searchFlightResponse.getFareFlightSegment().size() > 0){
					if(triptype.equalsIgnoreCase("O")){
						String connectingFlightNumber = "";
						HashMap<String, String> segmentsMap = new HashMap<>();
						for (FlightTravelRequestTripDetail flightTravelRequestTripDetail : flightTravelRequestTripDetaillist) {
							List<FlightTravelRequestConnectingFlightTripDetail> flightTravelRequestConnectingFlightTripDetaillist = flightTravelRequestDao.getFlightTravelRequestConnectingFlightTripDetailList(flightTravelRequestTripDetail.getId());
							if(flightTravelRequestConnectingFlightTripDetaillist.size() > 0){
								connectingFlightNumber = flightTravelRequestTripDetail.getFlightNumber()+"/";
								for (int i = 0; i < flightTravelRequestConnectingFlightTripDetaillist.size(); i++) {
									FlightTravelRequestConnectingFlightTripDetail flightTravelRequestConnectingFlightTripDetail = flightTravelRequestConnectingFlightTripDetaillist.get(i);
									connectingFlightNumber += flightTravelRequestConnectingFlightTripDetail.getFlightNumber();
									if(i != flightTravelRequestConnectingFlightTripDetaillist.size() - 1){
										connectingFlightNumber += "/";
									}
								}
							}
						}

						for (FareFlightSegment fareFlightSegment : searchFlightResponse.getFareFlightSegment()) {
							for (int i = 0; i < fareFlightSegment.getFlightSegmentsGroups().size(); i++) {
								FlightSegmentsGroup flightSegmentsGroup = fareFlightSegment.getFlightSegmentsGroups().get(i);
								for (FlightSegments flightSegments : flightSegmentsGroup.getFlightSegments()) {
									String connectflightno = "";
									for (int j = 0; j < flightSegments.getSegments().size(); j++) {
										Segments segments = flightSegments.getSegments().get(j);
										if(!connectingFlightNumber.equalsIgnoreCase("") ){
											connectflightno += segments.getFlight().getNumber();
											if(j != flightSegments.getSegments().size() - 1){
												connectflightno += "/";
											}
											flightIndex = flightSegments.getFlightIndex();
										}else{
											if(selectedAirline.equalsIgnoreCase(segments.getCarrier().getName()) && selectedFlightNumber.equalsIgnoreCase(segments.getFlight().getNumber()) && selectedFlightDepartTime.equalsIgnoreCase(segments.getDepTime()) && selectedFlightArrivalTime.equalsIgnoreCase(segments.getArrTime())){
												flightIndex = flightSegments.getFlightIndex();
												break;
											}
										}
									}
									if(!connectingFlightNumber.equalsIgnoreCase("") )
										segmentsMap.put(flightIndex, connectflightno);
								}
							}
						}
						if(!connectingFlightNumber.equalsIgnoreCase("") ){
							if(segmentsMap.size() > 0){
								for (Entry<String, String> entry : segmentsMap.entrySet()) {
									String key = entry.getKey();
									String value = entry.getValue();
									if(value.equalsIgnoreCase(connectingFlightNumber)){
										flightIndex = key;
										break;
									}
								}
							}
						}
						if(!searchKey.equals("") && !flightIndex.equals("")){
							//flightPriceResponse = flightPriceController.getFareRuleInfo(app_key, searchKey, flightIndex,response);
							// with low fare
							flightPriceResponse = flightPriceController.getFareRuleInfo(app_key, searchKey, flightIndex,true,"","","",true,"","","",request,response);
						}else{
							FlightException flightException =  new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.MATCHED_FLIGHT_NOTFOUND); 
							throw flightException;
						}
					}
					else if(triptype.equalsIgnoreCase("R") && isSpecialSearch){

						String onwardFlightIndex = "";
						String returnFlightInex = "";
						String onwardconnectingFlightNumber = "";
						String returnconnectingFlightNumber = "";
						HashMap<String, String> onwardsegmentsMap = new HashMap<>();
						HashMap<String, String> returnsegmentsMap = new HashMap<>();
						for (int i = 0; i < flightTravelRequestTripDetaillist.size(); i++) {
							FlightTravelRequestTripDetail flightTravelRequestTripDetail = flightTravelRequestTripDetaillist.get(i);
							if(i == 0){
								List<FlightTravelRequestConnectingFlightTripDetail> flightTravelRequestConnectingFlightTripDetaillist = flightTravelRequestDao.getFlightTravelRequestConnectingFlightTripDetailList(flightTravelRequestTripDetail.getId());
								if(flightTravelRequestConnectingFlightTripDetaillist.size() > 0){
									onwardconnectingFlightNumber = flightTravelRequestTripDetail.getFlightNumber()+"/";
									for (int j = 0; j < flightTravelRequestConnectingFlightTripDetaillist.size(); j++) {
										FlightTravelRequestConnectingFlightTripDetail flightTravelRequestConnectingFlightTripDetail = flightTravelRequestConnectingFlightTripDetaillist.get(j);
										onwardconnectingFlightNumber += flightTravelRequestConnectingFlightTripDetail.getFlightNumber();
										if(j != flightTravelRequestConnectingFlightTripDetaillist.size() - 1){
											onwardconnectingFlightNumber += "/";
										}
									}
								}
							}
							if(i == 1){
								List<FlightTravelRequestConnectingFlightTripDetail> flightTravelRequestConnectingFlightTripDetaillist = flightTravelRequestDao.getFlightTravelRequestConnectingFlightTripDetailList(flightTravelRequestTripDetail.getId());
								if(flightTravelRequestConnectingFlightTripDetaillist.size() > 0){
									returnconnectingFlightNumber = flightTravelRequestTripDetail.getFlightNumber()+"/";
									for (int k = 0; k < flightTravelRequestConnectingFlightTripDetaillist.size(); k++) {
										FlightTravelRequestConnectingFlightTripDetail flightTravelRequestConnectingFlightTripDetail = flightTravelRequestConnectingFlightTripDetaillist.get(k);
										returnconnectingFlightNumber += flightTravelRequestConnectingFlightTripDetail.getFlightNumber();
										if(k != flightTravelRequestConnectingFlightTripDetaillist.size() - 1){
											returnconnectingFlightNumber += "/";
										}
									}
								}
							}
						}

						List<FareFlightSegment> onwardFareFlightSegments = new ArrayList<>();
						List<FareFlightSegment> returnFareFlightSegments = new ArrayList<>();
						for (FareFlightSegment fareFlightSegment : searchFlightResponse.getFareFlightSegment()) {
							for (FlightSegmentsGroup flightSegmentsGroup : fareFlightSegment.getFlightSegmentsGroups()) {
								for (FlightSegments flightSegments : flightSegmentsGroup.getFlightSegments()) {
									for (Segments segment : flightSegments.getSegments()) {
										if(segment.getOri().equalsIgnoreCase(origin)){
											onwardFareFlightSegments.add(fareFlightSegment);
										}
										if(segment.getOri().equalsIgnoreCase(destination)){
											returnFareFlightSegments.add(fareFlightSegment);
										}
									}
								}
							}
						}

						for (FareFlightSegment fareFlightSegment : onwardFareFlightSegments) {
							for (int i = 0; i < fareFlightSegment.getFlightSegmentsGroups().size(); i++) {
								FlightSegmentsGroup flightSegmentsGroup = fareFlightSegment.getFlightSegmentsGroups().get(i);

								for (FlightSegments flightSegments : flightSegmentsGroup.getFlightSegments()) {
									String connectflightno = "";
									for (int j = 0; j < flightSegments.getSegments().size(); j++) {
										Segments segments = flightSegments.getSegments().get(j);
											if(!onwardconnectingFlightNumber.equalsIgnoreCase("") && selectedAirline.equalsIgnoreCase(segments.getCarrier().getName()) ){
												if(flightSegments.getSegments().size() > 1){
													connectflightno += segments.getFlight().getNumber();
													if(j != flightSegments.getSegments().size() - 1){
														connectflightno += "/";
													}
												}else{
													connectflightno = segments.getFlight().getNumber();
												}
												onwardFlightIndex = flightSegments.getFlightIndex();
											}
											else{
												if(selectedAirline.equalsIgnoreCase(segments.getCarrier().getName()) && selectedFlightNumber.equalsIgnoreCase(segments.getFlight().getNumber()) && selectedFlightDepartTime.equalsIgnoreCase(segments.getDepTime()) && selectedFlightArrivalTime.equalsIgnoreCase(segments.getArrTime())){
													logger.info("First segments found" +flightSegments.getFlightIndex());
													onwardFlightIndex = flightSegments.getFlightIndex();
                                                    break;
												}
											}

									}
									if(!onwardconnectingFlightNumber.equalsIgnoreCase("") )
										onwardsegmentsMap.put(onwardFlightIndex, connectflightno);

								}

							}

						}

						for (FareFlightSegment fareFlightSegment : returnFareFlightSegments) {
							for (int i = 0; i < fareFlightSegment.getFlightSegmentsGroups().size(); i++) {
								FlightSegmentsGroup flightSegmentsGroup = fareFlightSegment.getFlightSegmentsGroups().get(i);

								for (FlightSegments flightSegments : flightSegmentsGroup.getFlightSegments()) {
									String returnconnectflightno = "";
									for (int j = 0; j < flightSegments.getSegments().size(); j++) {
										Segments segments = flightSegments.getSegments().get(j);
											if(!returnconnectingFlightNumber.equalsIgnoreCase("") && returnSelectedAirline.equalsIgnoreCase(segments.getCarrier().getName()) ){
												if(flightSegments.getSegments().size() > 1){
													returnconnectflightno += segments.getFlight().getNumber();
													if(j != flightSegments.getSegments().size() - 1){
														returnconnectflightno += "/";
													}
												}else{
													returnconnectflightno = segments.getFlight().getNumber();
												}
												returnFlightInex = flightSegments.getFlightIndex();
											}
											else{
												if(returnSelectedAirline.equalsIgnoreCase(segments.getCarrier().getName()) && returnSelectedFlightNumber.equalsIgnoreCase(segments.getFlight().getNumber()) && returnSelectedFlightDepartTime.equalsIgnoreCase(segments.getDepTime()) && returnSelectedFlightArrivalTime.equalsIgnoreCase(segments.getArrTime())){
													logger.info("First segments found" +flightSegments.getFlightIndex());
													returnFlightInex = flightSegments.getFlightIndex();
                                                     break;
												}
											}

									}
									if(!returnconnectingFlightNumber.equalsIgnoreCase("") )
										returnsegmentsMap.put(returnFlightInex, returnconnectflightno);

								}

							}

						}

						if(!onwardconnectingFlightNumber.equalsIgnoreCase("") ){
							if(onwardsegmentsMap.size() > 0){
								for (Entry<String, String> entry : onwardsegmentsMap.entrySet()) {
									String key = entry.getKey();
									String value = entry.getValue();
									if(value.equalsIgnoreCase(onwardconnectingFlightNumber)){
										onwardFlightIndex = key;
										break;
									}
								}
							}
						}

						if(!returnconnectingFlightNumber.equalsIgnoreCase("") ){
							if(returnsegmentsMap.size() > 0){
								for (Entry<String, String> entry : returnsegmentsMap.entrySet()) {
									String key = entry.getKey();
									String value = entry.getValue();
									if(value.equalsIgnoreCase(returnconnectingFlightNumber)){
										returnFlightInex = key;
										break;
									}
								}
							}
						}
						
						flightIndex = onwardFlightIndex+"_"+returnFlightInex;						
					

						if(!searchKey.equals("") && !flightIndex.equals("")){
							flightPriceResponse = flightPriceController.getFareRuleInfo(app_key, searchKey, flightIndex,true,"","","",true,"","","",request,response);
							//flightPriceResponse = flightPriceController.getFareRuleInfo(app_key, searchKey, flightIndex,response);
						}else{
							throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIRPRICE);
						}


					}else{

						String onwardFlightIndex = "";
						String returnFlightInex = "";
						String onwardconnectingFlightNumber = "";
						String returnconnectingFlightNumber = "";
						String mergedFlightNumber = "";
						HashMap<String, String> onwardsegmentsMap = new HashMap<>();
						for (int i = 0; i < flightTravelRequestTripDetaillist.size(); i++) {
							FlightTravelRequestTripDetail flightTravelRequestTripDetail = flightTravelRequestTripDetaillist.get(i);
							if(i == 0){
								List<FlightTravelRequestConnectingFlightTripDetail> flightTravelRequestConnectingFlightTripDetaillist = flightTravelRequestDao.getFlightTravelRequestConnectingFlightTripDetailList(flightTravelRequestTripDetail.getId());
								if(flightTravelRequestConnectingFlightTripDetaillist.size() > 0){
									onwardconnectingFlightNumber = flightTravelRequestTripDetail.getFlightNumber()+"/";
									for (int j = 0; j < flightTravelRequestConnectingFlightTripDetaillist.size(); j++) {
										FlightTravelRequestConnectingFlightTripDetail flightTravelRequestConnectingFlightTripDetail = flightTravelRequestConnectingFlightTripDetaillist.get(j);
										onwardconnectingFlightNumber += flightTravelRequestConnectingFlightTripDetail.getFlightNumber();
										if(j != flightTravelRequestConnectingFlightTripDetaillist.size() - 1){
											onwardconnectingFlightNumber += "/";
										}
									}
								}

							}
							if(i == 1){
								List<FlightTravelRequestConnectingFlightTripDetail> flightTravelRequestConnectingFlightTripDetaillist = flightTravelRequestDao.getFlightTravelRequestConnectingFlightTripDetailList(flightTravelRequestTripDetail.getId());
								if(flightTravelRequestConnectingFlightTripDetaillist.size() > 0){
									returnconnectingFlightNumber = flightTravelRequestTripDetail.getFlightNumber()+"/";
									for (int k = 0; k < flightTravelRequestConnectingFlightTripDetaillist.size(); k++) {
										FlightTravelRequestConnectingFlightTripDetail flightTravelRequestConnectingFlightTripDetail = flightTravelRequestConnectingFlightTripDetaillist.get(k);
										returnconnectingFlightNumber += flightTravelRequestConnectingFlightTripDetail.getFlightNumber();
										if(k != flightTravelRequestConnectingFlightTripDetaillist.size() - 1){
											returnconnectingFlightNumber += "/";
										}
									}
								}

							}
						}


						mergedFlightNumber = onwardconnectingFlightNumber+returnconnectingFlightNumber;


						for (FareFlightSegment fareFlightSegment : searchFlightResponse.getFareFlightSegment()) {
							String connectflightno = "";
							String mergedflightindex = "";
							for (int i = 0; i < fareFlightSegment.getFlightSegmentsGroups().size(); i++) {

								FlightSegmentsGroup flightSegmentsGroup = fareFlightSegment.getFlightSegmentsGroups().get(i);

								for (FlightSegments flightSegments : flightSegmentsGroup.getFlightSegments()) {
									for (int j = 0; j < flightSegments.getSegments().size(); j++) {
										Segments segments = flightSegments.getSegments().get(j);
										if(!onwardconnectingFlightNumber.equalsIgnoreCase("") && selectedAirline.equalsIgnoreCase(segments.getCarrier().getName()) ){
											connectflightno += segments.getFlight().getNumber();
											if(j != flightSegments.getSegments().size() - 1){
												connectflightno += "/";
											}
											if(flightSegments.getFlightIndex().startsWith("TBOO") || flightSegments.getFlightIndex().startsWith("BSO"))
												onwardFlightIndex = flightSegments.getFlightIndex();
											if(flightSegments.getFlightIndex().startsWith("TBOR") || flightSegments.getFlightIndex().startsWith("BSR"))
												returnFlightInex = flightSegments.getFlightIndex();

											mergedflightindex = onwardFlightIndex+"_"+returnFlightInex;
										}
										else{
											if(selectedAirline.equalsIgnoreCase(segments.getCarrier().getName()) && selectedFlightNumber.equalsIgnoreCase(segments.getFlight().getNumber()) && selectedFlightDepartTime.equalsIgnoreCase(segments.getDepTime()) && selectedFlightArrivalTime.equalsIgnoreCase(segments.getArrTime())){
												flightIndex = flightSegments.getFlightIndex();
												break;
											}
										}

									}


								}

							}
							if(!onwardconnectingFlightNumber.equalsIgnoreCase("") )
								onwardsegmentsMap.put(mergedflightindex, connectflightno);
						}


						if(!onwardconnectingFlightNumber.equalsIgnoreCase("") ){
							if(onwardsegmentsMap.size() > 0){
								for (Entry<String, String> entry : onwardsegmentsMap.entrySet()) {
									String key = entry.getKey();
									String value = entry.getValue();
									if(value.equalsIgnoreCase(mergedFlightNumber)){
										flightIndex = key;
									}
								}
							}
						}

						if(!searchKey.equals("") && !flightIndex.equals("")){
							flightPriceResponse = flightPriceController.getFareRuleInfo(app_key, searchKey, flightIndex,true,"","","",true,"","","",request,response);

							//flightPriceResponse = flightPriceController.getFareRuleInfo(app_key, searchKey, flightIndex,response);
						}else{
							throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIRPRICE);
						}
					}
				}
				else{
					throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIRPRICE);
				}
			}
			else{
				throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_FLIGHT);
			}

		}catch(Exception  e){logger.error("Exception",e);
		FlightException flightException =  new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.MATCHED_FLIGHT_NOTFOUND); 
		throw flightException;
		}

		return flightPriceResponse;

	}
}
