//package com.fidelity.service.impl;
//
//import com.fidelity.business.entity.Client;
//import com.fidelity.business.enums.Direction;
//import com.fidelity.business.entity.Order;
//import com.fidelity.business.entity.Trade;
//import com.fidelity.integration.impl.TradeDaoImpl;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//class TradeServiceTest {
//
//    @Mock
//    TradeDaoImpl tradeDao;
//    @InjectMocks
//    @Autowired
//    TradeServiceImpl tradeService;
//
//    @BeforeEach
//    void setUp() throws Exception {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//
//    @Test
//    void testServiceRetrievesListOfTrades(){
//        List<Trade> tradeList = new ArrayList<>();
//        Order o1 = new Order("AAPl",10, new BigDecimal("100"),Direction.BUY,"10","ORD1");
//        tradeList.add(new Trade(o1,new BigDecimal(10000),10,Direction.BUY,"AAPl",10,"TRD01", LocalDateTime.now(),new BigDecimal(1001)));
//        tradeList.add(new Trade(o1,new BigDecimal(10000),10,Direction.BUY,"GOOGL",10,"TRD02", LocalDateTime.now(),new BigDecimal(1001)));
//        Client c = new Client();
//        c.setId(10);
//        Mockito.when(tradeDao.getTradeHistory(c)).thenReturn(tradeList);
//        List<Trade> actualTList = tradeService.getTradeHistory(c);
//        assertEquals(actualTList,tradeList);
//    }
//
//    @Test
//    void testServiceThrowsNPEWhenClientIsNUll(){
//        List<Trade> tradeList = new ArrayList<>();
//        Order o1 = new Order("AAPl",10, new BigDecimal("100"),Direction.BUY,"10","ORD1");
//        tradeList.add(new Trade(o1,new BigDecimal(10000),10,Direction.BUY,"AAPl",10,"TRD01", LocalDateTime.now(),new BigDecimal(1001)));
//        tradeList.add(new Trade(o1,new BigDecimal(10000),10,Direction.BUY,"GOOGL",10,"TRD02", LocalDateTime.now(),new BigDecimal(1001)));
//        Client c = new Client();
//        Mockito.when(tradeDao.getTradeHistory(c)).thenThrow(NullPointerException.class);
//        assertThrows(NullPointerException.class,()-> {tradeService.getTradeHistory(c);});
//    }
//
//    @Test
//    void testServiceThrowsIllegalArgWhenNullTrade() throws Exception {
//        assertThrows(IllegalArgumentException.class,()->{
//            tradeService.insertTrade(null);
//        });
//    }
//
//    @Test
//    void testServiceThrowsNPE() throws Exception {
//        Client client = new Client();
//        client.setName("Aparna");
//        client.setId(1);
//        client.setCountry("India");
//        client.setEmail("aparna@gmail.com");
//        List<Trade> tradesBeforeInsertion = tradeDao.getTradeHistory(client);
//        int initialSize = tradesBeforeInsertion.size();
//        Order order = new Order();
//        order.setOrderId("ORD004");
//        Trade trade = new Trade(order,new BigDecimal(250200),20, Direction.SELL,
//                "AMZN",1,"TRD006", LocalDateTime.now(),new BigDecimal(500));
//
//        Mockito.doThrow(NullPointerException.class).when(tradeDao).insertTrade(trade);
//        Exception exception = assertThrows(Exception.class, () -> {
//            tradeService.insertTrade(trade);
//        });
//        assertEquals("Error adding trade to the database", exception.getMessage());
//    }
//
//}