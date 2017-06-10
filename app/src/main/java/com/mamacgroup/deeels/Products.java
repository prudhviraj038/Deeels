package com.mamacgroup.deeels;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sriven on 5/6/2016.
 */
public class Products implements Serializable{
    String title,title_ar,id,code,price,oprice,latitude,longitude,start_date,end_date,start_time,end_time,starts,ends,time_diff,
            type,banner_image,video_script,small_description,small_description_ar,warranty,warranty_ar,details,details_ar,specifications,
            specifications_ar,notes,notes_ar,cat_id,cat_title,cat_title_ar,cart_price;
    ArrayList<Images> images;
    Products(JSONObject jsonObject){
        images=new ArrayList<>();
        try {
            this.id = jsonObject.getString("id");
            this.title=jsonObject.getString("title");
            this.title_ar=jsonObject.getString("title_ar");
            this.code = jsonObject.getString("code");
            this.price=jsonObject.getString("price");
            this.cart_price=jsonObject.getString("price");;
            this.oprice=jsonObject.getString("oprice");
            this.latitude=jsonObject.getString("latitude");
            this.longitude = jsonObject.getString("longitude");
            this.start_date=jsonObject.getString("start_date");
            this.end_date=jsonObject.getString("end_date");
            this.start_time=jsonObject.getString("start_time");
            this.end_time = jsonObject.getString("end_time");
            this.starts=jsonObject.getString("starts");
            this.ends=jsonObject.getString("ends");
            this.time_diff=jsonObject.getString("time_diff");
            this.type = jsonObject.getString("type");
            this.banner_image=jsonObject.getString("banner_image");
            this.video_script=jsonObject.getString("video_script");
            this.small_description=jsonObject.getString("small_description");
            this.small_description_ar = jsonObject.getString("small_description_ar");
            this.warranty=jsonObject.getString("warranty");
            this.warranty_ar=jsonObject.getString("warranty_ar");
            this.details=jsonObject.getString("details");
            this.details_ar = jsonObject.getString("details_ar");
            this.specifications=jsonObject.getString("specifications");
            this.specifications_ar=jsonObject.getString("specifications_ar");
            this.notes=jsonObject.getString("notes");
            this.cat_id=jsonObject.getJSONObject("category").getString("id");
            this.cat_title=jsonObject.getJSONObject("category").getString("title");
            this.cat_title_ar=jsonObject.getJSONObject("category").getString("title_ar");
            this.notes_ar = jsonObject.getString("notes_ar");
            for(int i=0;i<jsonObject.getJSONArray("images").length();i++){
                Images img=new Images(jsonObject.getJSONArray("images").getJSONObject(i));
                this.images.add(img);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getTitle(Context context) {
        if(Settings.get_user_language(context).equals("ar"))
            return title_ar;
        else
            return  title;
    }
    public String get_s_des(Context context) {
        if(Settings.get_user_language(context).equals("ar"))
            return small_description_ar;
        else
            return  small_description;
    }
    public String get_det(Context context) {
        if(Settings.get_user_language(context).equals("ar"))
            return details_ar;
        else
            return  details;
    }
    public String get_war(Context context) {
        if(Settings.get_user_language(context).equals("ar"))
            return warranty_ar;
        else
            return  warranty;
    }
    public String get_spec(Context context) {
        if(Settings.get_user_language(context).equals("ar"))
            return specifications_ar;
        else
            return  specifications;
    }
    public String get_note(Context context) {
        if(Settings.get_user_language(context).equals("ar"))
            return notes_ar;
        else
            return  notes;
    }
    public  class  Images implements  Serializable{
        String img,thumb;
        Images(JSONObject jsonObject){
            try {
                img=jsonObject.getString("image");
                thumb=jsonObject.getString("thumb");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
