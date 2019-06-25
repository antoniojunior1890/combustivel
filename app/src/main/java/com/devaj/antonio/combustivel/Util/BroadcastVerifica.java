package com.devaj.antonio.combustivel.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by antonio on 07/12/16.
 */
public class BroadcastVerifica extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Script", "onReceive()");
        //intent = new Intent("SERVICO_TEST");
        context.startService(new Intent(context, ServicoVerifica.class));
    }
}