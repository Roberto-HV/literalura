package com.example.literalura.model;

import com.example.literalura.dto.DatosLibro;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String idioma;

    @ManyToOne
    private Autor autor;

    // Constructor vacío requerido por JPA
    public Libro() {}

    // Constructor que acepta DatosLibro y un Autor
    public Libro(DatosLibro datos, Autor autor) {
        this.titulo = datos.getTitulo();
        this.idioma = (datos.getIdiomas() != null && !datos.getIdiomas().isEmpty())
                ? datos.getIdiomas().get(0)
                : "desconocido";
        this.autor = autor;
    }

    // Getters y Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getIdioma() { return idioma; }

    public void setIdioma(String idioma) { this.idioma = idioma; }

    public Autor getAutor() { return autor; }

    public void setAutor(Autor autor) { this.autor = autor; }

    @Override
    public String toString() {
        return "Libro: " + titulo + ", Idioma: " + idioma + ", Autor: " + autor.getNombre();
    }
}
