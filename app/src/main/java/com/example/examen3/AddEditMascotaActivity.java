package com.example.examen3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditMascotaActivity extends AppCompatActivity
{
    public static final String EXTRA_NOMBRE = "com.example.pmdm_04.EXTRA_NOMBRE";
    public static final String EXTRA_RAZA = "com.example.pmdm_04.EXTRA_RAZA";
    public static final String EXTRA_EDAD = "com.example.pmdm_04.EXTRA_EDAD";
    public static final String EXTRA_IMAGEN = "com.example.pmdm_04.EXTRA_IMAGEN";
    public static final String EXTRA_ID = "com.example.pmdm_04.EXTRA_ID";


    private EditText editTextNombre;
    private EditText editTextRaza;
    private EditText editTextEdad;
    private EditText editTextImagen;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reserva);

        editTextNombre = findViewById(R.id.editTextCrearNombre);
        editTextRaza = findViewById(R.id.editTextCrearRaza);
        editTextEdad = findViewById(R.id.editTextEdad);
        editTextImagen = findViewById(R.id.editTexImagen);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_ID))
        {
            setTitle("Editar mascota");
            editTextNombre.setText(intent.getStringExtra(EXTRA_NOMBRE));
            editTextRaza.setText(intent.getStringExtra(EXTRA_RAZA));
            editTextEdad.setText(intent.getStringExtra(EXTRA_EDAD));
            editTextImagen.setText(intent.getStringExtra(EXTRA_IMAGEN));
        }
        else
        {
            setTitle("Añadir mascota");
        }
    }

    private void saveMascota()
    {


        String nombre = editTextNombre.getText().toString();
        String raza = editTextRaza.getText().toString();
        String edad = editTextEdad.getText().toString();
        String imagen = editTextImagen.getText().toString();

        if(nombre.trim().isEmpty() || raza.trim().isEmpty() || edad.trim().isEmpty() || imagen.trim().isEmpty())
        {
            Toast.makeText(this,"Por favor, inserte los datos de su mascota",Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NOMBRE,nombre);
        data.putExtra(EXTRA_RAZA,raza);
        data.putExtra(EXTRA_EDAD,edad);
        data.putExtra(EXTRA_IMAGEN,imagen);

        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if(id != -1)
        {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK,data);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_mascota_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.save_mascota:
                saveMascota();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}