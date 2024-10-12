package com.fidelity.service;

import com.fidelity.business.entity.Client;
import com.fidelity.business.entity.Order;
import com.fidelity.business.entity.Trade;
import java.math.BigDecimal;
import java.util.List;

public interface TradeService {
    List<Trade> getTradeHistory(Client client);

    void insertTrade(Trade trade) throws Exception;

    boolean processOrderRequest(Order order) throws Exception;
}