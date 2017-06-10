package com.mamacgroup.deeels;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class ProductDetailsFragment extends Fragment implements  GoogleMap.OnMarkerClickListener {
    TextView title,price,time,details_tv,des_tv,spec_tv,notes_tv,warrenty_tv,sta_price,sta_time,buy_now_tv,det_title,spcification_title,
            warranty_title,notes_title;
    LinearLayout buy_ll;
    CategoryAdapter categoryAdapter;
    MapView mMapView;
    private GoogleMap googleMap;
    Products products;
    private SliderLayout mDemoSlider;
    FragmentTouchListner mCallBack;
    long millis;
    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    public interface FragmentTouchListner {
            public  void  to_cart(Products products,String qty);
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
        View rootview = inflater.inflate(R.layout.product_details_screen , container, false);
        return rootview;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getView();
        products=(Products)getArguments().getSerializable("products");

        sta_price=(TextView) v.findViewById(R.id.pd_price_sta_tv);
        sta_price.setText(Settings.getword(getActivity(),"price"));
        sta_time=(TextView) v.findViewById(R.id.pd_time_left_sta_tv);
        sta_time.setText(Settings.getword(getActivity(), "time_left"));
        buy_now_tv=(TextView) v.findViewById(R.id.pd_buynow_sta_tv);
        buy_now_tv.setText(Settings.getword(getActivity(),"buy_now"));
        det_title=(TextView) v.findViewById(R.id.pd_det_des_tv_sta);
        det_title.setText(Settings.getword(getActivity(), "details_description"));
        spcification_title=(TextView) v.findViewById(R.id.pd_sepci_tv_sta);
        spcification_title.setText(Settings.getword(getActivity(), "specifications"));
        notes_title=(TextView) v.findViewById(R.id.pd_notes_tv_sta);
        notes_title.setText(Settings.getword(getActivity(),"notes"));
        warranty_title=(TextView) v.findViewById(R.id.pd_warranty_tv_sta);
        warranty_title.setText(Settings.getword(getActivity(), "warranty"));

        title=(TextView)v.findViewById(R.id.title_pd_tv);
        price=(TextView)v.findViewById(R.id.price_pd_tv);
        time=(TextView)v.findViewById(R.id.time_pd_tv);
        details_tv=(TextView)v.findViewById(R.id.details_tv);
        des_tv=(TextView)v.findViewById(R.id.des_tv );
        spec_tv=(TextView)v.findViewById(R.id.specification_tv);
        notes_tv=(TextView)v.findViewById(R.id.notes_tv);
        warrenty_tv=(TextView)v.findViewById(R.id.warrenty_tv);
        buy_ll=(LinearLayout)v.findViewById(R.id.buy_now_pd_ll);
        millis = Integer.valueOf(products.time_diff)*1000;
        set_refresh_timer();
        title.setText(products.getTitle(getActivity()));
        price.setText(products.price);
//        time.setText(products.time_diff);
        details_tv.setText(Html.fromHtml(products.get_det(getActivity())));
        des_tv.setText(Html.fromHtml(products.get_s_des(getActivity())));
        spec_tv.setText(Html.fromHtml(products.get_spec(getActivity())));
        notes_tv.setText(Html.fromHtml(products.get_note(getActivity())));
        warrenty_tv.setText(Html.fromHtml(products.get_war(getActivity())));

        mDemoSlider = (SliderLayout)v.findViewById(R.id.product_background_image);
//        if(products.images.size()==1) {
//            Picasso.with(getActivity()).load(products.images.get(0).img).into(pro_img);
//        }else {
            for (int i = 0; i < products.images.size(); i++) {
                DefaultSliderView defaultSliderView = new DefaultSliderView(getActivity());
                defaultSliderView.image(products.images.get(i).img).setScaleType(BaseSliderView.ScaleType.CenterCrop);
                mDemoSlider.addSlider(defaultSliderView);
//            }
        }
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        googleMap = mMapView.getMap();
        googleMap.setOnMarkerClickListener(this);
        double latitude = 16.5217704;
        double longitude = 80.6888339;
        try {
            latitude = Double.parseDouble(products.latitude);
            longitude = Double.parseDouble(products.longitude);
        } catch (Exception ex) {
//                latitude = 16.5217704;
//                longitude = 80.6888339;
            mMapView.setVisibility(View.GONE);
        }

        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(latitude, longitude)).title(products.getTitle(getActivity()));
        // Changing marker icon
        marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE)).snippet(products.getTitle(getActivity()));
        // adding marker
        googleMap.clear();
        googleMap.addMarker(marker);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude)).zoom(10).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
        buy_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(millis>0){
                    Log.e("mili",String.valueOf(millis));
                    mCallBack.to_cart(products,"1");
                }else{
                    Toast.makeText(getActivity(),"this product Expired",Toast.LENGTH_SHORT).show();
                }

            }
        });

     }
    final Handler h = new Handler();
    final int delay = 1000*1; //milliseconds
    final Runnable r = new Runnable() {
        @Override
        public void run() {
            Log.e("time", "ticked");
            h.postDelayed(this, delay);
//                products.time_diff=String.valueOf(Integer.valueOf(products.time_diff)-1);
            if (Integer.valueOf( products.time_diff)> 0) {
                int seconds = (int) (Integer.valueOf(products.time_diff));
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
            // tabclicked(selected,true);
        }
    };
    private void set_refresh_timer(){

        h.postDelayed(r, delay);
    }
}

