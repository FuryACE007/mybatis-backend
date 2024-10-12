package com.fidelity.integration.impl;

import com.fidelity.integration.PriceDao;
import com.fidelity.integration.mapper.PriceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class PriceDaoImpl implements PriceDao {

    @Autowired
    private PriceMapper priceMapper;

    @Override
    public BigDecimal getCurrentBidPrice(String instrumentId) {
        return priceMapper.getCurrentBidPrice(instrumentId);
    }
}