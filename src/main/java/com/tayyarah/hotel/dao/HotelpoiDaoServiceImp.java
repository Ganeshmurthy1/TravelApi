package com.tayyarah.hotel.dao;


import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.model.HotelPoi;



public class HotelpoiDaoServiceImp implements HotelpoiDaoService {
	@Autowired
	HotelpoiDao hotelpoidao;

	@Override
	public HotelPoi getByPoiId(int PoiId) throws HibernateException {
		// TODO Auto-generated method stub
		return hotelpoidao.getHotelByPoiId(PoiId);
	}

	@Override
	public List<HotelPoi> getPoiEntityList() throws Exception {
		// TODO Auto-generated method stub
		return hotelpoidao.getHotelByPoiId();
	}



}
