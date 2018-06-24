package com.sample.useraccount.routes;

import org.springframework.stereotype.Component;

//import org.apache.camel.builder.RouteBuilder;

//@Component
public class UserRoute { /*extends RouteBuilder {

  @Override
  public void configure() throws Exception {

        //from("direct:user")
        from("timer:trigger?period=500").streamCaching()
        .log("User Request : ${body}")
        .hystrix()
          .hystrixConfiguration()
            .executionTimeoutInMilliseconds(5000)
            .circuitBreakerSleepWindowInMilliseconds(10000)
          .end()
        .to("http://localhost:8081/user/U12345")
            .log("http://localhost:8081/user/${body}")
          .onFallback()
            .transform()
            .constant("Error message")
          .end()
        .log("User Response : ${body}");
        //.to();
  }*/

}
