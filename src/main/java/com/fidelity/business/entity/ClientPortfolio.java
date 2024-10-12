package com.fidelity.business.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class ClientPortfolio {
    private int portfolioId;
    private String instrumentId;
    private int clientId;
    private int quantity;
    private BigDecimal cashValue;

    public ClientPortfolio() {
    }

    public ClientPortfolio(int portfolioId, String instrumentId, int clientId, int quantity, BigDecimal cashValue) {
        this.portfolioId = portfolioId;
        this.instrumentId = instrumentId;
        this.clientId = clientId;
        this.quantity = quantity;
        this.cashValue = cashValue;
    }

    public int getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(int portfolioId) {
        this.portfolioId = portfolioId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCashValue() {
        return cashValue;
    }

    public void setCashValue(BigDecimal cashValue) {
        this.cashValue = cashValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientPortfolio that = (ClientPortfolio) o;
        return portfolioId == that.portfolioId &&
               clientId == that.clientId &&
               quantity == that.quantity &&
               Objects.equals(instrumentId, that.instrumentId) &&
               Objects.equals(cashValue, that.cashValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(portfolioId, instrumentId, clientId, quantity, cashValue);
    }

    @Override
    public String toString() {
        return "ClientPortfolio{" +
                "portfolioId=" + portfolioId +
                ", instrumentId='" + instrumentId + '\'' +
                ", clientId=" + clientId +
                ", quantity=" + quantity +
                ", cashValue=" + cashValue +
                '}';
    }
}