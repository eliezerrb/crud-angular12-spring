package com.eliezer.crud_spring.controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.eliezer.crud_spring.model.Course;
import com.eliezer.crud_spring.repository.CourseRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;


// @Validated para validar as anotações que coloquei nos parametros é necessário ele
@RestController
@RequestMapping("/api/courses")
// Anotação para gerar todoss os construtores pelo lombok
@AllArgsConstructor
@Validated
public class CourseController {

    // final para não alterar a instancia é uma boa prática
    private final CourseRepository courseRepository;

    // @RequestMapping(method = RequestMethod.GET) é mesma coisa que @GetMapping
    // @RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public List<Course> list() {
        return courseRepository.findAll();
    }

    // com @PathVariable é possível pegar o valor que está vindo na url
    // ResponseEntity classe que permite retornar caso agente queira controlar o que
    // esta indo de resposta na requisição
    // .map(course -> ResponseEntity.ok(course)) -> se o (retorno) existir retorna
    // ela
    @GetMapping("/{id}")
    public ResponseEntity<Course> findById(@PathVariable @NotNull @Positive Long id) {
        return courseRepository.findById(id)
                .map(recordFound -> ResponseEntity.ok(recordFound))
                .orElse(ResponseEntity.notFound().build());
    }

    // @RequestMapping(method = RequestMethod.POST)
    // @RequestBody consegue pegar o que está vindo no payload na chamada do post
    // desde que as variaceis tenham o mesmo nome
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    // @Valid para validar as anotações que coloquei na entidade, ou seja, no json que chegar é validado com as anotações do Course
    public Course create(@RequestBody @Valid Course course) {
        return courseRepository.save(course);
        // Se estivesse retornando Course e não quisesse alterar o status -> return
        // courseRepository.save(course);
        // se não tivesse a anotação @ResponseStatus -> return
        // ResponseEntity.status(HttpStatus.CREATED).body(courseRepository.save(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid Course course) {
        return courseRepository.findById(id)
                .map(recordFound -> {
                    recordFound.setName(course.getName());
                    recordFound.setCategory(course.getCategory());
                    Course updated = courseRepository.save(recordFound);
                    return ResponseEntity.ok().body(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull @Positive Long id) {
        return courseRepository.findById(id)
                .map(recordFound -> {
                    courseRepository.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
