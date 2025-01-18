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

    //@Query("SELECT a FROM Autores a WHERE a.ano_de_nacimiento < :ano AND (a.ano_de_defuncion IS NULL OR a.ano_de_defuncion > :ano)")
    //List<Autores> findAutoresVivos(Long ano);

    //List<Autores> findByAnoDeNacimientoLessThanAndAnoDeDefuncionGreaterThanOrAnoDeDefuncionIsNull(Long anoDeNacimiento, Long anoDeDefuncion);

}

