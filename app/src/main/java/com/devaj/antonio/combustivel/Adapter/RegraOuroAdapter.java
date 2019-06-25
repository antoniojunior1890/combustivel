package com.devaj.antonio.combustivel.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.devaj.antonio.combustivel.Activity.FullscreenActivity;
import com.devaj.antonio.combustivel.Activity.LoginActivity;
import com.devaj.antonio.combustivel.Activity.MainActivity;
import com.devaj.antonio.combustivel.Model.Kaizen;
import com.devaj.antonio.combustivel.Model.RegraOuro;
import com.devaj.antonio.combustivel.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import java.util.List;

//import com.bumptech.glide.Glide;

/**
 * Created by antonio on 18/05/17.
 */
public class RegraOuroAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private List<RegraOuro> regraOuroList;
    private Context context;
    private static final String API_KEY = "AIzaSyAcSPSPT2E_eYTsvQe3RxGXuBUEPn7lOEI";
    private static final String TAG = "COMBUSTIVEL";

    public RegraOuroAdapter(List<RegraOuro> regraOuroList, Context context) {
        this.regraOuroList = regraOuroList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(context).inflate(R.layout.card_regras_ouro, parent, false);

        RegraOuroViewHolder holder = new RegraOuroViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        RegraOuroViewHolder regraOuroViewHolder = (RegraOuroViewHolder) holder;

        final RegraOuro regraOuro = regraOuroList.get(position);

//        Log.i(TAG, regraOuro.getNumero().toString());

        regraOuroViewHolder.numero.setText(regraOuro.getNumero().toString());
        regraOuroViewHolder.descricao.setText(regraOuro.getDescricao());

        regraOuroViewHolder.descricao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(regraOuro.getNumero() == 10){
                    new AlertDialog.Builder(context).setTitle("Regra de Ouro")
                        .setMessage("Regra nº 10 não possui video.")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
                }else{
                    Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) context, API_KEY,regraOuro.getVideo(),0,true,false);
                    context.startActivity(intent);

                }


            }
        });
//        kaizenViewHolder.draweeView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(context, FullscreenActivity.class);
//                intent.putExtra("imagemKaizen",  kaizen);
//                context.startActivity(intent);
//
//
//            }
//        });

//        kaizenViewHolder.imagemKaizen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                    Intent intent = new Intent(context, FullscreenActivity.class);
//                    intent.putExtra("imagemKaizen",  kaizen);
//                    context.startActivity(intent);
//
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return regraOuroList.size();
    }

    @Override
    public int getItemViewType(int position){
        return position;
    }

    public class RegraOuroViewHolder extends RecyclerView.ViewHolder{

        final TextView numero;
        final TextView descricao;
//        final ImageView imagemKaizen;
        public RegraOuroViewHolder(View itemView) {
            super(itemView);
            numero =  itemView.findViewById(R.id.numeroRegraOuro);
            descricao =  itemView.findViewById(R.id.descricaoRegraOuro);
//            imagemKaizen = (ImageView) itemView.findViewById(R.id.imagemKaizen);

        }
    }

    public void updateAnswers(List<RegraOuro> items) {
        regraOuroList = items;
        notifyDataSetChanged();
    }

}
