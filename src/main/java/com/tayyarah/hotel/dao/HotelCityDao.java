package com.tayyarah.hotel.dao;

import java.io.IOException;
import java.util.List;

import org.hibernate.HibernateException;

import com.tayyarah.hotel.entity.HotelSearchCity;
import com.tayyarah.hotel.entity.TboCity;
import com.tayyarah.hotel.model.City;

public interface HotelCityDao {
	public List<City> getCityList() throws Exception;
	public List<City> getByCity(String searchCityKey) throws Exception;
	public HotelSearchCity getHotelSearchCity(String cityName, String countryCode)
			throws HibernateException, IOException, Exception;
	public HotelSearchCity getHotelSearchCity(Integer cityId)	
			throws HibernateException, IOException, Exception;
	public List<HotelSearchCity> getHotelSearchCityDuplicates(Integer cityId)	
			throws HibernateException, IOException, Exception;
	public TboCity getTboCity(Integer cityId)	
			throws HibernateException, IOException, Exception;
	public List<HotelSearchCity> getHotelSearchCity(String key)
			throws HibernateException, IOException, Exception;
	public List<TboCity> getTBOCities(String key)
			throws HibernateException, IOException, Exception;
}
