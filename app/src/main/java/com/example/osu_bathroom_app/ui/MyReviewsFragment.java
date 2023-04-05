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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.osu_bathroom_app.R;
import com.example.osu_bathroom_app.adapters.MyReviewRecyclerAdapter;
import com.example.osu_bathroom_app.main.GlobalClass;
import com.example.osu_bathroom_app.model.Review;
import com.example.osu_bathroom_app.view_model.MyReviewListViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyReviewsFragment extends Fragment implements MyReviewRecyclerAdapter.OnNoteListener, AdapterView.OnItemSelectedListener
{

    RecyclerView recyclerView;
    DatabaseReference ref;
    MyReviewRecyclerAdapter adapter;
    ArrayList<Review> list;
    Button backBtn;
    int userId = 0;
    private FirebaseAuth mAuth;
    private MyReviewListViewModel mViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_my_reviews, container, false);
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
        GlobalClass globalClass = (GlobalClass) getActivity().getApplicationContext();
        recyclerView = v.findViewById(R.id.my_reviews_list);
        mViewModel = new ViewModelProvider(this).get(MyReviewListViewModel.class);
        mViewModel.init(globalClass.getUserId());
        backBtn = v.findViewById(R.id.review_back_btn);
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
        Log.i("Checking repo", "" + mViewModel.getMyReviews().getValue().toString());
        adapter = new MyReviewRecyclerAdapter(this.getContext(), mViewModel.getMyReviews().getValue(), this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setOnButtonClickListener(new MyReviewRecyclerAdapter.OnButtonClickListener()
        {
            @Override
            public void onButtonClick(int position)
            {
                mViewModel.removeReview(position);
            }
        });


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