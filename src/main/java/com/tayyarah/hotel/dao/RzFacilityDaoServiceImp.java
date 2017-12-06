package com.tayyarah.hotel.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.model.RzFacility;



public class RzFacilityDaoServiceImp implements RzFacilityDaoService {
	@Autowired
	RzFacilityDao rzfaciltydao;
	@Override
	public RzFacility getEntityVendorId(int VendorID) throws Exception {
		// TODO Auto-generated method stub
		return rzfaciltydao.getHotelByVendorID(VendorID);
	}

	@Override
	public List<RzFacility> getEntityByVid(String HotelRoomDesVid)
			throws Exception {
		// TODO Auto-generated method stub
		return rzfaciltydao.getByVendorId(HotelRoomDesVid);
	}

}
