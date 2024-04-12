package cool.cfapps.studentservice.config;

import feign.Feign;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@LoadBalancerClient(value = "address-service")
public class AddressServiceLoadBalancerConfig {

    //@LoadBalanced
    //@Bean
    public Feign.Builder feignBuilder() {
        return Feign.builder();
    }
}
