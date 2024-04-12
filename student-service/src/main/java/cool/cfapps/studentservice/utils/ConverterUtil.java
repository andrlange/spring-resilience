package cool.cfapps.studentservice.utils;

import cool.cfapps.studentservice.dto.AddressEmbedded;
import cool.cfapps.studentservice.dto.AddressResponse;
import cool.cfapps.studentservice.dto.StudentRequest;
import cool.cfapps.studentservice.dto.StudentResponse;
import cool.cfapps.studentservice.entity.StudentEntity;

public class ConverterUtil {
     public static StudentResponse entityToDto(StudentEntity studentEntity) {
        if(studentEntity != null) {
            return StudentResponse.builder()
                    .id(studentEntity.getId())
                    .firstName(studentEntity.getFirstName())
                    .lastName(studentEntity.getLastName())
                    .email(studentEntity.getEmail())
                    .build();
        } else {
            return null;
        }
    }

    public static AddressEmbedded dtoToDto(AddressResponse addressResponse) {
         return AddressEmbedded.builder()
                 .state(addressResponse.getState())
                 .zipCode(addressResponse.getZipCode())
                 .country(addressResponse.getCountry())
                 .city(addressResponse.getCity())
                 .street(addressResponse.getStreet())
                 .fallback(addressResponse.isFallback())
                 .build();
    }

    public static StudentEntity dtoToEntity(StudentRequest studentRequest) {
         return StudentEntity.builder()
                 .firstName(studentRequest.getFirstName())
                 .lastName(studentRequest.getLastName())
                 .email(studentRequest.getEmail())
                 .build();


    }

}
