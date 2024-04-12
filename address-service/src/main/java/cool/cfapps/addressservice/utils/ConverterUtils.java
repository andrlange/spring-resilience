package cool.cfapps.addressservice.utils;

import cool.cfapps.addressservice.dto.AddressRequest;
import cool.cfapps.addressservice.dto.AddressResponse;
import cool.cfapps.addressservice.entity.AddressEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConverterUtils {

    public static AddressResponse entityToDto(AddressEntity addressEntity) {
        return AddressResponse.builder()
                .id(addressEntity.getId())
                .street(addressEntity.getStreet())
                .city(addressEntity.getCity())
                .state(addressEntity.getState())
                .zipCode(addressEntity.getZipCode())
                .country(addressEntity.getCountry())
                .build();
    }

    public static Optional<AddressResponse> entityToDto(Optional<AddressEntity> addressEntity) {
        if(addressEntity.isPresent()){
            AddressResponse addressResponse = AddressResponse.builder()
                    .id(addressEntity.get().getId())
                    .street(addressEntity.get().getStreet())
                    .city(addressEntity.get().getCity())
                    .state(addressEntity.get().getState())
                    .zipCode(addressEntity.get().getZipCode())
                    .country(addressEntity.get().getCountry())
                    .build();
            return Optional.of(addressResponse);
        } else {
            return Optional.empty();
        }
    }

    public static List<AddressResponse> entityToDto(List<AddressEntity> addressEntities) {
        List<AddressResponse> addressResponses = new ArrayList<>();
        for(AddressEntity addressEntity : addressEntities){
            addressResponses.add(entityToDto(addressEntity));
        }
        return addressResponses;
    }

    public static AddressEntity dtoToEntity(AddressRequest addressRequest) {
        return AddressEntity.builder()
                .street(addressRequest.getStreet())
                .city(addressRequest.getCity())
                .state(addressRequest.getState())
                .zipCode(addressRequest.getZipCode())
                .country(addressRequest.getCountry())
                .build();
    }
}
