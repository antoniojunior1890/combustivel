package com.devaj.antonio.combustivel.Adapter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by antonio on 13/09/17.
 */

public class InterceptorCache implements Interceptor {
    private static final String TAG = "COMBUSTIVEL";
    Context context;

    public InterceptorCache(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

            if (isNet(context)) {
                // 1 day
                Log.i(TAG,"com net request");
                request.newBuilder()
//                        .header("Cache-Control", "no-cache")
                        .header("Cache-Control", "only-if-cached")
//                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build();
            } else {
                // 4 weeks stale
                Log.i(TAG,"sem net request");
                request.newBuilder()
                        .header("Cache-Control", "public, max-stale=2419200")
//                        .cacheControl(CacheControl.FORCE_CACHE )
                        .build();
            }

        Response response = chain.proceed(request);

        // Re-write response CC header to force use of cache
        return response.newBuilder()
                .header("Cache-Control", "public, max-age=60") // 1 day
                .build();

    }

    public static boolean isNet(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}


//mariabreuss@gm
//102033

//    Response original = chain.proceed(chain.request());
//
//        if(isNet(context)){
//                Log.i(TAG,"com net");
//                int max = 60; // read from cache for 1 minute
//                return original.newBuilder()
//                .header("Cache-Control", "public, max-age=" + max)
//                .build();
//                }else {
//                Log.i(TAG,"sem net");
//                int stale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
//                return original.newBuilder()
//                .header("Cache-Control", "public, only-if-cached max-stale=" + stale)
//                .build();
//                }