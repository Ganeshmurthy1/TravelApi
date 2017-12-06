package com.tayyarah.hotel.dao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.model.Hotelinandaround;




public class HotelinandaroundDaoServiceImp implements HotelinandaroundDaoService {
@Autowired
HotelinandaroundDao hotelarounddao;

@Override
public List<Hotelinandaround> getEntityByVid(String HotelaroundVid) throws Exception {
	// TODO Auto-generated method stub
	return hotelarounddao.getByVendorId(HotelaroundVid);
}	

}
