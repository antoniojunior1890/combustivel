package com.devaj.antonio.combustivel.Model;

import java.io.Serializable;

/**
 * Created by antonio on 12/08/17.
 */
public class Usuario implements Serializable{
    private String matricula ;
    private String nome ;
    private String senha ;

    public Usuario(String matricula, String nome, String senha) {
        this.matricula = matricula;
        this.nome = nome;
        this.senha = senha;
    }

    public Usuario() {
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Usuario)) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        return this.getMatricula().equals(other.getMatricula());
    }

    @Override
    public String toString() {
        return "matricula: "+ this.matricula
                + ", nome: "+ this.nome
                + ", senha: "+ this.senha;
    }
}