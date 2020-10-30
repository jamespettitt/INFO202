/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Customer;
import web.auth.CredentialsValidator;

/**
 *
 * @author jamespettitt
 */
public interface CustomerDAO extends CredentialsValidator {
    public void saveCustomer(Customer customer);
    public Customer getCustomer(String username);
    //public Boolean validateCredentials(String username, String password);  
}
