package com.android.segunfrancis.registo;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

    public static void logout() {

    }

    public static boolean checkAuthState() {
        return false;
    }
}
