package com.fidelity.restcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fidelity.business.entity.Order;
import com.fidelity.business.enums.Direction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class TradeControllerE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void placeOrder_ShouldReturnOk_WhenOrderIsValid() throws Exception {
        Order order = new Order("AAPL", 10, new BigDecimal("150.00"), Direction.BUY, 1);

        ResultActions response = mockMvc.perform(post("/api/trades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)));

        response.andExpect(status().isOk());
    }

    @Test
    void placeOrder_ShouldReturnBadRequest_WhenInsufficientFunds() throws Exception {
        Order order = new Order("AAPL", 1000000, new BigDecimal("150.00"), Direction.BUY, 1);

        ResultActions response = mockMvc.perform(post("/api/trades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)));

        response.andExpect(status().isBadRequest());
    }

    @Test
    void placeOrder_ShouldReturnBadRequest_WhenInsufficientInstruments() throws Exception {
        Order order = new Order("AAPL", 1000000, new BigDecimal("150.00"), Direction.SELL, 1);

        ResultActions response = mockMvc.perform(post("/api/trades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)));

        response.andExpect(status().isBadRequest());
    }

    @Test
    void placeOrder_ShouldReturnBadRequest_WhenPriceMismatch() throws Exception {
        Order order = new Order("AAPL", 10, new BigDecimal("1.00"), Direction.BUY, 1);

        ResultActions response = mockMvc.perform(post("/api/trades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)));

        response.andExpect(status().isBadRequest());
    }

    @Test
    void placeOrder_ShouldReturnBadRequest_WhenInvalidInstrument() throws Exception {
        Order order = new Order("INVALID", 10, new BigDecimal("150.00"), Direction.BUY, 1);

        ResultActions response = mockMvc.perform(post("/api/trades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)));

        response.andExpect(status().isBadRequest());
    }
}