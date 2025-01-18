package com.Literalura.AluraChallenge2.model;

import com.Literalura.AluraChallenge2.dto.datosDeAutoresDTO;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Autores")
public class Autores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column(nullable = false)
    private String nombre;
    private Long anoDeNacimiento;
    private Long anoDeDefuncion;

    //Añadimos la relación con la base de datos de los libros
    @OneToMany(mappedBy = "autores")
    private List<Libros> libros;

    public Autores(){}

    public Autores(datosDeAutoresDTO datosDeAutoresDTO){
        this.nombre = datosDeAutoresDTO.nombre();
        this.anoDeNacimiento = Long.valueOf(datosDeAutoresDTO.anoDeNacimiento());
        this.anoDeDefuncion = Long.valueOf(datosDeAutoresDTO.anoDeDefuncion());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getAnoDeNacimiento() {
        return anoDeNacimiento;
    }

    public void setAnoDeNacimiento(Long anoDeNacimiento) {
        this.anoDeNacimiento = anoDeNacimiento;
    }

    public Long getAnoDeDefuncion() {
        return anoDeDefuncion;
    }

    public void setAnoDeDefuncion(Long anoDeDefuncion) {
        this.anoDeDefuncion = anoDeDefuncion;
    }

    public void setLibros(List<Libros> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return "Autores{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", anoDeNacimiento=" + anoDeNacimiento +
                ", anoDeDefuncion=" + anoDeDefuncion +
                '}';
    }

    public List<Libros> getLibros() {
        return libros;
    }

}
