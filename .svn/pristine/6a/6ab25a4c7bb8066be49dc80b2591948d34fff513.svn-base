
package com.tayyarah.flight.util.api.tbo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.rmi.server.UID;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.tayyarah.api.flight.tbo.model.FareBreakdown;
import com.tayyarah.api.flight.tbo.model.FlightSearchrresponseSegment;
import com.tayyarah.api.flight.tbo.model.Passenger;
import com.tayyarah.api.flight.tbo.model.Response;
import com.tayyarah.api.flight.tbo.model.Result;
import com.tayyarah.api.flight.tbo.model.SearchResult;
import com.tayyarah.api.flight.tbo.model.TaxBreakup;
import com.tayyarah.api.flight.tbo.model.TboBookResponse;
import com.tayyarah.api.flight.tbo.model.TboCalendarFareResponse;
import com.tayyarah.api.flight.tbo.model.TboFareRuleResponse;
import com.tayyarah.api.flight.tbo.model.TboFlightAirpriceResponse;
import com.tayyarah.api.flight.tbo.model.TboFlightSearchResponse;
import com.tayyarah.api.flight.tbo.model.TboTicketResponse;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.exception.ErrorMessages;
import com.tayyarah.common.gstconfig.entity.FlightDomesticGstTaxConfig;
import com.tayyarah.common.gstconfig.entity.FlightInternationalGstTaxConfig;
import com.tayyarah.common.gstconfig.model.FlightGstTax;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.model.CurrencyConversionMap;
import com.tayyarah.common.servicetaxconfig.entity.FlightDomesticServiceTaxConfig;
import com.tayyarah.common.servicetaxconfig.entity.FlightInternationalServiceTaxConfig;
import com.tayyarah.common.servicetaxconfig.model.FlightServiceTax;
import com.tayyarah.common.util.AmountRoundingModeUtil;
import com.tayyarah.common.util.ApiResponseSaver;
import com.tayyarah.common.util.IndianUnionTerritories;
import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.company.entity.Company;
import com.tayyarah.company.entity.CompanyConfig;
import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.flight.dao.FlightBookingDao;
import com.tayyarah.flight.dao.FlightTempAirSegmentDAO;
import com.tayyarah.flight.entity.FlightOrderCustomer;
import com.tayyarah.flight.entity.FlightOrderCustomerPriceBreakup;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.flight.exception.FlightErrorMessages;
import com.tayyarah.flight.exception.FlightException;
import com.tayyarah.flight.model.Cabin;
import com.tayyarah.flight.model.CalendarResponseData;
import com.tayyarah.flight.model.Carrier;
import com.tayyarah.flight.model.Discount;
import com.tayyarah.flight.model.FareFlightSegment;
import com.tayyarah.flight.model.FareRule;
import com.tayyarah.flight.model.FareRuleResponse;
import com.tayyarah.flight.model.FareRules;
import com.tayyarah.flight.model.Flight;
import com.tayyarah.flight.model.FlightBookingResponse;
import com.tayyarah.flight.model.FlightCalendarSearch;
import com.tayyarah.flight.model.FlightCalendarSearchResponse;
import com.tayyarah.flight.model.FlightCustomerDetails;
import com.tayyarah.flight.model.FlightMarkUpConfig;
import com.tayyarah.flight.model.FlightPriceResponse;
import com.tayyarah.flight.model.FlightSegments;
import com.tayyarah.flight.model.FlightSegmentsGroup;
import com.tayyarah.flight.model.FlightTaxBreakUp;
import com.tayyarah.flight.model.Flightsearch;
import com.tayyarah.flight.model.MarkupCommissionDetails;
import com.tayyarah.flight.model.NewFareRule;
import com.tayyarah.flight.model.PassengerDetails;
import com.tayyarah.flight.model.PassengerFareBreakUp;
import com.tayyarah.flight.model.SearchFlightResponse;
import com.tayyarah.flight.model.Segments;
import com.tayyarah.flight.model.UAPISearchFlightKeyMap;
import com.tayyarah.flight.service.db.FlightDataBaseServices;
import com.tayyarah.flight.util.PriceComparator;
import com.tayyarah.flight.util.api.travelport.UmarkUpServiceCall;
import com.tayyarah.user.entity.WalletAmountTranferHistory;
import com.travelport.api_v33.AirResponse.FareInfo;
import com.travelport.api_v33.AirResponse.TypeBaseAirSegment;

public class TboResponseParser {
	static Logger logger = Logger.getLogger(TboResponseParser.class);
	protected static LinkedHashMap<String, TypeBaseAirSegment> airSegMap;
	protected static Map<String, FareInfo> fareInfolMap;

	// Oneway Parse Method
	public static SearchFlightResponse parseResponseOneway(
			TboFlightSearchResponse tboFlightSearchResponse,
			Map<String,List<FlightMarkUpConfig>> markupMap,
			Flightsearch flightsearch, Map<String, String> AirlineNameMap
			,CurrencyConversionMap currencyConversionMap,AppKeyVo appKeyVo,CompanyConfigDAO companyConfigDAO,CompanyDao companyDao) throws Exception {

		CompanyConfig companyConfig = null;
		try {
			companyConfig = appKeyVo.getCompanyConfig();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		Company company = null;
		try{
			company = appKeyVo.getCompany();
		}catch(Exception e){
			e.printStackTrace();
		}
		Company parentCompany = null;
		try{
			parentCompany = companyDao.getParentCompany(company);
		}catch(Exception e){
			e.printStackTrace();
		}

		UAPISearchFlightKeyMap uapiSearchFlightKeyMap = new UAPISearchFlightKeyMap();
		Map<String, FareFlightSegment> fareFlightSegmentMap = new HashMap<String, FareFlightSegment>();
		LinkedHashMap<String, FlightSegments> flightSegmentstMap = new LinkedHashMap<String, FlightSegments>();
		Map<String, FareRules> fareRulesMap = new HashMap<String, FareRules>();
		String flightindexForSegments = "";
		String FlightSegmentsGroupId = new UID().toString();
		String flightindexForfarerules = (new UID()).toString();

		SearchFlightResponse searchFlightResponse = new SearchFlightResponse();
		BigDecimal apiToBaseExchangeRate=new BigDecimal(currencyConversionMap.getCurrencyValue1());
		List<FareFlightSegment> fareFlightSegments = new ArrayList<FareFlightSegment>();
		Response flightresponse = new Response();
		if(tboFlightSearchResponse.getResponse()!=null && tboFlightSearchResponse.getResponse().getResults()!=null && tboFlightSearchResponse.getResponse().getResults().size()>0)
		{
			flightresponse.setResults(tboFlightSearchResponse.getResponse().getResults());
			TreeSet<BigDecimal> uniqPriceSet = new TreeSet<BigDecimal>(new PriceComparator());
			for(List<Result> line : flightresponse.getResults())
			{
				for(Result result : line)
				{
					uniqPriceSet.add(result.getFare().getOfferedFare());
				}
			}			
			if(uniqPriceSet!=null && uniqPriceSet.size()>0)
			{
				for(BigDecimal fare : uniqPriceSet)
				{
					List<FlightSegmentsGroup> flightSegmentsGroups = new ArrayList<FlightSegmentsGroup>();
					FlightSegmentsGroup flightSegmentsGroup = new FlightSegmentsGroup();
					List<FlightSegments> flightsegmentslist = new ArrayList<FlightSegments>();
					List<FareRules> fareRulesList = new ArrayList<FareRules>();

					FareFlightSegment fareFlightSegment = new FareFlightSegment();

					fareFlightSegment.setApiCurrency(currencyConversionMap.getApiCurrency());
					fareFlightSegment.setBaseCurrency(flightsearch.getBaseCurrency());
					fareFlightSegment.setApiToBaseExchangeRate(apiToBaseExchangeRate);
					fareFlightSegment.setId((new UID()).toString());
					fareFlightSegment.setBookingCurrency(flightsearch.getCurrency());
					fareFlightSegment.setExchangeRate(currencyConversionMap.getCurValue());
					fareFlightSegment.setCurrency(currencyConversionMap.getApiCurrency());
					if(flightresponse.getResults()!=null && flightresponse.getResults().size()>0)
					{
						for(List<Result> resultData : flightresponse.getResults())
						{
							if(resultData!=null && resultData.size()>0)
							{
								for(Result result : resultData)
								{
									if(result.getFare()!=null && fare.compareTo(result.getFare().getOfferedFare()) == 0){


										// we are taking offered price as totalprice, but we add base price and taxes we ll get published price.
										//so that we are removing from base price with CommissionEarned,PLBEarned,IncentiveEarned.
										//Their providing offered price based on CommissionEarned,PLBEarned,IncentiveEarned.
										// so we subtract base price using above 3 fields.

										/*  TBO Amount Calculation Formula
										 *
										 *  Published Fare :
									AdditionalTxnFeepub + AirlineTransFee +BaseFare + Tax + OtherCharges + ServiceFee

									Offered Fare(NET PAYABLE FARE) :
		                             Published Fare – (AgentCommission+ IncentiveEarned+ PLBEarned + AdditionalTxnFee) + (TdsOnCommission + TdsOnIncentive + TdsOnPLB)
										 */
										/*BigDecimal removalfarefrombasefare = new BigDecimal("0.0");

		                        if(result.getFare().getAdditionalTxnFee()!=null)
								    removalfarefrombasefare = result.getFare().getCommissionEarned().add(result.getFare().getPLBEarned().add(result.getFare().getIncentiveEarned().add(result.getFare().getAdditionalTxnFee())));
		                        else
		                        	 removalfarefrombasefare = result.getFare().getCommissionEarned().add(result.getFare().getPLBEarned().add(result.getFare().getIncentiveEarned()));


		                        BigDecimal basefareapiprice = new BigDecimal(replaceCurrency(String.valueOf(result.getFare().getBaseFare()), apiCurrency));
								BigDecimal basefare = basefareapiprice.subtract(removalfarefrombasefare);
								BigDecimal addtdstobasefare = result.getFare().getTdsOnCommission().add(result.getFare().getTdsOnIncentive().add(result.getFare().getTdsOnPLB()));
								BigDecimal finalbasefare = basefare.add(addtdstobasefare);

								fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(finalbasefare.multiply(apiToBaseExchangeRate)));
										 */
										//Old Logic

										BigDecimal removalbaseprice = result.getFare().getCommissionEarned().add(result.getFare().getIncentiveEarned().add(result.getFare().getPLBEarned()));
										BigDecimal tdsprice = result.getFare().getTdsOnCommission().add(result.getFare().getTdsOnIncentive().add(result.getFare().getTdsOnPLB()));

										fareFlightSegment.setSupplierCommissionearned(removalbaseprice);
										fareFlightSegment.setSupplierTds(tdsprice);

										fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
												String.valueOf(result.getFare().getBaseFare()), currencyConversionMap.getApiCurrency())).subtract(removalbaseprice).multiply(apiToBaseExchangeRate)));

										fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(AmountRoundingModeUtil.roundingMode(new BigDecimal(replaceCurrency(
												String.valueOf(result.getFare().getOfferedFare()), currencyConversionMap.getApiCurrency())).multiply(apiToBaseExchangeRate))));
										if(result.getFare().getTransactionFee()!=null){
											fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
													String.valueOf(result.getFare().getTax().add(result.getFare().getOtherCharges()).add(result.getFare().getAdditionalTxnFeeOfrd()).add(result.getFare().getAdditionalTxnFeePub()).add(result.getFare().getTransactionFee()).add(tdsprice)), currencyConversionMap.getApiCurrency())).multiply(apiToBaseExchangeRate)));
										}
										else{
											fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
													String.valueOf(result.getFare().getTax().add(result.getFare().getOtherCharges()).add(result.getFare().getAdditionalTxnFeeOfrd()).add(result.getFare().getAdditionalTxnFeePub()).add(tdsprice)), currencyConversionMap.getApiCurrency())).multiply(apiToBaseExchangeRate)));
										}


										/// Here we are showing original base price provided by api
										fareFlightSegment.setApi_basePriceWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
												String.valueOf(result.getFare().getBaseFare()), currencyConversionMap.getApiCurrency())).subtract(result.getFare().getCommissionEarned().add(result.getFare().getPLBEarned().add(result.getFare().getIncentiveEarned()))).multiply(apiToBaseExchangeRate)));
										fareFlightSegment.setApi_totalPriceWithoutMarkup(replaceCurrency(
												String.valueOf(result.getFare().getOfferedFare()), currencyConversionMap.getApiCurrency()));

										if(result.getFare().getTransactionFee()!=null){
											fareFlightSegment.setApi_taxesWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
													String.valueOf(result.getFare().getTax().add(result.getFare().getOtherCharges()).add(result.getFare().getAdditionalTxnFeeOfrd()).add(result.getFare().getAdditionalTxnFeePub()).add(result.getFare().getTransactionFee())), currencyConversionMap.getApiCurrency())).multiply(apiToBaseExchangeRate)));
										}
										else{
											fareFlightSegment.setApi_taxesWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
													String.valueOf(result.getFare().getTax().add(result.getFare().getOtherCharges()).add(result.getFare().getAdditionalTxnFeeOfrd()).add(result.getFare().getAdditionalTxnFeePub())), currencyConversionMap.getApiCurrency())).multiply(apiToBaseExchangeRate)));
										}

										Discount apidiscount = new Discount();
										apidiscount.setAmount(new BigDecimal(replaceCurrency(
												String.valueOf(result.getFare().getDiscount()), currencyConversionMap.getApiCurrency())).multiply(apiToBaseExchangeRate));
										fareFlightSegment.setApiDiscount(apidiscount);

										Discount systemdiscount = new Discount();
										systemdiscount.setAmount(new BigDecimal(0.0));
										fareFlightSegment.setSystemDiscount(systemdiscount);

										Discount discount = new Discount();
										discount.setAmount(new BigDecimal(replaceCurrency(
												String.valueOf(result.getFare().getDiscount()), currencyConversionMap.getApiCurrency())).multiply(apiToBaseExchangeRate).add(new BigDecimal(0.0)));
										fareFlightSegment.setDiscount(discount);

										fareFlightSegment.setLCC(result.getIsLCC());
										fareFlightSegment.setRefundable(result.getIsRefundable());

										if(result.getLastTicketDate()!=null){
											if(StringUtils.isNotEmpty(result.getLastTicketDate()) ){
												String lastTicketingDate= result.getLastTicketDate();
												parseLastTicketDate(lastTicketingDate,fareFlightSegment);
											}
										}
										FlightSegments flightSegments = new FlightSegments();

										String flightindex = "TB"+(new UID()).toString();

										flightindexForSegments = flightindex;
										flightindexForfarerules = flightindexForSegments;
										FlightSegmentsGroupId = flightindexForSegments;

										// Below for flight search key
										flightSegments.setFlightIndex(flightindex);
										flightSegments.setApiProvider("TBO");
										////added on 17th dec 2015
										flightSegments.setBookingCurrency(flightsearch.getCurrency());
										flightSegments.setExchangeRate(currencyConversionMap.getCurValue());

										List<Segments> segmentsList = new ArrayList<Segments>();
										String airlineCode = "ALL";
										for(List<FlightSearchrresponseSegment> Segments : result.getSegments())
										{
											for(FlightSearchrresponseSegment segment : Segments)
											{
												Segments segments = new Segments();
												buildFlightSegments(segment,segments,flightsearch);
												segments.setFareInfoRef(result.getResultIndex());
												if(segment.getDuration() != null)
													segments.setDuration(String.valueOf(segment.getDuration()));
												segmentsList.add(segments);
											}
											flightSegments.setSegments(segmentsList);
											flightsegmentslist.add(flightSegments);
											flightSegmentstMap.put(flightindexForSegments,flightSegments);

										}
										Set<String> fareBasisCodeSet = new HashSet<String>();
										List<FareRule> FareRuleList = new ArrayList<FareRule>();
										FareRules farerules = new FareRules();
										for(com.tayyarah.api.flight.tbo.model.FareRule fareruleobj : result.getFareRules())
										{
											FareRule farerule = new FareRule();
											fareBasisCodeSet.add(fareruleobj.getFareBasisCode());
											farerule.setAirlineCode(fareruleobj.getAirline());
											farerule.setArrCode(fareruleobj.getDestination());
											farerule.setBasisCode(fareruleobj.getFareBasisCode());
											farerule.setDepCode(fareruleobj.getOrigin());
											farerule.setFareProviderCode(tboFlightSearchResponse.getResponse().getTraceId());
											farerule.setFareInfoRef(result.getResultIndex());
											farerule.setFareValue("TB-"+result.getResultIndex());
											if (result.getBaggageAllowance() != null)
											{
												String[] splitedbaggage = result.getBaggageAllowance().split("\\s+");
												farerule.setBagAllowanceValue(new BigInteger(splitedbaggage[0]));
											}
											FareRuleList.add(farerule);
											farerules.setTravelerType(flightsearch.getTripType());
											farerules.setFareRule(FareRuleList);
										}
										fareRulesList.add(farerules);
										fareFlightSegment.setFareRules(fareRulesList);
										fareRulesMap.put(flightindexForfarerules, farerules);

										String fareBasisCode="ALL";
										if(fareBasisCodeSet!=null && fareBasisCodeSet.size()==1){
											fareBasisCode=fareBasisCodeSet.iterator().next();
										}
										airlineCode = result.getAirlineCode();

										// Add MarkUp
										UmarkUpServiceCall.getMarkupValues(flightsearch,
												markupMap,airlineCode,fareFlightSegment,fareBasisCode);

										fareFlightSegment.setFareBasisCode(fareBasisCode);									
										fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getBasePriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
										fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(AmountRoundingModeUtil.roundingMode(new BigDecimal(fareFlightSegment.getTotalPriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate()))));
										fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getTaxesWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
										fareFlightSegment.setBasePrice(String.valueOf(new BigDecimal(fareFlightSegment.getBasePrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
										fareFlightSegment.setTaxes(String.valueOf(new BigDecimal(fareFlightSegment.getTaxes()).multiply(flightsearch.getBaseToBookingExchangeRate())));

										// Add Service In total Price
										FlightServiceTax flightServiceTax = null;
										FlightGstTax flightGstTax = null;
										if(companyConfig != null){										
											if(companyConfig.getCompanyConfigType().isB2E()){
												if(companyConfig.getTaxtype()!=null && companyConfig.getTaxtype().equalsIgnoreCase("GST")){													
													fareFlightSegment.setTotalPrice(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
													flightGstTax = new FlightGstTax();
													flightGstTax = getFlightGstTax(flightsearch, companyConfig, company,parentCompany);
													BigDecimal totalPrice = new BigDecimal(fareFlightSegment.getTotalPrice());
													BigDecimal totalPriceAfterServiceTax = totalPrice.add(flightGstTax.getTotalTax()).add(flightGstTax.getManagementFee());
													fareFlightSegment.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(totalPriceAfterServiceTax)));
												}else{													
													fareFlightSegment.setTotalPrice(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
													flightServiceTax = new FlightServiceTax();
													flightServiceTax = getFlightServiceTax(fareFlightSegment.getBasePrice(),result.getFare().getYQTax(),flightsearch,companyConfig);
													BigDecimal totalPrice = new BigDecimal(fareFlightSegment.getTotalPrice());
													BigDecimal totalPriceAfterServiceTax = totalPrice.add(flightServiceTax.getTotalServiceTax()).add(flightServiceTax.getManagementFee());
													fareFlightSegment.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(totalPriceAfterServiceTax)));
												}												
											}
											else{
												fareFlightSegment.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate()))));
											}
										}else{
											fareFlightSegment.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate()))));
										}
										fareFlightSegmentMap.put(FlightSegmentsGroupId,fareFlightSegment);
									}
									flightSegmentsGroup.setFlightSegments(flightsegmentslist);
								}
							}
						}
					}
					flightSegmentsGroups.add(flightSegmentsGroup);
					fareFlightSegment.setFlightSegmentsGroups(flightSegmentsGroups);
					fareFlightSegments.add(fareFlightSegment);
					searchFlightResponse.setFareFlightSegment(fareFlightSegments);
					uapiSearchFlightKeyMap.setFareRulesMap(fareRulesMap);
					uapiSearchFlightKeyMap.setFareFlightSegmentMap(fareFlightSegmentMap);
					uapiSearchFlightKeyMap.setFlightSegmentstMap(flightSegmentstMap);
					uapiSearchFlightKeyMap.setFlightsearch(flightsearch);
					uapiSearchFlightKeyMap.setFlightMarkUpConfiglistMap(markupMap);
					uapiSearchFlightKeyMap.setExchangeRate(currencyConversionMap.getCurValue());
					searchFlightResponse.setUapiSearchFlightKeyMap(uapiSearchFlightKeyMap);
				}
			}
		}
		return searchFlightResponse;
	}

	// Oneway Parse Method for Domestic
	public static SearchFlightResponse parseResponseOnewayDomestic(
			TboFlightSearchResponse tboFlightSearchResponse,
			Map<String,List<FlightMarkUpConfig>> markupMap,
			Flightsearch flightsearch, Map<String, String> AirlineNameMap
			,CurrencyConversionMap currencyConversionMap,AppKeyVo appKeyVo,CompanyConfigDAO companyConfigDAO,CompanyDao companyDao)  {
		SearchFlightResponse searchFlightResponse = new SearchFlightResponse();

		try{
			CompanyConfig companyConfig = null;
			try {
				companyConfig = appKeyVo.getCompanyConfig();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			Company company = null;
			try{
				company = appKeyVo.getCompany();
			}catch(Exception e){
				e.printStackTrace();
			}
			Company parentCompany = null;
			try{
				parentCompany = companyDao.getParentCompany(company);
			}catch(Exception e){
				e.printStackTrace();
			}

			UAPISearchFlightKeyMap uapiSearchFlightKeyMap = new UAPISearchFlightKeyMap();
			Map<String, FareFlightSegment> fareFlightSegmentMap = new HashMap<String, FareFlightSegment>();
			LinkedHashMap<String, FlightSegments> flightSegmentstMap = new LinkedHashMap<String, FlightSegments>();		
			Map<String, FareRules> fareRulesMap = new HashMap<String, FareRules>();
			String flightindexForSegments = "";
			String FlightSegmentsGroupId = new UID().toString();
			String flightindexForfarerules = (new UID()).toString();
			BigDecimal apiToBaseExchangeRate=new BigDecimal(currencyConversionMap.getCurrencyValue1());
			List<FareFlightSegment> fareFlightSegments = new ArrayList<FareFlightSegment>();

			Response flightresponse = new Response();
			if(tboFlightSearchResponse.getResponse()!=null && tboFlightSearchResponse.getResponse().getResults()!=null && tboFlightSearchResponse.getResponse().getResults().size()>0)
			{
				flightresponse.setResults(tboFlightSearchResponse.getResponse().getResults());

				for(List<Result> resultData : flightresponse.getResults())
				{
					if(resultData!=null && resultData.size()>0)
					{
						for(Result result : resultData)
						{
							List<FlightSegmentsGroup> flightSegmentsGroups = new ArrayList<FlightSegmentsGroup>();
							FlightSegmentsGroup flightSegmentsGroup = new FlightSegmentsGroup();
							List<FlightSegments> flightsegmentslist = new ArrayList<FlightSegments>();
							List<FareRules> fareRulesList = new ArrayList<FareRules>();

							FareFlightSegment fareFlightSegment = new FareFlightSegment();
							fareFlightSegment.setApiCurrency(currencyConversionMap.getApiCurrency());
							fareFlightSegment.setBaseCurrency(flightsearch.getBaseCurrency());
							fareFlightSegment.setApiToBaseExchangeRate(apiToBaseExchangeRate);
							fareFlightSegment.setId((new UID()).toString());
							fareFlightSegment.setBookingCurrency(flightsearch.getCurrency());
							fareFlightSegment.setExchangeRate(currencyConversionMap.getCurValue());
							fareFlightSegment.setCurrency(currencyConversionMap.getApiCurrency());

							//if(fare.compareTo(result.getFare().getOfferedFare()) == 0){

							// we are taking offered price as totalprice, but we add base price and taxes we ll get published price.
							//so that we are removing from base price with CommissionEarned,PLBEarned,IncentiveEarned.
							//Their providing offered price based on CommissionEarned,PLBEarned,IncentiveEarned.
							// so we subtract base price using above 3 fields.

							/*  TBO Amount Calculation Formula
							 *
							 *  Published Fare :
										AdditionalTxnFeepub + AirlineTransFee +BaseFare + Tax + OtherCharges + ServiceFee

										Offered Fare(NET PAYABLE FARE) :
			                             Published Fare – (AgentCommission+ IncentiveEarned+ PLBEarned + AdditionalTxnFee) + (TdsOnCommission + TdsOnIncentive + TdsOnPLB)
							 */
							/*BigDecimal removalfarefrombasefare = new BigDecimal("0.0");

			                        if(result.getFare().getAdditionalTxnFee()!=null)
									    removalfarefrombasefare = result.getFare().getCommissionEarned().add(result.getFare().getPLBEarned().add(result.getFare().getIncentiveEarned().add(result.getFare().getAdditionalTxnFee())));
			                        else
			                        	 removalfarefrombasefare = result.getFare().getCommissionEarned().add(result.getFare().getPLBEarned().add(result.getFare().getIncentiveEarned()));

		                            BigDecimal basefareapiprice = new BigDecimal(replaceCurrency(String.valueOf(result.getFare().getBaseFare()), apiCurrency));
									BigDecimal basefare = basefareapiprice.subtract(removalfarefrombasefare);
									BigDecimal addtdstobasefare = result.getFare().getTdsOnCommission().add(result.getFare().getTdsOnIncentive().add(result.getFare().getTdsOnPLB()));
									BigDecimal finalbasefare = basefare.add(addtdstobasefare);

									fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(finalbasefare.multiply(apiToBaseExchangeRate)));
							 */
							//Old Logic

							BigDecimal removalbaseprice = result.getFare().getCommissionEarned().add(result.getFare().getIncentiveEarned().add(result.getFare().getPLBEarned()));
							BigDecimal tdsprice = result.getFare().getTdsOnCommission().add(result.getFare().getTdsOnIncentive().add(result.getFare().getTdsOnPLB()));

							fareFlightSegment.setSupplierCommissionearned(removalbaseprice);
							fareFlightSegment.setSupplierTds(tdsprice);

							fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
									String.valueOf(result.getFare().getBaseFare()), currencyConversionMap.getApiCurrency())).subtract(removalbaseprice).multiply(apiToBaseExchangeRate)));


							fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(AmountRoundingModeUtil.roundingMode(new BigDecimal(replaceCurrency(
									String.valueOf(result.getFare().getOfferedFare()), currencyConversionMap.getApiCurrency())).multiply(apiToBaseExchangeRate))));
							if(result.getFare().getTransactionFee()!=null){
								fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
										String.valueOf(result.getFare().getTax().add(result.getFare().getOtherCharges()).add(result.getFare().getAdditionalTxnFeeOfrd()).add(result.getFare().getAdditionalTxnFeePub()).add(result.getFare().getTransactionFee()).add(tdsprice)), currencyConversionMap.getApiCurrency())).multiply(apiToBaseExchangeRate)));
							}
							else{
								fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
										String.valueOf(result.getFare().getTax().add(result.getFare().getOtherCharges()).add(result.getFare().getAdditionalTxnFeeOfrd()).add(result.getFare().getAdditionalTxnFeePub()).add(tdsprice)), currencyConversionMap.getApiCurrency())).multiply(apiToBaseExchangeRate)));
							}

							/// Here we are showing original base price provided by api
							fareFlightSegment.setApi_basePriceWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
									String.valueOf(result.getFare().getBaseFare()), currencyConversionMap.getApiCurrency())).subtract(result.getFare().getCommissionEarned().add(result.getFare().getPLBEarned().add(result.getFare().getIncentiveEarned()))).multiply(apiToBaseExchangeRate)));

							fareFlightSegment.setApi_totalPriceWithoutMarkup(replaceCurrency(
									String.valueOf(result.getFare().getOfferedFare()), currencyConversionMap.getApiCurrency()));

							if(result.getFare().getTransactionFee()!=null){
								fareFlightSegment.setApi_taxesWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
										String.valueOf(result.getFare().getTax().add(result.getFare().getOtherCharges()).add(result.getFare().getAdditionalTxnFeeOfrd()).add(result.getFare().getAdditionalTxnFeePub()).add(result.getFare().getTransactionFee())), currencyConversionMap.getApiCurrency())).multiply(apiToBaseExchangeRate)));
							}
							else{
								fareFlightSegment.setApi_taxesWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
										String.valueOf(result.getFare().getTax().add(result.getFare().getOtherCharges()).add(result.getFare().getAdditionalTxnFeeOfrd()).add(result.getFare().getAdditionalTxnFeePub())), currencyConversionMap.getApiCurrency())).multiply(apiToBaseExchangeRate)));
							}
							Discount apidiscount = new Discount();
							apidiscount.setAmount(new BigDecimal(replaceCurrency(
									String.valueOf(result.getFare().getDiscount()), currencyConversionMap.getApiCurrency())).multiply(apiToBaseExchangeRate));
							fareFlightSegment.setApiDiscount(apidiscount);

							Discount systemdiscount = new Discount();
							systemdiscount.setAmount(new BigDecimal(0.0));
							fareFlightSegment.setSystemDiscount(systemdiscount);

							Discount discount = new Discount();
							discount.setAmount(new BigDecimal(replaceCurrency(
									String.valueOf(result.getFare().getDiscount()), currencyConversionMap.getApiCurrency())).multiply(apiToBaseExchangeRate).add(new BigDecimal(0.0)));
							fareFlightSegment.setDiscount(discount);

							fareFlightSegment.setLCC(result.getIsLCC());
							fareFlightSegment.setRefundable(result.getIsRefundable());
							if(result.getLastTicketDate()!=null){
								if(StringUtils.isNotEmpty(result.getLastTicketDate()) ){
									String lastTicketingDate= result.getLastTicketDate();
									parseLastTicketDate(lastTicketingDate,fareFlightSegment);
								}
							}

							FlightSegments flightSegments = new FlightSegments();

							String flightindex = "TB"+(new UID()).toString();
							flightindexForSegments = flightindex;
							flightindexForfarerules = flightindexForSegments;
							FlightSegmentsGroupId = flightindexForSegments;

							// Below for flight search key
							flightSegments.setFlightIndex(flightindex);
							flightSegments.setApiProvider("TBO");
							////added on 17th dec 2015
							flightSegments.setBookingCurrency(flightsearch.getCurrency());
							flightSegments.setExchangeRate(currencyConversionMap.getCurValue());

							List<Segments> segmentsList = new ArrayList<Segments>();
							String airlineCode = "ALL";

							if(result.getSegments()!=null && result.getSegments().size()>0)
							{
								for(List<FlightSearchrresponseSegment> Segments : result.getSegments())
								{
									for(FlightSearchrresponseSegment segment : Segments)
									{
										Segments segments = new Segments();

										// Build Segments
										buildFlightSegments(segment,segments,flightsearch);

										segments.setFareInfoRef(result.getResultIndex());
										if(segment.getDuration() != null && segment.getDuration().intValue()!=0)
											segments.setDuration(String.valueOf(segment.getDuration()));
										else
										{
											if(segment.getOrigin()!=null && segment.getOrigin().getDepTime()!=null && !segment.getOrigin().getDepTime().equalsIgnoreCase("") && segment.getDestination()!=null && segment.getDestination().getArrTime()!=null && !segment.getDestination().getArrTime().equalsIgnoreCase(""))
											{
												SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
												String departdatetime = segment.getOrigin().getDepTime().substring(0, 10) +" " + segment.getOrigin().getDepTime().substring(11, 19);
												String arrivaldatetime = segment.getDestination().getArrTime().substring(0, 10) +" " + segment.getDestination().getArrTime().substring(11, 19);

												Date d1 = null;
												Date d2 = null;
												long diffMinutes = 0 ;

												try {
													d1 = format.parse(departdatetime);
													d2 = format.parse(arrivaldatetime);
													long diff = d2.getTime() - d1.getTime();
													diffMinutes = diff / (60 * 1000) % 60;
												}catch(Exception e) {
													e.printStackTrace();
												}
												segments.setDuration(String.valueOf(diffMinutes));
											}
										}
										segmentsList.add(segments);
									}
									flightSegments.setSegments(segmentsList);
									flightsegmentslist.add(flightSegments);
									flightSegmentstMap.put(flightindexForSegments,flightSegments);
								}
							}
							Set<String> fareBasisCodeSet = new HashSet<String>();
							List<FareRule> FareRuleList = new ArrayList<FareRule>();
							FareRules farerules = new FareRules();
							if(result.getFareRules()!=null && result.getFareRules().size()>0)
							{
								for(com.tayyarah.api.flight.tbo.model.FareRule fareruleobj : result.getFareRules())
								{
									FareRule farerule = new FareRule();
									fareBasisCodeSet.add(fareruleobj.getFareBasisCode());
									farerule.setAirlineCode(fareruleobj.getAirline());
									farerule.setArrCode(fareruleobj.getDestination());
									farerule.setBasisCode(fareruleobj.getFareBasisCode());
									farerule.setDepCode(fareruleobj.getOrigin());
									farerule.setFareProviderCode(tboFlightSearchResponse.getResponse().getTraceId());
									farerule.setFareInfoRef(result.getResultIndex());
									farerule.setFareValue("TB-"+result.getResultIndex());
									if (result.getBaggageAllowance() != null)
									{
										String[] splitedbaggage = result.getBaggageAllowance().split("\\s+");
										farerule.setBagAllowanceValue(new BigInteger(splitedbaggage[0]));
									}
									FareRuleList.add(farerule);
									farerules.setTravelerType(flightsearch.getTripType());
									farerules.setFareRule(FareRuleList);
								}
								fareRulesList.add(farerules);
								fareFlightSegment.setFareRules(fareRulesList);
							}
							fareRulesMap.put(flightindexForfarerules, farerules);
							String fareBasisCode="ALL";
							if(fareBasisCodeSet!=null && fareBasisCodeSet.size()==1){
								fareBasisCode=fareBasisCodeSet.iterator().next();
							}
							airlineCode = result.getAirlineCode();

							// Add MarkUp
							UmarkUpServiceCall.getMarkupValues(flightsearch,
									markupMap,airlineCode,fareFlightSegment,fareBasisCode);

							fareFlightSegment.setFareBasisCode(fareBasisCode);							
							fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getBasePriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
							fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(AmountRoundingModeUtil.roundingMode(new BigDecimal(fareFlightSegment.getTotalPriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate()))));
							fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getTaxesWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
							fareFlightSegment.setBasePrice(String.valueOf(new BigDecimal(fareFlightSegment.getBasePrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
							fareFlightSegment.setTaxes(String.valueOf(new BigDecimal(fareFlightSegment.getTaxes()).multiply(flightsearch.getBaseToBookingExchangeRate())));

							// Add Service In total Price
							FlightServiceTax flightServiceTax = null;
							FlightGstTax flightGstTax = null;
							if(companyConfig != null){
								if(companyConfig.getCompanyConfigType().isB2E()){
									if(companyConfig.getTaxtype()!=null && companyConfig.getTaxtype().equalsIgnoreCase("GST")){													
										fareFlightSegment.setTotalPrice(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
										flightGstTax = new FlightGstTax();
										flightGstTax = getFlightGstTax(flightsearch, companyConfig, company,parentCompany);
										BigDecimal totalPrice = new BigDecimal(fareFlightSegment.getTotalPrice());
										BigDecimal totalPriceAfterServiceTax = totalPrice.add(flightGstTax.getTotalTax()).add(flightGstTax.getManagementFee());
										fareFlightSegment.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(totalPriceAfterServiceTax)));
									}else{	
										fareFlightSegment.setTotalPrice(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
										flightServiceTax = new FlightServiceTax();
										flightServiceTax = getFlightServiceTax(fareFlightSegment.getBasePrice(),result.getFare().getYQTax(),flightsearch,companyConfig);
										BigDecimal totalPrice = new BigDecimal(fareFlightSegment.getTotalPrice());
										BigDecimal totalPriceAfterServiceTax = totalPrice.add(flightServiceTax.getTotalServiceTax()).add(flightServiceTax.getManagementFee());
										fareFlightSegment.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(totalPriceAfterServiceTax)));
									}
								}								
								else{
									fareFlightSegment.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate()))));
								}
							}else{
								fareFlightSegment.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate()))));
							}
							fareFlightSegment.setFlightGstTax(flightGstTax);
							fareFlightSegment.setFlightServiceTax(flightServiceTax);
							fareFlightSegmentMap.put(FlightSegmentsGroupId,fareFlightSegment);
							flightSegmentsGroup.setFlightSegments(flightsegmentslist);
							flightSegmentsGroups.add(flightSegmentsGroup);
							fareFlightSegment.setFlightSegmentsGroups(flightSegmentsGroups);
							fareFlightSegments.add(fareFlightSegment);
						}
					}
					searchFlightResponse.setFareFlightSegment(fareFlightSegments);
					uapiSearchFlightKeyMap.setFareRulesMap(fareRulesMap);
					uapiSearchFlightKeyMap.setFareFlightSegmentMap(fareFlightSegmentMap);
					uapiSearchFlightKeyMap.setFlightSegmentstMap(flightSegmentstMap);
					uapiSearchFlightKeyMap.setFlightsearch(flightsearch);
					uapiSearchFlightKeyMap.setFlightMarkUpConfiglistMap(markupMap);
					uapiSearchFlightKeyMap.setExchangeRate(currencyConversionMap.getCurValue());
					searchFlightResponse.setUapiSearchFlightKeyMap(uapiSearchFlightKeyMap);
				}
			}
		}catch(Exception e){
			System.out.println("Exception" + e);
		}

		return searchFlightResponse;
	}

	// Round Parse Method for Domestic Special (SR)
	public static SearchFlightResponse parseResponseRoundSpecialDomestic(
			TboFlightSearchResponse tboFlightSearchResponse,
			Map<String,List<FlightMarkUpConfig>> markupMap,
			Flightsearch flightsearch, Map<String, String> AirlineNameMap
			,CurrencyConversionMap currencyConversionMap,AppKeyVo appKeyVo,CompanyConfigDAO companyConfigDAO,CompanyDao companyDao)  {
		SearchFlightResponse searchFlightResponse = new SearchFlightResponse();

		try{
			CompanyConfig companyConfig = null;
			try {
				companyConfig = appKeyVo.getCompanyConfig();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			Company company = null;
			try{
				company = appKeyVo.getCompany();
			}catch(Exception e){
				e.printStackTrace();
			}
			Company parentCompany = null;
			try{
				parentCompany = companyDao.getParentCompany(company);
			}catch(Exception e){
				e.printStackTrace();
			}

			UAPISearchFlightKeyMap uapiSearchFlightKeyMap = new UAPISearchFlightKeyMap();
			Map<String, FareFlightSegment> fareFlightSegmentMap = new HashMap<String, FareFlightSegment>();
			LinkedHashMap<String, FlightSegments> flightSegmentstMap = new LinkedHashMap<String, FlightSegments>();		
			Map<String, FareRules> fareRulesMap = new HashMap<String, FareRules>();
			String flightindexForSegments = "";
			String FlightSegmentsGroupId = new UID().toString();
			String flightindexForfarerules = (new UID()).toString();
			BigDecimal apiToBaseExchangeRate=new BigDecimal(currencyConversionMap.getCurrencyValue1());
			List<FareFlightSegment> fareFlightSegments = new ArrayList<FareFlightSegment>();

			Response flightresponse = new Response();
			if(tboFlightSearchResponse.getResponse()!=null && tboFlightSearchResponse.getResponse().getResults()!=null && tboFlightSearchResponse.getResponse().getResults().size()>0)
			{
				flightresponse.setResults(tboFlightSearchResponse.getResponse().getResults());

				for(List<Result> resultData : flightresponse.getResults())
				{
					if(resultData!=null && resultData.size()>0)
					{
						for(Result result : resultData)
						{
							List<FlightSegmentsGroup> flightSegmentsGroups = new ArrayList<FlightSegmentsGroup>();
							FlightSegmentsGroup flightSegmentsGroup = new FlightSegmentsGroup();
							List<FlightSegments> flightsegmentslist = new ArrayList<FlightSegments>();
							List<FareRules> fareRulesList = new ArrayList<FareRules>();
							FareFlightSegment fareFlightSegment = new FareFlightSegment();

							fareFlightSegment.setApiCurrency(currencyConversionMap.getApiCurrency());
							fareFlightSegment.setBaseCurrency(flightsearch.getBaseCurrency());
							fareFlightSegment.setApiToBaseExchangeRate(apiToBaseExchangeRate);
							fareFlightSegment.setId((new UID()).toString());
							fareFlightSegment.setBookingCurrency(flightsearch.getCurrency());
							fareFlightSegment.setExchangeRate(currencyConversionMap.getCurValue());
							fareFlightSegment.setCurrency(currencyConversionMap.getApiCurrency());

							//if(fare.compareTo(result.getFare().getOfferedFare()) == 0){

							// we are taking offered price as totalprice, but we add base price and taxes we ll get published price.
							//so that we are removing from base price with CommissionEarned,PLBEarned,IncentiveEarned.
							//Their providing offered price based on CommissionEarned,PLBEarned,IncentiveEarned.
							// so we subtract base price using above 3 fields.

							/*  TBO Amount Calculation Formula
							 *
							 *  Published Fare :
											AdditionalTxnFeepub + AirlineTransFee +BaseFare + Tax + OtherCharges + ServiceFee

											Offered Fare(NET PAYABLE FARE) :
				                             Published Fare – (AgentCommission+ IncentiveEarned+ PLBEarned + AdditionalTxnFee) + (TdsOnCommission + TdsOnIncentive + TdsOnPLB)
							 */
							/*BigDecimal removalfarefrombasefare = new BigDecimal("0.0");

				                        if(result.getFare().getAdditionalTxnFee()!=null)
										    removalfarefrombasefare = result.getFare().getCommissionEarned().add(result.getFare().getPLBEarned().add(result.getFare().getIncentiveEarned().add(result.getFare().getAdditionalTxnFee())));
				                        else
				                        	 removalfarefrombasefare = result.getFare().getCommissionEarned().add(result.getFare().getPLBEarned().add(result.getFare().getIncentiveEarned()));


				                        BigDecimal basefareapiprice = new BigDecimal(replaceCurrency(String.valueOf(result.getFare().getBaseFare()), apiCurrency));
										BigDecimal basefare = basefareapiprice.subtract(removalfarefrombasefare);
										BigDecimal addtdstobasefare = result.getFare().getTdsOnCommission().add(result.getFare().getTdsOnIncentive().add(result.getFare().getTdsOnPLB()));
										BigDecimal finalbasefare = basefare.add(addtdstobasefare);

										fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(finalbasefare.multiply(apiToBaseExchangeRate)));
							 */
							//Old Logic

							BigDecimal removalbaseprice = result.getFare().getCommissionEarned().add(result.getFare().getIncentiveEarned().add(result.getFare().getPLBEarned()));
							BigDecimal tdsprice = result.getFare().getTdsOnCommission().add(result.getFare().getTdsOnIncentive().add(result.getFare().getTdsOnPLB()));

							fareFlightSegment.setSupplierCommissionearned(removalbaseprice);
							fareFlightSegment.setSupplierTds(tdsprice);
							fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
									String.valueOf(result.getFare().getBaseFare()), currencyConversionMap.getApiCurrency())).subtract(removalbaseprice).multiply(apiToBaseExchangeRate)));

							fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(AmountRoundingModeUtil.roundingMode(new BigDecimal(replaceCurrency(
									String.valueOf(result.getFare().getOfferedFare()), currencyConversionMap.getApiCurrency())).multiply(apiToBaseExchangeRate))));
							if(result.getFare().getTransactionFee()!=null){
								fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
										String.valueOf(result.getFare().getTax().add(result.getFare().getOtherCharges()).add(result.getFare().getAdditionalTxnFeeOfrd()).add(result.getFare().getAdditionalTxnFeePub()).add(result.getFare().getTransactionFee()).add(tdsprice)), currencyConversionMap.getApiCurrency())).multiply(apiToBaseExchangeRate)));
							}
							else{
								fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
										String.valueOf(result.getFare().getTax().add(result.getFare().getOtherCharges()).add(result.getFare().getAdditionalTxnFeeOfrd()).add(result.getFare().getAdditionalTxnFeePub()).add(tdsprice)), currencyConversionMap.getApiCurrency())).multiply(apiToBaseExchangeRate)));
							}

							/// Here we are showing original base price provided by api
							fareFlightSegment.setApi_basePriceWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
									String.valueOf(result.getFare().getBaseFare()), currencyConversionMap.getApiCurrency())).subtract(result.getFare().getCommissionEarned().add(result.getFare().getPLBEarned().add(result.getFare().getIncentiveEarned()))).multiply(apiToBaseExchangeRate)));

							fareFlightSegment.setApi_totalPriceWithoutMarkup(replaceCurrency(
									String.valueOf(result.getFare().getOfferedFare()), currencyConversionMap.getApiCurrency()));

							if(result.getFare().getTransactionFee()!=null){
								fareFlightSegment.setApi_taxesWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
										String.valueOf(result.getFare().getTax().add(result.getFare().getOtherCharges()).add(result.getFare().getAdditionalTxnFeeOfrd()).add(result.getFare().getAdditionalTxnFeePub()).add(result.getFare().getTransactionFee())), currencyConversionMap.getApiCurrency())).multiply(apiToBaseExchangeRate)));
							}
							else{
								fareFlightSegment.setApi_taxesWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
										String.valueOf(result.getFare().getTax().add(result.getFare().getOtherCharges()).add(result.getFare().getAdditionalTxnFeeOfrd()).add(result.getFare().getAdditionalTxnFeePub())), currencyConversionMap.getApiCurrency())).multiply(apiToBaseExchangeRate)));
							}
							Discount apidiscount = new Discount();
							apidiscount.setAmount(new BigDecimal(replaceCurrency(
									String.valueOf(result.getFare().getDiscount()), currencyConversionMap.getApiCurrency())).multiply(apiToBaseExchangeRate));
							fareFlightSegment.setApiDiscount(apidiscount);

							Discount systemdiscount = new Discount();
							systemdiscount.setAmount(new BigDecimal(0.0));
							fareFlightSegment.setSystemDiscount(systemdiscount);

							Discount discount = new Discount();
							discount.setAmount(new BigDecimal(replaceCurrency(
									String.valueOf(result.getFare().getDiscount()), currencyConversionMap.getApiCurrency())).multiply(apiToBaseExchangeRate).add(new BigDecimal(0.0)));
							fareFlightSegment.setDiscount(discount);

							fareFlightSegment.setLCC(result.getIsLCC());
							fareFlightSegment.setRefundable(result.getIsRefundable());

							if(result.getLastTicketDate()!=null){
								if(StringUtils.isNotEmpty(result.getLastTicketDate()) ){
									String lastTicketingDate= result.getLastTicketDate();
									parseLastTicketDate(lastTicketingDate,fareFlightSegment);
								}
							}
							FlightSegments flightSegments = new FlightSegments();

							String flightindex = "TB"+(new UID()).toString();
							flightindexForSegments = flightindex;
							flightindexForfarerules = flightindexForSegments;
							FlightSegmentsGroupId = flightindexForSegments;

							// Below for flight search key
							flightSegments.setFlightIndex(flightindex);
							flightSegments.setApiProvider("TBO");
							////added on 17th dec 2015
							flightSegments.setBookingCurrency(flightsearch.getCurrency());
							flightSegments.setExchangeRate(currencyConversionMap.getCurValue());

							List<Segments> segmentsList = new ArrayList<Segments>();
							String airlineCode = "ALL";

							if(result.getSegments()!=null && result.getSegments().size()>0)
							{
								for(List<FlightSearchrresponseSegment> Segments : result.getSegments())
								{
									for(FlightSearchrresponseSegment segment : Segments)
									{
										Segments segments = new Segments();

										// Build Segments
										buildFlightSegments(segment,segments,flightsearch);

										segments.setFareInfoRef(result.getResultIndex());

										if(segment.getDuration() != null && segment.getDuration().intValue()!=0)
											segments.setDuration(String.valueOf(segment.getDuration()));
										else
										{
											if(segment.getOrigin()!=null && segment.getOrigin().getDepTime()!=null && !segment.getOrigin().getDepTime().equalsIgnoreCase("") && segment.getDestination()!=null && segment.getDestination().getArrTime()!=null && !segment.getDestination().getArrTime().equalsIgnoreCase(""))
											{
												SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
												String departdatetime = segment.getOrigin().getDepTime().substring(0, 10) +" " + segment.getOrigin().getDepTime().substring(11, 19);
												String arrivaldatetime = segment.getDestination().getArrTime().substring(0, 10) +" " + segment.getDestination().getArrTime().substring(11, 19);

												Date d1 = null;
												Date d2 = null;
												long diffMinutes = 0 ;

												try {
													d1 = format.parse(departdatetime);
													d2 = format.parse(arrivaldatetime);
													long diff = d2.getTime() - d1.getTime();
													diffMinutes = diff / (60 * 1000) % 60;
												}catch(Exception e) {
													e.printStackTrace();
												}
												segments.setDuration(String.valueOf(diffMinutes));
											}
										}
										segmentsList.add(segments);
									}
									flightSegments.setSegments(segmentsList);
									flightsegmentslist.add(flightSegments);
									flightSegmentstMap.put(flightindexForSegments,flightSegments);

								}
							}
							Set<String> fareBasisCodeSet = new HashSet<String>();
							List<FareRule> FareRuleList = new ArrayList<FareRule>();
							FareRules farerules = new FareRules();

							if(result.getFareRules()!=null && result.getFareRules().size()>0)
							{
								for(com.tayyarah.api.flight.tbo.model.FareRule fareruleobj : result.getFareRules())
								{
									FareRule farerule = new FareRule();

									fareBasisCodeSet.add(fareruleobj.getFareBasisCode());
									farerule.setAirlineCode(fareruleobj.getAirline());
									farerule.setArrCode(fareruleobj.getDestination());
									farerule.setBasisCode(fareruleobj.getFareBasisCode());
									farerule.setDepCode(fareruleobj.getOrigin());
									farerule.setFareProviderCode(tboFlightSearchResponse.getResponse().getTraceId());
									farerule.setFareInfoRef(result.getResultIndex());
									farerule.setFareValue("TB-"+result.getResultIndex());
									if (result.getBaggageAllowance() != null)
									{
										String[] splitedbaggage = result.getBaggageAllowance().split("\\s+");
										farerule.setBagAllowanceValue(new BigInteger(splitedbaggage[0]));
									}
									FareRuleList.add(farerule);
									farerules.setTravelerType(flightsearch.getTripType());
									farerules.setFareRule(FareRuleList);
								}
								fareRulesList.add(farerules);
								fareFlightSegment.setFareRules(fareRulesList);
							}
							fareRulesMap.put(flightindexForfarerules, farerules);
							String fareBasisCode="ALL";
							if(fareBasisCodeSet!=null && fareBasisCodeSet.size()==1){
								fareBasisCode=fareBasisCodeSet.iterator().next();
							}
							airlineCode = result.getAirlineCode();

							// Add Markup
							UmarkUpServiceCall.getMarkupValues(flightsearch,
									markupMap,airlineCode,fareFlightSegment,fareBasisCode);
							fareFlightSegment.setFareBasisCode(fareBasisCode);
							fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getBasePriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
							fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(AmountRoundingModeUtil.roundingMode(new BigDecimal(fareFlightSegment.getTotalPriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate()))));
							fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getTaxesWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
							fareFlightSegment.setBasePrice(String.valueOf(new BigDecimal(fareFlightSegment.getBasePrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
							fareFlightSegment.setTaxes(String.valueOf(new BigDecimal(fareFlightSegment.getTaxes()).multiply(flightsearch.getBaseToBookingExchangeRate())));

							// Add Service In total Price
							FlightServiceTax flightServiceTax = null;
							FlightGstTax flightGstTax = null;
							if(companyConfig != null){
								if(companyConfig.getCompanyConfigType().isB2E()){
									if(companyConfig.getTaxtype()!=null && companyConfig.getTaxtype().equalsIgnoreCase("GST")){													
										fareFlightSegment.setTotalPrice(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
										flightGstTax = new FlightGstTax();
										flightGstTax = getFlightGstTax(flightsearch, companyConfig, company,parentCompany);
										BigDecimal totalPrice = new BigDecimal(fareFlightSegment.getTotalPrice());
										BigDecimal totalPriceAfterServiceTax = totalPrice.add(flightGstTax.getTotalTax()).add(flightGstTax.getManagementFee());
										fareFlightSegment.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(totalPriceAfterServiceTax)));
									}else{	
										fareFlightSegment.setTotalPrice(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
										flightServiceTax = new FlightServiceTax();
										flightServiceTax = getFlightServiceTax(fareFlightSegment.getBasePrice(),result.getFare().getYQTax(),flightsearch,companyConfig);
										BigDecimal totalPrice = new BigDecimal(fareFlightSegment.getTotalPrice());
										BigDecimal totalPriceAfterServiceTax = totalPrice.add(flightServiceTax.getTotalServiceTax()).add(flightServiceTax.getManagementFee());
										fareFlightSegment.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(totalPriceAfterServiceTax)));
									}
								}else{
									fareFlightSegment.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate()))));
								}
							}else{
								fareFlightSegment.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate()))));
							}
							fareFlightSegmentMap.put(FlightSegmentsGroupId,fareFlightSegment);							
							flightSegmentsGroup.setFlightSegments(flightsegmentslist);
							flightSegmentsGroups.add(flightSegmentsGroup);
							fareFlightSegment.setFlightSegmentsGroups(flightSegmentsGroups);
							fareFlightSegments.add(fareFlightSegment);
						}
					}
					searchFlightResponse.setFareFlightSegment(fareFlightSegments);
					uapiSearchFlightKeyMap.setFareRulesMap(fareRulesMap);
					uapiSearchFlightKeyMap.setFareFlightSegmentMap(fareFlightSegmentMap);
					uapiSearchFlightKeyMap.setFlightSegmentstMap(flightSegmentstMap);
					uapiSearchFlightKeyMap.setFlightsearch(flightsearch);
					uapiSearchFlightKeyMap.setFlightMarkUpConfiglistMap(markupMap);
					uapiSearchFlightKeyMap.setExchangeRate(currencyConversionMap.getCurValue());
					searchFlightResponse.setUapiSearchFlightKeyMap(uapiSearchFlightKeyMap);
				}
			}
		}catch(Exception e){
			System.out.println("Exception" + e);
		}
		return searchFlightResponse;
	}

	// Round Trip Parse Method
	public static SearchFlightResponse parseResponseRoundTrip(
			TboFlightSearchResponse tboFlightSearchResponse,
			Map<String,List<FlightMarkUpConfig>> markupMap,
			Flightsearch flightsearch, Map<String, String> AirlineNameMap
			,CurrencyConversionMap currencyConversionMap,AppKeyVo appKeyVo,CompanyConfigDAO companyConfigDAO,CompanyDao companyDao) throws Exception {

		UAPISearchFlightKeyMap uapiSearchFlightKeyMap = new UAPISearchFlightKeyMap();
		Map<String, FareFlightSegment> fareFlightSegmentMap = new HashMap<String, FareFlightSegment>();
		LinkedHashMap<String, FlightSegments> flightSegmentstMap = new LinkedHashMap<String, FlightSegments>();
		Map<String, FareRules> fareRulesMap = new HashMap<String, FareRules>();

		CompanyConfig companyConfig = null;
		try {
			companyConfig = appKeyVo.getCompanyConfig();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		Company company = null;
		try{
			company = appKeyVo.getCompany();
		}catch(Exception e){
			e.printStackTrace();
		}
		Company parentCompany = null;
		try{
			parentCompany = companyDao.getParentCompany(company);
		}catch(Exception e){
			e.printStackTrace();
		}

		SearchFlightResponse searchFlightResponse = new SearchFlightResponse();
		BigDecimal apiToBaseExchangeRate=new BigDecimal(currencyConversionMap.getCurrencyValue1());
		List<FareFlightSegment> fareFlightSegments = new ArrayList<FareFlightSegment>();
		String flightindexForfarerules = (new UID()).toString();
		String returnflightindexForfarerules = (new UID()).toString();
		String flightindexForSegments = "";
		FareFlightSegment fareFlightSegment = null;
		List<FlightSegmentsGroup> flightSegmentsGroups =  null;
		String FlightSegmentsGroupId = new UID().toString();
		List<FareRules> fareRulesList = null;
		FlightSegmentsGroup flightSegmentsGroup = null;
		FlightSegmentsGroup flightSegmentsGroupReturn = null;
		List<FlightSegments> FlightSegmentsList = null;
		FareRules farerules =null;
		List<Segments> segmentsList = null;
		List<Segments> segmentsListreturn = null;
		List<FareRule> FareRuleList = null;
		FlightSegments flightSegments = null;
		String srid = "";
		Response flightresponse = new Response();
		if(tboFlightSearchResponse.getResponse()!=null && tboFlightSearchResponse.getResponse().getResults()!=null && tboFlightSearchResponse.getResponse().getResults().size()>0)
		{
			flightresponse.setResults(tboFlightSearchResponse.getResponse().getResults());			

			if(flightresponse.getResults()!=null && flightresponse.getResults().size()>0)
			{
				for(List<Result> resultData : flightresponse.getResults())
				{
					if(resultData!=null && resultData.size()>0)
					{
						for(Result result : resultData)
						{						
							fareFlightSegment = new FareFlightSegment();
							fareRulesList = new ArrayList<FareRules>();
							flightSegmentsGroups = new ArrayList<FlightSegmentsGroup>();
							fareFlightSegment.setBookingCurrency(flightsearch.getCurrency());
							fareFlightSegment.setExchangeRate(currencyConversionMap.getCurValue());
							fareFlightSegment.setId(new UID().toString());
							fareFlightSegment.setApiCurrency(currencyConversionMap.getApiCurrency());
							fareFlightSegment.setBaseCurrency(flightsearch.getBaseCurrency());
							fareFlightSegment.setApiToBaseExchangeRate(apiToBaseExchangeRate);
							fareFlightSegment.setCurrency(currencyConversionMap.getApiCurrency());

							// we are taking offered price as totalprice, but we add base price and taxes we ll get published price.
							//so that we are diving base price with CommissionEarned,PLBEarned,IncentiveEarned.
							//Their providing offered price based on CommissionEarned,PLBEarned,IncentiveEarned.
							// so we subtract base price using above 3 fields.

							BigDecimal removalbaseprice = result.getFare().getCommissionEarned().add(result.getFare().getIncentiveEarned().add(result.getFare().getPLBEarned()));
							BigDecimal tdsprice = result.getFare().getTdsOnCommission().add(result.getFare().getTdsOnIncentive().add(result.getFare().getTdsOnPLB()));

							fareFlightSegment.setSupplierCommissionearned(removalbaseprice);
							fareFlightSegment.setSupplierTds(tdsprice);

							fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
									String.valueOf(result.getFare().getBaseFare()), currencyConversionMap.getApiCurrency())).subtract(removalbaseprice).multiply(apiToBaseExchangeRate)));

							fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(AmountRoundingModeUtil.roundingMode(new BigDecimal(replaceCurrency(
									String.valueOf(result.getFare().getOfferedFare()), currencyConversionMap.getApiCurrency())).multiply(apiToBaseExchangeRate))));

							if(result.getFare().getTransactionFee()!=null){
								fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
										String.valueOf(result.getFare().getTax().add(result.getFare().getOtherCharges()).add(result.getFare().getAdditionalTxnFeeOfrd()).add(result.getFare().getAdditionalTxnFeePub()).add(result.getFare().getTransactionFee()).add(tdsprice)), currencyConversionMap.getApiCurrency())).multiply(apiToBaseExchangeRate)));
							}
							else{
								fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
										String.valueOf(result.getFare().getTax().add(result.getFare().getOtherCharges()).add(result.getFare().getAdditionalTxnFeeOfrd()).add(result.getFare().getAdditionalTxnFeePub()).add(tdsprice)), currencyConversionMap.getApiCurrency())).multiply(apiToBaseExchangeRate)));
							}

							fareFlightSegment.setApi_basePriceWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
									String.valueOf(result.getFare().getBaseFare()), currencyConversionMap.getApiCurrency())).subtract(result.getFare().getCommissionEarned().add(result.getFare().getPLBEarned().add(result.getFare().getIncentiveEarned()))).multiply(apiToBaseExchangeRate)));

							fareFlightSegment.setApi_totalPriceWithoutMarkup(replaceCurrency(
									String.valueOf(result.getFare().getOfferedFare()), currencyConversionMap.getApiCurrency()));
							if(result.getFare().getTransactionFee()!=null){
								fareFlightSegment.setApi_taxesWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
										String.valueOf(result.getFare().getTax().add(result.getFare().getOtherCharges()).add(result.getFare().getAdditionalTxnFeeOfrd()).add(result.getFare().getAdditionalTxnFeePub()).add(result.getFare().getTransactionFee())), currencyConversionMap.getApiCurrency())).multiply(apiToBaseExchangeRate)));
							}
							else{
								fareFlightSegment.setApi_taxesWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
										String.valueOf(result.getFare().getTax().add(result.getFare().getOtherCharges()).add(result.getFare().getAdditionalTxnFeeOfrd()).add(result.getFare().getAdditionalTxnFeePub())), currencyConversionMap.getApiCurrency())).multiply(apiToBaseExchangeRate)));
							}
							Discount apidiscount = new Discount();
							apidiscount.setAmount(new BigDecimal(replaceCurrency(
									String.valueOf(result.getFare().getDiscount()), currencyConversionMap.getApiCurrency())).multiply(apiToBaseExchangeRate));
							fareFlightSegment.setApiDiscount(apidiscount);

							Discount systemdiscount = new Discount();
							systemdiscount.setAmount(new BigDecimal(0.0));
							fareFlightSegment.setSystemDiscount(systemdiscount);

							Discount discount = new Discount();
							discount.setAmount(new BigDecimal(replaceCurrency(
									String.valueOf(result.getFare().getDiscount()), currencyConversionMap.getApiCurrency())).multiply(apiToBaseExchangeRate).add(new BigDecimal(0.0)));
							fareFlightSegment.setDiscount(discount);

							fareFlightSegment.setLCC(result.getIsLCC());
							fareFlightSegment.setRefundable(result.getIsRefundable());

							if(result.getLastTicketDate()!=null){
								if(StringUtils.isNotEmpty(result.getLastTicketDate()) ){
									String lastTicketingDate= result.getLastTicketDate();
									parseLastTicketDate(lastTicketingDate,fareFlightSegment);
								}
							}
							String airlineCode = "ALL";

							// Add MarkUp
							UmarkUpServiceCall.getMarkupValues(flightsearch,
									markupMap,result.getAirlineCode(),fareFlightSegment,"ALL");

							fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getBasePriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
							fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(AmountRoundingModeUtil.roundingMode(new BigDecimal(fareFlightSegment.getTotalPriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate()))));
							fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getTaxesWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
							fareFlightSegment.setBasePrice(String.valueOf(new BigDecimal(fareFlightSegment.getBasePrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
							fareFlightSegment.setTaxes(String.valueOf(new BigDecimal(fareFlightSegment.getTaxes()).multiply(flightsearch.getBaseToBookingExchangeRate())));

							// Add Service In total Price
							FlightServiceTax flightServiceTax = null;
							FlightGstTax flightGstTax = null;
							if(companyConfig != null){
								if(companyConfig.getCompanyConfigType().isB2E()){
									if(companyConfig.getTaxtype()!=null && companyConfig.getTaxtype().equalsIgnoreCase("GST")){													
										fareFlightSegment.setTotalPrice(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
										flightGstTax = new FlightGstTax();
										flightGstTax = getFlightGstTax(flightsearch, companyConfig, company,parentCompany);
										BigDecimal totalPrice = new BigDecimal(fareFlightSegment.getTotalPrice());
										BigDecimal totalPriceAfterServiceTax = totalPrice.add(flightGstTax.getTotalTax()).add(flightGstTax.getManagementFee());
										fareFlightSegment.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(totalPriceAfterServiceTax)));
									}else{
										fareFlightSegment.setTotalPrice(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
										flightServiceTax = new FlightServiceTax();
										flightServiceTax = getFlightServiceTax(fareFlightSegment.getBasePrice(),result.getFare().getYQTax(),flightsearch,companyConfig);
										BigDecimal totalPrice = new BigDecimal(fareFlightSegment.getTotalPrice());
										BigDecimal totalPriceAfterServiceTax = totalPrice.add(flightServiceTax.getTotalServiceTax()).add(flightServiceTax.getManagementFee());
										fareFlightSegment.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(totalPriceAfterServiceTax)));
									}
								}else{
									fareFlightSegment.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate()))));
								}
							}
							else{
								fareFlightSegment.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate()))));
							}
							if(result.getSegments()!=null && result.getSegments().size()>0)
							{
								for(int i = 0; i < result.getSegments().size(); i++)
								{
									List<FlightSearchrresponseSegment> Segments = result.getSegments().get(i);
									if(i == 0){
										// Onward flight segements
										flightSegmentsGroup = new FlightSegmentsGroup();
										flightSegments = new FlightSegments();
										FlightSegmentsList = new ArrayList<FlightSegments>();
										flightindexForSegments = "TBOO"+ (new UID()).toString();
										flightindexForfarerules = flightindexForSegments;
										FlightSegmentsGroupId = flightindexForSegments;
										farerules = new FareRules();
										segmentsList = new ArrayList<Segments>();
										FareRuleList = new ArrayList<FareRule>();
										flightSegments.setFlightIndex(flightindexForSegments);
										flightSegments.setApiProvider("TBO");
										flightSegments.setBookingCurrency(flightsearch.getCurrency());
										flightSegments.setExchangeRate(currencyConversionMap.getCurValue());
										for(FlightSearchrresponseSegment segment : Segments)
										{
											Segments segments = new Segments();
											buildFlightSegments(segment,segments,flightsearch);
											segments.setFareInfoRef(result.getResultIndex());
											if(segment.getDuration() != null)
												segments.setDuration(String.valueOf(segment.getDuration()));
											segmentsList.add(segments);
										}
										flightSegments.setSegments(segmentsList);
										FlightSegmentsList.add(flightSegments);
										flightSegmentstMap.put(flightindexForSegments,flightSegments);
										fareFlightSegmentMap.put(FlightSegmentsGroupId,fareFlightSegment);
										flightSegmentsGroup.setFlightSegments(FlightSegmentsList);
										flightSegmentsGroups.add(flightSegmentsGroup);
									}
									if(i == result.getSegments().size() - 1){

										flightSegmentsGroup = new FlightSegmentsGroup();
										flightSegments = new FlightSegments();
										FlightSegmentsList = new ArrayList<FlightSegments>();
										flightindexForSegments = "TBOR"+ (new UID()).toString();
										returnflightindexForfarerules = flightindexForSegments;
										FlightSegmentsGroupId = flightindexForSegments;
										farerules = new FareRules();
										segmentsList = new ArrayList<Segments>();
										FareRuleList = new ArrayList<FareRule>();
										flightSegments.setFlightIndex(flightindexForSegments);
										flightSegments.setApiProvider("TBO");
										flightSegments.setBookingCurrency(flightsearch.getCurrency());
										flightSegments.setExchangeRate(currencyConversionMap.getCurValue());
										for(FlightSearchrresponseSegment segment : Segments)
										{
											Segments segments = new Segments();
											// Build Segments
											buildFlightSegments(segment,segments,flightsearch);

											segments.setFareInfoRef(result.getResultIndex());
											if(segment.getDuration() != null)
												segments.setDuration(String.valueOf(segment.getDuration()));
											segmentsList.add(segments);
										}
										flightSegments.setSegments(segmentsList);
										FlightSegmentsList.add(flightSegments);
										flightSegmentstMap.put(flightindexForSegments,flightSegments);
										fareFlightSegmentMap.put(FlightSegmentsGroupId,fareFlightSegment);
										flightSegmentsGroup.setFlightSegments(FlightSegmentsList);
										flightSegmentsGroups.add(flightSegmentsGroup);
									}
								}
							}
							Set<String> fareBasisCodeSet = new HashSet<String>();
							FareRuleList = new ArrayList<FareRule>();
							List<FareRule> returnFareRuleList = new ArrayList<FareRule>();
							farerules = new FareRules();
							FareRules returnfarerules = new FareRules();
							if( result.getFareRules()!=null &&  result.getFareRules().size()>0)
							{
								for(com.tayyarah.api.flight.tbo.model.FareRule fareruleobj : result.getFareRules())
								{
									FareRule farerule = new FareRule();
									fareBasisCodeSet.add(fareruleobj.getFareBasisCode());
									farerule.setAirlineCode(fareruleobj.getAirline());
									farerule.setArrCode(fareruleobj.getDestination());
									farerule.setBasisCode(fareruleobj.getFareBasisCode());
									farerule.setDepCode(fareruleobj.getOrigin());
									farerule.setFareProviderCode(tboFlightSearchResponse.getResponse().getTraceId());
									farerule.setFareInfoRef(result.getResultIndex());
									farerule.setFareValue("TB-"+result.getResultIndex());
									if (result.getBaggageAllowance() != null)
									{
										String[] splitedbaggage = result.getBaggageAllowance().split("\\s+");
										farerule.setBagAllowanceValue(new BigInteger(splitedbaggage[0]));
									}
									if(fareruleobj.getOrigin().equalsIgnoreCase(flightsearch.getOrigin())  || fareruleobj.getDestination().equalsIgnoreCase(flightsearch.getDestination()) )
										FareRuleList.add(farerule);
									if(fareruleobj.getOrigin().equalsIgnoreCase(flightsearch.getDestination())  || fareruleobj.getDestination().equalsIgnoreCase(flightsearch.getOrigin()) )
										returnFareRuleList.add(farerule);

									farerules.setTravelerType(flightsearch.getTripType());
									farerules.setFareRule(FareRuleList);
									returnfarerules.setTravelerType(flightsearch.getTripType());
									returnfarerules.setFareRule(returnFareRuleList);
								}
								fareRulesList.add(farerules);
								fareRulesList.add(returnfarerules);
								fareFlightSegment.setFareRules(fareRulesList);
							}
							fareRulesMap.put(flightindexForfarerules, farerules);
							fareRulesMap.put(returnflightindexForfarerules, returnfarerules);

							String fareBasisCode="ALL";
							if(fareBasisCodeSet.size()==1){
								fareBasisCode=fareBasisCodeSet.iterator().next();
							}
							fareFlightSegment.setFlightSegmentsGroups(flightSegmentsGroups);
							fareFlightSegments.add(fareFlightSegment);
							searchFlightResponse.setFareFlightSegment(fareFlightSegments);
							uapiSearchFlightKeyMap.setFareRulesMap(fareRulesMap);
							uapiSearchFlightKeyMap.setFareFlightSegmentMap(fareFlightSegmentMap);
							uapiSearchFlightKeyMap.setFlightSegmentstMap(flightSegmentstMap);
							uapiSearchFlightKeyMap.setFlightsearch(flightsearch);
							uapiSearchFlightKeyMap.setFlightMarkUpConfiglistMap(markupMap);
							uapiSearchFlightKeyMap.setExchangeRate(currencyConversionMap.getCurValue());
							searchFlightResponse.setUapiSearchFlightKeyMap(uapiSearchFlightKeyMap);
						}
					}
				}
			}
		}
		return searchFlightResponse;
	}

	// FareRules Parse Method
	public static FareRuleResponse parseFareRules(TboFareRuleResponse Tbofareruleresponse){
		FareRuleResponse FRR = new FareRuleResponse();
		if(Tbofareruleresponse.getResponse()!=null && Tbofareruleresponse.getResponse().getFareRules()!=null && Tbofareruleresponse.getResponse().getFareRules().size()>0)
		{
			List<NewFareRule> fareruleList=new ArrayList<NewFareRule>();
			for (com.tayyarah.api.flight.tbo.model.FareRule FareRules : Tbofareruleresponse.getResponse().getFareRules()) {
				FRR = new FareRuleResponse();
				if(FareRules.getFareRuleDetail() != null && FareRules.getFareRuleDetail().length() > 0) {
					NewFareRule NFR = new NewFareRule();
					NFR.setValue(FareRules.getFareRuleDetail());
					fareruleList.add(NFR);
				}				
				FRR.setProviderCode(FareRules.getFareBasisCode());
			}
			FRR.setFareruleList(fareruleList);
		}
		return FRR;
	}

	// Airprice Response Parse Method
	public static FlightPriceResponse parseAirPrice(TboFlightAirpriceResponse Tboairpriceresponse,Map<String,List<FlightMarkUpConfig>> FlightMarkUpConfiglistMap,
			Map<String, String> AirlineNameMap,
			ArrayList<Map<String, String>> MapList, Flightsearch flightsearch,
			String transaction_key, FlightTempAirSegmentDAO tempDAO,BigDecimal exchangeRate,FareFlightSegment searchfareFlightSegment,MarkupCommissionDetails markupCommissionDetails,CompanyConfigDAO companyConfigDAO,AppKeyVo appKeyVo,CompanyDao companyDao){

		FlightPriceResponse flightPriceResponse = null;

		CompanyConfig companyConfig = null;
		try {
			companyConfig = appKeyVo.getCompanyConfig();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		Company company = null;
		try{
			company = appKeyVo.getCompany();
		}catch(Exception e){
			e.printStackTrace();
		}
		Company parentCompany = null;
		try{
			parentCompany = companyDao.getParentCompany(company);
		}catch(Exception e){
			e.printStackTrace();
		}

		Result result = Tboairpriceresponse.getResponse().getResults();
		if(result != null){
			flightPriceResponse = new FlightPriceResponse();
			try {
				logger.debug("--------- Start---------");
				int passegers_for_marhup = flightsearch.getAdult()
						+ flightsearch.getKid()+flightsearch.getInfant();
				List<FlightSegmentsGroup> flightSegmentsGroups = new ArrayList<FlightSegmentsGroup>();
				FlightSegmentsGroup flightSegmentsGroup = new FlightSegmentsGroup();
				List<FlightSegments> flightsegmentslist = new ArrayList<FlightSegments>();
				List<FareRules> fareRulesList = new ArrayList<FareRules>();

				flightPriceResponse.setTransactionKey(transaction_key);
				flightPriceResponse.setFlightsearch(flightsearch);
				flightPriceResponse.setCountry("YET to get");
				FareFlightSegment fareFlightSegment = new FareFlightSegment();		
				fareFlightSegment.setId(new UID().toString());
				fareFlightSegment.setCurrency(searchfareFlightSegment.getCurrency());
				fareFlightSegment.setApiCurrency(searchfareFlightSegment.getCurrency());
				fareFlightSegment.setBaseCurrency(flightsearch.getBaseCurrency());
				fareFlightSegment.setApiToBaseExchangeRate(searchfareFlightSegment.getApiToBaseExchangeRate());

				////////////////////////
				// we are taking offered price as totalprice, but we add base price and taxes we ll get published price.
				//so that we are diving base price with CommissionEarned,PLBEarned,IncentiveEarned.
				//Their providing offered price based on CommissionEarned,PLBEarned,IncentiveEarned.
				// so we subtract base price using above 3 fields.

				/*BigDecimal removalfarefrombasefare = new BigDecimal("0.0");

						 if(result.getFare().getAdditionalTxnFee()!=null)
						      removalfarefrombasefare = result.getFare().getCommissionEarned().add(result.getFare().getPLBEarned().add(result.getFare().getIncentiveEarned().add(result.getFare().getAdditionalTxnFee())));
						 else
							  removalfarefrombasefare = result.getFare().getCommissionEarned().add(result.getFare().getPLBEarned().add(result.getFare().getIncentiveEarned()));

						 BigDecimal basefareapiprice = result.getFare().getBaseFare();
						BigDecimal basefare = basefareapiprice.subtract(removalfarefrombasefare);
						BigDecimal addtdstobasefare = result.getFare().getTdsOnCommission().add(result.getFare().getTdsOnIncentive().add(result.getFare().getTdsOnPLB()));
						BigDecimal finalbasefare = basefare.add(addtdstobasefare);

						fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(finalbasefare.multiply(searchfareFlightSegment.getApiToBaseExchangeRate())));
				 */

				BigDecimal removalbaseprice = result.getFare().getCommissionEarned().add(result.getFare().getIncentiveEarned().add(result.getFare().getPLBEarned()));
				BigDecimal tdsprice = result.getFare().getTdsOnCommission().add(result.getFare().getTdsOnIncentive().add(result.getFare().getTdsOnPLB()));

				fareFlightSegment.setSupplierCommissionearned(removalbaseprice);
				fareFlightSegment.setSupplierTds(tdsprice);

				fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(result.getFare().getBaseFare().subtract(removalbaseprice).multiply(searchfareFlightSegment.getApiToBaseExchangeRate())));
				fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(AmountRoundingModeUtil.roundingMode(result.getFare().getOfferedFare()).multiply(searchfareFlightSegment.getApiToBaseExchangeRate())));

				if(result.getFare().getTransactionFee()!=null){
					fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(result.getFare().getTax().add(result.getFare().getAdditionalTxnFeeOfrd()).add(result.getFare().getAdditionalTxnFeePub()).add(result.getFare().getTransactionFee()).add(result.getFare().getOtherCharges()).add(tdsprice).multiply(searchfareFlightSegment.getApiToBaseExchangeRate())));
				}
				else{
					fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(result.getFare().getTax().add(result.getFare().getAdditionalTxnFeeOfrd().add(result.getFare().getAdditionalTxnFeePub().add(result.getFare().getOtherCharges())).add(tdsprice)).multiply(searchfareFlightSegment.getApiToBaseExchangeRate())));
				}
				fareFlightSegment.setApi_basePriceWithoutMarkup(String.valueOf(result.getFare().getBaseFare().subtract(removalbaseprice).multiply(searchfareFlightSegment.getApiToBaseExchangeRate())));
				fareFlightSegment.setApi_totalPriceWithoutMarkup(String.valueOf(result.getFare().getOfferedFare().multiply(searchfareFlightSegment.getApiToBaseExchangeRate())));
				if(result.getFare().getTransactionFee()!=null){
					fareFlightSegment.setApi_taxesWithoutMarkup(String.valueOf(result.getFare().getTax().add(result.getFare().getAdditionalTxnFeeOfrd().add(result.getFare().getAdditionalTxnFeePub().add(result.getFare().getOtherCharges()).add(result.getFare().getTransactionFee()))).multiply(searchfareFlightSegment.getApiToBaseExchangeRate())));
				}
				else{
					fareFlightSegment.setApi_taxesWithoutMarkup(String.valueOf(result.getFare().getTax().add(result.getFare().getAdditionalTxnFeeOfrd().add(result.getFare().getAdditionalTxnFeePub().add(result.getFare().getOtherCharges()))).multiply(searchfareFlightSegment.getApiToBaseExchangeRate())));
				}
				Discount apidiscount = new Discount();
				apidiscount.setAmount(new BigDecimal(replaceCurrency(
						String.valueOf(result.getFare().getDiscount()), searchfareFlightSegment.getCurrency())).multiply(searchfareFlightSegment.getApiToBaseExchangeRate()));
				fareFlightSegment.setApiDiscount(apidiscount);

				Discount systemdiscount = new Discount();
				systemdiscount.setAmount(new BigDecimal(0.0));
				fareFlightSegment.setSystemDiscount(systemdiscount);

				Discount discount = new Discount();
				discount.setAmount(new BigDecimal(replaceCurrency(
						String.valueOf(result.getFare().getDiscount()), searchfareFlightSegment.getCurrency())).multiply(searchfareFlightSegment.getApiToBaseExchangeRate()).add(new BigDecimal(0.0)));
				fareFlightSegment.setDiscount(discount);
				fareFlightSegment.setLCC(result.getIsLCC());
				fareFlightSegment.setRefundable(result.getIsRefundable());
				if(result.getLastTicketDate() != null && result.getLastTicketDate() != ""){
					if(StringUtils.isNotEmpty(result.getLastTicketDate()) ){
						String lastTicketingDate = result.getLastTicketDate();
						parseLastTicketDate(lastTicketingDate,fareFlightSegment);
					}
				}

				UmarkUpServiceCall.getMarkupValuesFPR(flightPriceResponse,
						FlightMarkUpConfiglistMap,searchfareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getCarrier().getCode(), fareFlightSegment,"ALL",companyConfig);

				fareFlightSegment.setExchangeRate(searchfareFlightSegment.getExchangeRate());
				fareFlightSegment.setBookingCurrency(searchfareFlightSegment.getBookingCurrency());
				List<PassengerFareBreakUp> passengerFareBreakUps = new ArrayList<PassengerFareBreakUp>();

				AddPassengerFareBreakUp(FlightMarkUpConfiglistMap, passengerFareBreakUps,
						flightPriceResponse,searchfareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getCarrier().getCode(), passegers_for_marhup,result.getFareBreakdown(),flightsearch, searchfareFlightSegment.getApiToBaseExchangeRate(),searchfareFlightSegment.getCurrency(),Tboairpriceresponse);

				flightPriceResponse.setPassengerFareBreakUps(passengerFareBreakUps);
				List<Segments> segmentsList = new ArrayList<Segments>();
				String airlineCode = "ALL";
				FlightSegments flightSegments = new FlightSegments();
				flightSegments.setFlightIndex(new UID().toString());
				flightSegments.setApiProvider("TBO");
				flightSegments.setBookingCurrency(flightsearch.getCurrency());
				flightSegments.setExchangeRate(exchangeRate);
				if(result.getSegments()!=null && result.getSegments().size() > 1 || flightsearch.isSpecialSearch()){
					List<FlightSegmentsGroup> newFlightSegmentsGroups = new ArrayList<FlightSegmentsGroup>();
					for(int i = 0; i < result.getSegments().size(); i++)
					{
						List<FlightSearchrresponseSegment> Segments = result.getSegments().get(i);
						if(i == 0){
							System.out.println("new group");
							FlightSegmentsGroup newgroupflightSegmentsGroup = new FlightSegmentsGroup();
							List<FlightSegments> newgroupflightSegmentsList = new ArrayList<FlightSegments>();
							List<Segments> newgroupsegmentsList = new ArrayList<Segments>();;
							FlightSegments newgroupflightSegments = new FlightSegments();

							newgroupflightSegments.setFlightIndex(flightSegments.getFlightIndex());
							newgroupflightSegments.setApiProvider("TBO");
							newgroupflightSegments.setBookingCurrency(flightSegments.getBookingCurrency());
							newgroupflightSegments.setExchangeRate(exchangeRate);

							for(FlightSearchrresponseSegment segment : Segments)
							{
								Segments segments = new Segments();

								buildFlightSegments(segment,segments,flightsearch);

								segments.setFareInfoRef(result.getResultIndex());
								if(segment.getDuration() != null)
									segments.setDuration(String.valueOf(segment.getDuration()));
								newgroupsegmentsList.add(segments);
							}
							newgroupflightSegments.setSegments(newgroupsegmentsList);
							newgroupflightSegmentsList.add(newgroupflightSegments);
							newgroupflightSegmentsGroup.setFlightSegments(newgroupflightSegmentsList);
							newFlightSegmentsGroups.add(newgroupflightSegmentsGroup);
						}
						if(i == 1){
							FlightSegmentsGroup newgroupflightSegmentsGroup = new FlightSegmentsGroup();
							List<FlightSegments> newgroupflightSegmentsList = new ArrayList<FlightSegments>();
							List<Segments> newgroupsegmentsList = new ArrayList<Segments>();;
							FlightSegments newgroupflightSegments = new FlightSegments();
							newgroupflightSegments.setFlightIndex(flightSegments.getFlightIndex());
							newgroupflightSegments.setApiProvider("TBO");
							newgroupflightSegments.setBookingCurrency(flightSegments.getBookingCurrency());
							newgroupflightSegments.setExchangeRate(exchangeRate);
							for(FlightSearchrresponseSegment segment : Segments)
							{
								Segments segments = new Segments();

								buildFlightSegments(segment,segments,flightsearch);

								segments.setFareInfoRef(result.getResultIndex());
								if(segment.getDuration() != null)
									segments.setDuration(String.valueOf(segment.getDuration()));

								newgroupsegmentsList.add(segments);
							}
							newgroupflightSegments.setSegments(newgroupsegmentsList);
							newgroupflightSegmentsList.add(newgroupflightSegments);
							newgroupflightSegmentsGroup.setFlightSegments(newgroupflightSegmentsList);
							newFlightSegmentsGroups.add(newgroupflightSegmentsGroup);
						}
					}
					fareFlightSegment.setNewFlightSegmentsGroups(newFlightSegmentsGroups);
				}

				if(result.getSegments()!=null && result.getSegments().size()>0)
				{
					for(List<FlightSearchrresponseSegment> Segments : result.getSegments())
					{
						for(FlightSearchrresponseSegment segment : Segments)
						{
							Segments segments = new Segments();

							buildFlightSegments(segment,segments,flightsearch);

							segments.setFareInfoRef(result.getResultIndex());

							if(segment.getDuration() != null)
								segments.setDuration(String.valueOf(segment.getDuration()));
							segmentsList.add(segments);
						}
						flightSegments.setSegments(segmentsList);
					}
				}
				flightsegmentslist.add(flightSegments);

				Set<String> fareBasisCodeSet = new HashSet<String>();
				List<FareRule> FareRuleList = new ArrayList<FareRule>();
				FareRules farerules = new FareRules();

				if(result.getFareRules()!=null && result.getFareRules().size()>0)
				{
					for(com.tayyarah.api.flight.tbo.model.FareRule fareruleobj : result.getFareRules())
					{
						FareRule farerule = new FareRule();
						fareBasisCodeSet.add(fareruleobj.getFareBasisCode());
						farerule.setAirlineCode(fareruleobj.getAirline());
						farerule.setArrCode(fareruleobj.getDestination());
						farerule.setBasisCode(fareruleobj.getFareBasisCode());
						farerule.setDepCode(fareruleobj.getOrigin());
						farerule.setFareProviderCode(Tboairpriceresponse.getResponse().getTraceId());
						farerule.setFareInfoRef(result.getResultIndex());
						if (StringUtils.isNotEmpty(result.getBaggageAllowance()))
						{
							String[] splitedbaggage = result.getBaggageAllowance().split("\\s+");
							farerule.setBagAllowanceValue(new BigInteger(splitedbaggage[0]));
						}
						FareRuleList.add(farerule);
						farerules.setTravelerType(flightsearch.getTripType());
						farerules.setFareRule(FareRuleList);
					}
					fareRulesList.add(farerules);
					fareFlightSegment.setFareRules(fareRulesList);
				}

				String fareBasisCode="ALL";
				if(fareBasisCodeSet.size()==1){
					fareBasisCode=fareBasisCodeSet.iterator().next();
				}
				fareFlightSegment.setFareBasisCode(fareBasisCode);				
				flightSegmentsGroup.setFlightSegments(flightsegmentslist);
				flightSegmentsGroups.add(flightSegmentsGroup);
				fareFlightSegment.setFareRules(fareRulesList);
				fareFlightSegment.setFlightSegmentsGroups(flightSegmentsGroups);
				fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getBasePriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
				fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(AmountRoundingModeUtil.roundingMode(new BigDecimal(fareFlightSegment.getTotalPriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate()))));
				fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getTaxesWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
				fareFlightSegment.setBasePrice(String.valueOf(new BigDecimal(fareFlightSegment.getBasePrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
				fareFlightSegment.setTaxes(String.valueOf(new BigDecimal(fareFlightSegment.getTaxes()).multiply(flightsearch.getBaseToBookingExchangeRate())));

				FlightServiceTax flightServiceTax = null;
				FlightGstTax flightGstTax = null;
				if(companyConfig != null){
					if(companyConfig.getCompanyConfigType().isB2E()){
						if(companyConfig.getTaxtype()!=null && companyConfig.getTaxtype().equalsIgnoreCase("GST")){													
							fareFlightSegment.setTotalPrice(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
							flightGstTax = new FlightGstTax();
							flightGstTax = getFlightGstTax(flightsearch, companyConfig, company,parentCompany);
							BigDecimal totalPrice = new BigDecimal(fareFlightSegment.getTotalPrice());
							BigDecimal totalPriceAfterServiceTax = totalPrice.add(flightGstTax.getTotalTax()).add(flightGstTax.getManagementFee());
							fareFlightSegment.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(totalPriceAfterServiceTax)));
							fareFlightSegment.setPayableAmount(AmountRoundingModeUtil.roundingMode(totalPriceAfterServiceTax));
						}else{
							fareFlightSegment.setTotalPrice(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
							flightServiceTax = new FlightServiceTax();
							flightServiceTax = getFlightServiceTax(fareFlightSegment.getBasePrice(),result.getFare().getYQTax(),flightsearch,companyConfig);
							BigDecimal totalPrice = new BigDecimal(fareFlightSegment.getTotalPrice());
							BigDecimal totalPriceAfterServiceTax = totalPrice.add(flightServiceTax.getTotalServiceTax()).add(flightServiceTax.getManagementFee());
							fareFlightSegment.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(totalPriceAfterServiceTax)));
							fareFlightSegment.setPayableAmount(AmountRoundingModeUtil.roundingMode(totalPriceAfterServiceTax));
						}
					}else{
						fareFlightSegment.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate()))));
					}
				}else{
					fareFlightSegment.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate()))));
				}
				fareFlightSegment.setFlightServiceTax(flightServiceTax);
				fareFlightSegment.setFlightGstTax(flightGstTax);

				flightPriceResponse.setFareFlightSegment(fareFlightSegment);
				flightPriceResponse.setFlightMarkUpConfiglistMap(FlightMarkUpConfiglistMap);
				flightPriceResponse.setMarkupCommissionDetails(markupCommissionDetails);
				flightPriceResponse.setFinalPriceWithGST(AmountRoundingModeUtil.roundingMode(new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice())));
				flightPriceResponse.setGSTonMarkup(new BigDecimal("0.00"));
				flightPriceResponse.setGSTonFlights(new BigDecimal("0.00"));

			}catch(Exception e){				
				logger.debug("--------- AIRPRICE Exception---------" +e);
				throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIRPRICE);
			}
		}
		return flightPriceResponse;
	}

	// Airprice Response Parse Method for Special Return LCC (SR)
	public static FlightPriceResponse parseSpecialReturnAirPrice(TboFlightAirpriceResponse Tboairpriceresponse,Map<String,List<FlightMarkUpConfig>> FlightMarkUpConfiglistMap,
			Map<String, String> AirlineNameMap,
			ArrayList<Map<String, String>> MapList, Flightsearch flightsearch,
			String transaction_key, FlightTempAirSegmentDAO tempDAO,BigDecimal exchangeRate,FareFlightSegment searchfareFlightSegment,MarkupCommissionDetails markupCommissionDetails,CompanyConfigDAO companyConfigDAO,AppKeyVo appKeyVo,CompanyDao companyDao){

		FlightPriceResponse flightPriceResponse = null;
		if(Tboairpriceresponse.getResponse()!=null && Tboairpriceresponse.getResponse().getResponseStatus() == 1 && Tboairpriceresponse.getResponse().getResults()!=null){

			Result result = Tboairpriceresponse.getResponse().getResults();
			flightPriceResponse = new FlightPriceResponse();
			try {
				CompanyConfig companyConfig = null;
				try {
					companyConfig =appKeyVo.getCompanyConfig();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				Company company = null;
				try{
					company = appKeyVo.getCompany();
				}catch(Exception e){
					e.printStackTrace();
				}
				Company parentCompany = null;
				try{
					parentCompany = companyDao.getParentCompany(company);
				}catch(Exception e){
					e.printStackTrace();
				}

				logger.debug("--------- Start---------");
				int passegers_for_marhup = flightsearch.getAdult()
						+ flightsearch.getKid()+flightsearch.getInfant();
				List<FlightSegmentsGroup> flightSegmentsGroups = new ArrayList<FlightSegmentsGroup>();
				FlightSegmentsGroup flightSegmentsGroup = new FlightSegmentsGroup();
				List<FlightSegments> flightsegmentslist = new ArrayList<FlightSegments>();
				List<FareRules> fareRulesList = new ArrayList<FareRules>();
				flightPriceResponse.setTransactionKey(transaction_key);
				flightPriceResponse.setFlightsearch(flightsearch);
				flightPriceResponse.setCountry("YET to get");
				FareFlightSegment fareFlightSegment = new FareFlightSegment();

				fareFlightSegment.setId(new UID().toString());
				fareFlightSegment.setCurrency(searchfareFlightSegment.getCurrency());
				fareFlightSegment.setApiCurrency(searchfareFlightSegment.getCurrency());
				fareFlightSegment.setBaseCurrency(flightsearch.getBaseCurrency());
				fareFlightSegment.setApiToBaseExchangeRate(searchfareFlightSegment.getApiToBaseExchangeRate());

				////////////////////////
				// we are taking offered price as totalprice, but we add base price and taxes we ll get published price.
				//so that we are diving base price with CommissionEarned,PLBEarned,IncentiveEarned.
				//Their providing offered price based on CommissionEarned,PLBEarned,IncentiveEarned.
				// so we subtract base price using above 3 fields.

				/*BigDecimal removalfarefrombasefare = new BigDecimal("0.0");

							 if(result.getFare().getAdditionalTxnFee()!=null)
							      removalfarefrombasefare = result.getFare().getCommissionEarned().add(result.getFare().getPLBEarned().add(result.getFare().getIncentiveEarned().add(result.getFare().getAdditionalTxnFee())));
							 else
								  removalfarefrombasefare = result.getFare().getCommissionEarned().add(result.getFare().getPLBEarned().add(result.getFare().getIncentiveEarned()));

							 BigDecimal basefareapiprice = result.getFare().getBaseFare();
							BigDecimal basefare = basefareapiprice.subtract(removalfarefrombasefare);
							BigDecimal addtdstobasefare = result.getFare().getTdsOnCommission().add(result.getFare().getTdsOnIncentive().add(result.getFare().getTdsOnPLB()));
							BigDecimal finalbasefare = basefare.add(addtdstobasefare);

							fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(finalbasefare.multiply(searchfareFlightSegment.getApiToBaseExchangeRate())));
				 */

				BigDecimal removalbaseprice = result.getFare().getCommissionEarned().add(result.getFare().getIncentiveEarned().add(result.getFare().getPLBEarned()));
				BigDecimal tdsprice = result.getFare().getTdsOnCommission().add(result.getFare().getTdsOnIncentive().add(result.getFare().getTdsOnPLB()));

				fareFlightSegment.setSupplierCommissionearned(removalbaseprice);
				fareFlightSegment.setSupplierTds(tdsprice);

				fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(result.getFare().getBaseFare().subtract(removalbaseprice).multiply(searchfareFlightSegment.getApiToBaseExchangeRate())));
				fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(AmountRoundingModeUtil.roundingMode(result.getFare().getOfferedFare()).multiply(searchfareFlightSegment.getApiToBaseExchangeRate())));

				if(result.getFare().getTransactionFee()!=null){
					fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(result.getFare().getTax().add(result.getFare().getAdditionalTxnFeeOfrd()).add(result.getFare().getAdditionalTxnFeePub()).add(result.getFare().getTransactionFee()).add(result.getFare().getOtherCharges()).add(tdsprice).multiply(searchfareFlightSegment.getApiToBaseExchangeRate())));
				}
				else{
					fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(result.getFare().getTax().add(result.getFare().getAdditionalTxnFeeOfrd().add(result.getFare().getAdditionalTxnFeePub().add(result.getFare().getOtherCharges())).add(tdsprice)).multiply(searchfareFlightSegment.getApiToBaseExchangeRate())));
				}
				fareFlightSegment.setApi_basePriceWithoutMarkup(String.valueOf(result.getFare().getBaseFare().subtract(removalbaseprice).multiply(searchfareFlightSegment.getApiToBaseExchangeRate())));
				fareFlightSegment.setApi_totalPriceWithoutMarkup(String.valueOf(result.getFare().getOfferedFare().multiply(searchfareFlightSegment.getApiToBaseExchangeRate())));
				if(result.getFare().getTransactionFee()!=null){
					fareFlightSegment.setApi_taxesWithoutMarkup(String.valueOf(result.getFare().getTax().add(result.getFare().getAdditionalTxnFeeOfrd().add(result.getFare().getAdditionalTxnFeePub().add(result.getFare().getOtherCharges()).add(result.getFare().getTransactionFee()))).multiply(searchfareFlightSegment.getApiToBaseExchangeRate())));
				}
				else{
					fareFlightSegment.setApi_taxesWithoutMarkup(String.valueOf(result.getFare().getTax().add(result.getFare().getAdditionalTxnFeeOfrd().add(result.getFare().getAdditionalTxnFeePub().add(result.getFare().getOtherCharges()))).multiply(searchfareFlightSegment.getApiToBaseExchangeRate())));
				}
				Discount apidiscount = new Discount();
				apidiscount.setAmount(new BigDecimal(replaceCurrency(
						String.valueOf(result.getFare().getDiscount()), searchfareFlightSegment.getCurrency())).multiply(searchfareFlightSegment.getApiToBaseExchangeRate()));
				fareFlightSegment.setApiDiscount(apidiscount);

				Discount systemdiscount = new Discount();
				systemdiscount.setAmount(new BigDecimal(0.0));
				fareFlightSegment.setSystemDiscount(systemdiscount);

				Discount discount = new Discount();
				discount.setAmount(new BigDecimal(replaceCurrency(
						String.valueOf(result.getFare().getDiscount()), searchfareFlightSegment.getCurrency())).multiply(searchfareFlightSegment.getApiToBaseExchangeRate()).add(new BigDecimal(0.0)));
				fareFlightSegment.setDiscount(discount);
				fareFlightSegment.setLCC(result.getIsLCC());
				fareFlightSegment.setRefundable(result.getIsRefundable());
				if(result.getLastTicketDate() != null && result.getLastTicketDate() != ""){
					if(StringUtils.isNotEmpty(result.getLastTicketDate()) ){
						String lastTicketingDate = result.getLastTicketDate();
						parseLastTicketDate(lastTicketingDate,fareFlightSegment);
					}
				}
				UmarkUpServiceCall.getMarkupValuesSpecialReturnFPR(flightPriceResponse,
						FlightMarkUpConfiglistMap,searchfareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getCarrier().getCode(), fareFlightSegment,"ALL",companyConfig);

				fareFlightSegment.setExchangeRate(searchfareFlightSegment.getExchangeRate());
				fareFlightSegment.setBookingCurrency(searchfareFlightSegment.getBookingCurrency());
				List<PassengerFareBreakUp> passengerFareBreakUps = new ArrayList<PassengerFareBreakUp>();

				AddPassengerFareBreakUp(FlightMarkUpConfiglistMap, passengerFareBreakUps,
						flightPriceResponse,searchfareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getCarrier().getCode(), passegers_for_marhup,result.getFareBreakdown(),flightsearch, searchfareFlightSegment.getApiToBaseExchangeRate(),searchfareFlightSegment.getCurrency(),Tboairpriceresponse);

				flightPriceResponse.setPassengerFareBreakUps(passengerFareBreakUps);
				List<Segments> segmentsList = new ArrayList<Segments>();
				String airlineCode = "ALL";
				FlightSegments flightSegments = new FlightSegments();
				flightSegments.setFlightIndex(new UID().toString());
				flightSegments.setApiProvider("TBO");
				flightSegments.setBookingCurrency(flightsearch.getCurrency());
				flightSegments.setExchangeRate(exchangeRate);
				if(result.getSegments()!=null && result.getSegments().size() > 1 || flightsearch.isSpecialSearch()){

					List<FlightSegmentsGroup> newFlightSegmentsGroups = new ArrayList<FlightSegmentsGroup>();

					for(int i = 0; i < result.getSegments().size(); i++)
					{
						List<FlightSearchrresponseSegment> Segments = result.getSegments().get(i);
						if(i == 0){						
							FlightSegmentsGroup newgroupflightSegmentsGroup = new FlightSegmentsGroup();
							List<FlightSegments> newgroupflightSegmentsList = new ArrayList<FlightSegments>();
							List<Segments> newgroupsegmentsList = new ArrayList<Segments>();;
							FlightSegments newgroupflightSegments = new FlightSegments();
							newgroupflightSegments.setFlightIndex(flightSegments.getFlightIndex());
							newgroupflightSegments.setApiProvider("TBO");
							newgroupflightSegments.setBookingCurrency(flightSegments.getBookingCurrency());
							newgroupflightSegments.setExchangeRate(exchangeRate);
							for(FlightSearchrresponseSegment segment : Segments)
							{
								Segments segments = new Segments();

								buildFlightSegments(segment,segments,flightsearch);

								segments.setFareInfoRef(result.getResultIndex());

								if(segment.getDuration() != null)
									segments.setDuration(String.valueOf(segment.getDuration()));

								newgroupsegmentsList.add(segments);
							}
							newgroupflightSegments.setSegments(newgroupsegmentsList);
							newgroupflightSegmentsList.add(newgroupflightSegments);
							newgroupflightSegmentsGroup.setFlightSegments(newgroupflightSegmentsList);
							newFlightSegmentsGroups.add(newgroupflightSegmentsGroup);
						}
						if(i == 1){
							FlightSegmentsGroup newgroupflightSegmentsGroup = new FlightSegmentsGroup();
							List<FlightSegments> newgroupflightSegmentsList = new ArrayList<FlightSegments>();
							List<Segments> newgroupsegmentsList = new ArrayList<Segments>();;
							FlightSegments newgroupflightSegments = new FlightSegments();
							newgroupflightSegments.setFlightIndex(flightSegments.getFlightIndex());
							newgroupflightSegments.setApiProvider("TBO");
							newgroupflightSegments.setBookingCurrency(flightSegments.getBookingCurrency());
							newgroupflightSegments.setExchangeRate(exchangeRate);
							for(FlightSearchrresponseSegment segment : Segments)
							{
								Segments segments = new Segments();

								buildFlightSegments(segment,segments,flightsearch);

								segments.setFareInfoRef(result.getResultIndex());

								if(segment.getDuration() != null)
									segments.setDuration(String.valueOf(segment.getDuration()));

								newgroupsegmentsList.add(segments);
							}
							newgroupflightSegments.setSegments(newgroupsegmentsList);
							newgroupflightSegmentsList.add(newgroupflightSegments);
							newgroupflightSegmentsGroup.setFlightSegments(newgroupflightSegmentsList);
							newFlightSegmentsGroups.add(newgroupflightSegmentsGroup);
						}
					}
					fareFlightSegment.setNewFlightSegmentsGroups(newFlightSegmentsGroups);
				}

				if(result.getSegments()!=null && result.getSegments().size()>0)
				{
					for(List<FlightSearchrresponseSegment> Segments : result.getSegments())
					{
						for(FlightSearchrresponseSegment segment : Segments)
						{
							Segments segments = new Segments();

							buildFlightSegments(segment,segments,flightsearch);

							segments.setFareInfoRef(result.getResultIndex());

							if(segment.getDuration() != null)
								segments.setDuration(String.valueOf(segment.getDuration()));
							segmentsList.add(segments);
						}
						flightSegments.setSegments(segmentsList);
					}
				}
				flightsegmentslist.add(flightSegments);

				Set<String> fareBasisCodeSet = new HashSet<String>();
				List<FareRule> FareRuleList = new ArrayList<FareRule>();
				FareRules farerules = new FareRules();

				if(result.getFareRules()!=null && result.getFareRules().size()>0)
				{
					for(com.tayyarah.api.flight.tbo.model.FareRule fareruleobj : result.getFareRules())
					{
						FareRule farerule = new FareRule();
						fareBasisCodeSet.add(fareruleobj.getFareBasisCode());
						farerule.setAirlineCode(fareruleobj.getAirline());
						farerule.setArrCode(fareruleobj.getDestination());
						farerule.setBasisCode(fareruleobj.getFareBasisCode());
						farerule.setDepCode(fareruleobj.getOrigin());
						farerule.setFareProviderCode(Tboairpriceresponse.getResponse().getTraceId());
						farerule.setFareInfoRef(result.getResultIndex());
						if (StringUtils.isNotEmpty(result.getBaggageAllowance()))
						{
							String[] splitedbaggage = result.getBaggageAllowance().split("\\s+");
							farerule.setBagAllowanceValue(new BigInteger(splitedbaggage[0]));
						}
						FareRuleList.add(farerule);
						farerules.setTravelerType(flightsearch.getTripType());
						farerules.setFareRule(FareRuleList);
					}
					fareRulesList.add(farerules);
					fareFlightSegment.setFareRules(fareRulesList);
				}

				String fareBasisCode="ALL";
				if(fareBasisCodeSet.size()==1){
					fareBasisCode=fareBasisCodeSet.iterator().next();
				}
				fareFlightSegment.setFareBasisCode(fareBasisCode);
				flightSegmentsGroup.setFlightSegments(flightsegmentslist);
				flightSegmentsGroups.add(flightSegmentsGroup);
				fareFlightSegment.setFareRules(fareRulesList);
				fareFlightSegment.setFlightSegmentsGroups(flightSegmentsGroups);
				fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getBasePriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
				fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(AmountRoundingModeUtil.roundingMode(new BigDecimal(fareFlightSegment.getTotalPriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate()))));
				fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getTaxesWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
				fareFlightSegment.setBasePrice(String.valueOf(new BigDecimal(fareFlightSegment.getBasePrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
				fareFlightSegment.setTaxes(String.valueOf(new BigDecimal(fareFlightSegment.getTaxes()).multiply(flightsearch.getBaseToBookingExchangeRate())));

				FlightServiceTax flightServiceTax = null;
				FlightGstTax flightGstTax = null;
				if(companyConfig != null){
					if(companyConfig.getCompanyConfigType().isB2E()){
						if(companyConfig.getTaxtype()!=null && companyConfig.getTaxtype().equalsIgnoreCase("GST")){													
							fareFlightSegment.setTotalPrice(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
							flightGstTax = new FlightGstTax();
							flightGstTax = getFlightGstTax(flightsearch, companyConfig, company,parentCompany);
							BigDecimal totalPrice = new BigDecimal(fareFlightSegment.getTotalPrice());
							BigDecimal totalPriceAfterServiceTax = totalPrice.add(flightGstTax.getTotalTax()).add(flightGstTax.getManagementFee());
							fareFlightSegment.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(totalPriceAfterServiceTax)));
							fareFlightSegment.setPayableAmount(AmountRoundingModeUtil.roundingMode(totalPriceAfterServiceTax));

						}else{
							fareFlightSegment.setTotalPrice(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
							flightServiceTax = new FlightServiceTax();
							flightServiceTax = getSpecialReturnFlightServiceTax(fareFlightSegment.getBasePrice(),result.getFare().getYQTax(),flightsearch,companyConfig);
							BigDecimal totalPrice = new BigDecimal(fareFlightSegment.getTotalPrice());
							BigDecimal totalPriceAfterServiceTax = totalPrice.add(flightServiceTax.getTotalServiceTax()).add(flightServiceTax.getManagementFee());
							fareFlightSegment.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(totalPriceAfterServiceTax)));
							fareFlightSegment.setPayableAmount(AmountRoundingModeUtil.roundingMode(totalPriceAfterServiceTax));
						}
					}else{
						fareFlightSegment.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate()))));
					}
				}else{
					fareFlightSegment.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate()))));
				}
				fareFlightSegment.setFlightServiceTax(flightServiceTax);
				fareFlightSegment.setFlightGstTax(flightGstTax);
				flightPriceResponse.setFareFlightSegment(fareFlightSegment);
				flightPriceResponse.setFlightMarkUpConfiglistMap(FlightMarkUpConfiglistMap);
				flightPriceResponse.setMarkupCommissionDetails(markupCommissionDetails);
				flightPriceResponse.setFinalPriceWithGST(AmountRoundingModeUtil.roundingMode(new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice())));
				flightPriceResponse.setGSTonMarkup(new BigDecimal("0.00"));
				flightPriceResponse.setGSTonFlights(new BigDecimal("0.00"));

			}catch(Exception e){
				System.out.println("--------- AIRPRICE Exception---------" +e);
				logger.debug("--------- AIRPRICE Exception---------" +e);
				throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIRPRICE);
			}
			return flightPriceResponse;
		}
		else
		{
			throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIRPRICE);			
		}
	}

	//Airprice Response Parse Method for Special RoundTrip
	public static void parseAirPriceSpecial(TboFlightAirpriceResponse Tboairpriceresponse,Map<String,List<FlightMarkUpConfig>> FlightMarkUpConfiglistMap,
			Map<String, String> AirlineNameMap,
			ArrayList<Map<String, String>> MapList, Flightsearch flightsearch,
			String transaction_key, FlightTempAirSegmentDAO tempDAO,BigDecimal exchangeRate,FareFlightSegment searchfareFlightSegment,MarkupCommissionDetails markupCommissionDetails,FlightPriceResponse flightPriceResponse,CompanyConfigDAO companyConfigDAO,AppKeyVo appKeyVo,CompanyDao companyDao){

		if(Tboairpriceresponse.getResponse()!=null && Tboairpriceresponse.getResponse().getResponseStatus() == 1 && Tboairpriceresponse.getResponse().getResults()!=null){
			Result result = Tboairpriceresponse.getResponse().getResults();
			try {
				CompanyConfig companyConfig = null;
				try {
					companyConfig = appKeyVo.getCompanyConfig();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				Company company = null;
				try{
					company = appKeyVo.getCompany();
				}catch(Exception e){
					e.printStackTrace();
				}
				Company parentCompany = null;
				try{
					parentCompany = companyDao.getParentCompany(company);
				}catch(Exception e){
					e.printStackTrace();
				}				

				int passegers_for_marhup = flightsearch.getAdult()
						+ flightsearch.getKid()+flightsearch.getInfant();

				List<FlightSegmentsGroup> flightSegmentsGroups = new ArrayList<FlightSegmentsGroup>();
				FlightSegmentsGroup flightSegmentsGroup = new FlightSegmentsGroup();
				List<FlightSegments> flightsegmentslist = new ArrayList<FlightSegments>();
				List<FareRules> fareRulesList = new ArrayList<FareRules>();

				flightPriceResponse.setTransactionKey(transaction_key);
				flightPriceResponse.setFlightsearch(flightsearch);
				flightPriceResponse.setCountry("YET to get");
				FareFlightSegment fareFlightSegment = new FareFlightSegment();

				fareFlightSegment.setId(new UID().toString());
				fareFlightSegment.setCurrency(searchfareFlightSegment.getCurrency());
				fareFlightSegment.setApiCurrency(searchfareFlightSegment.getCurrency());
				fareFlightSegment.setBaseCurrency(flightsearch.getBaseCurrency());
				fareFlightSegment.setApiToBaseExchangeRate(searchfareFlightSegment.getApiToBaseExchangeRate());
				BigDecimal removalbaseprice = result.getFare().getCommissionEarned().add(result.getFare().getIncentiveEarned().add(result.getFare().getPLBEarned()));
				BigDecimal tdsprice = result.getFare().getTdsOnCommission().add(result.getFare().getTdsOnIncentive().add(result.getFare().getTdsOnPLB()));
				fareFlightSegment.setSupplierCommissionearned(removalbaseprice);
				fareFlightSegment.setSupplierTds(tdsprice);
				fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(result.getFare().getBaseFare().subtract(removalbaseprice).multiply(searchfareFlightSegment.getApiToBaseExchangeRate())));
				fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(result.getFare().getOfferedFare().multiply(searchfareFlightSegment.getApiToBaseExchangeRate())));

				if(result.getFare().getTransactionFee()!=null){
					fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(result.getFare().getTax().add(result.getFare().getAdditionalTxnFeeOfrd().add(result.getFare().getAdditionalTxnFeePub().add(result.getFare().getOtherCharges()).add(result.getFare().getTransactionFee())).add(tdsprice)).multiply(searchfareFlightSegment.getApiToBaseExchangeRate())));
				}
				else{
					fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(result.getFare().getTax().add(result.getFare().getAdditionalTxnFeeOfrd().add(result.getFare().getAdditionalTxnFeePub().add(result.getFare().getOtherCharges())).add(tdsprice)).multiply(searchfareFlightSegment.getApiToBaseExchangeRate())));
				}
				fareFlightSegment.setApi_basePriceWithoutMarkup(String.valueOf(result.getFare().getBaseFare().multiply(searchfareFlightSegment.getApiToBaseExchangeRate())));
				fareFlightSegment.setApi_totalPriceWithoutMarkup(String.valueOf(result.getFare().getOfferedFare().multiply(searchfareFlightSegment.getApiToBaseExchangeRate())));

				if(result.getFare().getTransactionFee()!=null){
					fareFlightSegment.setApi_taxesWithoutMarkup(String.valueOf(result.getFare().getTax().add(result.getFare().getAdditionalTxnFeeOfrd().add(result.getFare().getAdditionalTxnFeePub().add(result.getFare().getOtherCharges()).add(result.getFare().getTransactionFee()))).multiply(searchfareFlightSegment.getApiToBaseExchangeRate())));
				}
				else{
					fareFlightSegment.setApi_taxesWithoutMarkup(String.valueOf(result.getFare().getTax().add(result.getFare().getAdditionalTxnFeeOfrd().add(result.getFare().getAdditionalTxnFeePub().add(result.getFare().getOtherCharges()))).multiply(searchfareFlightSegment.getApiToBaseExchangeRate())));
				}
				Discount apidiscount = new Discount();
				apidiscount.setAmount(new BigDecimal(replaceCurrency(
						String.valueOf(result.getFare().getDiscount()), searchfareFlightSegment.getCurrency())).multiply(searchfareFlightSegment.getApiToBaseExchangeRate()));
				fareFlightSegment.setApiDiscount(apidiscount);

				Discount systemdiscount = new Discount();
				systemdiscount.setAmount(new BigDecimal(0.0));
				fareFlightSegment.setSystemDiscount(systemdiscount);

				Discount discount = new Discount();
				discount.setAmount(new BigDecimal(replaceCurrency(
						String.valueOf(result.getFare().getDiscount()), searchfareFlightSegment.getCurrency())).multiply(searchfareFlightSegment.getApiToBaseExchangeRate()).add(new BigDecimal(0.0)));
				fareFlightSegment.setDiscount(discount);

				fareFlightSegment.setLCC(result.getIsLCC());
				fareFlightSegment.setRefundable(result.getIsRefundable());

				if(result.getLastTicketDate() != null && result.getLastTicketDate() != ""){
					if(StringUtils.isNotEmpty(result.getLastTicketDate()) ){
						String lastTicketingDate= result.getLastTicketDate();
						parseLastTicketDate(lastTicketingDate,fareFlightSegment);
					}
				}
				UmarkUpServiceCall.getMarkupValuesFPR(flightPriceResponse,
						FlightMarkUpConfiglistMap,searchfareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getCarrier().getCode(), fareFlightSegment,"ALL",companyConfig);

				fareFlightSegment.setExchangeRate(searchfareFlightSegment.getExchangeRate());
				fareFlightSegment.setBookingCurrency(searchfareFlightSegment.getBookingCurrency());
				List<PassengerFareBreakUp> passengerFareBreakUps = new ArrayList<PassengerFareBreakUp>();

				AddPassengerFareBreakUp(FlightMarkUpConfiglistMap, passengerFareBreakUps,
						flightPriceResponse,searchfareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getCarrier().getCode(), passegers_for_marhup,result.getFareBreakdown(),flightsearch, searchfareFlightSegment.getApiToBaseExchangeRate(),searchfareFlightSegment.getCurrency(),Tboairpriceresponse);

				flightPriceResponse.setSpecialPassengerFareBreakUps(passengerFareBreakUps);
				List<Segments> segmentsList = new ArrayList<Segments>();
				String airlineCode = "ALL";
				FlightSegments flightSegments = new FlightSegments();
				flightSegments.setFlightIndex(new UID().toString());
				flightSegments.setApiProvider("TBO");
				flightSegments.setBookingCurrency(flightsearch.getCurrency());
				flightSegments.setExchangeRate(exchangeRate);
				if(result.getSegments()!=null && result.getSegments().size()>0)
				{
					for(List<FlightSearchrresponseSegment> Segments : result.getSegments())
					{
						for(FlightSearchrresponseSegment segment : Segments)
						{
							Segments segments = new Segments();
							buildFlightSegments(segment,segments,flightsearch);

							segments.setFareInfoRef(result.getResultIndex());

							if(segment.getDuration() != null)
								segments.setDuration(String.valueOf(segment.getDuration()));
							segmentsList.add(segments);
						}
						flightSegments.setSegments(segmentsList);
					}
					flightsegmentslist.add(flightSegments);
				}
				Set<String> fareBasisCodeSet = new HashSet<String>();
				List<FareRule> FareRuleList = new ArrayList<FareRule>();
				FareRules farerules = new FareRules();
				if( result.getFareRules()!=null &&  result.getFareRules().size()>0)
				{
					for(com.tayyarah.api.flight.tbo.model.FareRule fareruleobj : result.getFareRules())
					{
						FareRule farerule = new FareRule();
						fareBasisCodeSet.add(fareruleobj.getFareBasisCode());
						farerule.setAirlineCode(fareruleobj.getAirline());
						farerule.setArrCode(fareruleobj.getDestination());
						farerule.setBasisCode(fareruleobj.getFareBasisCode());
						farerule.setDepCode(fareruleobj.getOrigin());
						farerule.setFareProviderCode(Tboairpriceresponse.getResponse().getTraceId());
						farerule.setFareInfoRef(result.getResultIndex());
						if (result.getBaggageAllowance() != null)
						{
							String[] splitedbaggage = result.getBaggageAllowance().split("\\s+");
							farerule.setBagAllowanceValue(new BigInteger(splitedbaggage[0]));
						}
						FareRuleList.add(farerule);
						farerules.setTravelerType(flightsearch.getTripType());
						farerules.setFareRule(FareRuleList);
					}
					fareRulesList.add(farerules);
					fareFlightSegment.setFareRules(fareRulesList);
				}
				String fareBasisCode="ALL";
				if(fareBasisCodeSet!=null && fareBasisCodeSet.size()==1){
					fareBasisCode=fareBasisCodeSet.iterator().next();
				}
				fareFlightSegment.setFareBasisCode(fareBasisCode);
				flightSegmentsGroup.setFlightSegments(flightsegmentslist);
				flightSegmentsGroups.add(flightSegmentsGroup);
				fareFlightSegment.setFareRules(fareRulesList);
				fareFlightSegment.setFlightSegmentsGroups(flightSegmentsGroups);
				fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getBasePriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
				fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
				fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getTaxesWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
				fareFlightSegment.setBasePrice(String.valueOf(new BigDecimal(fareFlightSegment.getBasePrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
				fareFlightSegment.setTaxes(String.valueOf(new BigDecimal(fareFlightSegment.getTaxes()).multiply(flightsearch.getBaseToBookingExchangeRate())));
				fareFlightSegment.setLatestTicketingTime(result.getLastTicketDate());

				FlightServiceTax flightServiceTax = null;
				FlightGstTax flightGstTax = null;
				if(companyConfig != null){
					if(companyConfig.getCompanyConfigType().isB2E()){
						if(companyConfig.getTaxtype()!=null && companyConfig.getTaxtype().equalsIgnoreCase("GST")){													
							fareFlightSegment.setTotalPrice(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
							flightGstTax = new FlightGstTax();
							flightGstTax = getFlightGstTax(flightsearch, companyConfig, company,parentCompany);
							BigDecimal totalPrice = new BigDecimal(fareFlightSegment.getTotalPrice());
							BigDecimal totalPriceAfterServiceTax = totalPrice.add(flightGstTax.getTotalTax()).add(flightGstTax.getManagementFee());
							fareFlightSegment.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(totalPriceAfterServiceTax)));
							fareFlightSegment.setPayableAmount(AmountRoundingModeUtil.roundingMode(totalPriceAfterServiceTax));

						}else{
							fareFlightSegment.setTotalPrice(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
							flightServiceTax = new FlightServiceTax();
							flightServiceTax = getFlightServiceTax(fareFlightSegment.getBasePrice(),result.getFare().getYQTax(),flightsearch,companyConfig);
							BigDecimal totalPrice = new BigDecimal(fareFlightSegment.getTotalPrice());
							BigDecimal totalPriceAfterServiceTax = totalPrice.add(flightServiceTax.getTotalServiceTax()).add(flightServiceTax.getManagementFee());
							fareFlightSegment.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(totalPriceAfterServiceTax)));
							fareFlightSegment.setPayableAmount(AmountRoundingModeUtil.roundingMode(totalPriceAfterServiceTax));
						}
					}else{
						fareFlightSegment.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate()))));

					}
				}else{
					fareFlightSegment.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate()))));

				}
				fareFlightSegment.setFlightServiceTax(flightServiceTax);
				fareFlightSegment.setFlightGstTax(flightGstTax);
				flightPriceResponse.setSpecialFareFlightSegment(fareFlightSegment);
				flightPriceResponse.setFlightMarkUpConfiglistMap(FlightMarkUpConfiglistMap);
				flightPriceResponse.setMarkupCommissionDetails(markupCommissionDetails);		
				flightPriceResponse.setFinalPriceWithGST(AmountRoundingModeUtil.roundingMode(new BigDecimal(flightPriceResponse.getSpecialFareFlightSegment().getTotalPrice()).add(flightPriceResponse.getFinalPriceWithGST())));
				flightPriceResponse.setGSTonMarkupSpecial((new BigDecimal("0.00")));

			}catch(Exception e){
				System.out.println("Exception " +e.getMessage());
				throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIRPRICE);
			}
		}
		else
		{
			throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIRPRICE);
		}
	}

	// Add passenger BreakUp in Airprice Method
	private static void AddPassengerFareBreakUp(Map<String,List<FlightMarkUpConfig>> markupMap ,
			List<PassengerFareBreakUp> passengerFareBreakUps,
			FlightPriceResponse flightPriceResponse, String airlinename,
			int passegers_for_marhup,List<FareBreakdown> farebreakdownlist, Flightsearch flightsearch,BigDecimal apiToBaseExchangeRate,String apicurrency,TboFlightAirpriceResponse tboflightAirpriceResponse) throws Exception {

		BigDecimal	baseToBookingExchangeRate = flightsearch.getBaseToBookingExchangeRate();
		// to match offer price we are dividing basefare with other values
		BigDecimal removalbasefare = tboflightAirpriceResponse.getResponse().getResults().getFare().getCommissionEarned().add(tboflightAirpriceResponse.getResponse().getResults().getFare().getIncentiveEarned().add( tboflightAirpriceResponse.getResponse().getResults().getFare().getPLBEarned()));
		int totalpassenger = flightsearch.getAdult()+flightsearch.getKid()+flightsearch.getInfant();
		removalbasefare = removalbasefare.divide(new BigDecimal(String.valueOf(totalpassenger)),RoundingMode.HALF_UP);

		BigDecimal removaltaxprice = new BigDecimal("0.0");
		if(tboflightAirpriceResponse.getResponse().getResults().getFare().getTransactionFee()!=null)
			removaltaxprice = tboflightAirpriceResponse.getResponse().getResults().getFare().getOtherCharges().add(tboflightAirpriceResponse.getResponse().getResults().getFare().getAdditionalTxnFeeOfrd()).add(tboflightAirpriceResponse.getResponse().getResults().getFare().getAdditionalTxnFeePub()).add(tboflightAirpriceResponse.getResponse().getResults().getFare().getTransactionFee());
		else
			removaltaxprice = tboflightAirpriceResponse.getResponse().getResults().getFare().getOtherCharges().add(tboflightAirpriceResponse.getResponse().getResults().getFare().getAdditionalTxnFeeOfrd()).add(tboflightAirpriceResponse.getResponse().getResults().getFare().getAdditionalTxnFeePub());

		BigDecimal addtdsprice = tboflightAirpriceResponse.getResponse().getResults().getFare().getTdsOnCommission().add(tboflightAirpriceResponse.getResponse().getResults().getFare().getTdsOnIncentive()).add(tboflightAirpriceResponse.getResponse().getResults().getFare().getTdsOnPLB());
		removaltaxprice = removaltaxprice.add(addtdsprice);
		String removetaxprice = removaltaxprice.divide(new BigDecimal(String.valueOf(totalpassenger)),2,RoundingMode.HALF_UP).toPlainString();
		removaltaxprice = AmountRoundingModeUtil.roundingMode(new BigDecimal(removetaxprice));
		if(farebreakdownlist!=null && farebreakdownlist.size()>0)
		{
			for(int i=0;i<farebreakdownlist.size();i++){
				FareBreakdown farebreakdown = farebreakdownlist.get(i);
				if(farebreakdown.getPassengerType() == 1){
					for(int j=0;j<flightsearch.getAdult();j++){

						PassengerFareBreakUp passengerFareBreakUp = new PassengerFareBreakUp();
						passengerFareBreakUp.setCurrency(apicurrency);
						passengerFareBreakUp.setId(new UID().toString());
						passengerFareBreakUp.setType("ADT");

						// as per offerprice we subtract baseprice with CommissionEarned,IncentiveEarned,PLBEarned
						BigDecimal farebreakdownbasefare = farebreakdown.getBaseFare();
						BigDecimal basefare = farebreakdownbasefare.divide(new BigDecimal(flightsearch.getAdult()));
						BigDecimal basefareval = basefare.subtract(removalbasefare);
						String baseprice = String.valueOf(basefareval);

						BigDecimal tax = farebreakdown.getTax().divide(new BigDecimal(flightsearch.getAdult()));
						BigDecimal taxval = tax.add(removaltaxprice);
						String taxprice = String.valueOf(taxval);
						String totalprice = String.valueOf(new BigDecimal(baseprice).add(new BigDecimal(taxprice)));

						passengerFareBreakUp.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(baseprice).multiply(apiToBaseExchangeRate)));
						passengerFareBreakUp.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(totalprice).multiply(apiToBaseExchangeRate)));
						passengerFareBreakUp.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(taxprice).multiply(apiToBaseExchangeRate)));
						passengerFareBreakUp.setApi_basePriceWithoutMarkup(baseprice);
						passengerFareBreakUp.setApi_totalPriceWithoutMarkup(totalprice);
						passengerFareBreakUp.setApi_taxesWithoutMarkup(taxprice);

						FlightTaxBreakUp flightTaxBreakUp = new FlightTaxBreakUp();
						flightTaxBreakUp.setYQ(farebreakdown.getYQTax().divide(new BigDecimal(flightsearch.getAdult())));
						for (TaxBreakup taxBreakup : tboflightAirpriceResponse.getResponse().getResults().getFare().getTaxBreakup()) {
							if(taxBreakup.getKey().equalsIgnoreCase("YR"))
								flightTaxBreakUp.setYR(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("PSF"))
								flightTaxBreakUp.setPSF(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("UDF"))
								flightTaxBreakUp.setUDF(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("JNTax"))
								flightTaxBreakUp.setJN(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("INTax"))
								flightTaxBreakUp.setIN(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("TransactionFee"))
								flightTaxBreakUp.setTransactionFee(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("G1")|| taxBreakup.getKey().equalsIgnoreCase("G1Tax"))
								flightTaxBreakUp.setG1(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("F2")|| taxBreakup.getKey().equalsIgnoreCase("F2Tax"))
								flightTaxBreakUp.setF2(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("F6")|| taxBreakup.getKey().equalsIgnoreCase("F6Tax"))
								flightTaxBreakUp.setF6(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("ZR")|| taxBreakup.getKey().equalsIgnoreCase("ZRTax"))
								flightTaxBreakUp.setZR(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("YC")|| taxBreakup.getKey().equalsIgnoreCase("YCTax"))
								flightTaxBreakUp.setYC(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("US")|| taxBreakup.getKey().equalsIgnoreCase("USTax"))
								flightTaxBreakUp.setUS(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("XA")|| taxBreakup.getKey().equalsIgnoreCase("XATax"))
								flightTaxBreakUp.setUS(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("AY")|| taxBreakup.getKey().equalsIgnoreCase("AYTax"))
								flightTaxBreakUp.setAY(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("XF")|| taxBreakup.getKey().equalsIgnoreCase("XFTax"))
								flightTaxBreakUp.setXF(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("K3")|| taxBreakup.getKey().equalsIgnoreCase("K3Tax"))
								flightTaxBreakUp.setK3(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
						}

						passengerFareBreakUp.setFlightTaxBreakUp(flightTaxBreakUp);
						Discount apidiscount = new Discount();
						apidiscount.setAmount(new BigDecimal(0.0));
						passengerFareBreakUp.setApiDiscount(apidiscount);
						Discount systemdiscount = new Discount();
						systemdiscount.setAmount(new BigDecimal(0.0));
						passengerFareBreakUp.setSystemDiscount(systemdiscount);
						Discount discount = new Discount();
						discount.setAmount(new BigDecimal(0.0).add(new BigDecimal(0.0)));
						passengerFareBreakUp.setDiscount(discount);

						UmarkUpServiceCall.getMarkupValuesForPassegers(flightPriceResponse,
								markupMap, airlinename, passengerFareBreakUp,
								passegers_for_marhup,"ALL");

						passengerFareBreakUp.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(passengerFareBreakUp.getBasePriceWithoutMarkup()).multiply(baseToBookingExchangeRate)));
						passengerFareBreakUp.setTotalPriceWithoutMarkup(String.valueOf(AmountRoundingModeUtil.roundingMode(new BigDecimal(passengerFareBreakUp.getTotalPriceWithoutMarkup()).multiply(baseToBookingExchangeRate))));
						passengerFareBreakUp.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(passengerFareBreakUp.getTaxesWithoutMarkup()).multiply(baseToBookingExchangeRate)));
						passengerFareBreakUp.setBasePrice(String.valueOf(new BigDecimal(passengerFareBreakUp.getBasePrice()).multiply(baseToBookingExchangeRate)));
						passengerFareBreakUp.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(new BigDecimal(passengerFareBreakUp.getTotalPrice()).multiply(baseToBookingExchangeRate))));
						passengerFareBreakUp.setTaxes(String.valueOf(new BigDecimal(passengerFareBreakUp.getTaxes()).multiply(baseToBookingExchangeRate)));
						passengerFareBreakUps.add(passengerFareBreakUp);
					}
				}
				if(farebreakdown.getPassengerType() == 2){
					for(int k=0;k<flightsearch.getKid();k++)
					{
						PassengerFareBreakUp passengerFareBreakUp = new PassengerFareBreakUp();
						passengerFareBreakUp.setCurrency(apicurrency);
						passengerFareBreakUp.setId(new UID().toString());
						passengerFareBreakUp.setType("CNN");

						// as per offerprice we subtract baseprice with CommissionEarned,IncentiveEarned,PLBEarned
						BigDecimal farebreakdownbasefare = farebreakdown.getBaseFare();
						BigDecimal basefare = farebreakdownbasefare.divide(new BigDecimal(flightsearch.getKid()));
						BigDecimal basefareval  = basefare.subtract(removalbasefare);
						String baseprice = String.valueOf(basefareval);
						BigDecimal tax = farebreakdown.getTax().divide(new BigDecimal(flightsearch.getKid()));
						BigDecimal taxval = tax.add(removaltaxprice);
						String taxprice = String.valueOf(taxval);
						String totalprice = String.valueOf(new BigDecimal(baseprice).add(new BigDecimal(taxprice)));

						passengerFareBreakUp.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(baseprice).multiply(apiToBaseExchangeRate)));
						passengerFareBreakUp.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(totalprice).multiply(apiToBaseExchangeRate)));
						passengerFareBreakUp.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(taxprice).multiply(apiToBaseExchangeRate)));
						passengerFareBreakUp.setApi_basePriceWithoutMarkup(baseprice);
						passengerFareBreakUp.setApi_totalPriceWithoutMarkup(totalprice);
						passengerFareBreakUp.setApi_taxesWithoutMarkup(taxprice);

						FlightTaxBreakUp flightTaxBreakUp = new FlightTaxBreakUp();
						flightTaxBreakUp.setYQ(farebreakdown.getYQTax().divide(new BigDecimal(flightsearch.getKid())));
						for (TaxBreakup taxBreakup : tboflightAirpriceResponse.getResponse().getResults().getFare().getTaxBreakup()) {
							if(taxBreakup.getKey().equalsIgnoreCase("YR"))
								flightTaxBreakUp.setYR(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("PSF"))
								flightTaxBreakUp.setPSF(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("UDF"))
								flightTaxBreakUp.setUDF(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("JNTax"))
								flightTaxBreakUp.setJN(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("INTax"))
								flightTaxBreakUp.setIN(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("TransactionFee"))
								flightTaxBreakUp.setTransactionFee(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("G1")|| taxBreakup.getKey().equalsIgnoreCase("G1Tax"))
								flightTaxBreakUp.setG1(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("F2")|| taxBreakup.getKey().equalsIgnoreCase("F2Tax"))
								flightTaxBreakUp.setF2(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("F6")|| taxBreakup.getKey().equalsIgnoreCase("F6Tax"))
								flightTaxBreakUp.setF6(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("ZR")|| taxBreakup.getKey().equalsIgnoreCase("ZRTax"))
								flightTaxBreakUp.setZR(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("YC")|| taxBreakup.getKey().equalsIgnoreCase("YCTax"))
								flightTaxBreakUp.setYC(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("US")|| taxBreakup.getKey().equalsIgnoreCase("USTax"))
								flightTaxBreakUp.setUS(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("XA")|| taxBreakup.getKey().equalsIgnoreCase("XATax"))
								flightTaxBreakUp.setUS(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("AY")|| taxBreakup.getKey().equalsIgnoreCase("AYTax"))
								flightTaxBreakUp.setAY(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("XF")|| taxBreakup.getKey().equalsIgnoreCase("XFTax"))
								flightTaxBreakUp.setXF(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("K3")|| taxBreakup.getKey().equalsIgnoreCase("K3Tax"))
								flightTaxBreakUp.setK3(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
						}

						passengerFareBreakUp.setFlightTaxBreakUp(flightTaxBreakUp);
						Discount apidiscount = new Discount();
						apidiscount.setAmount(new BigDecimal(0.0));
						passengerFareBreakUp.setApiDiscount(apidiscount);
						Discount systemdiscount = new Discount();
						systemdiscount.setAmount(new BigDecimal(0.0));
						passengerFareBreakUp.setSystemDiscount(systemdiscount);
						Discount discount = new Discount();
						discount.setAmount(new BigDecimal(0.0).add(new BigDecimal(0.0)));
						passengerFareBreakUp.setDiscount(discount);

						UmarkUpServiceCall.getMarkupValuesForPassegers(flightPriceResponse,
								markupMap, airlinename, passengerFareBreakUp,
								passegers_for_marhup,"ALL");

						passengerFareBreakUp.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(passengerFareBreakUp.getBasePriceWithoutMarkup()).multiply(baseToBookingExchangeRate)));
						passengerFareBreakUp.setTotalPriceWithoutMarkup(String.valueOf(AmountRoundingModeUtil.roundingMode(new BigDecimal(passengerFareBreakUp.getTotalPriceWithoutMarkup()).multiply(baseToBookingExchangeRate))));
						passengerFareBreakUp.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(passengerFareBreakUp.getTaxesWithoutMarkup()).multiply(baseToBookingExchangeRate)));
						passengerFareBreakUp.setBasePrice(String.valueOf(new BigDecimal(passengerFareBreakUp.getBasePrice()).multiply(baseToBookingExchangeRate)));
						passengerFareBreakUp.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(new BigDecimal(passengerFareBreakUp.getTotalPrice()).multiply(baseToBookingExchangeRate))));
						passengerFareBreakUp.setTaxes(String.valueOf(new BigDecimal(passengerFareBreakUp.getTaxes()).multiply(baseToBookingExchangeRate)));
						passengerFareBreakUps.add(passengerFareBreakUp);
					}
				}
				if(farebreakdown.getPassengerType() == 3){

					for(int m=0;m<flightsearch.getInfant();m++)
					{
						PassengerFareBreakUp passengerFareBreakUp = new PassengerFareBreakUp();
						passengerFareBreakUp.setCurrency(apicurrency);
						passengerFareBreakUp.setId(new UID().toString());
						passengerFareBreakUp.setType("INF");

						// as per offerprice we subtract baseprice with CommissionEarned,IncentiveEarned,PLBEarned
						BigDecimal farebreakdownbasefare = farebreakdown.getBaseFare();
						BigDecimal basefare = farebreakdownbasefare.divide(new BigDecimal(flightsearch.getInfant()));
						BigDecimal basefareval  = basefare.subtract(removalbasefare);


						String baseprice = String.valueOf(basefareval);
						BigDecimal tax = farebreakdown.getTax().divide(new BigDecimal(flightsearch.getInfant()));
						BigDecimal taxval = tax.add(removaltaxprice);
						String taxprice = String.valueOf(taxval);
						String totalprice = String.valueOf(new BigDecimal(baseprice).add(new BigDecimal(taxprice)));

						passengerFareBreakUp.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(baseprice).multiply(apiToBaseExchangeRate)));
						passengerFareBreakUp.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(totalprice).multiply(apiToBaseExchangeRate)));
						passengerFareBreakUp.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(taxprice).multiply(apiToBaseExchangeRate)));
						passengerFareBreakUp.setApi_basePriceWithoutMarkup(baseprice);
						passengerFareBreakUp.setApi_totalPriceWithoutMarkup(totalprice);
						passengerFareBreakUp.setApi_taxesWithoutMarkup(taxprice);

						FlightTaxBreakUp flightTaxBreakUp = new FlightTaxBreakUp();
						flightTaxBreakUp.setYQ(farebreakdown.getYQTax().divide(new BigDecimal(flightsearch.getInfant())));
						for (TaxBreakup taxBreakup : tboflightAirpriceResponse.getResponse().getResults().getFare().getTaxBreakup()) {
							if(taxBreakup.getKey().equalsIgnoreCase("YR"))
								flightTaxBreakUp.setYR(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("PSF"))
								flightTaxBreakUp.setPSF(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("UDF"))
								flightTaxBreakUp.setUDF(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("JNTax"))
								flightTaxBreakUp.setJN(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("INTax"))
								flightTaxBreakUp.setIN(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("TransactionFee"))
								flightTaxBreakUp.setTransactionFee(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger)));
							if(taxBreakup.getKey().equalsIgnoreCase("WO"))
								flightTaxBreakUp.setWO(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("G1")|| taxBreakup.getKey().equalsIgnoreCase("G1Tax"))
								flightTaxBreakUp.setG1(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("F2")|| taxBreakup.getKey().equalsIgnoreCase("F2Tax"))
								flightTaxBreakUp.setF2(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("F6")|| taxBreakup.getKey().equalsIgnoreCase("F6Tax"))
								flightTaxBreakUp.setF6(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("ZR")|| taxBreakup.getKey().equalsIgnoreCase("ZRTax"))
								flightTaxBreakUp.setZR(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("YC")|| taxBreakup.getKey().equalsIgnoreCase("YCTax"))
								flightTaxBreakUp.setYC(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("US")|| taxBreakup.getKey().equalsIgnoreCase("USTax"))
								flightTaxBreakUp.setUS(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("XA")|| taxBreakup.getKey().equalsIgnoreCase("XATax"))
								flightTaxBreakUp.setUS(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("AY")|| taxBreakup.getKey().equalsIgnoreCase("AYTax"))
								flightTaxBreakUp.setAY(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("XF")|| taxBreakup.getKey().equalsIgnoreCase("XFTax"))
								flightTaxBreakUp.setXF(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
							if(taxBreakup.getKey().equalsIgnoreCase("K3")|| taxBreakup.getKey().equalsIgnoreCase("K3Tax"))
								flightTaxBreakUp.setK3(new BigDecimal(taxBreakup.getValue()).divide(new BigDecimal(totalpassenger),2,RoundingMode.HALF_UP));
						}

						passengerFareBreakUp.setFlightTaxBreakUp(flightTaxBreakUp);
						Discount apidiscount = new Discount();
						apidiscount.setAmount(new BigDecimal(0.0));
						passengerFareBreakUp.setApiDiscount(apidiscount);
						Discount systemdiscount = new Discount();
						systemdiscount.setAmount(new BigDecimal(0.0));
						passengerFareBreakUp.setSystemDiscount(systemdiscount);
						Discount discount = new Discount();
						discount.setAmount(new BigDecimal(0.0).add(new BigDecimal(0.0)));
						passengerFareBreakUp.setDiscount(discount);

						UmarkUpServiceCall.getMarkupValuesForPassegers(flightPriceResponse,
								markupMap, airlinename, passengerFareBreakUp,
								passegers_for_marhup,"ALL");

						passengerFareBreakUp.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(passengerFareBreakUp.getBasePriceWithoutMarkup()).multiply(baseToBookingExchangeRate)));
						passengerFareBreakUp.setTotalPriceWithoutMarkup(String.valueOf(AmountRoundingModeUtil.roundingMode(new BigDecimal(passengerFareBreakUp.getTotalPriceWithoutMarkup()).multiply(baseToBookingExchangeRate))));
						passengerFareBreakUp.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(passengerFareBreakUp.getTaxesWithoutMarkup()).multiply(baseToBookingExchangeRate)));
						passengerFareBreakUp.setBasePrice(String.valueOf(new BigDecimal(passengerFareBreakUp.getBasePrice()).multiply(baseToBookingExchangeRate)));
						passengerFareBreakUp.setTotalPrice(String.valueOf(AmountRoundingModeUtil.roundingMode(new BigDecimal(passengerFareBreakUp.getTotalPrice()).multiply(baseToBookingExchangeRate))));
						passengerFareBreakUp.setTaxes(String.valueOf(new BigDecimal(passengerFareBreakUp.getTaxes()).multiply(baseToBookingExchangeRate)));
						passengerFareBreakUps.add(passengerFareBreakUp);
					}
				}
			}
		}
	}

	private static String replaceCurrency(String data, String currency) {
		return data.replace(currency, "");
	}

	// Ticket Response Parse Method
	public static FlightBookingResponse parseTicketResponse(FlightBookingResponse flightBookingResponse,TboTicketResponse tboTicketResponse, String orderId, FlightBookingDao FBDAO,EmailDao emaildao, String transactionkey, String paymode, WalletAmountTranferHistory walletAmountTranferHistory,int count,List<FlightOrderCustomer> flightOrderCustomers,FlightCustomerDetails flightCustomerDetails,FlightOrderRow flightOrderRow ) throws Exception {
		try{
			FlightDataBaseServices DBS = new FlightDataBaseServices();
			if(count!=1){
				flightBookingResponse = new FlightBookingResponse();
			}

			// Save Flight Api Response
			if(tboTicketResponse.getResponse() != null){
				ApiResponseSaver.saveFlightApiResponse(FBDAO, tboTicketResponse, flightOrderRow);
			}

			if(tboTicketResponse.getResponse()!=null && tboTicketResponse.getResponse().getError()!=null && tboTicketResponse.getResponse().getError().getErrorCode() == 0){

				if(tboTicketResponse.getResponse().getResponse()!=null && !tboTicketResponse.getResponse().getResponse().getIsPriceChanged())
				{
					if(count==0||count==10){
						flightBookingResponse.setPnr(tboTicketResponse.getResponse().getResponse().getPNR());
						flightBookingResponse.setBokingConditions("");
						flightBookingResponse.setBookingComments("Confirmed");
						flightBookingResponse.setBookingStatus(true);

					}else {
						flightBookingResponse.setPnrSpecial(tboTicketResponse.getResponse().getResponse().getPNR());
						flightBookingResponse.setBokingConditionsSpecial("");
						flightBookingResponse.setBookingCommentsSpecial("Confirmed");
						flightBookingResponse.setBookingStatusSpecial(true);
					}

					List<FlightOrderCustomer> flightOrderCustomerslist = new ArrayList<FlightOrderCustomer>();
					List<FlightOrderCustomerPriceBreakup> FlightOrderCustomerPriceBreakuplist = new ArrayList<FlightOrderCustomerPriceBreakup>();
					for(int i = 0; i < tboTicketResponse.getResponse().getResponse().getFlightItinerary().getPassenger().size(); i++){
						Passenger passenger = tboTicketResponse.getResponse().getResponse().getFlightItinerary().getPassenger().get(i);
						FlightOrderCustomer Customers = flightOrderCustomers.get(i);
						PassengerDetails passengerdetails = flightCustomerDetails.getPassengerdetailsList().get(i);
						Customers.setEticketid(String.valueOf(passenger.getTicket().getTicketId()));
						Customers.setEticketnumber(passenger.getTicket().getTicketNumber());
						passengerdetails.setEticketnumber(passenger.getTicket().getTicketNumber());
						flightOrderCustomerslist.add(Customers);						
					}
					try {
						FBDAO.updateFlightOrderCustomerDetails(flightOrderCustomerslist,flightOrderRow.getId());
					} catch (Exception e) {
						logger.error("Exception", e);
						throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
					}

					FlightOrderRow updatedflightOrderRow = DBS.updatePNRandWalletTBO(tboTicketResponse.getResponse().getResponse().getPNR(), orderId, FBDAO,String.valueOf(tboTicketResponse.getResponse().getResponse().getBookingId()),tboTicketResponse.getResponse().getResponse().getFlightItinerary().getFare().getDiscount(),new BigDecimal(0.0),tboTicketResponse.getResponse().getResponse().getFlightItinerary().getFare().getDiscount(),tboTicketResponse.getResponse().getTraceId());
					if(count==1||count==10){
						TboCommonUtil.updateKeystatus(transactionkey,  FBDAO);
						flightBookingResponse.setInvoiceNumber(updatedflightOrderRow.getInvoiceNo());

					}else{
						flightBookingResponse.setInvoiceNumberSpecial(updatedflightOrderRow.getInvoiceNo());
					}			

				}else{

					if(count==0||count==10){
						flightBookingResponse.setPnr("NA");
						flightBookingResponse.setBokingConditions("Fare changed, wants to continue? or try with new flight");
						flightBookingResponse.setBookingComments("Booking Failed");
						flightBookingResponse.setBookingStatus(false);
					}else{
						flightBookingResponse.setPnrSpecial("NA");
						flightBookingResponse.setBokingConditionsSpecial("Fare changed, wants to continue? or try with new flight");
						flightBookingResponse.setBookingCommentsSpecial("Booking Failed");
						flightBookingResponse.setBookingStatusSpecial(false);
					}					
					return flightBookingResponse;
				}
			}
			else{				
				flightBookingResponse.setBookingComments("Ticketing Failed");
				flightBookingResponse.setBookingStatus(false);
				throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.BOOKING_FAILED);
			}
		}
		catch(Exception e){
			flightBookingResponse.setBookingComments("Ticketing Failed");
			flightBookingResponse.setBookingStatus(false);
			throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.BOOKING_FAILED);
		}
		return flightBookingResponse;

	}

	// Ticket Response Parse Method
	public static FlightBookingResponse parseHoldBookResponse(FlightBookingResponse flightBookingResponse,TboBookResponse tboBookResponse, String orderId, FlightBookingDao FBDAO,EmailDao emaildao, String transactionkey, String paymode, WalletAmountTranferHistory walletAmountTranferHistory,int count,List<FlightOrderCustomer> flightOrderCustomers,FlightCustomerDetails flightCustomerDetails,FlightOrderRow flightOrderRow ) throws Exception {
		try{
			FlightDataBaseServices DBS = new FlightDataBaseServices();
			if(count!=1){
				flightBookingResponse=new FlightBookingResponse();
			}
			if(tboBookResponse.getResponse().getError().getErrorCode() == 0){

				if(!tboBookResponse.getResponse().getResponse().getIsPriceChanged())
				{
					//NonLCCResponse nonLCCResponse = tboBookResponse.getResponse().getResponse();
					if(count==0||count==10){
						flightBookingResponse.setPnr(tboBookResponse.getResponse().getResponse().getPNR());
						flightBookingResponse.setBokingConditions("");
						flightBookingResponse.setBookingComments("Hold");
						flightBookingResponse.setBookingStatus(false);
					}else {
						flightBookingResponse.setPnrSpecial(tboBookResponse.getResponse().getResponse().getPNR());
						flightBookingResponse.setBokingConditionsSpecial("");
						flightBookingResponse.setBookingCommentsSpecial("Hold");
						flightBookingResponse.setBookingStatusSpecial(false);
					}
					List<FlightOrderCustomer> flightOrderCustomerslist = new ArrayList<FlightOrderCustomer>();
					List<FlightOrderCustomerPriceBreakup> FlightOrderCustomerPriceBreakuplist = new ArrayList<FlightOrderCustomerPriceBreakup>();
					if(tboBookResponse.getResponse().getResponse().getFlightItinerary()!=null && tboBookResponse.getResponse().getResponse().getFlightItinerary().getPassenger()!=null && tboBookResponse.getResponse().getResponse().getFlightItinerary().getPassenger().size()>0)
						for(int i = 0; i < tboBookResponse.getResponse().getResponse().getFlightItinerary().getPassenger().size(); i++){
							Passenger passenger = tboBookResponse.getResponse().getResponse().getFlightItinerary().getPassenger().get(i);
							FlightOrderCustomer Customers = flightOrderCustomers.get(i);
							flightOrderCustomerslist.add(Customers);
							FlightOrderCustomerPriceBreakup flightOrderCustomerPriceBreakup = new FlightOrderCustomerPriceBreakup();
							flightOrderCustomerPriceBreakup.setBaseFare(passenger.getFare().getBaseFare());
							if(passenger.getPaxType() == "1"){
								flightOrderCustomerPriceBreakup.setTax(passenger.getFare().getTax().add(passenger.getFare().getAdditionalTxnFeeOfrd().add(passenger.getFare().getAdditionalTxnFeePub()).add(passenger.getFare().getOtherCharges())));
							}
							else{
								flightOrderCustomerPriceBreakup.setTax(passenger.getFare().getTax().add(passenger.getFare().getAdditionalTxnFeeOfrd().add(passenger.getFare().getAdditionalTxnFeePub())));
							}
							flightOrderCustomerPriceBreakup.setSupplierDiscount(passenger.getFare().getDiscount());
							flightOrderCustomerPriceBreakup.setSystemDiscount(new BigDecimal(0.0));
							flightOrderCustomerPriceBreakup.setPublishedDiscount(passenger.getFare().getDiscount());
							flightOrderCustomerPriceBreakup.setTotal(AmountRoundingModeUtil.roundingMode(passenger.getFare().getOfferedFare()));
							FlightOrderCustomerPriceBreakuplist.add(flightOrderCustomerPriceBreakup);
						}

					try {
						FBDAO.updateFlightOrderCustomerDetails(flightOrderCustomerslist,flightOrderRow.getId());
					} catch (Exception e) {
						logger.error("Exception", e);
						throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
					}

					// Update PNR and Status for Hold Booking
					DBS.updateHoldPNRTBO(tboBookResponse.getResponse().getResponse().getPNR(), orderId, FBDAO,String.valueOf(tboBookResponse.getResponse().getResponse().getBookingId()),tboBookResponse.getResponse().getResponse().getFlightItinerary().getFare().getDiscount(),new BigDecimal(0.0),tboBookResponse.getResponse().getResponse().getFlightItinerary().getFare().getDiscount(),tboBookResponse.getResponse().getTraceId(),"Hold");
					TboCommonUtil.updateMailstatus(orderId,emaildao);

				}else{
					if(count==0||count==10){
						flightBookingResponse.setPnr("NA");
						flightBookingResponse.setBokingConditions("Fare changed, wants to continue? or try with new flight");
						flightBookingResponse.setBookingComments("Booking Failed");
						flightBookingResponse.setBookingStatus(false);
					}else{
						flightBookingResponse.setPnrSpecial("NA");
						flightBookingResponse.setBokingConditionsSpecial("Fare changed, wants to continue? or try with new flight");
						flightBookingResponse.setBookingCommentsSpecial("Booking Failed");
						flightBookingResponse.setBookingStatusSpecial(false);
					}
					DBS.updatePNR("0", orderId, FBDAO);
					try{
						TboCommonUtil.updateMailstatus(orderId,emaildao );
					}
					catch(Exception e){
						logger.error("Email Update Exception after sucessful booking", e);
						return flightBookingResponse;
					}
				}
			}
			else{
				throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.BOOKING_FAILED);
			}
		}
		catch(Exception e){
			logger.error("Exception Hold booking", e);
			throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.BOOKING_FAILED);
		}
		return flightBookingResponse;
	}

	public static FlightCalendarSearchResponse parseCalendarFareResponse(TboCalendarFareResponse tboCalendarFareResponse,FlightCalendarSearch flightCalendarSearch,Map<String,List<FlightMarkUpConfig>> markupMap){
		FlightCalendarSearchResponse flightCalendarSearchResponse = null;
		try{
			flightCalendarSearchResponse = new FlightCalendarSearchResponse();
			if(tboCalendarFareResponse.getResponse().getError().getErrorCode() == 0){
				flightCalendarSearchResponse.setOri(tboCalendarFareResponse.getResponse().getOrigin());
				flightCalendarSearchResponse.setDest(tboCalendarFareResponse.getResponse().getDestination());
				List<CalendarResponseData> calendarResponseDataList = new ArrayList<>();
				for (SearchResult searchResult : tboCalendarFareResponse.getResponse().getSearchResults()) {
					CalendarResponseData calendarResponseData = new CalendarResponseData();
					calendarResponseData.setAirlineCode(searchResult.getAirlineCode());
					calendarResponseData.setAirlineName(searchResult.getAirlineName());
					calendarResponseData.setApi_basePriceWithoutMarkup(String.valueOf(searchResult.getBaseFare()));
					calendarResponseData.setApi_totalPriceWithoutMarkup(String.valueOf(searchResult.getFare()));
					calendarResponseData.setApi_taxesWithoutMarkup(String.valueOf(searchResult.getTax()));
					calendarResponseData.setBasePriceWithoutMarkup(String.valueOf(searchResult.getBaseFare()));
					calendarResponseData.setTaxesWithoutMarkup(String.valueOf(searchResult.getTax()));
					calendarResponseData.setTotalPriceWithoutMarkup(String.valueOf(searchResult.getBaseFare()));					
					UmarkUpServiceCall.getMarkupValuesForCalendar(markupMap,calendarResponseData);
					calendarResponseData.setApiCurrency("INR");
					calendarResponseData.setBaseCurrency("INR");
					calendarResponseData.setLowestFareOfMonth(searchResult.getIsLowestFareOfMonth());
					calendarResponseData.setDepDate(searchResult.getDepartureDate().substring(0, 10));
					calendarResponseDataList.add(calendarResponseData);
				}
				flightCalendarSearchResponse.setSearchResult(calendarResponseDataList);

			}else{
				flightCalendarSearchResponse.setSearchResult(null);			
			}

		}catch(Exception e){
			logger.error("Exception FlightCalendarSearchResponse", e);
			throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.CALENDARNOTAVAILABLE);
		}
		return flightCalendarSearchResponse;
	}

	private static void buildFlightSegments(FlightSearchrresponseSegment segment, Segments segments, Flightsearch flightsearch) {
		Flight flightdetail = new Flight();
		if(segment.getAirline()!=null)
		{
			flightdetail.setNumber(segment.getAirline().getFlightNumber());
			flightdetail.setEquipment(segment.getAirline().getFareClass());

			Cabin cabin = new Cabin();
			cabin.setName(flightsearch.getCabinClass());
			cabin.setCode(segment.getAirline().getFareClass());
			segments.setCabin(cabin);

			Carrier carrier = new Carrier();
			carrier.setCode(segment.getAirline().getAirlineCode());
			carrier.setName(segment.getAirline().getAirlineName());
			segments.setCarrier(carrier);
			segments.setCraft(segment.getCraft());
			segments.setFareClass(segment.getAirline().getFareClass());
		}
		if(segment.getDestination()!=null )
		{
			if(segment.getDestination().getAirport()!=null)
			{
				flightdetail.setDestTerminal(segment.getDestination().getAirport().getTerminal());
				segments.setDest(segment.getDestination().getAirport().getAirportCode());
				segments.setDestName(segment.getDestination().getAirport().getCityName());
				segments.setDestAirportName(segment.getDestination().getAirport().getAirportName());
			}
			if(segment.getDestination().getArrTime()!=null)
			{
				segments.setArrival(segment.getDestination().getArrTime());
				segments.setArrTime(segment.getDestination().getArrTime().substring(11, 16));
				segments.setArrDate(segment.getDestination().getArrTime().substring(0, 10));
			}
		}
		if(segment.getOrigin()!=null)
		{
			if(segment.getOrigin().getDepTime()!=null)
			{
				segments.setDepart(segment.getOrigin().getDepTime());
				segments.setDepTime(segment.getOrigin().getDepTime().substring(11, 16));
				segments.setDepDate(segment.getOrigin().getDepTime().substring(0, 10));
			}
			if(segment.getOrigin().getAirport()!=null )
			{
				flightdetail.setOriTerminal(segment.getOrigin().getAirport().getTerminal());
				segments.setOri(segment.getOrigin().getAirport().getAirportCode());
				segments.setOri(segment.getOrigin().getAirport().getAirportCode());
				segments.setOriName(segment.getOrigin().getAirport().getCityName());
				segments.setOriAirportName(segment.getOrigin().getAirport().getAirportName());
			}
		}
		segments.setFlight(flightdetail);
	}

	private static void parseLastTicketDate(String lastTicketingDate, FareFlightSegment fareFlightSegment) {
		String lastTicketDateNew = "";
		if(!lastTicketingDate.contains("T00:00:00")){
			try{
				if(lastTicketingDate.toUpperCase().contains("T0") || lastTicketingDate.toUpperCase().contains("T11")  || lastTicketingDate.toUpperCase().contains("T12") || lastTicketingDate.toUpperCase().contains("T10")){
					SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date date = originalFormat.parse(lastTicketingDate);
					lastTicketDateNew = targetFormat.format(date);
					fareFlightSegment.setLatestTicketingTime(lastTicketDateNew+"T00:00:00");
				}else{
					DateFormat originalFormat = new SimpleDateFormat("ddMMMyy");
					DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date date = originalFormat.parse(lastTicketingDate);
					lastTicketDateNew = targetFormat.format(date);
					fareFlightSegment.setLatestTicketingTime(lastTicketDateNew+"T00:00:00");
				}
			}catch(Exception e){
				SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date;
				try {
					date = originalFormat.parse(lastTicketingDate);
					lastTicketDateNew = targetFormat.format(date);
					fareFlightSegment.setLatestTicketingTime(lastTicketDateNew+"T00:00:00");
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
		}
		else{
			fareFlightSegment.setLatestTicketingTime(lastTicketingDate);
		}
	}

	public static FlightServiceTax getFlightServiceTax(String basefare,BigDecimal YqTax,Flightsearch flightsearch,CompanyConfig companyConfig){

		BigDecimal baseprice =  new BigDecimal(basefare).add(YqTax);
		BigDecimal baseServiceTax = new BigDecimal("0.0");
		BigDecimal SBC = new BigDecimal("0.0");
		BigDecimal KKC = new BigDecimal("0.0");
		BigDecimal totalServiceTax = new BigDecimal("0.0");
		BigDecimal managementFee  = new BigDecimal("0.0");

		if(flightsearch.isDomestic()){
			FlightDomesticServiceTaxConfig flightDomesticServiceTaxConfig = companyConfig.getFlightDomesticServiceTaxConfig();
			baseServiceTax = baseprice.divide(new BigDecimal("100.0")).multiply(flightDomesticServiceTaxConfig.getBasicTax());
			SBC =  baseprice.divide(new BigDecimal("100.0")).multiply(flightDomesticServiceTaxConfig.getSwatchBharathCess());
			KKC =  baseprice.divide(new BigDecimal("100.0")).multiply(flightDomesticServiceTaxConfig.getKrishiKalyanCess());
			totalServiceTax =  baseprice.divide(new BigDecimal("100.0")).multiply(flightDomesticServiceTaxConfig.getTotalTax());
			managementFee = flightDomesticServiceTaxConfig.getManagementFee();
		}

		if(flightsearch.isIsInternational()){
			FlightInternationalServiceTaxConfig flightInternationalServiceTaxConfig = companyConfig.getFlightInternationalServiceTaxConfig();
			baseServiceTax = baseprice.divide(new BigDecimal("100.0")).multiply(flightInternationalServiceTaxConfig.getBasicTax());
			SBC =  baseprice.divide(new BigDecimal("100.0")).multiply(flightInternationalServiceTaxConfig.getSwatchBharathCess());
			KKC =  baseprice.divide(new BigDecimal("100.0")).multiply(flightInternationalServiceTaxConfig.getKrishiKalyanCess());
			totalServiceTax =  baseprice.divide(new BigDecimal("100.0")).multiply(flightInternationalServiceTaxConfig.getTotalTax());
			managementFee = flightInternationalServiceTaxConfig.getManagementFee();
		}

		FlightServiceTax flightServiceTax = new FlightServiceTax();
		flightServiceTax.setBaseServicetax(baseServiceTax.setScale(2, RoundingMode.UP));
		flightServiceTax.setSBC(SBC.setScale(2, RoundingMode.UP));
		flightServiceTax.setKKC(KKC.setScale(2, RoundingMode.UP));
		flightServiceTax.setTotalServiceTax(totalServiceTax.setScale(2, RoundingMode.UP));
		flightServiceTax.setManagementFee(managementFee.setScale(2, RoundingMode.UP));

		return flightServiceTax;
	}

	public static FlightGstTax getFlightGstTax(Flightsearch flightsearch,CompanyConfig companyConfig,Company company,Company parentCompany){

		BigDecimal CGST = new BigDecimal("0.0");
		BigDecimal SGST = new BigDecimal("0.0");
		BigDecimal IGST = new BigDecimal("0.0");
		BigDecimal UGST = new BigDecimal("0.0");
		BigDecimal totalGst = new BigDecimal("0.0");
		BigDecimal managementFee  = new BigDecimal("0.0");
		boolean isParentCompanyUT = IndianUnionTerritories.isUnionter(parentCompany.getBillingstate().trim());
		boolean isBillingCompanyUT = IndianUnionTerritories.isUnionter(company.getBillingstate().trim());
		BigDecimal totalPassenger = new BigDecimal(flightsearch.getAdult()).add(new BigDecimal(flightsearch.getKid())).add(new BigDecimal(flightsearch.getInfant()));
		if(flightsearch.isDomestic()){
			FlightDomesticGstTaxConfig flightDomesticGstTaxConfig = companyConfig.getFlightDomesticGstTaxConfig();
			managementFee = flightDomesticGstTaxConfig.getManagementFee().multiply(totalPassenger);				

			if(isParentCompanyUT && isBillingCompanyUT){
				CGST = managementFee.divide(new BigDecimal("100.0")).multiply(flightDomesticGstTaxConfig.getCGST());
				UGST =  managementFee.divide(new BigDecimal("100.0")).multiply(flightDomesticGstTaxConfig.getUGST());
			}
			if(!company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim()) && IndianUnionTerritories.isUnionter(company.getBillingstate().trim())){
				CGST = managementFee.divide(new BigDecimal("100.0")).multiply(flightDomesticGstTaxConfig.getCGST());
				UGST =  managementFee.divide(new BigDecimal("100.0")).multiply(flightDomesticGstTaxConfig.getUGST());
			}
			if(!isParentCompanyUT && !isBillingCompanyUT){				
				if(company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim())){
					CGST = managementFee.divide(new BigDecimal("100.0")).multiply(flightDomesticGstTaxConfig.getCGST());
					SGST =  managementFee.divide(new BigDecimal("100.0")).multiply(flightDomesticGstTaxConfig.getSGST());				
				}
			}
			if(isParentCompanyUT && !isBillingCompanyUT){
				if(!company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim()) && !IndianUnionTerritories.isUnionter(company.getBillingstate().trim())){
					IGST =  managementFee.divide(new BigDecimal("100.0")).multiply(flightDomesticGstTaxConfig.getIGST());		
				}
			}		
			totalGst = CGST.add(SGST).add(IGST).add(UGST);	
		}
		if(flightsearch.isIsInternational()){
			FlightInternationalGstTaxConfig flightInternationalGstTaxConfig = companyConfig.getFlightInternationalGstTaxConfig();
			managementFee = flightInternationalGstTaxConfig.getManagementFee().multiply(totalPassenger);

			if(isParentCompanyUT && isBillingCompanyUT){
				CGST = managementFee.divide(new BigDecimal("100.0")).multiply(flightInternationalGstTaxConfig.getCGST());
				UGST =  managementFee.divide(new BigDecimal("100.0")).multiply(flightInternationalGstTaxConfig.getUGST());
			}
			if(!company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim()) && IndianUnionTerritories.isUnionter(company.getBillingstate().trim())){
				CGST = managementFee.divide(new BigDecimal("100.0")).multiply(flightInternationalGstTaxConfig.getCGST());
				UGST =  managementFee.divide(new BigDecimal("100.0")).multiply(flightInternationalGstTaxConfig.getUGST());
			}
			if(!isParentCompanyUT && !isBillingCompanyUT){
				if(company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim())){
					CGST = managementFee.divide(new BigDecimal("100.0")).multiply(flightInternationalGstTaxConfig.getCGST());
					SGST =  managementFee.divide(new BigDecimal("100.0")).multiply(flightInternationalGstTaxConfig.getSGST());				
				}
			}
			if(isParentCompanyUT && !isBillingCompanyUT){
				if(!company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim()) && !IndianUnionTerritories.isUnionter(company.getBillingstate().trim())){
					IGST =  managementFee.divide(new BigDecimal("100.0")).multiply(flightInternationalGstTaxConfig.getIGST());		
				}
			}	
			totalGst = CGST.add(SGST).add(IGST).add(UGST);	
		}
		FlightGstTax flightGstTax = new FlightGstTax();
		flightGstTax.setCGST(CGST);
		flightGstTax.setSGST(SGST);
		flightGstTax.setIGST(IGST);
		flightGstTax.setUGST(UGST);
		flightGstTax.setManagementFee(managementFee);
		flightGstTax.setTotalTax(totalGst);

		return flightGstTax;
	}


	public static FlightServiceTax getSpecialReturnFlightServiceTax(String basefare,BigDecimal YqTax,Flightsearch flightsearch,CompanyConfig companyConfig){

		BigDecimal baseprice =  new BigDecimal(basefare).add(YqTax);
		BigDecimal baseServiceTax = new BigDecimal("0.0");
		BigDecimal SBC = new BigDecimal("0.0");
		BigDecimal KKC = new BigDecimal("0.0");
		BigDecimal totalServiceTax = new BigDecimal("0.0");
		BigDecimal managementFee  = new BigDecimal("0.0");
		if(flightsearch.isDomestic() && flightsearch.getTripType().equalsIgnoreCase("SR")){
			FlightDomesticServiceTaxConfig flightDomesticServiceTaxConfig = companyConfig.getFlightDomesticServiceTaxConfig();
			baseServiceTax = baseprice.divide(new BigDecimal("100.0")).multiply(flightDomesticServiceTaxConfig.getBasicTax());
			SBC =  baseprice.divide(new BigDecimal("100.0")).multiply(flightDomesticServiceTaxConfig.getSwatchBharathCess());
			KKC =  baseprice.divide(new BigDecimal("100.0")).multiply(flightDomesticServiceTaxConfig.getKrishiKalyanCess());
			totalServiceTax =  baseprice.divide(new BigDecimal("100.0")).multiply(flightDomesticServiceTaxConfig.getTotalTax());
			managementFee = flightDomesticServiceTaxConfig.getManagementFee();

			// We have Calculate Service Tax for Onward and Return 
			baseServiceTax = baseServiceTax.multiply(new BigDecimal(2));
			SBC = SBC.multiply(new BigDecimal(2));
			KKC = KKC.multiply(new BigDecimal(2));			
			totalServiceTax = totalServiceTax.multiply(new BigDecimal(2));
			managementFee = managementFee.multiply(new BigDecimal(2));		
		}	
		FlightServiceTax flightServiceTax = new FlightServiceTax();
		flightServiceTax.setBaseServicetax(baseServiceTax.setScale(2, RoundingMode.UP));
		flightServiceTax.setSBC(SBC.setScale(2, RoundingMode.UP));
		flightServiceTax.setKKC(KKC.setScale(2, RoundingMode.UP));
		flightServiceTax.setTotalServiceTax(totalServiceTax.setScale(2, RoundingMode.UP));
		flightServiceTax.setManagementFee(managementFee.setScale(2, RoundingMode.UP));
		return flightServiceTax;
	}
}
