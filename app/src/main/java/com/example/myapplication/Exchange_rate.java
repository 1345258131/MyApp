package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Exchange_rate extends AppCompatActivity {

    double usd_rate=0.14;
    double eu_rate=0.1;
    double jp_rate=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exchange_rate);
        //Intent intent = getIntent();
        //usd_rate=intent.getDoubleExtra("usd_rate_key1",0.14);
       // eu_rate=intent.getDoubleExtra("eu_rate_key1",0.1);
       // jp_rate=intent.getDoubleExtra("jp_rate_key1",100);
    }
    protected void onActivityResult(int requestCode,int resultCode,Intent intent){

        super.onActivityResult(requestCode,resultCode,intent);
        usd_rate=intent.getDoubleExtra("usd_rate_key1",0.14);
        eu_rate=intent.getDoubleExtra("eu_rate_key1",0.1);
        jp_rate=intent.getDoubleExtra("jp_rate_key1",100);


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

}
