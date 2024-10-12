package com.fidelity.restcontroller;

import com.fidelity.business.entity.Trade;
import com.fidelity.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/trades")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @PostMapping
    public ResponseEntity<String> createTrade(@RequestBody Trade trade, @RequestParam BigDecimal askPrice) {
        try {
            boolean success = tradeService.processTradeRequest(trade, askPrice);
            if (success) {
                return ResponseEntity.ok("Trade processed successfully");
            } else {
                return ResponseEntity.badRequest().body("Trade could not be processed due to price mismatch");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing trade: " + e.getMessage());
        }
    }
}