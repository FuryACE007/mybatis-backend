package com.fidelity.integration.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

@Mapper
public interface PriceMapper {

    @Select("SELECT bidPrice FROM (SELECT bidPrice FROM Price WHERE instrumentId = #{instrumentId} ORDER BY timestamp DESC) WHERE ROWNUM = 1")
    BigDecimal getCurrentBidPrice(String instrumentId);
}