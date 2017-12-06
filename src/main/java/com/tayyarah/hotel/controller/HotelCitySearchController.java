package com.tayyarah.hotel.controller;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.util.AppControllerUtil;
import com.tayyarah.common.util.Status;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.flight.commission.dao.AirlineCommissionBlockDao;
import com.tayyarah.flight.commission.dao.AirlineCommissionSheetDao;
import com.tayyarah.flight.commission.entity.AirlineCommissionBlock;
import com.tayyarah.flight.commission.entity.AirlineCommissionSheet;
import com.tayyarah.flight.commission.model.AirlineCommision;
import com.tayyarah.flight.commission.model.AirlineLiteral;
import com.tayyarah.flight.commission.remarks.util.Constants;
import com.tayyarah.hotel.dao.HotelCityDao;
import com.tayyarah.hotel.dao.HotelDetailsDAO;
import com.tayyarah.hotel.entity.HotelDetails;
import com.tayyarah.hotel.entity.HotelSearchCity;
import com.tayyarah.hotel.entity.TboCity;
import com.tayyarah.hotel.model.Area;
import com.tayyarah.hotel.model.CitySearchResponse;
import com.tayyarah.hotel.util.HotelApiCredentials;
import com.tayyarah.services.CommissionService;
import com.tayyarah.services.HotelRepositService;



/**
 * @author Intelli
 *
 */
@RestController

@RequestMapping("/cities")
public class HotelCitySearchController {
	public static final Logger logger = Logger.getLogger(HotelCitySearchController.class);

	@Autowired
	HotelCityDao hotelCityDao;
	@Autowired
	AirlineCommissionBlockDao airlineCommissionBlockDao;
	@Autowired
	AirlineCommissionSheetDao airlineCommissionSheetDao;
	@Autowired
	CommissionService commissionService;
	@Autowired
	CitySearchResponse citySearchResponse;
	@Autowired
	HotelRepositService hotelTayyarahRepositService;
	@Autowired
	HotelDetailsDAO hotelDetailsDAO;
	@Autowired
	CompanyDao companyDao;


	@RequestMapping(value="/hotels",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	List<HotelDetails> searchTayyarahReposit(@RequestParam(value="city") String cityCode, @RequestParam(value="cc") String companyUserId, HttpServletRequest request, HttpServletResponse response) throws HibernateException, IOException, Exception {
		List<HotelDetails> hotelDetailList = new ArrayList<HotelDetails>();
		try {
			hotelDetailList = hotelDetailsDAO.getHotelDetails(cityCode, companyUserId);
			for (HotelDetails hotel : hotelDetailList) {
				logger.info("object transformation---: no of rooms : "+((hotel.getRooms()!=null) ? hotel.getRooms().size():0));
			}
		} 
		catch (Exception e) {			
			e.printStackTrace();
		}
		finally{
			return hotelDetailList;
		}
	}

	@RequestMapping(value="",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	CitySearchResponse search(@RequestParam(value="city") String city, @RequestParam(value="cc") String cc, @RequestParam(value="country") String country, HttpServletRequest request, HttpServletResponse response) throws HibernateException, IOException, Exception {
		HotelSearchCity hotelSearchCity = hotelCityDao.getHotelSearchCity(city, cc);		
		//logger.info("seconds ... hotelSearchCity-----------:"+ hotelSearchCity);
		//logger.info("seconds ... hotelSearchCity----tg ------:"+ hotelSearchCity.getTgCity());		
		//logger.info("seconds ... hotelSearchCity----reznext ------:"+ hotelSearchCity.getReznextCity());		
		//logger.info("seconds ... hotelSearchCity----rezlive -------:"+ hotelSearchCity.getRezliveCity());			 
		//logger.info("seconds ... hotelSearchCity----tbo -------:"+ hotelSearchCity.getTboCity());
		CitySearchResponse citySearchResponse = new CitySearchResponse();
		Status staus = new Status();
		staus.setCode(1);
		staus.setMessage("Success");

		citySearchResponse.setStatus(staus);
		citySearchResponse.setKey(city);

		ArrayList<Area> areas = new ArrayList<Area>();

		Area area = new Area();
		area.setId(hotelSearchCity.getId());		

		StringBuffer name = new StringBuffer(hotelSearchCity.getCity());

		if(hotelSearchCity.getState() != null)
		{
			name.append(","+hotelSearchCity.getState());
		}
		else if(hotelSearchCity.getTboCity()!=null && hotelSearchCity.getTboCity().getStateprovince()!=null)
		{
			name.append(","+hotelSearchCity.getTboCity().getStateprovince() +"("+hotelSearchCity.getTboCity().getCountrycode()+")");					
		}
		if(hotelSearchCity.getTgCity()!=null)
		{
			name.append(", India (IN)");						
		}
		else if(hotelSearchCity.getTboCity()!=null && hotelSearchCity.getTboCity().getCountry()!=null)
		{
			name.append(","+hotelSearchCity.getTboCity().getCountry());					
		}
		if(hotelSearchCity.getTgCity()!=null)
		{
			name.append(", India (IN)");	
		}
		area.setName(name.toString());
		areas.add(area);


		citySearchResponse.setAreas(areas);
		return citySearchResponse;

	}



	@RequestMapping(value="/preload",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	String preLoadCities(HttpServletRequest request, HttpServletResponse response) throws HibernateException, IOException, Exception {
		//logger.info("seconds ... citySearchResponse-----------:"+ ((citySearchResponse==null)?"EMPTY":citySearchResponse.toString()));		

		//logger.info("############## init citylist singleton..................");			
		//logger.info("############## init citylist singleton..................");	
		logger.info("############## init citylist singleton..................");	

		String key = "all";
		Status staus = new Status();
		staus.setCode(1);
		staus.setMessage("Success");
		CitySearchResponse citySearchResponseTemp = new CitySearchResponse();
		citySearchResponseTemp.setStatus(staus);
		citySearchResponseTemp.setKey("all");

		ArrayList<Area> areas = new ArrayList<Area>();
		// default app_key of the super user
		String appKey="zqJ3R9cGpNWgNXG55ub/WQ==";
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, appKey);
		if(appKeyVo==null)
		{
			// TODO Error 
		}
		HotelApiCredentials apidesiya = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_DESIA_IND,appKeyVo);		
		HotelApiCredentials apiTBO = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TBO_INTERNATIONAL,appKeyVo);	
		if(apidesiya.isEnabled() && apiTBO.isEnabled())
		{
			List<HotelSearchCity> citiesDb = hotelCityDao.getHotelSearchCity(key);
			for (HotelSearchCity hotelSearchCity : citiesDb) {
				//logger.info("############## hotelSearchCity == "+hotelSearchCity.toString());				
				Area area = new Area();
				area.setId(hotelSearchCity.getId());	
				StringBuffer name = new StringBuffer();
				StringBuffer state = new StringBuffer();
				StringBuffer country = new StringBuffer();
				StringBuffer cc = new StringBuffer();					
				if(hotelSearchCity.getTboCity()!=null)
				{
					TboCity tboCity = hotelSearchCity.getTboCity();
					name.append((tboCity.getDestination() != null && tboCity.getDestination().trim().length()>1)?tboCity.getDestination()+",":"");
					name.append((tboCity.getStateprovince() != null && tboCity.getStateprovince().trim().length()>1)?tboCity.getStateprovince()+",":"");
					name.append((tboCity.getCountry() != null && tboCity.getCountry().trim().length()>1)?tboCity.getCountry()+((tboCity.getCountrycode() != null && tboCity.getCountrycode().trim().length()>1)?"("+tboCity.getCountrycode()+")":""):"");										
				}
				else
				{
					name.append((hotelSearchCity.getCity() != null && hotelSearchCity.getCity().trim().length()>1)?hotelSearchCity.getCity()+",":"");										
					name.append((hotelSearchCity.getState() != null && hotelSearchCity.getState().trim().length()>1)?hotelSearchCity.getState()+",":"");
					name.append((hotelSearchCity.getTgCity() != null)?"India(IN)":(hotelSearchCity.getCountryCode() != null && hotelSearchCity.getCountryCode().trim().length()>1)?hotelSearchCity.getCountryCode():"");
				}
				area.setName(name.toString());
				areas.add(area);
			}
		}
		else if(apiTBO.isEnabled())
		{
			List<TboCity> citiesDb = hotelCityDao.getTBOCities(key);
			for (TboCity tboCity : citiesDb) {
				//logger.info("############## tboCity == "+tboCity.toString());				
				Area area = new Area();
				area.setId(tboCity.getCityid());	
				StringBuffer name = new StringBuffer();				
				name.append((tboCity.getDestination() != null && tboCity.getDestination().trim().length()>1)?tboCity.getDestination()+",":"");
				name.append((tboCity.getStateprovince() != null && tboCity.getStateprovince().trim().length()>1)?tboCity.getStateprovince()+",":"");
				name.append((tboCity.getCountry() != null && tboCity.getCountry().trim().length()>1)?tboCity.getCountry()+((tboCity.getCountrycode() != null && tboCity.getCountrycode().trim().length()>1)?"("+tboCity.getCountrycode()+")":""):"");
				area.setName(name.toString());
				areas.add(area);
			}
		}


		citySearchResponseTemp.setTotalCount(areas.size());
		citySearchResponseTemp.setCount(areas.size());
		citySearchResponseTemp.setAreas(areas);

		//logger.info("############## LOADED citylist singleton..................");			
		//logger.info("############## LOADED citylist singleton..................");	
		logger.info("############## LOADED citylist singleton..................");	


		citySearchResponse = citySearchResponseTemp;
		return "success";
		//return citySearchResponse;
	}


	@RequestMapping(value="/search",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	CitySearchResponse searchService(@RequestParam(value="key", defaultValue="") String key,  HttpServletRequest request, HttpServletResponse response) throws HibernateException, IOException, Exception {
		//Do your operations....

		logger.info("############## NO singleton cache direct access ..................");			
		if(key == null || key.trim().length()==0)
		{
			key = "all";
		}

		Status staus = new Status();
		staus.setCode(1);
		staus.setMessage("Success");
		CitySearchResponse citySearchResponseTemp = new CitySearchResponse();
		citySearchResponseTemp.setStatus(staus);
		citySearchResponseTemp.setKey(key);

		ArrayList<Area> areas = new ArrayList<Area>();
		// default app_key of the super user
		String appKey="zqJ3R9cGpNWgNXG55ub/WQ==";
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, appKey);
		if(appKeyVo==null)
		{
			// TODO Error 
		}
		HotelApiCredentials apidesiya = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_DESIA_IND,appKeyVo);		
		HotelApiCredentials apiTBO = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TBO_INTERNATIONAL,appKeyVo);	
		if(apidesiya.isEnabled() && apiTBO.isEnabled())
		{
			logger.info("############## Hotel city load desiya + tbo..................");			

			List<HotelSearchCity> citiesDb = hotelCityDao.getHotelSearchCity(key);
			for (HotelSearchCity hotelSearchCity : citiesDb) {
				//logger.info("############## hotelSearchCity == "+hotelSearchCity.toString());				
				Area area = new Area();
				area.setId(hotelSearchCity.getId());	
				StringBuffer name = new StringBuffer();
				StringBuffer state = new StringBuffer();
				StringBuffer country = new StringBuffer();
				StringBuffer cc = new StringBuffer();					
				if(hotelSearchCity.getTboCity()!=null)
				{
					TboCity tboCity = hotelSearchCity.getTboCity();
					name.append((tboCity.getDestination() != null && tboCity.getDestination().trim().length()>1)?tboCity.getDestination()+",":"");
					name.append((tboCity.getStateprovince() != null && tboCity.getStateprovince().trim().length()>1)?tboCity.getStateprovince()+",":"");
					name.append((tboCity.getCountry() != null && tboCity.getCountry().trim().length()>1)?tboCity.getCountry()+((tboCity.getCountrycode() != null && tboCity.getCountrycode().trim().length()>1)?"("+tboCity.getCountrycode()+")":""):"");										
				}
				else
				{
					name.append((hotelSearchCity.getCity() != null && hotelSearchCity.getCity().trim().length()>1)?hotelSearchCity.getCity()+",":"");										
					name.append((hotelSearchCity.getState() != null && hotelSearchCity.getState().trim().length()>1)?hotelSearchCity.getState()+",":"");
					name.append((hotelSearchCity.getTgCity() != null)?"India(IN)":(hotelSearchCity.getCountryCode() != null && hotelSearchCity.getCountryCode().trim().length()>1)?hotelSearchCity.getCountryCode():"");
				}
				area.setName(name.toString());
				areas.add(area);
			}
		}
		else if(apiTBO.isEnabled())
		{
			List<TboCity> citiesDb = hotelCityDao.getTBOCities(key);
			for (TboCity tboCity : citiesDb) {
				//logger.info("############## tboCity == "+tboCity.toString());				
				Area area = new Area();
				area.setId(tboCity.getCityid());	
				StringBuffer name = new StringBuffer();				
				name.append((tboCity.getDestination() != null && tboCity.getDestination().trim().length()>1)?tboCity.getDestination()+",":"");
				name.append((tboCity.getStateprovince() != null && tboCity.getStateprovince().trim().length()>1)?tboCity.getStateprovince()+",":"");
				name.append((tboCity.getCountry() != null && tboCity.getCountry().trim().length()>1)?tboCity.getCountry()+((tboCity.getCountrycode() != null && tboCity.getCountrycode().trim().length()>1)?"("+tboCity.getCountrycode()+")":""):"");
				area.setName(name.toString());
				areas.add(area);
			}
		}

		citySearchResponseTemp.setAreas(areas);
		citySearchResponseTemp.setTotalCount(areas.size());
		citySearchResponseTemp.setCount(areas.size());
		citySearchResponse = citySearchResponseTemp;
		return citySearchResponseTemp;

		//return citySearchResponse;
	}

	@RequestMapping(value="/test",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	List<HotelSearchCity> test(@RequestParam(value="key") String key,  HttpServletRequest request, HttpServletResponse response) throws HibernateException, IOException, Exception {
		//Do your operations....
		return hotelCityDao.getHotelSearchCity(key);
	}


	@RequestMapping(value="/testblock",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	List<AirlineCommissionBlock> testblock(HttpServletRequest request, HttpServletResponse response) throws HibernateException, IOException, Exception {
		//Do your operations....
		BigInteger childCompanyId = new BigInteger("2");
		BigInteger parentCompanyId = new BigInteger("1");
		Long sheetId = new Long("1");

		//createAirlineCommissionBlock

		return null;
		//return airlineCommissionBlockDao.createAirlineCommissionBlock(parentCompanyId, childCompanyId, sheetId);
	}

	@RequestMapping(value="/testsheet",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	List<AirlineCommissionSheet> testsheet(HttpServletRequest request, HttpServletResponse response) throws HibernateException, IOException, Exception {
		//Do your operations....
		return airlineCommissionSheetDao.getAirlineCommissionSheet(new Long("1"));
	}

	@RequestMapping(value="/testsheetcommon",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	AirlineCommissionSheet testsheetCommon(HttpServletRequest request, HttpServletResponse response) throws HibernateException, IOException, Exception {
		//Do your operations....
		return airlineCommissionSheetDao.getAirlineCommissionSheetCommons(new Long("1"));
	}

	@RequestMapping(value="/testsheetremark",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	AirlineCommissionSheet testsheetRemark(@RequestParam(value="iatacode") String iataCode, @RequestParam(value="isplb") Boolean isplb, HttpServletRequest request, HttpServletResponse response) throws HibernateException, IOException, Exception {
		//Do your operations....
		return airlineCommissionSheetDao.getAirlineCommissionSheetRemark(new Long("1"), iataCode, isplb);
	}


	@RequestMapping(value="/getcommission",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	Map<Integer, AirlineCommision> getcommission(@RequestParam(value="class") String classTravel, @RequestParam(value="iatacode") String iatacode, @RequestParam(value="companyId") int companyId, @RequestParam(value="configId") int configId, HttpServletRequest request, HttpServletResponse response) throws HibernateException, IOException, Exception {
		//Do your operations....

		/*Applicable countryApplicable = new Applicable(Constants.TYPE_COUNTRY, "Abu dabi");	
		Applicable countryApplicable2 = new Applicable(Constants.TYPE_COUNTRY, "saudi");	
		Applicable countryApplicable3 = new Applicable(Constants.TYPE_COUNTRY, "oman");	
		 */
		HashMap<Integer, AirlineLiteral> literals = new HashMap<Integer, AirlineLiteral>();
		literals.put(Constants.TYPE_CLASS, new AirlineLiteral(Constants.TYPE_CLASS, classTravel, true));
		/*literals.put(Constants.TYPE_AIRLINE, new AirlineLiteral(Constants.TYPE_AIRLINE, "American Airlines", true));
		literals.put(Constants.TYPE_COUNTRY, new AirlineLiteral(Constants.TYPE_COUNTRY, "india", true));		
		 */
		Map<Integer, AirlineCommision> agentCommissionMap = commissionService.getAirLineCommission(iatacode, companyId, configId, literals);
		return agentCommissionMap;
	}


	@RequestMapping(value="/getcommission2",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	Map<Integer, AirlineCommision> getcommission2( @RequestParam(value="iatacode") String iatacode, @RequestParam(value="companyId") int companyId, @RequestParam(value="configId") int configId, HttpServletRequest request, HttpServletResponse response) throws HibernateException, IOException, Exception {
		//Do your operations....

		/*Applicable countryApplicable = new Applicable(Constants.TYPE_COUNTRY, "Abu dabi");	
		Applicable countryApplicable2 = new Applicable(Constants.TYPE_COUNTRY, "saudi");	
		Applicable countryApplicable3 = new Applicable(Constants.TYPE_COUNTRY, "oman");	
		 */
		HashMap<Integer, AirlineLiteral> literals = new HashMap<Integer, AirlineLiteral>();
		literals.put(Constants.TYPE_CLASS, new AirlineLiteral(Constants.TYPE_CLASS, "A", true));
		/*literals.put(Constants.TYPE_AIRLINE, new AirlineLiteral(Constants.TYPE_AIRLINE, "American Airlines", true));
		literals.put(Constants.TYPE_COUNTRY, new AirlineLiteral(Constants.TYPE_COUNTRY, "india", true));		
		 */
		Map<Integer, AirlineCommision> agentCommissionMap = commissionService.getAirLineCommission(iatacode, companyId, configId, literals);
		return agentCommissionMap;
	}
	@RequestMapping(value="/getcommissionsheet",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	AirlineCommissionSheet getcommissionSheet(@RequestParam(value="iatacode") String iatacode, @RequestParam(value="companyId") int companyId, @RequestParam(value="configId") int configId, HttpServletRequest request, HttpServletResponse response) throws HibernateException, IOException, Exception {
		//Do your operations....

		AirlineCommissionSheet airlineCommissionSheetRow = commissionService.getAirLineCommissionSheetCompany(iatacode, companyId, configId);
		return airlineCommissionSheetRow;
	}



}
