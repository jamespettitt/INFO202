/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dao.CustomerCollectionsDAO;
import dao.CustomerDAO;
import dao.CustomerJdbcDAO;
import dao.ProductDAO;
import dao.ProductJdbcDAO;
import domain.Customer;
import org.jooby.Jooby;
import org.jooby.Result;
import org.jooby.Status;

/**
 *
 * @author jamespettitt
 */
public class CustomerModule extends Jooby {

    public CustomerModule(CustomerDAO customerDao){
        get("/api/customers/:username", (req) -> {
            String username = req.param("username").value();
        //return customerDao.getCustomer(username);
        if(customerDao.getCustomer(username) == null){
                return new Result().status(Status.NOT_FOUND);
            }else{
                return customerDao.getCustomer(username);
            }
        });
        
        post("/api/register", (req, rsp) -> {
        Customer customer = req.body().to(Customer.class);
        customerDao.saveCustomer(customer);
        rsp.status(Status.CREATED);
        });
    }
}