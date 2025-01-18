package com.Literalura.AluraChallenge2.repository;

import com.Literalura.AluraChallenge2.model.Autores;
import com.Literalura.AluraChallenge2.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibrosRepository extends JpaRepository<Libros, Long> {
    Optional<Libros> findByTitulo(String titulo);

    @Query("SELECT l FROM Libros l JOIN l.autores a WHERE a.id = :autorId")
    List<Libros> findLibrosByAutorId(Long autorId);

    //@Query("SELECT a FROM Autores a WHERE a.ano_de_defuncion > :fecha AND a.ano_de_nacimiento < :fecha")
    //List<Autores> listaDeAutoresVivos(Long fecha);

    // Método para buscar libros por idioma
    List<Libros> findByIdioma(String idioma);
}
