package com.android.segunfrancis.registo;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

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

        final TextInputLayout emailETLayout = view.findViewById(R.id.emailET_layout);
        TextInputLayout passwordETLayout = view.findViewById(R.id.passwordET_layout);

        Button loginButton = view.findViewById(R.id.sign_in_button);
        final ProgressBar pb = view.findViewById(R.id.login_progress_bar);

        FirebaseUtil.checkPasswordLength(passwordET, passwordETLayout);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailET.getText().toString().trim();
                String password = passwordET.getText().toString().trim();
                FirebaseUtil.hideSoftKeyboard(getContext(), view);

                if (FirebaseUtil.isEmpty(email)|| FirebaseUtil.isEmpty(password)) {
                    Toast.makeText(view.getContext(), "All Fields are required", Toast.LENGTH_SHORT).show();
                } else if (!FirebaseUtil.emailType(email)) {
                    emailETLayout.setError("Wrong Email Pattern");
                } else if (FirebaseUtil.isShort(password)) {
                    Toast.makeText(view.getContext(), "Password is too short", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseUtil.signIn(email, password, getContext());
                    FirebaseUtil.hideSoftKeyboard(getContext(), view);
                    FirebaseUtil.showProgressBar(pb);
                }
            }
        });
        return view;
    }
}
