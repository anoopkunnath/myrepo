package com.sample.useraccount.routes;

import org.springframework.stereotype.Component;

//import org.apache.camel.builder.RouteBuilder;

//@Component
public class AccountRoute { /*extends RouteBuilder {

  //@Override
  public void configure() throws Exception {

        from("direct:account")
        //from("timer:trigger?period=500").streamCaching()
        .log("Account Request : ${body}")
        .hystrix()
          .hystrixConfiguration()
            .executionTimeoutInMilliseconds(5000)
            .circuitBreakerSleepWindowInMilliseconds(10000)
          .end()
        .to("http://localhost:8082/account/${body}")
          .onFallback()
            .transform()
            .constant("Fallback message")
          .end()
        .log("Account Response : ${body}");
        //.to("mock:result");
  }*/

}
