package cool.cfapps.springcloudgateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class CustomFilter implements GlobalFilter {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        // Prefilter
        log.info("request uri: {}", request.getURI()+ ", Authorization: " + request.getHeaders().get("Authorization"));

        if(request.getURI().getPath().equals("/api/v1/student")){
            log.info(("student api request"));
        }

        // Response -> Post Filter
        return chain.filter(exchange).then(Mono.fromRunnable(() -> log.info("response status: {}",
                exchange.getResponse().getStatusCode())));
    }
}
