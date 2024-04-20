package cool.cfapps.studentservice.service;

import cool.cfapps.studentservice.client.AddressFeignClient;
import cool.cfapps.studentservice.dto.FlakyDto;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import io.github.resilience4j.retry.annotation.Retry;


import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class FlakyService {

    // The total number of calls to the /api/v1/retry/code/{code} endpoint
    private int callCnt = 1;
    // the total number of retries, reset to 0 after each call
    private int retryCnt = 0;

    private final AddressFeignClient addressFeignClient;


    public FlakyService(AddressFeignClient addressFeignClient) {
        this.addressFeignClient = addressFeignClient;
    }


    @Retry(name = "flakyRetry")
    public Optional<FlakyDto> getFlakyByCode(String code) {
        retryCnt++;
        log.info("getFlakyByCode: {} : call:retry = {}:{}", code, callCnt, retryCnt);
        FlakyDto dto = addressFeignClient.getFlakyByCode(code);
        // If we don't receive a response from the server, throw a runtime exception to retry
        if (dto == null) {
            throw new RuntimeException();
        }
        callCnt++;
        retryCnt = 0;
        return Optional.of(dto);
    }

    public List<FlakyDto> getAllFlaky() {
        return addressFeignClient.getAllFlaky();
    }

    @TimeLimiter(name = "flakyVersion", fallbackMethod = "getVersionFlakyTimeout")
    public CompletableFuture<String> getVersionFlaky() {
        return CompletableFuture.supplyAsync(addressFeignClient::getVersionFlaky);
    }

    public CompletableFuture<String> getVersionFlakyTimeout(Throwable th) {
        log.info("getVersionFlaky Fallback");
        return CompletableFuture.completedFuture("Not Available:" + th.getMessage());
    }
}
