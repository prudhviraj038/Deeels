package com.mamacgroup.deeels;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ContactUsFragment extends Fragment {
    TextView contact_us_txt,address_contact,name_tv,email_tv,mobile_tv,msg_tv;
    EditText name,email_address,phone_number,message;
    
    FragmentTouchListner mCallback;
    String head;
    public interface FragmentTouchListner {
        public  void  back();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contct_us_screen, container, false);

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (NavigationActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement Listner");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getView();
        name_tv=(TextView)v.findViewById(R.id.sta_con_name);
        name_tv.setText(Settings.getword(getActivity(), "name"));
        email_tv=(TextView)v.findViewById(R.id.sta_con_email);
        email_tv.setText(Settings.getword(getActivity(),"email_address"));
        mobile_tv=(TextView)v.findViewById(R.id.sta_con_mobile);
        mobile_tv.setText(Settings.getword(getActivity(), "mobile"));
        msg_tv=(TextView)v.findViewById(R.id.sta_con_msg);
        msg_tv.setText(Settings.getword(getActivity(), "msg"));
        LinearLayout contact_us=(LinearLayout)v.findViewById(R.id.contact_us);
        contact_us_txt=(TextView)v.findViewById(R.id.contact_us_tv);
        contact_us_txt.setText(Settings.getword(getActivity(),"contact_us"));
        address_contact=(TextView)v.findViewById(R.id.address_contact);
        address_contact.setText(Html.fromHtml(Settings.getSettings(getActivity(), "contact" + Settings.get_lan(getActivity()))));
        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validatedata();
            }
        });
        name = (EditText) v.findViewById(R.id.et_contctus_name);
        email_address = (EditText) v.findViewById(R.id.et_contactus_email);
        phone_number = (EditText) v.findViewById(R.id.et_contactus_mobile);
        message = (EditText) v.findViewById(R.id.et_contact_msg);

    }

  private void validatedata(){
      final String namee = name.getText().toString();
      final String email = email_address.getText().toString();
      final String phone = phone_number.getText().toString();
      final String msg = message.getText().toString();
        if(namee.equals("")){
//            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_name"), false);
            Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_name"), Toast.LENGTH_SHORT).show();
        }else if(email.equals("")){
//            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_email"), false);
            Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_email"),Toast.LENGTH_SHORT).show();
        }else if(phone.equals("")){
//            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_mobile"), false);
            Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_mobile"),Toast.LENGTH_SHORT).show();
        }else if(msg.equals("")){
//            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_message"), false);
            Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_message"),Toast.LENGTH_SHORT).show();
        }else{
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(Settings.getword(getActivity(), "please_wait"));
            progressDialog.show();
            String url = null;

            try {
                url = Settings.SERVERURL + "contact-us.php?phone=+965" + phone +
                        "&name=" + URLEncoder.encode(namee, "utf-8") +
                        "&email=" + URLEncoder.encode(email, "utf-8")
                        + "&message=" + URLEncoder.encode(msg, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Log.e("register url", url);
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    progressDialog.dismiss();
                    Log.e("response is: ", jsonObject.toString());
                    try {
                        String reply=jsonObject.getString("status");
                        if(reply.equals("Success")) {
                            String msg = jsonObject.getString("message");
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                            mCallback.back();
                        }
                        else {
                            String msg = jsonObject.getString("message");
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO Auto-generated method stub
                    Log.e("error response is:", error.toString());
                    if (progressDialog != null)
                        progressDialog.dismiss();
                    Toast.makeText(getActivity(), Settings.getword(getActivity(), "server_not_connected"), Toast.LENGTH_SHORT).show();

                }
            });

            AppController.getInstance().addToRequestQueue(jsObjRequest);
        }
        }
         

}

