package cool.cfapps.addressservice.controller;

import cool.cfapps.addressservice.dto.AddressRequest;
import cool.cfapps.addressservice.dto.AddressResponse;
import cool.cfapps.addressservice.service.AddressService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/address")
@Slf4j
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping()
    public List<AddressResponse> getAllAddresses() {
        return addressService.findAll();
    }

    @GetMapping("/{id}")
    @Observed(
            name = "user.name",
            contextualName = "address-->database",
            lowCardinalityKeyValues = {"userType", "userType2"}
    )
    @RateLimiter(name = "addressLimit", fallbackMethod = "addressErrorResponse")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable Long id) {
        log.info("get address with id: {}", id);
        Optional<AddressResponse> result = addressService.findById(id);
        return result.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<AddressResponse> addressErrorResponse(@PathVariable Long id, Throwable th) {
        log.info("fallback on rate limit hit");
        Optional<AddressResponse> result = Optional.of(AddressResponse.builder()
                .failure(true)
                .build());
        return result.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(@RequestBody AddressRequest addressRequest) {
        log.info(addressRequest.toString());
        return addressService.create(addressRequest).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAddress(@PathVariable Long id) {
        return addressService.delete(id) ? ResponseEntity.ok(true) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable Long id, @RequestBody AddressRequest addressRequest) {
        return addressService.update(id, addressRequest).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }
}
