package com.devaj.antonio.combustivel.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devaj.antonio.combustivel.Model.Ponto;
import com.devaj.antonio.combustivel.R;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by antonio on 26/09/17.
 */

public class PontoDBCAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {
    private List<Ponto> pontosDBC;
    private Context context;
    private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

    public PontoDBCAdapter(List<Ponto> pontosDBC, Context context) {
        this.pontosDBC = pontosDBC;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        view = LayoutInflater.from(context).inflate(R.layout.card_ponto_dbc, parent, false);

        PontoDBCViewHolder holder = new PontoDBCViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        PontoDBCViewHolder pontoDBCViewHolder = (PontoDBCViewHolder) holder;

        final Ponto pontoDBC = pontosDBC.get(position);

        pontoDBCViewHolder.ponto.setText(pontoDBC.getPontuacao().toString());
        pontoDBCViewHolder.data.setText(format.format(pontoDBC.getData()));
        pontoDBCViewHolder.tabela.setText(pontoDBC.getTabela());

    }

    @Override
    public int getItemCount() {
        return pontosDBC.size();
//        return informacoes.isEmpty() ? 0 : informacoes.size();
    }

    @Override
    public int getItemViewType(int position){
        return position;
//        return position == mProgressPosition ? 1 : 0;
    }


    public class PontoDBCViewHolder extends RecyclerView.ViewHolder{

//        final ImageView imagemInformacao;
        final TextView ponto;
        final TextView data;
        final TextView tabela;


        public PontoDBCViewHolder(View itemView) {
            super(itemView);
//            data = itemView.findViewById(R.id.imagemInformacao);
            ponto =  itemView.findViewById(R.id.pontoDBC);
            data =  itemView.findViewById(R.id.dataDBC);
            tabela = itemView.findViewById(R.id.tabela);


        }
    }

    public void updateAnswers(List<Ponto> items) {
        pontosDBC = items;
        notifyDataSetChanged();
    }



}
