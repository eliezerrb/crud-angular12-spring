package com.eliezer.crud_spring.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
}
