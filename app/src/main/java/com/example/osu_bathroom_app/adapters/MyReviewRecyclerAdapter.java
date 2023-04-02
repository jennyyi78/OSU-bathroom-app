package com.example.osu_bathroom_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.osu_bathroom_app.R;
import com.example.osu_bathroom_app.model.Review;

import java.util.List;

public class MyReviewRecyclerAdapter extends RecyclerView.Adapter<MyReviewRecyclerAdapter.MyReviewViewHolder>{

    Context context;
    List<Review> list;
   OnNoteListener mOnNoteListener;
    OnButtonClickListener bListener;

    public interface OnButtonClickListener
    {
        void onButtonClick(int position);
    }
    public void setOnButtonClickListener(MyReviewRecyclerAdapter.OnButtonClickListener buttonListener)
    {
        bListener=buttonListener;
    }

    public MyReviewRecyclerAdapter(Context context, List<Review> list, MyReviewRecyclerAdapter.OnNoteListener onNoteListener)
    {
        this.context=context;
        this.list=list;
        this.mOnNoteListener=onNoteListener;
    }

    @NonNull
    @Override
    public MyReviewRecyclerAdapter.MyReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.myrvlist, parent, false);
        return new MyReviewRecyclerAdapter.MyReviewViewHolder(v, mOnNoteListener,bListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyReviewViewHolder holder, int position) {
        Review review =list.get(position);
        float f=review.getRating();
        holder.rating.setText(Float.toString(f));
        holder.review.setText(review.getReview());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView rating,review;
        Button delete;
        MyReviewRecyclerAdapter.OnNoteListener onNoteListener;
        MyReviewRecyclerAdapter.OnButtonClickListener bListener;
        public MyReviewViewHolder(@NonNull View itemView, MyReviewRecyclerAdapter.OnNoteListener onNoteListener, MyReviewRecyclerAdapter.OnButtonClickListener buttonClickListener)
        {
            super(itemView);
            rating= itemView.findViewById(R.id.review_rating);
            review = itemView.findViewById(R.id.review_summary);
            delete=itemView.findViewById(R.id.rv_list_delete);
            this.onNoteListener = onNoteListener;
            this.bListener=buttonClickListener;
            itemView.setOnClickListener(this);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bListener.onButtonClick(getAdapterPosition());
                }
            });


        }

        @Override
        public void onClick(View view)
        {

            onNoteListener.onNoteClick(getAdapterPosition());


        }

    }
    public interface OnNoteListener
    {
        void onNoteClick(int position);
    }


}