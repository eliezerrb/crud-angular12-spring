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
        return new CourseDTO(course.getId(), course.getName(), "Front-end");
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
        course.setCategory(Category.FRONT_END);
        return  course;
    }
}
