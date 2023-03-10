package com.example.osu_bathroom_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.osu_bathroom_app.list_page.BathroomListFragment;


public class HomePageFragment extends Fragment {

    View view;
    Button toBathroomListBtn, toUserProfileBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_home_page, container, false);
        toBathroomListBtn=view.findViewById(R.id.bathroom_list_btn);
        toUserProfileBtn=view.findViewById(R.id.user_profile_btn);

        toBathroomListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new BathroomListFragment());
            }
        });

        toUserProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new UserProfileFragment());
            }
        });


        return view;
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view,fragment);
        fragmentTransaction.commit();

    }
}