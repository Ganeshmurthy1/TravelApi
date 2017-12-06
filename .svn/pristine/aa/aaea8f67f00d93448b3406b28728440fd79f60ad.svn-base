package com.tayyarah.hotel.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tayyarah.common.util.Timestampable;


@Entity
@Table(name = "hotel_payment_config")
public class HotelPaymentConfig extends Timestampable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="is_wallet", columnDefinition = "BOOLEAN DEFAULT false")
	private boolean isWallet;
	@Column(name="is_card", columnDefinition = "BOOLEAN DEFAULT false")
	private boolean isCard;
	
	public boolean isWallet() {
		return isWallet;
	}
	public void setWallet(boolean isWallet) {
		this.isWallet = isWallet;
	}
	public boolean isCard() {
		return isCard;
	}
	public void setCard(boolean isCard) {
		this.isCard = isCard;
	}	 
}