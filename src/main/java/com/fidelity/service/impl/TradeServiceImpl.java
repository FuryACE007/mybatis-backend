package com.fidelity.service.impl;

import com.fidelity.business.entity.Client;
import com.fidelity.business.entity.Trade;
import com.fidelity.business.enums.Direction;
import com.fidelity.integration.ClientDao;
import com.fidelity.integration.OrderDao;
import com.fidelity.integration.impl.TradeDaoImpl;
import com.fidelity.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {
    @Autowired
    TradeDaoImpl tradeDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ClientDao clientDao;

    @Override
    public List<Trade> getTradeHistory(Client client) {
        if(client==null) throw new NullPointerException("Can't get trade history, Client is NULL");
        else{
            return tradeDao.getTradeHistory(client);
        }
    }

    @Override
    public void insertTrade(Trade trade) throws Exception {
        if(trade == null){
            throw new IllegalArgumentException("Cannot insert null trade");
        }
        try {
            tradeDao.insertTrade(trade);
        } catch (Exception e) {
            throw new Exception("Error adding trade to the database", e);
        }
    }

    @Override
    @Transactional
    public boolean processTradeRequest(Trade trade, BigDecimal askPrice) throws Exception {
        // Check if client has enough balance for buying
        if (trade.getDirection() == Direction.BUY) {
            BigDecimal requiredBalance = trade.getCashValue();
            BigDecimal clientBalance = clientDao.getClientCashBalance(trade.getClientId());
            if (clientBalance.compareTo(requiredBalance) < 0) {
                throw new InsufficientFundsException("Client does not have enough balance for this trade");
            }
        }
        
        // Check if client has enough quantity for selling
        if (trade.getDirection() == Direction.SELL) {
            int requiredQuantity = trade.getQuantity();
            int clientQuantity = clientDao.getClientInstrumentQuantity(trade.getClientId(), trade.getInstrumentId());
            if (clientQuantity < requiredQuantity) {
                throw new InsufficientInstrumentsException("Client does not have enough instruments for this trade");
            }
        }

        // Existing code for order insertion
        boolean orderInserted = orderDao.insertOrder(trade.getOrder(), askPrice);
        if (!orderInserted) {
            return false;
        }

        // Existing code for trade insertion
        insertTrade(trade);

        // Existing code for portfolio and cash balance update
        BigDecimal cashValueChange = trade.getDirection() == Direction.BUY ? 
            trade.getCashValue().negate() : trade.getCashValue();
        clientDao.updateClientPortfolio(trade.getClientId(), trade.getInstrumentId(), trade.getQuantity(), trade.getDirection(), cashValueChange);
        clientDao.updateClientCashBalance(trade.getClientId(), cashValueChange);

        return true;
    }
}
