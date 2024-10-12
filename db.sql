-- Drop existing tables if they exist

BEGIN

   EXECUTE IMMEDIATE 'DROP TABLE Trade';

EXCEPTION

   WHEN OTHERS THEN

      IF SQLCODE != -942 THEN RAISE; END IF;

END;

/

BEGIN

   EXECUTE IMMEDIATE 'DROP TABLE Order_Table';

EXCEPTION

   WHEN OTHERS THEN

      IF SQLCODE != -942 THEN RAISE; END IF;

END;

/

BEGIN

   EXECUTE IMMEDIATE 'DROP TABLE Price';

EXCEPTION

   WHEN OTHERS THEN

      IF SQLCODE != -942 THEN RAISE; END IF;

END;

/

BEGIN

   EXECUTE IMMEDIATE 'DROP TABLE INSTRUMENTHISTORYPRICE';

EXCEPTION

   WHEN OTHERS THEN

      IF SQLCODE != -942 THEN RAISE; END IF;

END;

/

BEGIN

   EXECUTE IMMEDIATE 'DROP TABLE Instrument';

EXCEPTION

   WHEN OTHERS THEN

      IF SQLCODE != -942 THEN RAISE; END IF;

END;

/

BEGIN

   EXECUTE IMMEDIATE 'DROP TABLE InvestmentPreference';

EXCEPTION

   WHEN OTHERS THEN

      IF SQLCODE != -942 THEN RAISE; END IF;

END;

/

BEGIN

   EXECUTE IMMEDIATE 'DROP TABLE ClientIdentification';

EXCEPTION

   WHEN OTHERS THEN

      IF SQLCODE != -942 THEN RAISE; END IF;

END;

/

BEGIN

   EXECUTE IMMEDIATE 'DROP TABLE Client';

EXCEPTION

   WHEN OTHERS THEN

      IF SQLCODE != -942 THEN RAISE; END IF;

END;

/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Portfolio';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN RAISE; END IF;
END;
/


-- Create tables

CREATE TABLE Client (

    id NUMBER PRIMARY KEY,

    name VARCHAR2(100),

    email VARCHAR2(100) NOT NULL unique,

    dateOfBirth DATE,

    country VARCHAR2(50),

    postalCode VARCHAR2(20),

    password VARCHAR2(100),

    cashValue NUMBER(10,2)

);

CREATE TABLE ClientIdentification (

    id NUMBER,

    type VARCHAR2(50),

    value VARCHAR2(100),

    CONSTRAINT fk_clientid_client FOREIGN KEY (id) REFERENCES Client(id)

);

CREATE TABLE InvestmentPreference (

    clientEmail VARCHAR2(100) PRIMARY KEY,

    riskTolerance VARCHAR2(50),

    income VARCHAR2(50),

    lengthOfInvestment VARCHAR2(50),

    preference VARCHAR2(50),

    score VARCHAR2(10),

    CONSTRAINT fk_investment_client FOREIGN KEY (clientEmail) REFERENCES Client(email)

);

CREATE TABLE Instrument (

   instrumentId VARCHAR2(50) PRIMARY KEY,

   description VARCHAR2(200),

   externalId VARCHAR2(50),

   externalIdType varchar2(50),

   maxQuantity NUMBER,

   minQuantity NUMBER,

   categoryId VARCHAR2(50),

   mktCapDetails varchar(20)

);

CREATE TABLE INSTRUMENTHISTORYPRICE (

   instrumentId VARCHAR2(50),

   currDay NUMBER,

   prev1Day NUMBER,

   prev2Day NUMBER,

   prev3Day NUMBER,

   prev4Day NUMBER,

   CONSTRAINT fk_hist_instrument FOREIGN KEY (instrumentId) REFERENCES Instrument(instrumentId)

);

CREATE TABLE Price (

    id NUMBER PRIMARY KEY,

    instrumentId VARCHAR2(50),

    targetPrice NUMBER(10,2),

    bidPrice NUMBER(10,2),

    timestamp TIMESTAMP,

    CONSTRAINT fk_price_instrument FOREIGN KEY (instrumentId) REFERENCES Instrument(instrumentId)

);

CREATE TABLE Order_Table (

    orderId VARCHAR2(50) PRIMARY KEY,

    instrumentId VARCHAR2(50),

    quantity NUMBER,

    targetPrice NUMBER(10,2),

    direction VARCHAR2(10),

    clientId NUMBER,

    CONSTRAINT fk_order_instrument FOREIGN KEY (instrumentId) REFERENCES Instrument(instrumentId),

    CONSTRAINT fk_order_client FOREIGN KEY (clientId) REFERENCES Client(id)

);

CREATE TABLE Trade (

    tradeId VARCHAR2(50) PRIMARY KEY,

    orderId VARCHAR2(50),

    cashValue NUMBER(10,2),

    quantity NUMBER,

    direction VARCHAR2(10),

    instrumentId VARCHAR2(50),

    clientId NUMBER,

    executionPrice NUMBER(10,2),

    executedTime timestamp,

    CONSTRAINT fk_trade_order FOREIGN KEY (orderId) REFERENCES Order_Table(orderId),

    CONSTRAINT fk_trade_instrument FOREIGN KEY (instrumentId) REFERENCES Instrument(instrumentId),

    CONSTRAINT fk_trade_client FOREIGN KEY (clientId) REFERENCES Client(id)

);

CREATE TABLE Portfolio (
    portfolioId NUMBER PRIMARY KEY,
    instrumentId VARCHAR2(50),
    clientId NUMBER,
    quantity NUMBER,
    cashValue NUMBER(10,2),
    CONSTRAINT fk_portfolio_client FOREIGN KEY (clientId) REFERENCES Client(id),
    CONSTRAINT fk_portfolio_instrument FOREIGN KEY (instrumentId) REFERENCES Instrument(instrumentId)
);

-- Insert sample data

INSERT INTO Client (id, name, email, dateOfBirth, country, postalCode, password, cashValue) VALUES

(1, 'John Doe', 'john.doe@email.com', TO_DATE('1980-05-15', 'YYYY-MM-DD'), 'USA', '10001', 'password123', 10000.00);

INSERT INTO Client (id, name, email, dateOfBirth, country, postalCode, password, cashValue) VALUES

(2, 'Jane Smith', 'jane.smith@email.com', TO_DATE('1985-08-22', 'YYYY-MM-DD'), 'Canada', 'M5V 2T6', 'securepass456', 15000.00);

INSERT INTO Client (id, name, email, dateOfBirth, country, postalCode, password, cashValue) VALUES

(3, 'Alice Johnson', 'alice.j@email.com', TO_DATE('1990-03-10', 'YYYY-MM-DD'), 'UK', 'SW1A 1AA', 'alicepass789', 8000.00);

INSERT INTO Client (id, name, email, dateOfBirth, country, postalCode, password, cashValue) VALUES

(4, 'Bob Williams', 'bob.w@email.com', TO_DATE('1975-11-30', 'YYYY-MM-DD'), 'Australia', '2000', 'bobsecure321', 20000.00);

INSERT INTO Client (id, name, email, dateOfBirth, country, postalCode, password, cashValue) VALUES

(5, 'Emma Brown', 'emma.b@email.com', TO_DATE('1988-07-18', 'YYYY-MM-DD'), 'Germany', '10115', 'emmapass654', 12000.00);

INSERT INTO ClientIdentification (id, type, value) VALUES (1, 'Passport', 'P123456');

INSERT INTO ClientIdentification (id, type, value) VALUES (1, 'Driver License', 'DL789012');

INSERT INTO ClientIdentification (id, type, value) VALUES (2, 'National ID', 'NI345678');

INSERT INTO ClientIdentification (id, type, value) VALUES (3, 'Passport', 'P901234');

INSERT INTO ClientIdentification (id, type, value) VALUES (4, 'Driver License', 'DL567890');

INSERT INTO ClientIdentification (id, type, value) VALUES (5, 'National ID', 'NI123456');

Insert into SCOTT.INVESTMENTPREFERENCE (CLIENTEMAIL,RISKTOLERANCE,INCOME,LENGTHOFINVESTMENT,PREFERENCE,SCORE) values ('john.doe@email.com','Aggresive','150000+','10-15','Growth','Medium');

Insert into SCOTT.INVESTMENTPREFERENCE (CLIENTEMAIL,RISKTOLERANCE,INCOME,LENGTHOFINVESTMENT,PREFERENCE,SCORE) values ('jane.smith@email.com','Conservative','0-20000','10-15','Income','Risky');

Insert into SCOTT.INVESTMENTPREFERENCE (CLIENTEMAIL,RISKTOLERANCE,INCOME,LENGTHOFINVESTMENT,PREFERENCE,SCORE) values ('alice.j@email.com','Aggresive','150000+','10-15','Growth','Safe');

Insert into SCOTT.INVESTMENTPREFERENCE (CLIENTEMAIL,RISKTOLERANCE,INCOME,LENGTHOFINVESTMENT,PREFERENCE,SCORE) values ('bob.w@email.com','Aggresive','150000+','10-15','Growth','Safe');

Insert into SCOTT.INVESTMENTPREFERENCE (CLIENTEMAIL,RISKTOLERANCE,INCOME,LENGTHOFINVESTMENT,PREFERENCE,SCORE) values ('emma.b@email.com',null,null,null,null,null);


INSERT INTO Instrument (instrumentId, description, externalId, externalIdType, maxQuantity, minQuantity, categoryId, mktCapDetails) VALUES

('AAPL', 'Apple Inc.', 'US0378331005', 'ISIN', 1000000, 1, 'TECH', 'Large Cap');

INSERT INTO Instrument (instrumentId, description, externalId, externalIdType, maxQuantity, minQuantity, categoryId, mktCapDetails) VALUES

('GOOGL', 'Alphabet Inc.', 'US02079K3059', 'ISIN', 500000, 1, 'TECH', 'Large Cap');

INSERT INTO Instrument (instrumentId, description, externalId, externalIdType, maxQuantity, minQuantity, categoryId, mktCapDetails) VALUES

('MSFT', 'Microsoft Corporation', 'US5949181045', 'ISIN', 750000, 1, 'TECH', 'Large Cap');

INSERT INTO Instrument (instrumentId, description, externalId, externalIdType, maxQuantity, minQuantity, categoryId, mktCapDetails) VALUES

('AMZN', 'Amazon.com Inc.', 'US0231351067', 'ISIN', 300000, 1, 'CONSUMER', 'Large Cap');

INSERT INTO Instrument (instrumentId, description, externalId, externalIdType, maxQuantity, minQuantity, categoryId, mktCapDetails) VALUES

('JPM', 'JPMorgan Chase Co.', 'US46625H1005', 'ISIN', 400000, 1, 'FINANCE', 'Large Cap');

INSERT INTO Instrument (instrumentId, description, externalId, externalIdType, maxQuantity, minQuantity, categoryId, mktCapDetails) VALUES

('SP500', 'SP 500', 'US46625H1000', 'ISIN', 0, 0, 'MARKET', null);


INSERT INTO Price (id, instrumentId, targetPrice, bidPrice, timestamp) VALUES

(1, 'AAPL', 152.00, 150.25, SYSTIMESTAMP);

INSERT INTO Price (id, instrumentId, targetPrice, bidPrice, timestamp) VALUES

(2, 'GOOGL', 2825.00, 2800.50, SYSTIMESTAMP);

INSERT INTO Price (id, instrumentId, targetPrice, bidPrice, timestamp) VALUES

(3, 'MSFT', 310.00, 305.75, SYSTIMESTAMP);

INSERT INTO Price (id, instrumentId, targetPrice, bidPrice, timestamp) VALUES

(4, 'AMZN', 3375.00, 3350.00, SYSTIMESTAMP);

INSERT INTO Price (id, instrumentId, targetPrice, bidPrice, timestamp) VALUES

(5, 'JPM', 168.00, 165.50, SYSTIMESTAMP);

INSERT INTO Order_Table (orderId, instrumentId, quantity, targetPrice, direction, clientId) VALUES

('ORD001', 'AAPL', 100, 151.00, 'BUY', 1);

INSERT INTO Order_Table (orderId, instrumentId, quantity, targetPrice, direction, clientId) VALUES

('ORD002', 'GOOGL', 50, 2820.00, 'BUY', 2);

INSERT INTO Order_Table (orderId, instrumentId, quantity, targetPrice, direction, clientId) VALUES

('ORD003', 'MSFT', 75, 308.00, 'SELL', 3);

INSERT INTO Order_Table (orderId, instrumentId, quantity, targetPrice, direction, clientId) VALUES

('ORD004', 'AMZN', 25, 3365.00, 'BUY', 4);

INSERT INTO Order_Table (orderId, instrumentId, quantity, targetPrice, direction, clientId) VALUES

('ORD005', 'JPM', 200, 166.00, 'SELL', 5);

INSERT INTO Trade (tradeId, orderId, cashValue, quantity, direction, instrumentId, clientId, executionPrice, executedTime) VALUES

('TRD001', 'ORD001', 15100.00, 100, 'BUY', 'AAPL', 1, 151.00, SYSTIMESTAMP);

INSERT INTO Trade (tradeId, orderId, cashValue, quantity, direction, instrumentId, clientId, executionPrice, executedTime) VALUES

('TRD002', 'ORD002', 141000.00, 50, 'BUY', 'GOOGL', 2, 2820.00, SYSTIMESTAMP);

INSERT INTO Trade (tradeId, orderId, cashValue, quantity, direction, instrumentId, clientId, executionPrice, executedTime) VALUES

('TRD003', 'ORD003', 23100.00, 75, 'SELL', 'MSFT', 3, 308.00, SYSTIMESTAMP);

INSERT INTO Trade (tradeId, orderId, cashValue, quantity, direction, instrumentId, clientId, executionPrice, executedTime) VALUES

('TRD004', 'ORD004', 84125.00, 25, 'BUY', 'AMZN', 4, 3365.00, SYSTIMESTAMP);

INSERT INTO Trade (tradeId, orderId, cashValue, quantity, direction, instrumentId, clientId, executionPrice, executedTime) VALUES

('TRD005', 'ORD005', 33200.00, 200, 'SELL', 'JPM', 5, 166.00, SYSTIMESTAMP);


Insert into SCOTT.INSTRUMENTHISTORYPRICE (INSTRUMENTID,CURRDAY,PREV1DAY,PREV2DAY,PREV3DAY,PREV4DAY) values ('GOOGL',2000,1980,1650,1500,1690);

Insert into SCOTT.INSTRUMENTHISTORYPRICE (INSTRUMENTID,CURRDAY,PREV1DAY,PREV2DAY,PREV3DAY,PREV4DAY) values ('SP500',20000,19800,16500,15000,16900);

Insert into SCOTT.INSTRUMENTHISTORYPRICE (INSTRUMENTID,CURRDAY,PREV1DAY,PREV2DAY,PREV3DAY,PREV4DAY) values ('JPM',100,90,89,85,80);

INSERT INTO Portfolio (portfolioId, instrumentId, clientId, quantity, cashValue) VALUES
(1, 'AAPL', 1, 50, 7550.00);

INSERT INTO Portfolio (portfolioId, instrumentId, clientId, quantity, cashValue) VALUES-- Traded by Client 1
(2, 'GOOGL', 2, 20, 56400.00);   -- Traded by Client 2

INSERT INTO Portfolio (portfolioId, instrumentId, clientId, quantity, cashValue) VALUES
(3, 'MSFT', 3, 30, 9240.00);     -- Traded by Client 3

INSERT INTO Portfolio (portfolioId, instrumentId, clientId, quantity, cashValue) VALUES
(4, 'AMZN', 4, 10, 33650.00);    -- Traded by Client 4

INSERT INTO Portfolio (portfolioId, instrumentId, clientId, quantity, cashValue) VALUES
(5, 'JPM', 5, 100, 16600.00);     -- Traded by Client 5

COMMIT;
