package com.tayyarah.insurance.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.util.ResponseHeader;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.insurance.dao.InsuranceCommonDao;
import com.tayyarah.insurance.entity.TrawellTagPremiumChart;
import com.tayyarah.insurance.exception.InsuranceErrorMessages;
import com.tayyarah.insurance.exception.InsuranceException;
import com.tayyarah.insurance.model.CreateInsurancePolicyRequest;
import com.tayyarah.insurance.model.PlanRequest;
import com.tayyarah.insurance.model.PlanResponse;
import com.tayyarah.insurance.model.Status;
import com.tayyarah.insurance.util.InsuranceParamValidator;

@RestController
@RequestMapping("/insurance/plan")
public class InsurancePlanController {
	static final Logger logger = Logger.getLogger(InsurancePlanController.class);
	private static  InsuranceParamValidator insuranceParamValidator = new InsuranceParamValidator();
	@Autowired
	CompanyDao companyDao;
	@Autowired
	InsuranceCommonDao insuranceCommonDao;
	@RequestMapping(value = "", method = RequestMethod.POST, headers = { "Accept=application/json" }, produces = { "application/json" })
	public @ResponseBody PlanResponse getInsurancePolicyPlan(@RequestBody PlanRequest planRequest,HttpServletResponse response,HttpServletRequest request){
		PlanResponse planResponse = new PlanResponse();
		ResponseHeader.setResponse(response);
		// Check APP KEY
		if(planRequest.getApp_key()!=null && planRequest.getApp_key().equalsIgnoreCase(""))
		{
			throw new InsuranceException(ErrorCodeCustomerEnum.Exception,InsuranceErrorMessages.INVALID_APPKEY.getErrorMessage());
		}

		insuranceParamValidator.planValidator(planRequest);
		int planId = -1;
		
		   
		if(planRequest.getOriCountry().equalsIgnoreCase("india") && planRequest.getDestCountry().equalsIgnoreCase("india")){
	    	planId = CreateInsurancePolicyRequest.Smart_Domestic_Plan;
	    }
	    else if(planRequest.getOriCountry().equalsIgnoreCase("india") && !planRequest.getDestCountry().equalsIgnoreCase("USA") && !planRequest.getDestCountry().equalsIgnoreCase("Canada")  ){
	    	planId = CreateInsurancePolicyRequest.Smart_50K_Excluding;
	    }
	    else if(planRequest.getOriCountry().equalsIgnoreCase("india") && planRequest.getDestCountry().equalsIgnoreCase("USA") || planRequest.getDestCountry().equalsIgnoreCase("Canada")){
	    	planId = CreateInsurancePolicyRequest.Smart_50K_Including;
	    }else{
	    	planId = -1;
	    }
		
	    if(planId == -1){
	    	Status status = new Status();
	    	status.setErrorCode(Status.FAILEDCODE);
	    	status.setErrorMessage("No Plan Avaiable");
	    	planResponse.setIsPolicyAvaiable(false);
	    	planResponse.setStatus(status);
	    	
	    }else{
	    	TrawellTagPremiumChart trawellTagPremiumChart = insuranceCommonDao.getTrawellTagPremiumChart(planRequest.getNoOfDays(), planRequest.getAge(),planId);
	    	if(trawellTagPremiumChart != null){
	    		Status status = new Status();
		    	status.setErrorCode(Status.SUCCESSCODE);
		    	status.setErrorMessage("Success");
		    	planResponse.setIsPolicyAvaiable(true);
		    	planResponse.setStatus(status);
		    	planResponse.setInsuranceAmt(trawellTagPremiumChart.getPremiumAmount());
		    	planResponse.setPlanId(trawellTagPremiumChart.getTrawellTagPlan().getId().toString());
		    	planResponse.setPlanName(trawellTagPremiumChart.getTrawellTagPlan().getPlanName());
	    	}else{
	    		Status status = new Status();
		    	status.setErrorCode(Status.FAILEDCODE);
		    	status.setErrorMessage("No Plan Avaiable");
		    	planResponse.setIsPolicyAvaiable(false);
		    	planResponse.setStatus(status);
	    	}
	    
	    }	
		
		return planResponse;
	}
}
