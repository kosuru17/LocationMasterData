package com.ibm.locationconsumer.LocationConsumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.locationconsumer.dto.LocationEntity;
import com.ibm.locationconsumer.handler.LocationHandler;
import com.ibm.locationconsumer.repository.LocationRepo;
import com.ibm.locationconsumer.router.LocationRouterConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.mock;


@SpringBootTest
@AutoConfigureWebTestClient
@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class LocationApiTest {

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private LocationRepo locationRepo;
    @MockBean
    LocationHandler locationHandler;

    private TestUtility testUtility = new TestUtility();


    @BeforeEach
    void setUp() {
        locationRepo = mock(LocationRepo.class);
        locationHandler = new LocationHandler(locationRepo, new ObjectMapper());
        RouterFunction<?> routes = new LocationRouterConfig()
                .routerFunction(locationHandler);
        webTestClient = WebTestClient.bindToRouterFunction(routes)
                .build();
    }

    @Test
    void getDemo() {
        webTestClient.get()
                .uri("http://localhost:8085/router/demo")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Integer.class);
    }

    @Test
    void getLocById() throws JsonProcessingException {
        int id = 56;
        String name = "amniso";
        Mono<LocationEntity> entityMono = testUtility.getLocEntity(id);
        Mockito.when(locationRepo.findById(id)).thenReturn(entityMono);

        webTestClient.get()
                .uri("http://localhost:8085/router/id/{id}", id)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.name").isEqualTo(name);
    }

    @Test
    void testGetLocByName() throws JsonProcessingException {
        String locName = "Huerta";
        Flux<LocationEntity> entityFlux = testUtility.getLocEntities();
        Mockito.when(locationRepo.findByLocName(locName + "%")).thenReturn(entityFlux);
        webTestClient.get()
                .uri("http://localhost:8085/router/name/{locName}", locName)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].name").isEqualTo(locName);
    }

    @Test
    void testGetLocByType() throws JsonProcessingException {
        String locType = "City";
        Flux<LocationEntity> entityFlux = testUtility.getLocEntities();
        Mockito.when(locationRepo.findByLocType(locType)).thenReturn(entityFlux);
        webTestClient.get()
                .uri("http://localhost:8085/router/locationType/{locType}", locType)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].geoType").isEqualTo(locType)
                .jsonPath("[1].geoType").isEqualTo(locType);
    }

    @Test
    void testGetLocByCode() throws JsonProcessingException {
        String locCode = "I9XVMBBKTMMHL";
        Mono<LocationEntity> entityMono = testUtility.getLocEntity();
        Mockito.when(locationRepo.findByLocCode(locCode)).thenReturn(entityMono);
        webTestClient.get()
                .uri("http://localhost:8085/router/locCode/{locCode}", locCode)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.alternateCodes[0].code").isEqualTo(locCode);
    }

    @Test
    void testGetLocByCodeType() throws JsonProcessingException {
        String locCodeType = "locationId";
        Flux<LocationEntity> entityFlux = testUtility.getLocEntities();
        Mockito.when(locationRepo.findByLocCodeType(locCodeType)).thenReturn(entityFlux);
        webTestClient.get()
                .uri("http://localhost:8085/router/locCodeType/{locCodeType}", locCodeType)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].alternateCodes[0].codeType").isEqualTo(locCodeType);
    }

}
