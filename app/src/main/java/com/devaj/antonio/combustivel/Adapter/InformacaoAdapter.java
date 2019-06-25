package com.devaj.antonio.combustivel.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.devaj.antonio.combustivel.Activity.FullscreenActivity;
import com.devaj.antonio.combustivel.Model.Informacao;
import com.devaj.antonio.combustivel.R;
import com.devaj.antonio.combustivel.Util.ImageOverlayView;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.List;

/**
 * Created by antonio on 26/09/17.
 */

public class InformacaoAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {
    private List<Informacao> informacoes;
    private Context context;
    private Integer mProgressPosition = -1;
    ImageOverlayView overlayView;

    public InformacaoAdapter(List<Informacao> informacoes, Context context) {
        this.informacoes = informacoes;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        RecyclerView.ViewHolder mvh;
//        if(viewType == 0) {
//            View v = LayoutInflater.from(context).inflate(R.layout.card_informacao, parent, false);
//            mvh = new InformacaoViewHolder(v);
//        }else {
//            View v = LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false);
//            mvh = new ProgressViewHolder(v);
//        }
//
//        return mvh;

        View view;

        view = LayoutInflater.from(context).inflate(R.layout.card_informacao, parent, false);

        InformacaoViewHolder holder = new InformacaoViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

//        if(holder instanceof ProgressViewHolder) {
//            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
//        }
//        else if(holder instanceof InformacaoViewHolder) {


            InformacaoViewHolder informacaoViewHolder = (InformacaoViewHolder) holder;

            final Informacao informacao = informacoes.get(position);

//        Picasso.with(context)
//                .load(informacao.getImagemInformacao())
//                .placeholder (R.drawable.loading)
//                .error (R.drawable.sem_foto)
//                .into(informacaoViewHolder.imagemInformacao);

            informacaoViewHolder.draweeView.setImageURI(Uri.parse(informacao.getImagemInformacao()));

            informacaoViewHolder.draweeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    GenericDraweeHierarchyBuilder genericDraweeHierarchyBuilder = GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                            .setFailureImage(R.drawable.sem_foto)
                            .setPlaceholderImage(R.drawable.loading);

//                overlayView = new ImageOverlayView(context);

                    String[] imagem = {informacao.getImagemInformacao()};
                    new ImageViewer.Builder(context, imagem)
                            .setStartPosition(0)
                            .setCustomDraweeHierarchyBuilder(genericDraweeHierarchyBuilder)
                            .show();

//                Intent intent = new Intent(context, FullscreenActivity.class);
//                intent.putExtra("imagemInformacao",  informacao);
//                context.startActivity(intent);


                }
            });
//        }
    }

    @Override
    public int getItemCount() {
        return informacoes.size();
//        return informacoes.isEmpty() ? 0 : informacoes.size();
    }

    @Override
    public int getItemViewType(int position){
        return position;
//        return position == mProgressPosition ? 1 : 0;
    }


    public void addProgressBar(){
        informacoes.add(null);
        notifyItemInserted(informacoes.size());
        mProgressPosition = informacoes.size()-1;
    }

    public void removeProgressBar(){
        if(informacoes != null && informacoes.size() != 0){
            informacoes.remove(informacoes.size() - 1);
            notifyItemRemoved(informacoes.size() - 1);
            mProgressPosition = -1;

        }
    }


    public void addListItem(Informacao post, int position){
        informacoes.add(post);
        notifyItemInserted(position);
    }

    public class InformacaoViewHolder extends RecyclerView.ViewHolder{

//        final ImageView imagemInformacao;
        SimpleDraweeView draweeView ;


        public InformacaoViewHolder(View itemView) {
            super(itemView);
//            imagemInformacao = itemView.findViewById(R.id.imagemInformacao);
            draweeView =  itemView.findViewById(R.id.my_image_view);


        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar)v.findViewById(R.id.progressBar1);
        }
    }

    public void updateAnswers(List<Informacao> items) {
        informacoes = items;
        notifyDataSetChanged();
    }



}
