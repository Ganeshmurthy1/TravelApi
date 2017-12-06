package com.tayyarah.hotel.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.model.RzRoomAmenity;



public class RzRoomAmenityDaoServiceImp implements RzRoomAmenityDaoService {
	@Autowired
	RzRoomAmenityDao rzamenitydao;
	@Override
	public RzRoomAmenity getEntityAmenityId(int amenityid) throws Exception {
		// TODO Auto-generated method stub
		return rzamenitydao.getHotelByAmenityId(amenityid);
	}

	@Override
	public List<RzRoomAmenity> getEntityByVid(String HotelRoomDesVid)
			throws Exception {
		// TODO Auto-generated method stub
		return rzamenitydao.getByVendorId(HotelRoomDesVid);
	}

}
