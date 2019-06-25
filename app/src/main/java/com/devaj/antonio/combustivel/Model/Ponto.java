package com.devaj.antonio.combustivel.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by antonio on 12/08/17.
 */
public class Ponto implements Serializable{
    private final Integer codigo ;
    private final String avaliador ;
    private final String avaliado ;
    private final Date data ;
    private final String hora;
    private final Integer pontuacao ;
    private final String tabela;

    public Ponto(Integer codigo, String avaliador, String avaliado, Date data, String hora, Integer pontuacao, String tabela) {
        this.codigo = codigo;
        this.avaliador = avaliador;
        this.avaliado = avaliado;
        this.data = data;
        this.hora = hora;
        this.pontuacao = pontuacao;
        this.tabela = tabela;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getAvaliador() {
        return avaliador;
    }

    public String getAvaliado() {
        return avaliado;
    }

    public Date getData() {
        return data;
    }

    public String getHora() {
        return hora;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public String getTabela() {
        return tabela;
    }
}