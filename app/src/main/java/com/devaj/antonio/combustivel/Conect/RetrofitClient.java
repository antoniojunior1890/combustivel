package com.devaj.antonio.combustivel.Conect;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by antonio on 02/08/17.
 */
public class RetrofitClient {


    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl,OkHttpClient client){
        if(retrofit == null){

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
