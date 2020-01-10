package edu.ucsb.cs56.ucsb_courses_search;

import com.opencsv.CSVWriter;
import edu.ucsb.cs56.ucsb_courses_search.entities.Tutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.util.List;

public class TutorsToCSV {
    private static Logger logger = LoggerFactory.getLogger(TutorsToCSV.class);
    public static void writeTutors(PrintWriter writer, List<Tutor> tutors) {
        String[] CSV_HEADER = {"id", "firstname", "lastname", "email", "level"};
        try (
                CSVWriter csvWriter = new CSVWriter(writer,
                        CSVWriter.DEFAULT_SEPARATOR,
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);
        ) {
            csvWriter.writeNext(CSV_HEADER);

            for (Tutor tutor : tutors) {
                String[] data = {
                        String.valueOf(tutor.getId()),
                        tutor.getFname(),
                        tutor.getLname(),
                        tutor.getEmail(),
                        tutor.getLevel()
                };

                csvWriter.writeNext(data);
            }
            csvWriter.close();
            logger.info("Write CSV using CSVWriter successfully!");
        } catch (Exception e) {
            logger.info("Writing CSV error!");
            e.printStackTrace();
        }
    }
}