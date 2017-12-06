package com.tayyarah.hotel.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.tayyarah.hotel.entity.Hotelsecondaryarea;

public interface HotelsecondaryareaDao {
	public List<Hotelsecondaryarea> getHotelById(int vendorId) throws HibernateException;
	public List<Hotelsecondaryarea> getByVendorId(String hotelsecondaryVid) throws HibernateException;
}
