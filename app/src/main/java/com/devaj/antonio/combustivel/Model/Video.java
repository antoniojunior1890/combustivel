package com.devaj.antonio.combustivel.Model;

import android.view.View;

import java.util.Date;

/**
 * Created by antonio on 19/09/17.
 */

public class Video {
    private final Integer codVideo;
    private final String tituloVideo;
    private final String descricaoVideo;
    private final Date dataVideo;
    private final String linkVideo;

    public Video(Integer codVideo, String tituloVideo, String descricaoVideo, Date dataVideo, String linkVideo) {
        this.codVideo = codVideo;
        this.tituloVideo = tituloVideo;
        this.descricaoVideo = descricaoVideo;
        this.dataVideo = dataVideo;
        this.linkVideo = linkVideo;
    }

    public Integer getCodVideo() {
        return codVideo;
    }

    public String getTituloVideo() {
        return tituloVideo;
    }

    public String getDescricaoVideo() {
        return descricaoVideo;
    }

    public Date getDataVideo() {
        return dataVideo;
    }

    public String getLinkVideo() {
        return linkVideo;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Video)) {
            return false;
        }
        final Video other = (Video) obj;
        return this.getCodVideo().equals(other.getCodVideo());
    }

    @Override
    public String toString() {
        return "id: "+ this.codVideo
                + ", titulo: "+ this.tituloVideo
                + ", descricao: "+ this.descricaoVideo
                + ", data: "+ this.dataVideo
                + ", link: "+ this.linkVideo;
    }
}
