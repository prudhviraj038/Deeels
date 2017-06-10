package com.mamacgroup.deeels;

/**
 * Created by HP on 8/22/2016.
 */

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;


public class SlidingImageAdapter extends PagerAdapter {


    private ArrayList<String> IMAGES;
    private LayoutInflater inflater;
    private Context context;
    HashMap<Integer,View> views;


    public SlidingImageAdapter(Context context, ArrayList<String> IMAGES) {
        this.context = context;
        this.IMAGES=IMAGES;
        inflater = LayoutInflater.from(context);
        views = new HashMap<>();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);


       // imageView.setImageResource(IMAGES.get(position));

//        ImageLoader imageLoader = CustomVolleyRequest.getInstance(context)
//                .getImageLoader();
//        imageLoader.get(String.valueOf(IMAGES.get(position)), ImageLoader.getImageListener(imageView,
//                R.drawable.test1, android.R.drawable
//                        .ic_dialog_alert));
//        imageView.setImageUrl(String.valueOf(IMAGES.get(position)), imageLoader);
//        imageView.setImageResource(IMAGES.get(position));
        Log.e("banner_promf", IMAGES.get(position));
        Picasso.with(context).load(IMAGES.get(position)).into(imageView);
        view.addView(imageLayout, 0);

        views.put(position,imageLayout);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }



}