package com.tayyarah.insurance.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.tayyarah.company.entity.CompanyConfig;
import com.tayyarah.insurance.dao.InsuranceCommonDao;
import com.tayyarah.insurance.entity.InsuranceOrderRowCommission;
import com.tayyarah.insurance.entity.TrawellTagPremiumChart;
import com.tayyarah.insurance.model.CreateInsurancePolicyRequest;
import com.tayyarah.insurance.model.InsuranceMarkUp;
import com.tayyarah.insurance.model.InsuranceMarkupCommissionDetails;
import com.tayyarah.insurance.model.PriceDetail;






public class InsuranceMarkUpHelper {
	static final Logger logger = Logger.getLogger(InsuranceMarkUpHelper.class);
	
	public static void applyMarkupOnSummary(Map<String,List<InsuranceMarkUp>> markupMap,String originCountry,String destinationCountry,String dayLimit,InsuranceCommonDao insuranceCommonDao){
	    int planId = -1;	    
	    String ageLimt = "70";
		if(originCountry.equalsIgnoreCase("India") && destinationCountry.equalsIgnoreCase("India")){
			planId = CreateInsurancePolicyRequest.Smart_Domestic_Plan;
		}
		if(originCountry.equalsIgnoreCase("India") && !destinationCountry.equalsIgnoreCase("USA") && !destinationCountry.equalsIgnoreCase("Canada")){
			planId = CreateInsurancePolicyRequest.Smart_50K_Excluding;
		}
		if(originCountry.equalsIgnoreCase("India") && destinationCountry.equalsIgnoreCase("USA") && destinationCountry.equalsIgnoreCase("Canada")){
			planId = CreateInsurancePolicyRequest.Smart_50K_Including;
		}
		
		TrawellTagPremiumChart trawellTagPremiumChart = insuranceCommonDao.getTrawellTagPremiumChart(dayLimit, ageLimt, planId);
		BigDecimal totalPriceWithOutMarkUp = trawellTagPremiumChart.getPremiumAmount();
		BigDecimal totalPrice = totalPriceWithOutMarkUp;
		Map<String, List<InsuranceMarkUp>> markUpConfiglistMap = new TreeMap<String,List<InsuranceMarkUp>>();
		if(markupMap !=null && markupMap.size() > 0){
			markUpConfiglistMap.putAll(markupMap);
			for (Map.Entry<String,List<InsuranceMarkUp>> entry : markUpConfiglistMap.entrySet()) {
				List<InsuranceMarkUp> markConfigList = entry.getValue();	
				if(markConfigList!=null && markConfigList.size() > 0){
					for (InsuranceMarkUp insuranceMarkUp : markConfigList) {
						boolean isAccumulative = insuranceMarkUp.isAccumulative();
						boolean isFixedAmount = insuranceMarkUp.isFixedAmount();	
						if (isMarkupAppliable(insuranceMarkUp)) {
							BigDecimal markupAmt = insuranceMarkUp.getMarkupAmt();
							if(!isAccumulative){
								if(isFixedAmount) {
									totalPrice = totalPrice.add(markupAmt);
								}else{
									BigDecimal commission = totalPriceWithOutMarkUp.multiply(markupAmt).divide(new BigDecimal("100"));
									totalPrice = totalPrice.add(commission);
								}
								break;
							}else if(isAccumulative){
								if(isFixedAmount) {
									totalPrice = totalPrice.add(markupAmt);
								}else{
									BigDecimal commission = totalPriceWithOutMarkUp.multiply(markupAmt).divide(new BigDecimal("100"));
									totalPrice = totalPrice.add(commission);
								}
							}
						}
					}
				}
			}
		}
		
	}
	
	public static void applyMarkupOnPolicyResponse(PriceDetail priceDetail,Map<String,List<InsuranceMarkUp>> markupMap,CompanyConfig companyConfig){
		BigDecimal totalmarkupAmt = new BigDecimal(0);
		BigDecimal totalPriceWithOutMarkUp = priceDetail.getApiTotalPrice();
		BigDecimal totalPrice = totalPriceWithOutMarkUp;
		BigDecimal currentCompanyMarkup = new BigDecimal(0);
		Map<String, List<InsuranceMarkUp>> markUpConfiglistMap = new TreeMap<String,List<InsuranceMarkUp>>();
		if(markupMap !=null && markupMap.size() > 0){
			markUpConfiglistMap.putAll(markupMap);
			for (Map.Entry<String,List<InsuranceMarkUp>> entry : markUpConfiglistMap.entrySet()) {
				List<InsuranceMarkUp> markConfigList = entry.getValue();
				int currentCompany = Integer.parseInt(entry.getKey());
				if(markConfigList!=null && markConfigList.size() > 0){
					for (InsuranceMarkUp insuranceMarkUp : markConfigList) {
						boolean isAccumulative = insuranceMarkUp.isAccumulative();
						boolean isFixedAmount = insuranceMarkUp.isFixedAmount();	
						if (isMarkupAppliable(insuranceMarkUp)) {
							BigDecimal markupAmt = insuranceMarkUp.getMarkupAmt();
							if(!isAccumulative){
								if(isFixedAmount) {
									totalPrice = totalPrice.add(markupAmt);
								}else{
									BigDecimal commission = totalPriceWithOutMarkUp.multiply(markupAmt).divide(new BigDecimal("100"));
									totalPrice = totalPrice.add(commission);
								}
								break;
							}else if(isAccumulative){
								if(isFixedAmount) {
									totalPrice = totalPrice.add(markupAmt);
								}else{
									BigDecimal commission = totalPriceWithOutMarkUp.multiply(markupAmt).divide(new BigDecimal("100"));
									totalPrice = totalPrice.add(commission);
								}
							}
							if(companyConfig.getCompany_id() == currentCompany)
							{
								currentCompanyMarkup = currentCompanyMarkup.add(markupAmt);
							}
							totalmarkupAmt = totalmarkupAmt.add(markupAmt);
						}
					}
				}
			}
			
			priceDetail.setTotalPrice(totalPrice.setScale(2, RoundingMode.UP));
			BigDecimal totalTaxes = priceDetail.getApiTaxes().add(totalmarkupAmt).setScale(2, RoundingMode.UP);
			priceDetail.setTaxes(totalTaxes);
			if(companyConfig!=null){
				if(companyConfig.getCompanyConfigType().isB2E()){
					priceDetail.setTotalPayableAmount(priceDetail.getTotalPrice().setScale(2, RoundingMode.UP));
				}else if(companyConfig.getCompanyConfigType().isB2B()){
					priceDetail.setTotalPayableAmount(priceDetail.getTotalPrice().setScale(2, RoundingMode.UP).subtract(currentCompanyMarkup));
				}else{
					priceDetail.setTotalPayableAmount(priceDetail.getTotalPrice().setScale(2, RoundingMode.UP));
				}

			}
			
		}
		
	}
	
	public static BigDecimal applyMarkupBeforeCallAPI(BigDecimal totalPricewithoutmarkup,Map<String,List<InsuranceMarkUp>> markupMap,CompanyConfig companyConfig){
		BigDecimal totalpayableAmt = new BigDecimal(0);
		BigDecimal totalmarkupAmt = new BigDecimal(0);
		BigDecimal totalPriceWithOutMarkUp = totalPricewithoutmarkup;
		BigDecimal totalPrice = totalPriceWithOutMarkUp;
		BigDecimal currentCompanyMarkup = new BigDecimal(0);
		Map<String, List<InsuranceMarkUp>> markUpConfiglistMap = new TreeMap<String,List<InsuranceMarkUp>>();
		if(markupMap !=null && markupMap.size() > 0){
			markUpConfiglistMap.putAll(markupMap);
			for (Map.Entry<String,List<InsuranceMarkUp>> entry : markUpConfiglistMap.entrySet()) {
				List<InsuranceMarkUp> markConfigList = entry.getValue();
				int currentCompany = Integer.parseInt(entry.getKey());
				if(markConfigList!=null && markConfigList.size() > 0){
					for (InsuranceMarkUp insuranceMarkUp : markConfigList) {
						boolean isAccumulative = insuranceMarkUp.isAccumulative();
						boolean isFixedAmount = insuranceMarkUp.isFixedAmount();	
						if (isMarkupAppliable(insuranceMarkUp)) {
							BigDecimal markupAmt = insuranceMarkUp.getMarkupAmt();
							if(!isAccumulative){
								if(isFixedAmount) {
									totalPrice = totalPrice.add(markupAmt);
								}else{
									BigDecimal commission = totalPriceWithOutMarkUp.multiply(markupAmt).divide(new BigDecimal("100"));
									totalPrice = totalPrice.add(commission);
								}
								break;
							}else if(isAccumulative){
								if(isFixedAmount) {
									totalPrice = totalPrice.add(markupAmt);
								}else{
									BigDecimal commission = totalPriceWithOutMarkUp.multiply(markupAmt).divide(new BigDecimal("100"));
									totalPrice = totalPrice.add(commission);
								}
							}
							if(companyConfig.getCompany_id() == currentCompany)
							{
								currentCompanyMarkup = currentCompanyMarkup.add(markupAmt);
							}
							totalmarkupAmt = totalmarkupAmt.add(markupAmt);
						}
					}
				}
			}
			
			
			if(companyConfig!=null){
				if(companyConfig.getCompanyConfigType().isB2E()){
					totalpayableAmt = totalPrice.setScale(2, RoundingMode.UP);
				}else if(companyConfig.getCompanyConfigType().isB2B()){
					totalpayableAmt = totalPrice.setScale(2, RoundingMode.UP).subtract(currentCompanyMarkup); 
				}else{
					totalpayableAmt = totalPrice.setScale(2, RoundingMode.UP);
				}

			}
			
		}
		return totalpayableAmt;
	}
	
	public static void getMarkupAmtForEachCompany(Map<String,List<InsuranceMarkUp>> markupMap,
			BigDecimal totalPricewithoutmarkup,CreateInsurancePolicyRequest  createInsurancePolicyRequest,InsuranceMarkupCommissionDetails markupCommissionDetails) throws Exception {

		BigDecimal priceWithoutMarkup = totalPricewithoutmarkup;
		BigDecimal totalPrice = priceWithoutMarkup;
		Map<String, BigDecimal> companyMarkupMap = new HashMap<String, BigDecimal>();	
		Map<String, List<InsuranceMarkUp>> sortedMarkUpConfiglistMap = new TreeMap<String,List<InsuranceMarkUp>>();
		if(markupMap!=null && markupMap.size()>0)
		{
			sortedMarkUpConfiglistMap.putAll(markupMap);
			if(sortedMarkUpConfiglistMap!=null && sortedMarkUpConfiglistMap.size()>0)
			{
				for (Map.Entry<String,List<InsuranceMarkUp>> entry : sortedMarkUpConfiglistMap.entrySet()) {
					List<InsuranceMarkUp> insuranceMarkUpConfiglist = entry.getValue();				
					boolean result = false;
					BigDecimal Markup = BigDecimal.ZERO;
					if (insuranceMarkUpConfiglist != null) {
						int k = 0;					
						for (int i = 0; i < insuranceMarkUpConfiglist.size(); i++) {
							InsuranceMarkUp insuranceMarkUp = insuranceMarkUpConfiglist.get(i);
							boolean accumulative = insuranceMarkUp.isAccumulative();							
							boolean fixedAmount = insuranceMarkUp.isFixedAmount();
							BigDecimal markupAmt = insuranceMarkUp.getMarkupAmt();
							BigDecimal totalpassenger = new BigDecimal(createInsurancePolicyRequest.getTravellerDetails().size());
							markupAmt = markupAmt.multiply(totalpassenger);
							if(isMarkupAppliable(insuranceMarkUp)) {
								if (k == 0 && !accumulative) {
									if (fixedAmount) {									
										Markup = Markup.add(markupAmt);										
									} else {
										Markup = Markup.add((priceWithoutMarkup
												.multiply(markupAmt))
												.divide(new BigDecimal("100")));

										totalPrice = totalPrice.add((priceWithoutMarkup
												.multiply(markupAmt))
												.divide(new BigDecimal("100")));										
									}
									break;
								} else if (k != 0 && !accumulative&&result) {
									continue;
								}
								else if (k != 0 && !accumulative&&!result) {

									if (fixedAmount) {									
										Markup = Markup.add(markupAmt);										
									} else {
										Markup = Markup.add((priceWithoutMarkup
												.multiply(markupAmt))
												.divide(new BigDecimal("100")));
										totalPrice = totalPrice.add((priceWithoutMarkup
												.multiply(markupAmt))
												.divide(new BigDecimal("100")));										
									}
									break;

								}else if (accumulative){
									if (fixedAmount) {
										totalPrice = totalPrice.add(markupAmt);
										Markup = Markup.add(markupAmt);										
									} else {
										Markup = Markup.add((priceWithoutMarkup
												.multiply(markupAmt))
												.divide(new BigDecimal("100")));
										totalPrice = totalPrice.add((priceWithoutMarkup
												.multiply(markupAmt))
												.divide(new BigDecimal("100")));										
									}
									result=true;
								}
								k++;
							}
						}
					}
					companyMarkupMap.put(entry.getKey(), Markup);
				}
			}
		}
		markupCommissionDetails.setCompanyMarkupMap(companyMarkupMap);
	}
	public static void getCommissionWithMarkupValuesForEachCompany(List<InsuranceOrderRowCommission> insuranceOrderRowCommissions,Map<String,List<InsuranceMarkUp>> markupMap, 
			BigDecimal totalPriceWithOutMarkup,InsuranceMarkupCommissionDetails markupCommissionDetails,CreateInsurancePolicyRequest createInsurancePolicyRequest) throws Exception {

		BigDecimal priceWithoutMarkup = totalPriceWithOutMarkup;	
		BigDecimal totalAmount = priceWithoutMarkup;	
		if(insuranceOrderRowCommissions!=null && insuranceOrderRowCommissions.size()>0)
		{
			if(insuranceOrderRowCommissions.size()>1)
				Collections.sort(insuranceOrderRowCommissions, new companyIdComparator());

			for (int m = 0; m < insuranceOrderRowCommissions.size(); m++) {
				InsuranceOrderRowCommission insuranceOrderRowCommission = insuranceOrderRowCommissions.get(m);
				BigDecimal companycommissionAmount = new BigDecimal("0.00");
				if(insuranceOrderRowCommission.getRateType().equalsIgnoreCase("Commission")){
					if(insuranceOrderRowCommission.getCommissionType().equalsIgnoreCase("Fixed"))
					{
						companycommissionAmount = insuranceOrderRowCommission.getCommission();
					}					
					else{
						companycommissionAmount = totalAmount.multiply(insuranceOrderRowCommission.getCommission()).divide(new BigDecimal("100"),2,RoundingMode.HALF_UP);
					}
				}
				insuranceOrderRowCommission.setCommissionAmountValue(companycommissionAmount);
				if(m == insuranceOrderRowCommissions.size()-1){
					break;
				}

				BigDecimal Markup = BigDecimal.ZERO;			
				boolean result=false;
				List<InsuranceMarkUp> insuranceMarkUpList = markupMap.get(insuranceOrderRowCommission.getCompanyId());
				if (insuranceMarkUpList != null && insuranceMarkUpList.size()>0) {
					int k = 0;				
					for (int i = 0; i < insuranceMarkUpList.size(); i++) {
						InsuranceMarkUp insuranceMarkUp = insuranceMarkUpList.get(i);
						boolean accumulative = insuranceMarkUp.isAccumulative();
						boolean fixedAmount = insuranceMarkUp.isFixedAmount();
						BigDecimal markupAmt = insuranceMarkUp.getMarkupAmt();
						if(isMarkupAppliable(insuranceMarkUp)) {

							if (k == 0 && !accumulative) {

								if (fixedAmount) {
									totalAmount = totalAmount.add(markupAmt);
								} else {
									totalAmount = totalAmount.add((priceWithoutMarkup
											.multiply(markupAmt))
											.divide(new BigDecimal("100")));								
								}
								break;
							} else if (k != 0 && !accumulative&&result) {
								continue;
							}
							else if (k != 0 && !accumulative&&!result) {
								if (fixedAmount) {
									totalAmount = totalAmount.add(markupAmt);
								} else {
									totalAmount = totalAmount.add((priceWithoutMarkup
											.multiply(markupAmt))
											.divide(new BigDecimal("100")));								
								}
								break;

							}else if (accumulative){
								if (fixedAmount) {
									if (fixedAmount) {
										totalAmount = totalAmount.add(markupAmt);
									} else {
										totalAmount = totalAmount.add((priceWithoutMarkup
												.multiply(markupAmt))
												.divide(new BigDecimal("100")));								
									}
									result=true;
								}

								k++;
							}
						}
					}
				}
			}
		}

	}
	public static boolean isMarkupAppliable(InsuranceMarkUp insuranceMarkUp){
		boolean isAppliable = false;
		try{
			if(insuranceMarkUp!=null){				
				isAppliable = true;
				}
				else{
					isAppliable = false;
				}
			
		}catch(Exception e){
			logger.error("isMarkupAppliable Exception " +e.getMessage());
		}

		return isAppliable;

	}

	
}
class companyIdComparator implements Comparator<Object> {
	@Override
	public int compare(Object o1, Object o2) {
		InsuranceOrderRowCommission A1 = (InsuranceOrderRowCommission) o1;
		InsuranceOrderRowCommission A2 = (InsuranceOrderRowCommission) o2;
		int a1ID= A1.getCompanyId()!=null && !A1.getCompanyId().equalsIgnoreCase("")?Integer.parseInt(A1.getCompanyId()):0;
		int a2ID=A2.getCompanyId()!=null && A2.getCompanyId().equalsIgnoreCase("")?Integer.parseInt(A2.getCompanyId()):0;	
		if(a1ID>a2ID){
			return 1;
		}else if(a1ID<a2ID){
			return -1;
		}else{
			return 0;
		}
	}
}
