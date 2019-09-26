package com.example.ncuapptest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Locale;

public class start extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    Switch changelang;
    Button startbtn;
    TextView yourname;
    EditText username;
    String getusername;
    String tag="start";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        init();
        changelang.setOnCheckedChangeListener(this);
        Resources resources = getResources();//預設中文
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        config.locale = Locale.TRADITIONAL_CHINESE;
        resources.updateConfiguration(config, dm);
        Message msg=new Message();
        msg.what=1;
        mHandler.sendMessage(msg);
    }

    private void init() {//初始宣告
        changelang=findViewById(R.id.changelang);
        startbtn=findViewById(R.id.startbtn);
        yourname=findViewById(R.id.yourname);
        username=findViewById(R.id.username);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//switch 更改語系
        if(isChecked){
            Log.d(tag,"english");
            Resources resources = getResources();
            Configuration config = resources.getConfiguration();
            DisplayMetrics dm = resources.getDisplayMetrics();
            config.locale = Locale.ENGLISH;
            resources.updateConfiguration(config, dm);

        }
        else{
            Log.d(tag,"chinese");
            Resources resources = getResources();
            Configuration config = resources.getConfiguration();
            DisplayMetrics dm = resources.getDisplayMetrics();
            config.locale = Locale.TRADITIONAL_CHINESE;
            resources.updateConfiguration(config, dm);

        }
        Message msg=new Message();
        msg.what=1;
        mHandler.sendMessage(msg);
    }

    public void gotonext(View v){//跳至下一頁 傳送使用者名稱
        Log.d(tag,"press");
        getusername=username.getText().toString();
        Intent it=new Intent(start.this,secondpage.class);
        it.putExtra("username",getusername);
        startActivity(it);
        finish();
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    startbtn.setText(getString(R.string.startbtn));
                    yourname.setText(getString(R.string.yourname));
                    username.setHint(getString(R.string.username));
                    break;
                default:
                    break;
            }
        }
    };
}
