package com.alammar.routes.order.commands.compensation;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CancelProcessOrder extends RouteBuilder {
    @Override
    public void configure() {
        from("direct:cancelProcessOrder")
            .routeId("CancelProcessOrder")
            .process(exchange ->
                log.info("Cancelling Processing OrderId: " + exchange.getIn().getBody()))
            .log("Process Order ${body} cancelled successfully!");
    }
}
