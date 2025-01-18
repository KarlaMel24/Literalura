package com.Literalura.AluraChallenge2.model;

import com.Literalura.AluraChallenge2.dto.datosDeLibrosDTO;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "autor_id")
    private Autores autores;
    private String idioma;
    private Integer numeroDeDescargas;

    public Libros(){}

    public Libros(datosDeLibrosDTO datosDeLibrosDTO, Autores autor){
        this.id = datosDeLibrosDTO.id();
        this.titulo = datosDeLibrosDTO.titulo();
        this.autores = autor; //añadimos autor como parámetro para tratar de relacionar el autor con el libro
        this.idioma = String.join(", ", datosDeLibrosDTO.idioma());
        this.numeroDeDescargas = datosDeLibrosDTO.numeroDeDescargas();

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Autores getAutores() {
        return autores;
    }

    public void setAutores(Autores autores) {
        this.autores = autores;
    }

    public Integer getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Integer numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    @Override
    public String toString() {
        return "Libros{" +
                "titulo='" + titulo + '\'' +
                ", autores=" + autores +
                ", idioma='" + idioma + '\'' +
                ", numeroDeDescargas=" + numeroDeDescargas +
                '}';
    }

}
