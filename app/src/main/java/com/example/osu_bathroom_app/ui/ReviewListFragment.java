package com.example.osu_bathroom_app.ui;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.osu_bathroom_app.R;
import com.example.osu_bathroom_app.adapters.RecyclerAdapter;
import com.example.osu_bathroom_app.adapters.ReviewRecyclerAdapter;
import com.example.osu_bathroom_app.model.Review;
import com.example.osu_bathroom_app.view_model.ReviewListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ReviewListFragment extends Fragment implements RecyclerAdapter.OnNoteListener, AdapterView.OnItemSelectedListener
{


    RecyclerView recyclerView;
    ReviewRecyclerAdapter adapter;
    long id;
    ArrayList<Review> list;
    Button backBtn;
    private ReviewListViewModel mViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view;
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_review_list, container, false);
        ExecutorService service = Executors.newSingleThreadExecutor();
        Bundle bundle = getArguments();
        id = bundle.getLong("Id");
        recyclerView = view.findViewById(R.id.review_list);
        mViewModel = new ViewModelProvider(this).get(ReviewListViewModel.class);
        Log.i("select", "RVlist: " + id);
        mViewModel.init(id);
        backBtn = view.findViewById(R.id.button_back);

        mViewModel.getReviews().observe(this.getViewLifecycleOwner(), new Observer<List<Review>>()
        {
            @Override
            public void onChanged(List<Review> reviews)
            {
                adapter.notifyDataSetChanged();
            }
        });

        initRecyclerView();


        backBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                returnToList();
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            public void run()
            {
                Log.i("Help", "You");
                mViewModel.resetReview();
            }
        }, 600);

        return view;
    }

    private void initRecyclerView()
    {
        adapter = new ReviewRecyclerAdapter(this.getContext(), mViewModel.getReviews().getValue(), this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);


    }

    private void returnToList()
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BathroomListFragment frag = new BathroomListFragment();
        fragmentTransaction.replace(R.id.fragment_container_view, frag);
        fragmentTransaction.commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }

    @Override
    public void onNoteClick(int position)
    {

    }


}