package com.tayyarah.hotel.dao;

import java.util.List;

import com.tayyarah.hotel.model.Facility;


public interface HotelFacilityDaoService {
	
	public  List<Facility> getEntityByVid(String searchVid ) ;
	public List<Facility> getEntityDescriptionByVendorProperty(String VendorID,String AmenityType);
}
