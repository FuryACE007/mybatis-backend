package com.fidelity.service.impl;

import com.fidelity.business.entity.Client;
import com.fidelity.business.entity.ClientPortfolio;
import com.fidelity.integration.impl.ClientDaoImpl;
import com.fidelity.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;


@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientDaoImpl dao;

    @Override
    public void insertClient(Client client) throws SQLException {
        dao.insertClient(client);
    }
    @Override
    public List<ClientPortfolio> getClientPortfolio(int clientId) {
        if (clientId <= 0) {
            throw new IllegalArgumentException("Invalid client ID");
        }
        try {
            return dao.getClientPortfolio(clientId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve client portfolio", e);
        }
    }

    @Override
    public void updateClientInvestmentPref(Client client) {
        dao.updateClientInvestmentPref(client);
    }



    @Override
    public boolean verifyClient(Client client) throws Exception {
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }
        try {
            return dao.verifyClientLogin(client);
        } catch (Exception e) {
            throw new Exception("Error verifying Client", e);
        }
    }

    @Override
    public boolean verifyEmailExists(String clientEmail) {
        if (clientEmail.isEmpty()) {
            throw new IllegalArgumentException("Invalid client Email");
        }
        try {
            return dao.verifyEmailExists(clientEmail);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve client with the given email", e);
        }
    }
}