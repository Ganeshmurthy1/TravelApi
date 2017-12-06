package com.tayyarah.flight.util.api.tbo;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.tayyarah.api.flight.tbo.model.AirSearchResult;
import com.tayyarah.api.flight.tbo.model.Airline;
import com.tayyarah.api.flight.tbo.model.AuthenticateResponse;
import com.tayyarah.api.flight.tbo.model.Baggage;
import com.tayyarah.api.flight.tbo.model.BookRequestFare;
import com.tayyarah.api.flight.tbo.model.CalendarSegment;
import com.tayyarah.api.flight.tbo.model.Fare;
import com.tayyarah.api.flight.tbo.model.FlightSearchrresponseSegment;
import com.tayyarah.api.flight.tbo.model.GetBookingDetailsRequest;
import com.tayyarah.api.flight.tbo.model.Meal;
import com.tayyarah.api.flight.tbo.model.MealDynamic;
import com.tayyarah.api.flight.tbo.model.NonLCCTicketRequest;
import com.tayyarah.api.flight.tbo.model.Passenger;
import com.tayyarah.api.flight.tbo.model.PassengerLCC;
import com.tayyarah.api.flight.tbo.model.PriceBDRequestSegment;
import com.tayyarah.api.flight.tbo.model.Seat;
import com.tayyarah.api.flight.tbo.model.Segment;
import com.tayyarah.api.flight.tbo.model.TboAgencyBalanceRequest;
import com.tayyarah.api.flight.tbo.model.TboBookResponse;
import com.tayyarah.api.flight.tbo.model.TboBookingDetailsResponse;
import com.tayyarah.api.flight.tbo.model.TboCancelTicketRequest;
import com.tayyarah.api.flight.tbo.model.TboCancellationStatusRequest;
import com.tayyarah.api.flight.tbo.model.TboFlightAirpriceRequest;
import com.tayyarah.api.flight.tbo.model.TboFlightAirpriceResponse;
import com.tayyarah.api.flight.tbo.model.TboFlightBookingRequest;
import com.tayyarah.api.flight.tbo.model.TboFlightCalendarFareRequest;
import com.tayyarah.api.flight.tbo.model.TboFlightFareRuleRequest;
import com.tayyarah.api.flight.tbo.model.TboFlightSSRRequest;
import com.tayyarah.api.flight.tbo.model.TboFlightSearchRequest;
import com.tayyarah.api.flight.tbo.model.TboFlightSearchResponse;
import com.tayyarah.api.flight.tbo.model.TboPriceBDRequest;
import com.tayyarah.api.flight.tbo.model.TboReleasePNRRequest;
import com.tayyarah.api.flight.tbo.model.TboTicketRequestForLCC;
import com.tayyarah.apiconfig.model.TboFlightConfig;
import com.tayyarah.common.entity.OrderCustomer;
import com.tayyarah.common.util.TayyarahGSTDetails;
import com.tayyarah.flight.entity.FlightOrderCustomer;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.flight.model.FareRules;
import com.tayyarah.flight.model.FlightBookingResponse;
import com.tayyarah.flight.model.FlightCalendarSearch;
import com.tayyarah.flight.model.FlightCancelRequest;
import com.tayyarah.flight.model.FlightCustomerDetails;
import com.tayyarah.flight.model.FlightPriceResponse;
import com.tayyarah.flight.model.Flightsearch;
import com.travelport.api_v33.AirResponse.FareInfo;

public class TboRequestBuilder {
	private static TboRequestBuilder tboRequestBuilder;
	static final Logger logger = Logger.getLogger(TboRequestBuilder.class);

	private TboRequestBuilder(){

	}
	public static TboRequestBuilder getTboRequestBuilder(){
		if(tboRequestBuilder == null){
			tboRequestBuilder = new TboRequestBuilder();
		}
		return tboRequestBuilder;
	}

	private static void addSegments(TboFlightSearchRequest searchRequest,Flightsearch flightsearch) {
		ArrayList<Segment> segmentList=new ArrayList<Segment>();
		if(flightsearch.getTripType().equalsIgnoreCase("R")||(flightsearch.getTripType().equalsIgnoreCase("O")) ||(flightsearch.getTripType().equalsIgnoreCase("AD")) || flightsearch.getTripType().equalsIgnoreCase("SR") ){
			Segment segments=new Segment();
			segments.setOrigin(flightsearch.getOrigin());
			segments.setDestination(flightsearch.getDestination());
			if(flightsearch.getCabinClass().equalsIgnoreCase("Economy")){
				segments.setFlightCabinClass("2");
			}
			else if(flightsearch.getCabinClass().equalsIgnoreCase("PremiumEconomy")){
				segments.setFlightCabinClass("3");
			}
			else if(flightsearch.getCabinClass().equalsIgnoreCase("Business")){
				segments.setFlightCabinClass("4");
			}
			else if(flightsearch.getCabinClass().equalsIgnoreCase("PremiumBusiness")){
				segments.setFlightCabinClass("5");
			}
			else if(flightsearch.getCabinClass().equalsIgnoreCase("First")){
				segments.setFlightCabinClass("6");
			}
			else{
				segments.setFlightCabinClass("1");
			}

			segments.setPreferredArrivalTime(flightsearch.getDepDate()+"T00:00:00");
			segments.setPreferredDepartureTime(flightsearch.getDepDate()+"T00:00:00");
			segmentList.add(segments);
		}
		if(flightsearch.getTripType().equalsIgnoreCase("R") || flightsearch.getTripType().equalsIgnoreCase("SR")){
			Segment segments=new Segment();
			segments.setOrigin(flightsearch.getDestination());
			segments.setDestination(flightsearch.getOrigin());
			if(flightsearch.getCabinClass().equalsIgnoreCase("Economy")){
				segments.setFlightCabinClass("2");
			}
			else if(flightsearch.getCabinClass().equalsIgnoreCase("Premium")){
				segments.setFlightCabinClass("3");
			}
			else if(flightsearch.getCabinClass().equalsIgnoreCase("Business")){
				segments.setFlightCabinClass("4");
			}
			else if(flightsearch.getCabinClass().equalsIgnoreCase("PremiumBusiness")){
				segments.setFlightCabinClass("5");
			}
			else if(flightsearch.getCabinClass().equalsIgnoreCase("First")){
				segments.setFlightCabinClass("6");
			}
			else{
				segments.setFlightCabinClass("1");
			}
			segments.setPreferredArrivalTime(flightsearch.getArvlDate()+"T00:00:00");
			segments.setPreferredDepartureTime(flightsearch.getArvlDate()+"T00:00:00");
			segmentList.add(segments);
		}
		searchRequest.setSegments(segmentList);
	}

	public TboFlightCalendarFareRequest createCalendarFareSearchBuilder(FlightCalendarSearch flightCalendarSearch,TboFlightConfig tboFlightConfig){
		TboFlightCalendarFareRequest tboFlightCalendarFareRequest = new TboFlightCalendarFareRequest();
		String journeyType = "1";		
		ArrayList<CalendarSegment> segmentList = new ArrayList<CalendarSegment>();
		CalendarSegment segments = new CalendarSegment();
		segments.setOrigin(flightCalendarSearch.getOrigin());
		segments.setDestination(flightCalendarSearch.getDestination());
		segments.setPreferredDepartureTime(flightCalendarSearch.getDepDate()+"T00:00:00");
		if(flightCalendarSearch.getCabinClass().equalsIgnoreCase("Economy")){
			segments.setFlightCabinClass("2");
		}
		else if(flightCalendarSearch.getCabinClass().equalsIgnoreCase("PremiumEconomy")){
			segments.setFlightCabinClass("3");
		}
		else if(flightCalendarSearch.getCabinClass().equalsIgnoreCase("Business")){
			segments.setFlightCabinClass("4");
		}
		else if(flightCalendarSearch.getCabinClass().equalsIgnoreCase("PremiumBusiness")){
			segments.setFlightCabinClass("5");
		}
		else if(flightCalendarSearch.getCabinClass().equalsIgnoreCase("First")){
			segments.setFlightCabinClass("6");
		}
		else{
			segments.setFlightCabinClass("1");
		}
		segmentList.add(segments);
		String[] sources = new String[4];
		sources[0] = "SG";
		sources[1] = "G8";
		sources[2] = "6E";
		sources[3] = "GDS";

		tboFlightCalendarFareRequest.setJourneyType(journeyType);
		tboFlightCalendarFareRequest.setEndUserIp(tboFlightConfig.getEnduserip());
		tboFlightCalendarFareRequest.setTokenId(TboGetToken.getToken(true,tboFlightConfig));		
		tboFlightCalendarFareRequest.setSegments(segmentList);
		return tboFlightCalendarFareRequest;
	}


	/// Method For Create Search Request
	public TboFlightSearchRequest createSearchBuilder(Flightsearch flightsearch,TboFlightConfig tboFlightConfig){
		TboFlightSearchRequest searchRequest=new TboFlightSearchRequest();

		try {
			String journeyType="1";
			if(flightsearch.getTripType().equalsIgnoreCase("R")){
				journeyType="2";
			}else if(flightsearch.getTripType().equalsIgnoreCase("M")){
				journeyType="3";
			}else if(flightsearch.getTripType().equalsIgnoreCase("AD")){
				journeyType="4";			}
			else if(flightsearch.getTripType().equalsIgnoreCase("SR")){
				journeyType="5";
			}


			//TboFlightConfig tboFlightConfig = TboFlightConfig.GetTboConfig();
			searchRequest.setAdultCount(String.valueOf(flightsearch.getAdult()));
			searchRequest.setChildCount(String.valueOf(flightsearch.getKid()));
			searchRequest.setInfantCount(String.valueOf(flightsearch.getInfant()));
			searchRequest.setIsDomestic(String.valueOf(!flightsearch.isIsInternational()));
			searchRequest.setOneStopFlight("false");
			searchRequest.setDirectFlight("false");
			searchRequest.setJourneyType(journeyType);
			searchRequest.setEndUserIp(tboFlightConfig.getEnduserip());
			searchRequest.setTokenId(TboGetToken.getToken(true,tboFlightConfig));

			if(!flightsearch.getAirline().equalsIgnoreCase("All")){
				String[] PreferredAirlines = new String[1];
				PreferredAirlines[0] = flightsearch.getAirline();
				searchRequest.setPreferredAirlines(PreferredAirlines);
				String[] source = new String[1];
				source[0] = "GDS";
				searchRequest.setSources(source);
			}
			if(flightsearch.getTripType().equalsIgnoreCase("SR")){
				String[] sources = new String[3];
				sources[0] = "SG";
				sources[1] = "G8";
				sources[2] = "6E";
				searchRequest.setSources(sources);
			}


			addSegments(searchRequest,flightsearch);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}

		return searchRequest;
	}

	// Method For Create FareRule Request
	public TboFlightFareRuleRequest createFareruleBuilder(FareInfo fareinfo){
		TboFlightFareRuleRequest farerulerequest = new TboFlightFareRuleRequest();
		String ResultIndex = fareinfo.getKey();
		TboFlightConfig tboFlightConfig=TboFlightConfig.GetTboConfig(null);
		farerulerequest.setEndUserIp(tboFlightConfig.getEnduserip());
		farerulerequest.setTokenId(TboGetToken.getToken(true,null));
		farerulerequest.setResultIndex(ResultIndex);
		farerulerequest.setTraceId(fareinfo.getFareRuleKey().getProviderCode());
		return farerulerequest;
	}

	// Method For Create FareQuote Request
	public TboFlightAirpriceRequest createAirpriceRequestbuilder(FareRules farerule,TboFlightConfig tboFlightConfig){
		TboFlightAirpriceRequest airpricerequest = new TboFlightAirpriceRequest();
		String ResultIndex = farerule.getFareRule().get(0).getFareInfoRef();
		airpricerequest.setEndUserIp(tboFlightConfig.getEnduserip());
		airpricerequest.setTokenId(TboGetToken.getToken(true,tboFlightConfig));
		airpricerequest.setResultIndex(ResultIndex);
		airpricerequest.setTraceId(farerule.getFareRule().get(0).getFareProviderCode());
		return airpricerequest;
	}

	// Method For Create FareQuote Request for Special Return LCC
	public TboFlightAirpriceRequest createSpecialReturnAirpriceRequestbuilder(FareRules farerule,FareRules returnfarerule,TboFlightConfig tboFlightConfig){
		TboFlightAirpriceRequest airpricerequest = new TboFlightAirpriceRequest();
		String ResultIndex = farerule.getFareRule().get(0).getFareInfoRef();
		String ResultIndexReturn = returnfarerule.getFareRule().get(0).getFareInfoRef();
		airpricerequest.setEndUserIp(tboFlightConfig.getEnduserip());
		airpricerequest.setTokenId(TboGetToken.getToken(true,tboFlightConfig));
		airpricerequest.setResultIndex(ResultIndex+","+ResultIndexReturn);
		airpricerequest.setTraceId(farerule.getFareRule().get(0).getFareProviderCode());
		return airpricerequest;
	}

	// Method For Create SSR Request  Request for Special Return LCC
	public TboFlightSSRRequest createSpecialReturnSSRRequestBuilder(String ResultIndex,String traceId,TboFlightConfig tboFlightConfig){
		TboFlightSSRRequest SSRrequest = new TboFlightSSRRequest();		
		SSRrequest.setEndUserIp(tboFlightConfig.getEnduserip());
		SSRrequest.setTokenId(TboGetToken.getToken(true,tboFlightConfig));
		SSRrequest.setResultIndex(ResultIndex);
		SSRrequest.setTraceId(traceId);
		return SSRrequest;
	}

	// Method For Create SSR Request
	public TboFlightSSRRequest createSSRRequestBuilder(FareRules farerule,TboFlightConfig tboFlightConfig){
		TboFlightSSRRequest SSRrequest = new TboFlightSSRRequest();
		String ResultIndex = farerule.getFareRule().get(0).getFareInfoRef();
		SSRrequest.setEndUserIp(tboFlightConfig.getEnduserip());
		SSRrequest.setTokenId(TboGetToken.getToken(true,tboFlightConfig));
		SSRrequest.setResultIndex(ResultIndex);
		SSRrequest.setTraceId(farerule.getFareRule().get(0).getFareProviderCode());
		return SSRrequest;
	}

	// Method For Direct Ticket Request For LCC Carrier
	public TboTicketRequestForLCC createFlightTicketRequestbuilder(FlightBookingResponse flightBookingResponse,TboFlightAirpriceResponse tboFlightAirpriceResponse,OrderCustomer orderCustomer,FlightPriceResponse flightPriceResponse,List<FlightOrderCustomer> flightOrderCustomers,String orderId, FlightCustomerDetails flightCustomerDetails,boolean isSpecial,TboFlightConfig tboFlightConfig) throws ParseException{
		TboTicketRequestForLCC bookrequest = new TboTicketRequestForLCC();
		bookrequest.setEndUserIp(tboFlightConfig.getEnduserip());
		bookrequest.setTokenId(TboGetToken.getToken(true,tboFlightConfig));
		bookrequest.setResultIndex(tboFlightAirpriceResponse.getResponse().getResults().getResultIndex());
		bookrequest.setTraceId(tboFlightAirpriceResponse.getResponse().getTraceId());		
		Map<String,Integer> gendermap = new HashMap<String,Integer>();
		gendermap.put("Mr", 1);gendermap.put("Master", 1);gendermap.put("Mrs", 2);gendermap.put("Ms", 2);gendermap.put("Miss", 2);
		Map<String,Integer> Paxtypemap = new HashMap<String,Integer>();
		Paxtypemap.put("ADT", 1);Paxtypemap.put("CHD", 2);Paxtypemap.put("INF", 3);
		List<PassengerLCC> passengers = new ArrayList<PassengerLCC>();

		int count = 0;
		for(FlightOrderCustomer flightOrderCustomer : flightOrderCustomers){
			PassengerLCC passenger = new PassengerLCC();
			passenger.setFirstName(flightOrderCustomer.getFirstName());
			passenger.setLastName(flightOrderCustomer.getLastName());
			passenger.setPaxType(Paxtypemap.get(flightOrderCustomer.getPassengerTypeCode()));
			passenger.setTitle(flightOrderCustomer.getTitle());
			passenger.setIsLeadPax(true);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date dob = sdf.parse(flightOrderCustomer.getBirthday());

			String dateofbirth = sdf.format(dob);
			dateofbirth = dateofbirth+"T00:00:00";

			passenger.setDateOfBirth(dateofbirth);
			passenger.setCity(flightOrderCustomer.getCustomer().getCity());
			passenger.setAddressLine1(flightOrderCustomer.getCustomer().getAddress());
			passenger.setContactNo(flightOrderCustomer.getCustomer().getMobile());
			passenger.setCountryCode(flightOrderCustomer.getCustomer().getCountryId());
			if(flightOrderCustomer.getCustomer().getCountryId() == "IN")
				passenger.setCountryName("India");
			else
				passenger.setCountryName("India");

			passenger.setEmail(orderCustomer.getEmail());
			passenger.setGender(gendermap.get(flightOrderCustomer.getGender()));

			if(flightOrderCustomer.getPassportNo() == null || flightOrderCustomer.getPassportNo().equalsIgnoreCase(""))
				passenger.setPassportNo("");
			else
				passenger.setPassportNo(flightOrderCustomer.getPassportNo());


			if(flightOrderCustomer.getPassportNo() == null || flightOrderCustomer.getPassportNo().equalsIgnoreCase("") ){
				passenger.setPassportExpiry("");				
			}else{
				String PassportExpiryDate = sdf.format(flightOrderCustomer.getPassportExpiryDate());
				PassportExpiryDate = PassportExpiryDate+"T00:00:00";
				passenger.setPassportExpiry(PassportExpiryDate);
			}

			passenger.setFFAirline("");
			passenger.setFFNumber("");

			Fare fare = new Fare();
			fare.setBaseFare(tboFlightAirpriceResponse.getResponse().getResults().getFare().getBaseFare());
			fare.setTax(tboFlightAirpriceResponse.getResponse().getResults().getFare().getTax());
			fare.setYQTax(tboFlightAirpriceResponse.getResponse().getResults().getFare().getYQTax());
			fare.setAirTransFee(tboFlightAirpriceResponse.getResponse().getResults().getFare().getTransactionFee());
			fare.setAdditionalTxnFeeOfrd(tboFlightAirpriceResponse.getResponse().getResults().getFare().getAdditionalTxnFeeOfrd());
			fare.setAdditionalTxnFeePub(tboFlightAirpriceResponse.getResponse().getResults().getFare().getAdditionalTxnFeePub());
			if(tboFlightAirpriceResponse.getResponse().getResults().getFare().getAdditionalTxnFee() == null || tboFlightAirpriceResponse.getResponse().getResults().getFare().getAdditionalTxnFee().compareTo(BigDecimal.ZERO) == 0)
				fare.setAdditionalTxnFee(new BigDecimal("0.0"));
			else
				fare.setAdditionalTxnFee(tboFlightAirpriceResponse.getResponse().getResults().getFare().getAdditionalTxnFee());


			passenger.setFare(fare);

			if(passenger.getPaxType() != 3){

				Meal meal = null;
				MealDynamic mealDynamic = null;
				Seat seat = new Seat();
				seat.setCode("");
				seat.setDescription("");

				List<Baggage> baggagelist = new ArrayList<Baggage>();
				List<MealDynamic> mealdynamiclist = new ArrayList<MealDynamic>();
				Baggage baggage = null;

				if(isSpecial && flightPriceResponse.getReturnspecialServiceRequest() !=null ){
					if(flightCustomerDetails.getPassengerdetailsList().get(count).getReturnbaggagecode()!= null && flightCustomerDetails.getPassengerdetailsList().get(count).getReturnbaggagecode() != ""){
						for (List<Baggage> baggagetype : flightPriceResponse.getReturnspecialServiceRequest().getBaggage()) {
							for (Baggage baggageitem : baggagetype) {
								if(flightCustomerDetails.getPassengerdetailsList().get(count).getReturnbaggagecode().equalsIgnoreCase(baggageitem.getCode())){
									baggage = new Baggage();
									baggage = baggageitem;
									baggagelist.add(baggage);
								}
							}
						}
					}
					if(flightCustomerDetails.getPassengerdetailsList().get(count).getReturnseatcode()!= null && flightCustomerDetails.getPassengerdetailsList().get(count).getReturnseatcode() != ""){
						for (Seat seattype : flightPriceResponse.getReturnspecialServiceRequest().getSeatPreference()) {
							if(flightCustomerDetails.getPassengerdetailsList().get(count).getReturnseatcode().equalsIgnoreCase(seattype.getCode())){
								seat.setCode(seattype.getCode());
								seat.setDescription(seattype.getDescription());
							}
						}
					}
					if(flightCustomerDetails.getPassengerdetailsList().get(count).getReturnmealcode() != null && flightCustomerDetails.getPassengerdetailsList().get(count).getReturnmealcode() != ""){
						if(!flightPriceResponse.getReturnspecialServiceRequest().isIsLCC()){
							for (Meal mealtype : flightPriceResponse.getReturnspecialServiceRequest().getMeal()) {
								if(flightCustomerDetails.getPassengerdetailsList().get(count).getReturnmealcode().equalsIgnoreCase(mealtype.getCode())){
									meal = new Meal();
									meal.setCode(mealtype.getCode());
									meal.setDescription(mealtype.getDescription());
								}
							}
							passenger.setMeal(meal);
						}
						if(flightPriceResponse.getReturnspecialServiceRequest().isIsLCC()){
							for (List<MealDynamic> mealtype : flightPriceResponse.getReturnspecialServiceRequest().getMealDynamic()) {
								for (MealDynamic mealDynamicobj : mealtype) {
									if(flightCustomerDetails.getPassengerdetailsList().get(count).getReturnmealcode().equalsIgnoreCase(mealDynamicobj.getCode())){

										mealDynamic = new MealDynamic();
										mealDynamic = mealDynamicobj;
										mealdynamiclist.add(mealDynamic);

									}
								}
							}
							passenger.setMealDynamic(mealdynamiclist);
						}
					}
				}
				if(!isSpecial && flightPriceResponse.getSpecialServiceRequest() !=null ){
					if(flightCustomerDetails.getPassengerdetailsList().get(count).getBaggagecode()!= null && flightCustomerDetails.getPassengerdetailsList().get(count).getBaggagecode() != ""){
						for (List<Baggage> baggagetype : flightPriceResponse.getSpecialServiceRequest().getBaggage()) {
							for (Baggage baggageitem : baggagetype) {
								if(flightCustomerDetails.getPassengerdetailsList().get(count).getBaggagecode().equalsIgnoreCase(baggageitem.getCode())){
									baggage = new Baggage();
									baggage = baggageitem;
									baggagelist.add(baggage);
								}
							}

						}
					}
					if(flightCustomerDetails.getPassengerdetailsList().get(count).getSeatcode()!= null && flightCustomerDetails.getPassengerdetailsList().get(count).getSeatcode() != ""){
						for (Seat seattype : flightPriceResponse.getSpecialServiceRequest().getSeatPreference()) {
							if(flightCustomerDetails.getPassengerdetailsList().get(count).getSeatcode().equalsIgnoreCase(seattype.getCode())){
								seat.setCode(seattype.getCode());
								seat.setDescription(seattype.getDescription());
							}
						}
					}
					if(flightCustomerDetails.getPassengerdetailsList().get(count).getMealcode() != null && flightCustomerDetails.getPassengerdetailsList().get(count).getMealcode() != ""){
						if(!flightPriceResponse.getSpecialServiceRequest().isIsLCC()){
							for (Meal mealtype : flightPriceResponse.getSpecialServiceRequest().getMeal()) {
								if(flightCustomerDetails.getPassengerdetailsList().get(count).getMealcode().equalsIgnoreCase(mealtype.getCode())){
									meal = new Meal();
									meal.setCode(mealtype.getCode());
									meal.setDescription(mealtype.getDescription());
								}
							}
							passenger.setMeal(meal);
						}
						if(flightPriceResponse.getSpecialServiceRequest().isIsLCC()){
							for (List<MealDynamic> mealtype : flightPriceResponse.getSpecialServiceRequest().getMealDynamic()) {
								for (MealDynamic mealDynamicobj : mealtype) {
									if(flightCustomerDetails.getPassengerdetailsList().get(count).getMealcode().equalsIgnoreCase(mealDynamicobj.getCode())){
										if(flightPriceResponse.getFlightsearch().getOrigin().equalsIgnoreCase(mealDynamicobj.getOrigin())){
											mealDynamic = new MealDynamic();
											mealDynamic = mealDynamicobj;
											mealdynamiclist.add(mealDynamic);
										}
									}
								}
							}
							passenger.setMealDynamic(mealdynamiclist);
						}
					}
				}
				if(!flightPriceResponse.getFlightsearch().isSpecialSearch() && flightPriceResponse.getFlightsearch().getTripType().equalsIgnoreCase("R")){
					if(flightPriceResponse.getSpecialServiceRequest()!=null && flightPriceResponse.getSpecialServiceRequest().isIsLCC()){
						if(flightCustomerDetails.getPassengerdetailsList().get(count).getMealcode() != null && flightCustomerDetails.getPassengerdetailsList().get(count).getMealcode() != ""){
							for (MealDynamic mealDynamicobj : flightPriceResponse.getSpecialServiceRequest().getMealDynamic().get(0)) {
								if(flightCustomerDetails.getPassengerdetailsList().get(count).getMealcode().equalsIgnoreCase(mealDynamicobj.getCode())){
									mealDynamic = new MealDynamic();
									mealDynamic = mealDynamicobj;
									mealdynamiclist.add(mealDynamic);
									flightCustomerDetails.getPassengerdetailsList().get(count).setMealname(mealDynamicobj.getAirlineDescription());
								}
							}
						}
						if(flightCustomerDetails.getPassengerdetailsList().get(count).getReturnmealcode() != null && flightCustomerDetails.getPassengerdetailsList().get(count).getReturnmealcode() != ""){
							for (MealDynamic mealDynamicobj : flightPriceResponse.getSpecialServiceRequest().getMealDynamic().get(1)) {
								if(flightCustomerDetails.getPassengerdetailsList().get(count).getReturnmealcode().equalsIgnoreCase(mealDynamicobj.getCode())){
									mealDynamic = new MealDynamic();
									mealDynamic = mealDynamicobj;
									mealdynamiclist.add(mealDynamic);
									flightCustomerDetails.getPassengerdetailsList().get(count).setReturnmealname(mealDynamicobj.getAirlineDescription());
								}
							}
						}
						passenger.setMealDynamic(mealdynamiclist);

						if(flightCustomerDetails.getPassengerdetailsList().get(count).getBaggagecode()!= null && flightCustomerDetails.getPassengerdetailsList().get(count).getBaggagecode() != ""){
							for (Baggage baggageitem : flightPriceResponse.getSpecialServiceRequest().getBaggage().get(0)) {
								if(flightCustomerDetails.getPassengerdetailsList().get(count).getBaggagecode().equalsIgnoreCase(baggageitem.getCode()) ){
									baggage = new Baggage();
									baggage = baggageitem;
									baggagelist.add(baggage);
									flightCustomerDetails.getPassengerdetailsList().get(count).setBaggageweight(String.valueOf(baggageitem.getWeight()));
								}
							}
						}
						if(flightCustomerDetails.getPassengerdetailsList().get(count).getReturnbaggagecode()!= null && flightCustomerDetails.getPassengerdetailsList().get(count).getReturnbaggagecode() != ""){
							for (Baggage baggageitem : flightPriceResponse.getSpecialServiceRequest().getBaggage().get(0)) {
								if(flightCustomerDetails.getPassengerdetailsList().get(count).getReturnbaggagecode().equalsIgnoreCase(baggageitem.getCode())){
									baggage = new Baggage();
									baggage = baggageitem;
									baggagelist.add(baggage);
									flightCustomerDetails.getPassengerdetailsList().get(count).setReturnbaggageweight(String.valueOf(baggageitem.getWeight()));
								}
							}
						}
					}
				}
				passenger.setSeat(seat);
				passenger.setBaggage(baggagelist);

			}
			passenger.setgSTCompanyAddress(TayyarahGSTDetails.getGstValue("GSTCompanyAddress"));
			passenger.setgSTCompanyContactNumber(TayyarahGSTDetails.getGstValue("GSTCompanyContactNumber"));
			passenger.setgSTCompanyEmail(TayyarahGSTDetails.getGstValue("GSTCompanyEmail"));
			passenger.setgSTCompanyName(TayyarahGSTDetails.getGstValue("GSTCompanyName"));
			passenger.setgSTNumber(TayyarahGSTDetails.getGstValue("GSTNumber"));
			passengers.add(passenger);
			count++;
		}
		bookrequest.setPassengers(passengers);
		return bookrequest;
	}

	/* Method For Create Book Request For NONLCC Carrier */
	public TboFlightBookingRequest createFlightBookRequestbuilder(FlightBookingResponse flightBookingResponse,TboFlightAirpriceResponse tboFlightAirpriceResponse,OrderCustomer orderCustomer,FlightPriceResponse flightPriceResponse,List<FlightOrderCustomer> flightOrderCustomers,String orderId, FlightCustomerDetails flightCustomerDetails,boolean isSpecial,TboFlightConfig tboFlightConfig) throws ParseException{
		TboFlightBookingRequest bookrequest = new TboFlightBookingRequest();
		bookrequest.setEndUserIp(tboFlightConfig.getEnduserip());
		bookrequest.setTokenId(TboGetToken.getToken(true,tboFlightConfig));
		bookrequest.setResultIndex(tboFlightAirpriceResponse.getResponse().getResults().getResultIndex());
		bookrequest.setTraceId(tboFlightAirpriceResponse.getResponse().getTraceId());

		Map<String,String> titlemap = new HashMap<String,String>();
		titlemap.put("M", "Mr");titlemap.put("F", "Miss");
		Map<String,String> gendermap = new HashMap<String,String>();
		gendermap.put("Mr", "1");gendermap.put("Master", "1");gendermap.put("Mrs", "2");gendermap.put("Ms", "2");gendermap.put("Miss", "2");
		Map<String,String> Paxtypemap = new HashMap<String,String>();
		Paxtypemap.put("ADT", "1");Paxtypemap.put("CHD", "2");Paxtypemap.put("INF", "3");
		List<Passenger> passengers = new ArrayList<Passenger>();
		int count = 0;
		for(FlightOrderCustomer flightOrderCustomer : flightOrderCustomers){
			Passenger passenger = new Passenger();
			passenger.setFirstName(flightOrderCustomer.getFirstName());
			passenger.setLastName(flightOrderCustomer.getLastName());
			passenger.setPaxType(Paxtypemap.get(flightOrderCustomer.getPassengerTypeCode()));
			passenger.setTitle(flightOrderCustomer.getTitle());
			passenger.setIsLeadPax(true);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date dob = sdf.parse(flightOrderCustomer.getBirthday());

			String dateofbirth = sdf.format(dob);
			dateofbirth = dateofbirth+"T00:00:00";

			passenger.setDateOfBirth(dateofbirth);
			passenger.setCity(flightOrderCustomer.getCustomer().getCity());
			passenger.setAddressLine1(flightOrderCustomer.getCustomer().getAddress());
			passenger.setContactNo(flightOrderCustomer.getCustomer().getMobile());
			passenger.setCountryCode(flightOrderCustomer.getCustomer().getCountryId());
			if(flightOrderCustomer.getCustomer().getCountryId() == "IN")
				passenger.setCountryName("India");
			else
				passenger.setCountryName("India");

			passenger.setEmail(orderCustomer.getEmail());
			passenger.setGender(gendermap.get(flightOrderCustomer.getGender()));
			if(flightOrderCustomer.getPassportNo() == null || flightOrderCustomer.getPassportNo().equalsIgnoreCase(""))
				passenger.setPassportNo("");
			else
				passenger.setPassportNo(flightOrderCustomer.getPassportNo());


			if(flightOrderCustomer.getPassportNo() == null || flightOrderCustomer.getPassportNo().equalsIgnoreCase("") ){
				passenger.setPassportExpiry("");				
			}else{
				String PassportExpiryDate = sdf.format(flightOrderCustomer.getPassportExpiryDate());
				PassportExpiryDate = PassportExpiryDate+"T00:00:00";
				passenger.setPassportExpiry(PassportExpiryDate);
			}
			passenger.setFFAirline("");
			passenger.setFFNumber("");

			BookRequestFare fare = new BookRequestFare();
			fare.setBaseFare(tboFlightAirpriceResponse.getResponse().getResults().getFare().getBaseFare());
			fare.setTax(tboFlightAirpriceResponse.getResponse().getResults().getFare().getTax());
			fare.setYQTax(tboFlightAirpriceResponse.getResponse().getResults().getFare().getYQTax());
			fare.setAirTransFee(tboFlightAirpriceResponse.getResponse().getResults().getFare().getTransactionFee());
			fare.setAdditionalTxnFeeOfrd(tboFlightAirpriceResponse.getResponse().getResults().getFare().getAdditionalTxnFeeOfrd());
			fare.setAdditionalTxnFeePub(tboFlightAirpriceResponse.getResponse().getResults().getFare().getAdditionalTxnFeePub());
			if(tboFlightAirpriceResponse.getResponse().getResults().getFare().getAdditionalTxnFee() == null || tboFlightAirpriceResponse.getResponse().getResults().getFare().getAdditionalTxnFee().compareTo(BigDecimal.ZERO) == 0)
				fare.setAdditionalTxnFee(new BigDecimal("0.0"));
			else
				fare.setAdditionalTxnFee(tboFlightAirpriceResponse.getResponse().getResults().getFare().getAdditionalTxnFee());


			passenger.setFare(fare);
			Meal meal = null;
			Seat seat = null;

			if(!isSpecial &&  flightPriceResponse.getSpecialServiceRequest() !=null ){
				if(flightCustomerDetails.getPassengerdetailsList().get(count).getSeatcode()!= null && flightCustomerDetails.getPassengerdetailsList().get(count).getSeatcode() != ""){
					for (Seat seattype : flightPriceResponse.getSpecialServiceRequest().getSeatPreference()) {
						if(flightCustomerDetails.getPassengerdetailsList().get(count).getSeatcode().equalsIgnoreCase(seattype.getCode())){
							seat = new Seat();
							seat.setCode(seattype.getCode());
							seat.setDescription(seattype.getDescription());
							flightCustomerDetails.getPassengerdetailsList().get(count).setSeatcode(seattype.getCode());
							flightCustomerDetails.getPassengerdetailsList().get(count).setSeatname(seattype.getDescription());
						}
					}
				}
				if(flightCustomerDetails.getPassengerdetailsList().get(count).getMealcode() != null && flightCustomerDetails.getPassengerdetailsList().get(count).getMealcode() != ""){

					for (Meal mealtype : flightPriceResponse.getSpecialServiceRequest().getMeal()) {
						if(flightCustomerDetails.getPassengerdetailsList().get(count).getMealcode().equalsIgnoreCase(mealtype.getCode())){
							meal = new Meal();
							meal.setCode(mealtype.getCode());
							meal.setDescription(mealtype.getDescription());
							flightCustomerDetails.getPassengerdetailsList().get(count).setMealcode(mealtype.getCode());
							flightCustomerDetails.getPassengerdetailsList().get(count).setMealname(mealtype.getDescription());
						}
					}
				}
			}
			if(isSpecial &&  flightPriceResponse.getReturnspecialServiceRequest() !=null){
				if(flightCustomerDetails.getPassengerdetailsList().get(count).getReturnseatcode()!= null && flightCustomerDetails.getPassengerdetailsList().get(count).getReturnseatcode() != ""){
					for (Seat seattype : flightPriceResponse.getReturnspecialServiceRequest().getSeatPreference()) {
						if(flightCustomerDetails.getPassengerdetailsList().get(count).getReturnseatcode().equalsIgnoreCase(seattype.getCode())){
							seat = new Seat();
							seat.setCode(seattype.getCode());
							seat.setDescription(seattype.getDescription());
							flightCustomerDetails.getPassengerdetailsList().get(count).setReturnseatcode(seattype.getCode());
							flightCustomerDetails.getPassengerdetailsList().get(count).setReturnseatname(seattype.getDescription());
						}
					}
				}
				if(flightCustomerDetails.getPassengerdetailsList().get(count).getReturnmealcode() != null && flightCustomerDetails.getPassengerdetailsList().get(count).getReturnmealcode() != ""){

					for (Meal mealtype : flightPriceResponse.getReturnspecialServiceRequest().getMeal()) {
						if(flightCustomerDetails.getPassengerdetailsList().get(count).getReturnmealcode().equalsIgnoreCase(mealtype.getCode())){
							meal = new Meal();
							meal.setCode(mealtype.getCode());
							meal.setDescription(mealtype.getDescription());
							flightCustomerDetails.getPassengerdetailsList().get(count).setReturnmealcode(mealtype.getCode());
							flightCustomerDetails.getPassengerdetailsList().get(count).setReturnmealname(mealtype.getDescription());
						}
					}
				}
			}
			if(!flightPriceResponse.getFlightsearch().isSpecialSearch() &&  flightPriceResponse.getFlightsearch().getTripType().equalsIgnoreCase("R") && flightPriceResponse.getSpecialServiceRequest() !=null  && flightPriceResponse.getReturnspecialServiceRequest() !=null){
				if(flightPriceResponse.getReturnspecialServiceRequest() !=null ){
					if(flightCustomerDetails.getPassengerdetailsList().get(count).getReturnseatcode()!= null && flightCustomerDetails.getPassengerdetailsList().get(count).getReturnseatcode() != ""){
						for (Seat seattype : flightPriceResponse.getReturnspecialServiceRequest().getSeatPreference()) {
							if(flightCustomerDetails.getPassengerdetailsList().get(count).getReturnseatcode().equalsIgnoreCase(seattype.getCode())){
								seat = new Seat();
								seat.setCode(seattype.getCode());
								seat.setDescription(seattype.getDescription());
								flightCustomerDetails.getPassengerdetailsList().get(count).setReturnseatcode(seattype.getCode());
								flightCustomerDetails.getPassengerdetailsList().get(count).setReturnseatname(seattype.getDescription());
							}
						}
					}
					if(flightCustomerDetails.getPassengerdetailsList().get(count).getReturnmealcode() != null && flightCustomerDetails.getPassengerdetailsList().get(count).getReturnmealcode() != ""){
						for (Meal mealtype : flightPriceResponse.getReturnspecialServiceRequest().getMeal()) {
							if(flightCustomerDetails.getPassengerdetailsList().get(count).getReturnmealcode().equalsIgnoreCase(mealtype.getCode())){
								meal = new Meal();
								meal.setCode(mealtype.getCode());
								meal.setDescription(mealtype.getDescription());
								flightCustomerDetails.getPassengerdetailsList().get(count).setReturnmealcode(mealtype.getCode());
								flightCustomerDetails.getPassengerdetailsList().get(count).setReturnmealname(mealtype.getDescription());
							}
						}
					}
				}
				if(flightPriceResponse.getSpecialServiceRequest() !=null){
					if(flightCustomerDetails.getPassengerdetailsList().get(count).getSeatcode()!= null && flightCustomerDetails.getPassengerdetailsList().get(count).getSeatcode() != ""){
						for (Seat seattype : flightPriceResponse.getSpecialServiceRequest().getSeatPreference()) {
							if(flightCustomerDetails.getPassengerdetailsList().get(count).getSeatcode().equalsIgnoreCase(seattype.getCode())){
								seat = new Seat();
								seat.setCode(seattype.getCode());
								seat.setDescription(seattype.getDescription());
								flightCustomerDetails.getPassengerdetailsList().get(count).setSeatcode(seattype.getCode());
								flightCustomerDetails.getPassengerdetailsList().get(count).setSeatname(seattype.getDescription());
							}
						}
					}
					if(flightCustomerDetails.getPassengerdetailsList().get(count).getMealcode() != null && flightCustomerDetails.getPassengerdetailsList().get(count).getMealcode() != ""){

						for (Meal mealtype : flightPriceResponse.getSpecialServiceRequest().getMeal()) {
							if(flightCustomerDetails.getPassengerdetailsList().get(count).getMealcode().equalsIgnoreCase(mealtype.getCode())){
								meal = new Meal();
								meal.setCode(mealtype.getCode());
								meal.setDescription(mealtype.getDescription());
								flightCustomerDetails.getPassengerdetailsList().get(count).setMealcode(mealtype.getCode());
								flightCustomerDetails.getPassengerdetailsList().get(count).setMealname(mealtype.getDescription());
							}
						}
					}
				}
			}
			passenger.setMeal(meal);
			passenger.setSeat(seat);
			passenger.setgSTCompanyAddress(TayyarahGSTDetails.getGstValue("GSTCompanyAddress"));
			passenger.setgSTCompanyContactNumber(TayyarahGSTDetails.getGstValue("GSTCompanyContactNumber"));
			passenger.setgSTCompanyEmail(TayyarahGSTDetails.getGstValue("GSTCompanyEmail"));
			passenger.setgSTCompanyName(TayyarahGSTDetails.getGstValue("GSTCompanyName"));
			passenger.setgSTNumber(TayyarahGSTDetails.getGstValue("GSTNumber"));	
			passengers.add(passenger);
			count++;
		}
		bookrequest.setPassengers(passengers);
		return bookrequest;
	}

	/* Method For Create Ticket Request For NON LCC Carrier */
	public NonLCCTicketRequest createFlightNonLccTicketRequestbuilder(TboBookResponse tbononLCCBookResponse,TboFlightConfig tboFlightConfig) throws ParseException{
		NonLCCTicketRequest Ticketrequest = new NonLCCTicketRequest();
		Ticketrequest.setEndUserIp(tboFlightConfig.getEnduserip());
		Ticketrequest.setTokenId(TboGetToken.getToken(true,tboFlightConfig));
		Ticketrequest.setTraceId(tbononLCCBookResponse.getResponse().getTraceId());
		Ticketrequest.setPNR(tbononLCCBookResponse.getResponse().getResponse().getPNR());
		Ticketrequest.setBookingId(tbononLCCBookResponse.getResponse().getResponse().getBookingId());
		return Ticketrequest;
	}

	/* Method For Create Ticket Request For NON LCC Carrier After Hold */
	public NonLCCTicketRequest createFlightNonLccTicketAfterHoldRequestbuilder(FlightOrderRow flightOrderRow,TboFlightConfig tboFlightConfig) throws ParseException{
		NonLCCTicketRequest Ticketrequest = new NonLCCTicketRequest();
		Ticketrequest.setEndUserIp(tboFlightConfig.getEnduserip());
		Ticketrequest.setTokenId(TboGetToken.getToken(true,tboFlightConfig));
		Ticketrequest.setTraceId(flightOrderRow.getApitraceid());
		Ticketrequest.setPNR(flightOrderRow.getPnr());
		Ticketrequest.setBookingId(Integer.parseInt(flightOrderRow.getApi_commit()));
		return Ticketrequest;
	}

	public GetBookingDetailsRequest createGetBookingDetailsRequestbuilder(String pnr,String bookingid,TboFlightConfig tboFlightConfig) throws ParseException{
		GetBookingDetailsRequest getBookingDetailsRequest = new GetBookingDetailsRequest();
		getBookingDetailsRequest.setEndUserIp(tboFlightConfig.getEnduserip());
		getBookingDetailsRequest.setTokenId(TboGetToken.getToken(true,tboFlightConfig));
		getBookingDetailsRequest.setPNR(pnr);
		getBookingDetailsRequest.setBookingId(Integer.valueOf(bookingid));
		return getBookingDetailsRequest;
	}

	public TboReleasePNRRequest createReleasePNRRequestbuilder(TboBookingDetailsResponse tboBookingDetailsResponse,TboFlightConfig tboFlightConfig) throws ParseException{
		TboReleasePNRRequest tboReleasePNRRequest = new TboReleasePNRRequest();
		tboReleasePNRRequest.setEndUserIp(tboFlightConfig.getEnduserip());
		tboReleasePNRRequest.setTokenId(TboGetToken.getToken(true,tboFlightConfig));
		tboReleasePNRRequest.setSource(String.valueOf(tboBookingDetailsResponse.getResponse().getFlightItinerary().getSource()));
		tboReleasePNRRequest.setBookingId(tboBookingDetailsResponse.getResponse().getFlightItinerary().getBookingId());
		return tboReleasePNRRequest;
	}

	public TboCancelTicketRequest createCancellationRequestbuilder(FlightOrderRow flightOrderRow,FlightCancelRequest flightCancelRequest,List<FlightOrderCustomer> flightOrderCustomers,TboFlightConfig tboFlightConfig) throws ParseException{
		TboCancelTicketRequest tboCancelTicketRequest = new TboCancelTicketRequest(Integer.parseInt(flightOrderRow.getApi_commit()),Integer.parseInt(flightCancelRequest.getRequesttype()),Integer.parseInt(flightCancelRequest.getCancellationtype()),"",tboFlightConfig.getEnduserip(),TboGetToken.getToken(false,tboFlightConfig),null,null);

		tboCancelTicketRequest.setEndUserIp(tboFlightConfig.getEnduserip());
		tboCancelTicketRequest.setTokenId(TboGetToken.getToken(true,tboFlightConfig));
		tboCancelTicketRequest.setBookingId(Integer.parseInt(flightOrderRow.getApi_commit()));

		if(flightCancelRequest.getCancellationtype().equalsIgnoreCase(FlightCancelRequest.CancellationType_NotSet))
			tboCancelTicketRequest.setCancellationType(Integer.parseInt(FlightCancelRequest.CancellationType_NotSet));
		if(flightCancelRequest.getCancellationtype().equalsIgnoreCase(FlightCancelRequest.CancellationType_NoShow))
			tboCancelTicketRequest.setCancellationType(Integer.parseInt(FlightCancelRequest.CancellationType_NoShow));
		if(flightCancelRequest.getCancellationtype().equalsIgnoreCase(FlightCancelRequest.CancellationType_FlightCancelled))
			tboCancelTicketRequest.setCancellationType(Integer.parseInt(FlightCancelRequest.CancellationType_FlightCancelled));
		if(flightCancelRequest.getCancellationtype().equalsIgnoreCase(FlightCancelRequest.CancellationType_Others))
			tboCancelTicketRequest.setCancellationType(Integer.parseInt(FlightCancelRequest.CancellationType_Others));


		if(flightCancelRequest.getRequesttype().equalsIgnoreCase(FlightCancelRequest.RequestType_NotSet))
			tboCancelTicketRequest.setRequestType(Integer.parseInt(FlightCancelRequest.RequestType_NotSet));
		if(flightCancelRequest.getRequesttype().equalsIgnoreCase(FlightCancelRequest.RequestType_PartialCancellation))
			tboCancelTicketRequest.setRequestType(Integer.parseInt(FlightCancelRequest.RequestType_PartialCancellation));
		if(flightCancelRequest.getRequesttype().equalsIgnoreCase(FlightCancelRequest.RequestType_Reissuance))
			tboCancelTicketRequest.setRequestType(Integer.parseInt(FlightCancelRequest.RequestType_Reissuance));
		if(flightCancelRequest.getRequesttype().equalsIgnoreCase(FlightCancelRequest.RequestType_FullCancellation)){
			tboCancelTicketRequest.setRequestType(Integer.parseInt(FlightCancelRequest.RequestType_FullCancellation));
		}
		if(flightCancelRequest.getRequesttype().equalsIgnoreCase(FlightCancelRequest.RequestType_PartialCancellation)){
			List<Integer> TicketIdlist = new ArrayList<Integer>();
			for(int i = 0; i < flightOrderCustomers.size(); i++){
				FlightOrderCustomer Customer = flightOrderCustomers.get(i);
				TicketIdlist.add(Integer.parseInt(Customer.getEticketid()));
			}
			tboCancelTicketRequest.setSectors(flightCancelRequest.getSectors());
			tboCancelTicketRequest.setTicketId(TicketIdlist);
		}
		tboCancelTicketRequest.setRemarks(flightCancelRequest.getRemarks());
		return tboCancelTicketRequest;
	}

	public TboCancellationStatusRequest createCancellationStatusRequestbuilder(String changerequestid,TboFlightConfig tboFlightConfig) throws ParseException{
		TboCancellationStatusRequest tboCancellationStatusRequest = new TboCancellationStatusRequest();
		tboCancellationStatusRequest.setEndUserIp(tboFlightConfig.getEnduserip());
		tboCancellationStatusRequest.setTokenId(TboGetToken.getToken(true,tboFlightConfig));
		tboCancellationStatusRequest.setChangeRequestId(changerequestid);
		return tboCancellationStatusRequest;
	}

	public TboPriceBDRequest createPriceBDRequestbuilder(Flightsearch flightsearch,TboFlightSearchResponse tboFlightSearchResponse,TboFlightConfig tboFlightConfig) throws ParseException{
		TboPriceBDRequest tboPriceBDRequest = new TboPriceBDRequest();
		
		tboPriceBDRequest.setEndUserIp(tboFlightConfig.getEnduserip());
		tboPriceBDRequest.setTokenId(TboGetToken.getToken(true,tboFlightConfig));
		tboPriceBDRequest.setAdultCount(String.valueOf(flightsearch.getAdult()));
		tboPriceBDRequest.setChildCount(String.valueOf(flightsearch.getKid()));
		tboPriceBDRequest.setInfantCount(String.valueOf(flightsearch.getInfant()));
		tboPriceBDRequest.setTraceId(tboFlightSearchResponse.getResponse().getTraceId());

		AirSearchResult airSearchResult = new AirSearchResult();
		airSearchResult.setAirlineRemark(tboFlightSearchResponse.getResponse().getResults().get(0).get(0).getAirlineRemark());
		airSearchResult.setIsLCC(tboFlightSearchResponse.getResponse().getResults().get(0).get(0).getIsLCC());
		airSearchResult.setIsRefundable(tboFlightSearchResponse.getResponse().getResults().get(0).get(0).getIsRefundable());
		airSearchResult.setResultIndex(tboFlightSearchResponse.getResponse().getResults().get(0).get(0).getResultIndex());
		airSearchResult.setSource(tboFlightSearchResponse.getResponse().getResults().get(0).get(0).getSource());
		List<List<PriceBDRequestSegment>> ListPriceBDRequestSegment = new ArrayList<List<PriceBDRequestSegment>>();
		List<PriceBDRequestSegment> priceBDRequestSegmentlist = new ArrayList<PriceBDRequestSegment>();
		for (List<FlightSearchrresponseSegment> flightSearchrresponseSegment : tboFlightSearchResponse.getResponse().getResults().get(0).get(0).getSegments()) {
			for (FlightSearchrresponseSegment segment : flightSearchrresponseSegment) {
				PriceBDRequestSegment priceBDRequestSegment = new PriceBDRequestSegment();
				
				Airline airline = new Airline();
				airline.setAirlineCode(segment.getAirline().getAirlineCode());
				airline.setAirlineName(segment.getAirline().getAirlineName());
				airline.setFareClass(segment.getAirline().getFareClass());
				airline.setFlightNumber(segment.getAirline().getFlightNumber());
				airline.setOperatingCarrier(segment.getAirline().getOperatingCarrier());

				priceBDRequestSegment.setAirline(airline);
				priceBDRequestSegment.setSegmentIndicator(segment.getSegmentIndicator());
				priceBDRequestSegment.setTripIndicator(segment.getTripIndicator());
				priceBDRequestSegmentlist.add(priceBDRequestSegment);
			}
		}
		ListPriceBDRequestSegment.add(priceBDRequestSegmentlist);
		airSearchResult.setSegments(ListPriceBDRequestSegment);
		List<AirSearchResult> airSearchResultlist = new ArrayList<AirSearchResult>();
		airSearchResultlist.add(airSearchResult);
		tboPriceBDRequest.setAirSearchResult(airSearchResultlist);
		return tboPriceBDRequest;
	}
	
	public TboAgencyBalanceRequest createBalanceRequest(TboFlightConfig tboFlightConfig){
		TboAgencyBalanceRequest balanceRequest = new TboAgencyBalanceRequest();
		try{
			AuthenticateResponse authResponse = TboGetToken.getAuthResponse(tboFlightConfig);
			balanceRequest.setClientId(tboFlightConfig.getClientid());
			balanceRequest.setEndUserIp(tboFlightConfig.getEnduserip());
			balanceRequest.setTokenId(authResponse.getTokenId());
			balanceRequest.setTokenMemberId(authResponse.getMember().getMemberId());
			balanceRequest.setTokenAgencyId(authResponse.getMember().getAgencyId());

		}catch(Exception e){

		}
		return balanceRequest;
	}

}
