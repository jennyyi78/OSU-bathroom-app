package com.example.osu_bathroom_app;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

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
        Log.i("data", "" + dataSet.toString());

        return data;
    }

    private void setBathrooms() //where data is retrieved from firebase
    {
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
                    service.execute(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            Bathroom b = new Bathroom((String) ds.child("name").getValue(), (String) ds.child("address").getValue());
                            dataSet.add(b);
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
