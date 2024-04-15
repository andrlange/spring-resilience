# Spring Resilience Demo v1.1

(EUREKA, SPRING CLOUD CONFIG SERVER, SPRING CLOUD GATEWAY, CIRCUIT BREAKER, ACTUATORS, MICROMETER, ZIPKIN, FEIGN, 
RESILIENCE4J)
## Setup
- you can run PostgreSQL using the docker-compose.yaml
- Create to additional Databases (addresses, students)

PostgreSQL, PGAdmin and Zipkin is started using docker compose up -d


## Run Environment

Services:
- Eureka Service as Microservice and Gateway Registry
- Spring Cloud Config Server using https://github.com/andrlange/spring-resilience.git path=config
- Spring Cloud Gateway for routing and load balancing (Actuators, Zipkin and Micrometer)
- Student-Service (resilience4j, Spring AOP, Circuit Breaker, Feign Client, Actuators, Zipkin and Micrometer, Retry)
- Address-Service (Actuators, Zipkin and Micrometer, RateLimiter)
- Flaky-Service (Actuators, Zipkin and Micrometer) returns Randomly Courses

application.properties will show you the configuration of used Spring Components

Start order:
- Eureka Server
- Config Server
- Spring Cloud Gateway
- Student Service & Address Service
- Flaky Service

Note: to run multiple services for each, just export PORT=80xx to your environment and start multiple student or 
address microservices on different ports.

## URLs
- Eureka > http://localhost:8761
- Zipkin > http://localhost:9411
- Student using the Gateway: http://localhost:9090/student-service/api/v1/student/0
- Student retries: http://localhost:9090/student-service/api/v1/flaky/code/BIO



(Maintainer: Andreas Lange)