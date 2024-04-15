package cool.cfapps.studentservice.controller;

import cool.cfapps.studentservice.dto.StudentRequest;
import cool.cfapps.studentservice.dto.StudentResponse;
import cool.cfapps.studentservice.service.StudentService;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.core.functions.CheckedSupplier;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        return studentService.getStudent(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@RequestBody StudentRequest studentRequest) {
        log.info("create Student: {}", studentRequest);
        return studentService.createStudent(studentRequest).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }


    @GetMapping
    @Bulkhead(name = "bulkheadWithConcurrentCalls")
    public List<StudentResponse> getAllStudents() {

        List<StudentResponse> studentResponses = new ArrayList<>();
        try {
            studentResponses = studentService.getAllStudents();
        } catch (BulkheadFullException e) {
            log.error("Bulkhead 'bulkheadWithConcurrentCalls' is full and does not permit further calls");
        }
        return studentResponses;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id) ? ResponseEntity.ok(true) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> updateStudent(@PathVariable Long id, @RequestBody StudentRequest studentRequest) {
        return studentService.updateStudent(id, studentRequest).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }
}
