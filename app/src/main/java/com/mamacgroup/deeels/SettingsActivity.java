package com.mamacgroup.deeels;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SettingsActivity extends Activity {
    TextView country_tv,lan_tv,title,sta_lan_tv,sta_editprofile_tv,sta_change_password_tv,sta_my_ord_tv,sta_notifications_tv,sta_signout_tv,cp_submit_tv;
    LinearLayout ef,language,signout_ll,ch_pass_ll,my_orders_ll,submit_ll;
    EditText op,np,cp;
    ArrayList<String> grade;
    ImageView back;
    ViewFlipper vf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Settings.forceRTLIfSupported(this);
        setContentView(R.layout.settings_activity);

        sta_lan_tv=(TextView)findViewById(R.id.sta_lan_tv);
        sta_lan_tv.setText(Settings.getword(SettingsActivity.this, "language"));
        sta_editprofile_tv=(TextView)findViewById(R.id.sta_editprofile_tv);
        sta_editprofile_tv.setText(Settings.getword(SettingsActivity.this, "edit_profile"));
        sta_change_password_tv=(TextView)findViewById(R.id.sta_change_password_tv);
        sta_change_password_tv.setText(Settings.getword(SettingsActivity.this,"change_password"));
        sta_my_ord_tv=(TextView)findViewById(R.id.sta_my_ord_tv);
        sta_my_ord_tv.setText(Settings.getword(SettingsActivity.this,"my_orders"));
        sta_notifications_tv=(TextView)findViewById(R.id.sta_notifications_tv);
        sta_notifications_tv.setText(Settings.getword(SettingsActivity.this,"notications"));
        sta_signout_tv=(TextView)findViewById(R.id.sta_signout_tv);
        sta_signout_tv.setText(Settings.getword(SettingsActivity.this,"signout"));
        cp_submit_tv=(TextView)findViewById(R.id.cp_submit_tv);
        cp_submit_tv.setText(Settings.getword(SettingsActivity.this,"submit"));

        vf=(ViewFlipper)findViewById(R.id.viewFlipper);
        back=(ImageView)findViewById(R.id.sett_back_img);
        submit_ll=(LinearLayout)findViewById(R.id.cp_submit_ll);
        op=(EditText)findViewById(R.id.cp_et_opass);
        op.setHint(Settings.getword(SettingsActivity.this,"old_password"));
        np=(EditText)findViewById(R.id.cp_et_pass);
        np.setHint(Settings.getword(SettingsActivity.this,"new_password"));
        cp=(EditText)findViewById(R.id.cp_et_r_pass);
        cp.setHint(Settings.getword(SettingsActivity.this,"confirm_password"));
        language=(LinearLayout)findViewById(R.id.sett_lan_ll);
        ef=(LinearLayout)findViewById(R.id.ef_ll);
        signout_ll=(LinearLayout)findViewById(R.id.signout_lll);
        ch_pass_ll=(LinearLayout)findViewById(R.id.ch_pass_ll);
        my_orders_ll=(LinearLayout)findViewById(R.id.my_orders_ll);
        title=(TextView)findViewById(R.id.sett_title_tv);
        lan_tv=(TextView)findViewById(R.id.sett_lan_tv);
        if(Settings.get_user_language(this).equals("ar")){
            lan_tv.setText("Arabic");
        }else{
            lan_tv.setText("English");
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, LanguageActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, EditprofileActivity.class);
                startActivity(intent);
            }
        });
        ch_pass_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vf.setDisplayedChild(1);
                title.setText(Settings.getword(SettingsActivity.this,"change_password"));
            }
        });
        my_orders_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, NavigationActivity.class);
                intent.putExtra("type","ord");
                startActivity(intent);
            }
        });
        signout_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.setUserid(SettingsActivity.this,"-1","");
                Intent intent = new Intent(SettingsActivity.this, NavigationActivity.class);
                intent.putExtra("type","normal");
                startActivity(intent);
                finish();
            }
        });
        submit_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(op.getText().toString().equals("")) {
                    Toast.makeText(SettingsActivity.this, Settings.getword(SettingsActivity.this, "please_old_password"), Toast.LENGTH_SHORT).show();
                }else if(np.getText().toString().equals("")) {
                    Toast.makeText(SettingsActivity.this, Settings.getword(SettingsActivity.this, "please_enter_password"), Toast.LENGTH_SHORT).show();
                }else if(cp.getText().toString().equals("")) {
                    Toast.makeText(SettingsActivity.this,  Settings.getword(SettingsActivity.this,"please_enter_same_password"), Toast.LENGTH_SHORT).show();
                }else {
                    change_password();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        if(vf.getDisplayedChild()==1){
            vf.setDisplayedChild(0);
            title.setText(Settings.getword(SettingsActivity.this, "settings"));
        }else{
            super.onBackPressed();
        }
    }
    public  void change_password(){
        String url=Settings.SERVERURL+"change-password.php";
        final ProgressDialog progressDialog = new ProgressDialog(SettingsActivity.this);
        progressDialog.setMessage(Settings.getword(SettingsActivity.this,"please_wait"));
//        progressDialog.setMessage(Settings.getword(SignupLawyersActivity.SettingsActivity.this,"please_wait"));
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
//                        alert.showAlertDialog(SettingsActivity.this, "Info", msg, true);
                        Toast.makeText(SettingsActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String msg = jsonObject.getString("message");
                        Toast.makeText(SettingsActivity.this, msg, Toast.LENGTH_SHORT).show();
                        vf.setDisplayedChild(0);
                        title.setText(Settings.getword(SettingsActivity.this, "settings"));
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
                        Toast.makeText(SettingsActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("opassword", op.getText().toString());
                params.put("password", np.getText().toString());
                params.put("cpassword", cp.getText().toString());
                params.put("member_id", Settings.getUserid(SettingsActivity.this));
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
