# Spring Resilience Demo v1.0

## Setup
- you can run PostgreSQL using the docker-compose.yaml
- Create to additional Databases (addresses, students)

PostgreSQL, PGAdmin and Zipkin is started using docker compose up -d


## Run Environment

Services:
- Eureka Service as Microservice and Gateway Registry
- Spring Cloud Gateway for routing and load balancing (Actuators, Zipkin and Micrometer)
- Student-Service (resilience4j, Spring AOP, CircuitBreaker, FeignClient, Actuators, Zipkin and Micrometer)
- Address-Service (Actuators, Zipkin and Micrometer)

application.properties will show you the configuration of used Spring Components

## URLs
- Eureka > http://localhost:8761
- Zipkin > http://localhost:9411
- Student using the Gateway: http://localhost:9090/student-service/api/v1/student/0



(Maintainer: Andreas Lange)