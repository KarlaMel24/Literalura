package com.Literalura.AluraChallenge2.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record datosListaDeLibrosDTO(
       @JsonAlias("results") List<datosDeLibrosDTO> listaLibros
) {

}