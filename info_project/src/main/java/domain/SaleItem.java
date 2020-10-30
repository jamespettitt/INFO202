/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.math.BigDecimal;

/**
 *
 * @author petja122
 */
public class SaleItem {
	private Product product;
	
	private BigDecimal quantityPurchased;
	private BigDecimal salePrice;
        
        private Sale sale;

        
    public SaleItem(Product product, BigDecimal salePrice){
        this.product = product;
        this.salePrice = salePrice;
    }
            
    @Override
    public String toString() {
        return "SaleItem{" + "product=" + product + ", quantityPurchased=" + quantityPurchased + ", salePrice=" + salePrice + '}';
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
	
        

    public BigDecimal getItemTotal(){
            return salePrice.multiply(quantityPurchased);
    }

    public BigDecimal getQuantityPurchased() {
            return quantityPurchased;
    }

    public void setQuantityPurchased(BigDecimal quantityPurchased) {
            this.quantityPurchased = quantityPurchased;
    }

    public BigDecimal getSalePrice() {
            return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
            this.salePrice = salePrice;
    }
	
	
}

