package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class booking_details extends AppCompatActivity {

    private ArrayList timeAl;
    private ArrayList namesAl;
    private String selectedName="";
    private String id;

    private ArrayList ConvertedTimeAl;
    private ListView lv;

    private String finalStrPutDb;

    private TextView txtMessage;

    private String selectTime;
    private String finalDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        //Using bundle object to fetch the arraylist from SalonDetailsAdapter for time
        Bundle bundle = getIntent().getExtras();
        timeAl=bundle.getStringArrayList("time1");
        namesAl=bundle.getStringArrayList("nameDataset");
        selectedName=bundle.getString("name");
        id=bundle.getString("id");

        //Calling the function to fetch specific user time and convert
        ConvertedTimeAl=timeSeperator(timeAl,namesAl,selectedName);

        //Setting arraylist to the listview
        lv=findViewById(R.id.listviewTime);

        String st=ConvertedTimeAl.get(0).toString();

        //To fetch Current Date
        Calendar cal=Calendar.getInstance();
        Date date = cal.getTime();

        cal.add(Calendar.DATE,1);
        cal.add(Calendar.MONTH,0);
        cal.add(Calendar.YEAR,0);
        Date nextDay=cal.getTime();

        SimpleDateFormat datefm = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        finalDate = datefm.format(nextDay);
       // finalDate=nextDay.toString();

        txtMessage=findViewById(R.id.txtViewMessage);
        if(st.equals("")){
            txtMessage.setText("No Available Time Slots. Please select another stylist");
        }
        else{
            txtMessage.setText("Available Time Slots for " + finalDate +" . Please make a selection");
        }


        ArrayAdapter<String> items=new ArrayAdapter<String>(this,R.layout.row,ConvertedTimeAl);
        lv.setAdapter(items);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 selectTime= (String) lv.getItemAtPosition(position);

                new AlertDialog.Builder(booking_details.this)
                        .setTitle("Booking Confirmation")
                        .setMessage("Hurray! Your Booking is Confirmed with our Stylist "+selectedName + " for " +finalDate+" at "+selectTime)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //removing the selected time slot from the array
                                        ConvertedTimeAl.remove(new String(selectTime));

                                        //Passing New Array to function to convert it to a comma seperated string;
                                        finalStrPutDb=stringMaker(ConvertedTimeAl);
                                        updateTimeDB(selectedName,finalStrPutDb);
                                        Intent i=new Intent(booking_details.this,MainActivity.class);
                                        startActivity(i);
                                    }
                                    }).show();
            }
        });
    }

    public ArrayList<String> timeSeperator(ArrayList timeAl, ArrayList namesAl, String selectedName){

        String time="";

        int position=namesAl.indexOf(selectedName);
        time= (String) timeAl.get(position);

        //converting string to string array using split
        String[] splitTime=time.split(",");

        // Converting string into list and passing to an arralist
        List<String> temp=Arrays.asList(splitTime);
        ArrayList<String> finalTimeArrayList= new ArrayList<String>(temp);

        return finalTimeArrayList;

    }

    public String stringMaker(ArrayList newConvertedTimeAl){
        String finalStr=newConvertedTimeAl.toString();
        finalStr= finalStr.replace("[","")
                .replace("]","")
                .replace(" ","");
        return finalStr;
    }


    //to update time in firebase
    public void updateTimeDB(String selectedName,String time){
       // DatabaseReference dbr= FirebaseDatabase.getInstance().getReference("Stylist").child(String.valueOf(id));

        DatabaseReference dbr= FirebaseDatabase.getInstance().getReference("Stylist").child(selectedName);

        HashMap hashMap=new HashMap();
        hashMap.put("time1",time);

        dbr.updateChildren(hashMap);

    }
}