package com.tayyarah.hotel.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.tayyarah.hotel.model.Facility;

public interface HotelFacilityDao {	
	public List<Facility> getByVendorId(String searchVid) ;
	public List<Facility> getDescriptionByVendorProperty(String VendorID, String AmenityType) ;
	public Map<String, List<Facility>> getFacilityByVendorID(List<String> vendorIdList, String amenityType) throws HibernateException;	
	public Map<String, List<Facility>> getFacilityNativeMapByVendorIDs(List<String> vendorIdList, String amenityType, String apiProviderId) throws HibernateException;
	public Map<String, List<Facility>> getFacilityApiMap(Map<String, Integer> hotelidmap, String amenityType) throws HibernateException;
		
}
