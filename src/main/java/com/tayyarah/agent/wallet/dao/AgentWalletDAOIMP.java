package com.tayyarah.agent.wallet.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.common.util.AmountRoundingModeUtil;
import com.tayyarah.company.entity.Company;
import com.tayyarah.user.entity.User;
import com.tayyarah.user.entity.UserWallet;
import com.tayyarah.user.entity.WalletAmountTranferHistory;

public class AgentWalletDAOIMP implements AgentWalletDAO {


	@Autowired
	private SessionFactory sessionFactory;

	Session session = null;
	Transaction transaction = null;

	Logger logger = Logger.getLogger(AgentWalletDAOIMP.class);

	@Override
	public 	synchronized boolean getWalletStatusForSpecial(String userid,BigDecimal totalprice , WalletAmountTranferHistory walletAmountTranferHistory,WalletAmountTranferHistory walletAmountTranferHistorySpecial,String action) throws Exception{
		User user = null;
		BigDecimal Walletbalance = null;
		boolean result=false;
		Session session=null;
		Transaction  transaction=null;
		logger.info("getWalletStatus method called :");
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Criteria criteria=session.createCriteria(User.class);
			criteria.add(Restrictions.eq("id", Integer.parseInt(userid)));
			List<User> list = criteria.list();
			if(list!=null && list.size()>0){
				user = list.get(0);
				Walletbalance =user.getAgentWallet().getWalletbalance();
			}
			transaction.commit();
			logger.info("Walletbalance  :"+Walletbalance);
			BigDecimal totalwithGST=totalprice;
			// if ( Walletbalance.compareTo(totalprice)> -1 ){
			if ((user.getAgentWallet().getWalletType().equalsIgnoreCase("prepaid") && Walletbalance.compareTo(totalwithGST)> -1 )|| ((user.getAgentWallet().getWalletType().equalsIgnoreCase("postpaid")) && (Walletbalance.subtract(totalwithGST).compareTo(user.getAgentWallet().getWalletBalanceRange())> -1) )){
				// if ( Walletbalance.compareTo(totalprice)> -1 ){

				walletAmountTranferHistory.setUserId( Integer.parseInt(userid));
				walletAmountTranferHistory.setAction(action);
				walletAmountTranferHistory.setClosingBalance(Walletbalance.subtract(walletAmountTranferHistory.getAmount()));
				walletAmountTranferHistory.setOpeningBalance(Walletbalance);
				walletAmountTranferHistory.setWalletId(user.getAgentWallet().getWalletId());
				walletAmountTranferHistory.setRemarks("Flight Booking payment");

				logger.info("wallet payment...setClosingBalance "+walletAmountTranferHistory.getClosingBalance());
				logger.info("wallet payment...setOpeningBalance "+walletAmountTranferHistory.getOpeningBalance());

				logger.info("user.getAgentWallet().getWalletId() "+user.getAgentWallet().getWalletId());
				result=  updateWalletBalance(Walletbalance.subtract(walletAmountTranferHistory.getAmount()), user.getAgentWallet().getWalletId(),walletAmountTranferHistory);

				/////////////
				Walletbalance=walletAmountTranferHistory.getClosingBalance();
				walletAmountTranferHistorySpecial.setUserId( Integer.parseInt(userid));
				walletAmountTranferHistorySpecial.setAction(action);
				walletAmountTranferHistorySpecial.setClosingBalance(Walletbalance.subtract(walletAmountTranferHistorySpecial.getAmount()));
				walletAmountTranferHistorySpecial.setOpeningBalance(Walletbalance);
				walletAmountTranferHistorySpecial.setWalletId(user.getAgentWallet().getWalletId());
				walletAmountTranferHistory.setRemarks("Flight Booking payment");

				result=  updateWalletBalance(Walletbalance.subtract(walletAmountTranferHistorySpecial.getAmount()), user.getAgentWallet().getWalletId(),walletAmountTranferHistorySpecial);

			}

		} catch (NumberFormatException ne) {
			result=false;
			logger.error("NumberFormatException", ne);

		} catch (Exception e) {
			logger.error("Exception", e);
			result=false;
		} finally {
			session.close();
			return result;
		}

	}


	@Override
	public synchronized boolean getWalletStatus(String userid,BigDecimal totalprice, WalletAmountTranferHistory walletAmountTranferHistory,BigDecimal Gstonmarkup,BigDecimal Gstonflight,String walletRemarks,boolean bookingstatus) throws Exception {
		Session session=null;
		Transaction transaction = null;
		BigDecimal Walletbalance = null;
		boolean result = false;
		logger.info("getWalletStatus method called :");
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("id", Integer.parseInt(userid)));
			User user = (User) criteria.uniqueResult();
			BigDecimal totalwithGST = totalprice.add(Gstonmarkup).add(Gstonflight);

			// If User is SuperUser/Agent/Distributor/Corporate Debit Money from Their Own wallet
			if(user != null){
				Criteria parentcriteria = session.createCriteria(Company.class);
				parentcriteria.add(Restrictions.eq("companyid", user.getCompanyid()));
				Company myCompany = (Company) parentcriteria.uniqueResult();
				if(myCompany.getCompanyRole()!=null && myCompany.getCompanyRole().isCorporate())
				{
					Criteria userCriteria = session.createCriteria(User.class);
					userCriteria.add(Restrictions.eq("Email", myCompany.getEmail()));
					user = (User) userCriteria.uniqueResult();
				}
				// Booking confirmed
				if(bookingstatus)
					result = debitUserWalletAmountForBooking(user,totalwithGST,walletAmountTranferHistory,walletRemarks);
				else
					result = creditUserWalletAmountForBookingFailed(user,totalwithGST,walletAmountTranferHistory,walletRemarks);


			}

			/*walletAmountTranferHistory.setUserId(Integer.parseInt(userid));
				walletAmountTranferHistory.setAmount(totalwithGST);
				walletAmountTranferHistory.setClosingBalance(Walletbalance.subtract(totalwithGST));
				walletAmountTranferHistory.setOpeningBalance(Walletbalance);
				walletAmountTranferHistory.setWalletId(user.getAgentWallet().getWalletId());

				result = updateWalletBalance(Walletbalance.subtract(totalwithGST), user.getAgentWallet().getWalletId(),walletAmountTranferHistory);
			 */

		} catch (NumberFormatException ne) {
			result = false;
			logger.error("NumberFormatException", ne);

		} catch (Exception e) {
			logger.error("Exception", e);
			result = false;
		} finally {
			session.close();
		}
		return result;
	}

	@Override
	public synchronized void SetBookingStatusInWallet(String userid,BigDecimal totalprice, WalletAmountTranferHistory walletAmountTranferHistory,String action,BigDecimal Gstonmarkup,BigDecimal Gstonflight) throws Exception {
		Session session=null;
		Transaction  transaction=null;
		User user = null;
		BigDecimal Walletbalance = null;
		boolean result=false;
		logger.info("getWalletStatus method called :");
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Criteria criteria=session.createCriteria(User.class);
			criteria.add(Restrictions.eq("id", Integer.parseInt(userid)));
			List<User> list = criteria.list();
			if(list!=null && list.size()>0){
				user = list.get(0);
				Walletbalance =user.getAgentWallet().getWalletbalance();
			}
			transaction.commit();
			BigDecimal totalwithGST=totalprice.add(Gstonmarkup).add(Gstonflight);

			// if ( Walletbalance.compareTo(totalprice)> -1 ){
			if ((user.getAgentWallet().getWalletType().equalsIgnoreCase("prepaid") && Walletbalance.compareTo(totalwithGST)> -1 )|| ((user.getAgentWallet().getWalletType().equalsIgnoreCase("postpaid")) && (Walletbalance.subtract(totalwithGST).compareTo(user.getAgentWallet().getWalletBalanceRange())> -1) )){
				// if ( Walletbalance.compareTo(totalprice)> -1 ){
				walletAmountTranferHistory.setUserId( Integer.parseInt(userid));
				walletAmountTranferHistory.setAction(action);
				walletAmountTranferHistory.setAmount(totalwithGST);
				walletAmountTranferHistory.setClosingBalance(Walletbalance.subtract(totalwithGST));
				walletAmountTranferHistory.setOpeningBalance(Walletbalance);
				walletAmountTranferHistory.setWalletId(user.getAgentWallet().getWalletId());
				walletAmountTranferHistory.setRemarks("Flight Booking payment");

				logger.info("SetBookingStatusInWallet wallet payment...setClosingBalance "+walletAmountTranferHistory.getClosingBalance());
				logger.info("SetBookingStatusInWallet wallet payment...setOpeningBalance "+walletAmountTranferHistory.getOpeningBalance());
				logger.info("SetBookingStatusInWallet user.getAgentWallet().getWalletId() "+user.getAgentWallet().getWalletId());

			}

		} catch (NumberFormatException ne) {

			logger.error("NumberFormatException", ne);

		} catch (Exception e) {
			logger.error("Exception", e);

		} finally {

			session.close();

		}
	}

	@Override
	public 	synchronized void SetBookingStatusInWalletForSpecial(String userid,BigDecimal totalprice , WalletAmountTranferHistory walletAmountTranferHistory,WalletAmountTranferHistory walletAmountTranferHistorySpecial,String action) throws Exception{
		Session session=null;
		Transaction  transaction=null;
		User user = null;
		BigDecimal Walletbalance = null;
		boolean result=false;
		logger.info("getWalletStatus method called :");
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Criteria criteria=session.createCriteria(User.class);
			criteria.add(Restrictions.eq("id", Integer.parseInt(userid)));
			List<User> list = criteria.list();
			if(list!=null && list.size()>0){
				user = list.get(0);
				Walletbalance =user.getAgentWallet().getWalletbalance();
			}
			transaction.commit();
			logger.info("Walletbalance  :"+Walletbalance);
			BigDecimal totalwithGST=totalprice;
			// if ( Walletbalance.compareTo(totalprice)> -1 ){
			if ((user.getAgentWallet().getWalletType().equalsIgnoreCase("prepaid") && Walletbalance.compareTo(totalwithGST)> -1 )|| ((user.getAgentWallet().getWalletType().equalsIgnoreCase("postpaid")) && (Walletbalance.subtract(totalwithGST).compareTo(user.getAgentWallet().getWalletBalanceRange())> -1) )){
				// if ( Walletbalance.compareTo(totalprice)> -1 ){

				walletAmountTranferHistory.setUserId( Integer.parseInt(userid));
				walletAmountTranferHistory.setAction(action);
				walletAmountTranferHistory.setClosingBalance(Walletbalance.subtract(walletAmountTranferHistory.getAmount()));
				walletAmountTranferHistory.setOpeningBalance(Walletbalance);
				walletAmountTranferHistory.setWalletId(user.getAgentWallet().getWalletId());
				walletAmountTranferHistory.setRemarks("Flight Booking payment");

				logger.info("wallet payment...setClosingBalance "+walletAmountTranferHistory.getClosingBalance());
				logger.info("wallet payment...setOpeningBalance "+walletAmountTranferHistory.getOpeningBalance());

				logger.info("user.getAgentWallet().getWalletId() "+user.getAgentWallet().getWalletId());
				result=  updateWalletBalance(Walletbalance.subtract(walletAmountTranferHistory.getAmount()), user.getAgentWallet().getWalletId(),walletAmountTranferHistory);

				/////////////
				Walletbalance=walletAmountTranferHistory.getClosingBalance();
				walletAmountTranferHistorySpecial.setUserId( Integer.parseInt(userid));
				walletAmountTranferHistorySpecial.setAction(action);
				walletAmountTranferHistorySpecial.setClosingBalance(Walletbalance.subtract(walletAmountTranferHistorySpecial.getAmount()));
				walletAmountTranferHistorySpecial.setOpeningBalance(Walletbalance);
				walletAmountTranferHistory.setRemarks("Flight Booking payment");

				walletAmountTranferHistorySpecial.setWalletId(user.getAgentWallet().getWalletId());

			}

		} catch (NumberFormatException ne) {

			logger.error("NumberFormatException", ne);

		} catch (Exception e) {
			logger.error("Exception", e);

		} finally {
			session.close();

		}

	}

	@Override
	public boolean updateWalletBalance(BigDecimal amount,int walletId,WalletAmountTranferHistory AWTH)
			throws HibernateException, NumberFormatException, Exception {
		logger.info("updateWalletBalance method called : "+AWTH.getClosingBalance());
		List<UserWallet> list=null;
		boolean result=false;
		UserWallet agentWallet =null;
		Long id=0L;
		try{
			//session = sessionFactory.openSession();
			Transaction transaction2 = session.beginTransaction();
			agentWallet =  (UserWallet) session.get(UserWallet.class,walletId);
			transaction2.commit();
			agentWallet.setWalletbalance(amount);
			Transaction transaction7 = session.beginTransaction();
			agentWallet.setUpdatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
			session.update(agentWallet);
			logger.info("UserWallet updated : "+agentWallet.getWalletbalance());
			transaction7.commit();
			result=true;
			WalletAmountTranferHistory AWTHnew=AWTH;
			AWTH=AWTHnew;
			insertwalletamount_tranfer_history(AWTHnew);
		} catch (HibernateException e) {
			result=false;
			logger.error("Exception", e);
		} catch (Exception e) {
			result=false;
			logger.error("Exception", e);
		}

		//session.getTransaction().commit();
		finally{
			return result;
		}
	}

	@Override
	public void insertwalletamount_tranfer_history(WalletAmountTranferHistory AWTH) throws Exception {

		logger.info("updateWalletBalanceIfFailed method called :"+AWTH.getId()+" wallet amount :"+AWTH.getOpeningBalance());
		//session = sessionFactory.openSession();
		AWTH.setParentClosingBalance(new BigDecimal("0.0"));
		AWTH.setParentOpeningBalance(new BigDecimal("0.0"));
		AWTH.setParentUserId(0);
		Transaction transaction3 = session.beginTransaction();
		AWTH.setCreatedAt(new Timestamp(new Date().getTime()));
		session.save(AWTH);
		transaction3.commit();

	}
	public UserWallet updateUserWalletByWalletId(User user,BigDecimal newDepositBalance)
	{
		Session session=null;
		Transaction  transaction=null;
		UserWallet userWallet=null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			userWallet=(UserWallet) session.get(UserWallet.class, user.getAgentWallet().getWalletId());
			userWallet.setTransactionType(user.getAgentWallet().getTransactionType());
			userWallet.setDepositBalance(newDepositBalance);
			session.update(userWallet);
			transaction.commit();
		}
		catch (HibernateException e) {
			if(transaction!=null)
				transaction.rollback();
			logger.error("--------------HibernateException-----------------"+e.getMessage());

		}finally {
			if(session!=null && session.isOpen())
				session.close();
		}
		return userWallet;
	}

	public synchronized WalletAmountTranferHistory insertWalletAmountTransferHistory(WalletAmountTranferHistory walletAmountTranferHistory)
	{
		Session session =null;
		Transaction transaction=null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(walletAmountTranferHistory);
			transaction.commit();

		}catch (HibernateException e) {
			if(transaction!=null){
				transaction.rollback();
			}

			logger.error("-------HibernateException-------"+  e.getMessage());
		}
		finally {
			if(session!=null && session.isOpen())
				session.close();
		}
		return walletAmountTranferHistory;
	}

	public UserWallet updateParentWallet(UserWallet parentWalletObj)
	{
		Session session =null;
		Transaction transaction=null;
		try{
			if(parentWalletObj!=null){
				session = sessionFactory.openSession();
				transaction = session.beginTransaction();
				session.update(parentWalletObj);
				logger.info("--------------parent wallet balance---------------------"+parentWalletObj.getWalletbalance());
				transaction.commit();
			}
		}
		catch (HibernateException e) {
			if(transaction!=null)
				transaction.rollback();
			logger.error("--------------HibernateException-----------------"+e.getMessage());

		}finally {
			if(session!=null && session.isOpen())
				session.close();
		}
		return parentWalletObj;
	}

	private boolean debitUserWalletAmountForBooking(User user,BigDecimal totalprice,WalletAmountTranferHistory walletAmountTranferHistory,String walletRemarks){
		boolean result = false;
		try{
			BigDecimal finalPrice = totalprice;
			//			if ((user.getAgentWallet().getWalletType().equalsIgnoreCase("prepaid") && Walletbalance.compareTo(finalPrice)> -1 )|| ((user.getAgentWallet().getWalletType().equalsIgnoreCase("postpaid")) && (Walletbalance.subtract(finalPrice).compareTo(user.getAgentWallet().getWalletBalanceRange())> -1) )){
			String orderId= walletAmountTranferHistory.getActionId();
			BigDecimal totWalletBalance=user.getAgentWallet().getWalletbalance().setScale(5, BigDecimal.ROUND_UP).subtract(user.getAgentWallet().getWalletBalanceRange().setScale(5, BigDecimal.ROUND_UP));
			if(user!=null && user.getAgentWallet().getDepositBalance()!=null && AmountRoundingModeUtil.roundingMode(finalPrice).compareTo(user.getAgentWallet().getDepositBalance()) < 0){
				BigDecimal openingBalance = user.getAgentWallet().getDepositBalance();
				BigDecimal newDepositBalance= openingBalance.subtract(finalPrice);
				UserWallet newWalletObj= updateUserWalletByWalletId(user,newDepositBalance);
				if(newWalletObj!=null){
					WalletAmountTranferHistory  tranferHistory=new WalletAmountTranferHistory();
					tranferHistory.setCurrency(newWalletObj.getCurrencyCode());
					tranferHistory.setAmount(AmountRoundingModeUtil.roundingMode(finalPrice));
					tranferHistory.setWalletId(newWalletObj.getWalletId());
					tranferHistory.setOpeningBalance(openingBalance);
					tranferHistory.setClosingBalance(newDepositBalance);
					tranferHistory.setUserId(user.getId());
					tranferHistory.setCompanyId(user.getCompanyid());
					tranferHistory.setParentUserId(0);
					tranferHistory.setActionId(orderId);
					tranferHistory.setTransactionType("Withdrawal");
					tranferHistory.setCreatedAt(new Timestamp(new Date().getTime()));
					tranferHistory.setParentOpeningBalance(new BigDecimal("0"));
					tranferHistory.setRemarks(walletRemarks);
					tranferHistory.setParentClosingBalance(new BigDecimal("0"));
					tranferHistory.setAction(walletRemarks);
					tranferHistory.setFailed(false);
					tranferHistory = insertWalletAmountTransferHistory(tranferHistory);
					result = true;
				}

			}
			else if(AmountRoundingModeUtil.roundingMode(finalPrice).compareTo(totWalletBalance) < 0){
				BigDecimal openingBalance = user.getAgentWallet().getWalletbalance();
				BigDecimal newWalletBalance= openingBalance.subtract(finalPrice);
				UserWallet userWalletNew = user.getAgentWallet();
				userWalletNew.setWalletbalance(newWalletBalance);
				UserWallet newWalletObj = updateParentWallet(userWalletNew);
				if(newWalletObj!=null){
					WalletAmountTranferHistory  tranferHistory=new WalletAmountTranferHistory();
					tranferHistory.setCurrency(newWalletObj.getCurrencyCode());
					tranferHistory.setAmount(AmountRoundingModeUtil.roundingMode(finalPrice));
					tranferHistory.setWalletId(newWalletObj.getWalletId());
					tranferHistory.setOpeningBalance(openingBalance);
					tranferHistory.setClosingBalance(newWalletBalance);
					tranferHistory.setUserId(user.getId());
					tranferHistory.setCompanyId(user.getCompanyid());
					tranferHistory.setParentUserId(0);
					tranferHistory.setActionId(orderId);
					tranferHistory.setTransactionType("Debit");
					tranferHistory.setCreatedAt(new Timestamp(new Date().getTime()));
					tranferHistory.setParentOpeningBalance(new BigDecimal("0"));
					tranferHistory.setRemarks(walletRemarks);
					tranferHistory.setParentClosingBalance(new BigDecimal("0"));
					tranferHistory.setAction(walletRemarks);
					tranferHistory.setFailed(false);
					tranferHistory = insertWalletAmountTransferHistory(tranferHistory);
					result = true;
				}
			}
			else{
				return false;
			}
		}catch(Exception e){
		}
		return result;
	}





	public boolean creditUserWalletAmountForBookingFailed(User user,BigDecimal totalprice,WalletAmountTranferHistory walletAmountTranferHistory,String walletRemarks){
		boolean result = false;
		try{

			BigDecimal finalPrice = totalprice;
			String orderId = walletAmountTranferHistory.getActionId();
			BigDecimal totWalletBalance = user.getAgentWallet().getWalletbalance().setScale(5, BigDecimal.ROUND_UP).subtract(user.getAgentWallet().getWalletBalanceRange().setScale(5, BigDecimal.ROUND_UP));
			if(user!=null && user.getAgentWallet().getDepositBalance()!=null && AmountRoundingModeUtil.roundingMode(finalPrice).compareTo(user.getAgentWallet().getDepositBalance()) < 0 ){
				BigDecimal openingBalance = user.getAgentWallet().getDepositBalance();
				BigDecimal newDepositBalance= openingBalance.add(finalPrice);
				UserWallet newWalletObj= updateUserWalletByWalletId(user,newDepositBalance);
				if(newWalletObj!=null){
					WalletAmountTranferHistory  tranferHistory=new WalletAmountTranferHistory();
					tranferHistory.setCurrency(newWalletObj.getCurrencyCode());
					tranferHistory.setAmount(AmountRoundingModeUtil.roundingMode(finalPrice));
					tranferHistory.setWalletId(newWalletObj.getWalletId());
					tranferHistory.setOpeningBalance(openingBalance);
					tranferHistory.setClosingBalance(newDepositBalance);
					tranferHistory.setUserId(user.getId());
					tranferHistory.setCompanyId(user.getCompanyid());
					tranferHistory.setParentUserId(0);
					tranferHistory.setActionId(orderId);
					tranferHistory.setTransactionType("Deposit");
					tranferHistory.setCreatedAt(new Timestamp(new Date().getTime()));
					tranferHistory.setParentOpeningBalance(new BigDecimal("0"));
					tranferHistory.setRemarks(walletRemarks);
					tranferHistory.setParentClosingBalance(new BigDecimal("0"));
					tranferHistory.setAction(walletRemarks);
					tranferHistory.setFailed(true);
					tranferHistory = insertWalletAmountTransferHistory(tranferHistory);
					result= true;
				}

			}
			else if(AmountRoundingModeUtil.roundingMode(finalPrice).compareTo(totWalletBalance) < 0){
				BigDecimal openingBalance = user.getAgentWallet().getWalletbalance();
				BigDecimal newWalletBalance= openingBalance.add(finalPrice);
				UserWallet userWalletNew = user.getAgentWallet();
				userWalletNew.setWalletbalance(newWalletBalance);
				UserWallet newWalletObj = updateParentWallet(userWalletNew);
				if(newWalletObj!=null){
					WalletAmountTranferHistory  tranferHistory=new WalletAmountTranferHistory();
					tranferHistory.setCurrency(newWalletObj.getCurrencyCode());
					tranferHistory.setAmount(AmountRoundingModeUtil.roundingMode(finalPrice));
					tranferHistory.setWalletId(newWalletObj.getWalletId());
					tranferHistory.setOpeningBalance(openingBalance);
					tranferHistory.setClosingBalance(newWalletBalance);
					tranferHistory.setUserId(user.getId());
					tranferHistory.setCompanyId(user.getCompanyid());
					tranferHistory.setParentUserId(0);
					tranferHistory.setActionId(orderId);
					tranferHistory.setTransactionType("Credit");
					tranferHistory.setCreatedAt(new Timestamp(new Date().getTime()));
					tranferHistory.setParentOpeningBalance(new BigDecimal("0"));
					tranferHistory.setRemarks(walletRemarks);
					tranferHistory.setParentClosingBalance(new BigDecimal("0"));
					tranferHistory.setAction(walletRemarks);
					tranferHistory.setFailed(true);
					tranferHistory = insertWalletAmountTransferHistory(tranferHistory);
					result= true;
				}
			}
			else{
				return false;
			}
			if(result==true)
			{
				try{
					updateWalletHistoryWhenBookingFailed(orderId);
				}
				catch(Exception e)
				{
					logger.info("--------------Failed Booking Status in wallet Table---------------------"+e.getMessage());
				}
			}
		}catch(Exception e){
			logger.info("--------------Failed Booking Payment Update Change Error ---------------------"+e.getMessage());
		}


		return result;
	}

	public boolean walletTransferHistoryUpdateWithInvoiceNo(String orderid,String invoiceno){
		boolean isUpdated = false;
		Session session =null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(WalletAmountTranferHistory.class);
			criteria.add(Restrictions.eq("actionId", orderid));
			List<WalletAmountTranferHistory> walletAmountTranferHistoryList = criteria.list();
			transaction = session.beginTransaction();
			for (WalletAmountTranferHistory walletAmountTranferHistory : walletAmountTranferHistoryList) {
				if(invoiceno == null || invoiceno.equalsIgnoreCase(""))
					walletAmountTranferHistory.setInvoiceNo("N/A");
				else
					walletAmountTranferHistory.setInvoiceNo(invoiceno);

				session.update(walletAmountTranferHistory);
			}

			transaction.commit();	
			session.close();
			isUpdated = true;

		}catch(Exception e){
			logger.info("--------------walletTransferHistoryUpdateWithInvoiceNo---------------------"+e);
		}
		return isUpdated;
	}

	public  List<User> getUserList(List<Integer> useridlist){
		Session session =null;		
		List<User> userList = new ArrayList<>();
		try{
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(User.class);
			for (Integer userid : useridlist) {
				criteria.add(Restrictions.eq("id", userid));
				User user = (User) criteria.uniqueResult();
				userList.add(user);
			}

			session.close();
		}catch(Exception e){
			logger.info("--------------getUserList---------------------"+e);
		}
		return userList;
	}

	public enum CommonBookingStatusEnum {
		DEFAULT (0, "Default"),
		WALLET_ERROR (1, "Insufficient Wallet Balance."),
		FLIHT_BOOKING_SUCCESS(2, "FlightBooking Initiated"),
		HOTEL_BOOKING_SUCCESS(3, "HotelBooking Initiated"),
		CAR_BOOKING_SUCCESS(4, "CarBooking Initiated"),
		BUS_BOOKING_SUCCESS(5, "BusBooking Initiated"),
		TRAIN_BOOKING_SUCCESS(6, "TrainBooking Initiated"),
		VISA_BOOKING_SUCCESS(7, "VisaBooking Initiated"),
		INSURANCE_BOOKING_SUCCESS(8, "Insurance Booking Initiated"),
		FLIGHT_REMARKS(9, "Flight Booking payment"),
		HOTEL_REMARKS(10, "Hotel Booking payment"),
		CAR_REMARKS(11, "Car Booking payment"),
		BUS_REMARKS(12, "Bus Booking payment"),
		TRAIN_REMARKS(13, "Train Booking payment"),
		VISA_REMARKS(14, "Visa Booking payment"),
		INSURANCE_REMARKS(15, "Insurance Booking payment"),
		TRANSACTION_TYPE_DEBIT(16, "Debit"),
		TRANSACTION_TYPE_CREDIT(17, "Credit"),
		BOOKING_MODE_OFFLINE(18, "Offline"),
		BOOKING_MODE_ONLINE(19, "Online"),
		FLIGHT_FAILEDREMARKS(20, "Flight Booking Failed"),
		HOTEL_FAILEDREMARKS(21, "Hotel Booking Failed"),
		BUS_FAILEDREMARKS(22, "Bus Booking Failed"),
		INSURANCE_FAILEDREMARKS(23, "Insurance Booking Failed");

		public Integer getCode() {
			return code;
		}
		public String getMessage() {
			return message;
		}
		private final Integer code;
		private final String message;
		CommonBookingStatusEnum(Integer code, String message) {
			this.code = code;
			this.message = message;
		}

		public static CommonBookingStatusEnum getStatusEnum(Integer statusCode) {

			CommonBookingStatusEnum statusEnum = DEFAULT;
			if(statusCode == null)
				return DEFAULT;
			for (CommonBookingStatusEnum s : CommonBookingStatusEnum.values())
			{
				if(s.getCode() == statusCode)
				{
					statusEnum = s;
				}
			}
			return statusEnum;

		}
	}

	@Override
	public boolean checkWalletAmount(int userid, BigDecimal totalprice, BigDecimal Gstonmarkup, BigDecimal Gstonflights) throws Exception {
		Session session=null;
		boolean result = false;
		logger.info("checkWalletAmount method called :");
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("id", userid));
			User user = (User) criteria.uniqueResult();
			BigDecimal totalwithGST = totalprice.add(Gstonmarkup).add(Gstonflights);

			// If User is SuperUser/Agent/Distributor/Corporate Debit Money from Their Own wallet
			if(user != null){
				Criteria parentcriteria = session.createCriteria(Company.class);
				parentcriteria.add(Restrictions.eq("companyid", user.getCompanyid()));
				Company myCompany = (Company) parentcriteria.uniqueResult();
				if(myCompany.getCompanyRole()!=null && myCompany.getCompanyRole().isCorporate())
				{
					Criteria userCriteria = session.createCriteria(User.class);
					userCriteria.add(Restrictions.eq("Email", myCompany.getEmail()));
					user = (User) userCriteria.uniqueResult();
				}
				result = checkUserWalletAmountForBooking(user,totalwithGST);
			}

		} catch (NumberFormatException ne) {
			result = false;
			logger.error("NumberFormatException", ne);

		} catch (Exception e) {
			logger.error("Exception", e);
			result = false;
		} finally {
			session.close();
		}
		return result;
	}

	private boolean checkUserWalletAmountForBooking(User user,BigDecimal totalprice){
		boolean result = false;
		try{
			BigDecimal totWalletBalance=user.getAgentWallet().getWalletbalance().setScale(5, BigDecimal.ROUND_UP).subtract(user.getAgentWallet().getWalletBalanceRange().setScale(5, BigDecimal.ROUND_UP));
			if(user!=null && user.getAgentWallet().getDepositBalance()!=null && AmountRoundingModeUtil.roundingMode(totalprice).compareTo(user.getAgentWallet().getDepositBalance()) < 0){
				return true;
			}
			else if(AmountRoundingModeUtil.roundingMode(totalprice).compareTo(totWalletBalance) < 0){
				return true;
			}
			else{
				return false;
			}
		}catch(Exception e){
		}
		return result;
	}

	public void updateWalletHistoryWhenBookingFailed(String orderId) {
		String hqlUpdate = "update WalletAmountTranferHistory c set c.failed = :failed where c.actionId= :actionId";
		Session session=null;
		Transaction tx=null;
		try
		{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.createQuery(hqlUpdate).setBoolean("failed",true).setString("actionId",orderId).executeUpdate(); 
			session.close();
			tx.commit();
		}
		catch(HibernateException he){
			if(tx!=null)
				tx.rollback();
			logger.info("HibernateException------------------"+he.getMessage());
		}
		catch (Exception e){
			if(tx!=null)
				tx.rollback();
			logger.info("Exception------------------"+e.getMessage());
		}

		finally {
			if(session!=null && session.isOpen())
				session.close(); 
		}

	}
}


