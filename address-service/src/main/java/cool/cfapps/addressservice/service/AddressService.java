package cool.cfapps.addressservice.service;

import cool.cfapps.addressservice.dto.AddressRequest;
import cool.cfapps.addressservice.dto.AddressResponse;
import cool.cfapps.addressservice.entity.AddressEntity;
import cool.cfapps.addressservice.repository.AddressRepository;
import cool.cfapps.addressservice.utils.ConverterUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<AddressResponse> findAll() {
        return ConverterUtils.entityToDto(addressRepository.findAll());
    }

    public Optional<AddressResponse> findById(Long id) {
        return ConverterUtils.entityToDto(addressRepository.findById(id));
    }

    public Optional<AddressResponse> create(AddressRequest addressRequest) {
        if (addressRequest == null) {
            return Optional.empty();
        } else {
            AddressEntity result = addressRepository.save(ConverterUtils.dtoToEntity(addressRequest));
            return Optional.of(ConverterUtils.entityToDto(result));
        }
    }

    public boolean delete(Long id) {
        boolean isPresent = addressRepository.findById(id).isPresent();
        log.info("Address:"+id+":"+isPresent);
        if (isPresent) {
            addressRepository.deleteById(id);
        }
        return isPresent;
    }

    public Optional<AddressResponse> update(Long id, AddressRequest addressRequest) {
        Optional<AddressEntity> addressEntity = addressRepository.findById(id);
        if(addressEntity.isPresent()){
            AddressEntity entity = ConverterUtils.dtoToEntity(addressRequest);
            entity.setId(id);
            return Optional.of(ConverterUtils.entityToDto(addressRepository.save(entity)));
        }
        else {
            return Optional.empty();
        }
    }
}
