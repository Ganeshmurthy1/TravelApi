package com.tayyarah.flight.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.tayyarah.flight.model.FlightMarkUpConfig;
import com.tayyarah.flight.model.Flightsearch;
import com.tayyarah.flight.model.SearchFlightResponse;



public class FlightReSearchExecutorServiceTaskHelper 
{
	static final Logger logger = Logger.getLogger(FlightSearchExecutorServiceTaskHelper.class);
	public static List<SearchFlightResponse> doSearchFlightService(Flightsearch flightsearch,
			Map<String,List<FlightMarkUpConfig>> markupMap,
			Map<String, String> airlineNameMap,
			ArrayList<Map<String, String>> airportMapList,boolean isDomestic,Set<String> airlineList) {

		List<SearchFlightResponse> searchFlightResponses=new ArrayList<SearchFlightResponse>();	
		Iterator<String> it1=airlineList.iterator();		
		while(it1.hasNext()){
			String airline=it1.next();System.out.println("airline for tesing :"+airline);
		}
		ExecutorService executorService = Executors.newFixedThreadPool(airlineList.size());
		List<Future<SearchFlightResponse>> futures=new ArrayList<Future<SearchFlightResponse>>();
		try {
			Iterator<String> it=airlineList.iterator();
			while(it.hasNext()){
				String airline=it.next();
				flightsearch.setAirline(airline);
				logger.info("TravelPort RESEARCH ON :"+airline+"flightsearch :"+flightsearch.getAirline());
				Future<SearchFlightResponse> future = executorService.submit(new TravelportFlightReSearchExecutorServiceTask(flightsearch,markupMap,airlineNameMap,airportMapList,isDomestic,airline));
				futures.add(future);
			}

		} catch (Exception e) {
			logger.error("Exception",e);
		}
		for(Future<SearchFlightResponse> future:futures){
			try {
				searchFlightResponses.add(future.get());
			} catch (ExecutionException e)
			{
				logger.error("Exception",e);
			}
			catch (InterruptedException  e) {
				logger.error("Exception",e);
			}
		}
		return searchFlightResponses;
	}


}
