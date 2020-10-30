/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.ProductDAO;
import dao.ProductJdbcDAO;
import domain.Product;
import helpers.SimpleListModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import static org.assertj.swing.core.matcher.DialogMatcher.withTitle;
import static org.assertj.swing.core.matcher.JButtonMatcher.withText;
import org.assertj.swing.fixture.DialogFixture;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 *
 * @author jamespettitt
 */
public class ProductViewerTest {
    
    private ProductDAO dao;
    private DialogFixture fixture;
    private Robot robot;
    private Product p1;
    private Product p2;
    private Product p3;
    
    public ProductViewerTest() {
    }
    
    @BeforeEach
    public void setUp() {
        Collection<Product> products = new HashSet<>();
        p1 = new Product("1", "2", "3", "4", BigDecimal.ONE, BigDecimal.TEN);
        p2 = new Product("11", "22", "33", "44", BigDecimal.ONE, BigDecimal.TEN);
        p3 = new Product("111", "222", "333", "444", BigDecimal.ONE, BigDecimal.TEN);
        robot = BasicRobot.robotWithNewAwtHierarchy();

		  products.add(p1);
		  products.add(p2);
		  products.add(p3);
        // Slow down the robot a little bit - default is 30 (milliseconds).
        // Do NOT make it less than 10 or you will have thread-race problems.
        robot.settings().delayBetweenEvents(75); 
        
        dao = mock(ProductDAO.class);
        
        when(dao.getProducts()).thenReturn(products);
        
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable { 
                products.remove(p1);
                return null;
            }
        }).when(dao).removeProduct(p1);
    }
    
    @AfterEach
    public void tearDown() {
        fixture.cleanUp();
    }

    @Test
    public void testDelete() {
        // create the dialog passing in the mocked DAO
        ProductViewer dialog = new ProductViewer(null, true, dao);

        // use AssertJ to control the dialog
        fixture = new DialogFixture(robot, dialog);

        // show the dialog on the screen, and ensure it is visible
        fixture.show().requireVisible();

        fixture.click();
        
        fixture.list("listProducts").selectItem(p1.toString());
        fixture.button("buttonDelete").click();
        DialogFixture dialog1 = fixture.dialog(withTitle("Select an Option"));
        dialog1.button(withText("Yes")).click();
        verify(dao).removeProduct(p1);
        
        
        SimpleListModel model = (SimpleListModel) fixture.list("listProducts").target().getModel();
		  
        assertTrue("List has expected product1", !model.contains(p1));
        assertEquals("List has expected number of products", 2, model.getSize());
    
    }
    
    @Test
    public void testView(){
        ProductViewer dialog = new ProductViewer(null,true, dao);
        fixture = new DialogFixture(robot, dialog);
        fixture.show().requireVisible();
        
        fixture.click();
        verify(dao).getProducts();
        SimpleListModel model = (SimpleListModel) fixture.list("listProducts").target().getModel();
        Product tP = p1;
        assertTrue("List has expected product1", model.contains(tP));
        assertEquals("List has expected number of products", 3, model.getSize());
        
    }
    
}

