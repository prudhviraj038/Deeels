package com.mamacgroup.deeels;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import me.relex.circleindicator.CircleIndicator;


public class HomeProductListFragment extends Fragment {
    ListView lv;
    TextView title,no_res,price,time,des,type,no_bann_tv,sta_price,sta_time;
    LinearLayout buy_ll,ban_ll;
    CategoryAdapter1 categoryAdapter;
    FragmentTouchListner mCallBack;
    String pos;
    ProgressBar progressBar;
    ArrayList<Products> productses;
    ArrayList<Products> product_banners;
    private static ViewPager mPager;
    ArrayList<String> galleryImages;
    CircleIndicator indicator;
    ViewGroup myHeader;
    long millis;
    int temp=0;

    SlidingImageAdapter slidingImageAdapter;
    public interface FragmentTouchListner {
        public void to_pd(Products products);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallBack = (NavigationActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement Listner");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.home_productlist_screen , container, false);
        return rootview;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getView();
//        title=(TextView)v.findViewById(R.id.cat_title);
        productses=new ArrayList<>();
        product_banners=new ArrayList<>();
        galleryImages=new ArrayList<>();
        progressBar = (ProgressBar)v.findViewById(R.id.progressBar2);
        lv=(ListView)v.findViewById(R.id.hpl_lv);
        LayoutInflater myinflater = getLayoutInflater(savedInstanceState);
        myHeader = (ViewGroup)myinflater.inflate(R.layout.list_header, lv, false);
        lv.addHeaderView(myHeader, null, false);
        sta_price=(TextView) v.findViewById(R.id.hs_price_tv);
        sta_price.setText(Settings.getword(getActivity(),"price"));
        sta_time=(TextView) v.findViewById(R.id.hs_time_left_tv);
        sta_time.setText(Settings.getword(getActivity(), "time_left"));
        no_bann_tv=(TextView)v.findViewById(R.id.no_banner_products);
        no_bann_tv.setText(Settings.getword(getActivity(), "no_products"));
        getProductsBanners();
        no_res=(TextView)v.findViewById(R.id.no_products_home);
        no_res.setText(Settings.getword(getActivity(), "no_products"));
        title=(TextView)v.findViewById(R.id.p_title_hpl);
        des=(TextView)v.findViewById(R.id.hpl_p_des);
        price=(TextView)v.findViewById(R.id.price_tv_hpl);
        time=(TextView)v.findViewById(R.id.time_left_tv);
        type = (TextView) v.findViewById(R.id.item_type_h_tv);
        buy_ll=(LinearLayout)v.findViewById(R.id.buy_now_ll_hpl);
        ban_ll=(LinearLayout)v.findViewById(R.id.bann_ll_home);
        getHomeProducts();
        categoryAdapter=new CategoryAdapter1(getActivity(),productses);
        lv.setAdapter(categoryAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallBack.to_pd(productses.get(position - 1));
            }
        });
        buy_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.to_pd(product_banners.get(temp));
            }
        });

        indicator = (CircleIndicator)v.findViewById(R.id.indicator);
        mPager = (ViewPager)v.findViewById(R.id.view);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                title.setText(String.valueOf(position));

            }

            @Override
            public void onPageSelected(int position) {
                temp = position;
                title.setText(product_banners.get(position).getTitle(getActivity()));
                price.setText(product_banners.get(position).price);
                type.setText(product_banners.get(position).type);
                millis = Integer.valueOf(product_banners.get(position).time_diff) * 1000;
                des.setText(product_banners.get(position).get_s_des(getActivity()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        set_refresh_timer_hp();
     }
    final Handler h = new Handler();
    final int delay = 1000*1; //milliseconds
    final Runnable r = new Runnable() {
        @Override
        public void run() {
            Log.e("time", "ticked");
            h.postDelayed(this, delay);
            for(int i=0;i<product_banners.size();i++){
                product_banners.get(i).time_diff=String.valueOf(Integer.valueOf(product_banners.get(i).time_diff)-1);
            }
            try{
            if (Integer.valueOf(product_banners.get(temp).time_diff)> 0) {
                int seconds = (int) (Integer.valueOf(product_banners.get(temp).time_diff));
                int day=seconds/86400;
                int dey_rem=seconds%86400;
                int hr = dey_rem/3600;
                int rem = dey_rem%3600;
                int mn = rem/60;
                int sec = rem%60;
                String hrStr = (hr<10 ? "0" : "")+hr;
                String mnStr = (mn<10 ? "0" : "")+mn;
                String secStr = (sec<10 ? "0" : "")+sec;
                time.setText(day+"d:"+hrStr+"h:"+ mnStr+"m:"+secStr+ "s");
            } else {
                time.setText("Expired!!");
            }
            }catch (Exception e) {
                e.printStackTrace();
            }
            // tabclicked(selected,true);
        }
    };
    private void set_refresh_timer(){

        h.postDelayed(r, delay);
    }
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            for(int i=0;i<productses.size();i++){
                productses.get(i).time_diff=String.valueOf(Integer.valueOf(productses.get(i).time_diff)-1);
            }
            categoryAdapter.notifyDataSetChanged();
            timerHandler.postDelayed(this, 1000); //run every minute
        }
    };
    private void set_refresh_timer_hp(){

        timerHandler.postDelayed(timerRunnable, 1000);
    }
    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }
    }
    private void getProductsBanners(){
        String url;
        product_banners.clear();
        progressBar.setVisibility(View.VISIBLE);
        url = Settings.SERVERURL + "home_banners.php";
        Log.e("url", url);
        final JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                progressBar.setVisibility(View.GONE);
                try {
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject tmp_json = jsonArray.getJSONObject(i);
                        Products temp = new Products(tmp_json);
                        product_banners.add(temp);
                        galleryImages.add(product_banners.get(i).banner_image);
                    }

//        mPager.setPageMargin(25);
//        mPager.setClipToPadding(false);
//        mPager.setPadding(100, 0, 100, 0);
                    if(product_banners.size()!=0) {
                        no_bann_tv.setVisibility(View.GONE);
                        ban_ll.setVisibility(View.VISIBLE);
                        Log.e("banner_img", String.valueOf(galleryImages.size()));
                        Log.e("banner_price", product_banners.get(0).price);
                        slidingImageAdapter = new SlidingImageAdapter(getActivity(), galleryImages);
                        mPager.setAdapter(slidingImageAdapter);
                        indicator.setViewPager(mPager);
                        slidingImageAdapter.registerDataSetObserver(indicator.getDataSetObserver());
                        title.setText(product_banners.get(0).getTitle(getActivity()));
                        Log.e("banner_price", product_banners.get(0).price);
                        price.setText(product_banners.get(0).price);
                        type.setText(product_banners.get(0).type);
                        Log.e("banner_price", product_banners.get(0).time_diff);
                        millis = Integer.valueOf(product_banners.get(0).time_diff)*1000;
                        des.setText(product_banners.get(0).get_s_des(getActivity()));
                        set_refresh_timer();
                    }else{
                        no_bann_tv.setVisibility(View.VISIBLE);
                        ban_ll.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("response is:", error.toString());
                progressBar.setVisibility(View.GONE);

            }
        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }
    private void getHomeProducts(){
        String url;
        productses.clear();
        url = Settings.SERVERURL + "home_products.php";
        progressBar.setVisibility(View.VISIBLE);
        Log.e("url", url);
        final JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jsonArray) {
                progressBar.setVisibility(View.GONE);
                try {
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject tmp_json = jsonArray.getJSONObject(i);
                        Products temp = new Products(tmp_json);
                        productses.add(temp);
                    }
                    if(productses.size()==0){
                        no_res.setVisibility(View.VISIBLE);
                        no_res.setText(Settings.getword(getActivity(), "no_products"));
                    }else {
                        categoryAdapter = new CategoryAdapter1(getActivity(), productses);
                        lv.setAdapter(categoryAdapter);
                        categoryAdapter.notifyDataSetChanged();
                        setListViewHeightBasedOnItems(lv);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("response is:", error.toString());
                progressBar.setVisibility(View.GONE);

            }
        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }
}

