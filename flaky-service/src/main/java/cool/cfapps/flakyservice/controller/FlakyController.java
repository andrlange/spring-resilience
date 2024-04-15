package cool.cfapps.flakyservice.controller;

import cool.cfapps.flakyservice.dto.FlakyDto;
import cool.cfapps.flakyservice.service.FlakyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flaky")
@Slf4j
public class FlakyController {

    private final FlakyService flakyService;

    public FlakyController(FlakyService flakyService) {
        this.flakyService = flakyService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<FlakyDto>> getAll() {
        return ResponseEntity.ok(flakyService.getAllFlakyDtos());
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<FlakyDto> getByCode(@PathVariable String code) {
       double rand =  (Math.random());
        if(rand < 0.5) {
            return ResponseEntity.status(500).build();
        }

         FlakyDto flakyDto = flakyService.getFlakyDtoByCode(code).orElse(null);
        log.info("Course:"+flakyDto);
        return flakyService.getFlakyDtoByCode(code).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

    }
}
