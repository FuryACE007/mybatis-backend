package com.fidelity.integration;

import java.math.BigDecimal;

public interface PriceDao {
    BigDecimal getCurrentBidPrice(String instrumentId);
}