package cool.cfapps.studentservice.controller;

import cool.cfapps.studentservice.dto.FlakyDto;
import cool.cfapps.studentservice.service.FlakyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/flaky")
@Slf4j
public class FlakyController {

    private final FlakyService flakyService;

    public FlakyController(FlakyService flakyService) {
        this.flakyService = flakyService;
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<FlakyDto> getFlakyByCode(@PathVariable String code) {
        log.info("getFlakyByCode: {}", code);
        return flakyService.getFlakyByCode(code).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<FlakyDto>> getAllFlaky() {
        log.info("getAllFlaky");
        return ResponseEntity.ok(flakyService.getAllFlaky());
    }

    @GetMapping("/version")
    public CompletableFuture<String> getVersionFlaky() {
        log.info("getVersionFlaky");
        return flakyService.getVersionFlaky();
    }
}
