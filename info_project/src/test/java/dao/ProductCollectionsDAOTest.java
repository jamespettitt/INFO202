/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Product;
import java.math.BigDecimal;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author petja122
 */

public class ProductCollectionsDAOTest {
   
   //private ProductCollectionsDAO product;
   ProductDAO product = new ProductJdbcDAO("jdbc:h2:mem:tests;INIT=runscript from 'src/main/java/dao/schema.sql'"); 
 
   private Product p1;
   private Product p2;
   private Product p3;
   //private String c1;
   //private String c2;
   //private String c3;
   
   @BeforeEach
   public void setUp() {
        //product = new ProductCollectionsDAO();

        p1 = new Product("1", "James", "desc1", "cat1", new BigDecimal("1.00"), new BigDecimal("2.00"));
        p2 = new Product("2", "Matt", "desc2", "cat2", new BigDecimal("3.00"), new BigDecimal("4.00"));
        p3 = new Product("3", "Jack", "desc3", "cat3", new BigDecimal("5.00"), new BigDecimal("6.00"));
        //p1.setCategory("cat1");
        //p1.setProductID("1");
        //p2.setCategory("cat2");
        //p2.setProductID("2");
        //p3.setCategory("cat3");
        //p3.setProductID("3");

        product.saveProduct(p1);
        product.saveProduct(p2);
    }
	
    @AfterEach
    public void tearDown() {
        product.removeProduct(p1);
        product.removeProduct(p2);
    }

    @Test
    public void testSaveProduct() {
        product.saveProduct(p3);
        assertThat(product.getProducts(), hasSize(3));
        assertThat(product.getProducts(), hasItem(p1));
        assertThat(product.getProducts(), hasItem(p2));
        assertThat(product.getProducts(), hasItem(p3));
    }

    @Test
    public void testGetProducts() {
        assertThat(product.getProducts(), hasItem(p1));
        assertThat(product.getProducts(), hasItem(p2));
    }

    @Test
    public void testRemoveProduct() {
        product.removeProduct(p2);
        assertThat(product.getProducts(), hasSize(1));
        assertThat(product.getProducts(), not(hasItem(p2)));
    }

    @Test
    public void testGetCategories(){
        assertThat(product.getCategories(), hasItem(p1.getCategory()));
        assertThat(product.getCategories(), hasItem(p2.getCategory()));
    }

    @Test
    public void testSearchByID(){
        Product result = product.searchByID(p1.getProductID());
        assertThat(result, is(p1));
        assertThat(product.searchByID("-99"), is(nullValue()));
    }

    @Test
    public void filterByCategory() {
        assertThat(product.filterByCategory(p1.getCategory()), hasItem(p1));
        assertThat(product.filterByCategory(p1.getCategory()), not(hasItem(p2)));
    }
}
