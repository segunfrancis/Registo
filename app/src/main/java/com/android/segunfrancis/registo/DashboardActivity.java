package com.android.segunfrancis.registo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference mReference = FirebaseDatabase.getInstance().getReference("user-data").child(mAuth.getCurrentUser().getUid());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        final TextView usernameTV = findViewById(R.id.username_text_view);
        final TextView emailTV = findViewById(R.id.email_text_view);
        final TextView telephoneTV = findViewById(R.id.telephone_text_view);

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserModel model = dataSnapshot.getValue(UserModel.class);
                    String username = model.getUsername();
                    String email = model.getEmail();
                    String phoneNumber = model.getTelephone();
                    usernameTV.setText(username);
                    emailTV.setText(email);
                    telephoneTV.setText(phoneNumber);
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_about:
                // TODO: create about fragment
                return true;
            case R.id.action_logout:
                FirebaseUtil.logout(DashboardActivity.this);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
