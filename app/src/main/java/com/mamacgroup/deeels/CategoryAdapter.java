package com.mamacgroup.deeels;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class CategoryAdapter extends BaseAdapter{
    Context context;
    ArrayList<Products> productses;
//    TextView time;
    int temp;
    long millis;
    private static LayoutInflater inflater=null;
    private List<ViewHolder> lstHolders;
    private Handler mHandler = new Handler();
    private Runnable updateRemainingTimeRunnable = new Runnable() {
        @Override
        public void run() {
            synchronized (lstHolders) {
                for (ViewHolder holder : lstHolders) {
                   holder.updateTimeRemaining();
                }
            }
        }
    };
    public CategoryAdapter(Activity mainActivity, ArrayList<Products> productses) {
        // TODO Auto-generated constructor stubcontext=mainActivity;
        this.context = mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.productses = productses;
//        this.clientsListFragment = clientsListFragment;
        lstHolders = new ArrayList<>();
        startUpdateTimer();
    }
    private void startUpdateTimer() {
        Timer tmr = new Timer();
        tmr.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(updateRemainingTimeRunnable);
            }
        },1000);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return productses.size();
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


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder=null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.cat_item, null);
            holder.sta_price=(TextView) convertView.findViewById(R.id.sta_price_adp);
            holder.sta_price.setText(Settings.getword(context,"price"));
            holder.sta_time=(TextView) convertView.findViewById(R.id.sta_time_left_adp);
            holder.sta_time.setText(Settings.getword(context,"time_left"));
            holder.title = (TextView) convertView.findViewById(R.id.p_title_adp);
            holder.des = (TextView) convertView.findViewById(R.id.adp_p_des);
            holder.price = (TextView) convertView.findViewById(R.id.p_price_adp);
            holder.time = (TextView) convertView.findViewById(R.id.adp_time_left_tv);
            holder.type = (TextView) convertView.findViewById(R.id.item_type_tv);
            holder.img = (ImageView) convertView.findViewById(R.id.adp_img_pro_img);

//        holder.time.setText(productses.get(position).time_diff);

//        long outputTime = Math.abs(System.currentTimeMillis()-Integer.valueOf(productses.get(position).time_diff));
//            outputTime=outputTime-1000;
//        Date date = new java.util.Date(outputTime);
//        String result = new SimpleDateFormat("hh:mm:ss").format(date);

//            temp = temp - 1000;
//            productses.get(position).time_diff = String.valueOf(temp);
//            holder.time.setText(String.valueOf(temp / 3600000 + ":" + temp / 60000 + ":" + temp / 1000));

            convertView.setTag(holder);
            synchronized (lstHolders) {
                lstHolders.add(holder);
            }
        }
            else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.setData(productses.get(position));
        return convertView;

    }

    private class ViewHolder {
        TextView title,price,time,des,type,sta_price,sta_time;
        ImageView web,app,play,img;
        Products products;
        public void setData(Products item) {
            products = item;
            des.setText(products.get_s_des(context));
            title.setText(products.getTitle(context));
            type.setText(products.type);
            price.setText(products.price + " KD");
            Picasso.with(context).load(products.images.get(0).img).into(img);
            updateTimeRemaining();
        }

        public void updateTimeRemaining() {
            if (Integer.valueOf(products.time_diff)> 0) {
                int seconds = (int) (Integer.valueOf(products.time_diff) / 1000);
                int hr = seconds/3600;
                int rem = seconds%3600;
                int mn = rem/60;
                int sec = rem%60;
                String hrStr = (hr<10 ? "0" : "")+hr;
                String mnStr = (mn<10 ? "0" : "")+mn;
                String secStr = (sec<10 ? "0" : "")+sec;
                products.time_diff=String.valueOf(Integer.valueOf(products.time_diff)-1000);
                   time.setText(hrStr+"h:"+ mnStr+"m:"+secStr+ "s");
            } else {
                time.setText("Expired!!");
            }
        }
    }
}