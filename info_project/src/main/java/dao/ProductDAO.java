/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Product;
import java.util.Collection;

/**
 *
 * @author petja122
 */
public interface ProductDAO {

	Collection<Product> filterByCategory(String category);

	Collection<String> getCategories();

	Collection<Product> getProducts();

	void removeProduct(Product p1);

	void saveProduct(Product p1);

	Product searchByID(String productID);
	
}
