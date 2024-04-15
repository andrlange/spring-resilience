package cool.cfapps.studentservice.client;

import cool.cfapps.studentservice.dto.AddressResponse;
import cool.cfapps.studentservice.dto.FlakyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "spring-cloud-gateway")
public interface FlakyFeignClient {

    @GetMapping("/flaky-service/flaky/code/{code}")
    FlakyDto getFlakyByCode(@PathVariable String code);

    @GetMapping("/flaky-service/flaky/all")
    List<FlakyDto> getAllFlaky();

}
