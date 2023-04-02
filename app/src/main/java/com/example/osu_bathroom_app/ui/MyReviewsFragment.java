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

import com.example.osu_bathroom_app.R;
import com.example.osu_bathroom_app.adapters.RecyclerAdapter;
import com.example.osu_bathroom_app.adapters.ReviewRecyclerAdapter;
import com.example.osu_bathroom_app.adapters.MyReviewRecyclerAdapter;
import com.example.osu_bathroom_app.main.GlobalClass;
import com.example.osu_bathroom_app.model.Review;
import com.example.osu_bathroom_app.view_model.MyReviewListViewModel;
import com.example.osu_bathroom_app.view_model.ReviewListViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyReviewsFragment extends Fragment implements MyReviewRecyclerAdapter.OnNoteListener, AdapterView.OnItemSelectedListener{

    RecyclerView recyclerView;
    DatabaseReference ref;
    private FirebaseAuth mAuth;
    MyReviewRecyclerAdapter adapter;
    ArrayList<Review> list;
    private MyReviewListViewModel mViewModel;
    Button backBtn;

    int userId=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_my_reviews, container, false);
        Handler handler = new Handler();
        ExecutorService service = Executors.newSingleThreadExecutor();
      /*  mAuth = FirebaseAuth.getInstance();
        String username=mAuth.getCurrentUser().getEmail();
        ref = FirebaseDatabase.getInstance().getReference().child("Users");
       ref.child("user1").child("id").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Long l=(Long)task.getResult().getValue();
                userId=l.intValue();
                Log.i("Complete","Complete "+userId);
            }
        });*/
        GlobalClass globalClass=(GlobalClass) getActivity().getApplicationContext();
        recyclerView = v.findViewById(R.id.my_reviews_list);
        mViewModel=new ViewModelProvider(this).get(MyReviewListViewModel.class);
        mViewModel.init(globalClass.getUserId());
        backBtn=v.findViewById(R.id.review_back_btn);
        mViewModel.getMyReviews().observe(this.getViewLifecycleOwner(), new Observer<List<Review>>()
        {
            @Override
            public void onChanged(List<Review> reviews)
            {
                adapter.notifyDataSetChanged();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getParentFragmentManager().popBackStack();
            }
        });

        initRecyclerView();


        handler.postDelayed(new Runnable()
        {
            public void run()
            {
                Log.i("Help", "You");
                mViewModel.resetReview();
            }
        }, 600);
        return v;
    }
    private void initRecyclerView()
    {
        Log.i("Checking repo",""+ mViewModel.getMyReviews().getValue().toString());
        adapter = new MyReviewRecyclerAdapter(this.getContext(), mViewModel.getMyReviews().getValue(), this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setOnButtonClickListener(new MyReviewRecyclerAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(int position) {
                mViewModel.removeReview(position);
            }
        });



    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onNoteClick(int position) {

    }

}