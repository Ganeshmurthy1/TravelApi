package com.tayyarah.hotel.dao;

import java.util.List;

import com.tayyarah.hotel.model.Hotelreview;



public interface HotelreviewDaoService {
	
	public  List<Hotelreview> getEntityByVid(String hotelReviewVid ) throws Exception;
	public List<Hotelreview> getEntityById(int id) throws Exception;
}
