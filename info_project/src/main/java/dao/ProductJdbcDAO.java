/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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
 * @author petja122
 */
public class ProductJdbcDAO implements ProductDAO {
	private String url = DbConnection.getDefaultConnectionUri();
	
	public ProductJdbcDAO() {
	}
	
	//this may be wrong - check
	public ProductJdbcDAO(String uri){
		this.url = uri;
	}

	
	
	@Override
	public Collection<String> getCategories() {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		
		String sql = "select distinct category from Product";
		try (
			Connection dbCon = DbConnection.getConnection(url); //get connection to db
			PreparedStatement stmt = dbCon.prepareStatement(sql); //create stmt
		) {
			ResultSet rs = stmt.executeQuery(); //execute the query
			
			List<String> categories = new ArrayList<>(); //using list to preserver data order
			
			while(rs.next()){
				String category = rs.getString("Category"); //extracts category out of query
				categories.add(category); //adds it to category list
			}
			
			return categories;
		} catch (SQLException ex){
			throw new DAOException(ex.getMessage(), ex);
		}
	}

	
	
	@Override
	public Collection<Product> getProducts() {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		
		String sql = "select * from Product";
		try (
			Connection dbCon = DbConnection.getConnection(url); //get connection to db
			PreparedStatement stmt = dbCon.prepareStatement(sql); //create stmt
		) {
			ResultSet rs = stmt.executeQuery(); //execute the query
			
			List<Product> products = new ArrayList<>(); //using list to preserver data order
			
			//iterate through query results
			while (rs.next()){
				Product product = new Product(rs.getString("Product_ID"), 
														rs.getString("Product_Name"),
														rs.getString("Description"),
														rs.getString("Category"),
														rs.getBigDecimal("Price"),
														rs.getBigDecimal("Stock")
														);
				products.add(product); //put it in the collection
			}
			
			return products;
			
		} catch (SQLException ex){
			throw new DAOException(ex.getMessage(), ex);
		}
	}
	
	
	

	@Override
	public void removeProduct(Product p1) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		String sql = "delete from Product where Product_ID = ?";
		
		try (
			Connection dbCon = DbConnection.getConnection(url); //get connection to db
			PreparedStatement stmt = dbCon.prepareStatement(sql); //create stmt
		) {
			stmt.setString(1, p1.getProductID());
			stmt.executeUpdate();
			
		} catch (SQLException ex){
			throw new DAOException(ex.getMessage(), ex);
		}
	}
	
	
	
	
	@Override
	public void saveProduct(Product p1) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                
                //changed to merge so i can update records
		String sql = "merge into Product (Product_ID, Product_Name, Description, Category, Price, Stock) values (?, ?, ?, ?, ?, ?)";
		
		try (
			Connection dbCon = DbConnection.getConnection(url); //get connection to db
			PreparedStatement stmt = dbCon.prepareStatement(sql); //create stmt
		){
			stmt.setString(1, p1.getProductID());
			stmt.setString(2, p1.getName());
			stmt.setString(3, p1.getDescription());
			stmt.setString(4, p1.getCategory());
			stmt.setBigDecimal(5, p1.getPrice());
			stmt.setBigDecimal(6, p1.getStock());
			
			stmt.executeUpdate();
			
		} catch (SQLException ex){
			throw new DAOException(ex.getMessage(), ex);
		}
	
	}

	
	
	@Override
	public Product searchByID(String productID) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		String sql = "select * from Product where Product_ID = ?";
		
		try (
			Connection dbCon = DbConnection.getConnection(url); //get connection to db
			PreparedStatement stmt = dbCon.prepareStatement(sql); //create stmt
		) {
			stmt.setString(1, productID);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()){
				Product product = new Product(rs.getString("Product_ID"), 
														rs.getString("Product_Name"),
														rs.getString("Description"),
														rs.getString("Category"),
														rs.getBigDecimal("Price"),
														rs.getBigDecimal("Stock")
														);
				return product;
			} else {
				return null; //no match
			}
			
		} catch (SQLException ex){
			throw new DAOException(ex.getMessage(), ex);
		}
	
	}
	
	
	
	@Override
	public Collection<Product> filterByCategory(String category) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		String sql = "select * from Product where Category = ?";
		System.out.println(category);
		try (
			Connection dbCon = DbConnection.getConnection(url); //get connection to db
			PreparedStatement stmt = dbCon.prepareStatement(sql); //create stmt
		) {
			stmt.setString(1, category); //puts category into statement
			ResultSet rs = stmt.executeQuery();
			
			List<Product> categoryList = new ArrayList<>(); //create new arraylist of products with that category
			
			while(rs.next()){
				Product product = new Product(rs.getString("Product_ID"), 
														rs.getString("Product_Name"),
														rs.getString("Description"),
														rs.getString("Category"),
														rs.getBigDecimal("Price"),
														rs.getBigDecimal("Stock")
														);
				System.out.println(product);
				categoryList.add(product);
			} 
			return categoryList;
		} catch (SQLException ex){
			throw new DAOException(ex.getMessage(), ex);
		}
	}
	
	
}
