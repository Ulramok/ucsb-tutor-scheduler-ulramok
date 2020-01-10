package edu.ucsb.cs56.ucsb_courses_search.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;	
import org.springframework.web.bind.annotation.PostMapping;	
import org.springframework.web.bind.annotation.RequestParam;

import edu.ucsb.cs56.ucsb_courses_search.entities.TutorAssignment;
import edu.ucsb.cs56.ucsb_courses_search.entities.Tutor;
import edu.ucsb.cs56.ucsb_courses_search.entities.CourseOffering;
import edu.ucsb.cs56.ucsb_courses_search.repositories.TutorAssignmentRepository;
import edu.ucsb.cs56.ucsb_courses_search.repositories.TutorRepository;
import edu.ucsb.cs56.ucsb_courses_search.repositories.CourseOfferingRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;


@Controller
public class TutorAssignmentController {

  private Logger logger = LoggerFactory.getLogger(TutorAssignmentController.class);

  private final TutorAssignmentRepository tutorAssignmentRepository;
  private final TutorRepository tutorRepository;
  private final CourseOfferingRepository courseOfferingRepository;

  @Autowired
  public TutorAssignmentController(TutorAssignmentRepository tutorAssignmentRepository, TutorRepository tutorRepository,
      CourseOfferingRepository courseOfferingRepository) {
    this.tutorAssignmentRepository = tutorAssignmentRepository;
    this.tutorRepository = tutorRepository;
    this.courseOfferingRepository = courseOfferingRepository;
  }

  public boolean checkIfAssigned(Iterable<TutorAssignment> iter, CourseOffering courseOffering){
    boolean isAssignedQuarter = false;
    for(TutorAssignment assignment:iter ) {
      if(courseOffering.getQuarter().equals(assignment.getCourseOffering().getQuarter())){
        isAssignedQuarter = true;
      }
    }
    return isAssignedQuarter;
  }
  @PostMapping("/tutorAssignments/add")
  public String add(@RequestParam(name = "cid") long cid, @RequestParam(name = "tid") long tid, Model model) {
    Tutor tutor = tutorRepository.findById(tid)
        .orElseThrow(() -> new IllegalArgumentException("Invalid tutor Id:" + tid));
    CourseOffering courseOffering = courseOfferingRepository.findById(cid)
        .orElseThrow(() -> new IllegalArgumentException("Invalid course offering Id:" + cid));

    Iterable<TutorAssignment> iter = tutor.getTutorAssignments();

    boolean isAssignedQuarter = checkIfAssigned(iter, courseOffering);

    if(isAssignedQuarter == true) {
      final String errorIsAssignedQuarter = "Tutor is already assigned a course";
      model.addAttribute("errorIsAssignedQuarter", errorIsAssignedQuarter);
      return "index";
    }

    TutorAssignment TutorAssignment = new TutorAssignment(tutor, courseOffering);
    tutorAssignmentRepository.save(TutorAssignment);
    model.addAttribute("tutorAssignments", tutorAssignmentRepository.findAll());
    return "index";
  }

  @GetMapping("/tutorAssignments/delete/{id}")
  public String delete(@PathVariable("id") long id, Model model) {
    TutorAssignment a = tutorAssignmentRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid tutor assignment Id:" + id));
    tutorAssignmentRepository.delete(a);
    return "index";
  }


  @PostMapping(value = "/tutorAssignments/chooseQuarter")
  public String chooseQuarter(@RequestParam(name = "tutorAssignmentsQuarterFilter", defaultValue="")
                              String tutorAssignmentsQuarterFilter, Model model) {
    logger.info("LOOK HERE! tutorAssignmentsQuarterFilter = " + tutorAssignmentsQuarterFilter);
    model.addAttribute("tutorAssignmentsQuarterFilter", tutorAssignmentsQuarterFilter);
    return "index";
  }

}