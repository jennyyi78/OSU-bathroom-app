package com.example.osu_bathroom_app.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.osu_bathroom_app.R;
import com.example.osu_bathroom_app.UserProfileFragment;
import com.example.osu_bathroom_app.ui.MapFragment;
import com.example.osu_bathroom_app.ui.BathroomListFragment;
import com.example.osu_bathroom_app.ui.MyReviewsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class HomePageFragment extends Fragment
{
    View view;
    DatabaseReference ref;
    private FirebaseAuth mAuth;
    Button toBathroomMap, toBathroomListBtn, toFavorites, toUserProfileBtn, toMyReviews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_page, container, false);

        toUserProfileBtn = view.findViewById(R.id.user_profile_btn);
        toBathroomMap = view.findViewById(R.id.map_btn);
        toBathroomListBtn = view.findViewById(R.id.bathroom_list_btn);
        toFavorites = view.findViewById(R.id.favorites_btn);
        toMyReviews=view.findViewById(R.id.my_reviews_btn);
        toFavorites.setEnabled(false); //TODO - implement the favorites page, for now it will be unusable
        GlobalClass globalClass=(GlobalClass) getActivity().getApplicationContext();
        mAuth = FirebaseAuth.getInstance();
        String username=mAuth.getCurrentUser().getEmail();
        ref = FirebaseDatabase.getInstance().getReference().child("Users");
        ref.child("user1").child("id").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                long l=(long)task.getResult().getValue();
                globalClass.setUserId(l);
                Log.i("Complete","Complete "+globalClass.getUserId());
            }
        });

        toUserProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new UserProfileFragment());
            }
        });
        toBathroomMap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                replaceFragment(new MapFragment());
            }
        });
        toBathroomListBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                replaceFragment(new BathroomListFragment());
            }
        });
        toMyReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("My Clicked Button", "Clicked");
                replaceFragment(new MyReviewsFragment());
            }
        });


        return view;
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}