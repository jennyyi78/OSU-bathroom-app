package com.example.osu_bathroom_app.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.osu_bathroom_app.R;
import com.example.osu_bathroom_app.main.GlobalClass;
import com.example.osu_bathroom_app.model.Review;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddReviewFragment extends Fragment
{

    DatabaseReference ref;
    Button submit;
    Button back;
    EditText reviewText;
    RatingBar bar;
    GlobalClass globalClass;
    long bathroomId = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v;
        v = inflater.inflate(R.layout.fragment_add_review, container, false);
        globalClass = (GlobalClass) getActivity().getApplicationContext();
        submit = v.findViewById(R.id.reviewBtn);
        back = v.findViewById(R.id.add_back_btn);
        reviewText = v.findViewById(R.id.reviewText);
        bar = v.findViewById(R.id.ratingBar);
        Bundle bundle = getArguments();
        bathroomId = (long) bundle.get("Id");
        ref = FirebaseDatabase.getInstance().getReference().child("Reviews");
        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                backToList();
            }
        });
        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                addReview();
            }
        });
        return v;
    }

    private void backToList()
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BathroomListFragment frag = new BathroomListFragment();
        fragmentTransaction.replace(R.id.fragment_container_view, frag);
        fragmentTransaction.commit();
    }

    private void addReview()
    {
        long n = 0;
        float rating = bar.getRating();
        String review = reviewText.getText().toString();
        long l = globalClass.getUserId();
        Log.i("BID", "" + bathroomId);
        FirebaseDatabase.getInstance().getReference().child("NumId").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task)
            {
                long newId = (long) task.getResult().getValue();
                Review r = new Review(newId, bathroomId, rating, review, l);
                ref.push().setValue(r);
                long x = newId + 1;
                FirebaseDatabase.getInstance().getReference().child("NumId").setValue(x);

            }
        });

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