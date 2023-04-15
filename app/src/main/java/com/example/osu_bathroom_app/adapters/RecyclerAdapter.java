package com.example.osu_bathroom_app.adapters;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.osu_bathroom_app.R;
import com.example.osu_bathroom_app.model.Bathroom;
import com.example.osu_bathroom_app.model.Review;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>
{

    static FirebaseAuth mAuth;
    public Boolean canClick = true;
    Context context;
    List<Bathroom> list;
    int viewType;
    List<Review> reviewList;
    OnNoteListener mOnNoteListener;
    OnButtonClickListener bListener;

    public RecyclerAdapter(Context context, List<Bathroom> list, OnNoteListener onNoteListener)
    {
        this.context = context;
        if (this.list != null) {
            this.list.clear();
        }
        this.list = list;
        Log.i("adapter", "" + this.list.size());
        this.mOnNoteListener = onNoteListener;
        mAuth = FirebaseAuth.getInstance();

    }

    public void setOnButtonClickListener(OnButtonClickListener buttonListener)
    {
        bListener = buttonListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.brlist, parent, false);


        MyViewHolder v1 = new MyViewHolder(v, mOnNoteListener, bListener);
        //v1.setIsRecyclable(false);
        Log.i("adapter", "" + this.list.size());

        return v1;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        //Log.i("adapterhelp","help: "+list.size());
        Bathroom bathroom = list.get(position);
        holder.name.setText(bathroom.getName());
        holder.address.setText(bathroom.getAddress());
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public void filterList(ArrayList<Bathroom> br)
    {
        list = br;
        notifyDataSetChanged();
    }

    public interface OnButtonClickListener
    {
        void onButtonClick(int position);
    }

    public interface OnNoteListener
    {
        void onNoteClick(int position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView name, address;
        Button deleteBtn;
        OnNoteListener onNoteListener;
        OnButtonClickListener bListener;


        public MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener, OnButtonClickListener buttonClickListener)
        {
            super(itemView);
            name = itemView.findViewById(R.id.bathroom_name);
            address = itemView.findViewById(R.id.bathroom_address);
            deleteBtn = itemView.findViewById(R.id.delete_button);
            this.onNoteListener = onNoteListener;
            this.bListener = buttonClickListener;


            deleteBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    bListener.onButtonClick(getAdapterPosition());
                }
            });


            itemView.setOnClickListener(this);

            FirebaseUser user = mAuth.getCurrentUser();

            String email = user.getEmail();
            if (!email.equals("jennyyi78@gmail.com")) {
                deleteBtn.setVisibility(View.GONE);
            }


        }


        @Override
        public void onClick(View view)
        {
            onNoteListener.onNoteClick(getAdapterPosition());
        }

    }


}
