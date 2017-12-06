package com.tayyarah.bus.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import com.tayyarah.bus.entity.BusOrderCustomerDetail;
import com.tayyarah.bus.entity.BusOrderRow;
import com.tayyarah.bus.entity.BusOrderRowCommission;
import com.tayyarah.bus.entity.BusOrderRowGstTax;
import com.tayyarah.bus.entity.BusOrderRowMarkup;
import com.tayyarah.bus.entity.BusOrderRowServiceTax;
import com.tayyarah.bus.model.AvailableBus;
import com.tayyarah.bus.model.AvailableBuses;
import com.tayyarah.bus.model.BlockBusDetail;
import com.tayyarah.bus.model.BlockFareDetail;
import com.tayyarah.bus.model.BusBlockTicketRequest;
import com.tayyarah.bus.model.BusBlockTicketResponse;
import com.tayyarah.bus.model.BusLayoutRequest;
import com.tayyarah.bus.model.BusMarkUpConfig;
import com.tayyarah.bus.model.BusMarkupCommissionDetails;
import com.tayyarah.bus.model.BusPaxDetail;
import com.tayyarah.bus.model.BusSearchFilters;
import com.tayyarah.bus.model.BusSearchRequest;
import com.tayyarah.bus.model.BusServiceTax;
import com.tayyarah.bus.model.Fare;
import com.tayyarah.bus.model.Seat;
import com.tayyarah.bus.model.SeatFare;
import com.tayyarah.bus.model.Status;
import com.tayyarah.bus.model.TayyarahBusSearchMap;
import com.tayyarah.bus.model.TayyarahBusSeatMap;
import com.tayyarah.common.dao.MoneyExchangeDao;
import com.tayyarah.common.entity.OrderCustomer;
import com.tayyarah.common.gstconfig.entity.BusGstTaxConfig;
import com.tayyarah.common.gstconfig.model.BusGstTax;
import com.tayyarah.common.model.CommissionDetails;
import com.tayyarah.common.model.CurrencyConversionMap;
import com.tayyarah.common.servicetaxconfig.entity.BusServiceTaxConfig;
import com.tayyarah.common.util.AmountRoundingModeUtil;
import com.tayyarah.common.util.CutandPayModel;
import com.tayyarah.common.util.IndianUnionTerritories;
import com.tayyarah.common.util.enums.CommonBookingStatusEnum;
import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.company.entity.Company;
import com.tayyarah.company.entity.CompanyConfig;
import com.tayyarah.esmart.bus.util.EsmartBusConfig;
import com.tayyarah.user.entity.User;




public class BusCommonUtil {
	public static final Logger logger = Logger.getLogger(BusCommonUtil.class);

	public static CurrencyConversionMap buildCurrencyConversionMap(String currency,
			MoneyExchangeDao moneydao) {

		/// Get Default API Currency
		CurrencyConversionMap currencyConversionMap = new CurrencyConversionMap();
		String apiCurrency = EsmartBusConfig.DEFAULT_CURRENCY;
		currencyConversionMap.setApiCurrency(apiCurrency);
		Map<String, Double> currencyrate = null;
		try {			
			currencyrate = moneydao.getCurrencyRate(currency, apiCurrency);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.NO_BUSAVAILABLE.getErrorMessage());
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
			throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.NO_BUSAVAILABLE.getErrorMessage());
		}
		currencyConversionMap.setCurrencyrate1(currencyrateNow);
		Double currencyValueNow = currencyrateNow.get("value");
		currencyConversionMap.setCurrencyValue1(currencyValueNow);
		return currencyConversionMap;
	}

	public static Date convertStringtoDate(String date){
		Date convertedDate = null;
		try{
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			convertedDate = format.parse(date);
		}catch(Exception e){
			logger.error("convertStringtoDate Exception " + e);
		}
		return convertedDate;
	}
	public static String[] getStringArrayFromString(String prices,String regex){
		String[] string = prices.split(regex);
		return string;
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
	public static Object convertByteArrayToObject(byte[] data) throws IOException {
		Object object = null;
		ByteArrayInputStream byteInputStream = new ByteArrayInputStream(data);
		ObjectInputStream objectInputStream= new ObjectInputStream(byteInputStream);
		try {
			object= objectInputStream.readObject();
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException ",e);
		}
		catch (Exception e) {
			logger.error("Exception ",e);
		}
		return object;
	}
	public static void addDynamicMarkup(String appkey,List<BusMarkUpConfig> markupList,String markupAmount){
		String companyId = "invalid";
		String configId = "invalid";
		configId = appkey.substring(0, appkey.indexOf("-"));
		companyId = appkey.substring(appkey.indexOf("-") + 1);
		BusMarkUpConfig busMarkUpConfig = new BusMarkUpConfig();
		busMarkUpConfig.setName("Dynamic markup");
		busMarkUpConfig.setAccumulative(false);	
		busMarkUpConfig.setFixedAmount(true);	
		busMarkUpConfig.setCompanyId(Integer.parseInt(companyId));
		busMarkUpConfig.setConfigId(Integer.parseInt(configId));
		busMarkUpConfig.setMarkupAmt(new BigDecimal(markupAmount));
		busMarkUpConfig.setMarkupId(0);
		busMarkUpConfig.setPositionOfMarkup(1);
		busMarkUpConfig.setOrigin("ALL");
		busMarkUpConfig.setDestination("ALL");
		busMarkUpConfig.setOnwardDate("ALL");
		busMarkUpConfig.setReturnDate("ALL");	
		busMarkUpConfig.setPromofareStartDate("ALL");
		busMarkUpConfig.setPromofareEndDate("ALL");
		markupList.add(busMarkUpConfig);
	}
	public static BusServiceTax getBusServiceTax(String totalPrice,CompanyConfig companyConfig){
		BusServiceTax busServiceTax = new BusServiceTax();
		BigDecimal totalprice =  new BigDecimal(totalPrice);
		BigDecimal baseServiceTax = new BigDecimal("0.0");
		BigDecimal SBC = new BigDecimal("0.0");
		BigDecimal KKC = new BigDecimal("0.0");
		BigDecimal totalServiceTax = new BigDecimal("0.0");
		BigDecimal managementFee  = new BigDecimal("0.0");
		BusServiceTaxConfig busServiceTaxConfig = companyConfig.getBusServiceTaxConfig();
		managementFee = busServiceTaxConfig.getManagementFee();
		totalprice = totalprice.add(managementFee);
		baseServiceTax = totalprice.divide(new BigDecimal("100.0")).multiply(busServiceTaxConfig.getBasicTax());
		SBC =  totalprice.divide(new BigDecimal("100.0")).multiply(busServiceTaxConfig.getSwatchBharathCess());
		KKC =  totalprice.divide(new BigDecimal("100.0")).multiply(busServiceTaxConfig.getKrishiKalyanCess());
		totalServiceTax =  totalprice.divide(new BigDecimal("100.0")).multiply(busServiceTaxConfig.getTotalTax());

		busServiceTax.setBaseServicetax(baseServiceTax);
		busServiceTax.setKKC(KKC);
		busServiceTax.setManagementFee(managementFee);
		busServiceTax.setSBC(SBC);
		busServiceTax.setTotalServiceTax(totalServiceTax);			
		return busServiceTax;
	}
	public static BusGstTax getBusGstTax(CompanyConfig companyConfig,Company company,Company parentCompany){
		BusGstTax busGstTax = new BusGstTax();
		BigDecimal CGST = new BigDecimal("0.0");
		BigDecimal SGST = new BigDecimal("0.0");
		BigDecimal IGST = new BigDecimal("0.0");
		BigDecimal UGST = new BigDecimal("0.0");
		BigDecimal totalGst = new BigDecimal("0.0");
		BigDecimal managementFee  = new BigDecimal("0.0");		
		BigDecimal convenienceFee  = new BigDecimal("0.0");	
		BusGstTaxConfig busGstTaxConfig = companyConfig.getBusGstTaxConfig();
		if(busGstTaxConfig != null){
			managementFee = busGstTaxConfig.getManagementFee();
			convenienceFee = busGstTaxConfig.getConvenienceFee()!=null?busGstTaxConfig.getConvenienceFee():new BigDecimal("0.0");
			CGST = managementFee.divide(new BigDecimal("100.0")).multiply(busGstTaxConfig.getCGST());
			boolean isParentCompanyUT = IndianUnionTerritories.isUnionter(parentCompany.getBillingstate().trim());
			boolean isBillingCompanyUT = IndianUnionTerritories.isUnionter(company.getBillingstate().trim());

			if(isParentCompanyUT && isBillingCompanyUT){
				UGST =  managementFee.divide(new BigDecimal("100.0")).multiply(busGstTaxConfig.getUGST());
			}
			if(!company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim()) && IndianUnionTerritories.isUnionter(company.getBillingstate().trim())){
				UGST =  managementFee.divide(new BigDecimal("100.0")).multiply(busGstTaxConfig.getUGST());
			}
			if(!isParentCompanyUT && !isBillingCompanyUT){
				if(company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim())){
					SGST =  managementFee.divide(new BigDecimal("100.0")).multiply(busGstTaxConfig.getSGST());				
				}
			}
			if(isParentCompanyUT && !isBillingCompanyUT){
				if(!company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim()) && !IndianUnionTerritories.isUnionter(company.getBillingstate().trim())){
					IGST =  managementFee.divide(new BigDecimal("100.0")).multiply(busGstTaxConfig.getIGST());		
				}
			}	

			totalGst = CGST.add(SGST).add(IGST).add(UGST);	
		}
		busGstTax.setCGST(CGST);
		busGstTax.setSGST(SGST);
		busGstTax.setIGST(IGST);
		busGstTax.setUGST(UGST);
		busGstTax.setTotalTax(totalGst);
		busGstTax.setConvenienceFee(convenienceFee);
		busGstTax.setManagementFee(managementFee);		
		return busGstTax;
	}
	public static AvailableBuses busAvailableWithDynamicMarkupParser(AvailableBuses availableBusReponse,Map<String,List<BusMarkUpConfig>> markupMap,BusSearchRequest busSearchRequest,CurrencyConversionMap currencyConversionMap,String decAppKey,CompanyConfigDAO companyConfigDAO,CompanyDao CompanyDAO){
		AvailableBuses availableBuses = new AvailableBuses();
		try{
			Status status = new Status();
			Set<String> busOperators = new HashSet<>();
			Set<String> boardingPoints = new HashSet<>();
			Set<String> droppingPoints = new HashSet<>();
			Set<String> busTypes =  new HashSet<>();
			Set<String> fares =  new HashSet<>();
			List<AvailableBus> availableBusesList = new ArrayList<>();
			BusSearchFilters busSearchFilters = new BusSearchFilters();

			CompanyConfig companyConfig = null;
			String configId = decAppKey.substring(0,decAppKey.indexOf("-"));
			try {
				companyConfig = companyConfigDAO.getCompanyConfigByConfigId(Integer.parseInt(configId));
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			Company company = null;
			String companyId = decAppKey.substring(decAppKey.indexOf("-") + 1);
			try{
				company = CompanyDAO.getCompany(Integer.parseInt(companyId));
			}catch(Exception e){
				e.printStackTrace();
			}
			Company parentCompany = null;
			try{
				parentCompany = CompanyDAO.getParentCompany(company);
			}catch(Exception e){
				e.printStackTrace();
			}

			if(availableBusReponse.getStatus().getCode() == 1){
				status.setCode(1);
				status.setMessage("Success");
				for (AvailableBus availableBusTemp : availableBusReponse.getAvailableBus()) {
					AvailableBus availableBus = new AvailableBus();
					busOperators.add(availableBusTemp.getOperatorName());
					busTypes.add(availableBusTemp.getBusType());
					List<com.tayyarah.bus.model.BoardingPoint> BPList = new ArrayList<>();
					List<com.tayyarah.bus.model.DroppingPoint> DPList = new ArrayList<>();
					if(availableBusTemp.getBoardingPoints() != null && availableBusTemp.getBoardingPoints().size() > 0){
						for (com.tayyarah.bus.model.BoardingPoint boardingPoint : availableBusTemp.getBoardingPoints()) {
							boardingPoints.add(boardingPoint.getLoc());							
							com.tayyarah.bus.model.BoardingPoint BP = new com.tayyarah.bus.model.BoardingPoint();
							BP.setId(boardingPoint.getId());
							BP.setLoc(boardingPoint.getLoc());
							BP.setTime(boardingPoint.getTime());
							BPList.add(BP);
						}
					}
					if(availableBusTemp.getDroppingPoints() != null && availableBusTemp.getDroppingPoints().size() > 0){
						for (com.tayyarah.bus.model.DroppingPoint droppingPoint : availableBusTemp.getDroppingPoints()) {
							droppingPoints.add(droppingPoint.getLoc());							
							com.tayyarah.bus.model.DroppingPoint DP = new com.tayyarah.bus.model.DroppingPoint();
							DP.setId(droppingPoint.getId());
							DP.setLoc(droppingPoint.getLoc());
							DP.setTime(droppingPoint.getTime());
							DPList.add(DP);
						}
					}

					Fare busFare = new Fare();
					busFare.setApiPrice(availableBusTemp.getFare().getApiPrice());
					busFare.setBookingPrice(availableBusTemp.getFare().getBookingPrice());
					busFare.setBusServiceTax(null);
					busFare.setDiscount("0");
					busFare.setPayableAmount(availableBusTemp.getFare().getPayableAmount());
					busFare.setPriceWithOutMarkup(availableBusTemp.getFare().getPriceWithOutMarkup());
					availableBus.setFare(busFare);

					// Apply MarkUp 
					BusMarkupHelper.applyMarkupOnSearchResponse(markupMap, availableBus, busSearchRequest);


					BusServiceTax busServiceTax = null;
					BusGstTax busGstTax = null;
					if(companyConfig != null){
						if(companyConfig.getCompanyConfigType().isB2E()){
							if(companyConfig.getTaxtype()!=null && companyConfig.getTaxtype().equalsIgnoreCase("GST")){
								busGstTax = new BusGstTax();
								if(busFare.getBookingPrice().contains(",")){
									String[] prices = BusCommonUtil.getStringArrayFromString(busFare.getBookingPrice(),",");
									StringBuffer totalPrices = new StringBuffer(); 
									int i = 0;
									for (String price : prices) {
										busGstTax = BusCommonUtil.getBusGstTax(companyConfig,company,parentCompany);
										BigDecimal totalPrice = new BigDecimal(price);
										BigDecimal totalPriceAfterServiceTax = totalPrice.add(busGstTax.getTotalTax()).add(busGstTax.getManagementFee()).setScale(0, RoundingMode.UP);
										totalPriceAfterServiceTax = AmountRoundingModeUtil.roundingModeForBus(totalPriceAfterServiceTax);
										totalPrices.append(totalPriceAfterServiceTax.toString());
										if(i != prices.length - 1)
											totalPrices.append(",");

										i++;									
									}
									availableBus.getFare().setBookingPrice(totalPrices.toString());

								}else{
									busGstTax = BusCommonUtil.getBusGstTax(companyConfig,company,parentCompany);
									BigDecimal totalPrice = new BigDecimal(busFare.getBookingPrice());
									BigDecimal totalPriceAfterServiceTax = totalPrice.add(busGstTax.getTotalTax()).add(busGstTax.getManagementFee());
									totalPriceAfterServiceTax = AmountRoundingModeUtil.roundingModeForBus(totalPriceAfterServiceTax);
									availableBus.getFare().setBookingPrice(totalPriceAfterServiceTax.toString());
								}

							}else{
								busServiceTax = new BusServiceTax();
								if(busFare.getBookingPrice().contains(",")){
									String[] prices = BusCommonUtil.getStringArrayFromString(busFare.getBookingPrice(),",");
									StringBuffer totalPrices = new StringBuffer(); 
									int i = 0;
									for (String price : prices) {
										busServiceTax = BusCommonUtil.getBusServiceTax(price, companyConfig);
										BigDecimal totalPrice = new BigDecimal(price);
										BigDecimal totalPriceAfterServiceTax = totalPrice.add(busServiceTax.getTotalServiceTax()).add(busServiceTax.getManagementFee());
										totalPriceAfterServiceTax = AmountRoundingModeUtil.roundingModeForBus(totalPriceAfterServiceTax);
										totalPrices.append(totalPriceAfterServiceTax.toString());
										if(i != prices.length - 1)
											totalPrices.append(",");

										i++;									
									}
									availableBus.getFare().setBookingPrice(totalPrices.toString());

								}else{
									busServiceTax = BusCommonUtil.getBusServiceTax(busFare.getBookingPrice(), companyConfig);
									BigDecimal totalPrice = new BigDecimal(busFare.getBookingPrice());
									BigDecimal totalPriceAfterServiceTax = totalPrice.add(busServiceTax.getTotalServiceTax()).add(busServiceTax.getManagementFee());
									totalPriceAfterServiceTax = AmountRoundingModeUtil.roundingModeForBus(totalPriceAfterServiceTax);
									availableBus.getFare().setBookingPrice(totalPriceAfterServiceTax.toString());
								}


							}
						}
					}
					availableBus.getFare().setBusServiceTax(busServiceTax);
					availableBus.getFare().setBusGstTax(busGstTax);
					availableBus.setArrivalTime(availableBusTemp.getArrivalTime());
					availableBus.setAvailableSeats(availableBusTemp.getAvailableSeats());
					availableBus.setBoardingPoints(BPList);
					availableBus.setDroppingPoints(DPList);
					availableBus.setBusType(availableBusTemp.getBusType());
					availableBus.setCancellationPolicy(availableBusTemp.getCancellationPolicy());
					availableBus.setCommPCT(availableBusTemp.getCommPCT());
					availableBus.setDepartureTime(availableBusTemp.getDepartureTime());
					availableBus.setIdProofRequired(availableBusTemp.getIdProofRequired());
					availableBus.setInventoryType(availableBusTemp.getInventoryType());
					availableBus.setIsChildConcession(availableBusTemp.getIsChildConcession());
					availableBus.setIsFareUpdateRequired(availableBusTemp.getIsFareUpdateRequired());
					availableBus.setIsGetLayoutByBPDP(availableBusTemp.getIsGetLayoutByBPDP());
					availableBus.setIsOpLogoRequired(availableBusTemp.getIsOpLogoRequired());
					availableBus.setIsOpTicketTemplateRequired(availableBusTemp.getIsOpTicketTemplateRequired());
					availableBus.setIsRTC(availableBusTemp.getIsRTC());
					availableBus.setmTicketAllowed(availableBusTemp.getmTicketAllowed());
					availableBus.setOperatorId(availableBusTemp.getOperatorId());
					availableBus.setOperatorName(availableBusTemp.getOperatorName());
					availableBus.setPartialCancellationAllowed(availableBusTemp.getPartialCancellationAllowed());
					availableBus.setRouteScheduleId(availableBusTemp.getRouteScheduleId());
					availableBus.setServiceId(availableBusTemp.getServiceId());
					availableBusesList.add(availableBus);
					busSearchFilters.setBoardingPoints(new ArrayList<>(boardingPoints));
					busSearchFilters.setDroppingPoints(new ArrayList<>(droppingPoints));
					busSearchFilters.setBusOperators(new ArrayList<>(busOperators));
					busSearchFilters.setBusTypes(new ArrayList<>(busTypes));
					busSearchFilters.setFares(new ArrayList<>(fares));
					availableBuses.setAvailableBus(availableBusesList);
					availableBuses.setBusSearchFilters(busSearchFilters);
				}

			}else{
				status.setCode(0);
				status.setMessage("No buses available for this search criteria");	
			}

			availableBuses.setStatus(status);
			availableBuses.setOrigin(busSearchRequest.getOrigin());
			availableBuses.setDestination(busSearchRequest.getDestination());
			availableBuses.setCurrency(busSearchRequest.getCurrency());
			availableBuses.setOnwardDate(busSearchRequest.getOnwardDate());
			availableBuses.setReturnDate(busSearchRequest.getReturnDate());

		}catch(Exception e){
			logger.error("busAvailableBusParser " +e.getMessage());
			throw new BusException(ErrorCodeCustomerEnum.Exception, BusErrorMessages.NO_BUSAVAILABLE.getErrorMessage());
		}
		return availableBuses;
	}

	public static SeatFare getSeatFareDetail(TayyarahBusSeatMap tayyarahBusSeatMap,String seatId){
		SeatFare seatFare = new SeatFare();
		Map<String,com.tayyarah.bus.model.Seat> seatMap = tayyarahBusSeatMap.getSeatMap();
		Seat seat = seatMap.get(seatId);
		seatFare = seat.getSeatFare();
		return seatFare;
	}
	public static String getTransactionkey(TayyarahBusSearchMap busSearchMap,String searchkey){
		AvailableBuses availableBuses =  busSearchMap.getAvailableBusesMap().get(searchkey);
		String transactionKey = availableBuses.getTransactionkey();
		return transactionKey;
	}
	public static String getCancellationPolicy(TayyarahBusSearchMap busSearchMap,String searchkey,String routeScheduleId){
		AvailableBuses availableBuses =  busSearchMap.getAvailableBusesMap().get(searchkey);
		String cancellationPolicy = "";
		for (AvailableBus availableBus : availableBuses.getAvailableBus()) {
			if(routeScheduleId.equalsIgnoreCase(availableBus.getRouteScheduleId())){
				cancellationPolicy = availableBus.getCancellationPolicy();
			}
		}
		return cancellationPolicy;
	}
	public static String getRouteScheduleId(TayyarahBusSeatMap tayyarahBusSeatMap,String searchkey){
		BusLayoutRequest busLayoutRequest =  tayyarahBusSeatMap.getBusLayoutRequest();
		String routeScheduleId = busLayoutRequest.getRouteScheduleId();
		return routeScheduleId;
	}

	public static BlockBusDetail getBlockedBusDetail(TayyarahBusSearchMap busSearchMap,String searchkey,String routeScheduleId,BusBlockTicketRequest busBlockTicketRequest){
		BlockBusDetail blockBusDetail = new BlockBusDetail();
		AvailableBuses availableBuses =  busSearchMap.getAvailableBusesMap().get(searchkey);		
		for (AvailableBus availableBus : availableBuses.getAvailableBus()) {
			if(availableBus.getRouteScheduleId().equalsIgnoreCase(routeScheduleId)){
				blockBusDetail.setArrivalTime(availableBus.getArrivalTime());				
				blockBusDetail.setBoardingPoint(busBlockTicketRequest.getBoardingPoint());				
				blockBusDetail.setDroppingPoint(busBlockTicketRequest.getDroppingPoint());
				blockBusDetail.setBusType(availableBus.getBusType());
				blockBusDetail.setBusOperator(availableBus.getOperatorName());
				blockBusDetail.setDepartureTime(availableBus.getDepartureTime());
				blockBusDetail.setmTicketAllowed(availableBus.getmTicketAllowed());
				blockBusDetail.setPartialCancellationAllowed(availableBus.getPartialCancellationAllowed());	
				blockBusDetail.setCancellationPolicy(availableBus.getCancellationPolicy());
			}
		}
		return blockBusDetail;
	}

	public static BlockFareDetail getBlockedTicketFareDetail(TayyarahBusSeatMap tayyarahBusSeatMap,BusBlockTicketRequest busBlockTicketRequest){
		BlockFareDetail blockFareDetail = new BlockFareDetail();
		List<SeatFare> seatFareList = new ArrayList<>();
		for (BusPaxDetail busPaxDetail : busBlockTicketRequest.getBusPaxDetails()) {
			SeatFare seatFare = getSeatFareDetail(tayyarahBusSeatMap,busPaxDetail.getSeatNbr());
			seatFareList.add(seatFare);
		}
		BigDecimal basePrice = new BigDecimal(0);	
		BigDecimal taxes = new BigDecimal(0);	
		BigDecimal apiPrice = new BigDecimal(0);		
		BigDecimal priceWithOutMarkup = new BigDecimal(0);
		BigDecimal bookingPrice = new BigDecimal(0);
		BigDecimal totalPayableAmount = new BigDecimal(0);
		BusServiceTax busServiceTax = null;
		BigDecimal baseServicetax =  new BigDecimal(0);
		BigDecimal SBC =  new BigDecimal(0);
		BigDecimal KKC =  new BigDecimal(0);
		BusGstTax busGstTax = null;
		BigDecimal CGST =  new BigDecimal(0);
		BigDecimal SGST =  new BigDecimal(0);
		BigDecimal IGST =  new BigDecimal(0);
		BigDecimal UGST =  new BigDecimal(0);
		BigDecimal TotalGST =  new BigDecimal(0);
		BigDecimal totalServiceTax =  new BigDecimal(0);
		BigDecimal managementFee =  new BigDecimal(0);
		for (SeatFare seatFare : seatFareList) {
			basePrice = basePrice.add(seatFare.getBasePrice());
			taxes = taxes.add(seatFare.getTaxAmount());
			apiPrice = apiPrice.add(seatFare.getApiTotalFareWithTaxes());
			priceWithOutMarkup = priceWithOutMarkup.add(seatFare.getPriceWithOutMarkup());
			bookingPrice = bookingPrice.add(seatFare.getBookingPrice());
			totalPayableAmount = totalPayableAmount.add(seatFare.getTotalPayableAmount());
			if(seatFare.getBusServiceTax() != null){				
				baseServicetax = baseServicetax.add(seatFare.getBusServiceTax().getBaseServicetax());
				SBC = SBC.add(seatFare.getBusServiceTax().getSBC());
				KKC = KKC.add(seatFare.getBusServiceTax().getKKC());
				totalServiceTax = totalServiceTax.add(seatFare.getBusServiceTax().getTotalServiceTax());
				managementFee = managementFee.add(seatFare.getBusServiceTax().getManagementFee());				
			}
			if(seatFare.getBusGstTax() != null){
				CGST = CGST.add(seatFare.getBusGstTax().getCGST());
				SGST = SGST.add(seatFare.getBusGstTax().getSGST());
				IGST = IGST.add(seatFare.getBusGstTax().getIGST());
				UGST = UGST.add(seatFare.getBusGstTax().getUGST());
				TotalGST = TotalGST.add(seatFare.getBusGstTax().getTotalTax());
				managementFee = managementFee.add(seatFare.getBusGstTax().getManagementFee());
			}
		}
		if(seatFareList.get(0).getBusServiceTax() != null){	
			busServiceTax= new BusServiceTax();
			busServiceTax.setBaseServicetax(baseServicetax);
			busServiceTax.setKKC(KKC);
			busServiceTax.setSBC(SBC);
			busServiceTax.setTotalServiceTax(totalServiceTax);
			busServiceTax.setManagementFee(managementFee);
		}
		if(seatFareList.get(0).getBusGstTax() != null){	
			busGstTax= new BusGstTax();
			busGstTax.setCGST(CGST);
			busGstTax.setSGST(SGST);
			busGstTax.setIGST(IGST);
			busGstTax.setUGST(UGST);
			busGstTax.setTotalTax(TotalGST);
			busGstTax.setManagementFee(managementFee);
		}
		blockFareDetail.setBusServiceTax(busServiceTax);
		blockFareDetail.setBusGstTax(busGstTax);
		blockFareDetail.setBasePrice(basePrice);
		blockFareDetail.setTaxes(taxes);
		blockFareDetail.setApiPrice(apiPrice);
		blockFareDetail.setBookingPrice(AmountRoundingModeUtil.roundingModeForBus(bookingPrice));
		blockFareDetail.setPriceWithOutMarkup(priceWithOutMarkup);
		blockFareDetail.setTotalPayableAmount(AmountRoundingModeUtil.roundingModeForBus(totalPayableAmount));

		return blockFareDetail;
	}

	public static List<BusOrderCustomerDetail> getBusOrderCustomerDetail(BusBlockTicketRequest busBlockTicketRequest,BusOrderRow busOrderRow,TayyarahBusSeatMap tayyarahBusSeatMap){
		List<BusOrderCustomerDetail>  busOrderCustomerDetailList = new ArrayList<>();		
		for (BusPaxDetail busPaxDetail : busBlockTicketRequest.getBusPaxDetails()) {
			BusOrderCustomerDetail busOrderCustomerDetail = new BusOrderCustomerDetail();
			busOrderCustomerDetail.setPaxId(busPaxDetail.getPaxId());
			busOrderCustomerDetail.setFirstName(busPaxDetail.getFirstName());
			busOrderCustomerDetail.setLastName(busPaxDetail.getLastName());
			busOrderCustomerDetail.setTitle(busPaxDetail.getGender().equalsIgnoreCase("Male")?"Mr":"Mrs");
			busOrderCustomerDetail.setEmail(busBlockTicketRequest.getEmail());
			busOrderCustomerDetail.setGender(busPaxDetail.getGender());
			busOrderCustomerDetail.setIsSleeper(busPaxDetail.getIsSleeper());
			busOrderCustomerDetail.setMobile(busBlockTicketRequest.getPhone());
			busOrderCustomerDetail.setSeatNo(busPaxDetail.getSeatNbr());
			busOrderCustomerDetail.setBusOrderRow(busOrderRow);
			busOrderCustomerDetail.setAge(busPaxDetail.getAge());
			busOrderCustomerDetail.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));			
			SeatFare seatFare = getSeatFareDetail(tayyarahBusSeatMap, busPaxDetail.getSeatNbr());
			busOrderCustomerDetail.setSeatPrice(seatFare.getBasePrice());
			busOrderCustomerDetailList.add(busOrderCustomerDetail);
		}		
		return busOrderCustomerDetailList;
	}


	public static String checkGetEmulatedUserById(BusBlockTicketRequest busBlockTicketRequest) {
		if(busBlockTicketRequest.getIsEmulateFlag())
		{
			return busBlockTicketRequest.getEmulateByUserId();
		}
		return busBlockTicketRequest.getUserId();
	}
	public static String getBusFormatDate(){
		String dateString = "";
		try{
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date date = new Date();
			dateString = dateFormat.format(date);
		}catch(Exception e){
			logger.error("getBusFormatDate " +e.getMessage());
		}
		return dateString;
	}
	public static String getBusFormatDateToString(Date date){
		String dateString = "";
		try{
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");		
			dateString = dateFormat.format(date);
		}catch(Exception e){
			logger.error("getBusFormatDateToString " +e.getMessage());
		}
		return dateString;
	}
	public static Date getBusFormatDateFromString(String traveldate){
		Date date = new Date();
		try{
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");		
			date = dateFormat.parse(traveldate);
		}catch(Exception e){
			logger.error("getBusFormatDateFromString " +e.getMessage());
		}
		return date;
	}
	public static OrderCustomer createOrderCustomer(BusBlockTicketRequest busBlockTicketRequest,String appkey){
		OrderCustomer orderCustomer = new OrderCustomer();
		for(BusPaxDetail busPaxDetail : busBlockTicketRequest.getBusPaxDetails()){
			orderCustomer.setFirstName(busPaxDetail.getFirstName());
			orderCustomer.setLastName(busPaxDetail.getLastName());			
			orderCustomer.setGender(busPaxDetail.getGender());
			orderCustomer.setTitle(busPaxDetail.getGender().equalsIgnoreCase("M")?"Mr":"Mrs");
			break;
		}	
		orderCustomer.setEmail(busBlockTicketRequest.getEmail());
		orderCustomer.setMobile(busBlockTicketRequest.getPhone());
		orderCustomer.setPhone(busBlockTicketRequest.getPhone());		
		orderCustomer.setVersion(1);
		orderCustomer.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
		orderCustomer.setCompanyId(Integer.parseInt(appkey.substring(appkey.indexOf("-")+1)));
		orderCustomer.setConfigId(Integer.parseInt(appkey.substring(0,appkey.indexOf("-"))));
		orderCustomer.setBookingType("Bus");
		orderCustomer.setCreatedByUserId(Integer.parseInt(BusCommonUtil.checkGetEmulatedUserById(busBlockTicketRequest)));
		return orderCustomer;
	}
	public static List<BusOrderRowMarkup> getBusMarkupDetail(BusMarkupCommissionDetails markupCommissionDetails,BusOrderRow busOrderRow)  {		
		List<BusOrderRowMarkup> busOrderRowMarkupList = new ArrayList<BusOrderRowMarkup>();
		Set<String> companyIdset = new HashSet<String>();
		companyIdset = markupCommissionDetails.getCompanyMarkupMap().keySet();
		for(String companyId:companyIdset){
			BusOrderRowMarkup busOrderRowMarkup = new BusOrderRowMarkup();
			BigDecimal markupAmount = markupCommissionDetails.getCompanyMarkupMap().get(companyId);
			busOrderRowMarkup.setCompanyId(companyId);
			busOrderRowMarkup.setMarkUp(markupAmount);
			busOrderRowMarkup.setBusOrderRow(busOrderRow);
			busOrderRowMarkupList.add(busOrderRowMarkup);
		}
		return busOrderRowMarkupList;
	}

	public static List<BusOrderRowCommission> getCommissionDetails(BusMarkupCommissionDetails markupCommissionDetails,BusOrderRow busOrderRow) {
		List<BusOrderRowCommission> BusOrderRowCommissionList = new ArrayList<BusOrderRowCommission>();	
		for(CommissionDetails commissionDetails : markupCommissionDetails.getCommissionDetailslist()){
			BusOrderRowCommission busOrderRowCommission = new BusOrderRowCommission();
			busOrderRowCommission.setCompanyId(commissionDetails.getCompanyId());
			busOrderRowCommission.setBusOrderRow(busOrderRow);
			busOrderRowCommission.setCommission(commissionDetails.getCommissionAmount());
			busOrderRowCommission.setCommissionType(commissionDetails.getCommissionType());
			busOrderRowCommission.setRateType(commissionDetails.getRateType());		
			BusOrderRowCommissionList.add(busOrderRowCommission);
		}
		return BusOrderRowCommissionList;
	}
	public static BigDecimal getTotalMarkup(BusMarkupCommissionDetails markupCommissionDetails)  {		
		Set<String> compnyIdset=new HashSet<String>();
		compnyIdset = markupCommissionDetails.getCompanyMarkupMap().keySet();
		BigDecimal totalmarkupAmount=new BigDecimal("0");
		for(String companyId:compnyIdset){
			BigDecimal markupAmount = markupCommissionDetails.getCompanyMarkupMap().get(companyId);		
			totalmarkupAmount=totalmarkupAmount.add(markupAmount);
		}
		return totalmarkupAmount;
	}
	public static BusOrderRowServiceTax createBusOrderRowServiceTax(BusServiceTaxConfig busServiceTaxConfig, BusOrderRowServiceTax busOrderRowServiceTax) {
		busOrderRowServiceTax.setApplicableFare(busServiceTaxConfig.getApplicableFare());
		busOrderRowServiceTax.setBasicTax(busServiceTaxConfig.getBasicTax());
		busOrderRowServiceTax.setConvenienceFee(busServiceTaxConfig.getConvenienceFee());
		busOrderRowServiceTax.setKrishiKalyanCess(busServiceTaxConfig.getKrishiKalyanCess());
		busOrderRowServiceTax.setManagementFee(busServiceTaxConfig.getManagementFee());		
		busOrderRowServiceTax.setSwatchBharathCess(busServiceTaxConfig.getSwatchBharathCess());
		busOrderRowServiceTax.setTotalTax(busServiceTaxConfig.getTotalTax());
		return busOrderRowServiceTax;
	}
	public static BusOrderRowGstTax createBusOrderRowGstTax(BusGstTaxConfig busGstTaxConfig, BusOrderRowGstTax busOrderRowGstTax,Company company,Company parentCompany,int noofpassengers) {

		BigDecimal totalPassengers = new BigDecimal(noofpassengers);
		BigDecimal CGST = new BigDecimal("0.0");
		BigDecimal SGST = new BigDecimal("0.0");
		BigDecimal IGST = new BigDecimal("0.0");
		BigDecimal UGST = new BigDecimal("0.0");
		BigDecimal totalGst = new BigDecimal("0.0");
		BigDecimal managementFee  = new BigDecimal("0.0");
		boolean isParentCompanyUT = IndianUnionTerritories.isUnionter(parentCompany.getBillingstate().trim());
		boolean isBillingCompanyUT = IndianUnionTerritories.isUnionter(company.getBillingstate().trim());
		managementFee = busGstTaxConfig.getManagementFee().multiply(totalPassengers);

		if(isParentCompanyUT && isBillingCompanyUT){
			CGST = busGstTaxConfig.getCGST();
			UGST = busGstTaxConfig.getUGST();
		}
		if(!company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim()) && IndianUnionTerritories.isUnionter(company.getBillingstate().trim())){
			CGST = busGstTaxConfig.getCGST();
			UGST = busGstTaxConfig.getUGST();
		}
		if(!isParentCompanyUT && !isBillingCompanyUT){
			if(company.getBillingstate().equalsIgnoreCase(parentCompany.getBillingstate())){
				CGST = busGstTaxConfig.getCGST();
				SGST =  busGstTaxConfig.getSGST();				
			}
		}
		if(isParentCompanyUT && !isBillingCompanyUT){
			if(!company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim()) && !IndianUnionTerritories.isUnionter(company.getBillingstate().trim())){
				IGST =  busGstTaxConfig.getIGST();		
			}
		}				
		totalGst = CGST.add(SGST).add(IGST).add(UGST);	
		busOrderRowGstTax.setCGST(CGST);
		busOrderRowGstTax.setSGST(SGST);
		busOrderRowGstTax.setIGST(IGST);
		busOrderRowGstTax.setUGST(UGST);
		busOrderRowGstTax.setVersion(1);
		busOrderRowGstTax.setManagementFee(managementFee);
		busOrderRowGstTax.setConvenienceFee(busGstTaxConfig.getConvenienceFee());
		busOrderRowGstTax.setTotalGst(totalGst);
		busOrderRowGstTax.setApplicableFare(busGstTaxConfig.getApplicableFare());
		busOrderRowGstTax.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
		return busOrderRowGstTax;
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
	public static Map<Integer,CutandPayModel> getCutandPayModelUsers(BusBlockTicketResponse busBlockTicketResponse,CompanyConfig companyConfig,List<User> userList,Map<String, List<BusMarkUpConfig>> markups,List<CommissionDetails> commissions,BusOrderRow busOrderRow){
		Map<Integer,CutandPayModel> cutandpayMap = new LinkedHashMap<>();		
		try{
			BigDecimal payableamt = busBlockTicketResponse.getBlockFareDetail().getTotalPayableAmount();			
			BigDecimal totalPassengers = new BigDecimal(busBlockTicketResponse.getBusPaxDetails().size());
			Map<String, BigDecimal> busMarkups =  new LinkedHashMap<>();		
			Map<String, BigDecimal> busCommissions =  new LinkedHashMap<>();		

			for (Entry<String, List<BusMarkUpConfig>> entry : markups.entrySet()) {
				List<BusMarkUpConfig> BusMarkUpConfiglist = entry.getValue();
				String companyid = entry.getKey();
				if (BusMarkUpConfiglist != null) {
					for (int i = 0; i < BusMarkUpConfiglist.size(); i++) {
						BusMarkUpConfig busMarkUpConfig = BusMarkUpConfiglist.get(i);
						BigDecimal MarkupValue = busMarkUpConfig.getMarkupAmt();					
						MarkupValue = MarkupValue.multiply(totalPassengers);
						busMarkups.put(companyid, MarkupValue);
					}
				}
			}

			for(CommissionDetails commissionDetails: commissions){
				BigDecimal commissionAmt = commissionDetails.getCommissionAmount();
				String companyId = commissionDetails.getCompanyId();
				busCommissions.put(companyId, commissionAmt);
			}


			for (User payableUser : userList) {
				BigDecimal markupAmount = new BigDecimal(0);
				BigDecimal commisionAmount = new BigDecimal(0);
				markupAmount = busMarkups!=null && busMarkups.size()>0 && busMarkups.get(String.valueOf(payableUser.getCompanyid()))!=null?busMarkups.get(String.valueOf(payableUser.getCompanyid())):new BigDecimal(0);
				commisionAmount = busCommissions!=null && busCommissions.get(String.valueOf(payableUser.getCompanyid()))!=null ?busCommissions.get(String.valueOf(payableUser.getCompanyid())):new BigDecimal(0);
				BigDecimal busMarksForAllPassengers = markupAmount.multiply(totalPassengers);
				CutandPayModel cutandpay = new CutandPayModel();
				if(String.valueOf(payableUser.getId()).equalsIgnoreCase(busOrderRow.getUserId())){
					BigDecimal payableamtInner = busBlockTicketResponse.getBlockFareDetail().getTotalPayableAmount().subtract(commisionAmount);
					cutandpay.setUserId(String.valueOf(payableUser.getId()));  
					cutandpay.setPayableAmount(payableamtInner);
					cutandpay.setBookingRemarks(CommonBookingStatusEnum.BUS_REMARKS.getMessage());
					cutandpay.setBookingStatus(true);
				}
				else{
					payableamt = payableamt.subtract(busMarksForAllPassengers);
					payableamt = payableamt.subtract(commisionAmount);
					cutandpay.setUserId(String.valueOf(payableUser.getId()));  
					cutandpay.setPayableAmount(payableamt);
					cutandpay.setBookingRemarks(CommonBookingStatusEnum.BUS_REMARKS.getMessage());
					cutandpay.setBookingStatus(true);
				}
				cutandpayMap.put(payableUser.getId(), cutandpay);
			}
		}catch(Exception e){

		}
		return cutandpayMap;
	}

	/*public static BusFareAlertDetail getBusFareAlertDetail(BusBlockedSeatTemp busBlockedSeatTemp,BusBlockTicketRequest busBlockTicketRequest,BusCommonDao busCommonDao,String routeScheduleId,BusOrderRow busOrderRow,String reasons){
		BusFareAlertDetail busFareAlertDetail = null;
		try{
			busFareAlertDetail = new BusFareAlertDetail();
			BusSearchTemp busSearchTemp = busCommonDao.getBusSearchTemp(busBlockTicketRequest.getSearchkey());
			TayyarahBusSearchMap  busSearchMap = (TayyarahBusSearchMap) BusCommonUtil.convertByteArrayToObject(busSearchTemp.getBusSearchData());			
			AvailableBuses availableBuses =  busSearchMap.getAvailableBusesMap().get(busBlockTicketRequest.getSearchkey());		
			for (AvailableBus availableBus : availableBuses.getAvailableBus()) {
				if(availableBus.getRouteScheduleId().equalsIgnoreCase(routeScheduleId)){
					busFareAlertDetail.setBusType(availableBus.getBusType());
					busFareAlertDetail.setOperatorName(availableBus.getOperatorName());
					busFareAlertDetail.setDepartureTime(availableBus.getDepartureTime());
					busFareAlertDetail.setDestination(availableBuses.getDestination());
					busFareAlertDetail.setOrigin(availableBuses.getOrigin());
					busFareAlertDetail.setTravelDate(availableBuses.getOnwardDate());
					busFareAlertDetail.setBookingDate(busOrderRow.getBusBookingDate());
					busFareAlertDetail.setBoardingPoint(busBlockTicketRequest.getBoardingPoint().getLoc());
					if(availableBus.getFare().getBookingPrice().contains(",")){
						String[] prices = BusCommonUtil.getStringArrayFromString(availableBus.getFare().getBookingPrice(),",");
						ArrayList<String> price = (ArrayList<String>) Arrays.asList(prices);
						Collections.sort(price);						
						busFareAlertDetail.setTotalFare(new BigDecimal(price.get(0)));
					}else{
						busFareAlertDetail.setTotalFare(new BigDecimal(availableBus.getFare().getBookingPrice()));
					}
					busFareAlertDetail.setCompanyId(Integer.parseInt(busOrderRow.getCompanyId()));
					busFareAlertDetail.setConfigId(Integer.parseInt(busOrderRow.getConfigId()));
					busFareAlertDetail.setCreatedbyuserid(busOrderRow.getUserId());
					busFareAlertDetail.setOrderid(busOrderRow.getOrderId());
					busFareAlertDetail.setReasons(reasons);
				}
			}

		}catch(Exception e){

		}
		return busFareAlertDetail;
	}*/

}
