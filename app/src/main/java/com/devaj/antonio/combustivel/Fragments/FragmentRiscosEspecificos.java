package com.devaj.antonio.combustivel.Fragments;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devaj.antonio.combustivel.R;
import com.github.chrisbanes.photoview.PhotoView;

public class FragmentRiscosEspecificos extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public FragmentRiscosEspecificos(){}

    public static FragmentRiscosEspecificos newInstance(int sectionNumber) {
        FragmentRiscosEspecificos fragment = new FragmentRiscosEspecificos();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_riscos_especificos, container, false);

        Resources res = getResources();
        String mDrawableName = "cire"+getArguments().getInt(ARG_SECTION_NUMBER);
        int resID = getResources().getIdentifier(mDrawableName, "drawable", "com.devaj.antonio.combustivel");
        Drawable drawable = res.getDrawable(resID );

        //final ImageView imageView = (ImageView) view.findViewById(R.id.imageViewCartSSMA);

        //imageView.setImageDrawable(drawable );

//        ZoomageView demoView;
//        demoView = (ZoomageView)view.findViewById(R.id.demoView);


//        Picasso.with(getContext()).load(R.drawable.cissma2).into(imageView);
//        TextView textView = (TextView) view.findViewById(R.id.textViewTeste);
//        textView.setText(""+getArguments().getInt(ARG_SECTION_NUMBER));
        //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        PhotoView photoView = (PhotoView) view.findViewById(R.id.photo_view);
        photoView.setImageDrawable(drawable);

        return view;


    }


}
