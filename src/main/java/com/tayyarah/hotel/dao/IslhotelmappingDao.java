package com.tayyarah.hotel.dao;
import org.hibernate.HibernateException;

import com.tayyarah.hotel.entity.Islhotelmapping;

public interface IslhotelmappingDao {
	public Islhotelmapping getHotelByISLVendorID(String ISLVendorID) throws HibernateException;
	public Islhotelmapping getHotelByTGVendorID(String TGVendorID) throws HibernateException;
	public Islhotelmapping getHotelByReznextVendorID(Long ReznextVendorID) throws HibernateException;
	
}
