package com.fidelity.integration.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

@Mapper
public interface PriceMapper {

    @Select("SELECT bidPrice FROM Price WHERE instrumentId = #{instrumentId} ORDER BY priceTimestamp DESC LIMIT 1")
    BigDecimal getCurrentBidPrice(String instrumentId);
}