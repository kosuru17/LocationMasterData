package com.ibm.locationConsumer.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.locationConsumer.dto.Location;
import com.ibm.locationConsumer.dto.LocationEntity;
import com.ibm.locationConsumer.repository.LocationRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Slf4j
@Service
public class ConsumerHandler {

    @Autowired
    private LocationRepo repo;

    public Mono<LocationEntity> createLoc(String locationString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // Deserialize the JSON string to a Location object
        Location location = objectMapper.readValue(locationString, Location.class);

        Mono<LocationEntity> saveLoc = repo.existsByLocationName(location.getName())
                .flatMap(exists -> {
                    if (exists) {
                        log.error("Location with name " + location.getName() + " already exists.");
                        return Mono.just(new LocationEntity());
                    } else {
                        LocationEntity locationEntity = new LocationEntity();
                        locationEntity.setLocationName(location.getName());
                        locationEntity.setLocationType(location.getGeoType());
                        locationEntity.setLocationCode(location.getAlternateCodes().get(0).getCode());
                        locationEntity.setLocationCodeType(location.getAlternateCodes().get(0).getCodeType());
                        locationEntity.setLocation(locationString);

                        return repo.save(locationEntity)
                                .doOnError(e -> log.error("Error occurred while trying to save data to db."));
                    }
                });
        saveLoc.subscribe();
        return saveLoc;
    }
}

