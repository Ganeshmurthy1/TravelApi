package com.tayyarah.hotel.dao;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import com.tayyarah.hotel.model.Hotelinandaround;

public class HotelinandaroundDaoImp implements HotelinandaroundDao{
	@Autowired
	private SessionFactory hotelsessionFactory;
	Session session = null;
	Transaction tx = null;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Hotelinandaround> getByVendorId(String HotelaroundVid) throws HibernateException {
		session = hotelsessionFactory.openSession();		
		tx = session.beginTransaction();
		String sql = "from Hotelinandaround hd  where hd.hoteloverview.VendorID=:vendorId";
		Query query = session.createQuery(sql);
		query.setParameter("vendorId", HotelaroundVid);
		List<Hotelinandaround> list = query.list();
		if (list!=null &&  list.size() > 0) {
			session.close();
		}
		return list;
	}
}