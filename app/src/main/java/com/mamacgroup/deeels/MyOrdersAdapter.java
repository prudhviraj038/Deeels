package com.mamacgroup.deeels;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MyOrdersAdapter extends BaseAdapter{
    Context context;
    private static LayoutInflater inflater=null;
    ArrayList<Orders> orderses;
    public MyOrdersAdapter(Activity mainActivity, ArrayList<Orders> orderses) {
        // TODO Auto-generated constructor stubcontext=mainActivity;
        this.context = mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.orderses = orderses;
//        context.clientsListFragment = clientsListFragment;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return orderses.size();
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
        TextView o_id,o_date,price,p_sta,d_sta,sta_o_id,sta_o_date,sta_o_pay_sta,sta_deli_sta,sta_total_price;
        ImageView app,play,img;
        RelativeLayout v1,v2;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.my_orderss__item, null);

        holder.sta_o_id=(TextView)rowView.findViewById(R.id.sta_or_a_id);
        holder.sta_o_id.setText(Settings.getword(context, "order_id"));

        holder.sta_o_date=(TextView)rowView.findViewById(R.id.sta_or_a_date);
        holder.sta_o_date.setText(Settings.getword(context, "order_date"));
        holder.sta_o_pay_sta=(TextView)rowView.findViewById(R.id.sta_or_a_pay_sta);
        holder.sta_o_pay_sta.setText(Settings.getword(context, "pay_status"));
//        holder.sta_pay_met=(TextView)rowView.findViewById(R.id.sta_or_pay_met);
//        holder.sta_pay_met.setText(Settings.getword(context, "pay_method"));
        holder.sta_deli_sta=(TextView)rowView.findViewById(R.id.sta_or_a_del_sta);
        holder.sta_deli_sta.setText(Settings.getword(context, "delivery_status"));
//        holder.sta_dis_amt=(TextView)rowView.findViewById(R.id.sta_or_dis_amt_tv);
//        holder.sta_dis_amt.setText(Settings.getword(context, "discount_amt"));
        holder.sta_total_price=(TextView)rowView.findViewById(R.id.sta_or_tot_a_price_tv);
        holder.sta_total_price.setText(Settings.getword(context, "total_price"));

        holder.o_id=(TextView) rowView.findViewById(R.id.my_order_id_adp);
        holder.o_date=(TextView) rowView.findViewById(R.id.my_order_date_adp);
        holder.price=(TextView) rowView.findViewById(R.id.my_order_price_adp);
        holder.p_sta=(TextView) rowView.findViewById(R.id.my_order_ps_adp);
        holder.d_sta=(TextView) rowView.findViewById(R.id.my_order_ds_adp);
        holder.o_id.setText(orderses.get(position).id);
        holder.o_date.setText(orderses.get(position).date);
        holder.price.setText(orderses.get(position).price);
        holder.p_sta.setText(orderses.get(position).payment_status);
        holder.d_sta.setText(orderses.get(position).delivery_status);
        return rowView;
        
    }

}