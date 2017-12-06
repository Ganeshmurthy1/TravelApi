package com.tayyarah.user.dao;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.user.entity.WalletAmountTranferHistory;


public class WalletTransferHistoryDAOIMP implements WalletTransferHistoryDAO {
	@Autowired
	private SessionFactory  sessionFactory;
	public static final Logger logger = Logger.getLogger(WalletTransferHistoryDAOIMP.class);	
	
	@Override
	public WalletAmountTranferHistory getWalletById(String id)
			throws HibernateException, IOException, Exception {
		Session sess = null;	
		WalletAmountTranferHistory walletAmountTranferHistory = null;
		try{
			sess = sessionFactory.openSession();			
			Criteria  criteria=sess.createCriteria(WalletAmountTranferHistory.class);
			criteria.add(Restrictions.eq("id", Integer.parseInt(id)));
			walletAmountTranferHistory = (WalletAmountTranferHistory) criteria.uniqueResult();
			sess.close();
		}catch (HibernateException e) {			
			logger.info("Exceeption----HibernateException--"+e.getMessage());
		}
		finally
		{
			if(sess != null && sess.isOpen())
			{				
				sess.close(); 
			}
		}
		return walletAmountTranferHistory;	
	}

	@Override
	public WalletAmountTranferHistory insertOrUpdateWalletxx(
			WalletAmountTranferHistory hb) throws HibernateException,
			IOException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WalletAmountTranferHistory getWallet(String orderId)
			throws HibernateException, IOException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
}
