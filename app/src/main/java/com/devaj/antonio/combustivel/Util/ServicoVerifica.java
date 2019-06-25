package com.devaj.antonio.combustivel.Util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import com.devaj.antonio.combustivel.Activity.MainActivity;
import com.devaj.antonio.combustivel.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonio on 07/12/16.
 */
public class ServicoVerifica extends Service {

    public List<Worker> threads = new ArrayList<Worker>();
    public Worker w;
    private static final String TAG = "COMBUSTIVEL";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.i("Script", "onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        w = new Worker(startId);
        w.start();
        threads.add(w);


        // Let it continue running until it is stopped.
        Toast.makeText(this, "Script", Toast.LENGTH_LONG).show();
        return(super.onStartCommand(intent, flags, startId));
        // START_NOT_STICKY
        //return(START_STICKY);
        // START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for(int i = 0, tam = threads.size(); i < tam; i++){
            threads.get(i).ativo = false;
        }
        Toast.makeText(this, "Script", Toast.LENGTH_LONG).show();
    }

    class Worker extends Thread {
        public int count = 0;
        public int startId;
        public boolean ativo = true;

        public Worker(int startId){
            this.startId = startId;
        }

        public void run(){
            while(ativo){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if(count == 15){
                    sendNotification("testizinho");
//                    NotificationCompat.Builder builder =
//                            new NotificationCompat.Builder(getApplication())
//                            .setSmallIcon(R.mipmap.ic_launcher)
//                            .setContentTitle("TESTE NOTIFICAÇÃO")
//                            .setContentText("testizinho");
//                    Intent resultIntent = new Intent(getApplication(), MainActivity.class);
//
//                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplication());
//                    stackBuilder.addParentStack(MainActivity.class);
//
//                    stackBuilder.addNextIntent(resultIntent);
//                    PendingIntent resultPendingIntent =
//                            stackBuilder.getPendingIntent(
//                                    0,
//                                    PendingIntent.FLAG_UPDATE_CURRENT
//                            );
//                    builder.setContentIntent(resultPendingIntent);
//                    NotificationManager notificationManager =
//                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                    Integer mId = 001;
//                    notificationManager.notify(mId,builder.build());
                    count = 0;

                }

                count++;
                Log.i(TAG, "COUNT: "+count);
            }
            stopSelf(startId);
            //stopSelf();
        }
    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("ATIVIDADES", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("TESTE")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
