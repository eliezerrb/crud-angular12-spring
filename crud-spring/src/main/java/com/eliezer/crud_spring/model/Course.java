package com.eliezer.crud_spring.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

// @Data do lombok para criar getter, setter, construtor, toString e equals
@Data
@Entity
//@Table(name = "cursos")
public class Course {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // Utilizado porque no front o nome está _id e o Jackson na tranformação de objeto para Json  altera o nome com essa anotação, poderia tambem utilizar um DTO
    @JsonProperty("_id")
    private Long id;

    @Column(length = 200, nullable = false)
    private String name;

    @Column(length = 10, nullable = false)
    private String category;
}
