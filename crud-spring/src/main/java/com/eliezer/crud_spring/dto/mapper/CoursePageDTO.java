package com.eliezer.crud_spring.dto.mapper;

import java.util.List;

import com.eliezer.crud_spring.dto.CourseDTO;

public record CoursePageDTO(List<CourseDTO> content, long totalElements, int totalPages) {

}
