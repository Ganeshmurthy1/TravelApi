package com.tayyarah.hotel.dao;

import java.util.List;

import com.tayyarah.hotel.model.City;




public interface CityDaoService {
	public List<City> getCityEntityList() throws Exception ;
	public  List<City> getEntityByCity(String searchCityKey ) throws Exception;
}
