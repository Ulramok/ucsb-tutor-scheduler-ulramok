package edu.ucsb.cs56.ucsb_courses_search;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import edu.ucsb.cs56.ucsb_courses_search.entities.Tutor;
import edu.ucsb.cs56.ucsb_courses_search.entities.TutorBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CSVToTutors {
    public static List<Tutor> tutorBuilder(MultipartFile file) throws Exception {
        try {
                Reader reader = new InputStreamReader(file.getInputStream());
            ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
            strategy.setType(TutorBean.class);
            String[] memberFieldsToBindTo = {"id", "firstname", "lastname", "email", "level"};
            strategy.setColumnMapping(memberFieldsToBindTo);

            CsvToBean<TutorBean> csvToBean = new CsvToBeanBuilder(reader)
                    .withMappingStrategy(strategy)
                    .withSkipLines(1)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            Iterator<TutorBean> csvTutorIterator = csvToBean.iterator();
            List<Tutor> tutors = new ArrayList<>();
            while(csvTutorIterator.hasNext()) {
                TutorBean tutorBean = csvTutorIterator.next();
                Tutor tutor = new Tutor(tutorBean);
                tutors.add(tutor);
            }
            return tutors;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return null;
    }
}