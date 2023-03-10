package com.example.osu_bathroom_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserProfileFragment extends Fragment {
    private TextView welcomeTextView;

    private Button linkToHomePageBtn;
    View view;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_user_profile, container, false);

        welcomeTextView = view.findViewById(R.id.welcome);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        linkToHomePageBtn = view.findViewById(R.id.linkToHomePageButton);

        linkToHomePageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                replaceFragment(new HomePageFragment());
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

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view,fragment);
        fragmentTransaction.commit();

    }

}