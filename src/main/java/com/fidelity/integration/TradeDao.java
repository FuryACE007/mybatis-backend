package com.fidelity.integration;

import com.fidelity.business.entity.Client;
import com.fidelity.business.entity.Trade;

import java.util.List;

public interface TradeDao {
    List<Trade> getTradeHistory(Client client);

    void insertTrade(Trade trade) throws Exception;
}
