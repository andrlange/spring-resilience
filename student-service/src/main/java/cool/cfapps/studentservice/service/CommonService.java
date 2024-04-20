package cool.cfapps.studentservice.service;

import cool.cfapps.studentservice.client.AddressFeignClient;
import cool.cfapps.studentservice.dto.AddressResponse;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
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
        log.info("Calling address service cnt: {}", cnt);
        AddressResponse response = addressFeignClient.getAddressById(addressId);
        return Optional.of(response);
    }

    public Optional<AddressResponse> getStudentAddressNoLimit(Long addressId) {
        AddressResponse response = addressFeignClient.getAddressByIdNoLimit(addressId);
        return Optional.of(response);
    }

}
