package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Rate extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate);
        Intent intent = getIntent();
        float usd1=intent.getFloatExtra("usd_rate_key",0.0f);
        float eu1=intent.getFloatExtra("eu_rate_key",0.0f);
        float jp1=intent.getFloatExtra("jp_rate_key",0.0f);
        TextView t1 = findViewById(R.id.usd_rate);
        TextView t2 = findViewById(R.id.eu_rate);
        TextView t3 = findViewById(R.id.jp_rate);
        t1.setText(String.valueOf(usd1));
        t2.setText(String.valueOf(eu1));
        t3.setText(String.valueOf(jp1));
    }

    public void save(View view){    //跳回到汇率转换界面
        Intent save =getIntent();
        SharedPreferences sp =getSharedPreferences("myrate", Activity.MODE_PRIVATE);

        TextView t1 = findViewById(R.id.usd_rate);
        TextView t2 = findViewById(R.id.eu_rate);
        TextView t3 = findViewById(R.id.jp_rate);
        float usd2 = Float.valueOf(t1.getText().toString());
        float eu2 = Float.valueOf(t2.getText().toString());
        float jp2 = Float.valueOf(t3.getText().toString());

        save.putExtra("usd_rate_key1",usd2);
        save.putExtra("eu_rate_key1",eu2);
        save.putExtra("jp_rate_key1",jp2);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat("my_usd_rate",usd2);
        editor.putFloat("my_eu_rate",eu2);
        editor.putFloat("my_jp_rate",jp2);
        editor.apply();
        setResult(RESULT_OK,save);
        finish();
    }
}
