package com.devaj.antonio.combustivel.Model;

import android.content.Intent;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by antonio on 26/09/17.
 */

public class Informacao implements Serializable {
    private final Integer codInformacao;
    private final Date dataInformacao;
    private final String imagemInformacao;

    public Informacao(Integer codInformacao, Date dataInformacao, String imagemInformacao) {
        this.codInformacao = codInformacao;
        this.dataInformacao = dataInformacao;
        this.imagemInformacao = imagemInformacao;
    }

    public Integer getCodInformacao() {
        return codInformacao;
    }

    public Date getDataInformacao() {
        return dataInformacao;
    }

    public String getImagemInformacao() {
        return imagemInformacao;
    }
}
