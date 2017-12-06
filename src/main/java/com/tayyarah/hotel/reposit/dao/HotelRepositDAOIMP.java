package com.tayyarah.hotel.reposit.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.reposit.entity.Facility;
import com.tayyarah.hotel.reposit.entity.HotelMealFare;
import com.tayyarah.hotel.reposit.entity.HotelRoomExtraFare;
import com.tayyarah.hotel.reposit.entity.Hotelimage;
import com.tayyarah.hotel.reposit.entity.Hotelinandaround;
import com.tayyarah.hotel.reposit.entity.Hotelmealtype;
import com.tayyarah.hotel.reposit.entity.Hoteloverview;
import com.tayyarah.hotel.reposit.entity.Hotelreview;
import com.tayyarah.hotel.reposit.entity.Hotelroomdescription;
import com.tayyarah.hotel.reposit.entity.Hotelroomfare;
import com.tayyarah.hotel.reposit.entity.Hotelsegment;
import com.tayyarah.hotel.reposit.entity.Mealsfare;

public class HotelRepositDAOIMP implements HotelRepositDAO {
	Logger logger = Logger.getLogger(HotelRepositDAOIMP.class);

	@Autowired
	private SessionFactory lintashotelsessionFactory;	
	Transaction tx = null;	

	@Override
	public List<Facility> getFacilityById(int id) throws HibernateException {
		Session session = null;
		List<Facility> list=null;
		try{
			session = lintashotelsessionFactory.openSession();			
			tx = session.beginTransaction();		
			String sql = "from Facility f where f.vendorID=:VendorID";			
			Query query = session.createQuery(sql);	
			query.setParameter("VendorID", id);
			list= query.list();

		}catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(session != null && session.isOpen())
				session.close();
		}
		return list;
	}	
	
	@Override
	public List<Hotelimage> getHotelImageById(int id) throws HibernateException {
		Session session = null;
		List<Hotelimage> list=null;
		try{
			session = lintashotelsessionFactory.openSession();			
			tx = session.beginTransaction();			
			String sql = "from Hotelimage hi where hi.id=:id";			
			Query query = session.createQuery(sql);	
			query.setParameter("id",id);
			list= query.list();
			
		}catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(session != null && session.isOpen())
				session.close();
		}
		return list;
	}

	@Override
	public List<Hotelinandaround> getHotelinandaroundById(int id)
			throws HibernateException {	
		Session session = null;
		List<Hotelinandaround> list=null;
		try{
			session = lintashotelsessionFactory.openSession();			
			tx = session.beginTransaction();			
			String sql = "from Hotelinandaround h where h.id.vendorID=:vendorID";			
			Query query = session.createQuery(sql);	
			query.setParameter("vendorID", id);
			list= query.list();
			
		}catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(session != null && session.isOpen())
				session.close();
		}
		return list;
	}

	@Override
	public List<HotelMealFare> getHotelMealFareByVendorId(int vendorid)
			throws HibernateException {		
		Session session = null;
		List<HotelMealFare> list=null;
		try{
			session = lintashotelsessionFactory.openSession();			
			tx = session.beginTransaction();			
			String sql = "from HotelMealFare hf where hf.vendorid=:vendorid";			
			Query query = session.createQuery(sql);	
			query.setParameter("vendorid", vendorid);
			list= query.list();
			
		}catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(session != null && session.isOpen())
				session.close();
		}
		return list;
	}

	@Override
	public List<Hotelmealtype> getHotelmealtypeByMealId(byte mealtypeid)
			throws HibernateException {		
		Session session = null;
		List<Hotelmealtype> list=null;
		try{
			session = lintashotelsessionFactory.openSession();			
			tx = session.beginTransaction();		
			String sql = "from Hotelmealtype h where h.mealtypeid=:mealtypeid";			
			Query query = session.createQuery(sql);	
			query.setParameter("mealtypeid", (byte)mealtypeid);
			list= query.list();
			
		}catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(session != null && session.isOpen())
				session.close();
		}
		return list;
	}

	@Override
	public List<Hoteloverview> getHoteloverviewById(int id)
			throws HibernateException {		
		Session session = null;
		List<Hoteloverview> list=null;
		try{
			session = lintashotelsessionFactory.openSession();			
			tx = session.beginTransaction();			
			String sql = "from Hoteloverview hv where hv.id.id=:id";			
			Query query = session.createQuery(sql);	
			query.setParameter("id", id);
			list= query.list();
			
		}catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(session != null && session.isOpen())
				session.close();
		}
		return list;
	}

	@Override
	public List<Hotelreview> getHotelreviewById(int id)
			throws HibernateException {		
		Session session = null;
		List<Hotelreview> list=null;
		try{
			session = lintashotelsessionFactory.openSession();			
			tx = session.beginTransaction();
			String sql = "sfrom Hotelreview h where h.vendorID=:VendorID";		
			Query query = session.createQuery(sql);	
			query.setParameter("VendorID", id);
			list= query.list();
			
		}catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(session != null && session.isOpen())
				session.close();
		}
		return list;
	}

	@Override
	public List<Hotelroomdescription> getHotelroomdescriptionById(int id)
			throws HibernateException {		
		Session session = null;
		List<Hotelroomdescription> list=null;
		try{
			session = lintashotelsessionFactory.openSession();			
			tx = session.beginTransaction();			
			String sql = "from Hotelroomdescription h where h.vendorID=:VendorID";			
			Query query = session.createQuery(sql);	
			query.setParameter("VendorID", id);
			list= query.list();
			
		}catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(session != null && session.isOpen())
				session.close();
		}
		return list;
	}

	@Override
	public List<HotelRoomExtraFare> getHotelRoomExtraFareById(int id)
			throws HibernateException {		
		Session session = null;
		List<HotelRoomExtraFare> list=null;
		try{
			session = lintashotelsessionFactory.openSession();			
			tx = session.beginTransaction();			
			String sql = "from HotelRoomExtraFare h  where h.vendorid=:VendorID";
			logger.info("hotel."+sql);
			Query query = session.createQuery(sql);	
			query.setParameter("VendorID", id);
			list= query.list();
			
		}catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(session != null && session.isOpen())
				session.close();
		}
		return list;
	}

	@Override
	public List<Hotelroomfare> getHotelroomfare(int id)
			throws HibernateException {	
		Session session = null;
		List<Hotelroomfare> list=null;
		try{
			session = lintashotelsessionFactory.openSession();			
			tx = session.beginTransaction();			
			String sql = "from Hotelroomfare h  where h.vendorid=:VendorID";			
			Query query = session.createQuery(sql);	
			query.setParameter("VendorID", id);
			list= query.list();
			
		}catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(session != null && session.isOpen())
				session.close();
		}
		return list;
	}
	
	@Override
	public List<Hotelroomfare> getHotelroomfare(int id, int roomid)
			throws HibernateException {	
		Session session = null;
		List<Hotelroomfare> list=null;
		try{
			session = lintashotelsessionFactory.openSession();			
			tx = session.beginTransaction();			
			String sql = "from Hotelroomfare h where h.vendorid=:VendorID and h.roomno=:roomno";		
			Query query = session.createQuery(sql);	
			query.setParameter("VendorID", id);
			query.setParameter("roomno",roomid);
			list= query.list();
			
		}catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(session != null && session.isOpen())
				session.close();
		}
		return list;
	}
	
	@Override
	public Map<Integer, Hotelroomfare> getHotelroomfareMap(int id)
			throws HibernateException {
		Map<Integer, Hotelroomfare> hotelroomfareMap = new HashMap<Integer, Hotelroomfare>();
		Session session = null;
		List<Hotelroomfare> list=null;
		try{
			session = lintashotelsessionFactory.openSession();				
			String sql = "from Hotelroomfare h  where h.vendorid=:VendorID";			
			Query query = session.createQuery(sql);	
			query.setParameter("VendorID", id);
			list= query.list();			
			for (Hotelroomfare hotelroomfare : list) {
				hotelroomfareMap.put(hotelroomfare.getRoomno(), hotelroomfare);
			}			
			return hotelroomfareMap;	
			
		}catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(session != null && session.isOpen())
				session.close();
		}
		return hotelroomfareMap;		
	}

	@Override
	public List<Hotelsegment> getHotelsegmentById(int segmentId)
			throws HibernateException {		
		Session session = null;
		List<Hotelsegment> list=null;
		try{
			session = lintashotelsessionFactory.openSession();			
			tx = session.beginTransaction();		
			String sql = "from Hotelsegment h where h.segmentId=:segment_id";			
			Query query = session.createQuery(sql);	
			query.setParameter("segment_id", segmentId);
			list= query.list();
			
		}catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(session != null && session.isOpen())
				session.close();
		}
		return list;
	}

	@Override
	public List<Mealsfare> getMealsfareById(int mealfareId) throws HibernateException {	
		Session session = null;
		List<Mealsfare> list=null;
		try{
			session = lintashotelsessionFactory.openSession();			
			tx = session.beginTransaction();
			String sql = "from Mealsfare m where m.mealfareid=:mealfareid";			
			Query query = session.createQuery(sql);	
			query.setParameter("mealfareid", String.valueOf(mealfareId));
			list= query.list();
			
		}catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(session != null && session.isOpen())
				session.close();
		}
		return list;
	}
	
	@Override
	public List<Hoteloverview> getHoteloverviewByCity(String city) throws HibernateException {	
		Session session = null;
		List<Hoteloverview> list=null;
		try{
			session = lintashotelsessionFactory.openSession();			
			tx = session.beginTransaction();			
			String sql = "from Hoteloverview h  where h.city=:City";			
			Query query = session.createQuery(sql);	
			query.setParameter("City", city);
			list= query.list();
			
		}catch (HibernateException e) {			
			logger.error("HibernateException", e);
		} catch (Exception e) {			
			logger.error("Exception", e);
		}
		finally {
			if(session != null && session.isOpen())
				session.close();
		}
		return list;
	}
}