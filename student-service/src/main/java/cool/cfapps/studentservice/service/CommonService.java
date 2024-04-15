package cool.cfapps.studentservice.service;

import cool.cfapps.studentservice.client.AddressFeignClient;
import cool.cfapps.studentservice.dto.AddressResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class CommonService {

    private final AddressFeignClient addressFeignClient;
    private int cnt=0;

    public CommonService(AddressFeignClient addressFeignClient) {
        this.addressFeignClient = addressFeignClient;
    }

    @CircuitBreaker(name = "addressService", fallbackMethod = "getStudentAddressFallback")
    public Optional<AddressResponse> getStudentAddress(Long addressId) {
        cnt++;
        log.info("Calling address service with cnt: {}", cnt);
        //if(cnt % 2 == 0) {throw new RuntimeException();}
        AddressResponse response = addressFeignClient.getAddressById(addressId);
        return Optional.of(response);
    }

    public Optional<AddressResponse> getStudentAddressNoLimit(Long addressId) {
        AddressResponse response = addressFeignClient.getAddressByIdNoLimit(addressId);
        return Optional.of(response);
    }

    public Optional<AddressResponse> getStudentAddressFallback(Long addressId, Throwable th) {
        log.error("Address service did not returned with cnt:{} a valid address for: {} -> falling back", cnt,
                addressId);
        return Optional.of(AddressResponse.builder()
                .street("Street")
                .city("City")
                .state("State")
                .zipCode("ZipCode")
                .country("Country")
                .fallback(true)
                .build());
    }

}
