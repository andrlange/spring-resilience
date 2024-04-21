package cool.cfapps.studentservice.controller;

import cool.cfapps.studentservice.dto.NewsDto;
import cool.cfapps.studentservice.entity.NewsRecord;
import cool.cfapps.studentservice.service.NewsPublisherService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/news")
public class NewsController {

    private final NewsPublisherService newsPublisher;

    public NewsController(NewsPublisherService newsPublisher) {
        this.newsPublisher = newsPublisher;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void publishNews(@Valid @RequestBody NewsDto newsDto) {
        newsPublisher.send(new NewsRecord(newsDto.title(), Instant.now()));
    }
}
