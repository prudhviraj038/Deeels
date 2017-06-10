package com.mamacgroup.deeels;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MenuActivity extends Activity {
    TextView sta_signup_tv,sta_login_tv,sta_home_tv,sta_setting_tv,sta_wt_deel_tv,sta_del_retu_tv,sta_pri_pol_tv,sta_con_tv;
    LinearLayout login,signup,sl_ll,home,settings,wt_deel,pp,dr,con_ll;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Settings.forceRTLIfSupported(this);
        setContentView(R.layout.menu_activity);
        sta_signup_tv=(TextView)findViewById(R.id.sta_signup_tv);
        sta_signup_tv.setText(Settings.getword(this, "signup"));
        sta_login_tv=(TextView)findViewById(R.id.sta_login_tv);
        sta_login_tv.setText(Settings.getword(this,"login"));
        sta_home_tv=(TextView)findViewById(R.id.sta_home_tv);
        sta_home_tv.setText(Settings.getword(this,"home"));
        sta_setting_tv=(TextView)findViewById(R.id.sta_setting_tv);
        sta_setting_tv.setText(Settings.getword(this,"settings"));
        sta_wt_deel_tv=(TextView)findViewById(R.id.sta_wt_deel_tv);
        sta_wt_deel_tv.setText(Settings.getword(this,"what_deel"));
        sta_del_retu_tv=(TextView)findViewById(R.id.sta_del_retu_tv);
        sta_del_retu_tv.setText(Settings.getword(this,"delivery_return_policy"));
        sta_pri_pol_tv=(TextView)findViewById(R.id.sta_pri_pol_tv);
        sta_pri_pol_tv.setText(Settings.getword(this,"privacy_policy"));
        sta_con_tv=(TextView)findViewById(R.id.sta_contctuss_tv);
        sta_con_tv.setText(Settings.getword(this,"contact_us"));
        login=(LinearLayout)findViewById(R.id.menu_login_ll);
        signup=(LinearLayout)findViewById(R.id.menu_signup_ll);
        sl_ll=(LinearLayout)findViewById(R.id.menu_sl_ll);
        home=(LinearLayout)findViewById(R.id.menu_home_ll);
        settings=(LinearLayout)findViewById(R.id.menu_settings_ll);
        wt_deel=(LinearLayout)findViewById(R.id.menu_wt_deels_ll);
        pp=(LinearLayout)findViewById(R.id.menu_pp_deels_ll);
        dr=(LinearLayout)findViewById(R.id.menu_dr_deels_ll);
        con_ll=(LinearLayout)findViewById(R.id.menu_con_deels_ll);
        if(Settings.getUserid(this).equals("-1")){
            sl_ll.setVisibility(View.VISIBLE);
        }else{
            sl_ll.setVisibility(View.GONE);
        }
        back=(ImageView)findViewById(R.id.menu_back_img);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, NavigationActivity.class);
                intent.putExtra("type","normal");
                startActivity(intent);
                finish();
            }
        });
        wt_deel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, NavigationActivity.class);
                intent.putExtra("type", "wt");
                startActivity(intent);

            }
        });
        pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, NavigationActivity.class);
                intent.putExtra("type", "pp");
                startActivity(intent);

            }
        });
        dr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, NavigationActivity.class);
                intent.putExtra("type", "dr");
                startActivity(intent);

            }
        });
        con_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, NavigationActivity.class);
                intent.putExtra("type", "con");
                startActivity(intent);

            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Settings.getUserid(MenuActivity.this).equals("-1")){
                    Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(MenuActivity.this, SettingsActivity.class);
                    startActivity(intent);
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
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
