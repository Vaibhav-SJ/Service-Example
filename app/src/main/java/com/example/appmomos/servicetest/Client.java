package com.example.appmomos.servicetest;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmomos.servicetest.Service.Server;

import java.util.Timer;
import java.util.TimerTask;

public class Client extends AppCompatActivity
{

    boolean mBounded;
    Server mServer;
    TextView text;
    Button button;

    private Handler handler;
    private Runnable handlerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        text = findViewById(R.id.text);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener()
        {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v)
            {
                //HANDLER TO GET SONG TIME AFTER EVERY SECOND
                handler = new Handler();
                handlerTask = new Runnable()
                {
                    @Override
                    public void run() {
                        text.setText("Current Position : "+ mServer.getTime().get(0) +"\nTrack Length "+mServer.getTime().get(1) );
                        handler.postDelayed(handlerTask, 1000);
                    }
                };
                handlerTask.run();
                button.setVisibility(View.GONE);

            }
        });

    }


    @Override
    protected void onStart()
    {
        super.onStart();
        Intent mIntent = new Intent(this, Server.class);
        bindService(mIntent, mConnection, BIND_AUTO_CREATE);

        startService(mIntent);
    };

    ServiceConnection mConnection = new ServiceConnection()
    {
        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            mBounded = false;
            mServer = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            mBounded = true;
            Server.LocalBinder mLocalBinder = (Server.LocalBinder)service;
            mServer = mLocalBinder.getServerInstance();
        }
    };

    @Override
    protected void onStop()
    {
        super.onStop();
        if(mBounded)
        {
            unbindService(mConnection);
            mBounded = false;
        }
    };

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        if(mBounded)
        {
            unbindService(mConnection);
            mBounded = false;
            Toast.makeText(Client.this, "Service is disconnected", Toast.LENGTH_SHORT).show();
            stopService(new Intent(this, Server.class));
        }
    }
}
