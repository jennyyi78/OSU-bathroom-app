package com.example.osu_bathroom_app.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osu_bathroom_app.R;
import com.example.osu_bathroom_app.adapters.RecyclerAdapter;
import com.example.osu_bathroom_app.model.Bathroom;
import com.example.osu_bathroom_app.model.Favorite;
import com.example.osu_bathroom_app.view_model.BathroomListViewModel;
import com.example.osu_bathroom_app.view_model.FavoriteListViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.example.osu_bathroom_app.main.GlobalClass;
import java.util.ArrayList;
import java.util.List;

public class UserProfileFragment extends Fragment implements RecyclerAdapter.OnNoteListener, AdapterView.OnItemSelectedListener  {
    private TextView welcomeTextView;
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    private Button linkToHomePageBtn;
    View view;
    private FirebaseAuth mAuth;
    GlobalClass globalClass;

    String sortMethod;


    DatabaseReference ref;

    private FavoriteListViewModel mViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_user_profile, container, false);
        recyclerView = view.findViewById(R.id.favorites_list);
        welcomeTextView = view.findViewById(R.id.welcome);
        mViewModel = new ViewModelProvider(this).get(FavoriteListViewModel.class);
        ref = FirebaseDatabase.getInstance().getReference().child("Favorites");
        GlobalClass globalClass=(GlobalClass) getActivity().getApplicationContext();
        mViewModel.init(globalClass.getUserId());


        mViewModel.getMyFavorites().observe(this.getViewLifecycleOwner(), new Observer<List<Bathroom>>()
        {
            @Override
            public void onChanged(List<Bathroom> bathrooms)
            {
                adapter.notifyDataSetChanged();
            }
        });

        initRecyclerView();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        linkToHomePageBtn = view.findViewById(R.id.linkToHomePageButton);

        linkToHomePageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                getParentFragmentManager().popBackStack();
            }
        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Log.i("Help", "You");
                mViewModel.resetBathroom();
            }
        }, 600);

        if (user == null) {
            Toast.makeText(getActivity(), "User details not available", Toast.LENGTH_LONG).show();
        } else {
            showUserDetails(user);
        }
        return view;
    }

    private void initRecyclerView()
    {
        Log.i("Lengthfavs",""+mViewModel.getMyFavorites().getValue().size());
        adapter = new RecyclerAdapter(this.getContext(), mViewModel.getMyFavorites().getValue(), this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

    }

    private void showUserDetails(FirebaseUser user) {
        String email = user.getEmail();
        welcomeTextView.setText("Welcome, " + email + "!");

    }

    private void infoFragment(String name, String address, long id)
    {
        globalClass=(GlobalClass) getActivity().getApplicationContext();
        globalClass.setCurrentPage(1);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BathroomInfoFragment frag = new BathroomInfoFragment();
        Bundle args = new Bundle();
        args.putString("Name", name);
        args.putString("Address", address);
        args.putLong("Id",id);

        Log.i("select","BRLIST: "+id);
        frag.setArguments(args);
        fragmentTransaction.replace(R.id.frame_layout_1, frag);

        fragmentTransaction.commit();
        linkToHomePageBtn.setEnabled(false);

    }

    @Override
    public void onNoteClick(int position)
    {
        if (getActivity().getSupportFragmentManager().findFragmentById(R.id.frame_layout) != null) {
            Log.i("info", "testinfo: " + getActivity().getSupportFragmentManager().findFragmentById(R.id.frame_layout).toString());
        }
        if (getActivity().getSupportFragmentManager().findFragmentById(R.id.frame_layout) == null) {
            Bathroom b = mViewModel.getMyFavorites().getValue().get(position);
            Log.i("pos", "" + position);
            Log.i("info","info");
            infoFragment(b.getName(), b.getAddress(),b.getId());
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
        sortMethod = adapterView.getItemAtPosition(i).toString();
        if (sortMethod.equals("A-Z")) {
            Log.i("spinner", "A-Z");
        }
        if (sortMethod.equals("Z-A")) {
            Log.i("spinner", "Z-A");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }


}