package com.example.appmomos.servicetest.Service;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.example.appmomos.servicetest.R;

public class Server extends Service
{
    MediaPlayer myPlayer;

    IBinder mBinder = new LocalBinder();

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
        return songInfo;
    }

    /*public String convertDuration(long duration)
    {
        String out = null;
        long hours;

        try
        {
            hours = (duration / 3600000);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return out;
        }

        long remaining_minutes = (duration - (hours * 3600000)) / 60000;
        String minutes = String.valueOf(remaining_minutes);
        if (minutes.equals(0))
        {
            minutes = "00";
        }

        long remaining_seconds = (duration - (hours * 3600000) - (remaining_minutes * 60000));
        String seconds = String.valueOf(remaining_seconds);
        if (seconds.length() < 2)
        {
            seconds = "00";
        }
        else
        {
            seconds = seconds.substring(0, 2);
        }

        if (hours > 0)
        {
            out = hours + ":" + minutes + ":" + seconds;
        }
        else
        {
            out = minutes + ":" + seconds;
        }

        return out;

    }*/

    @SuppressLint("DefaultLocale")
    private String getTimeString(long millis)
    {
        StringBuffer buf = new StringBuffer();

        int hours = (int) (millis / (1000 * 60 * 60));
        int minutes = (int) ((millis % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) (((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000);

        buf
                .append(String.format("%02d", hours))
                .append(":")
                .append(String.format("%02d", minutes))
                .append(":")
                .append(String.format("%02d", seconds));

        return buf.toString();
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
        myPlayer.stop();
    }




}