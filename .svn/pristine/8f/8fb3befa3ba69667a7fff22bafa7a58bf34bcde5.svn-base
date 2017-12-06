package com.tayyarah.hotel.dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.entity.HotelDetails;
import com.tayyarah.hotel.entity.HotelRoomDetails;



public class HotelDetailsDAO {
	@Autowired
	private SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;
	Logger logger = Logger.getLogger(HotelDetailsDAO.class);	
	public List<HotelDetails> getHotelDetails(String cityCode, String companyUserId)
			throws HibernateException, IOException, Exception {
		Session sess = null;	
		List<HotelDetails> list = null;
		Criteria  hotelCriteria =null;		
		try{
			sess = sessionFactory.openSession();
			logger.info("------search HotelDetails-----keyword search-cityCode --"+cityCode);
			hotelCriteria = sess.createCriteria(HotelDetails.class);			
			list = hotelCriteria.list();
			logger.info("------search HotelDetails-----items found--"+list.size());
		}catch (HibernateException e) {
			logger.info("-------search HotelDetails-----HibernateException--"+e.getMessage());
			throw e; 
		}finally {
			logger.info("-------search HotelSearchCity-----finally--");
			if(sess!=null && sess.isOpen())
			{
				sess.close(); 
			}
		}
		return list;
	}


	public List<HotelRoomDetails> getHotelRoomDetails(HotelDetails hotelDetails)
			throws HibernateException, IOException, Exception {
		Session sess = null;	
		List<HotelRoomDetails> list = null;
		Criteria  hotelRoomsCriteria =null;		
		try{
			sess = sessionFactory.openSession();
			logger.info("------search HotelRoomDetails-----keyword search-hotelid --"+hotelDetails.getId());
			hotelRoomsCriteria = sess.createCriteria(HotelRoomDetails.class);	
			hotelRoomsCriteria.add(Restrictions.eq("hotelDetails.id", hotelDetails.getId()));
			list = hotelRoomsCriteria.list();
			logger.info("------search HotelRoomDetails-----items found--"+list.size());
		}catch (HibernateException e) {
			logger.info("-------search HotelRoomDetails-----HibernateException--"+e.getMessage());
			throw e; 
		}finally {
			logger.info("-------search HotelRoomDetails-----finally--");
			if(sess!=null && sess.isOpen())
			{
				sess.close(); 
			}
		}
		return list;
	}

	public boolean bookRooms(HashMap<Long,Integer> roomIdMap)
			throws HibernateException, IOException, Exception {
		Session sess = null;	
		Transaction tx = null;
		boolean result = false;
		try{
			sess = sessionFactory.openSession();			

			Criteria cr = sess.createCriteria(HotelRoomDetails.class);
			
			for (Entry<Long, Integer> roomIdItem : roomIdMap.entrySet()) {
				cr.add(Restrictions.eq("id", roomIdItem.getKey()));
				HotelRoomDetails hotelRoomDetailDb = (HotelRoomDetails) cr.uniqueResult();
				Integer roomsAvailablity = hotelRoomDetailDb.getAvailability();	
				logger.info("########################## DB room # "+roomIdItem.getKey()+" rooms available before booking "+hotelRoomDetailDb.getAvailability());	
				
				result = (roomsAvailablity>=roomIdItem.getValue());	
				if(!result)
				{
					logger.info("########################## DB rooms booking error:: insufficient no of rooms in room id "+roomIdItem.getKey());
					break;
				}
				else
				{
					roomsAvailablity = ((roomsAvailablity - roomIdItem.getValue())<0)?0:(roomsAvailablity - roomIdItem.getValue());			
					hotelRoomDetailDb.setAvailability(roomsAvailablity);
					tx = sess.beginTransaction();
					sess.save(hotelRoomDetailDb);
					logger.info("########################## DB room # "+roomIdItem.getKey()+" rooms available after booking "+hotelRoomDetailDb.getAvailability());	
				}				
			}			
			if(result)
			{
				
				tx.commit();
				logger.info("########################## DB rooms booking completed");	
			}
			else
			{
				tx.rollback();
				logger.info("########################## DB rooms booking error:: insufficient no of rooms ");
			}


		}catch (HibernateException e) {
			logger.info("########################## DB rooms booking Exception");	
			logger.error("########################## DB rooms booking Exception", e);	
			if (tx!=null) tx.rollback();
			result = false;			
		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}

		}
		return result;		
	}

	public boolean canBookRooms(HashMap<Long,Integer> roomIdMap)
			throws HibernateException, IOException, Exception {
		Session sess = null;		
		//logger.info("can block? check  roomId -  " + roomId + " noOfRoomsBooking - " + noOfRoomsBooking);
		boolean result = false;	
		try{
			sess = sessionFactory.openSession();
			Criteria cr = sess.createCriteria(HotelRoomDetails.class);
			
			for (Entry<Long, Integer> roomIdItem : roomIdMap.entrySet()) {
				cr.add(Restrictions.eq("id", roomIdItem.getKey()));
				HotelRoomDetails hotelRoomDetailDb = (HotelRoomDetails) cr.uniqueResult();
				Integer roomsAvailablity = hotelRoomDetailDb.getAvailability();	
				logger.info("########################## DB rooms available before booking"+hotelRoomDetailDb.getAvailability());	
				result = (roomsAvailablity>=roomIdItem.getValue());	
				if(!result)
				{
					logger.info("########################## DB rooms booking error:: insufficient no of rooms in room id "+roomIdItem.getKey());
					break;
				}
			}
			
					
		}catch (HibernateException e) {
			logger.info("########################## DB rooms blocking Exception");	
			logger.error("########################## DB rooms blocking Exception", e);	
			if (tx!=null) tx.rollback();
			result = false;			
		}finally {
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}

		}
		return result;		
	}


}
