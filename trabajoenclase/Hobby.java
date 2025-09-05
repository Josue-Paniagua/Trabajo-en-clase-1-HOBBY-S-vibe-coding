package com.example.trabajoenclase;

public class Hobby {
    private long id;
    private String nombre;
    private String dificultad;

    public Hobby(long id, String nombre, String dificultad) {
        this.id = id;
        this.nombre = nombre;
        this.dificultad = dificultad;
    }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }
}
