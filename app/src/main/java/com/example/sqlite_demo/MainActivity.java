package com.example.sqlite_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements JsonTask.JsonTaskListener {

    private final String JSON_URL = "https://mobprog.webug.se/json-api?login=brom";

    private RecyclerView mountainRecyclerView;
    private MountainAdapter adapter;
    private List<Mountain> mountains;

    private Button fetchWwwButton;
    private Button fetchDBButton;
    private Button clearViewButton;
    private Button clearDBButton;

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetchWwwButton = findViewById(R.id.fetch_www);
        fetchDBButton = findViewById(R.id.fetch_db);
        clearViewButton = findViewById(R.id.clear_recycler_view);
        clearDBButton = findViewById(R.id.clear_db);

        fetchWwwButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JsonTask(MainActivity.this).execute(JSON_URL);
            }
        });

        clearViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mountains.clear();
                adapter.notifyDataSetChanged();
            }
        });

        gson = new Gson();
        mountains = new ArrayList<Mountain>();
        mountainRecyclerView = findViewById(R.id.recycler_view);
        adapter = new MountainAdapter(mountains);
        mountainRecyclerView.setAdapter(adapter);
        mountainRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onPostExecute(String json) {
        Log.d("==>", json);

        Type type = new TypeToken<List<Mountain>>() {}.getType();
        List<Mountain> mountainList = gson.fromJson(json, type);
        mountains.addAll(mountainList);

        adapter.notifyDataSetChanged();
    }
}