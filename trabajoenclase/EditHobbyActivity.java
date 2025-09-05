package com.example.trabajoenclase;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class EditHobbyActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private TextInputEditText editTextNombre;
    private Spinner spinnerDificultad;
    private long hobbyId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hobby);

        dbHelper = new DatabaseHelper(this);
        editTextNombre = findViewById(R.id.editTextNombre);
        spinnerDificultad = findViewById(R.id.spinnerDificultad);
        Button buttonGuardar = findViewById(R.id.buttonGuardar);

        // Configurar el spinner con las opciones de dificultad
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"fácil", "medio", "difícil"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDificultad.setAdapter(adapter);

        // Obtener datos del hobby si estamos editando
        hobbyId = getIntent().getLongExtra("hobby_id", -1);
        if (hobbyId != -1) {
            String nombre = getIntent().getStringExtra("hobby_nombre");
            String dificultad = getIntent().getStringExtra("hobby_dificultad");
            editTextNombre.setText(nombre);

            // Establecer la dificultad seleccionada en el spinner
            for (int i = 0; i < adapter.getCount(); i++) {
                if (adapter.getItem(i).equals(dificultad)) {
                    spinnerDificultad.setSelection(i);
                    break;
                }
            }

            setTitle("Editar Hobby");
        } else {
            setTitle("Nuevo Hobby");
        }

        buttonGuardar.setOnClickListener(v -> guardarHobby());
    }

    private void guardarHobby() {
        String nombre = editTextNombre.getText().toString().trim();
        String dificultad = spinnerDificultad.getSelectedItem().toString();

        if (nombre.isEmpty()) {
            editTextNombre.setError("Por favor ingrese un nombre");
            return;
        }

        long resultado;
        if (hobbyId == -1) {
            resultado = dbHelper.insertHobby(nombre, dificultad);
            if (resultado > 0) {
                Toast.makeText(this, "Hobby creado exitosamente", Toast.LENGTH_SHORT).show();
            }
        } else {
            resultado = dbHelper.updateHobby(hobbyId, nombre, dificultad);
            if (resultado > 0) {
                Toast.makeText(this, "Hobby actualizado exitosamente", Toast.LENGTH_SHORT).show();
            }
        }

        if (resultado > 0) {
            finish();
        } else {
            Toast.makeText(this, "Error al guardar el hobby", Toast.LENGTH_SHORT).show();
        }
    }
}
