package com.tayyarah.hotel.dao;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.model.Hotelroomdescription;

public class HotelroomdescriptionDaoServiceImp implements HotelroomdescriptionDaoService {
@Autowired
HotelroomdescriptionDao hotelroomdesdao;

@Override
public List<Hotelroomdescription> getEntityByVid(String HotelRoomDesVid) throws Exception  {
	// TODO Auto-generated method stub
	return hotelroomdesdao.getByVendorId(HotelRoomDesVid);
}

@Override
public Hotelroomdescription getEntityRoomType(int RoomTypeID)
		throws HibernateException {
	// TODO Auto-generated method stub
	return hotelroomdesdao.getHotelByRoomType(RoomTypeID);
}




	
	

	

}
