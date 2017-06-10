package com.mamacgroup.deeels;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by Chinni on 04-05-2016.
 */
public class CartFragment extends Fragment {
    TextView total_amount,cs,checkout,sta_gt_tv;
    ListView cart_list;
    ArrayList<CartItem> cart_items;
    LinearLayout cs_ll,checkout_ll;
    Float total = 0f;
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public  void con_shopping();
        public  void to_checkout(ArrayList<CartItem> cartItem);
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
        View rootview = inflater.inflate(R.layout.cart_fragment, container, false);
        return rootview;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getView();
        cart_items=new ArrayList<>();
        cart_items=(ArrayList)getArguments().getSerializable("cart_items");
        cart_list=(ListView)v.findViewById(R.id.cart_listview);
        LayoutInflater myinflater = getLayoutInflater(savedInstanceState);
        ViewGroup myHeader = (ViewGroup)myinflater.inflate(R.layout.cart_footer, cart_list, false);
        cart_list.addFooterView(myHeader, null, false);
        cs=(TextView)v.findViewById(R.id.cs_cart_tv);
        cs.setText(Settings.getword(getActivity(),"continue_shopping"));
        checkout=(TextView)v.findViewById(R.id.checkout_cart_tv);
        checkout.setText(Settings.getword(getActivity(),"checkout"));
        sta_gt_tv=(TextView)v.findViewById(R.id.g_tot_sta_tv);
        sta_gt_tv.setText(Settings.getword(getActivity(),"grand_total"));
        total_amount=(TextView)v.findViewById(R.id.gt_cart_tv);
        cs_ll=(LinearLayout)v.findViewById(R.id.cs_cart_ll);
        checkout_ll=(LinearLayout)v.findViewById(R.id.checkout_cart_ll);

        for(int i=0;i<cart_items.size();i++){
            Float quty=0f;
            Float price=0f;
            quty=Float.parseFloat(cart_items.get(i).qty);
            price=Float.parseFloat(cart_items.get(i).products.cart_price);
            total=total+(quty*price);
        }
        total_amount.setText(String.format("%.2f", total) + " KD");
        CartAdapter cartPageAdapter=new CartAdapter(getActivity(),cart_items,this);
        cart_list.setAdapter(cartPageAdapter);
        checkout_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cart_items.size() == 0) {
                    Toast.makeText(getActivity(), Settings.getword(getActivity(), "no_items_in_cart"), Toast.LENGTH_SHORT).show();
                } else {
                    mCallBack.to_checkout(cart_items);
                }
            }
        });
        cs_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.con_shopping();
            }
        });
    }
}