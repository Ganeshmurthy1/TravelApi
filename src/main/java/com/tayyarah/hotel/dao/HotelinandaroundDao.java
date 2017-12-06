package com.tayyarah.hotel.dao;

import java.util.List;
import org.hibernate.HibernateException;
import com.tayyarah.hotel.model.Hotelinandaround;

public interface HotelinandaroundDao {	
	public List<Hotelinandaround> getByVendorId(String HotelaroundVid) throws HibernateException;
}
