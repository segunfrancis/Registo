package com.android.segunfrancis.registo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

import androidx.annotation.NonNull;

public class FirebaseUtil {

    private static final String TAG = "FirebaseUtil";

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseAuth.AuthStateListener sAuthStateListener;

    public static void signIn(String email, String password, final Context context) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            FirebaseUserMetadata metadata = FirebaseAuth.getInstance().getCurrentUser().getMetadata();
                            Toast.makeText(context.getApplicationContext(), "Signed in as " + user.getUid(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context.getApplicationContext(), DashboardActivity.class);
                            context.startActivity(intent);
                            ((Activity)(context)).finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(context.getApplicationContext(), "Authentication failed. " + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void SignUp(String email, String password, final Context context) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(context.getApplicationContext(), "Authenticated as " + user.getEmail(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context.getApplicationContext(), DashboardActivity.class);
                            context.startActivity(intent);
                            ((Activity)(context)).finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(context.getApplicationContext(), "Authentication failed. " + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void checkPasswordLength(final TextInputEditText editText, final TextInputLayout inputLayout) {
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
                    inputLayout.setHelperTextColor(ColorStateList.valueOf(Color.GREEN));
                    inputLayout.setCounterTextColor(ColorStateList.valueOf(Color.GREEN));
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

    static void showProgressBar(ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
    }

    static void hideProgressBar(ProgressBar progressBar) {
        progressBar.setVisibility(View.INVISIBLE);
    }
}
