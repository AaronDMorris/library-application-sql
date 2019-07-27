-- Drop the Student table

DROP TABLE tblStudent CASCADE CONSTRAINTS;

--Create a new Student table

CREATE TABLE tblStudent 
(
  Student_ID NUMBER(10,0) NOT NULL,
  Student_Num VARCHAR2(10)NOT NULL UNIQUE,
  First_Name VARCHAR2(20) NOT NULL,
  Last_Name VARCHAR2(20) NOT NULL,
  Address_Line1 VARCHAR2(50) NOT NULL,
  City VARCHAR2(20) NOT NULL,
  Email VARCHAR2(30) NOT NULL
);

--Alter the student table to use the Student_ID as the Surrogate Primary Key
ALTER TABLE tblStudent
ADD CONSTRAINT pk_tblStudent PRIMARY KEY(Student_ID);

-- Drop the Resource table
DROP TABLE tblResource CASCADE CONSTRAINTS;

--Create a new Resource table
CREATE TABLE tblResource
(
  Resource_ID NUMBER(10,0)NOT NULL,
  Campus_ID NUMBER(10,0)NOT NULL,
  Resource_Type_ID NUMBER(10,0)NOT NULL,
  Isbn VARCHAR2(20) NOT NULL,
  Resource_Title VARCHAR2(100) NOT NULL,
  Maximum_Loan_Length NUMBER(3,0)
)
  --Create the resource table partitions
  PARTITION BY LIST (Campus_ID)
    (
        PARTITION P1 VALUES(1),
        PARTITION P2 VALUES(2),
        PARTITION P3 VALUES(3)
    )
;

--Alter the Resource table to use the Resource_ID as the Surrogate Primary Key
ALTER TABLE tblResource
ADD CONSTRAINT pk_tblResource PRIMARY KEY(Resource_ID);

-- Drop the Resource table
DROP TABLE tblResourceType CASCADE CONSTRAINTS;

--Create a new Book table
CREATE TABLE tblResourceType
(
  Resource_Type_ID NUMBER(10,0)NOT NULL,
  Resource_Type VARCHAR2(20) NOT NULL
);

--Alter the ResourceType table to use the Resource_Type_ID as the Surrogate Primary Key
ALTER TABLE tblResourceType
ADD CONSTRAINT pk_tblResourceType PRIMARY KEY(Resource_Type_ID);

-- Drop the Loan table
DROP TABLE tblLoan CASCADE CONSTRAINTS;

--Create a new Loan table
CREATE TABLE tblLoan
(
  Loan_ID NUMBER(10,0)NOT NULL,
  Loan_Record_Num VARCHAR2(10)NOT NULL UNIQUE,
  Student_ID NUMBER(10,0)NOT NULL,
  Date_Issued DATE NOT NULL
)
  --Create the loan table partitions
  PARTITION BY RANGE (Date_Issued) 
  (
    PARTITION P1 values less than (TO_DATE ('01-JAN-2016','dd-MON-yyyy')), 
    PARTITION P2 values less than (TO_DATE ('01-JAN-2017','dd-MON-yyyy')), 
    PARTITION P3 values less than (TO_DATE ('01-JAN-2018','dd-MON-yyyy')),
    PARTITION P4 values less than (TO_DATE ('01-JAN-2019','dd-MON-yyyy')),
    PARTITION P5 values less than (TO_DATE ('01-JAN-2020','dd-MON-yyyy'))
  )
;

--Alter the Loan table to use the Loan_ID as the Surrogate Primary Key
ALTER TABLE tblLoan
ADD CONSTRAINT pk_tblLoan PRIMARY KEY(Loan_ID);

-- Drop the LoanItem table
DROP TABLE tblLoanItem CASCADE CONSTRAINTS;

--Create a new LoanItem table
CREATE TABLE tblLoanItem
(
  Loan_ID NUMBER(10,0)NOT NULL,
  Resource_ID NUMBER(10,0)NOT NULL
);

--Alter the LoanItem table to use the Loan_ID and Resource_ID as a Composite Primary Key
ALTER TABLE tblLoanItem
ADD CONSTRAINT pk_tblLoanItem PRIMARY KEY(Loan_ID, Resource_ID);

-- Drop the ReturnedBookItem table
DROP TABLE tblReturnItem CASCADE CONSTRAINTS;

--Create a new ReturnedBookItem table
CREATE TABLE tblReturnItem
(
  Loan_ID NUMBER(10,0)NOT NULL,
  Resource_ID NUMBER(10,0)NOT NULL,
  Date_Returned DATE NOT NULL
);

--Alter the ReturnedBookItem table to use the Loan_ID and Resource_ID as a Composite Primary Key
ALTER TABLE tblReturnItem
ADD CONSTRAINT pk_tblReturnItem PRIMARY KEY(Loan_ID, Resource_ID);

-- Drop the Fine table
DROP TABLE tblFine CASCADE CONSTRAINTS;

--Create a new Fine table
CREATE TABLE tblFine
(
  Fine_ID NUMBER(10,0)NOT NULL,
  Student_ID NUMBER(10,0)NOT NULL,
  Loan_ID NUMBER(10,0)NOT NULL,
  Resource_ID NUMBER(10,0)NOT NULL,
  Amount_Paid NUMBER(5,2)NOT NULL,
  Date_Paid DATE NOT NULL
);

-- Drop the Campus table
DROP TABLE tblCampus CASCADE CONSTRAINTS;

--Create a new Campus table
CREATE TABLE tblCampus
(
  Campus_ID NUMBER(10,0)NOT NULL,
  Campus_Name VARCHAR2(100)NOT NULL,
  Address_Line1 VARCHAR2(50) NOT NULL,
  City VARCHAR2(20) NOT NULL
);

-- Drop the Users table
DROP TABLE tblUsers CASCADE CONSTRAINTS;

--Create a new Users table
CREATE TABLE tblUsers
(
  User_ID NUMBER(10,0)NOT NULL,
  Username VARCHAR2(30)NOT NULL,
  Password_Hash VARCHAR2(60) NOT NULL,
  Has_Level1_Access NUMBER(1) NOT NULL,
  Has_Level2_Access NUMBER(1) NOT NULL,
  Has_Level3_Access NUMBER(1) NOT NULL
);

--Alter the Fine table to use the Fine_ID as a Surrogate Primary Key
ALTER TABLE tblFine
ADD CONSTRAINT pk_tblFine PRIMARY KEY(Fine_ID);

--Alter the Users table to use the User_ID as a Surrogate Primary Key
ALTER TABLE tblUsers
ADD CONSTRAINT pk_tblUsers PRIMARY KEY(User_ID);

--Alter the Campus table to use the Campus_ID as a Surrogate Primary Key
ALTER TABLE tblCampus
ADD CONSTRAINT pk_tblCampus PRIMARY KEY(Campus_ID);

--Alter the Loan table to reference the Student_ID as a Foreign Key constraint, for the Student_ID in the Student table
ALTER TABLE tblLoan
ADD CONSTRAINT fk_tblStudent_tblLoan
    FOREIGN KEY (Student_ID)
    REFERENCES tblStudent(Student_ID);

--Alter the Fine table to reference the Student_ID as a Foreign Key constraint, for the Student_ID in the Student table
ALTER TABLE tblFine
ADD CONSTRAINT fk_tblStudent_tblFine
    FOREIGN KEY (Student_ID)
    REFERENCES tblStudent(Student_ID);

--Alter the Fine table to reference the Loan_ID as a Foreign Key constraint, for the Loan_ID in the Loan table
ALTER TABLE tblFine
ADD CONSTRAINT fk_tblLoan_tblFine
    FOREIGN KEY (Loan_ID)
    REFERENCES tblLoan(Loan_ID);

--Alter the Fine table to reference the Book_ID as a Foreign Key constraint, for the Book_ID in the Book table
ALTER TABLE tblFine
ADD CONSTRAINT fk_tblResource_tblFine
    FOREIGN KEY (Resource_ID)
    REFERENCES tblResource(Resource_ID);

--Alter the LoanItem table to reference the Loan_ID as a Foreign Key constraint, for the Loan_ID in the Loan table
ALTER TABLE tblLoanItem
ADD CONSTRAINT fk_tblLoan_tblLoanBookItem
    FOREIGN KEY (Loan_ID)
    REFERENCES tblLoan(Loan_ID);

--Alter the LoanItem table to reference the Resource_ID as a Foreign Key constraint, for the Resource_ID in the Resource table
ALTER TABLE tblLoanItem
ADD CONSTRAINT fk_tblResource_tblLoanItem
    FOREIGN KEY (Resource_ID)
    REFERENCES tblResource(Resource_ID);

--Alter the ReturnItem table to reference the Loan_ID as a Foreign Key constraint, for the Loan_ID in the Loan table
ALTER TABLE tblReturnItem
ADD CONSTRAINT fk_tblLoan_tblReturnItem
    FOREIGN KEY (Loan_ID)
    REFERENCES tblLoan(Loan_ID);

--Alter the ReturnItem table to reference the Resource_ID as a Foreign Key constraint, for the Resource_ID in the Resource table
ALTER TABLE tblReturnItem
ADD CONSTRAINT fk_tblResource_tblReturnItem
    FOREIGN KEY (Resource_ID)
    REFERENCES tblResource(Resource_ID);
	
--Alter the Resource table to reference the Campus_ID as a Foreign Key constraint, for the Campus_ID in the Campus table
ALTER TABLE tblResource
ADD CONSTRAINT fk_tblCampus_tblResource
    FOREIGN KEY (Campus_ID)
    REFERENCES tblCampus(Campus_ID);

--Alter the Resource table to reference the Resource_Type_ID as a Foreign Key constraint, for the Resource_Type_ID in the ResourceType table
ALTER TABLE tblResource
ADD CONSTRAINT fk_tblResourceType_tblResource
    FOREIGN KEY (Resource_Type_ID)
    REFERENCES tblResourceType(Resource_Type_ID);	    	

--Drop the Sequence used for the Surrogate Student_ID Primary Key
DROP SEQUENCE tblStudent_Sequence;

--Create the Sequence used for the Surrogate Student_ID Primary Key
CREATE SEQUENCE tblStudent_Sequence;

--Drop the Sequence used for the Surrogate Resource_ID Primary Key
DROP SEQUENCE tblResource_Sequence;

--Create the Sequence used for the Surrogate Resource_ID Primary Key
CREATE SEQUENCE tblResource_Sequence;

--Drop the Sequence used for the Surrogate Resource_Type_ID Primary Key
DROP SEQUENCE tblResourceType_Sequence;

--Create the Sequence used for the Surrogate Resource_Type_ID Primary Key
CREATE SEQUENCE tblResourceType_Sequence;

--Drop the Sequence used for the Surrogate Fine_ID Primary Key
DROP SEQUENCE tblFine_Sequence;

--Create the Sequence used for the Surrogate Fine_ID Primary Key
CREATE SEQUENCE tblFine_Sequence;

--Drop the Sequence used for the Surrogate Loan_ID Primary Key
DROP SEQUENCE tblLoan_Sequence;

--Create the Sequence used for the Surrogate Loan_ID Primary Key
CREATE SEQUENCE tblLoan_Sequence;

--Drop the Sequence used for the Surrogate Campus_ID Primary Key
DROP SEQUENCE tblCampus_Sequence;

--Create the Sequence used for the Surrogate Campus_ID Primary Key
CREATE SEQUENCE tblCampus_Sequence;

--Drop the Sequence used for the Surrogate User_ID Primary Key
DROP SEQUENCE tblUsers_Sequence;

--Create the Sequence used for the Surrogate User_ID Primary Key
CREATE SEQUENCE tblUsers_Sequence;


--If the trigger for the User_ID does not exist, create it and insert the Sequence's next value into User_ID, otherwise, simply insert the Sequence's next value into User_ID
create or replace trigger "TBLUSERS_ON_INSERT_T1"
BEFORE
insert on "TBLUSERS"
for each row
begin
  SELECT tblUsers_Sequence.nextval
  INTO :new.User_ID
  FROM dual;
end;
/

--If the trigger for the Student_ID does not exist, create it and insert the Sequence's next value into Student_ID, otherwise, simply insert the Sequence's next value into Student_ID
create or replace trigger "TBLSTUDENT_ON_INSERT_T1"
BEFORE
insert on "TBLSTUDENT"
for each row
begin
  SELECT tblStudent_Sequence.nextval
  INTO :new.Student_ID
  FROM dual;
end;
/
    
    
--If the trigger for the Loan_ID does not exist, create it and insert the Sequence's next value into Loan_ID, otherwise, simply insert the Sequence's next value into Loan_ID
create or replace trigger "TBLLOAN_ON_INSERT_T1"
BEFORE
insert on "TBLLOAN"
for each row
begin
  SELECT tblLoan_Sequence.nextval
  INTO :new.Loan_ID
  FROM dual;
end;
/

--If the trigger for the Fine_ID does not exist, create it and insert the Sequence's next value into Fine_ID, otherwise, simply insert the Sequence's next value into Fine_ID
create or replace trigger "TBLFINE_ON_INSERT_T1"
BEFORE
insert on "TBLFINE"
for each row
begin
  SELECT tblFine_sequence.nextval
  INTO :new.Fine_ID
  FROM dual;
end;
/
    
--If the trigger for the Resource_ID does not exist, create it and insert the Sequence's next value into Resource_ID, otherwise, simply insert the Sequence's next value into Resource_ID
create or replace trigger "TBLRESOURCE_ON_INSERT_T1"
BEFORE
insert on "TBLRESOURCE"
for each row
begin
  SELECT tblResource_sequence.nextval
  INTO :new.Resource_ID
  FROM dual;
end;
/

--If the trigger for the Resource_Type_ID does not exist, create it and insert the Sequence's next value into Resource_Type_ID, otherwise, simply insert the Sequence's next value into Resource_Type_ID
create or replace trigger "TBLRESOURCETYPE_ON_INSERT_T1"
BEFORE
insert on "TBLRESOURCETYPE"
for each row
begin
  SELECT tblResourceType_sequence.nextval
  INTO :new.Resource_Type_ID
  FROM dual;
end;
/

--If the trigger for the Campus_ID does not exist, create it and insert the Sequence's next value into Campus_ID, otherwise, simply insert the Sequence's next value into Campus_ID
create or replace trigger "TBLCAMPUS_ON_INSERT_T1"
BEFORE
insert on "TBLCAMPUS"
for each row
begin
  SELECT tblCampus_Sequence.nextval
  INTO :new.Campus_ID
  FROM dual;
end;
/

    
    
