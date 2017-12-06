package com.tayyarah.common.dao;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.common.entity.MoneyExchange;

public class MoneyExchangeDaoServiceImp implements MoneyExchangeDaoService {
	@Autowired
	MoneyExchangeDao moneydao;
	@Override
	public MoneyExchange insertHotelSearch(MoneyExchange MoneyExchange)
			throws HibernateException, IOException, Exception {
		
		return moneydao.insertOrUpdateCurrency(MoneyExchange);
	}
	@Override
	public List<MoneyExchange> getEntityList() throws Exception {
		// TODO Auto-generated method stub
		return moneydao.getEntityList();
	}
	@Override
	public Map<String, Double> getEntityRate(String baseCur, String convertedCur)
			throws HibernateException {
		// TODO Auto-generated method stub
		return moneydao.getCurrencyRate(baseCur, convertedCur);
	}
	

}
