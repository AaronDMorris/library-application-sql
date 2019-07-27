/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import org.bson.Document;
import org.mindrot.BCrypt;

/**
 *
 * @author arron
 */
public class DAO_DataWarehouse {
    
    /**
     * This function returns a list of days of the week, and the amount of loans taken out on 
     * each of the days. The parameters depict what campus data to search, and the date range also
     * @param campus A String depicting the name of the campus data to search
     * @param fromDate A Date object depicting the date to start the search from
     * @param toDate A Date object depicting the date to which the search ends
     * @return An array list of documents
     */
    public ArrayList<Document> getBusiestDayOfTheWeek(String campus, Date fromDate, Date toDate ){
        
        String query = 
                       "SELECT DimDate.WEEK_DAY_FULL, COUNT(*) as Loan_Amount\n" +
                       "FROM FactLoan\n" +
                       "INNER JOIN DimDate\n" +
                       "ON DimDate.DAY_KEY = FactLoan.DAY_KEY\n" +
                       "INNER JOIN DimResource\n" +
                       "ON DimResource.Resource_Key = FactLoan.Resource_Key\n" +
                       "WHERE FactLoan.DAY_KEY BETWEEN TO_DATE('" + new SimpleDateFormat("MM/dd/yyyy").format(fromDate) + "', 'mm/dd/yyyy')\n" +
                       "AND TO_DATE('" + new SimpleDateFormat("MM/dd/yyyy").format(toDate) + "', 'mm/dd/yyyy')\n" + 
                       "AND REGEXP_LIKE(DimResource.Campus_Name, '" + campus + "')\n" +
                       "GROUP BY DimDate.WEEK_DAY_FULL\n" + 
                       "ORDER BY Loan_Amount DESC";
       
        ArrayList busiest_day_list = new ArrayList();
        
        Statement stmt = null;
        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
           
            while (rs.next()) {
                Document day = new Document();
                day.append("Week_Day_Full", rs.getString("Week_Day_Full"));
                day.append("Loan_Amount", rs.getString("Loan_Amount"));
                busiest_day_list.add(day);
            }
            
            stmt.close();
            
            return busiest_day_list;
            
        } catch (SQLException e ) {
            e.printStackTrace();
        } 
        
        return null;
        
        
        
    }
    
    /**
     * This function returns the sum of all the fines paid, at the campus that is specified,
     * for the date ranges specified also
     * @param campus A String depicting the name of the campus data to search
     * @param inputFromDate A Date object depicting the date to start the search from
     * @param inputToDate A Date object depicting the date to which the search ends
     * @return An array list of documents
     */
    public ArrayList<Document> getFinesAtCampus(String campus, Date inputFromDate, Date inputToDate) {
        
        String query = 
                       "SELECT DimResource.CAMPUS_NAME, sum(FactLoan.TOTAL_FINES_PAID) as Total_Fines\n" +
                       "FROM FactLoan\n" +
                       "INNER JOIN DimResource\n" +
                       "ON DimResource.Resource_Key = FactLoan.Resource_Key\n" +
                       "WHERE FactLoan.DAY_KEY BETWEEN TO_DATE('" + new SimpleDateFormat("MM/dd/yyyy").format(inputFromDate) + "', 'mm/dd/yyyy')\n" +
                       "AND TO_DATE('" + new SimpleDateFormat("MM/dd/yyyy").format(inputToDate) + "', 'mm/dd/yyyy')\n" +
                       "AND REGEXP_LIKE(DimResource.Campus_Name, '" + campus + "')\n" +
                       "GROUP BY DimResource.Campus_Name\n" + 
                       "ORDER BY Total_Fines ASC";
       
        
        ArrayList total_fines_list = new ArrayList();
        
        Statement stmt = null;
        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
           
            while (rs.next()) {
                Document campusDoc = new Document();
                campusDoc.append("Campus_Name", rs.getString("Campus_Name"));
                campusDoc.append("Total_Fines", rs.getString("Total_Fines"));
                total_fines_list.add(campusDoc);
            }
            
            stmt.close();
            
            return total_fines_list;
            
        } catch (SQLException e ) {
            e.printStackTrace();
        } 
        
        return null;
        
    }
    
    /**
     * This function returns the total amount of loaned resources taken out, at the campus
     * specified, for the date range specified 
     * @param campus A String depicting the name of the campus data to search
     * @param inputFromDate A Date object depicting the date to start the search from
     * @param inputToDate A Date object depicting the date to which the search ends
     * @return An array list of documents
     */
    public ArrayList<Document> getLoansAtCampus(String campus, Date inputFromDate, Date inputToDate) {
        
        String query = 
                       "SELECT DimResource.CAMPUS_NAME, COUNT(*) as Loan_Amount\n" +
                       "FROM FactLoan\n" +
                       "INNER JOIN DimResource\n" +
                       "ON DimResource.Resource_Key = FactLoan.Resource_Key\n" +
                       "WHERE FactLoan.DAY_KEY BETWEEN TO_DATE('" + new SimpleDateFormat("MM/dd/yyyy").format(inputFromDate) + "', 'mm/dd/yyyy')\n" +
                       "AND TO_DATE('" + new SimpleDateFormat("MM/dd/yyyy").format(inputToDate) + "', 'mm/dd/yyyy')\n" +
                       "AND REGEXP_LIKE(DimResource.Campus_Name, '" + campus + "')\n" +
                       "GROUP BY DimResource.Campus_Name\n" + 
                       "ORDER BY Loan_Amount DESC";
       
        
        ArrayList total_loans_list = new ArrayList();
        
        Statement stmt = null;
        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
           
            while (rs.next()) {
                Document campusDoc = new Document();
                campusDoc.append("Campus_Name", rs.getString("Campus_Name"));
                campusDoc.append("Loan_Amount", rs.getString("Loan_Amount"));
                total_loans_list.add(campusDoc);
            }
            
            stmt.close();
            
            return total_loans_list;
            
        } catch (SQLException e ) {
            e.printStackTrace();
        } 
        
        return null;
        
    }
    
    /**
     * This function returns the amount of loaned resources taken out, for the financial quarter given as an input
     * @param financialQuarter A String depicting which financial quarter of a year to search
     * @return An array list of documents
     */
    public ArrayList<Document> getLoansByFinancialQuarter(String financialQuarter) {
        
        String query = 
                       "SELECT DimResource.CAMPUS_NAME, COUNT(*) as Loan_Amount\n" +
                       "FROM FactLoan\n" +
                       "INNER JOIN DimResource\n" +
                       "ON DimResource.Resource_Key = FactLoan.Resource_Key\n" +
                       "INNER JOIN DimDate\n" +
                       "ON DimDate.DAY_KEY = FactLoan.DAY_KEY\n" +
                       "WHERE REGEXP_LIKE(DimResource.Campus_Name, '.')\n" +
                       "AND REGEXP_LIKE(DimDate.QUARTER_ID, '" + financialQuarter + "')\n" +
                       "GROUP BY DimResource.Campus_Name\n" + 
                       "ORDER BY Loan_Amount DESC";
       
        
        ArrayList total_loans_list = new ArrayList();
        
        Statement stmt = null;
        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
           
            while (rs.next()) {
                Document campusDoc = new Document();
                campusDoc.append("Campus_Name", rs.getString("Campus_Name"));
                campusDoc.append("Loan_Amount", rs.getString("Loan_Amount"));
                total_loans_list.add(campusDoc);
            }
            
            stmt.close();
            
            return total_loans_list;
            
        } catch (SQLException e ) {
            e.printStackTrace();
        } 
        
        return null;
        
    }
     
    /**
     * This function returns the amount of fines that have remained unpaid, for each of the campuses,
     * for the financial quarter given as an input
     * @param financialQuarter A String depicting which financial quarter of a year to search
     * @return An array list of documents
     */
    public ArrayList<Document> getUnpaidFinesByFinancialQuarter(String financialQuarter) {
        
        String query = 
                       "SELECT DimResource.CAMPUS_NAME, COUNT(*) as Unpaid_Fines\n" +
                       "FROM FactLoan\n" +
                       "INNER JOIN DimResource\n" +
                       "ON DimResource.Resource_Key = FactLoan.Resource_Key\n" +
                       "INNER JOIN DimDate\n" +
                       "ON DimDate.DAY_KEY = FactLoan.DAY_KEY\n" +
                       "WHERE REGEXP_LIKE(DimResource.Campus_Name, '.')\n" +
                       "AND REGEXP_LIKE(DimDate.QUARTER_ID, '" + financialQuarter + "')\n" +
                       "AND FactLoan.TOTAL_FINES_PAID IS NULL\n" +
                       "AND (FactLoan.RETAINED_DURATION > DimResource.MAXIMUM_LOAN_LENGTH\n" +
                       "    OR FactLoan.RETAINED_DURATION IS NULL AND ((sysdate - FactLoan.DAY_KEY) > DimResource.MAXIMUM_LOAN_LENGTH))\n" +
                       "GROUP BY DimResource.Campus_Name\n" + 
                       "ORDER BY Unpaid_Fines DESC";
       
        
        ArrayList unpaid_fines_list = new ArrayList();
        
        Statement stmt = null;
        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
           
            while (rs.next()) {
                Document campusDoc = new Document();
                campusDoc.append("Campus_Name", rs.getString("Campus_Name"));
                campusDoc.append("Unpaid_Fines", rs.getString("Unpaid_Fines"));
                unpaid_fines_list.add(campusDoc);
            }
            
            stmt.close();
            
            return unpaid_fines_list;
            
        } catch (SQLException e ) {
            e.printStackTrace();
        } 
        
        return null;
        
    }
    
    /**
     * This function returns the amount of unreturned resources at the campus given as an input
     * , for the year given as an input
     * @param year An integer depicting the year of which date range to search
     * @param campus A String depicting the name of the campus data to search
     * @return An array list of documents
     */
    public ArrayList<Document> getUnreturnedResourcesByYearAndCampus(String year, String campus) {
        
        String query = 
                       "SELECT DimDate.QUARTER_ID, COUNT(*) as Unreturned_Resources\n" +
                       "FROM FactLoan\n" +
                       "INNER JOIN DimResource\n" +
                       "ON DimResource.Resource_Key = FactLoan.Resource_Key\n" +
                       "INNER JOIN DimDate\n" +
                       "ON DimDate.DAY_KEY = FactLoan.DAY_KEY\n" +
                       "WHERE REGEXP_LIKE(DimResource.Campus_Name, '" + campus + "')\n" +
                       "AND REGEXP_LIKE(DimDate.YEAR_ID, '" + year + "')\n" +
                       "AND FactLoan.TOTAL_FINES_PAID IS NULL\n" +
                       "AND (FactLoan.RETAINED_DURATION > DimResource.MAXIMUM_LOAN_LENGTH\n" +
                       "    OR FactLoan.RETAINED_DURATION IS NULL AND ((sysdate - FactLoan.DAY_KEY) > DimResource.MAXIMUM_LOAN_LENGTH))\n" +
                       "GROUP BY DimDate.QUARTER_ID\n" + 
                       "ORDER BY Unreturned_Resources DESC";
       
        
        ArrayList unreturned_resource_list = new ArrayList();
        
        Statement stmt = null;
        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
           
            while (rs.next()) {
                Document campusDoc = new Document();
                campusDoc.append("Quarter_ID", rs.getString("Quarter_ID"));
                campusDoc.append("Unreturned_Resources", rs.getString("Unreturned_Resources"));
                unreturned_resource_list.add(campusDoc);
            }
            
            stmt.close();
            
            return unreturned_resource_list;
            
        } catch (SQLException e ) {
            e.printStackTrace();
        } 
        
        return null;
        
    }
    
    /**
     * This function returns the amount of loans for any given resource type, for the campus given as an input,
     * across the date ranges given as an input 
     * @param campus A String depicting the name of the campus data to search
     * @param inputFromDate A Date object depicting the date to start the search from
     * @param inputToDate A Date object depicting the date to which the search ends
     * @return An array list of documents
     */
    public ArrayList<Document> getPopularResourceType(String campus, Date inputFromDate, Date inputToDate) {
        
        String query = 
                       "SELECT DimResource.Resource_Type, COUNT(*) as Loan_Amount\n" +
                       "FROM FactLoan\n" +
                       "INNER JOIN DimResource\n" +
                       "ON DimResource.Resource_Key = FactLoan.Resource_Key\n" +
                       "WHERE FactLoan.DAY_KEY BETWEEN TO_DATE('" + new SimpleDateFormat("MM/dd/yyyy").format(inputFromDate) + "', 'mm/dd/yyyy')\n" +
                       "AND TO_DATE('" + new SimpleDateFormat("MM/dd/yyyy").format(inputToDate) + "', 'mm/dd/yyyy')\n" +
                       "AND REGEXP_LIKE(DimResource.Campus_Name, '" + campus + "')\n" +
                       "GROUP BY DimResource.Resource_Type\n" + 
                       "ORDER BY Loan_Amount DESC";
       
        
        ArrayList total_loans_list = new ArrayList();
        
        Statement stmt = null;
        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
           
            while (rs.next()) {
                Document campusDoc = new Document();
                campusDoc.append("Resource_Type", rs.getString("Resource_Type"));
                campusDoc.append("Loan_Amount", rs.getString("Loan_Amount"));
                total_loans_list.add(campusDoc);
            }
            
            stmt.close();
            
            return total_loans_list;
            
        } catch (SQLException e ) {
            e.printStackTrace();
        } 
        
        return null;
        
    }
    
    /**
     * This function returns the title of the resources loaned out, with the amount of times they have been loaned,
     * for the campus given as an input, across the date ranges given as an input
     * @param campus A String depicting the name of the campus data to search
     * @param inputFromDate A Date object depicting the date to start the search from
     * @param inputToDate A Date object depicting the date to which the search ends
     * @return An array list of documents
     */
    public ArrayList<Document> getPopularResource(String campus, Date inputFromDate, Date inputToDate) {
        
        String query = 
                       "SELECT DimResource.RESOURCE_TITLE, COUNT(*) as Loan_Amount\n" +
                       "FROM FactLoan\n" +
                       "INNER JOIN DimResource\n" +
                       "ON DimResource.Resource_Key = FactLoan.Resource_Key\n" +
                       "WHERE FactLoan.DAY_KEY BETWEEN TO_DATE('" + new SimpleDateFormat("MM/dd/yyyy").format(inputFromDate) + "', 'mm/dd/yyyy')\n" +
                       "AND TO_DATE('" + new SimpleDateFormat("MM/dd/yyyy").format(inputToDate) + "', 'mm/dd/yyyy')\n" +
                       "AND REGEXP_LIKE(DimResource.Campus_Name, '" + campus + "')\n" +
                       "GROUP BY DimResource.RESOURCE_TITLE\n" + 
                       "ORDER BY Loan_Amount DESC";
       
        
        ArrayList total_loans_list = new ArrayList();
        
        Statement stmt = null;
        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
           
            while (rs.next()) {
                Document campusDoc = new Document();
                campusDoc.append("Resource_Title", rs.getString("Resource_Title"));
                campusDoc.append("Loan_Amount", rs.getString("Loan_Amount"));
                total_loans_list.add(campusDoc);
            }
            
            stmt.close();
            
            return total_loans_list;
            
        } catch (SQLException e ) {
            e.printStackTrace();
        } 
        
        return null;
        
    }
    
    /**
     * This function will return the amount of loans for each day that loans have been taken out,
     * for the campus provided an input, across the year given as an input
     * @param year An integer depicting the year of which date range to search
     * @param campus A String depicting the name of the campus data to search
     * @return An array list of documents
     */
    public ArrayList<Document> getBusiestDayOfYear(String year, String campus) {
        
        String query = 
                       "SELECT FactLoan.DAY_KEY, COUNT(*) as Loan_Amount\n" +
                       "FROM FactLoan\n" +
                       "INNER JOIN DimDate\n" +
                       "ON DimDate.DAY_KEY = FactLoan.DAY_KEY\n" +
                       "INNER JOIN DimResource\n" +
                       "ON DimResource.Resource_Key = FactLoan.Resource_Key\n" +
                       "WHERE REGEXP_LIKE(DimResource.Campus_Name, '" + campus + "')\n" +
                       "AND REGEXP_LIKE(DimDate.YEAR_ID, '" + year + "')\n" +
                       "GROUP BY FactLoan.DAY_KEY\n" + 
                       "ORDER BY Loan_Amount DESC";
       
        
        ArrayList total_loans_list = new ArrayList();
        
        Statement stmt = null;
        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
           
            while (rs.next()) {
                Document campusDoc = new Document();
                campusDoc.append("Day_Key", rs.getString("Day_Key"));
                campusDoc.append("Loan_Amount", rs.getString("Loan_Amount"));
                total_loans_list.add(campusDoc);
            }
            
            stmt.close();
            
            return total_loans_list;
            
        } catch (SQLException e ) {
            e.printStackTrace();
        } 
        
        return null;
        
    }
    
    /**
     * This function returns the average amount of days that a loan has been kept for,
     * fore the campus provided in the input, for the year provided as an input
     * @param campus A String depicting the name of the campus data to search
     * @param year An integer depicting the year of which date range to search
     * @return An array list of documents
     */
    public ArrayList<Document> getAverageRetainedDuration(String campus, int year) {
        
        Date yearStart = new GregorianCalendar(year, 0, 1).getTime();
        Date yearEnd = new GregorianCalendar(year, 11, 31, 23, 59).getTime();
        
        String query = 
                       "SELECT DimDate.Quarter_ID, ROUND(AVG(FactLoan.RETAINED_DURATION)) as Average_Retained\n" +
                       "FROM FactLoan\n" +
                       "INNER JOIN DimDate\n" +
                       "ON DimDate.DAY_KEY = FactLoan.DAY_KEY\n" +
                       "INNER JOIN DimResource\n" +
                       "ON DimResource.Resource_Key = FactLoan.Resource_Key\n" +
                       "WHERE FactLoan.DAY_KEY BETWEEN TO_DATE('" + new SimpleDateFormat("MM/dd/yyyy").format(yearStart) + "', 'mm/dd/yyyy')\n" +
                       "AND TO_DATE('" + new SimpleDateFormat("MM/dd/yyyy").format(yearEnd) + "', 'mm/dd/yyyy')\n" +
                       "AND REGEXP_LIKE(DimResource.Campus_Name, '" + campus + "')\n" +
                       "GROUP BY DimDate.Quarter_ID\n" + 
                       "ORDER BY Average_Retained DESC";
       
        
        ArrayList total_loans_list = new ArrayList();
        
        Statement stmt = null;
        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
           
            while (rs.next()) {
                Document campusDoc = new Document();
                campusDoc.append("Quarter_ID", rs.getString("Quarter_ID"));
                campusDoc.append("Average_Retained", rs.getString("Average_Retained"));
                total_loans_list.add(campusDoc);
            }
            
            stmt.close();
            
            return total_loans_list;
            
        } catch (SQLException e ) {
            e.printStackTrace();
        } 
        
        return null;
    }
    
    /**
     * This function receives a username and password, and returns a document user object
     * with a user's associated permissions and authentication state
     * @param username A String representing the username of the user
     * @param password A String to be checked against the stored hash password
     * @return A document object containing a user
     */
    public Document performLogin(String username, String password) {
        
        String loginSql = "SELECT * from tblUsers WHERE Username = ?";
       
        try {
            
            Connection con = getConnection();
            PreparedStatement pStatement = con.prepareStatement(loginSql);
            pStatement.setString(1, username);
            
            ResultSet rs = pStatement.executeQuery();
            
            Document user = new Document();
            
            if(rs.next()){
                user.append("Username", rs.getString("Username"));
                user.append("Has_Level1_Access", rs.getString("Has_Level1_Access"));
                user.append("Has_Level2_Access", rs.getString("Has_Level2_Access"));
                user.append("Has_Level3_Access", rs.getString("Has_Level3_Access"));
                user.append("Is_Authenticated", BCrypt.checkpw(password, rs.getString("Password_Hash")));
                pStatement.close();
                if(user.get("Is_Authenticated").toString().equals("true")){
                    return user;
                }
            }
 
        } catch (SQLException e ) {
            e.printStackTrace();
        } 
        return null;
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
