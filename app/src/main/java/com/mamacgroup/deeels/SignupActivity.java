package com.mamacgroup.deeels;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class SignupActivity extends Activity {
    LinearLayout signup,female,male,dob_ll;
    EditText fname,lname,email,password,r_password;
    TextView dob,signup_tv,header,female_tv,male_tv,sta_your_imp;
    String gender,date;
    ImageView back;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Settings.forceRTLIfSupported(this);
        setContentView(R.layout.signup_activity);

        header=(TextView)findViewById(R.id.sta_header_tv);
        header.setText(Settings.getword(this, "signup"));
        sta_your_imp=(TextView)findViewById(R.id.sta_signup_ur_inp);
        sta_your_imp.setText(Settings.getword(this, "your_information"));
        male_tv=(TextView)findViewById(R.id.signup_male_tv);
        male_tv.setText(Settings.getword(this, "male"));
        female_tv=(TextView)findViewById(R.id.signup_female_tv);
        female_tv.setText(Settings.getword(this, "female"));
        signup_tv=(TextView)findViewById(R.id.signup_signup_tv);
        signup_tv.setText(Settings.getword(this,"signup"));

        signup=(LinearLayout)findViewById(R.id.signup_ll);
        female=(LinearLayout)findViewById(R.id.female_ll);
        male=(LinearLayout)findViewById(R.id.male_ll);
        fname=(EditText)findViewById(R.id.et_fname);
        fname.setHint(Settings.getword(this, "fname"));
        lname=(EditText)findViewById(R.id.et_lname);
        lname.setHint(Settings.getword(this, "lname"));
        dob=(TextView)findViewById(R.id.et_dob);
        dob.setText(Settings.getword(this,"dob"));
        email=(EditText)findViewById(R.id.et_email_signup);
        email.setText(Settings.getword(this,"email_address"));
        password=(EditText)findViewById(R.id.et_passwor_signup);
        password.setText(Settings.getword(this,"password"));
        r_password=(EditText)findViewById(R.id.et_rpasswor_signup);
        r_password.setText(Settings.getword(this,"re_password"));
        back=(ImageView)findViewById(R.id.signup_back_img);
        dob_ll=(LinearLayout)findViewById(R.id.signup_dob_ll);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(SignupActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fname.getText().toString().equals("")) {
                    Toast.makeText(SignupActivity.this, Settings.getword(SignupActivity.this, "please_enter_fname"), Toast.LENGTH_SHORT).show();
                }else if(lname.getText().toString().equals("")) {
                    Toast.makeText(SignupActivity.this, Settings.getword(SignupActivity.this, "please_enter_lname"), Toast.LENGTH_SHORT).show();
                }else if(dob.getText().toString().equals("")) {
                    Toast.makeText(SignupActivity.this, Settings.getword(SignupActivity.this, "please_enter_dob"), Toast.LENGTH_SHORT).show();
                }else if(gender.equals("")) {
                    Toast.makeText(SignupActivity.this, Settings.getword(SignupActivity.this, "please_select_gender"), Toast.LENGTH_SHORT).show();
                }else if(email.getText().toString().equals("")){
                    Toast.makeText(SignupActivity.this, Settings.getword(SignupActivity.this, "please_enter_email"), Toast.LENGTH_SHORT).show();
                }else if(!email.getText().toString().matches(emailPattern)) {
                    Toast.makeText(SignupActivity.this, Settings.getword(SignupActivity.this, "please_enter_valid_email"), Toast.LENGTH_SHORT).show();
                }else if(password.getText().toString().equals("")) {
                    Toast.makeText(SignupActivity.this,Settings.getword(SignupActivity.this,"please_enter_password"), Toast.LENGTH_SHORT).show();
                }else if(!r_password.getText().toString().equals(password.getText().toString())) {
                    Toast.makeText(SignupActivity.this,Settings.getword(SignupActivity.this,"please_enter_same_password"), Toast.LENGTH_SHORT).show();
                    r_password.setText("");
                }else {
                    register();
                }
            }
        });
    }
    public  void register(){
        String url= null;
            url = Settings.SERVERURL+"add-member.php";
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(Settings.getword(SignupActivity.this,"please_wait"));
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
                        Toast.makeText(SignupActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String msg = jsonObject.getString("message");
                        String m_id = jsonObject.getString("member_id");
                        Settings.setUserid(SignupActivity.this,m_id,msg);
                        Toast.makeText(SignupActivity.this, msg, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                        intent.putExtra("type","normal");
                        startActivity(intent);
                        finish();
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
                Log.e(email.getText().toString() + password.getText().toString(), "signup");
                params.put("fname", fname.getText().toString());
                params.put("lname", lname.getText().toString());
                params.put("dob", dob.getText().toString());
                params.put("gender", gender);
                params.put("email", email.getText().toString());
                params.put("password", password.getText().toString());
                params.put("cpassword",r_password.getText().toString());
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
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
