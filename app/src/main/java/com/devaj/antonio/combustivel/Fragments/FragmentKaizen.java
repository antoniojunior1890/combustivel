package com.devaj.antonio.combustivel.Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devaj.antonio.combustivel.Adapter.KaizenAdapter;
import com.devaj.antonio.combustivel.Conect.SOService;
import com.devaj.antonio.combustivel.Model.Kaizen;
import com.devaj.antonio.combustivel.Model.RespostaKaizen;
import com.devaj.antonio.combustivel.R;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
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


public class FragmentKaizen extends Fragment {
    private RecyclerView recyclerView;
    private SOService mService;
    private RecyclerView.LayoutManager layoutManager;
    private KaizenAdapter kaizenAdapter;
    private SwipeRefreshLayout mSwipeToRefresh;
    private static Context context;
    private static final String TAG = "COMBUSTIVEL";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_kaizen, container, false);

        context = getActivity();

        recyclerView = view.findViewById(R.id.recyclerviewKaizen);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        kaizenAdapter = new KaizenAdapter(new ArrayList<Kaizen>(0), getActivity());
        recyclerView.setAdapter(kaizenAdapter);

        mSwipeToRefresh = view.findViewById(R.id.swiperefresh);

        mSwipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                carregarRespostaKaizen() ;
            }
        });

        mSwipeToRefresh.setRefreshing(true);
        carregarRespostaKaizen();

        return view;
    }

    private void carregarRespostaKaizen() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addNetworkInterceptor(provideCacheInterceptor())
                .addInterceptor(provideOffCacheInterceptor())
                .connectTimeout(3,TimeUnit.SECONDS)
                .readTimeout(2,TimeUnit.SECONDS)
                .writeTimeout(2,TimeUnit.SECONDS)
                .cache(new Cache(new File(context.getCacheDir(), "DIR"), 5 * 1024 * 1024))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SOService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        SOService service = retrofit.create(SOService.class);
        Call<RespostaKaizen> respostaKaizenCall =  service.getRespostaKaizen();

        respostaKaizenCall.enqueue(new Callback<RespostaKaizen>() {
            @Override
            public void onResponse(Call<RespostaKaizen> call, Response<RespostaKaizen> response) {
                    if (!response.isSuccessful()) {
                        Log.i(TAG, "cod de erro : " + response.code());
                    } else {
                        Log.i(TAG, "cod de erro ok : " + response.code());
                        kaizenAdapter.updateAnswers(response.body().kaizens);
                        mSwipeToRefresh.setRefreshing(false);
//                        if (response.raw().cacheResponse() != null) {
//                            Log.i(TAG, "retorno do chache");
//                        }
//                        if (response.raw().networkResponse() != null) {
//                            Log.i(TAG, "retorno da NET");
//                        }
                    }
            }

            @Override
            public void onFailure(Call<RespostaKaizen> call, Throwable t) {
//                if(t.getMessage().equals("connect timed out")){
//                    Snackbar.make(getView(), "Conexão lenta. ", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                }else {
                    Snackbar.make(getView(), "Lista de kaizens não foi atualizada.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    mSwipeToRefresh.setRefreshing(false);
//                }

            }
        });

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
                            .maxStale(8, TimeUnit.DAYS)
                            .build();

                    request = request.newBuilder()
                            .cacheControl(cacheControl)
                            .build();
//                    return chain.proceed(request);
                }else {
                    try {
                        return chain.proceed(request);
                    }catch (SocketTimeoutException e){
                        if(e.getMessage().equals("connect timed out")){
//                            Log.i(TAG,"Vai:"+e.getMessage());
                            CacheControl cacheControl = new CacheControl.Builder()
                                    .maxStale(1, TimeUnit.DAYS)
                                    .build();

                            request = request.newBuilder()
                                    .cacheControl(cacheControl)
                                    .build();
                        }
                    }
                }
                return chain.proceed(request);

            }
        };
    }

    public static Interceptor getCodeErro(){
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                try {
                    return chain.proceed(request);
                }catch (SocketTimeoutException e){
                    if(e.getMessage().equals("connect timed out")){
                        Log.i(TAG,"Vai:"+e.getMessage());
                        CacheControl cacheControl = new CacheControl.Builder()
                                .maxStale(8, TimeUnit.DAYS)
                                .build();

                        request = request.newBuilder()
                                .cacheControl(cacheControl)
                                .build();
                        return chain.proceed(request);
                    }
                    return chain.proceed(chain.request());
                }
            }
        };
    }


    public static boolean isNet(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

}
