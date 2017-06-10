package com.mamacgroup.deeels;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ProductListFragment extends Fragment {
    ListView lv;
    TextView title,no_res;
    LinearLayout check;
    CategoryAdapter1 categoryAdapter;
    FragmentTouchListner mCallBack;
    String pos;
    ProgressBar progressBar;
    ArrayList<Products> productses;
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
        View rootview = inflater.inflate(R.layout.product_list_screen , container, false);
        return rootview;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getView();
//        title=(TextView)v.findViewById(R.id.cat_title);
        productses=new ArrayList<>();
        pos=(String)getArguments().getSerializable("position");
        productses=(ArrayList)getArguments().getSerializable("productses");
        no_res=(TextView)v.findViewById(R.id.no_res);
        if(productses.size()==0){
            no_res.setVisibility(View.VISIBLE);
            no_res.setText(Settings.getword(getActivity(), "no_products"));
        }else{
            no_res.setVisibility(View.GONE);
        }
        progressBar = (ProgressBar)v.findViewById(R.id.progressBar);
        lv=(ListView)v.findViewById(R.id.product_list_lv);
        categoryAdapter=new CategoryAdapter1(getActivity(),productses);
        lv.setAdapter(categoryAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallBack.to_pd(productses.get(position));
            }
        });
        set_refresh_timer();
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
    private void set_refresh_timer(){

        timerHandler.postDelayed(timerRunnable, 1000);
    }
    public static ProductListFragment newInstance(String s,ArrayList<Products> productses) {
        ProductListFragment companyListFragment = new ProductListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("position", s);
        bundle.putSerializable("productses", productses);
        companyListFragment.setArguments(bundle);
        return companyListFragment;
    }

}

