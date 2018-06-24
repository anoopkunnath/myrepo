package com.sample.useraccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@EnableAutoConfiguration
@EnableCircuitBreaker
@EnableHystrix
@SpringBootApplication
public class UserAccountApplication extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(UserAccountApplication.class);
  }

  public static void main(String... args) {
    SpringApplication.run(UserAccountApplication.class, args);
  }

}
