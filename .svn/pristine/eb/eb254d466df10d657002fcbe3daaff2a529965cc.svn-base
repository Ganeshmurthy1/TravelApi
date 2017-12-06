package com.tayyarah.flight.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.apiconfig.model.TboFlightConfig;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.exception.ErrorMessages;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.util.AppControllerUtil;
import com.tayyarah.common.util.ResponseHeader;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.flight.exception.FlightErrorMessages;
import com.tayyarah.flight.exception.FlightException;
import com.tayyarah.flight.model.FlightCalendarSearch;
import com.tayyarah.flight.model.FlightCalendarSearchResponse;
import com.tayyarah.flight.model.FlightMarkUpConfig;
import com.tayyarah.flight.model.MarkupCommissionDetails;
import com.tayyarah.flight.util.api.tbo.TboServiceCall;


@RestController
@RequestMapping("/flight/calsearch")
public class FlightCalendarFareSearchController {
	static final Logger logger = Logger.getLogger(FlightCalendarFareSearchController.class);
	@Autowired
	CompanyDao companyDao;

	@RequestMapping(value="/response",headers={"Accept=application/json"},produces={"application/json"})
	public @ResponseBody FlightCalendarSearchResponse getCalenderSearchresponse(@RequestBody FlightCalendarSearch flightCalendarSearch,HttpServletResponse response) {
		FlightCalendarSearchResponse flightCalendarSearchResponse = new FlightCalendarSearchResponse();
		ResponseHeader.setResponse(response);
		if(flightCalendarSearch.getApp_key()!=null && flightCalendarSearch.getApp_key().equalsIgnoreCase(""))
		{
			throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.NOTFOUND_APPKEY);
		}
		try{
			AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, flightCalendarSearch.getApp_key());
			if(appKeyVo==null)
			{
				throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_APPKEY);
			}
			Map<String,List<FlightMarkUpConfig>> markupMap = new HashMap<String,List<FlightMarkUpConfig>>();
			try {
				markupMap = companyDao.getFlightMarkUpConfigMapByCompanyId(appKeyVo,  markupMap);
			} catch (Exception e1) {
				logger.error("getFlightMarkUpConfigMapByCompanyId Exception", e1);
			}
			MarkupCommissionDetails markupCommissionDetails = new MarkupCommissionDetails();
			try {
				markupCommissionDetails = companyDao.getFlightMarkupCommissionDetailsByCompanyId(appKeyVo, markupCommissionDetails);
			} catch (Exception e1) {
				logger.error("getFlightMarkupCommissionDetailsByCompanyId Exception", e1);
			}	
			TboFlightConfig tboconfig = TboFlightConfig.GetTboConfig(appKeyVo);
			flightCalendarSearchResponse = TboServiceCall.callCalendarFareService(flightCalendarSearch,tboconfig,markupMap);

		}catch(Exception e){
			throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.CALENDARNOTAVAILABLE);
		}		
		return flightCalendarSearchResponse;
	}
}