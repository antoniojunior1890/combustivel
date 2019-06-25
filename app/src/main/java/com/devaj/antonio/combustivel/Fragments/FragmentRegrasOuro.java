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

import com.devaj.antonio.combustivel.Adapter.InformacaoAdapter;
import com.devaj.antonio.combustivel.Adapter.RegraOuroAdapter;
import com.devaj.antonio.combustivel.Conect.SOService;
import com.devaj.antonio.combustivel.Model.Informacao;
import com.devaj.antonio.combustivel.Model.RegraOuro;
import com.devaj.antonio.combustivel.Model.RespostaInformacoes;
import com.devaj.antonio.combustivel.R;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

public class FragmentRegrasOuro extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RegraOuroAdapter regraOuroAdapter;
    private SwipeRefreshLayout mSwipeToRefresh;
    private static Context context;
    private static final String TAG = "COMBUSTIVEL";

    public FragmentRegrasOuro() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Fresco.initialize(getActivity()  ) ;
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_regra_ouro, container, false);

        context = getActivity();

        recyclerView = view.findViewById(R.id.recyclerviewRegraOuro);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        regraOuroAdapter = new RegraOuroAdapter(new ArrayList<RegraOuro>(0), getActivity());
        recyclerView.setAdapter(regraOuroAdapter);

        carregarRespostaRegraOuro();
        return view;
    }

    private void carregarRespostaRegraOuro() {


        List<RegraOuro> list = new ArrayList<RegraOuro>();

        list.add(new RegraOuro(1, "Elaborar análise de risco e obter as permissões de trabalho quando necessário.", "exAgsn44UWo"));
        list.add(new RegraOuro(2, "Fazer, testar e não violar os bloqueios de máquinas e equipamentos.", "99UsmzUAGmU"));
        list.add(new RegraOuro(3, "Não acessar áreas operacionais ou executar atividades sem fazer uso correto dos EPIs e EPCs obrigatórios.", "egOjFI3IWuA"));
        list.add(new RegraOuro(4, "Não realizar atividades sem estar habilitado, treinado, autorizado e apto pelo exame de saúde.", "09CFUewhtIM"));
        list.add(new RegraOuro(5, "Não trabalhar sob efeito de álcool e outras drogas.","sx8hsF9tWL0"));
        list.add(new RegraOuro(6, "Não acessar área isolada e sinalizada, onde ocorre a movimentação de cargas e equipamentos, sem a devida autorização.", "jww2fOSB9FA"));
        list.add(new RegraOuro(7, "Não realizar trabalhos em altura sem a utilização de cinto de segurança devidamente fixado.","iGoH93SqKBY"));
        list.add(new RegraOuro(8, "Não utilizar equipamentos, componentes e ferramentas defeituosas ou improvisadas.", "5pr68PzHrqE"));
        list.add(new RegraOuro(9, "Utilizar o cinto de segurança e respeitar os limites de velocidade na condução de veículos automotores e equipamentos móveis.", "W7Ptbetn65Q"));
        list.add(new RegraOuro(10, "Cumprir as medidas preventivas estabelecidas pela Vale para a saúde e segurança do viajante.", "sem video"));


        regraOuroAdapter.updateAnswers(list);
    }

}
