package com.example.osu_bathroom_app.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osu_bathroom_app.R;
import com.example.osu_bathroom_app.adapters.RecyclerAdapter;
import com.example.osu_bathroom_app.model.Favorite;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserProfileFragment extends Fragment  {
    private TextView welcomeTextView;
    TextView favoritesTextView;
    private Button linkToHomePageBtn;
    View view;
    private FirebaseAuth mAuth;

    DatabaseReference ref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_user_profile, container, false);
        favoritesTextView = view.findViewById(R.id.favorites_list);
        welcomeTextView = view.findViewById(R.id.welcome);

        ref = FirebaseDatabase.getInstance().getReference().child("Favorites");



        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        linkToHomePageBtn = view.findViewById(R.id.linkToHomePageButton);

        Query q = ref.orderByChild("user").equalTo(user.getEmail());

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StringBuilder sb = new StringBuilder();

                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    int id= childSnapshot.child("bathroomId").getValue(Integer.class);
                    sb.append(String.valueOf(id)).append("\n");
                }

                favoritesTextView.setText(sb.toString());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors that occur
            }
        });

        linkToHomePageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                getParentFragmentManager().popBackStack();
            }
        });

        if (user == null) {
            Toast.makeText(getActivity(), "User details not available", Toast.LENGTH_LONG).show();
        } else {
            showUserDetails(user);
        }
        return view;
    }

    private void showUserDetails(FirebaseUser user) {
        String email = user.getEmail();
        welcomeTextView.setText("Welcome, " + email + "!");

    }

}