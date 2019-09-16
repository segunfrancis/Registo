package com.android.segunfrancis.registo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference mReference = FirebaseDatabase.getInstance().getReference("user-data").child(mAuth.getCurrentUser().getUid());
    ShimmerFrameLayout mFrameLayout1;
    ShimmerFrameLayout mFrameLayout2;
    ShimmerFrameLayout mFrameLayout3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        final TextView usernameTV = findViewById(R.id.username_text_view);
        final TextView emailTV = findViewById(R.id.email_text_view);
        final TextView telephoneTV = findViewById(R.id.telephone_text_view);

        mFrameLayout1 = findViewById(R.id.shimmer1);
        mFrameLayout2 = findViewById(R.id.shimmer2);
        mFrameLayout3 = findViewById(R.id.shimmer3);

        // Start Shimmer Effect
        mFrameLayout1.startShimmer();
        mFrameLayout2.startShimmer();
        mFrameLayout3.startShimmer();

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel model = dataSnapshot.getValue(UserModel.class);
                String username = model.getUsername();
                String email = model.getEmail();
                String phoneNumber = model.getTelephone();
                usernameTV.setText(username);
                emailTV.setText(email);
                telephoneTV.setText(phoneNumber);

                // Stop and hide Shimmer Effect
                mFrameLayout1.stopShimmer();
                mFrameLayout2.stopShimmer();
                mFrameLayout3.stopShimmer();
                mFrameLayout1.setVisibility(View.GONE);
                mFrameLayout2.setVisibility(View.GONE);
                mFrameLayout3.setVisibility(View.GONE);
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
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            case R.id.action_logout:
                Utils.logout(DashboardActivity.this);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
