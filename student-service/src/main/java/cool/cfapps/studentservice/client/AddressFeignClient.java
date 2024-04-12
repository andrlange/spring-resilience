package cool.cfapps.studentservice.client;

import cool.cfapps.studentservice.dto.AddressEmbedded;
import cool.cfapps.studentservice.dto.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "spring-cloud-gateway")
public interface AddressFeignClient {

    @GetMapping("/address-service/api/v1/address/{id}")
    AddressResponse getAddressById(@PathVariable Long id);// throws Exception;

    @PostMapping("/address-service/api/v1/address/")
    AddressResponse createAddress(AddressEmbedded addressEmbedded) throws Exception;

    @DeleteMapping("/address-service/api/v1/address//{id}")
    void deleteAddressById(@PathVariable Long id) throws Exception;

    @PutMapping("/address-service/api/v1/address//{id}")
    void updateAddressById(@PathVariable Long id, @RequestBody AddressEmbedded addressEmbedded) throws Exception;

}
