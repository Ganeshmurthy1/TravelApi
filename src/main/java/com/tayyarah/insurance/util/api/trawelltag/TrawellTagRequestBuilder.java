package com.tayyarah.insurance.util.api.trawelltag;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tayyarah.api.insurance.trawelltag.model.Contactdetails;
import com.tayyarah.api.insurance.trawelltag.model.Identity;
import com.tayyarah.api.insurance.trawelltag.model.Insured;
import com.tayyarah.api.insurance.trawelltag.model.Otherdetails;
import com.tayyarah.api.insurance.trawelltag.model.Plan;
import com.tayyarah.api.insurance.trawelltag.model.Policy;
import com.tayyarah.api.insurance.trawelltag.model.Traveldetails;
import com.tayyarah.apiconfig.model.TrawellTagConfig;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.util.DateConversion;
import com.tayyarah.insurance.entity.TrawellTagPremiumChart;
import com.tayyarah.insurance.exception.InsuranceErrorMessages;
import com.tayyarah.insurance.exception.InsuranceException;
import com.tayyarah.insurance.model.CreateInsurancePolicyRequest;
import com.tayyarah.insurance.model.TravellerDetails;


public class TrawellTagRequestBuilder {

	public static Policy createPolicyRequest(TrawellTagConfig trawellTagConfig,CreateInsurancePolicyRequest createInsurancePolicyRequest,TrawellTagPremiumChart trawellTagPremiumChart,TravellerDetails travellerDetails){
		Policy policy = null;
		try{
			policy = new Policy();				
			SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy");
			//create identity
			Identity identity = new Identity();
			identity.setBranchsign(trawellTagConfig.getBranchSign());
			identity.setReference(createInsurancePolicyRequest.getInsuredProductOrderId().trim()+"-C"+travellerDetails.getCustomerId());
			identity.setSign(trawellTagConfig.getSign());
			identity.setUsername(trawellTagConfig.getUserName());

			//create insured
			Insured insured = new Insured();
			//insured.setAge("27");
			insured.setAge(travellerDetails.getAge());
			Date dob = DateConversion.StringToDateInsurance(travellerDetails.getDateOfBirth());
			String dateofbirth = dateformat.format(dob);
			insured.setDateofbirth(dateofbirth);
			insured.setName(travellerDetails.getFirstName()+" "+travellerDetails.getLastName());
			insured.setNominee(travellerDetails.getNominee());
			if(!travellerDetails.getPassportNumber().equalsIgnoreCase(""))
			   insured.setPassport(travellerDetails.getPassportNumber());
			
			   insured.setRelation(travellerDetails.getRelationShipWithNominee());

			//create insured Contactdetails 
			Contactdetails contactdetails = new Contactdetails();
			contactdetails.setAddress1(travellerDetails.getAddress());
			contactdetails.setCity(travellerDetails.getCity());
			contactdetails.setCountry(travellerDetails.getCountry());
			contactdetails.setDistrict(travellerDetails.getDistrict());
			contactdetails.setEmailaddress(travellerDetails.getEmailAddress());
			contactdetails.setMobile(travellerDetails.getMobile());
			contactdetails.setPincode(travellerDetails.getPinCode());
			contactdetails.setState(travellerDetails.getState());

			insured.setContactdetails(contactdetails);

			//create plan
			Plan plan = new Plan();
		//	Riders riders = new Riders();
		//	Ridercode ridercode = new Ridercode();
		//	riders.setRidercode(ridercode);
		//	plan.setRiders(riders);
			plan.setCategorycode(trawellTagPremiumChart.getTrawellTagPlan().getTrawellTagCategory().getCode());
			plan.setPlancode(trawellTagPremiumChart.getTrawellTagPlan().getPlanCode());
			plan.setBasecharges(trawellTagPremiumChart.getPremiumAmount().setScale(0, RoundingMode.UP).toPlainString());

			BigDecimal serviceTax = trawellTagPremiumChart.getPremiumAmount().divide(new BigDecimal("1.18"),2,RoundingMode.HALF_UP);
			serviceTax = trawellTagPremiumChart.getPremiumAmount().subtract(serviceTax.setScale(0, RoundingMode.UP));
			serviceTax = serviceTax.setScale(0, RoundingMode.UP);
			plan.setServicetax(serviceTax.toPlainString());
			BigDecimal totalBaseCharges = trawellTagPremiumChart.getPremiumAmount().subtract(serviceTax);
			totalBaseCharges = totalBaseCharges.setScale(0, RoundingMode.UP);
			plan.setTotalbasecharges(totalBaseCharges.toPlainString());
			BigDecimal totalCharges = totalBaseCharges.add(serviceTax);
			totalCharges = totalCharges.setScale(0, RoundingMode.UP);		
		
			plan.setTotalcharges(totalCharges.toPlainString());

			//Traveldetails otherdetails
			Traveldetails traveldetails = new Traveldetails();
			traveldetails.setDays(createInsurancePolicyRequest.getNoOfDays());
			Date departdate = DateConversion.StringToDate(createInsurancePolicyRequest.getDepartureDate());
			Date arrdate = DateConversion.StringToDate(createInsurancePolicyRequest.getArrivalDate());

			String departuredate = dateformat.format(departdate);
			String arrivaldate = dateformat.format(arrdate);
			traveldetails.setArrivaldate(arrivaldate);
			traveldetails.setDeparturedate(departuredate);

			//create otherdetails
			Otherdetails otherdetails = new Otherdetails();

			policy.setIdentity(identity);
			policy.setInsured(insured);
			policy.setPlan(plan);
			policy.setOtherdetails(otherdetails);
			policy.setTraveldetails(traveldetails);

		}catch(Exception e){
			throw new InsuranceException(ErrorCodeCustomerEnum.Exception,InsuranceErrorMessages.CREATEPOLICY_ERROR.getErrorMessage());
		}

		return policy;
	}
	
	/*<policy>
   
    <insured>
        <age>29</age>
        <contactdetails>
            <address1>bangalore</address1>
            <city>bangalore</city>
            <country>india</country>
            <district>bangalore</district>
            <emailaddress>vimalsvsr@gmail.com</emailaddress>
            <mobile>8123447347</mobile>
            <pincode>560007</pincode>
            <state>karanataka</state>
        </contactdetails>
        <dateofbirth>15-Jan-1988</dateofbirth>
        <name>vimal</name>
        <nominee>alex</nominee>
        <passport></passport>
        <relation>brother</relation>
    </insured>
    <otherdetails/>
    <plan>
        <basecharges>107</basecharges>
        <categorycode>DE5EE71C-098F-4CC0-B486-E69391CC9FA8</categorycode>
        <plancode>056cb231-ab40-4d8c-a4b5-1a71f2ebe77c</plancode>
         <servicetax>91</servicetax>
        <totalbasecharges>16</totalbasecharges>
        <totalcharges>107</totalcharges>
    </plan>
    <traveldetails>
        <arrivaldate>26-Aug-2017</arrivaldate>
        <days>2</days>
        <departuredate>25-Aug-2017</departuredate>
    </traveldetails>
</policy>*/
	
	public static Policy createPolicyRequestTest(TrawellTagConfig trawellTagConfig,CreateInsurancePolicyRequest createInsurancePolicyRequest,TrawellTagPremiumChart trawellTagPremiumChart,TravellerDetails travellerDetails){
		Policy policy = null;
		try{
			policy = new Policy();				
			SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy");
			//create identity
			Identity identity = new Identity();
			identity.setBranchsign(trawellTagConfig.getBranchSign());
			identity.setReference(trawellTagConfig.getReferance());
			identity.setSign(trawellTagConfig.getSign());
			identity.setUsername(trawellTagConfig.getUserName());

			//create insured
			Insured insured = new Insured();
			insured.setAge("29");			
			insured.setDateofbirth("15-Jan-1988");
			insured.setName("vimal alex");
			//insured.setPassport("");
			insured.setNominee("alex");  
			insured.setRelation("brother");

			//create insured Contactdetails 
			Contactdetails contactdetails = new Contactdetails();
			contactdetails.setAddress1("bangalore");
			contactdetails.setCity("bangalore");
			contactdetails.setCountry("india");
			contactdetails.setDistrict("bangalore");
			contactdetails.setEmailaddress("vimalsvsr@gmail.com");
			contactdetails.setMobile("8123447347");
			contactdetails.setPincode("560007");
			contactdetails.setState("karanataka");

			insured.setContactdetails(contactdetails);

			//create plan
			Plan plan = new Plan();
		//	Riders riders = new Riders();
		//	Ridercode ridercode = new Ridercode();
		//	riders.setRidercode(ridercode);
		//	plan.setRiders(riders);
			plan.setCategorycode(trawellTagPremiumChart.getTrawellTagPlan().getTrawellTagCategory().getCode());
			plan.setPlancode(trawellTagPremiumChart.getTrawellTagPlan().getPlanCode());
			plan.setBasecharges(trawellTagPremiumChart.getPremiumAmount().setScale(0, RoundingMode.UP).toPlainString());

			BigDecimal serviceTax = trawellTagPremiumChart.getPremiumAmount().divide(new BigDecimal("1.18"),2,RoundingMode.HALF_UP);
			serviceTax = trawellTagPremiumChart.getPremiumAmount().subtract(serviceTax.setScale(0, RoundingMode.UP));
			serviceTax = serviceTax.setScale(0, RoundingMode.UP);
			plan.setServicetax(serviceTax.toPlainString());
			BigDecimal totalBaseCharges = trawellTagPremiumChart.getPremiumAmount().subtract(serviceTax);
			totalBaseCharges = totalBaseCharges.setScale(0, RoundingMode.UP);
			plan.setTotalbasecharges(totalBaseCharges.toPlainString());
			BigDecimal totalCharges = totalBaseCharges.add(serviceTax);
			totalCharges = totalCharges.setScale(0, RoundingMode.UP);		
		
			plan.setTotalcharges(totalCharges.toPlainString());

			//Traveldetails otherdetails
			Traveldetails traveldetails = new Traveldetails();
			traveldetails.setDays("2");
			Date departdate = DateConversion.StringToDate(createInsurancePolicyRequest.getDepartureDate());
			Date arrdate = DateConversion.StringToDate(createInsurancePolicyRequest.getArrivalDate());

			String departuredate = dateformat.format(departdate);
			String arrivaldate = dateformat.format(arrdate);
			traveldetails.setArrivaldate("26-Aug-2017");
			traveldetails.setDeparturedate("26-Aug-2017");

			//create otherdetails
			Otherdetails otherdetails = new Otherdetails();

			policy.setIdentity(identity);
			policy.setInsured(insured);
			policy.setPlan(plan);
			policy.setOtherdetails(otherdetails);
			policy.setTraveldetails(traveldetails);

		}catch(Exception e){
			throw new InsuranceException(ErrorCodeCustomerEnum.Exception,InsuranceErrorMessages.CREATEPOLICY_ERROR.getErrorMessage());
		}

		return policy;
	}
	

	public static StringBuilder createSoapMessage(String xmlData,String ref){

		//Sample Request

		/*<?xml version="1.0" encoding="utf-8"?>
		<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
		  <soap:Body>
		    <CreatePolicy xmlns="https://karvatgroup.in/trawelltag/">
		      <Data>string</Data>
		      <Ref>string</Ref>
		    </CreatePolicy>
		  </soap:Body>
		</soap:Envelope>*/

		StringBuilder reqstr = new StringBuilder("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+"<soap:Body>"
				+"<CreatePolicy xmlns=\"https://karvatgroup.in/trawelltag/\">"
				+"<Data>"+xmlData+"</Data>"
				+"<Ref>"+ref+"</Ref>"
				+"</CreatePolicy>"
				+"</soap:Body>"
				+"</soap:Envelope>");

		return reqstr;
	}

}

