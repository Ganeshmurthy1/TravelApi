package com.tayyarah.hotel.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;




import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tayyarah.common.util.Timestampable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
@Component
@Scope("session")
@Entity
@Access(AccessType.FIELD)
@Table(name = "hotel_order_cancellation_policy")
public class HotelOrderCancellationPolicy extends Timestampable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6240232324191765200L;
	@Column(name = "cancellation_day")
	private Integer cancellationDay;
	@Column(name = "fee_type")
	private String feeType;
	@Column(name = "fee_amount", columnDefinition="decimal(20,2) default '0.0'")
	private BigDecimal feeAmount;
	@Transient
	@Column(name="feeAmountRoundOff2Dec")
	private BigDecimal feeAmountRoundOff2Dec;
	@Transient
	@Column(name="feeAmountRoundOff")
	private BigDecimal feeAmountRoundOff;	

	@Column(name = "currency")
	private String currency;
	@Column(name = "remarks", columnDefinition = "LONGTEXT")
	private String remarks;

	@Column(name = "formatted_fee_amount")
	private String formattedFeeAmount;

	@Column(name = "start_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	@Column(name = "end_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	@Column(name = "from_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fromDate;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id", referencedColumnName = "id")
	private HotelOrderRow hotelOrderRow;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "room_id", referencedColumnName = "id")
	private HotelOrderRoomInfo roomInfo;


	public Integer getCancellationDay() {
		return cancellationDay;
	}

	public void setCancellationDay(Integer cancellationDay) {
		this.cancellationDay = cancellationDay;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public BigDecimal getFeeAmount()
	{
		return feeAmount;
	}

	public void setFeeAmount(BigDecimal feeAmount)
	{
		this.feeAmount = feeAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public HotelOrderRoomInfo getRoomInfo() {
		return roomInfo;
	}

	public void setRoomInfo(HotelOrderRoomInfo roomInfo) {
		this.roomInfo = roomInfo;
	}

	public String getFormattedFeeAmount() {
		return formattedFeeAmount;
	}

	public void setFormattedFeeAmount(String formattedFeeAmount) {
		this.formattedFeeAmount = formattedFeeAmount;
	}


	public HotelOrderRow getHotelOrderRow() {
		return hotelOrderRow;
	}

	public void setHotelOrderRow(HotelOrderRow hotelOrderRow) {
		this.hotelOrderRow = hotelOrderRow;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public BigDecimal getFeeAmountRoundOff() {		
		return feeAmountRoundOff;
	}

	public BigDecimal getFeeAmountRoundOff2Dec() {		
		return feeAmountRoundOff2Dec;
	}
	public void roundOffFeeAmount() {
		feeAmountRoundOff = new BigDecimal(0).setScale(0);
		feeAmountRoundOff2Dec = new BigDecimal(0).setScale(0);
		if(feeAmount != null)
		{
			try{
				feeAmountRoundOff2Dec = feeAmount.setScale(2,
						BigDecimal.ROUND_HALF_UP);
				feeAmountRoundOff = feeAmount.setScale(0,
						BigDecimal.ROUND_HALF_UP);


			} catch (Exception e) {
				feeAmountRoundOff = new BigDecimal(0).setScale(0);
				feeAmountRoundOff2Dec = new BigDecimal(0).setScale(0);
			}
		}
	}

}
