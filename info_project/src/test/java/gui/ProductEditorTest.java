/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.ProductDAO;
import dao.ProductJdbcDAO;
import domain.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.DialogFixture;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author jamespettitt
 */
public class ProductEditorTest {
    
    private ProductDAO dao;
    private DialogFixture fixture;
    private Robot robot;
    private Product p1;
    private Product p2;
    private Product p3;
    
    public ProductEditorTest() {
    }
    
    @BeforeEach
    public void setUp() {
        robot = BasicRobot.robotWithNewAwtHierarchy();

        // Slow down the robot a little bit - default is 30 (milliseconds).
        // Do NOT make it less than 10 or you will have thread-race problems.
        robot.settings().delayBetweenEvents(75); 
        
        //add some products for testing with
        Collection<String> categories = new ArrayList<>();
        categories.add("C1");
        categories.add("C2");
        categories.add("C3");
        
        dao = mock(ProductDAO.class);
        
        when(dao.getCategories()).thenReturn(categories);
    }
    
    @AfterEach
    public void tearDown() {
        fixture.cleanUp();
    }

    @Test
    public void testSaveProduct() {
        // create the dialog passing in the mocked DAO
        ProductEditor dialog = new ProductEditor(null, true, dao);

        // use AssertJ to control the dialog
        fixture = new DialogFixture(robot, dialog);

        // show the dialog on the screen, and ensure it is visible
        fixture.show().requireVisible();

        // enter some details into the UI components
        fixture.textBox("txtID").enterText("99");
        fixture.textBox("txtName").enterText("00");
        fixture.textBox("txtDescription").enterText("D");
        fixture.comboBox("comboboxCategory").selectItem("C1");
        fixture.textBox("txtPrice").enterText("10");
        fixture.textBox("txtStock").enterText("10");

        // click the save button
        fixture.button("buttonSave").click();

        // create a Mockito argument captor to use to retrieve the passed student from the mocked DAO
        ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);

        // verify that the DAO.save method was called, and capture the passed student
        verify(dao).saveProduct(argument.capture());

        // retrieve the passed student from the captor
        Product savedProduct = argument.getValue();

        // test that the student's details were properly saved
        assertThat("Ensure the ID was saved", savedProduct.getProductID(), is("99"));
        assertThat("Ensure the name was saved", savedProduct.getName(), is("00"));
        assertThat("Ensure the description was saved", savedProduct.getDescription(), is("D"));
        assertThat("Ensure the category was saved", savedProduct.getCategory(), is("C1"));
        assertThat("Ensure the price was saved", savedProduct.getPrice().toString(), is("10"));
        assertThat("Ensure the stock was saved", savedProduct.getStock().toString(), is("10"));
    }
    
}
