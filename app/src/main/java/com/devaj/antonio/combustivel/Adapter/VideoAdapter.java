package com.devaj.antonio.combustivel.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codewaves.youtubethumbnailview.ImageLoader;
import com.codewaves.youtubethumbnailview.ThumbnailLoader;
import com.codewaves.youtubethumbnailview.ThumbnailView;
import com.devaj.antonio.combustivel.Model.Video;
import com.devaj.antonio.combustivel.R;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

/**
 * Created by antonio on 18/05/17.
 */
public class VideoAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private List<Video> videos;
    private Context context;
    private static final String TAG = "COMBUSTIVEL";
    private static final String API_KEY = "AIzaSyAcSPSPT2E_eYTsvQe3RxGXuBUEPn7lOEI";
    private static final String PRE_LINK_YT = "https://www.youtube.com/watch?v=";
    private static final String LINK = "qTfB1xG2wBE";

    public VideoAdapter(List<Video> videos, Context context) {
        this.videos = videos;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ThumbnailLoader.initialize(API_KEY);

        view = LayoutInflater.from(context).inflate(R.layout.card_video, parent, false);

        VideoViewHolder holder = new VideoViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final VideoViewHolder videoViewHolder = (VideoViewHolder) holder;

        final Video video = videos.get(position);

        videoViewHolder.tituloVideo.setText(video.getTituloVideo());

        videoViewHolder.miniaturaVideo.loadThumbnail(PRE_LINK_YT+ video.getLinkVideo(), new ImageLoader() {
            @Override
            public Bitmap load(String url) throws IOException {
//                return Picasso.with(context)
//                        .load(url)
//                        .error(R.drawable.sem_video)
//                        .placeholder(R.drawable.loading)
//                        .get();
                return Picasso.get()
                        .load(url)
                        .placeholder(R.drawable.adr)
                        .get();
            }
        });

//        videoViewHolder.descricaoVideo.setText(video.getDescricaoVideo());

        videoViewHolder.miniaturaVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Log.i(TAG,video.getLinkVideo());
                Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) context, API_KEY,video.getLinkVideo(),0,true,false);
                context.startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return videos.size();
    }

    @Override
    public int getItemViewType(int position){
        return position;
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{

        final TextView tituloVideo;
        final ThumbnailView miniaturaVideo;
//        final TextView descricaoVideo;

        public VideoViewHolder(View itemView) {
            super(itemView);
            tituloVideo = itemView.findViewById(R.id.tituloVideo);
            miniaturaVideo = itemView.findViewById(R.id.miniaturaVideo);
//            descricaoVideo =  itemView.findViewById(R.id.descricaoVideo);

        }
    }

    public void updateAnswers(List<Video> items) {
        videos = items;
        notifyDataSetChanged();
    }

}
