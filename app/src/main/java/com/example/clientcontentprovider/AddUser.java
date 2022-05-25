package com.example.clientcontentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class AddUser extends AppCompatActivity {
    Button btnSave, btnCancel;
    EditText txtNewFirstName, txtNewLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        btnSave = (Button)findViewById(R.id.btnSave);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        txtNewFirstName = (EditText)findViewById(R.id.txtNewFirstName);
        txtNewLastName = (EditText)findViewById(R.id.txtNewLastName);

        btnSave.setOnClickListener(view -> {
            if(txtNewFirstName.getText().length() > 0 && txtNewLastName.getText().length() > 0){
                ContentValues cv = new ContentValues();
                cv.put(ListItem.COLUMN_FIRSTNAME, txtNewFirstName.getText().toString());
                cv.put(ListItem.COLUMN_LASTNAME, txtNewLastName.getText().toString());

                Uri uri = getContentResolver().insert(ListItem.CONTENT_URI, cv);
                Intent intent = new Intent();
                intent.putExtra("response", uri.toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btnCancel.setOnClickListener(view -> {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        });

    }
}