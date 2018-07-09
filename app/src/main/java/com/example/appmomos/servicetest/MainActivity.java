package com.example.appmomos.servicetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.appmomos.servicetest.Service.MyService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    Button buttonStart, buttonStop,buttonNext,bindService;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart = findViewById(R.id.buttonStart);
        buttonStop = findViewById(R.id.buttonStop);
        buttonNext = findViewById(R.id.buttonNext);
        bindService = findViewById(R.id.bindService);

        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        bindService.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.buttonStart:
                startService(new Intent(this, MyService.class));
                break;

            case R.id.buttonStop:
                stopService(new Intent(this, MyService.class));
                break;

            case R.id.buttonNext:
                startActivity(new Intent(this,NextPageActivity.class));
                break;

            case R.id.bindService:
                startActivity(new Intent(this,Client.class));
                break;
        }
    }


}
