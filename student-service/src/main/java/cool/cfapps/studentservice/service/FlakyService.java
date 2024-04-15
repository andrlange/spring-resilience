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
    private int retryCnt = 0;
    private final AddressFeignClient addressFeignClient;

    public FlakyService(AddressFeignClient addressFeignClient) {
        this.addressFeignClient = addressFeignClient;
    }


    @Retry(name = "flakyRetry")
    public Optional<FlakyDto> getFlakyByCode(String code) {
        retryCnt++;
        log.info("getFlakyByCode: {} : call: {}:{}", code, cnt, retryCnt);
        FlakyDto dto = addressFeignClient.getFlakyByCode(code);
        if (dto == null) {
            throw new RuntimeException();
        }
        cnt++;
        retryCnt = 0;
        return Optional.of(dto);
    }

    public List<FlakyDto> getAllFlaky() {
        return addressFeignClient.getAllFlaky();
    }
}
