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

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.osu_bathroom_app.R;
import com.example.osu_bathroom_app.ui.AddReviewFragment;
import com.example.osu_bathroom_app.ui.ReviewListFragment;


public class BathroomInfoFragment extends DialogFragment implements View.OnTouchListener, GestureDetector.OnGestureListener
{

    View view;
    Button review_button;
    Button info_button;

    Button exit_button;
    final int min_Distance = 400;

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
        review_button = view.findViewById(R.id.review_button);
        info_button = view.findViewById(R.id.view_button);

        TextView name = (TextView) view.findViewById(R.id.name);
        TextView address = (TextView) view.findViewById(R.id.address);
        name.setText(String.valueOf(bundle.getString("Name")));
        address.setText(String.valueOf(bundle.getString("Address")));

        review_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReview();
            }
        });

        info_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewReviews();
            }
        });


        return view;
    }

    private void addReview()
    {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(this);
        fragmentTransaction.commitNow();
        AddReviewFragment frag = new AddReviewFragment();
        fragmentTransaction.replace(R.id.fragment_container_view, frag);
        fragmentTransaction.commit();
    }

    private void viewReviews()
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(this);
        fragmentTransaction.commitNow();
        ReviewListFragment frag = new ReviewListFragment();
        fragmentTransaction.replace(R.id.fragment_container_view, frag);
        fragmentTransaction.commit();
    }

    private void closeInfoFragment()
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment frag=fragmentManager.findFragmentById(R.id.frame_layout);
        fragmentTransaction.remove(this);
        fragmentTransaction.commit();
        //fragmentManager.popBackStackImmediate(R.id.frame_layout,0);
       //BathroomListFragment frag=BathroomListFragment.newInstance();
        //frag.view.findViewById(R.id.addbtn).setEnabled(true);
        View v = view.getRootView();
       v.findViewById(R.id.fragment_container_view).findViewById(R.id.addbtn).setEnabled(true);
        v.findViewById(R.id.fragment_container_view).findViewById(R.id.sort_button).setEnabled(true);
        v.findViewById(R.id.fragment_container_view).findViewById(R.id.spinner).setEnabled(true);
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