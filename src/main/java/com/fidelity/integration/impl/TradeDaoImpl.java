package com.fidelity.integration.impl;


import com.fidelity.business.entity.Client;
import com.fidelity.business.entity.Trade;
import com.fidelity.integration.TradeDao;
import com.fidelity.integration.mapper.TradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("tradeDao")
public class TradeDaoImpl implements TradeDao {

    @Autowired
    TradeMapper mapper;

    @Override
    public List<Trade> getTradeHistory(Client client){
       return mapper.getTradeHistory(client);
   }

    @Override
    public void insertTrade(Trade trade) throws Exception {
           mapper.insertTrade(trade);
   }

}
