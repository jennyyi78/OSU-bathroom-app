package com.example.osu_bathroom_app.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.osu_bathroom_app.model.Bathroom;
import com.example.osu_bathroom_app.model.Favorite;
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

public class BathroomRepository {

    private static BathroomRepository instance;
    DatabaseReference ref;
    private final ArrayList<Bathroom> dataSet = new ArrayList<>();
    private final ArrayList<Review> dataSetReviews = new ArrayList<>();
    private final ArrayList<Review> dataSetMyReviews = new ArrayList<>();

    private final ArrayList<Favorite> dataSetMyFavorites = new ArrayList<>();

    private final ArrayList<Bathroom> dataSetMyBathrooms = new ArrayList<>();
    public static BathroomRepository getInstance() {
        if (instance == null) {
            instance = new BathroomRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Bathroom>> getBathrooms() {
        setBathrooms();

        MutableLiveData<List<Bathroom>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        Log.i("datatest", "" + data.getValue().size());

        return data;
    }

    public MutableLiveData<List<Review>> getReviews(long id) {
        setReviews(id);
        MutableLiveData<List<Review>> data = new MutableLiveData<>();
        data.setValue(dataSetReviews);
        return data;
    }

    public MutableLiveData<List<Review>> getMyReviews(long id) {
        setMyReviews(id);
        MutableLiveData<List<Review>> data = new MutableLiveData<>();
        data.setValue(dataSetMyReviews);
        Log.i("iop",data.toString());
        return data;
    }

    public MutableLiveData<List<Bathroom>> getMyFavorites(long id) {
        setMyFavorites(id);
        MutableLiveData<List<Bathroom>> data = new MutableLiveData<>();
        data.setValue(dataSetMyBathrooms);

        return data;
    }

    private void setBathrooms() //where data is retrieved from firebase
    {
        Log.i("Keys", "Help");

        ExecutorService service = Executors.newSingleThreadExecutor();
        ArrayList<String> list = new ArrayList<String>();
        ref = FirebaseDatabase.getInstance().getReference().child("Bathrooms");

        //dataSet.add()
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("Keys", "Help");

                dataSet.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    //   service.execute(new Runnable()
                    // {
                    // @Override
                    // public void run()
                    // {
                    Double d;

                    Object o = ds.child("avgRating").getValue();
                    if (o instanceof Double)
                        d = (Double) ds.child("avgRating").getValue();
                    else {
                        long l = (long) ds.child("avgRating").getValue();
                        d = l * 1.0;
                    }
                    //Double d = (double) ds.child("avgRating").getValue();
                    Bathroom b = new Bathroom((long) ds.child("id").getValue(), (String) ds.child("name").getValue(), (String) ds.child("address").getValue(), d.floatValue(), (String) ds.child("information").getValue());
                    dataSet.add(b);
                    // }
                    // });
                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void setMyBathrooms() //where data is retrieved from firebase
    {
        Log.i("Keys", "Help");

        ExecutorService service = Executors.newSingleThreadExecutor();
        ArrayList<String> list = new ArrayList<String>();
        ref = FirebaseDatabase.getInstance().getReference().child("Bathrooms");

        //dataSet.add()
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("Keys", "Help");

                dataSetMyBathrooms.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    //   service.execute(new Runnable()
                    // {
                    // @Override
                    // public void run()
                    // {
                    Double d;

                    Object o = ds.child("avgRating").getValue();
                    if (o instanceof Double)
                        d = (Double) ds.child("avgRating").getValue();
                    else {
                        long l = (long) ds.child("avgRating").getValue();
                        d = l * 1.0;
                    }
                    //Double d = (double) ds.child("avgRating").getValue();
                    Bathroom b = new Bathroom((long) ds.child("id").getValue(), (String) ds.child("name").getValue(), (String) ds.child("address").getValue(), d.floatValue(), (String) ds.child("information").getValue());

                    for (Favorite f : dataSetMyFavorites) {
                        if (f.getBathroomId() == b.getId()) {
                            Log.d("bathfavs", f.toString());
                            dataSetMyBathrooms.add(b);
                        }
                    }
                    // }
                    // });
                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void removeBathrooms(long id) {
        ref = FirebaseDatabase.getInstance().getReference().child("Bathrooms");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    long bId = (long) ds.child("id").getValue();
                    if (id == bId) {
                        // Log.i("remove","test");
                        Log.i("remove", "" + ds.getRef().removeValue());

                        break;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void removeReviews(long id) {
        ref = FirebaseDatabase.getInstance().getReference().child("Reviews");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    long bId = (long) ds.child("id").getValue();
                    if (id == bId) {
                        // Log.i("remove","test");
                        Log.i("remove", "" + ds.getRef().removeValue());

                        break;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void setReviews(long id) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        ref = FirebaseDatabase.getInstance().getReference().child("Reviews");
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("Keys", "Help");
                dataSetReviews.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    service.execute(new Runnable() {
                        @Override
                        public void run() {

                            Double d;

                            Object o = ds.child("rating").getValue();
                            if (o instanceof Double)
                                d = (Double) ds.child("rating").getValue();
                            else {
                                long l = (long) ds.child("rating").getValue();
                                d = l * 1.0;
                            }

                            //Object o=ds.child("rating").getValue();

                            Log.i("teviews", "" + d);
                            //Log.i("reviews",""+(double)ds.child("rating").getValue());
                            Review r = new Review((long) ds.child("id").getValue(),(long) ds.child("bathroomId").getValue(), d.floatValue(), (String) ds.child("review").getValue(),(long)ds.child("userId").getValue());
                            Log.i("select", "" + id);
                            Log.i("select", "br" + r.getBathroomId());
                            if(r.getBathroomId()==id) {
                                dataSetReviews.add(r);
                            }

                        }
                    });
                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void setMyReviews(long id) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        ref = FirebaseDatabase.getInstance().getReference().child("Reviews");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("Keys", "Help");
                dataSetMyReviews.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    service.execute(new Runnable() {
                        @Override
                        public void run() {

                            Double d;

                            Object o = ds.child("rating").getValue();
                            if (o instanceof Double)
                                d = (Double) ds.child("rating").getValue();
                            else {
                                long l = (long) ds.child("rating").getValue();
                                d = l * 1.0;
                            }

                            //Object o=ds.child("rating").getValue();

                            Log.i("teviews", "" + d);
                            //Log.i("reviews",""+(double)ds.child("rating").getValue());
                            Review r = new Review((long) ds.child("id").getValue(),(long) ds.child("bathroomId").getValue(), d.floatValue(), (String) ds.child("review").getValue(), (long)ds.child("userId").getValue());
                            Log.i("iop",""+r.getId());
                            Log.i("iop","Hello me"+id);
                            if(r.getUserId()==id)
                            {
                                Log.i("iop","Hello"+r.getId());
                                dataSetMyReviews.add(r);
                            }


                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setMyFavorites(long id) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        ref = FirebaseDatabase.getInstance().getReference().child("Favorites");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("Keys", "Help");
                dataSetMyFavorites.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    service.execute(new Runnable() {
                        @Override
                        public void run() {

                            Favorite f = new Favorite((long)ds.child("bathroomId").getValue(), (long)ds.child("userId").getValue());
                            Log.d("favs", f.toString());
                            if(f.getUserId()==id)
                            {
                                Log.d("favs2", f.toString());
                                dataSetMyFavorites.add(f);
                            }


                        }
                    });
                }
                setMyBathrooms();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}
