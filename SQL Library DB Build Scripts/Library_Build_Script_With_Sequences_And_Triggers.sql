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

-- Drop the Book table
DROP TABLE tblBook CASCADE CONSTRAINTS;

--Create a new Book table
CREATE TABLE tblBook
(
  Book_ID NUMBER(10,0)NOT NULL,
  Isbn VARCHAR2(20) NOT NULL UNIQUE,
  Book_Name VARCHAR2(100)NOT NULL
);

--Alter the Book table to use the Book_ID as the Surrogate Primary Key
ALTER TABLE tblBook
ADD CONSTRAINT pk_tblBook PRIMARY KEY(Book_ID);

-- Drop the Loan table
DROP TABLE tblLoan CASCADE CONSTRAINTS;

--Create a new Loan table
CREATE TABLE tblLoan
(
  Loan_ID NUMBER(10,0)NOT NULL,
  Loan_Record_Num VARCHAR2(10)NOT NULL UNIQUE,
  Student_ID NUMBER(10,0)NOT NULL,
  Date_Issued DATE NOT NULL
);

--Alter the Loan table to use the Loan_ID as the Surrogate Primary Key
ALTER TABLE tblLoan
ADD CONSTRAINT pk_tblLoan PRIMARY KEY(Loan_ID);

-- Drop the LoanBookItem table
DROP TABLE tblLoanBookItem CASCADE CONSTRAINTS;

--Create a new LoanBookItem table
CREATE TABLE tblLoanBookItem
(
  Loan_ID NUMBER(10,0)NOT NULL,
  Book_ID NUMBER(10,0)NOT NULL
);

--Alter the LoanBookItem table to use the Loan_ID and Book_ID as a Composite Primary Key
ALTER TABLE tblLoanBookItem
ADD CONSTRAINT pk_tblLoanBookItem PRIMARY KEY(Loan_ID, Book_ID);

-- Drop the ReturnedBookItem table
DROP TABLE tblReturnedBookItem CASCADE CONSTRAINTS;

--Create a new ReturnedBookItem table
CREATE TABLE tblReturnedBookItem
(
  Loan_ID NUMBER(10,0)NOT NULL,
  Book_ID NUMBER(10,0)NOT NULL,
  Date_Returned DATE NOT NULL
);

--Alter the ReturnedBookItem table to use the Loan_ID and Book_ID as a Composite Primary Key
ALTER TABLE tblReturnedBookItem
ADD CONSTRAINT pk_tblReturnedBookItem PRIMARY KEY(Loan_ID, Book_ID);

-- Drop the Fine table
DROP TABLE tblFine CASCADE CONSTRAINTS;

--Create a new Fine table
CREATE TABLE tblFine
(
  Fine_ID NUMBER(10,0)NOT NULL,
  Student_ID NUMBER(10,0)NOT NULL,
  Loan_ID NUMBER(10,0)NOT NULL,
  Book_ID NUMBER(10,0)NOT NULL,
  Amount_Paid NUMBER(5,2)NOT NULL,
  Date_Paid DATE NOT NULL
);

--Alter the Fine table to use the Fine_ID as a Surrogate Primary Key
ALTER TABLE tblFine
ADD CONSTRAINT pk_tblFine PRIMARY KEY(Fine_ID);

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
ADD CONSTRAINT fk_tblBook_tblFine
    FOREIGN KEY (Book_ID)
    REFERENCES tblBook(Book_ID);

--Alter the LoanBookItem table to reference the Loan_ID as a Foreign Key constraint, for the Loan_ID in the Loan table
ALTER TABLE tblLoanBookItem
ADD CONSTRAINT fk_tblLoan_tblLoanBookItem
    FOREIGN KEY (Loan_ID)
    REFERENCES tblLoan(Loan_ID);

--Alter the LoanBookItem table to reference the Book_ID as a Foreign Key constraint, for the Book_ID in the Book table
ALTER TABLE tblLoanBookItem
ADD CONSTRAINT fk_tblBook_tblLoanBookItem
    FOREIGN KEY (Book_ID)
    REFERENCES tblBook(Book_ID);

--Alter the ReturnedBookItem table to reference the Loan_ID as a Foreign Key constraint, for the Loan_ID in the Loan table
ALTER TABLE tblReturnedBookItem
ADD CONSTRAINT fk_tblLoan_tblReturnedBookItem
    FOREIGN KEY (Loan_ID)
    REFERENCES tblLoan(Loan_ID);

--Alter the ReturnedBookItem table to reference the Book_ID as a Foreign Key constraint, for the Book_ID in the Book table
ALTER TABLE tblReturnedBookItem
ADD CONSTRAINT fk_tblBook_tblReturnedBookItem
    FOREIGN KEY (Book_ID)
    REFERENCES tblBook(Book_ID);

--Drop the Sequence used for the Surrogate Student_ID Primary Key
DROP SEQUENCE tblStudent_Sequence;

--Create the Sequence used for the Surrogate Student_ID Primary Key
CREATE SEQUENCE tblStudent_Sequence;

--Drop the Sequence used for the Surrogate Book_ID Primary Key
DROP SEQUENCE tblBook_Sequence;

--Create the Sequence used for the Surrogate Book_ID Primary Key
CREATE SEQUENCE tblBook_Sequence;

--Drop the Sequence used for the Surrogate Fine_ID Primary Key
DROP SEQUENCE tblFine_Sequence;

--Create the Sequence used for the Surrogate Fine_ID Primary Key
CREATE SEQUENCE tblFine_Sequence;

--Drop the Sequence used for the Surrogate Loan_ID Primary Key
DROP SEQUENCE tblLoan_Sequence;

--Create the Sequence used for the Surrogate Loan_ID Primary Key
CREATE SEQUENCE tblLoan_Sequence;


--If the trigger for the Student_ID does not exist, create it to insert the Sequence's next value into Student_ID on insert
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
    
    
--If the trigger for the Loan_ID does not exist, create it to insert the Sequence's next value into Loan_ID on insert
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

--If the trigger for the Fine_ID does not exist, create it to insert the Sequence's next value into Fine_ID on insert
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
    
--If the trigger for the Book_ID does not exist, create it to insert the Sequence's next value into Book_ID on insert
create or replace trigger "TBLBOOK_ON_INSERT_T1"
BEFORE
insert on "TBLBOOK"
for each row
begin
  SELECT tblBook_sequence.nextval
  INTO :new.Book_ID
  FROM dual;
end;
/

    
    
