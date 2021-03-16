package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class stylist_info_acticity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList nameAl;
    private ArrayList ratingAl;
    private ArrayList timeAl;
    private ArrayList servicesAl;
    private ArrayList aboutAl;
    private String selection="";
    private String profile_img="";

    private ArrayList<String> newNameAl=new ArrayList<>();
    private ArrayList<String> newRatingAl=new ArrayList<>();
    private ArrayList<String> newAboutAl=new ArrayList<>();
    private ArrayList<String> newTimeAl=new ArrayList<>();
    private ArrayList<String> profile_imgAl=new ArrayList<>();

    private int indx=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stylist_info_acticity);




        //Getting the arrayLists from salon_details_activity page
        Bundle bundle = getIntent().getExtras();
        nameAl=bundle.getStringArrayList("namesArray");
        ratingAl=bundle.getStringArrayList("ratingArray");
        timeAl=bundle.getStringArrayList("timeArray");
        servicesAl=bundle.getStringArrayList("servicesArray");
        aboutAl=bundle.getStringArrayList("aboutArray");
        selection=bundle.getString("selection");
        profile_imgAl=bundle.getStringArrayList("profile_img");

        create();

        recyclerView=findViewById(R.id.rView);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        mAdapter=new StylistInfoAdapter(newNameAl,newRatingAl,newTimeAl,servicesAl,newAboutAl,selection,profile_imgAl);
        recyclerView.setAdapter(mAdapter);




    }

    public void create(){
        //creating new arrays for names,ratings,etc based on the service selection
        for(Object ser : servicesAl) {
            indx++;
            if (ser.toString().toUpperCase().contains(selection)) {
//                int index = servicesAl.indexOf(ser);
                String s = nameAl.get(indx).toString();
                newNameAl.add(s);
                newRatingAl.add(ratingAl.get(indx).toString());
                newAboutAl.add(aboutAl.get(indx).toString());
                newTimeAl.add(timeAl.get(indx).toString());
            }
        }
    }
}