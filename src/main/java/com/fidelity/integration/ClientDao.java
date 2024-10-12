package com.fidelity.integration;

import com.fidelity.business.entity.Client;
import com.fidelity.business.entity.ClientPortfolio;
import com.fidelity.business.entity.InstrumentHistData;
import com.fidelity.business.entity.InvestmentPreference;
import com.fidelity.business.enums.Direction;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface ClientDao {
    List<Client> getAllClients();
    boolean verifyEmailExists(String clientEmail);
    void insertClient(Client client) throws SQLException;
    boolean verifyClientLogin(Client client);
    void updateClientInvestmentPref(Client client);
    List<ClientPortfolio> getClientPortfolio(int clientId);
    List<InstrumentHistData> getTradeRecommendation(InvestmentPreference preference);
    void updateClientPortfolio(int clientId, String instrumentId, int quantity, Direction direction, BigDecimal cashValueChange);
    void updateClientCashBalance(int clientId, BigDecimal cashValueChange);
}