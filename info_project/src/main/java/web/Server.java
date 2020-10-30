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
import dao.SaleDAO;
import dao.SaleJdbcDAO;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.jooby.Jooby;
import org.jooby.json.Gzon;
import web.auth.BasicHttpAuthenticator;

/**
 *
 * @author jamespettitt
 */
public class Server extends Jooby {
    private ProductDAO productDao = new ProductJdbcDAO();
    private CustomerDAO customerDao = new CustomerJdbcDAO();
    private SaleDAO saleDao = new SaleJdbcDAO();
    
    public static void main(String[] args) throws Exception {
        System.out.println("\nStarting Server.");

        Server server = new Server();

        CompletableFuture.runAsync(() -> {
           server.start();
        });

        server.onStarted(() -> {
           System.out.println("\nPress Enter to stop the server.");
        });

        // wait for user to hit the Enter key
        System.in.read();
        System.exit(0);
     }
    
    public Server(){
        port(8080);
        use(new Gzon());
        use(new AssetModule());
        
        List<String> noAuth = Arrays.asList("/api/register");
        use(new BasicHttpAuthenticator(customerDao, noAuth));
        
        use(new ProductModule(productDao));
        use(new CustomerModule(customerDao));
        use(new SaleModule(saleDao));
        
    }
    
}
