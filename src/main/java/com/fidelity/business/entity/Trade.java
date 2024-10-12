package com.fidelity.business.entity;


import com.fidelity.business.enums.Direction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


public class Trade {
	private Order order;
    private BigDecimal cashValue;
    private int quantity;
    private Direction direction;
    private String instrumentId;
    private int clientId;
    private String tradeId;
    private LocalDateTime timeStamp;
    private BigDecimal executionPrice;

    public Trade(){
        this.tradeId = UUID.randomUUID().toString();
    }

    public Trade(Order order,BigDecimal cashValue,int quantity,Direction direction,
                 String instrumentId,int clientId,LocalDateTime timeStamp,
                 BigDecimal executionPrice){
        this.tradeId = UUID.randomUUID().toString();
        this.order = order;
        this.cashValue = cashValue;
        this.quantity = quantity;
        this.direction = direction;
        this.clientId = clientId;
        this.instrumentId = instrumentId;
        this.timeStamp = timeStamp;
        this.executionPrice = executionPrice;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public BigDecimal getCashValue() {
        return cashValue;
    }

    public void setCashValue(BigDecimal cashValue) {
        this.cashValue = cashValue;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public BigDecimal getExecutionPrice() {
        return executionPrice;
    }

    public void setExecutionPrice(BigDecimal executionPrice) {
        this.executionPrice = executionPrice;
    }


}