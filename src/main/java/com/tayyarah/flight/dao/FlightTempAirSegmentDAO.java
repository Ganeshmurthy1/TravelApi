package com.tayyarah.flight.dao;


import java.util.List;

import org.hibernate.HibernateException;

import com.tayyarah.flight.cache.entity.FlightSearchCache;
import com.tayyarah.flight.entity.FlightAirPriceDetailsTemp;
import com.tayyarah.flight.entity.FlightApiSearchResponseTemp;
import com.tayyarah.flight.entity.FlightBookingKeysTemp;
import com.tayyarah.flight.entity.FlightSearchDetailsTemp;
import com.tayyarah.flight.model.Flightsearch;
import com.tayyarah.flight.model.TempAirSegment;



public interface FlightTempAirSegmentDAO {

	public void InsertTAS(TempAirSegment TAS)throws HibernateException,
	NumberFormatException,Exception;
	public void InsertAirPriceDetails(FlightAirPriceDetailsTemp airPriceDetails)throws HibernateException,
	NumberFormatException,Exception;
	public FlightAirPriceDetailsTemp getAirPriceDetails(String transactionkey)throws HibernateException,
	NumberFormatException,Exception;
	public void InsertAll(FlightSearchDetailsTemp searcdetails)throws HibernateException,
	NumberFormatException,Exception;
	public void insertKeys(FlightBookingKeysTemp flightBookingKeys)throws HibernateException,
	NumberFormatException,Exception;
	public void updatePriceKey(String transactionkey,String price_key)throws HibernateException,
	NumberFormatException,Exception;
	public FlightSearchDetailsTemp getAll(String  searchkey)throws HibernateException,
	NumberFormatException,Exception;
	public FlightSearchDetailsTemp getAllByTransactionKey(String transactionkey);
	public  TempAirSegment getTAS(String flightindex)throws HibernateException,
	NumberFormatException,Exception;
	public  void  InsertApiResponse(FlightApiSearchResponseTemp flightApiSearchResponseTemp)throws HibernateException,
	NumberFormatException,Exception;
	public boolean CheckSearchKeyExists(String searchKey,String apiProvider) throws HibernateException, NumberFormatException, Exception;
	public List<FlightApiSearchResponseTemp>  GetApiSearchResponse(String searchKey) throws HibernateException, NumberFormatException, Exception;
	public void UpdateFlightSearchDetailsTempAll(FlightSearchDetailsTemp searchDetails)	throws HibernateException, NumberFormatException, Exception;
	public void updateKeys(FlightBookingKeysTemp flightBookingKeys)	throws HibernateException, NumberFormatException, Exception ;
	public void InsertFlightSearchCache(FlightSearchCache flightSearchCache) throws HibernateException, NumberFormatException, Exception ;
	public FlightSearchCache getApiSearchCacheResponse(Flightsearch flightsearch)throws HibernateException, NumberFormatException, Exception ;
}
