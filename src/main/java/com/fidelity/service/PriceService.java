package com.fidelity.service;

import java.math.BigDecimal;

public interface PriceService {
    BigDecimal getCurrentBidPrice(String instrumentId);
}
