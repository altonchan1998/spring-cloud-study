# spring-cloud-study

### H2 DataBase Console
- {baseUrl}/h2-console

### Swagger UI
- {baseUrl}/swagger-ui/index.html

### Spring Config Monitor
- With Spring Cloud Bus -> Automate applications refresh with config update 
- https://docs.spring.io/spring-cloud-config/docs/current/reference/html/#_push_notifications_and_spring_cloud_bus

### Spring Cloud Bus
- {baseUrl}/actuator/busrefresh
- trigger every application subscribed for the same bus queue to refresh
- Used to broadcast state changes (e.g. configuration changes) or other management instructions. AMQP and Kafka broker implementations are included with the project.
- Alternative approach to refresh applications in runtime

### Actuator API
- {baseUrl}/actuator/refresh
- refresh API: allow reload changed external properties without re-deploy
- drawback: need to manually call api with times of no. of application -> use Spring Cloud Bus

### Config Server
- {baseUrl}/{application-name}/{profile}

### RabbitMQ
- 5672 for application ; 15672 for console
- docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.12-management

