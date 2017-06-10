package com.mamacgroup.deeels;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class CheckoutAdapter extends BaseAdapter{
    String [] result;
    Context context;
    int [] imageId;
//    Restaurants restaurants;
    ArrayList<CartItem> cartItems;
    private static LayoutInflater inflater=null;
    public CheckoutAdapter(Context mainActivity, ArrayList<CartItem> cartItems) {
        // TODO Auto-generated constructor stub
        //  result=prgmNameList;
        context=mainActivity;
        this.cartItems=cartItems;
        //  imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return cartItems.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView name,price,qty;
        LinearLayout rating;
      }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.checkout_item, null);
        holder.name=(TextView) rowView.findViewById(R.id.ck_adp_pr_title);
        holder.price=(TextView) rowView.findViewById(R.id.ck_adp_price);
        holder.qty=(TextView) rowView.findViewById(R.id.ck_adp_qty);
        holder.name.setText(cartItems.get(position).products.getTitle(context));
        holder.price.setText(cartItems.get(position).products.cart_price+" KD");
        holder.qty.setText(cartItems.get(position).qty);
        return rowView;
    }
}