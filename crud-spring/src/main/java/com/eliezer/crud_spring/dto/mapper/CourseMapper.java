package com.eliezer.crud_spring.dto.mapper;

import org.springframework.stereotype.Component;

import com.eliezer.crud_spring.dto.CourseDTO;
import com.eliezer.crud_spring.model.Course;
import com.eliezer.enums.Category;

@Component
public class CourseMapper {

    public CourseDTO toDTO(Course course) {
        if (course == null) {
            return null;
        }
        return new CourseDTO(course.getId(), course.getName(), course.getCategory().getValue());
    }

    public Course toEntity(CourseDTO courseDTO) {

        if (courseDTO == null) {
            return null;
        }

        Course course = new Course();
        if (courseDTO.id() != null) {
            course.setId(courseDTO.id());
        }
        course.setName(courseDTO.name());
        // TODO: use a mapper para Category
        course.setCategory(convertCategoryValue(courseDTO.category()));
        return  course;
    }

    public Category convertCategoryValue(String value) {
        if (value == null) { 
            return null;
        }
        return switch (value) {
            case "Front-end" -> Category.FRONT_END;
            case "Back-end" -> Category.BACK_END;
            default -> throw new IllegalArgumentException("Categoria inválida: " + value);
        };
            
    }


}
