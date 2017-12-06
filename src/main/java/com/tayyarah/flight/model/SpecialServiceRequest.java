package com.tayyarah.flight.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.tayyarah.api.flight.tbo.model.Baggage;
import com.tayyarah.api.flight.tbo.model.Meal;
import com.tayyarah.api.flight.tbo.model.MealDynamic;
import com.tayyarah.api.flight.tbo.model.Seat;

public class SpecialServiceRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	private List<Meal> meal = new ArrayList<Meal>();
	private List<Seat> seatPreference = new ArrayList<Seat>();	
	private List<List<Baggage>> baggage = new ArrayList<List<Baggage>>();	
	private List<List<MealDynamic>> mealDynamic = new ArrayList<List<MealDynamic>>();
	private boolean IsLCC;
	
	public boolean isIsLCC() {
		return IsLCC;
	}
	public void setIsLCC(boolean isLCC) {
		IsLCC = isLCC;
	}
	public List<Meal> getMeal() {
		return meal;
	}
	public void setMeal(List<Meal> meal) {
		this.meal = meal;
	}
	public List<Seat> getSeatPreference() {
		return seatPreference;
	}
	public void setSeatPreference(List<Seat> seatPreference) {
		this.seatPreference = seatPreference;
	}
	public List<List<Baggage>> getBaggage() {
		return baggage;
	}
	public void setBaggage(List<List<Baggage>> baggage) {
		this.baggage = baggage;
	}
	public List<List<MealDynamic>> getMealDynamic() {
		return mealDynamic;
	}
	public void setMealDynamic(List<List<MealDynamic>> mealDynamic) {
		this.mealDynamic = mealDynamic;
	}
}