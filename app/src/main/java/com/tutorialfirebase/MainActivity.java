package com.tutorialfirebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    Button btnLogout;
    private DatabaseReference mPostReference;
    ArrayList<User> users;
    private UserAdapter userAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Write a message to the database
        userAdapter = new UserAdapter(this);
        recyclerView = findViewById(R.id.rvUser);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // Initialize Database
        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("users");

        btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

        getDataFirebase();

    }

    private void getDataFirebase() {
        // Add value event listener to the post
        // [START post_value_event_listener]

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot!=null) {
                    GenericTypeIndicator<HashMap<String, User>> objectsGTypeInd = new GenericTypeIndicator<HashMap<String, User>>() {
                    };
                    Map<String, User> objectHashMap = dataSnapshot.getValue(objectsGTypeInd);
                    ArrayList<User> objectArrayList = new ArrayList<User>(objectHashMap.values());
                    userAdapter.add(objectArrayList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerView.setAdapter(userAdapter);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("yudha", "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(MainActivity.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        mPostReference.addValueEventListener(postListener);
        // [END post_value_event_listener]
    }

    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        mDatabase.child("users").child(userId).child("username").setValue(name);

    }
}