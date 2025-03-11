package com.eliezer.crud_spring.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

// @Data do lombok para criar getter, setter, construtor, toString e equals
@SuppressWarnings("deprecation")
@Data
@Entity
//@Table(name = "cursos")
// Utilizado para fazer um soft delete, ou seja, não deleta o registro do banco, apenas altera o status para inativo
@SQLDelete(sql = "UPDATE Course SET status = 'Inativo' WHERE id = ?")
@Where(clause = "status = 'Ativo'")
public class Course {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // Utilizado porque no front o nome está _id e o Jackson na tranformação de objeto para Json  altera o nome com essa anotação, poderia tambem utilizar um DTO
    @JsonProperty("_id")
    private Long id;

    @NotBlank
    @NotNull
    @Size(min = 3, max = 100)
    @Column(length = 100, nullable = false)
    private String name;

    @NotNull
    @Size(max = 10)
    // Utilizado para validar se o valor é Back-end ou Front-end
    @Pattern(regexp = "Back-end|Front-end")
    @Column(length = 10, nullable = false)
    private String category;

    @NotNull
    @Size(max = 10)
    @Pattern(regexp = "Ativo|Inativo")
    @Column(length = 10, nullable = false)
    private String status = "Ativo";
}
