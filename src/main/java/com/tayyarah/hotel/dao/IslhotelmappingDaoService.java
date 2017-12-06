package com.tayyarah.hotel.dao;



import org.hibernate.HibernateException;

import com.tayyarah.hotel.entity.Islhotelmapping;




public interface IslhotelmappingDaoService {

	public Islhotelmapping getEntityByISLVendorID(String ISLVendorID) throws HibernateException;
	public Islhotelmapping getEntityByTGVendorID(String TGVendorID) throws HibernateException;
	public Islhotelmapping getEntityByReznextVendorID(Long ReznextVendorID) throws HibernateException;
	
}
