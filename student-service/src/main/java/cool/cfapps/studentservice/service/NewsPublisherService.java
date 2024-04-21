package cool.cfapps.studentservice.service;

import cool.cfapps.studentservice.entity.NewsRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NewsPublisherService {


    private final StreamBridge streamBridge;

    public NewsPublisherService(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void send(NewsRecord news) {
        streamBridge.send("news-out-0", news);
        log.info("{} sent!", news);
    }
}
