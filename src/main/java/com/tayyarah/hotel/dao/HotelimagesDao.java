package com.tayyarah.hotel.dao;

import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;
import com.tayyarah.hotel.model.Hotelimage;

public interface HotelimagesDao {	
	public List<Hotelimage> getByVendorId(String HotelImageVid) ;
	public List<String> getImagesByVendorID(String vendorID) ;
	public Map<String, List<String>> getImagesByVendorID(List<String> vendorIdList) throws HibernateException;
	public Map<String, List<String>> getImagesMapByVendorIDs(List<String> vendorIdList, String apiProviderId, boolean isCommon) throws HibernateException;
	public Map<String, List<String>> getImagesApiMap(Map<String, Integer> hotelidmap) throws HibernateException;
	
}
