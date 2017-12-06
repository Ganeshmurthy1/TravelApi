package com.tayyarah.hotel.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.apiconfig.model.BluestarConfig;
import com.tayyarah.apiconfig.model.TravelportConfig;
import com.tayyarah.common.entity.OrderCustomer;
import com.tayyarah.configuration.CommonConfig;
import com.tayyarah.email.entity.model.Email;
import com.tayyarah.hotel.entity.HotelOrderCancellationPolicy;
import com.tayyarah.hotel.entity.HotelOrderGuest;
import com.tayyarah.hotel.entity.HotelOrderHotelData;
import com.tayyarah.hotel.entity.HotelOrderRoomInfo;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.hotel.entity.HotelOrderRowCancellation;
import com.tayyarah.hotel.entity.HotelOrderRowCancellationAPIResponse;
import com.tayyarah.hotel.entity.HotelOrderRowCommission;
import com.tayyarah.hotel.entity.HotelOrderRowMarkup;
import com.tayyarah.hotel.model.HotelReport;
import com.tayyarah.user.entity.User;
import com.tayyarah.user.entity.UserWallet;
import com.tayyarah.user.entity.WalletAmountTranferHistory;


public class HotelOrderDaoImp implements HotelOrderDao {
	@Autowired
	private SessionFactory sessionFactory;
	Logger logger = Logger.getLogger(HotelOrderDaoImp.class);

	@Override
	public HotelOrderRowCancellation insertOrUpdateHotelOrderRowCancellation(HotelOrderRowCancellation hotelOrderRowCancellation) throws HibernateException, Exception {
		Session sess = null;
		Transaction tx = null;
		Email email = new Email();
		try{
			sess = sessionFactory.openSession();
			Criteria crit = sess.createCriteria(HotelOrderRowCancellation.class);
			crit.add(Restrictions.eq("orderId", hotelOrderRowCancellation.getOrderId()));
			HotelOrderRowCancellation hotelOrderRowCancellationDb = (HotelOrderRowCancellation) crit.uniqueResult();

			Timestamp updated_at = new Timestamp(new Date().getTime());

			if(hotelOrderRowCancellationDb != null)
			{
				hotelOrderRowCancellationDb.setAPIStatusCode(hotelOrderRowCancellation.getAPIStatusCode());
				hotelOrderRowCancellationDb.setAPIStatusMessage(hotelOrderRowCancellation.getAPIStatusMessage());
				hotelOrderRowCancellationDb.setAPIConfirmationNumber(hotelOrderRowCancellation.getAPIConfirmationNumber());
				hotelOrderRowCancellationDb.setAPIChargeType(hotelOrderRowCancellation.getAPIChargeType());
				hotelOrderRowCancellationDb.setAPIChargeAmount(hotelOrderRowCancellation.getAPIChargeAmount());
				hotelOrderRowCancellationDb.setAPIBookingAmount(hotelOrderRowCancellation.getAPIBookingAmount());
				hotelOrderRowCancellationDb.setCreditNoteNo(hotelOrderRowCancellation.getCreditNoteNo());
				hotelOrderRowCancellationDb.setCreditNoteCreatedOn(hotelOrderRowCancellation.getCreditNoteCreatedOn());
				
				if(hotelOrderRowCancellation.getAPIChargeAmount()==null){
					hotelOrderRowCancellationDb.setAPIChargeAmount(new BigDecimal("0.00"));
				}
				hotelOrderRowCancellationDb.setAPICurrency(hotelOrderRowCancellation.getAPICurrency());
				hotelOrderRowCancellationDb.setAPIPaymentType(hotelOrderRowCancellation.getAPIPaymentType());
				hotelOrderRowCancellationDb.setAPIReference(hotelOrderRowCancellation.getAPIReference());
				
				hotelOrderRowCancellationDb.setAPIRefundAmount(hotelOrderRowCancellation.getAPIRefundAmount());
				if(hotelOrderRowCancellation.getAPIRefundAmount()==null){
					hotelOrderRowCancellationDb.setAPIRefundAmount(new BigDecimal("0.00"));
				}
				hotelOrderRowCancellationDb.setHotelOrderRow(hotelOrderRowCancellation.getHotelOrderRow());
				hotelOrderRowCancellationDb.setStatusCode(hotelOrderRowCancellation.getStatusCode());
				hotelOrderRowCancellationDb.setStatusMessage(hotelOrderRowCancellation.getStatusMessage());
				hotelOrderRowCancellationDb.setUpdatedAt(updated_at);
				tx = sess.beginTransaction();
				sess.update(hotelOrderRowCancellationDb);
				tx.commit();
				hotelOrderRowCancellation = hotelOrderRowCancellationDb;
				logger.info("hotelOrderRowCancellation updated successfully-");
			}
			else
			{
				tx = sess.beginTransaction();
				hotelOrderRowCancellation.setCreatedAt(updated_at);
				sess.saveOrUpdate(hotelOrderRowCancellation);
				tx.commit();
				logger.info("hotelOrderRowCancellation inserted successfully-");
			}		

		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			logger.info("Exceeption----HibernateException--"+e.getMessage());
		}
		finally
		{
			if(sess != null && sess.isOpen())
			{
				sess.close();
			}
		}
		return hotelOrderRowCancellation;
	}


	@Override
	public HotelOrderRowCancellation getUpdateHotelOrderRowCancellation(String orderId) throws HibernateException, Exception {
		Session sess = null;
		Email email = new Email();
		HotelOrderRowCancellation hotelOrderRowCancellationDb = null;
		try{
			sess = sessionFactory.openSession();
			Criteria crit = sess.createCriteria(HotelOrderRowCancellation.class);
			crit.add(Restrictions.eq("orderId", orderId));
			hotelOrderRowCancellationDb = (HotelOrderRowCancellation) crit.uniqueResult();
			
		}catch (HibernateException e) {
			logger.info("Exceeption----HibernateException--"+e.getMessage());
		}
		finally
		{
			if(sess != null && sess.isOpen())
			{
				sess.close();
			}
			return hotelOrderRowCancellationDb;
		}
	}

	@Override
	public OrderCustomer insertOrderCustomerDetails(OrderCustomer oc) throws HibernateException, Exception {
		Session sess = null;
		Transaction tx = null;
		try{
			sess = sessionFactory.openSession();
			tx = sess.beginTransaction();
			sess.saveOrUpdate(oc);			
			tx.commit();			
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			logger.info("Exceeption----HibernateException--"+e.getMessage());
		}
		finally
		{
			if(sess != null && sess.isOpen())
			{
				sess.close();
			}
		}
		return oc;
	}

	@Override
	public HotelOrderCancellationPolicy insertHotelOrderCancellationPolicyDetails(HotelOrderCancellationPolicy hocp)
			throws HibernateException, Exception {
		Session sess = null;
		Transaction tx = null;
		try{
			sess = sessionFactory.openSession();
			tx = sess.beginTransaction();
			sess.saveOrUpdate(hocp);		
			tx.commit();			
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			logger.info("Exceeption----HibernateException--"+e.getMessage());
		}
		finally
		{
			if(sess != null && sess.isOpen())
			{
				sess.close();
			}
		}
		return hocp;
	}

	@Override
	public HotelOrderGuest insertHotelOrderGuestDetails(HotelOrderGuest hog) throws HibernateException, Exception {
		Session sess = null;
		Transaction tx = null;
		try{
			sess = sessionFactory.openSession();
			tx = sess.beginTransaction();
			sess.saveOrUpdate(hog);		
			tx.commit();
			sess.close();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			logger.info("Exceeption----HibernateException--"+e.getMessage());
		}
		finally
		{
			if(sess != null && sess.isOpen())
			{
				sess.close();
			}
		}
		return hog;
	}

	@Override
	public HotelOrderHotelData insertHotelOrderHotelDataDetails(HotelOrderHotelData hohd) throws HibernateException, Exception {
		Session sess = null;
		Transaction tx = null;
		try{
			sess = sessionFactory.openSession();
			tx = sess.beginTransaction();
			sess.saveOrUpdate(hohd);			
			tx.commit();
			sess.close();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			logger.info("Exceeption----HibernateException--"+e.getMessage());
		}
		finally
		{
			if(sess != null && sess.isOpen())
			{
				sess.close();
			}
		}
		return hohd;
	}

	@Override
	public HotelOrderRoomInfo insertHotelOrderRoomInfoDetails(HotelOrderRoomInfo hori) throws HibernateException, Exception {
		Session sess = null;
		Transaction tx = null;
		try{
			sess = sessionFactory.openSession();
			tx = sess.beginTransaction();
			sess.saveOrUpdate(hori);			
			tx.commit();
			sess.close();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			logger.info("Exceeption----HibernateException--"+e.getMessage());
		}
		finally
		{
			if(sess != null && sess.isOpen())
			{
				sess.close();
			}
		}
		return hori;
	}
	
	@Override
	public HotelOrderRow insertHotelOrderRowDetails(HotelOrderRow hor) throws HibernateException, Exception {
		try{
			BigDecimal finalprice = hor.getFinalPrice();
			hor.setFinalPrice(finalprice.setScale(2,  BigDecimal.ROUND_HALF_UP));
			BigDecimal feeamount = hor.getFeeAmount();
			hor.setFeeAmount(feeamount.setScale(2,  BigDecimal.ROUND_HALF_UP));
			BigDecimal apiamount = hor.getApiPrice();
			hor.setFinalPrice(apiamount.setScale(2,  BigDecimal.ROUND_HALF_UP));
			BigDecimal taxes = hor.getTaxes();
			hor.setTaxes(taxes.setScale(2,  BigDecimal.ROUND_HALF_UP));
			BigDecimal markup = hor.getMarkupAmount();
			hor.setMarkupAmount(markup.setScale(2,  BigDecimal.ROUND_HALF_UP));
		}
		catch(Exception e)
		{
			logger.error("Rounding up frice Exception", e);
		}
		Session sess = null;
		Transaction tx = null;
		try{
			sess = sessionFactory.openSession();
			tx = sess.beginTransaction();
			sess.saveOrUpdate(hor);		
			tx.commit();
			sess.close();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			logger.info("Exceeption----HibernateException--"+e.getMessage());
		}
		finally
		{
			if(sess != null && sess.isOpen())
			{
				sess.close();
			}
		}
		return hor;
	}

	@Override
	public List<HotelReport> getHotelOrderCancellationPolicyDetails() throws HibernateException, Exception {
		HotelOrderCancellationPolicy  hotelOrderCancellationPolicy=null ;
		List<HotelReport>  orderData_list=new ArrayList<HotelReport>();
		Session sess = null;
		try{
			sess = sessionFactory.openSession();
			String sql = "from HotelOrderCancellationPolicy";
			Query query = sess.createQuery(sql);
			@SuppressWarnings("unchecked")
			List<HotelOrderCancellationPolicy> list = query.list();
			for (Iterator  iterator = list.iterator(); iterator.hasNext();){
				hotelOrderCancellationPolicy= (HotelOrderCancellationPolicy)iterator.next();
				HotelReport reportData=new HotelReport();
				reportData.setAgencyUsername(hotelOrderCancellationPolicy.getHotelOrderRow().getAgencyUserName());
				reportData.setAgentCom(hotelOrderCancellationPolicy.getHotelOrderRow().getMarkupAmount());
				reportData.setBase_price(hotelOrderCancellationPolicy.getHotelOrderRow().getFinalPrice());
				reportData.setBooking_status(hotelOrderCancellationPolicy.getHotelOrderRow().getBookingStatus());
				reportData.setCheckInDate(hotelOrderCancellationPolicy.getHotelOrderRow().getCheckInDate());
				reportData.setCheckOutDate(hotelOrderCancellationPolicy.getHotelOrderRow().getCheckOutDate());
				reportData.setCurCode(hotelOrderCancellationPolicy.getHotelOrderRow().getBaseCurrency());
				reportData.setDiscount(hotelOrderCancellationPolicy.getHotelOrderRow().getDiscountAmount());
				reportData.setEmail(hotelOrderCancellationPolicy.getHotelOrderRow().getOrderCustomer().getEmail());
				reportData.setFirstname(hotelOrderCancellationPolicy.getHotelOrderRow().getOrderCustomer().getFirstName());
				reportData.setGender(hotelOrderCancellationPolicy.getHotelOrderRow().getOrderCustomer().getGender());
				reportData.setGuests(hotelOrderCancellationPolicy.getHotelOrderRow().getTotalGuest());
				reportData.setLastname(hotelOrderCancellationPolicy.getHotelOrderRow().getOrderCustomer().getLastName());
				reportData.setMobile(hotelOrderCancellationPolicy.getHotelOrderRow().getOrderCustomer().getMobile());
				reportData.setPaymentStatus(hotelOrderCancellationPolicy.getHotelOrderRow().getPaymentStatus());
				reportData.setPhone(hotelOrderCancellationPolicy.getHotelOrderRow().getOrderCustomer().getPhone());
				reportData.setRef_code(hotelOrderCancellationPolicy.getHotelOrderRow().getReferenceCode());
				reportData.setHotelName(hotelOrderCancellationPolicy.getHotelOrderRow().getHotelOrderHotelData().getName());
				reportData.setHotel_loc(hotelOrderCancellationPolicy.getHotelOrderRow().getHotelOrderHotelData().getHotelLocation());
				reportData.setHotel_cat(hotelOrderCancellationPolicy.getHotelOrderRow().getHotelOrderHotelData().getHotelCategory());
				reportData.setState(hotelOrderCancellationPolicy.getHotelOrderRow().getHotelOrderHotelData().getState());
				reportData.setCountry(hotelOrderCancellationPolicy.getHotelOrderRow().getHotelOrderHotelData().getCountry());
				reportData.setFee_amount(hotelOrderCancellationPolicy.getHotelOrderRow().getFeeAmount());
				orderData_list.add(reportData);
			}		
		}catch (HibernateException e) {
			logger.info("Exceeption----HibernateException--"+e.getMessage());
		}
		finally
		{
			if(sess != null && sess.isOpen())
			{
				sess.close();
			}
		}
		return orderData_list;
	}

	@Override
	public HotelOrderRow getHotelOrderRow(String orderid) throws HibernateException, Exception {
		HotelOrderRow hotelOrderRow =null;
		Session sess = null;
		try{
			sess = sessionFactory.openSession();
			Criteria criteria = sess.createCriteria(HotelOrderRow.class);
			criteria = criteria.add(Restrictions.eq("orderReference", orderid));
			hotelOrderRow = (HotelOrderRow)criteria.uniqueResult();
		}catch (HibernateException e) {
			logger.info("Exceeption----HibernateException--"+e.getMessage());
		}
		finally
		{
			if(sess != null && sess.isOpen())
			{
				sess.close();
			}
		}
		return hotelOrderRow;
	}

	public HotelOrderRow updateBookStatusTemp(String orderReference, HotelOrderRow hor)
	{
		Session sess = null;
		Transaction tx = null;
		try{
			sess = sessionFactory.openSession();
			tx = sess.beginTransaction();
			sess.saveOrUpdate(hor);		
			tx.commit();		
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			logger.info("Exceeption----HibernateException--"+e.getMessage());
		}
		finally
		{
			if(sess != null && sess.isOpen())
			{
				sess.close();
			}
		}		
		return hor;	
	}



	public HotelOrderRow updateHotelBookStatusOld(String orderReference, HotelOrderRow hor) {
		Session sess = null;
		Transaction trans = null;
		try{
			sess = sessionFactory.openSession();
			Criteria crit = sess.createCriteria(HotelOrderRow.class);
			crit.add(Restrictions.eq("orderReference", hor.getOrderReference()));
			HotelOrderRow hordb = (HotelOrderRow) crit.uniqueResult();			
			trans = sess.beginTransaction();
			hordb.setApiBookingId(hor.getApiBookingId());
			hordb.setApiConfirmationNo(hor.getApiConfirmationNo());
			hordb.setConfirmationNo(hor.getConfirmationNo());
			hordb.setSearchKey(hor.getSearchKey());
			hordb.setInvoiceNo(hor.getInvoiceNo());
			hordb.setFinalPrice(hor.getFinalPrice());
			hordb.setMarkupAmount(hor.getMarkupAmount());
			hordb.setReferenceCode(hor.getReferenceCode());
			hordb.setRefUniqueType(hor.getRefUniqueType());
			hordb.setBookingStatus(hor.getBookingStatus());
			hordb.setPaymentStatus(hor.getPaymentStatus());
			hordb.setStatusAction(hor.getStatusAction());
			hordb.setUpdatedAt(new Timestamp(new Date().getTime()));
			List<HotelOrderRoomInfo> hotelOrderRoomInfos = new ArrayList<HotelOrderRoomInfo>();
			for (HotelOrderRoomInfo hotelOrderRoomInfo : hordb.getHotelOrderRoomInfos()) {
				HotelOrderRoomInfo horoom = hotelOrderRoomInfo;
				horoom.setStatus(hor.getBookingStatus());
				hotelOrderRoomInfos.add(horoom);
			}
			hordb.setHotelOrderRoomInfos(hotelOrderRoomInfos);
			sess.saveOrUpdate(hordb);
			trans.commit();		
		} catch (HibernateException e) {
			if (trans != null) {
				trans.rollback();
			}
			logger.error("HibernateException", e);
		} catch (Exception e) {
			if (trans != null) {
				trans.rollback();
			}
			logger.error("Exception", e);
		}
		finally {
			if(sess != null && sess.isOpen())
				sess.close();
		}
		return null;
	}


	@Override
	public void updateRoomsBookStatus(String orderReference, String status) {
		Session sess = null;
		Transaction trans = null;
		try {
			sess = sessionFactory.openSession();
			trans = sess.beginTransaction();
			Criteria crit = sess.createCriteria(HotelOrderRow.class);
			crit.add(Restrictions.eq("orderReference", orderReference));
			// Here is updated code
			ScrollableResults items = crit.scroll();
			int count=0;
			while ( items.next() ) {
				HotelOrderRow e = (HotelOrderRow)items.get(0);
				e.setStatusAction(status);
				sess.saveOrUpdate(e);
				if ( ++count % 100 == 0 ) {
					sess.flush();
					sess.clear();
				}
			}
			trans.commit();
		} catch (HibernateException ex) {
			if (trans != null) {
				trans.rollback();
			}
			logger.debug(ex.getMessage());
		} finally {
			if(sess != null && sess.isOpen())
				sess.close();
		}
	}

	@Override
	public void insertCommission(
			HotelOrderRowCommission hotelOrderRowCommission) throws Exception {		
		Session sess = null;
		Transaction tx = null;
		try{
			sess = sessionFactory.openSession();
			tx = sess.beginTransaction();
			sess.saveOrUpdate(hotelOrderRowCommission);			
			tx.commit();			
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			logger.info("Exceeption----HibernateException--"+e.getMessage());
		}
		finally
		{
			if(sess != null && sess.isOpen())
			{
				sess.close();
			}
		}
	}

	@Override
	public void insertMarkupDetails(HotelOrderRowMarkup hotelOrderRowMarkup)
			throws Exception {	
		Session sess = null;
		Transaction tx = null;
		try{
			sess = sessionFactory.openSession();
			tx = sess.beginTransaction();
			sess.saveOrUpdate(hotelOrderRowMarkup);		
			tx.commit();			
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			logger.info("Exceeption----HibernateException--"+e.getMessage());
		}
		finally
		{
			if(sess != null && sess.isOpen())
			{
				sess.close();
			}
		}
	}	

	@Override
	public HotelOrderRow updateHotelBookStatus(String orderReference, HotelOrderRow hor) {
		HotelOrderRow hordb = null;
		Session sess = null;
		Transaction tx = null;
		try{
			sess = sessionFactory.openSession();
			Criteria crit = sess.createCriteria(HotelOrderRow.class);
			crit.add(Restrictions.eq("orderReference", hor.getOrderReference()));
			hordb = (HotelOrderRow) crit.uniqueResult();
			tx = sess.beginTransaction();
			hordb.setApiBookingId(hor.getApiBookingId());
			hordb.setApiConfirmationNo(hor.getApiConfirmationNo());
			hordb.setConfirmationNo(hor.getConfirmationNo());
			hordb.setSearchKey(hor.getSearchKey());
			hordb.setMarkupAmount(hor.getMarkupAmount());
			hordb.setCompanyEntityId(hor.getCompanyEntityId());
			hordb.setInvoiceNo(hor.getInvoiceNo());
			hordb.setFinalPrice(hor.getFinalPrice());
			hordb.setReferenceCode(hor.getReferenceCode());
			hordb.setRefUniqueType(hor.getRefUniqueType());
			hordb.setBookingStatus(hor.getBookingStatus());
			hordb.setPaymentStatus(hor.getPaymentStatus());
			hordb.setStatusAction(hor.getStatusAction());
			hordb.setUpdatedAt(new Timestamp(new Date().getTime()));
			List<HotelOrderRoomInfo> hotelOrderRoomInfos = new ArrayList<HotelOrderRoomInfo>();
			for (HotelOrderRoomInfo hotelOrderRoomInfo : hordb.getHotelOrderRoomInfos()) {
				HotelOrderRoomInfo horoom = hotelOrderRoomInfo;
				horoom.setStatus(hor.getBookingStatus());
				hotelOrderRoomInfos.add(horoom);
			}
			hordb.setHotelOrderRoomInfos(hotelOrderRoomInfos);
			sess.saveOrUpdate(hordb);			
			tx.commit();			
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			logger.info("Exceeption----HibernateException--"+e.getMessage());
		}
		finally
		{
			if(sess != null && sess.isOpen())
			{
				sess.close();
			}
		}
		try{
			if(hordb.getStatusAction().equalsIgnoreCase("Confirmed") && !CommonConfig.GetCommonConfig().getSystem_model().equalsIgnoreCase("CUTANDPAY")){
				logger.info("transaction is confirmed");
				//Update commission and markup
				List<HotelOrderRowCommission> hotelOrderRowCommissionList = hordb.getHotelOrderRowCommissionList();
				Collections.sort(hotelOrderRowCommissionList, new comapnyIDComparator());
				
				for (int m = 0; m < hotelOrderRowCommissionList.size(); m++) {
					HotelOrderRowCommission hotelOrderRowCommission=hotelOrderRowCommissionList.get(m);
					if(hotelOrderRowCommission.getRateType().equalsIgnoreCase("Commission")){
						if(hotelOrderRowCommission.getCommissionAmountValue().compareTo(new BigDecimal("0.00"))==1){
							if(m!=0){
								UpdateMarkupAndCommsionInWallet(hotelOrderRowCommissionList.get(m-1).getCompanyId(),hotelOrderRowCommission.getCommissionAmountValue(), "Hotel Commission Shared",hordb.getOrderReference());
							}
							UpdateMarkupAndCommsionInWallet(hotelOrderRowCommission.getCompanyId(),hotelOrderRowCommission.getCommissionAmountValue(), "Hotel Commission Added",hordb.getOrderReference());
						}
					}
				}
				///////////////For tayyarah adding TDS.........adding 10%tds
				if(CommonConfig.GetCommonConfig().isIs_tayyarah_enabled()){

					for (int m = 1; m < hotelOrderRowCommissionList.size(); m++) {
						HotelOrderRowCommission hotelOrderRowCommission=hotelOrderRowCommissionList.get(m);
						if(hotelOrderRowCommission.getRateType().equalsIgnoreCase("Commission")){
							if(hotelOrderRowCommission.getCommissionAmountValue().compareTo(new BigDecimal("0.00"))==1){
								BigDecimal tdsAmount=new BigDecimal("0.00");
								tdsAmount=(hotelOrderRowCommission.getCommissionAmountValue().multiply(new BigDecimal(10))).divide(new BigDecimal(100));
								if(m<hotelOrderRowCommissionList.size()-1){
									tdsAmount=((hotelOrderRowCommission.getCommissionAmountValue().subtract(hotelOrderRowCommissionList.get(m+1).getCommissionAmountValue())).multiply(new BigDecimal(10))).divide(new BigDecimal(100));
								}
								UpdateMarkupAndCommsionInWallet(hotelOrderRowCommissionList.get(m).getCompanyId(),tdsAmount, "TDS on commission",hordb.getOrderReference());
							}
						}
					}
				}
				//End of TDS
				///////for markup
				List<HotelOrderRowMarkup> hotelOrderRowMarkupList=hordb.getHotelOrderRowMarkupList();
				logger.info("hotelOrderRowMarkupList is confirmed size :"+hotelOrderRowMarkupList.size());
				for(HotelOrderRowMarkup hotelOrderRowMarkup:hotelOrderRowMarkupList){
					if(hotelOrderRowMarkup.getMarkUp().compareTo(new BigDecimal("0.00"))==1){
						UpdateMarkupAndCommsionInWallet(hotelOrderRowMarkup.getCompanyId(),hotelOrderRowMarkup.getMarkUp(), "Hotel Markup",hordb.getOrderReference());
					}
				}
			}
			if(hordb.getStatusAction().equalsIgnoreCase("Confirmed") && CommonConfig.GetCommonConfig().getSystem_model().equalsIgnoreCase("CUTANDPAY")){
				logger.info("transaction is confirmed");
				//Update commission and markup
				List<HotelOrderRowCommission> hotelOrderRowCommissionList = hordb.getHotelOrderRowCommissionList();
				Collections.sort(hotelOrderRowCommissionList, new comapnyIDComparator());
				logger.info("hotelOrderRowCommissionList is confirmed size :"+hotelOrderRowCommissionList.size());
				//	for(FlightOrderRowCommission flightOrderRowCommission:flightOrderRowCommissionList){
				for (int m = 0; m < hotelOrderRowCommissionList.size(); m++) {
					HotelOrderRowCommission hotelOrderRowCommission=hotelOrderRowCommissionList.get(m);
					if(hotelOrderRowCommission.getRateType().equalsIgnoreCase("Commission")){
						if(hotelOrderRowCommission.getCommissionAmountValue().compareTo(new BigDecimal("0.00"))==1){
							if(m!=0){
							//	UpdateMarkupAndCommsionInWallet(hotelOrderRowCommissionList.get(m-1).getCompanyId(),hotelOrderRowCommission.getCommissionAmountValue(), "Hotel Commission Shared",hordb.getOrderReference());
							}
							//UpdateMarkupAndCommsionInWallet(hotelOrderRowCommission.getCompanyId(),hotelOrderRowCommission.getCommissionAmountValue(), "Hotel Commission Added",hordb.getOrderReference());
						}
					}
				}
				///////////////For tayyarah adding TDS.........adding 10%tds
				if(CommonConfig.GetCommonConfig().isIs_tayyarah_enabled()){

					for (int m = 1; m < hotelOrderRowCommissionList.size(); m++) {
						HotelOrderRowCommission hotelOrderRowCommission=hotelOrderRowCommissionList.get(m);
						if(hotelOrderRowCommission.getRateType().equalsIgnoreCase("Commission")){
							if(hotelOrderRowCommission.getCommissionAmountValue().compareTo(new BigDecimal("0.00"))==1){
								BigDecimal tdsAmount=new BigDecimal("0.00");
								tdsAmount=(hotelOrderRowCommission.getCommissionAmountValue().multiply(new BigDecimal(10))).divide(new BigDecimal(100));
								if(m<hotelOrderRowCommissionList.size()-1){
									tdsAmount=((hotelOrderRowCommission.getCommissionAmountValue().subtract(hotelOrderRowCommissionList.get(m+1).getCommissionAmountValue())).multiply(new BigDecimal(10))).divide(new BigDecimal(100));
								}
							//	UpdateMarkupAndCommsionInWallet(hotelOrderRowCommissionList.get(m).getCompanyId(),tdsAmount, "TDS on commission",hordb.getOrderReference());
							}
						}
					}
				}
				//End of TDS
				///////for markup
				List<HotelOrderRowMarkup> hotelOrderRowMarkupList=hordb.getHotelOrderRowMarkupList();
				logger.info("hotelOrderRowMarkupList is confirmed size :"+hotelOrderRowMarkupList.size());
				for(HotelOrderRowMarkup hotelOrderRowMarkup:hotelOrderRowMarkupList){
					if(hotelOrderRowMarkup.getMarkUp().compareTo(new BigDecimal("0.00"))==1){
					//	UpdateMarkupAndCommsionInWallet(hotelOrderRowMarkup.getCompanyId(),hotelOrderRowMarkup.getMarkUp(), "Hotel Markup",hordb.getOrderReference());
					}
				}
			}
			

		} catch (HibernateException e) {
			logger.error("Exception", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		return hordb;

	}

	public synchronized void UpdateMarkupAndCommsionInWallet(String companyid,BigDecimal totalprice, String action,String orderID) throws Exception {
		WalletAmountTranferHistory walletAmountTranferHistory = new WalletAmountTranferHistory();
		walletAmountTranferHistory.setActionId(orderID);
		walletAmountTranferHistory.setCompanyId(Integer.parseInt(companyid));
		//walletAmountTranferHistory.setCurrency(get.DEFAULT_CURRENY); set ur base currency
		if(CommonConfig.GetCommonConfig().isIs_lintas_enabled()){
			walletAmountTranferHistory.setCurrency(TravelportConfig.GetTravelportConfig().DEFAULT_CURRENY);
		}
		if(CommonConfig.GetCommonConfig().isIs_tayyarah_enabled()){
			walletAmountTranferHistory.setCurrency(BluestarConfig.GetBluestarConfig(null).DEFAULT_CURRENCY);
		}
		User user = null;
		BigDecimal Walletbalance = null;
		boolean result=false;
		logger.info("getWalletStatus method called :");

		Session sess = null;
		Transaction tx = null;
		try{
			sess = sessionFactory.openSession();
			String sql = "from User u where u.Companyid=:companyid";
			Query query = sess.createQuery(sql);
			query.setParameter("companyid", Integer.parseInt(companyid));
			List<User> list = query.list();
			if(list.size()>0){
				user = list.get(0);
				Walletbalance =user.getAgentWallet().getWalletbalance();
			}
			sess.close();
			walletAmountTranferHistory.setUserId( user.getId());
			walletAmountTranferHistory.setAction(action);
			walletAmountTranferHistory.setAmount(totalprice);
			walletAmountTranferHistory.setOpeningBalance(Walletbalance);
			walletAmountTranferHistory.setWalletId(user.getAgentWallet().getWalletId());
			walletAmountTranferHistory.setTransactionType("Credit");

			if(action.contains("Markup"))
				walletAmountTranferHistory.setRemarks("Hotel Markup amount");
			else if(action.contains("Commission"))
				walletAmountTranferHistory.setRemarks("Hotel Commission amount");
			else
				walletAmountTranferHistory.setRemarks("Hotel Markup / Commission amount");	
			
			if(action.equalsIgnoreCase("Hotel Markup")||action.equalsIgnoreCase("Hotel Commission Added")){
				walletAmountTranferHistory.setClosingBalance(Walletbalance.add(totalprice));
				result = updateWalletBalance(Walletbalance.add(totalprice), user.getAgentWallet().getWalletId(),walletAmountTranferHistory);
			}else{
				walletAmountTranferHistory.setClosingBalance(Walletbalance.subtract(totalprice));
				result = updateWalletBalance(Walletbalance.subtract(totalprice), user.getAgentWallet().getWalletId(),walletAmountTranferHistory);

			}
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			result=false;
			logger.info("Exceeption----HibernateException--"+e.getMessage());
		}
		catch (NumberFormatException ne) {
			result=false;
			logger.error("NumberFormatException", ne);

		} catch (Exception e) {
			logger.error("Exception", e);
			result=false;
		}
		finally
		{
			if(sess != null && sess.isOpen())
			{
				sess.close();
			}
		}
	}

	public boolean updateWalletBalance(BigDecimal amount,int walletId,WalletAmountTranferHistory AWTH)
			throws HibernateException, NumberFormatException, Exception {

		List<UserWallet> list = null;
		boolean result = false;
		UserWallet agentWallet = null;	
		Session sess = null;
		Transaction tx = null;
	
		try{
			sess = sessionFactory.openSession();
			agentWallet =  (UserWallet) sess.get(UserWallet.class,walletId);
			agentWallet.setWalletbalance(amount);
			tx = sess.beginTransaction();
			agentWallet.setUpdatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
			sess.update(agentWallet);		
			tx.commit();			
			result = true;
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			result=false;
			logger.info("Exceeption----HibernateException--"+e.getMessage());
		}
		catch (Exception e) {
			result=false;
			logger.error("Exception", e);
		}
		finally
		{
			if(sess != null && sess.isOpen())
			{
				sess.close();
			}
			insertwalletamount_tranfer_history(AWTH);
			
		}
		return result;
	}

	public void insertwalletamount_tranfer_history(WalletAmountTranferHistory AWTH) throws Exception {
		Session sess = null;
		Transaction tx = null;
		try{
			sess = sessionFactory.openSession();
			tx = sess.beginTransaction();
			AWTH.setCreatedAt(new Timestamp(new Date().getTime()));
			AWTH.setParentClosingBalance(new BigDecimal("0.0"));
			AWTH.setParentOpeningBalance(new BigDecimal("0.0"));
			AWTH.setParentUserId(0);
			sess.save(AWTH);			
			tx.commit();
	
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			logger.info("Exceeption----HibernateException--"+e.getMessage());
		}
		finally
		{
			if(sess != null && sess.isOpen())
			{
				sess.close();
			}
		}
	}

	@Override
	public List<HotelOrderRow> getHotelBookingList(List<String> companyIdList,Integer pageNo,Integer pageSize) {
		Session session = null;
		List<HotelOrderRow> hotelOrderRowList=new ArrayList<HotelOrderRow>();
		try{
			session=sessionFactory.openSession();
			Criteria criteria=session.createCriteria(HotelOrderRow.class);
			criteria.add(Restrictions.in("companyId", companyIdList));			
			criteria.addOrder(Order.desc("bookingDate"));
			criteria.setFirstResult((pageNo-1)*pageSize + 1);
			criteria.setMaxResults(pageSize);
			hotelOrderRowList=(List<HotelOrderRow>) criteria.list();
		}catch (Exception e) {
		}
		finally {
			session.close();
		}
		return hotelOrderRowList;
	}

	@Override
	public HotelOrderRow getHotelBookingDetail(Long flightId) {		
		Session session = null;
		HotelOrderRow hotelOrderRow = new HotelOrderRow();		
		try{
			session=sessionFactory.openSession();
			Criteria criteria=session.createCriteria(HotelOrderRow.class);
			criteria.add(Restrictions.eq("id", flightId));
			hotelOrderRow=(HotelOrderRow) criteria.uniqueResult();
		}catch (Exception e) {
		}
		finally {
			session.close();
		}
		return hotelOrderRow;
	}

	@Override
	public  void insertHotelOrderRowCancellationApiResposnse(HotelOrderRowCancellationAPIResponse hotelOrderRowCancellationAPIResponse)throws HibernateException, Exception {
		Session sess = null;
		Transaction tx = null;
		try{
			sess = sessionFactory.openSession();
			tx = sess.beginTransaction();
			sess.save(hotelOrderRowCancellationAPIResponse);
			tx.commit();
			System.out.println("HotelOrderRowCancellationAPIResponse inserted successfully with orderrow api response id:"+hotelOrderRowCancellationAPIResponse.getId()+"And  orderrow cancellation id:"+hotelOrderRowCancellationAPIResponse.getHotelOrderRowCancellation().getId());
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			logger.info("Exceeption----HibernateException--"+e.getMessage());
		}
		finally
		{
			if(sess != null && sess.isOpen())
			{
				sess.close();
			}
		}		
	}
}

class comapnyIDComparator implements Comparator<Object> {
	@Override
	public int compare(Object o1, Object o2) {
		HotelOrderRowCommission A1 = (HotelOrderRowCommission) o1;
		HotelOrderRowCommission A2 = (HotelOrderRowCommission) o2;
		return A1.getCompanyId().compareTo(A2.getCompanyId());
	}
}