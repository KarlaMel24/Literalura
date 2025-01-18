package com.Literalura.AluraChallenge2.controller;

import com.Literalura.AluraChallenge2.dto.datosDeLibrosDTO;
import com.Literalura.AluraChallenge2.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @PostMapping("/registrar")
    public String registrarLibro(@RequestBody datosDeLibrosDTO libroDTO) {
        libroService.registrarLibro(libroDTO);
        return "Libro registrado correctamente.";
    }
}
