package cool.cfapps.studentservice.service;

import cool.cfapps.studentservice.client.AddressFeignClient;
import cool.cfapps.studentservice.dto.AddressEmbedded;
import cool.cfapps.studentservice.dto.AddressResponse;
import cool.cfapps.studentservice.dto.StudentRequest;
import cool.cfapps.studentservice.dto.StudentResponse;
import cool.cfapps.studentservice.entity.StudentEntity;
import cool.cfapps.studentservice.repository.StudentRepository;
import cool.cfapps.studentservice.utils.ConverterUtil;
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
    private final AddressFeignClient addressFeignClient;

    public StudentService(StudentRepository studentRepository, CommonService commonService, AddressFeignClient addressFeignClient) {
        this.studentRepository = studentRepository;
        this.commonService = commonService;
        this.addressFeignClient = addressFeignClient;
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

    public Optional<StudentResponse> createStudent(StudentRequest studentRequest) {
        StudentEntity studentEntity = ConverterUtil.dtoToEntity(studentRequest);
        AddressEmbedded addressEmbedded = studentRequest.getAddress();

        try {
            log.info("try to create address: {}", addressEmbedded);
            AddressResponse addressResponse = addressFeignClient.createAddress(addressEmbedded);
            log.info("address created: {}", addressResponse);
            studentEntity.setAddressId(addressResponse.getId());
            log.info("try to create student: {}", studentEntity);
            StudentEntity result = studentRepository.save(studentEntity);
            StudentResponse response = ConverterUtil.entityToDto(result);
            response.setAddress(ConverterUtil.dtoToDto(addressResponse));
            return Optional.of(response);
        } catch (Exception e) {
            log.info("Student not created: {}", studentRequest);
            return Optional.empty();
        }

    }

    public List<StudentResponse> getAllStudents() {
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

    public boolean deleteStudent(Long id) {
        Optional<StudentEntity> student = studentRepository.findById(id);
        if (student.isPresent()) {
            long addressId = student.get().getAddressId();
            try {
                addressFeignClient.deleteAddressById(addressId);
                studentRepository.delete(student.get());
                return true;
            } catch (Exception e) {
                log.info("Student not deleted id: {}", id);
                return false;
            }
        } else {
            return false;
        }
    }

    public Optional<StudentResponse> updateStudent(Long id, StudentRequest studentRequest) {

        log.info("try to update student: {}", studentRequest);
        Optional<StudentEntity> student = studentRepository.findById(id);
        if (student.isPresent()) {
            AddressEmbedded addressEmbedded = studentRequest.getAddress();
            long addressId = student.get().getAddressId();
            try {
                AddressResponse addressResponse = addressFeignClient.getAddressById(addressId);
                AddressEmbedded address = ConverterUtil.dtoToDto(addressResponse);
                if (!address.equals(addressEmbedded)) {
                    addressFeignClient.updateAddressById(addressId, addressEmbedded);
                }
            } catch (Exception e) {
                log.info("Student not updated id: {}", id);
            }
            StudentEntity newStudent = StudentEntity.builder()
                    .id(id)
                    .firstName(studentRequest.getFirstName())
                    .lastName(studentRequest.getLastName())
                    .email(studentRequest.getEmail())
                    .addressId(addressId)
                    .build();

            if (!student.get().equalsWithoutAddress(studentRequest)) {
                log.info("try to update student core data: {}", newStudent);
                newStudent = studentRepository.save(newStudent);
            }

            StudentResponse response = ConverterUtil.entityToDto(newStudent);
            response.setAddress(addressEmbedded);
            return Optional.of(response);
        }
        return Optional.empty();
    }
}
