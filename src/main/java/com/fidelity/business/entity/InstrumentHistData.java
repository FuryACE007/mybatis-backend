package com.fidelity.business.entity;

public class InstrumentHistData {

	private String instrumentId;
	private double currDayPrice;
	private double prev1DayPrice;
	private double prev2DayPrice;
	private double prev3DayPrice;
	private double prev4DayPrice;

	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}

	public void setCurrDayPrice(double currDayPrice) {
		this.currDayPrice = currDayPrice;
	}

	public void setPrev1DayPrice(double prev1DayPrice) {
		this.prev1DayPrice = prev1DayPrice;
	}

	public void setPrev2DayPrice(double prev2DayPrice) {
		this.prev2DayPrice = prev2DayPrice;
	}

	public void setPrev3DayPrice(double prev3DayPrice) {
		this.prev3DayPrice = prev3DayPrice;
	}

	public void setPrev4DayPrice(double prev4DayPrice) {
		this.prev4DayPrice = prev4DayPrice;
	}

	public InstrumentHistData(String instrumentId, double currDayPrice, double prev1DayPrice, double prev2DayPrice,
							  double prev3DayPrice, double prev4DayPrice) {
		this.instrumentId = instrumentId;
		this.currDayPrice = currDayPrice;
		this.prev1DayPrice = prev1DayPrice;
		this.prev2DayPrice = prev2DayPrice;
		this.prev3DayPrice = prev3DayPrice;
		this.prev4DayPrice = prev4DayPrice;
	}

	public String getInstrumentId() {
		return instrumentId;
	}

	public double getCurrDayPrice() {
		return currDayPrice;
	}

	public double getPrev1DayPrice() {
		return prev1DayPrice;
	}

	public double getPrev2DayPrice() {
		return prev2DayPrice;
	}

	public double getPrev3DayPrice() {
		return prev3DayPrice;
	}

	public double getPrev4DayPrice() {
		return prev4DayPrice;
	}

}