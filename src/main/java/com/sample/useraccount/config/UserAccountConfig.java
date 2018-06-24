package com.sample.useraccount.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//import org.apache.camel.component.hystrix.metrics.servlet.HystrixEventStreamServlet;

@Configuration
public class UserAccountConfig {

  /*@Bean
  public HystrixEventStreamServlet hystrixServlet() {
    return new HystrixEventStreamServlet();
  }

  @Bean
  public ServletRegistrationBean servletRegistrationBean() {
    return new ServletRegistrationBean(hystrixServlet(), "/hystrix.stream");
  }*/

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

}
