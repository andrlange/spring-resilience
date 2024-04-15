package cool.cfapps.studentservice.service;

import cool.cfapps.studentservice.client.AddressFeignClient;
import cool.cfapps.studentservice.dto.FlakyDto;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FlakyService {

    private int cnt = 1;
    private final AddressFeignClient addressFeignClient;

    public FlakyService(AddressFeignClient addressFeignClient) {
        this.addressFeignClient = addressFeignClient;
    }


    @Retry(name = "flakyRetry")
    public Optional<FlakyDto> getFlakyByCode(String code) {

        log.info("getFlakyByCode: {} : call: {}", code,cnt);
        FlakyDto dto = addressFeignClient.getFlakyByCode(code);
        if(dto == null) {throw new RuntimeException();}
        cnt++;
        return Optional.of(dto);
    }

    public List<FlakyDto> getAllFlaky() {
        return addressFeignClient.getAllFlaky();
    }
}
