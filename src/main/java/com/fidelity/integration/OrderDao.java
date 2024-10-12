package com.fidelity.integration;

import com.fidelity.business.entity.Order;
import java.math.BigDecimal;

public interface OrderDao {
    boolean insertOrder(Order order, BigDecimal askPrice) throws Exception;
}