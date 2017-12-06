package com.tayyarah.flight.util.api.travelport;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.tayyarah.common.util.AmountRoundingModeUtil;
import com.tayyarah.company.entity.CompanyConfig;
import com.tayyarah.flight.entity.FlightOrderRowCommission;
import com.tayyarah.flight.model.CalendarResponseData;
import com.tayyarah.flight.model.FareFlightSegment;
import com.tayyarah.flight.model.FlightMarkUpConfig;
import com.tayyarah.flight.model.FlightPriceResponse;
import com.tayyarah.flight.model.Flightsearch;
import com.tayyarah.flight.model.MarkupCommissionDetails;
import com.tayyarah.flight.model.PassengerFareBreakUp;
import com.tayyarah.flight.util.FlightWebServiceEndPointValidator;


public class UmarkUpServiceCall {
	static final Logger logger = Logger.getLogger(UmarkUpServiceCall.class);

	public static void getMarkupValues(Flightsearch flightsearch,Map<String,List<FlightMarkUpConfig>> markupMap, String Airline,
			FareFlightSegment fare,String fareBasisCode) throws Exception {

		String BasePriceAmount = fare.getBasePriceWithoutMarkup();
		String TaxesAmount = fare.getTaxesWithoutMarkup();
		BigDecimal TPAmount;
		BigDecimal BPAmount = new BigDecimal(BasePriceAmount);
		BigDecimal TAmount = new BigDecimal(TaxesAmount);
		TPAmount = BPAmount.add(TAmount);
		BigDecimal totalAdult = new BigDecimal(flightsearch.getAdult());
		BigDecimal totalKid = new BigDecimal(flightsearch.getKid());
		BigDecimal totalInfant = new BigDecimal(flightsearch.getInfant());
		BigDecimal totalPassengers = totalAdult.add(totalKid).add(totalInfant);
		Map<String, List<FlightMarkUpConfig>> sortedFlightMarkUpConfiglistMap = new TreeMap<String,List<FlightMarkUpConfig>>();
		if(markupMap!=null && markupMap.size()>0)
		{
			sortedFlightMarkUpConfiglistMap.putAll(markupMap);
			if(sortedFlightMarkUpConfiglistMap!=null && sortedFlightMarkUpConfiglistMap.size()>0)
			{
				for (Map.Entry<String,List<FlightMarkUpConfig>> entry : sortedFlightMarkUpConfiglistMap.entrySet()) {
					List<FlightMarkUpConfig> FlightMarkUpConfiglist = entry.getValue();		
					if (FlightMarkUpConfiglist != null) {
						int k = 0;
						boolean result=false;
						BigDecimal TEMPTAAmount = TAmount;
						for (int i = 0; i < FlightMarkUpConfiglist.size(); i++) {
							FlightMarkUpConfig flightMarkUpConfig = FlightMarkUpConfiglist
									.get(i);
							boolean accumulative = flightMarkUpConfig.isAccumulative();
							boolean fixedAmount = flightMarkUpConfig.isFixedAmount();
							String markup = flightMarkUpConfig.getMarkup();						
							if (isMarkupAppliable(flightsearch, flightMarkUpConfig, Airline,fareBasisCode)) {
								BigDecimal MarkupValue = new BigDecimal(markup);

								// Add markup per passengers
								MarkupValue = MarkupValue.multiply(totalPassengers);
								if (k == 0 && !accumulative) {
									if (fixedAmount) {
										TAmount = TAmount.add(MarkupValue);
										TPAmount = BPAmount.add(TAmount);
									} else {
										TAmount = TAmount.add((TEMPTAAmount
												.multiply(MarkupValue))
												.divide(new BigDecimal("100")));
										TPAmount = BPAmount.add(TAmount);
									}									
									break;

								} else if (k != 0 && !accumulative&&result) {
									continue;
								}
								else if (k != 0 && !accumulative&&!result) {
									if (fixedAmount) {
										TAmount = TAmount.add(MarkupValue);
										TPAmount = BPAmount.add(TAmount);
									} else {
										TAmount = TAmount.add((TEMPTAAmount
												.multiply(MarkupValue))
												.divide(new BigDecimal("100")));
										TPAmount = BPAmount.add(TAmount);
									}
									break;
								}else if (accumulative){
									if (fixedAmount) {
										TAmount = TAmount.add(MarkupValue);
										TPAmount = BPAmount.add(TAmount);
									} else {
										TAmount = TAmount.add((TEMPTAAmount
												.multiply(MarkupValue))
												.divide(new BigDecimal("100")));
										TPAmount = BPAmount.add(TAmount);
									}
									result=true;
								}
								k++;
							}
						}
					}					
				}
			}
		}
		fare.setBasePrice(String.valueOf(BPAmount));
		fare.setTotalPrice(String.valueOf(TPAmount));
		fare.setTaxes(String.valueOf(TAmount));
	}


	public static void getMarkupValuesForEachCOMpany(Flightsearch flightsearch,Map<String,List<FlightMarkUpConfig>> markupMap, String Airline,
			FareFlightSegment fare,MarkupCommissionDetails markupCommissionDetails,String fareBasisCode) throws Exception {

		BigDecimal TAmount = new BigDecimal(fare.getTaxesWithoutMarkup()).divide(AmountRoundingModeUtil.roundingMode(flightsearch.getBaseToBookingExchangeRate()));
		Map<String, BigDecimal> companyMarkupMap=new HashMap<String, BigDecimal>();
		Map<String, List<FlightMarkUpConfig>> sortedFlightMarkUpConfiglistMap = new TreeMap<String,List<FlightMarkUpConfig>>();
		if(markupMap!=null && markupMap.size()>0)
		{
			sortedFlightMarkUpConfiglistMap.putAll(markupMap);
			if(sortedFlightMarkUpConfiglistMap!=null && sortedFlightMarkUpConfiglistMap.size()>0)
			{
				for (Map.Entry<String,List<FlightMarkUpConfig>> entry : sortedFlightMarkUpConfiglistMap.entrySet()) {
					List<FlightMarkUpConfig> FlightMarkUpConfiglist = entry.getValue();
					boolean result = false;
					BigDecimal Markup = BigDecimal.ZERO;
					if (FlightMarkUpConfiglist != null) {
						int k = 0;
						BigDecimal TEMPTAAmount = TAmount;
						for (int i = 0; i < FlightMarkUpConfiglist.size(); i++) {
							FlightMarkUpConfig flightMarkUpConfig = FlightMarkUpConfiglist
									.get(i);
							boolean accumulative = flightMarkUpConfig.isAccumulative();
							// If
							// this
							// is
							// true
							// we
							// shud
							// apply
							// all
							// the
							// Markups
							boolean fixedAmount = flightMarkUpConfig.isFixedAmount();
							String markup = flightMarkUpConfig.getMarkup();
							if (isMarkupAppliable(flightsearch, flightMarkUpConfig, Airline,fareBasisCode)) {
								BigDecimal MarkupValue = new BigDecimal(markup);
								// Set Markup for Onward and Return for Special Return (SR)
								if(flightsearch.isSpecialSearch() && flightsearch.getTripType().equalsIgnoreCase("SR"))
									MarkupValue = MarkupValue.multiply(new BigDecimal(2));									
								if (k == 0 && !accumulative) {
									if (fixedAmount) {
										Markup = Markup.add(MarkupValue);								
									} else {
										Markup = Markup.add((TEMPTAAmount
												.multiply(MarkupValue))
												.divide(new BigDecimal("100")));
										TAmount = TAmount.add((TEMPTAAmount
												.multiply(MarkupValue))
												.divide(new BigDecimal("100")));										
									}
									break;
								} else if (k != 0 && !accumulative&&result) {
									continue;
								}
								else if (k != 0 && !accumulative&&!result) {
									if (fixedAmount) {									
										Markup = Markup.add(MarkupValue);										
									} else {
										Markup = Markup.add((TEMPTAAmount
												.multiply(MarkupValue))
												.divide(new BigDecimal("100")));
										TAmount = TAmount.add((TEMPTAAmount
												.multiply(MarkupValue))
												.divide(new BigDecimal("100")));								
									}
									break;
								}else if (accumulative){
									if (fixedAmount) {
										TAmount = TAmount.add(MarkupValue);
										Markup = Markup.add(MarkupValue);									
									} else {
										Markup = Markup.add((TEMPTAAmount
												.multiply(MarkupValue))
												.divide(new BigDecimal("100")));
										TAmount = TAmount.add((TEMPTAAmount
												.multiply(MarkupValue))
												.divide(new BigDecimal("100")));										
									}
									result=true;
								}
								k++;
							}							
						}						
					}
					companyMarkupMap.put(entry.getKey(), Markup);
				}
			}
		}
		markupCommissionDetails.setCompnayMarkupMap(companyMarkupMap);
	}

	public static void getCommissionWithMarkupValuesForEachCOMpany(List<FlightOrderRowCommission> flightOrderRowCommissions, Flightsearch flightsearch,Map<String,List<FlightMarkUpConfig>> markupMap, String Airline,
			FareFlightSegment fare,MarkupCommissionDetails markupCommissionDetails, String fareBasisCode) throws Exception {
		BigDecimal totalAmount;
		BigDecimal totalBaseFare = new BigDecimal(fare.getBasePriceWithoutMarkup()).divide(AmountRoundingModeUtil.roundingMode(flightsearch.getBaseToBookingExchangeRate()));
		BigDecimal totalTaxAmount = new BigDecimal(fare.getTaxesWithoutMarkup()).divide(AmountRoundingModeUtil.roundingMode(flightsearch.getBaseToBookingExchangeRate()));
		totalAmount = totalBaseFare.add(totalTaxAmount);
		if(flightOrderRowCommissions!=null && flightOrderRowCommissions.size()>0)
		{
			if(flightOrderRowCommissions.size()>1)
				Collections.sort(flightOrderRowCommissions, new comapnyIDComparator());
			for (int m = 0; m < flightOrderRowCommissions.size(); m++) {
				FlightOrderRowCommission flightOrderRowCommission = flightOrderRowCommissions.get(m);
				BigDecimal compnaycommissionAMount=new BigDecimal("0.00");
				if(flightOrderRowCommission.getRateType().equalsIgnoreCase("Commission")){
					if(flightOrderRowCommission.getCommissionType().equalsIgnoreCase("Fixed"))
					{
						compnaycommissionAMount=flightOrderRowCommission.getCommission();
					}
					else if(flightOrderRowCommission.isSheetMode())
					{
						//to calculate base without iata
						BigDecimal originalBase = totalBaseFare;
						BigDecimal commisionIata = new BigDecimal("0.00");
						BigDecimal commisionPlb = new BigDecimal("0.00");

						try{
							originalBase = totalBaseFare.divide(new BigDecimal(1).add(AmountRoundingModeUtil.roundingMode(flightOrderRowCommission.getIataCommission().divide(new BigDecimal("100")))));
							commisionIata = originalBase.multiply(AmountRoundingModeUtil.roundingMode(flightOrderRowCommission.getIataCommission().divide(new BigDecimal("100"))));
							commisionPlb = originalBase.multiply(AmountRoundingModeUtil.roundingMode(flightOrderRowCommission.getPlbCommission().divide(new BigDecimal("100"))));
							compnaycommissionAMount = commisionIata.add(commisionPlb);
							logger.info("api base amount "+totalBaseFare);
							logger.info("commisionIata % "+flightOrderRowCommission.getIataCommission());
							logger.info("commisionPlb % "+flightOrderRowCommission.getPlbCommission());
							logger.info("commisionIata "+commisionIata);
							logger.info("commisionPlb "+commisionPlb);
							logger.info("originalBase (without iata) "+originalBase);
						}
						catch(ArithmeticException ex)
						{
							originalBase = totalBaseFare.divide(new BigDecimal(1).add(AmountRoundingModeUtil.roundingMode(flightOrderRowCommission.getIataCommission().divide(AmountRoundingModeUtil.roundingMode(new BigDecimal("100"))))));
							commisionIata = originalBase.multiply(AmountRoundingModeUtil.roundingMode(flightOrderRowCommission.getIataCommission().divide(new BigDecimal("100"))));
							commisionPlb = originalBase.multiply(AmountRoundingModeUtil.roundingMode(flightOrderRowCommission.getPlbCommission().divide(new BigDecimal("100"))));
							compnaycommissionAMount = commisionIata.add(commisionPlb);
							logger.info("api base amount "+totalBaseFare);
							logger.info("commisionIata % "+flightOrderRowCommission.getIataCommission());
							logger.info("commisionPlb % "+flightOrderRowCommission.getPlbCommission());
							logger.info("commisionIata "+commisionIata);
							logger.info("commisionPlb "+commisionPlb);
							logger.info("originalBase (without iata) "+originalBase);
						}
					}
					else{
						compnaycommissionAMount=totalBaseFare.multiply(flightOrderRowCommission.getCommission()).divide(AmountRoundingModeUtil.roundingMode(new BigDecimal("100")));
					}
				}
				flightOrderRowCommission.setCommissionAmountValue(compnaycommissionAMount);
				if(m == flightOrderRowCommissions.size()-1){
					break;
				}
				BigDecimal Markup = BigDecimal.ZERO;
				boolean result = false;
				List<FlightMarkUpConfig> FlightMarkUpConfiglist=markupMap.get(flightOrderRowCommission.getCompanyId());
				if (FlightMarkUpConfiglist != null && FlightMarkUpConfiglist.size()>0) {
					int k = 0;
					BigDecimal TEMPBPAmount = totalBaseFare;
					for (int i = 0; i < FlightMarkUpConfiglist.size(); i++) {
						FlightMarkUpConfig flightMarkUpConfig = FlightMarkUpConfiglist
								.get(i);
						boolean accumulative = flightMarkUpConfig.isAccumulative();
						boolean fixedAmount = flightMarkUpConfig.isFixedAmount();
						String markup = flightMarkUpConfig.getMarkup();
						if (isMarkupAppliable(flightsearch, flightMarkUpConfig, Airline,fareBasisCode)) {
							BigDecimal MarkupValue = new BigDecimal(markup);
							if (k == 0 && !accumulative) {
								if (fixedAmount) {
									totalBaseFare = totalBaseFare.add(MarkupValue);
									totalAmount = totalBaseFare.add(totalTaxAmount);
								} else {
									totalBaseFare = totalBaseFare.add((TEMPBPAmount
											.multiply(MarkupValue))
											.divide(new BigDecimal("100")));
									totalAmount = totalBaseFare.add(totalTaxAmount);
								}
								break;
							} else if (k != 0 && !accumulative&&result) {
								continue;
							}
							else if (k != 0 && !accumulative&&!result) {
								if (fixedAmount) {
									totalBaseFare = totalBaseFare.add(MarkupValue);
									totalAmount = totalBaseFare.add(totalTaxAmount);
								} else {
									totalBaseFare = totalBaseFare.add((TEMPBPAmount
											.multiply(MarkupValue))
											.divide(new BigDecimal("100")));
									totalAmount = totalBaseFare.add(totalTaxAmount);
								}
								break;
							}else if (accumulative){
								if (fixedAmount) {
									totalBaseFare = totalBaseFare.add(MarkupValue);
									totalAmount = totalBaseFare.add(totalTaxAmount);
								} else {
									totalBaseFare = totalBaseFare.add((TEMPBPAmount
											.multiply(MarkupValue))
											.divide(new BigDecimal("100")));

									totalAmount = totalBaseFare.add(totalTaxAmount);
								}
								result=true;
							}
							k++;
						}
					}				
				}
			}
		}
	}

	public static List<String> getFinalOriDestList(List<String>  oriList) {
		List<String> oriDestList = new ArrayList<String>();
		if(oriList!=null && oriList.size()>0){
			for(String temp:oriList){
				if(!temp.equalsIgnoreCase("All")){
					String markupOri=temp.substring(
							temp.indexOf("(") + 1,
							temp.indexOf(")"));
					oriDestList.add(markupOri);
				}else{
					oriDestList.add(temp);
				}
			}
		}
		return oriDestList;
	}

	public static List<String> getOriDestList(String oriODest) {
		List<String> oriDestList = new ArrayList<String>();
		if(oriODest!=null)
		{
			String Tempflightindex = oriODest;
			while (Tempflightindex.length() > 0) {
				if (Tempflightindex.indexOf(";") != -1) {
					String Newflightindex = Tempflightindex.substring(0,
							Tempflightindex.indexOf(";"));
					oriDestList.add(Newflightindex.trim());
					Tempflightindex = Tempflightindex.substring(Newflightindex
							.length() + 1);
				} else {
					oriDestList.add(Tempflightindex.trim());
					break;
				}
			}
		}
		return oriDestList;
	}

	public static boolean isMarkupAppliable(Flightsearch flightsearch,
			FlightMarkUpConfig flightMarkUpConfig, String Airline,String fareBasisCode) {
		try{
			if(flightMarkUpConfig!=null)
			{
				long psd = 1111111111111L;
				long ped = 9999999999999L;
				if(!flightMarkUpConfig.getPromofareEndDate().equalsIgnoreCase("ALL")){
					ped=getTimeStamp(flightMarkUpConfig.getPromofareEndDate());
				}
				if(!flightMarkUpConfig.getPromofareStartDate().equalsIgnoreCase("ALL")){
					psd=getTimeStamp(flightMarkUpConfig.getPromofareStartDate());
				}
				List<String> destList=getOriDestList(flightMarkUpConfig.getDestination());
				List<String> oriList=getOriDestList(flightMarkUpConfig.getOrigin());
				oriList=getFinalOriDestList(oriList);
				destList=getFinalOriDestList(destList);
				List<String> airlineList=getOriDestList(flightMarkUpConfig.getAirline());
				int satisfiedCount = 0;
				if (airlineList.contains(Airline)
						|| flightMarkUpConfig.getAirline().equalsIgnoreCase("All")) {
					satisfiedCount++;
				} else {
					return false;
				}
				if (flightsearch.getTripType() != null
						&& flightsearch.getTripType().equalsIgnoreCase("R")) {
					if (flightMarkUpConfig.getArrvDate().equalsIgnoreCase(
							FlightWebServiceEndPointValidator.getMarkupDateformat(flightsearch.getArvlDate()))||flightMarkUpConfig.getArrvDate().equalsIgnoreCase(
									FlightWebServiceEndPointValidator.getMarkupDateformat(flightsearch.getDepDate()))
							|| flightMarkUpConfig.getArrvDate().equalsIgnoreCase("All")) {
						satisfiedCount++;
					}
				} else {
					satisfiedCount++;
				}
				if (flightsearch.getTripType() != null
						&& flightsearch.getTripType().equalsIgnoreCase("R")) {
					long adt=getTimeStamp(FlightWebServiceEndPointValidator.getMarkupDateformat(flightsearch.getArvlDate()));
					if (psd<=adt&&adt<=ped) {
						satisfiedCount++;

					}
				} else {
					satisfiedCount++;
				}				
				if(flightsearch.getTripType() != null
						&& flightsearch.getTripType().equalsIgnoreCase("M")){
					if (flightsearch.getDepDate() != null
							&& flightMarkUpConfig.getDeptDate() != null
							&& (checkDateList(flightMarkUpConfig.getDeptDate(),flightsearch)
									|| flightMarkUpConfig.getDeptDate().equalsIgnoreCase("All"))) {
						satisfiedCount++;

					} else {
						return false;
					}
					if (flightMarkUpConfig.getOrigin() != null
							&& (flightMarkUpConfig.getOrigin().equalsIgnoreCase("All")
									|| checkOriList(oriList,flightsearch))) {
						satisfiedCount++;
					} else {
						return false;
					}
					if (flightMarkUpConfig.getDestination() != null
							&& (flightMarkUpConfig.getDestination().equalsIgnoreCase("All")
									|| checkDestList(destList,flightsearch))) {
						satisfiedCount++;
					} else {
						return false;
					}
					long ddt=getTimeStamp(FlightWebServiceEndPointValidator.getMarkupDateformat(flightsearch.getDepDate()));
					if (psd<=ddt&&ddt<=ped) {
						satisfiedCount++;

					}else {
						return false;
					}
				}else
					if(flightsearch.getTripType() != null
					&& flightsearch.getTripType().equalsIgnoreCase("R")){
						if (flightMarkUpConfig.getDeptDate() != null
								&& (flightMarkUpConfig.getDeptDate().equalsIgnoreCase(
										FlightWebServiceEndPointValidator.getMarkupDateformat(flightsearch.getArvlDate()))||flightMarkUpConfig.getDeptDate().equalsIgnoreCase(
												FlightWebServiceEndPointValidator.getMarkupDateformat(flightsearch.getDepDate()))
										|| flightMarkUpConfig.getDeptDate().equalsIgnoreCase("All"))) {
							satisfiedCount++;
						} else {
							return false;
						}
						if (flightMarkUpConfig.getOrigin() != null
								&& (flightMarkUpConfig.getOrigin().equalsIgnoreCase("All")
										|| oriList.contains(flightsearch.getOrigin())|| oriList.contains(flightsearch.getDestination()))) {
							satisfiedCount++;
						} else {
							return false;
						}
						if (flightMarkUpConfig.getDestination() != null
								&& (flightMarkUpConfig.getDestination().equalsIgnoreCase("All")
										|| destList.contains(flightsearch.getDestination())|| destList.contains(flightsearch.getOrigin()))) {
							satisfiedCount++;
						} else {
							return false;
						}
						long ddt=getTimeStamp(FlightWebServiceEndPointValidator.getMarkupDateformat(flightsearch.getDepDate()));
						if (psd<=ddt&&ddt<=ped) {
							satisfiedCount++;
						}else {
							return false;
						}
					}
					else{
						if (flightsearch.getDepDate() != null
								&& flightMarkUpConfig.getDeptDate() != null
								&& (flightMarkUpConfig.getDeptDate().equalsIgnoreCase(
										FlightWebServiceEndPointValidator.getMarkupDateformat(flightsearch.getDepDate()))
										|| flightMarkUpConfig.getDeptDate().equalsIgnoreCase("All"))) {
							satisfiedCount++;							
						} else {
							return false;
						}
						if (flightMarkUpConfig.getOrigin() != null
								&& (flightMarkUpConfig.getOrigin().equalsIgnoreCase("All")
										|| oriList.contains(flightsearch.getOrigin()))) {
							satisfiedCount++;						
						} else {
							return false;
						}
						if (flightMarkUpConfig.getDestination() != null
								&& (flightMarkUpConfig.getDestination().equalsIgnoreCase("All")
										|| destList.contains(flightsearch.getDestination()))) {
							satisfiedCount++;							
						} else {
							return false;
						}
						long ddt = getTimeStamp(FlightWebServiceEndPointValidator.getMarkupDateformat(flightsearch.getDepDate()));
						if (psd <= ddt && ddt <= ped) {
							satisfiedCount++;
						}else {
							return false;
						}

					}
				if ((flightMarkUpConfig.getDestinationType() != null && flightsearch.getFlightType() != null)
						&& (flightMarkUpConfig.getDestinationType().equalsIgnoreCase(flightsearch.getFlightType())
								|| flightMarkUpConfig.getDestinationType().equalsIgnoreCase("All"))) {
					satisfiedCount++;					
				} else {
					return false;
				}
				if (flightMarkUpConfig.getClassOfService() != null
						&&( flightMarkUpConfig.getClassOfService().equalsIgnoreCase(
								flightsearch.getCabinClass())
								|| flightMarkUpConfig.getClassOfService().equalsIgnoreCase(
										"All"))) {
					satisfiedCount++;					
				} else {
					return false;
				}
				if (flightMarkUpConfig.getFareBasisCode() != null
						&& (flightMarkUpConfig.getFareBasisCode().equalsIgnoreCase(fareBasisCode)
								|| flightMarkUpConfig.getFareBasisCode().equalsIgnoreCase(
										"All"))) {
					satisfiedCount++;					
				} else {
					return false;
				}
				if (satisfiedCount == 10)
					return true;
				else
					return false;
			}
		}
		catch(Exception e)
		{
			logger.info("is markup applicable check... Exception found-false return.... "+e.getMessage());
			return false;
		}
		return false;
	}

	public static void getMarkupValuesForPassegers(
			FlightPriceResponse flightPriceResponse,
			Map<String,List<FlightMarkUpConfig>> FlightMarkUpConfiglistMap, String Airline,
			PassengerFareBreakUp passengerFareBreakUp, int passegers_for_markup,String fareBasisCode)
					throws Exception {
		String BasePriceAmount = passengerFareBreakUp.getBasePriceWithoutMarkup();
		String TaxesAmount = passengerFareBreakUp.getTaxesWithoutMarkup();
		BigDecimal TPAmount;
		BigDecimal BPAmount = new BigDecimal(BasePriceAmount);
		BigDecimal TAmount = new BigDecimal(TaxesAmount);
		TPAmount = BPAmount.add(TAmount);
		Map<String, List<FlightMarkUpConfig>> sortedFlightMarkUpConfiglistMap = new TreeMap<String,List<FlightMarkUpConfig>>();
		if(FlightMarkUpConfiglistMap!=null && FlightMarkUpConfiglistMap.size()>0)
		{
			sortedFlightMarkUpConfiglistMap.putAll(FlightMarkUpConfiglistMap);
			if(sortedFlightMarkUpConfiglistMap!=null && sortedFlightMarkUpConfiglistMap.size()>0)
			{
				for (Map.Entry<String,List<FlightMarkUpConfig>> entry : sortedFlightMarkUpConfiglistMap.entrySet()) {
					List<FlightMarkUpConfig> FlightMarkUpConfiglist=entry.getValue();
					if (FlightMarkUpConfiglist != null) {					
						int k = 0;
						boolean result=false;
						BigDecimal TEMPTAAmount = TAmount;
						for (int i = 0; i < FlightMarkUpConfiglist.size(); i++) {
							
							FlightMarkUpConfig flightMarkUpConfig = FlightMarkUpConfiglist
									.get(i);
							boolean accumulative = flightMarkUpConfig.isAccumulative();// If
							// this
							// is
							// true
							// we
							// shud
							// apply
							// all
							// the
							// Markups
							boolean fixedAmount = flightMarkUpConfig.isFixedAmount();
							String markup = flightMarkUpConfig.getMarkup();
							if (isMarkupAppliable(flightPriceResponse,
									flightMarkUpConfig, Airline, fareBasisCode)) {
								BigDecimal MarkupValue = new BigDecimal(markup);							
								if (k == 0 && !accumulative) {
									if (fixedAmount) {
										TAmount = TAmount
												.add(MarkupValue);
										TPAmount = BPAmount.add(TAmount);
									} else {
										TAmount = TAmount.add((TEMPTAAmount
												.multiply(MarkupValue))
												.divide(new BigDecimal("100")));
										TPAmount = BPAmount.add(TAmount);
									}
									break;
								}  else if (k != 0 && !accumulative&&result) {
									continue;
								}
								else if (k != 0 && !accumulative&&!result) {

									if (fixedAmount) {
										TAmount = TAmount
												.add(MarkupValue);
										TPAmount = BPAmount.add(TAmount);
									} else {
										TAmount = TAmount.add((TEMPTAAmount
												.multiply(MarkupValue))
												.divide(new BigDecimal("100")));
										TPAmount = BPAmount.add(TAmount);
									}
									break;
								}else if (accumulative){
									if (fixedAmount) {
										TAmount = TAmount
												.add(MarkupValue);
										TPAmount = BPAmount.add(TAmount);
									} else {
										TAmount = TAmount.add((TEMPTAAmount
												.multiply(MarkupValue))
												.divide(new BigDecimal("100")));
										TPAmount = BPAmount.add(TAmount);
									}
									result=true;
								}
								k++;
							}
						}
					}
				}
			}
		}
		passengerFareBreakUp.setBasePrice(String.valueOf(BPAmount));
		passengerFareBreakUp.setTotalPrice(String.valueOf(TPAmount));
		passengerFareBreakUp.setTaxes(String.valueOf(TAmount));
	}

	public static boolean isMarkupAppliable(
			FlightPriceResponse flightPriceResponse,
			FlightMarkUpConfig flightMarkUpConfig, String Airline,String fareBasisCode) {
		int satisfiedCount = 0;
		try{		
			if(flightMarkUpConfig!=null)
			{
				long psd = 1111111111111L;
				long ped = 9999999999999L;
				if(!flightMarkUpConfig.getPromofareEndDate().equalsIgnoreCase("ALL")){
					ped=getTimeStamp(flightMarkUpConfig.getPromofareEndDate());
				}
				if(!flightMarkUpConfig.getPromofareStartDate().equalsIgnoreCase("ALL")){
					psd=getTimeStamp(flightMarkUpConfig.getPromofareStartDate());
				}
				List<String> destList=getOriDestList(flightMarkUpConfig.getDestination());
				List<String> oriList=getOriDestList(flightMarkUpConfig.getOrigin());
				oriList=getFinalOriDestList(oriList);
				destList=getFinalOriDestList(destList);
				List<String> airlineList=getOriDestList(flightMarkUpConfig.getAirline());
				if (airlineList.contains(Airline)
						|| flightMarkUpConfig.getAirline().equalsIgnoreCase("All")) {

					satisfiedCount++;
				} else {
					return false;
				}

				if (flightPriceResponse.getFlightsearch() != null
						&& flightPriceResponse.getFlightsearch().getTripType() != null
						&& flightPriceResponse.getFlightsearch().getTripType()
						.equalsIgnoreCase("R")) {
					if (flightMarkUpConfig.getArrvDate().equalsIgnoreCase(
							FlightWebServiceEndPointValidator.getMarkupDateformat(flightPriceResponse.getFlightsearch().getArvlDate()))
							|| flightMarkUpConfig.getArrvDate().equalsIgnoreCase(
									FlightWebServiceEndPointValidator.getMarkupDateformat(flightPriceResponse.getFlightsearch().getDepDate()))
							|| flightMarkUpConfig.getArrvDate().equalsIgnoreCase("All")) {
						satisfiedCount++;

					}
				} else {
					satisfiedCount++;
				}
				if (flightPriceResponse.getFlightsearch().getTripType() != null
						&& flightPriceResponse.getFlightsearch().getTripType().equalsIgnoreCase("R")) {
					long adt=getTimeStamp(FlightWebServiceEndPointValidator.getMarkupDateformat(flightPriceResponse.getFlightsearch().getArvlDate()));
					if (psd<=adt&&adt<=ped) {
						satisfiedCount++;logger.info("inside getArrvDate");

					}
				} else {
					satisfiedCount++;
				}
				if(flightPriceResponse.getFlightsearch().getTripType() != null
						&& flightPriceResponse.getFlightsearch().getTripType().equalsIgnoreCase("M")){// for multicity,
					if (FlightWebServiceEndPointValidator.getMarkupDateformat(flightPriceResponse.getFlightsearch().getDepDate()) != null
							&& flightMarkUpConfig.getDeptDate() != null
							&& (checkDateList(flightMarkUpConfig.getDeptDate(),flightPriceResponse.getFlightsearch())
									|| flightMarkUpConfig.getDeptDate().equalsIgnoreCase("All"))) {
						satisfiedCount++;
					} else {
						return false;
					}// for multicity,
					if (flightMarkUpConfig.getOrigin() != null
							&& flightMarkUpConfig.getOrigin().equalsIgnoreCase("All")
							|| checkOriList(oriList,flightPriceResponse.getFlightsearch())) {
						satisfiedCount++;
					} else {
						return false;
					}
					if (flightMarkUpConfig.getDestination() != null
							&& flightMarkUpConfig.getDestination().equalsIgnoreCase("All")
							|| checkDestList(destList,flightPriceResponse.getFlightsearch())) {
						satisfiedCount++;
					} else {
						return false;
					}

					long ddt=getTimeStamp(FlightWebServiceEndPointValidator.getMarkupDateformat(flightPriceResponse.getFlightsearch().getDepDate()));
					if (psd<=ddt&&ddt<=ped) {
						satisfiedCount++;logger.info("inside getDeptDate");

					}else {
						return false;
					}
				}else
					if(flightPriceResponse.getFlightsearch().getTripType() != null
					&& flightPriceResponse.getFlightsearch().getTripType().equalsIgnoreCase("R")){
						if (flightMarkUpConfig.getDeptDate() != null
								&& flightMarkUpConfig.getDeptDate().equalsIgnoreCase(
										FlightWebServiceEndPointValidator.getMarkupDateformat(flightPriceResponse.getFlightsearch().getArvlDate()))||flightMarkUpConfig.getDeptDate().equalsIgnoreCase(
												FlightWebServiceEndPointValidator.getMarkupDateformat(flightPriceResponse.getFlightsearch().getDepDate()))
								|| flightMarkUpConfig.getDeptDate().equalsIgnoreCase("All")) {
							satisfiedCount++;
						} else {
							return false;
						}
						if (flightMarkUpConfig.getOrigin() != null
								&& flightMarkUpConfig.getOrigin().equalsIgnoreCase("All")
								|| oriList.contains(flightPriceResponse.getFlightsearch().getOrigin())|| oriList.contains(flightPriceResponse.getFlightsearch().getDestination())) {
							satisfiedCount++;
						} else {
							return false;
						}
						if (flightMarkUpConfig.getDestination() != null
								&& flightMarkUpConfig.getDestination().equalsIgnoreCase("All")
								|| destList.contains(flightPriceResponse.getFlightsearch().getDestination())|| destList.contains(flightPriceResponse.getFlightsearch().getOrigin())) {
							satisfiedCount++;
						} else {
							return false;
						}
						long ddt=getTimeStamp(FlightWebServiceEndPointValidator.getMarkupDateformat(flightPriceResponse.getFlightsearch().getDepDate()));
						if (psd<=ddt&&ddt<=ped) {
							satisfiedCount++;logger.info("inside getDeptDate");

						}else {
							return false;
						}
					}
					else{
						if (flightPriceResponse.getFlightsearch() != null
								&& FlightWebServiceEndPointValidator.getMarkupDateformat(flightPriceResponse.getFlightsearch().getDepDate()) != null
								&& flightMarkUpConfig.getDeptDate() != null
								&& flightMarkUpConfig.getDeptDate().equalsIgnoreCase(
										FlightWebServiceEndPointValidator.getMarkupDateformat(flightPriceResponse.getFlightsearch().getDepDate()))
								|| flightMarkUpConfig.getDeptDate().equalsIgnoreCase("All")) {
							satisfiedCount++;
						} else {
							return false;
						}
						if (flightMarkUpConfig.getOrigin() != null
								&& flightMarkUpConfig.getOrigin().equalsIgnoreCase("All")
								|| oriList.contains(flightPriceResponse.getFlightsearch().getOrigin())) {
							satisfiedCount++;
						} else {
							return false;
						}
						if (flightMarkUpConfig.getDestination() != null
								&& flightMarkUpConfig.getDestination().equalsIgnoreCase("All")
								|| destList.contains(flightPriceResponse.getFlightsearch().getDestination())) {
							satisfiedCount++;
						} else {
							return false;
						}

						long ddt=getTimeStamp(FlightWebServiceEndPointValidator.getMarkupDateformat(flightPriceResponse.getFlightsearch().getDepDate()));
						if (psd<=ddt&&ddt<=ped) {
							satisfiedCount++;logger.info("inside getDeptDate");

						}else {
							return false;
						}
					}
				if ((flightMarkUpConfig.getDestinationType() != null && flightPriceResponse.getFlightsearch().getFlightType() != null)
						&& (flightMarkUpConfig.getDestinationType().equalsIgnoreCase(flightPriceResponse.getFlightsearch().getFlightType())
								|| flightMarkUpConfig.getDestinationType().equalsIgnoreCase("All"))) {
					satisfiedCount++;
				} else {
					return false;
				}
				if (flightMarkUpConfig.getClassOfService().equalsIgnoreCase(
						flightPriceResponse.getFlightsearch().getCabinClass())
						|| flightMarkUpConfig.getClassOfService().equalsIgnoreCase(
								"All")) {
					satisfiedCount++;
				} else {
					return false;
				}
				if (flightMarkUpConfig.getFareBasisCode().equalsIgnoreCase(fareBasisCode)
						|| flightMarkUpConfig.getFareBasisCode().equalsIgnoreCase(
								"All")) {
					satisfiedCount++;
				} else {
					return false;
				}

				if (satisfiedCount == 10)
					return true;
				else
					return false;
			}
		}
		catch(Exception e)
		{			
			logger.info("is markup applicable check... Exception found-false return.... "+e.getMessage());
			return false;
		}
		return false;
	}

	public static void getMarkupValuesFPR(
			FlightPriceResponse flightPriceResponse,
			Map<String,List<FlightMarkUpConfig>> FlightMarkUpConfiglistMap, String Airline,
			FareFlightSegment fare, String fareBasisCode,CompanyConfig companyConfig) throws Exception {

		fare.getTotalPriceWithoutMarkup();
		String BasePriceAmount = fare.getBasePriceWithoutMarkup();
		String TaxesAmount = fare.getTaxesWithoutMarkup();
		BigDecimal TPAmount;
		BigDecimal BPAmount = new BigDecimal(BasePriceAmount);
		BigDecimal TAmount = new BigDecimal(TaxesAmount);
		TPAmount = BPAmount.add(TAmount);
		BigDecimal totalAdult = new BigDecimal(flightPriceResponse.getFlightsearch().getAdult());
		BigDecimal totalKid = new BigDecimal(flightPriceResponse.getFlightsearch().getKid());
		BigDecimal totalInfant = new BigDecimal(flightPriceResponse.getFlightsearch().getInfant());
		BigDecimal totalPassengers = totalAdult.add(totalKid).add(totalInfant);
		BigDecimal currentCompanyMarkup = new BigDecimal(0);
		Map<String, List<FlightMarkUpConfig>> sortedFlightMarkUpConfiglistMap = new TreeMap<String,List<FlightMarkUpConfig>>();
		if(FlightMarkUpConfiglistMap!=null && FlightMarkUpConfiglistMap.size()>0)
		{
			sortedFlightMarkUpConfiglistMap.putAll(FlightMarkUpConfiglistMap);
			if(sortedFlightMarkUpConfiglistMap!=null && sortedFlightMarkUpConfiglistMap.size()>0)
			{
				for (Map.Entry<String,List<FlightMarkUpConfig>> entry : sortedFlightMarkUpConfiglistMap.entrySet()) {
					List<FlightMarkUpConfig> FlightMarkUpConfiglist=entry.getValue();
					int currentCompany = Integer.parseInt(entry.getKey());
					if (FlightMarkUpConfiglist != null) {
						int k = 0;
						boolean result=false;
						for (int i = 0; i < FlightMarkUpConfiglist.size(); i++) {
							FlightMarkUpConfig flightMarkUpConfig = FlightMarkUpConfiglist
									.get(i);
							BigDecimal TEMPTAAmount = TAmount;
							boolean accumulative = flightMarkUpConfig.isAccumulative();// If
							// this
							// is
							// true
							// we
							// shud
							// apply
							// all
							// the
							// Markups
							boolean fixedAmount = flightMarkUpConfig.isFixedAmount();
							String markup = flightMarkUpConfig.getMarkup();
							if (isMarkupAppliable(flightPriceResponse, flightMarkUpConfig,
									Airline,fareBasisCode)) {
								BigDecimal MarkupValue = new BigDecimal(markup);
								MarkupValue = MarkupValue.multiply(totalPassengers);
								if (k == 0 && !accumulative) {
									if (fixedAmount) {
										TAmount = TAmount.add(MarkupValue);
										TPAmount = BPAmount.add(TAmount);
									} else {
										TAmount = TAmount.add((TEMPTAAmount
												.multiply(MarkupValue))
												.divide(new BigDecimal("100")));
										TPAmount = BPAmount.add(TAmount);
									}
									break;
								}  else if (k != 0 && !accumulative&&result) {
									continue;
								}
								else if (k != 0 && !accumulative&&!result) {
									if (fixedAmount) {
										TAmount = TAmount.add(MarkupValue);
										TPAmount = BPAmount.add(TAmount);
									} else {
										TAmount = TAmount.add((TEMPTAAmount
												.multiply(MarkupValue))
												.divide(new BigDecimal("100")));
										TPAmount = BPAmount.add(TAmount);
									}
									break;
								}else if (accumulative){
									if (fixedAmount) {
										TAmount = TAmount.add(MarkupValue);
										TPAmount = BPAmount.add(TAmount);
									} else {
										TAmount = TAmount.add((TEMPTAAmount
												.multiply(MarkupValue))
												.divide(new BigDecimal("100")));
										TPAmount = BPAmount.add(TAmount);
									}
									result=true;
								}
								k++;
								if(companyConfig.getCompany_id() == currentCompany)
								{
									currentCompanyMarkup= currentCompanyMarkup.add(MarkupValue);
								}
							}
						}
					}
				}
			}
		}
		fare.setBasePrice(String.valueOf(BPAmount));
		fare.setTotalPrice(String.valueOf(TPAmount));
		fare.setTaxes(String.valueOf(TAmount));
		if(companyConfig!=null){
			if(companyConfig.getCompanyConfigType().isB2E()){
				fare.setPayableAmount(AmountRoundingModeUtil.roundingMode(new BigDecimal(fare.getTotalPrice())));
			}else if(companyConfig.getCompanyConfigType().isB2B()){
				fare.setPayableAmount(AmountRoundingModeUtil.roundingMode(new BigDecimal(fare.getTotalPrice()).subtract(currentCompanyMarkup)));
			}else{
				fare.setPayableAmount(AmountRoundingModeUtil.roundingMode(new BigDecimal(fare.getTotalPrice())));
			}
		}
	}

	public static void getMarkupValuesSpecialReturnFPR(
			FlightPriceResponse flightPriceResponse,
			Map<String,List<FlightMarkUpConfig>> FlightMarkUpConfiglistMap, String Airline,
			FareFlightSegment fare, String fareBasisCode,CompanyConfig companyConfig) throws Exception {

		fare.getTotalPriceWithoutMarkup();
		String BasePriceAmount = fare.getBasePriceWithoutMarkup();
		String TaxesAmount = fare.getTaxesWithoutMarkup();
		BigDecimal TPAmount;
		BigDecimal BPAmount = new BigDecimal(BasePriceAmount);
		BigDecimal TAmount = new BigDecimal(TaxesAmount);
		TPAmount = BPAmount.add(TAmount);
		BigDecimal totalAdult = new BigDecimal(flightPriceResponse.getFlightsearch().getAdult());
		BigDecimal totalKid = new BigDecimal(flightPriceResponse.getFlightsearch().getKid());
		BigDecimal totalInfant = new BigDecimal(flightPriceResponse.getFlightsearch().getInfant());
		BigDecimal totalPassengers = totalAdult.add(totalKid).add(totalInfant);
		BigDecimal currentCompanyMarkup = new BigDecimal(0);
		Map<String, List<FlightMarkUpConfig>> sortedFlightMarkUpConfiglistMap = new TreeMap<String,List<FlightMarkUpConfig>>();
		if(FlightMarkUpConfiglistMap!=null && FlightMarkUpConfiglistMap.size()>0)
		{
			sortedFlightMarkUpConfiglistMap.putAll(FlightMarkUpConfiglistMap);
			if(sortedFlightMarkUpConfiglistMap!=null && sortedFlightMarkUpConfiglistMap.size()>0)
			{
				for (Map.Entry<String,List<FlightMarkUpConfig>> entry : sortedFlightMarkUpConfiglistMap.entrySet()) {
					List<FlightMarkUpConfig> FlightMarkUpConfiglist=entry.getValue();
					int currentCompany = Integer.parseInt(entry.getKey());
					if (FlightMarkUpConfiglist != null) {
						int k = 0;
						boolean result=false;
						for (int i = 0; i < FlightMarkUpConfiglist.size(); i++) {
							FlightMarkUpConfig flightMarkUpConfig = FlightMarkUpConfiglist
									.get(i);
							BigDecimal TEMPTAAmount = TAmount;
							boolean accumulative = flightMarkUpConfig.isAccumulative();// If
							// this
							// is
							// true
							// we
							// shud
							// apply
							// all
							// the
							// Markups
							boolean fixedAmount = flightMarkUpConfig.isFixedAmount();
							String markup = flightMarkUpConfig.getMarkup();

							if (isMarkupAppliable(flightPriceResponse, flightMarkUpConfig,
									Airline,fareBasisCode)) {
								BigDecimal MarkupValue = new BigDecimal(markup);
								MarkupValue = MarkupValue.multiply(totalPassengers);
								// Markup value for onward and return(SR)
								MarkupValue = MarkupValue.multiply(new BigDecimal(2));
								if (k == 0 && !accumulative) {
									if (fixedAmount) {
										TAmount = TAmount.add(MarkupValue);
										TPAmount = BPAmount.add(TAmount);
									} else {
										TAmount = TAmount.add((TEMPTAAmount
												.multiply(MarkupValue))
												.divide(new BigDecimal("100")));
										TPAmount = BPAmount.add(TAmount);
									}
									break;
								}  else if (k != 0 && !accumulative&&result) {
									continue;
								}
								else if (k != 0 && !accumulative&&!result) {
									if (fixedAmount) {
										TAmount = TAmount.add(MarkupValue);
										TPAmount = BPAmount.add(TAmount);
									} else {
										TAmount = TAmount.add((TEMPTAAmount
												.multiply(MarkupValue))
												.divide(new BigDecimal("100")));
										TPAmount = BPAmount.add(TAmount);
									}
									break;
								}else if (accumulative){
									if (fixedAmount) {
										TAmount = TAmount.add(MarkupValue);
										TPAmount = BPAmount.add(TAmount);
									} else {
										TAmount = TAmount.add((TEMPTAAmount
												.multiply(MarkupValue))
												.divide(new BigDecimal("100")));
										TPAmount = BPAmount.add(TAmount);
									}
									result=true;
								}
								k++;
								if(companyConfig.getCompany_id() == currentCompany)
								{
									currentCompanyMarkup= currentCompanyMarkup.add(MarkupValue);
								}
							}
						}
					}
				}
			}
		}
		fare.setBasePrice(String.valueOf(BPAmount));
		fare.setTotalPrice(String.valueOf(TPAmount));
		fare.setTaxes(String.valueOf(TAmount));
		if(companyConfig!=null){
			if(companyConfig.getCompanyConfigType().isB2E()){
				fare.setPayableAmount(AmountRoundingModeUtil.roundingMode(new BigDecimal(fare.getTotalPrice())));
			}else if(companyConfig.getCompanyConfigType().isB2B()){
				fare.setPayableAmount(AmountRoundingModeUtil.roundingMode(new BigDecimal(fare.getTotalPrice()).subtract(currentCompanyMarkup)));
			}else{
				fare.setPayableAmount(AmountRoundingModeUtil.roundingMode(new BigDecimal(fare.getTotalPrice())));
			}
		}
	}


	public static void getMarkupValuesForCalendar(Map<String,List<FlightMarkUpConfig>> markupMap,
			CalendarResponseData fare) throws Exception {

		String BasePriceAmount = fare.getBasePriceWithoutMarkup();
		String TaxesAmount = fare.getTaxesWithoutMarkup();
		BigDecimal TPAmount;
		BigDecimal BPAmount = new BigDecimal(BasePriceAmount);
		BigDecimal TAmount = new BigDecimal(TaxesAmount);
		TPAmount = BPAmount.add(TAmount);
		BigDecimal totalPassengers = new BigDecimal(1);	
		Map<String, List<FlightMarkUpConfig>> sortedFlightMarkUpConfiglistMap = new TreeMap<String,List<FlightMarkUpConfig>>();
		if(markupMap!=null && markupMap.size()>0)
		{
			sortedFlightMarkUpConfiglistMap.putAll(markupMap);
			if(sortedFlightMarkUpConfiglistMap!=null && sortedFlightMarkUpConfiglistMap.size()>0)
			{
				for (Map.Entry<String,List<FlightMarkUpConfig>> entry : sortedFlightMarkUpConfiglistMap.entrySet()) {
					List<FlightMarkUpConfig> FlightMarkUpConfiglist = entry.getValue();	
					if (FlightMarkUpConfiglist != null) {
						int k = 0;
						boolean result=false;
						BigDecimal TEMPTAAmount = TAmount;
						for (int i = 0; i < FlightMarkUpConfiglist.size(); i++) {
							FlightMarkUpConfig flightMarkUpConfig = FlightMarkUpConfiglist
									.get(i);
							boolean accumulative = flightMarkUpConfig.isAccumulative();
							boolean fixedAmount = flightMarkUpConfig.isFixedAmount();
							String markup = flightMarkUpConfig.getMarkup();
							BigDecimal MarkupValue = new BigDecimal(markup);
							// Add markup per passengers
							MarkupValue = MarkupValue.multiply(totalPassengers);
							if (k == 0 && !accumulative) {
								if (fixedAmount) {
									TAmount = TAmount.add(MarkupValue);
									TPAmount = BPAmount.add(TAmount);
								} else {
									TAmount = TAmount.add((TEMPTAAmount
											.multiply(MarkupValue))
											.divide(new BigDecimal("100")));
									TPAmount = BPAmount.add(TAmount);
								}								
								break;
							} else if (k != 0 && !accumulative&&result) {
								continue;
							}
							else if (k != 0 && !accumulative&&!result) {
								if (fixedAmount) {
									TAmount = TAmount.add(MarkupValue);
									TPAmount = BPAmount.add(TAmount);
								} else {
									TAmount = TAmount.add((TEMPTAAmount
											.multiply(MarkupValue))
											.divide(new BigDecimal("100")));
									TPAmount = BPAmount.add(TAmount);
								}
								break;
							}else if (accumulative){
								if (fixedAmount) {
									TAmount = TAmount.add(MarkupValue);
									TPAmount = BPAmount.add(TAmount);
								} else {
									TAmount = TAmount.add((TEMPTAAmount
											.multiply(MarkupValue))
											.divide(new BigDecimal("100")));
									TPAmount = BPAmount.add(TAmount);
								}
								result=true;
							}
							k++;							
						}
					}				
				}
			}
		}
		fare.setBasePrice(String.valueOf(BPAmount));
		fare.setTotalPrice(String.valueOf(TPAmount));
		fare.setTaxes(String.valueOf(TAmount));
	}


	static boolean checkOriList(List<String> oriList,Flightsearch flightsearch)
	{
		boolean result=false;
		String Ori=flightsearch.getMFS().getOrigin1();
		if(oriList!=null && oriList.size()>0)
		{
			if(oriList.contains(Ori)){
				result=true;
			}
		}
		return result;
	}
	
	static boolean checkDestList(List<String> destList,Flightsearch flightsearch)
	{
		boolean result=false;
		if(destList!=null && destList.size()>0)
		{
			String dest=flightsearch.getMFS().getDest3();
			if(flightsearch.getTrips().equals("4")){
				dest=flightsearch.getMFS().getDest4();
			}
			if(destList.contains(dest)){
				result=true;
			}
		}
		return result;
	}
	
	static boolean checkDateList(String depDate,Flightsearch flightsearch)
	{
		boolean result=false;
		if(flightsearch!=null)
		{
			String depDateNew = FlightWebServiceEndPointValidator.getMarkupDateformat(flightsearch.getMFS().getDepartDate1());
			if(depDateNew.equals(depDate)){
				result=true;
			}
		}
		return result;
	}

	public static long getTimeStamp(String inputDate){
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); //  dd-MM-yyyy
		long timestamp1 = 1111111111;
		try {
			Date d = formatter.parse(inputDate);
			timestamp1 = d.getTime();

		} catch (ParseException e) {		
			logger.error(e);
		}
		catch (Exception e) {			
			logger.error(e);
		}
		return timestamp1;

	}
}
class comapnyIDComparator implements Comparator<Object> {
	@Override
	public int compare(Object o1, Object o2) {
		FlightOrderRowCommission A1 = (FlightOrderRowCommission) o1;
		FlightOrderRowCommission A2 = (FlightOrderRowCommission) o2;
		int a1ID = A1.getCompanyId()!=null && !A1.getCompanyId().equalsIgnoreCase("")?Integer.parseInt(A1.getCompanyId()):0;
		int a2ID = A2.getCompanyId()!=null && A2.getCompanyId().equalsIgnoreCase("")?Integer.parseInt(A2.getCompanyId()):0;
		if(a1ID>a2ID){
			return 1;
		}else if(a1ID<a2ID){
			return -1;
		}else{
			return 0;
		}
	}
}