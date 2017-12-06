package com.tayyarah.hotel.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;



@Component
@Scope("session")
@Entity
@Table(name = "hotel_order_row_commission")
@Proxy(lazy = false)
public class HotelOrderRowCommission  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue
	Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "order_row_id", referencedColumnName = "id")
	private HotelOrderRow hotelOrderRow;

	@Column(name="commission_amount_value")
	private BigDecimal commissionAmountValue;

	public BigDecimal getCommissionAmountValue() {
		return commissionAmountValue;
	}

	public void setCommissionAmountValue(BigDecimal commissionAmountValue) {
		this.commissionAmountValue = commissionAmountValue;
	}


	public HotelOrderRow getHotelOrderRow() {
		return hotelOrderRow;
	}

	public void setHotelOrderRow(HotelOrderRow hotelOrderRow) {
		this.hotelOrderRow = hotelOrderRow;
	}

	/*@Column(name="order_id")
	private String orderId;*/
	@Column(name="company_id")
	private String CompanyId;
	@Column(name="commission")
	private BigDecimal commission;
	@Column(name="commission_type")
	private String commissionType;
	@Column(name="rate_type")
	private String rateType;

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public String getCommissionType() {
		return commissionType;
	}

	public void setCommissionType(String commissionType) {
		this.commissionType = commissionType;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public String getCompanyId() {
		return CompanyId;
	}  

	public void setCompanyId(String companyId) {
		CompanyId = companyId;
	}

	/*public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}*/
}

