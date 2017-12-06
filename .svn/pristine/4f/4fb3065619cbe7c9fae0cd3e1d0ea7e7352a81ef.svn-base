package com.tayyarah.flight.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.apiconfig.model.BluestarConfig;
import com.tayyarah.apiconfig.model.TravelportConfig;
import com.tayyarah.common.entity.FlightAndHotelBookApiResponse;
import com.tayyarah.common.entity.OrderCustomer;
import com.tayyarah.common.entity.PaymentTransaction;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.util.RandomConfigurationNumber;
import com.tayyarah.configuration.CommonConfig;
import com.tayyarah.flight.entity.FlightBookingDetailsTemp;
import com.tayyarah.flight.entity.FlightBookingKeysTemp;
import com.tayyarah.flight.entity.FlightFareAlertConnectingFlight;
import com.tayyarah.flight.entity.FlightFareAlertDetail;
import com.tayyarah.flight.entity.FlightOrderCustomer;
import com.tayyarah.flight.entity.FlightOrderCustomerPriceBreakup;
import com.tayyarah.flight.entity.FlightOrderCustomerSSR;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.flight.entity.FlightOrderRowCancellation;
import com.tayyarah.flight.entity.FlightOrderRowCommission;
import com.tayyarah.flight.entity.FlightOrderRowMarkup;
import com.tayyarah.flight.entity.FlightOrderTripDetail;
import com.tayyarah.flight.entity.FlightSearchDetailsTemp;
import com.tayyarah.flight.model.FareFlightSegment;
import com.tayyarah.flight.model.UAPISearchFlightKeyMap;
import com.tayyarah.flight.service.db.FlightDataBaseServices;
import com.tayyarah.user.entity.User;
import com.tayyarah.user.entity.UserWallet;
import com.tayyarah.user.entity.WalletAmountTranferHistory;



public class FlightBookingIMP implements FlightBookingDao {

	@Autowired
	private SessionFactory sessionFactory;
	Logger logger = Logger.getLogger(FlightBookingIMP.class);

	/* Insert the agent details into the database */
	@Override
	public synchronized void insertOrderCustomerDetails(OrderCustomer oc) throws Exception {
		Session session = null;
		Transaction transaction = null;
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		session.saveOrUpdate(oc);
		transaction.commit();
		session.close();
	}

	/* Update OrderCustomer Details */
	public synchronized void updateOrderCustomerDetails(OrderCustomer oc)  {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(oc);
			transaction.commit();
		}catch(Exception e){
			logger.error("updateOrderCustomerDetails Exception", e);
		}finally {
			session.close();
		}
	}

	/* Insert FlightOrderCustomer Details */
	@Override
	public synchronized void insertFlightOrderCustomerDetails(FlightOrderCustomer foc)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		session.saveOrUpdate(foc);
		transaction.commit();
		session.close();
	}

	/* Insert FlightOrderCustomerPriceBreakupDetails  */
	@Override
	public synchronized void insertFlightOrderCustomerPriceBreakupDetails(
			FlightOrderCustomerPriceBreakup foc) throws Exception {
		Session session = null;
		Transaction transaction = null;
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		session.saveOrUpdate(foc);
		transaction.commit();
		session.close();
	}

	/* Insert FlightOrderRow  */
	@Override
	public synchronized void insertFlightOrderRowDetails(FlightOrderRow foc)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		session.saveOrUpdate(foc);
		transaction.commit();
		session.close();
	}

	/* Insert FlightOrderTripDetail  */
	@Override
	public synchronized void insertFlightOrderTripDetailDetails(FlightOrderTripDetail foc)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		session.saveOrUpdate(foc);
		transaction.commit();
		session.close();
	}

	/* Insert PaymentTransactionDetails  */
	@Override
	public synchronized void insertPaymentTransactionDetails(PaymentTransaction foc)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		session.saveOrUpdate(foc);
		transaction.commit();
		session.close();
	}

	/* Insert FlightOrderCustomerSSR  */
	@Override
	public synchronized void insertFlightOrderCustomerSSRDetails(FlightOrderCustomerSSR focs)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		session.saveOrUpdate(focs);
		transaction.commit();
		session.close();
	}

	/* Update FlightOrderCustomerDetails  */
	@Override
	public synchronized  void updateFlightOrderCustomerDetails(List<FlightOrderCustomer> foc,long orderrowid)
			throws Exception {		
		List<FlightOrderCustomer> flightOrderCustomerlist = null;
		Session session = null;
		Transaction transaction = null;		
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(FlightOrderCustomer.class);
			criteria.add(Restrictions.eq("flightOrderRow.id", orderrowid));
			flightOrderCustomerlist =  criteria.list();
			for(int i = 0; i < flightOrderCustomerlist.size(); i++){
				FlightOrderCustomer flightOrderCustomer = flightOrderCustomerlist.get(i);			
				FlightOrderCustomer flightOrderCustomerupdate = (FlightOrderCustomer) session.get(FlightOrderCustomer.class, flightOrderCustomer.getId());
				flightOrderCustomerupdate.setEticketid(foc.get(i).getEticketid());
				flightOrderCustomerupdate.setEticketnumber(foc.get(i).getEticketnumber());
				session.update(flightOrderCustomerupdate);
			}
			transaction.commit();
		} catch(HibernateException e) {
			if(transaction!=null)
				transaction.rollback();
			logger.error("Exception", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		finally{
			session.close();
		}
	}

	/* Update FlightOrderCustomerPriceBreakupDetails  */
	@Override
	public synchronized  void updateFlightOrderCustomerPriceBreakupDetails(List<FlightOrderCustomerPriceBreakup> focp,long orderrowid)
			throws Exception {		
		List<FlightOrderCustomerPriceBreakup> FlightOrderCustomerPriceBreakuplist = null;		
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(FlightOrderCustomerPriceBreakup.class);
			criteria.add(Restrictions.eq("flightOrderRow.id", orderrowid));
			FlightOrderCustomerPriceBreakuplist =  criteria.list();
			for(int i = 0; i < FlightOrderCustomerPriceBreakuplist.size(); i++){
				FlightOrderCustomerPriceBreakup flightOrderCustomerPriceBreakup = FlightOrderCustomerPriceBreakuplist.get(i);				
				FlightOrderCustomerPriceBreakup flightOrderCustomerPriceBreakupupdate = (FlightOrderCustomerPriceBreakup) session.get(FlightOrderCustomerPriceBreakup.class, flightOrderCustomerPriceBreakup.getId());
				flightOrderCustomerPriceBreakupupdate.setBaseFare(focp.get(i).getBaseFare());
				flightOrderCustomerPriceBreakupupdate.setTax(focp.get(i).getTax());
				flightOrderCustomerPriceBreakupupdate.setTotal(focp.get(i).getTotal());
				flightOrderCustomerPriceBreakupupdate.setPublishedDiscount(focp.get(i).getPublishedDiscount());
				flightOrderCustomerPriceBreakupupdate.setSupplierDiscount(focp.get(i).getSupplierDiscount());
				flightOrderCustomerPriceBreakupupdate.setSystemDiscount(focp.get(i).getSystemDiscount());
				flightOrderCustomerPriceBreakupupdate.setUpdatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
				session.update(flightOrderCustomerPriceBreakupupdate);
			}
			transaction.commit();

		} catch(HibernateException e) {
			if(transaction!=null)
				transaction.rollback();
			logger.error("Exception", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		finally{
			session.close();
		}
	}

	/* Update PNR in FlightOrderRow */
	@Override
	public synchronized  void updatePNR(String pnr, String orderID)
			throws HibernateException, NumberFormatException, Exception {
		List<FlightOrderRow> list = null;
		Session session = null;
		Transaction transaction = null;
		FlightOrderRow flightOrderRow = null;
		Long id = 0L;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			try {
				Criteria criteria = session.createCriteria(FlightOrderRow.class);
				criteria.add(Restrictions.eq("orderId", orderID));
				list = criteria.list();
			} catch (Exception e) {
				logger.error("Exception", e);
			}
			if(list.size() > 0){
				id = list.get(0).getId();
				flightOrderRow = (FlightOrderRow) session.get(FlightOrderRow.class, id);
				if (!transaction.wasCommitted()){
					transaction.commit();
				}
				flightOrderRow.setPnr(pnr);
				flightOrderRow.setStatusAction("Confirmed");
				flightOrderRow.setPaymentStatus("Success");
				flightOrderRow.setBookingDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				if (pnr.equals("0")) {
					flightOrderRow.setPaymentStatus("Failed");
					flightOrderRow.setStatusAction("Failed");
				}
				if (pnr.equals("0#")) {
					flightOrderRow.setStatusAction("Failed");
					flightOrderRow.setPaymentStatus("Failed");
				}
				if (pnr.equals("#0")) {
					flightOrderRow.setStatusAction("Failed");
					flightOrderRow
					.setPaymentStatus("insufficient wallet balance");
				}
				if (flightOrderRow.getStatusAction().equalsIgnoreCase(
						"Confirmed")) {		
					flightOrderRow.setInvoiceNo(RandomConfigurationNumber.generateFlightOfflineInvoiceNumber(id).toString());
				}
				Transaction trans = session.beginTransaction();
				session.update(flightOrderRow);
				trans.commit();
			}
		} catch (HibernateException e) {
			logger.error("Exception", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		finally {
			session.close();
		}

	}

	/* Get FlightOrderRow By OrderId */
	@Override
	public synchronized FlightOrderRow getflightorderrow(String orderID)
			throws HibernateException, NumberFormatException, Exception {
		List<FlightOrderRow> list = null;
		Session session = null;		
		FlightOrderRow flightOrderRow = null;
		Long id = 0L;
		try {
			session = sessionFactory.openSession();	
			Criteria criteria = session.createCriteria(FlightOrderRow.class);
			criteria.add(Restrictions.eq("orderId", orderID));
			list = criteria.list();			
			if(list.size() > 0){
				id = list.get(0).getId();
				flightOrderRow = (FlightOrderRow) session.get(FlightOrderRow.class, id);
			}
		} catch (HibernateException e) {
			logger.error("Exception", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}finally {
			session.close();
		}		
		return flightOrderRow;
	}

	/* Get FlightOrderRow By PNR */
	@Override
	public synchronized FlightOrderRow getflightorderrowbypnr(String pnr)
			throws HibernateException, NumberFormatException, Exception {
		List<FlightOrderRow> list = null;
		Session session = null;	
		FlightOrderRow flightOrderRow = null;
		Long id = 0L;
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(FlightOrderRow.class);
			criteria.add(Restrictions.eq("pnr", pnr));
			list = criteria.list();
			if(list.size() > 0){
				id = list.get(0).getId();
				flightOrderRow = (FlightOrderRow) session.get(FlightOrderRow.class, id);
			}	
		} catch (HibernateException e) {
			logger.error("Exception", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}finally {
			session.close();
		}		
		return flightOrderRow;
	}

	/* Get PriceKey From FlightBookingKeysTemp */
	@Override
	public synchronized FlightBookingKeysTemp getpricekey(String transactionkey)
			throws HibernateException, NumberFormatException, Exception {		
		Session session = null;
		FlightBookingKeysTemp flightBookingKeysTemp = null;	
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(FlightBookingKeysTemp.class);
			criteria.add(Restrictions.eq("transaction_key", transactionkey));
			flightBookingKeysTemp = (FlightBookingKeysTemp) criteria.uniqueResult();

		} catch (HibernateException e) {
			logger.error("Exception", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}finally {
			session.close();
		}		
		return flightBookingKeysTemp;
	}

	/* Update FlightOrderRow */
	@Override
	public synchronized  void updateFlightOrderRowDetails(FlightOrderRow foc)
			throws Exception {
		List<FlightOrderRow> list = null;
		Session session = null;
		Transaction transaction = null;
		FlightOrderRow flightOrderRow = null;
		Long id = 0L;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(FlightOrderRow.class);
			criteria.add(Restrictions.eq("id", foc.getId()));
			list = criteria.list();
			if(list.size() > 0){
				id = list.get(0).getId();
				flightOrderRow = (FlightOrderRow) session.get(
						FlightOrderRow.class, id);
				if (!transaction.wasCommitted()){
					transaction.commit();}

				flightOrderRow.setInsuranceOrderRowId(foc.getInsuranceOrderRowId());
				flightOrderRow.setIsInsuranceAdded(foc.getIsInsuranceAdded());
				Transaction trans = session.beginTransaction();
				session.update(flightOrderRow);
				trans.commit();
			}
		} catch (HibernateException e) {
			logger.error("Exception", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}finally {
			session.close();
		}	
	}

	/* Update Transactionkey in FlightBookingKeysTemp */
	@Override
	public synchronized  void updateKeyStatus(String transactionkey)
			throws HibernateException, NumberFormatException, Exception {
		List<FlightBookingKeysTemp> list = null;
		FlightBookingKeysTemp flightBookingKeys = null;
		int id = 0;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(FlightBookingKeysTemp.class);
			criteria = criteria.add(Restrictions.eq("transaction_key",transactionkey));
			list = criteria.list();
			if(list.size() > 0){
				id = list.get(id).getId();
				flightBookingKeys = (FlightBookingKeysTemp)session.get(FlightBookingKeysTemp.class, id);
				if (!transaction.wasCommitted())
				{ 
					transaction.commit();
				}
				flightBookingKeys.setActive(false);
				Transaction trans = session.beginTransaction();
				session.update(flightBookingKeys);
				trans.commit();
			}

		} catch (HibernateException e) {
			logger.error("Exception", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}finally {
			session.close();
		}

	}

	/* Get Status in FlightBookingKeysTemp */
	@Override
	@SuppressWarnings({ "unchecked", "finally" })
	public boolean getTransStatus(String transId) {
		FlightBookingKeysTemp flightBookingKeys = null;
		boolean keystatus = false;
		Session session = null;	
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(FlightBookingKeysTemp.class);
			criteria.add(Restrictions.eq("transaction_key", transId));
			List<FlightBookingKeysTemp> list = criteria.list();
			if (list.size() > 0) {
				flightBookingKeys = list.get(0);
				keystatus = flightBookingKeys.isActive();
			}
		}
		catch (HibernateException e) {
			logger.error("HibernateException", e);
		}
		catch (NumberFormatException ne) {
			logger.error("NumberFormatException", ne);
		} catch (Exception e) {
			logger.error("Exception", e);
		} finally {
			session.close();			
		}
		return keystatus;
	}

	/* Insert FlightBookingKeysTemp */
	@Override
	public synchronized void insertBookingDetails(FlightBookingDetailsTemp bookingDetailsToDb)
			throws HibernateException, Exception {
		Session session = null;
		Transaction transaction = null;
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		session.saveOrUpdate(bookingDetailsToDb);
		if (!transaction.wasCommitted()){
			transaction.commit();}
		session.close();
	}

	/* Get FlightBookingKeysTemp By OrderId */
	@Override
	public FlightBookingDetailsTemp getBookingDetailsToDb(String order_id) {	
		FlightBookingDetailsTemp bookingDetailsToDb = null;
		Session session = null;		
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(FlightBookingDetailsTemp.class);
			criteria = criteria.add(Restrictions.eq("order_id", order_id));
			List<FlightBookingDetailsTemp> list = criteria.list();			
			if (list.size() > 0) {
				bookingDetailsToDb = list.get(0);
			}		
		}
		catch (HibernateException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			logger.error("Exception", e);
		} finally {
			session.close();
		}
		return bookingDetailsToDb;
	}

	/* Update PaymentTransaction */
	@Override
	public synchronized  void updatePaymentStatus(PaymentTransaction paymentTransaction)
			throws HibernateException, Exception {

		List<PaymentTransaction> list = null;
		Session session = null;
		Transaction transaction = null;
		PaymentTransaction PaymentTransactionNEW = null;
		Long id = 0L;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();	
			Criteria criteria = session.createCriteria(PaymentTransaction.class);
			criteria.add(Restrictions.eq("refno", paymentTransaction.getRefno())).add(Restrictions.eq("api_transaction_id", paymentTransaction.getApi_transaction_id()));
			list = criteria.list();				
			if(list.size() > 0){
				id = list.get(0).getId();
				PaymentTransactionNEW = (PaymentTransaction) session.get(
						PaymentTransaction.class, id);
				if (!transaction.wasCommitted()){  transaction.commit();}
				PaymentTransactionNEW.setIsPaymentSuccess(paymentTransaction
						.getIsPaymentSuccess());
				PaymentTransactionNEW.setTransactionId(paymentTransaction
						.getTransactionId());
				PaymentTransactionNEW.setResponse_message(paymentTransaction
						.getResponse_message());
				PaymentTransactionNEW.setResponseCode(paymentTransaction
						.getResponseCode());
				PaymentTransactionNEW.setPayment_status(paymentTransaction
						.getPayment_status());
				Transaction transaction7 = session.beginTransaction();
				session.update(PaymentTransactionNEW);
				transaction7.commit();
			}
		} catch (HibernateException e) {
			logger.error("Exception", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		finally {
			session.close();
		}

	}

	/* Insert WalletAmountTranferHistory */
	@Override
	public synchronized  void insertwalletamount_tranfer_history(WalletAmountTranferHistory walletAmountTranferHistory){	
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			walletAmountTranferHistory.setCreatedAt(new Timestamp(new Date().getTime()));
			walletAmountTranferHistory.setParentUserId(0);
			session.save(walletAmountTranferHistory);
			transaction.commit();
			session.close();
		}catch(Exception e){
			logger.error("insertwalletamount_tranfer_history Exception "+ e.getLocalizedMessage());
		}
	}

	/* Update UserWallet */
	@Override
	public synchronized void updateWalletBalanceIfFailed(BigDecimal amount, int walletId,
			WalletAmountTranferHistory walletAmountTranferHistory, int travelType) throws HibernateException,
	NumberFormatException, Exception {
		Session session = null;
		Transaction transaction = null;
		boolean result = false;
		UserWallet agentWallet = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			agentWallet = (UserWallet) session.get(UserWallet.class, walletId);
			agentWallet.setWalletbalance(agentWallet.getWalletbalance().add(amount));
			agentWallet.setUpdatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
			session.update(agentWallet);
			transaction.commit();

			walletAmountTranferHistory.setClosingBalance(agentWallet.getWalletbalance().add(amount));
			walletAmountTranferHistory.setOpeningBalance(agentWallet.getWalletbalance());
			walletAmountTranferHistory.setRemarks("Flight Booking  - Refund");

			switch (travelType) {
			case 1:
				walletAmountTranferHistory.setAction("FlightBooking Failed");
				break;
			case 2:
				walletAmountTranferHistory.setAction("HotelBooking Failed");
				break;
			default:
				break;
			}
			insertwalletamount_tranfer_history(walletAmountTranferHistory);

		} catch (HibernateException e) {
			result = false;
			logger.error("Exception", e);
		} catch (Exception e) {
			result = false;
			logger.error("Exception", e);
		}
		finally {
			session.close();
		}
	}

	/* Insert/Update PaymentTransaction */
	@Override
	public synchronized PaymentTransaction insertPaymentTransactionDetail(
			PaymentTransaction paymentTransaction) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(paymentTransaction); 

			if (!transaction.wasCommitted())
				transaction.commit();				

		} catch (HibernateException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			if (!transaction.wasCommitted())
				transaction.commit();
			session.close();
		}
		return paymentTransaction;
	}

	/* Get PaymentTransaction */
	@Override
	public synchronized PaymentTransaction getPaymentTransactionDetail(String paymentref)
			throws Exception {
		List<PaymentTransaction> list = null;
		PaymentTransaction paymentTransaction = null;
		Long id = 0L;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(PaymentTransaction.class);
			criteria = criteria.add(Restrictions.eq("refno", paymentref));
			list = criteria.list();

			if(list.size() > 0){
				id = list.get(0).getId();
				paymentTransaction = (PaymentTransaction) session.get(
						PaymentTransaction.class, id);
				if (!transaction.wasCommitted()){
					transaction.commit();
				}
			}

		} catch (HibernateException e) {
			logger.error("Exception", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}finally {
			session.close();
		}		
		return paymentTransaction;
	}

	/* Get PaymentTransaction Count */
	@Override
	public int getPaymentTransactionsCount(String orderid) throws Exception {
		Long count = new Long("0");
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			Criteria crit = session.createCriteria(PaymentTransaction.class);
			crit.add(Restrictions.isNotNull("refno"));
			crit.add(Restrictions.eq("api_transaction_id", orderid));
			crit.setProjection(Projections.rowCount());
			count = (Long) crit.uniqueResult();

		} catch (HibernateException e) {
			logger.error("Exception", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}finally {
			session.close();
		}		
		return count.intValue();
	}

	/* Get PaymentTransactionList  */
	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentTransaction> getPaymentTransactionDetails(String orderid)
			throws Exception {
		List<PaymentTransaction> list = new ArrayList<PaymentTransaction>();
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(PaymentTransaction.class);
			criteria.add(Restrictions.eq("api_transaction_id", orderid));
			list = criteria.list();
		} catch (HibernateException e) {
			throw e;
		} finally {
			session.close();
		}
		return list;
	}

	/* Get OrderId From PaymentTransaction */
	@Override
	public String getorderID(String paymentGatewayId)
			throws HibernateException, Exception {
		// TODO Auto-generated method stub
		String orderID1 = "invalid";
		String orderID2 = "invalid";
		int count = 0;
		StringBuilder sb = new StringBuilder();
		List<PaymentTransaction> list = null;
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Criteria criteria = session	.createCriteria(PaymentTransaction.class);
			criteria = criteria.add(Restrictions.eq("refno", paymentGatewayId));
			list = criteria.list();

			if (list!=null && list.size() > 0) {
				orderID1 = list.get(0).getApi_transaction_id();
			}
			if (list!=null && list.size() > 1) {
				orderID2 = list.get(1).getApi_transaction_id();
			}
			if (!transaction.wasCommitted()){  
				transaction.commit();
			}
			if(!orderID1.equals("invalid")){
				try {
					transaction = session.beginTransaction();
					Criteria secondCriteria = session.createCriteria(PaymentTransaction.class);
					secondCriteria = secondCriteria.add(Restrictions.eq("api_transaction_id", orderID1));
					list = secondCriteria.list();
					count = list.size();				
					if (!transaction.wasCommitted()){
						transaction.commit();
					}
					sb.append("<orderid1>" + orderID1 + "</orderid1><orderid2>" + orderID2 + "</orderid2><count>" + count
							+ "</count>");
				} catch (Exception e) {
					logger.error("Exception", e);
				}
			}

		}catch (Exception e) {
			logger.error("Exception", e);
		}
		finally {
			session.close();
		}
		return sb.toString();
	}

	/* Insert FlightOrderRowCommission */
	@Override
	public synchronized  void insertCommission(FlightOrderRowCommission flightOrderRowCommission) throws Exception {	
		Session session = null;
		Transaction transaction = null;
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		session.saveOrUpdate(flightOrderRowCommission);
		transaction.commit();
		session.close();
	}

	/* Insert FlightOrderRowMarkup */
	@Override
	public synchronized  void insertMarkupDetails(FlightOrderRowMarkup flightOrderRowMarkup) {		
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(flightOrderRowMarkup);
			transaction.commit();
		}catch(Exception e){
			logger.error("Exception", e);
		}finally {
			session.close();
		}

	}

	/* UPDATE FlightOrderRow Values */
	@Override
	public synchronized  void updatePNRandWallet(String pnr, String orderID, String refNO)
			throws HibernateException, NumberFormatException, Exception {
		List<FlightOrderRow> list = null;
		Session session = null;
		Transaction transaction = null;
		FlightOrderRow flightOrderRow = null;
		Long id = 0L;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(FlightOrderRow.class);
			criteria = criteria.add(Restrictions.eq("orderId", orderID));
			list = criteria.list();
			if(list.size() > 0){
				id = list.get(0).getId();

				flightOrderRow = (FlightOrderRow) session.get(
						FlightOrderRow.class, id);
				if (!transaction.wasCommitted()){  transaction.commit();}
				flightOrderRow.setPnr(pnr);
				flightOrderRow.setApi_commit(refNO);
				flightOrderRow.setStatusAction("Confirmed");
				flightOrderRow.setPaymentStatus("Success");
				flightOrderRow.setBookingDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				if (pnr.equals("0")) {
					flightOrderRow.setPaymentStatus("Failed");
					flightOrderRow.setStatusAction("Failed");
				}
				if (pnr.equals("0#")) {
					flightOrderRow.setStatusAction("Failed");
					flightOrderRow.setPaymentStatus("Failed");
				}
				if (pnr.equals("#0")) {
					flightOrderRow.setStatusAction("Failed");
					flightOrderRow.setPaymentStatus("insufficient wallet balance");
				}
				if (flightOrderRow.getStatusAction().equalsIgnoreCase("Confirmed")) 
				{				
					flightOrderRow.setInvoiceNo(RandomConfigurationNumber.generateFlightOfflineInvoiceNumber(id).toString());
				}
				Transaction trans = session.beginTransaction();
				session.update(flightOrderRow);
				trans.commit();
			}
		} catch (HibernateException e) {
			logger.error("Exception", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}

		if (!CommonConfig.GetCommonConfig().getSystem_model().equalsIgnoreCase("CUTANDPAY")) {
			try {
				if (flightOrderRow!= null && flightOrderRow.getStatusAction().equalsIgnoreCase("Confirmed")) {

					// Update commission and markup

					List<FlightOrderRowCommission> flightOrderRowCommissionList = flightOrderRow
							.getFlightOrderRowCommissionList();
					if(flightOrderRowCommissionList.size()>1){
						Collections.sort(flightOrderRowCommissionList,
								new comapnyIDComparatorforWallet());
					}
					for (int m = 0; m < flightOrderRowCommissionList.size(); m++) {
						FlightOrderRowCommission flightOrderRowCommission = flightOrderRowCommissionList.get(m);
						if (flightOrderRowCommission.getRateType().equalsIgnoreCase("Commission")) {
							if (flightOrderRowCommission.getCommissionAmountValue().compareTo(new BigDecimal("0.00")) == 1) {
								if (m != 0) {									
									UpdateMarkupAndCommsionInWallet(
											flightOrderRowCommissionList.get(m - 1)
											.getCompanyId(),
											flightOrderRowCommission
											.getCommissionAmountValue(),
											"Flight Commission Shared", orderID,session);
								}
								UpdateMarkupAndCommsionInWallet(
										flightOrderRowCommission.getCompanyId(),
										flightOrderRowCommission
										.getCommissionAmountValue(),
										"Flight Commission Added", orderID,session);
							}
						}
					}
					// /////////////For tayyarah adding TDS.........adding 10%tds
					if (CommonConfig.GetCommonConfig().isIs_tayyarah_enabled()) {
						for (int m = 1; m < flightOrderRowCommissionList.size(); m++) {
							FlightOrderRowCommission flightOrderRowCommission = flightOrderRowCommissionList.get(m);
							if (flightOrderRowCommission.getRateType()
									.equalsIgnoreCase("Commission")) {
								if (flightOrderRowCommission
										.getCommissionAmountValue().compareTo(
												new BigDecimal("0.00")) == 1) {
									BigDecimal tdsAmount = new BigDecimal("0.00");

									tdsAmount = (flightOrderRowCommission
											.getCommissionAmountValue()
											.multiply(new BigDecimal(10)))
											.divide(new BigDecimal(100));
									if (m < flightOrderRowCommissionList.size() - 1) {

										tdsAmount = ((flightOrderRowCommission
												.getCommissionAmountValue()
												.subtract(flightOrderRowCommissionList
														.get(m + 1)
														.getCommissionAmountValue()))
												.multiply(new BigDecimal(10)))
												.divide(new BigDecimal(100));

									}
									UpdateMarkupAndCommsionInWallet(
											flightOrderRowCommissionList.get(m)
											.getCompanyId(), tdsAmount,
											"TDS on commission", orderID,session);
								}
							}
						}
					}
					// End of TDS

					// /////for markup
					List<FlightOrderRowMarkup> flightOrderRowMarkupList = flightOrderRow.getFlightOrderRowMarkupList();
					for (FlightOrderRowMarkup flightOrderRowMarkup : flightOrderRowMarkupList) {
						if (flightOrderRowMarkup!= null && flightOrderRowMarkup.getMarkUp()!=null && flightOrderRowMarkup.getMarkUp().compareTo(
								new BigDecimal("0.00")) == 1) {
							int noOfPassengers = (flightOrderRowMarkup.getFlightOrderRow() != null && flightOrderRowMarkup.getFlightOrderRow().getPassengerCount()>0)?flightOrderRowMarkup.getFlightOrderRow().getPassengerCount():0;
							BigDecimal totalMarkup = flightOrderRowMarkup.getMarkUp().multiply(new BigDecimal(noOfPassengers));
							UpdateMarkupAndCommsionInWallet(
									flightOrderRowMarkup.getCompanyId(),
									totalMarkup,
									"Flight Markup", orderID,session);
						}
					}
				}
			} catch (HibernateException e) {
				logger.error("Exception", e);
			} catch (Exception e) {
				logger.error("Exception", e);
			} finally {
				session.close();
			}
		}
	}

	/* UPDATE FlightOrderRow Values For TBO */
	@Override
	public synchronized  FlightOrderRow updatePNRandWalletTBO(String pnr, String orderID, String refNO,BigDecimal suppierdiscount,BigDecimal systemdiscount,BigDecimal publisheddiscount,String apitraceid)
			throws HibernateException, NumberFormatException, Exception {
		List<FlightOrderRow> list = null;
		Session session = null;
		Transaction transaction = null;
		FlightOrderRow flightOrderRow = null;
		Long id = 0L;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(FlightOrderRow.class);
			criteria = criteria.add(Restrictions.eq("orderId", orderID));
			list = criteria.list();
			if(list.size() > 0){
				id = list.get(0).getId();
				flightOrderRow = (FlightOrderRow) session.get(FlightOrderRow.class, id);
				if (!transaction.wasCommitted())
				{ 
					transaction.commit();
				}
				flightOrderRow.setPnr(pnr);
				flightOrderRow.setApi_commit(refNO);
				flightOrderRow.setStatusAction("Confirmed");
				flightOrderRow.setPaymentStatus("Success");
				flightOrderRow.setSupplierDiscount(suppierdiscount);
				flightOrderRow.setSystemDiscount(systemdiscount);
				flightOrderRow.setPublishedDiscount(publisheddiscount);
				flightOrderRow.setApitraceid(apitraceid);
				flightOrderRow.setBookingDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				if (pnr.equals("0")) {
					flightOrderRow.setPaymentStatus("Failed");
					flightOrderRow.setStatusAction("Failed");
				}
				if (pnr.equals("0#")) {
					flightOrderRow.setStatusAction("Failed");
					flightOrderRow.setPaymentStatus("Failed");
				}
				if (pnr.equals("#0")) {
					flightOrderRow.setStatusAction("Failed");
					flightOrderRow.setPaymentStatus("insufficient wallet balance");
				}
				if (flightOrderRow.getStatusAction().equalsIgnoreCase("Confirmed")) 
				{
					flightOrderRow.setInvoiceNo(RandomConfigurationNumber.generateFlightOfflineInvoiceNumber(id).toString());
				}
				Transaction trans = session.beginTransaction();
				session.update(flightOrderRow);
				trans.commit();
			}

		} catch (HibernateException e) {
			logger.error("Exception", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}		
		try {
			if (flightOrderRow != null && flightOrderRow.getStatusAction().equalsIgnoreCase(
					"Confirmed") && !CommonConfig.GetCommonConfig().getSystem_model().equalsIgnoreCase("CUTANDPAY")) {

				// Update commission and markup

				List<FlightOrderRowCommission> flightOrderRowCommissionList = flightOrderRow
						.getFlightOrderRowCommissionList();
				if(flightOrderRowCommissionList.size()>1){
					Collections.sort(flightOrderRowCommissionList,
							new comapnyIDComparatorforWallet());
				}				
				for (int m = 0; m < flightOrderRowCommissionList.size(); m++) {
					FlightOrderRowCommission flightOrderRowCommission = flightOrderRowCommissionList
							.get(m);
					if (flightOrderRowCommission.getRateType()
							.equalsIgnoreCase("Commission")) {
						if (flightOrderRowCommission.getCommissionAmountValue()
								.compareTo(new BigDecimal("0.00")) == 1) {
							if (m != 0) {							
								UpdateMarkupAndCommsionInWallet(
										flightOrderRowCommissionList.get(m - 1)
										.getCompanyId(),
										flightOrderRowCommission
										.getCommissionAmountValue(),
										"Flight Commission Shared", orderID,session);
							}
							UpdateMarkupAndCommsionInWallet(
									flightOrderRowCommission.getCompanyId(),
									flightOrderRowCommission
									.getCommissionAmountValue(),
									"Flight Commission Added", orderID,session);
						}
					}
				}

				// /////////////For tayyarah adding TDS.........adding 10%tds
				if (CommonConfig.GetCommonConfig().isIs_tayyarah_enabled()) {

					for (int m = 1; m < flightOrderRowCommissionList.size(); m++) {
						FlightOrderRowCommission flightOrderRowCommission = flightOrderRowCommissionList
								.get(m);
						if (flightOrderRowCommission.getRateType()
								.equalsIgnoreCase("Commission")) {
							if (flightOrderRowCommission
									.getCommissionAmountValue().compareTo(
											new BigDecimal("0.00")) == 1) {
								BigDecimal tdsAmount = new BigDecimal("0.00");

								tdsAmount = (flightOrderRowCommission
										.getCommissionAmountValue()
										.multiply(new BigDecimal(10)))
										.divide(new BigDecimal(100));
								if (m < flightOrderRowCommissionList.size() - 1) {
									tdsAmount = ((flightOrderRowCommission
											.getCommissionAmountValue()
											.subtract(flightOrderRowCommissionList
													.get(m + 1)
													.getCommissionAmountValue()))
											.multiply(new BigDecimal(10)))
											.divide(new BigDecimal(100));

								}
								UpdateMarkupAndCommsionInWallet(
										flightOrderRowCommissionList.get(m)
										.getCompanyId(), tdsAmount,
										"TDS on commission", orderID,session);

							}
						}
					}
				}
				// End of TDS

				// /////for markup
				List<FlightOrderRowMarkup> flightOrderRowMarkupList = flightOrderRow
						.getFlightOrderRowMarkupList();			
				for (FlightOrderRowMarkup flightOrderRowMarkup : flightOrderRowMarkupList) {
					if (flightOrderRowMarkup!= null && flightOrderRowMarkup.getMarkUp()!=null && flightOrderRowMarkup.getMarkUp().compareTo(
							new BigDecimal("0.00")) == 1) {
						int noOfPassengers = flightOrderRow != null?flightOrderRow.getPassengerCount():0;
						BigDecimal totalMarkup = flightOrderRowMarkup.getMarkUp().multiply(new BigDecimal(noOfPassengers));
						UpdateMarkupAndCommsionInWallet(
								flightOrderRowMarkup.getCompanyId(),
								totalMarkup,
								"Flight Markup", orderID,session);

					}
				}

			}
		} catch (HibernateException e) {
			logger.error("Exception", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		} finally {
			session.close();
		}

		return flightOrderRow;

	}

	// Update PNR and Status For Hold Booking
	@Override
	public synchronized  void updateHoldPNRTBO(String pnr, String orderID, String refNO,BigDecimal suppierdiscount,BigDecimal systemdiscount,BigDecimal publisheddiscount,String apitraceid,String statusaction)
			throws HibernateException, NumberFormatException, Exception {

		List<FlightOrderRow> list = null;
		Session session = null;
		Transaction transaction = null;
		FlightOrderRow flightOrderRow = null;
		Long id = 0L;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(FlightOrderRow.class);
			criteria = criteria.add(Restrictions.eq("orderId", orderID));
			list = criteria.list();
			if(list.size() > 0){
				id = list.get(0).getId();
				flightOrderRow = (FlightOrderRow) session.get(
						FlightOrderRow.class, id);
				if (!transaction.wasCommitted()){  transaction.commit();}
				flightOrderRow.setPnr(pnr);
				flightOrderRow.setApi_commit(refNO);
				flightOrderRow.setStatusAction(statusaction);
				flightOrderRow.setPaymentStatus("Pending");
				flightOrderRow.setSupplierDiscount(suppierdiscount);
				flightOrderRow.setSystemDiscount(systemdiscount);
				flightOrderRow.setPublishedDiscount(publisheddiscount);
				flightOrderRow.setApitraceid(apitraceid);
				flightOrderRow.setBookingDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				if(pnr!=null){
					if (pnr.equals("0")) {
						flightOrderRow.setPaymentStatus("Failed");
						flightOrderRow.setStatusAction("Failed");
					}
					if (pnr.equals("0#")) {
						flightOrderRow.setStatusAction("Failed");
						flightOrderRow.setPaymentStatus("Failed");
					}
					if (pnr.equals("#0")) {
						flightOrderRow.setStatusAction("Failed");
						flightOrderRow
						.setPaymentStatus("insufficient wallet balance");
					}
				}else{
					flightOrderRow.setPaymentStatus("Failed");
					flightOrderRow.setStatusAction("Failed");
				}
				if (flightOrderRow.getStatusAction().equalsIgnoreCase(
						"Confirmed")) {				
					flightOrderRow.setInvoiceNo(RandomConfigurationNumber.generateFlightOfflineInvoiceNumber(id).toString());
				}

				Transaction trans = session.beginTransaction();
				session.update(flightOrderRow);
				trans.commit();
			}

		} catch (HibernateException e) {
			logger.error("Exception", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		}		
		try {
			if (flightOrderRow != null && flightOrderRow.getStatusAction().equalsIgnoreCase(
					"Confirmed") && !CommonConfig.GetCommonConfig().getSystem_model().equalsIgnoreCase("CUTANDPAY")) {

				// Update commission and markup

				List<FlightOrderRowCommission> flightOrderRowCommissionList = flightOrderRow
						.getFlightOrderRowCommissionList();
				if(flightOrderRowCommissionList.size()>1){
					Collections.sort(flightOrderRowCommissionList,
							new comapnyIDComparatorforWallet());
				}				
				for (int m = 0; m < flightOrderRowCommissionList.size(); m++) {
					FlightOrderRowCommission flightOrderRowCommission = flightOrderRowCommissionList.get(m);
					if (flightOrderRowCommission.getRateType()
							.equalsIgnoreCase("Commission")) {
						if (flightOrderRowCommission.getCommissionAmountValue()
								.compareTo(new BigDecimal("0.00")) == 1) {
							if (m != 0) {							
								UpdateMarkupAndCommsionInWallet(
										flightOrderRowCommissionList.get(m - 1)
										.getCompanyId(),
										flightOrderRowCommission
										.getCommissionAmountValue(),
										"Flight Commission Shared", orderID,session);
							}
							UpdateMarkupAndCommsionInWallet(
									flightOrderRowCommission.getCompanyId(),
									flightOrderRowCommission
									.getCommissionAmountValue(),
									"Flight Commission Added", orderID,session);
						}
					}
				}

				// /////////////For tayyarah adding TDS.........adding 10%tds
				if (CommonConfig.GetCommonConfig().isIs_tayyarah_enabled()) {
					for (int m = 1; m < flightOrderRowCommissionList.size(); m++) {
						FlightOrderRowCommission flightOrderRowCommission = flightOrderRowCommissionList.get(m);
						if (flightOrderRowCommission.getRateType()
								.equalsIgnoreCase("Commission")) {
							if (flightOrderRowCommission
									.getCommissionAmountValue().compareTo(
											new BigDecimal("0.00")) == 1) {
								BigDecimal tdsAmount = new BigDecimal("0.00");
								tdsAmount = (flightOrderRowCommission
										.getCommissionAmountValue()
										.multiply(new BigDecimal(10)))
										.divide(new BigDecimal(100));
								if (m < flightOrderRowCommissionList.size() - 1) {
									tdsAmount = ((flightOrderRowCommission
											.getCommissionAmountValue()
											.subtract(flightOrderRowCommissionList
													.get(m + 1)
													.getCommissionAmountValue()))
											.multiply(new BigDecimal(10)))
											.divide(new BigDecimal(100));
								}
								UpdateMarkupAndCommsionInWallet(
										flightOrderRowCommissionList.get(m)
										.getCompanyId(), tdsAmount,
										"TDS on commission", orderID,session);

							}
						}
					}
				}
				// End of TDS

				// /////for markup
				List<FlightOrderRowMarkup> flightOrderRowMarkupList = flightOrderRow.getFlightOrderRowMarkupList();
				for (FlightOrderRowMarkup flightOrderRowMarkup : flightOrderRowMarkupList) {
					if (flightOrderRowMarkup!= null && flightOrderRowMarkup.getMarkUp()!=null && flightOrderRowMarkup.getMarkUp().compareTo(
							new BigDecimal("0.00")) == 1) {
						int noOfPassengers = (flightOrderRowMarkup.getFlightOrderRow() != null && flightOrderRowMarkup.getFlightOrderRow().getPassengerCount()>0)?flightOrderRowMarkup.getFlightOrderRow().getPassengerCount():0;
						BigDecimal totalMarkup = flightOrderRowMarkup.getMarkUp().multiply(new BigDecimal(noOfPassengers));
						UpdateMarkupAndCommsionInWallet(
								flightOrderRowMarkup.getCompanyId(),
								totalMarkup,
								"Flight Markup", orderID,session);
					}
				}
			}
		} catch (HibernateException e) {
			logger.error("Exception", e);
		} catch (Exception e) {
			logger.error("Exception", e);
		} finally {
			session.close();
		}

	}


	public synchronized void UpdateMarkupAndCommsionInWallet(String companyid,
			BigDecimal totalprice, String action, String orderID,Session currentSession)
					throws Exception {
		AppKeyVo appKeyVo= new AppKeyVo();
		appKeyVo.setCompanyId(1);
		appKeyVo.setConfigId(1);
		Transaction transaction = null;
		WalletAmountTranferHistory walletAmountTranferHistory = new WalletAmountTranferHistory();
		walletAmountTranferHistory.setActionId(orderID);
		if (CommonConfig.GetCommonConfig().isIs_lintas_enabled()) {
			walletAmountTranferHistory.setCurrency(TravelportConfig
					.GetTravelportConfig().DEFAULT_CURRENY);
		}
		if (CommonConfig.GetCommonConfig().isIs_tayyarah_enabled()) {
			walletAmountTranferHistory.setCurrency(BluestarConfig
					.GetBluestarConfig(appKeyVo).DEFAULT_CURRENCY);
		}

		User user = null;
		BigDecimal Walletbalance = null;
		boolean result = false;		
		try {		 
			transaction = currentSession.beginTransaction();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Criteria criteria = currentSession.createCriteria(User.class);
			criteria.add(Restrictions.eq("Companyid", Integer.parseInt(companyid)));
			List<User> list = criteria.list();
			if (list!=null && list.size() > 0) {
				user = list.get(0);
				Walletbalance = user.getAgentWallet().getWalletbalance();
			}
			if (!transaction.wasCommitted()){  transaction.commit();}

			walletAmountTranferHistory.setUserId(user.getId());
			walletAmountTranferHistory.setAction(action);
			walletAmountTranferHistory.setCompanyId(user.getCompanyid());
			walletAmountTranferHistory.setParentClosingBalance(new BigDecimal("0.00"));
			walletAmountTranferHistory.setParentOpeningBalance(new BigDecimal("0.00"));
			walletAmountTranferHistory.setTransactionType("Credit");
			walletAmountTranferHistory.setAmount(totalprice);
			walletAmountTranferHistory.setOpeningBalance(Walletbalance);

			if(action.contains("Markup"))
				walletAmountTranferHistory.setRemarks("Flight Markup amount");
			else if(action.contains("Commission"))
				walletAmountTranferHistory.setRemarks("Flight Commission amount");
			else
				walletAmountTranferHistory.setRemarks("Flight Markup / Commission amount");

			walletAmountTranferHistory.setWalletId(user.getAgentWallet().getWalletId());

			if (action.equalsIgnoreCase("Flight Markup")
					|| action.equalsIgnoreCase("Flight Commission Added")) {
				walletAmountTranferHistory.setClosingBalance(Walletbalance
						.add(totalprice));
				result = updateWalletBalance(Walletbalance.add(totalprice),
						user.getAgentWallet().getWalletId(),
						walletAmountTranferHistory,currentSession);
			} else {
				walletAmountTranferHistory.setClosingBalance(Walletbalance
						.subtract(totalprice));
				result = updateWalletBalance(
						Walletbalance.subtract(totalprice), user
						.getAgentWallet().getWalletId(),
						walletAmountTranferHistory,currentSession);
			}

		} catch (NumberFormatException ne) {
			result = false;
			logger.error("NumberFormatException", ne);

		} catch (Exception e) {
			logger.error("Exception", e);
			result = false;
		}
	}

	public synchronized boolean updateWalletBalance(BigDecimal amount, int walletId,
			WalletAmountTranferHistory AWTH,Session session) throws HibernateException,
	NumberFormatException, Exception {
		List<UserWallet> list = null;
		boolean result = false;
		UserWallet agentWallet = null;
		Long id = 0L;
		try {
			session = sessionFactory.openSession();
			Transaction userWalletTransaction = session.beginTransaction();

			agentWallet = (UserWallet) session.get(UserWallet.class, walletId);
			userWalletTransaction.commit();
			agentWallet.setWalletbalance(amount);

			Transaction secondUserWalletTransaction = session.beginTransaction();
			agentWallet.setUpdatedAt(new java.sql.Timestamp(Calendar
					.getInstance().getTime().getTime()));
			session.update(agentWallet);		
			secondUserWalletTransaction.commit();
			result = true;
			WalletAmountTranferHistory walletAmountTranferHistory = AWTH;
			AWTH = walletAmountTranferHistory;
			// Insert Into WalletAmountTranferHistory
			insertwalletamount_tranfer_history(walletAmountTranferHistory);

		} catch (HibernateException e) {
			result = false;
			logger.error("Exception", e);
		} catch (Exception e) {
			result = false;
			logger.error("Exception", e);
		}
		return result;
	}

	@Override
	public synchronized FlightOrderRow GetFlightBookingDetails(String Orderid){
		FlightOrderRow flightOrderRow = null;
		Session session = null;	
		try {
			session = sessionFactory.openSession();			
			Criteria criteria = session.createCriteria(FlightOrderRow.class);
			criteria.add(Restrictions.eq("orderId", Orderid));
			flightOrderRow = (FlightOrderRow) criteria.uniqueResult();

		}catch (HibernateException e) {
			e.printStackTrace();
			logger.error("Exception", e);
		}
		finally{
			session.close();

		}
		return flightOrderRow;
	}

	@Override
	public synchronized void SaveApiBookingStatus(FlightAndHotelBookApiResponse flightAndHotelBookApiResponse){
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(flightAndHotelBookApiResponse);
			transaction.commit();
			session.close();

		}catch (Exception e) {
			logger.error("Exception", e);
		}
	}


	public List<FlightOrderRowCancellation> getAllFailedCancellations(){
		Session session = null;
		List<FlightOrderRowCancellation> flightOrderRowCancellations=null;
		Double refundAmount = new Double("0.00");	
		String apiStatusCode = "1";
		int ChangeRequestStatus = 4;
		int noofattempts = 5;
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(FlightOrderRowCancellation.class);			
			criteria.add(Restrictions.eq("apirefundamount", refundAmount));	
			criteria.add(Restrictions.eq("apistatuscode", apiStatusCode));
			criteria.add(Restrictions.ne("ChangeRequestStatus", ChangeRequestStatus));	
			criteria.add(Restrictions.le("noofAttempt", noofattempts));
			criteria.addOrder(Order.desc("orderid"));
			flightOrderRowCancellations = criteria.list();
		}catch (Exception e) {
			logger.error("Exception", e);
		}
		finally{
			session.close();
		}

		return flightOrderRowCancellations;		
	}

	public FareFlightSegment getLowPriceFlightFareSegment(String transactionkey, String flightIndex,FlightTempAirSegmentDAO flightTempAirSegmentDAO){
		FareFlightSegment fareFlightSegment = null;
		try{
			FlightDataBaseServices DBS = new FlightDataBaseServices();
			FlightSearchDetailsTemp searchDetails = DBS.getUAPISearchFlightKeyMapUsingTransactionkey(transactionkey, flightTempAirSegmentDAO);
			byte[] abc = searchDetails.getUapiSearchFlightKeyMap();			
			UAPISearchFlightKeyMap uapiSearchFlightKeyMap = FlightDataBaseServices.convertByteArrayToUAPISearchFlightKeyMap(abc);						
			Map<String, FareFlightSegment> fareFlightSegmentMap = uapiSearchFlightKeyMap.getFareFlightSegmentMap();
			fareFlightSegment = fareFlightSegmentMap.get(flightIndex);

		}catch(Exception e){
			logger.error("getLowPriceFlightFareSegment Exception", e);
		}
		return fareFlightSegment;
	}
	public FlightFareAlertDetail insertLowFareFlightDetail(FlightFareAlertDetail flightFareAlertDetail){
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(flightFareAlertDetail);	
			if (!transaction.wasCommitted()){
				transaction.commit();}

		}catch(Exception e){
			logger.error("insertLowFareFlightDetail Exception", e);
		}finally {
			session.close();
		}
		return flightFareAlertDetail;
	}
	public void updateLowFareFlightDetail(FlightFareAlertDetail flightFareAlertDetail){
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(flightFareAlertDetail);	
			if (!transaction.wasCommitted()){
				transaction.commit();}

		}catch(Exception e){
			logger.error("updateLowFareFlightDetail Exception", e);
		}finally {
			session.close();
		}

	}

	public FlightFareAlertConnectingFlight insertFlightFareAlertConnectingFlight(FlightFareAlertConnectingFlight flightFareAlertConnectingFlight){
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(flightFareAlertConnectingFlight);	
			if (!transaction.wasCommitted()){
				transaction.commit();}

		}catch(Exception e){
			logger.error("insertFlightFareAlertConnectingFlight Exception", e);
		}finally {
			session.close();
		}
		return flightFareAlertConnectingFlight;
	}

}

class comapnyIDComparatorforWallet implements Comparator<Object> {
	@Override
	public int compare(Object o1, Object o2) {
		FlightOrderRowCommission A1 = (FlightOrderRowCommission) o1;
		FlightOrderRowCommission A2 = (FlightOrderRowCommission) o2;
		int firstCompanyId = Integer.parseInt(A1.getCompanyId());
		int secondCompanyId = Integer.parseInt(A2.getCompanyId());		
		if(firstCompanyId > secondCompanyId){
			return 1;
		}else if(firstCompanyId < secondCompanyId){
			return -1;
		}else{
			return 0;
		}
	}


}
