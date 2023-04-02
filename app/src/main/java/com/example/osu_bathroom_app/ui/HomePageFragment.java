package com.example.osu_bathroom_app.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.osu_bathroom_app.R;


public class HomePageFragment extends Fragment
{

    View view;
    Button toBathroomMap;
    Button toBathroomListBtn;
    Button toFavorites;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_page, container, false);

        toBathroomMap = view.findViewById(R.id.map_btn);
        toBathroomListBtn = view.findViewById(R.id.bathroom_list_btn);
        toFavorites = view.findViewById(R.id.favorites_btn);


        toFavorites.setEnabled(false); //TODO - implement the favorites page, for now it will be unusable
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




        return view;
    }

    private void replaceFragment(Fragment fragment)
    {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view, fragment);
        fragmentTransaction.commit();

    }

}