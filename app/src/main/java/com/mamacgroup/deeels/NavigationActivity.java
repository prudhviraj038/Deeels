package com.mamacgroup.deeels;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class NavigationActivity extends FragmentActivity implements ProductListFragment.FragmentTouchListner,
        ProductDetailsFragment.FragmentTouchListner, CategorySlidingFragment.FragmentTouchListner, HomeProductListFragment.FragmentTouchListner,
        CartFragment.FragmentTouchListner, CheckoutFragment.FragmentTouchListner, MyOrdersFragment.FragmentTouchListner,
        InvoiceFragment.FragmentTouchListner, WhatDeeelFragment.FragmentTouchListner, ReturnPolicyFragment.FragmentTouchListner, PrivacyPolicyFragment.FragmentTouchListner, ContactUsFragment.FragmentTouchListner {
    private DrawerLayout mDrawerLayout;
    ImageView menu;
    FragmentManager fragmentManager;
    TextView header;
    FrameLayout container;ArrayList<CartItem> cart_items=new ArrayList<>();
    String type="normal";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Settings.forceRTLIfSupported(this);
        setContentView(R.layout.navigation_screen);
        type=getIntent().getStringExtra("type");
        Log.e("type",type);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);
        header=(TextView)findViewById(R.id.tv_header);
        menu=(ImageView)findViewById(R.id.menu_buttion);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavigationActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
        container = (FrameLayout) findViewById(R.id.container_main);
        if(type.equals("ord")){
            fragmentManager = getSupportFragmentManager();
            MyOrdersFragment fragment = new MyOrdersFragment();
            fragmentManager.beginTransaction().replace(R.id.container_main, fragment).commit();
        }else if(type.equals("wt")) {
            fragmentManager = getSupportFragmentManager();
            WhatDeeelFragment fragment = new WhatDeeelFragment();
            fragmentManager.beginTransaction().replace(R.id.container_main, fragment).commit();
        }else if(type.equals("pp")) {
            fragmentManager = getSupportFragmentManager();
            PrivacyPolicyFragment fragment = new PrivacyPolicyFragment();
            fragmentManager.beginTransaction().replace(R.id.container_main, fragment).commit();
        }else if(type.equals("dr")) {
            fragmentManager = getSupportFragmentManager();
            ReturnPolicyFragment fragment = new ReturnPolicyFragment();
            fragmentManager.beginTransaction().replace(R.id.container_main, fragment).commit();
        }else if(type.equals("con")) {
            fragmentManager = getSupportFragmentManager();
            ContactUsFragment fragment = new ContactUsFragment();
            fragmentManager.beginTransaction().replace(R.id.container_main, fragment).commit();
        }else {
            fragmentManager = getSupportFragmentManager();
            CategorySlidingFragment fragment = new CategorySlidingFragment();
            fragmentManager.beginTransaction().replace(R.id.container_main, fragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void five_items_com_list() {

    }

    @Override
    public void to_pd(Products products) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("products", products);
        FragmentManager fragmentManager1 = getSupportFragmentManager();
        ProductDetailsFragment cartFragment = new ProductDetailsFragment();
        cartFragment.setArguments(bundle);
        fragmentManager1.beginTransaction().add(R.id.container_main, cartFragment).addToBackStack(null).commit();
    }

    @Override
    public void to_cart(Products products, String qty) {
        if(cart_items.size()!=0) {
            int temp=0;
            for (int i = 0; i < cart_items.size(); i++) {
                if (cart_items.get(i).products.id.equals(products.id)) {
                    cart_items.get(i).qty = String.valueOf(Integer.valueOf(cart_items.get(i).qty) + 1);
                    temp=1;
                }
            }
            if(temp==0) {
                CartItem item = new CartItem(products, qty);
                cart_items.add(item);
            }
        }else{
            CartItem item=new CartItem(products,qty);
            cart_items.add(item);
        }
        Bundle bundle=new Bundle();
        bundle.putSerializable("cart_items", cart_items);
        FragmentManager fragmentManager = getSupportFragmentManager();
        CartFragment cartFragment = new CartFragment();
        cartFragment.setArguments(bundle);
        fragmentManager.beginTransaction().add(R.id.container_main, cartFragment).addToBackStack(null).commit();
    }

    @Override
    public void con_shopping() {
        FragmentManager fragmentManager1 = getSupportFragmentManager();
        CategorySlidingFragment cartFragment = new CategorySlidingFragment();
        fragmentManager1.beginTransaction().replace(R.id.container_main, cartFragment).addToBackStack(null).commit();
    }

    @Override
    public void to_checkout(ArrayList<CartItem> cartItem) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("cart_items", cartItem);
        FragmentManager fragmentManager = getSupportFragmentManager();
        CheckoutFragment cartFragment = new CheckoutFragment();
        cartFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.container_main, cartFragment).addToBackStack(null).commit();
    }

    @Override
    public void to_home() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        CategorySlidingFragment fragment = new CategorySlidingFragment();
        fragmentManager.beginTransaction().replace(R.id.container_main, fragment).commit();
    }

    @Override
    public void to_invoice(String id) {
        Bundle bundle=new Bundle();
        Log.e("id1",id);
        bundle.putString("id",id);
        FragmentManager fragmentManager = getSupportFragmentManager();
        InvoiceFragment fragment = new InvoiceFragment();
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.container_main, fragment).commit();
    }

    @Override
    public void back() {
        onBackPressed();
    }
}
