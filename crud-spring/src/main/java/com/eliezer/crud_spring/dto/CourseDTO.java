package com.eliezer.crud_spring.dto;

import java.util.List;
import java.util.Locale.Category;

import com.eliezer.enums.validation.ValueOfEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CourseDTO(
     @JsonProperty("_id") Long id,
     @NotBlank @NotNull @Size(min = 3, max = 100) String name,
     @NotNull @Size(max = 10) @ValueOfEnum(enumClass = Category.class) String category,
     @NotNull @NotEmpty @Valid List<LessonDTO> lessons
) {
     
}
