package com.example.neodestiny.epe2_firestore.model;

public class Producto {
    private String nombre, tipoAnimal, nombreDueno, sexo ;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoAnimal() {
        return tipoAnimal;
    }

    public void setTipoAnimal(String tipoAnimal) {
        this.tipoAnimal = tipoAnimal;
    }

    public String getNombreDueno() {
        return nombreDueno;
    }

    public void setNombreDueno(String nombreDueno) {
        this.nombreDueno = nombreDueno;
    }

    public String getSexo() {
        return sexo;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}
