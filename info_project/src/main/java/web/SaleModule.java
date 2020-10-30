/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dao.SaleDAO;
import dao.SaleJdbcDAO;
import domain.Sale;
import org.jooby.Jooby;
import org.jooby.Status;

/**
 *
 * @author jamespettitt
 */
public class SaleModule extends Jooby{
    public SaleModule(SaleDAO saleDAO){
        post("/api/sales", (req, rsp) -> {
            Sale sale = req.body().to(Sale.class);
            System.out.println(sale);
            saleDAO.save(sale);
            rsp.status(Status.CREATED);
            });
    }
}
