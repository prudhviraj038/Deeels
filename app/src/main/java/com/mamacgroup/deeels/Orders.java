package com.mamacgroup.deeels;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yellowsoft on 4/5/17.
 */
public class Orders implements Serializable {
    String id,fname,lname,phone,email,gender,dob,country,bill_area_id,bill_area_title,bill_area_title_ar,bill_house,bill_street,bill_appartment,
            bill_block,bill_floor,bill_avenue,bill_phone,bill_mobile,bill_pincode,ship_area_id,ship_area_title,ship_area_title_ar,ship_house,
            ship_street,ship_appartment,ship_block,ship_floor,ship_avenue,ship_phone,ship_mobile,ship_pincode,coupon_code,discount_amount,price,
            payment_method,payment_status,delivery_status,date;
    ArrayList<Product> products;
    Orders(JSONObject jsonObject){
        products=new ArrayList<>();
        try {
            id=jsonObject.getString("id");
            fname=jsonObject.getString("fname");
            lname=jsonObject.getString("lname");
            phone=jsonObject.getString("phone");
            email=jsonObject.getString("email");
            country=jsonObject.getString("country");
            gender=jsonObject.getString("gender");
            dob=jsonObject.getString("dob");
            bill_area_id=jsonObject.getJSONObject("bill_area").getString("id");
            bill_area_title=jsonObject.getJSONObject("bill_area").getString("title");
            bill_area_title_ar=jsonObject.getJSONObject("bill_area").getString("title_ar");
            bill_house=jsonObject.getString("bill_house");
            bill_street=jsonObject.getString("bill_street");
            bill_appartment=jsonObject.getString("bill_appartment");
            bill_block=jsonObject.getString("bill_block");
            bill_floor=jsonObject.getString("bill_floor");
            bill_avenue=jsonObject.getString("bill_avenue");
            bill_phone=jsonObject.getString("bill_phone");
            bill_mobile=jsonObject.getString("bill_mobile");
            bill_pincode=jsonObject.getString("bill_pincode");
            ship_area_id=jsonObject.getJSONObject("ship_area").getString("id");
            ship_area_title=jsonObject.getJSONObject("ship_area").getString("title");
            ship_area_title_ar=jsonObject.getJSONObject("ship_area").getString("title_ar");
            ship_house=jsonObject.getString("ship_house");
            ship_street=jsonObject.getString("ship_street");
            ship_appartment=jsonObject.getString("ship_appartment");
            ship_block=jsonObject.getString("ship_block");
            ship_floor=jsonObject.getString("ship_floor");
            ship_avenue=jsonObject.getString("ship_avenue");
            ship_phone=jsonObject.getString("ship_phone");
            ship_mobile=jsonObject.getString("ship_mobile");
            ship_pincode=jsonObject.getString("ship_pincode");
            coupon_code=jsonObject.getString("coupon_code");
            discount_amount=jsonObject.getString("discount_amount");
            price=jsonObject.getString("price");
            payment_method=jsonObject.getString("payment_method");
            payment_status=jsonObject.getString("payment_status");
            delivery_status=jsonObject.getString("delivery_status");
            date=jsonObject.getString("date");
            for(int i=0;i<jsonObject.getJSONArray("products").length();i++){
                JSONObject temp=jsonObject.getJSONArray("products").getJSONObject(i);
                Product img=new Product(temp);
                this.products.add(img);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public class Product implements Serializable  {
        String p_id,p_title,p_title_ar,quantity,p_price,p_img,p_t_price,type;
        Product(JSONObject jsonObject){
            try {
                p_id=jsonObject.getString("product_id");
                p_title=jsonObject.getString("product_name");
                p_title_ar=jsonObject.getString("product_name_ar");
                quantity=jsonObject.getString("quantity");
                p_price=jsonObject.getString("price");
                type=jsonObject.getString("type");
                p_img=jsonObject.getString("image");
                p_t_price=jsonObject.getString("total_price");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        public String getTitle(Context context) {
            if(Settings.get_user_language(context).equals("ar"))
                return p_title_ar;
            else
                return  p_title;
        }
    }
}
