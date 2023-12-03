package com.ibm.locationConsumer.handler;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.locationConsumer.dto.Location;
import com.ibm.locationConsumer.dto.LocationEntity;
import com.ibm.locationConsumer.repository.LocationRepo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;



@Slf4j
@RequiredArgsConstructor
@AllArgsConstructor
@Component
public class LocationHandler {

    @Autowired
    private LocationRepo locationRepo;

    private ObjectMapper objectMapper;

    public Mono<ServerResponse> getDemo(ServerRequest request) {
     return ServerResponse.ok().body(Mono.just(1).log(),Integer.class);
    }

    public Mono<ServerResponse> getLocation(ServerRequest request){
        String id = request.pathVariable("id");
        Mono<LocationEntity> locationEntityMono = locationRepo.findById(Integer.parseInt(id))
                .switchIfEmpty(Mono.error(new RuntimeException("Location not found with the id : "+ id)));
        return ok().body(locationEntityMono, LocationEntity.class);
    }

    public Mono<ServerResponse> getLocByName(ServerRequest request){
        String locName = request.pathVariable("locName");
       // Optional<String> locName = request.queryParam("locName").map(value -> value + "%");
        Mono<LocationEntity> locationEntity = locationRepo.findByLocName(locName).switchIfEmpty(Mono.error(new RuntimeException("Location with the given name is not found.")));


        Mono<Location> location = locationEntity.flatMap(data -> {
            try {
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                return Mono.just(objectMapper.readValue(data.getLocation(), Location.class));
            } catch (JsonProcessingException e) {
                return Mono.error(new RuntimeException("Error while deserializing to Location object."));
            }
        });
        return ok().body(location, Location.class);
    }

    public Mono<ServerResponse> getLocByType(ServerRequest request){
        String locType = request.pathVariable("locType");
        Flux<LocationEntity> locEntity = locationRepo.findByLocType(locType).switchIfEmpty(Mono.error(new RuntimeException("Location with the given type is not found.")));
        Flux<Location> locations = locEntity.map(data-> {
            // Deserialize the JSON string to a Location object
            try {
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                return objectMapper.readValue(data.getLocation(), Location.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error while deserializing to Location object.");
            }

        });
        return ok().body(locations, Location.class);
    }

    public Mono<ServerResponse> getLocByCode(ServerRequest request){
        String locCode = request.pathVariable("locCode");
        Mono<LocationEntity> locationEntity = locationRepo.findByLocCode(locCode).switchIfEmpty(Mono.error(new RuntimeException("Location with the given location code is not found.")));;
        Mono<Location> locations = locationEntity.flatMap(data-> {
            // Deserialize the JSON string to a Location object
            try {
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                return Mono.just(objectMapper.readValue(data.getLocation(), Location.class));
            } catch (JsonProcessingException e) {
                return Mono.error(new RuntimeException("Error while deserializing to Location object."));
            }
        });
        return ok().body(locations, Location.class);
    }

    public Mono<ServerResponse> getLocByCodeType(ServerRequest request){
        String locCodeType = request.pathVariable("locCodeType");
        Flux<LocationEntity> locationEntityFlux = locationRepo.findByLocCodeType(locCodeType).switchIfEmpty(Mono.error(new RuntimeException("Location with the given code type is not found.")));;
        Flux<Location> locations = locationEntityFlux.map(data-> {
            // Deserialize the JSON string to a Location object
            try {
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                return objectMapper.readValue(data.getLocation(), Location.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error while deserializing to Location object.");
            }
        });
        return ok().body(locations, Location.class);
    }

}
