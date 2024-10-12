package com.fidelity.integration;

import com.fidelity.business.entity.Client;
import com.fidelity.business.entity.Order;
import com.fidelity.business.entity.Trade;
import com.fidelity.business.enums.Direction;
import com.fidelity.integration.impl.TradeDaoImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class TradeMyBatisImplementationTest {

    @Autowired
    private TradeDaoImpl tradeDao;

    @Test
    void testDaoCreated() {
        assertNotNull(tradeDao);
    }

    @Test
    void testGetTradeHistory() {
        Client client = new Client();
        client.setId(1);
        client.setName("Test Client");
        client.setCountry("Test Country");
        client.setEmail("test@example.com");

        List<Trade> tradeHistory = tradeDao.getTradeHistory(client);

        assertNotNull(tradeHistory, "The trade history should not be null");
        assertFalse(tradeHistory.isEmpty(), "The trade history should have at least one entry");
    }

    @Test
    void testInsertTrade() throws Exception {
        Order order = new Order("AAPL", 10, new BigDecimal("150.00"), Direction.BUY, 1);
        Trade trade = new Trade(order, new BigDecimal("1500.00"), 10, Direction.BUY, "AAPL", 1, LocalDateTime.now(), new BigDecimal("150.00"));

        tradeDao.insertTrade(trade);

        Client client = new Client();
        client.setId(1);
        List<Trade> tradeHistory = tradeDao.getTradeHistory(client);

        assertTrue(tradeHistory.stream().anyMatch(t -> t.getOrder().getOrderId().equals(order.getOrderId())),
                "The inserted trade should be present in the trade history");
    }

    @Test
    void testInsertTradeWithNullTrade() {
        assertThrows(IllegalArgumentException.class, () -> tradeDao.insertTrade(null),
                "Inserting a null trade should throw an IllegalArgumentException");
    }

    @Test
    void testGetTradeHistoryWithNullClient() {
        assertThrows(IllegalArgumentException.class, () -> tradeDao.getTradeHistory(null),
                "Getting trade history with a null client should throw an IllegalArgumentException");
    }

    @Test
    void testGetTradeHistoryWithNonExistentClient() {
        Client nonExistentClient = new Client();
        nonExistentClient.setId(9999);

        List<Trade> tradeHistory = tradeDao.getTradeHistory(nonExistentClient);

        assertTrue(tradeHistory.isEmpty(), "Trade history for a non-existent client should be empty");
    }
}