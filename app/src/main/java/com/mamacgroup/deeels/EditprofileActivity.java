package com.mamacgroup.deeels;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class EditprofileActivity extends Activity {
    LinearLayout submit,female,male,billing_ll,offers_ll,dob_ll,area_ll;
    EditText fname,lname,block,street,house,appartment,floor,avenue,mobile,phone,pin;
    TextView dob,area,header,sta_your_imp,female_tv,male_tv,offers_tv,billing_tv,submit_tv;
    ImageView billing_img,offers_img;
    String gender="",date;
    ImageView back;
    AreaAdapter personAdapter;
    ViewFlipper vf;
    ArrayList<Area> area_list;
    String bill="0",offer="0",area_id="";
    String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private int mYear, mMonth, mDay, mHour, mMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Settings.forceRTLIfSupported(this);
        setContentView(R.layout.editprofile_activity);
        area_list = new ArrayList<>();
        getarea();
        vf=(ViewFlipper)findViewById(R.id.viewFlipper4);
        submit=(LinearLayout)findViewById(R.id.ef_submit_ll);
        female=(LinearLayout)findViewById(R.id.ef_female_ll);
        male=(LinearLayout)findViewById(R.id.ef_male_ll);
        billing_ll=(LinearLayout)findViewById(R.id.ef_billing_ll);
        offers_ll=(LinearLayout)findViewById(R.id.ef_offers_ll);
        dob_ll=(LinearLayout)findViewById(R.id.ef_dob_ll);
        area_ll=(LinearLayout)findViewById(R.id.ef_area_ll);

        billing_img=(ImageView)findViewById(R.id.ef_billing_img);
        offers_img=(ImageView)findViewById(R.id.ef_offers_img);

        header=(TextView)findViewById(R.id.ef_header);
        header.setText(Settings.getword(this,"edit_profile"));
        sta_your_imp=(TextView)findViewById(R.id.your_imp_tv);
        sta_your_imp.setText(Settings.getword(this,"your_information"));
        male_tv=(TextView)findViewById(R.id.ef_male_tv);
        male_tv.setText(Settings.getword(this,"male"));
        female_tv=(TextView)findViewById(R.id.ef_female_tv);
        female_tv.setText(Settings.getword(this,"female"));
        billing_tv=(TextView)findViewById(R.id.ef_billing_tv);
        billing_tv.setText(Settings.getword(this,"biling"));
        offers_tv=(TextView)findViewById(R.id.ef_offers_tv);
        offers_tv.setText(Settings.getword(this,"offers"));
        submit_tv=(TextView)findViewById(R.id.ef_submit_tv);
        submit_tv.setText(Settings.getword(this,"submit"));

        fname=(EditText)findViewById(R.id.ef_fname);
        fname.setHint(Settings.getword(this, "fname"));
        lname=(EditText)findViewById(R.id.ef_lname);
        lname.setHint(Settings.getword(this, "lname"));
        dob=(TextView)findViewById(R.id.ef_dob);
        dob.setText(Settings.getword(this,"dob"));
        area=(TextView)findViewById(R.id.ef_area);
        area.setText(Settings.getword(this, "select_area"));
        block=(EditText)findViewById(R.id.ef_block);
        block.setHint(Settings.getword(this, "block"));
        street=(EditText)findViewById(R.id.ef_street);
        street.setHint(Settings.getword(this, "street"));
        house=(EditText)findViewById(R.id.ef_house);
        house.setHint(Settings.getword(this, "house"));
        appartment=(EditText)findViewById(R.id.ef_appartment);
        appartment.setHint(Settings.getword(this, "appartment"));
        floor=(EditText)findViewById(R.id.ef_floor);
        floor.setHint(Settings.getword(this, "floor"));
        avenue=(EditText)findViewById(R.id.ef_avenue);
        avenue.setHint(Settings.getword(this, "avenue"));
        mobile=(EditText)findViewById(R.id.ef_mobile);
        mobile.setHint(Settings.getword(this, "mobile"));
        phone=(EditText)findViewById(R.id.ef_phone);
        phone.setHint(Settings.getword(this, "phone"));
        pin=(EditText)findViewById(R.id.ef_pin);
        pin.setHint(Settings.getword(this, "pin"));
        info();
        ListView area_listView = (ListView)findViewById(R.id.area_listView_ef);
        personAdapter = new AreaAdapter(this, area_list);
        area_listView.setAdapter(personAdapter);
        area_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vf.setDisplayedChild(0);
                header.setText(Settings.getword(EditprofileActivity.this, "edit_profile"));
                area_id = area_list.get(position).id;
                area.setText(area_list.get(position).getArea(EditprofileActivity.this));
            }
        });
        area_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vf.setDisplayedChild(1);
                header.setText(Settings.getword(EditprofileActivity.this, "areas"));
            }
        });
        back=(ImageView)findViewById(R.id.ef_back_img);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        dob_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditprofileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String temp = String.valueOf(monthOfYear + 1);
                        if (temp.length() < 2)
                            temp = "0" + temp;
                        String temp1 = String.valueOf(dayOfMonth);
                        if (temp1.length() < 2)
                            temp1 = "0" + temp1;
                        date = temp1 + "-" + temp + "-" + year;
                        dob.setText(date);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender="female";
                female.setBackgroundResource(R.drawable.green_rounded_corners_with_fill);
                male.setBackgroundResource(R.drawable.white_rounded_corners_with_bo);
            }
        });
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender="male";
                male.setBackgroundResource(R.drawable.green_rounded_corners_with_fill);
                female.setBackgroundResource(R.drawable.white_rounded_corners_with_bo);
            }
        });
        billing_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bill.equals("0")){
                    billing_img.setImageResource(R.drawable.check_empty);
                    bill = "1";
                }else{
                    billing_img.setImageResource(R.drawable.check_tick);
                    bill = "0";
                }
            }
        });
        offers_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(offer.equals("0")){
                    offers_img.setImageResource(R.drawable.check_empty);
                    offer = "1";
                }else{
                    offers_img.setImageResource(R.drawable.check_tick);
                    offer = "0";
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fname.getText().toString().equals("")) {
                    Toast.makeText(EditprofileActivity.this, Settings.getword(EditprofileActivity.this, "please_enter_fname"), Toast.LENGTH_SHORT).show();
                }else if(lname.getText().toString().equals("")) {
                    Toast.makeText(EditprofileActivity.this, Settings.getword(EditprofileActivity.this, "please_enter_lname"), Toast.LENGTH_SHORT).show();
                }else if(dob.getText().toString().equals("")) {
                    Toast.makeText(EditprofileActivity.this, Settings.getword(EditprofileActivity.this, "please_enter_dob"), Toast.LENGTH_SHORT).show();
                }else if(gender.equals("")) {
                    Toast.makeText(EditprofileActivity.this, Settings.getword(EditprofileActivity.this, "please_select gender"), Toast.LENGTH_SHORT).show();
                }else if(area_id.equals("")){
                    Toast.makeText(EditprofileActivity.this, Settings.getword(EditprofileActivity.this, "please_enter_area"), Toast.LENGTH_SHORT).show();
                }else if(block.getText().toString().equals("")) {
                    Toast.makeText(EditprofileActivity.this,Settings.getword(EditprofileActivity.this,"please_enter_block"), Toast.LENGTH_SHORT).show();
                }else if(street.getText().toString().equals("")) {
                    Toast.makeText(EditprofileActivity.this,Settings.getword(EditprofileActivity.this,"please_enter_street"), Toast.LENGTH_SHORT).show();
                }else if(house.getText().toString().equals("")) {
                    Toast.makeText(EditprofileActivity.this,Settings.getword(EditprofileActivity.this,"please_enter_house"), Toast.LENGTH_SHORT).show();
                }else if(appartment.getText().toString().equals("")) {
                    Toast.makeText(EditprofileActivity.this,Settings.getword(EditprofileActivity.this,"please_enter_appartment"), Toast.LENGTH_SHORT).show();
                }else if(mobile.getText().toString().equals("")) {
                    Toast.makeText(EditprofileActivity.this,Settings.getword(EditprofileActivity.this,"please_enter_mobile"), Toast.LENGTH_SHORT).show();
                }else if(pin.getText().toString().equals("")) {
                    Toast.makeText(EditprofileActivity.this,Settings.getword(EditprofileActivity.this,"please_enter_postal_code"), Toast.LENGTH_SHORT).show();
                }else {
                    register();
                }
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        if(vf.getDisplayedChild()==1){
            vf.setDisplayedChild(0);
            header.setText(Settings.getword(EditprofileActivity.this, "edit_profile"));
        }else {
            super.onBackPressed();  // optional depending on your needs
        }
    }
    private void getarea() {
        String url = null;
        try {
            url = Settings.SERVERURL + "areas.php";
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(EditprofileActivity.this);
        progressDialog.setMessage(Settings.getword(EditprofileActivity.this, "please_wait"));
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
                Toast.makeText(EditprofileActivity.this, Settings.getword(EditprofileActivity.this,"server_not_connected"), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }
    public  void register(){
        String url= null;
            url = Settings.SERVERURL+"edit-member.php";
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(Settings.getword(EditprofileActivity.this,"please_wait"));
//        progressDialog.setMessage(Settings.getword(SignupLawyersActivity.this,"please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);
        Log.e("url", url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("res", response);
                if(progressDialog!=null)
                    progressDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String reply=jsonObject.getString("status");
                    if(reply.equals("Failed")) {
                        String msg = jsonObject.getString("message");
//                        alert.showAlertDialog(getActivity(), "Info", msg, true);
                        Toast.makeText(EditprofileActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String msg = jsonObject.getString("message");
                        Toast.makeText(EditprofileActivity.this, msg, Toast.LENGTH_SHORT).show();
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
//                        Toast.makeText(SignupLawyersActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("member_id", Settings.getUserid(EditprofileActivity.this));
                params.put("fname", fname.getText().toString());
                params.put("lname", lname.getText().toString());
                params.put("dob", dob.getText().toString());
                params.put("gender", gender);
                params.put("area", area_id);
                params.put("block", block.getText().toString());
                params.put("street",street.getText().toString());
                params.put("house", house.getText().toString());
                params.put("appartment", appartment.getText().toString());
                params.put("floor", floor.getText().toString());
                params.put("avenue", avenue.getText().toString());
                params.put("mobile",mobile.getText().toString());
                params.put("phone", phone.getText().toString());
                params.put("pincode",pin.getText().toString());
                params.put("billing",bill);
                params.put("offers",bill);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
    public  void info(){
        String url = Settings.SERVERURL + "members.php?member_id="+ Settings.getUserid(EditprofileActivity.this);
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(EditprofileActivity.this);
        progressDialog.setMessage(Settings.getword(EditprofileActivity.this, "please_wait"));
//        progressDialog.setMessage(Settings.getword(SettingsActivity.this, "please_wait"));
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
                            male.setBackgroundResource(R.drawable.green_rounded_corners_with_fill);
                        }
                        else if(gender.equals("female")){
                            female.setBackgroundResource(R.drawable.green_rounded_corners_with_fill);
                        }else{}

                        dob.setText(sub.getString("dob"));
                        area.setText(sub.getJSONObject("area").getString("title"+Settings.get_lan(EditprofileActivity.this)));
                        block.setText(sub.getString("block"));
                        street.setText(sub.getString("street"));
                        house.setText(sub.getString("house"));
                        appartment.setText(sub.getString("appartment"));
                        floor.setText(sub.getString("floor"));
                        avenue.setText(sub.getString("avenue"));
                        phone.setText(sub.getString("phone"));
                        mobile.setText(sub.getString("mobile"));
                        pin.setText(sub.getString("pincode"));
                        bill=sub.getString("billing");
                        offer=sub.getString("offers");
                        if(offer.equals("0")){
                            offers_img.setImageResource(R.drawable.check_empty);
                        }else{
                            offers_img.setImageResource(R.drawable.check_tick);
                        }
                        if(bill.equals("0")){
                            billing_img.setImageResource(R.drawable.check_empty);
                        }else{
                            billing_img.setImageResource(R.drawable.check_tick);
                        }
//                        u_phone.setText(sub.getString("phone"));
//                        Picasso.with(SettingsActivity.this).load(sub.getString("image")).into(u_img);
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
                Toast.makeText(EditprofileActivity.this, Settings.getword(EditprofileActivity.this, "server_not_connected"), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
