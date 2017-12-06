package com.tayyarah.insurance.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.company.entity.Company;
import com.tayyarah.flight.entity.FlightCreditNote;
import com.tayyarah.hotel.entity.HotelCreditNote;
import com.tayyarah.insurance.entity.InsuranceCreditNote;
import com.tayyarah.user.entity.User;
import com.tayyarah.user.entity.UserWallet;
import com.tayyarah.user.entity.WalletAmountTranferHistory;




public class InsuranceCreditNoteDaoImp implements InsuranceCreditNoteDao ,Serializable{
	public static final Logger logger = Logger.getLogger(InsuranceCreditNoteDaoImp.class);	
	/**
	 * By Yogesh
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private SessionFactory  sessionFactory;
	Session session = null;
	Transaction tx = null;

	@Override
	public List<InsuranceCreditNote> getCreditNoteByUser(int id)
			throws HibernateException {	
		List<InsuranceCreditNote> userlist = null;
		try{
			session = sessionFactory.openSession();			
			Criteria criteria=session.createCriteria(InsuranceCreditNote.class);
			criteria.add(Restrictions.eq("id",id));			
			userlist = criteria.list();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			throw e; 
		}finally {
			if(session != null && session.isOpen())
				session.close();
		}
		return userlist;
	}

	@Override
	public InsuranceCreditNote getCreditNoteById(String id) throws HibernateException {
		InsuranceCreditNote creditNote = null;		
		try{
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(InsuranceCreditNote.class);
			criteria.add(Restrictions.eq("id",Integer.parseInt(id)));
			creditNote = (InsuranceCreditNote) criteria.uniqueResult();	
		}catch (HibernateException e) {			
			throw e; 
		}finally {
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}			
		}
		return creditNote;
	}

	public InsuranceCreditNote getCreditNoteByRowId(String id)
			throws HibernateException {
		Session sess = null;		
		InsuranceCreditNote insuranceCreditNote = null;			
		try{
			sess = sessionFactory.openSession();			
			Criteria criteria=sess.createCriteria(InsuranceCreditNote.class);
			criteria.add(Restrictions.eq("id", Integer.parseInt(id)));
			insuranceCreditNote = (InsuranceCreditNote) criteria.uniqueResult();	
		}catch (HibernateException e) {			
			throw e; 
		}finally {
			sess.close(); 
		}
		return insuranceCreditNote;
	}

	public List<WalletAmountTranferHistory> getWalletHistoryDetailsByFlightOrderId(String orderId) {		
		Session session = null;
		List<WalletAmountTranferHistory> historyList=null;
		try{
			session = sessionFactory.openSession();			
			Criteria criteria = session.createCriteria(WalletAmountTranferHistory.class);
			criteria.add(Restrictions.eq("actionId", orderId));
			historyList =  criteria.list();				
		}
		catch (HibernateException e) {
			logger.error("--------------HibernateException-----------------"+e.getMessage());
		}finally {
			session.close(); 
		}
		return historyList;
	}
	
	public synchronized UserWallet getWalletAmountByWalletId(WalletAmountTranferHistory walletAmountTranferHistory) {
		Session session = null;
		UserWallet userWallet = null;
		try{
			session = sessionFactory.openSession();			
			Criteria criteria = session.createCriteria(WalletAmountTranferHistory.class);
			criteria.add(Restrictions.eq("walletId", walletAmountTranferHistory.getWalletId()));
			userWallet = (UserWallet) criteria.uniqueResult();				
		}
		catch (HibernateException e) {
			logger.error("--------------HibernateException-----------------"+e.getMessage());
		}finally {
			session.close(); 
		}
		return userWallet;
	}

	public synchronized UserWallet getWalletAmountByWalletId(int walletId) {		
		UserWallet userWallet = null;
		Session session = null;
		try{
			session = sessionFactory.openSession();			
			Criteria criteria = session.createCriteria(WalletAmountTranferHistory.class);
			criteria.add(Restrictions.eq("walletId", walletId));
			userWallet = (UserWallet) criteria.uniqueResult();			
		}
		catch (HibernateException e) {
			logger.error("--------------HibernateException-----------------"+e.getMessage());
		}finally {
			session.close(); 
		}
		return userWallet;
	}

	public synchronized UserWallet updateUserWallet(UserWallet userWallet) {		
		UserWallet updateUserWallet = null;
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();			
			updateUserWallet =  (UserWallet) session.get(UserWallet.class,userWallet.getWalletId());
			updateUserWallet.setWalletbalance(userWallet.getWalletbalance());
			updateUserWallet.setUpdatedAt(new Timestamp(new Date().getTime()));
			session.save(updateUserWallet);
			transaction.commit();
		}
		catch(Exception e){
			if(transaction!=null){
				transaction.rollback();
			}
			logger.error("---------Exception---------"+e.getMessage());

		}finally{
			session.close();
		}
		return updateUserWallet;
	}

	public synchronized WalletAmountTranferHistory updateWalletHistory(String message, BigDecimal walletbalance, BigDecimal addAmount,WalletAmountTranferHistory walletAmountTranferHistory) {
		WalletAmountTranferHistory	updatedWalletHistory= new WalletAmountTranferHistory();
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			updatedWalletHistory.setAction(message);
			updatedWalletHistory.setActionId(walletAmountTranferHistory.getActionId());
			updatedWalletHistory.setCompanyId(walletAmountTranferHistory.getCompanyId());
			updatedWalletHistory.setAmount(walletAmountTranferHistory.getAmount());
			updatedWalletHistory.setOpeningBalance(walletbalance);
			updatedWalletHistory.setClosingBalance(addAmount);
			updatedWalletHistory.setCreatedAt(new Timestamp(new Date().getTime()));
			updatedWalletHistory.setCurrency(walletAmountTranferHistory.getCurrency());
			updatedWalletHistory.setParentClosingBalance(walletAmountTranferHistory.getParentClosingBalance());
			updatedWalletHistory.setParentOpeningBalance(walletAmountTranferHistory.getParentOpeningBalance());
			updatedWalletHistory.setParentUserId(walletAmountTranferHistory.getParentUserId());
			updatedWalletHistory.setUserId(walletAmountTranferHistory.getUserId());
			updatedWalletHistory.setWalletId(walletAmountTranferHistory.getWalletId());
			session.save(updatedWalletHistory);
			transaction.commit();
		}
		catch(Exception e){
			if(transaction!=null){
				transaction.rollback();
			}
			logger.error("---------Exception---------"+e.getMessage());

		}finally{
			session.close();
		}
		return updatedWalletHistory;	
	}

	public FlightCreditNote updateRefundingAmount(FlightCreditNote flightCreditNote) {		
		FlightCreditNote updateCreditNote   =  null;
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();			
			updateCreditNote =  (FlightCreditNote) session.get(FlightCreditNote.class,flightCreditNote.getId());
			updateCreditNote.setRefundedAmount(flightCreditNote.getRefundedAmount());
			updateCreditNote.setOrderUpdated(true);			
			updateCreditNote.setIssuedAt(new Timestamp(new Date().getTime()));
			session.save(updateCreditNote);
			transaction.commit();
		}
		catch(Exception e){
			if(transaction!=null){
				transaction.rollback();
			}
			logger.error("---------Exception---------"+e.getMessage());

		}finally{
			session.close();
		}
		return updateCreditNote;
	}

	public  List<User>  getAgentAddress(String userId){
		Session session = null;
		List<User> list=null;
		try{
			if(userId!=null){
				session = sessionFactory.openSession();			
				Criteria criteria = session.createCriteria(User.class);
				criteria.add(Restrictions.eq("id", Integer.parseInt(userId)));
				list = criteria.list();					
			}
		}
		catch (HibernateException e) {
			logger.error("--------------HibernateException-----------------"+e.getMessage());
		}finally {
			session.close(); 
		}
		return list;
	}

	public  User getParentAddress(int userId){
		Session session = null;
		User parentAgentAddress = null;
		try{
			session = sessionFactory.openSession();			
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("id", userId));
			parentAgentAddress =  (User) criteria.uniqueResult();		
		}
		catch (HibernateException e) {
			logger.error("--------------HibernateException-----------------"+e.getMessage());
		}finally {
			session.close(); 
		}
		return parentAgentAddress;
	}


	public Company getCompanyAddress(String companyId){
		Company company = null;
		Session session = null;
		try{
			if(companyId != null){
				session = sessionFactory.openSession();			
				Criteria criteria = session.createCriteria(Company.class);
				criteria.add(Restrictions.eq("companyid", Integer.parseInt(companyId)));
				company =  (Company) criteria.uniqueResult();			
			}
		}
		catch (HibernateException e) {
			logger.error("--------------HibernateException-----------------"+e.getMessage());
		}finally {
			session.close(); 
		}
		return company;
	}

	public int getParentUserIdLevel1(Integer userId) throws Exception
	{
		Session session = null;
		int parentUserid = 0;
		try{
			session = sessionFactory.openSession();			
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("id", userId));
			User user =  (User) criteria.uniqueResult();		
			if (user!=null) {
				parentUserid=user.getCreatedbyCompanyUserId();
			}
		}
		catch (HibernateException e) {
			logger.error("-------HibernateException-------"+  e.getMessage());
		}
		finally {
			session.close(); 
		}
		return parentUserid;
	}

	public List<Integer> getParentUserIdLevel2(Integer integer) throws Exception
	{
		List<Integer> userIds = new LinkedList<>();
		Session session = null;
		try{
			int parentUseridLevel1=getParentUserIdLevel1(integer);
			if(integer!=parentUseridLevel1){
				userIds.add(integer);
				userIds.add(parentUseridLevel1);
			}
			else{
				userIds.add(integer);
			}

			session = sessionFactory.openSession();			
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("id", parentUseridLevel1));
			User user =  (User) criteria.uniqueResult();				
			if (user != null) {
				int parentUseridLevel2 = user.getCreatedbyCompanyUserId();
				if(parentUseridLevel2 != parentUseridLevel1){
					userIds.add(parentUseridLevel2);
				}
			}
		}
		catch (HibernateException e) {
			logger.error("-------HibernateException-------"+  e.getMessage());
		}
		finally {
			session.close(); 
		}
		Collections.reverse(userIds);
		return  userIds;
	}

	public  Company findParentCompany(Company company)
	{
		Session session = null;
		Company parentCompany=null;
		try{
			session = sessionFactory.openSession();			
			Criteria criteria = session.createCriteria(Company.class);
			criteria.add(Restrictions.eq("company_userid", company.getParent_company_userid()));
			parentCompany =  (Company) criteria.uniqueResult();			
		}
		catch (HibernateException e) {
			logger.error("-------HibernateException-------"+  e.getMessage());
		}
		finally {
			session.close(); 
		}
		return  parentCompany;
	}

	public  User getCompanyIdObjByPassingUserId(int userId)
	{		
		Session session = null;
		User user = null;
		try{
			session = sessionFactory.openSession();			
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("id", userId));
			user =  (User) criteria.uniqueResult();		
		}
		catch (HibernateException e) {
			logger.error("-------HibernateException-------"+  e.getMessage());
		}
		finally {
			session.close(); 
		}
		return  user;
	}

	public List<InsuranceCreditNote> getCreditNoteListByOrderRowID(Long id) {
		int rowId=id.intValue();
		Session session = null;
		List<InsuranceCreditNote> creditNoteList = null;
		try{ 
			session = sessionFactory.openSession();			
			Criteria criteria = session.createCriteria(InsuranceCreditNote.class);
			criteria.add(Restrictions.eq("rowId", rowId));
			creditNoteList =  criteria.list();				
		}
		catch (HibernateException e) {
			logger.error("--------------HibernateException-----------------"+e.getMessage());
		}finally {
			session.close(); 
		}
		return creditNoteList;
	}
	public InsuranceCreditNote getCreditNoteDetailsByComapnyId(int companyid, Long id) {
		InsuranceCreditNote creditNoteObj   = null;
		Session session = null;
		try
		{
			session = sessionFactory.openSession();			
			Criteria criteria = session.createCriteria(InsuranceCreditNote.class);
			criteria.add(Restrictions.eq("companyId", String.valueOf(companyid)));
			criteria.add(Restrictions.eq("rowId", id.intValue()));
			criteria.add(Restrictions.eq("afterStatus", "Cancelled"));
			criteria.add(Restrictions.eq("afterPayStatus", "Refund"));
			creditNoteObj = (InsuranceCreditNote) criteria.uniqueResult();			
		}
		catch(Exception e){
			logger.error("---------Exception---------"+e.getMessage());

		}finally{
			session.close();
		}
		return creditNoteObj;
	}

}
