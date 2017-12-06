package com.tayyarah.hotel.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.SerializationUtils;

import com.tayyarah.hotel.entity.HotelSearchResponseCacheEntity;
import com.tayyarah.hotel.model.APIHotelMap;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;

public class TopCityDIrectResponsedao {

	@Autowired
	private SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;
	Serializable pk;

	public HotelSearchResponseCacheEntity save(HotelSearchResponseCacheEntity hotelSearchResponse) {
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			pk = session.save(hotelSearchResponse);
			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return hotelSearchResponse;
	}

	public APIHotelMap getStoredResponse(Integer cityCode) {
		APIHotelMap apiHotelMap = null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(HotelSearchResponseCacheEntity.class);
			criteria.add(Restrictions.eq("citycode", cityCode));
			HotelSearchResponseCacheEntity hotelSearchResponseDTO = (HotelSearchResponseCacheEntity) criteria.uniqueResult();
			if (hotelSearchResponseDTO != null) {
				byte[] response = hotelSearchResponseDTO.getHotel_search_response();
				 apiHotelMap = (APIHotelMap) SerializationUtils.deserialize(response);				
			}

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
		return apiHotelMap;
	}
	
	public List<HotelSearchResponseCacheEntity> getAllCityDataFromCacheTable(){		
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(HotelSearchResponseCacheEntity.class);
			List<HotelSearchResponseCacheEntity> list=criteria.list();
			return list;
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return null;
	}

	public void TruncateParticularRow(Integer cityCode) {
		try {
			session = sessionFactory.openSession();
			Transaction tx=session.beginTransaction();
			Criteria criteria = session.createCriteria(HotelSearchResponseCacheEntity.class);
			criteria.add(Restrictions.eq("citycode", cityCode));
			HotelSearchResponseCacheEntity hotelSearchResponseCacheEntity = (HotelSearchResponseCacheEntity) criteria
					.uniqueResult();
			session.delete(hotelSearchResponseCacheEntity);
			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}
	
	public HotelSearchResponseCacheEntity updateDbByCornSchdeduler(HotelSearchResponseCacheEntity hotelSearchResponse) {
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			pk = session.save(hotelSearchResponse);
			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return hotelSearchResponse;
	}
}
