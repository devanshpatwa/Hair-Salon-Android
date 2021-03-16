package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;


public class SalonDetailsAdapter extends RecyclerView.Adapter<SalonDetailsAdapter.MyViewHolder>{

    private String id;
    private String name;
    private ArrayList<String> mDataset;
    private ArrayList<String> time1AL;
   // static boolean isClickable = true;


    SalonDetailsAdapter(ArrayList<String> myDataset,ArrayList<String> time1AL,String id){
        mDataset=myDataset;
        this.time1AL=time1AL;
        //isClickable = true;
        this.id=id;
    }

    @NonNull
    @Override
    public SalonDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        return new MyViewHolder(layoutInflater,parent);
    }

    @Override
    public void onBindViewHolder(@NonNull SalonDetailsAdapter.MyViewHolder holder, int position) {
        holder.stylistName.setText(mDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

     class MyViewHolder extends RecyclerView.ViewHolder{
        TextView stylistName;
        MyViewHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.stylist_details,parent,false));
            stylistName = itemView.findViewById(R.id.txtStylistNames);


            itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction() == MotionEvent.ACTION_DOWN){
                       // if(isClickable){
                            //isClickable=false;
                            Intent i = new Intent(v.getContext(),booking_details.class);
                            i.putExtra("name",stylistName.getText().toString());
                            i.putExtra("time1",time1AL);
                            i.putExtra("nameDataset",mDataset);
                            i.putExtra("id",id);
                           // Log.v("pos",Integer.toString(position));
                            v.getContext().startActivity(i);

                        //}
                    }
                    return true;
                }

            });



        }
    }
}