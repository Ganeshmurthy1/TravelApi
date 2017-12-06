package com.tayyarah.hotel.dao;

import java.io.IOException;
import java.util.HashMap;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS;
import com.tayyarah.hotel.model.APIHotelMap;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;

public class ApiHotelMapStoreDaoServiceImp implements
		ApiHotelMapStoreDaoService {
	
	@Autowired
	ApiHotelMapStoreDao apihotelstoredao;	

	@Override
	public HashMap<String, RoomStay> getRoomStaysMap(int searchKey) throws HibernateException, IOException {
		// TODO Auto-generated method stub
		return apihotelstoredao.getRoomStaysMap(searchKey);
	}

	@Override
	public HashMap<String, OTAHotelAvailRS.RoomStays.RoomStay> getTGRoomStaysMap(int searchKey)
			throws HibernateException, IOException {
		// TODO Auto-generated method stub
		return apihotelstoredao.getTGRoomStaysMap(searchKey);
	}
	@Override
	public int insertApiMapBySearchKey(int searchKey, APIHotelMap apiHotelMap) throws HibernateException, IOException {
		// TODO Auto-generated method stub
		return apihotelstoredao.insertApiMapBySearchKey(searchKey, apiHotelMap);
	}
	

}
