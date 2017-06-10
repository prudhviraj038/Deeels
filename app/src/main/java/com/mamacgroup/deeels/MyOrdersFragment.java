package com.mamacgroup.deeels;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Chinni on 04-05-2016.
 */
public class MyOrdersFragment extends Fragment {
    TextView sta_stotal,stotal,sta_shipping,shipping,sta_gtotl,gtotal,head_pro_det,head_price_det,sta_pro_title,sta_pro_qty,sta_pro_price,
    sta_ord_det,sta_ord_id,sta_ord_date,sta_ord_pay_status,sta_ord_del_status,sta_shipping_add,add1,add2,add3,add4,add5,add6,add7,add8,
            b_add1,b_add2,b_add3,b_add4,b_add5,b_add6,b_add7,b_add8,ord_id,ord_date,ord_pay_status,ord_del_status,dis_amt,pay_type,sta_pay_type,
    sta_billing_address,p_add1,p_add2,p_add3,p_add4,sta_iv_dis_amt,no_ord;
    ViewFlipper vf;
    String pro_type="0";
    LinearLayout shi_add_ll,bill_add_ll,pro_det_ll;
    FragmentTouchListner mCallBack;
    ArrayList<Orders> orderses;
    MyOrdersAdapter myOrdersAdapter;
    ListView lv,p_lv;
    MyOrdersProductsAdapter myordproductAdapter;
    ProgressBar progressBar;
    public interface FragmentTouchListner {
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
        View rootview = inflater.inflate(R.layout.myorders_fragment, container, false);
        return rootview;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getView();
        orderses=new ArrayList<>();
        progressBar=(ProgressBar)v.findViewById(R.id.progressBar4);
        getOrders();
        lv=(ListView)v.findViewById(R.id.my_or_lv);
        p_lv=(ListView)v.findViewById(R.id.my_or_pro_lv);
        vf=(ViewFlipper)v.findViewById(R.id.viewFlipper2);
        no_ord=(TextView)v.findViewById(R.id.no_ord_sta_my_ord);
        no_ord.setText(Settings.getword(getActivity(),"no_orders"));
        sta_gtotl=(TextView)v.findViewById(R.id.sta_my_or_grand_total);
        sta_gtotl.setText(Settings.getword(getActivity(),"grand_total"));
        sta_shipping=(TextView)v.findViewById(R.id.sta_my_or_shipping);
//        sta_shipping.setText(Settings.getword(getActivity(),"sub_total"));
        sta_stotal=(TextView)v.findViewById(R.id.sta_my_or_sub_total);
        sta_stotal.setText(Settings.getword(getActivity(),"sub_total"));
        sta_iv_dis_amt=(TextView)v.findViewById(R.id.sta_my_or_dis_amtt);
        sta_iv_dis_amt.setText(Settings.getword(getActivity(),"discount"));
        head_pro_det=(TextView)v.findViewById(R.id.sta_my_or_prod_det);
        head_pro_det.setText(Settings.getword(getActivity(),"product_details"));
        head_price_det=(TextView)v.findViewById(R.id.sta_my_or_price_det);
        head_price_det.setText(Settings.getword(getActivity(),"price_details"));
        sta_pro_title=(TextView)v.findViewById(R.id.sta_my_or_prod_title);
        sta_pro_title.setText(Settings.getword(getActivity(),"title"));
        sta_pro_qty=(TextView)v.findViewById(R.id.sta_my_or_prod_qty);
        sta_pro_qty.setText(Settings.getword(getActivity(),"qty"));
        sta_pro_price=(TextView)v.findViewById(R.id.sta_my_or_prod_price);
        sta_pro_price.setText(Settings.getword(getActivity(),"price"));
        sta_ord_det=(TextView)v.findViewById(R.id.sta_my_or_ord_title);
        sta_ord_det.setText(Settings.getword(getActivity(),"order_details"));
        sta_ord_id=(TextView)v.findViewById(R.id.sta_my_or_ord_id);
        sta_ord_id.setText(Settings.getword(getActivity(),"order_id"));
        sta_ord_date=(TextView)v.findViewById(R.id.sta_my_or_ord_date);
        sta_ord_date.setText(Settings.getword(getActivity(),"order_date"));
        sta_ord_del_status=(TextView)v.findViewById(R.id.sta_my_or_ord_del_status);
        sta_ord_del_status.setText(Settings.getword(getActivity(),"delivery_status"));
        sta_ord_pay_status=(TextView)v.findViewById(R.id.sta_my_or_ord_pay_status);
        sta_ord_pay_status.setText(Settings.getword(getActivity(),"pay_status"));
        sta_pay_type=(TextView)v.findViewById(R.id.sta_my_or_ord_pay_meth);
        sta_pay_type.setText(Settings.getword(getActivity(),"pay_type"));
        sta_shipping_add=(TextView)v.findViewById(R.id.sta_my_or_shipping_address);
        sta_shipping_add.setText(Settings.getword(getActivity(),"shipping_address"));
        sta_billing_address=(TextView)v.findViewById(R.id.sta_my_or_billing_address);
        sta_billing_address.setText(Settings.getword(getActivity(),"billing_address"));

        shi_add_ll=(LinearLayout)v.findViewById(R.id.shipping_address_ll);
        bill_add_ll=(LinearLayout)v.findViewById(R.id.bill_address_ll);
        pro_det_ll=(LinearLayout)v.findViewById(R.id.my_ord_pro_det_ll);

        stotal=(TextView)v.findViewById(R.id.my_or_sub_total);
        gtotal=(TextView)v.findViewById(R.id.my_or_grand_total);
        shipping=(TextView)v.findViewById(R.id.my_or_shipping);
        dis_amt=(TextView)v.findViewById(R.id.my_or_dis_amtt);
        ord_id=(TextView)v.findViewById(R.id.my_or_ord_id);
        ord_date=(TextView)v.findViewById(R.id.my_or_ord_date);
        ord_pay_status=(TextView)v.findViewById(R.id.my_or_ord_pay_status);
        ord_del_status=(TextView)v.findViewById(R.id.my_or_ord_del_status);
        pay_type=(TextView)v.findViewById(R.id.my_or_ord_pay_meth);


        p_add1=(TextView)v.findViewById(R.id.p_add1);
        p_add2=(TextView)v.findViewById(R.id.p_add2);
        p_add3=(TextView)v.findViewById(R.id.p_add3);
        p_add4=(TextView)v.findViewById(R.id.p_add4);

        b_add1=(TextView)v.findViewById(R.id.b_add1);
        b_add2=(TextView)v.findViewById(R.id.b_add2);
        b_add3=(TextView)v.findViewById(R.id.b_add3);
        b_add4=(TextView)v.findViewById(R.id.b_add4);
        b_add5=(TextView)v.findViewById(R.id.b_add5);
        b_add6=(TextView)v.findViewById(R.id.b_add6);
        b_add7=(TextView)v.findViewById(R.id.b_add7);

        add1=(TextView)v.findViewById(R.id.my_or_add1);
        add2=(TextView)v.findViewById(R.id.my_or_add2);
        add3=(TextView)v.findViewById(R.id.my_or_add3);
        add4=(TextView)v.findViewById(R.id.my_or_add4);
        add5=(TextView)v.findViewById(R.id.my_or_add5);
        add6=(TextView)v.findViewById(R.id.my_or_add6);
        add7=(TextView)v.findViewById(R.id.my_or_add7);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vf.setDisplayedChild(1);
                for(int i=0;i<orderses.get(position).products.size();i++){
                    if(!orderses.get(position).products.get(i).type.equals("Coupon")){
                        pro_type="1";
                    }else{
                        pro_type="0";
                    }
                }
                if(pro_type.equals("0")){
                    shi_add_ll.setVisibility(View.GONE);
                    bill_add_ll.setVisibility(View.GONE);
                    pro_det_ll.setVisibility(View.GONE);
                }else{
                    shi_add_ll.setVisibility(View.VISIBLE);
                    bill_add_ll.setVisibility(View.VISIBLE);
                    pro_det_ll.setVisibility(View.VISIBLE);
                }
                stotal.setText(orderses.get(position).price+" KD");
                dis_amt.setText(orderses.get(position).discount_amount+" KD");
                gtotal.setText(orderses.get(position).price+" KD");
//                shipping.setText(orderses.get(position).price);
                ord_id.setText(orderses.get(position).id);
                ord_date.setText(orderses.get(position).date);
                ord_pay_status.setText(orderses.get(position).payment_status);
                ord_del_status.setText(orderses.get(position).delivery_status);
                pay_type.setText(orderses.get(position).payment_method);
                p_add1.setText(orderses.get(position).fname+" "+orderses.get(position).lname);
                p_add2.setText(orderses.get(position).country);
                p_add3.setText(orderses.get(position).phone);
                p_add4.setText(orderses.get(position).email);
                b_add1.setText(orderses.get(position).bill_area_title+Settings.get_lan(getActivity()));
                b_add2.setText(orderses.get(position).bill_house+", "+orderses.get(position).bill_street);
                b_add3.setText(orderses.get(position).bill_appartment+", "+orderses.get(position).bill_block);
                b_add4.setText(orderses.get(position).bill_floor+", "+orderses.get(position).bill_avenue);
                b_add5.setText(Settings.getword(getActivity(),"mobile")+" :"+orderses.get(position).bill_mobile);
                if(!orderses.get(position).bill_phone.equals("")) {
                    b_add6.setVisibility(View.VISIBLE);
                    b_add6.setText("Ph :" + orderses.get(position).bill_phone);
                }else{
                    b_add6.setVisibility(View.GONE);
                }
                b_add5.setText(Settings.getword(getActivity(),"pin")+" :"+orderses.get(position).bill_pincode);
                add1.setText(orderses.get(position).ship_area_title+Settings.get_lan(getActivity()));
                add2.setText(orderses.get(position).ship_house+", "+orderses.get(position).ship_street);
                add3.setText(orderses.get(position).ship_appartment+", "+orderses.get(position).ship_block);
                add4.setText(orderses.get(position).ship_floor+", "+orderses.get(position).ship_avenue);
                add5.setText(Settings.getword(getActivity(),"mobile")+" :"+orderses.get(position).ship_mobile);
                if(!orderses.get(position).ship_phone.equals("")) {
                    add6.setVisibility(View.VISIBLE);
                    add6.setText("Ph :" + orderses.get(position).ship_phone);
                }else{
                    add6.setVisibility(View.GONE);
                }
                add5.setText(Settings.getword(getActivity(),"pin")+" :"+orderses.get(position).ship_pincode);
                myordproductAdapter=new MyOrdersProductsAdapter(getActivity(),orderses,position);
                p_lv.setAdapter(myordproductAdapter);
                setListViewHeightBasedOnItems(p_lv);

            }
        });
        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (vf.getDisplayedChild()==1) {
                        vf.setDisplayedChild(0);
                        return true;
                    } else
                        return false;
//                            viewFlipper.setDisplayedChild(0);
                }
                return false;
            }
        });


    }
    private void getOrders(){
        String url;
        orderses.clear();
        url = Settings.SERVERURL + "order-history.php?member_id="+Settings.getUserid(getActivity());
        Log.e("url", url);
        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jsonArray) {
                progressBar.setVisibility(View.GONE);
                orderses.clear();
                try {
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject tmp_json = jsonArray.getJSONObject(i);
                        Orders temp = new Orders(tmp_json);
                        orderses.add(temp);
                    }
                    myOrdersAdapter = new MyOrdersAdapter(getActivity(),orderses);
                    lv.setAdapter(myOrdersAdapter);
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
}