package com.example.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DatosLibro {

    @JsonAlias("title")
    private String titulo;

    @JsonAlias("author_name")
    private List<String> autores;

    @JsonAlias("first_publish_year")
    private Integer anioPublicacion;

    @JsonAlias("language")
    private List<String> idiomas;

    public String getTitulo() {
        return titulo;
    }

    public List<String> getAutores() {
        return autores;
    }

    public Integer getAnioPublicacion() {
        return anioPublicacion;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    @Override
    public String toString() {
        return "Título: " + titulo +
                "\nAutores: " + autores +
                "\nAño de publicación: " + anioPublicacion +
                "\nIdiomas: " + idiomas;
    }
}
