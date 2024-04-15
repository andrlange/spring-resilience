package cool.cfapps.studentservice.client;

import cool.cfapps.studentservice.dto.AddressEmbedded;
import cool.cfapps.studentservice.dto.AddressResponse;
import cool.cfapps.studentservice.dto.FlakyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "spring-cloud-gateway")
public interface AddressFeignClient {

    @GetMapping("/address-service/api/v1/address/{id}")
    AddressResponse getAddressById(@PathVariable Long id);// throws Exception;

    @GetMapping("/address-service/api/v1/address/nolimit/{id}")
    AddressResponse getAddressByIdNoLimit(@PathVariable Long id);

    @PostMapping("/address-service/api/v1/address/")
    AddressResponse createAddress(AddressEmbedded addressEmbedded) throws Exception;

    @DeleteMapping("/address-service/api/v1/address//{id}")
    void deleteAddressById(@PathVariable Long id) throws Exception;

    @PutMapping("/address-service/api/v1/address//{id}")
    void updateAddressById(@PathVariable Long id, @RequestBody AddressEmbedded addressEmbedded) throws Exception;

    @GetMapping("/flaky-service/flaky/code/{code}")
    FlakyDto getFlakyByCode(@PathVariable String code);

    @GetMapping("/flaky-service/flaky/all")
    List<FlakyDto> getAllFlaky();
}
