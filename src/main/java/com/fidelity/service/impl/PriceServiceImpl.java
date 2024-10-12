package com.fidelity.service.impl;

import com.fidelity.integration.PriceDao;
import com.fidelity.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PriceServiceImpl implements PriceService {
    @Autowired
    private PriceDao priceDao;

    @Override
    public BigDecimal getCurrentBidPrice(String instrumentId) {
        return priceDao.getCurrentBidPrice(instrumentId);
    }
}
