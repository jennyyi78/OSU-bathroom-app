package com.example.osu_bathroom_app.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.osu_bathroom_app.model.Bathroom;
import com.example.osu_bathroom_app.model.Review;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BathroomRepository
{

    private static BathroomRepository instance;
    DatabaseReference ref;
    private final ArrayList<Bathroom> dataSet = new ArrayList<>();
    private final ArrayList<Review> dataSetReviews = new ArrayList<>();
    public static BathroomRepository getInstance()
    {
        if (instance == null) {
            instance = new BathroomRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Bathroom>> getBathrooms()
    {
        setBathrooms();

        MutableLiveData<List<Bathroom>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        Log.i("datatest", "" + data.getValue().size());

        return data;
    }

    public MutableLiveData<List<Review>> getReviews()
    {
        setReviews();
        MutableLiveData<List<Review>> data = new MutableLiveData<>();
        data.setValue(dataSetReviews);
        return data;
    }

    private void setBathrooms() //where data is retrieved from firebase
    {
        Log.i("Keys", "Help");

        ExecutorService service = Executors.newSingleThreadExecutor();
        ArrayList<String> list = new ArrayList<String>();
        ref = FirebaseDatabase.getInstance().getReference().child("Bathrooms");

        //dataSet.add()
        ref.addValueEventListener(new ValueEventListener()
        {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                Log.i("Keys", "Help");

               dataSet.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                 //   service.execute(new Runnable()
                   // {
                       // @Override
                       // public void run()
                       // {
                            Double d=(double)ds.child("avgRating").getValue();
                            Bathroom b = new Bathroom((long)ds.child("id").getValue(),(String) ds.child("name").getValue(), (String) ds.child("address").getValue(),d.floatValue(),(String) ds.child("information").getValue());
                            dataSet.add(b);
                       // }
                   // });
                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });


    }

    public void removeBathrooms(long id)
    {
        ref = FirebaseDatabase.getInstance().getReference().child("Bathrooms");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    long bId=(long)ds.child("id").getValue();
                    if(id==bId)
                    {
                       // Log.i("remove","test");
                        Log.i("remove",""+ds.getRef().removeValue());

                        break;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void setReviews()
    {
        ExecutorService service = Executors.newSingleThreadExecutor();
        ref = FirebaseDatabase.getInstance().getReference().child("Reviews");
        ref.addValueEventListener(new ValueEventListener()
        {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                Log.i("Keys", "Help");
                dataSetReviews.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    service.execute(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            Double d=(double)ds.child("rating").getValue();

                            //Log.i("reviews",""+(double)ds.child("rating").getValue());
                           Review r = new Review((long)ds.child("id").getValue(),d.floatValue(),(String)ds.child("review").getValue());
                            dataSetReviews.add(r);
                            Log.i("Reviews", ""+r.getRating());
                        }
                    });
                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });


    }

}
