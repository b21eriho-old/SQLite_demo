package com.example.sqlite_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
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

    private DataBaseHelper db;

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

        fetchDBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = db.getReadableDatabase().rawQuery("SELECT * FROM " + DataBaseHelper.TABLE_MOUNTAIN + " ORDER BY " + DataBaseHelper.COLLUMN_METER, null, null);
                List<Mountain> tmpMountains = new ArrayList<>();

                while (cursor.moveToNext()) {
                    Mountain mountain = new Mountain(
                            cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLLUMN_NAME)),
                            cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLLUMN_LOCATION)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLLUMN_METER))
                    );
                    tmpMountains.add(mountain);
                }
                cursor.close();
                mountains.addAll(tmpMountains);
                adapter.notifyDataSetChanged();
            }
        });

        clearDBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.getWritableDatabase().execSQL("DELETE FROM " + DataBaseHelper.TABLE_MOUNTAIN);
            }
        });

        db = new DataBaseHelper(this);
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

        for(int i = 0; i < mountainList.size(); i++){
            Mountain mountain = mountainList.get(i);
            ContentValues values = new ContentValues();
            values.put(DataBaseHelper.COLLUMN_NAME, mountain.getName());
            values.put(DataBaseHelper.COLLUMN_LOCATION, mountain.getLocation());
            values.put(DataBaseHelper.COLLUMN_METER, mountain.getMeter());
            db.getWritableDatabase().insert(DataBaseHelper.TABLE_MOUNTAIN, null, values);

        }

        mountains.addAll(mountainList);
        adapter.notifyDataSetChanged();
    }
}