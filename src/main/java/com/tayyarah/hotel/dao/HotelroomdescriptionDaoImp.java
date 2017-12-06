package com.tayyarah.hotel.dao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.tayyarah.hotel.model.Hotelroomdescription;


public class HotelroomdescriptionDaoImp implements HotelroomdescriptionDao{
	@Autowired
	private SessionFactory hotelsessionFactory;
	@SuppressWarnings("unchecked")
	@Override
	public List<Hotelroomdescription> getByVendorId(String HotelRoomDesVid) throws HibernateException {

		Session session = hotelsessionFactory.openSession();		
		String sql = "from Hotelroomdescription  hd where hd.apiVendorId=:api_vendor_id";
		Query query = session.createQuery(sql);
		query.setParameter("api_vendor_id", HotelRoomDesVid);
		List<Hotelroomdescription> list = query.list();
		if (list!=null && list.size() > 0) {
			session.close();

		}
		return list;
	}

	@Override
	public Hotelroomdescription getHotelByRoomType(int RoomTypeID)
			throws NonUniqueResultException,HibernateException {

		Session session = null;
		try{
			session = hotelsessionFactory.openSession();		
			String sql = "from Hotelroomdescription hd where hd.roomTypeID=:roomTypeID";
			Hotelroomdescription hotelroomdescription = (Hotelroomdescription) session.createQuery(sql)
					.setParameter("roomTypeID", RoomTypeID) 
					.uniqueResult();
			return hotelroomdescription;
		}catch(org.hibernate.NonUniqueResultException e)
		{
			try{
				session = hotelsessionFactory.openSession();		
				String sql = "from Hotelroomdescription hd where hd.roomTypeID=:roomTypeID";
				Hotelroomdescription hotelroomdescription = (Hotelroomdescription) session.createQuery(sql)
						.setParameter("roomTypeID", RoomTypeID).setMaxResults(1).uniqueResult();
				return hotelroomdescription;
			}
			catch(org.hibernate.NonUniqueResultException e2)
			{
				session = hotelsessionFactory.openSession();
				String sql = "from Hotelroomdescription hd where hd.roomTypeID=:roomTypeID";
				Hotelroomdescription hotelroomdescription = (Hotelroomdescription) session.createQuery(sql)
						.setParameter("roomTypeID", RoomTypeID).setMaxResults(0).list();
				return hotelroomdescription;
			}
			finally {
				if(session != null)
					session.close();  
			}
		}
		finally {
			if(session != null)
				session.close();  
		}
	}

	@Override
	public Map<String, Map<Integer, Hotelroomdescription>> getHotelroomdescriptionByVendorID(List<String> vendorIdList)
			throws HibernateException {
		Map<String, Map<Integer, Hotelroomdescription>> hotelroomdesmap = new HashMap<String, Map<Integer, Hotelroomdescription>>();
		StringBuilder vendorIdinquerysection = new StringBuilder();
		for (int i = 0; i < vendorIdList.size(); i++) {
			if(i == vendorIdList.size()-1)
				vendorIdinquerysection.append("'"+vendorIdList.get(i)+"'");
			else
				vendorIdinquerysection.append("'"+vendorIdList.get(i)+"',");
			hotelroomdesmap.put(vendorIdList.get(i), new HashMap<Integer, Hotelroomdescription>());
		}		
		Session session = null;
		try{
			session = hotelsessionFactory.openSession();			
			String sql = "from Hotelroomdescription  hd where hd.apiVendorId IN (:vendorIds)";
			Query query = session.createQuery(sql);
			query.setParameterList("vendorIds", vendorIdList);
			query.setReadOnly(true);
			List<Hotelroomdescription> list = query.list();		
			for (Hotelroomdescription roomdes : list)
			{
				Map<Integer, Hotelroomdescription> existmap = hotelroomdesmap.get(roomdes.getApiVendorId());
				existmap.put(roomdes.getRoomTypeID(), roomdes);
				hotelroomdesmap.put(roomdes.getApiVendorId(), existmap);
			}
		}catch (HibernateException e) {			
			throw e; 
		}finally {
			if(session != null)
				session.close();  
		}		
		return hotelroomdesmap;
	}

	@Override
	public Map<String, Map<Integer, Hotelroomdescription>> getHotelroomdescriptionApiMap(Map<String, Integer> hotelidmap) throws HibernateException{
		Map<String, Map<Integer, Hotelroomdescription>> hotelroomdesmap = new HashMap<String, Map<Integer, Hotelroomdescription>>();
		StringBuilder hotelidquerysection = new StringBuilder();	
		List<Integer> hotelIds = new ArrayList<>();
		int i=0;
		for (Entry<String, Integer> entry : hotelidmap.entrySet()) {
			hotelIds.add(entry.getValue());
			if(i == hotelidmap.size()-1)
				hotelidquerysection.append(""+entry.getValue()+"");
			else
				hotelidquerysection.append(""+entry.getValue()+",");
			hotelroomdesmap.put(entry.getKey(), new HashMap<Integer, Hotelroomdescription>());
			i++;
		}		
		Session session = null;
		try{
			session = hotelsessionFactory.openSession();			
			String sql = "from Hotelroomdescription  hd where hd.hoteId IN (:hotelIds)";
			Query query = session.createQuery(sql);
			query.setParameterList("hotelIds", hotelIds);
			query.setReadOnly(true);
			List<Hotelroomdescription> list = query.list();		
			for (Hotelroomdescription roomdes : list)
			{
				Map<Integer, Hotelroomdescription> existmap = hotelroomdesmap.get(roomdes.getApiVendorId());
				existmap.put(roomdes.getRoomTypeID(), roomdes);
				hotelroomdesmap.put(roomdes.getApiVendorId(), existmap);
			}
		}catch (HibernateException e) {		
			throw e; 
		}finally {
			if(session != null)
				session.close();  
		}	
		return hotelroomdesmap;
	}
}
