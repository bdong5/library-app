package com.bdong5.mylibrary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AlreadyReadBookActivity extends AppCompatActivity {

    /**
     * setFlags helps clear "history" of back button to ensure smooth flow
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_already_read_book);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.bookRecView);
        BookRecViewAdapter adapter = new BookRecViewAdapter(this, "alreadyRead");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


//        adapter.setBooks((Utils.getInstance(this).getAlreadyReadBooks()));
        Utils.getInstance(this).getAlreadyReadBooks(new Utils.FirebaseCallback<ArrayList<Book>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onCallback(ArrayList<Book> result) {
                adapter.setBooks(result);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception e) {
                Log.w("Firestore", "Error getting books", e);
            }
        });

    }
}