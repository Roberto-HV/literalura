package com.example.literalura.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer anioNacimiento;
    private Integer anioMuerte;

    @OneToMany(mappedBy = "autor")
    private List<Libro> libros;

    // Getters, setters y toString()
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Integer getAnioNacimiento() { return anioNacimiento; }
    public void setAnioNacimiento(Integer anioNacimiento) { this.anioNacimiento = anioNacimiento; }
    public Integer getAnioMuerte() { return anioMuerte; }
    public void setAnioMuerte(Integer anioMuerte) { this.anioMuerte = anioMuerte; }
    public List<Libro> getLibros() { return libros; }
    public void setLibros(List<Libro> libros) { this.libros = libros; }
    @Override
    public String toString() {
        return "Autor: " + nombre + " (" + anioNacimiento + " - " + (anioMuerte != null ? anioMuerte : "vivo") + ")";
    }
}
