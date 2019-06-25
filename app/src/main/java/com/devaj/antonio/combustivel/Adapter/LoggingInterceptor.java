package com.devaj.antonio.combustivel.Adapter;

import android.util.Log;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by antonio on 13/09/17.
 */

public class LoggingInterceptor implements Interceptor {
    private static final String TAG = "NetworkInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        // Requesting to server...
        Request request = chain.request();
        long t1 = System.nanoTime();
        Log.i(TAG, String.format("Sending request %s", request.url()));

        // Received response...
        Response response = chain.proceed(request);
        int tryCount = 0;
        while (!response.isSuccessful() && tryCount < 3) {
            Log.i(TAG, String.format(Locale.ENGLISH,
                    "Request failed -- Attempt %d... calling.. %s",
                    tryCount + 1, response.request().url()));
            tryCount++;
            // retry the request
            response = chain.proceed(request);
        }
        long t2 = System.nanoTime();
        Log.i(TAG, String.format(Locale.ENGLISH, "Received response for %s in %.1fms",
                response.request().url(), (t2 - t1) / 1e6d));

        return response;

    }
}
