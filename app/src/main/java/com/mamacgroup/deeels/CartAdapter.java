package com.mamacgroup.deeels;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class CartAdapter extends BaseAdapter{
    float total;
    Context context;
    ArrayList<CartItem> cart_items;
    String temp="";
    String temp1="";
    String temp2="";
    HashMap<Integer,Integer> number;

    CartFragment cartFragment;
    private static LayoutInflater inflater=null;
    public CartAdapter(Activity mainActivity, ArrayList<CartItem> cart_items, CartFragment cartFragment) {
        // TODO Auto-generated constructor stubcontext=mainActivity;
        this.context = mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.cart_items = cart_items;
        this.cartFragment=cartFragment;
        number = new HashMap<>();
//        this.clientsListFragment = clientsListFragment;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return cart_items.size();
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
        TextView title,price,qty,opt,type,total_price,sta_price,sta_type,sta_qty,sta_remove,sta_stotal;
        ImageView img;
        LinearLayout delete,minus,plus;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.cart_item, null);
        holder.sta_price=(TextView) rowView.findViewById(R.id.sta_cart_adp_price);
        holder.sta_price.setText(Settings.getword(context,"price"));
        holder.sta_type=(TextView) rowView.findViewById(R.id.sta_cart_adp_type);
        holder.sta_type.setText(Settings.getword(context,"type"));
        holder.sta_qty=(TextView) rowView.findViewById(R.id.sta_cart_adp_qty);
        holder.sta_qty.setText(Settings.getword(context,"quantity"));
        holder.sta_remove=(TextView) rowView.findViewById(R.id.sta_cart_adp_remove);
        holder.sta_remove.setText(Settings.getword(context,"remove_item"));
        holder.sta_stotal=(TextView) rowView.findViewById(R.id.sta_cart_adp_stotal);
        holder.sta_stotal.setText(Settings.getword(context,"sub_total"));

        holder.title=(TextView) rowView.findViewById(R.id.cart_item_pro_title);
        holder.title.setText(cart_items.get(position).products.getTitle(context));
        holder.total_price=(TextView) rowView.findViewById(R.id.sub_total_cart_price);
        holder.total_price.setText(String.valueOf(Float.parseFloat(cart_items.get(position).qty) * Float.parseFloat(cart_items.get(position).products.cart_price))+" KD");
        holder.price=(TextView) rowView.findViewById(R.id.cart_item_pro_price);
        holder.price.setText(cart_items.get(position).products.price+" KD");
        holder.qty=(TextView) rowView.findViewById(R.id.qty_tv_cart_adp);
        holder.qty.setText(cart_items.get(position).qty);
        holder.type=(TextView) rowView.findViewById(R.id.type_cart_adp);
        holder.type.setText(cart_items.get(position).products.type);
        holder.img=(ImageView) rowView.findViewById(R.id.p_img_cart_item);
        Picasso.with(context).load(cart_items.get(position).products.images.get(0).img).into(holder.img);
        holder.minus=(LinearLayout) rowView.findViewById(R.id.minus_ll);
        holder.plus=(LinearLayout) rowView.findViewById(R.id.plus_ll);
        holder.delete=(LinearLayout) rowView.findViewById(R.id.remove_item_ll);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart_items.remove(position);
                if(cart_items.size()==0)
                    cartFragment.total_amount.setText(String.format("%.3f",0.0)+" KD");
                notifyDataSetChanged();
            }
        });
        number.put(position,Integer.parseInt(cart_items.get(position).qty));
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if((Integer.parseInt(cart_items.get(position).products.qut)-1)<get_cart_count(cart_items.get(position).products.res_id)){
//                    alert.showAlertDialog(context, "Info",Settings.getword(context,"out_stock")+" select "+cart_items.get(position).products.qut+" products", true);
//                }else {
                number.put(position,number.get(position)+1);
                holder.qty.setText(" " + String.valueOf(number.get(position)));
                cart_items.get(position).qty = String.valueOf(number.get(position));
                notifyDataSetChanged();
//                }
            }
        });
        holder. minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number.get(position) > 1) {
                    number.put(position, number.get(position) - 1);
                    holder.qty.setText(" " + String.valueOf(number.get(position)));
                    cart_items.get(position).qty = String.valueOf(number.get(position));
                    notifyDataSetChanged();
                }
            }
        });
        total = number.get(position) * Float.parseFloat(cart_items.get(position).products.cart_price);
        holder.total_price.setText(" KD " + String.format("%.2f", total));
        getsum();
        return rowView;

    }
    public void getsum(){
        float temp=0;
        for(int i=0;i<cart_items.size();i++){
            temp=temp+Float.parseFloat(cart_items.get(i).qty)*Float.parseFloat(cart_items.get(i).products.cart_price);
            cartFragment.total=temp;
            cartFragment.total_amount.setText(String.format("%.2f",temp)+" KD");

        }}

}