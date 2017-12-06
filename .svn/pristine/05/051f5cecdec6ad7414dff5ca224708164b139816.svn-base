package com.tayyarah.hotel.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.model.City;


public class CityDaoServiceImp implements CityDaoService {
@Autowired
HotelCityDao citydao;
	@Override
	public List<City> getCityEntityList() throws Exception {
		// TODO Auto-generated method stub
		return citydao.getCityList();
	}
	@Override
	public List<City> getEntityByCity(String searchCityKey) throws Exception {
		// TODO Auto-generated method stub
		return citydao.getByCity(searchCityKey);
	}

}
