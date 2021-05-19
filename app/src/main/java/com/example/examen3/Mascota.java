package com.example.examen3;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mascota_table")
public class Mascota
{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nombre, raza, edad, imagen;

    public Mascota(String nombre, String raza, String edad, String imagen) {
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.imagen = imagen;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRaza() {
        return raza;
    }

    public String getEdad()
    {
        return edad;
    }

    public String getImagen() { return imagen; }


}
