package edu.ucsb.cs56.ucsb_courses_search.controllers;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;

import edu.ucsb.cs56.ucsb_courses_search.repositories.TutorRepository;
import edu.ucsb.cs56.ucsb_courses_search.repositories.CourseOfferingRepository;
import edu.ucsb.cs56.ucsb_courses_search.repositories.TimeSlotRepository;
import edu.ucsb.cs56.ucsb_courses_search.repositories.TutorAssignmentRepository;
import edu.ucsb.cs56.ucsb_courses_search.entities.Tutor;
import edu.ucsb.cs56.ucsb_courses_search.entities.TutorAssignment;
import edu.ucsb.cs56.ucsb_courses_search.entities.CourseOffering;
import edu.ucsb.cs56.ucsb_courses_search.entities.TimeSlot;

import java.util.List;

@ControllerAdvice
public class RepositoryControllerAdvice {

    private final TutorRepository tutorRepository;
    private final CourseOfferingRepository courseOfferingRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final TutorAssignmentRepository tutorAssignmentRepository;
    
    @Autowired
    public RepositoryControllerAdvice(TutorRepository tutorRepository,
            CourseOfferingRepository courseOfferingRepository, TimeSlotRepository timeSlotRepository,
            TutorAssignmentRepository tutorAssignmentRepository) {
        this.tutorRepository = tutorRepository;
        this.courseOfferingRepository = courseOfferingRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.tutorAssignmentRepository = tutorAssignmentRepository;
    }

    @ModelAttribute("tutors")
    public Iterable<Tutor> getTutors() {
        return tutorRepository.findAll();
    }

    @ModelAttribute("courseOfferings")
    public Iterable<CourseOffering> getCourseOfferings() {
        return courseOfferingRepository.findAll();
    }

    @ModelAttribute("timeSlots")
    public Iterable<TimeSlot> getTimeSlots() {
        return timeSlotRepository.findAll();
    }

    @ModelAttribute("tutorAssignments")
    public Iterable<TutorAssignment> getTutorAssignments() {
        return tutorAssignmentRepository.findAll();
    }
}
