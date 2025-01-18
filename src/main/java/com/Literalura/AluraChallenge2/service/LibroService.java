package com.Literalura.AluraChallenge2.service;

import com.Literalura.AluraChallenge2.dto.datosDeAutoresDTO;
import com.Literalura.AluraChallenge2.dto.datosDeLibrosDTO;
import com.Literalura.AluraChallenge2.model.Autores;
import com.Literalura.AluraChallenge2.model.Libros;
import com.Literalura.AluraChallenge2.repository.LibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    @Autowired
    private LibrosRepository libroRepository;

    public void registrarLibro(datosDeLibrosDTO libroDTO) {
        // Primero verificar si el libro ya existe
        Optional<Libros> libroExistente = libroRepository.findByTitulo(libroDTO.titulo());
        if (libroExistente.isPresent()) {
            System.out.println("El libro ya está registrado.");
            return;
        }

        // Crear la entidad libros
        Libros libro = new Libros();
        libro.setTitulo(libroDTO.titulo());
        libro.setNumeroDeDescargas(libroDTO.numeroDeDescargas());

        // Crear la lista de autores para este libro (sin usar el repositorio de autores)
        List<Autores> autores = new ArrayList<>();

        for (datosDeAutoresDTO autorDTO : libroDTO.autores()) {
            // Crear un nuevo autor a partir de los datos del DTO
            Autores autor = new Autores(autorDTO);
            autores.add(autor);  // Agregar el autor a la lista (no se verifica si ya existe)
        }

        // Asociar los autores al libro
        libro.setAutores((Autores) autores);

        // Guardar el libro en la base de datos
        libroRepository.save(libro);
        System.out.println("Libro registrado correctamente.");
    }
}
