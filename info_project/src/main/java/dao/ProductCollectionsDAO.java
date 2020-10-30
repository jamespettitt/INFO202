/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import domain.Product;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


/**
 *
 * @author petja122
 */
public class ProductCollectionsDAO implements ProductDAO {
    private static Collection<Product> products = new HashSet<>();
    private static Collection<String> categoriesList = new HashSet<String>();
    private static Map<String, Product> productMap = new HashMap<>();
    private static Multimap<String,Product> categoryMap = HashMultimap.create();
	
	@Override
    public void saveProduct(Product p1){
        products.add(p1);
        categoriesList.add(p1.getCategory());
        productMap.put(p1.getProductID(), p1);
    }

	@Override
    public Collection<Product> getProducts(){
        return products;
    }

	@Override
    public void removeProduct(Product p1){
        products.remove(p1);
    }
        
	@Override
    public Collection<String> getCategories(){
        return categoriesList;
    }
	
	@Override
    public Product searchByID(String productID){
        boolean doesExist = productMap.containsKey(productID);
        if (doesExist) {
                return (productMap.get(productID));
        } else {
                return null;
        }
    }
    
	@Override
    public Collection<Product> filterByCategory(String category){
        for( Product product : products ){
            categoryMap.put(product.getCategory(), product);
        }
        Collection<Product> elements = categoryMap.get(category);
        return elements;
    }
	
}
