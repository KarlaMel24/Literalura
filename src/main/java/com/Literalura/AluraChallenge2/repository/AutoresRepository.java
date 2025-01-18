package com.Literalura.AluraChallenge2.repository;

import com.Literalura.AluraChallenge2.model.Autores;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutoresRepository extends CrudRepository<Autores, Long> {

    @Query("SELECT a FROM Autores a JOIN FETCH a.libros l WHERE a.id = :autorId")
    Optional<Autores> findByIdWithLibros(Long autorId);

    @Query("SELECT a FROM Autores a JOIN FETCH a.libros")
    List<Autores> findAllWithLibros();

}

