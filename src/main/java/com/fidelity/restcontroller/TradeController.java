package com.fidelity.restcontroller;

import com.fidelity.business.entity.Order;
import com.fidelity.service.TradeService;
import com.fidelity.exceptions.InsufficientFundsException;
import com.fidelity.exceptions.InsufficientInstrumentsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trades")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody Order order) {
        try {
            boolean success = tradeService.processOrderRequest(order);
            if (success) {
                return ResponseEntity.ok("Order processed successfully");
            } else {
                return ResponseEntity.badRequest().body("Order could not be processed due to price mismatch");
            }
        } catch (InsufficientFundsException | InsufficientInstrumentsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error processing order: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing order: " + e.getMessage());
        }
    }
}