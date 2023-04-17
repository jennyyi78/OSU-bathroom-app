package com.example.osu_bathroom_app;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PowerMockIgnore("jdk.internal.reflect.*")
@PrepareForTest({ FirebaseDatabase.class})
public class ExampleUnitTest {
    private static final String DB_URL = "https://osu-bathroom-app-default-rtdb.firebaseio.com/";
    @InjectMocks
    BathroomListFragment frag=new BathroomListFragment();
    private DatabaseReference ref;
    private BathroomRepository mRepo;
    private MutableLiveData<List<Bathroom>> mBathrooms;
    FirebaseDatabase database;
    FirebaseApp app;
    private static FirebaseApp testApp;
    Task<AuthResult> successTask;
    @Mock
    FirebaseAuth mAuth;
    //FirebaseDatabase mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);
    @Before
    public void before() {

        ref = Mockito.mock(DatabaseReference.class);
        FirebaseDatabase mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);
        PowerMockito.mockStatic(FirebaseDatabase.class);
        PowerMockito.when(FirebaseDatabase.getInstance("https://osu-bathroom-app-default-rtdb.firebaseio.com/")).thenReturn(mockedFirebaseDatabase);
        Mockito.when(mockedFirebaseDatabase.getReferenceFromUrl("https://osu-bathroom-app-default-rtdb.firebaseio.com/")).thenReturn(ref);


    }




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
    public void testFailedSearch() {

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

        ArrayList<Bathroom> b=frag.testFilter("dog",l);
        assertEquals(b.size(),0);


    }
    @Test
    public void testAddressSearch() {

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

        ArrayList<Bathroom> b=frag.testFilter("240",l);
        assertEquals(b.get(0).getName(),"Apple");


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





}