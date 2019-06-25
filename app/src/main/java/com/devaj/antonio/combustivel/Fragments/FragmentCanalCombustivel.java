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
import com.devaj.antonio.combustivel.Adapter.VideoAdapter;
import com.devaj.antonio.combustivel.Conect.SOService;
import com.devaj.antonio.combustivel.Model.RespostaVideos;
import com.devaj.antonio.combustivel.Model.Video;
import com.devaj.antonio.combustivel.R;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

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

public class FragmentCanalCombustivel extends Fragment {

    private YouTubeThumbnailView youTubeThumbnailView;
    private YouTubeThumbnailLoader youTubeThumbnailLoader;
    public static final String API_KEY = "AIzaSyAcSPSPT2E_eYTsvQe3RxGXuBUEPn7lOEI";
    public static final String VIDEO_ID = "qTfB1xG2wBE";
    private RecyclerView recyclerView;
    private SOService mService;
    private RecyclerView.LayoutManager layoutManager;
    private VideoAdapter videoAdapter;
    private KaizenAdapter kaizenAdapter;
    private SwipeRefreshLayout mSwipeToRefresh;
    private static Context context;
    private static final String TAG = "COMBUSTIVEL";

    public FragmentCanalCombustivel() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video_youtube, container, false);
        context = getActivity();

        recyclerView = view.findViewById(R.id.recyclerviewVideo);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        videoAdapter = new VideoAdapter(new ArrayList<Video>(0),getActivity());
        recyclerView.setAdapter(videoAdapter);

        mSwipeToRefresh = view.findViewById(R.id.swiperefresh);

        mSwipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                carregarRespostaVideos();
            }
        });

        mSwipeToRefresh.setRefreshing(true);

//        carregarRespostaKaizen();
        carregarRespostaVideos();

//        youTubeThumbnailView = (YouTubeThumbnailView)view.findViewById(R.id.youtubethumbnailview);
//        youTubeThumbnailView.initialize(API_KEY, this);
//        youTubeThumbnailView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), YouTube.class);
//                intent.putExtra("link", "qTfB1xG2wBE");
//                startActivity(intent);
//            }
//        });




        return  view;
    }

    private void carregarRespostaVideos() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addNetworkInterceptor(provideCacheInterceptor())
                .addInterceptor(provideOffCacheInterceptor())
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(2,TimeUnit.SECONDS)
                .writeTimeout(2,TimeUnit.SECONDS)
                .cache(new Cache(new File(context.getCacheDir(), "DIRVIDEO"), 5 * 1024 * 1024))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SOService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        SOService service = retrofit.create(SOService.class);
        Call<RespostaVideos> respostaVideosCall =  service.getRespostaVideos();

        respostaVideosCall.enqueue(new Callback<RespostaVideos>() {
            @Override
            public void onResponse(Call<RespostaVideos> call, Response<RespostaVideos> response) {
                if (!response.isSuccessful()) {
                    Log.i(TAG, "cod de erro : " + response.code());
                } else {
                    videoAdapter.updateAnswers(response.body().videos);
                    mSwipeToRefresh.setRefreshing(false);
                    if (response.raw().cacheResponse() != null) {
                        Log.i(TAG, "retorno do chache");
                    }
                    if (response.raw().networkResponse() != null) {
                        Log.i(TAG, "retorno da NET");
                    }
                }
            }

            @Override
            public void onFailure(Call<RespostaVideos> call, Throwable t) {
//                if(t.getMessage().equals("connect timed out")){
//                    Snackbar.make(getView(), "Conexão lenta.", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                }else {
                    mSwipeToRefresh.setRefreshing(false);
                    Snackbar.make(getView(), "Lista de videos não foi atualizada.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
//                }

//                Log.i(TAG, "Falhou " + t.getMessage());
            }
        });
    }


//    public static Interceptor provideCacheInterceptor(){
//        return new Interceptor(){
//            @Override
//            public okhttp3.Response intercept(Chain chain) throws IOException {
//                okhttp3.Response response = chain.proceed(chain.request());
//
//                CacheControl cacheControl = new CacheControl.Builder()
//                        .maxAge(1, TimeUnit.MINUTES)
//                        .build();
//
//                return response.newBuilder()
//                        .header("Cache-Control",cacheControl.toString())
//                        .build();
//            }
//        };
//    }
//
//    public static Interceptor provideOffCacheInterceptor(){
//        return new Interceptor() {
//            @Override
//            public okhttp3.Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//
//                if(!isNet(context)){
//                    CacheControl cacheControl = new CacheControl.Builder()
//                            .maxStale(1, TimeUnit.MINUTES)
//                            .build();
//
//                    request = request.newBuilder()
//                            .cacheControl(cacheControl)
//                            .build();
////                    return chain.proceed(request);
//                }else {
//                    try {
//                        return chain.proceed(request);
//                    }catch (SocketTimeoutException e){
//                        if(e.getMessage().equals("connect timed out")){
////                            Log.i(TAG,"Vai:"+e.getMessage());
//                            CacheControl cacheControl = new CacheControl.Builder()
//                                    .maxStale(1, TimeUnit.DAYS)
//                                    .build();
//
//                            request = request.newBuilder()
//                                    .cacheControl(cacheControl)
//                                    .build();
//                        }
//                    }
//                }
//                return chain.proceed(request);
//            }
//        };
//    }

    public static Interceptor provideCacheInterceptor(){
        return new Interceptor(){
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                Log.i(TAG,"COM NET");
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
                    Log.i(TAG,"SEM NET");
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
