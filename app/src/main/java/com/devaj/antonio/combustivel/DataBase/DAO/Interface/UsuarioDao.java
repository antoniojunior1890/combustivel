package com.devaj.antonio.combustivel.DataBase.DAO.Interface;

import com.devaj.antonio.combustivel.Model.Usuario;

public interface UsuarioDao {

    Usuario buscarUsuarioPorNome(String s);

    Long inserirUsuario(Usuario usuario);

    Integer pegarTamanho();

}
