package com.devaj.antonio.combustivel.Model;

import java.io.Serializable;

/**
 * Created by antonio on 26/09/17.
 */

public class RespostaUsuario {

    private String mensagem;
    private boolean erro;
    public Usuario usuario;

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public boolean isErro() {
        return erro;
    }

    public void setErro(boolean erro) {
        this.erro = erro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
