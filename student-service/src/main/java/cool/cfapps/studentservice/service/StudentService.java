package cool.cfapps.studentservice.service;

import cool.cfapps.studentservice.client.AddressFeignClient;
import cool.cfapps.studentservice.dto.AddressEmbedded;
import cool.cfapps.studentservice.dto.AddressResponse;
import cool.cfapps.studentservice.dto.StudentRequest;
import cool.cfapps.studentservice.dto.StudentResponse;
import cool.cfapps.studentservice.entity.StudentEntity;
import cool.cfapps.studentservice.repository.StudentRepository;
import cool.cfapps.studentservice.utils.ConverterUtil;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequestMapping("/api/v1/student")
public class StudentService {

    private final StudentRepository studentRepository;
    private final CommonService commonService;

    public StudentService(StudentRepository studentRepository, CommonService commonService) {
        this.studentRepository = studentRepository;
        this.commonService = commonService;
    }


    public Optional<StudentResponse> getStudent(Long id) {
        Optional<StudentEntity> student = studentRepository.findById(id);
        if (student.isPresent()) {
            long addressId = student.get().getAddressId();
            Optional<AddressResponse> address = commonService.getStudentAddress(addressId);
            StudentResponse studentResponse = ConverterUtil.entityToDto(student.get());
            studentResponse.setAddress(new AddressEmbedded());
            address.ifPresent(addressResponse -> studentResponse.setAddress(ConverterUtil.dtoToDto(addressResponse)));
            return Optional.of(studentResponse);
        } else {
            return Optional.empty();
        }
    }


    public List<StudentResponse> getAllStudents(){
        try{Thread.sleep(500);} catch (InterruptedException e) {}
        List<StudentEntity> students = studentRepository.findAll();
        List<StudentResponse> studentResponses = new ArrayList<>();
        students.forEach(student -> {
            long addressId = student.getAddressId();
            Optional<AddressResponse> address = commonService.getStudentAddressNoLimit(addressId);
            StudentResponse studentResponse = ConverterUtil.entityToDto(student);
            studentResponse.setAddress(new AddressEmbedded());
            address.ifPresent(addressResponse -> studentResponse.setAddress(ConverterUtil.dtoToDto(addressResponse)));
            studentResponses.add(studentResponse);
        });

        return studentResponses;
    }

}
