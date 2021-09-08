# Java log tracing

A idéia desse reposítório e realizar uma POC de comunicação entre dois frameworks utilizando o compartilhamento de traceId de uma requisição para impressão nos logs das aplicações. Foi utilizado o [Spring Boot com o Spring Cloud Sleuth](https://spring.io/projects/spring-cloud-sleuth) e o [Quarkus com o OpenTracing](quarkus.io/guides/opentracing).

# Executando a aplicação

- Faça o clone do projeto

- Execute o build

```
make build
```

- Gere as imagens docker

```
make install
```

- Inicie os clients

```
make run
```

# Testando

As quatro aplicações serão iniciadas, com chamadas encadeadas entre elas.

`spring_log_tracing_1 (8080) -> quarkus_log_tracing_1 (8090) -> spring_log_tracing_2 (8081) -> quarkus_log_tracing_2 (8091)`

Ao chamar por exemplo o primeiro client: `http://localhost:8080/ping` a aplicação chamará as demais conforme o fluxo acima.

Nota-se o objetivo do projeto ao monitorar os logs no qual foi configurado com o seguinte pattern:

`%d{HH:mm:ss.SSS} [%X{traceId},%X{spanId}] [%t] %-5p [%c{2.}] - %s%e%n`

Ex. de uma requisição na primeira app:

```
spring_log_tracing_1     | 19:26:23.047 [97062ee8e4cb8052,97062ee8e4cb8052] [http-nio-8080-exec-1] INFO  [b.c.helpdev.logtrace.PingController] - new request log sample - spring_log_tracing_1
spring_log_tracing_1     | 19:26:23.047 [97062ee8e4cb8052,97062ee8e4cb8052] [http-nio-8080-exec-1] INFO  [b.c.helpdev.logtrace.PingController] - external ping is enabled, executing external call
quarkus_log_tracing_1    | 19:26:23.051 [97062ee8e4cb8052,3bc49d26bd6a0a16] [executor-thread-0] INFO  [br.co.he.lo.GreetingResource] - new request log sample - quarkus_log_tracing_1
quarkus_log_tracing_1    | 19:26:23.051 [97062ee8e4cb8052,3bc49d26bd6a0a16] [executor-thread-0] INFO  [br.co.he.lo.GreetingResource] - external ping is enabled, executing external call
spring_log_tracing_2     | 19:26:23.055 [97062ee8e4cb8052,d4610433c856530f] [http-nio-8081-exec-2] INFO  [b.c.helpdev.logtrace.PingController] - new request log sample - spring_log_tracing_2
spring_log_tracing_2     | 19:26:23.055 [97062ee8e4cb8052,d4610433c856530f] [http-nio-8081-exec-2] INFO  [b.c.helpdev.logtrace.PingController] - external ping is enabled, executing external call
quarkus_log_tracing_2    | 19:26:23.059 [97062ee8e4cb8052,31697b8ca95d0ac4] [executor-thread-0] INFO  [br.co.he.lo.GreetingResource] - new request log sample - quarkus_log_tracing_2
quarkus_log_tracing_2    | 19:26:23.059 [97062ee8e4cb8052,31697b8ca95d0ac4] [executor-thread-0] INFO  [br.co.he.lo.GreetingResource] - external ping is disabled! return direct PONG
```