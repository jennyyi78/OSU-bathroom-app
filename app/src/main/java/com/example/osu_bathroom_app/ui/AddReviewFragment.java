package com.example.osu_bathroom_app.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.osu_bathroom_app.R;
import com.example.osu_bathroom_app.main.GlobalClass;
import com.example.osu_bathroom_app.model.Review;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddReviewFragment extends Fragment {

    DatabaseReference ref;
    Button submit;
    EditText reviewText;
    RatingBar bar;
    GlobalClass globalClass;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        v= inflater.inflate(R.layout.fragment_add_review, container, false);
        globalClass=(GlobalClass)getActivity().getApplicationContext();
       submit=v.findViewById(R.id.reviewBtn);
        reviewText=v.findViewById(R.id.reviewText);
        bar=v.findViewById(R.id.ratingBar);
        ref= FirebaseDatabase.getInstance().getReference().child("Reviews");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReview();
            }
        });
        return v;
    }

   private void addReview()
    {
        float rating=bar.getRating();
        String review=reviewText.getText().toString();
        long l=(long)globalClass.getUserId();
        Review r=new Review(6,2,rating,review,l);
        ref.push().setValue(r);
        returnToBathroomList();

    }
    private void returnToBathroomList()
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BathroomListFragment frag = new BathroomListFragment();
        fragmentTransaction.replace(R.id.fragment_container_view, frag);
        fragmentTransaction.commit();
    }
}