package com.tayyarah.hotel.dao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.model.Hotelimage;




public class HotelimagesDaoServiceImp implements HotelimagesDaoService {
@Autowired
HotelimagesDao hotelimagesdao;

@Override
public List<Hotelimage> getEntityByVid(String HotelImageVid)  {
	// TODO Auto-generated method stub
	return hotelimagesdao.getByVendorId(HotelImageVid);
}

@Override
public List<String> getEntityByImageUrl(String vendorID)  {
	// TODO Auto-generated method stub
	return hotelimagesdao.getImagesByVendorID(vendorID);
}




	
	

	

}
