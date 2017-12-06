package com.tayyarah.hotel.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.model.RzAmenityType;


public class RzAmenityTypeDaoServiceImp implements RzAmenityTypeDaoService {
	@Autowired
	RzAmenityTypeDao rzamenityDao;
	@Override
	public RzAmenityType getEntityAmenityId(int amenityid) throws Exception {
		// TODO Auto-generated method stub
		return rzamenityDao.getHotelByAmenityId(amenityid);
	}

	@Override
	public List<RzAmenityType> getEntityByVid(String HotelRoomDesVid)
			throws Exception {
		// TODO Auto-generated method stub
		return rzamenityDao.getByVendorId(HotelRoomDesVid);
	}

}
