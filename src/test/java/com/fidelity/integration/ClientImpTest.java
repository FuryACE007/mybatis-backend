package com.fidelity.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTableWhere;

import com.fidelity.business.entity.Client;
import com.fidelity.business.entity.ClientIdentification;
import com.fidelity.business.entity.InstrumentHistData;
import com.fidelity.business.entity.InvestmentPreference;
import com.fidelity.business.enums.CustomerScore;
import com.fidelity.business.enums.IncomeCategory;
import com.fidelity.business.enums.LengthOfInvestment;
import com.fidelity.business.enums.RiskTolerance;
import com.fidelity.integration.impl.ClientDaoImpl;
import com.fidelity.service.ClientService;
import org.junit.jupiter.api.Disabled;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class ClientImpTest {

    @Autowired
    ClientDaoImpl clientDao;

    @Autowired
    ClientService clientService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testInsertClient() {
        int oldSize = clientDao.getAllClients().size();
        Set<ClientIdentification> identities = new HashSet<>();
        identities.add(new ClientIdentification(0, "SSN", "123-145-881"));
        identities.add(new ClientIdentification(0, "Passport", "A1239967"));
        var newPref = new InvestmentPreference(0, "SIP", RiskTolerance.CONSERVATIVE, IncomeCategory.eightyk_to_onelakhk, LengthOfInvestment.five_to_seven_years);
        Client client = new Client(15, "Alicia", "USA", "alicia@example.com", LocalDate.now(), "600009", "securepassword113", identities, new BigDecimal(10000), newPref);


        assertDoesNotThrow(() -> clientDao.insertClient(client));
        int newSize = clientDao.getAllClients().size();
        assertEquals(newSize, oldSize + 1);

    }

    @Test
    void testInsertClientWithMissingFields() {
        int oldSize = clientDao.getAllClients().size();
        Client client = new Client(0, null, "USA", "missing@example.com", LocalDate.now(), "600009", "password", new HashSet<>(), new BigDecimal(10000), null);

        assertThrows(IllegalArgumentException.class, () -> clientDao.insertClient(client));
        int newSize = clientDao.getAllClients().size();
        assertEquals(oldSize, newSize);
    }

    @Test
    void testInsertClientWithDuplicateIdentifications() {
        int oldSize = clientDao.getAllClients().size();
        Set<ClientIdentification> identities = new HashSet<>();
        identities.add(new ClientIdentification(0, "SSN", "123-145-881"));
        identities.add(new ClientIdentification(0, "SSN", "123-145-881")); // Duplicate SSN
        Client client = new Client(16, "John", "USA", "john@example.com", LocalDate.now(), "600010", "securepassword114", identities, new BigDecimal(15000), null);

        assertThrows(SQLException.class, () -> clientDao.insertClient(client));
        int newSize = clientDao.getAllClients().size();
        assertEquals(oldSize, newSize);
    }

    @Test
    void testInsertClientWithInvalidEmail() {
        Set<ClientIdentification> identities = new HashSet<>();
        identities.add(new ClientIdentification(0, "SSN", "123-145-883"));
        Client client = new Client(0, "Emma", "USA", "invalid-email", LocalDate.now().minusYears(30), "600011", "securepassword115", identities, new BigDecimal(20000), null);

        assertThrows(IllegalArgumentException.class, () -> clientDao.insertClient(client));
    }

    @Test
    void testInsertClientWithNegativeBalance() {
        Set<ClientIdentification> identities = new HashSet<>();
        identities.add(new ClientIdentification(0, "SSN", "123-145-884"));
        Client client = new Client(0, "Liam", "USA", "liam@example.com", LocalDate.now().minusYears(30), "600012", "securepassword116", identities, new BigDecimal(-5000), null);

        assertThrows(IllegalArgumentException.class, () -> clientDao.insertClient(client));
    }

    @Test
    void testInsertClientWithFutureDOB() {
        Set<ClientIdentification> identities = new HashSet<>();
        identities.add(new ClientIdentification(0, "SSN", "123-145-885"));
        Client client = new Client(0, "Olivia", "USA", "olivia@example.com", LocalDate.now().plusDays(1), "600013", "securepassword117", identities, new BigDecimal(25000), null);

        assertThrows(IllegalArgumentException.class, () -> clientDao.insertClient(client));
    }

    @Test
    void testGetAllClients() {
        List<Client> clientList = clientDao.getAllClients();
        assertNotNull(clientList);
        assertEquals(5, clientList.size());
    }

    @Test
    void testUpdatePreference() {
        // ARRANGE
        var rowCount = countRowsInTable(jdbcTemplate, "investmentpreference");
        var newPref = new InvestmentPreference(2, "SIP", RiskTolerance.CONSERVATIVE, IncomeCategory.eightyk_to_onelakhk, LengthOfInvestment.five_to_seven_years);
        Client client = new Client(15, "Alicia", "USA", "alicia@example.com", LocalDate.now(), "600009", "securepassword113", null, new BigDecimal(10000), newPref);
        // ACT
        clientService.updateClientInvestmentPref(client);
        // ASSERT
        assertEquals(rowCount, countRowsInTable(jdbcTemplate, "investmentpreference"));
        assertEquals(1, countRowsInTableWhere(jdbcTemplate, "investmentpreference", """
                	 clientid = 2 and
                 preference='SIP' and score = 'Medium'
                """));
    }

    @Disabled
    @Test
    void testGetRecommendation() {
        var newPref = new InvestmentPreference(2, "SIP", RiskTolerance.CONSERVATIVE, IncomeCategory.eightyk_to_onelakhk, LengthOfInvestment.five_to_seven_years);
        newPref.setClientScore(CustomerScore.Safe);
        List<InstrumentHistData> topStocks = clientDao.getTradeRecommendation(newPref);
        assertNotNull(topStocks);
    }

    @Test
    void testVerifyClientLoginSuccessful() {
        Client client = new Client();
        client.setEmail("john.doe@email.com");
        client.setPassword("password123");
        boolean result = clientDao.verifyClientLogin(client);
        assertTrue(result, "Client should be able to log in with correct credentials.");
    }

    @Test
    void testVerifyClientLoginFailed() {
        Client client = new Client();
        client.setEmail("wrong@example.com");
        client.setPassword("wrongpassword");
        boolean result = clientDao.verifyClientLogin(client);
        assertFalse(result, "Client should not be able to log in with incorrect credentials.");
    }

    @Test
    void testVerifyEmailExists()
    {
        assertTrue(clientDao.verifyEmailExists("john.doe@email.com"));
    }

    @Test
    void testNonExistantEmail()
    {
        assertFalse(clientDao.verifyEmailExists("casper@email.com"));
    }
}







