package com.eliezer.crud_spring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.eliezer.crud_spring.model.Course;
import com.eliezer.crud_spring.repository.CourseRepository;

import lombok.AllArgsConstructor;



@RestController
@RequestMapping("/api/courses")
// Anotação para gerar todoss os construtores pelo lombok
@AllArgsConstructor
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
    // ResponseEntity classe que permite retornar caso agente queira controlar o que esta indo de resposta na requisição
    // .map(course -> ResponseEntity.ok(course)) -> se o (retorno) existir retorna ela
    @GetMapping("/{id}")
    public ResponseEntity<Course> findById(@PathVariable Long id) {
        return courseRepository.findById(id)
                .map(course -> ResponseEntity.ok(course))
                .orElse(ResponseEntity.notFound().build());
    }
    

    // @RequestMapping(method = RequestMethod.POST)   
    // @RequestBody consegue pegar o que está vindo no payload na chamada do post desde que as variaceis tenham o mesmo nome
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Course create(@RequestBody Course course) {
        return courseRepository.save(course);
        // Se  estivesse retornando Course e não quisesse alterar o status -> return courseRepository.save(course);
        // se não tivesse a anotação @ResponseStatus -> return ResponseEntity.status(HttpStatus.CREATED).body(courseRepository.save(course));
    }
}
