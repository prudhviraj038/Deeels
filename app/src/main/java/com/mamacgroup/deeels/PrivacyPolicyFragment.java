package com.mamacgroup.deeels;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Chinni on 04-05-2016.
 */
public class PrivacyPolicyFragment extends Fragment {
    TextView header,des;
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public  void con_shopping();
        public  void to_checkout(ArrayList<CartItem> cartItem);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallBack = (NavigationActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement Listner");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.whatdeel_fragment, container, false);
        return rootview;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getView();
        header=(TextView)v.findViewById(R.id.header_deeels);
        des=(TextView)v.findViewById(R.id.abt_deeeels);

        header.setText(Settings.getword(getActivity(),"privacy_policy"));
        des.setText(Html.fromHtml(Settings.getSettings(getActivity(), "privacy_policy"+Settings.get_lan(getActivity()))));
    }
}