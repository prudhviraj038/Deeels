package com.mamacgroup.deeels;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class CheckoutFragment extends Fragment {
    LinearLayout login,email_ll,knet_ll,vm_ll,place_order_ll,apply_cc_ll,male_ll,female_ll,dob_ll,country_ll,place_order_one_ll,
            bi_ship_ll,area_bb_ll,area_ss_ll,email_tv_ll;
    ListView lv;
    TextView dob_tv,subtotal,grandtotal,shipping,dis_amt_tv,country_tv,ph_code_tv,area_bb_tv,area_ss_tv,mobile,fname_tv,lname_tv,gender_tv,dob_tv_tv,
            country_tv_tv,email_tv_tv;
    TextView sta_title_checkout_tv,sta_alr_deels,login_tv,personal_details,personal_details1,sta_ck_fname_tv,sta_ck_lname_tv,sta_ck_dob_tv,
            sta_ck_country_tv,male_tv,female_tv,sta_ck_phone_tv,sta_ck_email_tv,sta_pay_metode_ck,knet_tv,vm_tv,sta_review_order,sta_pro_title,
            sta_pro_qty,sta_pro_price,sta_gtotl,sta_shipping,sta_stotal,sta_iv_dis_amt,sta_coupon_code,apply_coupon_tv,place_order_one_tv
            ,sta_ck_email_tv1,sta_gender_tv,sta_ck_country_tv1,sta_ck_dob_tv1,sta_ck_lname_tv1,sta_ck_fname_tv1,sta_ck_area_tv,sta_ck_pin_tv_s,
            sta_ck_phonee_tv_s,sta_ck_mobile_tv_s,sta_ck_avenue_tv_s,sta_ck_floor_tv_s,sta_ck_appart_tv_s,sta_ck_house_tv_s,sta_ck_street_tv_s,
            sta_ck_block_tv_s,sta_ck_area_tv_s,sta_ck_pin_tv,sta_ck_phonee_tv,sta_ck_mobile_tv,sta_ck_avenue_tv,sta_ck_floor_tv,sta_ck_appart_tv,
            sta_ck_house_tv,sta_ck_street_tv,sta_ck_block_tv,sta_biling_address,sta_shipping_address,place_order_tv,sta_bill_as_shipp_tv;
    ImageView knet_img,vm_img,bi_shi_img;
    EditText fname,lname,block,street,house,appartment,floor,avenue,pin,phone,et_cc,email,ph_main,s_block,s_street,
            s_house,s_appartment,s_floor,s_avenue,s_mobile,s_phone,s_pin;
    String gender="",date="",pay_type="",dis_amount="0";
    JSONObject place_order_object = new JSONObject();
    private int mYear, mMonth, mDay, mHour, mMinute;
    CheckoutAdapter checkoutAdapter;
    ArrayList<CartItem> cart_items;
    float temp=0;
    Float grn_total = 0f;
    String co_id="",area_id_b="",area_id_s="";
    int bs=0;
    int are=0;
    String temp_ord_id="";
    AreaAdapter personAdapter;
    ViewFlipper vf;
    String pro_type;
    ArrayList<Area> area_list;
    ArrayList<String> country_id;
    ArrayList<String> country_name;
    ArrayList<String> country_sname;
    ArrayList<String> country_code;
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public void to_invoice(String id);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // getActivity() makes sure that the container activity has implemented
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
        View rootview = inflater.inflate(R.layout.checkout_fragment, container, false);
        return rootview;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getView();
        cart_items=new ArrayList<>();
        country_id=new ArrayList<>();
        country_name=new ArrayList<>();
        country_sname=new ArrayList<>();
        country_code=new ArrayList<>();
        area_list = new ArrayList<>();
        getCountry();
        getarea();
        vf=(ViewFlipper)v.findViewById(R.id.viewFlipper3);
        cart_items=(ArrayList)getArguments().getSerializable("cart_items");

        sta_title_checkout_tv=(TextView)v.findViewById(R.id.sta_title_checkout_tv);
        sta_title_checkout_tv.setText(Settings.getword(getActivity(), "checkout"));
        sta_alr_deels=(TextView)v.findViewById(R.id.sta_alr_deels);
        sta_alr_deels.setText(Settings.getword(getActivity(), "sta_alr_deels"));
        login_tv=(TextView)v.findViewById(R.id.ck_login_tv);
        login_tv.setText(Settings.getword(getActivity(),"login"));
        personal_details=(TextView)v.findViewById(R.id.sta_per_det_tv_ck);
        personal_details.setText(Settings.getword(getActivity(),"personal_det"));
        personal_details1=(TextView)v.findViewById(R.id.sta_per_det_tv_ck1);
        personal_details1.setText(Settings.getword(getActivity(),"personal_det"));
        sta_ck_fname_tv=(TextView)v.findViewById(R.id.sta_ck_fname_tv);
        sta_ck_fname_tv.setText(Settings.getword(getActivity(),"fname"));
        sta_ck_lname_tv=(TextView)v.findViewById(R.id.sta_ck_lname_tv);
        sta_ck_lname_tv.setText(Settings.getword(getActivity(),"lname"));
        sta_ck_dob_tv=(TextView)v.findViewById(R.id.sta_ck_dob_tv);
        sta_ck_dob_tv.setText(Settings.getword(getActivity(),"dob"));
        sta_ck_country_tv=(TextView)v.findViewById(R.id.sta_ck_country_tv);
        sta_ck_country_tv.setText(Settings.getword(getActivity(),"country"));
        male_tv=(TextView)v.findViewById(R.id.male_ck_tv);
        male_tv.setText(Settings.getword(getActivity(),"male"));
        female_tv=(TextView)v.findViewById(R.id.female_ck_tv);
        female_tv.setText(Settings.getword(getActivity(),"female"));
        sta_ck_phone_tv=(TextView)v.findViewById(R.id.sta_ck_phoneee_tv);
        sta_ck_phone_tv.setText(Settings.getword(getActivity(),"mobile"));
        sta_ck_email_tv=(TextView)v.findViewById(R.id.sta_ck_email_tv);
        sta_ck_email_tv.setText(Settings.getword(getActivity(),"email_address"));
        sta_pay_metode_ck=(TextView)v.findViewById(R.id.sta_pay_metode_ck);
        sta_pay_metode_ck.setText(Settings.getword(getActivity(),"pay_type"));
        knet_tv=(TextView)v.findViewById(R.id.knet_tv);
        knet_tv.setText(Settings.getword(getActivity(),"knet"));
        vm_tv=(TextView)v.findViewById(R.id.vm_tv);
        vm_tv.setText(Settings.getword(getActivity(),"visa_master"));
        sta_review_order=(TextView)v.findViewById(R.id.sta_review_order);
        sta_review_order.setText(Settings.getword(getActivity(),"review"));
        sta_pro_title=(TextView)v.findViewById(R.id.sta_pro_check_title);
        sta_pro_title.setText(Settings.getword(getActivity(),"title"));
        sta_pro_qty=(TextView)v.findViewById(R.id.sta_pro_check_qty);
        sta_pro_qty.setText(Settings.getword(getActivity(),"qty"));
        sta_pro_price=(TextView)v.findViewById(R.id.sta_pro_check_price);
        sta_pro_price.setText(Settings.getword(getActivity(),"price"));
        sta_gtotl=(TextView)v.findViewById(R.id.sta_gtotal_tv);
        sta_gtotl.setText(Settings.getword(getActivity(),"grand_total"));
        sta_shipping=(TextView)v.findViewById(R.id.sta_shipping_tv);
//        sta_shipping.setText(Settings.getword(getActivity(),"shipping"));
        sta_stotal=(TextView)v.findViewById(R.id.sta_stotal_tv);
        sta_stotal.setText(Settings.getword(getActivity(),"sub_total"));
        sta_iv_dis_amt=(TextView)v.findViewById(R.id.sta_dis_amtt_tv);
        sta_iv_dis_amt.setText(Settings.getword(getActivity(),"discount"));
        sta_coupon_code=(TextView)v.findViewById(R.id.sta_coupon_code);
        sta_coupon_code.setText(Settings.getword(getActivity(),"coupon_code"));
        apply_coupon_tv=(TextView)v.findViewById(R.id.apply_coupon_tv);
        apply_coupon_tv.setText(Settings.getword(getActivity(),"apply_coupon"));
        place_order_one_tv=(TextView)v.findViewById(R.id.place_order_one_tv);
        place_order_one_tv.setText(Settings.getword(getActivity(),"place_order"));
        sta_ck_fname_tv1=(TextView)v.findViewById(R.id.sta_ck_fname_tv1);
        sta_ck_fname_tv1.setText(Settings.getword(getActivity(),"fname"));
        sta_ck_lname_tv1=(TextView)v.findViewById(R.id.sta_ck_lname_tv1);
        sta_ck_lname_tv1.setText(Settings.getword(getActivity(),"lname"));
        sta_ck_dob_tv1=(TextView)v.findViewById(R.id.sta_ck_dob_tv1);
        sta_ck_dob_tv1.setText(Settings.getword(getActivity(),"dob"));
        sta_ck_country_tv1=(TextView)v.findViewById(R.id.sta_ck_country_tv1);
        sta_ck_country_tv1.setText(Settings.getword(getActivity(),"country"));
        sta_gender_tv=(TextView)v.findViewById(R.id.sta_gender_tv_ck);
        sta_gender_tv.setText(Settings.getword(getActivity(),"gender"));
        sta_ck_email_tv1=(TextView)v.findViewById(R.id.sta_ck_email_tv1);
        sta_ck_email_tv1.setText(Settings.getword(getActivity(),"email_address"));
        sta_biling_address=(TextView)v.findViewById(R.id.sta_biling_address);
        sta_biling_address.setText(Settings.getword(getActivity(), "billing_address"));
        sta_ck_area_tv=(TextView)v.findViewById(R.id.sta_ck_area_tv);
        sta_ck_area_tv.setText(Settings.getword(getActivity(), "select_area"));
        sta_ck_block_tv=(TextView)v.findViewById(R.id.sta_ck_block_tv);
        sta_ck_block_tv.setText(Settings.getword(getActivity(), "block"));
        sta_ck_street_tv=(TextView)v.findViewById(R.id.sta_ck_street_tv);
        sta_ck_street_tv.setText(Settings.getword(getActivity(), "street"));
        sta_ck_house_tv=(TextView)v.findViewById(R.id.sta_ck_house_tv);
        sta_ck_house_tv.setText(Settings.getword(getActivity(), "house"));
        sta_ck_appart_tv=(TextView)v.findViewById(R.id.sta_ck_appart_tv);
        sta_ck_appart_tv.setText(Settings.getword(getActivity(), "appartment"));
        sta_ck_floor_tv=(TextView)v.findViewById(R.id.sta_ck_floor_tv);
        sta_ck_floor_tv.setText(Settings.getword(getActivity(), "floor"));
        sta_ck_avenue_tv=(TextView)v.findViewById(R.id.sta_ck_avenue_tv);
        sta_ck_avenue_tv.setText(Settings.getword(getActivity(), "avenue"));
        sta_ck_mobile_tv=(TextView)v.findViewById(R.id.sta_ck_mobile_tv);
        sta_ck_mobile_tv.setText(Settings.getword(getActivity(), "mobile"));
        sta_ck_phonee_tv=(TextView)v.findViewById(R.id.sta_ck_phone_tv);
        sta_ck_phonee_tv.setText(Settings.getword(getActivity(), "phone"));
        sta_ck_pin_tv=(TextView)v.findViewById(R.id.sta_ck_pin_tv);
        sta_ck_pin_tv.setText(Settings.getword(getActivity(), "pin"));
        sta_shipping_address=(TextView)v.findViewById(R.id.sta_shipping_address);
        sta_shipping_address.setText(Settings.getword(getActivity(), "shipping_address"));
        sta_ck_area_tv_s=(TextView)v.findViewById(R.id.sta_ck_area_tv_s);
        sta_ck_area_tv_s.setText(Settings.getword(getActivity(), "select_area"));
        sta_ck_block_tv_s=(TextView)v.findViewById(R.id.sta_ck_block_tv_s);
        sta_ck_block_tv_s.setText(Settings.getword(getActivity(), "block"));
        sta_ck_street_tv_s=(TextView)v.findViewById(R.id.sta_ck_street_tv_s);
        sta_ck_street_tv_s.setText(Settings.getword(getActivity(), "street"));
        sta_ck_house_tv_s=(TextView)v.findViewById(R.id.sta_ck_house_tv_s);
        sta_ck_house_tv_s.setText(Settings.getword(getActivity(), "house"));
        sta_ck_appart_tv_s=(TextView)v.findViewById(R.id.sta_ck_appart_tv_s);
        sta_ck_appart_tv_s.setText(Settings.getword(getActivity(), "appartment"));
        sta_ck_floor_tv_s=(TextView)v.findViewById(R.id.sta_ck_floor_tv_s);
        sta_ck_floor_tv_s.setText(Settings.getword(getActivity(), "floor"));
        sta_ck_avenue_tv_s=(TextView)v.findViewById(R.id.sta_ck_avenue_tv_s);
        sta_ck_avenue_tv_s.setText(Settings.getword(getActivity(), "avenue"));
        sta_ck_mobile_tv_s=(TextView)v.findViewById(R.id.sta_ck_mobile_tv_s);
        sta_ck_mobile_tv_s.setText(Settings.getword(getActivity(), "mobile"));
        sta_ck_phonee_tv_s=(TextView)v.findViewById(R.id.sta_ck_phone_tv_s);
        sta_ck_phonee_tv_s.setText(Settings.getword(getActivity(), "phone"));
        sta_ck_pin_tv_s=(TextView)v.findViewById(R.id.sta_ck_pin_tv_s);
        sta_ck_pin_tv_s.setText(Settings.getword(getActivity(), "pin"));
        place_order_tv=(TextView)v.findViewById(R.id.place_order_tv);
        place_order_tv.setText(Settings.getword(getActivity(),"place_order"));
        sta_bill_as_shipp_tv=(TextView)v.findViewById(R.id.sta_bill_as_shipp_tv);
        sta_bill_as_shipp_tv.setText(Settings.getword(getActivity(),"bill_as_shipping"));

        login=(LinearLayout)v.findViewById(R.id.ck_login_ll);
        email_ll=(LinearLayout)v.findViewById(R.id.ck_email_ll);
        knet_ll=(LinearLayout)v.findViewById(R.id.knet_ll);
        vm_ll=(LinearLayout)v.findViewById(R.id.vm_ll);
        place_order_one_ll=(LinearLayout)v.findViewById(R.id.place_order_one_ll);
        place_order_ll=(LinearLayout)v.findViewById(R.id.place_order_ll);
        apply_cc_ll=(LinearLayout)v.findViewById(R.id.apply_coupon_ll);
        female_ll=(LinearLayout)v.findViewById(R.id.female_ck_ll);
        male_ll=(LinearLayout)v.findViewById(R.id.male_ck_ll);
        dob_ll=(LinearLayout)v.findViewById(R.id.dob_ck_ll);
        country_ll=(LinearLayout)v.findViewById(R.id.country_ck_ll);
        bi_ship_ll=(LinearLayout)v.findViewById(R.id.bi_as_ship_ll);
        area_bb_ll=(LinearLayout)v.findViewById(R.id.area_bb_ll);
        area_ss_ll=(LinearLayout)v.findViewById(R.id.area_ss_ll);
        email_tv_ll=(LinearLayout)v.findViewById(R.id.emailll_ll_ck);

        knet_img=(ImageView)v.findViewById(R.id.knet_img);
        vm_img=(ImageView)v.findViewById(R.id.vm_img);
        bi_shi_img=(ImageView)v.findViewById(R.id.bi_as_ship_img);

        ph_code_tv=(TextView)v.findViewById(R.id.phone_code_main_ck_tv);
        country_tv=(TextView)v.findViewById(R.id.country_ck_tv);
        dob_tv=(TextView)v.findViewById(R.id.dob_ck_tv);
        subtotal=(TextView)v.findViewById(R.id.stotal_tv);
        grandtotal=(TextView)v.findViewById(R.id.gtotal_tv);
        shipping=(TextView)v.findViewById(R.id.shipping_tv);
        dis_amt_tv=(TextView)v.findViewById(R.id.dis_amt_ck_tv);
        area_bb_tv=(TextView)v.findViewById(R.id.area_bb_tv);
        area_ss_tv=(TextView)v.findViewById(R.id.area_ss_tv);
        mobile=(TextView)v.findViewById(R.id.et_ck_mobile);

        fname_tv=(TextView)v.findViewById(R.id.fname_tv_ck);
        lname_tv=(TextView)v.findViewById(R.id.lname_tv_ck);
        gender_tv=(TextView)v.findViewById(R.id.genderr_tv_ck);
        dob_tv_tv=(TextView)v.findViewById(R.id.dobbb_tv_ck);
        country_tv_tv=(TextView)v.findViewById(R.id.coun_tv_ck);
        email_tv_tv=(TextView)v.findViewById(R.id.emailll_tv_ck);
        
        fname=(EditText)v.findViewById(R.id.et_ck_fname);
        lname=(EditText)v.findViewById(R.id.et_ck_lname);
        ph_main=(EditText)v.findViewById(R.id.et_phone_main_ck);


        block=(EditText)v.findViewById(R.id.et_ck_block);
        street=(EditText)v.findViewById(R.id.et_ck_street);
        house=(EditText)v.findViewById(R.id.et_ck_house);
        appartment=(EditText)v.findViewById(R.id.et_ck_appartment);
        floor=(EditText)v.findViewById(R.id.et_ck_floor);
        avenue=(EditText)v.findViewById(R.id.et_ck_avenue);

        phone=(EditText)v.findViewById(R.id.et_ck_phone);
        pin=(EditText)v.findViewById(R.id.et_ck_pin);


        s_block=(EditText)v.findViewById(R.id.et_block_s);
        s_street=(EditText)v.findViewById(R.id.et_Street_s);
        s_house=(EditText)v.findViewById(R.id.et_house_s);
        s_appartment=(EditText)v.findViewById(R.id.et_appartment_s);
        s_floor=(EditText)v.findViewById(R.id.et_floor_s);
        s_avenue=(EditText)v.findViewById(R.id.et_avenue_s);
        s_mobile=(EditText)v.findViewById(R.id.et_mobile_s);
        s_phone=(EditText)v.findViewById(R.id.et_phone_s);
        s_pin=(EditText)v.findViewById(R.id.et_pin_s);

        et_cc=(EditText)v.findViewById(R.id.et_coupon_code);
        et_cc.setHint(Settings.getword(getActivity(),"enter_coupon_code"));
        email=(EditText)v.findViewById(R.id.et_ck_email);
        lv=(ListView)v.findViewById(R.id.check_pro_list);
        checkoutAdapter=new CheckoutAdapter(getActivity(),cart_items);
        lv.setAdapter(checkoutAdapter);
        setListViewHeightBasedOnItems(lv);
        ListView area_listView = (ListView)v.findViewById(R.id.area_listView);
        personAdapter = new AreaAdapter(getActivity(), area_list);
        area_listView.setAdapter(personAdapter);
        area_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vf.setDisplayedChild(1);
                if (are == 0) {
                    area_id_b = area_list.get(position).id;
                    area_bb_tv.setText(area_list.get(position).getArea(getActivity()));
                } else {
                    area_id_s = area_list.get(position).id;
                    area_ss_tv.setText(area_list.get(position).getArea(getActivity()));
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        for(int i=0;i<cart_items.size();i++){
            temp=temp+Float.parseFloat(cart_items.get(i).qty)*Float.parseFloat(cart_items.get(i).products.cart_price);
            grn_total=temp;
            Log.e("total",String.valueOf(temp));
            subtotal.setText(String.format("%.2f", temp) + " KD");
            grandtotal.setText(String.format("%.2f", temp) + " KD");
        }
        area_bb_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vf.setDisplayedChild(2);
                are=0;
            }
        });
        area_ss_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vf.setDisplayedChild(2);
                are=1;
            }
        });
        country_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(Settings.getword(getActivity(),"choose_country"));
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, country_name);
                builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(ChooseSubjectActivity.getActivity(), level_title.get(which), Toast.LENGTH_SHORT).show();
                        co_id = country_id.get(which);
                        country_tv.setText(country_name.get(which));
                        ph_code_tv.setText(country_code.get(which));
                    }
                });

                final AlertDialog dialog = builder.create();
                dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                dialog.show();
       
            }
        });
        if(!Settings.getUserid(getActivity()).equals("-1")){
            info();
        }
        if(Settings.getUserid(getActivity()).equals("-1")){
            login.setVisibility(View.VISIBLE);
            email_ll.setVisibility(View.VISIBLE);
            email_tv_ll.setVisibility(View.VISIBLE);
        }else{
            login.setVisibility(View.GONE);
            email_ll.setVisibility(View.GONE);
            email_tv_ll.setVisibility(View.GONE);
        }
        knet_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay_type="1";
                knet_img.setImageResource(R.drawable.check_tick);
                vm_img.setImageResource(R.drawable.check_empty);
            }
        });
        vm_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay_type="2";
                knet_img.setImageResource(R.drawable.check_empty);
                vm_img.setImageResource(R.drawable.check_tick);
            }
        });
        dob_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {                    String temp = String.valueOf(monthOfYear + 1);
                        if (temp.length() < 2)
                            temp = "0" + temp;
                        String temp1 = String.valueOf(dayOfMonth);
                        if (temp1.length() < 2)
                            temp1 = "0" + temp1;
                        date = temp1 + "-" + temp + "-" + year;
                        dob_tv.setText(date);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        female_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender="female";
                female_ll.setBackgroundResource(R.drawable.green_rounded_corners_with_fill);
                male_ll.setBackgroundResource(R.drawable.green_corners_with_border);
            }
        });
        male_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender="male";
                male_ll.setBackgroundResource(R.drawable.green_rounded_corners_with_fill);
                female_ll.setBackgroundResource(R.drawable.green_corners_with_border);
            }
        });
        apply_cc_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_cc.getText().toString().equals(""))
//                    alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "enter_valid_coupon_code"), false);
                    Toast.makeText(getActivity(),Settings.getword(getActivity(), "please_enter_valid_coupon"), Toast.LENGTH_SHORT).show();
                else
                    set_coupon();
            }
        });
        for(int i=0;i<cart_items.size();i++){
            if(!cart_items.get(i).products.type.equals("Coupon")){
                pro_type="1";
            }else{
                pro_type="0";
            }
        }

        bi_ship_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bs==0){
                   bi_shi_img.setImageResource(R.drawable.check_tick);
                    bs=1;
                    area_id_s=area_id_b;
                    area_ss_tv.setText(area_bb_tv.getText().toString());
                    s_block.setText(block.getText().toString());
                    s_street.setText(street.getText().toString());
                    s_house.setText(house.getText().toString());
                    s_appartment.setText(appartment.getText().toString());
                    s_floor.setText(floor.getText().toString());
                    s_avenue.setText(avenue.getText().toString());
                    s_mobile.setText(mobile.getText().toString());
                    s_phone.setText(phone.getText().toString());
                    s_pin.setText(pin.getText().toString());

                }else{
                    bi_shi_img.setImageResource(R.drawable.check_empty);
                    bs=0;
                    area_id_s="";
                    area_ss_tv.setText("");
                    s_block.setText("");
                    s_street.setText("");
                    s_house.setText("");
                    s_appartment.setText("");
                    s_floor.setText("");
                    s_avenue.setText("");
                    s_mobile.setText("");
                    s_phone.setText("");
                    s_pin.setText("");
                }
            }
        });
        place_order_one_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname_tv.setText(fname.getText().toString());
                lname_tv.setText(lname.getText().toString());
                gender_tv.setText(gender);
                dob_tv_tv.setText(dob_tv.getText().toString());
                country_tv_tv.setText(country_tv.getText().toString());
                email_tv_tv.setText(email.getText().toString());
                mobile.setText(ph_main.getText().toString());
                if (Settings.getUserid(getActivity()).equals("-1")) {
                    if (fname.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_fname"), Toast.LENGTH_SHORT).show();
                    } else if (lname.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_lname"), Toast.LENGTH_SHORT).show();
                    } else if (dob_tv.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_dob"), Toast.LENGTH_SHORT).show();
                    } else if (gender.equals("")) {
                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_select_gender"), Toast.LENGTH_SHORT).show();
                    }else if (co_id.equals("")) {
                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_select_country"), Toast.LENGTH_SHORT).show();
                    }  else if (ph_main.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_mobile"), Toast.LENGTH_SHORT).show();
                    }else if (email.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_email"), Toast.LENGTH_SHORT).show();
                    }else if (pay_type.equals("")) {
                        Toast.makeText(getActivity(),Settings.getword(getActivity(),"please_select_pay_type"),Toast.LENGTH_SHORT).show();
                    } else {
                        if (pro_type.equals("1")) {
                            vf.setDisplayedChild(1);
                        } else {
                            place_set_data();
                        }
                    }
                }else{
                    if (fname.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_fname"), Toast.LENGTH_SHORT).show();
                    } else if (lname.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_lname"), Toast.LENGTH_SHORT).show();
                    } else if (dob_tv.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_dob"), Toast.LENGTH_SHORT).show();
                    } else if (gender.equals("")) {
                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_select_gender"), Toast.LENGTH_SHORT).show();
                    } else if (co_id.equals("")) {
                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_select_country"), Toast.LENGTH_SHORT).show();
                    }  else if (ph_main.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_mobile"), Toast.LENGTH_SHORT).show();
                    } else if (pay_type.equals("")) {
                        Toast.makeText(getActivity(),Settings.getword(getActivity(),"please_select_pay_type"),Toast.LENGTH_SHORT).show();
                    } else {
                        if (pro_type.equals("1")) {
                            vf.setDisplayedChild(1);
                        } else {
                            place_set_data();
                        }
                    }
                }
            }
        });
        place_order_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (area_id_b.equals("")) {
                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_billing_area"), Toast.LENGTH_SHORT).show();
                    } else if (block.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_block"), Toast.LENGTH_SHORT).show();
                    } else if (street.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_street"), Toast.LENGTH_SHORT).show();
                    } else if (house.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_house"), Toast.LENGTH_SHORT).show();
                    } else if (appartment.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_appart"), Toast.LENGTH_SHORT).show();
                    } else if (mobile.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_mobile"), Toast.LENGTH_SHORT).show();
                    } else if (pin.getText().toString().equals("")) {
                        Toast.makeText(getActivity(),Settings.getword(getActivity(),"please_enter_pin_code"),Toast.LENGTH_SHORT).show();
                    } else if (area_id_s.equals("")) {
                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_shipping_area"), Toast.LENGTH_SHORT).show();
                    } else if (s_block.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_block"), Toast.LENGTH_SHORT).show();
                    } else if (s_street.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_street"), Toast.LENGTH_SHORT).show();
                    } else if (s_house.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_house"), Toast.LENGTH_SHORT).show();
                    } else if (s_appartment.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_appart"), Toast.LENGTH_SHORT).show();
                    } else if (s_mobile.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_mobile"), Toast.LENGTH_SHORT).show();
                    } else if (s_pin.getText().toString().equals("")) {
                        Toast.makeText(getActivity(),Settings.getword(getActivity(),"please_enter_pin_code"),Toast.LENGTH_SHORT).show();
                    }  else {
                        place_set_data();
                    }
            }
        });
    }
    public  void place_set_data(){
        JSONObject address_object = new JSONObject();
        JSONArray products_array = new JSONArray();
        try {
            if(pro_type.equals("0")){
                address_object.put("first_name", fname.getText().toString());
                address_object.put("last_name", lname.getText().toString());
                address_object.put("dob", dob_tv.getText().toString());
                address_object.put("gender", gender);
                address_object.put("country", co_id);
                address_object.put("phone",ph_main.getText().toString());
                address_object.put("email", email.getText().toString());
                address_object.put("bill_area", "");
                address_object.put("bill_block", "");
                address_object.put("bill_street", "");
                address_object.put("bill_house", "");
                address_object.put("bill_floor", "");
                address_object.put("bill_appartment", "");
                address_object.put("bill_avenue","");
                address_object.put("bill_pincode", "");
                address_object.put("bill_phone", "");
                address_object.put("bill_mobile", "");
                address_object.put("ship_area", "");
                address_object.put("ship_block", "");
                address_object.put("ship_street", "");
                address_object.put("ship_house", "");
                address_object.put("ship_floor", "");
                address_object.put("ship_appartment", "");
                address_object.put("ship_avenue","");
                address_object.put("ship_pincode","");
                address_object.put("ship_phone", "");
                address_object.put("ship_mobile", "");
            }else {
                address_object.put("first_name", fname.getText().toString());
                address_object.put("last_name", lname.getText().toString());
                address_object.put("dob", dob_tv.getText().toString());
                address_object.put("gender", gender);
                address_object.put("country", co_id);
                address_object.put("phone",ph_main.getText().toString());
                address_object.put("email", email.getText().toString());
                address_object.put("bill_area", area_id_b);
                address_object.put("bill_block", block.getText().toString());
                address_object.put("bill_street", street.getText().toString());
                address_object.put("bill_house", house.getText().toString());
                address_object.put("bill_floor", floor.getText().toString());
                address_object.put("bill_appartment", appartment.getText().toString());
                address_object.put("bill_avenue", avenue.getText().toString());
                address_object.put("bill_pincode", pin.getText().toString());
                address_object.put("bill_phone", phone.getText().toString());
                address_object.put("bill_mobile", mobile.getText().toString());
                address_object.put("ship_area", area_id_s);
                address_object.put("ship_block", s_block.getText().toString());
                address_object.put("ship_street", s_street.getText().toString());
                address_object.put("ship_house", s_house.getText().toString());
                address_object.put("ship_floor", s_floor.getText().toString());
                address_object.put("ship_appartment", s_appartment.getText().toString());
                address_object.put("ship_avenue", s_avenue.getText().toString());
                address_object.put("ship_pincode", s_pin.getText().toString());
                address_object.put("ship_phone", s_phone.getText().toString());
                address_object.put("ship_mobile", s_mobile.getText().toString());
            }
            place_order_object.put("address", address_object);
            
            place_order_object.put("coupon_code", et_cc.getText().toString());
            place_order_object.put("discount_amount", dis_amount);
//            place_order_object.put("shipping", "0");
            place_order_object.put("total_price",String.valueOf(grn_total) );
            place_order_object.put("payment_method", pay_type);
//            place_order_object.put("member_id", Settings.getUserid(getActivity()));
            Log.e("cart", String.valueOf(cart_items.size()));
            for (int i = 0; i < cart_items.size(); i++) {
                JSONObject product = new JSONObject();
                product.put("product_id", cart_items.get(i).products.id);
                product.put("quantity", cart_items.get(i).qty);
                product.put("price", String.valueOf(Float.parseFloat(cart_items.get(i).qty)*Float.parseFloat(cart_items.get(i).products.cart_price)));
                products_array.put(product);
            }
            place_order_object.put("products", products_array);
            place_order();
            Log.e("order", place_order_object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public  void place_order() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Settings.getword(getActivity(), "please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);
        String url = Settings.SERVERURL+"place-order.php?";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("res",response);
                if(progressDialog!=null)
                    progressDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString("status").equals("Success")){
                        Toast.makeText(getActivity(), jsonObject.getString("status"), Toast.LENGTH_SHORT).show();
                        temp_ord_id=jsonObject.getString("invoice_id");
                        Log.e("pay_type",pay_type);
                        if(pay_type.equals("1")) {
                            Intent payment = new Intent(getActivity(), PaymentActivity.class);
                            payment.putExtra("cust_id", Settings.getUserid(getActivity()));
                            payment.putExtra("pack_price", String.valueOf(grn_total));
                            payment.putExtra("order_id", temp_ord_id);
                            startActivityForResult(payment, 7);
                        }else if(pay_type.equals("2")) {
                            Intent payment = new Intent(getActivity(), PaymentActivityMaster.class);
                            payment.putExtra("cust_id", Settings.getUserid(getActivity()));
                            payment.putExtra("pack_price", String.valueOf(grn_total));
                            payment.putExtra("order_id",temp_ord_id);
                            startActivityForResult(payment, 8);
                        }else{}
//                        mCallBack.to_invoice(temp_ord_id);
                    }
                    else{
                        String reply=jsonObject.getString("status");
                        if(reply.equals("Failed")) {
                            String msg = jsonObject.getString("message");
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("member_id", Settings.getUserid(getActivity()));
                params.put("order", place_order_object.toString());
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 7) {
            if(data.getStringExtra("msg").equals("OK"))
            {
                Toast.makeText(getActivity(),"Payment success",Toast.LENGTH_SHORT).show();
                mCallBack.to_invoice(temp_ord_id);
            }
            else
                Toast.makeText(getActivity(),Settings.getword(getActivity(),"pay_failed"),Toast.LENGTH_SHORT).show();
        }else if(requestCode == 8){
            if(data.getStringExtra("msg").equals("OK"))
            {
                Toast.makeText(getActivity(),"Payment success",Toast.LENGTH_SHORT).show();
                mCallBack.to_invoice(temp_ord_id);
            }
            else
                Toast.makeText(getActivity(),Settings.getword(getActivity(),"pay_failed"),Toast.LENGTH_SHORT).show();
        }else{}
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
    public  void set_coupon(){
        String url = Settings.SERVERURL+"coupon-check.php?";
        try {
            url = url +"&cart_total="+URLEncoder.encode(String.valueOf(temp),"utf-8")+
                    "&coupon="+URLEncoder.encode(et_cc.getText().toString(),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Settings.getword(getActivity(), "please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                progressDialog.dismiss();
                Log.e("response is: ", jsonObject.toString());

                try {
                    String reply=jsonObject.getString("status");
                    if(reply.equals("Failure")) {
                        String msg = jsonObject.getString("message");
//                        alert.showAlertDialog(getActivity(), "Info",msg, false);
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String dis_type=jsonObject.getString("discount_type");
                        String dis_value=jsonObject.getString("discount_value");
                        dis_amount=dis_value;
                        grn_total=temp-Float.parseFloat(dis_value);
                        dis_amt_tv.setText(dis_value+" KD");
                        subtotal.setText(String.format("%.2f",grn_total)+" KD");
                        grandtotal.setText(String.format("%.2f",grn_total)+" KD");
                        apply_cc_ll.setVisibility(View.GONE);
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
                Toast.makeText(getActivity(), Settings.getword(getActivity(),"server_not_connected"), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);


    }
    private void getCountry(){
        String url;
        url = Settings.SERVERURL + "countries.php";
        Log.e("url", url);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Settings.getword(getActivity(), "please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jsonArray) {
                progressDialog.dismiss();
                country_name.clear();country_id.clear();
                country_sname.clear();country_code.clear();
                try {
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject tmp_json = jsonArray.getJSONObject(i);
                        country_id.add(tmp_json.getString("id"));
                        country_name.add(tmp_json.getString("name"));
                        country_sname.add(tmp_json.getString("shortname"));
                        country_code.add(tmp_json.getString("phonecode"));
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
                progressDialog.dismiss();

            }
        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }
    public  void info(){
        String url = Settings.SERVERURL + "members.php?member_id="+ Settings.getUserid(getActivity());
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Settings.getword(getActivity(), "please_wait"));
//        progressDialog.setMessage(Settings.getword(SettingsActivity.getActivity(), "please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonObject) {
                progressDialog.dismiss();
                Log.e("response is: ", jsonObject.toString());
                try {

                    for (int i = 0; i < jsonObject.length(); i++) {
                        JSONObject sub = jsonObject.getJSONObject(i);
                        fname.setText(sub.getString("fname"));
                        lname.setText(sub.getString("lname"));
                        gender=sub.getString("gender");
                        if(gender.equals("male")) {
                            male_ll.setBackgroundResource(R.drawable.green_rounded_corners_with_fill);
                        }
                        else if(gender.equals("female")){
                            female_ll.setBackgroundResource(R.drawable.green_rounded_corners_with_fill);
                        }else{}
                        dob_tv.setText(sub.getString("dob"));
                        area_bb_tv.setText(sub.getJSONObject("area").getString("title" + Settings.get_lan(getActivity())));
                        block.setText(sub.getString("block"));
                        street.setText(sub.getString("street"));
                        house.setText(sub.getString("house"));
                        appartment.setText(sub.getString("appartment"));
                        floor.setText(sub.getString("floor"));
                        avenue.setText(sub.getString("avenue"));
                        phone.setText(sub.getString("phone"));
                        mobile.setText(sub.getString("mobile"));
                        pin.setText(sub.getString("pincode"));
                        email.setText(sub.getString("email"));
//                        u_phone.setText(sub.getString("phone"));
//                        Picasso.with(SettingsActivity.getActivity()).load(sub.getString("image")).into(u_img);
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
                Toast.makeText(getActivity(), Settings.getword(getActivity(), "server_not_connected"), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }
    private void getarea() {
        String url = null;
        try {
            url = Settings.SERVERURL + "areas.php";
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Settings.getword(getActivity(), "please_wait"));
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                progressDialog.dismiss();
                Log.e("orders response is: ", jsonArray.toString());

                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject sub = jsonArray.getJSONObject(i);
                        String id = jsonArray.getJSONObject(i).getString("id");
                        String area = jsonArray.getJSONObject(i).getString("title");
                        String area_ar = jsonArray.getJSONObject(i).getString("title_ar");
                        Area person = new Area(id,area,area_ar,true);
                        area_list.add(person);

                        Log.e("titleee", sub.getString("title"));

                        JSONArray jsonArray1=sub.getJSONArray("areas");
                        for (int j = 0; j < jsonArray1.length(); j++) {
                            String idt = jsonArray1.getJSONObject(j).getString("id");
                            String areat = jsonArray1.getJSONObject(j).getString("title");
                            String areat_ar = jsonArray1.getJSONObject(j).getString("title_ar");
                            Area persont = new Area(idt,areat,areat_ar,false);
                            area_list.add(persont);
                        }

                    }
                    personAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("response is:", error.toString());
                Toast.makeText(getActivity(), Settings.getword(getActivity(),"server_not_connected"), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }

}
