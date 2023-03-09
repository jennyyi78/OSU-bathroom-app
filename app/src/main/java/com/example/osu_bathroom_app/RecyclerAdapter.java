package com.example.osu_bathroom_app;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    Context context;
    List<Bathroom> list;
   public Boolean canClick=true;

    OnNoteListener mOnNoteListener;

    public RecyclerAdapter(Context context, List<Bathroom> list, OnNoteListener onNoteListener) {
        this.context = context;
        this.list = list;

        this.mOnNoteListener=onNoteListener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.brlist,parent,false);
        return new MyViewHolder(v,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Bathroom bathroom=list.get(position);
        holder.name.setText(bathroom.getName());
        holder.address.setText(bathroom.getAddress());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name,address;
        OnNoteListener onNoteListener;
        public MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener){
            super(itemView);
            name=itemView.findViewById(R.id.bathroom_name);
            address=itemView.findViewById(R.id.bathroom_address);
            this.onNoteListener=onNoteListener;
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {

            onNoteListener.onNoteClick(getAdapterPosition());


        }

    }

    public interface OnNoteListener
    {
        void onNoteClick(int position);


    }
}
