/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author petja122
 */
public class Sale {
	private Collection<SaleItem> saleItems = new HashSet<SaleItem>();
	private Customer customer;
	
	private Integer saleID;
	private LocalDateTime date;
	private String status;

    @Override
    public String toString() {
        return "Sale{" + "saleItems=" + saleItems + ", customer=" + customer + ", saleID=" + saleID + ", date=" + date + ", status=" + status + '}';
    }

        
        
	public Collection<SaleItem> getSaleItems() {
		return saleItems;
	}

	public void setSaleItems(Collection<SaleItem> saleItems) {
		this.saleItems = saleItems;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
	
	public BigDecimal getTotal(){
		BigDecimal total = new BigDecimal(0);
		for(SaleItem item : saleItems){
			 total = total.add(item.getItemTotal());
		}
		return total;
	}
	
	public void addItem(SaleItem saleItem){
      saleItems.add(saleItem);
	}

	public Integer getSaleID() {
		return saleID;
	}

	public void setSaleID(Integer saleID) {
		this.saleID = saleID;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
