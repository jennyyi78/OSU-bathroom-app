package com.example.osu_bathroom_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.osu_bathroom_app.R;
import com.example.osu_bathroom_app.model.Review;

import java.util.List;

public class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewRecyclerAdapter.ReviewViewHolder>{

    Context context;
    List<Review> list;
    RecyclerAdapter.OnNoteListener mOnNoteListener;

    public ReviewRecyclerAdapter(Context context, List<Review> list, RecyclerAdapter.OnNoteListener onNoteListener)
    {
        this.context=context;
        this.list=list;
        this.mOnNoteListener=onNoteListener;
    }

    @NonNull
    @Override
    public ReviewRecyclerAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.rvlist, parent, false);
        return new ReviewRecyclerAdapter.ReviewViewHolder(v, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review =list.get(position);
        float f=review.getRating();
        holder.rating.setText(Float.toString(f));
        holder.review.setText(review.getReview());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

    TextView rating,review;
    RecyclerAdapter.OnNoteListener onNoteListener;

        public ReviewViewHolder(@NonNull View itemView, RecyclerAdapter.OnNoteListener onNoteListener)
        {
         super(itemView);
         rating= itemView.findViewById(R.id.review_rating);
         review = itemView.findViewById(R.id.review_summary);
         this.onNoteListener = onNoteListener;
         itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view)
        {

            onNoteListener.onNoteClick(getAdapterPosition());


     }

    }



}
