package com.fidelity.integration.impl;

import com.fidelity.business.entity.Order;
import com.fidelity.integration.OrderDao;
import com.fidelity.integration.PriceDao;
import com.fidelity.integration.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private PriceDao priceDao;

    @Override
    public boolean insertOrder(Order order) throws Exception {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        BigDecimal currentBidPrice = priceDao.getCurrentBidPrice(order.getInstrumentId());
        BigDecimal priceDifference = order.getTargetPrice().subtract(currentBidPrice).abs();
        BigDecimal maxAllowedDifference = currentBidPrice.multiply(new BigDecimal("0.05"));

        if (priceDifference.compareTo(maxAllowedDifference) > 0) {
            return false;
        }

        orderMapper.insertOrder(order);
        return true;
    }
}