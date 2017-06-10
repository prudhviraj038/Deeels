package com.mamacgroup.deeels;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spalsh_screen);
//        Settings.setArea_id(getApplicationContext(), "-1","-1","-1");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        ImageView imageView=(ImageView)findViewById(R.id.splash);
//        ExceptionHandler.register(this, "https://www.darabeel.com/api/error.php");
     //   https://www.darabeel.com/api/error.php
//        Settings.setArea_id(this,"-1","","");
//        imageView.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                newone();
                get_language_words();
            }
        }, 2000);

    }
    public  void newone(){
        String abc= Settings.get_isfirsttime(SplashActivity.this);
        Log.e("lng", abc);
        if(abc.equals("-1")) {
            Intent mainIntent = new Intent(getApplicationContext(), LanguageActivity.class);
            startActivity(mainIntent);
            finish();
        }
        else {
//            if(Settings.getUserid(SplashActivity.this).equals("-1")){
//                Intent mainIntent = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(mainIntent);
//                finish();
//            }else {
                Intent mainIntent = new Intent(getApplicationContext(), NavigationActivity.class);
                 mainIntent.putExtra("type", "normal");
                startActivity(mainIntent);
                finish();
//            }

        }
    }

    private void get_language_words(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(Settings.getword(this, "please_wait"));
        progressDialog.setCancelable(false);
        String url = Settings.SERVERURL+"words-json-android.php";
        Log.e("url", url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                Log.e("response is: ", jsonObject.toString());
                Settings.set_user_language_words(SplashActivity.this, jsonObject.toString());
//                Intent intent = new Intent(SplashActivity.this,NavigationActivity.class);
//                startActivity(intent);
//                finish();
                newone();
                setting();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                Log.e("error",error.toString());
                Toast.makeText(SplashActivity.this, Settings.getword(SplashActivity.this,"server_not_connected"), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);


    }
    private void setting() {
        String url = Settings.SERVERURL + "settings.php";
        Log.e("url", url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                Log.e("response is: ", jsonObject.toString());
                Settings.setSettings(SplashActivity.this, jsonObject.toString());


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString());
                Toast.makeText(SplashActivity.this,Settings.getword(SplashActivity.this,"server_not_connected"), Toast.LENGTH_SHORT).show();
                finish();
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

