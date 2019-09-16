package com.android.segunfrancis.registo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private ProgressBar pb;

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
        pb = view.findViewById(R.id.login_progress_bar);

        Utils.checkPasswordLength(passwordET, passwordETLayout);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String email = emailET.getText().toString().trim();
                String password = passwordET.getText().toString().trim();
                Utils.hideSoftKeyboard(getContext(), view);

                if (Utils.isEmpty(email)|| Utils.isEmpty(password)) {
                    Toast.makeText(view.getContext(), "All Fields are required", Toast.LENGTH_SHORT).show();
                } else if (!Utils.emailType(email)) {
                    emailETLayout.setError("Wrong Email Pattern");
                } else if (Utils.isShort(password)) {
                    Toast.makeText(view.getContext(), "Password is too short", Toast.LENGTH_SHORT).show();
                } else {
                    showProgressBar();
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        FirebaseUserMetadata metadata = FirebaseAuth.getInstance().getCurrentUser().getMetadata();
                                        Toast.makeText(view.getContext(), "Signed in as " + user.getUid(), Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(view.getContext(), DashboardActivity.class);
                                        view.getContext().startActivity(intent);
                                        ((Activity)(view.getContext())).finish();
                                        hideProgressBar();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(view.getContext(), "Authentication failed. " + task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                        hideProgressBar();
                                    }
                                }
                            });
                    Utils.hideSoftKeyboard(getContext(), view);
                }
            }
        });
        return view;
    }

    private void showProgressBar() {
        pb.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        if (pb.getVisibility() == View.VISIBLE) {
            pb.setVisibility(View.GONE);
        }
    }
}
