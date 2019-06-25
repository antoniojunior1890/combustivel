package com.devaj.antonio.combustivel.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devaj.antonio.combustivel.Activity.FullscreenActivity;
import com.devaj.antonio.combustivel.Activity.TesteFS;
import com.devaj.antonio.combustivel.Model.Kaizen;
import com.devaj.antonio.combustivel.R;
import com.devaj.antonio.combustivel.Util.ImageOverlayView;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.List;

//import com.bumptech.glide.Glide;

/**
 * Created by antonio on 18/05/17.
 */
public class KaizenAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private List<Kaizen> kaizens;
    private Context context;
    private static final String TAG = "COMBUSTIVEL";
    ImageOverlayView overlayView;

    public KaizenAdapter(List<Kaizen> kaizens, Context context) {
        this.kaizens = kaizens;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(context).inflate(R.layout.card_kaizen, parent, false);

        KaizenViewHolder holder = new KaizenViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        KaizenViewHolder kaizenViewHolder = (KaizenViewHolder) holder;

        final Kaizen kaizen = kaizens.get(position);

        kaizenViewHolder.descricaoKaizen.setText(kaizen.getDescricaoKaizen());
//        Picasso.with(context)
//                .load(kaizen.getImagemKaizen())
//                .placeholder (R.drawable.loading)
//                .error (R.drawable.sem_foto)
//                .into(kaizenViewHolder.imagemKaizen);

        kaizenViewHolder.draweeView.setImageURI( Uri.parse(kaizen.getImagemKaizen()));

        kaizenViewHolder.draweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GenericDraweeHierarchyBuilder genericDraweeHierarchyBuilder = GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                        .setFailureImage(R.drawable.sem_foto)
                        .setPlaceholderImage(R.drawable.loading);

                overlayView = new ImageOverlayView(context);

                String[] imagem = {kaizen.getImagemKaizen()};
                new ImageViewer.Builder(context, imagem)
                        .setStartPosition(0)
                        .setOverlayView(overlayView)
                        .setImageChangeListener(getImageChangerListener(kaizen.getDescricaoKaizen()))
                        .setCustomDraweeHierarchyBuilder(genericDraweeHierarchyBuilder)
                        .show();

//                Intent intent = new Intent(context, TesteFS.class);
//                intent.putExtra("imagemKaizen",  kaizen);
//                context.startActivity(intent);


            }
        });

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
        return kaizens.size();
    }

    @Override
    public int getItemViewType(int position){
        return position;
    }

    public class KaizenViewHolder extends RecyclerView.ViewHolder{

        final SimpleDraweeView draweeView ;
        final TextView descricaoKaizen;
//        final ImageView imagemKaizen;
        public KaizenViewHolder(View itemView) {
            super(itemView);
            draweeView =  itemView.findViewById(R.id.my_image_view);
            descricaoKaizen = (TextView) itemView.findViewById(R.id.descricaoKaizen);
//            imagemKaizen = (ImageView) itemView.findViewById(R.id.imagemKaizen);

        }
    }

    public void updateAnswers(List<Kaizen> items) {
        kaizens = items;
        notifyDataSetChanged();
    }

    private ImageViewer.OnImageChangeListener getImageChangerListener(final String texto){
        return new ImageViewer.OnImageChangeListener() {
            @Override
            public void onImageChange(int position) {
                overlayView.setDescription(texto);
            }
        };
    }

}
