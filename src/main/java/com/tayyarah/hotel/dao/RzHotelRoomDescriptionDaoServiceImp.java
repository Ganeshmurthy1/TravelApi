package com.tayyarah.hotel.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.model.RzHotelRoomDescription;


public class RzHotelRoomDescriptionDaoServiceImp implements
		RzHotelRoomDescriptionDaoService {
	@Autowired
	RzHotelRoomDescriptionDao rzRoomDesDao;
	@Override
	public RzHotelRoomDescription getEntityRoomId(int roomid) throws Exception {
		// TODO Auto-generated method stub
		return rzRoomDesDao.getHotelByRoomId(roomid);
	}

	@Override
	public List<RzHotelRoomDescription> getEntityByVid(String HotelRoomDesVid)
			throws Exception {
		// TODO Auto-generated method stub
		return rzRoomDesDao.getByVendorId(HotelRoomDesVid);
	}

}
