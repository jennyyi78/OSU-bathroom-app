package com.example.osu_bathroom_app.ui;


import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.osu_bathroom_app.R;
import com.example.osu_bathroom_app.main.GlobalClass;
import com.example.osu_bathroom_app.model.Favorite;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class BathroomInfoFragment extends DialogFragment implements View.OnTouchListener, GestureDetector.OnGestureListener
{

    final int min_Distance = 400;
    View view;
    Button review_button;
    Button info_button;
    long id;
    Button exit_button;
    Button add_favorite_button;
    private Button linkToHomePageBtn;
    DatabaseReference favoriteRef;
    GlobalClass globalClass;
    private FirebaseAuth mAuth;
    private GestureDetector detector;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bathroom_info, container, false);
        view.setOnTouchListener(this);
        detector = new GestureDetector(this.getContext(), this);
        Bundle bundle = getArguments();
        globalClass = (GlobalClass) getActivity().getApplicationContext();
        mAuth = FirebaseAuth.getInstance();
        review_button = view.findViewById(R.id.review_button);
        info_button = view.findViewById(R.id.view_button);
        id = bundle.getLong("Id");
        add_favorite_button = view.findViewById(R.id.add_favorite_button);


        favoriteRef = FirebaseDatabase.getInstance().getReference().child("Favorites");

        TextView name = view.findViewById(R.id.name);
        TextView address = view.findViewById(R.id.address);
        name.setText(String.valueOf(bundle.getString("Name")));
        address.setText(String.valueOf(bundle.getString("Address")));

        review_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                addReview();
            }
        });

        info_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                viewReviews(id);
            }
        });

        add_favorite_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                addFavorite();
            }
        });


        Query q = favoriteRef.orderByChild("bathroomId").equalTo(id);

        q.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    long userId = snapshot.child("userId").getValue(Long.class);
                    if (userId == globalClass.getUserId()) {
                        add_favorite_button.setText("Remove from Favorites");

                        add_favorite_button.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                removeFavorite();
                            }
                        });
                    } else {
                        add_favorite_button.setText("Add to Favorites");
                        add_favorite_button.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                addFavorite();
                            }
                        });
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                // Handle any errors that occur
            }
        });


        return view;
    }

    private void addFavorite()
    {

        Favorite f = new Favorite(this.id, globalClass.getUserId());
        favoriteRef.push().setValue(f);
        Toast.makeText(getActivity(), "Added Favorite", Toast.LENGTH_LONG).show();
    }

    private void removeFavorite()
    {
        Query q = favoriteRef.orderByChild("userId").equalTo(globalClass.getUserId());

        q.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    long bathroomId = snapshot.child("bathroomId").getValue(Long.class);
                    if (bathroomId == id) {
                        snapshot.getRef().removeValue();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Log.d("TAG", "onCancelled: " + databaseError);
            }
        });
        Toast.makeText(getActivity(), "Removed Favorite", Toast.LENGTH_LONG).show();
        add_favorite_button.setText("Add to Favorites");
        add_favorite_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                addFavorite();
            }
        });
    }


    private void addReview()
    {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(this);
        fragmentTransaction.commitNow();
        AddReviewFragment frag = new AddReviewFragment();
        Bundle args = new Bundle();
        args.putLong("Id", id);
        frag.setArguments(args);
        fragmentTransaction.replace(R.id.fragment_container_view, frag);
        fragmentTransaction.commit();
    }

    private void viewReviews(long id)
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(this);
        fragmentTransaction.commitNow();
        ReviewListFragment frag = new ReviewListFragment();


        Bundle args = new Bundle();
        Log.i("select", "BRINFO: " + id);
        args.putLong("Id", id);
        frag.setArguments(args);
        fragmentTransaction.replace(R.id.fragment_container_view, frag);
        fragmentTransaction.commit();
    }

    private void closeInfoFragment()
    {
        if (globalClass.getCurrentPage() == 0) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment frag = fragmentManager.findFragmentById(R.id.frame_layout);
            fragmentTransaction.remove(this);
            fragmentTransaction.commit();
            //fragmentManager.popBackStackImmediate(R.id.frame_layout,0);
            //BathroomListFragment frag=BathroomListFragment.newInstance();
            //frag.view.findViewById(R.id.addbtn).setEnabled(true);
            View v = view.getRootView();
            v.findViewById(R.id.fragment_container_view).findViewById(R.id.addbtn).setEnabled(true);
            v.findViewById(R.id.fragment_container_view).findViewById(R.id.back_btn).setEnabled(true);
            v.findViewById(R.id.fragment_container_view).findViewById(R.id.sort_button).setEnabled(true);
            v.findViewById(R.id.fragment_container_view).findViewById(R.id.spinner).setEnabled(true);
        } else {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment frag = fragmentManager.findFragmentById(R.id.frame_layout_1);
            fragmentTransaction.remove(this);
            fragmentTransaction.commit();

            View v = view.getRootView();
            v.findViewById(R.id.fragment_container_view).findViewById(R.id.linkToHomePageButton).setEnabled(true);

        }
        //Log.i("views", "" + v);

    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        int action = motionEvent.getAction();
        float x1 = 0f;
        float y1 = 0f;
        float x2 = 0f;
        float y2 = 0f;
        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                x1 = motionEvent.getX();
                y1 = motionEvent.getY();
                Log.i("swipe", "down");
                return true;
            case (MotionEvent.ACTION_MOVE):
                Log.i("swipe", "move");
                Log.i("swipe", "x,y:" + motionEvent.getX() + "," + motionEvent.getY());

                return true;
            case (MotionEvent.ACTION_UP):
                Log.i("swipe", "up");
                x2 = motionEvent.getX();
                y2 = motionEvent.getY();

                float x = x2 - x1;
                float y = y2 - y1;

                if (Math.abs(y) >= min_Distance) {
                    if (y2 > y1) {
                        closeInfoFragment();
                    }
                }
                return true;
            case (MotionEvent.ACTION_CANCEL):
                Log.i("swipe", "cancel");
                return true;
            case (MotionEvent.ACTION_OUTSIDE):
                Log.i("swipe", "outside");
                return true;
            default:
                return view.onTouchEvent(motionEvent);
        }

        //detector.onTouchEvent(motionEvent);
        //return false;
    }

    @Override
    public boolean onDown(@NonNull MotionEvent motionEvent)
    {
        Log.i("swipes", "onDown");
        return false;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent motionEvent)
    {
        Log.i("swipes", "onShow");
    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent motionEvent)
    {
        Log.i("swipes", "onSingle");
        return false;
    }

    @Override
    public boolean onScroll(@NonNull MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1)
    {
        Log.i("swipes", "onScroll");
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent motionEvent)
    {
        Log.i("swipes", "onLong");

    }

    @Override
    public boolean onFling(@NonNull MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1)
    {
        Log.i("swipes", "onFling");
        return false;
    }
}