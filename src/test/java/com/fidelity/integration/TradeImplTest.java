package com.fidelity.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fidelity.business.entity.Client;
import com.fidelity.business.enums.Direction;
import com.fidelity.business.entity.Order;
import com.fidelity.business.entity.Trade;
import com.fidelity.integration.impl.TradeDaoImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;




@Transactional
class TradeImplTest {

    @Autowired
    TradeDaoImpl dao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getTradeHistoryListNotNull(){
        Client c = new Client();
        c.setName("krishna");
        c.setId(1);
        c.setCountry("India");
        c.setEmail("sameer@gmail.com");
        List<Trade> tradeList = dao.getTradeHistory(c);
        assertNotNull(tradeList);
    }

    @Test
    void getTradeHistoryOrderObjectNotNull(){
        Client c = new Client();
        c.setName("krishna");
        c.setId(1);
        c.setCountry("India");
        c.setEmail("sameer@gmail.com");
        List<Trade> tradeList = dao.getTradeHistory(c);
        assertNotNull(tradeList.get(0).getOrder());
    }
    @Test
    void getTradeHistoryObjectsSize(){
        Client c = new Client();
        c.setName("krishna");
        c.setId(1);
        c.setCountry("India");
        c.setEmail("sameer@gmail.com");
        List<Trade> tradeList = dao.getTradeHistory(c);
        assertTrue(12 <= dao.getTradeHistory(c).size());
    }

    @Test
    void testAddTrade_Success() throws Exception {
        Client client = new Client();
        client.setName("Aparna");
        client.setId(1);
        client.setCountry("India");
        client.setEmail("aparna@gmail.com");
        List<Trade> tradesBeforeInsertion = dao.getTradeHistory(client);
        int initialSize = tradesBeforeInsertion.size();
        Order order = new Order();
        order.setOrderId("ORD004");
        Trade trade = new Trade(order,new BigDecimal(250200),20, Direction.SELL,
                "AMZN",1,"TRD006", LocalDateTime.now(),new BigDecimal(500));

        dao.insertTrade(trade);

        List<Trade> tradesAfterInsertion = dao.getTradeHistory(client);
        int newSize = tradesAfterInsertion.size();

        assertEquals(initialSize + 1, newSize);
    }


}

