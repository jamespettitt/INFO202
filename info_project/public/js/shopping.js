"use strict";

class SaleItem {

    constructor(product, quantity) {
        // only set the fields if we have a valid product
        if (product) {
            this.product = product;
            this.quantityPurchased = quantity;
            this.salePrice = product.price;
        }
    }

    getItemTotal() {
        return this.salePrice * this.quantityPurchased;
    }

}

class ShoppingCart {

    constructor() {
        this.saleItems = new Array();
    }

    reconstruct(sessionData) {
        for (let item of sessionData.saleItems) {
            this.addItem(Object.assign(new SaleItem(), item));
        }
    }

    getItems() {
        return this.saleItems;
    }

    addItem(item) {
        this.saleItems.push(item);
    }

    setCustomer(customer) {
        this.customer = customer;
    }

    getTotal() {
        let total = 0;
        for (let item of this.saleItems) {
            total += item.getItemTotal();
        }
        return total;
    }

}

// create a new module, and load the other pluggable modules
var module = angular.module('ShoppingApp', ['ngResource', 'ngStorage']);

module.config(function ($sessionStorageProvider, $httpProvider) {
   // get the auth token from the session storage
   let authToken = $sessionStorageProvider.get('authToken');

   // does the auth token actually exist?
   if (authToken) {
      // add the token to all HTTP requests
      $httpProvider.defaults.headers.common.Authorization = 'Basic ' + authToken;
   }
});

module.factory('productAPI', function ($resource) {
    return $resource('/api/products/:id');
});

module.factory('categoryAPI', function ($resource) {
    return $resource('/api/categories/:cat');
});

module.factory('saveAPI', function ($resource) {
   return $resource('/api/sales/'); 
});

module.factory('cart', function ($sessionStorage) {
    let cart = new ShoppingCart();

    if ($sessionStorage.cart) {

        cart.reconstruct($sessionStorage.cart);
    }

    return cart;
});

module.controller('CartController', function(cart, $sessionStorage, $window, saveAPI){
    this.items = cart.getItems();
    this.total = cart.getTotal();
    
    this.buyButton = function(product){
        $sessionStorage.selectedProduct = product;
        $window.location.href = 'buy.html';
    };
    
    this.selectedProduct = $sessionStorage.selectedProduct;
    
    this.addToCart = function(quantity){
        //if stock > 0 then :
        let product = $sessionStorage.selectedProduct; //get selected product
        let productToAdd = new SaleItem(product, quantity); //create instance of saleitem to initialise the object
        cart.addItem(productToAdd); //adds item on the injected cart object
        $sessionStorage.cart = cart; //stores cart object in the session
        console.log(productToAdd);
        $window.location.href = "cart.html"; //redirects
    };
    
    this.checkOut = function(){
        cart.setCustomer($sessionStorage.customer);
        saveAPI.save(cart);
        delete $sessionStorage.cart;
        $window.location.href = "thanks.html";
    };
    
});



module.controller('ProductController', function (productAPI, categoryAPI) {
    // load the products
    this.products = productAPI.query();
    // load the categories
    this.categories = categoryAPI.query();
    
    // click handler for the category filter buttons
    this.selectCategory = function (selectedCat) {
       this.products = categoryAPI.query({"cat": selectedCat});
    };
    
    this.selectAll = function(){
        this.products = productAPI.query();
    };
});

module.factory('registerAPI', function ($resource) {
    return $resource('/api/register');
});

module.factory('signInAPI', function ($resource) {
   return $resource('/api/customers/:username');
});

module.controller('CustomerController', function (registerAPI, $window, signInAPI, $sessionStorage, $http) {
    this.signInMessage = "Please sign in to continue.";
    this.registerCustomer = function (customer) {
    registerAPI.save(null, customer,

        // success callback
        function () {
           $window.location = 'signin.html';
        },

        // error callback
        function (error) {
           console.log(error);
        }
    );
    };
    
    // alias 'this' so that we can access it inside callback functions
    let ctrl = this;

    this.signIn = function (username, password) {
        // generate authentication token
        let authToken = $window.btoa(username + ":" + password);

        // store token
        $sessionStorage.authToken = authToken;

        // add token to the HTTP request headers
        $http.defaults.headers.common.Authorization = 'Basic ' + authToken;
        
        
       // get customer from web service
       signInAPI.get({'username': username},
          // success callback
          function (customer) {
             // also store the retrieved customer
             $sessionStorage.customer = customer;

             // redirect to home
             $window.location = '.';
          },
          // fail callback
          function () {
             ctrl.signInMessage = 'Sign in failed. Please try again.';
          }
       );
    };
    
    this.checkSignIn = function () {
        if ($sessionStorage.customer) {
           this.signedIn = true;
           this.welcome = "Welcome " + $sessionStorage.customer.firstName;
         } else {
           this.signedIn = false;
         }
    };
    
    
    //logout not currently working
    this.signOut = function () {
        $sessionStorage.$reset();
        this.signedIn = false;
        $window.location.href = "index.html"; //index or signin page better? 
    };
    
});

