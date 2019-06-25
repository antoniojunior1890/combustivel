package com.devaj.antonio.combustivel.Util;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.devaj.antonio.combustivel.Fragments.FragmentCart;


public class CustomFragmentPageAdapter extends FragmentPagerAdapter {

    private static final String TAG = CustomFragmentPageAdapter.class.getSimpleName();

    private static final int FRAGMENT_COUNT = 14;

    public CustomFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentCart.newInstance(position + 1);
//        switch (position){
//            case 0:
//                return new FragmentTeste1();
//            case 1:
//                return new FragmentTeste2();
//            case 2:
//                return new FragmentTeste1();
//            case 3:
//                return new FragmentTeste2();
//            /*case 1:
//                return new PlaylistFragment();
//            case 2:
//                return new AlbumFragment();
//            case 3:
//                return new ArtistFragment();*/
//        }
//        return null;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        switch (position){
//            case 0:
//                return "Songs";
////            case 1:
////                return "Playlists";
////            case 2:
////                return "Albums";
////            case 3:
////                return "Artists";
//        }
//        return null;
//    }
}
