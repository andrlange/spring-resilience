package cool.cfapps.addressservice.config;

import cool.cfapps.addressservice.entity.NewsRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class NewsListener {


    @Bean
    public Consumer<NewsRecord> news() {
        return news -> log.info("Received News! \"{}\" created on '{}'", news.title(), news.createdOn());
    }
}
