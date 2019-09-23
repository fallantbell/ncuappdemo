package com.example.ncuapptest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class secondpage extends AppCompatActivity {
    TextView inputusername;
    String objectname;
    EditText inputobjectname;
    ListView listview;
    String currenttime;
    String tag="secondpae";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondpage);
        Log.d(tag,"oncreate...");
        Intent it=getIntent();
        init();
        inputusername.setText(it.getStringExtra("username"));
        listview.setAdapter(new myadapter(this));
        timecal t=new timecal();
        t.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(tag,"destroy...");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(tag,"pause...");
    }

    class timecal extends Thread{//每秒刷新計算時間
        public void run(){
            try {
                Log.d(tag,"timerun");
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis()) ;
            currenttime=formatter.format(curDate);//當前時間
        }
    }
    public final class Viewholder{
        public TextView time;
        public TextView objname;
        public ImageButton exitbtn;
        public ImageView img;
    }
    private ArrayList<String> mlist=new ArrayList<>();
    private class myadapter extends BaseAdapter{
        private LayoutInflater mInflater;

        public myadapter(Context context){
            this.mInflater = LayoutInflater.from(context);
            mlist=new ArrayList<>();
        }
        public void additem(){
            mlist.add(objectname);//增加object
        }
        public void deleteitem(int index){
            mlist.remove(index);//移除當前position的object
        }
        @Override
        public int getCount() {
            return mlist.size();//回傳當前object個數
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Viewholder holder = null;
            if (convertView == null) {//adapter 初始
                holder=new Viewholder();
                convertView=mInflater.inflate(R.layout.listlayout,null);
                holder.exitbtn=findViewById(R.id.imgbtn);
                holder.img=findViewById(R.id.img);
                holder.objname=findViewById(R.id.objname);
                holder.time=findViewById(R.id.time);
                convertView.setTag(holder);
            }
            else{
                holder = (Viewholder)convertView.getTag();
            }
            holder.objname.setText(mlist.get(position));//設定object名稱
            holder.time.setText(currenttime);//設定當前時間
            holder.exitbtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    deleteitem(position);
                }
            });
            notifyDataSetChanged();
            return convertView;
        }
    }
    private void init() {//初始宣告
        inputusername=findViewById(R.id.inputusername);
        inputobjectname=findViewById(R.id.inputobjectname);
        listview=findViewById(R.id.listview);
    }

    public void back(View v){//回上一頁
        Intent it2=new Intent(this,start.class);
        startActivity(it2);
    }
    public void add(View v){//增加物件
        objectname=inputobjectname.getText().toString();
        new myadapter(this).additem();
    }
}
