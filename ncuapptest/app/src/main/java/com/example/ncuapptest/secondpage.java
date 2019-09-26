package com.example.ncuapptest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    private myadapter adapter;
    private SharedPreferences preferences;
    private int count = 0;
    private ArrayList<String> time=new ArrayList<>();
    private ArrayList<String> objectsavename=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondpage);
        Log.d(tag,"oncreate...");
        Intent it=getIntent();
        init();
        readData();
        inputusername.setText(it.getStringExtra("username"));
        adapter=new myadapter();
        listview.setAdapter(adapter);
        Thread t=new Thread(new timecal());
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

    class timecal implements Runnable{//每秒刷新計算時間
        public void run(){
            while(true){
                try {
                    Log.d(tag,"timerun");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis()) ;
                currenttime=formatter.format(curDate);//當前時間
            }
        }
    }
    public final class Viewholder{
        public TextView time;
        public TextView objname;
        public ImageButton exitbtn;
        public ImageView img;
    }
    private ArrayList<String> mlist=new ArrayList<>();
    private ArrayList<String> timelist=new ArrayList<>();
    private class myadapter extends BaseAdapter{
        private LayoutInflater mInflater;

        public void deleteitem(int index){
            mlist.remove(index);//移除當前position的object
            objectsavename.remove(index);
            time.remove(index);
            timelist.remove(index);
            saveData();
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
            View v =convertView;
            Log.d(tag,"position: "+position);
            if (convertView == null) {//adapter 初始
                holder=new Viewholder();
                v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.listlayout, null);
             //   convertView=mInflater.inflate(R.layout.listlayout,null);
                holder.exitbtn=v.findViewById(R.id.imgbtn);
                holder.img=v.findViewById(R.id.img);
                holder.objname=v.findViewById(R.id.objname);
                holder.time=v.findViewById(R.id.time);
                v.setTag(holder);
                Log.d(tag,"1");
            }
            else{
                Log.d(tag,"2");
                holder = (Viewholder) v.getTag();
            }

            holder.objname.setText(mlist.get(position));//設定object名稱
            holder.time.setText(timelist.get(position));//設定當前時間

            holder.exitbtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    deleteitem(position);
                    adapter.notifyDataSetChanged();
                    Log.d(tag,"position:"+Integer.toString(position));
                }
            });

            notifyDataSetChanged();
            return v;
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
        finish();
    }

    public void add(View v){//增加物件
        objectname=inputobjectname.getText().toString();
        mlist.add(objectname);
        timelist.add(currenttime);
        objectsavename.add(objectname);
        time.add(currenttime);
        saveData();
        adapter.notifyDataSetChanged();
        Log.d(tag,"addentry...");
        Log.d(tag,"mlist:"+mlist.size());
    }
    public void readData(){
        preferences=getSharedPreferences("testarray", MODE_PRIVATE);
        int size=preferences.getInt("size",0);
        Log.d(tag,Integer.toString(size));
        objectsavename.clear();
        time.clear();
        for(int i=0;i<size;i++){
            objectsavename.add(preferences.getString("str"+i, " "));
            Log.d("tag",preferences.getString("str"+i, " "));
            mlist.add(objectsavename.get(i));
        }
        for(int i=0;i<size;i++){
            time.add(preferences.getString("str2"+i, " "));
            timelist.add(time.get(i));
        }

    }
    public void saveData(){
        preferences=getSharedPreferences("testarray", MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putInt("size", objectsavename.size());
        editor.commit();
        for(int i=0;i<objectsavename.size();i++){
            Log.d(tag,"yes");
            editor=preferences.edit();
            editor.putString("str"+i, objectsavename.get(i));
            editor.commit();
        }
        for(int i=0;i<time.size();i++){
            editor=preferences.edit();
            editor.putString("str2"+i, time.get(i));
            editor.commit();
        }
    }
}
