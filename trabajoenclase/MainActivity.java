package com.example.trabajoenclase;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HobbyAdapter.OnHobbyClickListener {
    private DatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private List<Hobby> hobbiesList;
    private HobbyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerViewHobbies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        hobbiesList = new ArrayList<>();
        adapter = new HobbyAdapter(hobbiesList, this);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fabAddHobby = findViewById(R.id.fabAddHobby);
        fabAddHobby.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditHobbyActivity.class);
            startActivity(intent);
        });

        cargarHobbies();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarHobbies();
    }

    private void cargarHobbies() {
        hobbiesList.clear();
        Cursor cursor = dbHelper.getAllHobbies();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String nombre = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NOMBRE));
                String dificultad = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DIFICULTAD));
                hobbiesList.add(new Hobby(id, nombre, dificultad));
            } while (cursor.moveToNext());
            cursor.close();
        }

        adapter.updateHobbies(hobbiesList);
    }

    @Override
    public void onHobbyClick(Hobby hobby) {
        Intent intent = new Intent(this, EditHobbyActivity.class);
        intent.putExtra("hobby_id", hobby.getId());
        intent.putExtra("hobby_nombre", hobby.getNombre());
        intent.putExtra("hobby_dificultad", hobby.getDificultad());
        startActivity(intent);
    }

    @Override
    public void onHobbyLongClick(Hobby hobby) {
        new AlertDialog.Builder(this)
            .setTitle("Eliminar Hobby")
            .setMessage("¿Estás seguro de que deseas eliminar este hobby?")
            .setPositiveButton("Sí", (dialog, which) -> {
                dbHelper.deleteHobby(hobby.getId());
                cargarHobbies();
            })
            .setNegativeButton("No", null)
            .show();
    }
}