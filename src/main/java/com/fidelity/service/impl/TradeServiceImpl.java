package com.fidelity.service.impl;

import com.fidelity.business.entity.Client;
import com.fidelity.business.entity.Order;
import com.fidelity.business.entity.Trade;
import com.fidelity.business.enums.Direction;
import com.fidelity.exceptions.InsufficientFundsException;
import com.fidelity.exceptions.InsufficientInstrumentsException;
import com.fidelity.integration.ClientDao;
import com.fidelity.integration.OrderDao;
import com.fidelity.integration.impl.TradeDaoImpl;
import com.fidelity.service.PriceService;
import com.fidelity.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.time.LocalDateTime;

@Service
public class TradeServiceImpl implements TradeService {
    @Autowired
    TradeDaoImpl tradeDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private PriceService priceService;

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
    public boolean processOrderRequest(Order order) throws Exception {
        // Check if client has enough balance for buying or enough instruments for selling
        if (order.getDirection() == Direction.BUY) {
            BigDecimal requiredBalance = order.getTargetPrice().multiply(new BigDecimal(order.getQuantity()));
            BigDecimal clientBalance = clientDao.getClientCashBalance(order.getClientId());
            if (clientBalance.compareTo(requiredBalance) < 0) {
                throw new InsufficientFundsException("Client does not have enough balance for this trade");
            }
        } else {
            int clientQuantity = clientDao.getClientInstrumentQuantity(order.getClientId(), order.getInstrumentId());
            if (clientQuantity < order.getQuantity()) {
                throw new InsufficientInstrumentsException("Client does not have enough instruments for this trade");
            }
        }

        // Insert order
        boolean orderInserted = orderDao.insertOrder(order);
        if (!orderInserted) {
            return false;
        }

        // Create and insert trade
        Trade trade = new Trade(order, order.getTargetPrice().multiply(new BigDecimal(order.getQuantity())),
                order.getQuantity(), order.getDirection(), order.getInstrumentId(), order.getClientId(),
                LocalDateTime.now(), order.getTargetPrice());
        tradeDao.insertTrade(trade);

        // Update client portfolio and cash balance
        BigDecimal cashValueChange = order.getDirection() == Direction.BUY ?
                trade.getCashValue().negate() : trade.getCashValue();
        clientDao.updateClientPortfolio(order.getClientId(), order.getInstrumentId(), order.getQuantity(), order.getDirection(), cashValueChange);
        clientDao.updateClientCashBalance(order.getClientId(), cashValueChange);

        return true;
    }
}
