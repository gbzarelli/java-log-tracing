package br.com.helpdev.logtrace;

import feign.Contract;
import feign.jaxrs.JAXRSContract;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class SpringApplication {

  public static void main(String[] args) {
    org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
  }

  @Bean
  Contract contract() {
    return new JAXRSContract();
  }

}
