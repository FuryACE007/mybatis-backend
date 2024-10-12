package com.fidelity.service;

import com.fidelity.business.entity.Client;
import com.fidelity.business.entity.ClientPortfolio;
import java.util.List;

import java.sql.SQLException;


public interface ClientService {
    public boolean verifyEmailExists(String clientEmail);
    public boolean verifyClient(Client client) throws Exception;
    public void insertClient(Client client) throws SQLException;

    List<ClientPortfolio> getClientPortfolio(int clientId);

    public  void updateClientInvestmentPref(Client client);
}