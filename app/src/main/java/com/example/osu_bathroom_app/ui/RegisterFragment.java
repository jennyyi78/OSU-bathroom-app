package com.example.osu_bathroom_app.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.osu_bathroom_app.R;
import com.example.osu_bathroom_app.ui.LoginFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment
{

    View view;
    private EditText usernameTextView, passwordTextView;
    private Button registerBtn;
    private Button backBtn;
    private FirebaseAuth mAuth;
    private final String TAG = "MainActivity";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);

        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        usernameTextView = view.findViewById(R.id.username);
        passwordTextView = view.findViewById(R.id.password);
        registerBtn = view.findViewById(R.id.registerButton);
        backBtn = view.findViewById((R.id.backbtn));
        registerBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                registerNewUser();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                replaceFragment(new LoginFragment());
            }
        });


        return view;
    }

    private void registerNewUser()
    {

        // Take the value of two edit texts in Strings
        String username, password;
        username = usernameTextView.getText().toString();
        password = passwordTextView.getText().toString();
        Log.i("register", "" + password);
        // Validations for input email and password
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(getActivity().getApplicationContext(),
                            "Please enter email!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity().getApplicationContext(),
                            "Please enter password!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // create new user or register new user
        mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity().getApplicationContext(),
                                    "Registration successful!",
                                    Toast.LENGTH_LONG)
                            .show();


                    replaceFragment(new LoginFragment());
                } else {

                    // Registration failed
                    Toast.makeText(
                                    getActivity().getApplicationContext(),
                                    "Registration failed!!"
                                            + " Please try again later",
                                    Toast.LENGTH_LONG)
                            .show();

                }
            }
        });
    }

    private void replaceFragment(Fragment fragment)
    {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view, fragment);
        fragmentTransaction.commit();

    }
}