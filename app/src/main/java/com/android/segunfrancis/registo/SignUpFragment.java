package com.android.segunfrancis.registo;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpFragment extends Fragment {

    public SignUpFragment() {
        // Required empty public constructor
    }

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mReference;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private ProgressBar pb;
    private static final String TAG = "SignUpFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        Button signUpButton = view.findViewById(R.id.sign_up_button);
        final TextInputEditText emailET = view.findViewById(R.id.email_editText);
        final TextInputEditText usernameET = view.findViewById(R.id.username_edit_text);
        final TextInputEditText passwordET = view.findViewById(R.id.password_edit_text);
        final TextInputEditText confirmPasswordET = view.findViewById(R.id.confirm_password_editText);
        final TextInputEditText phoneNumberET = view.findViewById(R.id.phone_number_edit_text);

        final TextInputLayout passwordETLayout = view.findViewById(R.id.passwordET);
        final TextInputLayout confirmPasswordETLayout = view.findViewById(R.id.confirm_passwordET);

        pb = view.findViewById(R.id.sign_up_progress_bar);

        Utils.checkPasswordLength(passwordET, passwordETLayout, view.getContext());

        confirmPasswordET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int count) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (confirmPasswordET.getText().length() < 6) {
                    confirmPasswordETLayout.setError("Too short");
                    confirmPasswordETLayout.setCounterTextColor(ColorStateList.valueOf(Color.RED));
                } else if (confirmPasswordET.getText().length() >= 6 && !TextUtils.equals(passwordET.getText(), confirmPasswordET.getText())) {
                    confirmPasswordETLayout.setHelperText("Password does not match the one above");
                    confirmPasswordETLayout.setHelperTextColor(ColorStateList.valueOf(Color.RED));
                    confirmPasswordETLayout.setCounterTextColor(ColorStateList.valueOf(view.getContext().getResources().getColor(R.color.colorGreen)));
                } else {
                    confirmPasswordETLayout.setHelperText(" ");
                    confirmPasswordETLayout.setHelperTextColor(ColorStateList.valueOf(view.getContext().getResources().getColor(R.color.colorGreen)));
                    confirmPasswordETLayout.setCounterTextColor(ColorStateList.valueOf(view.getContext().getResources().getColor(R.color.colorGreen)));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Creating a new user
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String email = emailET.getText().toString().trim();
                final String username = usernameET.getText().toString().trim();
                final String phoneNumber = phoneNumberET.getText().toString().trim();
                String password = passwordET.getText().toString().trim();
                String confirmPassword = confirmPasswordET.getText().toString().trim();

                if (Utils.isEmpty(email) || Utils.isEmpty(username) || Utils.isEmpty(password)
                        || Utils.isEmpty(confirmPassword) || Utils.isEmpty(phoneNumber)) {
                    Toast.makeText(view.getContext(), "All Fields are required", Toast.LENGTH_SHORT).show();
                } else if (!Utils.emailType(email)) {
                    Toast.makeText(view.getContext(), "Wrong Email Pattern", Toast.LENGTH_SHORT).show();
                    emailET.requestFocus();
                } else if (!Utils.phoneNumberIsOK(phoneNumber)) {
                    Toast.makeText(view.getContext(), "Check Phone Number", Toast.LENGTH_SHORT).show();
                    phoneNumberET.requestFocus();
                } else if (!TextUtils.equals(password, confirmPassword)) {
                    Toast.makeText(view.getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else if (Utils.isShort(password) || Utils.isShort(confirmPassword)) {
                    Toast.makeText(view.getContext(), "Password is too short", Toast.LENGTH_SHORT).show();
                } else {
                    showProgressBar();
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(view.getContext(), "Authenticated as " + user.getEmail(), Toast.LENGTH_SHORT).show();
                                        mFirebaseDatabase = FirebaseDatabase.getInstance();
                                        mReference = mFirebaseDatabase.getReference("user-data").child(mAuth.getCurrentUser().getUid());
                                        UserModel model = new UserModel();

                                        // Writing into firebase database
                                        model.setEmail(email);
                                        model.setUsername(username);
                                        model.setTelephone(phoneNumber);
                                        mReference.setValue(model);
                                        Intent intent = new Intent(view.getContext(), DashboardActivity.class);
                                        view.getContext().startActivity(intent);
                                        ((Activity) (view.getContext())).finish();
                                        hideProgressBar();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(view.getContext(), "Authentication failed.\n" + task.getException(),
                                                Toast.LENGTH_LONG).show();
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
