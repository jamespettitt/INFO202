/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Customer;
import domain.Product;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author jamespettitt
 */
public class CustomerJdbcDAO implements CustomerDAO{

    
    private String url = DbConnection.getDefaultConnectionUri();
    
    
    public CustomerJdbcDAO() {
    }
	
    //this may be wrong - check
    public CustomerJdbcDAO(String uri){
            this.url = uri;
    }
    
    @Override
    public void saveCustomer(Customer customer) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		String sql = "insert into Customer (Username, First_Name, Surname, Password, Email_Address, Shipping_Address) values (?, ?, ?, ?, ?, ?)";
		
		try (
			Connection dbCon = DbConnection.getConnection(url); //get connection to db
			PreparedStatement stmt = dbCon.prepareStatement(sql); //create stmt
		){
			//stmt.setInt(1, customer.getCustomerID());
                        stmt.setString(1, customer.getUsername());
                        stmt.setString(2, customer.getFirstName());
                        stmt.setString(3, customer.getSurname());
                        stmt.setString(4, customer.getPassword());
                        stmt.setString(5, customer.getEmailAddress());
                        stmt.setString(6, customer.getShippingAddress());
                     
			stmt.executeUpdate();
			
		} catch (SQLException ex){
			throw new DAOException(ex.getMessage(), ex);
		}
    }

    @Override
    public Customer getCustomer(String username) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String sql = "SELECT * FROM CUSTOMER where Username = ?";
		
		try (
			Connection dbCon = DbConnection.getConnection(url); //get connection to db
			PreparedStatement stmt = dbCon.prepareStatement(sql); //create stmt
		){
			stmt.setString(1, username);
                        ResultSet rs = stmt.executeQuery();
                        
                        if(rs.next()){
                            Integer customerID = rs.getInt("Customer_ID");
                            String customerUsername = rs.getString("Username");
                            String firstName = rs.getString("First_Name");
                            String surname = rs.getString("Surname");
                            String password = rs.getString("Password");
                            String emailAddress = rs.getString("Email_Address");
                            String shippingAddress = rs.getString("Shipping_Address");
                            
                            return new Customer(customerID, customerUsername, firstName, surname, password, emailAddress, shippingAddress);
                            
                        } else {
                            return null;
                        }
			
		} catch (SQLException ex){
			throw new DAOException(ex.getMessage(), ex);
		}
    }

    @Override
    public Boolean validateCredentials(String username, String password) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String sql = "SELECT * FROM CUSTOMER where Username = ? and Password = ?";
        try (
			Connection dbCon = DbConnection.getConnection(url); //get connection to db
			PreparedStatement stmt = dbCon.prepareStatement(sql); //create stmt
		){
			stmt.setString(1, username);
                        stmt.setString(2, password);
                        ResultSet rs = stmt.executeQuery();
                        
                        if(rs.next()){
                            return true;
                        } else {
                            return false;
                        }
			
		} catch (SQLException ex){
			throw new DAOException(ex.getMessage(), ex);
	}
    }
    
    
    
}
