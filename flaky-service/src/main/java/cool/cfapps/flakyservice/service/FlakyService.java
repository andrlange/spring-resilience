package cool.cfapps.flakyservice.service;

import cool.cfapps.flakyservice.dto.FlakyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FlakyService {

    List<FlakyDto> courses = new ArrayList<>();

    public FlakyService() {
        courses.add(FlakyDto.builder().courseCode("INF").courseName("Informatics").build());
        courses.add(FlakyDto.builder().courseCode("NUM").courseName("Numeric Algebra").build());
        courses.add(FlakyDto.builder().courseCode("PHY").courseName("Physics").build());
        courses.add(FlakyDto.builder().courseCode("BIO").courseName("Biology").build());
    }


    public Optional<FlakyDto> getFlakyDtoByCode(String code) {

        for(FlakyDto course : courses) {
            if(course.getCourseCode().equals(code)) {
                return Optional.of(course);
            }
        }

        return Optional.empty();

    }

    public List<FlakyDto> getAllFlakyDtos() {
        return courses;
    }
}
