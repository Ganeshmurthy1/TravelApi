package com.tayyarah.hotel.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.tayyarah.hotel.model.APIHotelBook;
import com.tayyarah.hotel.model.APIHotelCancelResponse;
import com.tayyarah.hotel.model.APIStatus;
import com.tayyarah.hotel.model.CancelRuleType;
import com.tayyarah.hotel.model.HotelReservationIDsType;
import com.tayyarah.hotel.model.HotelReservationsType;
import com.tayyarah.hotel.model.HotelReservationsType.HotelReservation;
import com.tayyarah.hotel.model.OTAHotelResRS;
import com.tayyarah.hotel.model.RateType.Rate;
import com.tayyarah.hotel.model.ResGlobalInfoType;
import com.tayyarah.hotel.model.RoomStayType;
import com.tayyarah.hotel.model.UniqueIDType;



public class TayyarahResponseParser {
	static final Logger logger = Logger.getLogger(TayyarahResponseParser.class);

	public APIHotelBook convertRezLivetoNativePreBookResponse(APIHotelBook apiHotelBook, APIHotelBook apiHotelBookResponse) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{		
		OTAHotelResRS totaHotelResRS = new OTAHotelResRS();	
		APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "Prebooking ..." );
		if(apiHotelBookResponse.getPreBookRes() != null)
		{
			totaHotelResRS = apiHotelBookResponse.getPreBookRes();
			totaHotelResRS.setCorrelationID(apiHotelBook.getBook().getCorrelationid());
			totaHotelResRS.setTransactionIdentifier(apiHotelBook.getBook().getOrderid());
			totaHotelResRS.setBookingCode(apiHotelBookResponse.getBook().getPaymentid());
			status = totaHotelResRS.getStatus();
		}
		if((apiHotelBookResponse.getStatus().getCode().equals(APIStatus.STATUS_CODE_SUCCESS)))
		{
			status = apiHotelBookResponse.getStatus();		
			totaHotelResRS.setCorrelationID(apiHotelBook.getBook().getCorrelationid());
			totaHotelResRS.setTransactionIdentifier(apiHotelBook.getBook().getOrderid());
			HotelReservationsType hotelReservationsType = new HotelReservationsType();
			List<HotelReservation> hotelReservationslist = new ArrayList<HotelReservation>();
			HotelReservation thotelReservation = new HotelReservation();			
			List<UniqueIDType> uniqueIDTypeList = new ArrayList<UniqueIDType>();
			UniqueIDType uniqueIDType = new UniqueIDType();
			uniqueIDType.setType(RoomDetailConstantFactory.BOOKING_ID_TYPE);
			uniqueIDType.setID(apiHotelBookResponse.getBook().getOrderid());			
			uniqueIDType.setApiBookingId(apiHotelBookResponse.getBook().getOrderid());
			uniqueIDType.setApiBookingCode(apiHotelBookResponse.getBook().getOrderid());
			uniqueIDType.setApiConfirmationNo(apiHotelBookResponse.getBook().getOrderid());			
			totaHotelResRS.setBookingCode(apiHotelBookResponse.getBook().getPaymentid());		
			uniqueIDTypeList.add(uniqueIDType);
			thotelReservation.setUniqueIDs(uniqueIDTypeList);	
			ResGlobalInfoType tresGlobalinfo = new ResGlobalInfoType();
			HotelReservationIDsType thotelresIds = new HotelReservationIDsType();
			List<HotelReservationIDsType.HotelReservationID> thotelresidlist = new ArrayList<HotelReservationIDsType.HotelReservationID>();

			RoomStayType.RoomRates.RoomRate roomrate = apiHotelBookResponse.getRoomsummary().getRoomRates().getRoomRates().get(0);
			for (Rate rate : roomrate.getRates().getRates()) {
				HotelReservationIDsType.HotelReservationID thotelresid = new HotelReservationIDsType.HotelReservationID();
				thotelresid.setResIDType(RoomDetailConstantFactory.BOOKING_ID_TYPE);
				thotelresid.setResIDValue(apiHotelBookResponse.getBook().getOrderid());//XHUB891565bookingId
				thotelresid.setResIDSourceContext("");
				thotelresid.setForGuest(true);
				thotelresidlist.add(thotelresid);
			}
			thotelresIds.setHotelReservationIDs(thotelresidlist);
			tresGlobalinfo.setHotelReservationIDs(thotelresIds);
			thotelReservation.setResGlobalInfo(tresGlobalinfo);
			hotelReservationslist.add(thotelReservation);	
			hotelReservationsType.setHotelReservations(hotelReservationslist);
			totaHotelResRS.setHotelReservations(hotelReservationsType);		
			BigDecimal newtotalrate = apiHotelBookResponse.getPreBookRes().getBookingFinalPrice();
			totaHotelResRS.setApiFinalPrice(newtotalrate);
			totaHotelResRS.setBaseFinalPrice(newtotalrate);
			totaHotelResRS.setBaseFinalPriceWithoutMarkup(newtotalrate);
			totaHotelResRS.setBookingFinalPrice(newtotalrate);			
		}		
		totaHotelResRS.setStatus(status);
		apiHotelBook.setStatus(status);
		apiHotelBook.setPreBookRes(totaHotelResRS);
		return apiHotelBook;		
	}
	
	public APIHotelBook convertRezLivetoNativeBookResponse(APIHotelBook apiHotelBook, APIHotelBook apiHotelBookResponse) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{	
		OTAHotelResRS totaHotelResRS = new OTAHotelResRS();	
		APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "Prebooking ..." );
		if(apiHotelBookResponse.getBookRes() != null)
		{
			totaHotelResRS = apiHotelBookResponse.getBookRes();
			status = totaHotelResRS.getStatus();
			totaHotelResRS.setCorrelationID(apiHotelBook.getBook().getCorrelationid());
			totaHotelResRS.setTransactionIdentifier(apiHotelBook.getBook().getOrderid());
			totaHotelResRS.setBookingCode(apiHotelBookResponse.getBook().getPaymentid());
		}
		if((apiHotelBookResponse.getStatus().getCode().equals(APIStatus.STATUS_CODE_SUCCESS)))
		{
			status = apiHotelBookResponse.getStatus();
			totaHotelResRS.setCorrelationID(apiHotelBook.getBook().getCorrelationid());
			totaHotelResRS.setTransactionIdentifier(apiHotelBook.getBook().getOrderid());
			HotelReservationsType hotelReservationsType = new HotelReservationsType();
			List<HotelReservation> hotelReservationslist = new ArrayList<HotelReservation>();
			HotelReservation thotelReservation = new HotelReservation();
			List<UniqueIDType> uniqueIDTypeList = new ArrayList<UniqueIDType>();
			UniqueIDType uniqueIDType = new UniqueIDType();
			uniqueIDType.setType(RoomDetailConstantFactory.BOOKING_ID_TYPE);
			uniqueIDType.setID(apiHotelBookResponse.getBookRes().getInvoiceNo());
			uniqueIDType.setApiBookingId(apiHotelBookResponse.getBook().getOrderid());
			uniqueIDType.setApiBookingCode(apiHotelBookResponse.getBook().getOrderid());
			uniqueIDType.setApiConfirmationNo(apiHotelBookResponse.getBook().getOrderid());
			totaHotelResRS.setBookingCode(apiHotelBookResponse.getBook().getPaymentid());
			uniqueIDTypeList.add(uniqueIDType);
			thotelReservation.setUniqueIDs(uniqueIDTypeList);			
			ResGlobalInfoType tresGlobalinfo = new ResGlobalInfoType();
			HotelReservationIDsType thotelresIds = new HotelReservationIDsType();
			List<HotelReservationIDsType.HotelReservationID> thotelresidlist = new ArrayList<HotelReservationIDsType.HotelReservationID>();

			RoomStayType.RoomRates.RoomRate roomrate = apiHotelBookResponse.getRoomsummary().getRoomRates().getRoomRates().get(0);
			for (Rate rate : roomrate.getRates().getRates()) {
				HotelReservationIDsType.HotelReservationID thotelresid = new HotelReservationIDsType.HotelReservationID();
				thotelresid.setResIDType(RoomDetailConstantFactory.BOOKING_ID_TYPE);
				thotelresid.setResIDValue(apiHotelBookResponse.getBookRes().getInvoiceNo());//XHUB891565bookingId
				thotelresid.setResIDSourceContext("");
				thotelresid.setForGuest(true);
				thotelresidlist.add(thotelresid);
			}
			thotelresIds.setHotelReservationIDs(thotelresidlist);
			tresGlobalinfo.setHotelReservationIDs(thotelresIds);
			thotelReservation.setResGlobalInfo(tresGlobalinfo);
			hotelReservationslist.add(thotelReservation);	
			hotelReservationsType.setHotelReservations(hotelReservationslist);
			totaHotelResRS.setHotelReservations(hotelReservationsType);
			BigDecimal newtotalrate = apiHotelBookResponse.getBookRes().getBookingFinalPrice();
			totaHotelResRS.setApiFinalPrice(newtotalrate);
			totaHotelResRS.setBaseFinalPrice(newtotalrate);
			totaHotelResRS.setBaseFinalPriceWithoutMarkup(newtotalrate);
			totaHotelResRS.setBookingFinalPrice(newtotalrate);
		}
		totaHotelResRS.setStatus(status);
		apiHotelBook.setStatus(status);
		apiHotelBook.setBookRes(totaHotelResRS);
		return apiHotelBook;		
	}
	
	public APIHotelCancelResponse converttbotoNativeCancelResponse(APIHotelCancelResponse apiHotelCancel, APIHotelCancelResponse apiHotelCancelTayyarah) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{		
		APIStatus apiStatus = new APIStatus(APIStatus.STATUS_CODE_ERROR, "Cancellation failed ..." );
		if(apiHotelCancelTayyarah != null)
		{		
			apiStatus = apiHotelCancelTayyarah.getApiStatus();
			if(apiHotelCancelTayyarah.getApiUniqueId() != null)
			{				
				UniqueIDType apicanceluniqueid =apiHotelCancelTayyarah.getApiUniqueId();			
				apiHotelCancel.setApiUniqueId(apicanceluniqueid);				
				UniqueIDType canceluniqueid = new UniqueIDType();
				BeanUtils.copyProperties(apicanceluniqueid, canceluniqueid);
				apiHotelCancel.setUniqueId(canceluniqueid);					
				CancelRuleType apiCancelRule = apiHotelCancelTayyarah.getApiCancelRule()==null?new CancelRuleType():apiHotelCancelTayyarah.getApiCancelRule();
				CancelRuleType cancelRule = new CancelRuleType();
				BeanUtils.copyProperties(apiCancelRule, cancelRule);
				apiHotelCancel.setApiCancelRule(apiCancelRule);
				apiHotelCancel.setCancelRule(cancelRule);						
				
			}			
		}
		apiHotelCancel.setApiStatus(apiStatus);
		return apiHotelCancel;
	}
}