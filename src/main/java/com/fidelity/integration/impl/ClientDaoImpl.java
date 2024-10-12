package com.fidelity.integration.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import com.fidelity.business.entity.*;
import com.fidelity.business.enums.CustomerScore;
import com.fidelity.business.enums.Direction;
import com.fidelity.integration.ClientDao;
import com.fidelity.integration.mapper.ClientMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository("clientDao")
public class ClientDaoImpl implements ClientDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private ClientMapper mapper;

    @Override
    public List<Client> getAllClients() {
        return mapper.getAllClients();
    }

    @Override
    public boolean verifyEmailExists(String clientEmail) {
        return mapper.verifyEmailExists(clientEmail) != null;

    }

    @Override
    public void insertClient(Client client) throws SQLException {
        // Check for missing required fields
        if (client.getName() == null || client.getEmail() == null || client.getCountry() == null) {
            throw new IllegalArgumentException("Client name, email, and country are required.");
        }

        // Check for invalid email format
        if (!client.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }

        // Check for negative balance
        if (client.getCashValue().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Balance cannot be negative.");
        }

        // Check for future date of birth
        if (client.getDob().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of birth cannot be in the future.");
        }

        // Check for duplicate identifications
        Set<String> identificationTypes = new HashSet<>();
        for (ClientIdentification identity : client.getIdentities()) {
            if (!identificationTypes.add(identity.getType())) {
                throw new SQLException("Duplicate identification type: " + identity.getType());
            }
        }

        // Insert client
        mapper.insertClient(client);

        // Insert client identifications
        for (ClientIdentification identity : client.getIdentities()) {
            identity.setId(client.getId());
            mapper.insertClientIdentification(identity);
        }

        // Insert client investment preference
        InvestmentPreference pref = client.getPreference();
        if (pref != null) {
            pref.setId(client.getId());
            mapper.insertClientInvestmentPref(pref);
        }
    }
    @Override
    public boolean verifyClientLogin(Client client) {
        int count = mapper.verifyClientLogin(client.getEmail(), client.getPassword());
        return count > 0;
    }

    @Override
    public void updateClientInvestmentPref(Client client) {
        InvestmentPreference preference = client.getPreference();
        mapper.updateClientInvestmentPref(preference);
    }
    // To remove and add to a separate dao of portfolio
    @Override
    public List<ClientPortfolio> getClientPortfolio(int clientId) {
        return mapper.getClientPortfolio(clientId);
    }

    @Override
    public List<InstrumentHistData> getTradeRecommendation(InvestmentPreference preference) {
        if (preference.getClientScore() == CustomerScore.Safe) {
            List<Double> stkRecPoint = new ArrayList<>();
            int index1 = -1;
            int index2 = -1;

            String capDetails = "Large Cap";
            List<InstrumentHistData> stocks = mapper.getTradeRecommendation(capDetails, preference);
            InstrumentHistData histData = mapper.processResultSetForMktData();
            System.out.println(stocks.size());
            System.out.println(histData.getInstrumentId());
            double[] mktPrice = {histData.getCurrDayPrice(), histData.getPrev1DayPrice(), histData.getPrev2DayPrice(), histData.getPrev3DayPrice(), histData.getPrev4DayPrice()};
            for (InstrumentHistData hd : stocks) {
                double[] stockPrice = {hd.getCurrDayPrice(), hd.getPrev1DayPrice(), hd.getPrev2DayPrice(), hd.getPrev3DayPrice(), hd.getPrev4DayPrice()};
                RoboAdvisor roboAdvisor = new RoboAdvisor(mktPrice, stockPrice);
                stkRecPoint.add(roboAdvisor.stockRecommendationPoint());
            }
            double max1 = Double.NEGATIVE_INFINITY;
            double max2 = Double.NEGATIVE_INFINITY;

            for (int i = 0; i < stkRecPoint.size(); i++) {
                System.out.println(stkRecPoint.get(i));
                if (stkRecPoint.get(i) > max1) {
                    max2 = max1;
                    index2 = index1;
                    max1 = stkRecPoint.get(i);
                    index1 = i;
                } else if (stkRecPoint.get(i) > max2) {
                    max2 = stkRecPoint.get(i);
                    index2 = i;
                }
            }
            List<InstrumentHistData> topStocks = new ArrayList<>();
            topStocks.add(stocks.get(index1));
            topStocks.add(stocks.get(index2));
            return topStocks;
        } else {
            return List.of();

        }
    }

    @Override
    public void updateClientPortfolio(int clientId, String instrumentId, int quantity, Direction direction, BigDecimal cashValueChange) {
        int quantityChange = direction == Direction.BUY ? quantity : -quantity;
        mapper.updateClientPortfolio(clientId, instrumentId, quantityChange, cashValueChange);
    }

    @Override
    public void updateClientCashBalance(int clientId, BigDecimal cashValueChange) {
        mapper.updateClientCashBalance(clientId, cashValueChange);
    }

}


