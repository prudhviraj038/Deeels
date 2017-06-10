package com.mamacgroup.deeels;

import java.io.Serializable;

/**
 * Created by Chinni on 12-05-2016.
 */
public class CartItem implements Serializable {
    Products products;
    String qty;
    CartItem(Products products,String qty){
        this.products=products;
        this.qty=qty;
    }

}
