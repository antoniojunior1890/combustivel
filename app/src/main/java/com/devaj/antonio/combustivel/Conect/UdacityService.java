package com.devaj.antonio.combustivel.Conect;

import com.devaj.antonio.combustivel.Model.RespostaKaizen;
import com.devaj.antonio.combustivel.Model.UdacityCatalog;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by antonio on 19/09/17.
 */

public interface UdacityService {
    public static final String BASE_URL = "https://www.udacity.com/public-api/v0/";

    @GET("courses")
    Call<UdacityCatalog> listCatalog();
}
