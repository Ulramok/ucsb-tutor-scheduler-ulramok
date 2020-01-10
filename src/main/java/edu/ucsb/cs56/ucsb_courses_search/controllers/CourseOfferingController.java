package edu.ucsb.cs56.ucsb_courses_search.controllers;

import javax.validation.Valid;

import edu.ucsb.cs56.ucsb_courses_search.entities.TutorAssignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import edu.ucsb.cs56.ucsb_courses_search.entities.CourseOffering;
import edu.ucsb.cs56.ucsb_courses_search.repositories.CourseOfferingRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

@Controller
public class CourseOfferingController {

    private Logger logger = LoggerFactory.getLogger(CourseOfferingController.class);

    private final CourseOfferingRepository courseOfferingRepository;

    @Autowired
    public CourseOfferingController(CourseOfferingRepository courseOfferingRepository) {
        this.courseOfferingRepository = courseOfferingRepository;
    }

    @GetMapping("/courseOfferings/create")
    public String create(CourseOffering courseOffering) {
        return "courseOfferings/create";
    }

    @GetMapping("/courseOfferings/mainCourse")
    public String mainCourse(CourseOffering courseOffering) {
        return "courseOfferings/mainCourse";
    }

    public boolean checkIfCourseExists(Iterable<CourseOffering> iter, CourseOffering courseOffering){
        boolean courseExists = false;
        for(CourseOffering course : iter) {
            if(courseOffering.equals(course)){
                courseExists = true;
            }
        }
        return courseExists;

    }
    @PostMapping("/courseOfferings/add")
    public String add(@Valid CourseOffering courseOffering, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "courseOfferings/create";
        }
        Iterable<CourseOffering> iter = courseOfferingRepository.findAll();
        boolean courseExists = checkIfCourseExists(iter, courseOffering);
        if(courseExists == true){
            final String errorCourseExists = "Course already exists for this quarter";
            model.addAttribute("errorCourseExists", errorCourseExists);
            return "index";
        }
        courseOfferingRepository.save(courseOffering);
        model.addAttribute("courseOfferings", courseOfferingRepository.findAll());
        return "index";
    }

    @GetMapping("/courseOfferings/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        CourseOffering courseOffering = courseOfferingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course offering Id:" + id));
        model.addAttribute("courseOffering", courseOffering);
        return "courseOfferings/update";
    }

    @PostMapping("/courseOfferings/update/{id}")
    public String update(@PathVariable("id") long id, @Valid CourseOffering courseOffering, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            courseOffering.setId(id);
            return "courseOfferings/update";
        }

        courseOfferingRepository.save(courseOffering);
        return "index";
    }

    @GetMapping("/courseOfferings/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        CourseOffering courseOffering = courseOfferingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid courseoffering Id:" + id));
        courseOfferingRepository.delete(courseOffering);
        model.addAttribute("courseOfferings", courseOfferingRepository.findAll());
        return "index";
    }
}
