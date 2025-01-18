package com.Literalura.AluraChallenge2.principal;

import com.Literalura.AluraChallenge2.dto.datosDeAutoresDTO;
import com.Literalura.AluraChallenge2.dto.datosDeLibrosDTO;
import com.Literalura.AluraChallenge2.dto.datosListaDeLibrosDTO;
import com.Literalura.AluraChallenge2.model.Autores;
import com.Literalura.AluraChallenge2.model.Libros;
import com.Literalura.AluraChallenge2.repository.AutoresRepository;
import com.Literalura.AluraChallenge2.repository.LibrosRepository;
import com.Literalura.AluraChallenge2.service.ConsumoAPI;
import com.Literalura.AluraChallenge2.service.ConvertirDatos;
import com.Literalura.AluraChallenge2.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Principal {
    public Scanner teclado = new Scanner(System.in);
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvertirDatos conversor = new ConvertirDatos();
    private ConsumoAPI consumir = new ConsumoAPI();
    private List<datosDeLibrosDTO> listaDatosDeLibrosDTO = new ArrayList<>();
    private LibrosRepository repositorio;
    private LibroService libroService;
    private AutoresRepository autoresRepository; // Agrega el repositorio de autores


    @Autowired
    public Principal(LibroService libroService, LibrosRepository repository, AutoresRepository autoresRepository) {
        this.libroService = libroService;
        this.repositorio = repository;
        this.autoresRepository = autoresRepository;
    }

    public void menuPrincipal() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1. Buscar libro por título
                    2. Listar libros registrados
                    3. Listar autores registrados
                    4. Listar autores vivos en un determinado año
                    5. Listar libros por idioma
                    0. Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    autoresVivos();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private datosListaDeLibrosDTO buscarLibroPorTitulo() {
        // Solicitar el nombre del libro al usuario
        System.out.println("Escriba el nombre del libro:");
        var nombrelibro = teclado.nextLine();

        // Crear la URL para hacer la búsqueda en la API
        String url = URL_BASE + "?search=" + nombrelibro.toLowerCase().replace(" ", "%20");

        // Obtener los datos de la API en formato JSON
        var json = consumir.obtenerDatos(url);
        //System.out.println("Respuesta de la API: " + json);

        // Convertir el JSON a datosListaDeLibrosDTO
        datosListaDeLibrosDTO tituloBuscado = conversor.obtenerDatos(json, datosListaDeLibrosDTO.class);

        // Filtrar el libro con el título correspondiente
        if (tituloBuscado != null && tituloBuscado.listaLibros() != null && !tituloBuscado.listaLibros().isEmpty()) {
            // Filtrar para obtener solo el primer libro que coincida con el nombre
            var libro = tituloBuscado.listaLibros().stream()
                    .filter(datosDeLibrosDTO -> datosDeLibrosDTO.titulo().toUpperCase().contains(nombrelibro.toUpperCase()))
                    .findFirst();

            if (libro.isPresent()) {
                // Guardar el libro y autores
                guardarLibro(libro.get());

                // Imprimir los detalles del libro
                Optional<Libros> libroGuardado = repositorio.findByTitulo(libro.get().titulo());
                imprimirDetallesLibro(libroGuardado.get());

                return tituloBuscado;
            } else {
                System.out.println("No se encontró el libro. Vuelve a intentarlo.");
                return null;
            }
        } else {
            // En caso de que no haya resultados en la respuesta de la API
            System.out.println("No se encontró ningún libro con ese título. Vuelve a intentarlo.");
            return null;
        }


    }

    private void guardarLibro(datosDeLibrosDTO libroDTO) {
        // Verificar si el libro ya está en la base de datos
        Optional<Libros> libroExistente = repositorio.findByTitulo(libroDTO.titulo());
        if (libroExistente.isPresent()) {
            System.out.println("El libro ya está registrado.");
            return;
        }

        // Crear la entidad libros
        Libros libro = new Libros();
        libro.setTitulo(libroDTO.titulo());
        libro.setNumeroDeDescargas(libroDTO.numeroDeDescargas());

        //Asignar el idioma
        libro.setIdioma(String.valueOf(libroDTO.idioma()));

        if (libroDTO.autores() != null && !libroDTO.autores().isEmpty()) {
            datosDeAutoresDTO autorDTO = libroDTO.autores().get(0); // Obtener el primer autor
            Autores autor = new Autores(autorDTO); // Crear el autor con el constructor de Autores

            // Crear el libro y asignar el autor
            libro.setAutores(autor);

            // Guardar el libro en la base de datos
            repositorio.save(libro);
            System.out.println("Libro registrado correctamente.");
        } else {
            System.out.println("No se encontró un autor para este libro.");
        }
    }

    private void imprimirDetallesLibro(Libros libros) {
        // Imprimir los detalles del libro
        System.out.println("------------LIBRO------------");
        System.out.println("Título: " + libros.getTitulo());
        System.out.println("Autor: " + libros.getAutores().getNombre());
        System.out.println("Idioma: " + libros.getIdioma());
        System.out.println("Número de descargas: " + libros.getNumeroDeDescargas());
    }

    private void listarLibrosRegistrados() {
        List<Libros> libros = repositorio.findAll();
        libros.stream().sorted(Comparator.comparing(Libros::getTitulo))
                .forEach(this::imprimirDetallesLibro);
    }

    private void listarAutoresRegistrados() {
        List<Libros> libros = repositorio.findAll();

        Set<Autores> autoresUnicos = libros.stream()
                .map(Libros::getAutores)
                .collect(Collectors.toSet());

        autoresUnicos.forEach(autor -> {
            System.out.println("Autor: " + autor.getNombre());
            System.out.println("Fecha de nacimiento: " + autor.getAnoDeNacimiento());
            System.out.println("Fecha de fallecimiento: " + autor.getAnoDeDefuncion());
            System.out.println();
        });
    }

    private void autoresVivos() {
        // Solicitar el año al usuario
        System.out.println("Escriba el año que desea buscar");
        var anoDeseado = teclado.nextInt();
        teclado.nextLine();

        // Obtener todos los libros registrados
        List<Libros> libros = repositorio.findAll();

        // Usar un conjunto para evitar autores duplicados
        Set<Autores> autoresVivos = new HashSet<>();

        // Filtrar los autores vivos basándose en los libros y sus autores
        for (Libros libro : libros) {
            Autores autor = libro.getAutores();
            if (autor != null &&
                    autor.getAnoDeNacimiento() < anoDeseado &&
                    (autor.getAnoDeDefuncion() == null || autor.getAnoDeDefuncion() > anoDeseado)) {
                autoresVivos.add(autor);
            }
        }

        // Mostrar los autores vivos
        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año especificado.");
        } else {
            for (Autores autor : autoresVivos) {
                System.out.println("Autor: " + autor.getNombre());
                System.out.println("Fecha de nacimiento: " + autor.getAnoDeNacimiento());
                System.out.println("Fecha de fallecimiento: " + (autor.getAnoDeDefuncion() == null ? "Aún vivo" : autor.getAnoDeDefuncion()));
                System.out.println();
            }
        }
    }

    private void listarLibrosPorIdioma() {
        var opcion = -1;
        var menuIdioma = """
                    1. Español
                    2. Inglés
                    3. Francés
                    """;
        System.out.println(menuIdioma);
        opcion = teclado.nextInt();
        teclado.nextLine();
        String idioma;

        switch (opcion){
            case 1:
                idioma = "[es]";
                break;
            case 2:
                idioma = "[en]";
                break;
            case 3:
                idioma = "[fr]";
                break;
            default:
                System.out.println("Opción incorrecta, buscando inglés");
                idioma = "[en]";
                break;
        }

        // Consulta de los libros
        List<Libros> librosPorIdioma = repositorio.findByIdioma(idioma);

        //Mostrar los libros
        if (librosPorIdioma.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma seleccionado.");
        } else {
            librosPorIdioma.forEach(this::imprimirDetallesLibro);
        }

    }
}
