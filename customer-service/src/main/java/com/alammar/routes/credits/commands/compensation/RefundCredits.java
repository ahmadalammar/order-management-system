package com.alammar.routes.credits.commands.compensation;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RefundCredits extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:refundCredits")
            .routeId("RefundCredits")
            .log("Credits ${body} refunded successfully!");
    }
}
