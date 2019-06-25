package com.devaj.antonio.combustivel.Conect;


import com.devaj.antonio.combustivel.Model.RespostaInformacoes;
import com.devaj.antonio.combustivel.Model.RespostaKaizen;
import com.devaj.antonio.combustivel.Model.RespostaPonto;
import com.devaj.antonio.combustivel.Model.RespostaUsuario;
import com.devaj.antonio.combustivel.Model.RespostaVideos;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by antonio on 02/08/17.
 */
public interface SOService {


    public static final String BASE_URL = "http://devaj.tech/android/combustivel/php/";

    @GET("kaizens.php")
    Call<RespostaKaizen> getRespostaKaizen();

    @GET("videos.php")
    Call<RespostaVideos> getRespostaVideos();

    @GET("informacoes.php")
    Call<RespostaInformacoes> getRespostaInformacoes();

    @GET("avaliadodbc.php")
    Call<RespostaPonto> getRespostaPonto(@Query("avaliado") String avaliado);

//    @GET("informacoes.php")
//    Call<RespostaInformacoes> getRespostaInformacoes(@Query("datainicio") String datainicio, @Query("datafim") String datafim);

//    @FormUrlEncoded
//    @POST("informacoes.php")
//    Call<RespostaInformacoes> getRespostaInformacoes(@Field("datainicio") String datainicio, @Field("datafim") String datafim);

    @FormUrlEncoded
    @POST("logar.php")
    Call<RespostaUsuario> registrar(@Field("matricula") String matricula, @Field("senha") String senha, @Field("token") String token);

}
