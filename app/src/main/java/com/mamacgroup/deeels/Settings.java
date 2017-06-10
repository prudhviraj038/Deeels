package com.mamacgroup.deeels;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

/**
 * Created by Chinni on 03-05-2016.
 */
public class Settings {
    public static final String SERVERURL = "https://deeels.com/api/";
    public static final String PAYMENT_URL = "https://deeels.com/knet/buy-an.php?";
    public static final String PAYMENT_URL_MASTER  = "https://deeels.com/cards/pay-an.php?";
    static String Area_id="area_id";
    static String Address="Address";
    static String Area_name="area_name";
    static String Area_name_ar="area_name_ar";
    static String Deli_charges="delivery_charge";
    static String lan_key = "deeels_lan";
    static String words_key = "deeels_words";
    public static final String USERID = "deeels_id";
    public static final String NAME = "deeels_name";
    static SharedPreferences sharedPreferences;
    public static String get_isfirsttime(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("firsttime", "-1");
    }
    public static void set_isfirsttime(Context context, String gcm_id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("firsttime",gcm_id);
        editor.commit();
    }
    public static void setUserid(Context context, String member_id, String name) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERID, member_id);
        editor.putString(NAME, name);
        editor.commit();

    }
    
    public static void setArea_id(Context context, String area_id,String area,String area_ar) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Area_id, area_id);
        editor.putString(Area_name, area);
        editor.putString(Area_name_ar, area_ar);
        editor.commit();
    }
    public static String getArea_id(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(Area_id, "-1");
    }
    public static void setDelivery_charges(Context context, String deli_charges) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Deli_charges, deli_charges);
        editor.commit();
    }
    public static String getDelivery_charges(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(Deli_charges, "0");
    }
    public static String getArea_name(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(Area_name+get_lan(context), "-1");
    }
    public static String getUserid(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(USERID, "-1");

    }
    public static void set_user_language(Context context,String user_id){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(lan_key,user_id);
        editor.commit();
    }
    public static String get_user_language(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(lan_key,"en");
    }
    public static void set_user_language_words(Context context,String user_id){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(words_key,user_id);
        editor.commit();
    }

    public static String get_lan(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if(sharedPreferences.getString(lan_key,"en").equals("ar")){
            return "_ar";
        }else {
            return "";
        }
    }
    public static JSONObject get_user_language_words(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(sharedPreferences.getString(words_key,"-1"));
            jsonObject = jsonObject.getJSONObject(get_user_language(context));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    public static String getword(Context context,String word)
    {

        JSONObject words = get_user_language_words(context);

        try {
            return words.getString(word);
        } catch (JSONException e) {
            e.printStackTrace();
            return word;
        }
    }
    public static void setSettings(Context context, String about_us) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("settings",about_us);
        editor.commit();
    }

    public static String getSettings(Context context,String word) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        JSONObject jsonObject;
        String temp;
        try {
            jsonObject=new JSONObject( sharedPreferences.getString("settings","-1"));
            temp=jsonObject.getString(word);
        } catch (JSONException e) {
            temp=word;
            e.printStackTrace();
        }

        return temp;
    }
//    public static  void set_rating(Context context,String value,LinearLayout rating_ll){
//        for(float i=1;i<=5;i++) {
//            ImageView star = new ImageView(context);
//            star.setAdjustViewBounds(true);
//            if(i<= Float.parseFloat(value))
//                star.setImageResource(R.drawable.star_full);
//            else if(i- Float.parseFloat(value)<1)
//                star.setImageResource(R.drawable.star_half);
//            else
//                star.setImageResource(R.drawable.star_empty);
//            rating_ll.addView(star);
//        }
//    }

    public static   void forceRTLIfSupported(Activity activity)
    {
        SharedPreferences sharedPref;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
        Log.e("lan", sharedPref.getString(lan_key, "-1"));

        if (sharedPref.getString(lan_key, "-1").equals("en")) {
            Resources res = activity.getResources();
            // Change locale settings in the app.
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.locale = new Locale("en".toLowerCase());
            res.updateConfiguration(conf, dm);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
                activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            }
        }

        else if(sharedPref.getString(lan_key, "-1").equals("ar")){
            Resources res = activity.getResources();
            // Change locale settings in the app.
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.locale = new Locale("ar".toLowerCase());
            res.updateConfiguration(conf, dm);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
                activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
        }

        else {
            Resources res = activity.getResources();
            // Change locale settings in the app.
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.locale = new Locale("en".toLowerCase());
            res.updateConfiguration(conf, dm);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
                activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            }
        }

    }
}
