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
import androidx.fragment.app.FragmentTransaction;

import com.example.osu_bathroom_app.R;
import com.example.osu_bathroom_app.main.HomePageFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment
{
    private final String TAG = "LoginFragment";
    View view;
    private EditText usernameTextView, passwordTextView;
    private Button loginBtn, linkToRegisterBtn;

    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        view = inflater.inflate(R.layout.fragment_login, container, false);
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        usernameTextView = view.findViewById(R.id.username);
        passwordTextView = view.findViewById(R.id.password);
        loginBtn = view.findViewById(R.id.loginButton);
        linkToRegisterBtn = view.findViewById(R.id.linkToRegisterButton);

        Log.i("Activities", "OnCreateView");

        // Set on Click Listener on login button
        loginBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                loginUserAccount();
            }
        });

        // set listener on link to register button
        linkToRegisterBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("Button", "Test");
                replaceFragment(new RegisterFragment());
            }
        });
        Log.i(TAG, "OnCreateView");
        return view;
    }

    private void loginUserAccount()
    {

        // Take the value of two edit texts in Strings
        String username, password;
        username = usernameTextView.getText().toString();
        password = passwordTextView.getText().toString();

        // validations for input email and password
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

        // login existing user
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null && user.isEmailVerified()) {
                                        Toast.makeText(getActivity().getApplicationContext(),
                                                        "Login successful!!",
                                                        Toast.LENGTH_LONG)
                                                .show();

                                        // if sign-in is successful
                                        // intent to home activity
                                        replaceFragment(new HomePageFragment());
                                    } else {
                                        Toast.makeText(getActivity(), "Please verify your email address.", Toast.LENGTH_SHORT).show();
                                    }

                                } else {

                                    // sign-in failed
                                    Toast.makeText(getActivity().getApplicationContext(),
                                                    "Login failed!!",
                                                    Toast.LENGTH_LONG)
                                            .show();

                                }
                            }
                        });
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}