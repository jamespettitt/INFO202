/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  petja122
 * Created: 4/08/2020
 */

create table Product (
	Product_ID varchar(10) not null,
	Product_Name varchar(100) not null,
	Description varchar(2500),
	Category varchar(50) not null,
	Price decimal(7,2) not null,
	Stock decimal(7,2) not null,
	constraint ID_PK primary key (Product_ID)
);

create table Customer (
        Customer_ID int not null AUTO_INCREMENT,
        Username varchar(20) not null unique,
        First_Name varchar(25) not null,
        Surname varchar(25) not null, 
        Password varchar (20) not null,
        Email_Address varchar (50) not null,
        Shipping_Address varchar (120) not null,
        constraint Customer_PK primary key (Customer_ID)
);

create table Sale (
        Sale_ID int AUTO_INCREMENT,
        Date date not null,
        Status varchar(25) not null,
        Customer_ID int,

        constraint Sale_PK primary key (Sale_ID),
        constraint Sale_Customer_FK foreign key (Customer_ID) references Customer(Customer_ID)     
);



create table Sale_Item (
        Quantity decimal(7,2) not null,
        Price decimal(7,2) not null,
        Product_ID varchar(10) not null,
        Sale_ID int,

        constraint Sale_Item_PK primary key (Sale_ID, Product_ID),
        constraint Sale_Item_Product_FK foreign key (Product_ID) references Product(Product_ID),
        constraint Sale_Item_Sale_FK foreign key (Sale_ID) references Sale(Sale_ID) 
);
