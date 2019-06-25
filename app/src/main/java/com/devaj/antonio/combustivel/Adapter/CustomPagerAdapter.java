package com.devaj.antonio.combustivel.Adapter;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devaj.antonio.combustivel.R;

public class CustomPagerAdapter extends PagerAdapter {
    private int orientation;

    public CustomPagerAdapter(int orientation) {
        this.orientation = orientation;
    }

    public Object instantiateItem(ViewGroup container, int position){
        final View view = LayoutInflater.from(container.getContext()).inflate(R.layout.view_page, container,false);

        TextView orientation = view.findViewById(R.id.title);
        orientation.setText(Configuration.ORIENTATION_PORTRAIT == this.orientation ? "PORTRAIT" : "LANDSCAÈ");

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object){
        return POSITION_UNCHANGED;
    }
}
