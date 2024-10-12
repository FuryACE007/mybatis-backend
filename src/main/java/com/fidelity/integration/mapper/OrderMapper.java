package com.fidelity.integration.mapper;

import com.fidelity.business.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    @Insert("INSERT INTO Order_Table (orderId, instrumentId, quantity, targetPrice, direction, clientId) " +
            "VALUES (#{orderId}, #{instrumentId}, #{quantity}, #{targetPrice}, #{direction}, #{clientId})")
    void insertOrder(Order order);
}