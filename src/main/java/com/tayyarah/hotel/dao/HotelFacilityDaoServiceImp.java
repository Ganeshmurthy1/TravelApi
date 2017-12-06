package com.tayyarah.hotel.dao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.model.Facility;


public class HotelFacilityDaoServiceImp implements HotelFacilityDaoService {
@Autowired
HotelFacilityDao hotelFacilityDao;

@Override
public List<Facility> getEntityByVid(String searchVid)  {
	// TODO Auto-generated method stub
	return hotelFacilityDao.getByVendorId(searchVid);
}

@Override
public List<Facility> getEntityDescriptionByVendorProperty(String VendorID,
		String AmenityType) {
	// TODO Auto-generated method stub
	return hotelFacilityDao.getDescriptionByVendorProperty(VendorID, AmenityType);
}




	
	

	

}
