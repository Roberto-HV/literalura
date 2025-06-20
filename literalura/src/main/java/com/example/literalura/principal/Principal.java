package com.example.literalura.principal;

import com.example.literalura.dto.DatosLibro;
import com.example.literalura.dto.ResultadoBusqueda;
import com.example.literalura.model.Autor;
import com.example.literalura.model.Libro;
import com.example.literalura.repository.AutorRepository;
import com.example.literalura.repository.LibroRepository;
import com.example.literalura.service.ConsumoAPI;
import com.example.literalura.service.ConvierteDatos;

import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private LibroRepository libroRepo;
    private AutorRepository autorRepo;

    public Principal(LibroRepository libroRepo, AutorRepository autorRepo) {
        this.libroRepo = libroRepo;
        this.autorRepo = autorRepo;
    }

    public void muestraMenu() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("""
                Elija la opción a través de su número:
                1- Buscar libro por título
                2- Listar libros registrados
                3- Listar autores registrados
                4- Listar autores vivos en un determinado año
                5- Listar libros por idioma
                0- Salir
                """);

            opcion = Integer.parseInt(teclado.nextLine());

            switch (opcion) {
                case 1 -> buscarLibroPorTitulo();
                case 2 -> listarLibros();
                case 3 -> listarAutores();
                case 4 -> listarAutoresVivosEnAnio();
                case 5 -> listarLibrosPorIdioma();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println("Ingrese el nombre del libro que desea buscar:");
        String titulo = teclado.nextLine();
        String url = "https://openlibrary.org/search.json?title=" + titulo.replace(" ", "+");

        String json = consumoApi.obtenerDatos(url);
        DatosRespuesta respuesta = conversor.obtenerDatos(json, DatosRespuesta.class);

        if (respuesta.getDocs().isEmpty()) {
            System.out.println("No se encontraron libros con ese título.");
            return;
        }

        DatosLibro datos = respuesta.getDocs().get(0); // Tomamos el primer resultado
        System.out.println("Resultado encontrado:");
        System.out.println(datos);

        // Validar si ya está registrado
        Optional<Libro> libroExistente = libroRepo.findByTituloContainsIgnoreCase(datos.getTitulo());
        if (libroExistente.isPresent()) {
            System.out.println("El libro ya está registrado: " + libroExistente.get());
            return;
        }

        // Obtener nombre del autor
        String nombreAutor = datos.getAutores().isEmpty() ? "Autor desconocido" : datos.getAutores().get(0);

        // Buscar o crear el autor
        Autor autor = autorRepo.findAll().stream()
                .filter(a -> a.getNombre().equalsIgnoreCase(nombreAutor))
                .findFirst()
                .orElseGet(() -> {
                    Autor nuevo = new Autor();
                    nuevo.setNombre(nombreAutor);
                    return autorRepo.save(nuevo);
                });

        // Crear y guardar el libro
        Libro libro = new Libro();
        libro.setTitulo(datos.getTitulo());
        libro.setIdioma(datos.getIdiomas().isEmpty() ? "Desconocido" : datos.getIdiomas().get(0));
        libro.setAutor(autor);

        libroRepo.save(libro);
        System.out.println("Libro guardado en la base de datos.");
    }


    private void listarLibros() {
        List<Libro> libros = libroRepo.findAll();
        libros.forEach(System.out::println);
    }

    private void listarAutores() {
        List<Autor> autores = autorRepo.findAll();
        autores.forEach(System.out::println);
    }

    private void listarAutoresVivosEnAnio() {
        System.out.println("Ingrese el año para listar autores vivos:");
        int anio = Integer.parseInt(teclado.nextLine());
        List<Autor> autoresVivos = autorRepo.findByAnioMuerteGreaterThan(anio);
        autoresVivos.forEach(System.out::println);
    }

    private void listarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma para listar libros:");
        String idioma = teclado.nextLine();
        List<Libro> librosPorIdioma = libroRepo.findByIdiomaIgnoreCase(idioma);
        librosPorIdioma.forEach(System.out::println);
    }
}
