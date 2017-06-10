package com.mamacgroup.deeels;


import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.astuetz.PagerSlidingTabStrip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Chinni on 04-05-2016.
 */
public class CategorySlidingFragment extends Fragment {
    TabLayout pagerSlidingTabStrip;
    ViewPager viewPager;
    int cat=0;
    ProgressBar progressBar;
    Map<Integer,ProductListFragment> frags;
    MyPagerAdapter adapter;
    ArrayList<Category> categories = new ArrayList<>();
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
//        public void five_items();
        public  void five_items_com_list();
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
        View rootview = inflater.inflate(R.layout.product_sliding_fragment, container, false);
        return rootview;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
        mCallBack.five_items_com_list();
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar3);
        getProducts();
        pagerSlidingTabStrip = (TabLayout) view.findViewById(R.id.view35);
        pagerSlidingTabStrip.setTabMode(TabLayout.MODE_SCROLLABLE);
        pagerSlidingTabStrip.setSelectedTabIndicatorColor(Color.parseColor("#83AD3C"));
        pagerSlidingTabStrip.setSelectedTabIndicatorHeight(7);
        pagerSlidingTabStrip.setBackgroundColor(Color.parseColor("#f8f8f8"));
        pagerSlidingTabStrip.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#83AD3C"));
//        pagerSlidingTabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.view3);
//        pagerSlidingTabStrip.setTextColor(Color.parseColor("#000000"));
//        pagerSlidingTabStrip.setIndicatorHeight(7);
//        pagerSlidingTabStrip.setDividerColorResource(R.color.aa_menu_text_selected);
//        pagerSlidingTabStrip.setBackgroundColor(Color.parseColor("#f8f8f8"));
//        pagerSlidingTabStrip.setIndicatorColorResource(R.color.aa_app_green);
        viewPager = (ViewPager) view.findViewById(R.id.view4);




    }
//    public  void filter(){
//        Log.e("current item",String.valueOf(viewPager.getCurrentItem()));
//        Log.e("adapter",String.valueOf(adapter.getCount()));
//        adapter.getthisfrag(viewPager.getCurrentItem()).filter();
//    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
//        private final Map<Integer,ProductListFragment>

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position==0)
                return Settings.getword(getActivity(),"home");
            else
            return categories.get(position-1).getTitle(getActivity());
        }

        @Override
        public int getCount() {
            return categories.size()+1;
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0){
                return new HomeProductListFragment();
            }else {
                if (frags == null) {
                    frags = new Hashtable<>();
                }
                frags.put(position-1, ProductListFragment.newInstance(String.valueOf(position-1), categories.get(position-1).productses));
                //frags.put(position,new TestFragment());
                return frags.get(position-1);
            }
        }

        public ProductListFragment getthisfrag(int pos){

            return    frags.get(pos);
        }

    }
    private void getProducts(){
        String url;
        categories.clear();
        url = Settings.SERVERURL + "products.php";
        Log.e("url", url);
        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jsonArray) {
                progressBar.setVisibility(View.GONE);
                categories.clear();
                try {
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject tmp_json = jsonArray.getJSONObject(i);
                        Category temp = new Category(tmp_json);
                        categories.add(temp);
                    }
                    adapter = new MyPagerAdapter(getChildFragmentManager());
                    viewPager.setAdapter(adapter);
                    pagerSlidingTabStrip.setupWithViewPager(viewPager);
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
