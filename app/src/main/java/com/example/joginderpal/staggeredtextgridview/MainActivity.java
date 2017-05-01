package com.example.joginderpal.staggeredtextgridview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.library.StaggeredView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String[] name={

            "Anmol",
            "Ankit",
            "Archit",
            "Naman",
            "Akhil",
            "Himanshu",
            "Pawan",
            "Love",
            "Akshay",
            "Anshul",
            "Ayush",
            "Bineet",
            "Deepak",
            "Dhruv Aggarwal",
            "Hiten",
            "Akash",
            "Mahima",
            "Nitish",
            "Sakshi",
            "Sonali",
            "Ashwath",
            "Maneet",
            "Nimisha",
            "Prajwal",
            "Rahul",
            "Rajat",
            "Saheel",
            "Sahil",
            "Tushar",
            "Yashasvi",
            "Abhishek",
            "Aashi",
            "Bharat",
            "Arsh",
            "Mayank",
            "Vineet"

    };
    List<String> li;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        li=new ArrayList<>();
        for (int i=0;i<name.length;i++){
            li.add(name[i]);
        }
        StaggeredView staggeredView= (StaggeredView) findViewById(R.id.staggeredTextView);
        WordsAdapter arrayAdapter = new WordsAdapter(this,li);
        staggeredView.setmAdapter(arrayAdapter);
    }
}
