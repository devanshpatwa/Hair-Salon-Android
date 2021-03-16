package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import java.util.Map;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.Query;

public class MainActivity extends AppCompatActivity {

//    private RecyclerView recyclerView;
//    private RecyclerView.Adapter mAdapter;
//    private RecyclerView.LayoutManager layoutManager;
//    private ArrayList<Integer> myMapset;
//    private ArrayList<String> salonName;


    private Query query;
    private HomePageAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        query= FirebaseDatabase.getInstance().getReference().child("StyleStudio");

        FirebaseRecyclerOptions<StyleStudio> options=new FirebaseRecyclerOptions.Builder<StyleStudio>().setQuery(query, StyleStudio.class).build();

        adapter=new HomePageAdapter(options);

        recyclerView=findViewById(R.id.recyclerViewMain);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }
}
