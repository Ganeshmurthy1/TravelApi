package com.tayyarah.esmart.bus.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.tayyarah.api.bus.esmart.model.ApiAvailableBus;
import com.tayyarah.api.bus.esmart.model.ApiCancelResponse;
import com.tayyarah.api.bus.esmart.model.ApiConfirmTicket;
import com.tayyarah.api.bus.esmart.model.BlockTicketResponse;
import com.tayyarah.api.bus.esmart.model.BoardingPoint;
import com.tayyarah.api.bus.esmart.model.DroppingPoint;
import com.tayyarah.api.bus.esmart.model.GetAvaiableSeats;
import com.tayyarah.api.bus.esmart.model.GetAvailableBus;
import com.tayyarah.api.bus.esmart.model.GetStations;
import com.tayyarah.api.bus.esmart.model.Seat;
import com.tayyarah.api.bus.esmart.model.StationList;
import com.tayyarah.bus.dao.BusCommonDao;
import com.tayyarah.bus.entity.BusOrderRow;
import com.tayyarah.bus.model.AvailableBus;
import com.tayyarah.bus.model.AvailableBuses;
import com.tayyarah.bus.model.AvailableSeats;
import com.tayyarah.bus.model.BlockBusDetail;
import com.tayyarah.bus.model.BlockFareDetail;
import com.tayyarah.bus.model.BusBlockTicketRequest;
import com.tayyarah.bus.model.BusBlockTicketResponse;
import com.tayyarah.bus.model.BusCancelRequest;
import com.tayyarah.bus.model.BusCancelResponse;
import com.tayyarah.bus.model.BusConfirmResponse;
import com.tayyarah.bus.model.BusLayoutRequest;
import com.tayyarah.bus.model.BusMarkUpConfig;
import com.tayyarah.bus.model.BusPaxDetail;
import com.tayyarah.bus.model.BusSearchFilters;
import com.tayyarah.bus.model.BusSearchRequest;
import com.tayyarah.bus.model.BusServiceTax;
import com.tayyarah.bus.model.BusStations;
import com.tayyarah.bus.model.Fare;
import com.tayyarah.bus.model.SeatFare;
import com.tayyarah.bus.model.Station;
import com.tayyarah.bus.model.Status;
import com.tayyarah.bus.model.TayyarahBusSearchMap;
import com.tayyarah.bus.model.TayyarahBusSeatMap;
import com.tayyarah.bus.util.BusCommonUtil;
import com.tayyarah.bus.util.BusErrorMessages;
import com.tayyarah.bus.util.BusException;
import com.tayyarah.bus.util.BusMarkupHelper;
import com.tayyarah.bus.util.ErrorCodeCustomerEnum;
import com.tayyarah.common.gstconfig.model.BusGstTax;
import com.tayyarah.common.model.CurrencyConversionMap;
import com.tayyarah.common.util.AmountRoundingModeUtil;
import com.tayyarah.common.util.ApiResponseSaver;
import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.company.entity.Company;
import com.tayyarah.company.entity.CompanyConfig;

public class EsmartResponseParser {
	static final Logger logger = Logger.getLogger(EsmartBusConfig.class);
	public static BusStations busStationsParser(GetStations stationReponse){
		BusStations busStations = null;
		try{
			busStations = new BusStations();
			List<Station> busStationList = null;
			Status status = new Status();
			if(stationReponse.getApiStatus().getSuccess()){		
				busStationList = new ArrayList<>();
				status.setCode(Status.SUCCESSCODE);
				status.setMessage("Success");				
				for (StationList stations : stationReponse.getStationList()) {
					Station station = new Station();
					station.setStationId(stations.getStationId());
					station.setStationName(stations.getStationName());	
					busStationList.add(station);
				}				
			}else{			
				status.setCode(Status.FAILEDCODE);
				status.setMessage("No stations available");				
			}
			busStations.setStationList(busStationList);
			busStations.setStatus(status);
		}catch(Exception e){
			logger.error("busStationsParser " +e.getMessage());
			throw new BusException(ErrorCodeCustomerEnum.Exception, BusErrorMessages.NO_STATIONAVAILABLE.getErrorMessage());
		}
		return busStations;
	}
	public static AvailableBuses busAvailableParser(GetAvailableBus availableBusReponse,Map<String,List<BusMarkUpConfig>> markupMap,BusSearchRequest busSearchRequest,CurrencyConversionMap currencyConversionMap,String decAppKey,CompanyConfigDAO companyConfigDAO,CompanyDao CompanyDAO){
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

			if(availableBusReponse.getApiStatus().getSuccess()){
				status.setCode(Status.SUCCESSCODE);
				status.setMessage("Success");
				for (ApiAvailableBus apiAvailableBus : availableBusReponse.getApiAvailableBuses()) {
					AvailableBus availableBus = new AvailableBus();
					busOperators.add(apiAvailableBus.getOperatorName());
					busTypes.add(apiAvailableBus.getBusType());
					List<com.tayyarah.bus.model.BoardingPoint> BPList = new ArrayList<>();
					List<com.tayyarah.bus.model.DroppingPoint> DPList = new ArrayList<>();
					if(apiAvailableBus.getBoardingPoints() != null && apiAvailableBus.getBoardingPoints().size() > 0){
						for (BoardingPoint boardingPoint : apiAvailableBus.getBoardingPoints()) {
							boardingPoints.add(boardingPoint.getLocation());							
							com.tayyarah.bus.model.BoardingPoint BP = new com.tayyarah.bus.model.BoardingPoint();
							BP.setId(boardingPoint.getId());
							BP.setLoc(boardingPoint.getLocation());
							BP.setTime(boardingPoint.getTime());
							BPList.add(BP);
						}
					}
					if(apiAvailableBus.getDroppingPoints() != null && apiAvailableBus.getDroppingPoints().size() > 0){
						for (DroppingPoint droppingPoint : apiAvailableBus.getDroppingPoints()) {
							droppingPoints.add(droppingPoint.getLocation());							
							com.tayyarah.bus.model.DroppingPoint DP = new com.tayyarah.bus.model.DroppingPoint();
							DP.setId(droppingPoint.getId());
							DP.setLoc(droppingPoint.getLocation());
							DP.setTime(droppingPoint.getTime());
							DPList.add(DP);
						}
					}

					Fare busFare = new Fare();
					busFare.setApiPrice(apiAvailableBus.getFare());
					busFare.setBookingPrice(apiAvailableBus.getFare());
					busFare.setBusServiceTax(null);
					busFare.setDiscount("0");
					busFare.setPayableAmount(apiAvailableBus.getFare());
					busFare.setPriceWithOutMarkup(apiAvailableBus.getFare());
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
					
					if(busFare.getBookingPrice().contains(",")){
						String[] prices = BusCommonUtil.getStringArrayFromString(busFare.getBookingPrice(),",");
						for (String price : prices) {
							fares.add(price);	
						}
					}else{
						fares.add(busFare.getBookingPrice());
					}
					availableBus.setArrivalTime(apiAvailableBus.getArrivalTime());
					availableBus.setAvailableSeats(apiAvailableBus.getAvailableSeats());
					availableBus.setBoardingPoints(BPList);
					availableBus.setDroppingPoints(DPList);
					availableBus.setBusType(apiAvailableBus.getBusType());
					availableBus.setCancellationPolicy(apiAvailableBus.getCancellationPolicy());
					availableBus.setCommPCT(new BigDecimal(apiAvailableBus.getCommPCT()).setScale(2,RoundingMode.UP));
					availableBus.setDepartureTime(apiAvailableBus.getDepartureTime());
					availableBus.setIdProofRequired(apiAvailableBus.getIdProofRequired());
					availableBus.setInventoryType(apiAvailableBus.getInventoryType());
					availableBus.setIsChildConcession(apiAvailableBus.getIsChildConcession());
					availableBus.setIsFareUpdateRequired(apiAvailableBus.getIsFareUpdateRequired());
					availableBus.setIsGetLayoutByBPDP(apiAvailableBus.getIsGetLayoutByBPDP());
					availableBus.setIsOpLogoRequired(apiAvailableBus.getIsOpLogoRequired());
					availableBus.setIsOpTicketTemplateRequired(apiAvailableBus.getIsOpTicketTemplateRequired());
					availableBus.setIsRTC(apiAvailableBus.getIsRTC());
					availableBus.setmTicketAllowed(apiAvailableBus.getMTicketAllowed());
					availableBus.setOperatorId(apiAvailableBus.getOperatorId());
					availableBus.setOperatorName(apiAvailableBus.getOperatorName());
					availableBus.setPartialCancellationAllowed(apiAvailableBus.getPartialCancellationAllowed());
					availableBus.setRouteScheduleId(apiAvailableBus.getRouteScheduleId());
					availableBus.setServiceId(apiAvailableBus.getServiceId());
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
				status.setCode(Status.FAILEDCODE);
				status.setMessage("No buses available for this search criteria");	
			}		

			availableBuses.setStatus(status);

		}catch(Exception e){
			logger.error("busAvailableBusParser " +e.getMessage());
			throw new BusException(ErrorCodeCustomerEnum.Exception, BusErrorMessages.NO_BUSAVAILABLE.getErrorMessage());
		}
		return availableBuses;
	}

	public static AvailableSeats availableSeatParser(GetAvaiableSeats getAvaiableSeats,Map<String,List<BusMarkUpConfig>> markupMap,BusLayoutRequest busLayoutRequest,CurrencyConversionMap currencyConversionMap,String decAppKey,CompanyConfigDAO companyConfigDAO,TayyarahBusSearchMap  busSearchMap,CompanyDao CompanyDAO){
		AvailableSeats availableSeats = new AvailableSeats();
		try{
			Status status = new Status();

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

			if(getAvaiableSeats.getApiStatus().getSuccess()){
				status.setCode(Status.SUCCESSCODE);
				status.setMessage("Success");
				List<com.tayyarah.bus.model.Seat> seatList = new ArrayList<>();
				for (Seat apiseat : getAvaiableSeats.getSeats()) {
					com.tayyarah.bus.model.Seat seat = new com.tayyarah.bus.model.Seat();
					seat.setAc(apiseat.getAc());
					seat.setAvailable(apiseat.getAvailable());
					seat.setBookedBy(apiseat.getBookedBy());
					seat.setColumn(apiseat.getColumn());
					seat.setId(apiseat.getId());
					seat.setzIndex(apiseat.getZIndex());
					seat.setLadiesSeat(apiseat.getLadiesSeat());
					seat.setLength(apiseat.getLength());
					seat.setRow(apiseat.getRow());
					seat.setSleeper(apiseat.getSleeper());
					seat.setWidth(apiseat.getWidth());

					SeatFare fare = new SeatFare();
								
					fare.setApiPrice(new BigDecimal(apiseat.getFare()));
					fare.setApiTotalFareWithTaxes(new BigDecimal(apiseat.getTotalFareWithTaxes()));
					fare.setApiCommission(apiseat.getCommission()!=null?new BigDecimal(apiseat.getCommission()):new BigDecimal(0));
					fare.setApiOperatorServiceChargeAbsolute(new BigDecimal(apiseat.getOperatorServiceChargeAbsolute()));
					fare.setApiOperatorServiceChargePercent(new BigDecimal(apiseat.getOperatorServiceChargePercent()));
					fare.setApiServiceTaxAmount(new BigDecimal(apiseat.getServiceTaxAmount()));					
					fare.setDiscount(new BigDecimal(0));
					fare.setPriceWithOutMarkup(new BigDecimal(apiseat.getTotalFareWithTaxes()));

					// Apply MarkUp 
					BusMarkupHelper.applyMarkupOnSeatResponse(markupMap, fare, busLayoutRequest,companyConfig);

					BusServiceTax busServiceTax = null;
					BusGstTax busGstTax = null;
					if(companyConfig != null){
						if(companyConfig.getCompanyConfigType().isB2E()){
							if(companyConfig.getTaxtype()!=null && companyConfig.getTaxtype().equalsIgnoreCase("GST")){
								busGstTax = new BusGstTax();
								busGstTax = BusCommonUtil.getBusGstTax(companyConfig,company,parentCompany);
								BigDecimal totalPrice = fare.getBookingPrice();
								fare.setBasePrice(totalPrice);	
								BigDecimal totalPriceAfterServiceTax = totalPrice.add(busGstTax.getTotalTax()).add(busGstTax.getManagementFee());
								totalPriceAfterServiceTax = AmountRoundingModeUtil.roundingModeForBus(totalPriceAfterServiceTax);
								BigDecimal totalPayable = fare.getTotalPayableAmount().add(busGstTax.getManagementFee()).add(busGstTax.getTotalTax());
								totalPayable = AmountRoundingModeUtil.roundingModeForBus(totalPayable);
								fare.setBookingPrice(totalPriceAfterServiceTax);	
								fare.setTotalPayableAmount(totalPayable);
								fare.setTaxAmount(busGstTax.getTotalTax());
								
							}else{
							busServiceTax = new BusServiceTax();
							busServiceTax = BusCommonUtil.getBusServiceTax(fare.getBookingPrice().toString(), companyConfig);
							BigDecimal totalPrice = fare.getBookingPrice();
							fare.setBasePrice(totalPrice);	
							BigDecimal totalPriceAfterServiceTax = totalPrice.add(busServiceTax.getTotalServiceTax()).add(busServiceTax.getManagementFee());
							totalPriceAfterServiceTax = AmountRoundingModeUtil.roundingModeForBus(totalPriceAfterServiceTax);
							BigDecimal totalPayable = fare.getTotalPayableAmount().add(busServiceTax.getManagementFee()).add(busServiceTax.getTotalServiceTax());
							totalPayable = AmountRoundingModeUtil.roundingModeForBus(totalPayable);
							fare.setBookingPrice(totalPriceAfterServiceTax);	
							fare.setTotalPayableAmount(totalPayable);
							fare.setTaxAmount(busServiceTax.getTotalServiceTax());
									
							}
						}else{
							fare.setBasePrice(fare.getBookingPrice());
							fare.setTaxAmount(new BigDecimal(0));
						}
					}
					fare.setBusGstTax(busGstTax);
					fare.setBusServiceTax(busServiceTax);
					seat.setSeatFare(fare);		
					seatList.add(seat);
				}
				availableSeats.setSeats(seatList);
				List<com.tayyarah.bus.model.BoardingPoint> bpList = new ArrayList<>();
				if(getAvaiableSeats.getBoardingPoints()!=null && getAvaiableSeats.getBoardingPoints().size() > 0){				
					for (BoardingPoint boardingPoint : getAvaiableSeats.getBoardingPoints()) {
						com.tayyarah.bus.model.BoardingPoint bp = new com.tayyarah.bus.model.BoardingPoint();
						bp.setId(boardingPoint.getId());
						bp.setLoc(boardingPoint.getLocation());
						bp.setTime(boardingPoint.getTime());
						bpList.add(bp);
					}
				}else{
					List<com.tayyarah.bus.model.BoardingPoint> boardingPointsList = busSearchMap.getBoardingPointsMap().get(busLayoutRequest.getRouteScheduleId());
					bpList = boardingPointsList;
				}
				List<com.tayyarah.bus.model.DroppingPoint> dpList = new ArrayList<>();
				if(busSearchMap.getDroppingPointMap() != null && busSearchMap.getDroppingPointMap().size() > 0){
					dpList = busSearchMap.getDroppingPointMap().get(busLayoutRequest.getRouteScheduleId());
				}else{
					com.tayyarah.bus.model.DroppingPoint dp = new com.tayyarah.bus.model.DroppingPoint();
					dp.setLoc(busLayoutRequest.getDestination());
					dp.setId("0");
					dp.setTime("");
					dpList.add(dp);
				}
				
				availableSeats.setDroppingPoints(dpList);
				availableSeats.setBoardingPoints(bpList);
				availableSeats.setInventoryType(getAvaiableSeats.getInventoryType());
				availableSeats.setSearchKey(busLayoutRequest.getSearchkey());

			}else{
				status.setCode(Status.FAILEDCODE);
				status.setMessage("No Seats available");	
			}	

			availableSeats.setStatus(status);

		}catch(Exception e){
			logger.error("availableSeatParser " +e.getMessage());
		}
		return availableSeats;
	}
	
	public static BusBlockTicketResponse blockTicketResponseParser(BlockTicketResponse blockTicketResponse,BusBlockTicketRequest busBlockTicketRequest,Map<String,List<BusMarkUpConfig>> markupMap,CurrencyConversionMap currencyConversionMap,String decAppKey,CompanyConfigDAO companyConfigDAO,TayyarahBusSeatMap tayyarahBusSeatMap,TayyarahBusSearchMap  busSearchMap){
		BusBlockTicketResponse busBlockTicketResponse = new BusBlockTicketResponse();
		try{
			Status status = new Status();
			if(blockTicketResponse.getApiStatus().getSuccess()){
				status.setCode(Status.SUCCESSCODE);
				status.setMessage("Success");
				busBlockTicketResponse.setOrigin(busBlockTicketRequest.getOrigin());
				busBlockTicketResponse.setDestination(busBlockTicketRequest.getDestination());
				busBlockTicketResponse.setOnwardDate(busBlockTicketRequest.getOnwardDate());
				busBlockTicketResponse.setBlockTicketKey(blockTicketResponse.getBlockTicketKey());
				List<BusPaxDetail> busPaxDetailList = new ArrayList<>();
				for (BusPaxDetail busPaxDetailObj : busBlockTicketRequest.getBusPaxDetails()) {
					busPaxDetailList.add(busPaxDetailObj);
				}
				BlockBusDetail blockBusDetail = BusCommonUtil.getBlockedBusDetail(busSearchMap, busBlockTicketRequest.getSearchkey(), busBlockTicketRequest.getRouteScheduleId(), busBlockTicketRequest);
				busBlockTicketResponse.setBlockBusDetail(blockBusDetail);
				BlockFareDetail blockFareDetail = BusCommonUtil.getBlockedTicketFareDetail(tayyarahBusSeatMap, busBlockTicketRequest);
				busBlockTicketResponse.setBlockFareDetail(blockFareDetail);				
				busBlockTicketResponse.setBusPaxDetails(busPaxDetailList);
				
			}else{
				status.setCode(Status.FAILEDCODE);
				status.setMessage("failed to block the ticket");
			}
			
			busBlockTicketResponse.setStatus(status);
			
		}catch(Exception e){
			throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.NO_BLOCKFAILED.getErrorMessage());
		}
		return busBlockTicketResponse;
	}

	public static BusConfirmResponse confirmTicketResposeParser(ApiConfirmTicket apiConfirmTicket,BusBlockTicketResponse busBlockTicketResponse,BusOrderRow busOrderRow,BusCommonDao busCommonDao){
		BusConfirmResponse busConfirmResponse = new BusConfirmResponse();
		try{
			BlockBusDetail blockBusDetail = busBlockTicketResponse.getBlockBusDetail();
			BlockFareDetail blockFareDetail = busBlockTicketResponse.getBlockFareDetail();
			if(apiConfirmTicket.getApiStatus() != null)			
				ApiResponseSaver.saveBusApiResponse(busCommonDao, apiConfirmTicket, busOrderRow);
			
			Status status = new Status();
			if(apiConfirmTicket.getApiStatus().getSuccess()){
				status.setCode(Status.SUCCESSCODE);
				status.setMessage("Success");
				
				busConfirmResponse.setBlockBusDetail(blockBusDetail);
				busConfirmResponse.setBlockFareDetail(blockFareDetail);
				busConfirmResponse.setConfirmationNo(apiConfirmTicket.getEtstnumber());
				busConfirmResponse.setOrigin(busOrderRow.getOrigin());
				busConfirmResponse.setDestination(busOrderRow.getDestination());
				busConfirmResponse.setTransactionkey(busOrderRow.getTransactionKey());
				busConfirmResponse.setOnwardDate(BusCommonUtil.getBusFormatDateToString(busOrderRow.getTravelDate()));
				busConfirmResponse.setBusPaxDetails(busBlockTicketResponse.getBusPaxDetails());
				busConfirmResponse.setOperatorPnr(apiConfirmTicket.getOpPNR());
				busConfirmResponse.setSearchkey(busBlockTicketResponse.getSearchkey());
				busConfirmResponse.setCancellationPolicy(apiConfirmTicket.getCancellationPolicy());
				busConfirmResponse.setTripCode(apiConfirmTicket.getTripCode());		
				busConfirmResponse.setOrderId(busOrderRow.getOrderId());
				
			}else{
				status.setCode(Status.FAILEDCODE);
				status.setMessage("failed to book the ticket");
			}
			busConfirmResponse.setStatus(status);
			
		}catch(Exception e){
			throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.NO_BOOKINGFAILED.getErrorMessage());
		}
		return busConfirmResponse;
	}
	
	public static BusConfirmResponse paymentTicketResposeParser(ApiConfirmTicket apiConfirmTicket,BusBlockTicketResponse busBlockTicketResponse,BusOrderRow busOrderRow){
		BusConfirmResponse busConfirmResponse = new BusConfirmResponse();
		try{
			BlockBusDetail blockBusDetail = busBlockTicketResponse.getBlockBusDetail();
			BlockFareDetail blockFareDetail = busBlockTicketResponse.getBlockFareDetail();
			
			Status status = new Status();
			if(apiConfirmTicket.getApiStatus().getSuccess()){
				status.setCode(Status.SUCCESSCODE);
				status.setMessage("Success");
				BigDecimal bookingPrice = blockFareDetail.getBookingPrice().add(busOrderRow.getProcessingFees());
				BigDecimal totalPayableAmount = blockFareDetail.getTotalPayableAmount().add(busOrderRow.getProcessingFees());
				blockFareDetail.setBookingPrice(bookingPrice);
				blockFareDetail.setTotalPayableAmount(totalPayableAmount);
				busConfirmResponse.setBlockBusDetail(blockBusDetail);
				busConfirmResponse.setBlockFareDetail(blockFareDetail);
				busConfirmResponse.setConfirmationNo(apiConfirmTicket.getEtstnumber());
				busConfirmResponse.setOrigin(busOrderRow.getOrigin());
				busConfirmResponse.setDestination(busOrderRow.getDestination());
				busConfirmResponse.setTransactionkey(busOrderRow.getTransactionKey());
				busConfirmResponse.setOnwardDate(BusCommonUtil.getBusFormatDateToString(busOrderRow.getTravelDate()));
				busConfirmResponse.setBusPaxDetails(busBlockTicketResponse.getBusPaxDetails());
				busConfirmResponse.setOperatorPnr(apiConfirmTicket.getOpPNR());
				busConfirmResponse.setSearchkey(busBlockTicketResponse.getSearchkey());
				busConfirmResponse.setCancellationPolicy(apiConfirmTicket.getCancellationPolicy());
				busConfirmResponse.setTripCode(apiConfirmTicket.getTripCode());		
				busConfirmResponse.setOrderId(busOrderRow.getOrderId());
				
			}else{
				status.setCode(Status.FAILEDCODE);
				status.setMessage("failed to book the ticket");
			}
			busConfirmResponse.setStatus(status);
			
		}catch(Exception e){
			throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.NO_BOOKINGFAILED.getErrorMessage());
		}
		return busConfirmResponse;
	}
	
	public static BusCancelResponse cancellationResponseParser(ApiCancelResponse apiCancelResponse,BusCancelRequest busCancelRequest,BusOrderRow busOrderRow,BigDecimal totalAmtPaid,String EtsTicketNo){
		BusCancelResponse busCancelResponse = new BusCancelResponse();
		try{
			Status status = new Status();
			if(apiCancelResponse.getApiStatus().getSuccess()){
				status.setCode(Status.SUCCESSCODE);
				status.setMessage("Success");
				busCancelResponse.setApiPrice(new BigDecimal(apiCancelResponse.getTotalTicketFare()));
				busCancelResponse.setOrderid(busOrderRow.getOrderId());
				busCancelResponse.setTotalAmount(busOrderRow.getTotalAmount());
				busCancelResponse.setCancellationCharges(new BigDecimal(apiCancelResponse.getCancellationCharges()));
				busCancelResponse.setCancelChargesPercentage(apiCancelResponse.getCancelChargesPercentage());
				busCancelResponse.setIsPartiallyCancellable(apiCancelResponse.getPartiallyCancellable());
				busCancelResponse.setSearchkey(busCancelRequest.getSearchkey());
				busCancelResponse.setTotalAmountPaid(totalAmtPaid);
				busCancelResponse.setSeatNbr(busCancelRequest.getSeatNbr());
				busCancelResponse.setTransactionkey(busCancelRequest.getTransactionkey());
				busCancelResponse.setIsCancellable(apiCancelResponse.getCancellable());
				busCancelResponse.setTotalRefundAmount(new BigDecimal(apiCancelResponse.getTotalRefundAmount()));
				busCancelResponse.setApiConfirtmationNo(EtsTicketNo);
			}else{
				status.setCode(Status.FAILEDCODE);
				status.setMessage("failed to cancel the ticket");
			}
			busCancelResponse.setStatus(status);
			
		}catch(Exception e){
			throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.CANCELLATIONFAILED.getErrorMessage());
		}
		return busCancelResponse;
	}
}
