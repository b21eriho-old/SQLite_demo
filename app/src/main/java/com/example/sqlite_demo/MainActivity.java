package com.example.sqlite_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

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

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gson = new Gson();
        mountains = new ArrayList<Mountain>();
        mountainRecyclerView = findViewById(R.id.recycler_view);
        adapter = new MountainAdapter(mountains);
        mountainRecyclerView.setAdapter(adapter);
        mountainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        new JsonTask(this).execute(JSON_URL);

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