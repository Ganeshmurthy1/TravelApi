package com.tayyarah.common.dao;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;

import com.tayyarah.common.entity.MoneyExchange;

public interface MoneyExchangeDao {
	public MoneyExchange insertOrUpdateCurrency(MoneyExchange MoneyExchange) throws HibernateException, IOException, Exception;
	public List<MoneyExchange> getEntityList() throws Exception;  
	public Map<String, Double> getCurrencyRate(String baseCur, String convertedCur) throws HibernateException;	
	
}
