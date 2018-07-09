package com.example.appmomos.servicetest.Service;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.appmomos.servicetest.MainActivity;
import com.example.appmomos.servicetest.R;

public class Server extends Service
{
    MediaPlayer myPlayer;

    IBinder mBinder = new LocalBinder();

    NotificationCompat.Builder builder;

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {
        public Server getServerInstance() {
            return Server.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Toast.makeText(this, "Bind Service Created", Toast.LENGTH_SHORT).show();

        myPlayer = MediaPlayer.create(this, R.raw.swag_se_swagat);
        myPlayer.setLooping(true);
    }


    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);
        Toast.makeText(this, "Bind Service Started", Toast.LENGTH_SHORT).show();
        myPlayer.start();
    }


    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }


    @SuppressLint("NewApi")
    public ArrayList<String> getTime()
    {
        ArrayList<String> songInfo = new ArrayList<>();
        songInfo.add(String.valueOf( getTimeString(myPlayer.getCurrentPosition()) ));
        songInfo.add(String.valueOf( getTimeString(myPlayer.getDuration()) ));

        addNotification(String.valueOf( getTimeString(myPlayer.getCurrentPosition()) ),String.valueOf( getTimeString(myPlayer.getDuration()) ));

        return songInfo;
    }

    @SuppressLint("DefaultLocale")
    private String getTimeString(long millis)
    {
        StringBuffer buf = new StringBuffer();

        int hours = (int) (millis / (1000 * 60 * 60));
        int minutes = (int) ((millis % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) (((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000);

       if(hours > 0)
       {
           buf .append(String.format("%02d", hours))
                   .append(":")
                   .append(String.format("%02d", minutes))
                   .append(":")
                   .append(String.format("%02d", seconds));
       }
       else
       {
           buf .append(String.format("%02d", minutes))
                   .append(":")
                   .append(String.format("%02d", seconds));
       }

        return buf.toString();
    }


    private void addNotification(String currentPos, String trackLength)
    {
        builder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("Track Info")
                        .setContentText("Current Position : "+currentPos+" / "+trackLength);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.notify(0, builder.build());
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
        myPlayer.stop();
    }




}