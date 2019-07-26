/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.bson.Document;

/**
 *
 * @author arron
 */
public class DAO_LoanRecord {
    
    /**
     *  This method returns a book document object, that is specified by the ISBN parameter.
     * 
     * @param isbn The unique identifier of the book object
     * @return A book document object associated with the ISBN parameter
     */

    public Document getBookByIsbn(String isbn) {
        String query = "SELECT * FROM tblBook WHERE ISBN = '" + isbn + "'";
        
        Statement stmt = null;
        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            Document book = new Document();
            while (rs.next()) {
                book.append("Book_ID", rs.getString("Book_ID"));
                book.append("Isbn", rs.getString("Isbn"));
                book.append("Book_Name", rs.getString("Book_Name"));
            }
            
            stmt.close();
            return book;
        } catch (SQLException e ) {
            e.printStackTrace();
        } 
        
        return null;
    }
    
     /**
     *  This method returns a book document object, that is specified by the id parameter.
     * 
     * @param id The unique identifier of the book object
     * @return A book document object associated with the id parameter
     */
    public Document getBookById(String id) {
        String query = "SELECT * FROM tblBook WHERE Book_ID = '" + id + "'";
        
        Statement stmt = null;
        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            Document book = new Document();
            while (rs.next()) {
                book.append("Book_ID", rs.getString("Book_ID"));
                book.append("Isbn", rs.getString("Isbn"));
                book.append("Book_Name", rs.getString("Book_Name"));
            }
            
            stmt.close();
            return book;
        } catch (SQLException e ) {
            e.printStackTrace();
        } 
        
        return null;
    }
    
    /**
     *  This method accepts a parameter of a student number, and returns the student document object associated with said student number.
     * 
     * @param studentNumber A unique student number 
     * @return A student document object related to the student number parameter
     */
    public Document getStudentByStudentNum(String studentNumber) {
        
        String selectStudent = "SELECT * FROM tblStudent WHERE Student_Num = '" + studentNumber + "'";
        
        Statement stmt = null;
        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(selectStudent);
            
            Document student = new Document();
            while (rs.next()) {
                student.append("Student_ID", rs.getString("Student_ID"));
                student.append("Student_Num", rs.getString("Student_Num"));
                student.append("First_Name", rs.getString("First_Name"));
                student.append("Last_Name", rs.getString("Last_Name"));
                student.append("Address_Line1", rs.getString("Address_Line1"));
                student.append("City", rs.getString("City"));
                student.append("Email", rs.getString("Email"));
            }
            
            stmt.close();
            return student;
        } catch (SQLException e ) {
            e.printStackTrace();
        } 
        
        return null;
    }
    
    /**
     * This method accepts a parameter of a student ID  , and returns the student document object associated with said student ID.
     * @param studentId The surrogate key for any given student, associated with the required student object to be returned
     * @return A student document object related to the student id parameter
     */
    public Document getStudentByStudentId(String studentId) {
        
        String selectStudent = "SELECT * FROM tblStudent WHERE Student_ID = '" + studentId + "'";
        
        Statement stmt = null;
        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(selectStudent);
            
            Document student = new Document();
            while (rs.next()) {
                student.append("Student_ID", rs.getString("Student_ID"));
                student.append("Student_Num", rs.getString("Student_Num"));
                student.append("First_Name", rs.getString("First_Name"));
                student.append("Last_Name", rs.getString("Last_Name"));
                student.append("Address_Line1", rs.getString("Address_Line1"));
                student.append("City", rs.getString("City"));
                student.append("Email", rs.getString("Email"));
            }
            
            stmt.close();
            return student;
        } catch (SQLException e ) {
            e.printStackTrace();
        } 
        
        return null;
    }
    
    /**
     *  Returns a Loan Document object, that is associated with the loan record number parameter.
     * @param loanRecordNumber The unique loan record number
     * @return A loan document object
     */
    public Document getLoanByRecordNum(String loanRecordNumber) {
        
        String selectLoan = "SELECT * FROM tblLoan WHERE Loan_Record_Num = '" + loanRecordNumber + "'";
        
        Statement stmt = null;
        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(selectLoan);
            
            Document loan = new Document();
            while (rs.next()) {
                loan.append("Loan_ID", rs.getString("Loan_ID"));
                loan.append("Loan_Record_Num", rs.getString("Loan_Record_Num"));
                loan.append("Student_ID", rs.getString("Student_ID"));
                loan.append("Date_Issued", rs.getString("Date_Issued"));
            }
            
            stmt.close();
            return loan;
        } catch (SQLException e ) {
            e.printStackTrace();
        } 
        
        return null;
    }
    
    /**
     *  This method creates a new loan , within the Loan table of Oracle.
     * 
     * @param loan The loan that is wished to be created
     */
    public void createLoan(Document loan) {
        
        Document student = getStudentByStudentNum((String) loan.get("Student_Num"));
        
        String insertLoanTable = "INSERT INTO tblLoan"
				+ "(Loan_Record_Num, Student_ID, Date_Issued) " + "VALUES ("
				+ " '" + loan.get("Loan_Record_Num") + "', " 
                                + " '" + student.get("Student_ID") + "', " 
                                + " sysdate " + ")";
                
        Statement stmt = null;
        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            
            System.out.println(insertLoanTable);

            // execute insert SQL stetement
            stmt.executeUpdate(insertLoanTable);
            
            Document loanCreated = getLoanByRecordNum((String) loan.get("Loan_Record_Num"));
            ArrayList loanBookList = (ArrayList) loan.get("Loan_Books");
    
            Iterator itr = loanBookList.iterator();
            Document book;
            while(itr.hasNext()){
                book = (Document)itr.next();
                String insertInvoiceLineTable = "INSERT INTO tblLoanBookItem"
				+ "(Loan_ID, Book_ID) " + "VALUES ("
				+ " '" + loanCreated.get("Loan_ID") + "', " 
                                + " '" + book.get("Book_ID") + "'" + ")";
                
                stmt.executeUpdate(insertInvoiceLineTable);
                                
            }
            
            stmt.close();
            
        } catch (SQLException e ) {
            e.printStackTrace();
        } 
        
    }
    
    
    
    
    /**
     * This method returns the entire loan history of any given student, for the time-frame specified from the date parameters parsed.
     * 
     * @param studentNum The student number associated with the loan history requested
     * @param fromDate The date that will determine the day at which the returned history will start at.
     * @param toDate The date that will determine the day at which the returned history will end at.
     * @return The loan history, as a FindIterable array of Documents.
     */
    public ArrayList<Document> getLoanHistory(String studentNum,Date fromDate, Date toDate) {
        
        Document student = getStudentByStudentNum(studentNum);
        
        
        String query = "SELECT tblLoan.Loan_Record_Num, tblStudent.Student_Num,\n" +
                       "First_Name, Last_Name, Address_Line1, City, Email,\n" +
                       "Date_Issued, tblLoanBookItem.Book_ID, Book_Name\n" +
                       "from tblStudent\n" +
                       "inner join tblLoan\n" +
                       "on tblStudent.Student_ID = tblLoan.Student_ID\n" +
                       "inner join tblLoanBookItem\n" +
                       "on tblLoan.Loan_ID = tblLoanBookItem.Loan_ID\n" +
                       "inner join tblBook\n" +
                       "on tblLoanBookItem.Book_ID = tblBook.Book_ID\n" +
                       "WHERE tblLoan.Student_ID = '" + student.get("Student_ID") + "'\n" +
                       "AND Date_Issued BETWEEN TO_DATE('" + new SimpleDateFormat("MM/dd/yyyy").format(fromDate) + "', 'mm/dd/yyyy')\n" +
                       "AND TO_DATE('" + new SimpleDateFormat("MM/dd/yyyy").format(toDate) + "', 'mm/dd/yyyy')";
        
        
        ArrayList loan_history_list = new ArrayList();
        
        Statement stmt = null;
        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
           
            Document loan = new Document();
            String current_loan_no = "";
            String loan_no_in_rs_row;
            while (rs.next()) {
                Document bookItem = new Document();
                bookItem.append("Book_ID", rs.getString("Book_ID"));
                bookItem.append("Book_Name", rs.getString("Book_Name"));
                
                loan_no_in_rs_row = rs.getString("Loan_Record_Num");
                
                ArrayList<Document> loan_items;
                if (loan_no_in_rs_row.equalsIgnoreCase(current_loan_no) == false) {
                    loan = new Document();
                    
                    loan.append("Loan_Record_Num", loan_no_in_rs_row);
                    loan.append("Student_Num", rs.getString("Student_Num"));
                    loan.append("First_Name", rs.getString("First_Name"));
                    loan.append("Last_Name", rs.getString("Last_Name"));
                    loan.append("Address_Line1", rs.getString("Address_Line1"));
                    loan.append("City", rs.getString("City"));
                    loan.append("Email", rs.getString("Email"));
                    loan.append("Date_Issued", rs.getString("Date_Issued"));
                    
                    loan_items = new ArrayList();
                    loan_items.add(bookItem);
                    loan.append("Loan_Books", loan_items);
                    
                    current_loan_no = loan_no_in_rs_row;
                    loan_history_list.add(loan);
                } else {
                    ((ArrayList)loan.get("Loan_Books")).add(bookItem);
                }
  
            }
            
            stmt.close();
            
             return loan_history_list;
            
        } catch (SQLException e ) {
            e.printStackTrace();
        } 
        
        return null;
        
    }
    
    /**
     * This method returns all of the books returned of any given student, for the time-frame specified from the date parameters parsed.
     * 
     * @param studentNum The unique student number given to a student
     * @param fromDate The date that will determine the day at which the returned history will start at.
     * @param toDate The date that will determine the day at which the returned history will end at.
     * @return The loan history, as a FindIterable array of Documents.
     */
    public ArrayList<Document> getStudentReturnedHistory(String studentNum,Date fromDate, Date toDate) {
        
        Document student = getStudentByStudentNum(studentNum);
        
        String query = "SELECT tblLoan.Loan_Record_Num, tblReturnedBookItem.Book_ID, Book_Name\n" +
                       "from tblStudent\n" +
                       "inner join tblLoan\n" +
                       "on tblStudent.Student_ID = tblLoan.Student_ID\n" +
                       "inner join tblReturnedBookItem\n" +
                       "on tblLoan.Loan_ID = tblReturnedBookItem.Loan_ID\n" +
                       "inner join tblBook\n" +
                       "on tblReturnedBookItem.Book_ID = tblBook.Book_ID\n" +
                       "WHERE tblLoan.Student_ID = '" + student.get("Student_ID") + "'\n" +
                       "AND Date_Issued BETWEEN TO_DATE('" + new SimpleDateFormat("MM/dd/yyyy").format(fromDate) + "', 'mm/dd/yyyy')\n" +
                       "AND TO_DATE('" + new SimpleDateFormat("MM/dd/yyyy").format(toDate) + "', 'mm/dd/yyyy')";
        
        ArrayList loan_history_list = new ArrayList();
        
        Statement stmt = null;
        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            Document loan = new Document();
            String current_loan_no = "";
            String loan_no_in_rs_row;
            while (rs.next()) {
                
                Document bookItem = new Document();
                String loanRecordNum = rs.getString("Loan_Record_Num");
                bookItem.append("Book_ID", rs.getString("Book_ID"));
                bookItem.append("Book_Name", rs.getString("Book_Name"));
                
                loan_no_in_rs_row = loanRecordNum;
                
                ArrayList<Document> loan_items;
                if (loan_no_in_rs_row.equalsIgnoreCase(current_loan_no) == false) {
                    loan = new Document();
                    
                    loan_items = new ArrayList();
                    loan.append("Loan_Record_Num", loanRecordNum);
                    loan_items.add(bookItem);
                    loan.append("Returned_Books", loan_items);
                    
                    current_loan_no = loan_no_in_rs_row;
                    loan_history_list.add(loan);
                } else {
                    
                    ((ArrayList)loan.get("Returned_Books")).add(bookItem);
                }
                
            }
            
            stmt.close();
            
            return loan_history_list;
            
        } catch (SQLException e ) {
            e.printStackTrace();
        } 
        
        ArrayList<Document> emptyReturnedBooks = new ArrayList();
        Document emptyBookItem = new Document();
        emptyBookItem.append("Book_ID", "");
        emptyBookItem.append("Book_Name", "");
        emptyBookItem.append("Returned_Books", emptyReturnedBooks);
        
        return emptyReturnedBooks;
        
    }
    
    /**
     * This method returns all of the books returned of any given student, for the time-frame specified from the date parameters parsed.
     * 
     * @param loanRecordNumber The Loan Record number associated with the returned books requested
     * @param fromDate The date that will determine the day at which the returned history will start at.
     * @param toDate The date that will determine the day at which the returned history will end at.
     * @return The loan history, as a FindIterable array of Documents.
     */
    public ArrayList<Document> getReturnedHistoryByLoanRecNum(String loanRecordNumber, Date fromDate, Date toDate) {
        
        Document loanRecord = getLoanByRecordNum(loanRecordNumber);
        
        String query = "SELECT tblLoan.Loan_Record_Num, tblReturnedBookItem.Book_ID, Book_Name\n" +
                       "from tblStudent\n" +
                       "inner join tblLoan\n" +
                       "on tblStudent.Student_ID = tblLoan.Student_ID\n" +
                       "inner join tblReturnedBookItem\n" +
                       "on tblLoan.Loan_ID = tblReturnedBookItem.Loan_ID\n" +
                       "inner join tblBook\n" +
                       "on tblReturnedBookItem.Book_ID = tblBook.Book_ID\n" +
                       "WHERE tblReturnedBookItem.Loan_ID = '" + loanRecord.get("Loan_ID") + "'\n" +
                       "AND Date_Issued BETWEEN TO_DATE('" + new SimpleDateFormat("MM/dd/yyyy").format(fromDate) + "', 'mm/dd/yyyy')\n" +
                       "AND TO_DATE('" + new SimpleDateFormat("MM/dd/yyyy").format(toDate) + "', 'mm/dd/yyyy')";
        
        ArrayList loan_history_list = new ArrayList();
        
        Statement stmt = null;
        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            Document loan = new Document();
            String current_loan_no = "";
            String loan_no_in_rs_row;
            while (rs.next()) {
                
                Document bookItem = new Document();
                String loanRecordNum = rs.getString("Loan_Record_Num");
                bookItem.append("Book_ID", rs.getString("Book_ID"));
                bookItem.append("Book_Name", rs.getString("Book_Name"));
                
                loan_no_in_rs_row = loanRecordNum;
                
                ArrayList<Document> loan_items;
                if (loan_no_in_rs_row.equalsIgnoreCase(current_loan_no) == false) {
                    loan = new Document();
                    
                    loan_items = new ArrayList();
                    loan.append("Loan_Record_Num", loanRecordNum);
                    loan_items.add(bookItem);
                    loan.append("Returned_Books", loan_items);
                    
                    current_loan_no = loan_no_in_rs_row;
                    loan_history_list.add(loan);
                } else {
                    
                    ((ArrayList)loan.get("Returned_Books")).add(bookItem);
                }
                
            }
            
            stmt.close();
            
            return loan_history_list;
            
        } catch (SQLException e ) {
            e.printStackTrace();
        } 
        
        ArrayList<Document> emptyReturnedBooks = new ArrayList();
        Document emptyBookItem = new Document();
        emptyBookItem.append("Book_ID", "");
        emptyBookItem.append("Book_Name", "");
        emptyBookItem.append("Returned_Books", emptyReturnedBooks);
        
        return emptyReturnedBooks;
        
    }
    
 
    /**
     * This method returns the entire fine history of any given student, for the time-frame specified from the date parameters parsed.
     * 
     * @param studentNumber A unique student number to identify the student
     * @param fromDate The date that will determine the day at which the returned history will start at.
     * @param toDate The date that will determine the day at which the returned history will end at.
     * @return The fine history, as an ArrayList of Documents.
     */
    public ArrayList<Document> getFineHistory(String studentNumber, Date fromDate, Date toDate ){
      
        Document student = getStudentByStudentNum(studentNumber);
        
        
        String query = 
                       "SELECT tblFine.Loan_ID, tblFine.Book_ID, tblFine.Amount_Paid, tblFine.Date_Paid\n" +
                       "FROM tblFine\n" +
                       "INNER JOIN tblLoan\n" +
                       "ON tblLoan.Loan_ID = tblFine.Loan_ID\n" +
                       "WHERE tblFine.Student_ID = '" + student.get("Student_ID") + "'\n" +
                       "AND Date_Paid BETWEEN TO_DATE('" + new SimpleDateFormat("MM/dd/yyyy").format(fromDate) + "', 'mm/dd/yyyy')\n" +
                       "AND TO_DATE('" + new SimpleDateFormat("MM/dd/yyyy").format(toDate) + "', 'mm/dd/yyyy')";
       
   
        ArrayList fine_history_list = new ArrayList();
        
        Statement stmt = null;
        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
           
            while (rs.next()) {
                Document fineItem = new Document();
                Document bookItem = getBookById(rs.getString("Book_ID"));
                fineItem.append("Book_Name", bookItem.get("Book_Name"));
                fineItem.append("Amount_Paid", rs.getDouble("Amount_Paid"));
                fineItem.append("Date_Paid", rs.getString("Date_Paid").toString());
                fine_history_list.add(fineItem);
            }
            
            stmt.close();
            
             return fine_history_list;
            
        } catch (SQLException e ) {
            e.printStackTrace();
        } 
        
        return null;
        
        
        
    }
    
    /**
     *  This method inserts a book as a returned book, into the loan record specified.
     *
     * @param loanRecordNum The loan record associated with the book returned
     * @param isbn The ISBN of the book being returned
     */
    
    public void returnBook(String loanRecordNum, String isbn) {
        
        Document book = getBookByIsbn(isbn);
        Document loan = getLoanByRecordNum(loanRecordNum);
        
        String insertReturnTable = "INSERT INTO tblReturnedBookItem"
				+ "(Loan_ID, Book_Id, Date_Returned) " + "VALUES ("
				+ " '" + loan.get("Loan_ID") + "', " 
                                + " '" + book.get("Book_ID") + "', " 
                                + " sysdate " + ")";
                
        Statement stmt = null;
        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            
            System.out.println(insertReturnTable);

            // execute insert SQL stetement
            stmt.executeUpdate(insertReturnTable);
            
            stmt.close();
            
        } catch (SQLException e ) {
            e.printStackTrace();
        } 
    }
    
    /**
     *  This method inserts a fine document, associated with an already loaned loan record and book.
     * 
     * @param loanRecordNum The loan record number associated with the fine being paid
     * @param isbn The ISBN of the book associated with the fine
     * @param amountPaid The amount of fine that is being paid
     */
    public void payFine(String loanRecordNum, String isbn,  double amountPaid) {
        
        Document book = getBookByIsbn(isbn);
        Document loan = getLoanByRecordNum(loanRecordNum);
                
        String insertReturnTable = "INSERT INTO tblFine"
				+ "(Student_ID, Loan_Id, Book_ID , Amount_Paid, Date_Paid) " + "VALUES ("
                                + " '" + loan.get("Student_ID") + "', " 
				+ " '" + loan.get("Loan_ID") + "', " 
                                + " '" + book.get("Book_ID") + "', "
                                + " '" + amountPaid + "', " 
                                + " sysdate " + ")";
                
        Statement stmt = null;
        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            
            System.out.println(insertReturnTable);

            // execute insert SQL stetement
            stmt.executeUpdate(insertReturnTable);
            
            stmt.close();
            
        } catch (SQLException e ) {
            e.printStackTrace();
        } 

    }
    
    /**
     * This method creates a connection to Oracle
     * @return A connection object
     */
  
    public Connection getConnection() {
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) {

            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return null;

        }

        System.out.println("Oracle JDBC Driver Registered!");

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@apollo01.glos.ac.uk:1521:orcl", "S1408926",
                    "S1408926");
                    
            if (connection != null) {
                System.out.println("You are now connected to the database!");
                return connection;
            } else {
                System.out.println("Failed to make connection!");
                return null;
            }

        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return null;

        }

        
    }



}
