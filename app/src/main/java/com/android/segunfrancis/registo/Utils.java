package com.android.segunfrancis.registo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Utils {

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static void checkPasswordLength(final TextInputEditText editText, final TextInputLayout inputLayout, final Context context) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editText.getText().length() < 6) {
                    inputLayout.setError("Too short");
                    inputLayout.setCounterTextColor(ColorStateList.valueOf(Color.RED));
                } else {
                    inputLayout.setHelperText(" ");
                    inputLayout.setHelperTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGreen)));
                    inputLayout.setCounterTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGreen)));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public static void logout(Context context) {
        mAuth.signOut();
        context.startActivity(new Intent(context.getApplicationContext(), MainActivity.class));
        ((Activity)(context)).finish();
    }

    static void hideSoftKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    static boolean isEmpty(String s) {
        return TextUtils.isEmpty(s);
    }

    static boolean emailType(String emailAddress) {
        return emailAddress.matches(Patterns.EMAIL_ADDRESS.toString());
    }

    static boolean phoneNumberIsOK(String phoneNumber) {
        return phoneNumber.length() == 11;
    }

    static boolean isShort(String password) {
        return password.length() < 6;
    }
}
