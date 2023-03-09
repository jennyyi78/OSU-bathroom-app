package com.example.osu_bathroom_app;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BathroomListFragment extends Fragment implements RecyclerAdapter.OnNoteListener, AdapterView.OnItemSelectedListener {


    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    Spinner spinner;
    Button add;
    Button sort;
    static Boolean canClick=true;
    Boolean loop=true;

    ArrayList<Bathroom> list;
    String sortMethod;

    FrameLayout layout;
    private BathroomListViewModel mViewModel;
    View view;
    public static BathroomListFragment newInstance() {
        return new BathroomListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_bathroom_list, container, false);


        recyclerView=view.findViewById(R.id.bathroom_list);
        ExecutorService service= Executors.newSingleThreadExecutor();
        add=view.findViewById(R.id.addbtn);
        mViewModel=new ViewModelProvider(this).get(BathroomListViewModel.class);
        layout=view.findViewById(R.id.frame_layout);
        spinner=view.findViewById(R.id.spinner);
        sort=view.findViewById(R.id.sort_button);
        ArrayAdapter<CharSequence> adapterArray=ArrayAdapter.createFromResource(this.getContext(),R.array.sorting, android.R.layout.simple_spinner_item);
        adapterArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterArray);
        spinner.setOnItemSelectedListener(this);
        mViewModel.init();
        Log.i("Hello,",""+mViewModel.getBathrooms().getValue().toString());
        //mViewModel.getBathrooms().observe(this.getViewLifecycleOwner(), bathrooms -> adapter.notifyDataSetChanged());
            mViewModel.getBathrooms().observe(this.getViewLifecycleOwner(), new Observer<List<Bathroom>>() {
                @Override
                public void onChanged(List<Bathroom> bathrooms) {


                    adapter.notifyDataSetChanged();
                }
            });


                initRecyclerView();




        /*if (getActivity().getSupportFragmentManager().findFragmentById(R.id.frame_layout)==null)
        {
            canClick=true;

        }*/

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.execute(new Runnable() {
                    @Override
                    public void run() {
                        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                        AddBathroomFragment frag=new AddBathroomFragment();
                        fragmentTransaction.replace(R.id.fragment_container_view,frag);

                        fragmentTransaction.commit();

                        //mViewModel.addBathroom(new Bathroom("test","4"));
                        //Log.i("help",""+mViewModel.getBathrooms().getValue().size());

                    }
                });
            }
        });

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sortMethod.equals("A-Z"))
                {
                    sortBathrooms(0);
                    Log.i("spinners","A-Z!");
                }
                if(sortMethod.equals("Z-A"))
                {
                    sortBathrooms(1);
                    Log.i("spinners","Z-A!");
                }
            }
        });



            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    Log.i("Help", "You");
                    mViewModel.resetBathroom();
                }
            }, 600);



        return view;
    }

    private void initRecyclerView()
    {

        adapter=new RecyclerAdapter(this.getContext(),mViewModel.getBathrooms().getValue(),this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);





    }



    private void infoFragment(String name, String address)
    {
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        BathroomInfoFragment frag=new BathroomInfoFragment();
        Bundle args=new Bundle();
        args.putString("Name",name);
        args.putString("Address",address);

        frag.setArguments(args);
        fragmentTransaction.replace(R.id.frame_layout,frag);

        fragmentTransaction.commit();
        add.setEnabled(false);
        sort.setEnabled(false);
        spinner.setEnabled(false);



    }

    @Override
    public void onNoteClick(int position) {
        if (getActivity().getSupportFragmentManager().findFragmentById(R.id.frame_layout)==null) {
            Bathroom b = mViewModel.getBathrooms().getValue().get(position);
            Log.i("pos", "" + position);
            infoFragment(b.getName(), b.getAddress());
        }
    }

    public void sortBathrooms(int method)
    {
        if(method==0) {
            Collections.sort(mViewModel.getBathrooms().getValue(), new Comparator<Bathroom>() {
                @Override
                public int compare(Bathroom bathroom, Bathroom t1) {
                    return bathroom.getName().compareToIgnoreCase(t1.getName());
                }
            });
        }
        else if(method==1)
        {
            Collections.sort(mViewModel.getBathrooms().getValue(), new Comparator<Bathroom>() {
                @Override
                public int compare(Bathroom bathroom, Bathroom t1) {
                    return t1.getName().compareToIgnoreCase(bathroom.getName());
                }
            });
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        sortMethod=adapterView.getItemAtPosition(i).toString();
        if(sortMethod.equals("A-Z"))
        {
            Log.i("spinner","A-Z");
        }
        if(sortMethod.equals("Z-A"))
        {
            Log.i("spinner","Z-A");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}