package com.example.osu_bathroom_app.main;

import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.osu_bathroom_app.R;
import com.example.osu_bathroom_app.account.LoginFragment;

public class MainActivity extends AppCompatActivity
{
    private final String TAG = "Activities";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("message");

        //myRef.setValue("Hello, World!");
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, LoginFragment.class, null)
                    .addToBackStack(null)
                    .commit();
        }
        Log.i(TAG, "OnCreate");
    }

    protected void onStart()
    {
        super.onStart();
        Log.i(TAG, "OnStart");
    }

    protected void onResume()
    {
        super.onResume();
        Log.i(TAG, "OnResume");
    }

    protected void onPause()
    {
        super.onPause();
        Log.i(TAG, "OnPause");
    }

    protected void onStop()
    {
        super.onStop();
        Log.i(TAG, "OnStop");
    }

    protected void onRestart()
    {
        super.onRestart();
        Log.i(TAG, "OnRestart");
    }

    protected void onDestroy()
    {
        super.onDestroy();
        Log.i(TAG, "OnDestroy");
    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }

}