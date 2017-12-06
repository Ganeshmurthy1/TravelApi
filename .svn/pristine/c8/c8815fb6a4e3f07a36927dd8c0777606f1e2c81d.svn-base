package com.tayyarah.hotel.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.tayyarah.api.hotel.travelguru.model.BasicPropertyInfoType.Award;
import com.tayyarah.common.util.AmountRoundingModeUtil;
import com.tayyarah.hotel.model.APIHotelBook;
import com.tayyarah.hotel.model.APIStatus;
import com.tayyarah.hotel.model.AddressType;
import com.tayyarah.hotel.model.BasicPropertyInfoType;
import com.tayyarah.hotel.model.BasicPropertyInfoType.ContactNumbers.ContactNumber;
import com.tayyarah.hotel.model.CountryNameType;
import com.tayyarah.hotel.model.ErrorType;
import com.tayyarah.hotel.model.ErrorsType;
import com.tayyarah.hotel.model.HotelReservationsType;
import com.tayyarah.hotel.model.HotelReservationsType.HotelReservation;
import com.tayyarah.hotel.model.RateType.Rate;
import com.tayyarah.hotel.model.RoomStayType.RoomRates;
import com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate;
import com.tayyarah.hotel.model.RoomStayType.RoomTypes;
import com.tayyarah.hotel.model.RoomStaysType;
import com.tayyarah.hotel.model.RoomTypeType;
import com.tayyarah.hotel.model.RoomTypeType.Occupancy;
import com.tayyarah.hotel.model.TPAExtensions;
import com.tayyarah.hotel.model.TPAExtensions.RoomType;
import com.tayyarah.hotel.model.UniqueIDType;

public class TGResponseParser {
	static final Logger logger = Logger.getLogger(TGResponseParser.class);

	public TGResponseParser() {
		// TODO Auto-generated constructor stub
	}
	public APIHotelBook convertTGtoNativePreBookResponse(com.tayyarah.hotel.model.TotalType revisedRate, APIHotelBook apiHotelBook,com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay roomStays, com.tayyarah.api.hotel.travelguru.model.OTAHotelResRS rs) throws NumberFormatException, Exception 
	{		
		com.tayyarah.hotel.model.OTAHotelResRS totaHotelResRS = new com.tayyarah.hotel.model.OTAHotelResRS();	
		APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "Prebooking ..." );

		logger.info("pre parsing--1--apiHotelBook:"+apiHotelBook);
		logger.info("pre parsing--1--totaHotelResRS:"+totaHotelResRS);
		logger.info("pre parsing--1--status:"+status);
		logger.info("pre parsing--1--status msg:"+status.getMessage());		
		logger.info("pre parsing--1--apiHotelBook.getStatus():"+apiHotelBook.getStatus());
		logger.info("pre parsing--1--apiHotelBook.getStatus() status msg:"+apiHotelBook.getStatus().getMessage());

		if(apiHotelBook.getPreBookRes() != null)
		{
			totaHotelResRS = apiHotelBook.getPreBookRes();
			status = totaHotelResRS.getStatus();
			
		}			
		BigDecimal totalAmountPayableBeforePriceChange = new BigDecimal("0.0");
		for (RoomRate roomratebefore : roomStays.getRoomRates().getRoomRates()) {			
			if(roomratebefore.getRates().getRates().size()>0)
			{
				for (Rate rate : roomratebefore.getRates().getRates()) {				
					totalAmountPayableBeforePriceChange = totalAmountPayableBeforePriceChange.add(rate.getBookingPrice().getTotalAmountPayable());
				}
			}
		}		
		if(rs == null)
		{			
			logger.info("object transformation---: OTAHotelResRS is null ");
			
			ErrorsType errors = new ErrorsType();
			List<ErrorType> errorlist = new ArrayList<ErrorType>();
			ErrorType error = new ErrorType();
			error.setCode(ErrorType.CODE_API_ISSUE);
			error.setStatus(ErrorType.STATUS_API_ISSUE);
			errorlist.add(error);
			errors.setErrors(errorlist);
			totaHotelResRS.setErrors(errors);
			status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "No reservation response from server" );
		}
		else
		{
			totaHotelResRS.setCorrelationID(rs.getCorrelationID());
			totaHotelResRS.setTransactionIdentifier(rs.getTransactionIdentifier());
			
			//check for error..
			if(rs.getErrors() != null && rs.getErrors().getErrors() != null && !rs.getErrors().getErrors().isEmpty())
			{
				logger.info("object transformation---: OTAHotelResRS has errors ");
				
				ErrorsType errors = new ErrorsType();
				List<ErrorType> errorlist = new ArrayList<ErrorType>();
				for (com.tayyarah.api.hotel.travelguru.model.ErrorType error : rs.getErrors().getErrors()) {
					
					if(error.getCode()!=null && error.getCode().equalsIgnoreCase("083") ){
						totaHotelResRS.setPriceChanged(true);
						totaHotelResRS.setOldBookingFinalPrice(totalAmountPayableBeforePriceChange);
					}						
					else
						totaHotelResRS.setPriceChanged(false);

					ErrorType terror = new ErrorType();
					terror.setCode(error.getCode());
					terror.setStatus(error.getStatus());
					terror.setShortText(error.getShortText());
					terror.setDocURL(error.getDocURL());
					terror.setLanguage(error.getLanguage());
					terror.setNodeList(error.getNodeList());
					terror.setTag(error.getTag());
					terror.setType(error.getType());
					terror.setValue(error.getValue());
					errorlist.add(terror);
				}
				errors.setErrors(errorlist);
				totaHotelResRS.setErrors(errors);
				status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "No reservation response from server" );
			}
			HotelReservationsType hotelReservationsType = new HotelReservationsType();
			List<HotelReservation> hotelReservationslist = new ArrayList<HotelReservation>();
			if(rs.getHotelReservations() != null && rs.getHotelReservations().getHotelReservations() != null && !rs.getHotelReservations().getHotelReservations().isEmpty())					
			{
				for (com.tayyarah.api.hotel.travelguru.model.HotelReservationType hotelReservation : rs.getHotelReservations().getHotelReservations()) {
					HotelReservation thotelReservation = new HotelReservation();
					List<UniqueIDType> uniqueIDTypeList = new ArrayList<UniqueIDType>();
					if(!hotelReservation.getUniqueIDs().isEmpty())
					{
						for (com.tayyarah.api.hotel.travelguru.model.UniqueIDType uniqueid : hotelReservation.getUniqueIDs()) {
							UniqueIDType uniqueIDType = new UniqueIDType();
							uniqueIDType.setType(uniqueid.getType());
							uniqueIDType.setID(uniqueid.getId());
							uniqueIDType.setApiBookingId(uniqueid.getId());
							uniqueIDType.setApiBookingCode(uniqueid.getId());
							uniqueIDType.setApiConfirmationNo(uniqueid.getId());
							uniqueIDTypeList.add(uniqueIDType);

						}
						thotelReservation.setUniqueIDs(uniqueIDTypeList);
						
						/*// Testing for Price Change
						totaHotelResRS.setPriceChanged(true);
						BigDecimal addpricetochange = apiHotelBook.getApiRate().getPayableAmt().add(new BigDecimal("100"));
						apiHotelBook.getApiRate().setPayableAmt(addpricetochange);
						apiHotelBook.getApiRate().setAmountbeforeTax(addpricetochange.subtract(apiHotelBook.getApiRate().getRoomrateTax()) );
						apiHotelBook.getApiRate().setRoomrate(addpricetochange.subtract(apiHotelBook.getApiRate().getRoomrateTax()) );
						apiHotelBook.getApiRate().setRoomrateBeforeDiscount(apiHotelBook.getApiRate().getRoomrate().add(apiHotelBook.getApiRate().getRoomrateDiscount()));*/

					}

					if(hotelReservation.getRoomStays() != null && hotelReservation.getRoomStays().getRoomStaies() != null)
					{
						com.tayyarah.hotel.model.RoomStaysType troomstayes = new RoomStaysType();
						List<com.tayyarah.hotel.model.RoomStaysType.RoomStay> troomstaylist = new ArrayList<com.tayyarah.hotel.model.RoomStaysType.RoomStay>();
						for (com.tayyarah.api.hotel.travelguru.model.RoomStaysType.RoomStay roomStay : hotelReservation.getRoomStays().getRoomStaies()) {
							com.tayyarah.hotel.model.RoomStaysType.RoomStay troomstay = new com.tayyarah.hotel.model.RoomStaysType.RoomStay();
							com.tayyarah.hotel.model.TotalType ttotal = new com.tayyarah.hotel.model.TotalType();
							if(roomStay.getTotal() != null)
							{								
								if(totaHotelResRS.isPriceChanged()){
									BigDecimal roomrateBeforeTax = roomStay.getTotal().getAmountAfterTax().subtract(apiHotelBook.getApiRate().getRoomrateTax());
									BigDecimal discountPerDay = apiHotelBook.getApiRate().getRoomrateDiscount().divide(new BigDecimal(apiHotelBook.getApiRate().getNoofdays()));
									BigDecimal basePriceAfterDiscount = roomrateBeforeTax.divide(new BigDecimal(apiHotelBook.getApiRate().getNoofdays()));
									BigDecimal basePrice = basePriceAfterDiscount.add(discountPerDay);
									
									apiHotelBook.getApiRate().setPayableAmt(roomStay.getTotal().getAmountAfterTax());
									apiHotelBook.getApiRate().setAmountbeforeTax(roomStay.getTotal().getAmountAfterTax().subtract(apiHotelBook.getApiRate().getRoomrateTax()) );
									apiHotelBook.getApiRate().setRoomrate(roomStay.getTotal().getAmountAfterTax().subtract(apiHotelBook.getApiRate().getRoomrateTax()) );
									apiHotelBook.getApiRate().setRoomrateBeforeDiscount(apiHotelBook.getApiRate().getRoomrate().add(apiHotelBook.getApiRate().getRoomrateDiscount()));
								}
								ttotal.setAmountAfterTax(roomStay.getTotal().getAmountAfterTax());
								ttotal.setAmountBeforeTax(roomStay.getTotal().getAmountBeforeTax());
								ttotal.setAmountIncludingMarkup(roomStay.getTotal().getAmountIncludingMarkup());
								ttotal.setEditCommission(roomStay.getTotal().getEditCommission());
								ttotal.setEditNetRate(roomStay.getTotal().getEditNetRate());
								ttotal.setEditSellRate(roomStay.getTotal().getEditSellRate());
								ttotal.setEditServiceCharge(roomStay.getTotal().getEditServiceCharge());
								ttotal.setEditServiceTax(roomStay.getTotal().getEditServiceTax());
								ttotal.setEditTotalTax(roomStay.getTotal().getEditTotalTax());
								ttotal.setCurrencyCode(roomStay.getTotal().getCurrencyCode());								
								ttotal.setAdditionalFeesExcludedIndicator(roomStay.getTotal().isAdditionalFeesExcludedIndicator());
								ttotal.setType(roomStay.getTotal().getType());
								troomstay.setTotal(ttotal);
							}
							troomstaylist.add(troomstay);							
						}						
						troomstayes.setRoomStaies(troomstaylist);
						thotelReservation.setRoomStays(troomstayes);
					}	

					hotelReservationslist.add(thotelReservation);		
				}
				hotelReservationsType.setHotelReservations(hotelReservationslist);
				totaHotelResRS.setHotelReservations(hotelReservationsType);
			}
			if(rs.getSuccess() != null)
			{				
				if(revisedRate == null)
				{
					logger.info("booking revised rate setApiFinalPrice----"+apiHotelBook.getApiRate().getPayableAmt());	
					logger.info("booking revised rate setBaseFinalPrice----"+apiHotelBook.getBaseRate().getPayableAmt());	
					logger.info("booking revised rate setBaseFinalPriceWithoutMarkup----"+apiHotelBook.getBaseRateWithoutMarkUp().getPayableAmt());						
					logger.info("booking revised rate setBookingFinalPrice----"+apiHotelBook.getBookingRate().getPayableAmt());	
					
					totaHotelResRS.setApiFinalPrice(apiHotelBook.getApiRate().getPayableAmt());
					totaHotelResRS.setBaseFinalPrice(apiHotelBook.getBaseRate().getPayableAmt());
					totaHotelResRS.setBaseFinalPriceWithoutMarkup(apiHotelBook.getBaseRateWithoutMarkUp().getPayableAmt());
					totaHotelResRS.setBookingFinalPrice(apiHotelBook.getBookingRate().getPayableAmt());
				}
				else
				{
					BigDecimal finalprice = revisedRate.getAmountAfterTax();
					logger.info("----------object transformation---: prebooking single day price- "+revisedRate.getAmountAfterTax());						
					logger.info("------------object transformation---: prebooking final total api price-- "+finalprice);	
					
					totaHotelResRS.setApiFinalPrice(finalprice);
					totaHotelResRS.setBaseFinalPrice(finalprice);
					totaHotelResRS.setBaseFinalPriceWithoutMarkup(finalprice);
					//totaHotelResRS.setBaseFinalPriceWithoutMarkup(finalprice.add(CommonUtil.getMarkUpAmount(apiHotelBook)));
					totaHotelResRS.setBookingFinalPrice(finalprice);					
				}				
				status = new APIStatus(APIStatus.STATUS_CODE_SUCCESS, "Success" );					
				logger.info("object transformation---: OTAHotelResRS has unique unit ");				
			}else{
				totaHotelResRS.setApiFinalPrice(apiHotelBook.getApiRate().getPayableAmt());
				totaHotelResRS.setBaseFinalPrice(apiHotelBook.getBaseRate().getPayableAmt());
				totaHotelResRS.setBaseFinalPriceWithoutMarkup(apiHotelBook.getBaseRateWithoutMarkUp().getPayableAmt());
				totaHotelResRS.setBookingFinalPrice(apiHotelBook.getBookingRate().getPayableAmt());
				
			}
		}
		totaHotelResRS.setStatus(status);
		apiHotelBook.setStatus(status);
		apiHotelBook.setPreBookRes(totaHotelResRS);
		logger.info("pre parsing--3--apiHotelBook:"+apiHotelBook);
		logger.info("pre parsing--3--totaHotelResRS:"+totaHotelResRS);
		logger.info("pre parsing--3--status:"+status);
		logger.info("pre parsing--3--status msg:"+status.getMessage());		
		logger.info("pre parsing--3--apiHotelBook.getStatus():"+apiHotelBook.getStatus());
		logger.info("pre parsing--3--apiHotelBook.getStatus() status msg:"+apiHotelBook.getStatus().getMessage());
		return apiHotelBook;

	}	

	public APIHotelBook convertTGtoNativeFinalBookResponse(APIHotelBook apiHotelBook, com.tayyarah.api.hotel.travelguru.model.OTAHotelResRS rs) throws NumberFormatException, Exception 
	{	
		com.tayyarah.hotel.model.OTAHotelResRS totaHotelResRS = new com.tayyarah.hotel.model.OTAHotelResRS();	
		APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "Booking ..." );

		if(apiHotelBook.getBookRes() != null)
		{
			totaHotelResRS = apiHotelBook.getBookRes();
			status = totaHotelResRS.getStatus();
		}			
		if(rs == null)
		{
			logger.info("object transformation---: OTAHotelResRS is null ");

			ErrorsType errors = new ErrorsType();
			List<ErrorType> errorlist = new ArrayList<ErrorType>();
			ErrorType error = new ErrorType();
			error.setCode(ErrorType.CODE_API_ISSUE);
			error.setStatus(ErrorType.STATUS_API_ISSUE);
			errorlist.add(error);
			errors.setErrors(errorlist);
			totaHotelResRS.setErrors(errors);
			status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "No reservation response from server" );
		}
		else
		{
			totaHotelResRS.setCorrelationID(rs.getCorrelationID());
			totaHotelResRS.setTransactionIdentifier(rs.getTransactionIdentifier());
			//check for error..
			if(rs.getErrors() != null && rs.getErrors().getErrors() != null && !rs.getErrors().getErrors().isEmpty())
			{
				logger.info("object transformation---: OTAHotelResRS has errors ");
				
				ErrorsType errors = new ErrorsType();
				List<ErrorType> errorlist = new ArrayList<ErrorType>();
				for (com.tayyarah.api.hotel.travelguru.model.ErrorType error : rs.getErrors().getErrors()) {
					ErrorType terror = new ErrorType();
					terror.setCode(error.getCode());
					terror.setStatus(error.getStatus());
					terror.setShortText(error.getShortText());
					terror.setDocURL(error.getDocURL());
					terror.setLanguage(error.getLanguage());
					terror.setNodeList(error.getNodeList());
					terror.setTag(error.getTag());
					terror.setType(error.getType());
					terror.setValue(error.getValue());
					errorlist.add(terror);
				}
				errors.setErrors(errorlist);
				totaHotelResRS.setErrors(errors);
				status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "No reservation response from server" );
				totaHotelResRS.setStatus(status);
			}
			HotelReservationsType hotelReservationsType = new HotelReservationsType();
			List<HotelReservation> hotelReservationslist = new ArrayList<HotelReservation>();
			if(rs.getHotelReservations() != null && rs.getHotelReservations().getHotelReservations() != null && !rs.getHotelReservations().getHotelReservations().isEmpty())					
			{				
				for (com.tayyarah.api.hotel.travelguru.model.HotelReservationType hotelReservation : rs.getHotelReservations().getHotelReservations()) {
					HotelReservation thotelReservation = new HotelReservation();
					List<UniqueIDType> uniqueIDTypeList = new ArrayList<UniqueIDType>();
					if(!hotelReservation.getUniqueIDs().isEmpty())
					{
						for (com.tayyarah.api.hotel.travelguru.model.UniqueIDType uniqueid : hotelReservation.getUniqueIDs()) {
							UniqueIDType uniqueIDType = new UniqueIDType();
							uniqueIDType.setType(uniqueid.getType());
							uniqueIDType.setID(uniqueid.getId());
							uniqueIDType.setApiBookingId(uniqueid.getId());
							uniqueIDType.setApiBookingCode(uniqueid.getId());
							uniqueIDType.setApiConfirmationNo(uniqueid.getId());							
							uniqueIDTypeList.add(uniqueIDType);
						}
						thotelReservation.setUniqueIDs(uniqueIDTypeList);
					}

					if(hotelReservation.getRoomStays() != null && hotelReservation.getRoomStays().getRoomStaies() != null)
					{						
						BigDecimal finalprice  = new BigDecimal(0);					
						com.tayyarah.hotel.model.RoomStaysType troomstayes = new RoomStaysType();
						List<com.tayyarah.hotel.model.RoomStaysType.RoomStay> troomstaylist = new ArrayList<com.tayyarah.hotel.model.RoomStaysType.RoomStay>();
						for (com.tayyarah.api.hotel.travelguru.model.RoomStaysType.RoomStay roomStay : hotelReservation.getRoomStays().getRoomStaies()) {
							com.tayyarah.hotel.model.RoomStaysType.RoomStay troomstay = new com.tayyarah.hotel.model.RoomStaysType.RoomStay();
							com.tayyarah.hotel.model.TotalType ttotal = new com.tayyarah.hotel.model.TotalType();
							if(roomStay.getTotal() != null)
							{
								ttotal.setAmountAfterTax(roomStay.getTotal().getAmountAfterTax());								
								finalprice = finalprice.add(roomStay.getTotal().getAmountAfterTax());								
								ttotal.setAmountBeforeTax(roomStay.getTotal().getAmountBeforeTax());
								ttotal.setAmountIncludingMarkup(roomStay.getTotal().getAmountIncludingMarkup());
								ttotal.setEditCommission(roomStay.getTotal().getEditCommission());
								ttotal.setEditNetRate(roomStay.getTotal().getEditNetRate());
								ttotal.setEditSellRate(roomStay.getTotal().getEditSellRate());
								ttotal.setEditServiceCharge(roomStay.getTotal().getEditServiceCharge());
								ttotal.setEditServiceTax(roomStay.getTotal().getEditServiceTax());
								ttotal.setEditTotalTax(roomStay.getTotal().getEditTotalTax());
								ttotal.setCurrencyCode(roomStay.getTotal().getCurrencyCode());
								//ttotal.setCurrencyCode(roomStay.getTotal().getTaxes()());
								ttotal.setAdditionalFeesExcludedIndicator(roomStay.getTotal().isAdditionalFeesExcludedIndicator());
								ttotal.setType(roomStay.getTotal().getType());
								troomstay.setTotal(ttotal);
							}												
							RoomTypes troomtypes = new RoomTypes();
							List<com.tayyarah.hotel.model.RoomTypeType> lrtlist = new ArrayList<com.tayyarah.hotel.model.RoomTypeType>();
							if(roomStay.getRoomTypes() != null && roomStay.getRoomTypes().getRoomTypes() != null)							
								for (com.tayyarah.api.hotel.travelguru.model.RoomTypeType rt : roomStay.getRoomTypes().getRoomTypes()) {
									com.tayyarah.hotel.model.RoomTypeType lrt = new com.tayyarah.hotel.model.RoomTypeType();
									lrt.setFloor(rt.getFloor());
									lrt.setNonSmoking(rt.isNonSmoking());
									lrt.setRoomType(rt.getRoomType());
									lrt.setRoomTypeCode(rt.getRoomTypeCode());									
									List<Occupancy> locclist = new ArrayList<RoomTypeType.Occupancy>();
									logger.info("object transformation---: RoomTypes. Occupancy size "+rt.getOccupancies().size());
									if(rt.getOccupancies() != null)
									{
										for (com.tayyarah.api.hotel.travelguru.model.RoomTypeType.Occupancy occ : rt.getOccupancies()) {
											/*<Occupancy AgeQualifyingCode="8" MaxOccupancy="0" MinAge="6" MaxAge="12"/>*/
											Occupancy locc = new Occupancy();
											locc.setAgeQualifyingCode(occ.getAgeQualifyingCode());
											locc.setMaxOccupancy(occ.getMaxOccupancy());
											locc.setAgeBucket(occ.getAgeBucket());
											//locc.setAgeTimeUnit(occ.getAgeTimeUnit());
											locc.setMaxAge(occ.getMaxAge());
											locc.setMinAge(occ.getMinAge());
											locc.setMinOccupancy(occ.getMinOccupancy());

											locclist.add(locc);
										}
									}
									lrt.setOccupancies(locclist);

									TPAExtensions lext = new TPAExtensions();
									RoomType lextroomtype = new RoomType();
									if(rt.getTPAExtensions() != null && rt.getTPAExtensions().getRoomType() != null)
									{
										lextroomtype.setMaxAdult(rt.getTPAExtensions().getRoomType().getMaxAdult());
										lextroomtype.setMaxChild(rt.getTPAExtensions().getRoomType().getMaxChild());
										lextroomtype.setMaxChildAge(rt.getTPAExtensions().getRoomType().getMaxChildAge());
										lextroomtype.setMaxGuest(rt.getTPAExtensions().getRoomType().getMaxGuest());
										lextroomtype.setMaxInfant(rt.getTPAExtensions().getRoomType().getMaxInfant());
										lextroomtype.setMinChildAge(rt.getTPAExtensions().getRoomType().getMinChildAge());
										lextroomtype.setSmoking(rt.getTPAExtensions().getRoomType().getSmoking());

									}
									lext.setRoomType(lextroomtype);
									lrt.setTPAExtensions(lext);			

									lrtlist.add(lrt);
								}
							troomtypes.setRoomTypes(lrtlist);
							troomstay.setRoomTypes(troomtypes);

							RoomRates troomrates = new RoomRates();
							List<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate> lrratelist = new ArrayList<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate>();

							if(roomStay.getRoomRates() != null && roomStay.getRoomRates().getRoomRates() != null)										
								for (com.tayyarah.api.hotel.travelguru.model.RoomStayType.RoomRates.RoomRate rr : roomStay.getRoomRates().getRoomRates()) {
									com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate lrr = new com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate();
									lrr.setRatePlanCode(rr.getRatePlanCode());
									lrr.setBookingCode(rr.getBookingCode());
									lrr.setEffectiveDate(rr.getEffectiveDate());
									lrr.setExpireDate(rr.getExpireDate());
									lrr.setInvBlockCode(rr.getInvBlockCode());
									lrr.setNumberOfUnits(rr.getNumberOfUnits());
									lrr.setRoomID(rr.getRoomID());
									lrr.setRoomTypeCode(rr.getRoomID());
									lrratelist.add(lrr);
								}
							troomrates.setRoomRates(lrratelist);
							troomstay.setRoomRates(troomrates);							

							BasicPropertyInfoType tbasic = new BasicPropertyInfoType();
							if(roomStay.getBasicPropertyInfo() != null)
							{
								tbasic.setHotelCode(roomStay.getBasicPropertyInfo().getHotelCode());
								tbasic.setHotelName(roomStay.getBasicPropertyInfo().getHotelName());
								tbasic.setArea_Seo_Id(roomStay.getBasicPropertyInfo().getAreaID());

								AddressType laddress = new AddressType();
								if(roomStay.getBasicPropertyInfo().getAddress() != null)
								{
									com.tayyarah.api.hotel.travelguru.model.AddressInfoType address = roomStay.getBasicPropertyInfo().getAddress();
									laddress.setCityName(address.getCityName());									
									CountryNameType cn = new CountryNameType();
									if(address.getCountryName() != null)
									{
										cn.setCode(address.getCountryName().getCode());
										cn.setValue(address.getCountryName().getValue());
									}									
									laddress.setCountryName(cn);
									List<String> addresslines = new ArrayList<String>();
									if(address.getAddressLines() != null)
									{
										for (String string : addresslines) {
											addresslines.add(string);
										}										
									}									
									laddress.setAddressLines(addresslines);
								}
								List<ContactNumber> contactnos = new ArrayList<ContactNumber>();								
								if(roomStay.getBasicPropertyInfo().getContactNumbers() != null && roomStay.getBasicPropertyInfo().getContactNumbers().getContactNumbers() != null)
								{
									for (com.tayyarah.api.hotel.travelguru.model.BasicPropertyInfoType.ContactNumbers.ContactNumber contactNumber : roomStay.getBasicPropertyInfo().getContactNumbers().getContactNumbers()) {
										contactnos.add(new ContactNumber(contactNumber.getCountryAccessCode()+"-"+contactNumber.getPhoneNumber()));
									}
								}
								tbasic.setAddress(laddress);
								tbasic.setContactNumbers(contactnos);
								List<com.tayyarah.hotel.model.BasicPropertyInfoType.Award> awards = new ArrayList<com.tayyarah.hotel.model.BasicPropertyInfoType.Award>();

								if(roomStay.getBasicPropertyInfo().getAwards() != null)
								{
									for (Award award : roomStay.getBasicPropertyInfo().getAwards()) {
										com.tayyarah.hotel.model.BasicPropertyInfoType.Award taward = new com.tayyarah.hotel.model.BasicPropertyInfoType.Award ();
										taward.setProvider(award.getProvider());
										taward.setRating(award.getRating());
										awards.add(taward);
									}
								}
								tbasic.setAwards(awards);

							}							
							tbasic.setApiProvider(HotelApiCredentials.API_DESIA_IND);
							tbasic.setIsOfflineBooking(false);

							/*<BasicPropertyInfo HotelCode="00043263" HotelName="  OYO Premium Electronic City Phase 1   " AreaID="none">
                            <Address>
                                <AddressLine>701/48 &amp; 177/90, Doddathogur, Near Velankani Software Gate 1, Electronic City Phase 1,</AddressLine>
                                <CityName>BANGALORE</CityName>
                                <StateProv>KARNATAKA</StateProv>
                                <CountryName>India</CountryName>
                            </Address>
                            <ContactNumbers>
                                <ContactNumber AreaCityCode="0" CountryAccessCode="91" PhoneNumber="9313931393" PhoneTechType="1" />
                                <ContactNumber AreaCityCode="0" CountryAccessCode="91" PhoneNumber="" PhoneTechType="3" />
                            </ContactNumbers>
                            <Award Rating="3" />
                        </BasicPropertyInfo>
                        <TPA_Extensions>
                            <HotelBasicInformation />
                        </TPA_Extensions>*/
							troomstaylist.add(troomstay);							
						}						
						troomstayes.setRoomStaies(troomstaylist);
						thotelReservation.setRoomStays(troomstayes);

					/*	totaHotelResRS.setFinalpriceWithoutMarkup(finalprice);
						totaHotelResRS.setFinalprice(finalprice.add(CommonUtil.getMarkUpAmount(apiHotelBook)));
						logger.info("booking revised rate----"+totaHotelResRS.getFinalpriceWithoutMarkup());				
						logger.info("booking revised rate with markup----"+totaHotelResRS.getFinalprice());				
						
						*/
						
						finalprice = AmountRoundingModeUtil.roundingModeForHotel(finalprice);	
						
						totaHotelResRS.setApiFinalPrice(finalprice);
						totaHotelResRS.setBaseFinalPrice(finalprice);
						totaHotelResRS.setBaseFinalPriceWithoutMarkup(finalprice);
						totaHotelResRS.setBookingFinalPrice(finalprice);							
					}	
					hotelReservationslist.add(thotelReservation);		
				}
				hotelReservationsType.setHotelReservations(hotelReservationslist);
				totaHotelResRS.setHotelReservations(hotelReservationsType);
			}
			if(rs.getSuccess() != null)
			{
				status = new APIStatus(APIStatus.STATUS_CODE_SUCCESS, "Success" );				
				logger.info("object transformation---: OTAHotelResRS has unique unit ");				
			}
		}
		totaHotelResRS.setStatus(status);
		apiHotelBook.setStatus(status);
		apiHotelBook.setBookRes(totaHotelResRS);
		return apiHotelBook;
	}
}
