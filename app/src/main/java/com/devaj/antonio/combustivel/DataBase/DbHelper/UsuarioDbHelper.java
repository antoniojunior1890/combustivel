package com.devaj.antonio.combustivel.DataBase.DbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.devaj.antonio.combustivel.DataBase.Contract.UsuarioContract;

public class UsuarioDbHelper extends SQLiteOpenHelper{

    private static final String TIPO_TEXTO = " TEXT";
    private static final String VIRGULA = ",";
    private static final String SQL_CREAR_USUARIO =
            "CREATE TABLE "+ UsuarioContract.UsuarioEntry.NOME_TABELA+" (" +
                    UsuarioContract.UsuarioEntry.NOME_COLUNA_MATRICULA+" TEXT PRIMARY KEY, "+
                    UsuarioContract.UsuarioEntry.NOME_COLUNA_NOME+TIPO_TEXTO+VIRGULA+
                    UsuarioContract.UsuarioEntry.NOME_COLUNA_SENHA+TIPO_TEXTO+" )";
    private static final String SQL_DELETAR_USUARIO =
            "DROP TABLE IF EXISTS "+ UsuarioContract.UsuarioEntry.NOME_TABELA;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOME = "UsuarioTeste12.db";


    public UsuarioDbHelper(Context context) {
        super(context, DATABASE_NOME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREAR_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DELETAR_USUARIO);
        onCreate(sqLiteDatabase);
    }

    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){
        onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }
}
