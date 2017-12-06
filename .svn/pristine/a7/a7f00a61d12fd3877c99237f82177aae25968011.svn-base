package com.tayyarah.hotel.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.common.dao.MoneyExchangeDaoImp;
import com.tayyarah.common.gstconfig.entity.HotelGstTaxConfig;
import com.tayyarah.common.gstconfig.model.HotelGstTax;
import com.tayyarah.common.model.CommissionDetails;
import com.tayyarah.common.servicetaxconfig.entity.HotelServiceTaxConfig;
import com.tayyarah.common.servicetaxconfig.model.HotelServiceTax;
import com.tayyarah.common.util.AmountRoundingModeUtil;
import com.tayyarah.common.util.AppControllerUtil;
import com.tayyarah.common.util.CommonUtil;
import com.tayyarah.common.util.IndianUnionTerritories;
import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.company.entity.Company;
import com.tayyarah.company.entity.CompanyConfig;
import com.tayyarah.email.dao.EmailDaoImp;
import com.tayyarah.flight.dao.FlightBookingDao;
import com.tayyarah.hotel.dao.HotelBookingDao;
import com.tayyarah.hotel.dao.HotelOrderDao;
import com.tayyarah.hotel.dao.HotelSearchDao;
import com.tayyarah.hotel.dao.HotelTransactionDao;
import com.tayyarah.hotel.entity.HotelMarkup;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.hotel.model.APIHotelBook;
import com.tayyarah.hotel.model.APIHotelCancelResponse;
import com.tayyarah.hotel.model.APIStatus;
import com.tayyarah.hotel.model.AmountPercentType;
import com.tayyarah.hotel.model.AmountType.Discount;
import com.tayyarah.hotel.model.BasicPropertyInfoType;
import com.tayyarah.hotel.model.CancelPenaltiesType;
import com.tayyarah.hotel.model.CancelPenaltyType;
import com.tayyarah.hotel.model.CancelRuleType;
import com.tayyarah.hotel.model.HotelSearchCommand;
import com.tayyarah.hotel.model.OTAHotelResRS;
import com.tayyarah.hotel.model.RateType;
import com.tayyarah.hotel.model.RateType.Rate;
import com.tayyarah.hotel.model.RoomStayType.RatePlans;
import com.tayyarah.hotel.model.RoomStayType.RoomRates;
import com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;
import com.tayyarah.hotel.model.TaxType;
import com.tayyarah.hotel.model.TaxesType;
import com.tayyarah.hotel.model.TotalType;
import com.tayyarah.hotel.util.HotelApiCredentials.ApiRateInfo;
import com.tayyarah.user.dao.UserWalletDAO;

public class CurrencyManager {
	public static final Logger logger = Logger.getLogger(CurrencyManager.class);
	
	@Autowired
	HotelObjectTransformer hotelObjectTransformer;
	@Autowired
	HotelTransactionDao hotelTransactionDao;
	@Autowired
	HotelSearchDao hotelSearchDao;
	@Autowired
	HotelOrderDao hotelOrderDao;
	@Autowired
	com.tayyarah.hotel.util.api.concurrency.AsyncSupport asyncSupport;
	@Autowired
	FlightBookingDao FBDAO;
	@Autowired
	CompanyDao CDAO;
	@Autowired
	UserWalletDAO AWDAO;
	@Autowired
	HotelBookingDao hotelBookingDao;
	@Autowired
	HotelIdFactoryImpl hotelIdFactory;
	@Autowired
	EmailDaoImp emaildao;
	@Autowired
	MoneyExchangeDaoImp moneydao;
	@Autowired
	CompanyConfigDAO companyConfigDAO;


	public RoomStay fillCurrencyDataOnHotel(RoomStay rs, Map<Integer, ApiRateInfo> apiCurrencyRateMap,  HotelSearchCommand hs,CompanyConfig companyConfig,Company company,Company parentCompany) throws HibernateException, IOException, ParseException
	{
		List<Entry<String, CommissionDetails>> commissionList = new LinkedList<Entry<String, CommissionDetails>>(hs.getHotelMarkupCommissionDetails().getCommissionDetailsMap().entrySet());
		// Sorting the list based on values
		Collections.sort(commissionList, new Comparator<Entry<String, CommissionDetails>>() {
			@Override
			public int compare(Entry<String, CommissionDetails> c1, Entry<String, CommissionDetails> c2) {
				Integer companyid1 = Integer.valueOf(c1.getValue().getCompanyId());
				Integer companyid2 = Integer.valueOf(c2.getValue().getCompanyId());
				return companyid1.compareTo(companyid2);
			}
		});

		int noofdays = CommonUtil.getNoofStayDays(hs);
		int noofguests =  CommonUtil.getGuestTotalCount(hs);
		int totalmarkupmultiplier = noofdays * hs.getNoofrooms();

		BasicPropertyInfoType tbasic = rs.getBasicPropertyInfo();
		ApiRateInfo apiRateInfo = apiCurrencyRateMap.get(tbasic.getApiProvider());

		String apiCurrency = apiRateInfo.getApiCurrency();
		String baseCurrency = apiRateInfo.getBaseCurrency();
		String bookingCurrency = hs.getCurrency();

		BigDecimal apiPrice = tbasic.getApiPrice();
		BigDecimal exRateApiToBase  = apiRateInfo.getExRateApiToBase();
		BigDecimal exRateBaseToBooking = apiRateInfo.getExRateBaseToBooking();

		BigDecimal basePrice = apiPrice.multiply(exRateApiToBase);
		BigDecimal basePriceWithoutMarpup = basePrice;

		BigDecimal commissionalAmount = basePrice;
		BigDecimal baseMarkUpAmount = BigDecimal.valueOf(0);
		for (Entry<String, CommissionDetails> commissionEntry : commissionList) {
			CommissionDetails agentcommission = commissionEntry.getValue();
			String companyId = agentcommission.getCompanyId();
			List<HotelMarkup> markups = hs.getHotelMarkupCommissionDetails().getMarkups().get(agentcommission.getCompanyId());

			//redo commission details..
			//agentcommission.se
			BigDecimal companycommissionAMount = new BigDecimal("0.00");
			if(agentcommission.getRateType().equalsIgnoreCase("Commission")){
				if(agentcommission.getCommissionType().equalsIgnoreCase("Fixed")){
					companycommissionAMount = agentcommission.getCommissionAmount();
				}else{
					companycommissionAMount = commissionalAmount
							.multiply(agentcommission.getCommissionAmount()).divide(new BigDecimal("100"));
				}
			}
			agentcommission.setCommissionCalculatedAmount(companycommissionAMount);
			BigDecimal agentmarkup = BigDecimal.valueOf(0);
			int markupindex = 0;
			for (HotelMarkup hotelMarkup : markups) {
				boolean isappliable = HotelMarkUpUtil.isMarkUpApplicable(hs, hotelMarkup, rs);
				//logger.info(hotelMarkup.getName()+"\\\\\\\\markup--isappliable-----"+isappliable+"----for ----:"+ rs.getBasicPropertyInfo().getHotelName());
				//logger.info(hotelMarkup.getName()+"\\\\\\\\markup--getIsaccumulative----"+hotelMarkup.getIsaccumulative()+"----");
				//logger.info(hotelMarkup.getName()+"\\\\\\\\markup--getIsfixedAmount----"+hotelMarkup.getIsfixedAmount()+"----");
				//logger.info(hotelMarkup.getName()+"\\\\\\\\markup--getIsaccumulative----"+hotelMarkup.getIsaccumulative()+"----");
				BigDecimal markUpAmountbasePrice = BigDecimal.valueOf(0);
				BigDecimal markUpAmountATax = BigDecimal.valueOf(0);
				BigDecimal markUpAmountPayable = BigDecimal.valueOf(0);
				if(isappliable)
				{
					if(markupindex == 0 && (hotelMarkup.getIsaccumulative()==0))
					{
						markUpAmountbasePrice = HotelMarkUpUtil.applyMarkUp(hotelMarkup, basePrice, 1,totalmarkupmultiplier);

						agentmarkup = agentmarkup.add(markUpAmountbasePrice);
						//Adding markup to total markup
						baseMarkUpAmount = baseMarkUpAmount.add(markUpAmountbasePrice);
						break;
					}
					else if(markupindex != 0 && (hotelMarkup.getIsaccumulative()==0))
					{
						continue;
					}
					else if(hotelMarkup.getIsaccumulative()==1)
					{
						markUpAmountbasePrice = HotelMarkUpUtil.applyMarkUp(hotelMarkup, basePrice, 1,totalmarkupmultiplier);

						agentmarkup = agentmarkup.add(markUpAmountbasePrice);
						//Adding markup to total markup
						baseMarkUpAmount = baseMarkUpAmount.add(markUpAmountbasePrice);
					}
				}
				markupindex++;
			}
			commissionalAmount = commissionalAmount.add(agentmarkup);
		}	
		basePrice = basePrice.add(baseMarkUpAmount);
		BigDecimal bookingPrice = basePrice.multiply(exRateBaseToBooking);
		
		tbasic.setApiPrice(apiPrice);
		tbasic.setBasePrice(AmountRoundingModeUtil.roundingModeForHotel(basePrice));
		tbasic.setBasePriceWithoutMarkup(AmountRoundingModeUtil.roundingModeForHotel(basePriceWithoutMarpup));
		tbasic.setBookingPrice(AmountRoundingModeUtil.roundingModeForHotel(bookingPrice));
		tbasic.setExRateApiToBase(exRateApiToBase);
		tbasic.setExRateBaseToBooking(exRateBaseToBooking);

		HotelServiceTax hotelServiceTax = null;
		HotelGstTax hotelGstTax = null;
		if(companyConfig != null){
			if(companyConfig.getCompanyConfigType().isB2E()){
				if(companyConfig.getTaxtype()!=null && companyConfig.getTaxtype().equalsIgnoreCase("GST")){
					hotelGstTax = new HotelGstTax();
					hotelGstTax = getHotelGstTax(hs.getCountry(), companyConfig, noofdays, hs.getNoofrooms(), company, parentCompany,noofguests);
					tbasic.setBookingPrice(AmountRoundingModeUtil.roundingModeForHotel(bookingPrice.add(hotelGstTax.getTotalTax().add(hotelGstTax.getManagementFee()))));
				}else{
					hotelServiceTax = new HotelServiceTax();
					hotelServiceTax = getHotelServiceTax(basePrice,hs.getCountry(),companyConfig,noofdays,hs.getNoofrooms());
					tbasic.setBookingPrice(AmountRoundingModeUtil.roundingModeForHotel(bookingPrice.add(hotelServiceTax.getTotalServiceTax().add(hotelServiceTax.getManagementFee()))));
				}
			}
		}
		tbasic.setApiCurrency(apiCurrency);
		tbasic.setBaseCurrency(baseCurrency);
		tbasic.setBookingCurrency(bookingCurrency);
		rs.setBasicPropertyInfo(tbasic);
		return rs;
	}	

	public APIHotelBook fillCurrencyOnPreBookResponse(APIHotelBook apiHotelBook,HotelOrderRow hor)throws HibernateException, IOException, ParseException
	{
		List<Entry<String, CommissionDetails>> commissionList = new LinkedList<Entry<String, CommissionDetails>>(apiHotelBook.getSearch().getHotelMarkupCommissionDetails().getCommissionDetailsMap().entrySet());
		// Sorting the list based on values
		Collections.sort(commissionList, new Comparator<Entry<String, CommissionDetails>>() {
			@Override
			public int compare(Entry<String, CommissionDetails> c1, Entry<String, CommissionDetails> c2) {
				Integer companyid1 = Integer.valueOf(c1.getValue().getCompanyId());
				Integer companyid2 = Integer.valueOf(c2.getValue().getCompanyId());
				return companyid1.compareTo(companyid2);
			}
		});

		Map<String,List<HotelMarkup>> markupsmap = apiHotelBook.getSearch().getHotelMarkupCommissionDetails().getMarkups();
		OTAHotelResRS otaHotelResRS = apiHotelBook.getPreBookRes();
		BasicPropertyInfoType tbasic = apiHotelBook.getRoomsummary().getBasicPropertyInfo();
		int noofdays = CommonUtil.getNoofStayDays(apiHotelBook.getSearch());
		int noofguests =  CommonUtil.getGuestTotalCount(apiHotelBook.getSearch());
		//int totalmarkupmultiplier = noofdays * noofguests;
		//int markupMultiplier = noofguests * noofdays * apiHotelBook.getSearch().getNoofrooms();
		int markupMultiplier = apiHotelBook.getSearch().getNoofrooms() * noofdays ;
		int markupDivider = 1;


		String decryptrdAppKey = AppControllerUtil.getDecryptedAppKey(CDAO, apiHotelBook.getSearch().getApikey());
		CompanyConfig companyConfig = null;
		String configId = decryptrdAppKey.substring(0,decryptrdAppKey.indexOf("-"));
		try {
			companyConfig = companyConfigDAO.getCompanyConfigByConfigId(Integer.parseInt(configId));
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		Company company = null;
		String companyId = decryptrdAppKey.substring(decryptrdAppKey.indexOf("-") + 1);
		try{
			company = CDAO.getCompany(Integer.parseInt(companyId));
		}catch(Exception e){
			e.printStackTrace();
		}
		Company parentCompany = null;
		try{
			parentCompany = CDAO.getParentCompany(company);
		}catch(Exception e){
			e.printStackTrace();
		}

		String apiCurrency = tbasic.getApiCurrency();
		String baseCurrency = tbasic.getBaseCurrency();
		String bookingCurrency = tbasic.getBookingCurrency();
		BigDecimal exRateApiToBase  = tbasic.getExRateApiToBase();
		BigDecimal exRateBaseToBooking = tbasic.getExRateBaseToBooking();

		BigDecimal apiFinalPrice = otaHotelResRS.getApiFinalPrice();
		BigDecimal baseFinalPrice = otaHotelResRS.getBaseFinalPrice().multiply(exRateApiToBase);
		BigDecimal baseFinalPriceWithoutMarkup = otaHotelResRS.getBaseFinalPriceWithoutMarkup().multiply(exRateApiToBase);

     	BigDecimal bookingFinalPrice = AmountRoundingModeUtil.roundingModeForHotel(baseFinalPrice.multiply(exRateBaseToBooking));
		BigDecimal bookingPayablePrice = AmountRoundingModeUtil.roundingModeForHotel(otaHotelResRS.getBookingPayablePrice().multiply(exRateBaseToBooking));

		Map<String, BigDecimal> markupmap = new HashMap<String, BigDecimal>();
		Map<String, CommissionDetails> commissionmap = new HashMap<String, CommissionDetails>();
		BigDecimal totalBasePriceBeforeTax = apiFinalPrice.subtract(apiHotelBook.getApiRate().getRoomrateTax());
		logger.info("totalBasePriceBeforeTax-------base ----:"+ totalBasePriceBeforeTax);
		BigDecimal oneDayDiscount = AmountRoundingModeUtil.roundingModeForHotel(apiHotelBook.getApiRate().getRoomrateDiscount().divide(new BigDecimal(apiHotelBook.getApiRate().getNoofdays())));
		logger.info("oneDayDiscount-------base ----:"+ oneDayDiscount);
		BigDecimal oneDayTax = AmountRoundingModeUtil.roundingModeForHotel(apiHotelBook.getApiRate().getRoomrateTax().divide(new BigDecimal(apiHotelBook.getApiRate().getNoofdays())));
		logger.info("oneDayTax-------base ----:"+ oneDayTax);
		BigDecimal oneDayBasePriceBeforeDiscount = AmountRoundingModeUtil.roundingModeForHotel(totalBasePriceBeforeTax.divide(new BigDecimal(apiHotelBook.getApiRate().getNoofdays()),2,RoundingMode.UP));
		logger.info("oneDayBasePriceBeforeDiscount-------base ----:"+ oneDayBasePriceBeforeDiscount);
		BigDecimal oneDayBasePrice = AmountRoundingModeUtil.roundingModeForHotel(oneDayBasePriceBeforeDiscount.add(oneDayDiscount));

		logger.info("oneDayBasePrice-------base ----:"+ oneDayBasePrice);

		BigDecimal basetotalAmountPayableMarkUpAmount = BigDecimal.valueOf(0);
		BigDecimal commissionalAmount = baseFinalPrice;

		buildMarkupMapWithAmount(commissionList,apiHotelBook,commissionalAmount,commissionmap,basetotalAmountPayableMarkUpAmount,oneDayTax,markupDivider, markupMultiplier,markupmap);

		if(otaHotelResRS.isPriceChanged()){
			logger.info("Applying markup-------base markUpAmount----:"+ basetotalAmountPayableMarkUpAmount);
			logger.info("oneDayBasePrice----------:"+ oneDayBasePrice);

			oneDayTax = oneDayTax.add(basetotalAmountPayableMarkUpAmount);

			TotalType base = new TotalType();
			TaxesType tax = new TaxesType();
			tax.setAmount(oneDayTax);
			base.setTaxes(tax);
			base.setAmountBeforeTax(oneDayBasePrice);			

			logger.info("oneDayTax after add markup----------:"+ oneDayTax);
			oneDayBasePrice = oneDayBasePrice.add(oneDayTax);
			base.setAmountAfterTax(oneDayBasePrice);
			List<Discount> discountlist = new ArrayList<>();
			Discount discount = new Discount();
			discount.setAmountBeforeTax(oneDayDiscount);
			discountlist.add(discount);
			base.setDiscounts(discountlist);

			logger.info("oneDayBasePrice after add tax----------:"+ oneDayBasePrice);
			oneDayBasePrice = oneDayBasePrice.subtract(oneDayDiscount);
			logger.info("oneDayBasePrice after subract discount----------:"+ oneDayBasePrice);
			baseFinalPrice = oneDayBasePrice;
			baseFinalPrice = baseFinalPrice.multiply(exRateApiToBase);

			bookingPayablePrice = CommonUtil.getPayableAmount(apiHotelBook.getSearch(),companyConfig,base);
			bookingFinalPrice = AmountRoundingModeUtil.roundingModeForHotel(baseFinalPrice.multiply(exRateBaseToBooking));
		}		

		logger.info("################# prebook apiFinalPrice-"+apiFinalPrice);
		logger.info("################# prebook baseFinalPrice-"+baseFinalPrice);
		logger.info("################# prebook baseFinalPriceWithoutMarkup-"+baseFinalPriceWithoutMarkup);
		logger.info("################# prebook bookingFinalPrice-"+bookingFinalPrice);

		otaHotelResRS.setBookingPayablePrice(bookingPayablePrice);
		otaHotelResRS.setApiFinalPrice(apiFinalPrice);
		otaHotelResRS.setBaseFinalPrice(baseFinalPrice);
		otaHotelResRS.setBaseFinalPriceWithoutMarkup(baseFinalPriceWithoutMarkup);
		otaHotelResRS.setBookingFinalPrice(bookingFinalPrice);
		otaHotelResRS.setMarkupmap(markupmap);
		otaHotelResRS.setCommissionmap(commissionmap);
		apiHotelBook.setPreBookRes(otaHotelResRS);
		
		apiHotelBook.reviseRate(apiFinalPrice, baseFinalPrice, baseFinalPriceWithoutMarkup, bookingFinalPrice,bookingPayablePrice,hor);

		/// Set Service Tax component
		HotelServiceTax hotelServiceTax = null;
		HotelGstTax hotelGstTax = null;

		if(companyConfig != null){
			if(companyConfig.getCompanyConfigType().isB2E()){
				if(companyConfig.getTaxtype()!=null && companyConfig.getTaxtype().equalsIgnoreCase("GST")){
					hotelGstTax = new HotelGstTax();
					hotelGstTax = getHotelGstTax(apiHotelBook.getSearch().getCountry(), companyConfig, noofdays, 0, company, parentCompany,noofguests);
					apiHotelBook.getBookingRate().setTotalPayableAmt(apiHotelBook.getBookingRate().getPayableAmt().add(hotelGstTax.getTotalTax()).add(hotelGstTax.getManagementFee()));
				}else{
					hotelServiceTax = new HotelServiceTax();
					hotelServiceTax = getHotelServiceTaxForAllDay(apiHotelBook.getBookingRate().getPayableAmt(),apiHotelBook.getSearch().getCountry(),companyConfig,noofdays);
					//apiHotelBook.getBookingRate().setPayableAmt(apiHotelBook.getBookingRate().getPayableAmt().add(hotelServiceTax.getTotalServiceTax()).add(hotelServiceTax.getManagementFee()));
					apiHotelBook.getBookingRate().setTotalPayableAmt(apiHotelBook.getBookingRate().getPayableAmt().add(hotelServiceTax.getTotalServiceTax()).add(hotelServiceTax.getManagementFee()));
							
				}
			}
		}
		apiHotelBook.getBookingRate().setHotelGstTax(hotelGstTax);
		apiHotelBook.getBookingRate().setHotelServiceTax(hotelServiceTax);

		com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs = fillCurrencyDataOnCancellationPloicyWithMarkups(apiHotelBook.getSearch(), markupsmap, apiHotelBook.getRoomsummary());
		apiHotelBook.setRoomsummary(rs);


		return apiHotelBook;
	}

	private void buildMarkupMapWithAmount(List<Entry<String, CommissionDetails>> commissionList, APIHotelBook apiHotelBook, BigDecimal commissionalAmount, Map<String, CommissionDetails> commissionmap, BigDecimal basetotalAmountPayableMarkUpAmount, BigDecimal oneDayTax, int markupDivider, int markupMultiplier, Map<String, BigDecimal> markupmap) throws HibernateException, IOException {
		BigDecimal markupvalue = new BigDecimal(0); 
		for (Entry<String, CommissionDetails> commissionEntry : commissionList) {
			CommissionDetails agentcommission = commissionEntry.getValue();
			String companyId = agentcommission.getCompanyId();
			List<HotelMarkup> markups = apiHotelBook.getSearch().getHotelMarkupCommissionDetails().getMarkups().get(agentcommission.getCompanyId());
  
			//redo commission details..
			//agentcommission.se
			BigDecimal compnaycommissionAMount = new BigDecimal("0.00");
			if(agentcommission.getRateType().equalsIgnoreCase("Commission")){
				if(agentcommission.getCommissionType().equalsIgnoreCase("Fixed")){
					compnaycommissionAMount = agentcommission.getCommissionAmount();
				}else{
					compnaycommissionAMount = commissionalAmount
							.multiply(agentcommission.getCommissionAmount()).divide(new BigDecimal("100"));
				}
			}
			agentcommission.setCommissionCalculatedAmount(compnaycommissionAMount);
			commissionmap.put(companyId, agentcommission);
			BigDecimal agentmarkup = BigDecimal.valueOf(0);
			int markupindex = 0;
			for (HotelMarkup hotelMarkup : markups) {
				boolean isappliable = HotelMarkUpUtil.isMarkUpApplicable(apiHotelBook.getSearch(), hotelMarkup, apiHotelBook.getRoomsummary());
				logger.info(hotelMarkup.getName()+"\\\\\\\\markup--isappliable-----"+isappliable+"----for ----:"+ apiHotelBook.getRoomsummary().getBasicPropertyInfo().getHotelName());
				logger.info(hotelMarkup.getName()+"\\\\\\\\markup--getIsaccumulative----"+hotelMarkup.getIsaccumulative()+"----");
				logger.info(hotelMarkup.getName()+"\\\\\\\\markup--getIsfixedAmount----"+hotelMarkup.getIsfixedAmount()+"----");
				logger.info(hotelMarkup.getName()+"\\\\\\\\markup--getIsaccumulative----"+hotelMarkup.getIsaccumulative()+"----");
				BigDecimal markUpAmountTaxPrice = BigDecimal.valueOf(0);
				BigDecimal markUpAmountbasePrice = BigDecimal.valueOf(0);
				BigDecimal markUpAmountATax = BigDecimal.valueOf(0);
				BigDecimal markUpAmountPayable = BigDecimal.valueOf(0);
				if(isappliable)
				{
					if(markupindex == 0 && (hotelMarkup.getIsaccumulative()==0))
					{
						//Apply Markup On Tax
						markUpAmountTaxPrice = HotelMarkUpUtil.applyMarkUp(hotelMarkup, oneDayTax, markupDivider, markupMultiplier);
						//Apply Markup On Base
						//markUpAmountbasePrice = HotelMarkUpUtil.applyMarkUp(hotelMarkup, oneDayBasePrice, markupDivider, markupMultiplier);
						agentmarkup = agentmarkup.add(markUpAmountTaxPrice);
						//Adding markup to total markup
						basetotalAmountPayableMarkUpAmount = basetotalAmountPayableMarkUpAmount.add(markUpAmountTaxPrice);
						break;
					}
					else if(markupindex != 0 && (hotelMarkup.getIsaccumulative()==0))
					{
						continue;
					}
					else if(hotelMarkup.getIsaccumulative()==1)
					{
						//Apply Markup On Tax
						markUpAmountTaxPrice = HotelMarkUpUtil.applyMarkUp(hotelMarkup, oneDayTax, markupDivider, markupMultiplier);
						//Apply Markup On Base
						//markUpAmountbasePrice = HotelMarkUpUtil.applyMarkUp(hotelMarkup, oneDayBasePrice, markupDivider, markupMultiplier);
						agentmarkup = agentmarkup.add(markUpAmountTaxPrice);
						//Adding markup to total markup
						basetotalAmountPayableMarkUpAmount = basetotalAmountPayableMarkUpAmount.add(markUpAmountTaxPrice);
					}
				}
				markupindex++;
			}
			commissionalAmount = commissionalAmount.add(agentmarkup);
			//company markup amount
			markupmap.put(companyId, agentmarkup);
			markupvalue = markupvalue.add(basetotalAmountPayableMarkUpAmount);
			basetotalAmountPayableMarkUpAmount = markupvalue;
		}
	}

	public APIHotelBook fillCurrencyOnBookResponse(APIHotelBook apiHotelBook,HotelOrderRow hor)
	{
		try{
			List<Entry<String, CommissionDetails>> commissionList = new LinkedList<Entry<String, CommissionDetails>>(apiHotelBook.getSearch().getHotelMarkupCommissionDetails().getCommissionDetailsMap().entrySet());
			// Sorting the list based on values
			Collections.sort(commissionList, new Comparator<Entry<String, CommissionDetails>>() {
				@Override
				public int compare(Entry<String, CommissionDetails> c1, Entry<String, CommissionDetails> c2) {
					Integer companyid1 = Integer.valueOf(c1.getValue().getCompanyId());
					Integer companyid2 = Integer.valueOf(c2.getValue().getCompanyId());
					return companyid1.compareTo(companyid2);
				}
			});
			String decryptrdAppKey = AppControllerUtil.getDecryptedAppKey(CDAO, apiHotelBook.getSearch().getApikey());
			CompanyConfig companyConfig = null;
			String configId = decryptrdAppKey.substring(0,decryptrdAppKey.indexOf("-"));
			try {
				companyConfig = companyConfigDAO.getCompanyConfigByConfigId(Integer.parseInt(configId));
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			Company company = null;
			String companyId = decryptrdAppKey.substring(decryptrdAppKey.indexOf("-") + 1);
			try{
				company = CDAO.getCompany(Integer.parseInt(companyId));
			}catch(Exception e){
				e.printStackTrace();
			}
			Company parentCompany = null;
			try{
				parentCompany = CDAO.getParentCompany(company);
			}catch(Exception e){
				e.printStackTrace();
			}

			Map<String, BigDecimal> markupmap = new HashMap<String, BigDecimal>();
			Map<String, CommissionDetails> commissionmap = new HashMap<String, CommissionDetails>();


			Map<String,List<HotelMarkup>> markupsmap = apiHotelBook.getSearch().getHotelMarkupCommissionDetails().getMarkups();

			OTAHotelResRS otaHotelResRS = apiHotelBook.getBookRes();
			BasicPropertyInfoType tbasic = apiHotelBook.getRoomsummary().getBasicPropertyInfo();
			int noofdays = CommonUtil.getNoofStayDays(apiHotelBook.getSearch());
			int noofguests =  CommonUtil.getGuestTotalCount(apiHotelBook.getSearch());
			int markupMultiplier = apiHotelBook.getSearch().getNoofrooms() * noofdays ;
			int markupDivider = 1;

			String apiCurrency = tbasic.getApiCurrency();
			String baseCurrency = tbasic.getBaseCurrency();
			String bookingCurrency = tbasic.getBookingCurrency();
			BigDecimal exRateApiToBase  = tbasic.getExRateApiToBase();
			BigDecimal exRateBaseToBooking = tbasic.getExRateBaseToBooking();

			BigDecimal apiFinalPrice = otaHotelResRS.getApiFinalPrice();
			BigDecimal baseFinalPrice = otaHotelResRS.getBaseFinalPrice().multiply(exRateApiToBase);
			BigDecimal baseFinalPriceWithoutMarkup = otaHotelResRS.getBaseFinalPriceWithoutMarkup().multiply(exRateApiToBase);
			BigDecimal bookingPayablePrice = AmountRoundingModeUtil.roundingModeForHotel(baseFinalPrice.multiply(exRateBaseToBooking));
			BigDecimal bookingFinalPrice =  AmountRoundingModeUtil.roundingModeForHotel(otaHotelResRS.getBookingFinalPrice().multiply(exRateBaseToBooking));

			logger.info("apiFinalPrice-------api ----:"+ apiFinalPrice);
			logger.info("apiHotelBook.getApiRate().getRoomrateDiscount()-------api ----:"+ apiHotelBook.getApiRate().getRoomrateDiscount());
			logger.info("apiHotelBook.getApiRate().getRoomrateTax()-------api ----:"+ apiHotelBook.getApiRate().getRoomrateTax());

			BigDecimal totalBasePriceBeforeTax = AmountRoundingModeUtil.roundingModeForHotel(apiFinalPrice.subtract(apiHotelBook.getApiRate().getRoomrateTax()));
			logger.info("totalBasePriceBeforeTax-------base ----:"+ totalBasePriceBeforeTax);
			BigDecimal oneDayDiscount = AmountRoundingModeUtil.roundingModeForHotel(apiHotelBook.getApiRate().getRoomrateDiscount().divide(new BigDecimal(apiHotelBook.getApiRate().getNoofdays())));
			logger.info("oneDayDiscount-------base ----:"+ oneDayDiscount);
			BigDecimal oneDayTax = AmountRoundingModeUtil.roundingModeForHotel(apiHotelBook.getApiRate().getRoomrateTax().divide(new BigDecimal(apiHotelBook.getApiRate().getNoofdays())));
			logger.info("oneDayTax-------base ----:"+ oneDayTax);
			BigDecimal oneDayBasePriceBeforeDiscount = AmountRoundingModeUtil.roundingModeForHotel(totalBasePriceBeforeTax.divide(new BigDecimal(apiHotelBook.getApiRate().getNoofdays()),2,RoundingMode.UP));
			logger.info("oneDayBasePriceBeforeDiscount-------base ----:"+ oneDayBasePriceBeforeDiscount);
			BigDecimal oneDayBasePrice = AmountRoundingModeUtil.roundingModeForHotel(oneDayBasePriceBeforeDiscount.add(oneDayDiscount));
			logger.info("oneDayBasePrice-------base ----:"+ oneDayBasePrice);

			BigDecimal basetotalAmountPayableMarkUpAmount = BigDecimal.valueOf(0);
			BigDecimal commissionalAmount = baseFinalPrice;

			buildMarkupMapWithAmount(commissionList,apiHotelBook,commissionalAmount,commissionmap,basetotalAmountPayableMarkUpAmount,oneDayTax,markupDivider, markupMultiplier,markupmap);

			if(otaHotelResRS.isPriceChanged()){	
				logger.info("Applying markup-------base markUpAmount----:"+ basetotalAmountPayableMarkUpAmount);
				logger.info("oneDayBasePrice----------:"+ oneDayBasePrice);

				oneDayTax = oneDayTax.add(basetotalAmountPayableMarkUpAmount);

				oneDayBasePrice = oneDayBasePrice.add(basetotalAmountPayableMarkUpAmount);
				logger.info("oneDayBasePrice after add markup----------:"+ oneDayBasePrice);
				TotalType base = new TotalType();
				TaxesType tax = new TaxesType();
				tax.setAmount(oneDayTax);
				base.setTaxes(tax);
				base.setAmountBeforeTax(oneDayBasePrice);

				oneDayBasePrice = oneDayBasePrice.add(oneDayTax);
				base.setAmountAfterTax(oneDayBasePrice);
				List<Discount> discountlist = new ArrayList<>();
				Discount discount = new Discount();
				discount.setAmountBeforeTax(oneDayDiscount);
				discountlist.add(discount);
				base.setDiscounts(discountlist);

				logger.info("oneDayBasePrice after add tax----------:"+ oneDayBasePrice);
				oneDayBasePrice = oneDayBasePrice.subtract(oneDayDiscount);
				logger.info("oneDayBasePrice after subract discount----------:"+ oneDayBasePrice);
				baseFinalPrice = oneDayBasePrice.multiply(new BigDecimal(apiHotelBook.getApiRate().getNoofdays()));
				baseFinalPrice = baseFinalPrice.multiply(exRateApiToBase);

				bookingPayablePrice = CommonUtil.getPayableAmount(apiHotelBook.getSearch(),companyConfig,base);
				bookingFinalPrice = AmountRoundingModeUtil.roundingModeForHotel(baseFinalPrice.multiply(exRateBaseToBooking));

			}
			logger.info("################# prebook apiFinalPrice-"+apiFinalPrice);
			logger.info("################# prebook baseFinalPrice-"+baseFinalPrice);
			logger.info("################# prebook baseFinalPriceWithoutMarkup-"+baseFinalPriceWithoutMarkup);
			logger.info("################# prebook bookingFinalPrice-"+bookingFinalPrice);
			otaHotelResRS.setBookingPayablePrice(bookingPayablePrice);
			otaHotelResRS.setApiFinalPrice(apiFinalPrice);
			otaHotelResRS.setBaseFinalPrice(baseFinalPrice);
			otaHotelResRS.setBaseFinalPriceWithoutMarkup(baseFinalPriceWithoutMarkup);
			otaHotelResRS.setBookingFinalPrice(bookingFinalPrice);
			otaHotelResRS.setMarkupmap(markupmap);
			otaHotelResRS.setCommissionmap(commissionmap);

			/// Set Service Tax component
			HotelServiceTax hotelServiceTax = null;
			HotelGstTax hotelGstTax = null;
			if(companyConfig != null){
				if(companyConfig.getCompanyConfigType().isB2E()){
					if(companyConfig.getTaxtype()!=null && companyConfig.getTaxtype().equalsIgnoreCase("GST")){
						hotelGstTax = new HotelGstTax();
						hotelGstTax = getHotelGstTax(apiHotelBook.getSearch().getCountry(), companyConfig, noofdays, 0, company, parentCompany,noofguests);
						
					}else{
					hotelServiceTax = new HotelServiceTax();
					hotelServiceTax = getHotelServiceTaxForAllDay(apiHotelBook.getBookingRate().getPayableAmt(),apiHotelBook.getSearch().getCountry(),companyConfig,noofdays);
					}
				}

			}
			apiHotelBook.getBookingRate().setHotelGstTax(hotelGstTax);
			apiHotelBook.getBookingRate().setHotelServiceTax(hotelServiceTax);
			apiHotelBook.setBookRes(otaHotelResRS);

			apiHotelBook.reviseRate(apiFinalPrice, baseFinalPrice, baseFinalPriceWithoutMarkup, bookingFinalPrice,bookingPayablePrice,hor);

			com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs = fillCurrencyDataOnCancellationPloicyWithMarkups(apiHotelBook.getSearch(), markupsmap, apiHotelBook.getRoomsummary());
			apiHotelBook.setRoomsummary(rs);
			
		}catch(ArithmeticException e){

			APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, e.getMessage() );
			apiHotelBook.setStatus(status);

		}catch (Exception e) {
			APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, e.getMessage() );
			apiHotelBook.setStatus(status);
		}
		return apiHotelBook;
	}


	public APIHotelCancelResponse fillCurrencyOnCancelResponse(APIHotelCancelResponse apiHotelCancelResponse, HotelOrderRow hotelOrderRow)throws HibernateException, IOException, ParseException
	{
		String apiCurrency = hotelOrderRow.getApiCurrency();
		String baseCurrency = hotelOrderRow.getBaseCurrency();
		String bookingCurrency = hotelOrderRow.getBookedCurrency();
		BigDecimal exRateApiToBase  = hotelOrderRow.getApiToBaseExchangeRate();
		BigDecimal exRateBaseToBooking = hotelOrderRow.getBaseToBookingExchangeRate();


		if(apiHotelCancelResponse != null && apiHotelCancelResponse.getApiCancelRule() != null)
		{
			CancelRuleType apiCancelRuleType = apiHotelCancelResponse.getApiCancelRule();
			CancelRuleType cancelRuleType = apiHotelCancelResponse.getCancelRule();
			if(apiCancelRuleType != null && apiCancelRuleType.getAmount()!= null)
			{
				BigDecimal apiAmount = apiCancelRuleType.getAmount();
				BigDecimal baseAmount = apiAmount;
				BigDecimal baseAmountWithoutMarkup = apiAmount;
				BigDecimal bookingAmount = apiAmount;
				BigDecimal apiAmountRefund = apiCancelRuleType.getRefundAmount();
				BigDecimal baseAmountRefund = apiAmount;
				BigDecimal baseAmountWithoutMarkupRefund = apiAmount;
				BigDecimal bookingAmountRefund = apiAmount;

				baseAmount = apiAmount.multiply(exRateApiToBase);
				baseAmountWithoutMarkup = apiAmount.multiply(exRateApiToBase);
				bookingAmount = baseAmount.multiply(exRateBaseToBooking);

				baseAmountRefund = apiAmountRefund.multiply(exRateApiToBase);
				baseAmountWithoutMarkupRefund = apiAmountRefund.multiply(exRateApiToBase);
				bookingAmountRefund = baseAmountRefund.multiply(exRateBaseToBooking);

				if(cancelRuleType == null)
					cancelRuleType = apiCancelRuleType;
				cancelRuleType.setCurrencyCode(bookingCurrency);
				cancelRuleType.setAmount(bookingAmount);
				cancelRuleType.setRefundAmount(bookingAmountRefund);

				apiHotelCancelResponse.setCancelRule(cancelRuleType);
			}

		}

		return apiHotelCancelResponse;
	}

	public com.tayyarah.hotel.model.OTAHotelResRS fillCurrencyDataOnPreBookResponseFinalPrices(com.tayyarah.hotel.model.OTAHotelResRS totaHotelResRS,APIHotelBook apiHotelBook){

		BigDecimal apiRoomrateBeforeTax = apiHotelBook.getApiRate().getPayableAmt().subtract(apiHotelBook.getApiRate().getRoomrateTax());
		BigDecimal apiDiscountPerDay = apiHotelBook.getApiRate().getRoomrateDiscount().divide(new BigDecimal(apiHotelBook.getApiRate().getNoofdays()));
		BigDecimal apiBasePriceAfterDiscount = apiRoomrateBeforeTax.divide(new BigDecimal(apiHotelBook.getApiRate().getNoofdays()));
		BigDecimal apiBasePrice = apiBasePriceAfterDiscount.add(apiDiscountPerDay);


		List<Entry<String, CommissionDetails>> commissionList = new LinkedList<Entry<String, CommissionDetails>>(apiHotelBook.getSearch().getHotelMarkupCommissionDetails().getCommissionDetailsMap().entrySet());
		// Sorting the list based on values
		Collections.sort(commissionList, new Comparator<Entry<String, CommissionDetails>>() {
			@Override
			public int compare(Entry<String, CommissionDetails> c1, Entry<String, CommissionDetails> c2) {
				Integer companyid1 = Integer.valueOf(c1.getValue().getCompanyId());
				Integer companyid2 = Integer.valueOf(c2.getValue().getCompanyId());
				return companyid1.compareTo(companyid2);
			}
		});
		Map<String, BigDecimal> markupmap = new HashMap<String, BigDecimal>();
		Map<String, CommissionDetails> commissionmap = new HashMap<String, CommissionDetails>();
		com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs = apiHotelBook.getRoomsummary();
		BasicPropertyInfoType tbasic = rs.getBasicPropertyInfo();
		return totaHotelResRS;
	}

	public RoomStay fillCurrencyDataOnRoomDetailWithMarkups(HotelSearchCommand hs, Map<String,List<HotelMarkup>> markupsmap, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs,Company company,Company parentCompany)throws HibernateException, IOException, ParseException
	{

		List<Entry<String, CommissionDetails>> commissionList = new LinkedList<Entry<String, CommissionDetails>>(hs.getHotelMarkupCommissionDetails().getCommissionDetailsMap().entrySet());
		// Sorting the list based on values
		Collections.sort(commissionList, new Comparator<Entry<String, CommissionDetails>>() {
			@Override
			public int compare(Entry<String, CommissionDetails> c1, Entry<String, CommissionDetails> c2) {
				Integer companyid1 = Integer.valueOf(c1.getValue().getCompanyId());
				Integer companyid2 = Integer.valueOf(c2.getValue().getCompanyId());
				return companyid1.compareTo(companyid2);
			}
		});
		String decryptrdAppKey = AppControllerUtil.getDecryptedAppKey(CDAO, hs.getApikey());
		CompanyConfig companyConfig = null;
		String configId = decryptrdAppKey.substring(0,decryptrdAppKey.indexOf("-"));
		try {
			companyConfig = companyConfigDAO.getCompanyConfigByConfigId(Integer.parseInt(configId));
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		Map<String, BigDecimal> markupmap = new HashMap<String, BigDecimal>();
		Map<String, CommissionDetails> commissionmap = new HashMap<String, CommissionDetails>();

		BasicPropertyInfoType tbasic = rs.getBasicPropertyInfo();
		int noofdays = CommonUtil.getNoofStayDays(hs);
		int noofguests =  CommonUtil.getGuestTotalCount(hs);
		int totalmarkupmultiplier = noofdays * hs.getNoofrooms();
		int totalmarkupdivider = 1;

		String apiCurrency = tbasic.getApiCurrency();
		String baseCurrency = tbasic.getBaseCurrency();
		String bookingCurrency = tbasic.getBookingCurrency();
		BigDecimal exRateApiToBase  = tbasic.getExRateApiToBase();
		BigDecimal exRateBaseToBooking = tbasic.getExRateBaseToBooking();

		RoomRates troomrates = rs.getRoomRates();
		List<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate> lrratelist = new ArrayList<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate>();
		for (RoomRate rr : rs.getRoomRates().getRoomRates()) {
			RoomRate lrr = rr;
			//Rates
			RateType lrates = new RateType();
			List<Rate> lratelist = new ArrayList<Rate>();

			for (Rate rtype : rr.getRates().getRates()) {
				Rate lrate = rtype;
				TotalType base = rtype.getBase();
				TotalType apiPrice = rtype.getApiPrice();
				TotalType bookingPrice = rtype.getBookingPrice();
				TotalType baseWithoutMarkUp = rtype.getBaseWithoutMarkUp();

				BigDecimal apiamountBTax = apiPrice.getAmountBeforeTax();
				BigDecimal apiamountATax = apiPrice.getAmountAfterTax();
				BigDecimal apitotalAmountPayable = apiPrice.getTotalAmountPayable();
				BigDecimal apiTax = apiPrice.getTaxes().getAmount();

				BigDecimal apiDiscount = new BigDecimal("0");
				for (Discount discount : rtype.getApiPrice().getDiscounts()) {
					apiDiscount = (discount.getAmountBeforeTax() == null)?new BigDecimal("0"):discount.getAmountBeforeTax();
				}
				BigDecimal baseamountBTax = apiamountBTax.multiply(exRateApiToBase);
				BigDecimal baseamountATax = apiamountATax.multiply(exRateApiToBase);
				BigDecimal basetotalAmountPayable = apitotalAmountPayable.multiply(exRateApiToBase);

				baseWithoutMarkUp.setAmountBeforeTax(apiamountBTax.multiply(exRateApiToBase));
				baseWithoutMarkUp.setAmountAfterTax(apiamountATax.multiply(exRateApiToBase));
				baseWithoutMarkUp.setTotalAmountPayable(apitotalAmountPayable.multiply(exRateApiToBase));
				baseWithoutMarkUp.setPayableAmount(apitotalAmountPayable.multiply(exRateApiToBase));

				BigDecimal baseTax = apiTax.multiply(exRateApiToBase);
				BigDecimal baseTaxMarkUpAmount = BigDecimal.valueOf(0);

				BigDecimal baseamountBTaxMarkUpAmount = BigDecimal.valueOf(0);
				BigDecimal baseamountATaxMarkUpAmount = BigDecimal.valueOf(0);
				BigDecimal basetotalAmountPayableMarkUpAmount = BigDecimal.valueOf(0);

				logger.info("\\\\\\\\************************* applying markup before--baseamountBTax----"+baseamountATax+"----");
				logger.info("\\\\\\\\************************* applying markup--rateplan code----"+rr.getRatePlanCode()+"----");
				logger.info("\\\\\\\\************************* applying markup--rateplan code----baseamountBTax before markup "+baseamountBTax+"----");
				logger.info("\\\\\\\\************************* applying markup--rateplan code----baseTax before markup "+baseTax+"----");

				BigDecimal commissionalAmount = basetotalAmountPayable;
				for (Entry<String, CommissionDetails> commissionEntry : commissionList) {

					CommissionDetails agentcommission = commissionEntry.getValue();
					String companyId = agentcommission.getCompanyId();
					List<HotelMarkup> markups = hs.getHotelMarkupCommissionDetails().getMarkups().get(agentcommission.getCompanyId());

					logger.info("\\\\\\\\*************************  company id---"+agentcommission.getCompanyId());
					logger.info("\\\\\\\\*************************  company id---"+agentcommission.getCompanyId()+"---markups size--"+markups.size());
					logger.info("\\\\\\\\*************************  company id---"+agentcommission.getCompanyId()+"---commissionalAmount--"+commissionalAmount);

					//redo commission details..
					//agentcommission.se
					BigDecimal compnaycommissionAMount = new BigDecimal("0.00");
					if(agentcommission.getRateType().equalsIgnoreCase("Commission")){
						if(agentcommission.getCommissionType().equalsIgnoreCase("Fixed")){
							compnaycommissionAMount = agentcommission.getCommissionAmount();
						}else{
							compnaycommissionAMount = commissionalAmount
									.multiply(agentcommission.getCommissionAmount()).divide(new BigDecimal("100"));
						}
					}
					agentcommission.setCommissionCalculatedAmount(compnaycommissionAMount);
					commissionmap.put(companyId, agentcommission);
					logger.info("\\\\\\\\************************* commission for company id---"+companyId+"---is-"+compnaycommissionAMount);

					BigDecimal agentmarkup = BigDecimal.valueOf(0);
					int markupindex = 0;
					for (HotelMarkup hotelMarkup : markups) {

						boolean isappliable = HotelMarkUpUtil.isMarkUpApplicable(hs, hotelMarkup, rs);

						logger.info(hotelMarkup.getName()+"\\\\\\\\markup--isappliable-----"+isappliable+"----for ----:"+ rs.getBasicPropertyInfo().getHotelName());
						logger.info(hotelMarkup.getName()+"\\\\\\\\markup--getIsaccumulative----"+hotelMarkup.getIsaccumulative()+"----");
						logger.info(hotelMarkup.getName()+"\\\\\\\\markup--getIsfixedAmount----"+hotelMarkup.getIsfixedAmount()+"----");
						logger.info(hotelMarkup.getName()+"\\\\\\\\markup--getIsaccumulative----"+hotelMarkup.getIsaccumulative()+"----");
						BigDecimal markUpAmountOnTax = BigDecimal.valueOf(0);
						BigDecimal markUpAmountBTax = BigDecimal.valueOf(0);
						BigDecimal markUpAmountATax = BigDecimal.valueOf(0);
						BigDecimal markUpAmountPayable = BigDecimal.valueOf(0);
						if(isappliable)
						{
							if(markupindex == 0 && (hotelMarkup.getIsaccumulative()==0))
							{
								// Apply markup on Tax
								markUpAmountOnTax = HotelMarkUpUtil.applyMarkUp(hotelMarkup, baseTax,totalmarkupdivider, totalmarkupmultiplier);

								/// Apply Markup On Base
								//markUpAmountBTax = HotelMarkUpUtil.applyMarkUp(hotelMarkup, baseamountBTax,totalmarkupdivider, totalmarkupmultiplier);
								//markUpAmountATax = HotelMarkUpUtil.applyMarkUp(hotelMarkup, baseamountATax,totalmarkupdivider, totalmarkupmultiplier);
								markUpAmountPayable = HotelMarkUpUtil.applyMarkUp(hotelMarkup, basetotalAmountPayable,totalmarkupdivider, totalmarkupmultiplier);

								agentmarkup = agentmarkup.add(markUpAmountOnTax);
								//Adding markup to total markup
								baseTaxMarkUpAmount = baseTaxMarkUpAmount.add(markUpAmountOnTax);
								//baseamountBTaxMarkUpAmount = baseamountBTaxMarkUpAmount.add(markUpAmountBTax);
								//baseamountATaxMarkUpAmount = baseamountATaxMarkUpAmount.add(markUpAmountATax);
								basetotalAmountPayableMarkUpAmount = basetotalAmountPayableMarkUpAmount.add(markUpAmountPayable);
								break;
							}
							else if(markupindex != 0 && (hotelMarkup.getIsaccumulative()==0))
							{
								continue;
							}
							else if(hotelMarkup.getIsaccumulative()==1)
							{
								// Apply markup on Tax
								markUpAmountOnTax = HotelMarkUpUtil.applyMarkUp(hotelMarkup, baseTax,totalmarkupdivider, totalmarkupmultiplier);
								/// Apply Markup On Base
								//markUpAmountBTax = HotelMarkUpUtil.applyMarkUp(hotelMarkup, baseamountBTax, totalmarkupdivider,totalmarkupmultiplier);
								//markUpAmountATax = HotelMarkUpUtil.applyMarkUp(hotelMarkup, baseamountATax,totalmarkupdivider, totalmarkupmultiplier);
								markUpAmountPayable = HotelMarkUpUtil.applyMarkUp(hotelMarkup, basetotalAmountPayable,totalmarkupdivider, totalmarkupmultiplier);

								agentmarkup = agentmarkup.add(markUpAmountOnTax);
								//Adding markup to total markup
								baseTaxMarkUpAmount = baseTaxMarkUpAmount.add(markUpAmountOnTax);
								//baseamountBTaxMarkUpAmount = baseamountBTaxMarkUpAmount.add(markUpAmountBTax);
								//baseamountATaxMarkUpAmount = baseamountATaxMarkUpAmount.add(markUpAmountATax);
								basetotalAmountPayableMarkUpAmount = basetotalAmountPayableMarkUpAmount.add(markUpAmountPayable);
							}
						}
						markupindex++;
					}
					commissionalAmount = commissionalAmount.add(agentmarkup);
					//company markup amount
					logger.info("\\\\\\\\************************* markup for company id---"+companyId+"---is-"+agentmarkup);

					//redo commission details..
					//agentcommission.se
					//agentmarkup = agentmarkup.multiply(new BigDecimal(totalmarkupmultiplier));
					markupmap.put(companyId, agentmarkup);
					logger.info("\\\\\\\\************************* markup for company id---"+companyId+"---is-"+agentmarkup);
				}
				
				logger.info("\\\\\\\\************************* applying markup--baseTaxMarkUpAmount----"+baseTaxMarkUpAmount+"----");
				logger.info("\\\\\\\\************************* applying markup--baseamountBTaxMarkUpAmount----"+baseamountBTaxMarkUpAmount+"----");
				logger.info("\\\\\\\\************************* applying markup--baseamountBTax----"+baseamountBTax+"----");
				logger.info("\\\\\\\\************************* applying markup--baseamountATax----"+baseamountATax+"----");

				baseTax = AmountRoundingModeUtil.roundingModeForHotel(baseTax.add(baseTaxMarkUpAmount));
				baseamountBTax = AmountRoundingModeUtil.roundingModeForHotel(baseamountBTax.add(baseamountBTaxMarkUpAmount));
				// Remove API Tax from BaseAmount After Tax
				baseamountATax = AmountRoundingModeUtil.roundingModeForHotel(baseamountATax.subtract(apiTax));
				// Add New Tax with MarkUp in AmountAfter Rax
				baseamountATax = AmountRoundingModeUtil.roundingModeForHotel(baseamountATax.add(baseTax));
				// Markup apply on Base fare
				//baseamountATax = AmountRoundingModeUtil.roundingModeForHotel(baseamountATax.add(baseamountATaxMarkUpAmount));

				base.setAmountBeforeTax(baseamountBTax);

				basetotalAmountPayable =  (baseamountBTax.subtract(apiDiscount));
				base.setTotalRoomPrice(AmountRoundingModeUtil.roundingModeForHotel(basetotalAmountPayable));

				logger.info("\\\\\\\\************************* applying markup--rateplan code----baseamountBTax after markup"+baseamountBTax+"----");

				base.setAmountAfterTax(baseamountATax.subtract(apiDiscount));
				base.setTotalAmountPayable(AmountRoundingModeUtil.roundingModeForHotel((baseamountATax.subtract(apiDiscount))));

				bookingPrice.setAmountBeforeTax(baseamountBTax.multiply(exRateBaseToBooking));
				bookingPrice.setAmountAfterTax(baseamountATax.multiply(exRateBaseToBooking));
				bookingPrice.setTotalAmountPayable(AmountRoundingModeUtil.roundingModeForHotel(base.getTotalAmountPayable().multiply(exRateBaseToBooking)));
				bookingPrice.setTotalRoomPrice(AmountRoundingModeUtil.roundingModeForHotel(base.getTotalRoomPrice().multiply(exRateBaseToBooking)));

				if(rtype.getApiPrice().getTaxes()!=null && rtype.getApiPrice().getTaxes().getAmount() !=null)
				{
					TaxesType ltaxapi = new TaxesType();
					TaxesType ltaxbase = new TaxesType();
					TaxesType ltaxbasewithoutmarkup = new TaxesType();
					TaxesType ltaxbooking = new TaxesType();

					List<TaxType> ltaxlistapi  = new ArrayList<TaxType>();
					List<TaxType> ltaxlistbase  = new ArrayList<TaxType>();
					List<TaxType> ltaxlistbasewithoutmarkup  = new ArrayList<TaxType>();
					List<TaxType> ltaxlistbooking  = new ArrayList<TaxType>();

					for (TaxType rtt :rtype.getApiPrice().getTaxes().getTaxes()) {
						TaxType lrttapi = new TaxType();
						TaxType lrttbase = new TaxType();
						TaxType lrttbasewithoutmarkup = new TaxType();
						TaxType lrttbooking = new TaxType();
						
						//Without Markup
						BigDecimal apitaxamountapi = (rtt.getAmount() == null)?new BigDecimal("0"):rtt.getAmount();
						lrttapi.setAmount(AmountRoundingModeUtil.roundingModeForHotel(apitaxamountapi));
						lrttapi.setCode(rtt.getCode());

						//With Markup on Tax
						BigDecimal taxamountapi = baseTax;
						lrttbase.setAmount(AmountRoundingModeUtil.roundingModeForHotel(taxamountapi.multiply(exRateApiToBase)));
						lrttbase.setCode(rtt.getCode());

						lrttbasewithoutmarkup.setAmount(AmountRoundingModeUtil.roundingModeForHotel(apitaxamountapi.multiply(exRateApiToBase)));
						lrttbasewithoutmarkup.setCode(rtt.getCode());

						lrttbooking.setAmount(AmountRoundingModeUtil.roundingModeForHotel(taxamountapi.multiply(exRateApiToBase).multiply(exRateBaseToBooking)));
						lrttbooking.setCode(rtt.getCode());

						ltaxlistapi.add(lrttapi);
						ltaxlistbase.add(lrttbase);
						ltaxlistbasewithoutmarkup.add(lrttbasewithoutmarkup);
						ltaxlistbooking.add(lrttbooking);
					}
					//Without Markup
					BigDecimal apitaxamountapitotal = (rtype.getApiPrice().getTaxes().getAmount() == null)?new BigDecimal("0"):rtype.getApiPrice().getTaxes().getAmount();

					ltaxapi.setAmount(AmountRoundingModeUtil.roundingModeForHotel(apitaxamountapitotal));
					ltaxapi.setTaxes(ltaxlistapi);

					//With Markup on Tax
					BigDecimal taxamountapitotal = baseTax;
					ltaxbase.setAmount(AmountRoundingModeUtil.roundingModeForHotel(taxamountapitotal.multiply(exRateApiToBase)));
					ltaxbase.setTaxes(ltaxlistbase);

					ltaxbasewithoutmarkup.setAmount(AmountRoundingModeUtil.roundingModeForHotel(apitaxamountapitotal.multiply(exRateApiToBase)));
					ltaxbasewithoutmarkup.setTaxes(ltaxlistbasewithoutmarkup);

					ltaxbooking.setAmount(AmountRoundingModeUtil.roundingModeForHotel(taxamountapitotal.multiply(exRateApiToBase).multiply(exRateBaseToBooking)));
					ltaxbooking.setTaxes(ltaxlistbooking);

					apiPrice.setTaxes(ltaxapi);
					base.setTaxes(ltaxbase);
					baseWithoutMarkUp.setTaxes(ltaxbasewithoutmarkup);
					bookingPrice.setTaxes(ltaxbooking);
				}

				if(rtype.getApiPrice().getDiscounts()!=null && rtype.getApiPrice().getDiscounts().size()>0)
				{
					List<Discount> ldiscountlistApi  = new ArrayList<Discount>();
					List<Discount> ldiscountlistBase  = new ArrayList<Discount>();
					List<Discount> ldiscountlistBasewithoutmarkup  = new ArrayList<Discount>();
					List<Discount> ldiscountlistBooking  = new ArrayList<Discount>();					
					for (Discount d :rtype.getApiPrice().getDiscounts()) {
						Discount aApi = new Discount();
						Discount aBase = new Discount();
						Discount aBasewithoutmarkup = new Discount();
						Discount aBooking = new Discount();
						BigDecimal amountBeforeTaxApi = (d.getAmountBeforeTax() == null)?new BigDecimal("0"):d.getAmountBeforeTax();
						BigDecimal amountAeforeTaxApi = (d.getAmountAfterTax() == null)?new BigDecimal("0"):d.getAmountAfterTax();
						aApi.setAmountBeforeTax(AmountRoundingModeUtil.roundingModeForHotel(amountBeforeTaxApi));
						aApi.setAmountAfterTax(AmountRoundingModeUtil.roundingModeForHotel(amountAeforeTaxApi));

						aApi.setAppliesTo("Base");
						ldiscountlistApi.add(aApi);

						aBase.setAmountBeforeTax(AmountRoundingModeUtil.roundingModeForHotel(amountBeforeTaxApi.multiply(exRateApiToBase)));
						aBase.setAmountAfterTax(AmountRoundingModeUtil.roundingModeForHotel(amountBeforeTaxApi.multiply(exRateApiToBase)));
						aBase.setAppliesTo("Base");
						ldiscountlistBase.add(aBase);

						aBasewithoutmarkup.setAmountBeforeTax(AmountRoundingModeUtil.roundingModeForHotel(amountBeforeTaxApi.multiply(exRateApiToBase)));
						aBasewithoutmarkup.setAmountAfterTax(AmountRoundingModeUtil.roundingModeForHotel(amountBeforeTaxApi.multiply(exRateApiToBase)));
						aBasewithoutmarkup.setAppliesTo("Base");
						ldiscountlistBasewithoutmarkup.add(aBasewithoutmarkup);

						aBooking.setAmountBeforeTax(AmountRoundingModeUtil.roundingModeForHotel(amountBeforeTaxApi.multiply(exRateApiToBase).multiply(exRateBaseToBooking)));
						aBooking.setAmountAfterTax(AmountRoundingModeUtil.roundingModeForHotel(amountBeforeTaxApi.multiply(exRateApiToBase).multiply(exRateBaseToBooking)));
						aBooking.setAppliesTo("Base");
						ldiscountlistBooking.add(aBooking);

					}
					apiPrice.setDiscounts(ldiscountlistApi);
					base.setDiscounts(ldiscountlistBase);
					baseWithoutMarkUp.setDiscounts(ldiscountlistBasewithoutmarkup);
					bookingPrice.setDiscounts(ldiscountlistBooking);
				}

				/// Set Service Tax component
				HotelServiceTax hotelServiceTax = null;
				HotelGstTax hotelGstTax = null;
				if(companyConfig != null){
					if(companyConfig.getCompanyConfigType().isB2E()){
						if(companyConfig.getTaxtype()!=null && companyConfig.getTaxtype().equalsIgnoreCase("GST")){
							hotelGstTax = new HotelGstTax();
							hotelGstTax = getHotelGstTax(hs.getCountry(), companyConfig, noofdays, hs.getNoofrooms(), company, parentCompany,noofguests);
							bookingPrice.setTotalAmountPayable(AmountRoundingModeUtil.roundingModeForHotel(base.getTotalAmountPayable().multiply(exRateBaseToBooking)));
							bookingPrice.setPayableAmount(bookingPrice.getTotalAmountPayable());
						}else{
						hotelServiceTax = new HotelServiceTax();
						hotelServiceTax = getHotelServiceTax( bookingPrice.getTotalAmountPayable(),hs.getCountry(),companyConfig,noofdays,hs.getNoofrooms());
						bookingPrice.setTotalAmountPayable(AmountRoundingModeUtil.roundingModeForHotel(base.getTotalAmountPayable().multiply(exRateBaseToBooking)));
						bookingPrice.setPayableAmount(bookingPrice.getTotalAmountPayable());
						}
					}
					if(companyConfig.getCompanyConfigType().isB2B()){						
						BigDecimal payableAmt = CommonUtil.getPayableAmount(hs,companyConfig,base);		
						if(payableAmt.compareTo(new BigDecimal(0))  == 0)
							payableAmt = bookingPrice.getTotalAmountPayable();		


						base.setPayableAmount(payableAmt);
						bookingPrice.setPayableAmount(payableAmt);

					}else{
						base.setPayableAmount(AmountRoundingModeUtil.roundingModeForHotel(base.getTotalAmountPayable()));
						bookingPrice.setPayableAmount(AmountRoundingModeUtil.roundingModeForHotel(bookingPrice.getTotalAmountPayable()));
					}
				}
				bookingPrice.setHotelServiceTax(hotelServiceTax);
				bookingPrice.setHotelGstTax(hotelGstTax);

				lrate.setBase(base);
				lrate.setBaseWithoutMarkUp(baseWithoutMarkUp);
				lrate.setBookingPrice(bookingPrice);

				lrate.setMarkupmap(markupmap);
				lrate.setCommissionmap(commissionmap);

				logger.info("\\\\\\\\************************* currency filling-is done------base--"+lrate.getBase().getAmountBeforeTax());
				logger.info("\\\\\\\\************************* currency filling-is done------baseWithoutMarkUp--"+lrate.getBaseWithoutMarkUp().getAmountBeforeTax());
				logger.info("\\\\\\\\************************* currency filling-is done------bookingPrice--"+lrate.getBookingPrice().getAmountBeforeTax());

				lratelist.add(lrate);
			}
			lrates.setRates(lratelist);
			lrr.setRates(lrates);
			lrratelist.add(lrr);
		}
		troomrates.setRoomRates(lrratelist);
		rs.setRoomRates(troomrates);
		return rs;
	}

	public RoomStay fillCurrencyDataOnCancellationPloicyWithMarkups(HotelSearchCommand hs, Map<String,List<HotelMarkup>> markupsmap, com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs)throws HibernateException, IOException, ParseException
	{

		List<Entry<String, CommissionDetails>> commissionList = new LinkedList<Entry<String, CommissionDetails>>(hs.getHotelMarkupCommissionDetails().getCommissionDetailsMap().entrySet());
		// Sorting the list based on values
		Collections.sort(commissionList, new Comparator<Entry<String, CommissionDetails>>() {
			@Override
			public int compare(Entry<String, CommissionDetails> c1, Entry<String, CommissionDetails> c2) {
				Integer companyid1 = Integer.valueOf(c1.getValue().getCompanyId());
				Integer companyid2 = Integer.valueOf(c2.getValue().getCompanyId());
				return companyid1.compareTo(companyid2);
			}
		});

		Map<String, BigDecimal> markupmap = new HashMap<String, BigDecimal>();
		Map<String, CommissionDetails> commissionmap = new HashMap<String, CommissionDetails>();

		BasicPropertyInfoType tbasic = rs.getBasicPropertyInfo();
		int noofdays = CommonUtil.getNoofStayDays(hs);

		String apiCurrency = tbasic.getApiCurrency();
		String baseCurrency = tbasic.getBaseCurrency();
		String bookingCurrency = tbasic.getBookingCurrency();
		BigDecimal exRateApiToBase  = tbasic.getExRateApiToBase();
		BigDecimal exRateBaseToBooking = tbasic.getExRateBaseToBooking();

		RatePlans trateplans = rs.getRatePlans();
		List<com.tayyarah.hotel.model.RatePlanType> lplantlist = new ArrayList<com.tayyarah.hotel.model.RatePlanType>();

		logger.info("object transformation---: RatePlanType. size "+rs.getRatePlans().getRatePlan().size());
		for (com.tayyarah.hotel.model.RatePlanType pt : rs.getRatePlans().getRatePlan()) {
			com.tayyarah.hotel.model.RatePlanType tpt = pt;
			CancelPenaltiesType cancelpenalities = pt.getCancelPenalties();
			List<CancelPenaltyType> cancellist = new ArrayList<CancelPenaltyType>();
			if(pt.getCancelPenalties()!=null && pt.getCancelPenalties().getCancelPenalties() != null)
			{
				logger.info("object transformation---: RatePlanType.CancelPenaltyType size "+pt.getCancelPenalties().getCancelPenalties().size());
				for (CancelPenaltyType cpt : pt.getCancelPenalties().getCancelPenalties()) {
					CancelPenaltyType tcpt = cpt;

					AmountPercentType apiAmountPercent = cpt.getApiAmountPercent();
					AmountPercentType baseAmountPercent = cpt.getBaseAmountPercent();
					AmountPercentType baseWithoutMarkupAmountPercent = cpt.getBaseWithoutMarkupAmountPercent();
					AmountPercentType amountPercent = cpt.getAmountPercent();

					BigDecimal apiAmount = apiAmountPercent.getAmount();
					BigDecimal baseAmount = apiAmount;
					BigDecimal baseAmountWithoutMarkup = apiAmount;
					BigDecimal bookingAmount = apiAmount;

					if(amountPercent.getBasisType().equalsIgnoreCase("Amount"))
					{
						apiAmount = apiAmountPercent.getAmount();
						baseAmount = apiAmount.multiply(exRateApiToBase);
						baseAmountWithoutMarkup = apiAmount.multiply(exRateApiToBase);
						bookingAmount = baseAmount.multiply(exRateBaseToBooking);
					}
					apiAmountPercent.setCurrencyCode(apiCurrency);
					apiAmountPercent.setAmount(apiAmount);
					baseAmountPercent.setCurrencyCode(baseCurrency);
					baseAmountPercent.setAmount(baseAmount);
					baseWithoutMarkupAmountPercent.setCurrencyCode(baseCurrency);
					baseWithoutMarkupAmountPercent.setAmount(baseAmountWithoutMarkup);
					amountPercent.setCurrencyCode(bookingCurrency);
					amountPercent.setAmount(bookingAmount);

					tcpt.setAmountPercent(amountPercent);
					tcpt.setApiAmountPercent(apiAmountPercent);
					tcpt.setBaseAmountPercent(baseAmountPercent);
					tcpt.setBaseWithoutMarkupAmountPercent(baseWithoutMarkupAmountPercent);
					cancellist.add(tcpt);
				}
			}
			cancelpenalities.setCancelPenalties(cancellist);
			tpt.setCancelPenalties(cancelpenalities);
			lplantlist.add(tpt);
		}
		trateplans.setRatePlen(lplantlist);
		rs.setRatePlans(trateplans);
		return rs;
	}

	private static HotelServiceTax getHotelServiceTax(BigDecimal PayableAmt,String country,CompanyConfig companyConfig,int noofdays,int noofrooms){
		HotelServiceTax hotelServiceTax = null;
		hotelServiceTax = new HotelServiceTax();

		BigDecimal totalprice = PayableAmt.multiply(new BigDecimal(noofdays)).multiply(new BigDecimal(noofrooms)) ;
		BigDecimal baseServiceTax = new BigDecimal("0.0");
		BigDecimal SBC = new BigDecimal("0.0");
		BigDecimal KKC = new BigDecimal("0.0");
		BigDecimal totalServiceTax = new BigDecimal("0.0");
		BigDecimal managementFee  = new BigDecimal("0.0");

		HotelServiceTaxConfig hotelServiceTaxConfig = companyConfig.getHotelServiceTaxConfig();
		if(country.equalsIgnoreCase("India")){
			totalprice = totalprice.add(hotelServiceTaxConfig.getDomesticManagementFee());
			managementFee = hotelServiceTaxConfig.getDomesticManagementFee();
		}
		if(!country.equalsIgnoreCase("India")){
			totalprice = totalprice.add(hotelServiceTaxConfig.getManagementFee());
			managementFee = hotelServiceTaxConfig.getManagementFee();
		}

		baseServiceTax = totalprice.divide(new BigDecimal("100.0")).multiply(hotelServiceTaxConfig.getBasicTax());
		SBC =  totalprice.divide(new BigDecimal("100.0")).multiply(hotelServiceTaxConfig.getSwatchBharathCess());
		KKC =  totalprice.divide(new BigDecimal("100.0")).multiply(hotelServiceTaxConfig.getKrishiKalyanCess());

		totalServiceTax =  totalprice.divide(new BigDecimal("100.0")).multiply(hotelServiceTaxConfig.getTotalTax());

		hotelServiceTax.setBaseServicetax(baseServiceTax.setScale(2, RoundingMode.UP));
		hotelServiceTax.setSBC(SBC.setScale(2, RoundingMode.UP));
		hotelServiceTax.setKKC(KKC.setScale(2, RoundingMode.UP));
		hotelServiceTax.setManagementFee(managementFee.setScale(2, RoundingMode.UP));
		hotelServiceTax.setTotalServiceTax(totalServiceTax.setScale(2, RoundingMode.UP));

		return hotelServiceTax;
	}

	private static HotelServiceTax getHotelServiceTaxForAllDay(BigDecimal PayableAmt,String country,CompanyConfig companyConfig,int noofdays){
		HotelServiceTax hotelServiceTax = null;
		hotelServiceTax = new HotelServiceTax();

		BigDecimal totalprice = PayableAmt;
		BigDecimal baseServiceTax = new BigDecimal("0.0");
		BigDecimal SBC = new BigDecimal("0.0");
		BigDecimal KKC = new BigDecimal("0.0");
		BigDecimal totalServiceTax = new BigDecimal("0.0");
		BigDecimal managementFee  = new BigDecimal("0.0");

		HotelServiceTaxConfig hotelServiceTaxConfig = companyConfig.getHotelServiceTaxConfig();
		if(country.equalsIgnoreCase("India")){
			totalprice = totalprice.add(hotelServiceTaxConfig.getDomesticManagementFee());		
			managementFee = hotelServiceTaxConfig.getDomesticManagementFee();
		}
		if(!country.equalsIgnoreCase("India")){
			totalprice = totalprice.add(hotelServiceTaxConfig.getManagementFee());			
			managementFee = hotelServiceTaxConfig.getManagementFee();
		}

		baseServiceTax = totalprice.divide(new BigDecimal("100.0")).multiply(hotelServiceTaxConfig.getBasicTax());
		SBC =  totalprice.divide(new BigDecimal("100.0")).multiply(hotelServiceTaxConfig.getSwatchBharathCess());
		KKC =  totalprice.divide(new BigDecimal("100.0")).multiply(hotelServiceTaxConfig.getKrishiKalyanCess());

		totalServiceTax =  totalprice.divide(new BigDecimal("100.0")).multiply(hotelServiceTaxConfig.getTotalTax());

		hotelServiceTax.setBaseServicetax(baseServiceTax.setScale(2, RoundingMode.UP));
		hotelServiceTax.setSBC(SBC.setScale(2, RoundingMode.UP));
		hotelServiceTax.setKKC(KKC.setScale(2, RoundingMode.UP));
		hotelServiceTax.setManagementFee(managementFee.setScale(2, RoundingMode.UP));
		hotelServiceTax.setTotalServiceTax(totalServiceTax.setScale(2, RoundingMode.UP));

		return hotelServiceTax;
	}
	
	private static HotelGstTax getHotelGstTax(String country,CompanyConfig companyConfig,int noofdays,int noofrooms,Company company,Company parentCompany,int totalguests){
		HotelGstTax hotelGstTax = new HotelGstTax();
		
		BigDecimal totalGuests = new BigDecimal(totalguests);
		BigDecimal CGST = new BigDecimal("0.0");
		BigDecimal SGST = new BigDecimal("0.0");
		BigDecimal IGST = new BigDecimal("0.0");
		BigDecimal UGST = new BigDecimal("0.0");
		BigDecimal totalGst = new BigDecimal("0.0");
		BigDecimal managementFee  = new BigDecimal("0.0");
		BigDecimal convenienceFee  = new BigDecimal("0.0");
		boolean isParentCompanyUT = IndianUnionTerritories.isUnionter(parentCompany.getBillingstate().trim());
		boolean isBillingCompanyUT = IndianUnionTerritories.isUnionter(company.getBillingstate().trim());
		
		if(country.equalsIgnoreCase("India")){
			HotelGstTaxConfig hotelGstTaxConfig = companyConfig.getHotelGstTaxConfig();
			managementFee = hotelGstTaxConfig.getDomesticManagementFee().multiply(totalGuests);
			convenienceFee = hotelGstTaxConfig.getConvenienceFee();
			
			if(isParentCompanyUT && isBillingCompanyUT){
				CGST = managementFee.divide(new BigDecimal("100.0")).multiply(hotelGstTaxConfig.getCGST());
				UGST =  managementFee.divide(new BigDecimal("100.0")).multiply(hotelGstTaxConfig.getUGST());
			}
			if(!company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim()) && IndianUnionTerritories.isUnionter(company.getBillingstate().trim())){
				CGST = managementFee.divide(new BigDecimal("100.0")).multiply(hotelGstTaxConfig.getCGST());
				UGST =  managementFee.divide(new BigDecimal("100.0")).multiply(hotelGstTaxConfig.getUGST());
			}
			if(!isParentCompanyUT && !isBillingCompanyUT){
				if(company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim())){
					CGST = managementFee.divide(new BigDecimal("100.0")).multiply(hotelGstTaxConfig.getCGST());
					SGST =  managementFee.divide(new BigDecimal("100.0")).multiply(hotelGstTaxConfig.getSGST());				
				}
			}
			if(isParentCompanyUT && !isBillingCompanyUT){
				if(!company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim()) && !IndianUnionTerritories.isUnionter(company.getBillingstate().trim())){
					IGST =  managementFee.divide(new BigDecimal("100.0")).multiply(hotelGstTaxConfig.getIGST());		
				}
			}	
			totalGst = CGST.add(SGST).add(IGST).add(UGST);	

		}

		if(!country.equalsIgnoreCase("India")){
			HotelGstTaxConfig hotelGstTaxConfig = companyConfig.getHotelGstTaxConfig();
			managementFee = hotelGstTaxConfig.getIntlManagementFee().multiply(totalGuests);
			convenienceFee = hotelGstTaxConfig.getConvenienceFee();
			
			if(isParentCompanyUT && isBillingCompanyUT){
				CGST = managementFee.divide(new BigDecimal("100.0")).multiply(hotelGstTaxConfig.getCGST());
				UGST =  managementFee.divide(new BigDecimal("100.0")).multiply(hotelGstTaxConfig.getUGST());
			}
			if(!company.getBillingstate().equalsIgnoreCase(parentCompany.getBillingstate()) && IndianUnionTerritories.isUnionter(company.getBillingstate())){
				CGST = managementFee.divide(new BigDecimal("100.0")).multiply(hotelGstTaxConfig.getCGST());
				UGST =  managementFee.divide(new BigDecimal("100.0")).multiply(hotelGstTaxConfig.getUGST());
			}
			if(!isParentCompanyUT && !isBillingCompanyUT){
				if(company.getBillingstate().equalsIgnoreCase(parentCompany.getBillingstate())){
					CGST = managementFee.divide(new BigDecimal("100.0")).multiply(hotelGstTaxConfig.getCGST());
					SGST =  managementFee.divide(new BigDecimal("100.0")).multiply(hotelGstTaxConfig.getSGST());				
				}
			}
			if(isParentCompanyUT && !isBillingCompanyUT){
				if(!company.getBillingstate().equalsIgnoreCase(parentCompany.getBillingstate()) && !IndianUnionTerritories.isUnionter(company.getBillingstate())){
					IGST =  managementFee.divide(new BigDecimal("100.0")).multiply(hotelGstTaxConfig.getIGST());		
				}
			}	
			totalGst = CGST.add(SGST).add(IGST).add(UGST);	
		}
		hotelGstTax.setCGST(CGST);
		hotelGstTax.setSGST(SGST);
		hotelGstTax.setIGST(IGST);
		hotelGstTax.setUGST(UGST);
		hotelGstTax.setTotalTax(totalGst);
		hotelGstTax.setManagementFee(managementFee);
		hotelGstTax.setConvenienceFee(convenienceFee);

		return hotelGstTax;
	}
}
class ComapnyIDComparator implements Comparator<CommissionDetails> {
	@Override
	public int compare(CommissionDetails c1, CommissionDetails c2) {
		Integer companyid1 = Integer.valueOf(c1.getCompanyId());
		Integer companyid2 = Integer.valueOf(c2.getCompanyId());
		return companyid1.compareTo(companyid2);
	}
}
