package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class uploadSide extends AppCompatActivity {
    EditText key, notes;
    Button submit;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    boolean keyExist = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_side);

        key = findViewById(R.id.key);
        notes = findViewById(R.id.notes);
        submit = findViewById(R.id.submit);
        progressDialog = new ProgressDialog(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ConfirmationDialog = new AlertDialog.Builder(view.getContext());
                ConfirmationDialog.setTitle("Confirmation");
                ConfirmationDialog.setMessage("Are you sure you want to upload these?");
                ConfirmationDialog.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        checkKey();
                    }
                });
                ConfirmationDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                ConfirmationDialog.create().show();
            }
        });
    }
    private void checkKey() {
        keyExist = false;
        FirebaseDatabase.getInstance().getReference("Notes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String cuKey = key.getText().toString().trim();
                    String dbKey = dataSnapshot.getKey();
                    if(cuKey.equals(dbKey)){
                        keyExist = true;
                        break;
                    }
                }
                if(keyExist){
                    key.setError("Key already exist");
                    key.requestFocus();
                }
                else{
                    uploadData();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void uploadData() {
        String key1 = key.getText().toString().trim();
        String notes1 = notes.getText().toString().trim();
        databaseReference = FirebaseDatabase.getInstance().getReference("Notes");

        if(key1.isEmpty()){
            key.setError("key cannot be empty");
            key.requestFocus();
        }
        else if(notes1.isEmpty()){
            notes.setError("Please write something");
            notes.requestFocus();
        }
        else{
            progressDialog.setMessage("Please wait..");
            progressDialog.setTitle("Submitting");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            NotesModel notesModel = new NotesModel(notes1);
            databaseReference.child(key1).setValue(notesModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(uploadSide.this, "uploaded successfully", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        toMainActivity();
                    }
                    else{
                        Toast.makeText(uploadSide.this, "failed to upload", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });

        }
    }

    private void toMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}