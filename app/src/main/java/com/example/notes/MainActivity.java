package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button upload, retrieve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        upload = findViewById(R.id.upload);
        retrieve = findViewById(R.id.retrieve);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toUploadActivity();
            }
        });

        retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toRetrieveActivity();
            }
        });
    }

    private void toUploadActivity() {
        Intent intent = new Intent(this, uploadSide.class);
        startActivity(intent);
    }
    private void toRetrieveActivity() {
        Intent intent = new Intent(this, retrieveSide.class);
        startActivity(intent);
    }
}