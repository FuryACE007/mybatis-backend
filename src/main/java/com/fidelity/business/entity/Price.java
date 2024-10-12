package com.fidelity.business.entity;



import java.math.BigDecimal;
import java.time.LocalDateTime;



public class Price {
	private Instrument instrument;
	private BigDecimal targetPrice;
	private String instrumentId;
	private BigDecimal currentPrice;
	private LocalDateTime timestamp;


	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	public BigDecimal getTargetPrice() {
		return targetPrice;
	}

	public void setTargetPrice(BigDecimal targetPrice) {
		this.targetPrice = targetPrice;
	}

	public String getInstrumentId() {
		return instrumentId;
	}

	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}

	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}


}
