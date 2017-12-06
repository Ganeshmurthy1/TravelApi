package com.tayyarah.hotel.dao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.model.Hotelreview;



public class HotelreviewDaoServiceImp implements HotelreviewDaoService {
@Autowired
HotelreviewDao hotelreviewdao;

@Override
public List<Hotelreview> getEntityByVid(String hotelReviewVid) throws Exception {
	// TODO Auto-generated method stub
	return hotelreviewdao.getByVendorId(hotelReviewVid);
}


@Override
public  List<Hotelreview> getEntityById(int rid) throws Exception {
	return hotelreviewdao.getHotelById(rid);
	// TODO Auto-generated method stub
	
}




	
	

	

}
