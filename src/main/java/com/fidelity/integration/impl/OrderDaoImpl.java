package com.fidelity.integration.impl;

import com.fidelity.business.entity.Order;
import com.fidelity.integration.OrderDao;
import com.fidelity.integration.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;

@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public boolean insertOrder(Order order, BigDecimal askPrice) throws Exception {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        BigDecimal priceDifference = askPrice.subtract(order.getTargetPrice()).abs();
        BigDecimal maxAllowedDifference = askPrice.multiply(new BigDecimal("0.05"));
        
        if (priceDifference.compareTo(maxAllowedDifference) > 0) {
            return false;
        }
        
        orderMapper.insertOrder(order);
        return true;
    }
}