package com.tayyarah.esmart.bus.util;

import java.util.ArrayList;
import java.util.List;

import com.tayyarah.api.bus.esmart.model.ApiCancelConfirmation;
import com.tayyarah.api.bus.esmart.model.BlockSeatPaxDetail;
import com.tayyarah.api.bus.esmart.model.BlockTicketRequest;
import com.tayyarah.api.bus.esmart.model.BoardingPoint;
import com.tayyarah.api.bus.esmart.model.DroppingPoint;
import com.tayyarah.bus.model.BusBlockTicketRequest;
import com.tayyarah.bus.model.BusCancelRequest;
import com.tayyarah.bus.model.BusPaxDetail;
import com.tayyarah.bus.model.SeatFare;
import com.tayyarah.bus.model.TayyarahBusSeatMap;
import com.tayyarah.bus.util.BusCommonUtil;

public class EsmartRequestBuilder {

	public static String getAvailableBusRequest(String origin,String destination,String onwarddate){
		String request = "?sourceCity="+origin+"&destinationCity="+destination+"&doj="+onwarddate;
		return request;
	}
	public static String getAvailableBusSeatsRequest(String origin,String destination,String onwarddate,String inventoryType,String routeScheduleId){
		String request = "?sourceCity="+origin+"&destinationCity="+destination+"&doj="+onwarddate+"&inventoryType="+inventoryType+"&routeScheduleId="+routeScheduleId;
		return request;		
	}
	
	public static BlockTicketRequest getBlockSeatRequest(BusBlockTicketRequest busBlockTicketRequest,TayyarahBusSeatMap tayyarahBusSeatMap){
	
		BlockTicketRequest blockTicketRequest = new BlockTicketRequest();
		BoardingPoint boardingPoint = null;
		if(busBlockTicketRequest.getBoardingPoint() != null){
			boardingPoint = new BoardingPoint();
			boardingPoint.setId(busBlockTicketRequest.getBoardingPoint().getId());
			boardingPoint.setLocation(busBlockTicketRequest.getBoardingPoint().getLoc());
			boardingPoint.setTime(busBlockTicketRequest.getBoardingPoint().getTime());
		}
		DroppingPoint droppingPoint = null;
		if(busBlockTicketRequest.getDroppingPoint() != null){
			droppingPoint = new DroppingPoint();
			droppingPoint.setId(busBlockTicketRequest.getDroppingPoint().getId());
			droppingPoint.setLocation(busBlockTicketRequest.getDroppingPoint().getLoc());
			droppingPoint.setTime(busBlockTicketRequest.getDroppingPoint().getTime());
		}
		List<BlockSeatPaxDetail> blockSeatPaxDetailList = new ArrayList<>();
		
		for (BusPaxDetail paxDetail : busBlockTicketRequest.getBusPaxDetails()) {
			BlockSeatPaxDetail blockSeatPaxDetail = new BlockSeatPaxDetail();
			blockSeatPaxDetail.setAc(paxDetail.getIsAc());
			blockSeatPaxDetail.setAge(paxDetail.getAge());
			blockSeatPaxDetail.setEmail(busBlockTicketRequest.getEmail());
			blockSeatPaxDetail.setTitle(paxDetail.getGender().equalsIgnoreCase("Male")?"Mr":"Mrs");
			blockSeatPaxDetail.setSex(paxDetail.getGender());
			blockSeatPaxDetail.setIdNumber("1213454");
			blockSeatPaxDetail.setIdType("PAN");
			blockSeatPaxDetail.setLadiesSeat(paxDetail.getIsladiesSeat());
			blockSeatPaxDetail.setName(paxDetail.getFirstName());
			blockSeatPaxDetail.setLastName(paxDetail.getLastName());
			blockSeatPaxDetail.setMobile(busBlockTicketRequest.getPhone());
			blockSeatPaxDetail.setNameOnId(paxDetail.getFirstName());
			blockSeatPaxDetail.setPrimary(paxDetail.getIsPrimaryPax());
			blockSeatPaxDetail.setSeatNbr(paxDetail.getSeatNbr());
			blockSeatPaxDetail.setSleeper(paxDetail.getIsSleeper());
			
			SeatFare seatFare = BusCommonUtil.getSeatFareDetail(tayyarahBusSeatMap, paxDetail.getSeatNbr());
			blockSeatPaxDetail.setFare(seatFare.getApiPrice().intValue());
			blockSeatPaxDetail.setServiceTaxAmount(seatFare.getApiServiceTaxAmount().doubleValue());
			blockSeatPaxDetail.setTotalFareWithTaxes(seatFare.getApiTotalFareWithTaxes().doubleValue());
			blockSeatPaxDetail.setOperatorServiceChargeAbsolute(seatFare.getApiOperatorServiceChargeAbsolute().doubleValue());	
			blockSeatPaxDetailList.add(blockSeatPaxDetail);
		}
		blockTicketRequest.setBlockSeatPaxDetails(blockSeatPaxDetailList);
		blockTicketRequest.setBoardingPoint(boardingPoint);
		blockTicketRequest.setDroppingPoint(droppingPoint);
		blockTicketRequest.setCustomerName(busBlockTicketRequest.getBusPaxDetails().get(0).getFirstName());
		blockTicketRequest.setCustomerLastName(busBlockTicketRequest.getBusPaxDetails().get(0).getLastName());
		blockTicketRequest.setCustomerAddress("Bangalore");
		blockTicketRequest.setCustomerEmail(busBlockTicketRequest.getEmail());
		blockTicketRequest.setCustomerPhone(busBlockTicketRequest.getPhone());
		blockTicketRequest.setSourceCity(busBlockTicketRequest.getOrigin());
		blockTicketRequest.setDestinationCity(busBlockTicketRequest.getDestination());
		blockTicketRequest.setDoj(busBlockTicketRequest.getOnwardDate());
		blockTicketRequest.setInventoryType(Integer.parseInt(busBlockTicketRequest.getInventoryType()));
		blockTicketRequest.setRouteScheduleId(busBlockTicketRequest.getRouteScheduleId());
		blockTicketRequest.setEmergencyPhNumber(busBlockTicketRequest.getPhone());	
		
		return blockTicketRequest;				
	}
	public static String getConfirmTicketRequest(String blockTicketKey){
		String request = "?blockTicketKey="+blockTicketKey;
		return request;		
	}
	public static ApiCancelConfirmation getConfirmCancelTicketRequest(BusCancelRequest busCancelRequest,String apiConfirmationNo){
		ApiCancelConfirmation apiCancelConfirmation = new ApiCancelConfirmation();
		apiCancelConfirmation.setEtsTicketNo(apiConfirmationNo);
		List<String> seatList = new ArrayList<>();
		for (String seatno : busCancelRequest.getSeatNbr()) {
			seatList.add(seatno);
		}
		apiCancelConfirmation.setSeatNbrsToCancel(seatList);
		return apiCancelConfirmation;		
	}
	
}
