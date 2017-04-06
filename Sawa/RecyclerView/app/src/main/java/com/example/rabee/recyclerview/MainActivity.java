package com.example.rabee.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    int image1 = R.drawable.image;
    int image2 = R.drawable.image2;
    int image3 = R.drawable.image;
    int image4 = R.drawable.image2;
    ArrayList<DataProvider> arrayList = new ArrayList<DataProvider>();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        DataProvider data=new DataProvider(image1,image2,"Ibrahim Post ");
        DataProvider data1=new DataProvider(image3,image4,"Raghad post");
        arrayList.add(data);
        arrayList.add(data1);
        recyclerAdapter=new RecyclerAdapter(arrayList);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);
    }
}
