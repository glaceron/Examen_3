package com.example.examen3;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MascotaDao
{
    @Insert
    void insert(Mascota mascota);

    @Update
    void update(Mascota mascota);

    @Delete
    void delete(Mascota mascota);


    @Query("DELETE FROM mascota_table")
    void deleteAllMascotas();

    @Query("SELECT * FROM mascota_table")
    LiveData<List<Mascota>> getAllMascotas();
}
