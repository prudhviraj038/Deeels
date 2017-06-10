package com.mamacgroup.deeels;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sriven on 5/6/2016.
 */
public class Category implements Serializable{
    String title,title_ar,id,image;
    ArrayList<Products> productses;
    Category(JSONObject jsonObject){
        productses=new ArrayList<>();
        try {
            this.id = jsonObject.getString("id");
            this.title=jsonObject.getString("title");
            this.title_ar=jsonObject.getString("title_ar");
            this.image=jsonObject.getString("image");
            for(int i=0;i<jsonObject.getJSONArray("products").length();i++){
                Products products=new Products(jsonObject.getJSONArray("products").getJSONObject(i));
                this.productses.add(products);
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

}
