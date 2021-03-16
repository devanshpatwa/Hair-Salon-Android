package com.example.myapplication;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class StylistInfoAdapter extends RecyclerView.Adapter<StylistInfoAdapter.MyViewHolder> {
    private ArrayList nameAl;
    private ArrayList ratingAl;
    private ArrayList timeAl;
    private ArrayList servicesAl;
    private ArrayList profile_imgAl;
    private ArrayList aboutAl;
    private String selection;
    private String profile_img;



    StylistInfoAdapter(ArrayList<String> nameAl, ArrayList<String> ratingAl, ArrayList<String> timeAl,
                       ArrayList<String> servicesAl, ArrayList<String> aboutAl,String selection,ArrayList<String> profile_imgAl) {
        this.nameAl = nameAl;
        this.ratingAl = ratingAl;
        this.timeAl = timeAl;
        this.servicesAl = servicesAl;
        this.aboutAl = aboutAl;
        this.selection=selection;
        this.profile_imgAl=profile_imgAl;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        return new MyViewHolder(layoutInflater,parent);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        holder.txtName.setText(nameAl.get(position).toString());
        holder.txtRating.setText(ratingAl.get(position).toString());
        holder.txtAbout.setText(aboutAl.get(position).toString());
        holder.txtTime.setText(timeAl.get(position).toString());
        holder.txtTime.setVisibility(View.INVISIBLE);

        StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(profile_imgAl.get(position).toString());

        GlideApp.with(holder.imageView)
                .load(ref)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(),calendar_activity.class);
                i.putExtra("stylistName",holder.txtName.getText().toString());
                i.putExtra("time",holder.txtTime.getText().toString());
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nameAl.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtRating;
        TextView txtAbout;
        TextView btnRequest;
        TextView txtTime;
        ImageView imageView;

        MyViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.stylist_info_recyclerview, parent, false));
            txtName = itemView.findViewById(R.id.txtName);
            txtAbout = itemView.findViewById(R.id.txtAbout);
            txtRating = itemView.findViewById(R.id.txtRating);
            btnRequest = itemView.findViewById(R.id.btnRequest);
            txtTime=itemView.findViewById(R.id.txtTime);
            imageView=itemView.findViewById(R.id.profile_image);


        }

    }
}
