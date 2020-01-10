package edu.ucsb.cs56.ucsb_courses_search.controllers;

import edu.ucsb.cs56.ucsb_courses_search.entities.Tutor;
import edu.ucsb.cs56.ucsb_courses_search.TutorsToCSV;
import edu.ucsb.cs56.ucsb_courses_search.repositories.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class CSVDownloadController {
    @Autowired
    TutorRepository tutorRepository;

    @GetMapping("/tutorCSVDownload")
    public void downloadCSV(HttpServletResponse response) throws IOException{
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; file=tutors.csv");
        List<Tutor> tutors =  (List<Tutor>) tutorRepository.findAll();

        TutorsToCSV.writeTutors(response.getWriter(), tutors);
    }
}
