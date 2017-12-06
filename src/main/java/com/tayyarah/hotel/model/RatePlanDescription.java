package com.tayyarah.hotel.model;

import java.io.Serializable;

public class RatePlanDescription implements Serializable{
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	String text;
}
