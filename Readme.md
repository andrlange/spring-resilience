# Spring Resilience Demo v1.0

## Setup
- you can run PostgreSQL using the docker-compose.yaml
- Create to additional Databases (addresses, students)


## Run Environment

Services:
- Eureka Service as Microservice and Gateway Registry
- Spring Cloud Gateway for routing and load balancing
- Student-Service (resilience4j, Spring AOP, CircuitBreaker, FeignClient, Actuators)
- Address-Service (Actuators)

application.properties will show you the configuration of used Spring Components


(Maintainer: Andreas Lange)