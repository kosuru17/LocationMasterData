package com.ibm.locationconsumer.repository;

import com.ibm.locationconsumer.dto.LocationEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface LocationRepo extends ReactiveCrudRepository<LocationEntity,Integer> {
    @Query("SELECT EXISTS(SELECT 1 FROM locations WHERE location_name = :name)")
    Mono<Boolean> existsByLocationName(String name);

    @Query("Select * from locations where location_name LIKE :locName")
    Flux<LocationEntity> findByLocName(String locName);
    @Query("Select * from locations where location_type=:locationType")
    Flux<LocationEntity> findByLocType(String locType);

    @Query("Select * from locations where location_code=:locCode")
    Mono<LocationEntity> findByLocCode(String locCode);

    @Query("Select * from locations where location_code_type=:locCodeType")
    Flux<LocationEntity> findByLocCodeType(String locCodeType);
}
