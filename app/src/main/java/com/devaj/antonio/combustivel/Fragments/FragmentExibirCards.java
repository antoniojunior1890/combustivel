package com.devaj.antonio.combustivel.Fragments;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devaj.antonio.combustivel.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.rd.PageIndicatorView;


@SuppressLint("ValidFragment")
public class FragmentExibirCards extends Fragment {
    private static final String TAG = "COMBUSTIVEL";
    private static final String ARG_SECTION_NUMBER = "section_number";
    private final String fragment;

    @SuppressLint("ValidFragment")
    public FragmentExibirCards(String fragment) {
        this.fragment = fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fragment_teste2, container, false);
        ViewPager viewPager = view.findViewById(R.id.viewPager);

//        Log.i(TAG, "getArg : "+getArguments().getInt(ARG_SECTION_NUMBER));

        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(fragment);
        viewPager.setAdapter(customPagerAdapter);


        PageIndicatorView pageIndicatorView = view.findViewById(R.id.pageIndicatorView);
        pageIndicatorView.setViewPager(viewPager);
        if(fragment.equals("SSMA"))
            pageIndicatorView.setPadding(6);








//        UsuarioDaoImpl usuarioDao = new UsuarioDaoImpl(getContext());

//        Usuario u = new Usuario();
//        u.setMatricula("00");
//        u.setNome("Ant");
//        u.setSenha("99");
//
//        Log.i(TAG,""+usuarioDao.inserirUsuario(u));
;
//        Usuario u2 = usuarioDao.buscarUsuarioPorNome("Ant");
//        if(u2 != null)
//            Log.i(TAG,""+u2.getSenha());
//        Log.i(TAG,""+usuarioDao.pegarTamanho());
        return view;
    }

    private class CustomPagerAdapter extends PagerAdapter {

        private final String fragment;

        public CustomPagerAdapter(String fragment) {
            this.fragment = fragment;
        }

        public Object instantiateItem(ViewGroup container, int position){
            final View view = LayoutInflater.from(container.getContext()).inflate(R.layout.view_page, container,false);

//            TextView orientation = view.findViewById(R.id.title);
//            orientation.setText(Configuration.ORIENTATION_PORTRAIT == this.orientation ? "PORTRAIT" : "LANDSCAÈ");

            Drawable drawable;

            if(fragment.equals("RE")) {

                Resources res = getResources();
                String mDrawableName = "cire" + (position + 1);
                int resID = getResources().getIdentifier(mDrawableName, "drawable", "com.devaj.antonio.combustivel");
                drawable = res.getDrawable(resID);
            }else {
                Resources res = getResources();
                String mDrawableName = "cissma" + (position + 1);
                int resID = getResources().getIdentifier(mDrawableName, "drawable", "com.devaj.antonio.combustivel");
                drawable = res.getDrawable(resID);
            }

            PhotoView photoView = (PhotoView) view.findViewById(R.id.photo_view);
            photoView.setImageDrawable(drawable);

            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            if(fragment.equals("RE"))
                return 8;
            else
                return 15;
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
//            return object.
        }
    }

}
