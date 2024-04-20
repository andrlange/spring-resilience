package cool.cfapps.studentservice.controller;

import cool.cfapps.studentservice.dto.StudentResponse;
import cool.cfapps.studentservice.service.StudentService;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
@Slf4j
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    @Observed(
            name = "user.name",
            contextualName = "student-->address",
            lowCardinalityKeyValues = {"userType", "userType2"}
    )
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable Long id) {
        log.info("getStudentById: {}", id);
        return studentService.getStudent(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }



    @GetMapping
    @Bulkhead(name = "bulkheadWithConcurrentCalls")
    public List<StudentResponse> getAllStudents() {
        log.info("getAllStudents()");
        return  studentService.getAllStudents();
    }

}
