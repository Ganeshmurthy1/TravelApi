package com.tayyarah.bus.util;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.tayyarah.bus.entity.BusOrderRowCommission;
import com.tayyarah.bus.model.AvailableBus;
import com.tayyarah.bus.model.BlockFareDetail;
import com.tayyarah.bus.model.BusBlockTicketRequest;
import com.tayyarah.bus.model.BusLayoutRequest;
import com.tayyarah.bus.model.BusMarkUpConfig;
import com.tayyarah.bus.model.BusMarkupCommissionDetails;
import com.tayyarah.bus.model.BusSearchRequest;
import com.tayyarah.bus.model.SeatFare;
import com.tayyarah.common.util.AmountRoundingModeUtil;
import com.tayyarah.company.entity.CompanyConfig;




public class BusMarkupHelper {
	static final Logger logger = Logger.getLogger(BusMarkupHelper.class);

	public static void applyMarkupOnSearchResponse(Map<String,List<BusMarkUpConfig>> markupMap,AvailableBus availableBus,BusSearchRequest busSearchRequest){
		String priceWithOutMarkUp = availableBus.getFare().getPriceWithOutMarkup();
		if(priceWithOutMarkUp.contains(",")){
			String[] prices = BusCommonUtil.getStringArrayFromString(priceWithOutMarkUp,",");
			StringBuffer totalPrices = new StringBuffer(); 
			int i = 0;
			for (String price : prices) {
				BigDecimal totalPriceWithOutMarkUp = new BigDecimal(price.trim());
				BigDecimal totalPrice = totalPriceWithOutMarkUp;
				Map<String, List<BusMarkUpConfig>> markUpConfiglistMap = new TreeMap<String,List<BusMarkUpConfig>>();
				if(markupMap !=null && markupMap.size() > 0){
					markUpConfiglistMap.putAll(markupMap);
					for (Map.Entry<String,List<BusMarkUpConfig>> entry : markUpConfiglistMap.entrySet()) {
						List<BusMarkUpConfig> markConfigList = entry.getValue();	
						if(markConfigList!=null && markConfigList.size() > 0){
							for (BusMarkUpConfig busMarkUpConfig : markConfigList) {
								boolean isAccumulative = busMarkUpConfig.isAccumulative();
								boolean isFixedAmount = busMarkUpConfig.isFixedAmount();						
								if (isMarkupAppliable(busMarkUpConfig, availableBus.getOperatorName(),busSearchRequest.getOrigin(),busSearchRequest.getDestination(),busSearchRequest.getOnwardDate(),busSearchRequest.getReturnDate() )) {
									BigDecimal markupAmt = busMarkUpConfig.getMarkupAmt();
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
				totalPrices.append(totalPrice.toString());
				if(i != prices.length - 1)
					totalPrices.append(",");

				i++;
			}

			availableBus.getFare().setBookingPrice(totalPrices.toString());

		}else{
			BigDecimal totalPriceWithOutMarkUp = new BigDecimal(availableBus.getFare().getPriceWithOutMarkup());
			BigDecimal totalPrice = totalPriceWithOutMarkUp;
			Map<String, List<BusMarkUpConfig>> markUpConfiglistMap = new TreeMap<String,List<BusMarkUpConfig>>();
			if(markupMap !=null && markupMap.size() > 0){
				markUpConfiglistMap.putAll(markupMap);
				for (Map.Entry<String,List<BusMarkUpConfig>> entry : markUpConfiglistMap.entrySet()) {
					List<BusMarkUpConfig> markConfigList = entry.getValue();	
					if(markConfigList!=null && markConfigList.size() > 0){
						for (BusMarkUpConfig busMarkUpConfig : markConfigList) {
							boolean isAccumulative = busMarkUpConfig.isAccumulative();
							boolean isFixedAmount = busMarkUpConfig.isFixedAmount();						
							if (isMarkupAppliable(busMarkUpConfig, availableBus.getOperatorName(),busSearchRequest.getOrigin(),busSearchRequest.getDestination(),busSearchRequest.getOnwardDate(),busSearchRequest.getReturnDate() )) {
								BigDecimal markupAmt = busMarkUpConfig.getMarkupAmt();
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

			availableBus.getFare().setBookingPrice(totalPrice.toString());	
		}

	}

	public static void applyMarkupOnSeatResponse(Map<String,List<BusMarkUpConfig>> markupMap,SeatFare fare,BusLayoutRequest busLayoutRequest,CompanyConfig companyConfig){
		BigDecimal priceWithoutMarkup = fare.getPriceWithOutMarkup();	
		BigDecimal totalPrice = priceWithoutMarkup;
		Map<String, List<BusMarkUpConfig>> markUpConfiglistMap = new TreeMap<String,List<BusMarkUpConfig>>();
		BigDecimal currentCompanyMarkup = new BigDecimal(0);
		if(markupMap !=null && markupMap.size() > 0){
			markUpConfiglistMap.putAll(markupMap);
			for (Map.Entry<String,List<BusMarkUpConfig>> entry : markUpConfiglistMap.entrySet()) {
				List<BusMarkUpConfig> markConfigList = entry.getValue();	
				int currentCompany = Integer.parseInt(entry.getKey());
				if(markConfigList!=null && markConfigList.size() > 0){
					for (BusMarkUpConfig busMarkUpConfig : markConfigList) {
						boolean isAccumulative = busMarkUpConfig.isAccumulative();
						boolean isFixedAmount = busMarkUpConfig.isFixedAmount();						
						if(isMarkupAppliable(busMarkUpConfig,"",busLayoutRequest.getOrigin(),busLayoutRequest.getDestination(),busLayoutRequest.getOnwardDate(),"")) {
							BigDecimal markupAmt = busMarkUpConfig.getMarkupAmt();
							if(!isAccumulative){
								if(isFixedAmount) {
									totalPrice = totalPrice.add(markupAmt);
								}else{
									BigDecimal commission = priceWithoutMarkup.multiply(markupAmt).divide(new BigDecimal("100"));
									totalPrice = totalPrice.add(commission);
								}							
							}else if(isAccumulative){
								if(isFixedAmount) {
									totalPrice = totalPrice.add(markupAmt);
								}else{
									BigDecimal commission = priceWithoutMarkup.multiply(markupAmt).divide(new BigDecimal("100"));
									totalPrice = totalPrice.add(commission);
								}
							}
							if(companyConfig.getCompany_id() == currentCompany)
							{
								currentCompanyMarkup = currentCompanyMarkup.add(markupAmt);
							}
						}
					}
				}
			}
		}
		fare.setBookingPrice(totalPrice);
		if(companyConfig!=null){
			if(companyConfig.getCompanyConfigType().isB2E()){
				fare.setTotalPayableAmount(AmountRoundingModeUtil.roundingMode(fare.getBookingPrice()));
			}else if(companyConfig.getCompanyConfigType().isB2B()){
				fare.setTotalPayableAmount(AmountRoundingModeUtil.roundingMode(fare.getBookingPrice()).subtract(currentCompanyMarkup));
			}else{
				fare.setTotalPayableAmount(AmountRoundingModeUtil.roundingMode(fare.getBookingPrice()));
			}

		}
	}

	public static void getMarkupAmtForEachCompany(Map<String,List<BusMarkUpConfig>> markupMap,
			BlockFareDetail blockFareDetail,BusBlockTicketRequest busBlockTicketRequest,BusMarkupCommissionDetails markupCommissionDetails) throws Exception {

		BigDecimal priceWithoutMarkup = blockFareDetail.getPriceWithOutMarkup();
		BigDecimal totalPrice = priceWithoutMarkup;
		Map<String, BigDecimal> companyMarkupMap = new HashMap<String, BigDecimal>();	
		Map<String, List<BusMarkUpConfig>> sortedFlightMarkUpConfiglistMap = new TreeMap<String,List<BusMarkUpConfig>>();
		if(markupMap!=null && markupMap.size()>0)
		{
			sortedFlightMarkUpConfiglistMap.putAll(markupMap);
			if(sortedFlightMarkUpConfiglistMap!=null && sortedFlightMarkUpConfiglistMap.size()>0)
			{
				for (Map.Entry<String,List<BusMarkUpConfig>> entry : sortedFlightMarkUpConfiglistMap.entrySet()) {
					List<BusMarkUpConfig> BusMarkUpConfiglist = entry.getValue();				
					boolean result = false;
					BigDecimal Markup = BigDecimal.ZERO;
					if (BusMarkUpConfiglist != null) {
						int k = 0;					
						for (int i = 0; i < BusMarkUpConfiglist.size(); i++) {
							BusMarkUpConfig busMarkUpConfig = BusMarkUpConfiglist.get(i);
							boolean accumulative = busMarkUpConfig.isAccumulative();							
							boolean fixedAmount = busMarkUpConfig.isFixedAmount();
							BigDecimal markupAmt = busMarkUpConfig.getMarkupAmt();
							BigDecimal totalpassenger = new BigDecimal(busBlockTicketRequest.getBusPaxDetails().size());
							markupAmt = markupAmt.multiply(totalpassenger);
							if(isMarkupAppliable(busMarkUpConfig,"",busBlockTicketRequest.getOrigin(),busBlockTicketRequest.getDestination(),busBlockTicketRequest.getOnwardDate(),"")) {
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

	public static void getCommissionWithMarkupValuesForEachCompany(List<BusOrderRowCommission> busOrderRowCommissions,Map<String,List<BusMarkUpConfig>> markupMap, 
			BlockFareDetail blockFareDetail,BusMarkupCommissionDetails markupCommissionDetails,BusBlockTicketRequest busBlockTicketRequest) throws Exception {

		BigDecimal priceWithoutMarkup = blockFareDetail.getPriceWithOutMarkup();	
		BigDecimal totalAmount = priceWithoutMarkup;	
		if(busOrderRowCommissions!=null && busOrderRowCommissions.size()>0)
		{
			if(busOrderRowCommissions.size()>1)
				Collections.sort(busOrderRowCommissions, new companyIdComparator());

			for (int m = 0; m < busOrderRowCommissions.size(); m++) {
				BusOrderRowCommission busOrderRowCommission = busOrderRowCommissions.get(m);
				BigDecimal companycommissionAmount = new BigDecimal("0.00");
				if(busOrderRowCommission.getRateType().equalsIgnoreCase("Commission")){
					if(busOrderRowCommission.getCommissionType().equalsIgnoreCase("Fixed"))
					{
						companycommissionAmount = busOrderRowCommission.getCommission();
					}					
					else{
						companycommissionAmount = totalAmount.multiply(busOrderRowCommission.getCommission()).divide(AmountRoundingModeUtil.roundingMode(new BigDecimal("100")));
					}
				}
				busOrderRowCommission.setCommissionAmountValue(companycommissionAmount);
				if(m == busOrderRowCommissions.size()-1){
					break;
				}

				BigDecimal Markup = BigDecimal.ZERO;			
				boolean result=false;
				List<BusMarkUpConfig> BusMarkUpConfigList = markupMap.get(busOrderRowCommission.getCompanyId());
				if (BusMarkUpConfigList != null && BusMarkUpConfigList.size()>0) {
					int k = 0;				
					for (int i = 0; i < BusMarkUpConfigList.size(); i++) {
						BusMarkUpConfig busMarkUpConfig = BusMarkUpConfigList.get(i);
						boolean accumulative = busMarkUpConfig.isAccumulative();
						boolean fixedAmount = busMarkUpConfig.isFixedAmount();
						BigDecimal markupAmt = busMarkUpConfig.getMarkupAmt();
						if(isMarkupAppliable(busMarkUpConfig,"",busBlockTicketRequest.getOrigin(),busBlockTicketRequest.getDestination(),busBlockTicketRequest.getOnwardDate(),"")) {

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

	public static boolean isMarkupAppliable(BusMarkUpConfig busMarkUpConfig,String operatorName,String origin,String destination,String onwardDate,String returnDate){
		boolean isAppliable = false;
		try{
			if(busMarkUpConfig!=null){				

				//Check Mark on Origin
				if(busMarkUpConfig.getOrigin().trim().equalsIgnoreCase("ALL")){
					isAppliable = true;
				}
				else if(!busMarkUpConfig.getOrigin().trim().equalsIgnoreCase("ALL") && busMarkUpConfig.getOrigin().trim().equalsIgnoreCase(origin.trim())){
					isAppliable = true;
				}
				//Check Mark on Destination
				else if(busMarkUpConfig.getDestination().trim().equalsIgnoreCase("ALL")){
					isAppliable = true;
				}
				else if(!busMarkUpConfig.getDestination().trim().equalsIgnoreCase("ALL") && busMarkUpConfig.getDestination().trim().equalsIgnoreCase(destination.trim())){
					isAppliable = true;
				}
				//Check Mark on Onward date
				else if(busMarkUpConfig.getOnwardDate().trim().equalsIgnoreCase("ALL")){
					isAppliable = true;
				}
				else if(!busMarkUpConfig.getOnwardDate().trim().equalsIgnoreCase("ALL") && busMarkUpConfig.getOnwardDate().trim().equalsIgnoreCase(onwardDate.trim())){
					isAppliable = true;
				}
				//Check Mark on Return date	
				else if(returnDate !=null && busMarkUpConfig.getOnwardDate().trim().equalsIgnoreCase("ALL")){
					isAppliable = true;
				}
				else if(returnDate !=null && !busMarkUpConfig.getOnwardDate().trim().equalsIgnoreCase("ALL") && busMarkUpConfig.getOnwardDate().trim().equalsIgnoreCase(returnDate.trim())){
					isAppliable = true;				}			

				//Check Mark on PromofareStart Date		
				else if(busMarkUpConfig.getPromofareStartDate().trim().equalsIgnoreCase("ALL")){
					isAppliable = true;
				}
				else if(!busMarkUpConfig.getPromofareStartDate().trim().equalsIgnoreCase("ALL") && (BusCommonUtil.convertStringtoDate(busMarkUpConfig.getPromofareStartDate().trim()).compareTo(BusCommonUtil.convertStringtoDate(onwardDate.trim())) == 0 || BusCommonUtil.convertStringtoDate(busMarkUpConfig.getPromofareStartDate().trim()).compareTo(BusCommonUtil.convertStringtoDate(onwardDate.trim())) < 0)){
					isAppliable = true;
				}
				//Check Mark on PromofareEnd Date	
				else if(busMarkUpConfig.getPromofareEndDate().trim().equalsIgnoreCase("ALL")){
					isAppliable = true;
				}
				else if(!busMarkUpConfig.getPromofareEndDate().trim().equalsIgnoreCase("ALL") && (BusCommonUtil.convertStringtoDate(busMarkUpConfig.getPromofareEndDate().trim()).compareTo(BusCommonUtil.convertStringtoDate(onwardDate.trim())) == 0)){
					isAppliable = true;
				}
				//Check Mark on BusOperators
				else if(busMarkUpConfig.getBusOperators().equalsIgnoreCase("ALL")){
					isAppliable = true;
				}
				else if(busMarkUpConfig.getBusOperators().equalsIgnoreCase(operatorName)){
					isAppliable = true;
				}
				else{
					isAppliable = false;
				}
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
		BusOrderRowCommission A1 = (BusOrderRowCommission) o1;
		BusOrderRowCommission A2 = (BusOrderRowCommission) o2;
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