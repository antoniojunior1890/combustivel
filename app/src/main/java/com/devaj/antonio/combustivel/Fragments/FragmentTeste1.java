package com.devaj.antonio.combustivel.Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devaj.antonio.combustivel.Conect.SOService;
import com.devaj.antonio.combustivel.Conect.UdacityService;
import com.devaj.antonio.combustivel.Model.Course;
import com.devaj.antonio.combustivel.Model.Instructors;
import com.devaj.antonio.combustivel.Model.RespostaKaizen;
import com.devaj.antonio.combustivel.Model.UdacityCatalog;
import com.devaj.antonio.combustivel.R;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentTeste1 extends Fragment {
    private static final String TAG = "COMBUSTIVEL";
    private static Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_teste1, container, false);

        context = getActivity();

        OkHttpClient client = new OkHttpClient.Builder()
//                .addNetworkInterceptor(new InterceptorCache(getActivity()))
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//                .addInterceptor(new LoggingInterceptor())
//                .addNetworkInterceptor(provideCacheInterceptor())
//                .addInterceptor(provideOffCacheInterceptor())
//                .connectTimeout(3, TimeUnit.SECONDS)
//                .readTimeout(3,TimeUnit.SECONDS)
//                .writeTimeout(3,TimeUnit.SECONDS)
//                .cache(new Cache(new File(context.getCacheDir(), "DIRTESTE"), 5 * 1024 * 1024))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UdacityService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        UdacityService service = retrofit.create(UdacityService.class);
        Call<UdacityCatalog> requestCatalog =  service.listCatalog();

        requestCatalog.enqueue(new Callback<UdacityCatalog>() {
            @Override
            public void onResponse(Call<UdacityCatalog> call, Response<UdacityCatalog> response) {
                if(!response.isSuccessful()){
                    Log.i(TAG,"cod de erro : "+ response.code());
                }
                else {
                    UdacityCatalog catalog = response.body();
                    for (Course c : catalog.courses){
                        Log.i(TAG, String.format("%s : %s ", c.title,c.subtitle));
                        for (Instructors i : c.instructors){
                            Log.i(TAG, i.name);
                        }
                        Log.i(TAG, "-----------------------------");
                    }
                }
            }

            @Override
            public void onFailure(Call<UdacityCatalog> call, Throwable t) {
                Log.i(TAG, "Falhou " + t.getMessage());
            }
        });

        return view;
    }

    public static Interceptor provideCacheInterceptor(){
        return new Interceptor(){
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Response response = chain.proceed(chain.request());

                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(1, TimeUnit.MINUTES)
                        .build();

                return response.newBuilder()
                        .header("Cache-Control",cacheControl.toString())
                        .build();
            }
        };
    }

    public static Interceptor provideOffCacheInterceptor(){
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                if(!isNet(context)){
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(7, TimeUnit.DAYS)
                            .build();

                    request = request.newBuilder()
                            .cacheControl(cacheControl)
                            .build();
                }
                return chain.proceed(request);
            }
        };
    }

    public static boolean isNet(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
