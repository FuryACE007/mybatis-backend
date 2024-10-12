package com.fidelity.service.impl;

import com.fidelity.business.entity.ClientPortfolio;
import com.fidelity.integration.impl.ClientDaoImpl;
import com.fidelity.integration.mapper.ClientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@Transactional
class ClientServiceTest {

    @Mock
    private ClientDaoImpl clientDao;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Autowired
    ClientServiceImpl realClientService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetClientPortfolioWithInvalidClientId() {
        assertThrows(IllegalArgumentException.class, () -> clientService.getClientPortfolio(-1));
    }

    @Test
    public void testGetClientPortfolioWithDatabaseError() {
        int clientId = 1;
        when(clientDao.getClientPortfolio(clientId)).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> clientService.getClientPortfolio(clientId));
    }

    @Test
    public void testGetClientPortfolioWithValidClientId() {
        int clientId = 1;
        List<ClientPortfolio> mockPortfolio = Collections.singletonList(new ClientPortfolio());
        when(clientDao.getClientPortfolio(clientId)).thenReturn(mockPortfolio);

        List<ClientPortfolio> portfolio = clientService.getClientPortfolio(clientId);
        assertNotNull(portfolio);
    }

    // Returns a list of ClientPortfolio objects for a valid clientId
    @Test
    public void testReturnsClientPortfolioForValidClientId() {
        ClientMapper mapper = mock(ClientMapper.class);
        ClientDaoImpl clientDao = mock(ClientDaoImpl.class);

        int validClientId = 1;
        List<ClientPortfolio> expectedPortfolio = Arrays.asList(
                new ClientPortfolio("inst1", "desc1", 10, new BigDecimal("1000.00")),
                new ClientPortfolio("inst2", "desc2", 5, new BigDecimal("500.00"))
        );
        when(clientDao.getClientPortfolio(validClientId)).thenReturn(expectedPortfolio);

        List<ClientPortfolio> actualPortfolio = clientDao.getClientPortfolio(validClientId);

        assertEquals(expectedPortfolio, actualPortfolio);
    }

    @Test
    public void testHandlesNonexistentClientId() {
        ClientMapper mapper = mock(ClientMapper.class);
        ClientDaoImpl clientDao = mock(ClientDaoImpl.class);
        int nonexistentClientId = 999;
        when(clientDao.getClientPortfolio(nonexistentClientId)).thenReturn(Collections.emptyList());

        List<ClientPortfolio> actualPortfolio = clientDao.getClientPortfolio(nonexistentClientId);

        assertTrue(actualPortfolio.isEmpty());
    }

    @Test
    void testGetClientPortfolioWithRealDao() {
        int clientId = 1;
        List<ClientPortfolio> portfolio = realClientService.getClientPortfolio(clientId);
        assertNotNull(portfolio);
        assertFalse(portfolio.isEmpty(), "Client portfolio should not be empty");
    }

    @Test
    void testGetClientPortfolioReturnsNullForNonExistentUser() {
        int clientId = 99;
        List<ClientPortfolio> portfolio = realClientService.getClientPortfolio(clientId);
        assertTrue(portfolio.isEmpty());
    }


    @Test
    void testVerifyEmailExistsTrue(){
        Mockito.when(clientDao.verifyEmailExists("someemail@gmail.com")).thenReturn(true);
        Boolean val = clientService.verifyEmailExists("someemail@gmail.com");
        assertEquals(true,val);
    }

    @Test
    void testVerifyEmailExistsEmailNull(){
        Mockito.when(clientDao.verifyEmailExists("")).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class,() ->{clientService.verifyEmailExists("");});
    }

    @Test
    void testVerifyClientCantLogin(){
        Mockito.when(clientDao.verifyClientLogin(null)).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class,() -> {clientService.verifyClient(null);});
    }


}