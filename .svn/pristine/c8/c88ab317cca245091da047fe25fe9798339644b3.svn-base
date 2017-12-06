package com.tayyarah.insurance.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.insurance.exception.InsuranceErrorMessages;
import com.tayyarah.insurance.exception.InsuranceException;
import com.tayyarah.insurance.model.CreateInsurancePolicyRequest;
import com.tayyarah.insurance.model.PlanRequest;
import com.tayyarah.insurance.model.TravellerDetails;



public class InsuranceParamValidator {
	static final Logger logger = Logger.getLogger(InsuranceParamValidator.class);
	public void validateString(String param, String paramName) {
		if (param == null || param.isEmpty()) {
			throw new InsuranceException(ErrorCodeCustomerEnum.Exception,InsuranceErrorMessages.PARAMVALUE_REQUIED.getErrorMessage());			
		}
	}
	public void validateLong(Long param, String paramName) {
		if (param == null || param.equals(0L)) {
			throw new InsuranceException(ErrorCodeCustomerEnum.Exception, 
					paramName + " is required");
		}
	}
	public void validateDate(String param, String paramName) {
		if (param == null || param.isEmpty()) {
			throw new InsuranceException(ErrorCodeCustomerEnum.Exception, 
					paramName + " is required");
		}else{
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyyy");
			format.setLenient(false);
			try {
				Date date = format.parse(param);			
			} catch (ParseException e) {
				throw new InsuranceException(ErrorCodeCustomerEnum.Exception, 
						paramName + " format should be in dd-MM-YYYY");
				
			}
		}
	}
	public void validateInteger(String param, String paramName) {
		if (param == null || param.isEmpty()) {
			throw new InsuranceException(ErrorCodeCustomerEnum.Exception, 
					paramName + " is required");
		}else{
			try{
				Integer.parseInt(param);
			}catch(NumberFormatException e){
				throw new InsuranceException(ErrorCodeCustomerEnum.Exception, 
						paramName + " is invalid, it should be numeric");	
			}catch (Exception e) {
				throw new InsuranceException(ErrorCodeCustomerEnum.Exception, 
						paramName + " is invalid, it should be numeric");	

			}

		}
	}
	public void validateDecimal(String param, String paramName) {
		if (param == null || param.isEmpty()) {
			throw new InsuranceException(ErrorCodeCustomerEnum.Exception,
					paramName + " is required");
		}else{
			try{
				new BigDecimal(param);
			}catch(NumberFormatException e){
				throw new InsuranceException(ErrorCodeCustomerEnum.Exception,
						paramName + " is invalid, it should be numeric");	
			}catch (Exception e) {
				throw new InsuranceException(ErrorCodeCustomerEnum.Exception,
						paramName + " is invalid, it should be numeric");	

			}

		}
	}	
	public void createpolicyValidator(CreateInsurancePolicyRequest createInsurancePolicyRequest){
		validateString(createInsurancePolicyRequest.getOrigin(), "origin");
		validateString(createInsurancePolicyRequest.getDestination(), "destination");
		validateDate(createInsurancePolicyRequest.getDepartureDate(), "onwardDate");	
		if(!createInsurancePolicyRequest.getArrivalDate().equalsIgnoreCase(""))
		   validateDate(createInsurancePolicyRequest.getArrivalDate(), "arrivalDate");	
		
		validateString(createInsurancePolicyRequest.getNoOfDays(), "noOfDays");
		for (TravellerDetails travellerDetails : createInsurancePolicyRequest.getTravellerDetails()) {
			validateString(travellerDetails.getAddress(), "address");
			validateString(travellerDetails.getAge(), "age");
			validateString(travellerDetails.getCity(), "city");
			validateString(travellerDetails.getCountry(), "country");
			validateString(travellerDetails.getDateOfBirth(), "dateOfBirth");
			validateString(travellerDetails.getDistrict(), "district");
			validateString(travellerDetails.getEmailAddress(), "emailAddress");
			validateString(travellerDetails.getMobile(), "mobile");
			validateString(travellerDetails.getFirstName(), "firstName");
			validateString(travellerDetails.getLastName(), "lastName");
			//validateString(travellerDetails.getNominee(), "nominee");
			validateString(travellerDetails.getPinCode(), "pinCode");
			//validateString(travellerDetails.getRelationShipWithNominee(), "relationShipWithNominee");
			validateString(travellerDetails.getState(), "state");
		}	
	}
	public void planValidator(PlanRequest planRequest){
		validateString(planRequest.getAge(), "age");
		validateString(planRequest.getDestCountry(), "destCountry");
		validateString(planRequest.getNoOfDays(), "noOfDays");	
		validateString(planRequest.getOriCountry(), "oriCountry");	
	
		
	}
}
