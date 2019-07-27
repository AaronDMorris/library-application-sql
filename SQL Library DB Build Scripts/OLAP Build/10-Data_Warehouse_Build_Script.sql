--Drop the DimDate table
DROP TABLE DimDate CASCADE CONSTRAINTS;

CREATE TABLE DimDate AS
SELECT CurrDate AS Day_Key,
1 AS Day_Time_Span,
CurrDate AS Day_End_Date,
TO_CHAR(CurrDate,'Day') AS Week_Day_Full,
TO_CHAR(CurrDate,'DY') AS Week_Day_Short,
TO_NUMBER(TRIM(leading '0' FROM TO_CHAR(CurrDate,'D'))) AS Day_Num_of_Week,
TO_NUMBER(TRIM(leading '0' FROM TO_CHAR(CurrDate,'DD'))) AS Day_Num_of_Month,
TO_NUMBER(TRIM(leading '0' FROM TO_CHAR(CurrDate,'DDD'))) AS Day_Num_of_Year,
UPPER(TO_CHAR(CurrDate,'Mon') || '-' || TO_CHAR(CurrDate,'YYYY')) AS Month_ID,
MAX(TO_NUMBER(TO_CHAR(CurrDate, 'DD'))) OVER (PARTITION BY TO_CHAR(CurrDate,'Mon')) AS Month_Time_Span,
MAX(CurrDate) OVER (PARTITION BY TO_CHAR(CurrDate,'Mon')) as Month_End_Date,
TO_CHAR(CurrDate,'Mon') || ' ' || TO_CHAR(CurrDate,'YYYY') AS Month_Short_Desc,
RTRIM(TO_CHAR(CurrDate,'Month')) || ' ' || TO_CHAR(CurrDate,'YYYY') AS Month_Long_Desc,
TO_CHAR(CurrDate,'Mon') AS Month_Short,
TO_CHAR(CurrDate,'Month') AS Month_Long,
TO_NUMBER(TRIM(leading '0'FROM TO_CHAR(CurrDate,'MM'))) AS Month_Num_of_Year,
'Q' || UPPER(TO_CHAR(CurrDate,'Q') || '-' || TO_CHAR(CurrDate,'YYYY')) AS Quarter_ID,
COUNT(*) OVER (PARTITION BY TO_CHAR(CurrDate,'Q')) AS Quarter_Time_Span,
MAX(CurrDate) OVER (PARTITION BY TO_CHAR(CurrDate,'Q')) AS Quarter_End_Date,
TO_NUMBER(TO_CHAR(CurrDate,'Q')) AS Quarter_Num_of_Year,
TO_CHAR(CurrDate,'YYYY') AS Year_ID,
COUNT(*) OVER (PARTITION BY TO_CHAR(CurrDate,'YYYY')) AS Year_Time_Span,
MAX(CurrDate) OVER (PARTITION BY TO_CHAR(CurrDate,'YYYY')) Year_End_Date
FROM
(SELECT level n,
-- The dates will start the day after the following date
TO_DATE('31/12/2015','DD/MM/YYYY') + NUMTODSINTERVAL(level,'day') CurrDate
FROM dual
-- The following parameter determines how many days to add to the Dimension
CONNECT BY level <= 1825)
ORDER BY CurrDate;


--Alter the date dimension to use the Day_Key as the Surrogate Primary Key
ALTER TABLE DimDate
ADD CONSTRAINT pk_DimDate PRIMARY KEY(Day_Key);

-- Drop Tables
DROP TABLE FactLoan CASCADE CONSTRAINTS;
DROP TABLE DimResource CASCADE CONSTRAINTS;


--Create a new Resource dimension
CREATE TABLE DimResource
(
  Resource_Key NUMBER(10,0)NOT NULL,
  Isbn VARCHAR2(20) NOT NULL,
  Campus_Name VARCHAR2(100)NOT NULL,
  Address_Line1 VARCHAR2(50) NOT NULL,
  City VARCHAR2(20) NOT NULL,
  Resource_Title VARCHAR2(100) NOT NULL,
  Resource_Type VARCHAR2(20) NOT NULL,
  Maximum_Loan_Length NUMBER(3,0)
);

--Alter the Resource table to use the Resource_ID as the Surrogate Primary Key
ALTER TABLE DimResource
ADD CONSTRAINT pk_DimResource PRIMARY KEY(Resource_Key);

--Create a new Loan table

CREATE TABLE FactLoan
(
  Resource_Key NUMBER(10,0) NOT NULL,
  Day_Key DATE NOT NULL,
  Retained_Duration NUMBER(3,0),
  Total_Fines_Paid NUMBER(5,2)
);

--Alter the Loan Dimension to use the  Resource_Key, Day_Key as the Composite Primary Key
ALTER TABLE FactLoan
ADD CONSTRAINT pk_FactLoan PRIMARY KEY(Resource_Key, Day_Key);

-- Foreign Keys


ALTER TABLE FactLoan
    ADD CONSTRAINT fk_DimDate_FactLoan
    FOREIGN KEY(Day_Key) 
    REFERENCES DimDate (Day_Key);

ALTER TABLE FactLoan
    ADD CONSTRAINT fk_DimResource_FactLoan
    FOREIGN KEY(Resource_Key) 
    REFERENCES DimResource (Resource_Key);


--Creation of Day_Key index on the FactLoan
CREATE INDEX "S1408926"."FACTLOAN_DAYKEY_IDX" ON "S1408926"."FACTLOAN" ("DAY_KEY") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "APEX_120307229767015963" ;    


  