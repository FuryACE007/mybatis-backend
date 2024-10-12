package com.fidelity.business.entity;


import com.fidelity.business.enums.Direction;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Order {
    private String instrumentId;
    private int quantity;
    private BigDecimal targetPrice;
    private Direction direction;
    private int clientId;
    private String orderId;

    public Order() {
        this.orderId = UUID.randomUUID().toString();
    }

    public Order(String instrumentId, int quantity, BigDecimal targetPrice, Direction direction, int clientId) {
        this.instrumentId = instrumentId;
        this.quantity = quantity;
        this.targetPrice = targetPrice;
        this.direction = direction;
        this.clientId = clientId;
        this.orderId = UUID.randomUUID().toString();
    }

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(BigDecimal targetPrice) {
        this.targetPrice = targetPrice;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getOrderId() {
        return orderId;
    }

//    public void setOrderId(String orderId) {
//        this.orderId = orderId;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return quantity == order.quantity && Objects.equals(instrumentId, order.instrumentId) && Objects.equals(targetPrice, order.targetPrice) && direction == order.direction && Objects.equals(clientId, order.clientId) && Objects.equals(orderId, order.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instrumentId, quantity, targetPrice, direction, clientId, orderId);
    }

    @Override
    public String toString() {
        return "Order{" +
                "instrumentId='" + instrumentId + '\'' +
                ", quantity=" + quantity +
                ", targetPrice=" + targetPrice +
                ", direction=" + direction +
                ", clientId='" + clientId + '\'' +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}