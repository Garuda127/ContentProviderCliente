package com.example.clientcontentprovider;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

public class UpdateUser extends AppCompatActivity {
    Button btnUpdate, btnCancel;
    EditText txtUpdateFirstName, txtUpdateLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        String uid = getIntent().getStringExtra("uid");
        String firstName = getIntent().getStringExtra("firstName");
        String lastName = getIntent().getStringExtra("lastName");

        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        btnCancel = (Button)findViewById(R.id.btnCancel2);
        txtUpdateFirstName = (EditText)findViewById(R.id.txtUpdateFirstName);
        txtUpdateLastName = (EditText)findViewById(R.id.txtUpdateLastName);

        txtUpdateFirstName.setText(firstName);
        txtUpdateLastName.setText(lastName);

        btnUpdate.setOnClickListener(view -> {
            ContentValues cv = new ContentValues();
            cv.put(ListItem.COLUMN_FIRSTNAME, txtUpdateFirstName.getText().toString());
            cv.put(ListItem.COLUMN_LASTNAME, txtUpdateLastName.getText().toString());

            int result = getContentResolver().update(Uri.withAppendedPath(ListItem.CONTENT_URI, uid), cv, null, null);
            Intent intent = new Intent(UpdateUser.this, MainActivity.class);
            startActivity(intent);
        });

        btnCancel.setOnClickListener(view -> {
            Intent intent = new Intent(UpdateUser.this, MainActivity.class);
            startActivity(intent);
        });
    }
}