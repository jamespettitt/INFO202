package dao;

import domain.Customer;
import domain.Product;
import domain.Sale;
import domain.SaleItem;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaleJdbcDAO implements SaleDAO {

	private final String url = DbConnection.getDefaultConnectionUri();

	@Override
	public void save(Sale sale) {

		Connection con = DbConnection.getConnection(url);
		try {
			try (
					PreparedStatement insertSaleStmt = con.prepareStatement(
							"insert into Sale (Date, Status, Customer_ID) values(?,?,?)",
							Statement.RETURN_GENERATED_KEYS);

					PreparedStatement insertSaleItemStmt = con.prepareStatement(
							"insert into Sale_Item (Quantity, Price, Product_ID, Sale_ID) values (?,?,?,?)");

					PreparedStatement updateProductStmt = con.prepareStatement(
							"merge into Product (Product_ID, Product_Name, Description, Category, Price, Stock) values (?,?,?,?,?,?)");

					) {

				// Since saving a Sale involves multiple statements across
				// multiple tables we need to control the transaction ourselves
				// to ensure the DB remains consistent.  Turning off auto-commit
				// effectively starts a new transaction.
				con.setAutoCommit(false);

				Customer customer = sale.getCustomer(); //gets the customer details

				// #### save the sale ### //

				// add a date to the sale if one doesn't already exist
				if(sale.getDate() == null) {
					sale.setDate(LocalDateTime.now());
				}

				// convert sale date into to java.sql.Timestamp
				LocalDateTime date = sale.getDate();
				Timestamp timestamp = Timestamp.valueOf(date); //used below


				// ****
				// write code here that saves the timestamp and username in the
				// sale table using the insertSaleStmt statement.
				// ****
                                insertSaleStmt.setTimestamp(1, timestamp);
                                insertSaleStmt.setString(2, "Done");
                                insertSaleStmt.setInt(3, customer.getCustomerID());
                                insertSaleStmt.executeUpdate();
                                


				// get the auto-generated sale ID from the database
				ResultSet rs = insertSaleStmt.getGeneratedKeys();

				Integer saleId = null;

				if (rs.next()) {
					saleId = rs.getInt(1);
				} else {
					throw new DAOException("Problem getting generated sale ID");
				}

				// ## save the sale items ## //

				Collection<SaleItem> items = sale.getSaleItems();


				// ****
				// write code here that iterates through the sale items and
				// saves them using the insertSaleItemStmt statement.
				// ****
                                
                                


				// ## update the product quantities ## //
				for (SaleItem item : items) {

					Product product = item.getProduct();
                                        
                                        insertSaleItemStmt.setBigDecimal(1, item.getQuantityPurchased());
                                        insertSaleItemStmt.setBigDecimal(2, item.getSalePrice());
                                        insertSaleItemStmt.setString(3, product.getProductID());
                                        insertSaleItemStmt.setInt(4, saleId);
                                        insertSaleItemStmt.executeUpdate();
                                        


					// ****
					// write code here that updates the product quantity using
					// the updateProductStmt statement.
					// ****
                                        
                                        BigDecimal quantity1 = product.getStock();
                                        BigDecimal quantity2 = item.getQuantityPurchased();
                                        BigDecimal newQuantity = quantity1.subtract(quantity2);
                                        
                                        //id name description category price stock
                                        updateProductStmt.setString(1, product.getProductID());
                                        updateProductStmt.setString(2, product.getName());
                                        updateProductStmt.setString(3, product.getDescription());
                                        updateProductStmt.setString(4, product.getCategory());
                                        updateProductStmt.setBigDecimal(5, product.getPrice());
                                        updateProductStmt.setBigDecimal(6, newQuantity);
                                        updateProductStmt.executeUpdate();


				}

				// commit the transaction
				con.setAutoCommit(true);
			}
		} catch (SQLException ex) {

			Logger.getLogger(SaleJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);

			try {
				// something went wrong so rollback
				con.rollback();

				// turn auto-commit back on
				con.setAutoCommit(true);

				// and throw an exception to tell the user something bad happened
				throw new DAOException(ex.getMessage(), ex);
			} catch (SQLException ex1) {
				throw new DAOException(ex1.getMessage(), ex1);
			}

		} finally {
			try {
				con.close();
			} catch (SQLException ex) {
				Logger.getLogger(SaleJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

}