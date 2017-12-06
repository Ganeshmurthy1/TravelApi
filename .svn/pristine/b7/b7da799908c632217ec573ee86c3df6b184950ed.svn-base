package com.tayyarah.insurance.util;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.tayyarah.apiconfig.model.TrawellTagConfig;
import com.tayyarah.common.dao.MoneyExchangeDao;
import com.tayyarah.common.entity.OrderCustomer;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.gstconfig.entity.InsuranceGstTaxConfig;
import com.tayyarah.common.gstconfig.model.InsurenceGstTax;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.model.CommissionDetails;
import com.tayyarah.common.model.CurrencyConversionMap;
import com.tayyarah.common.util.CutandPayModel;
import com.tayyarah.common.util.IndianUnionTerritories;
import com.tayyarah.common.util.enums.CommonBookingStatusEnum;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.company.entity.Company;
import com.tayyarah.company.entity.CompanyConfig;
import com.tayyarah.insurance.entity.InsuranceOrderCustomerDetail;
import com.tayyarah.insurance.entity.InsuranceOrderRow;
import com.tayyarah.insurance.entity.InsuranceOrderRowCommission;
import com.tayyarah.insurance.entity.InsuranceOrderRowGstTax;
import com.tayyarah.insurance.entity.InsuranceOrderRowMarkup;
import com.tayyarah.insurance.exception.InsuranceErrorMessages;
import com.tayyarah.insurance.exception.InsuranceException;
import com.tayyarah.insurance.model.CreateInsurancePolicyRequest;
import com.tayyarah.insurance.model.InsuranceMarkUp;
import com.tayyarah.insurance.model.InsuranceMarkupCommissionDetails;
import com.tayyarah.insurance.model.TravellerDetails;
import com.tayyarah.user.entity.User;



public class InsuranceCommonUtil {
	public static final Logger logger = Logger.getLogger(InsuranceCommonUtil.class);
	public static CurrencyConversionMap buildCurrencyConversionMap(String currency,
			MoneyExchangeDao moneydao) {

		/// Get Default API Currency
		CurrencyConversionMap currencyConversionMap = new CurrencyConversionMap();
		String apiCurrency = TrawellTagConfig.DEFAULT_CURRENCY;
		currencyConversionMap.setApiCurrency(apiCurrency);
		Map<String, Double> currencyrate = null;
		try {			
			currencyrate = moneydao.getCurrencyRate(currency, apiCurrency);
		} catch (Exception e) {
			e.printStackTrace();
			throw new InsuranceException(ErrorCodeCustomerEnum.Exception,InsuranceErrorMessages.CREATEPOLICY_ERROR.getErrorMessage());
		}
		currencyConversionMap.setCurrencyrate(currencyrate);
		Double currencyValue =   currencyrate.get("value");
		BigDecimal curValue = new BigDecimal(currencyValue);
		currencyConversionMap.setCurrencyValue(currencyValue);
		currencyConversionMap.setCurValue(curValue);
		Map<String, Double> currencyrateNow = null;
		try {			
			currencyrateNow =  moneydao.getCurrencyRate(currency, apiCurrency);
		} catch (Exception e) {
			logger.error(e);
			throw new InsuranceException(ErrorCodeCustomerEnum.Exception,InsuranceErrorMessages.CREATEPOLICY_ERROR.getErrorMessage());
		}
		currencyConversionMap.setCurrencyrate1(currencyrateNow);
		Double currencyValueNow = currencyrateNow.get("value");
		currencyConversionMap.setCurrencyValue1(currencyValueNow);
		return currencyConversionMap;
	}

	public static long getPlanId(int planid){
		long planId = -1;
		switch (planid) {
		case CreateInsurancePolicyRequest.Smart_50K_Excluding:
			planId = 1;
			break;
		case CreateInsurancePolicyRequest.Smart_Domestic_Plan:
			planId = 2;
			break;
		case CreateInsurancePolicyRequest.Smart_50K_Including:
			planId = 3;
			break;
		default:
			break;
		}
		return planId;
	}

	public static InsurenceGstTax getInsurenceGstTax(CompanyConfig companyConfig,Company company,Company parentCompany){
		InsurenceGstTax insurenceGstTax = new InsurenceGstTax();
		BigDecimal CGST = new BigDecimal("0.0");
		BigDecimal SGST = new BigDecimal("0.0");
		BigDecimal IGST = new BigDecimal("0.0");
		BigDecimal UGST = new BigDecimal("0.0");
		BigDecimal totalGst = new BigDecimal("0.0");
		BigDecimal managementFee  = new BigDecimal("0.0");		
		BigDecimal convenienceFee  = new BigDecimal("0.0");	
		InsuranceGstTaxConfig insuranceGstTaxConfig = companyConfig.getInsuranceGstTaxConfig();
		if(insuranceGstTaxConfig != null){
			managementFee = insuranceGstTaxConfig.getManagementFee();
			convenienceFee = insuranceGstTaxConfig.getConvenienceFee()!=null?insuranceGstTaxConfig.getConvenienceFee():new BigDecimal("0.0");
			CGST = managementFee.divide(new BigDecimal("100.0")).multiply(insuranceGstTaxConfig.getCGST());
			boolean isParentCompanyUT = IndianUnionTerritories.isUnionter(parentCompany.getBillingstate().trim());
			boolean isBillingCompanyUT = IndianUnionTerritories.isUnionter(company.getBillingstate().trim());

			if(isParentCompanyUT && isBillingCompanyUT){
				UGST =  managementFee.divide(new BigDecimal("100.0")).multiply(insuranceGstTaxConfig.getUGST());
			}
			if(!company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim()) && IndianUnionTerritories.isUnionter(company.getBillingstate().trim())){
				UGST =  managementFee.divide(new BigDecimal("100.0")).multiply(insuranceGstTaxConfig.getUGST());
			}
			if(!isParentCompanyUT && !isBillingCompanyUT){
				if(company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim())){
					SGST =  managementFee.divide(new BigDecimal("100.0")).multiply(insuranceGstTaxConfig.getSGST());				
				}
			}
			if(isParentCompanyUT && !isBillingCompanyUT){
				if(!company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim()) && !IndianUnionTerritories.isUnionter(company.getBillingstate().trim())){
					IGST =  managementFee.divide(new BigDecimal("100.0")).multiply(insuranceGstTaxConfig.getIGST());		
				}
			}	

			totalGst = CGST.add(SGST).add(IGST).add(UGST);	
		}
		insurenceGstTax.setCGST(CGST);
		insurenceGstTax.setSGST(SGST);
		insurenceGstTax.setIGST(IGST);
		insurenceGstTax.setUGST(UGST);
		insurenceGstTax.setTotalTax(totalGst);
		insurenceGstTax.setConvenienceFee(convenienceFee);
		insurenceGstTax.setManagementFee(managementFee);		
		return insurenceGstTax;
	}

	public static InsuranceOrderRowGstTax createInsuranceOrderRowGstTax(InsuranceGstTaxConfig insuranceGstTaxConfig, InsuranceOrderRowGstTax insuranceOrderRowGstTax,Company company,Company parentCompany,int noofpassengers) {

		BigDecimal totalPassengers = new BigDecimal(noofpassengers);
		BigDecimal CGST = new BigDecimal("0.0");
		BigDecimal SGST = new BigDecimal("0.0");
		BigDecimal IGST = new BigDecimal("0.0");
		BigDecimal UGST = new BigDecimal("0.0");
		BigDecimal totalGst = new BigDecimal("0.0");
		BigDecimal managementFee  = new BigDecimal("0.0");
		boolean isParentCompanyUT = IndianUnionTerritories.isUnionter(parentCompany.getBillingstate().trim());
		boolean isBillingCompanyUT = IndianUnionTerritories.isUnionter(company.getBillingstate().trim());
		managementFee = insuranceGstTaxConfig.getManagementFee().multiply(totalPassengers);

		if(isParentCompanyUT && isBillingCompanyUT){
			CGST = insuranceGstTaxConfig.getCGST();
			UGST = insuranceGstTaxConfig.getUGST();
		}
		if(!company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim()) && IndianUnionTerritories.isUnionter(company.getBillingstate().trim())){
			CGST = insuranceGstTaxConfig.getCGST();
			UGST = insuranceGstTaxConfig.getUGST();
		}
		if(!isParentCompanyUT && !isBillingCompanyUT){
			if(company.getBillingstate().equalsIgnoreCase(parentCompany.getBillingstate())){
				CGST = insuranceGstTaxConfig.getCGST();
				SGST =  insuranceGstTaxConfig.getSGST();				
			}
		}
		if(isParentCompanyUT && !isBillingCompanyUT){
			if(!company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim()) && !IndianUnionTerritories.isUnionter(company.getBillingstate().trim())){
				IGST =  insuranceGstTaxConfig.getIGST();		
			}
		}				
		totalGst = CGST.add(SGST).add(IGST).add(UGST);	
		insuranceOrderRowGstTax.setCGST(CGST);
		insuranceOrderRowGstTax.setSGST(SGST);
		insuranceOrderRowGstTax.setIGST(IGST);
		insuranceOrderRowGstTax.setUGST(UGST);
		insuranceOrderRowGstTax.setVersion(1);
		insuranceOrderRowGstTax.setManagementFee(managementFee);
		insuranceOrderRowGstTax.setConvenienceFee(insuranceGstTaxConfig.getConvenienceFee());
		insuranceOrderRowGstTax.setTotalGst(totalGst);
		insuranceOrderRowGstTax.setApplicableFare(insuranceGstTaxConfig.getApplicableFare());
		insuranceOrderRowGstTax.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
		return insuranceOrderRowGstTax;
	}

	public static List<InsuranceOrderCustomerDetail> createInsuranceOrderCustomerDetailList(CreateInsurancePolicyRequest createInsurancePolicyRequest,InsuranceOrderRow insuranceOrderRow){
		List<InsuranceOrderCustomerDetail> insuranceOrderCustomerDetailList = new ArrayList<>();
		for (TravellerDetails travellerDetails : createInsurancePolicyRequest.getTravellerDetails()) {
			InsuranceOrderCustomerDetail insuranceOrderCustomerDetail = new InsuranceOrderCustomerDetail();
			insuranceOrderCustomerDetail.setPaxId(travellerDetails.getPaxId());
			insuranceOrderCustomerDetail.setAddress(travellerDetails.getAddress());
			insuranceOrderCustomerDetail.setAge(travellerDetails.getAge());
			insuranceOrderCustomerDetail.setCity(travellerDetails.getCity());
			insuranceOrderCustomerDetail.setCountry(travellerDetails.getCountry());
			insuranceOrderCustomerDetail.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
			insuranceOrderCustomerDetail.setDateOfBirth(travellerDetails.getDateOfBirth());
			insuranceOrderCustomerDetail.setDistrict(travellerDetails.getDistrict());
			insuranceOrderCustomerDetail.setEmail(travellerDetails.getEmailAddress());
			insuranceOrderCustomerDetail.setFirstName(travellerDetails.getFirstName());
			insuranceOrderCustomerDetail.setLastName(travellerDetails.getLastName());
			insuranceOrderCustomerDetail.setTitle(travellerDetails.getTitle());
			insuranceOrderCustomerDetail.setMobile(travellerDetails.getMobile());
			insuranceOrderCustomerDetail.setNominee(travellerDetails.getNominee());
			insuranceOrderCustomerDetail.setPassportNumber(travellerDetails.getPassportNumber());
			insuranceOrderCustomerDetail.setPinCode(travellerDetails.getPinCode());
			insuranceOrderCustomerDetail.setRelationShipWithNominee(travellerDetails.getRelationShipWithNominee());
			insuranceOrderCustomerDetail.setState(travellerDetails.getState());
			insuranceOrderCustomerDetail.setInsuranceOrderRow(insuranceOrderRow);
			insuranceOrderCustomerDetailList.add(insuranceOrderCustomerDetail);
		}
		return insuranceOrderCustomerDetailList;
	}
	public static OrderCustomer createOrderCustomer(CreateInsurancePolicyRequest createInsurancePolicyRequest,AppKeyVo appKeyVo,String orderId){
		OrderCustomer orderCustomer = new OrderCustomer();
		int i = 0;
		for (TravellerDetails travellerDetails : createInsurancePolicyRequest.getTravellerDetails()) {
			if(i == 0){
				orderCustomer.setAddress(travellerDetails.getAddress());
				orderCustomer.setAge(travellerDetails.getAge());
				orderCustomer.setCity(travellerDetails.getCity());
				orderCustomer.setCountryId("91");
				orderCustomer.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
				orderCustomer.setBirthday(travellerDetails.getDateOfBirth());			
				orderCustomer.setEmail(travellerDetails.getEmailAddress());
				orderCustomer.setFirstName(travellerDetails.getFirstName());
				orderCustomer.setLastName(travellerDetails.getLastName());
				orderCustomer.setTitle(travellerDetails.getTitle());
				orderCustomer.setMobile(travellerDetails.getMobile());				
				orderCustomer.setState(travellerDetails.getState());
				orderCustomer.setCompanyId(appKeyVo.getCompanyId());
				orderCustomer.setConfigId(appKeyVo.getConfigId());
				orderCustomer.setBookingType("Insurance");
				orderCustomer.setCreatedByUserId(Integer.parseInt(InsuranceCommonUtil.checkGetEmulatedUserById(createInsurancePolicyRequest)));
				orderCustomer.setOrderId(orderId);
			}
			i++;

		}
		return orderCustomer;
	}
	public static List<InsuranceOrderRowMarkup> getInsuranceMarkupDetail(InsuranceMarkupCommissionDetails markupCommissionDetails,InsuranceOrderRow insuranceOrderRow)  {		
		List<InsuranceOrderRowMarkup> insuranceOrderRowMarkupList = new ArrayList<InsuranceOrderRowMarkup>();
		Set<String> companyIdset = new HashSet<String>();
		companyIdset = markupCommissionDetails.getCompanyMarkupMap().keySet();
		for(String companyId:companyIdset){
			InsuranceOrderRowMarkup insuranceOrderRowMarkup = new InsuranceOrderRowMarkup();
			BigDecimal markupAmount = markupCommissionDetails.getCompanyMarkupMap().get(companyId);
			insuranceOrderRowMarkup.setCompanyId(companyId);
			insuranceOrderRowMarkup.setMarkUp(markupAmount);
			insuranceOrderRowMarkup.setInsuranceOrderRow(insuranceOrderRow);
			insuranceOrderRowMarkupList.add(insuranceOrderRowMarkup);
		}
		return insuranceOrderRowMarkupList;
	}

	public static List<InsuranceOrderRowCommission> getCommissionDetails(InsuranceMarkupCommissionDetails markupCommissionDetails,InsuranceOrderRow insuranceOrderRow) {
		List<InsuranceOrderRowCommission> insuranceOrderRowCommissionList = new ArrayList<InsuranceOrderRowCommission>();	
		for(CommissionDetails commissionDetails : markupCommissionDetails.getCommissionDetailslist()){
			InsuranceOrderRowCommission insuranceOrderRowCommission = new InsuranceOrderRowCommission();
			insuranceOrderRowCommission.setCompanyId(commissionDetails.getCompanyId());
			insuranceOrderRowCommission.setInsuranceOrderRow(insuranceOrderRow);
			insuranceOrderRowCommission.setCommission(commissionDetails.getCommissionAmount());
			insuranceOrderRowCommission.setCommissionType(commissionDetails.getCommissionType());
			insuranceOrderRowCommission.setRateType(commissionDetails.getRateType());		
			insuranceOrderRowCommissionList.add(insuranceOrderRowCommission);
		}
		return insuranceOrderRowCommissionList;
	}
	public static BigDecimal getTotalMarkup(InsuranceMarkupCommissionDetails markupCommissionDetails)  {		
		Set<String> compnyIdset=new HashSet<String>();
		compnyIdset = markupCommissionDetails.getCompanyMarkupMap().keySet();
		BigDecimal totalmarkupAmount=new BigDecimal("0");
		for(String companyId:compnyIdset){
			BigDecimal markupAmount = markupCommissionDetails.getCompanyMarkupMap().get(companyId);		
			totalmarkupAmount=totalmarkupAmount.add(markupAmount);
		}
		return totalmarkupAmount;
	}
	public static LinkedList<Company> getParentCompanyBottomToTop(int companyId, CompanyDao CDAO) {
		Company company= CDAO.getCompany(companyId);
		Company companyTemp=company;
		LinkedList<Company> companies= new LinkedList<>();
		companies.add(companyTemp);
		while(companyTemp!=null && companyTemp.getCompanyRole()!=null && !companyTemp.getCompanyRole().isSuperUser())
		{
			companyTemp =CDAO.getParentCompany(companyTemp);
			companies.add(companyTemp);
		}
		return companies;

	}
	public static Map<Integer,CutandPayModel> getCutandPayModelUsers(BigDecimal totalpayableAmt,BigDecimal nooftravellers,CompanyConfig companyConfig,List<User> userList,Map<String, List<InsuranceMarkUp>> markups,List<CommissionDetails> commissions,InsuranceOrderRow insuranceOrderRow){
		Map<Integer,CutandPayModel> cutandpayMap = new LinkedHashMap<>();		
		try{
			BigDecimal payableamt = totalpayableAmt;			
			BigDecimal totalPassengers = nooftravellers;
			Map<String, BigDecimal> insuranceMarkups =  new LinkedHashMap<>();		
			Map<String, BigDecimal> insuranceCommissions =  new LinkedHashMap<>();		

			for (Entry<String, List<InsuranceMarkUp>> entry : markups.entrySet()) {
				List<InsuranceMarkUp> insuranceMarkUplist = entry.getValue();
				String companyid = entry.getKey();
				if (insuranceMarkUplist != null) {
					for (int i = 0; i < insuranceMarkUplist.size(); i++) {
						InsuranceMarkUp insuranceMarkUp = insuranceMarkUplist.get(i);
						BigDecimal MarkupValue = insuranceMarkUp.getMarkupAmt();					
						MarkupValue = MarkupValue.multiply(totalPassengers);
						insuranceMarkups.put(companyid, MarkupValue);
					}
				}
			}

			for(CommissionDetails commissionDetails: commissions){
				BigDecimal commissionAmt = commissionDetails.getCommissionAmount();
				String companyId = commissionDetails.getCompanyId();
				insuranceCommissions.put(companyId, commissionAmt);
			}


			for (User payableUser : userList) {
				BigDecimal markupAmount = new BigDecimal(0);
				BigDecimal commisionAmount = new BigDecimal(0);
				markupAmount = insuranceMarkups!=null && insuranceMarkups.size()>0 && insuranceMarkups.get(String.valueOf(payableUser.getCompanyid()))!=null?insuranceMarkups.get(String.valueOf(payableUser.getCompanyid())):new BigDecimal(0);
				commisionAmount = insuranceCommissions!=null && insuranceCommissions.get(String.valueOf(payableUser.getCompanyid()))!=null ?insuranceCommissions.get(String.valueOf(payableUser.getCompanyid())):new BigDecimal(0);
				BigDecimal busMarksForAllPassengers = markupAmount.multiply(totalPassengers);
				CutandPayModel cutandpay = new CutandPayModel();
				if(String.valueOf(payableUser.getId()).equalsIgnoreCase(insuranceOrderRow.getUserId())){
					BigDecimal payableamtInner = totalpayableAmt.subtract(commisionAmount);
					cutandpay.setUserId(String.valueOf(payableUser.getId()));  
					cutandpay.setPayableAmount(payableamtInner);
					cutandpay.setBookingRemarks(CommonBookingStatusEnum.INSURANCE_REMARKS.getMessage());
					cutandpay.setBookingStatus(true);
				}
				else{
					payableamt = payableamt.subtract(busMarksForAllPassengers);
					payableamt = payableamt.subtract(commisionAmount);
					cutandpay.setUserId(String.valueOf(payableUser.getId()));  
					cutandpay.setPayableAmount(payableamt);
					cutandpay.setBookingRemarks(CommonBookingStatusEnum.INSURANCE_REMARKS.getMessage());
					cutandpay.setBookingStatus(true);
				}
				cutandpayMap.put(payableUser.getId(), cutandpay);
			}
		}catch(Exception e){

		}
		return cutandpayMap;
	}


	public static String checkGetEmulatedUserById(CreateInsurancePolicyRequest createInsurancePolicyRequest) {
		if(createInsurancePolicyRequest.getIsEmulateFlag())
		{
			return createInsurancePolicyRequest.getEmulateByUserId();
		}
		return createInsurancePolicyRequest.getUserId();
	}
	public static String getInsuranceFormatDate(){
		String dateString = "";
		try{
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date date = new Date();
			dateString = dateFormat.format(date);
		}catch(Exception e){
			logger.error("getInsuranceFormatDate " +e.getMessage());
		}
		return dateString;
	}
	public static Date getInsuranceFormatDateFromString(String traveldate){
		Date date = new Date();
		try{
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");		
			date = dateFormat.parse(traveldate);
			String datestring = dateFormat1.format(date);
			date = dateFormat1.parse(datestring);
		}catch(Exception e){
			logger.error("getInsuranceFormatDateFromString " +e.getMessage());
		}
		return date;
	}
	public static byte[] convertObjectToByteArray(Object object)  {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] bytearray = null;
		try{
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(object);	
			bytearray = byteArrayOutputStream.toByteArray();
		}catch(Exception e){
			logger.error("convertObjectToByteArray Exception " + e);
		}		
		return bytearray ;
	}
	public static int findClosestPremiumDay(SortedSet<Integer> daysarray,int find) {
		int closest = daysarray.first();
		int distance = Math.abs(closest - find);
		for(int i: daysarray) {
			int distanceI = Math.abs(i - find);
			if(distance > distanceI) {
				if(i >= find){
					closest = i;
					distance = distanceI;
				}
			}
		}
		return closest;
	}
	public static int getNoofStayDays(String depdate,String arrivaldate) throws java.text.ParseException
	{
		DateTimeFormatter dateStringFormat = DateTimeFormat
				.forPattern("yyyy-MM-dd");
		DateTime firstTime = dateStringFormat.parseDateTime(depdate);
		DateTime secondTime = dateStringFormat.parseDateTime(arrivaldate);
		int days = Days.daysBetween(new LocalDate(firstTime),
				new LocalDate(secondTime)).getDays();		
		return days;	
	}
}
