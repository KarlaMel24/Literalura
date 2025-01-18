package com.Literalura.AluraChallenge2.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record datosDeAutoresDTO(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") Integer anoDeNacimiento,
        @JsonAlias ("death_year") Integer anoDeDefuncion
) {
}
