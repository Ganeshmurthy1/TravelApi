package com.tayyarah.hotel.dao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.model.RzHotelRoomImage;


public class RzHotelRoomImageDaoServiceImp implements
RzHotelRoomImageDaoService {

	@Autowired
	RzHotelRoomImageDao rzroomimageDao;

	@Override
	public RzHotelRoomImage getEntityByRoomImageId(int id) throws Exception {
		// TODO Auto-generated method stub
		return rzroomimageDao.getHotelByRoomImageId(id);
	}

	@Override
	public List<RzHotelRoomImage> getEntityByVid(String HotelRoomDesVid)
			throws Exception {
		// TODO Auto-generated method stub
		return rzroomimageDao.getByVendorId(HotelRoomDesVid);
	}

}
