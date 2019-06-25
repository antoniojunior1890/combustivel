package com.devaj.antonio.combustivel.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by antonio on 12/08/17.
 */
public class RegraOuro implements Serializable{
    private final Integer numero;
    private final String descricao;
    private final String video;

    public RegraOuro(Integer numero, String descricao, String video) {
        this.numero = numero;
        this.descricao = descricao;
        this.video = video;
    }

    public Integer getNumero() {
        return numero;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getVideo() {
        return video;
    }

    @Override
    public String toString() {
        return "numero: "+ this.numero
                + ", descricao: "+ this.descricao;
    }
}