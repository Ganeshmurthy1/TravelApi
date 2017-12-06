/**
 * 
 */
package com.tayyarah.flight.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.flight.cache.dao.FlightSearchCacheDao;
import com.tayyarah.flight.cache.entity.FlightSearchCacheDestination;



/**
 * @author      : Manish Samrat
 * @createdAt   : 17/08/2017
 * @version
 */
@RestController
@RequestMapping(value = "/FlightSearchCache")
public class FlightCacheController {

	@Autowired
	FlightSearchCacheDao flightSearchCacheDao;


	@Autowired
	FlightSearchController flightSearchController;

	@RequestMapping(value = "/List", method = RequestMethod.GET, headers = "Accept=application/json") 
	public @ResponseBody  List<FlightSearchCacheDestination> fetchAllCasheList(HttpServletRequest request, HttpServletResponse response) throws  Exception {
		List<FlightSearchCacheDestination> searchCacheDestinationList=new ArrayList<FlightSearchCacheDestination>();;
		try {
			searchCacheDestinationList=flightSearchCacheDao.fetchAllSearchCacheList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return searchCacheDestinationList;
	}

	@RequestMapping(value = "callAPIForCache", method = RequestMethod.GET, headers = "Accept=application/json") 
	public @ResponseBody synchronized String getFlightSearchData(HttpServletRequest request, HttpServletResponse response) throws  Exception {
		try {

			List<FlightSearchCacheDestination> flightSearchCacheDestinations = flightSearchCacheDao.fetchAllSearchCacheList();

			if(flightSearchCacheDestinations!=null && flightSearchCacheDestinations.size()>0)
			{
				for(FlightSearchCacheDestination flightSearchCacheDestination:flightSearchCacheDestinations)
				{
					List<String> departDateList = getDepatureDate();
					if(departDateList.size() > 0){
						for (String depdate : departDateList) {
							if(flightSearchCacheDestination.isOneway())
								flightSearchController.search("All", "1", "O", flightSearchCacheDestination.getOrigin(), flightSearchCacheDestination.getDestination(), depdate, "", "1", "0", "0", "Economy", "INR", "zqJ3R9cGpNWgNXG55ub/WQ==", false, false, false, false, "0", "", true,request, response);
						}
					}

				}
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Success";
	}
	public static  List<String> getDepatureDate(){
		List<String> departDateList = new ArrayList<String>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date currentDate = new Date();
		//System.out.println(dateFormat.format(currentDate));

		for(int i=1;i<=30;i++){
			// convert date to calendar
			Calendar c = Calendar.getInstance();
			c.setTime(currentDate);

			// manipulate date     
			c.add(Calendar.DATE, i); //same with c.add(Calendar.DAY_OF_MONTH, 1);   

			// convert calendar to date
			Date currentDatePlusOne = c.getTime();
			String departDate = dateFormat.format(currentDatePlusOne);
			departDateList.add(departDate);     
		}
		return departDateList;
	}
}

