package com.tayyarah.hotel.dao;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;
import com.tayyarah.hotel.model.HotelOverview;

public interface HoteloverviewDao {
	public HotelOverview getHotelOverviewByVendorID(String VendorID) throws HibernateException;
	public Map<String, HotelOverview> getHotelOverviewByVendorID(List<String> vendorIdList) throws HibernateException;
	public Map<String, HotelOverview> getHotelOverviewNativeMapByVendorIDs(List<String> vendorIdList, String apiProviderId) throws HibernateException;
	public Map<String, HotelOverview> getHotelOverviewApiMapByVendorIDs(List<String> vendorIdList, String apiProviderId) throws HibernateException;
	
	
}
