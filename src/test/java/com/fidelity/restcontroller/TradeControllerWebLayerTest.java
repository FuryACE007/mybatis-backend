package com.fidelity.restcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fidelity.business.entity.Order;
import com.fidelity.business.enums.Direction;
import com.fidelity.exceptions.InsufficientFundsException;
import com.fidelity.exceptions.InsufficientInstrumentsException;
import com.fidelity.service.TradeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TradeController.class)
public class TradeControllerWebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeService tradeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void placeOrder_ShouldReturnOk_WhenOrderProcessedSuccessfully() throws Exception {
        Order order = new Order("AAPL", 100, new BigDecimal("150.00"), Direction.BUY, 1);
        when(tradeService.processOrderRequest(any(Order.class))).thenReturn(true);

        ResultActions response = mockMvc.perform(post("/api/trades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)));

        response.andExpect(status().isOk());
    }

    @Test
    void placeOrder_ShouldReturnBadRequest_WhenPriceMismatch() throws Exception {
        Order order = new Order("AAPL", 100, new BigDecimal("150.00"), Direction.BUY, 1);
        when(tradeService.processOrderRequest(any(Order.class))).thenReturn(false);

        ResultActions response = mockMvc.perform(post("/api/trades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)));

        response.andExpect(status().isBadRequest());
    }

    @Test
    void placeOrder_ShouldReturnBadRequest_WhenInsufficientFunds() throws Exception {
        Order order = new Order("AAPL", 100, new BigDecimal("150.00"), Direction.BUY, 1);
        when(tradeService.processOrderRequest(any(Order.class))).thenThrow(new InsufficientFundsException("Insufficient funds"));

        ResultActions response = mockMvc.perform(post("/api/trades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)));

        response.andExpect(status().isBadRequest());
    }

    @Test
    void placeOrder_ShouldReturnBadRequest_WhenInsufficientInstruments() throws Exception {
        Order order = new Order("AAPL", 100, new BigDecimal("150.00"), Direction.SELL, 1);
        when(tradeService.processOrderRequest(any(Order.class))).thenThrow(new InsufficientInstrumentsException("Insufficient instruments"));

        ResultActions response = mockMvc.perform(post("/api/trades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)));

        response.andExpect(status().isBadRequest());
    }

    @Test
    void placeOrder_ShouldReturnInternalServerError_WhenUnexpectedErrorOccurs() throws Exception {
        Order order = new Order("AAPL", 100, new BigDecimal("150.00"), Direction.BUY, 1);
        when(tradeService.processOrderRequest(any(Order.class))).thenThrow(new RuntimeException("Unexpected error"));

        ResultActions response = mockMvc.perform(post("/api/trades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)));

        response.andExpect(status().isInternalServerError());
    }
}