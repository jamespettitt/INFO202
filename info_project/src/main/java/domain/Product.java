/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.math.BigDecimal;
import java.util.Objects;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNegative;
import net.sf.oval.constraint.NotNull;

/**
 *
 * @author petja122
 */
		  
public class Product {
        @NotNull (message = "Product ID must be provided.")
        @NotBlank (message = "Product  ID must be provided.")
        @Length(min=1, message="Product ID must contain at least one character.")
	private String productID;
        
        @NotNull (message = "Name must be provided.")
        @NotBlank (message = "Name must be provided.")
        @Length(min=2, message="Name must contain at least two characters.")
	private String name;
        
        //Doesn't have to be inputted
	private String description;
        
        @NotNull (message = "Category must be provided.")
        @NotBlank (message = "Category must be provided.")
        @Length(min=2, message="Category must contain at least two characters.")
	private String category;
        
        @NotNull (message = "Price must be provided.")
        @NotNegative (message = "Price must be zero or greater.")
	private BigDecimal price;
        
        @NotNull (message = "Quantity must be provided.")
        @NotNegative (message = "Quantity must be zero or greater.")
	private BigDecimal stock;

	public Product(String productID, String name, String description, String category, BigDecimal price, BigDecimal stock) {
		this.productID = productID;
		this.name = name;
		this.description = description;
		this.category = category;
		this.price = price;
		this.stock = stock;
	}

	public Product() {
	}

	
	@Override
	public String toString() {
		return (productID + ", " + name);
	}
	
	public String getProductID() {
		return productID;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getCategory() {
		return category;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getStock() {
		return stock;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setStock(BigDecimal stock) {
		this.stock = stock;
	}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.productID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (!Objects.equals(this.productID, other.productID)) {
            return false;
        }
        return true;
    }
	
	
}
