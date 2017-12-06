package com.tayyarah.flight.service.db;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import com.tayyarah.api.flight.tbo.model.TboFlightAirpriceResponse;
import com.tayyarah.common.entity.Airport;
import com.tayyarah.common.entity.PaymentTransaction;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.exception.ErrorMessages;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.flight.cache.entity.FlightSearchCache;
import com.tayyarah.flight.dao.AirportDAO;
import com.tayyarah.flight.dao.FlightBookingDao;
import com.tayyarah.flight.dao.FlightTempAirSegmentDAO;
import com.tayyarah.flight.entity.FlightAirPriceDetailsTemp;
import com.tayyarah.flight.entity.FlightApiSearchResponseTemp;
import com.tayyarah.flight.entity.FlightBookingDetailsTemp;
import com.tayyarah.flight.entity.FlightBookingKeysTemp;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.flight.entity.FlightSearchDetailsTemp;
import com.tayyarah.flight.exception.FlightErrorMessages;
import com.tayyarah.flight.exception.FlightException;
import com.tayyarah.flight.model.FlightPriceResponse;
import com.tayyarah.flight.model.Flightsearch;
import com.tayyarah.flight.model.TempAirSegment;
import com.tayyarah.flight.model.UAPISearchFlightKeyMap;
import com.tayyarah.user.entity.WalletAmountTranferHistory;
import com.travelport.api_v33.AirResponse.TypeBaseAirSegment;


public class FlightDataBaseServices {
	static final Logger logger = Logger.getLogger(FlightDataBaseServices.class);

	public void isInternational(Flightsearch flightsearch, Map<String, Airport> airportMap,CompanyDao cdao) {
		boolean isInternational = false;
		Set<String> countrySet = new HashSet<String>();

		if(flightsearch.getTripType().equalsIgnoreCase("M")){
			if(flightsearch.getTrips().equalsIgnoreCase("2")){
				try {
					countrySet.add(getCountry(airportMap,flightsearch.getMFS().getDest1()));
					countrySet.add(getCountry(airportMap,flightsearch.getMFS().getDest2()));
					countrySet.add(getCountry(airportMap,flightsearch.getMFS().getOrigin1()));
					countrySet.add(getCountry(airportMap,flightsearch.getMFS().getOrigin2()));

				} catch (HibernateException e) {
					logger.error("HibernateException ",e);
					throw new FlightException(ErrorCodeCustomerEnum.HibernateException,FlightErrorMessages.NO_AIPRICE);
				}
				catch (Exception e) {logger.error("Exception ",e);
				throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIPRICE);
				}

			}else if(flightsearch.getTrips().equalsIgnoreCase("3")){
				try {
					countrySet.add(getCountry(airportMap,flightsearch.getMFS().getDest1()));
					countrySet.add(getCountry(airportMap,flightsearch.getMFS().getDest2()));
					countrySet.add(getCountry(airportMap,flightsearch.getMFS().getDest3()));
					countrySet.add(getCountry(airportMap,flightsearch.getMFS().getOrigin1()));
					countrySet.add(getCountry(airportMap,flightsearch.getMFS().getOrigin2()));
					countrySet.add(getCountry(airportMap,flightsearch.getMFS().getOrigin3()));

				} catch (HibernateException e) {
					logger.error("HibernateException ",e);
					throw new FlightException(ErrorCodeCustomerEnum.HibernateException,FlightErrorMessages.NO_AIPRICE);
				}
				catch (Exception e) {logger.error("Exception ",e);
				throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIPRICE);
				}
			}else if(flightsearch.getTrips().equalsIgnoreCase("4")){
				try {
					countrySet.add(getCountry(airportMap,flightsearch.getMFS().getDest1()));
					countrySet.add(getCountry(airportMap,flightsearch.getMFS().getDest2()));
					countrySet.add(getCountry(airportMap,flightsearch.getMFS().getDest3()));
					countrySet.add(getCountry(airportMap,flightsearch.getMFS().getDest4()));
					countrySet.add(getCountry(airportMap,flightsearch.getMFS().getOrigin1()));
					countrySet.add(getCountry(airportMap,flightsearch.getMFS().getOrigin2()));
					countrySet.add(getCountry(airportMap,flightsearch.getMFS().getOrigin3()));
					countrySet.add(getCountry(airportMap,flightsearch.getMFS().getOrigin4()));

				} catch (HibernateException e) {
					logger.error("HibernateException ",e);
					throw new FlightException(ErrorCodeCustomerEnum.HibernateException,FlightErrorMessages.NO_AIPRICE);
				}
				catch (Exception e) {logger.error("Exception ",e);
				throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIPRICE);
				}
			}
		}
		else{
			try {
				countrySet.add(getCountry(airportMap,flightsearch.getOrigin()));
				countrySet.add(getCountry(airportMap,flightsearch.getDestination()));

			} catch (HibernateException e) {
				logger.error("HibernateException ",e);
				throw new FlightException(ErrorCodeCustomerEnum.HibernateException,FlightErrorMessages.NO_AIPRICE);
			}
			catch (Exception e) {logger.error("Exception ",e);
			throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIPRICE);
			}
		}
		flightsearch.setCountry("ALL");
		try{
			if(countrySet.size()>1){
				isInternational=true;
				flightsearch.setFlightType("International");
			}else if(countrySet.size()==1){
				try {
					String biilingcountry=cdao.getBillingCoyuntry();
					if(biilingcountry.equalsIgnoreCase(countrySet.iterator().next())){
						flightsearch.setFlightType("Domestic");
					}else{
						flightsearch.setFlightType("International");
					}
				} catch (Exception e) {					
					logger.error("Exception ",e);
				}
			}
		}catch (Exception e) {
			logger.error("Exception ",e);
			throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIPRICE);
		}
		if(isInternational)		
			flightsearch.setDomestic(false);		
		else		
			flightsearch.setDomestic(true);

		flightsearch.setIsInternational(isInternational);
	}

	private String getCountry(Map<String, Airport> airportMap, String data) {
		if(airportMap!=null && airportMap.get(data)!=null)
			return airportMap.get(data).getCountry();
		return data;
	}

	public synchronized  void storeTempAirSegment(TypeBaseAirSegment typeBaseAirSegment, String ID, FlightTempAirSegmentDAO tempDAO) {
		TempAirSegment TAS = new TempAirSegment();
		TAS.setGroup(typeBaseAirSegment.getGroup());
		TAS.setFlightindex(ID);
		TAS.setCarrier(typeBaseAirSegment.getCarrier());
		TAS.setKey(typeBaseAirSegment.getKey());
		TAS.setFlightNumber(typeBaseAirSegment.getFlightNumber());
		TAS.setAvailabilitySource(typeBaseAirSegment.getAvailabilitySource());
		TAS.setAvailabilityDisplayType("dfdfd");
		TAS.setOrigin(typeBaseAirSegment.getOrigin());
		TAS.setDestination(typeBaseAirSegment.getDestination());
		TAS.setDepartureTime(typeBaseAirSegment.getDepartureTime());
		TAS.setArrivalTime(typeBaseAirSegment.getArrivalTime());
		TAS.setDistance(typeBaseAirSegment.getDistance());
		TAS.setFlightTime(typeBaseAirSegment.getFlightTime());

		try {
			tempDAO.InsertTAS(TAS);
		} catch (HibernateException e) {
			logger.error("Exception ",e);			
		}catch (NumberFormatException e) {
			logger.error("NumberFormatException ",e);		
		}
		catch (Exception e) {
			logger.error("Exception ",e);		
		}
	}

	public  void insertKeys(String transaction_key,String searchkey, FlightTempAirSegmentDAO tempDAO) {		
		FlightBookingKeysTemp flightBookingKeys=new FlightBookingKeysTemp();
		flightBookingKeys.setSearch_key(searchkey);
		flightBookingKeys.setTransaction_key(transaction_key);
		flightBookingKeys.setActive(true);
		try {
			tempDAO.insertKeys(flightBookingKeys);
		} catch (HibernateException e) {
			logger.error("HibernateException ",e);			
		}catch (NumberFormatException e) {
			logger.error("NumberFormatException ",e);		
		}
		catch (Exception e) {
			logger.error("Exception ",e);			
		}
	}

	public  void updateKeys(String transaction_key,String searchkey, FlightTempAirSegmentDAO tempDAO) {		
		FlightBookingKeysTemp flightBookingKeys=new FlightBookingKeysTemp();
		flightBookingKeys.setSearch_key(searchkey);
		flightBookingKeys.setTransaction_key(transaction_key);
		flightBookingKeys.setActive(true);
		try {
			tempDAO.insertKeys(flightBookingKeys);
		} catch (HibernateException e) {
			logger.error("HibernateException ",e);			
		}catch (NumberFormatException e) {
			logger.error("NumberFormatException ",e);		
		}
		catch (Exception e) {
			logger.error("Exception ",e);			
		}
	}

	public  void insertPriceKey(String transaction_key,String price_key, FlightTempAirSegmentDAO tempDAO) {
		try {
			tempDAO.updatePriceKey(transaction_key,price_key);
		} catch (HibernateException e) {
			logger.error("HibernateException ",e);			
		}catch (NumberFormatException e) {
			logger.error("NumberFormatException ",e);			
		}
		catch (Exception e) {
			logger.error("Exception ",e);			
		}
	}

	public synchronized  void insertwalletamount_tranfer_history(WalletAmountTranferHistory walletAmountTranferHistory,FlightBookingDao FBDAO) {
		try {
			FBDAO.insertwalletamount_tranfer_history(walletAmountTranferHistory);
		} catch (HibernateException e) {
			logger.error("HibernateException ",e);		
		}catch (NumberFormatException e) {
			logger.error("NumberFormatException ",e);		
		}
		catch (Exception e) {
			logger.error("Exception ",e);			
		}
	}

	public synchronized  void updatePNR(String pnr,String orderID,FlightBookingDao FBDAO) {
		try {
			FBDAO.updatePNR(pnr,orderID);
		} catch (HibernateException e) {
			logger.error("HibernateException ",e);		
		}catch (NumberFormatException e) {
			logger.error("NumberFormatException ",e);			
		}
		catch (Exception e) {
			logger.error("Exception ",e);		
		}
	}

	public synchronized  void updatePNRandWallet(String pnr,String orderID,FlightBookingDao FBDAO, String refNO) {
		try {
			FBDAO.updatePNRandWallet(pnr,orderID,refNO);
		} catch (HibernateException e) {
			logger.error("HibernateException ",e);			
		}catch (NumberFormatException e) {
			logger.error("NumberFormatException ",e);			
		}
		catch (Exception e) {
			logger.error("Exception ",e);			
		}
	}

	public synchronized  FlightOrderRow updatePNRandWalletTBO(String pnr,String orderID,FlightBookingDao FBDAO, String refNO,BigDecimal suppierdiscount,BigDecimal systemdiscount,BigDecimal publisheddiscount,String apitraceid) {
		FlightOrderRow flightOrderRow = null;
		try {
			flightOrderRow = FBDAO.updatePNRandWalletTBO(pnr,orderID,refNO,suppierdiscount,systemdiscount,publisheddiscount,apitraceid);
		} catch (HibernateException e) {
			logger.error("HibernateException ",e);		
		}catch (NumberFormatException e) {
			logger.error("NumberFormatException ",e);			
		}
		catch (Exception e) {
			logger.error("Exception ",e);			
		}
		return flightOrderRow;
	}

	public synchronized  void updateHoldPNRTBO(String pnr,String orderID,FlightBookingDao FBDAO, String refNO,BigDecimal suppierdiscount,BigDecimal systemdiscount,BigDecimal publisheddiscount,String apitraceid,String statusaction) {
		try {
			FBDAO.updateHoldPNRTBO(pnr,orderID,refNO,suppierdiscount,systemdiscount,publisheddiscount,apitraceid,statusaction);
		} catch (HibernateException e) {
			logger.error("HibernateException ",e);			
		}catch (NumberFormatException e) {
			logger.error("NumberFormatException ",e);		
		}
		catch (Exception e) {
			logger.error("Exception ",e);		
		}
	}

	public  void updateWalletBalanceIfFailed(BigDecimal amount,int walletId,FlightBookingDao FBDAO,WalletAmountTranferHistory AWTH) {
		try {
			FBDAO.updateWalletBalanceIfFailed(amount,walletId,AWTH, 1);
		} catch (HibernateException e) {
			logger.error("HibernateException ",e);

		}catch (NumberFormatException e) {
			logger.error("NumberFormatException ",e);			
		}
		catch (Exception e) {
			logger.error("Exception ",e);			
		}
	}
	public  void updatePaymentStatus(PaymentTransaction paymentTransaction, FlightBookingDao FBDAO) {
		try {
			FBDAO.updatePaymentStatus(paymentTransaction);
		} catch (HibernateException e) {
			logger.error("Exception ",e);

		}catch (NumberFormatException e) {
			logger.error("Exception ",e);			
		}
		catch (Exception e) {
			logger.error("Exception ",e);		
		}
	}

	public  void insertPaymentTransaction(PaymentTransaction paymentTransaction, FlightBookingDao FBDAO) {
		try {
			FBDAO.insertPaymentTransactionDetails(paymentTransaction);
			logger.error("insertPaymentTransaction is done...... ");
		} catch (HibernateException e) {
			logger.error("Exception ",e);			
		}catch (NumberFormatException e) {
			logger.error("Exception ",e);			
		}
		catch (Exception e) {
			logger.error("Exception ",e);			
		}
	}

	public  void storeSearchFlightKeyMap(UAPISearchFlightKeyMap uapiSearchFlightKeyMap,String transaction_key,String searchkey, FlightTempAirSegmentDAO tempDAO) {
		FlightSearchDetailsTemp searcdetails = new FlightSearchDetailsTemp();
		byte[] uapiSearch = null;
		try {
			uapiSearch = convertObjectToUAPISearchFlightKeyMap(uapiSearchFlightKeyMap);
		} catch (IOException e) {
			logger.error("HibernateException ",e);
		}
		searcdetails.setUapiSearchFlightKeyMap(uapiSearch);
		searcdetails.setDatetime(new Date());
		searcdetails.setSearchkey(searchkey);
		searcdetails.setTransactionkey(transaction_key);
		try {
			tempDAO.InsertAll(searcdetails);
		} catch (HibernateException e) {
			logger.error("HibernateException ",e);
		}catch (NumberFormatException e) {
			logger.error("NumberFormatException ",e);
		}
		catch (Exception e) {
			logger.error("Exception ",e);			
		}
	}

	public  void UpdateSearchFlightKeyMap(UAPISearchFlightKeyMap uapiSearchFlightKeyMap,String transaction_key,String searchkey, FlightTempAirSegmentDAO tempDAO) {
		FlightSearchDetailsTemp searcdetails = new FlightSearchDetailsTemp();
		byte[] uapiSearch = null;
		try {
			uapiSearch = convertObjectToUAPISearchFlightKeyMap(uapiSearchFlightKeyMap);
		} catch (IOException e) {
			logger.error("HibernateException ",e);
		}
		searcdetails.setUapiSearchFlightKeyMap(uapiSearch);
		searcdetails.setDatetime(new Date());
		searcdetails.setSearchkey(searchkey);
		searcdetails.setTransactionkey(transaction_key);
		try {
			tempDAO.UpdateFlightSearchDetailsTempAll(searcdetails);
		} catch (HibernateException e) {
			logger.error("HibernateException ",e);
		}catch (NumberFormatException e) {
			logger.error("NumberFormatException ",e);
		}
		catch (Exception e) {
			logger.error("Exception ",e);			
		}
	}

	public  void storeFlightSearchApiResponses(String searchkey,byte[] FlightSearchResponse,FlightTempAirSegmentDAO tempDAO,Flightsearch flightsearch,String apiProvider) {
		FlightApiSearchResponseTemp searchDetails = new FlightApiSearchResponseTemp();		
		byte[] FSRequest = null;
		try {
			FSRequest = convertObjectToByteArray(flightsearch);
		} catch (IOException e1) {			
			logger.error("IOException ",e1);
		}
		searchDetails.setSearchRequest(FSRequest);
		searchDetails.setSearchResponse(FlightSearchResponse);
		searchDetails.setDateTime(new Date());
		searchDetails.setSearchKey(searchkey);
		searchDetails.setProductType("Flight");
		searchDetails.setApiprovider(apiProvider);
		try {
			tempDAO.InsertApiResponse(searchDetails);
		} catch (HibernateException e) {
			logger.error("HibernateException ",e);
		}catch (NumberFormatException e) {
			logger.error("NumberFormatException ",e);
		}
		catch (Exception e) {
			logger.error("Exception ",e);			
		}
	}

	public  void storeAirPriceDetails(FlightPriceResponse flightPriceResponse,String pricekey,Flightsearch flightsearch, FlightTempAirSegmentDAO tempDAO) {
		FlightAirPriceDetailsTemp airPriceDetails = new FlightAirPriceDetailsTemp();
		byte[] FPR = null;
		try {
			FPR = convertObjectToByteArray(flightPriceResponse);
		} catch (IOException e1) {			
			logger.error("IOException ",e1);
		}
		airPriceDetails.setFlightPriceResponse(FPR);
		airPriceDetails.setDatetime(new Date());
		airPriceDetails.setPrice_key(pricekey);
		try {
			tempDAO.InsertAirPriceDetails(airPriceDetails);
		} catch (HibernateException e) {			
			logger.error("HibernateException ",e);			
		}catch (NumberFormatException e) {			
			logger.error("NumberFormatException ",e);			
		}
		catch (Exception e) {			
			logger.error("Exception ",e);			
		}
	}


	public  void storeTBOAirPriceDetails(FlightPriceResponse flightPriceResponse,String pricekey,Flightsearch flightsearch, FlightTempAirSegmentDAO tempDAO,TboFlightAirpriceResponse tboFlightAirpriceResponse,TboFlightAirpriceResponse tboFlightAirpriceResponsespecial,String lowFareFlightIndex1,String lowFareFlightIndex2,String reason ,String lowFareFlightIndexReturn1,String lowFareFlightIndexReturn2,String reasonReturn) {
		FlightAirPriceDetailsTemp airPriceDetails = new FlightAirPriceDetailsTemp();
		byte[] FPR = null;
		try {
			FPR = convertObjectToByteArray(flightPriceResponse);
		} catch (IOException e1) {			
			logger.error("IOException ",e1);
		}
		byte[] TBOFPR = null;
		if(tboFlightAirpriceResponse!=null){
			try {
				TBOFPR = convertObjectToByteArray(tboFlightAirpriceResponse);
			} catch (IOException e1) {				
				logger.error("IOException ",e1);
			}
		}
		byte[] TBOFPRS = null;
		if(tboFlightAirpriceResponsespecial!=null){
			try {
				TBOFPRS = convertObjectToByteArray(tboFlightAirpriceResponsespecial);
			} catch (IOException e1) {				
				logger.error("IOException ",e1);
			}
		}
		airPriceDetails.setTBOFlightPriceResponse(TBOFPR);
		airPriceDetails.setTBOFlightPriceResponseSpecial(TBOFPRS);
		airPriceDetails.setFlightPriceResponse(FPR);
		airPriceDetails.setDatetime(new Date());
		airPriceDetails.setPrice_key(pricekey);

		if(!lowFareFlightIndex1.equalsIgnoreCase(""))
			airPriceDetails.setLowFareFlightIndex1(lowFareFlightIndex1);

		if(!lowFareFlightIndex2.equalsIgnoreCase(""))
			airPriceDetails.setLowFareFlightIndex2(lowFareFlightIndex2);

		if(!reason.equalsIgnoreCase(""))
			airPriceDetails.setReasonToSelect(reason);

		if(!lowFareFlightIndexReturn1.equalsIgnoreCase(""))
			airPriceDetails.setLowFareFlightIndexReturn1(lowFareFlightIndexReturn1);

		if(!lowFareFlightIndexReturn2.equalsIgnoreCase(""))
			airPriceDetails.setLowFareFlightIndexReturn1(lowFareFlightIndexReturn2);

		if(!reasonReturn.equalsIgnoreCase(""))
			airPriceDetails.setReasonToSelectReturn(reasonReturn);

		try {
			tempDAO.InsertAirPriceDetails(airPriceDetails);
		} catch (HibernateException e) {			
			logger.error("HibernateException ",e);			
		}catch (NumberFormatException e) {			
			logger.error("NumberFormatException ",e);			
		}
		catch (Exception e) {			
			logger.error("Exception ",e);			
		}
	}


	@SuppressWarnings("finally")
	public  TempAirSegment getTempAirSegment(String flightindex, FlightTempAirSegmentDAO tempDAO) {
		TempAirSegment TAS = new TempAirSegment();
		try {
			TAS=tempDAO.getTAS(flightindex);
		} catch (HibernateException e) {
			logger.error("HibernateException ",e);
		}catch (NumberFormatException e) {
			logger.error("NumberFormatException ",e);
		}
		catch (Exception e) {
			logger.error("Exception ",e);			
		}finally{
			return TAS;
		}
	}

	@SuppressWarnings("finally")
	public  FlightSearchDetailsTemp getUAPISearchFlightKeyMap(String searchkey, FlightTempAirSegmentDAO tempDAO) {
		FlightSearchDetailsTemp searchDetails=new FlightSearchDetailsTemp();
		try {
			searchDetails=tempDAO.getAll(searchkey);
		} catch (HibernateException e) {
			logger.error("HibernateException ",e);	
		}catch (NumberFormatException e) {
			logger.error("NumberFormatException ",e);
		}
		catch (Exception e) {
			logger.error("Exception ",e);			
		}finally{
			return searchDetails;
		}
	}

	public  FlightSearchDetailsTemp getUAPISearchFlightKeyMapUsingTransactionkey (String transactionkey, FlightTempAirSegmentDAO tempDAO) {
		FlightSearchDetailsTemp searchDetails =  null;
		try {
			searchDetails =  new FlightSearchDetailsTemp();
			searchDetails = tempDAO.getAllByTransactionKey(transactionkey);
		} catch (HibernateException e) {
			logger.error("HibernateException ",e);	
		}catch (NumberFormatException e) {
			logger.error("NumberFormatException ",e);
		}
		catch (Exception e) {
			logger.error("Exception ",e);			
		}
		return searchDetails;
	}

	@SuppressWarnings("finally")
	public  FlightAirPriceDetailsTemp getAirPriceDetails(String pricekey, FlightTempAirSegmentDAO tempDAO) {
		FlightAirPriceDetailsTemp airPriceDetails = new FlightAirPriceDetailsTemp();
		try {
			airPriceDetails = tempDAO.getAirPriceDetails(pricekey);
		} catch (HibernateException e) {
			logger.error("HibernateException ",e);
			throw new FlightException(ErrorCodeCustomerEnum.HibernateException,FlightErrorMessages.NO_FLIGHT);
		}catch (NumberFormatException e) {
			logger.error("NumberFormatException ",e);
			throw new FlightException(ErrorCodeCustomerEnum.NumberFormatException,ErrorMessages.INVALID_APPKEY);
		}
		catch (Exception e) {
			logger.error("Exception ",e);
			throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_FLIGHT);
		}finally{
			return airPriceDetails;
		}
	}

	@SuppressWarnings("finally")
	public  FlightBookingDetailsTemp getBookingDetailsToDb(String orderId, FlightBookingDao FBDAO) {
		FlightBookingDetailsTemp bookingDetailstodb=new FlightBookingDetailsTemp();
		try {
			bookingDetailstodb=FBDAO.getBookingDetailsToDb(orderId);
		} catch (HibernateException e) {
			logger.error("HibernateException ",e);
		}catch (NumberFormatException e) {
			logger.error("NumberFormatException ",e);
		}
		catch (Exception e) {
			logger.error("Exception ",e);			
		}finally{
			return bookingDetailstodb;
		}
	}

	public static byte[] convertObjectToUAPISearchFlightKeyMap(UAPISearchFlightKeyMap uapiSearchFlightKeyMap) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		objectOutputStream.writeObject(uapiSearchFlightKeyMap);
		return byteArrayOutputStream.toByteArray();
	}

	public static byte[] convertObjectToByteArray(Object object) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		objectOutputStream.writeObject(object);
		return byteArrayOutputStream.toByteArray();
	}

	public static Object  convertByteArrayToObject(byte[] byt) throws IOException {
		Object object = null;
		ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byt);
		ObjectInputStream objectInputStream= new ObjectInputStream(byteInputStream);
		try {
			object = objectInputStream.readObject();
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException ",e);
		}
		catch (Exception e) {
			logger.error("Exception ",e);
		}
		return object;
	}

	public static UAPISearchFlightKeyMap  convertByteArrayToUAPISearchFlightKeyMap(byte[] byt) throws IOException {
		UAPISearchFlightKeyMap uapiSearchFlightKeyMap=null;
		ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byt);
		ObjectInputStream objectInputStream= new ObjectInputStream(byteInputStream);
		try {
			uapiSearchFlightKeyMap= (UAPISearchFlightKeyMap)objectInputStream.readObject();
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException ",e);
		}
		catch (Exception e) {
			logger.error("Exception ",e);
		}
		return uapiSearchFlightKeyMap;
	}

	public  void insertFlightSearchCacheApiResponses(FlightSearchCache flightSearchCache,FlightTempAirSegmentDAO tempDAO) {	
		try {
			tempDAO.InsertFlightSearchCache(flightSearchCache);
		} catch (HibernateException e) {
			logger.error("HibernateException ",e);
		}catch (NumberFormatException e) {
			logger.error("NumberFormatException ",e);
		}
		catch (Exception e) {
			logger.error("Exception ",e);			
		}
	}
}
