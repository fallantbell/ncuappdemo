package com.example.ncuapptest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    String tag="main";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(tag,"oncreate");
        startthread st=new startthread();
        st.start();
    }
    class startthread extends Thread{//等待三秒跳轉畫面
        public void run(){
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent it=new Intent(MainActivity.this,start.class);
            startActivity(it);
        }
    }
}
