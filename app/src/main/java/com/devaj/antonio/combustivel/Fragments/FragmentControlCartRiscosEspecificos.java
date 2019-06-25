package com.devaj.antonio.combustivel.Fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devaj.antonio.combustivel.R;
import com.devaj.antonio.combustivel.Util.CustomFragmentPageAdapter;
import com.devaj.antonio.combustivel.Util.CustomFragmentPageAdapterRiscosEpecificos;


public class FragmentControlCartRiscosEspecificos extends Fragment {

    private static final String TAG = FragmentControlCartRiscosEspecificos.class.getSimpleName();

//    private TabLayout tabLayout;
    private ViewPager viewPager;


    public FragmentControlCartRiscosEspecificos() {
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control_cart_ssma, container, false);

//        tabLayout = (TabLayout)view.findViewById(R.id.tabs);
        viewPager = (ViewPager)view.findViewById(R.id.view_pager);

        viewPager.setAdapter(new CustomFragmentPageAdapterRiscosEpecificos(getChildFragmentManager()));
//        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}
