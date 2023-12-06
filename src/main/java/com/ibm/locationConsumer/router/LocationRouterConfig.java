package com.ibm.locationConsumer.router;


import com.ibm.locationConsumer.handler.LocationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;



@Configuration
public class LocationRouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(LocationHandler handler){
        return route().path(
                        "/router", builder ->
                                builder.GET("/demo",handler::getDemo)
                                        .GET("/id/{id}",handler::getLocation)
                                        .GET("/name/{locName}",handler::getLocByName)
                                        .GET("/locationType/{locType}",handler::getLocByType)
                                        .GET("/locCode/{locCode}",handler::getLocByCode)
                                        .GET("/locCodeType/{locCodeType}",handler::getLocByCodeType))
                .build();
    }
}
