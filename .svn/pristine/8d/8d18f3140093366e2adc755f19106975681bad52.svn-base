package com.tayyarah.flight.controller;


import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.common.entity.Airport;
import com.tayyarah.common.exception.BaseException;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.exception.ErrorMessages;
import com.tayyarah.common.exception.RestError;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.util.AppControllerUtil;
import com.tayyarah.common.util.ResponseHeader;
import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.company.entity.Company;
import com.tayyarah.flight.dao.FlightTempAirSegmentDAO;
import com.tayyarah.flight.exception.FlightErrorMessages;
import com.tayyarah.flight.exception.FlightException;
import com.tayyarah.flight.model.FlightPriceResponse;
import com.tayyarah.flight.service.db.AirlineService;
import com.tayyarah.flight.service.db.AirportService;
import com.tayyarah.flight.util.FlightWebServiceEndPointValidator;
import com.tayyarah.flight.util.api.travelport.UapiServiceCall;
import com.tayyarah.insurance.controller.InsurancePlanController;
import com.tayyarah.insurance.model.PlanRequest;
import com.tayyarah.insurance.model.PlanResponse;
import com.tayyarah.insurance.util.InsuranceCommonUtil;


@RestController
@RequestMapping("/airprice")
public class FlightPriceController {	

	static final Logger logger = Logger.getLogger(FlightPriceController.class);
	private  FlightWebServiceEndPointValidator validator = new FlightWebServiceEndPointValidator();

	@Autowired
	FlightTempAirSegmentDAO TempDAO;
	@Autowired
	CompanyDao companyDao;
	@Autowired
	AirportService airportService;
	@Autowired
	AirlineService airlineService;
	@Autowired
	CompanyConfigDAO companyConfigDAO;	
	@Autowired
	InsurancePlanController insurancePlanController;

	@RequestMapping(value="/response",headers={"Accept=application/json"},produces={"application/json"})
	public @ResponseBody FlightPriceResponse getFareRuleInfo(@RequestParam(value="app_key") String app_key,@RequestParam(value="searchkey") String searchkey,@RequestParam(value="flightindex") String flightindex,@RequestParam(value="islowfare") Boolean isLowFare,@RequestParam(value="lowfareflightindex1") String lowFareFlightIndex1,@RequestParam(value="lowfareflightindex2") String lowFareFlightIndex2,@RequestParam(value="reasontoselect") String reasonToSelect,@RequestParam(value="islowfarereturn",defaultValue="true") Boolean isLowFareReturn,@RequestParam(value="lowfareflightindexreturn1",defaultValue="") String lowFareFlightIndexReturn1,@RequestParam(value="lowfareflightindexreturn2",defaultValue="") String lowFareFlightIndexReturn2,@RequestParam(value="reasontoselectreturn",defaultValue="") String reasonToSelectReturn,HttpServletRequest request,HttpServletResponse response){

		ResponseHeader.setResponse(response);//Setting response header
		AppControllerUtil.validateAppKey(companyDao, app_key);
		validator.airPriceValidator(searchkey,flightindex);		
		ArrayList<Map<String,String>> MapList=new ArrayList<Map<String,String>>();
		Map<String,String> AirlineNameMap;
		try {
			AirlineNameMap = airlineService.getAirlineNameMap();
			MapList = airportService.getAirportMap();

		}catch (HibernateException e) {
			logger.error("HibernateException",e);
			throw new FlightException(ErrorCodeCustomerEnum.HibernateException,FlightErrorMessages.NO_AIRPRICE);
		} catch (Exception e) {
			logger.error("Exception",e);
			throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIRPRICE);
		}
		FlightPriceResponse flightPriceResponse = new FlightPriceResponse();
		try {
			AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, app_key);
			if(appKeyVo==null)
			{
				throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_APPKEY);
			}
			//flightPriceResponse = UapiServiceCall.callAirPriceService(flightindex,searchkey, AirlineNameMap,MapList,TempDAO,ADAO,decapp_key,companyConfigDAO,CDAO);
			flightPriceResponse = UapiServiceCall.callAirPriceService(flightindex,searchkey, AirlineNameMap,MapList,TempDAO,appKeyVo,companyConfigDAO,companyDao,isLowFare,lowFareFlightIndex1,lowFareFlightIndex2,reasonToSelect,isLowFareReturn,lowFareFlightIndexReturn1,lowFareFlightIndexReturn2,reasonToSelectReturn);
			Company newCompany=companyDao.getCompany(appKeyVo.getCompanyId());
			flightPriceResponse.setGstNumber(newCompany!=null && newCompany.getCompanyGstIn()!=null?newCompany.getCompanyGstIn():null);
			Map<String, Airport> airportMap = airportService.getAllAirportMap();
			String originCountry = airportMap.get(flightPriceResponse.getFlightsearch().getOrigin()).getCountry(); //  airportMap.getyCountryName(flightPriceResponse.getFlightsearch().getOrigin());
			String destCountry = airportMap.get(flightPriceResponse.getFlightsearch().getDestination()).getCountry(); //airportService.getyCountryName(flightPriceResponse.getFlightsearch().getDestination());
			if(originCountry.equalsIgnoreCase("india") && destCountry.equalsIgnoreCase("india")) {
				PlanRequest planRequest = new PlanRequest();			
				planRequest.setApp_key(app_key);
				planRequest.setOriCountry(originCountry);
				planRequest.setDestCountry(destCountry);
				if(flightPriceResponse.getFlightsearch().getTripType().equalsIgnoreCase("O")){
					planRequest.setAge("30");
					planRequest.setNoOfDays("20"); 
				}else{
					planRequest.setAge("30");
					int noofdays = InsuranceCommonUtil.getNoofStayDays(flightPriceResponse.getFlightsearch().getDepDate(), flightPriceResponse.getFlightsearch().getArvlDate());
					planRequest.setNoOfDays(String.valueOf(noofdays)); 
				}
				PlanResponse insurancePlanResponse = insurancePlanController.getInsurancePolicyPlan(planRequest, response, request);
				if(insurancePlanResponse != null){
					flightPriceResponse.setInsurancePlanResponse(insurancePlanResponse);
				}
			}			
		} catch (ClassNotFoundException  e) {logger.error("ClassNotFoundException",e);
		throw new FlightException(ErrorCodeCustomerEnum.ClassNotFoundException,FlightErrorMessages.NO_AIRPRICE);
		}
		catch(SOAPException e){logger.error("SOAPException",e);
		throw new FlightException(ErrorCodeCustomerEnum.SOAPException,FlightErrorMessages.NO_AIRPRICE);
		}catch(JAXBException  e){logger.error("JAXBException",e);
		throw new FlightException(ErrorCodeCustomerEnum.JAXBException,FlightErrorMessages.NO_AIRPRICE);

		}
		catch(Exception  e){logger.error("Exception",e);
		throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIRPRICE);
		}

		return flightPriceResponse;
	}

	@ExceptionHandler(BaseException.class)
	public @ResponseBody RestError handleCustomException (BaseException ex, HttpServletResponse response) {
		response.setHeader("Content-Type", "application/json");
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return ex.transformException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}

}
