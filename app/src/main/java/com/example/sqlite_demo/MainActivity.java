package com.example.sqlite_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity implements JsonTask.JsonTaskListener {

    private final String JSON_URL = "https://mobprog.webug.se/json-api?login=brom";

    private RecyclerView mountainRecyclerView;
    private MountainAdapter adapter;
    private List<Mountain> mountains;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mountainRecyclerView = findViewById(R.id.recycler_view);
        adapter = new MountainAdapter(mountains);
        mountainRecyclerView.setAdapter(adapter);
        mountainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        new JsonTask(this).execute(JSON_URL);

    }

    @Override
    public void onPostExecute(String json) {
        Log.d("==>", json);
    }
}