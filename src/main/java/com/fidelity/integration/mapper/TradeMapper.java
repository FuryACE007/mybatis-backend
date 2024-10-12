package com.fidelity.integration.mapper;

import com.fidelity.business.entity.Client;
import com.fidelity.business.entity.Order;
import com.fidelity.business.entity.Trade;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface TradeMapper {

    @Select("""
            SELECT
                t.tradeid,
                t.orderid,
                t.cashvalue,
                t.quantity,
                t.direction,
                t.instrumentid,
                t.clientid,
                t.executionprice,
                t.executedtime,
                o.quantity AS orderqty,
                o.targetprice
            FROM
                trade       t
                LEFT OUTER JOIN order_table o ON t.orderid = o.orderid where t.clientid=#{id}
            ORDER BY
                t.executedtime DESC
                  FETCH FIRST 100 ROWS ONLY
            """)
    @Result(property = "tradeId",column = "tradeid",id=true)
    @Result(property = "order",column="orderid",one=@One(select="getOrderDetails"))
    @Result(property = "cashValue",column = "cashvalue")
    @Result(property ="quantity",column="quantity" )
    @Result(property="direction",column = "direction")
    @Result(property = "instrumentId",column="instrumentid")
    @Result(property = "clientId",column ="clientid" )
    @Result(property = "executionPrice",column="executionprice")
    @Result(property = "timeStamp",column = "executedtime")
    List<Trade> getTradeHistory(Client client);


    @Select("""
            SELECT
                orderid,
                instrumentid,
                quantity,
                targetprice,
                direction,
                clientid
            FROM
                order_table where orderid=#{orderId}
            """)
    @Result(property = "orderId",column = "orderid", id=true)
    @Result(property = "instrumentId",column="instrumentid")
    @Result(property = "quantity",column = "quantity")
    @Result(property = "targetPrice",column="targetprice")
    @Result(property = "direction",column="direction")
    @Result(property = "clientId",column="clientid")
    Order getOrderDetails(String orderId);

    @Insert("""
        INSERT INTO Trade (tradeId, orderId, cashValue, quantity, direction, instrumentId, clientId, executionPrice, executedTime)
        VALUES (#{tradeId}, #{order.orderId}, #{cashValue}, #{quantity}, #{direction}, #{instrumentId}, #{clientId}, #{executionPrice}, #{timeStamp})
        """)
    void insertTrade(Trade trade);
}
