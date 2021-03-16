package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class salon_details_activity extends AppCompatActivity {

    private String id;
    DatabaseReference databaseRefStudio,databaseRefStylist;
    private ImageView imgSalon;
    private TextView txtStylistNames;
    private RatingBar ratingSalon;
    private TextView txtAbout;
    private String about;
    private int ratings;
    private Spinner spinnerService;

    // variables for setting the recycler view for stylist
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<String> myDataset=new ArrayList<String>();
    private ArrayList<String> time1AL=new ArrayList<String>();
    private ArrayList<String> servicesAL=new ArrayList<String>();
    private ArrayList<String> ratingStylistAL=new ArrayList<String>();
    private ArrayList<String> aboutStylistAL=new ArrayList<String>();
    private ArrayList<String> profile_imgAL=new ArrayList<String>();
    String name="";
    String time1="";
    String services="";
    Float ratingStylist;
    String aboutStylist="";
    String profile_img="";
    private boolean check=false;
    private Button btnLocation;

    private double v1;
    private double v2;
    private String name_salon;



    //variables for Book button
    Button btnBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_details_activity);

        btnLocation=findViewById(R.id.btnLocation);

//        ratingSalon=findViewById(R.id.ratingBarSalon);
        txtAbout=findViewById(R.id.textViewAbout);
        spinnerService=findViewById(R.id.spinnerService);
//        txtStylistNames=findViewById(R.id.textViewStylistNames);
//        txtStylistNames.setText("Our Hair Stylists and Experts");


        //Creating ArrayList for all the available services
        List<String> availableServices=new ArrayList<>();
        availableServices.add("--Choose Your Service--");
        availableServices.add("HAIRCUT");
        availableServices.add("HAIR COLOR");
        availableServices.add("EYELASH LIFT");
        availableServices.add("BODY TATTOO");

        //Setting up the Spinner control
        ArrayAdapter<String> servicesAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,availableServices);
        servicesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerService.setAdapter(servicesAdapter);

        spinnerService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(check){
                String selection=spinnerService.getSelectedItem().toString();
                Log.i("selection",selection);
                Intent i=new Intent(view.getContext(),stylist_info_acticity.class);
                i.putExtra("namesArray",myDataset);
                i.putExtra("timeArray",time1AL);
                i.putExtra("servicesArray",servicesAL);
                i.putExtra("aboutArray",aboutStylistAL);
                i.putExtra("ratingArray",ratingStylistAL);
                i.putExtra("selection",selection);
                i.putExtra("profile_img",profile_imgAL);

                view.getContext().startActivity(i);}
                else{
                    check=true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        // Retrieving Id passed from HomeAdapter Intenet
        id=getIntent().getExtras().get("id").toString();


        // Setting the database Reference for "Stylist Studio" and attaching EventListener
        databaseRefStudio=FirebaseDatabase.getInstance().getReference("StyleStudio").child(id);
        databaseRefStudio.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name_salon = dataSnapshot.child("name").getValue().toString();
                int salon_id = Integer.parseInt(dataSnapshot.child("id").getValue().toString());
                String image_url=dataSnapshot.child("image").getValue().toString();
                about= dataSnapshot.child("about").getValue().toString();
                ratings=Integer.parseInt(dataSnapshot.child("ratings").getValue().toString());
                v1=Double.parseDouble(dataSnapshot.child("v").getValue().toString());
                v2=Double.parseDouble(dataSnapshot.child("v1").getValue().toString());


                txtAbout.setText(about);

                //Set the ratings
//                ratingSalon.setRating(ratings);

                // For setting the salon image
                imgSalon=findViewById(R.id.imgSalon);

                StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(image_url);

                GlideApp.with(imgSalon)
                        .load(ref)
                        .into(imgSalon);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Setting the database Reference for "Stylist" and attaching EventListener
        databaseRefStylist=FirebaseDatabase.getInstance().getReference("Stylist");
        databaseRefStylist.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if(Integer.parseInt(ds.child("id").getValue().toString())==Integer.parseInt(id)){
                        time1=ds.child("time1").getValue(String.class);
                        name = ds.child("name").getValue(String.class);
                        services=ds.child("services").getValue(String.class);
                        aboutStylist=ds.child("about").getValue(String.class);
                        ratingStylist=ds.child("rating").getValue(Float.class);
                        profile_img=ds.child("p_i").getValue(String.class);


                        myDataset.add(name);
                        time1AL.add(time1);
                        profile_imgAL.add(profile_img);
                        servicesAL.add(services);
                        aboutStylistAL.add(aboutStylist);
                        ratingStylistAL.add(ratingStylist.toString());

                    }
                }
//                RecyclerDisplayStylist(myDataset);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //For Location Page
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(),GoogleMapAPI.class);
                i.putExtra("v1",v1);
                i.putExtra("v2",v2);
                i.putExtra("name",name_salon);

                startActivity(i);
            }
        });
    }


}