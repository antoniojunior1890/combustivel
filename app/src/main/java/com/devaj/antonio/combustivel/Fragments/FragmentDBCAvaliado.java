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
import android.widget.TextView;

import com.devaj.antonio.combustivel.Activity.MainActivity;
import com.devaj.antonio.combustivel.Adapter.PontoDBCAdapter;
import com.devaj.antonio.combustivel.Conect.SOService;
import com.devaj.antonio.combustivel.Model.Ponto;
import com.devaj.antonio.combustivel.Model.RespostaPonto;
import com.devaj.antonio.combustivel.R;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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

public class FragmentDBCAvaliado extends Fragment {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeToRefresh;
    private static Context context;
    private PontoDBCAdapter pontoDBCAdapter;
    private static final String TAG = "COMBUSTIVEL";
    private RecyclerView.LayoutManager layoutManager;
    private TextView pontoTotal;

    private String matriculaAvaliado;

    public FragmentDBCAvaliado() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_dbc_avaliado , container, false);

        pontoTotal =  view.findViewById(R.id.totalpontos);

        MainActivity mainActivity = (MainActivity) getActivity();
        matriculaAvaliado = mainActivity.usuarioDao.buscarUsuario().getMatricula();

        context = getActivity();

        recyclerView = view.findViewById(R.id.recyclerviewDBCAvaliado);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        pontoDBCAdapter = new PontoDBCAdapter(new ArrayList<Ponto>(0), getActivity());
        recyclerView.setAdapter(pontoDBCAdapter);

        mSwipeToRefresh = view.findViewById(R.id.swiperefresh);

        mSwipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                carregarRespostaPontos(matriculaAvaliado) ;
            }
        });

        mSwipeToRefresh.setRefreshing(true);
        carregarRespostaPontos(matriculaAvaliado);

        return view;
    }

    private void carregarRespostaPontos(String matriculaAvaliado) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//                .addNetworkInterceptor(provideCacheInterceptor())
//                .addInterceptor(provideOffCacheInterceptor())
//                .connectTimeout(3,TimeUnit.SECONDS)
//                .readTimeout(2,TimeUnit.SECONDS)
//                .writeTimeout(2,TimeUnit.SECONDS)
//                .cache(new Cache(new File(context.getCacheDir(), "DIR"), 5 * 1024 * 1024))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SOService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        SOService service = retrofit.create(SOService.class);
        Call<RespostaPonto> respostaPontoDBCCall =  service.getRespostaPonto(matriculaAvaliado);

        respostaPontoDBCCall.enqueue(new Callback<RespostaPonto>() {
            @Override
            public void onResponse(Call<RespostaPonto> call, Response<RespostaPonto> response) {
//                Log.i(TAG, response.body().pontos.toString() );
                if(response.body().pontos.size() > 0){
                    pontoDBCAdapter.updateAnswers(response.body().pontos);

                    Integer totalpontos = 0;
                    for(Ponto p : response.body().pontos){
                        totalpontos += p.getPontuacao();
                    }
                    pontoTotal.setText(totalpontos.toString());
                }else {
                    Snackbar.make(getView(), "Avaliado não possui pontos na DBC.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                mSwipeToRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<RespostaPonto> call, Throwable t) {
                Snackbar.make(getView(), "Lista de pontos não foi atualizada.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                mSwipeToRefresh.setRefreshing(false);
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
