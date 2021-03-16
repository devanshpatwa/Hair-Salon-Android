package com.example.myapplication;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class HomePageAdapter extends FirebaseRecyclerAdapter<StyleStudio, HomePageAdapter.HomePageHolder> {

    public HomePageAdapter(FirebaseRecyclerOptions<StyleStudio> options){
        super(options);
    }


    public HomePageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        return new HomePageHolder(layoutInflater,parent);
    }

    public void onBindViewHolder(HomePageHolder holder, final int position, StyleStudio model){
        StorageReference storeRef= FirebaseStorage.getInstance().getReferenceFromUrl(model.getImage());
        holder.salonName.setText(model.getName());
        Glide.with(holder.salonNameImage.getContext()).load(storeRef).into(holder.salonNameImage);


        // Click listener for CardView
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=getRef(position).getKey();

                Intent i = new Intent(v.getContext(),salon_details_activity.class);
                i.putExtra("id",id);
                v.getContext().startActivity(i);
            }
        });

    }

    static class HomePageHolder extends RecyclerView.ViewHolder {
            CardView cardView;
            ImageView salonNameImage;
            TextView salonName;

            HomePageHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.homepage_salon_cardview, parent, false));
                cardView = itemView.findViewById(R.id.cardViewSalon);
                salonNameImage = itemView.findViewById(R.id.imageViewSalonName);
                salonName=itemView.findViewById(R.id.TextViewName);
            }
    }

}
