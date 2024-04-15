package cool.cfapps.flakyservice.service;

import com.netflix.discovery.EurekaClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j

public class EurekaShutdownHandler implements ApplicationListener<ContextClosedEvent> {

    private final EurekaClient eurekaClient;

    public EurekaShutdownHandler(EurekaClient eurekaClient) {
        this.eurekaClient = eurekaClient;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        try {
            // Inform Eureka server about the shutdown
            eurekaClient.shutdown();
        } catch (Exception e) {
            // Log the exception during Eureka deregistration
            log.error("Failed to deregister with Eureka: " + e.getMessage());
        }
    }
}
