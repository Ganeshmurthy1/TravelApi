package com.tayyarah.hotel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tayyarah.common.gstconfig.model.HotelGstTax;
import com.tayyarah.common.servicetaxconfig.model.HotelServiceTax;
import com.tayyarah.common.util.AmountRoundingModeUtil;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.hotel.model.AmountType.Discount;
import com.tayyarah.hotel.model.HotelSearchCommand.GuestCount;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;
import com.tayyarah.hotel.model.RateType.Rate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIHotelBook implements Serializable{

	public static final Logger logger = Logger.getLogger(APIHotelBook.class);


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	public APIHotelBook() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "APIHotelBook [search=" + search + ", book=" + book + ", status=" + status + ", preBookRes=" + preBookRes
				+ ", bookRes=" + bookRes + ", searchKey=" + searchKey + ", transactionKey=" + transactionKey
				+ ", apiRate=" + apiRate + ", baseRate=" + baseRate + ", baseRateWithoutMarkUp=" + baseRateWithoutMarkUp
				+ ", bookingRate=" + bookingRate + ", total=" + total + ", roomsummary=" + roomsummary + "]";
	}

	public APIHotelBook(HotelSearchCommand search, HotelBookCommand book, APIStatus status, OTAHotelResRS otaHotelResRS,
			BigInteger searchKey, BigInteger transactionKey, TotalType total, RoomStay roomsummary) {
		super();
		this.search = search;
		this.book = book;
		this.status = status;
		this.preBookRes = otaHotelResRS;
		this.bookRes = null;
		this.searchKey = searchKey;
		this.transactionKey = transactionKey;
		this.total = total;
		this.roomsummary = roomsummary;
		this.apiRate = null;
		this.baseRate = null;
		this.baseRateWithoutMarkUp = null;
		this.bookingRate = null;
	}
	public com.tayyarah.hotel.model.OTAHotelResRS getPreBookRes() {
		if(this.preBookRes == null)
			this.preBookRes = new OTAHotelResRS();
		return preBookRes;
	}

	public void setPreBookRes(com.tayyarah.hotel.model.OTAHotelResRS preBookRes) {
		this.preBookRes = preBookRes;
	}

	public com.tayyarah.hotel.model.OTAHotelResRS getBookRes() {
		if(this.bookRes == null)
			this.bookRes = new OTAHotelResRS();
		return bookRes;

	}

	public void setBookRes(com.tayyarah.hotel.model.OTAHotelResRS bookRes) {
		this.bookRes = bookRes;
	}

	public APIHotelBook(APIStatus status) {
		super();
		this.search = null;
		this.book = null;
		this.status = status;
		this.preBookRes = null;
		this.bookRes = null;
		this.searchKey = null;
		this.transactionKey = null;
		//this.ratelist = null;
		this.total = null;
		this.roomsummary = null;
		this.apiRate = null;
		this.baseRate = null;
		this.baseRateWithoutMarkUp = null;
		this.bookingRate = null;
	}



	public RateMap getApiRate() {
		return apiRate;
	}

	public void setApiRate(RateMap apiRate) {
		this.apiRate = apiRate;
	}

	public RateMap getBaseRate() {
		return baseRate;
	}

	public void setBaseRate(RateMap baseRate) {
		this.baseRate = baseRate;
	}

	public RateMap getBaseRateWithoutMarkUp() {
		return baseRateWithoutMarkUp;
	}

	public void setBaseRateWithoutMarkUp(RateMap baseRateWithoutMarkUp) {
		this.baseRateWithoutMarkUp = baseRateWithoutMarkUp;
	}

	public RateMap getBookingRate() {
		return bookingRate;
	}

	public void setBookingRate(RateMap bookingRate) {
		this.bookingRate = bookingRate;
	}

	/*public List<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate> getRatelist() {
		return ratelist;
	}
	public void setRatelist(List<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate> ratelist) {
		this.ratelist = ratelist;
	}*/
	public HotelSearchCommand getSearch() {
		return search;
	}
	public void setSearch(HotelSearchCommand search) {
		this.search = search;
	}
	public HotelBookCommand getBook() {
		return book;
	}
	public void setBook(HotelBookCommand book) {
		this.book = book;
	}
	public APIStatus getStatus() {
		if(this.status == null)
			this.status = new APIStatus(APIStatus.STATUS_CODE_ERROR , APIStatus.STATUS_CODE_ERROR);
		return status;
	}
	public void setStatus(APIStatus status) {
		this.status = status;
	}

	public BigInteger getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(BigInteger searchKey) {
		this.searchKey = searchKey;
	}
	public BigInteger getTransactionKey() {
		return transactionKey;
	}
	public void setTransactionKey(BigInteger transactionKey) {
		this.transactionKey = transactionKey;
	}
	public TotalType getTotal() {
		return total;
	}
	public void setTotal(TotalType total) {
		this.total = total;
	}





	private HotelSearchCommand search;
	private HotelBookCommand book;
	private APIStatus status;
	private com.tayyarah.hotel.model.OTAHotelResRS preBookRes;
	private com.tayyarah.hotel.model.OTAHotelResRS bookRes;
	private BigInteger searchKey;
	private BigInteger transactionKey;

	private RateMap apiRate;
	private RateMap baseRate;
	private RateMap baseRateWithoutMarkUp;
	private RateMap bookingRate;






	private TotalType total;
	//private List<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate> ratelist;
	private com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay roomsummary;


	public com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay getRoomsummary() {
		return roomsummary;
	}
	public void setRoomsummary(com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay roomsummary) {
		this.roomsummary = roomsummary;
	}
	public BigDecimal getRoomAmountPerDay(com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate rr, String priceType)
	{
		RateType lrates = new RateType();
		List<Rate> lratelist = new ArrayList<Rate>();
		BigDecimal roomratebaseAmount = new BigDecimal("0");
		for (Rate rtype : rr.getRates().getRates()) {
			Rate lrate = rtype;

			//base amount
			TotalType ltot = rtype.getApiPrice();
			if(priceType.equalsIgnoreCase("api"))
				ltot = rtype.getApiPrice();
			else if(priceType.equalsIgnoreCase("base"))
				ltot = rtype.getBase();
			else if(priceType.equalsIgnoreCase("basewithoutmarkup"))
				ltot = rtype.getBaseWithoutMarkUp();
			else if(priceType.equalsIgnoreCase("booking"))
				ltot = rtype.getBookingPrice();
			roomratebaseAmount = ltot.getAmountBeforeTax();
			//logger.info(rr.getRatePlanCode()+"  rate ----roomratebaseAmount :"+ roomratebaseAmount);

			BigDecimal roomratediscount = new BigDecimal("0");
			for (Discount disType : rtype.getApiPrice().getDiscounts()) {
				roomratediscount = roomratediscount.add(disType.getAmountBeforeTax());
			}
			//logger.info(rr.getRatePlanCode()+"  rate ----roomratediscount :"+ roomratediscount);
			roomratebaseAmount = roomratebaseAmount.subtract(roomratediscount);
			//logger.info(rr.getRatePlanCode()+"  rate ----((roomratebaseAmount-roomratediscount)) :"+ roomratebaseAmount);

		}
		return roomratebaseAmount;
	}
	public BigDecimal getRoomAmountBeforeDiscountPerDay(com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate rr, String priceType)
	{
		RateType lrates = new RateType();
		List<Rate> lratelist = new ArrayList<Rate>();
		BigDecimal roomratebaseAmount = new BigDecimal("0");
		for (Rate rtype : rr.getRates().getRates()) {
			Rate lrate = rtype;

			//base amount
			//base amount
			TotalType ltot = rtype.getApiPrice();
			if(priceType.equalsIgnoreCase("api"))
				ltot = rtype.getApiPrice();
			else if(priceType.equalsIgnoreCase("base"))
				ltot = rtype.getBase();
			else if(priceType.equalsIgnoreCase("basewithoutmarkup"))
				ltot = rtype.getBaseWithoutMarkUp();
			else if(priceType.equalsIgnoreCase("booking"))
				ltot = rtype.getBookingPrice();
			roomratebaseAmount = ltot.getAmountBeforeTax();

		}
		return roomratebaseAmount;
	}
	public BigDecimal getRoomTotalPayableAmountPerDay(com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate rr)
	{		
		BigDecimal roomratepayableamount = new BigDecimal("0");
		for (Rate rtype : rr.getRates().getRates()) {			
			TotalType ltot = rtype.getBookingPrice();		
			roomratepayableamount = ltot.getPayableAmount();
			}
		return roomratepayableamount;
	}
	public BigDecimal getRoomDiscountAmountPerDay(com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate rr, String priceType)
	{
		RateType lrates = new RateType();
		List<Rate> lratelist = new ArrayList<Rate>();
		BigDecimal roomrateDiscountAmount = new BigDecimal("0");
		for (Rate rtype : rr.getRates().getRates()) {
			Rate lrate = rtype;

			//base amount
			TotalType ltot = rtype.getApiPrice();
			if(priceType.equalsIgnoreCase("api"))
				ltot = rtype.getApiPrice();
			else if(priceType.equalsIgnoreCase("base"))
				ltot = rtype.getBase();
			else if(priceType.equalsIgnoreCase("basewithoutmarkup"))
				ltot = rtype.getBaseWithoutMarkUp();
			else if(priceType.equalsIgnoreCase("booking"))
				ltot = rtype.getBookingPrice();

			for (Discount disType : rtype.getApiPrice().getDiscounts()) {
				roomrateDiscountAmount = roomrateDiscountAmount.add(disType.getAmountBeforeTax());
			}

		}
		return roomrateDiscountAmount;
	}

	public AdditionalGuestAmountType getRoomAmountTaxPerDay(com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate rr, GuestCount agc)
	{
		AdditionalGuestAmountType additionalguestamount = null;
		for (Rate rtype : rr.getRates().getRates()) {
			Rate lrate = rtype;

			com.tayyarah.hotel.model.AmountType.AdditionalGuestAmounts additionalGuestAmounts = rtype.getAdditionalGuestAmounts();
			if(additionalGuestAmounts != null)
			for (AdditionalGuestAmountType addguestamount : additionalGuestAmounts.getAdditionalGuestAmounts()) {
				if(addguestamount != null && addguestamount.ageQualifyingCode != null && addguestamount.ageQualifyingCode.equalsIgnoreCase(agc.getAgequalifyingcode()))
				{
					additionalguestamount = addguestamount;
					break;
				}
			}

		}
		logger.info("additional guest  --getRoomAmountTaxPerDay--additionalguestamount :");

		return additionalguestamount;
	}

	public BigDecimal getRoomAdditionalGuestAmountPerDay(com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate rr,  String priceType) throws NumberFormatException, Exception
	{
		RateType lrates = new RateType();
		List<Rate> lratelist = new ArrayList<Rate>();
		BigDecimal roomrateadditionlAmount = new BigDecimal("0");
		for (Rate rtype : rr.getRates().getRates()) {
			Rate lrate = rtype;
			for (GuestCount agc : search.getGuestcounts()) {
				logger.info("additional guest  ----agc :"+ agc);

				AdditionalGuestAmountType additionalguestamount = getRoomAmountTaxPerDay(rr, agc);
				logger.info("additional guest  ----additionalguestamount :"+ additionalguestamount);
				logger.info("additional guest  ----condition :"+ (additionalguestamount != null && additionalguestamount.getAmount() != null && additionalguestamount.getAmount().amountBeforeTax != null));
				if(additionalguestamount != null && additionalguestamount.getAmount() != null && additionalguestamount.getAmount().amountBeforeTax != null)
					roomrateadditionlAmount = roomrateadditionlAmount.add(additionalguestamount.getAmount().amountBeforeTax);

			}
		}

		return roomrateadditionlAmount;
	}
	public BigDecimal getRoomAmountTaxPerDay(com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate rr,  String priceType)
	{
		RateType lrates = new RateType();
		List<Rate> lratelist = new ArrayList<Rate>();
		BigDecimal roomratetax = new BigDecimal("0");
		if(rr.getRates() != null && rr.getRates().getRates() != null)
			for (Rate rtype : rr.getRates().getRates()) {

				TotalType ltot = rtype.getApiPrice();
				if(priceType.equalsIgnoreCase("api"))
					ltot = rtype.getApiPrice();
				else if(priceType.equalsIgnoreCase("base"))
					ltot = rtype.getBase();
				else if(priceType.equalsIgnoreCase("basewithoutmarkup"))
					ltot = rtype.getBaseWithoutMarkUp();
				else if(priceType.equalsIgnoreCase("booking"))
					ltot = rtype.getBookingPrice();

				if(ltot != null && ltot.getTaxes() != null)
					for (TaxType rtt : ltot.getTaxes().getTaxes()) {
						if(rtt.getAmount()!=null)
							roomratetax = roomratetax.add(rtt.getAmount());
					}
			}

		return roomratetax;
	}
	/*public BigDecimal getRoomAdditionalGuestAmountTaxPerDay(com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate rr)
	{
		RateType lrates = new RateType();
		List<Rate> lratelist = new ArrayList<Rate>();
		BigDecimal roomratebaseAmount = new BigDecimal("0");
		for (Rate rtype : rr.getRates().getRates()) {
			Rate lrate = rtype;

			//base amount
			TotalType ltot = rtype.getBase();
			roomratebaseAmount = rtype.getBase().getAmountBeforeTax();
			BigDecimal roomratediscount = new BigDecimal("0");
			for (Discount disType : rtype.getDiscounts()) {
				roomratediscount = roomratediscount.add(disType.getAmountBeforeTax());
			}
			roomratebaseAmount = roomratebaseAmount.subtract(roomratediscount);
		}
		return roomratebaseAmount;
	}*/


	public void initRatexx(List<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate> ratelist, int noofdays) throws NumberFormatException, Exception
	{

		BigDecimal apiroomrate = new BigDecimal("0");
		BigDecimal apiroomrateBeforeDiscount = new BigDecimal("0");
		BigDecimal apiroomrateDiscount = new BigDecimal("0");
		BigDecimal apiadditionalCharge = new BigDecimal("0");
		BigDecimal apiamountbeforeTax = new BigDecimal("0");
		BigDecimal apiroomrateTax = new BigDecimal("0");
		BigDecimal apiadditionalChargeTax = new BigDecimal("0");
		BigDecimal apipayableAmt = new BigDecimal("0");


		BigDecimal roomrate = new BigDecimal("0");
		BigDecimal roomrateBeforeDiscount = new BigDecimal("0");
		BigDecimal roomrateDiscount = new BigDecimal("0");
		BigDecimal additionalCharge = new BigDecimal("0");
		BigDecimal amountbeforeTax = new BigDecimal("0");
		BigDecimal roomrateTax = new BigDecimal("0");
		BigDecimal additionalChargeTax = new BigDecimal("0");
		BigDecimal payableAmt = new BigDecimal("0");


		BigDecimal basewithoutmarkuproomrate = new BigDecimal("0");
		BigDecimal basewithoutmarkuproomrateBeforeDiscount = new BigDecimal("0");
		BigDecimal basewithoutmarkuproomrateDiscount = new BigDecimal("0");
		BigDecimal basewithoutmarkupadditionalCharge = new BigDecimal("0");
		BigDecimal basewithoutmarkupamountbeforeTax = new BigDecimal("0");
		BigDecimal basewithoutmarkuproomrateTax = new BigDecimal("0");
		BigDecimal basewithoutmarkupadditionalChargeTax = new BigDecimal("0");
		BigDecimal basewithoutmarkuppayableAmt = new BigDecimal("0");


		BigDecimal bookingroomrate = new BigDecimal("0");
		BigDecimal bookingroomrateBeforeDiscount = new BigDecimal("0");
		BigDecimal bookingroomrateDiscount = new BigDecimal("0");
		BigDecimal bookingadditionalCharge = new BigDecimal("0");
		BigDecimal bookingamountbeforeTax = new BigDecimal("0");
		BigDecimal bookingroomrateTax = new BigDecimal("0");
		BigDecimal bookingadditionalChargeTax = new BigDecimal("0");
		BigDecimal bookingpayableAmt = new BigDecimal("0");


		List<BigDecimal> roomrates = new ArrayList<BigDecimal>();
		BigDecimal total =  new BigDecimal("0");
		for (com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate rr : ratelist) {
			//calculation for individual room ..
			///multiplied by noofdays
			//Api calculations
			BigDecimal apitemp = getRoomAmountBeforeDiscountPerDay(rr, "api");
			logger.info("rate ----single day roomrateBeforeDiscount :"+ apitemp);
			apitemp = apitemp.multiply(new BigDecimal(noofdays));
			logger.info("rate ----("+noofdays+") days roomrateBeforeDiscount :"+ apitemp);
			apiroomrateBeforeDiscount = apiroomrateBeforeDiscount.add(apitemp);//discount is being reducted
			logger.info("rate ----total apiroomrateBeforeDiscount :"+ apiroomrateBeforeDiscount);
			apitemp = getRoomDiscountAmountPerDay(rr, "api");
			logger.info("rate ----single day apiroomrateDiscount :"+ apitemp);
			apitemp = apitemp.multiply(new BigDecimal(noofdays));
			logger.info("rate ----("+noofdays+") days apiroomrateDiscount :"+ apitemp);
			apiroomrateDiscount = apiroomrateDiscount.add(apitemp);//discount is being reducted
			logger.info("rate ----total apiroomrateDiscount :"+ apiroomrateDiscount);
			apitemp = getRoomAmountPerDay(rr, "api");
			logger.info("rate ----single day roomrate Discount included :"+ apitemp);
			apitemp = apitemp.multiply(new BigDecimal(noofdays));
			logger.info("rate ----("+noofdays+") days roomrate :"+ apitemp);

			apiroomrate = apiroomrate.add(apitemp);//discount is being reducted
			logger.info("rate ----total apiroomrate :"+ apiroomrate);
			apitemp = getRoomAdditionalGuestAmountPerDay(rr, "api");
			apitemp = apitemp.multiply(new BigDecimal(noofdays));
			apiadditionalCharge = apiadditionalCharge.add(apitemp);//discount is being reducted
			logger.info("rate ----apiadditionalCharge :"+ apiadditionalCharge);
			apitemp = getRoomAmountTaxPerDay(rr, "api");
			apitemp = apitemp.multiply(new BigDecimal(noofdays));
			apiroomrateTax = apiroomrateTax.add(apitemp);//discount is being reducted
			logger.info("rate ----apiroomrateTax :"+ apiroomrateTax);

			//Base calculations
			BigDecimal temp = getRoomAmountBeforeDiscountPerDay(rr, "base");
			logger.info("rate ----single day roomrateBeforeDiscount :"+ temp);
			temp = temp.multiply(new BigDecimal(noofdays));
			logger.info("rate ----("+noofdays+") days roomrateBeforeDiscount :"+ temp);
			roomrateBeforeDiscount = roomrateBeforeDiscount.add(temp);//discount is being reducted
			logger.info("rate ----total roomrateBeforeDiscount :"+ roomrateBeforeDiscount);
			temp = getRoomDiscountAmountPerDay(rr, "base");
			logger.info("rate ----single day roomrateDiscount :"+ temp);
			temp = temp.multiply(new BigDecimal(noofdays));
			logger.info("rate ----("+noofdays+") days roomrateDiscount :"+ temp);
			roomrateDiscount = roomrateDiscount.add(temp);//discount is being reducted
			logger.info("rate ----total roomrateDiscount :"+ roomrateDiscount);
			temp = getRoomAmountPerDay(rr, "base");
			logger.info("rate ----single day roomrate Discount included :"+ temp);
			temp = temp.multiply(new BigDecimal(noofdays));
			logger.info("rate ----("+noofdays+") days roomrate :"+ temp);

			roomrate = roomrate.add(temp);//discount is being reducted
			logger.info("rate ----total roomrate :"+ roomrate);
			temp = getRoomAdditionalGuestAmountPerDay(rr, "base");
			temp = temp.multiply(new BigDecimal(noofdays));
			additionalCharge = additionalCharge.add(temp);//discount is being reducted
			logger.info("rate ----additionalCharge :"+ additionalCharge);
			temp = getRoomAmountTaxPerDay(rr, "base");
			temp = temp.multiply(new BigDecimal(noofdays));
			roomrateTax = roomrateTax.add(temp);//discount is being reducted
			logger.info("rate ----roomrateTax :"+ roomrateTax);

			//basewithoutmarkup calculations
			BigDecimal basewithoutmarkuptemp = getRoomAmountBeforeDiscountPerDay(rr, "basewithoutmarkup");
			logger.info("rate ----single day roomrateBeforeDiscount :"+ basewithoutmarkuptemp);
			basewithoutmarkuptemp = basewithoutmarkuptemp.multiply(new BigDecimal(noofdays));
			logger.info("rate ----("+noofdays+") days roomrateBeforeDiscount :"+ basewithoutmarkuptemp);
			basewithoutmarkuproomrateBeforeDiscount = basewithoutmarkuproomrateBeforeDiscount.add(basewithoutmarkuptemp);//discount is being reducted
			logger.info("rate ----total basewithoutmarkuproomrateBeforeDiscount :"+ basewithoutmarkuproomrateBeforeDiscount);
			basewithoutmarkuptemp = getRoomDiscountAmountPerDay(rr, "basewithoutmarkup");
			logger.info("rate ----single day basewithoutmarkuproomrateDiscount :"+ basewithoutmarkuptemp);
			basewithoutmarkuptemp = basewithoutmarkuptemp.multiply(new BigDecimal(noofdays));
			logger.info("rate ----("+noofdays+") days basewithoutmarkuproomrateDiscount :"+ basewithoutmarkuptemp);
			basewithoutmarkuproomrateDiscount = basewithoutmarkuproomrateDiscount.add(basewithoutmarkuptemp);//discount is being reducted
			logger.info("rate ----total basewithoutmarkuproomrateDiscount :"+ basewithoutmarkuproomrateDiscount);
			basewithoutmarkuptemp = getRoomAmountPerDay(rr, "basewithoutmarkup");
			logger.info("rate ----single day roomrate Discount included :"+ basewithoutmarkuptemp);
			basewithoutmarkuptemp = basewithoutmarkuptemp.multiply(new BigDecimal(noofdays));
			logger.info("rate ----("+noofdays+") days roomrate :"+ basewithoutmarkuptemp);

			basewithoutmarkuproomrate = basewithoutmarkuproomrate.add(basewithoutmarkuptemp);//discount is being reducted
			logger.info("rate ----total basewithoutmarkuproomrate :"+ basewithoutmarkuproomrate);
			basewithoutmarkuptemp = getRoomAdditionalGuestAmountPerDay(rr, "basewithoutmarkup");
			basewithoutmarkuptemp = basewithoutmarkuptemp.multiply(new BigDecimal(noofdays));
			basewithoutmarkupadditionalCharge = basewithoutmarkupadditionalCharge.add(basewithoutmarkuptemp);//discount is being reducted
			logger.info("rate ----basewithoutmarkupadditionalCharge :"+ basewithoutmarkupadditionalCharge);
			basewithoutmarkuptemp = getRoomAmountTaxPerDay(rr, "basewithoutmarkup");
			basewithoutmarkuptemp = basewithoutmarkuptemp.multiply(new BigDecimal(noofdays));
			basewithoutmarkuproomrateTax = basewithoutmarkuproomrateTax.add(basewithoutmarkuptemp);//discount is being reducted
			logger.info("rate ----basewithoutmarkuproomrateTax :"+ basewithoutmarkuproomrateTax);


			//booking calculations
			BigDecimal bookingtemp = getRoomAmountBeforeDiscountPerDay(rr, "booking");
			logger.info("rate ----single day roomrateBeforeDiscount :"+ bookingtemp);
			bookingtemp = bookingtemp.multiply(new BigDecimal(noofdays));
			logger.info("rate ----("+noofdays+") days roomrateBeforeDiscount :"+ bookingtemp);
			bookingroomrateBeforeDiscount = bookingroomrateBeforeDiscount.add(bookingtemp);//discount is being reducted
			logger.info("rate ----total bookingroomrateBeforeDiscount :"+ bookingroomrateBeforeDiscount);
			bookingtemp = getRoomDiscountAmountPerDay(rr, "booking");
			logger.info("rate ----single day bookingroomrateDiscount :"+ bookingtemp);
			bookingtemp = bookingtemp.multiply(new BigDecimal(noofdays));
			logger.info("rate ----("+noofdays+") days bookingroomrateDiscount :"+ bookingtemp);
			bookingroomrateDiscount = bookingroomrateDiscount.add(bookingtemp);//discount is being reducted
			logger.info("rate ----total bookingroomrateDiscount :"+ bookingroomrateDiscount);
			bookingtemp = getRoomAmountPerDay(rr, "booking");
			logger.info("rate ----single day roomrate Discount included :"+ bookingtemp);
			bookingtemp = bookingtemp.multiply(new BigDecimal(noofdays));
			logger.info("rate ----("+noofdays+") days roomrate :"+ bookingtemp);

			bookingroomrate = bookingroomrate.add(bookingtemp);//discount is being reducted
			logger.info("rate ----total bookingroomrate :"+ bookingroomrate);
			bookingtemp = getRoomAdditionalGuestAmountPerDay(rr, "booking");
			bookingtemp = bookingtemp.multiply(new BigDecimal(noofdays));
			bookingadditionalCharge = bookingadditionalCharge.add(bookingtemp);//discount is being reducted
			logger.info("rate ----bookingadditionalCharge :"+ bookingadditionalCharge);
			bookingtemp = getRoomAmountTaxPerDay(rr, "booking");
			bookingtemp = bookingtemp.multiply(new BigDecimal(noofdays));
			bookingroomrateTax = bookingroomrateTax.add(bookingtemp);//discount is being reducted
			logger.info("rate ----bookingroomrateTax :"+ bookingroomrateTax);

			bookingpayableAmt = getRoomTotalPayableAmountPerDay(rr);
			bookingpayableAmt = bookingpayableAmt.multiply(new BigDecimal(noofdays)).multiply(new BigDecimal(noofdays));

		}

		apiamountbeforeTax = apiroomrate.add(apiadditionalCharge);
		logger.info("rate ----total apiamountbeforeTax :"+ apiamountbeforeTax);
		logger.info("rate ----total api tax :"+ apiroomrateTax);
		//BigDecimal roomrateadditionalGCtax = getRoomAdditionalGuestAmountPerDay(rr);//discount is being reducted
		apipayableAmt = apiamountbeforeTax.add(apiroomrateTax);
		logger.info("rate ----apipayableAmt :"+ apipayableAmt);

		this.apiRate =  new RateMap(apiroomrate, apiadditionalCharge,  apiamountbeforeTax, apiroomrateBeforeDiscount, apiroomrateDiscount, apiroomrateTax, noofdays, apiadditionalChargeTax, apipayableAmt,apipayableAmt);


		amountbeforeTax = roomrate.add(additionalCharge);
		logger.info("rate ----total amountbeforeTax :"+ amountbeforeTax);
		logger.info("rate ----total tax :"+ roomrateTax);
		//BigDecimal roomrateadditionalGCtax = getRoomAdditionalGuestAmountPerDay(rr);//discount is being reducted
		payableAmt = amountbeforeTax.add(roomrateTax);
		logger.info("rate ----payableAmt :"+ payableAmt);

		this.baseRate =  new RateMap(roomrate, additionalCharge,  amountbeforeTax, roomrateBeforeDiscount, roomrateDiscount, roomrateTax, noofdays, additionalChargeTax, payableAmt,payableAmt);


		basewithoutmarkupamountbeforeTax = basewithoutmarkuproomrate.add(basewithoutmarkupadditionalCharge);
		logger.info("rate ----total basewithoutmarkupamountbeforeTax :"+ basewithoutmarkupamountbeforeTax);
		logger.info("rate ----total basewithoutmarkup tax :"+ basewithoutmarkuproomrateTax);
		//BigDecimal roomrateadditionalGCtax = getRoomAdditionalGuestAmountPerDay(rr);//discount is being reducted
		basewithoutmarkuppayableAmt = basewithoutmarkupamountbeforeTax.add(basewithoutmarkuproomrateTax);
		logger.info("rate ----basewithoutmarkuppayableAmt :"+ basewithoutmarkuppayableAmt);

		this.baseRateWithoutMarkUp =  new RateMap(basewithoutmarkuproomrate, basewithoutmarkupadditionalCharge,  basewithoutmarkupamountbeforeTax, basewithoutmarkuproomrateBeforeDiscount, basewithoutmarkuproomrateDiscount, basewithoutmarkuproomrateTax, noofdays, basewithoutmarkupadditionalChargeTax, basewithoutmarkuppayableAmt,basewithoutmarkuppayableAmt);

		bookingamountbeforeTax = bookingroomrate.add(bookingadditionalCharge);
		logger.info("rate ----total bookingamountbeforeTax :"+ bookingamountbeforeTax);
		logger.info("rate ----total booking tax :"+ bookingroomrateTax);
		//BigDecimal roomrateadditionalGCtax = getRoomAdditionalGuestAmountPerDay(rr);//discount is being reducted
		bookingpayableAmt = bookingamountbeforeTax.add(bookingroomrateTax);
		logger.info("rate ----bookingpayableAmt :"+ bookingpayableAmt);

		this.bookingRate =  new RateMap(bookingroomrate, bookingadditionalCharge,  bookingamountbeforeTax, bookingroomrateBeforeDiscount, bookingroomrateDiscount, bookingroomrateTax, noofdays, bookingadditionalChargeTax, bookingpayableAmt,bookingpayableAmt);



	}
	public void initRate(com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay trs, int noofdays,int noofrooms) throws NumberFormatException, Exception
	{

		BigDecimal apiroomrate = new BigDecimal("0");
		BigDecimal apiroomrateBeforeDiscount = new BigDecimal("0");
		BigDecimal apiroomrateDiscount = new BigDecimal("0");
		BigDecimal apiadditionalCharge = new BigDecimal("0");
		BigDecimal apiamountbeforeTax = new BigDecimal("0");
		BigDecimal apiroomrateTax = new BigDecimal("0");
		BigDecimal apiadditionalChargeTax = new BigDecimal("0");
		BigDecimal apipayableAmt = new BigDecimal("0");
		


		BigDecimal roomrate = new BigDecimal("0");
		BigDecimal roomrateBeforeDiscount = new BigDecimal("0");
		BigDecimal roomrateDiscount = new BigDecimal("0");
		BigDecimal additionalCharge = new BigDecimal("0");
		BigDecimal amountbeforeTax = new BigDecimal("0");
		BigDecimal roomrateTax = new BigDecimal("0");
		BigDecimal additionalChargeTax = new BigDecimal("0");
		BigDecimal payableAmt = new BigDecimal("0");
	


		BigDecimal basewithoutmarkuproomrate = new BigDecimal("0");
		BigDecimal basewithoutmarkuproomrateBeforeDiscount = new BigDecimal("0");
		BigDecimal basewithoutmarkuproomrateDiscount = new BigDecimal("0");
		BigDecimal basewithoutmarkupadditionalCharge = new BigDecimal("0");
		BigDecimal basewithoutmarkupamountbeforeTax = new BigDecimal("0");
		BigDecimal basewithoutmarkuproomrateTax = new BigDecimal("0");
		BigDecimal basewithoutmarkupadditionalChargeTax = new BigDecimal("0");
		BigDecimal basewithoutmarkuppayableAmt = new BigDecimal("0");


		BigDecimal bookingroomrate = new BigDecimal("0");
		BigDecimal bookingroomrateBeforeDiscount = new BigDecimal("0");
		BigDecimal bookingroomrateDiscount = new BigDecimal("0");
		BigDecimal bookingadditionalCharge = new BigDecimal("0");
		BigDecimal bookingamountbeforeTax = new BigDecimal("0");
		BigDecimal bookingroomrateTax = new BigDecimal("0");
		BigDecimal bookingadditionalChargeTax = new BigDecimal("0");
		BigDecimal bookingpayableAmt = new BigDecimal("0");
		BigDecimal totalPayableAmt = new BigDecimal("0");


		List<BigDecimal> roomrates = new ArrayList<BigDecimal>();
		BigDecimal total =  new BigDecimal("0");
		logger.info("rate ----single day roomrate list size :"+ trs.getRoomRates().getRoomRates().size());
		for (com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate rr : trs.getRoomRates().getRoomRates()) {
			
			//calculation for individual room ..
			///multiplied by noofdays
			//Api calculations
			BigDecimal apitemp = getRoomAmountBeforeDiscountPerDay(rr, "api");
			logger.info("apirate ----single day roomrateBeforeDiscount :"+ apitemp);
			apitemp = apitemp.multiply(new BigDecimal(noofdays));
			logger.info("apirate ----("+noofdays+") days roomrateBeforeDiscount :"+ apitemp);
			apiroomrateBeforeDiscount = apiroomrateBeforeDiscount.add(apitemp);//discount is being reducted
			logger.info("apirate ----total apiroomrateBeforeDiscount :"+ apiroomrateBeforeDiscount);
			apitemp = getRoomDiscountAmountPerDay(rr, "api");
			logger.info("apirate ----single day apiroomrateDiscount :"+ apitemp);
			apitemp = apitemp.multiply(new BigDecimal(noofdays));
			logger.info("apirate ----("+noofdays+") days apiroomrateDiscount :"+ apitemp);
			apiroomrateDiscount = apiroomrateDiscount.add(apitemp);//discount is being reducted
			logger.info("apirate ----total apiroomrateDiscount :"+ apiroomrateDiscount);
			apitemp = getRoomAmountPerDay(rr, "api");
			logger.info("apirate ----single day roomrate Discount included :"+ apitemp);
			apitemp = apitemp.multiply(new BigDecimal(noofdays));
			logger.info("apirate ----("+noofdays+") days roomrate :"+ apitemp);

			apiroomrate = apiroomrate.add(apitemp);//discount is being reducted
			logger.info("apirate ----total apiroomrate :"+ apiroomrate);
			apitemp = getRoomAdditionalGuestAmountPerDay(rr, "api");
			apitemp = apitemp.multiply(new BigDecimal(noofdays));
			apiadditionalCharge = apiadditionalCharge.add(apitemp);//discount is being reducted
			logger.info("apirate ----apiadditionalCharge :"+ apiadditionalCharge);
			apitemp = getRoomAmountTaxPerDay(rr, "api");
			apitemp = apitemp.multiply(new BigDecimal(noofdays));
			apiroomrateTax = apiroomrateTax.add(apitemp);//discount is being reducted
			logger.info("apirate ----apiroomrateTax :"+ apiroomrateTax);

			//Base calculations
			BigDecimal temp = getRoomAmountBeforeDiscountPerDay(rr, "base");
			logger.info("Baserate ----single day roomrateBeforeDiscount :"+ temp);
			temp = temp.multiply(new BigDecimal(noofdays));
			logger.info("Baserate ----("+noofdays+") days roomrateBeforeDiscount :"+ temp);
			roomrateBeforeDiscount = roomrateBeforeDiscount.add(temp);//discount is being reducted
			logger.info("Baserate ----total roomrateBeforeDiscount :"+ roomrateBeforeDiscount);
			temp = getRoomDiscountAmountPerDay(rr, "base");
			logger.info("Baserate ----single day roomrateDiscount :"+ temp);
			temp = temp.multiply(new BigDecimal(noofdays));
			logger.info("Baserate ----("+noofdays+") days roomrateDiscount :"+ temp);
			roomrateDiscount = roomrateDiscount.add(temp);//discount is being reducted
			logger.info("Baserate ----total roomrateDiscount :"+ roomrateDiscount);
			temp = getRoomAmountPerDay(rr, "base");
			logger.info("Baserate ----single day roomrate Discount included :"+ temp);
			temp = temp.multiply(new BigDecimal(noofdays));
			logger.info("Baserate ----("+noofdays+") days roomrate :"+ temp);

			roomrate = roomrate.add(temp);//discount is being reducted
			logger.info("Baserate ----total roomrate :"+ roomrate);
			temp = getRoomAdditionalGuestAmountPerDay(rr, "base");
			temp = temp.multiply(new BigDecimal(noofdays));
			additionalCharge = additionalCharge.add(temp);//discount is being reducted
			logger.info("Baserate ----additionalCharge :"+ additionalCharge);
			temp = getRoomAmountTaxPerDay(rr, "base");
			temp = temp.multiply(new BigDecimal(noofdays));
			roomrateTax = roomrateTax.add(temp);//discount is being reducted
			logger.info("Baserate ----roomrateTax :"+ roomrateTax);

			//basewithoutmarkup calculations
			BigDecimal basewithoutmarkuptemp = getRoomAmountBeforeDiscountPerDay(rr, "basewithoutmarkup");
			logger.info("basewithoutmarkuprate ----single day roomrateBeforeDiscount :"+ basewithoutmarkuptemp);
			basewithoutmarkuptemp = basewithoutmarkuptemp.multiply(new BigDecimal(noofdays));
			logger.info("basewithoutmarkuprate ----("+noofdays+") days roomrateBeforeDiscount :"+ basewithoutmarkuptemp);
			basewithoutmarkuproomrateBeforeDiscount = basewithoutmarkuproomrateBeforeDiscount.add(basewithoutmarkuptemp);//discount is being reducted
			logger.info("basewithoutmarkuprate ----total basewithoutmarkuproomrateBeforeDiscount :"+ basewithoutmarkuproomrateBeforeDiscount);
			basewithoutmarkuptemp = getRoomDiscountAmountPerDay(rr, "basewithoutmarkup");
			logger.info("basewithoutmarkuprate ----single day basewithoutmarkuproomrateDiscount :"+ basewithoutmarkuptemp);
			basewithoutmarkuptemp = basewithoutmarkuptemp.multiply(new BigDecimal(noofdays));
			logger.info("basewithoutmarkuprate ----("+noofdays+") days basewithoutmarkuproomrateDiscount :"+ basewithoutmarkuptemp);
			basewithoutmarkuproomrateDiscount = basewithoutmarkuproomrateDiscount.add(basewithoutmarkuptemp);//discount is being reducted
			logger.info("basewithoutmarkuprate ----total basewithoutmarkuproomrateDiscount :"+ basewithoutmarkuproomrateDiscount);
			basewithoutmarkuptemp = getRoomAmountPerDay(rr, "basewithoutmarkup");
			logger.info("basewithoutmarkuprate ----single day roomrate Discount included :"+ basewithoutmarkuptemp);
			basewithoutmarkuptemp = basewithoutmarkuptemp.multiply(new BigDecimal(noofdays));
			logger.info("basewithoutmarkuprate ----("+noofdays+") days roomrate :"+ basewithoutmarkuptemp);

			basewithoutmarkuproomrate = basewithoutmarkuproomrate.add(basewithoutmarkuptemp);//discount is being reducted
			logger.info("basewithoutmarkuprate ----total basewithoutmarkuproomrate :"+ basewithoutmarkuproomrate);
			basewithoutmarkuptemp = getRoomAdditionalGuestAmountPerDay(rr, "basewithoutmarkup");
			basewithoutmarkuptemp = basewithoutmarkuptemp.multiply(new BigDecimal(noofdays));
			basewithoutmarkupadditionalCharge = basewithoutmarkupadditionalCharge.add(basewithoutmarkuptemp);//discount is being reducted
			logger.info("basewithoutmarkuprate ----basewithoutmarkupadditionalCharge :"+ basewithoutmarkupadditionalCharge);
			basewithoutmarkuptemp = getRoomAmountTaxPerDay(rr, "basewithoutmarkup");
			basewithoutmarkuptemp = basewithoutmarkuptemp.multiply(new BigDecimal(noofdays));
			basewithoutmarkuproomrateTax = basewithoutmarkuproomrateTax.add(basewithoutmarkuptemp);//discount is being reducted
			logger.info("basewithoutmarkuprate ----basewithoutmarkuproomrateTax :"+ basewithoutmarkuproomrateTax);


			//booking calculations
			BigDecimal bookingtemp = getRoomAmountBeforeDiscountPerDay(rr, "booking");
			logger.info("bookingrate ----single day roomrateBeforeDiscount :"+ bookingtemp);
			bookingtemp = bookingtemp.multiply(new BigDecimal(noofdays));
			logger.info("bookingrate ----("+noofdays+") days roomrateBeforeDiscount :"+ bookingtemp);
			bookingroomrateBeforeDiscount = bookingroomrateBeforeDiscount.add(bookingtemp);//discount is being reducted
			logger.info("bookingrate ----total bookingroomrateBeforeDiscount :"+ bookingroomrateBeforeDiscount);
			bookingtemp = getRoomDiscountAmountPerDay(rr, "booking");
			logger.info("bookingrate ----single day bookingroomrateDiscount :"+ bookingtemp);
			bookingtemp = bookingtemp.multiply(new BigDecimal(noofdays));
			logger.info("bookingrate ----("+noofdays+") days bookingroomrateDiscount :"+ bookingtemp);
			bookingroomrateDiscount = bookingroomrateDiscount.add(bookingtemp);//discount is being reducted
			logger.info("bookingrate ----total bookingroomrateDiscount :"+ bookingroomrateDiscount);
			bookingtemp = getRoomAmountPerDay(rr, "booking");
			logger.info("bookingrate ----single day roomrate Discount included :"+ bookingtemp);
			bookingtemp = bookingtemp.multiply(new BigDecimal(noofdays));
			logger.info("bookingrate ----("+noofdays+") days roomrate :"+ bookingtemp);

			bookingroomrate = bookingroomrate.add(bookingtemp);//discount is being reducted
			logger.info("bookingrate ----total bookingroomrate :"+ bookingroomrate);
			bookingtemp = getRoomAdditionalGuestAmountPerDay(rr, "booking");
			bookingtemp = bookingtemp.multiply(new BigDecimal(noofdays));
			bookingadditionalCharge = bookingadditionalCharge.add(bookingtemp);//discount is being reducted
			logger.info("bookingrate ----bookingadditionalCharge :"+ bookingadditionalCharge);
			bookingtemp = getRoomAmountTaxPerDay(rr, "booking");
			bookingtemp = bookingtemp.multiply(new BigDecimal(noofdays));
			
			bookingroomrateTax = bookingroomrateTax.add(bookingtemp);//discount is being reducted
			logger.info("bookingrate ----bookingroomrateTax :"+ bookingroomrateTax);

			bookingpayableAmt = getRoomTotalPayableAmountPerDay(rr);
			bookingpayableAmt = bookingpayableAmt.multiply(new BigDecimal(noofdays)).multiply(new BigDecimal(noofrooms));


		}

		apiamountbeforeTax = apiroomrate.add(apiadditionalCharge);
		logger.info("apirate ----total apiamountbeforeTax :"+ apiamountbeforeTax);
		logger.info("apirate ----total api tax :"+ apiroomrateTax);
		//BigDecimal roomrateadditionalGCtax = getRoomAdditionalGuestAmountPerDay(rr);//discount is being reducted
		apipayableAmt = apiamountbeforeTax.add(apiroomrateTax);
		logger.info("apirate ----apipayableAmt :"+ apipayableAmt);

		this.apiRate =  new RateMap(apiroomrate, apiadditionalCharge,  apiamountbeforeTax, apiroomrateBeforeDiscount, apiroomrateDiscount, apiroomrateTax, noofdays, apiadditionalChargeTax, apipayableAmt,apipayableAmt);


		amountbeforeTax = roomrate.add(additionalCharge);
		logger.info("baserate ----total amountbeforeTax :"+ amountbeforeTax);
		logger.info("baserate ----total tax :"+ roomrateTax);
		//BigDecimal roomrateadditionalGCtax = getRoomAdditionalGuestAmountPerDay(rr);//discount is being reducted
		payableAmt = amountbeforeTax.add(roomrateTax);
		logger.info("baserate ----payableAmt :"+ payableAmt);

		this.baseRate =  new RateMap(roomrate, additionalCharge,  amountbeforeTax, roomrateBeforeDiscount, roomrateDiscount, roomrateTax, noofdays, additionalChargeTax, payableAmt, payableAmt);


		basewithoutmarkupamountbeforeTax = basewithoutmarkuproomrate.add(basewithoutmarkupadditionalCharge);
		logger.info("basewithoutmarkuprate ----total basewithoutmarkupamountbeforeTax :"+ basewithoutmarkupamountbeforeTax);
		logger.info("basewithoutmarkuprate ----total basewithoutmarkup tax :"+ basewithoutmarkuproomrateTax);
		//BigDecimal roomrateadditionalGCtax = getRoomAdditionalGuestAmountPerDay(rr);//discount is being reducted
		basewithoutmarkuppayableAmt = basewithoutmarkupamountbeforeTax.add(basewithoutmarkuproomrateTax);
		logger.info("basewithoutmarkuprate ----basewithoutmarkuppayableAmt :"+ basewithoutmarkuppayableAmt);

		this.baseRateWithoutMarkUp =  new RateMap(basewithoutmarkuproomrate, basewithoutmarkupadditionalCharge,  basewithoutmarkupamountbeforeTax, basewithoutmarkuproomrateBeforeDiscount, basewithoutmarkuproomrateDiscount, basewithoutmarkuproomrateTax, noofdays, basewithoutmarkupadditionalChargeTax, basewithoutmarkuppayableAmt,basewithoutmarkuppayableAmt);

		bookingamountbeforeTax = bookingroomrate.add(bookingadditionalCharge);
		logger.info("bookingrate ----total bookingamountbeforeTax :"+ bookingamountbeforeTax);
		logger.info("bookingrate ----total booking tax :"+ bookingroomrateTax);
		//BigDecimal roomrateadditionalGCtax = getRoomAdditionalGuestAmountPerDay(rr);//discount is being reducted
		totalPayableAmt = bookingamountbeforeTax.add(bookingroomrateTax);
		logger.info("bookingrate ----bookingpayableAmt :"+ totalPayableAmt);
		logger.info("bookingrate ----bookingpayableAmt :"+ bookingpayableAmt);

		this.bookingRate =  new RateMap(bookingroomrate, bookingadditionalCharge,  bookingamountbeforeTax, bookingroomrateBeforeDiscount, bookingroomrateDiscount, bookingroomrateTax, noofdays, bookingadditionalChargeTax, totalPayableAmt,bookingpayableAmt);



	}


	public void reviseRate(com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs, BigDecimal revisedRateApi) throws NumberFormatException, Exception
	{
		this.apiRate.setPayableAmt(revisedRateApi);
		BigDecimal markupamount = this.baseRate.getPayableAmt().subtract(this.baseRateWithoutMarkUp.getPayableAmt());
		//BasicPropertyInfoType tbasic = rs.getBasicPropertyInfo();

		BasicPropertyInfoType tbasic = rs.getBasicPropertyInfo();
		if(tbasic != null && tbasic.getApiProviderBasicMap()!= null && tbasic.getApiProviderBasicMap().containsKey(rs.getBasicPropertyInfo().getApiProvider()))
			tbasic = tbasic.getApiProviderBasicMap().get(rs.getBasicPropertyInfo().getApiProvider());


		String apiCurrency = tbasic.getApiCurrency();
		String baseCurrency = tbasic.getBaseCurrency();
		String bookingCurrency = tbasic.getBookingCurrency();
		BigDecimal exRateApiToBase  = tbasic.getExRateApiToBase();
		BigDecimal exRateBaseToBooking = tbasic.getExRateBaseToBooking();

		BigDecimal basePriceWithoutMarpup = revisedRateApi.multiply(exRateApiToBase);
		BigDecimal basePrice = basePriceWithoutMarpup.add(markupamount);
		BigDecimal bookingPrice = basePrice.multiply(exRateBaseToBooking);


		this.baseRate.setPayableAmt(basePrice);
		this.baseRateWithoutMarkUp.setPayableAmt(basePriceWithoutMarpup);
		this.bookingRate.setPayableAmt(bookingPrice);

		logger.info("rate ----this.api  :"+ this.apiRate.toString());
		logger.info("rate ----this.bookingPrice  :"+ this.bookingRate.toString());
	}
	public void reviseRate(BigDecimal revisedApiPrice, BigDecimal revisedBasePrice, BigDecimal revisedBasePriceWithoutMarpup, BigDecimal revisedBookingPrice,BigDecimal revisedPayableBookingPrice,HotelOrderRow hor)
	{

		BigDecimal diffInApiFinalPrice = revisedApiPrice.subtract(this.apiRate.getPayableAmt());
		BigDecimal roomRateApiOld = this.apiRate.getRoomrate();
		BigDecimal roomRateApiNew = roomRateApiOld.add(diffInApiFinalPrice);

		BigDecimal payableApiOld = this.apiRate.getPayableAmt();
		BigDecimal payableApiNew = payableApiOld.add(diffInApiFinalPrice);

		BigDecimal amountBeforeTaxOld = this.apiRate.getAmountbeforeTax();
		BigDecimal amountBeforeTaxNew = amountBeforeTaxOld.add(diffInApiFinalPrice);

		BigDecimal roomRateBeforeApiDiscountOld = this.apiRate.getPayableAmt();
		BigDecimal roomRateBeforeApiDiscountNew = roomRateBeforeApiDiscountOld.add(diffInApiFinalPrice);

		this.apiRate.setRoomrate(roomRateApiNew);
		this.apiRate.setPayableAmt(payableApiNew);
		this.apiRate.setAmountbeforeTax(amountBeforeTaxNew);
		this.apiRate.setRoomrateBeforeDiscount(roomRateBeforeApiDiscountNew);



		BigDecimal diffInBaseFinalPrice = revisedBasePrice.subtract(this.baseRate.getPayableAmt());
		BigDecimal roomRateBaseOld = this.baseRate.getRoomrate();
		BigDecimal roomRateBaseNew = roomRateBaseOld.add(diffInBaseFinalPrice);

		BigDecimal payableBaseOld = this.baseRate.getPayableAmt();
		BigDecimal payableBaseNew = payableBaseOld.add(diffInBaseFinalPrice);

		BigDecimal amountBeforeTaxApiOld = this.baseRate.getAmountbeforeTax();
		BigDecimal amountBeforeTaxApiNew = amountBeforeTaxApiOld.add(diffInBaseFinalPrice);

		BigDecimal roomRateBeforeBaseDiscountOld = this.baseRate.getPayableAmt();
		BigDecimal roomRateBeforeBaseDiscountNew = roomRateBeforeBaseDiscountOld.add(diffInBaseFinalPrice);

		this.baseRate.setRoomrate(roomRateBaseNew);
		this.baseRate.setPayableAmt(payableBaseNew);
		this.baseRate.setAmountbeforeTax(amountBeforeTaxApiNew);
		this.baseRate.setRoomrateBeforeDiscount(roomRateBeforeBaseDiscountNew);



		BigDecimal diffInBaseWithoutMarkupFinalPrice = revisedBasePriceWithoutMarpup.subtract(this.baseRateWithoutMarkUp.getPayableAmt());
		BigDecimal roomRateBaseWithoutMarkupOld = this.baseRateWithoutMarkUp.getRoomrate();
		BigDecimal roomRateBaseWithoutMarkupNew = roomRateBaseWithoutMarkupOld.add(diffInBaseWithoutMarkupFinalPrice);

		BigDecimal payableBaseWithoutMarkupOld = this.baseRateWithoutMarkUp.getPayableAmt();
		BigDecimal payableBaseWithoutMarkupNew = payableBaseWithoutMarkupOld.add(diffInBaseWithoutMarkupFinalPrice);

		BigDecimal amountBeforeTaxBaseWithoutMarkupOld = this.baseRateWithoutMarkUp.getAmountbeforeTax();
		BigDecimal amountBeforeTaxBaseWithoutMarkupNew = amountBeforeTaxBaseWithoutMarkupOld.add(diffInBaseWithoutMarkupFinalPrice);

		BigDecimal roomRateBeforeBaseWithoutMarkupDiscountOld = this.baseRateWithoutMarkUp.getPayableAmt();
		BigDecimal roomRateBeforeBaseWithoutMarkupDiscountNew = roomRateBeforeBaseWithoutMarkupDiscountOld.add(diffInBaseWithoutMarkupFinalPrice);

		this.baseRateWithoutMarkUp.setRoomrate(roomRateBaseWithoutMarkupNew);
		this.baseRateWithoutMarkUp.setPayableAmt(payableBaseWithoutMarkupNew);
		this.baseRateWithoutMarkUp.setAmountbeforeTax(amountBeforeTaxBaseWithoutMarkupNew);
		this.baseRateWithoutMarkUp.setRoomrateBeforeDiscount(roomRateBeforeBaseWithoutMarkupDiscountNew);



		BigDecimal diffInBookingFinalPrice = revisedBookingPrice.subtract(this.bookingRate.getPayableAmt());
		BigDecimal roomRateBookingOld = this.bookingRate.getRoomrate();
		BigDecimal roomRateBookingNew = roomRateBookingOld.add(diffInBookingFinalPrice);

		BigDecimal payableBookingOld = this.bookingRate.getPayableAmt();
		BigDecimal payableBookingNew = payableBookingOld.add(diffInBookingFinalPrice);

		BigDecimal amountBeforeTaxBookingOld = this.bookingRate.getAmountbeforeTax();
		BigDecimal amountBeforeTaxBookingNew = amountBeforeTaxBookingOld.add(diffInBookingFinalPrice);

		BigDecimal roomRateBeforeBookingDiscountOld = this.bookingRate.getPayableAmt();
		BigDecimal roomRateBeforeBookingDiscountNew = roomRateBeforeBookingDiscountOld.add(diffInBookingFinalPrice);
		
		BigDecimal roomRateBeforeTotalAmountOld = this.bookingRate.getTotalPayableAmt();
		BigDecimal roomRateBeforeTotalAmountNew = roomRateBeforeTotalAmountOld.add(diffInBookingFinalPrice);
		

		this.bookingRate.setRoomrate(roomRateBookingNew);
		this.bookingRate.setPayableAmt(AmountRoundingModeUtil.roundingModeForHotel(payableBookingNew.add(hor.getFeeAmount())));
		this.bookingRate.setTotalPayableAmt(roomRateBeforeTotalAmountNew);
		this.bookingRate.setAmountbeforeTax(amountBeforeTaxBookingNew);
		this.bookingRate.setRoomrateBeforeDiscount(roomRateBeforeBookingDiscountNew);



		/*this.apiRate.setPayableAmt(revisedApiPrice);
		this.baseRate.setPayableAmt(revisedBasePrice);
		this.baseRateWithoutMarkUp.setPayableAmt(revisedBasePriceWithoutMarpup);
		this.bookingRate.setPayableAmt(revisedBookingPrice);*/

		logger.info("rate ----this.api  :"+ this.apiRate.toString());
		logger.info("rate ----this.bookingPrice  :"+ this.bookingRate.toString());
	}





	/*public void initRate(com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay trs, int noofdays)
	{
		BigDecimal roomrate = new BigDecimal("0");
		BigDecimal roomrateBeforeDiscount = new BigDecimal("0");
		BigDecimal roomrateDiscount = new BigDecimal("0");
		BigDecimal additionalCharge = new BigDecimal("0");
		BigDecimal amountbeforeTax = new BigDecimal("0");
		BigDecimal roomrateTax = new BigDecimal("0");
		BigDecimal additionalChargeTax = new BigDecimal("0");
		BigDecimal payableAmt = new BigDecimal("0");

		BigDecimal roomrateMarkup = new BigDecimal("0");
		BigDecimal roomrateBeforeDiscountMarkup = new BigDecimal("0");
		BigDecimal roomrateDiscountMarkup = new BigDecimal("0");
		BigDecimal additionalChargeMarkup = new BigDecimal("0");
		BigDecimal amountbeforeTaxMarkup = new BigDecimal("0");
		BigDecimal roomrateTaxMarkup = new BigDecimal("0");
		BigDecimal additionalChargeTaxMarkup = new BigDecimal("0");
		BigDecimal payableAmtMarkup = new BigDecimal("0");

		BigDecimal total =  new BigDecimal("0");

		if(trs.getRoomTypes() != null || trs.getRoomTypes().getRoomTypes() != null || (!trs.getRoomTypes().getRoomTypes().isEmpty()))
			for (RoomTypeType rt : trs.getRoomTypes().getRoomTypes()) {
				for (Room room : rt.getRooms()) {
					logger.info("rate ----total room.getTotalWithoutMarkup() :"+ room.getTotalWithoutMarkup());
					logger.info("rate ----total room.getTotal() :"+ room.getTotal());

					if(room.getTotal() != null)
						roomrateMarkup = roomrateMarkup.add(room.getTotal());
					if(room.getTotalWithoutMarkup() != null)
						roomrate = roomrate.add(room.getTotalWithoutMarkup());

					payableAmt = roomrateMarkup;
					payableAmtMarkup = roomrateMarkup;

				}
			}

		payableAmt = payableAmt.multiply(new BigDecimal(noofdays));
		payableAmt = payableAmt.multiply(new BigDecimal(noofdays));
		this.rateWithoutMarkUp = new RateMap(roomrate, additionalCharge,  amountbeforeTax, roomrateBeforeDiscount, roomrateDiscount, roomrateTax, noofdays, additionalChargeTax, payableAmt);
		this.rate = new RateMap(roomrateMarkup, additionalChargeMarkup,  amountbeforeTaxMarkup, roomrateBeforeDiscountMarkup, roomrateDiscountMarkup, roomrateTaxMarkup, noofdays, additionalChargeTaxMarkup, payableAmtMarkup);
		logger.info("rate ----this.rate  :"+ this.rate .toString());
		logger.info("rate ----this.rateWithoutMarkUp  :"+ this.rateWithoutMarkUp .toString());
	}
*/

	/*public BigDecimal getTotalPayable(List<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate> ratelist, int noofdays)
	{
		List<BigDecimal> roomrates = new ArrayList<BigDecimal>();
		BigDecimal total =  new BigDecimal("0");;
		for (com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate rr : ratelist) {

			//calculation for individual room ..
			///multiplied by noofdays
			BigDecimal roomrate = getRoomAmountPerDay(rr, false);//discount is being reducted
			BigDecimal roomrateadditionalGC = getRoomAdditionalGuestAmountPerDay(rr, false);//discount is being reducted

			BigDecimal roomtotalpayable = new BigDecimal("0");

			BigDecimal amountbeforetax = roomrate.add(roomrateadditionalGC);
			amountbeforetax = amountbeforetax.multiply(new BigDecimal(noofdays));

			//tax of the room..
			BigDecimal roomratetax = getRoomAmountTaxPerDay(rr);//discount is being reducted
			//BigDecimal roomrateadditionalGCtax = getRoomAdditionalGuestAmountPerDay(rr);//discount is being reducted
			roomratetax = roomratetax.multiply(new BigDecimal(noofdays));

			roomtotalpayable = amountbeforetax.subtract(roomratetax);
			roomrates.add(roomtotalpayable);

		}

		for (BigDecimal bigDecimal : roomrates) {
			total = total.add(bigDecimal);
		}
		return total;

	}
	 */

	public static class RateMap implements Serializable{
		@Override
		public String toString() {
			return "RateMap [roomrate=" + roomrate + ", additionalCharge=" + additionalCharge + ", amountbeforeTax="
					+ amountbeforeTax + ", roomrateBeforeDiscount=" + roomrateBeforeDiscount + ", roomrateDiscount="
					+ roomrateDiscount + ", roomrateTax=" + roomrateTax + ", noofdays=" + noofdays
					+ ", additionalChargeTax=" + additionalChargeTax + ", payableAmt=" + payableAmt + "]";
		}
		public RateMap(BigDecimal roomrate, BigDecimal additionalCharge, BigDecimal amountbeforeTax,
				BigDecimal roomrateBeforeDiscount, BigDecimal roomrateDiscount, BigDecimal roomrateTax, int noofdays,
				BigDecimal additionalChargeTax, BigDecimal payableAmt,BigDecimal totalPayableAmt) {
			super();
			this.roomrate = roomrate;
			this.additionalCharge = additionalCharge;
			this.amountbeforeTax = amountbeforeTax;
			this.roomrateBeforeDiscount = roomrateBeforeDiscount;
			this.roomrateDiscount = roomrateDiscount;
			this.roomrateTax = roomrateTax;
			this.noofdays = noofdays;
			this.additionalChargeTax = additionalChargeTax;
			this.payableAmt = payableAmt;
			this.totalPayableAmt = totalPayableAmt;
		}
		public BigDecimal getRoomrateBeforeDiscount() {
			return roomrateBeforeDiscount;
		}
		public void setRoomrateBeforeDiscount(BigDecimal roomrateBeforeDiscount) {
			this.roomrateBeforeDiscount = roomrateBeforeDiscount;
		}
		public BigDecimal getRoomrateDiscount() {
			return roomrateDiscount;
		}
		public void setRoomrateDiscount(BigDecimal roomrateDiscount) {
			this.roomrateDiscount = roomrateDiscount;
		}
		public int getNoofdays() {
			return noofdays;
		}
		public void setNoofdays(int noofdays) {
			this.noofdays = noofdays;
		}
		/*public RateMap(BigDecimal roomrate, BigDecimal additionalCharge, BigDecimal amountbeforeTax,
				BigDecimal roomrateTax, BigDecimal additionalChargeTax, BigDecimal payableAmt) {
			super();
			this.roomrate = roomrate;
			this.roomrateBeforeDiscount = roomrateBeforeDiscount;
			this.roomrateDiscount = roomrateDiscount;
			this.noofdays = noofdays;
			this.additionalCharge = additionalCharge;
			this.amountbeforeTax = amountbeforeTax;
			this.roomrateTax = roomrateTax;
			this.additionalChargeTax = additionalChargeTax;
			this.payableAmt = payableAmt;
		}*/

		public BigDecimal getRoomrate() {
			return roomrate;
		}
		public void setRoomrate(BigDecimal roomrate) {
			this.roomrate = roomrate;
		}
		public BigDecimal getAdditionalCharge() {
			return additionalCharge;
		}
		public void setAdditionalCharge(BigDecimal additionalCharge) {
			this.additionalCharge = additionalCharge;
		}
		public BigDecimal getAmountbeforeTax() {
			return amountbeforeTax;
		}
		public void setAmountbeforeTax(BigDecimal amountbeforeTax) {
			this.amountbeforeTax = amountbeforeTax;
		}
		public BigDecimal getRoomrateTax() {
			return roomrateTax;
		}
		public void setRoomrateTax(BigDecimal roomrateTax) {
			this.roomrateTax = roomrateTax;
		}
		public BigDecimal getAdditionalChargeTax() {
			return additionalChargeTax;
		}
		public void setAdditionalChargeTax(BigDecimal additionalChargeTax) {
			this.additionalChargeTax = additionalChargeTax;
		}
		public BigDecimal getPayableAmt() {
			return payableAmt;
		}
		public void setPayableAmt(BigDecimal payableAmt) {
			this.payableAmt = payableAmt;
		}
		public RateMap() {
			super();
			// TODO Auto-generated constructor stub
		}

		private BigDecimal roomrate;
		private BigDecimal additionalCharge;


		private BigDecimal amountbeforeTax;
		private BigDecimal roomrateBeforeDiscount;
		private BigDecimal roomrateDiscount;
		private BigDecimal roomrateTax;

		private int noofdays;
		private BigDecimal additionalChargeTax;
		private BigDecimal payableAmt;
		private BigDecimal totalPayableAmt;
        private HotelServiceTax hotelServiceTax;
        private HotelGstTax hotelGstTax;

		public HotelGstTax getHotelGstTax() {
			return hotelGstTax;
		}
		public void setHotelGstTax(HotelGstTax hotelGstTax) {
			this.hotelGstTax = hotelGstTax;
		}
		public HotelServiceTax getHotelServiceTax() {
			return hotelServiceTax;
		}
		public void setHotelServiceTax(HotelServiceTax hotelServiceTax) {
			this.hotelServiceTax = hotelServiceTax;
		}
		public BigDecimal getTotalPayableAmt() {
			return totalPayableAmt;
		}
		public void setTotalPayableAmt(BigDecimal totalPayableAmt) {
			this.totalPayableAmt = totalPayableAmt;
		}

	}


	//private UniqueIDType uniqueid;
}
