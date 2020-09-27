package com.example.myapplication;

import android.content.Intent;
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
        double usd1=intent.getDoubleExtra("usd_rate_key",0.0f);
        double eu1=intent.getDoubleExtra("eu_rate_key",0.0f);
        double jp1=intent.getDoubleExtra("jp_rate_key",0.0f);
        TextView t1 = findViewById(R.id.usd_rate);
        TextView t2 = findViewById(R.id.eu_rate);
        TextView t3 = findViewById(R.id.jp_rate);
        t1.setText(String.valueOf(usd1));
        t2.setText(String.valueOf(eu1));
        t3.setText(String.valueOf(jp1));
    }

    public void save(View view){    //跳回到汇率转换界面
        Intent save =getIntent();
        TextView t1 = findViewById(R.id.usd_rate);
        TextView t2 = findViewById(R.id.eu_rate);
        TextView t3 = findViewById(R.id.jp_rate);
        double usd2 = Double.valueOf(t1.getText().toString());
        double eu2 = Double.valueOf(t2.getText().toString());
        double jp2 = Double.valueOf(t3.getText().toString());
        save.putExtra("usd_rate_key1",usd2);
        save.putExtra("eu_rate_key1",eu2);
        save.putExtra("jp_rate_key1",jp2);
        setResult(RESULT_OK,save);
        finish();
    }
}
