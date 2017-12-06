/**
 * 
 */
package com.tayyarah.admin.analytics.lookbook.dao;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.admin.analytics.lookbook.entity.BusLookBook;
import com.tayyarah.admin.analytics.lookbook.entity.FlightLookBook;
import com.tayyarah.admin.analytics.lookbook.entity.HotelLookBook;
import com.tayyarah.admin.analytics.lookbook.entity.LookBookCustomerIPHistory;
import com.tayyarah.admin.analytics.lookbook.entity.LookBookCustomerIPStatus;


/**
 * @author      : Manish Samrat
 * @createdAt   : 12/07/2017
 * @version
 */
@SuppressWarnings("rawtypes")
public class LookBookDaoImpl<T> implements LookBookDao<T>{

	@Autowired
	private SessionFactory  sessionFactory;
	

	public static final Logger logger = Logger.getLogger(LookBookDaoImpl.class);
	private T t;

	@Override
	public T insertIntoTable(T tableEntity){
		Session session = null;   
		Transaction transaction=null;
		try{
			session = sessionFactory.openSession();
			transaction=session.beginTransaction();
			session.save(tableEntity);
			transaction.commit();
		}
		catch (Exception e) {
			transaction.rollback();
			tableEntity=null;
		}
		finally {
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return tableEntity;
	}
	@SuppressWarnings("unchecked")
	@Override
	public T fetchFromTableById(long id){
		Session session = null;   
		Criteria criteria=null;
		try{
			session = sessionFactory.openSession();
			criteria.add(Restrictions.eq("id", id));
			t=(T) criteria.uniqueResult();
		}
		catch (Exception e) {
		}
		finally {
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return t;
	}

	@Override
	public FlightLookBook CheckAndFetchFlightLookBookByAppKey(FlightLookBook flightLookBook){
		Session session = null;   
		Criteria criteria=null;
		try{
			session = sessionFactory.openSession();
			criteria=session.createCriteria(FlightLookBook.class);
			criteria.add(Restrictions.eq("appkey", flightLookBook.getAppkey()));
			flightLookBook=(FlightLookBook) criteria.uniqueResult();
		}
		catch (Exception e) {
			System.out.println("Exception");
		}
		finally {
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return flightLookBook;
	}
	@Override
	public HotelLookBook CheckAndFetchHotelLookBookByAppKey(HotelLookBook hotelLookBook){
		Session session = null;   
		Criteria criteria=null;
		try{
			session = sessionFactory.openSession();
			criteria=session.createCriteria(HotelLookBook.class);
			criteria.add(Restrictions.eq("appkey", hotelLookBook.getAppkey()));
			hotelLookBook=(HotelLookBook) criteria.uniqueResult();
		}
		catch (Exception e) {
			System.out.println("Exception");
		}
		finally {
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return hotelLookBook;
	}
	@Override
	public BusLookBook CheckAndFetchBusLookBookByAppKey(BusLookBook busLookBook){
		Session session = null;   
		Criteria criteria=null;
		try{
			session = sessionFactory.openSession();
			criteria=session.createCriteria(BusLookBook.class);
			criteria.add(Restrictions.eq("appkey", busLookBook.getAppkey()));
			busLookBook=(BusLookBook) criteria.uniqueResult();
		}
		catch (Exception e) {
			System.out.println("Exception");
		}
		finally {
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return busLookBook;
	}
	@Override
	public FlightLookBook updateIntoTable(FlightLookBook flightLookBook,String updateBookedOrSearchCount) {
		Session session = null;
		Transaction transaction=null;
		FlightLookBook flightLookBookUpdate=null;
		try{
			session = sessionFactory.openSession();
			flightLookBookUpdate=(FlightLookBook) session.get(FlightLookBook.class, flightLookBook.getId());

			if(updateBookedOrSearchCount.equalsIgnoreCase("booking"))
				flightLookBookUpdate.setTotalBookedCount(flightLookBookUpdate.getTotalBookedCount()+1);
			if(updateBookedOrSearchCount.equalsIgnoreCase("search"))
				flightLookBookUpdate.setTotalSearchCount(flightLookBookUpdate.getTotalSearchCount()+1);

			transaction=session.beginTransaction();
			session.save(flightLookBookUpdate);
			transaction.commit();

		}
		catch (Exception e) {
			transaction.rollback();
		}
		finally {
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}

		//		lookBookServices
		return flightLookBookUpdate;
	}

	@Override
	public HotelLookBook updateIntoHotelTable(HotelLookBook lookBook,String updateBookedOrSearchCount) {
		Session session = null;
		Transaction transaction=null;
		HotelLookBook hotelLookBookUpdate=null;
		try{
			session = sessionFactory.openSession();
			hotelLookBookUpdate=(HotelLookBook) session.get(HotelLookBook.class, lookBook.getId());

			if(updateBookedOrSearchCount.equalsIgnoreCase("booking"))
				hotelLookBookUpdate.setTotalBookedCount(hotelLookBookUpdate.getTotalBookedCount()+1);
			if(updateBookedOrSearchCount.equalsIgnoreCase("search"))
				hotelLookBookUpdate.setTotalSearchCount(hotelLookBookUpdate.getTotalSearchCount()+1);

			transaction=session.beginTransaction();
			session.save(hotelLookBookUpdate);
			transaction.commit();

		}
		catch (Exception e) {
			transaction.rollback();
		}
		finally {
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}

		//		lookBookServices
		return hotelLookBookUpdate;
	}
	@Override
	public BusLookBook updateIntoBusTable(BusLookBook lookBook,String updateBookedOrSearchCount) {
		Session session = null;
		Transaction transaction=null;
		BusLookBook busLookBookUpdate=null;
		try{
			session = sessionFactory.openSession();
			busLookBookUpdate=(BusLookBook) session.get(BusLookBook.class, lookBook.getId());			
			if(updateBookedOrSearchCount.equalsIgnoreCase("booking"))
				busLookBookUpdate.setTotalBookedCount(busLookBookUpdate.getTotalBookedCount()+1);
			if(updateBookedOrSearchCount.equalsIgnoreCase("search"))
				busLookBookUpdate.setTotalSearchCount(busLookBookUpdate.getTotalSearchCount()+1);	

			transaction=session.beginTransaction();
			session.save(busLookBookUpdate);
			transaction.commit();			
		}
		catch (Exception e) {
			transaction.rollback();
		}
		finally {
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}

		return busLookBookUpdate;
	}
	@Override
	public LookBookCustomerIPStatus CheckAndFetchIpStatus(String ip){
		Session session = null;   
		Criteria criteria=null;
		LookBookCustomerIPStatus ipStatus=null;

		try{
			session = sessionFactory.openSession();
			criteria=session.createCriteria(LookBookCustomerIPStatus.class);
			Conjunction conjunction=Restrictions.conjunction();
			conjunction.add(Restrictions.eq("ip",ip ));
			//			conjunction.add(Restrictions.eq("blockStatus",false ));
			//			conjunction.add(Restrictions.le("totalSearchCount",100 ));

			criteria.add(conjunction);
			ipStatus=(LookBookCustomerIPStatus) criteria.uniqueResult();


		}
		catch (Exception e) {
			System.out.println("Exception");
		}
		finally {
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return ipStatus;
	}
	public LookBookCustomerIPStatus updateIpStatus(LookBookCustomerIPStatus ipStatus){
		Session session = null;
		Transaction transaction=null;
		LookBookCustomerIPStatus ipStatusUpdate=null;
		try{
			session = sessionFactory.openSession();
			ipStatusUpdate=(LookBookCustomerIPStatus) session.get(LookBookCustomerIPStatus.class, ipStatus.getId());
			ipStatusUpdate.setLastDate(ipStatus.getLastDate());
			ipStatusUpdate.setTotalSearchCount(ipStatus.getTotalSearchCount());
			ipStatusUpdate.setTotalBookedCount(ipStatus.getTotalBookedCount());
			ipStatusUpdate.setCompanyName(ipStatus.getCompanyName());
			ipStatusUpdate.setConfigName(ipStatus.getConfigName());
			ipStatusUpdate.setBlockStatus(ipStatus.isBlockStatus());
			transaction=session.beginTransaction();
			session.save(ipStatusUpdate);
			transaction.commit();

		}
		catch (Exception e) {
			transaction.rollback();
		}
		finally {
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return ipStatusUpdate;
	}

	@Override
	public LookBookCustomerIPHistory CheckAndfetchIpHistory(String ip){
		Session session = null;   
		Criteria criteria=null;
		LookBookCustomerIPHistory ipStatus=null;

		try{
			session = sessionFactory.openSession();
			criteria=session.createCriteria(LookBookCustomerIPHistory.class);
			Conjunction conjunction=Restrictions.conjunction();
			conjunction.add(Restrictions.eq("ip",ip ));

			criteria.add(conjunction);
			ipStatus=(LookBookCustomerIPHistory) criteria.uniqueResult();


		}
		catch (Exception e) {
			System.out.println("Exception");
		}
		finally {
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return ipStatus;
	}
	public LookBookCustomerIPHistory updateIpHistory(LookBookCustomerIPHistory history){
		Session session = null;
		Transaction transaction=null;
		LookBookCustomerIPHistory ipStatusHistoryUpdate=null;
		try{
			session = sessionFactory.openSession();
			ipStatusHistoryUpdate=(LookBookCustomerIPHistory) session.get(LookBookCustomerIPHistory.class, history.getId());
			ipStatusHistoryUpdate.setLastDate(history.getLastDate());
			ipStatusHistoryUpdate.setTotalSearchCount(history.getTotalSearchCount());
			ipStatusHistoryUpdate.setTotalBookedCount(history.getTotalBookedCount());
			ipStatusHistoryUpdate.setCompanyName(history.getCompanyName());
			ipStatusHistoryUpdate.setConfigName(history.getConfigName());
			transaction=session.beginTransaction();
			session.save(ipStatusHistoryUpdate);
			transaction.commit();

		}
		catch (Exception e) {
			transaction.rollback();
		}
		finally {
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return ipStatusHistoryUpdate;
	}
	public String resetAllB2C(){
		Session session = null;
		Transaction transaction=null;
		LookBookCustomerIPStatus customerIPStatusDelete=null;
		List<LookBookCustomerIPStatus> list=new ArrayList<LookBookCustomerIPStatus>();
		Criteria criteria=null;
		try{
			session = sessionFactory.openSession();
			criteria=session.createCriteria(LookBookCustomerIPStatus.class);
			criteria.add(Restrictions.eq("b2cFlag", true));
			list= criteria.list();
		}
		catch (Exception e) {
			System.out.println("Exception");
		}
		try{
			if(list.size()>0){
				for(LookBookCustomerIPStatus ipStatus:list){
					session = sessionFactory.openSession();
					customerIPStatusDelete=(LookBookCustomerIPStatus) session.get(LookBookCustomerIPStatus.class, ipStatus.getId());
					transaction=session.beginTransaction();
					session.delete(customerIPStatusDelete);
					transaction.commit();
				}
			}
		}
		catch (Exception e) {
			transaction.rollback();
		}
		finally {
			if(session != null && session.isOpen())
			{				
				session.close(); 
			}
		}
		return "";
	}
	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}
}
