package com.mamacgroup.deeels;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class LoginActivity extends Activity {
    TextView login_tv,fp,fp_title,fp_et,submit_tv,header,new_user;
    EditText email,password;
    LinearLayout login,fp_pop_ll,submit_ll;
    ImageView close_fp;
    ImageView back;
    String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Settings.forceRTLIfSupported(this);
        setContentView(R.layout.login_activity);
        login=(LinearLayout)findViewById(R.id.login_ll);
        email=(EditText)findViewById(R.id.et_login_email);
        email.setHint(Settings.getword(this,"email_address"));
        password=(EditText)findViewById(R.id.et_login_password);
        password.setHint(Settings.getword(this,"password"));
        fp_et=(EditText)findViewById(R.id.fp_et_email);
        fp_et.setHint(Settings.getword(this,"email_address"));
        header=(TextView)findViewById(R.id.login_page_header);
        header.setText(Settings.getword(this, "login"));
        fp=(TextView)findViewById(R.id.fp_tv);
        fp.setText(Settings.getword(this,"forgot_password"));
        fp_title=(TextView)findViewById(R.id.fp_title);
        fp_title.setText(Settings.getword(this,"forgot_password_title"));
        login_tv=(TextView)findViewById(R.id.login_tv);
        login_tv.setText(Settings.getword(this,"login"));
        new_user=(TextView)findViewById(R.id.login_new_user_tv);
        new_user.setText(Settings.getword(this,"new_user"));
        submit_tv=(TextView)findViewById(R.id.fp_submit_tv);
        submit_tv.setText(Settings.getword(this,"submit"));
        back=(ImageView)findViewById(R.id.login_back_img);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        fp_pop_ll=(LinearLayout)findViewById(R.id.fp_pop_ll);
        submit_ll=(LinearLayout)findViewById(R.id.fp_submit_ll);
        close_fp=(ImageView)findViewById(R.id.fp_close);
        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fp_pop_ll.setVisibility(View.VISIBLE);
            }
        });
        close_fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fp_pop_ll.setVisibility(View.GONE);
            }
        });
        new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, Settings.getword(LoginActivity.this, "please_enter_email"), Toast.LENGTH_SHORT).show();
                }else if(!email.getText().toString().matches(emailPattern)) {
                    Toast.makeText(LoginActivity.this, Settings.getword(LoginActivity.this, "please_enter_valid_email"), Toast.LENGTH_SHORT).show();
                }else if(password.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this,Settings.getword(LoginActivity.this,"please_enter_password"), Toast.LENGTH_SHORT).show();
                }else {
                    login();
                }
            }
        });
        submit_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fp_et.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, Settings.getword(LoginActivity.this,"please_enter_email"), Toast.LENGTH_SHORT).show();
                } else if (!fp_et.getText().toString().matches(emailPattern)){
                    Toast.makeText(LoginActivity.this, Settings.getword(LoginActivity.this, "please_enter_valid_email"), Toast.LENGTH_SHORT).show();
                }else{
                    fp_pop_ll.setVisibility(View.GONE);
                    forgot_pass();
                }
            }
        });
    }
    public  void login(){
        String url = null;
        try {
            url = Settings.SERVERURL+"login.php?email="+ URLEncoder.encode(email.getText().toString(), "utf-8")+
                    "&password="+URLEncoder.encode(password.getText().toString(),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(Settings.getword(LoginActivity.this, "please_wait"));
//        progressDialog.setMessage(Settings.getword(this, "please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                progressDialog.dismiss();
                Log.e("response is: ", jsonObject.toString());
                try {
                    String reply=jsonObject.getString("status");
                    if(reply.equals("Failure")) {
                        String msg = jsonObject.getString("message");
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String mem_id=jsonObject.getString("member_id");
                        String name=jsonObject.getString("name");
                        Settings.setUserid(LoginActivity.this, mem_id, name);
                        Toast.makeText(LoginActivity.this, name, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
                        intent.putExtra("type","normal");
                        startActivity(intent);
                        finish();
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
                Toast.makeText(LoginActivity.this, Settings.getword(LoginActivity.this,"server_not_connected"), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }
    public void forgot_pass(){
        String url = Settings.SERVERURL+"forget-password.php?email="+fp_et.getText().toString();
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(Settings.getword(LoginActivity.this,"please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                progressDialog.dismiss();
                Log.e("response is: ", jsonObject.toString());
                try {
                    String reply=jsonObject.getString("status");
                    if(reply.equals("Failed")) {
                        String msg = jsonObject.getString("message");
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String msg = jsonObject.getString("message");
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        fp_pop_ll.setVisibility(View.GONE);
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
                Toast.makeText(LoginActivity.this,  Settings.getword(LoginActivity.this,"server_not_connected"), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
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
