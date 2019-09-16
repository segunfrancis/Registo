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
import android.widget.ProgressBar;
import android.widget.Toast;

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
        final TextInputEditText usernameET = view.findViewById(R.id.username_edit_text);
        final TextInputEditText passwordET = view.findViewById(R.id.password_edit_text);
        final TextInputEditText confirmPasswordET = view.findViewById(R.id.confirm_password_editText);
        final TextInputEditText phoneNumberET = view.findViewById(R.id.phone_number_edit_text);

        final TextInputLayout emailETLayout = view.findViewById(R.id.emailET);
        final TextInputLayout passwordETLayout = view.findViewById(R.id.passwordET);
        final TextInputLayout confirmPasswordETLayout = view.findViewById(R.id.confirm_passwordET);

        final ProgressBar pb = view.findViewById(R.id.sign_up_progress_bar);

        FirebaseUtil.checkPasswordLength(passwordET, passwordETLayout);

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
                String email = emailET.getText().toString().trim();
                String username = usernameET.getText().toString().trim();
                String phoneNumber = phoneNumberET.getText().toString().trim();
                String password = passwordET.getText().toString().trim();
                String confirmPassword = confirmPasswordET.getText().toString().trim();

                if (FirebaseUtil.isEmpty(email) || FirebaseUtil.isEmpty(username) || FirebaseUtil.isEmpty(password)
                        || FirebaseUtil.isEmpty(confirmPassword) || FirebaseUtil.isEmpty(phoneNumber)) {
                    Toast.makeText(view.getContext(), "All Fields are required", Toast.LENGTH_SHORT).show();
                } else if (!FirebaseUtil.emailType(email)) {
                    Toast.makeText(view.getContext(), "Wrong Email Pattern", Toast.LENGTH_SHORT).show();
                    emailET.requestFocus();
                } else if (!FirebaseUtil.phoneNumberIsOK(phoneNumber)) {
                    Toast.makeText(view.getContext(), "Check Phone Number", Toast.LENGTH_SHORT).show();
                    phoneNumberET.requestFocus();
                } else if (!TextUtils.equals(password, confirmPassword)) {
                    Toast.makeText(view.getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else if (FirebaseUtil.isShort(password) || FirebaseUtil.isShort(confirmPassword)) {
                    Toast.makeText(view.getContext(), "Password is too short", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseUtil.SignUp(email, password, getContext());
                    FirebaseUtil.hideSoftKeyboard(getContext(), view);
                    FirebaseUtil.showProgressBar(pb);
                }
            }
        });
        return view;
    }
}
