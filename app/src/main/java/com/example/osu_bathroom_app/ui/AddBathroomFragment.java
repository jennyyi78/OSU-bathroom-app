package com.example.osu_bathroom_app.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.osu_bathroom_app.R;
import com.example.osu_bathroom_app.model.Bathroom;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddBathroomFragment extends Fragment
{

    DatabaseReference ref;
    Button submit;
    EditText name;
    EditText address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v;
        v = inflater.inflate(R.layout.fragment_add_bathroom, container, false);

        submit = v.findViewById(R.id.submitBtn);
        name = v.findViewById(R.id.editTextName);
        address = v.findViewById(R.id.editTextAddress);
        ref = FirebaseDatabase.getInstance().getReference().child("Bathrooms");

        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                submit();
            }
        });

        return v;
    }


    public void submit()
    {
        String bathroomName = name.getText().toString();
        String bathroomAddress = address.getText().toString();
        Log.i("Key", "" + ref.getKey());
        //ref.child("BR1").setValue(new Bathroom(bathroomName,bathroomAddress));
        long l=4;
        float f=2.2f;
        String info="info";
        ref.push().setValue(new Bathroom(l,bathroomName, bathroomAddress,f,info));


        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BathroomListFragment frag = new BathroomListFragment();
        fragmentTransaction.replace(R.id.fragment_container_view, frag);
        fragmentTransaction.commit();
    }
}