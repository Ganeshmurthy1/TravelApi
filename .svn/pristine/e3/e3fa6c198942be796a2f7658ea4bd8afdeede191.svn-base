package com.tayyarah.hotel.dao;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.email.helper.VisaEmailHelper;
import com.tayyarah.hotel.model.APIHotelMap;
import com.tayyarah.hotel.model.ApiHotelMapStore;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;

public class ApiHotelMapStoreDaoImp implements ApiHotelMapStoreDao {

	public static final Logger logger = Logger.getLogger(ApiHotelMapStoreDaoImp.class);
	@Autowired
	private SessionFactory hotelsessionFactory;
	Session session = null;
	Transaction tx = null;	

	@Override
	public HashMap<String, RoomStay> getRoomStaysMap(int searchKey) throws HibernateException, IOException {
		// TODO Auto-generated method stub
		try{
			session = hotelsessionFactory.openSession();
			tx = session.beginTransaction();
			ApiHotelMapStore apimapstore = (ApiHotelMapStore)session.get(ApiHotelMapStore.class, searchKey);			
			HashMap<String, RoomStay> roomStaysMap = (HashMap<String, RoomStay>) SerializationUtils.deserialize(apimapstore.getHotelres_map());
			return roomStaysMap;

		} catch (HibernateException e) {
			logger.info("------getApiMapBySearchKey---"+searchKey+"--HibernateException--"+e.getMessage());

			e.printStackTrace();
		} catch (Exception e) {
			logger.info("------getApiMapBySearchKey---"+searchKey+"--Exception--"+e.getMessage());
			e.printStackTrace();
		}
		finally{		
			if (!tx.wasCommitted())
				tx.commit();						
		}
		return new HashMap<String, RoomStay>();
	}

	@Override
	public HashMap<String, com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS.RoomStays.RoomStay> getTGRoomStaysMap(int searchKey)
			throws HibernateException, IOException {
		try{
			session = hotelsessionFactory.openSession();
			tx = session.beginTransaction();
			ApiHotelMapStore apimapstore = (ApiHotelMapStore)session.get(ApiHotelMapStore.class, searchKey);			
			HashMap<String, com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS.RoomStays.RoomStay> tgRoomStaysMap = (HashMap<String, com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS.RoomStays.RoomStay>) SerializationUtils.deserialize(apimapstore.getTg_hotelres_map());
			return tgRoomStaysMap;

		} catch (HibernateException e) {
			logger.info("------getApiMapBySearchKey---"+searchKey+"--HibernateException--"+e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("------getApiMapBySearchKey---"+searchKey+"--Exception--"+e.getMessage());
			e.printStackTrace();
		}
		finally{		
			if (!tx.wasCommitted())
				tx.commit();							
		}
		return new HashMap<String, com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS.RoomStays.RoomStay>();
	}

	public int insertApiMapBySearchKey(int searchKey, APIHotelMap apiHotelMap) throws HibernateException, IOException {
		try{
			session = hotelsessionFactory.openSession();
			tx = session.beginTransaction();					
			ApiHotelMapStore apimapstore = new ApiHotelMapStore();
			byte[] roomStaysMapdata = SerializationUtils.serialize(apiHotelMap.getRoomStays());
			apimapstore.setHotelres_map(roomStaysMapdata);				
			session.save(apimapstore);    //Save the data
			if (!tx.wasCommitted())
				tx.commit();

			return apimapstore.getSearch_key();

		} catch (HibernateException e) {
			logger.info("------insertApiMapBySearchKey---"+searchKey+"--Exception--"+e.getMessage());
			return 0;
		} catch (Exception e) {
			logger.info("------insertApiMapBySearchKey---"+searchKey+"--Exception--"+e.getMessage());
			e.printStackTrace();
			return 0;
		}
		finally{	
			if (!tx.wasCommitted())
				tx.commit();						
		}
	}

}
