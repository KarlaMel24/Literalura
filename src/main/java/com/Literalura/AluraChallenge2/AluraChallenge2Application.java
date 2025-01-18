package com.Literalura.AluraChallenge2;

import com.Literalura.AluraChallenge2.principal.Principal;
import com.Literalura.AluraChallenge2.repository.AutoresRepository;
import com.Literalura.AluraChallenge2.repository.LibrosRepository;
import com.Literalura.AluraChallenge2.service.LibroService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AluraChallenge2Application {

	/*public static void main(String[] args) {
		SpringApplication.run(AluraChallenge2Application.class, args);
	}
	@Bean
	public CommandLineRunner run() {
		return args -> {
			// Instancia la clase Principal que contiene el menú
			Principal principal = new Principal();
			principal.menuPrincipal(); // Esto ejecuta tu menú
		};
	}*/

	@Autowired
	private LibroService libroService;
	@Autowired
	private LibrosRepository repository;
	private AutoresRepository autoresRepository;

	public static void main(String[] args) {
		SpringApplication.run(AluraChallenge2Application.class, args);
	}

	@Bean
	public CommandLineRunner run() {
		return args -> {
			//Principal principal = new Principal(libroService, repository);
			Principal principal =new Principal(libroService, repository, autoresRepository);
			principal.menuPrincipal();
		};
	/*@Bean
	public CommandLineRunner run() {
		return args -> {
			// Instanciamos la clase Principal, que manejará el menú
			Principal principal = new Principal((LibroService) libroService);  // Pasamos libroService al constructor
			principal.menuPrincipal();  // Ejecutamos el menú principal
		};
	}*/

	/*@Autowired
	private LibrosRepository repository;
	public static void main(String[] args){
		SpringApplication.run(AluraChallenge2Application.class, args);
	}

	public void run(String... args) throws Exception{
		Principal principal = new Principal(repository);
		principal.menuPrincipal();
	}*/

		}
	}

