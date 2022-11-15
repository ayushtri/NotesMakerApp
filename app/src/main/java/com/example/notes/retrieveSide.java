package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class retrieveSide extends AppCompatActivity {
    EditText keyR;
    TextView notesR;
    Button enter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_side);
        keyR = findViewById(R.id.keyR);
        notesR = findViewById(R.id.notesR);
        enter = findViewById(R.id.enter);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrieveData();
            }
        });
    }

    private void retrieveData() {
        String keyVal = keyR.getText().toString().trim();
        databaseReference = FirebaseDatabase.getInstance().getReference("Notes");

        if(keyVal.isEmpty()){
            keyR.setError("Please enter key");
            keyR.requestFocus();
        }
        else{
            databaseReference.child(keyVal).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        String reNotes=String.valueOf(snapshot.child("notes").getValue());
                        notesR.setText(reNotes);
                    }
                    else{
                        Toast.makeText(retrieveSide.this, "Invalid Key or Network Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}