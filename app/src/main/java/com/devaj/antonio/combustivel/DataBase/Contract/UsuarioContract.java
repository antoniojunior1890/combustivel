package com.devaj.antonio.combustivel.DataBase.Contract;

import android.provider.BaseColumns;

public final class UsuarioContract {

    private UsuarioContract(){}

    public static class UsuarioEntry implements BaseColumns{
        public static final String NOME_TABELA = "usuario";
        public static final String NOME_COLUNA_MATRICULA = "matricula";
        public static final String NOME_COLUNA_NOME = "nome";
        public static final String NOME_COLUNA_SENHA = "senha";
    }
}
