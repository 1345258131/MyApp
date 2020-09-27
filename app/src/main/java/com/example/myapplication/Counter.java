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
    public void addScoreA(View view){
        TextView score = findViewById(R.id.textView2);
        int previousScoreA = Integer.parseInt(score.getText().toString());
        int presentScoreA = Integer.parseInt(view.getTag().toString());
        score.setText(Integer.toString(previousScoreA+presentScoreA));
    }
    public void addScoreB(View view){
        TextView score = findViewById(R.id.textView3);
        int previousScoreB = Integer.parseInt(score.getText().toString());
        int presentScoreB = Integer.parseInt(view.getTag().toString());
        score.setText(Integer.toString(previousScoreB+presentScoreB));
    }

    public void Reset(View view){
        TextView scoreA = (TextView)findViewById(R.id.textView2);
        TextView scoreB = (TextView)findViewById(R.id.textView3);
        scoreA.setText("0");
        scoreB.setText("0");
    }


}