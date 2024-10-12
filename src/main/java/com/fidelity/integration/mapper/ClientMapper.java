package com.fidelity.integration.mapper;


import com.fidelity.business.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.math.BigDecimal;

@Mapper
public interface ClientMapper {

    @Select("select id,name,email,dateofbirth,country,postalcode,password,cashvalue from client ")
    List<Client> getAllClients();

    // 1 .Add a method to verify an email address.
    @Select("select id,name,email,dateofbirth,country,postalcode,password,cashvalue from client where email=#{clientEmail} ")
    Boolean verifyEmailExists(String clientEmail); // query person table to check email exists and confirm the same clientid exists in client table

    // 2. Create an method in the ClientDao interface and JDBC API implementation class
    // to add a client to the database.
    @Insert("INSERT INTO Client (id,name, email, dateofbirth, country, postalcode, password, cashvalue) " +
            "VALUES (#{id},#{name}, #{email}, #{dob}, #{country}, #{postalCode}, #{password}, #{cashValue})")
    void insertClient(Client client);


    @Insert("INSERT INTO ClientIdentification (id, type, value) " +
            "VALUES (#{id}, #{type}, #{value})")
    void insertClientIdentification(ClientIdentification identity);


    @Insert("""
            INSERT INTO investmentpreference (clientId, riskTolerance, income, lengthOfInvestment, preference, score)
                                                      				            VALUES (#{id},#{riskTolerance},#{incomeCategory},#{lengthOfInvestment},#{investmentPreference},#{clientScore})
            """)
    void insertClientInvestmentPref(InvestmentPreference pref);
    // Build unit tests to verify the client insert into the database.

    // 3. Create an method in the ClientDao interface and JDBC API implementation class
    // to verify a client login.

    @Select("SELECT COUNT(*) FROM client WHERE email = #{email} AND password = #{password}")
    int verifyClientLogin(@Param("email") String email, @Param("password") String password);

    // 4. Create an method in the ClientDao interface and JDBC API implementation class
    // to add a client’s preferences to the database.
    @Update("""
            UPDATE investmentpreference set riskTolerance=#{riskTolerance},income=#{incomeCategory},lengthOfInvestment=#{lengthOfInvestment},preference=#{investmentPreference},score=#{clientScore} where clientId=#{id}
            """)
    void updateClientInvestmentPref(InvestmentPreference preference);


    @Select("SELECT i.instrumentId, i.description, SUM(t.quantity) AS quantity, " +
            "(c.cashValue + COALESCE(SUM(CASE WHEN t.direction = 'SELL' THEN t.executionPrice * t.quantity ELSE 0 END), 0) " +
            "- COALESCE(SUM(CASE WHEN t.direction = 'BUY' THEN t.executionPrice * t.quantity ELSE 0 END), 0)) AS availableCashBalance " +
            "FROM Client c " +
            "LEFT JOIN Trade t ON c.id = t.clientId " +
            "LEFT JOIN Instrument i ON t.instrumentId = i.instrumentId " +
            "WHERE c.id = #{clientId} " +
            "GROUP BY i.instrumentId, i.description, c.cashValue")
    List<ClientPortfolio> getClientPortfolio(int clientId);

    @Select(""" 
               <script> SELECT
                ih.instrumentid,
                ih.currday,
                ih.prev1day,
                ih.prev2day,
                ih.prev3day,
                ih.prev4day
            FROM
                     instrument i
                JOIN instrumenthistoryprice ih ON i.instrumentid = ih.instrumentid <if test='mktCapDetails != null'> WHERE  i.mktcapdetails = #{mktCapDetails}</if>
                </script>""")
    List<InstrumentHistData> getTradeRecommendation(@Param("mktCapDetails") String mktCapDetails, InvestmentPreference investmentPreference);

    @Select(""" 
             SELECT
             					    ih.instrumentid,
             					    ih.currday,
             					    ih.prev1day,
             					    ih.prev2day,
             					    ih.prev3day,
             					    ih.prev4day
             					FROM
             					     instrumenthistoryprice ih
             					WHERE
             					    ih.instrumentid = 'SP500'
            """)
    InstrumentHistData processResultSetForMktData();

    // Update client portfolio and cash value
    @Update("""
    UPDATE Portfolio SET
    quantity = quantity + #{quantityChange},
    cashValue = cashValue + #{cashValueChange}
    WHERE clientId = #{clientId} AND instrumentId = #{instrumentId}
    """)
    void updateClientPortfolio(@Param("clientId") int clientId, 
                                @Param("instrumentId") String instrumentId, 
                                @Param("quantityChange") int quantityChange,
                                @Param("cashValueChange") BigDecimal cashValueChange);


    // Update client cash balance
    @Update("UPDATE Client SET cashValue = cashValue + #{cashValueChange} " +
            "WHERE id = #{clientId}")
    void updateClientCashBalance(@Param("clientId") int clientId, 
                                 @Param("cashValueChange") BigDecimal cashValueChange);



    @Select("SELECT cashValue FROM Client WHERE id = #{clientId}")
    BigDecimal getClientCashBalance(int clientId);

    @Select("SELECT quantity FROM Portfolio WHERE clientId = #{clientId} AND instrumentId = #{instrumentId}")
    int getClientInstrumentQuantity(int clientId, String instrumentId);



}
