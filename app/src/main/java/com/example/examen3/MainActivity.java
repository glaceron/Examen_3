package com.example.examen3;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private MascotaViewModel mascotaViewModel;
    public static final int ADD_MASCOTA_REQUEST = 1;
    public static final int EDIT_MASCOTA_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddMascota = findViewById(R.id.button_add_mascota);

        buttonAddMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, AddEditMascotaActivity.class);
                startActivityForResult(intent, ADD_MASCOTA_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        MascotaAdapter adapter = new MascotaAdapter();
        recyclerView.setAdapter(adapter);

        mascotaViewModel = ViewModelProviders.of(this).get(MascotaViewModel.class);
        mascotaViewModel.getAllMascotas().observe(this, new Observer<List<Mascota>>()
        {
            @Override
            public void onChanged(List<Mascota> mascotas)
            {
                //Update RecyclerView
                adapter.setMascotas(mascotas);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT)
        {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target)
            {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
            {
                mascotaViewModel.delete(adapter.getMascotaAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Mascota borrada correctamente!", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new MascotaAdapter.OnItemClickListener() {
            @Override
            public void onIntemClick(Mascota mascota) {
                Intent intent = new Intent(MainActivity.this, AddEditMascotaActivity.class);
                intent.putExtra(AddEditMascotaActivity.EXTRA_ID, mascota.getId());
                intent.putExtra(AddEditMascotaActivity.EXTRA_NOMBRE, mascota.getNombre());
                intent.putExtra(AddEditMascotaActivity.EXTRA_RAZA, mascota.getRaza());
                intent.putExtra(AddEditMascotaActivity.EXTRA_EDAD, mascota.getEdad());
                intent.putExtra(AddEditMascotaActivity.EXTRA_IMAGEN, mascota.getImagen());
                startActivityForResult(intent, EDIT_MASCOTA_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_MASCOTA_REQUEST && resultCode == RESULT_OK)
        {
            String nombre = data.getStringExtra(AddEditMascotaActivity.EXTRA_NOMBRE);
            String raza = data.getStringExtra(AddEditMascotaActivity.EXTRA_RAZA);
            String edad = data.getStringExtra(AddEditMascotaActivity.EXTRA_EDAD);
            String imagen = data.getStringExtra(AddEditMascotaActivity.EXTRA_IMAGEN);

            Mascota mascota = new Mascota(nombre, raza, edad, imagen);
            mascotaViewModel.insert(mascota);

        }
        else if(requestCode == EDIT_MASCOTA_REQUEST && resultCode == RESULT_OK)
        {
            int id = data.getIntExtra(AddEditMascotaActivity.EXTRA_ID, -1);

            String nombre = data.getStringExtra(AddEditMascotaActivity.EXTRA_NOMBRE);
            String raza = data.getStringExtra(AddEditMascotaActivity.EXTRA_RAZA);
            String edad = data.getStringExtra(AddEditMascotaActivity.EXTRA_EDAD);
            String imagen = data.getStringExtra(AddEditMascotaActivity.EXTRA_IMAGEN);

                Mascota mascota = new Mascota(nombre, raza, edad, imagen);
                mascota.setId(id);
                mascotaViewModel.update(mascota);

                Toast.makeText(this, "Mascota actualizada", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.delete_all_mascotas:
                mascotaViewModel.deleteAllMascotas();
                Toast.makeText(this, "Todas las mascotas han sido borradas con exito", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}