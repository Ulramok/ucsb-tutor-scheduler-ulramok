package edu.ucsb.cs56.ucsb_courses_search.controllers;

import edu.ucsb.cs56.ucsb_courses_search.CSVToTutors;
import edu.ucsb.cs56.ucsb_courses_search.entities.Tutor;
import edu.ucsb.cs56.ucsb_courses_search.repositories.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class CSVUploadController {
    @Autowired
    TutorRepository tutorRepository;

    @RequestMapping(path = "/tutorCSVUpload", method = RequestMethod.POST)
    public String uploadCSV(@RequestParam("file") MultipartFile file,
                            RedirectAttributes redirectAttributes) throws Exception {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "index";
        }
        try{
            List<Tutor> tutors = CSVToTutors.tutorBuilder(file);
            tutorRepository.saveAll(tutors);
            redirectAttributes.addFlashAttribute("message", "You successfully uploaded '" + file.getOriginalFilename() + "'");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }
}