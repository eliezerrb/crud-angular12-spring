package com.eliezer.crud_spring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
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

import com.eliezer.crud_spring.dto.CourseDTO;
import com.eliezer.crud_spring.service.CourseService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

// @Validated para validar as anotações que coloquei nos parametros é necessário ele
@RestController
@RequestMapping("/api/courses")
@Validated
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // @RequestMapping(method = RequestMethod.GET) é mesma coisa que @GetMapping
    // @RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public List<CourseDTO> list() {
        return courseService.list();
    }

    // com @PathVariable é possível pegar o valor que está vindo na url
    // ResponseEntity classe que permite retornar caso agente queira controlar o que
    // esta indo de resposta na requisição
    // .map(course -> ResponseEntity.ok(course)) -> se o (retorno) existir retorna
    // ela
    @GetMapping("/{id}")
    public CourseDTO findById(@PathVariable @NotNull @Positive Long id) {
        return courseService.findById(id);
    }

    // @RequestMapping(method = RequestMethod.POST)
    // @RequestBody consegue pegar o que está vindo no payload na chamada do post
    // desde que as variaceis tenham o mesmo nome
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    // @Valid para validar as anotações que coloquei na entidade, ou seja, no json
    // que chegar é validado com as anotações do Course
    public CourseDTO create(@RequestBody @Valid CourseDTO course) {
        return courseService.create(course);
        // Se estivesse retornando Course e não quisesse alterar o status -> return
        // courseRepository.save(course);
        // se não tivesse a anotação @ResponseStatus -> return
        // ResponseEntity.status(HttpStatus.CREATED).body(courseRepository.save(course));
    }

    @PutMapping("/{id}")
    public CourseDTO update(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid @NotNull CourseDTO course) {
        return courseService.update(id, course);      
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NotNull @Positive Long id) {
        courseService.delete(id);
    }

}
