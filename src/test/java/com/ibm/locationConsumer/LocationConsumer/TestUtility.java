package com.ibm.locationConsumer.LocationConsumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.locationConsumer.dto.Location;
import com.ibm.locationConsumer.dto.LocationEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class TestUtility {

    public Mono<LocationEntity> getLocEntity(int id) throws JsonProcessingException {
        String locationString = "{\"bdas\":null,\"name\":\"amniso1\",\"locationId\":\"I9XVMBBKTMMHL\",\"status\":\"InActive\",\"bdaType\":null,\"country\":null,\"geoType\":\"Continent\",\"parents\":null,\"validTo\":\"2022-07-17\",\"huluName\":null,\"latitude\":null,\"portFlag\":false,\"timeZone\":null,\"longitude\":null,\"validFrom\":\"2022-07-17\",\"restricted\":null,\"description\":null,\"dialingCode\":null,\"bdaLocations\":null,\"isDuskCity\":false,\"olsonTimezone\":null,\"alternateCodes\":[{\"code\":\"I9XVMBBKTMMHL\",\"codeType\":\"locationId\"}],\"alternateNames\":[{\"name\":\"amnira\",\"status\":\"InActive\",\"description\":null},{\"name\":\"sjsksklk12344\",\"status\":\"Active\",\"description\":\"sjsksklk\"}],\"subCityParents\":null,\"utcOffsetMinutes\":null,\"workaroundReason\":null,\"daylightSavingEnd\":null,\"daylightSavingTime\":null,\"daylightSavingStart\":null,\"postalCodeMandatory\":null,\"dialingCodeDescription\":null,\"stateProvinceMandatory\":null,\"daylightSavingShiftMinutes\":null}";
        String jsonData = locationString.replaceAll("^\"|\"$|\\\\", "").trim();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Location location = objectMapper.readValue(jsonData, Location.class);
        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setId(id);
        locationEntity.setLocationName(location.getName());
        locationEntity.setLocationType(location.getGeoType());
        locationEntity.setLocationCode(location.getAlternateCodes().get(0).getCode());
        locationEntity.setLocationCodeType(location.getAlternateCodes().get(0).getCodeType());
        locationEntity.setLocation(locationString);
        Mono<LocationEntity> locationEntityMono = Mono.just(locationEntity);
        return locationEntityMono;
    }

    public static Mono<LocationEntity> getLocEntity() throws JsonProcessingException {
        Mono<String> locationString = Mono.just("{\"bdas\":null,\"name\":\"amniso\",\"locationId\":\"I9XVMBBKTMMHL\",\"status\":\"InActive\",\"bdaType\":null,\"country\":null,\"geoType\":\"Continent\",\"parents\":null,\"validTo\":\"2022-07-17\",\"huluName\":null,\"latitude\":null,\"portFlag\":false,\"timeZone\":null,\"longitude\":null,\"validFrom\":\"2022-07-17\",\"restricted\":null,\"description\":null,\"dialingCode\":null,\"bdaLocations\":null,\"isDuskCity\":false,\"olsonTimezone\":null,\"alternateCodes\":[{\"code\":\"I9XVMBBKTMMHL\",\"codeType\":\"locationId\"}],\"alternateNames\":[{\"name\":\"amnira\",\"status\":\"InActive\",\"description\":null},{\"name\":\"sjsksklk12344\",\"status\":\"Active\",\"description\":\"sjsksklk\"}],\"subCityParents\":null,\"utcOffsetMinutes\":null,\"workaroundReason\":null,\"daylightSavingEnd\":null,\"daylightSavingTime\":null,\"daylightSavingStart\":null,\"postalCodeMandatory\":null,\"dialingCodeDescription\":null,\"stateProvinceMandatory\":null,\"daylightSavingShiftMinutes\":null}");
        Mono<String> jsonData = locationString.map(data -> data.replaceAll("^\"|\"$|\\\\", "").trim());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        LocationEntity locationEntity = new LocationEntity();
        Mono<LocationEntity> monoLocation = jsonData.map(data -> {
            try {
                Location location = objectMapper.readValue(data, Location.class);
                locationEntity.setLocationName(location.getName());
                locationEntity.setLocationType(location.getGeoType());
                locationEntity.setLocationCode(location.getAlternateCodes().get(0).getCode());
                locationEntity.setLocationCodeType(location.getAlternateCodes().get(0).getCodeType());
                locationEntity.setLocation(data);
                return locationEntity;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        monoLocation.subscribe();
        return monoLocation;
    }

    public Flux<LocationEntity> getLocEntities() throws JsonProcessingException {
        Flux<String> locationString = Flux.just("{\"bdas\":[{\"name\":\"ESVCI\",\"type\":\"Business Defined Area\",\"bdaType\":\"POOL\",\"alternateCodes\":[{\"code\":\"DNUN67OM64J6T\",\"codeType\":\"locationId\"}]}],\"name\":\"Huerta\",\"locationId\":\"005ER3ADTRUJF\",\"status\":\"Active\",\"bdaType\":null,\"country\":{\"name\":\"Spain\",\"alternateCodes\":[{\"code\":\"ES\",\"codeType\":\"localcode1\"},{\"code\":\"0NA73RG3ECXHA\",\"codeType\":\"locationId\"},{\"code\":\"127\",\"codeType\":\"localcode2\"}]},\"geoType\":\"City\",\"parents\":[{\"name\":\"Spain\",\"type\":\"Country\",\"bdaType\":null,\"alternateCodes\":[{\"code\":\"ES\",\"codeType\":\"localcode1\"},{\"code\":\"0NA73RG3ECXHA\",\"codeType\":\"locationId\"},{\"code\":\"127\",\"codeType\":\"localcode2\"}]}],\"validTo\":\"2019-08-05\",\"huluName\":null,\"latitude\":\"40.968135\",\"portFlag\":false,\"timeZone\":null,\"longitude\":\"-5.468873\",\"validFrom\":\"1900-01-01\",\"restricted\":null,\"description\":null,\"dialingCode\":null,\"bdaLocations\":null,\"isDuskCity\":true,\"olsonTimezone\":null,\"alternateCodes\":[{\"code\":\"005ER3ADTRUJF\",\"codeType\":\"locationId\"},{\"code\":\"ESH1R\",\"codeType\":\"localcode1\"},{\"code\":\"A(W\",\"codeType\":\"localcode2\"}],\"alternateNames\":null,\"subCityParents\":null,\"utcOffsetMinutes\":null,\"workaroundReason\":null,\"daylightSavingEnd\":null,\"daylightSavingTime\":null,\"daylightSavingStart\":null,\"postalCodeMandatory\":null,\"dialingCodeDescription\":null,\"stateProvinceMandatory\":null,\"daylightSavingShiftMinutes\":null}",
                "{\"bdas\":[{\"name\":\"BRSSZ\",\"type\":\"Business Defined Area\",\"bdaType\":\"POOL\",\"alternateCodes\":[{\"code\":\"OLVYMO8TLMMBU\",\"codeType\":\"locationId\"}]}],\"name\":\"Nova Friburgo\",\"locationId\":\"005JS6F2RZ7DH\",\"status\":\"Active\",\"bdaType\":null,\"country\":{\"name\":\"Brazil\",\"alternateCodes\":[{\"code\":\"33YYZ8LYAH9XA\",\"codeType\":\"locationId\"},{\"code\":\"305\",\"codeType\":\"localcode1\"},{\"code\":\"BR\",\"codeType\":\"localcode2\"}]},\"geoType\":\"City\",\"parents\":[{\"name\":\"Rio de Janeiro\",\"type\":\"State/Prov\",\"bdaType\":null,\"alternateCodes\":[{\"code\":\"0ZCBU5C36L5QP\",\"codeType\":\"locationId\"},{\"code\":\"RJ\",\"codeType\":\"ISO TERRITORY\"}]}],\"validTo\":\"2019-08-05\",\"hsudName\":null,\"latitude\":\"-22.287136\",\"portFlag\":false,\"timeZone\":null,\"longitude\":\"-42.533698\",\"validFrom\":\"1900-01-01\",\"restricted\":null,\"description\":null,\"dialingCode\":null,\"bdaLocations\":null,\"isMaerskCity\":true,\"olsonTimezone\":null,\"alternateCodes\":[{\"code\":\"BRNFU\",\"codeType\":\"localcode1\"},{\"code\":\"60M\",\"codeType\":\"localcode2\"},{\"code\":\"005JS6F2RZ7DH\",\"codeType\":\"locationId\"}],\"alternateNames\":null,\"subCityParents\":null,\"utcOffsetMinutes\":null,\"workaroundReason\":null,\"daylightSavingEnd\":null,\"daylightSavingTime\":null,\"daylightSavingStart\":null,\"postalCodeMandatory\":null,\"dialingCodeDescription\":null,\"stateProvinceMandatory\":null,\"daylightSavingShiftMinutes\":null}");
        Flux<String> jsonData = locationString.map(data -> data.replaceAll("^\"|\"$|\\\\", "").trim());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        LocationEntity locationEntity = new LocationEntity();
        Flux<LocationEntity> flux = jsonData.map(data -> {
            try {
                Location location1 = objectMapper.readValue(data, Location.class);
                locationEntity.setLocationName(location1.getName());
                locationEntity.setLocationType(location1.getGeoType());
                locationEntity.setLocationCode(location1.getAlternateCodes().get(0).getCode());
                locationEntity.setLocationCodeType(location1.getAlternateCodes().get(0).getCodeType());
                locationEntity.setLocation(data);

                return locationEntity;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        flux.subscribe();
        return flux;
    }
}
