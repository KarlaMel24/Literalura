package com.Literalura.AluraChallenge2;

import com.Literalura.AluraChallenge2.principal.Principal;
import com.Literalura.AluraChallenge2.repository.AutoresRepository;
import com.Literalura.AluraChallenge2.repository.LibrosRepository;
import com.Literalura.AluraChallenge2.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AluraChallenge2Application {

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
		}
	}

