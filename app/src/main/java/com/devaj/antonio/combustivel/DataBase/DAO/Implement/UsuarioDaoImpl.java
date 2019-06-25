package com.devaj.antonio.combustivel.DataBase.DAO.Implement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.devaj.antonio.combustivel.DataBase.Contract.UsuarioContract;
import com.devaj.antonio.combustivel.DataBase.DAO.Interface.UsuarioDao;
import com.devaj.antonio.combustivel.DataBase.DbHelper.UsuarioDbHelper;
import com.devaj.antonio.combustivel.Model.Usuario;

public class UsuarioDaoImpl implements UsuarioDao {

    private static final String TIPO_TEXTO = " TEXT";
    private static final String VIRGULA = ",";
    private static final String SQL_DELETAR_USUARIO =
            "DROP TABLE IF EXISTS "+ UsuarioContract.UsuarioEntry.NOME_TABELA;
    private static final String SQL_CREAR_USUARIO =
            "CREATE TABLE "+ UsuarioContract.UsuarioEntry.NOME_TABELA+" (" +
                    UsuarioContract.UsuarioEntry.NOME_COLUNA_MATRICULA+" TEXT PRIMARY KEY, "+
                    UsuarioContract.UsuarioEntry.NOME_COLUNA_NOME+TIPO_TEXTO+VIRGULA+
                    UsuarioContract.UsuarioEntry.NOME_COLUNA_SENHA+TIPO_TEXTO+" )";

    private SQLiteDatabase sqLiteDatabase;
    private UsuarioDbHelper usuarioDbHelper;

    public UsuarioDaoImpl(Context context) {
        usuarioDbHelper = new UsuarioDbHelper(context);
    }


    @Override
    public Usuario buscarUsuarioPorNome(String s) {
        Usuario u;
        String[] colunas = {
                UsuarioContract.UsuarioEntry.NOME_COLUNA_MATRICULA,
                UsuarioContract.UsuarioEntry.NOME_COLUNA_NOME,
                UsuarioContract.UsuarioEntry.NOME_COLUNA_SENHA
        };
        String criterio = UsuarioContract.UsuarioEntry.NOME_COLUNA_NOME+" = ?";
        String[] argumentos = {s};
        String ordem = UsuarioContract.UsuarioEntry.NOME_COLUNA_NOME+" DESC";

        sqLiteDatabase = usuarioDbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                UsuarioContract.UsuarioEntry.NOME_TABELA,
                colunas,
                criterio,
                argumentos,
                null,
                null,
                ordem);


        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            u = new Usuario(cursor.getString(cursor.getColumnIndexOrThrow(UsuarioContract.UsuarioEntry.NOME_COLUNA_MATRICULA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(UsuarioContract.UsuarioEntry.NOME_COLUNA_NOME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(UsuarioContract.UsuarioEntry.NOME_COLUNA_SENHA)));
            sqLiteDatabase.close();
            return u;
        }
        sqLiteDatabase.close();
        return null;
    }

    @Override
    public Long inserirUsuario(Usuario usuario) {
        ContentValues valores ;
        long resultado;

        sqLiteDatabase = usuarioDbHelper.getWritableDatabase();
        valores= new ContentValues();

        valores.put(UsuarioContract.UsuarioEntry.NOME_COLUNA_MATRICULA, usuario.getMatricula());
        valores.put(UsuarioContract.UsuarioEntry.NOME_COLUNA_NOME, usuario.getNome());
        valores.put(UsuarioContract.UsuarioEntry.NOME_COLUNA_SENHA,usuario.getSenha());

        resultado = sqLiteDatabase.insert(UsuarioContract.UsuarioEntry.NOME_TABELA,null,valores);
        sqLiteDatabase.close();

        return resultado;
    }

    @Override
    public Integer pegarTamanho() {
        String[] colunas = {
                UsuarioContract.UsuarioEntry.NOME_COLUNA_MATRICULA,
                UsuarioContract.UsuarioEntry.NOME_COLUNA_NOME,
                UsuarioContract.UsuarioEntry.NOME_COLUNA_SENHA
        };
        String criterio = UsuarioContract.UsuarioEntry.NOME_COLUNA_NOME+" = ?";
        String ordem = UsuarioContract.UsuarioEntry.NOME_COLUNA_NOME+" DESC";

        sqLiteDatabase = usuarioDbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                UsuarioContract.UsuarioEntry.NOME_TABELA,
                colunas,
                null,
                null,
                null,
                null,
                ordem);


        return cursor.getCount();
    }

    public void apagarTabela() {
        sqLiteDatabase = usuarioDbHelper.getWritableDatabase();
        sqLiteDatabase.execSQL(SQL_DELETAR_USUARIO);
    }

    public void criarTabela() {
        sqLiteDatabase = usuarioDbHelper.getWritableDatabase();
        sqLiteDatabase.execSQL(SQL_CREAR_USUARIO);
    }

    public Usuario buscarUsuario() {
        Usuario u;
        String[] colunas = {
                UsuarioContract.UsuarioEntry.NOME_COLUNA_MATRICULA,
                UsuarioContract.UsuarioEntry.NOME_COLUNA_NOME,
                UsuarioContract.UsuarioEntry.NOME_COLUNA_SENHA
        };
        String ordem = UsuarioContract.UsuarioEntry.NOME_COLUNA_NOME+" DESC";

        sqLiteDatabase = usuarioDbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                UsuarioContract.UsuarioEntry.NOME_TABELA,
                colunas,
                null,
                null,
                null,
                null,
                ordem);


        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            u = new Usuario(cursor.getString(cursor.getColumnIndexOrThrow(UsuarioContract.UsuarioEntry.NOME_COLUNA_MATRICULA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(UsuarioContract.UsuarioEntry.NOME_COLUNA_NOME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(UsuarioContract.UsuarioEntry.NOME_COLUNA_SENHA)));
            sqLiteDatabase.close();
            return u;
        }
        sqLiteDatabase.close();
        return null;
    }

}
