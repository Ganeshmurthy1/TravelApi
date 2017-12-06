package com.tayyarah.user.dao;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.HibernateException;

import com.tayyarah.user.entity.User;
import com.tayyarah.user.entity.WalletAmountTranferHistory;

public interface UserWalletDAO {
	public void insertwalletamount_tranfer_history(WalletAmountTranferHistory AWTH) throws Exception;
	public 	boolean getWalletStatus(String userid,BigDecimal totalprice , WalletAmountTranferHistory walletAmountTranferHistory,BigDecimal Gstonmarkup,BigDecimal Gstonflights,String walletRemarks,boolean bookingstatus) throws Exception;
	public boolean updateWalletBalance(BigDecimal amount, int walletId, WalletAmountTranferHistory AWTH)
			throws HibernateException, NumberFormatException, Exception;
	public 	boolean getWalletStatusForSpecial(String userid,BigDecimal totalprice , WalletAmountTranferHistory walletAmountTranferHistory,WalletAmountTranferHistory walletAmountTranferHistorySpecial,String action) throws Exception;
	public void SetBookingStatusInWallet(String userid, BigDecimal totalprice,
			WalletAmountTranferHistory walletAmountTranferHistory, String action, BigDecimal Gstonmarkup,
			BigDecimal Gstonflight) throws Exception;
	public void SetBookingStatusInWalletForSpecial(String userid, BigDecimal totalprice,
			WalletAmountTranferHistory walletAmountTranferHistory,
			WalletAmountTranferHistory walletAmountTranferHistorySpecial, String action) throws Exception;
	public boolean walletTransferHistoryUpdateWithInvoiceNo(String orderid,String invoiceno)throws Exception;
	public  List<User> getUserList(List<Integer> useridlist)throws Exception;
	public 	boolean checkWalletAmount(int userid,BigDecimal totalprice ,BigDecimal Gstonmarkup,BigDecimal Gstonflights) throws Exception;
	public void updateWalletHistoryWhenBookingFailed(String OrderId) throws Exception;	
}
