package com.devaj.antonio.combustivel.Fragments;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devaj.antonio.combustivel.Activity.MainActivity;
import com.devaj.antonio.combustivel.Adapter.InformacaoAdapter;
import com.devaj.antonio.combustivel.Conect.SOService;
import com.devaj.antonio.combustivel.Model.Informacao;
import com.devaj.antonio.combustivel.Model.RespostaInformacoes;
import com.devaj.antonio.combustivel.Model.RespostaKaizen;
import com.devaj.antonio.combustivel.R;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

public class FragmentInformacoes extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private InformacaoAdapter informacaoAdapter;
    private SwipeRefreshLayout mSwipeToRefresh;
    private static Context context;
    private static final String TAG = "COMBUSTIVEL";
    private List<Informacao> informacaoList;
    private Call<RespostaInformacoes> respostaInformacoesCall;
//    private String datainicial;
//    private String datafinal;
//    private Calendar c;
//    private boolean verificaProg = false;
//    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public FragmentInformacoes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        MainActivity mainActivity = (MainActivity) getActivity();
//        Log.i(TAG,mainActivity.usuarioDao.buscarUsuario().getNome());

//        Log.d(TAG, "Fragment: Metodo onCreateView() chamado");
        Fresco.initialize(getActivity()  ) ;
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_informacoes, container, false);

        informacaoList = new ArrayList<Informacao>(0);
        context = getActivity();

        recyclerView = view.findViewById(R.id.recyclerviewKaizen);
        recyclerView.setHasFixedSize(true);

//        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        informacaoAdapter = new InformacaoAdapter(informacaoList, getActivity());
        recyclerView.setAdapter(informacaoAdapter);

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
////                Log.i(TAG, "fl : "+ informacaoList.size() +" - "+(linearLayoutManager.findLastCompletelyVisibleItemPosition()+1));
//                if(informacaoList.size() == linearLayoutManager.findLastCompletelyVisibleItemPosition()+1){
////                    informacaoAdapter.addProgressBar();
////                    recyclerView.scrollToPosition(informacaoList.size() - 1);
//                    if(!verificaProg){
//
//
////                        contMesAtual += 1;
//
////                        c = Calendar.getInstance();
//                        c.add(Calendar.MONTH,-1);
//                        c.set(Calendar.DAY_OF_MONTH,1);
//                        datainicial = format.format(c.getTime());
////
//                        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
//                        datafinal = format.format(c.getTime());
//
//
//                        carregarRespostaInformacao(datainicial, datafinal);
//                    }
//                }
//            }
//        });

        mSwipeToRefresh = view.findViewById(R.id.swiperefresh);

        // Seta o Listener para atualizar o conteudo quando o gesto for feito
        mSwipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                mSwipeToRefresh.setRefreshing(false);

//                informacaoList.clear();
//                informacaoAdapter.updateAnswers(informacaoList);
//
//                c = Calendar.getInstance();
//                c.set(Calendar.DAY_OF_MONTH,1);
//                datainicial = format.format(c.getTime());
////
//                c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
//                datafinal = format.format(c.getTime());

                carregarRespostaInformacao() ;
            }
        });


        mSwipeToRefresh.setRefreshing(true);
////
//                c = Calendar.getInstance();
//                c.set(Calendar.DAY_OF_MONTH,1);
//                datainicial = format.format(c.getTime());
////
//                c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
//                datafinal = format.format(c.getTime());


        carregarRespostaInformacao();

//        c = Calendar.getInstance();
////        c.set(Calendar.DAY_OF_MONTH,1);
////        c.set(Calendar.MONTH, 1);
////        c.set(Calendar.YEAR,2018);
////        Log.i(TAG,"Ultimo dia: "+ c.getActualMaximum(Calendar.DAY_OF_MONTH));
//        Log.i(TAG,"data : "+ format.format(c.getTime()));
//        c.add(Calendar.MONTH,1);
//        Log.i(TAG,"data + 1 mes : "+ format.format(c.getTime()));
//
//        Date data = new Date(System.currentTimeMillis());
//
//        Log.i(TAG,"Data atual : "+ format.format(data));
//
//        data = new Date("2018-01-07");
////
//        Log.i(TAG,"teste Date() = "+data);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Log.d(TAG, "Fragment: Metodo onActivityCreated() chamado");
        if(savedInstanceState != null) {
//            savedInstanceState.getString("someVarB");
            String someVarB = savedInstanceState.getString("someVarB");
            Log.d(TAG, "Dados recuperado : " + someVarB);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
//        Log.d(TAG, "Fragment: Metodo onStart() chamado");
    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.d(TAG, "Fragment: Metodo onResume() chamado");
    }

    @Override
    public void onPause() {
        super.onPause();
//        Log.d(TAG, "Fragment: Metodo onPause() chamado");
    }

    @Override
    public void onStop() {
        super.onStop();
//        Log.d(TAG, "Fragment: Metodo onStop() chamado");
    }


//    void performSaveInstanceState(Bundle outState) {
//        onSaveInstanceState(outState);
//    }
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        Log.d(TAG, "Fragment: Metodo onSavedInstanceState() chamado");
//        outState.putString("someVarB", "Dados salvo");
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        Bundle state = new Bundle();
//        performSaveInstanceState(state);
//        Log.d(TAG, "Fragment: Metodo onDestroyView() chamado");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        respostaInformacoesCall.cancel();
//        Log.d(TAG, "Fragment: Metodo onDestroy() chamado");
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        Log.d(TAG, "Fragment: Metodo onDetach() chamado");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        Log.d(TAG, "Fragment: Metodo onAttach() chamado");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.d(TAG, "Fragment: Metodo onCreate() chamado");
    }

    private void carregarRespostaInformacao() {

//        Log.i(TAG, "INI: "+datainicial+" FIM: "+datafinal);
//        verificaProg = true;
//        informacaoAdapter.addProgressBar();
//        recyclerView.scrollToPosition(informacaoList.size()-1);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addNetworkInterceptor(provideCacheInterceptor())
                .addInterceptor(provideOffCacheInterceptor())
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(2,TimeUnit.SECONDS)
                .writeTimeout(2,TimeUnit.SECONDS)
                .cache(new Cache(new File(context.getCacheDir(), "DIR_INFO"), 5 * 1024 * 1024))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SOService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        SOService service = retrofit.create(SOService.class);
        respostaInformacoesCall =  service.getRespostaInformacoes();



        respostaInformacoesCall.enqueue(new Callback<RespostaInformacoes>() {
            @Override
            public void onResponse(Call<RespostaInformacoes> call, Response<RespostaInformacoes> response) {
//                informacaoAdapter.removeProgressBar();
                if(response.isSuccessful()){
//                    verificaProg = false;
                    if (response.raw().cacheResponse() != null) {
                        Log.i(TAG, "retorno do chache");
                    }
                    if (response.raw().networkResponse() != null) {
                        Log.i(TAG, "retorno da NET");
                    }
                    //informacaoAdapter.updateAnswers(response.body().informacoes);
//                    informacaoList = response.body().informacoes;
//                    informacaoAdapter.updateAnswers(informacaoList);
                    informacaoAdapter.updateAnswers(response.body().informacoes);
                    mSwipeToRefresh.setRefreshing(false);
//                    if(response.body().getCount() == informacaoList.size()){
//                        verificaProg = true;
//                        Snackbar.make(getView(), "Não há mais dados para atualizar.", Snackbar.LENGTH_LONG)
//                                .setAction("Action", null).show();
//                    }else {
//                        List<Informacao> posts = response.body().informacoes;
//                        if(posts != null){
//                            for(Informacao post : posts){
//                                informacaoAdapter.addListItem(post, informacaoList.size());
//                            }
//                            Log.i(TAG, "List cont: "+informacaoList.size());
//                        }else {
//                            c.add(Calendar.MONTH,-1);
//                            c.set(Calendar.DAY_OF_MONTH,1);
//                            String datainicial = format.format(c.getTime());
////
//                            c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
//                            String datafinal = format.format(c.getTime());
//
//
//                            carregarRespostaInformacao(datainicial, datafinal);
//                        }
//                    }

//                    List<Informacao> posts = response.body().informacoes;
//                    if(posts != null){
//                        for(Informacao post : posts){
//                            informacaoAdapter.addListItem(post, informacaoList.size());
//                        }
//                        Log.i(TAG, "List cont: "+informacaoList.size());
//                    }else {
//                        Snackbar.make(getView(), "Não há mais dados para atualizar.", Snackbar.LENGTH_LONG)
//                                .setAction("Action", null).show();
//                    }


                }
            }
            @Override
            public void onFailure(Call<RespostaInformacoes > call, Throwable t) {
//                if(t.getMessage().equals("connect timed out")){
//                    Snackbar.make(getView(), "Conexão lenta. ", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                }else {
                Snackbar.make(getView(), "Lista de informações não foi atualizada.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                mSwipeToRefresh.setRefreshing(false);
//                }

            }

//            @Override
//            public void onFailure(Call<RespostaInformacoes> call, Throwable t) {
//                informacaoAdapter.removeProgressBar();
//                if(respostaInformacoesCall.isCanceled()){
//                    Log.i(TAG, "consulta cancelada!!");
//                }else {
//                    if(getView() != null){
//                        Snackbar.make(getView(), "Lista de informações não foi atualizada.", Snackbar.LENGTH_LONG)
//                                .setAction("Tentar novamente", new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        String datainicial = format.format(c.getTime());
////
//                                        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
//                                        String datafinal = format.format(c.getTime());
//
//                                        carregarRespostaInformacao(datainicial, datafinal);
//                                    }
//                                }).show();
//                    }
//                }
//                mSwipeToRefresh.setRefreshing(false);
//            }
        });
    }



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
                            .maxStale(1, TimeUnit.MINUTES)
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
