package com.example.osu_bathroom_app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import androidx.test.core.app.ActivityScenario;
import android.app.Application;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.osu_bathroom_app.model.Bathroom;
import com.example.osu_bathroom_app.repository.BathroomRepository;
import com.example.osu_bathroom_app.ui.BathroomListFragment;
import com.example.osu_bathroom_app.ui.MainActivity;
import com.example.osu_bathroom_app.view_model.BathroomListViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

    DatabaseReference ref;
    private BathroomRepository mRepo;
    private MutableLiveData<List<Bathroom>> mBathrooms;
    FirebaseDatabase database;
    FirebaseApp app;




    @Test
    public void testSearch() {

        BathroomListFragment frag=new BathroomListFragment();
        List<Bathroom> l=new ArrayList<Bathroom>();
        Bathroom b1 = new Bathroom(1, "Apple", "240 Fondiller", 4.1f, "info");
        Bathroom b2 = new Bathroom(2, "Baker", "1 North", 4.2f, "info");
        Bathroom b3 = new Bathroom(3, "Car", "test street", 1.1f, "info");
        Bathroom b4 = new Bathroom(4, "Adam", "test street", 1.1f, "info");
        l.add(b1);
        l.add(b2);
        l.add(b3);
        l.add(b4);

        ArrayList<Bathroom> b=frag.testFilter("ap",l);
        assertEquals(b.get(0).getName(),"Apple");
        assertEquals(b.size(),1);
        ArrayList<Bathroom> list=frag.testFilter("a",l);
        assertEquals(list.get(0).getName(),"Apple");
        assertEquals(list.get(1).getName(),"Adam");
        assertEquals(list.size(),2);

    }

    @Test
    public void testSort() {
        BathroomListFragment frag=new BathroomListFragment();
        List<Bathroom> l=new ArrayList<Bathroom>();
        Bathroom b1 = new Bathroom(1, "Apple", "240 Fondiller", 4.1f, "info");
        Bathroom b2 = new Bathroom(2, "Baker", "1 North", 4.2f, "info");
        Bathroom b3 = new Bathroom(3, "Car", "test street", 1.1f, "info");
        Bathroom b4 = new Bathroom(4, "Adam", "test street", 1.1f, "info");
        l.add(b1);
        l.add(b2);
        l.add(b3);
        l.add(b4);

        List<Bathroom> l1=frag.testSortBathrooms(0,l);
        assertEquals(l1.get(0).getName(),"Adam");
        assertEquals(l1.get(3).getName(),"Car");
        List<Bathroom> l2=frag.testSortBathrooms(1,l);
        assertEquals(l2.get(0).getName(),"Car");
        assertEquals(l2.get(3).getName(),"Adam");


    }
    @Test
    public void test() {
       BathroomListViewModel v=new BathroomListViewModel();
       v.init();



    }
   /* @Test
    public void checkBathroomRetrieval() {
       //FirebaseApp.initializeApp(InstrumentationRegistry.getInstrumentation().getTargetContext());
       // mRepo = BathroomRepository.getInstance();
       // mBathrooms=mRepo.getBathrooms();
        MainActivity main
        FirebaseApp.initializeApp();
       FirebaseDatabase f= Mockito.mock(FirebaseDatabase.class);
       Mockito.when(f.getInstance().getReference().child("Bathrooms")).thenReturn(ref);
       // ref= f.getInstance().getReference().child("Bathrooms");
       /* long modelLength=mBathrooms.getValue().size();
        final long[] repLength = {0};
        ref.addListenerForSingleValueEvent(new ValueEventListener() { //ref will be your desired path where you want to count children
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {   // Check for data snapshot has some value
                    repLength[0] =dataSnapshot.getChildrenCount();  // check for counts of data snapshot children
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        assertEquals(modelLength,repLength[0]);

    }*/
}