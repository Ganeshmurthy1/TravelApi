package com.tayyarah.hotel.dao;


import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.entity.Islhotelmapping;


public class IslhotelmappingDaoServiceImp implements IslhotelmappingDaoService {
@Autowired
IslhotelmappingDao islhotelmappingDao;

@Override
public Islhotelmapping getEntityByISLVendorID(String ISLVendorID)
		throws HibernateException {
	// TODO Auto-generated method stub
	return islhotelmappingDao.getHotelByISLVendorID(ISLVendorID);
}

@Override
public Islhotelmapping getEntityByTGVendorID(String TGVendorID)
		throws HibernateException {
	// TODO Auto-generated method stub
	return islhotelmappingDao.getHotelByTGVendorID(TGVendorID);
}

@Override
public Islhotelmapping getEntityByReznextVendorID(Long ReznextVendorID)
		throws HibernateException {
	// TODO Auto-generated method stub
	return islhotelmappingDao.getHotelByReznextVendorID(ReznextVendorID);
}





	
	

	

}
