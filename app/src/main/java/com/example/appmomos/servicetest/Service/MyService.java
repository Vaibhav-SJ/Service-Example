package com.example.appmomos.servicetest.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import com.example.appmomos.servicetest.R;



public class MyService extends Service
{
    MediaPlayer myPlayer;

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }


    @Override
    public void onCreate()
    {
        super.onCreate();

        Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show();

        myPlayer = MediaPlayer.create(this, R.raw.swag_se_swagat);
        myPlayer.setLooping(false); // Set looping
    }


    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
        myPlayer.start();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
        myPlayer.stop();
    }
}
