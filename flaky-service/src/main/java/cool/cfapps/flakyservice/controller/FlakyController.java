package cool.cfapps.flakyservice.controller;

import cool.cfapps.flakyservice.dto.FlakyDto;
import cool.cfapps.flakyservice.service.FlakyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

record Book(String title){}

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

        log.info("get all flaky courses");
        return ResponseEntity.ok(flakyService.getAllFlakyDtos());
    }

    /*
     * getByCode returns a course code if it exists, otherwise returns a 404
     * 50% of all calls will return a 500 Internal Server Error to simulate a flaky service
     *
     */
    @GetMapping("/code/{code}")
    public ResponseEntity<FlakyDto> getByCode(@PathVariable String code) {
        double rand = (Math.random());
        if (rand < 0.5) {
            log.info("return 500 Internal Server Error");
            return ResponseEntity.status(500).build();
        }

        FlakyDto flakyDto = flakyService.getFlakyDtoByCode(code).orElse(null);
        log.info("return Course:" + flakyDto);
        return flakyService.getFlakyDtoByCode(code).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/version")
    public String getVersion() {
        try {Thread.sleep(5000);} catch (InterruptedException e) {e.printStackTrace();}
        return "1.0.0";
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok(new ArrayList<>(List.of(new Book("Lord of the Rings"), new Book("Hobbit"),
                new Book("Silmarillion"))));
    }
}
