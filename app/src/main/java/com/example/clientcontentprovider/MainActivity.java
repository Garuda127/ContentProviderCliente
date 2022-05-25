package com.example.clientcontentprovider;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton btnAdd;
    SearchView searchView;
    ListAdapter adapter;
    List<ListItem> users;

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        Serializable response = intent.getSerializableExtra("response");
                        Log.d("RESPONSE", response.toString());
                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG);
                        init(1,null);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = (FloatingActionButton) findViewById(R.id.btnAdd);
        searchView = (SearchView) findViewById(R.id.searchView);

        btnAdd.setOnClickListener(view -> {
            mStartForResult.launch(new Intent(this, AddUser.class));
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("SEARCH", "onQueryTextChange: " + newText);
                init(2, newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("SEARCH", "onQueryTextSubmit: " + query);
                return true;
            }
        });

        init(1,null);
    }

    public void init(int option, String value) {

        if(option == 1){
            getUsers();
        }

        if(option == 2){
            getUserByName(value);
        }

        adapter = new ListAdapter(users, this);
        RecyclerView recyclerView = findViewById(R.id.lstUsuarios);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper helperSwipe = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getBindingAdapterPosition();
                        ListItem item = adapter.getItemAtPosition(position);
                        Log.d("ITEM", "Posicion: " + position + " || UID: " + item.getUid() + " || Nombre: " + item.getFirstName());
                        int result = getContentResolver().delete(Uri.withAppendedPath(ListItem.CONTENT_URI, String.valueOf(item.getUid())), null, null);
                        Log.d("DELETED", String.valueOf(result));
                        if(result > 0){
                            Toast.makeText(MainActivity.this, item.getFirstName() + " eliminado!", Toast.LENGTH_LONG).show();
                        }
                        init(1,null);
                    }
                }
        );

        helperSwipe.attachToRecyclerView(recyclerView);

    }

    private void getUsers() {
        users = new ArrayList<>();
        Cursor cursor = getContentResolver().query(ListItem.CONTENT_URI, ListItem.COLUMNS_NAME, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Log.d("Consulta", cursor.getInt(0) + " - " + cursor.getString(1));
                users.add(new ListItem(cursor.getInt(0), "#0AB1F0", cursor.getString(1), cursor.getString(2)));
            }
        } else {
            Log.d("Consulta", "NO DEVUELVE");
        }
    }

    private void getUserByName(String value){
        users = new ArrayList<>();
        Cursor cursor = getContentResolver().query(Uri.withAppendedPath(ListItem.CONTENT_URI, value), ListItem.COLUMNS_NAME, null, null, null);
        if(cursor != null){
            while(cursor.moveToNext()){
                Log.d("USER", cursor.getInt(0) + " - " + cursor.getString(1));
                users.add(new ListItem(cursor.getInt(0), "#0AB1F0", cursor.getString(1), cursor.getString(2)));
            }
        }
    }
}