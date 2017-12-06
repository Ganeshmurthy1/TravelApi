package com.tayyarah.hotel.dao;

import java.io.Serializable;
import java.util.ArrayList;
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

import com.tayyarah.hotel.entity.HotelOrderCancellationPolicy;
import com.tayyarah.hotel.entity.HotelOrderGuest;
import com.tayyarah.hotel.entity.HotelOrderRoomInfo;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.hotel.model.HotelReport;

public class HotelOrderRowEmailDaoImp implements HotelOrderRowEmailDao,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Logger logger = Logger.getLogger(HotelOrderRowEmailDaoImp.class);	
	@Autowired
	private SessionFactory  sessionFactory;

	Session session = null;
	Transaction tx = null;	
	@Override

	public HotelOrderRow hotelOrderRowByOrderId(String orderId)
			throws HibernateException {
		HotelOrderRow  hotelOrderRowObj=null;
		try{
			session = sessionFactory.openSession();
			//String sql = "select * from hotel_order_row  where orderReference='" + orderId + "'";
			Criteria criteria=session.createCriteria(HotelOrderRow.class);
			criteria.add(Restrictions.eq("orderReference", orderId));
			hotelOrderRowObj = (HotelOrderRow) criteria.uniqueResult();
		}catch (HibernateException e) {
			throw e; 
		}finally {
			session.close(); 
		}
		return hotelOrderRowObj;


	}


	@Override
	public List<HotelOrderCancellationPolicy> getCancelPolicies(HotelOrderRow hotelOrderRow)
			throws HibernateException {
		List<HotelOrderCancellationPolicy> list = new ArrayList<HotelOrderCancellationPolicy>();
		try{
			session = sessionFactory.openSession();
			//String sql = "select  * from hotel_order_cancellation_policy where order_id ='" + hotelOrderRow.getId() + "'";
			Criteria criteria=session.createCriteria(HotelOrderCancellationPolicy.class);
			criteria.add(Restrictions.eq("hotelOrderRow.id", hotelOrderRow.getId()));
			list = criteria.list();

		}catch (HibernateException e) {
			list = new ArrayList<HotelOrderCancellationPolicy>();
			//
		}finally {
			session.close(); 

		}
		return list;
	}

	@Override
	public List<HotelOrderRoomInfo> roomInfoDeatails(HotelOrderRow hotelOrderRow)
			throws HibernateException {
		List<HotelOrderRoomInfo> list =null;
		try{
			session = sessionFactory.openSession();
			//tx = session.beginTransaction();
			//String sql = "select  * from hotel_order_room_info where order_id ='" + hotelOrderRow.getId() + "'";
			Criteria criteria=session.createCriteria(HotelOrderRoomInfo.class);
			criteria.add(Restrictions.eq("hotelOrderRow.id", hotelOrderRow.getId()));
			list = criteria.list();
			 
		}catch (HibernateException e) {
			throw e; 
		}finally {
			session.close(); 
		}
		return list;
	}
	@Override
	public List<HotelOrderGuest> guesInformationDeatails(
			List<HotelOrderRoomInfo> roomInfo) throws HibernateException {
		List<HotelOrderGuest> hotelOrderGuestlist = new ArrayList<HotelOrderGuest>();
		List<Long> roomIds=new ArrayList<>();
		try{
			//String sql = "select * from hotel_order_guest where room_id='"+orderRoomInfo.getId()+"'";
			session = sessionFactory.openSession();
			Criteria criteria=session.createCriteria(HotelOrderGuest.class);
			logger.info("----roomInfo.size()----"+roomInfo.size());				
			for(HotelOrderRoomInfo orderRoomInfo:roomInfo){
				roomIds.add(orderRoomInfo.getId());
			 }
			 criteria.add(Restrictions.in("roomInfo.id", roomIds));
			 hotelOrderGuestlist= criteria.list();
			 logger.info("----hotelOrderGuestlist size"+hotelOrderGuestlist.size());
			
		}catch (HibernateException e) {

			throw e; 
		}finally {
			session.close(); 
		}
		return hotelOrderGuestlist;
	}
	 
	@Override
	public HotelOrderRow hotelOrderRowByRowId(String id)
			throws HibernateException {
		HotelOrderRow hotelOrderRow=null;
		Session session = null;		
		try{
			session = sessionFactory.openSession();
			///String sql = "select * from hotel_order_row  where id =" + id + "";
			Criteria criteria=session.createCriteria(HotelOrderRow.class);
			criteria.add(Restrictions.eq("id",Long.parseLong(id)));
			hotelOrderRow = (HotelOrderRow)criteria.uniqueResult();
		}catch (HibernateException e) {
			hotelOrderRow=null;
			throw e; 
		}finally {
			if(session != null && session.isOpen())
				session.close();
		 }	
		return hotelOrderRow;
	}


	@Override
	public HotelReport hotelRoomandGuestandHotelOrderRowInfo(String orderId) {
		HotelReport reportData=new HotelReport();
		try{
			session = sessionFactory.openSession();
			String sql = "from HotelOrderRow hor where hor.orderReference=:id";
			Query query = session.createQuery(sql);
			query.setParameter("id", orderId);
			HotelOrderRow list = (HotelOrderRow) query.uniqueResult();
			reportData.setHotelOrderRow(list);
		}catch (HibernateException e) {
			logger.error("--------------HibernateException-----------------"+e.getMessage());
		}finally {
			if(session!=null && session.isOpen())
				session.close(); 
		}
		List<HotelOrderRoomInfo> hotelOrderRoomInfos=roomInfoDeatails(reportData.getHotelOrderRow());
		reportData.setHotelOrderRoomInfo(hotelOrderRoomInfos);
		return reportData;
	}


	@Override
	public List<HotelOrderGuest> hotelOrderGuestInfo(Long id) {
			List<HotelOrderGuest> list = null;
			try{
				session = sessionFactory.openSession();
				Criteria criteria=session.createCriteria(HotelOrderGuest.class);
				criteria.add(Restrictions.eq("roomInfo.id",id));
				list = criteria.list();
			}catch (HibernateException e) {
				logger.error("--------------HibernateException-----------------"+e.getMessage());
			}finally {
				if(session!=null && session.isOpen())
					session.close(); 
			}	 
			return list;
		 
	}
}
