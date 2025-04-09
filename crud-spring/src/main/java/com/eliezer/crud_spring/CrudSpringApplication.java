package com.eliezer.crud_spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.eliezer.crud_spring.model.Course;
import com.eliezer.crud_spring.model.Lesson;
import com.eliezer.crud_spring.repository.CourseRepository;
import com.eliezer.enums.Category;

@SpringBootApplication
public class CrudSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudSpringApplication.class, args);
	}

	// Bean falando para o spring para ele gerenciar todo o ciclo de vida
	// CommandLineRunner Assim que executar o projeto, faça isso
	@Bean
	// @Profile("dev") - só vai executar esse método se o perfil for dev
	@Profile({"test", "dev"})
	CommandLineRunner initDatabase(CourseRepository courseRepository) {
		return args -> {
			courseRepository.deleteAll();

			for (int i = 0; i < 20; i++) {
				Course c = new Course();
				c.setName("Angular com Spring" + i);
				c.setCategory(Category.FRONT_END);

				Lesson l = new Lesson();
				l.setName("Introdução");
				l.setYoutubeUrl("01234567890");
				l.setCourse(c);
				c.getLessons().add(l);

				Lesson l1 = new Lesson();
				l1.setName("Angular");
				l1.setYoutubeUrl("01234567891");
				l1.setCourse(c);
				c.getLessons().add(l1);

				courseRepository.save(c);
			}

		};
	}

}
