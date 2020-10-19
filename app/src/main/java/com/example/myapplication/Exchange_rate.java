package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Exchange_rate extends AppCompatActivity implements Runnable{

    private static final String TAG = "Exchange_rate";
    Handler handler;

    float usd_rate=0.0f;
    float eu_rate=0.0f;
    float jp_rate=0.0f;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exchange_rate);

        Thread t = new Thread(this);
        t.start();

        handler = new Handler(){

            public void handleMessage(Message msg){
                if(msg.what==5){
                    Bundle bundle = (Bundle) msg.obj;

                    usd_rate = bundle.getFloat("usd_rate");
                    eu_rate = bundle.getFloat("eu_rate");
                    jp_rate = bundle.getFloat("jp_rate");
                    //Log.i(TAG,"handleMessage:getMessage msg ="+ str);

                }
            super.handleMessage(msg);
            }
        };

       // SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        //PreferenceManager.getDefaultSharedPreferences(this);
       // usd_rate = sharedPreferences.getFloat("my_usd_rate",0.0f);
      //  eu_rate = sharedPreferences.getFloat("my_eu_rate",0.0f);
       // jp_rate = sharedPreferences.getFloat("my_jp_rate",0.0f);

    }
    protected void onActivityResult(int requestCode,int resultCode,Intent intent){

        super.onActivityResult(requestCode,resultCode,intent);
        usd_rate=intent.getFloatExtra("usd_rate_key1",0.14f);
        eu_rate=intent.getFloatExtra("eu_rate_key1",0.1f);
        jp_rate=intent.getFloatExtra("jp_rate_key1",100);


    }

    public void CNYtoOther(View view){
        TextView CNY = findViewById(R.id.editTextTextPersonName);
        TextView EX = findViewById(R.id.editTextTextPersonName2);

        int cny = Integer.parseInt(CNY.getText().toString());
        if(view.getId()==R.id.btn_usd){
            double usd = cny*usd_rate;
            EX.setText(String.valueOf(usd));
        }
        if(view.getId()==R.id.btn_eu){
            double eu = cny*eu_rate;
            EX.setText(String.valueOf(eu));
        }
        if(view.getId()==R.id.btn_jp) {
            double jp = cny *jp_rate ;
            EX.setText(String.valueOf(jp));
        }
    }
    public void jumpRate(View view){    //跳转到汇率配置界面
        Intent config = new Intent();
        config.putExtra("usd_rate_key",usd_rate);
        config.putExtra("eu_rate_key",eu_rate);
        config.putExtra("jp_rate_key",jp_rate);
        config.setClass(Exchange_rate.this, Rate.class);
        startActivityForResult(config,1);
    }

    @Override
    public void run() {
        //URL url = null;
        try {
            String url = "https://www.usd-cny.com";
            Document doc = Jsoup.connect(url).get();
            Log.i(TAG,"run:" + doc.title());
            String rate[] = new String[10];
            rate = get_rate(doc);

            //Log.i(TAG,"run:" + rate[2] );
            Bundle bundle = new Bundle();
            float usdRate = 100 / Float.parseFloat(rate[1]);
            bundle.putFloat("usd_rate",usdRate);
            float euRate = 100 / Float.parseFloat(rate[2]);
            bundle.putFloat("eu_rate",euRate);
            float jpRate = 100 / Float.parseFloat(rate[5]);
            bundle.putFloat("jp_rate",jpRate);

            Message msg = handler.obtainMessage(5);
            msg.obj = bundle;
            handler.sendMessage(msg);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String inputStram2String(InputStream inputStream)throws IOException{
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out =new StringBuilder();
        Reader in = new InputStreamReader(inputStream,"gb2312");
        while(true){
            int rsz = in.read(buffer,0,buffer.length);
            if(rsz<0)break;
            out.append(buffer,0,rsz);
        }
        return out.toString();
    }
    private static String[] get_rate(Document doc){
        Elements trs = doc.select("table").select("tr");
        int i;
        Elements tds;
        String rate[] = new String[10];
        for ( i=1; i<=5; i++){
            tds = trs.get(i).select("td");
            rate[i] = tds.get(4).text();
        }
        return rate;
    }
}
