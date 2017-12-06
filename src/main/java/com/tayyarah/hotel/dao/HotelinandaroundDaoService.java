package com.tayyarah.hotel.dao;

import java.util.List;

import com.tayyarah.hotel.model.Hotelinandaround;



public interface HotelinandaroundDaoService {
	
	public  List<Hotelinandaround> getEntityByVid(String HotelaroundVid ) throws Exception;
}
