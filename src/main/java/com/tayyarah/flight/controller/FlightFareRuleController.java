package com.tayyarah.flight.controller;



import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;



import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.apiconfig.model.TboFlightConfig;
import com.tayyarah.apiconfig.model.TravelportConfig;
import com.tayyarah.common.exception.BaseException;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.exception.ErrorMessages;
import com.tayyarah.common.exception.RestError;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.util.AppControllerUtil;
import com.tayyarah.common.util.ResponseHeader;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.flight.exception.FlightErrorMessages;
import com.tayyarah.flight.exception.FlightException;
import com.tayyarah.flight.model.FareRuleResponse;
import com.tayyarah.flight.util.FlightWebServiceEndPointValidator;
import com.tayyarah.flight.util.api.tbo.TboServiceCall;
import com.tayyarah.flight.util.api.travelport.UapiServiceCall;
import com.travelport.api_v33.AirResponse.FareInfo;
import com.travelport.api_v33.AirResponse.FareRuleKey;

//http://localhost:8080/LintasTravelAPI/farerule/response?farerulekey=xiI03O7jSYidujvKzJBCUg==&farerulevalue=gws-eJxNTssKwzAM+5iiuyw2ltySbQ0tdGEb66GX/f9nzGkoTGAZW/IjpSTamcGU/jDgO+QZdb0BFfJ45AwFO8G82EDTiHV+ok+L3q270rM1DwuLQBQV60oDtp2vy/tY2K7BfWg0Tp5qvn8oGRlijOHl/Qv8oR9HeiYV&providercode=1G

@RestController
@RequestMapping("/farerule")
public class FlightFareRuleController {
	@Autowired
	CompanyDao companyDao;
	private  FlightWebServiceEndPointValidator validator = new FlightWebServiceEndPointValidator();
	static final Logger logger = Logger.getLogger(FlightFareRuleController.class);

	@RequestMapping(value="/response",headers={"Accept=application/json"},produces={"application/json"})
	public @ResponseBody FareRuleResponse getFareRuleInfo(@RequestParam(value="app_key") String app_key,@RequestParam(value="farerulekey") String farerulekey,@RequestParam(value="farerulevalue") String farerulevalue,@RequestParam(value="providercode") String providercode,HttpServletResponse response) {

		ResponseHeader.setResponse(response);//Setting response header
		logger.info("getFareRuleInfo method called : ");	
		AppControllerUtil.validateAppKey(companyDao, app_key);
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, app_key);
		if(appKeyVo==null)
		{
			throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_APPKEY);
		}validator.fareRuleValidator(farerulevalue, providercode, farerulekey);

		farerulevalue = farerulevalue.replaceAll(" ", "+");
		farerulekey = farerulekey.replaceAll(" ", "+");
		FareInfo fareInfo=new FareInfo();		
		FareRuleKey FIK=new  FareRuleKey();		
		FIK.setValue(farerulevalue);
		FIK.setProviderCode(providercode);
		fareInfo.setKey(farerulekey);		
		fareInfo.setFareRuleKey(FIK);
		FareRuleResponse FRR = new FareRuleResponse();
		try {			
			if(fareInfo.getFareRuleKey().getProviderCode().startsWith("BS")&&fareInfo.getFareRuleKey().getProviderCode().length()<15) {
				throw new FlightException(ErrorCodeCustomerEnum.ClassNotFoundException,FlightErrorMessages.NO_FARERULE);

			}else if(fareInfo.getFareRuleKey().getValue().startsWith("TB")){
				TboFlightConfig tboconfig = TboFlightConfig.GetTboConfig(appKeyVo); 
				FRR = TboServiceCall.callFareRuleService(fareInfo,tboconfig);
			}		
			else{			
				FRR = UapiServiceCall.callFareRuleService(TravelportConfig.GetTravelportConfig(), fareInfo);
			}
		} catch (ClassNotFoundException  e) {
			logger.error("ClassNotFoundException!", e);
			throw new FlightException(ErrorCodeCustomerEnum.ClassNotFoundException,FlightErrorMessages.NO_FARERULE);
		}
		catch(SOAPException e){
			logger.error("SOAPException!", e);
			throw new FlightException(ErrorCodeCustomerEnum.SOAPException,FlightErrorMessages.NO_FARERULE);
		}catch(JAXBException  e){
			logger.error("JAXBException!", e);
			throw new FlightException(ErrorCodeCustomerEnum.JAXBException,FlightErrorMessages.NO_FARERULE);

		}
		catch(Exception  e){
			logger.error("Exception!", e);
			throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_FARERULE);
		}
		return FRR;
	}

	@ExceptionHandler(BaseException.class)
	public @ResponseBody RestError handleCustomException (BaseException ex, HttpServletResponse response) {
		response.setHeader("Content-Type", "application/json");
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return ex.transformException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}