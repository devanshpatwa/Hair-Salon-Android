//package com.example.myapplication;
//
//import android.net.Uri;
//import android.view.LayoutInflater;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.cardview.widget.CardView;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Map;
//
//public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
//
//    private ArrayList<Integer> myMapset;
//    private ArrayList<String> salonName;
//
//    MyAdapter(ArrayList<Integer> myMap,ArrayList<String> salonName) {
//        myMapset = myMap;
//        this.salonName=salonName;
//    }
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
//        return new MyViewHolder(layoutInflater,parent);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
//        holder.salonNameImage.setImageResource(myMapset.get(position));
//        holder.salonName.setText(salonName.get(position));
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return myMapset.size();
//    }
//
//    static class MyViewHolder extends RecyclerView.ViewHolder {
//        CardView cardView;
//        ImageView salonNameImage;
//        TextView salonName;
//
//        MyViewHolder(LayoutInflater inflater, ViewGroup parent) {
//            super(inflater.inflate(R.layout.homepage_salon_cardview, parent, false));
//            cardView = itemView.findViewById(R.id.cardViewSalon);
//            salonNameImage = itemView.findViewById(R.id.imageViewSalonName);
//            salonName=itemView.findViewById(R.id.TextViewName);
//        }
//    }
//}
//
//
