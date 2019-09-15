package com.android.segunfrancis.registo;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        final TextInputEditText emailET = view.findViewById(R.id.sign_in_email_edittext);
        final TextInputEditText passwordET = view.findViewById(R.id.sign_in_password_edittext);

        TextInputLayout emailETLayout = view.findViewById(R.id.emailET_layout);
        TextInputLayout passwordETLayout = view.findViewById(R.id.passwordET_layout);

        Button loginButton = view.findViewById(R.id.sign_in_button);

        FirebaseUtil.checkPasswordLength(passwordET, passwordETLayout);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUtil.signIn(emailET.getText().toString(), passwordET.getText().toString(), getContext());
            }
        });
        return view;
    }
}
