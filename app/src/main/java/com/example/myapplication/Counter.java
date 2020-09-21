package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class Counter extends AppCompatActivity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counter);
    }
    public void addScore(View view){
        TextView score = findViewById(R.id.textView2);
        int previousScore = Integer.parseInt(score.getText().toString());
        int presentScore = Integer.parseInt(view.getTag().toString());
        score.setText(Integer.toString(previousScore+presentScore));
    }

    public void Reset(View view){
        TextView score = (TextView)findViewById(R.id.textView2);
        score.setText("0");
    }


}