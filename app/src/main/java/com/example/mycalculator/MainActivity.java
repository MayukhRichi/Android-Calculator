package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.mycalculator.Main2Activity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //splash screen
        Handler handler = new Handler();
        //handler.postDelayed(Thread,milliseconds to delay)
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent next=new Intent(MainActivity.this, Main2Activity.class);
                startActivity(next);
                finish();//such one can't come back to the splash screen again
            }
        },2000);
    }
}