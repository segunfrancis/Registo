package com.android.segunfrancis.registo;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpFragment extends Fragment {

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        Button button = view.findViewById(R.id.sign_up_button);
        final TextInputEditText emailET = view.findViewById(R.id.email_editText);
        final TextInputEditText passwordET = view.findViewById(R.id.password_edit_text);
        final TextInputEditText confirmPasswordET = view.findViewById(R.id.confirm_password_editText);

        final TextInputLayout passwordETLayout = view.findViewById(R.id.passwordET);
        final TextInputLayout confirmPasswordETLayout = view.findViewById(R.id.confirm_passwordET);

        passwordET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int count) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (passwordET.getText().length() < 6) {
                    passwordETLayout.setError("Too short");
                    passwordETLayout.setCounterTextColor(ColorStateList.valueOf(Color.RED));
                } else {
                    passwordETLayout.setHelperText(" ");
                    passwordETLayout.setHelperTextColor(ColorStateList.valueOf(Color.GREEN));
                    passwordETLayout.setCounterTextColor(ColorStateList.valueOf(Color.GREEN));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        confirmPasswordET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int count) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (confirmPasswordET.getText().length() < 6) {
                    confirmPasswordETLayout.setError("Too short");
                    confirmPasswordETLayout.setCounterTextColor(ColorStateList.valueOf(Color.RED));
                } else if (confirmPasswordET.getText().length() >= 6 &&  !TextUtils.equals(passwordET.getText(), confirmPasswordET.getText())) {
                    confirmPasswordETLayout.setHelperText("Password does not match the one above");
                    confirmPasswordETLayout.setHelperTextColor(ColorStateList.valueOf(Color.RED));
                    confirmPasswordETLayout.setCounterTextColor(ColorStateList.valueOf(Color.GREEN));
                } else {
                    confirmPasswordETLayout.setHelperText(" ");
                    confirmPasswordETLayout.setHelperTextColor(ColorStateList.valueOf(Color.GREEN));
                    confirmPasswordETLayout.setCounterTextColor(ColorStateList.valueOf(Color.GREEN));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();

                FirebaseUtil.SignUp(email, password, getContext());
            }
        });
        return view;
    }
}
