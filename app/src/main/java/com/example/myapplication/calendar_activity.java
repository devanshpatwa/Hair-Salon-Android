package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class calendar_activity extends AppCompatActivity {
    TextView txtDate;
    TextView txtTime;
    Button btnDate;
    Button btnTime;
    Button btnBook;
    String name;
   String time;
   ImageView imgUpload;
   public Uri imageUri;
   private FirebaseStorage storage;
   private StorageReference storageReference;
   private boolean flagDate=false;
    private boolean flagTime=false;
    private String am_pm="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_activity);

        imgUpload=findViewById(R.id.imgUpload);
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();


        Intent i=getIntent();
         name=i.getStringExtra("stylistName");
        time=i.getStringExtra("time");

        Toast.makeText(this,name , Toast.LENGTH_SHORT).show();

        txtDate=findViewById(R.id.txtDate);
        btnDate=findViewById(R.id.btnDate);
        txtTime=findViewById(R.id.txtTime);
        btnTime=findViewById(R.id.btnTime);
        btnBook=findViewById(R.id.btnBook);

        //Click listener for image upload
        imgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPicture();
            }
        });


        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagDate=true;
                handleDate();
            }
        });

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagTime=true;
                handleTime();
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flagTime==true && flagDate==true){
                    if(time.contains(txtDate.getText().toString()+txtTime.getText().toString())){
                        Toast.makeText(calendar_activity.this, "The date and time is already booked", Toast.LENGTH_SHORT).show();
                    }else {
                        time = ", " + time + "," + txtDate.getText().toString() + txtTime.getText().toString();
                        updateTimeDB(name, time);
                        Toast.makeText(calendar_activity.this, "Appointment Successfully Booked", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(calendar_activity.this, "Both Date and Time Should be selected", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }

    public void handleDate(){
        final Calendar calendar=Calendar.getInstance();

        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        final int date=calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dateString=year + "-" + month + "-" + date;


                Calendar calendar1=Calendar.getInstance();
                calendar1.set(Calendar.YEAR,year);
                calendar1.set(Calendar.MONTH,month);
                calendar1.set(Calendar.DATE,dayOfMonth);

                CharSequence dateCharSequence=DateFormat.format("EEEE, dd MMM yyyy",calendar1);
                txtDate.setText(dateCharSequence);
            }
        },year,month,date);
        datePickerDialog.show();
    }

    public void handleTime(){
        Calendar calendar=Calendar.getInstance();

        int HOUR=calendar.get(calendar.HOUR);
        int MINUTE=calendar.get(calendar.MINUTE);
        boolean is24HourFormat= DateFormat.is24HourFormat(this);

        TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {


                if(hour<8 || hour>20){
                    Toast.makeText(calendar_activity.this, "Salon Hours 8:00 am to 8:00 pm", Toast.LENGTH_SHORT).show();
                }else {

                    if(hour>=8 && hour<=12){
                        am_pm="AM";
                    }else{
                        am_pm="PM";
                    }


                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(Calendar.HOUR, hour);
                    calendar1.set(Calendar.MINUTE, minute);

                    CharSequence timeCharSequence = DateFormat.format("hh:mm", calendar1);
                    txtTime.setText(timeCharSequence+" " + am_pm);
                }
            }
        },HOUR,MINUTE,true);
    timePickerDialog.show();
    }

    //to update time in firebase
    public void updateTimeDB(String selectedName,String time){
        // DatabaseReference dbr= FirebaseDatabase.getInstance().getReference("Stylist").child(String.valueOf(id));

        DatabaseReference dbr= FirebaseDatabase.getInstance().getReference("Stylist").child(selectedName);

        HashMap hashMap=new HashMap();
        hashMap.put("time1",time);

        dbr.updateChildren(hashMap);

    }


    public void selectPicture(){
        Intent i =new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri=data.getData();
            imgUpload.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture() {
        final ProgressDialog pd;
        pd = new ProgressDialog(this);
        pd.setTitle("Uploading image");
        pd.show();

        final String randomKey= UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/"+ randomKey);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content),"Uploaded",Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        Toast.makeText(calendar_activity.this, "Failed to upload", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double progressPercent=(100.00*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                        pd.setMessage("Percentage " + (int)progressPercent+"%");
                    }
                });
    }
}